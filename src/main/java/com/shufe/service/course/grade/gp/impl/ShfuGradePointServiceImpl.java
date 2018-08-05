/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author cheneystar
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * cheneystar             2008-11-5            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.grade.gp.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.bean.comparators.PropertyComparator;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.teach.program.service.GradeFilter;
import com.ekingstar.eams.teach.program.service.SubstituteCourseService;
import com.ekingstar.eams.teach.program.service.impl.SubstituteGradeFilter;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.grade.gp.StdGP;
import com.shufe.model.course.grade.gp.StdGPPerTerm;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * @author chaostone
 * 
 */
public class ShfuGradePointServiceImpl extends GradePointServiceImpl {
    
    protected SubstituteCourseService substituteCourseService;
    
    private String preservedGrades = "2005-9,2004-9,2004-3";
    
    public StdGP statStdGPA(Student std, List grades) {
        if (grades.isEmpty()) {
            return new StdGP(std);
        }
        MajorType majorType = ((CourseGrade) grades.get(0)).getMajorType();
        return this.statStdGPA(std, majorType, Boolean.TRUE, grades, null);
    }
    
    public Float statStdGPA(Student std, TeachCalendar calendar, MajorType majorType,
            Boolean isPublished, Boolean isBestGrade) {
        return this.statStdGPA(std, majorType, isPublished, null, isBestGrade).getGPA();
    }
    
    public StdGP statStdGPA(Student std, MajorType majorType, Boolean isPublished) {
        return this.statStdGPA(std, majorType, isPublished, null, null);
    }
    
    /**
     * 首先获得各个学期的学分*绩点和学分(group by semester),<br>
     * 然后在此基础上生成每学期的绩点<br>
     * 并依照各个学期的学分*绩点和学分计算总的平均绩点
     * **IMPORTANT* 这里算出的绩点就是最好绩点，不论传入的成绩list是什么。
     */
    public StdGP statStdGPA(Student std, MajorType majorType, Boolean isPublished, List grades,
            Boolean isBestGrade) {
        if (StringUtils.contains(preservedGrades, std.getGrade())) {
            return super.statStdGPA(std, majorType, isPublished);
        }
        List<CourseGrade> stdGrades = grades;
        EntityQuery entityQuery = new EntityQuery(CourseGrade.class, "grade");
        // 是否指定成绩
        if (CollectionUtils.isNotEmpty(stdGrades)) {
            addConditionOfGradeId(stdGrades, entityQuery);
        } else {
            if (null != majorType) {
                entityQuery.add(new Condition("grade.majorType=:majorType", majorType));
            }
            if (null != isPublished) {
                if (isPublished.booleanValue()) {
                    entityQuery.add(new Condition("grade.status=:status", Grade.PUBLISHED));
                } else {
                    entityQuery.add(new Condition("grade.status<>:status", Grade.PUBLISHED));
                }
            }
            entityQuery.add(new Condition("grade.std=:std", std));
            stdGrades = (List) utilService.search(entityQuery);
            if (BooleanUtils.isTrue(isBestGrade)) {
                GradeFilter filter = new SubstituteGradeFilter(substituteCourseService
                        .getStdSubstituteCourses(std, majorType));
                stdGrades = filter.filter(stdGrades);
                addConditionOfGradeId(stdGrades, entityQuery);
            }
        }
        entityQuery
                .setSelect("select grade.calendar.id,sum(CASE WHEN grade.GP is not null then grade.course.credits ELSE 0 END), "
                        + "sum(grade.course.credits*grade.GP),sum(grade.course.credits*grade.score),"
                        + "count(*),sum(CASE WHEN grade.isPass=true THEN grade.course.credits ELSE 0 END)");
        entityQuery.groupBy("grade.calendar.id");
        List rs = (List) utilDao.search(entityQuery);
        StdGP stdGP = new StdGP(std);
        double receivedCredits = 0;// 获得的总学分
        int counts = 0;// 总们数
        int creditScores = 0;// 分数=∑学分*成
        if (!rs.isEmpty()) {
            // 遍历每一个学期的成绩
            for (Iterator iter = rs.iterator(); iter.hasNext();) {
                Object[] gpInfo = (Object[]) iter.next();
                StdGPPerTerm stdGPPerTerm = new StdGPPerTerm();
                stdGPPerTerm.setCalendar((TeachCalendar) utilDao.get(TeachCalendar.class,
                        (Long) gpInfo[0]));
                double creditsTerm = (null == gpInfo[1]) ? 0 : ((Number) gpInfo[1]).doubleValue();
                double creditGPsTerm = (null == gpInfo[2]) ? 0 : ((Number) gpInfo[2]).doubleValue();
                double receivedCreditsPerTerm = (null == gpInfo[5]) ? 0 : ((Number) gpInfo[5])
                        .doubleValue();
                // 计算平均绩点和平均分
                stdGPPerTerm.setCredits(new Float(receivedCreditsPerTerm));
                double creditScoresTerm = 0;
                if (0 != creditsTerm) {
                    stdGPPerTerm.setGPA(new Float(creditGPsTerm / creditsTerm));
                    creditScoresTerm = (null == gpInfo[3]) ? 0 : ((Number) gpInfo[3]).doubleValue();
                    stdGPPerTerm.setGA(new Float(creditScoresTerm / creditsTerm));
                }
                int count = (null == gpInfo[4]) ? 0 : ((Number) gpInfo[4]).intValue();
                stdGPPerTerm.setCount(new Integer(count));
                counts += count;
                creditScores += creditScoresTerm;
                // credits += creditsTerm;
                receivedCredits += receivedCreditsPerTerm;
                stdGP.add(stdGPPerTerm);
                stdGP.setCount(new Integer(counts));
            }
            EntityQuery entityQueryGPA = new EntityQuery(CourseGrade.class, "courseGrade");
            if (null != majorType) {
                entityQueryGPA.add(new Condition("courseGrade.majorType=:majorType", majorType));
            }
            entityQueryGPA.add(new Condition("courseGrade.status=" + Grade.PUBLISHED));
            entityQueryGPA.add(new Condition("courseGrade.std=:std", std));
            List bestStdGrades = (List) utilService.search(entityQueryGPA);
            GradeFilter filter = new SubstituteGradeFilter(substituteCourseService
                    .getStdSubstituteCourses(std, majorType));
            bestStdGrades = filter.filter(bestStdGrades);
            double[] creaditsGPInfo = this.calcGradePoint(std, majorType, bestStdGrades, null);
            stdGP.setCredits(new Float(creaditsGPInfo[0]));
            if (0 != creaditsGPInfo[0]) {
                stdGP.setGPA(new Float(creaditsGPInfo[1] / creaditsGPInfo[0]));
                stdGP.setGA(new Float(creditScores / creaditsGPInfo[0]));
            }
        }
        Collections.sort(stdGP.getGPList(), new PropertyComparator("calendar.beginOn", true));
        return stdGP;
    }
    
    /**
     * @param stdGrades
     * @param entityQuery
     */
    private void addConditionOfGradeId(List<CourseGrade> stdGrades, EntityQuery entityQuery) {
        int size = stdGrades.size();
        Long[] gradeIds = new Long[size <= 50 ? size : 50];
        StringBuilder hql = new StringBuilder();
        hql.append("(");
        List<Long[]> paramValues = new ArrayList<Long[]>();
        for (int i = 0, j = 0; i < stdGrades.size(); i++) {
            if (i > 0 && 0 == i % 50) {
                hql.append("grade.id in (:gradeId" + j + ") or ");
                paramValues.add(gradeIds);
                j++;
                gradeIds = new Long[(size - i) <= 50 ? (size - i) : 50];
            }
            CourseGrade grade = (CourseGrade) stdGrades.get(i);
            gradeIds[i % 50] = grade.getId();
        }
        hql.append("grade.id in (:gradeIdFF))");
        paramValues.add(gradeIds);
        Condition gradeIdCondition = new Condition(hql.toString());
        gradeIdCondition.getValues().addAll(paramValues);
        entityQuery.add(gradeIdCondition);
    }
    
    /**
     * 得到总学分和绩点
     * 
     * @param std
     * @param majorType
     * @param gpaList
     * @return
     */
    public double[] calcGradePoint(Student std, MajorType majorType, List gpaList,
            Boolean isBestGrade) {
        double credits = 0;// 总学分
        double creditGPs = 0;// 绩点=∑绩点*学分
        double[] creditGPInfo = new double[2];
        if (!gpaList.isEmpty()) {
            for (Iterator it = gpaList.iterator(); it.hasNext();) {
                CourseGrade courseGrade = (CourseGrade) it.next();
                if (null != courseGrade.getGP()) {
                    double credit = courseGrade.getCredit().doubleValue();
                    double creditGP = (courseGrade.getCredit().doubleValue())
                            * (courseGrade.getGP().doubleValue());
                    credits += credit;
                    creditGPs += creditGP;
                }
            }
        }
        creditGPInfo[0] = credits;
        creditGPInfo[1] = creditGPs;
        return creditGPInfo;
    }
    
    public SubstituteCourseService getSubstituteCourseService() {
        return substituteCourseService;
    }
    
    public void setSubstituteCourseService(SubstituteCourseService substituteCourseService) {
        this.substituteCourseService = substituteCourseService;
    }
    
}
