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
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2007-1-3            Created
 *  
 ********************************************************************************/

package com.shufe.model.course.grade.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.ekingstar.commons.bean.comparators.ChainComparator;
import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.eams.system.config.SystemConfig;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.std.Student;
import com.shufe.web.action.course.grade.report.GradeReportSetting;

/**
 * 学生成绩单打印模型
 * 
 * @author chaostone
 */
public class StdGrade {
    
    protected Student std;
    
    protected List grades;
    
    protected List orignGrades;
    
    protected Integer printGradeType;
    
    public StdGrade() {
        super();
    }
    
    /**
     * 将grades转换成[course.id.toString,courseGrade]样式的map<br>
     * 主要用于快速根据课程找到成绩.对于重修课程(课程出现重复)对应的成绩是不可预知的. FIXME
     * 
     * @return
     */
    public Map toGradeMap() {
        Map gradeMap = new HashMap();
        if (null == grades || grades.isEmpty())
            return gradeMap;
        else {
            for (Iterator iter = grades.iterator(); iter.hasNext();) {
                CourseGrade courseGrade = (CourseGrade) iter.next();
                gradeMap.put(courseGrade.getCourse().getId().toString(), courseGrade);
            }
            return gradeMap;
        }
    }
    
    public StdGrade(Student std, List grades, Comparator cmp, Integer printGradeType) {
        this.orignGrades = grades;
        this.std = std;
        this.printGradeType = printGradeType;
        if (null != cmp) {
            Collections.sort(grades, cmp);
        }
        // 及格成绩
        if (GradeReportSetting.PASS_GRADE.equals(printGradeType)) {
            this.grades = new ArrayList(CollectionUtils.select(grades, new Predicate() {
                
                public boolean evaluate(Object arg0) {
                    Grade grade = (Grade) arg0;
                    if (Boolean.TRUE.equals(grade.getIsPass())) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }));
        } else if (GradeReportSetting.BEST_GRADE.equals(printGradeType)) {
            List newGrades = new ArrayList(grades);
            // FIXME 暂时按照最终得分进行排序,用户选择的可能不是打印最终成绩
            ChainComparator chainCmp = new ChainComparator();
            chainCmp.addComparator(new PropertyComparator("course"));
            chainCmp.addComparator(new PropertyComparator("score"));
            Collections.sort(newGrades, chainCmp);
            Map gradeMap = new HashMap();
            for (Iterator iter = newGrades.iterator(); iter.hasNext();) {
                CourseGrade grade = (CourseGrade) iter.next();
                gradeMap.put(grade.getCourse().getId(), grade);
            }
            newGrades.clear();
            newGrades.addAll(gradeMap.values());
            if (null != cmp)
                Collections.sort(newGrades, cmp);
            this.grades = newGrades;
        } else {
            this.grades = grades;
        }
    }
    
    public StdGrade(Student std, List grades, Comparator cmp, Integer printGradeType,
            boolean isFilter) {
        this(std, grades, cmp, printGradeType);
        if (!isFilter) {
            this.grades = this.orignGrades;
        }
    }
    
    /**
     * 计算学生已经获得的学分(成绩合格)
     * 
     * @return
     */
    public Float getCredit() {
        if (null == orignGrades || orignGrades.isEmpty())
            return new Float(0);
        SystemConfig config = SystemConfigLoader.getConfig();
        List gpaGrades = orignGrades;
        if ("byReportType".equals(config.getConfigItemValue("course.grade.GPAReportRule"))) {
            gpaGrades = grades;
        }
        float credits = 0;
        for (Iterator iter = gpaGrades.iterator(); iter.hasNext();) {
            CourseGrade courseGrade = (CourseGrade) iter.next();
            if (Boolean.TRUE.equals(courseGrade.getIsPass()))
                credits += courseGrade.getCredit().floatValue();
        }
        return new Float(credits);
    }
    
    /**
     * 计算学生成绩的平均绩点(及格不及格都计算在内)
     * 
     * @return
     */
    public Float getGPA() {
        if (null == orignGrades || orignGrades.isEmpty()) {
            return new Float(0);
        }
        SystemConfig config = SystemConfigLoader.getConfig();
        List gpaGrades = orignGrades;
        if ("byReportType".equals(config.getConfigItemValue("course.grade.GPAReportRule"))) {
            gpaGrades = grades;
        }
        double credits = 0;
        double gp = 0;
        for (Iterator iter = gpaGrades.iterator(); iter.hasNext();) {
            CourseGrade courseGrade = (CourseGrade) iter.next();
            credits += courseGrade.getCredit().doubleValue();
            gp += courseGrade.getCredit().doubleValue() * courseGrade.getGP().doubleValue();
        }
        if (credits == 0) {
            return new Float(0);
        } else {
            return new Float(gp / credits);
        }
    }
    
    public List getGrades() {
        return grades;
    }
    
    public Student getStd() {
        return std;
    }
    
    public void setStd(Student std) {
        this.std = std;
    }
    
}
