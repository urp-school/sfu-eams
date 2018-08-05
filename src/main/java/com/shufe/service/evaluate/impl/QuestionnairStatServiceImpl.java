//$Id: EvaluateTeacherStatServiceImpl.java,v 1.1 2008-5-19 上午09:21:23 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2008-5-19         	Created
 *  
 ********************************************************************************/

package com.shufe.service.evaluate.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.shufe.model.quality.evaluate.EvaluationCriteriaItem;
import com.shufe.model.quality.evaluate.Question;
import com.shufe.model.quality.evaluate.QuestionResult;
import com.shufe.model.quality.evaluate.stat.EvaluateCollegeStat;
import com.shufe.model.quality.evaluate.stat.EvaluateDepartmentStat;
import com.shufe.model.quality.evaluate.stat.EvaluateTeacherStat;
import com.shufe.model.quality.evaluate.stat.QuestionStat;
import com.shufe.model.quality.evaluate.stat.QuestionTeacherStat;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.evaluate.QuestionnairStatService;

/**
 * @author zhouqi
 */
public class QuestionnairStatServiceImpl implements QuestionnairStatService {
    
    protected UtilDao utilDao;
    
    /**
     * 院系排名
     * 
     * @see com.shufe.service.evaluate.QuestionnairStatService#evaluateDepartmentScorePlace(TeachCalendar)
     */
    public List evaluateDepartmentScorePlace(TeachCalendar calendar) {
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        query.add(new Condition("evaluateTeacher.calendar.id = (:calendarId)", calendar.getId()));
        List orders = new ArrayList();
        orders
                .add(new Order(
                        "evaluateTeacher.teacher.department.id, evaluateTeacher.sumScore desc"));
        query.addOrder(orders);
        List evaluateTeachers = (List) utilDao.search(query);
        
        int rank = 1;
        Long beforeDepartId = null;
        for (Iterator it = evaluateTeachers.iterator(); it.hasNext();) {
            EvaluateTeacherStat evaluateTeacher = (EvaluateTeacherStat) it.next();
            if (!ObjectUtils.equals(beforeDepartId, evaluateTeacher.getTeacher().getDepartment()
                    .getId())) {
                beforeDepartId = evaluateTeacher.getTeacher().getDepartment().getId();
                rank = 1;
            }
            evaluateTeacher.setDepartRank(new Integer(rank++));
        }
        return evaluateTeachers;
    }
    
    public List evaluateDepartmentsCount(TeachCalendar calendar) {
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        query.add(new Condition("evaluateTeacher.calendar.id = (:calendarId)", calendar.getId()));
        query.groupBy("evaluateTeacher.teacher.department.id");
        query.setSelect("evaluateTeacher.teacher.department.id, count(*)");
        List evaluateTeachers = (List) utilDao.search(query);
        List evaluateDepartments = getEvaluateDepartments(calendar);
        for (Iterator it1 = evaluateTeachers.iterator(); it1.hasNext();) {
            Object[] obj = (Object[]) it1.next();
            for (Iterator it2 = evaluateDepartments.iterator(); it2.hasNext();) {
                EvaluateDepartmentStat evaluateDepartment = (EvaluateDepartmentStat) it2.next();
                if (ObjectUtils.equals(obj[0], evaluateDepartment.getDepartment().getId())) {
                    evaluateDepartment.setCount(new Integer(obj[1].toString()));
                }
            }
        }
        return evaluateDepartments;
    }
    
    public List evaluateCoursesCount(TeachCalendar calendar) {
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        query.add(new Condition("evaluateTeacher.calendar.id = (:calendarId)", calendar.getId()));
        List results = (List) utilDao.search(query);
        
        EvaluateCollegeStat evaluateCollege = getEvaluateCollegeStat(calendar);
        evaluateCollege.setCount(new Integer(results.size()));
        List evaluateColleges = new ArrayList();
        evaluateColleges.add(evaluateCollege);
        return evaluateColleges;
    }
    
    public List evaluateTeacherScorePlace(TeachCalendar calendar) {
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        query.add(new Condition("evaluateTeacher.calendar.id = (:calendarId)", calendar.getId()));
        List orders = new ArrayList();
        orders.add(new Order("evaluateTeacher.sumScore desc"));
        query.addOrder(orders);
        List evaluateTeachers = (List) utilDao.search(query);
        
        int rank = 1;
        for (Iterator it = evaluateTeachers.iterator(); it.hasNext();) {
            EvaluateTeacherStat evaluateTeacher = (EvaluateTeacherStat) it.next();
            evaluateTeacher.setRank(new Integer(rank++));
        }
        
        return evaluateTeachers;
    }
    
    public List buildEvaluateRanks(TeachCalendar calendar) {
        // 重新统计评教数据（指定学期）
        List results = new ArrayList();
        results.addAll(evaluateTeacherScorePlace(calendar));
        results.addAll(evaluateCoursesCount(calendar));
        results.addAll(evaluateDepartmentScorePlace(calendar));
        results.addAll(evaluateDepartmentsCount(calendar));
        return results;
    }
    
    public List getEvaluateTeachers(TeachCalendar calendar) {
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        query.add(new Condition("evaluateTeacher.calendar = :teachCalendar", calendar));
        return getEvaluateTeachers(calendar, (Department) null);
    }
    
    public List getEvaluateTeachers(TeachCalendar calendar, Department department) {
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        query.add(new Condition("evaluateTeacher.calendar = :teachCalendar", calendar));
        if (null != department) {
            query
                    .add(new Condition("evaluateTeacher.teacher.department = :department",
                            department));
        }
        return (List) utilDao.search(query);
    }
    
    public List getEvaluateTeachers(TeachCalendar calendar, Teacher teacher) {
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        query.add(new Condition("evaluateTeacher.calendar = :teachCalendar", calendar));
        query.add(new Condition("evaluateTeacher.teacher = :teacher", teacher));
        return (List) utilDao.search(query);
    }
    
    public List getEvaluateTeachers(TeachCalendar calendar, boolean isAllCalendar) {
        if (isAllCalendar) {
            EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
            query.add(new Condition("evaluateTeacher.calendar.start <= :calendarStart", calendar
                    .getStart()));
            query.add(new Condition("evaluateTeacher.calendar.studentType = :studentType", calendar
                    .getStudentType()));
            return (List) utilDao.search(query);
        } else {
            return getEvaluateTeachers(calendar);
        }
    }
    
    public List getEvaluateDepartments(TeachCalendar calendar) {
        EntityQuery query = new EntityQuery(EvaluateDepartmentStat.class, "evaluateDepartment");
        query.add(new Condition("evaluateDepartment.calendar = :teachCalendar", calendar));
        return (List) utilDao.search(query);
    }
    
    public List getEvaluateDepartments(Department department) {
        EntityQuery query = new EntityQuery(EvaluateDepartmentStat.class, "evaluateDepartment");
        query.add(new Condition("evaluateDepartment.department = :department", department));
        return (List) utilDao.search(query);
    }
    
    public EvaluateTeacherStat getEvaluateTeacherStat(TeachCalendar calendar, Course course,
            Teacher teacher) {
        if (null == calendar || null == course || null == teacher) {
            return null;
        }
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        query.add(new Condition("evaluateTeacher.calendar = :teachCalendar", calendar));
        query.add(new Condition("evaluateTeacher.course = :course", course));
        query.add(new Condition("evaluateTeacher.teacher = :teacher", teacher));
        List results = (List) utilDao.search(query);
        if (CollectionUtils.isEmpty(results)) {
            return null;
        } else {
            return (EvaluateTeacherStat) results.get(0);
        }
    }
    
    public EvaluateDepartmentStat getEvaluateDepartmentStat(TeachCalendar calendar,
            Department department) {
        if (null == calendar || null == department) {
            return null;
        }
        EntityQuery query = new EntityQuery(EvaluateDepartmentStat.class, "evaluateDepartment");
        query.add(new Condition("evaluateDepartment.calendar = :teachCalendar", calendar));
        query.add(new Condition("evaluateDepartment.department = :depart", department));
        List results = (List) utilDao.search(query);
        if (CollectionUtils.isEmpty(results)) {
            return null;
        } else {
            return (EvaluateDepartmentStat) results.get(0);
        }
    }
    
    public EvaluateCollegeStat getEvaluateCollegeStat(TeachCalendar calendar) {
        if (null == calendar) {
            return null;
        }
        EntityQuery query = new EntityQuery(EvaluateCollegeStat.class, "evaluateCollege");
        query.add(new Condition("evaluateCollege.calendar = :teachCalendar", calendar));
        List results = (List) utilDao.search(query);
        if (CollectionUtils.isEmpty(results)) {
            return null;
        } else {
            return (EvaluateCollegeStat) results.get(0);
        }
    }
    
    public List getAllEvaluateColleges() {
        return utilDao.loadAll(EvaluateCollegeStat.class);
    }
    
    /**
     * 每个教师的评教统计结果
     * 
     * @return
     */
    public List buildTeacherStatResult(Long[] stdTypeIds, Long[] departmentIds,
            TeachCalendar calendar) {
        List evaluateResults = questionEvaluateTeacherList(stdTypeIds, departmentIds, calendar,
                true);
        evaluateResults.addAll(questionEvaluateTeacherList(stdTypeIds, departmentIds, calendar,
                false));
        
        Map teacherStatMap = new HashMap();
        for (Iterator it = evaluateResults.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            Teacher teacher = (Teacher) utilDao.load(Teacher.class, new Long(obj[0].toString()));
            Course course = (Course) utilDao.load(Course.class, new Long(obj[1].toString()));
            Question question = (Question) utilDao
                    .load(Question.class, new Long(obj[2].toString()));
            Double avgPoints = new Double(obj[3].toString());
            String key = teacher.getId() + "_" + course.getId();
            // 构建 EvaluateTeacherStat
            EvaluateTeacherStat evaluateTeacher = null;
            // 在已有EvaluateTeacherStat的情况下，添加问题统计结果
            // QuestionTeacherStat questionTeacher = null;
            if (teacherStatMap.containsKey(key)) {
                evaluateTeacher = (EvaluateTeacherStat) teacherStatMap.get(key);
                if (null == evaluateTeacher.getQuestionsStat()) {
                    evaluateTeacher.setQuestionsStat(new ArrayList());
                }
                // questionTeacher = new QuestionTeacherStat(evaluateTeacher, question, avgPoints);
                evaluateTeacher.getQuestionsStat().add(
                        new QuestionTeacherStat(evaluateTeacher, question, avgPoints));
            } else {
                // 否则就新建一个EvaluateTeacherStat对象，把问题统计结果加入
                evaluateTeacher = getEvaluateTeacherStat(calendar, course, teacher);
                if (null != evaluateTeacher
                        && !CollectionUtils.isEmpty(evaluateTeacher.getQuestionsStat())) {
                    evaluateTeacher.getQuestionsStat().clear();
                }
                if (null == evaluateTeacher) {
                    evaluateTeacher = new EvaluateTeacherStat();
                }
                evaluateTeacher.setTeacher(teacher);
                evaluateTeacher.setDepartment((Department) teacher.getDepartment());
                evaluateTeacher.setCourse(course);
                evaluateTeacher.setCalendar(calendar);
                if (null == evaluateTeacher.getQuestionsStat()) {
                    evaluateTeacher.setQuestionsStat(new ArrayList());
                }
                evaluateTeacher.setValidTickets(new Integer(obj[4].toString()));
                // questionTeacher = new QuestionTeacherStat(evaluateTeacher, question, avgPoints);
                evaluateTeacher.getQuestionsStat().add(
                        new QuestionTeacherStat(evaluateTeacher, question, avgPoints));
                teacherStatMap.put(key, evaluateTeacher);
            }
            // EntityQuery query = new EntityQuery(QuestionResult.class, "questionResult");
            // query.add(new Condition("questionResult.result.teachCalendar = (:calendar)",
            // calendar));
            // query.add(new Condition("questionResult.result.task.course = (:course)", course));
            // query.join("questionResult.result.task.arrangeInfo.teachers", "teacher");
            // query.add(new Condition("teacher = (:teacher)", teacher));
            // query.add(new Condition("questionResult.question = (:question)", question));
            // questionTeacher.setQuestionResults((List) utilDao.search(query));
        }
        
        List results = new ArrayList();
        for (Iterator it = teacherStatMap.entrySet().iterator(); it.hasNext();) {
            Entry entry = (Entry) it.next();
            EvaluateTeacherStat evaluateTeacher = (EvaluateTeacherStat) teacherStatMap.get(entry
                    .getKey());
            evaluateTeacher.statSumSorce();
            results.add(evaluateTeacher);
        }
        return results;
    }
    
    /**
     * @param stdTypeIds
     * @param departmentIds
     * @param calendar
     * @param evaluateByTeacher
     * @return
     */
    private List questionEvaluateTeacherList(Long[] stdTypeIds, Long[] departmentIds,
            TeachCalendar calendar, boolean evaluateByTeacher) {
        EntityQuery query = new EntityQuery(QuestionResult.class, "questionResult");
        query.add(new Condition("questionResult.result.teachCalendar.year = (:calendarYear)",
                calendar.getYear()));
        query.add(new Condition("questionResult.result.teachCalendar.term = (:calendarTerm)",
                calendar.getTerm()));
        if (null != stdTypeIds) {
            query
                    .add(new Condition("questionResult.result.stdType.id in (:stdTypeIds)",
                            stdTypeIds));
        }
        if (null != departmentIds) {
            query.add(new Condition("questionResult.result.department.id in (:departmentIds)",
                    departmentIds));
        }
        query.add(new Condition("questionResult.result.statState = true"));
        String groupBy = null;
        if (evaluateByTeacher) {
            query.add(new Condition(
                    "questionResult.result.task.requirement.evaluateByTeacher = true"));
            groupBy = "questionResult.result.teacher.id, questionResult.result.task.course.id, questionResult.question.id";
        } else {
            query.add(new Condition(
                    "questionResult.result.task.requirement.evaluateByTeacher = false"));
            query.join("questionResult.result.task.arrangeInfo.teachers", "teacher");
            groupBy = "teacher.id, questionResult.result.task.course.id, questionResult.question.id";
        }
        query.groupBy(groupBy);
        query.setSelect(groupBy + ", avg(questionResult.score), count(*)");
        return (List) utilDao.search(query);
    }
    
    /**
     * 每个院系的评教统计结果
     * 
     * @return
     */
    public List buildDepartmentStatResult(Long[] departmentIds, TeachCalendar calendar) {
        List evaluateResults = questionEvaluateTeacherList(null, departmentIds, calendar, true);
        evaluateResults.addAll(questionEvaluateTeacherList(null, departmentIds, calendar, false));
        
        Map deparmentStatMap = new HashMap();
        for (Iterator it = evaluateResults.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            Department department = (Department) ((Teacher) utilDao.load(Teacher.class, new Long(
                    obj[0].toString()))).getDepartment();
            Question question = (Question) utilDao
                    .load(Question.class, new Long(obj[2].toString()));
            Double avgPoints = new Double(obj[3].toString());
            String key = department.getId() + "";
            // 构建 EvaluateDepartmentStat
            EvaluateDepartmentStat evaluateDepartment = null;
            // 在已有EvaluateDepartmentStat的情况下，添加问题统计结果
            if (deparmentStatMap.containsKey(key)) {
                evaluateDepartment = (EvaluateDepartmentStat) deparmentStatMap.get(key);
                if (CollectionUtils.isEmpty(evaluateDepartment.getQuestionsStat())) {
                    evaluateDepartment.setQuestionsStat(new ArrayList());
                }
                evaluateDepartment.getQuestionsStat().add(
                        new QuestionStat(evaluateDepartment, question, avgPoints));
            } else {
                // 否则就新建一个EvaluateDepartmentStat对象，把问题统计结果加入
                evaluateDepartment = getEvaluateDepartmentStat(calendar, department);
                if (null != evaluateDepartment
                        && !CollectionUtils.isEmpty(evaluateDepartment.getQuestionsStat())) {
                    evaluateDepartment.getQuestionsStat().clear();
                }
                if (null == evaluateDepartment) {
                    evaluateDepartment = new EvaluateDepartmentStat();
                }
                evaluateDepartment.setCalendar(calendar);
                evaluateDepartment.setDepartment(department);
                if (null == evaluateDepartment.getQuestionsStat()) {
                    evaluateDepartment.setQuestionsStat(new ArrayList());
                }
                evaluateDepartment.getQuestionsStat().add(
                        new QuestionStat(evaluateDepartment, question, avgPoints));
                deparmentStatMap.put(key, evaluateDepartment);
            }
        }
        
        List results = new ArrayList();
        for (Iterator it = deparmentStatMap.entrySet().iterator(); it.hasNext();) {
            Entry entry = (Entry) it.next();
            EvaluateDepartmentStat evaluateDepartment = (EvaluateDepartmentStat) deparmentStatMap
                    .get(entry.getKey());
            evaluateDepartment.statSumSorce();
            results.add(evaluateDepartment);
        }
        return results;
    }
    
    public List buildCollegeStatResult(TeachCalendar calendar) {
        List evaluateResults = questionEvaluateTeacherList(null, null, calendar, true);
        evaluateResults.addAll(questionEvaluateTeacherList(null, null, calendar, false));
        
        Map collegeStatMap = new HashMap();
        for (Iterator it = evaluateResults.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            Question question = (Question) utilDao
                    .load(Question.class, new Long(obj[2].toString()));
            Double avgPoints = new Double(obj[3].toString());
            String key = calendar.getId() + "";
            EvaluateCollegeStat evaluateCollege = null;
            if (collegeStatMap.containsKey(key)) {
                evaluateCollege = (EvaluateCollegeStat) collegeStatMap.get(key);
                if (CollectionUtils.isEmpty(evaluateCollege.getQuestionsStat())) {
                    evaluateCollege.setQuestionsStat(new ArrayList());
                }
                evaluateCollege.getQuestionsStat().add(
                        new QuestionStat(evaluateCollege, question, avgPoints));
            } else {
                evaluateCollege = getEvaluateCollegeStat(calendar);
                if (null != evaluateCollege
                        && !CollectionUtils.isEmpty(evaluateCollege.getQuestionsStat())) {
                    evaluateCollege.getQuestionsStat().clear();
                }
                if (null == evaluateCollege) {
                    evaluateCollege = new EvaluateCollegeStat();
                }
                evaluateCollege.setCalendar(calendar);
                if (null == evaluateCollege.getQuestionsStat()) {
                    evaluateCollege.setQuestionsStat(new ArrayList());
                }
                evaluateCollege.getQuestionsStat().add(
                        new QuestionStat(evaluateCollege, question, avgPoints));
                collegeStatMap.put(key, evaluateCollege);
            }
        }
        
        List results = new ArrayList();
        for (Iterator it = collegeStatMap.entrySet().iterator(); it.hasNext();) {
            Entry entry = (Entry) it.next();
            EvaluateCollegeStat evaluateCollege = (EvaluateCollegeStat) collegeStatMap.get(entry
                    .getKey());
            evaluateCollege.statSumSorce();
            results.add(evaluateCollege);
        }
        return results;
    }
    
    /**
     * 评教统计
     * 
     * @see com.shufe.service.evaluate.QuestionnairStatService#buildStatResult(java.lang.Long[],
     *      java.lang.Long[], com.shufe.model.system.calendar.TeachCalendar)
     */
    public List buildStatResult(Long[] stdTypeIds, Long[] departmentIds, TeachCalendar calendar) {
        // 在统计之前先清除老数据（指定学期）
        List cleanOldResults = new ArrayList();
        cleanOldResults.addAll(getEvaluateTeachers(calendar));
        cleanOldResults.addAll(getEvaluateDepartments(calendar));
        Object obj = getEvaluateCollegeStat(calendar);
        if (null != obj) {
            cleanOldResults.add(obj);
        }
        utilDao.remove(cleanOldResults);
        
        List results = new ArrayList();
        results.addAll(buildTeacherStatResult(stdTypeIds, departmentIds, calendar));
        results.addAll(buildDepartmentStatResult(departmentIds, calendar));
        results.addAll(buildCollegeStatResult(calendar));
        return results;
    }
    
    public Object[] getCollegeResultList(List departmentResults) {
        Object[] collegeResult = null;
        for (Iterator it = departmentResults.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            if (null == collegeResult) {
                collegeResult = new Object[obj.length];
                collegeResult[0] = getEvaluateCollegeStat(((EvaluateDepartmentStat) obj[0])
                        .getCalendar());
            }
            for (int i = 1; i < obj.length; i++) {
                int count = null == obj[i] ? 0 : Integer.parseInt(obj[i].toString());
                if (null == collegeResult[i]) {
                    collegeResult[i] = String.valueOf(count);
                } else {
                    collegeResult[i] = String.valueOf(Integer.parseInt(collegeResult[i].toString())
                            + count);
                }
            }
        }
        return collegeResult;
    }
    
    public List getCollegeResultList(TeachCalendar calendar, List criteriaItems) {
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        // 组合教师评教汇总的优、良、中、差等统计字段
        String[] caseWhens = new String[criteriaItems.size()];
        int scoreLevel = 0;
        for (Iterator it = criteriaItems.iterator(); it.hasNext();) {
            EvaluationCriteriaItem criteriaItem = (EvaluationCriteriaItem) it.next();
            caseWhens[scoreLevel++] = " when evaluateTeacher.sumScore >= " + criteriaItem.getMin()
                    + " and evaluateTeacher.sumScore <= " + criteriaItem.getMax() + " then "
                    + scoreLevel;
        }
        StringBuffer groupBy = new StringBuffer();
        groupBy.append("evaluateTeacher.calendar.id");
        groupBy.append(",(case");
        for (int i = 0; i < caseWhens.length - 1; i++) {
            groupBy.append(caseWhens[i]);
        }
        groupBy.append(" else " + caseWhens.length + " end)");
        if (null != calendar) {
            query.add(new Condition("evaluateTeacher.calendar.start <= :calendarStart", calendar
                    .getStart()));
        }
        query.groupBy(groupBy.toString());
        List orders = new ArrayList();
        orders.add(new Order("evaluateTeacher.calendar.id"));
        orders.add(new Order("count(*) desc"));
        query.addOrder(orders);
        query.setSelect("evaluateTeacher.calendar.id, count(*)");
        List queryList = (List) utilDao.search(query); // 得到统计指定列的数据记录
        
        List collegeResults = new ArrayList();
        TeachCalendar beforeCalendar = null;
        Object[] newObj = null;
        int k = 1;
        for (Iterator it = queryList.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            TeachCalendar teachCalendar = (TeachCalendar) utilDao.load(TeachCalendar.class,
                    new Long(obj[0].toString()));
            // 如果不是一个教学日历的，就另起一个
            if (!ObjectUtils.equals(beforeCalendar, teachCalendar)) {
                newObj = new Object[criteriaItems.size() + 1];
                newObj[0] = getEvaluateCollegeStat(teachCalendar);
                collegeResults.add(newObj);
                beforeCalendar = teachCalendar;
                k = 1;
            }
            newObj[k++] = obj[1];
        }
        return collegeResults;
    }
    
    public List getCollegeResultList(Collection criteriaItems) {
        return getCollegeResultList(null, (List) criteriaItems);
    }
    
    public Map getCollegeResultList(TeachCalendar calendar) {
        return getDepartResultList(calendar, null);
    }
    
    public List getDepartResultList(List criteriaItems, TeachCalendar calendar) {
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        // 组合院系评教汇总的优、良、中、差等统计字段
        String[] caseWhens = new String[criteriaItems.size()];
        int scoreLevel = 0;
        for (Iterator it = criteriaItems.iterator(); it.hasNext();) {
            EvaluationCriteriaItem criteriaItem = (EvaluationCriteriaItem) it.next();
            caseWhens[scoreLevel++] = " when evaluateTeacher.sumScore >= " + criteriaItem.getMin()
                    + " and evaluateTeacher.sumScore <= " + criteriaItem.getMax() + " then "
                    + scoreLevel;
        }
        StringBuffer groupBy = new StringBuffer();
        groupBy.append("evaluateTeacher.teacher.department.id");
        groupBy.append(",(case");
        for (int i = 0; i < caseWhens.length - 1; i++) {
            groupBy.append(caseWhens[i]);
        }
        groupBy.append(" else " + caseWhens.length + " end)");
        query.groupBy(groupBy.toString());
        query.add(new Condition("evaluateTeacher.calendar = :calendar", calendar));
        List orders = new ArrayList();
        orders.add(new Order("evaluateTeacher.teacher.department.id"));
        orders.add(new Order("count(*) desc"));
        query.addOrder(orders);
        query.setSelect("evaluateTeacher.teacher.department.id, count(*)");
        List queryList = (List) utilDao.search(query);
        
        List departmentResults = new ArrayList();
        Department beforeDepartment = null;
        Object[] newObj = null;
        int k = 1;
        for (Iterator it = queryList.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            Department department = (Department) utilDao.load(Department.class, new Long(obj[0]
                    .toString()));
            if (!ObjectUtils.equals(beforeDepartment, department)) {
                newObj = new Object[criteriaItems.size() + 1];
                EvaluateDepartmentStat evaluateDepartment = getEvaluateDepartmentStat(calendar,
                        department);
                if (null == evaluateDepartment) {
                    continue;
                }
                newObj[0] = evaluateDepartment;
                departmentResults.add(newObj);
                beforeDepartment = department;
                k = 1;
            }
            newObj[k++] = obj[1];
        }
        return departmentResults;
        
    }
    
    public List getDepartResultList(List criteriaItems, Department department) {
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        // 组合院系评教汇总的优、良、中、差等统计字段
        String[] caseWhens = new String[criteriaItems.size()];
        int scoreLevel = 0;
        for (Iterator it = criteriaItems.iterator(); it.hasNext();) {
            EvaluationCriteriaItem criteriaItem = (EvaluationCriteriaItem) it.next();
            caseWhens[scoreLevel++] = " when evaluateTeacher.sumScore >= " + criteriaItem.getMin()
                    + " and evaluateTeacher.sumScore <= " + criteriaItem.getMax() + " then "
                    + scoreLevel;
        }
        query.add(new Condition("evaluateTeacher.teacher.department = :department", department));
        StringBuffer groupBy = new StringBuffer();
        groupBy.append("evaluateTeacher.calendar.id");
        groupBy.append(",(case");
        for (int i = 0; i < caseWhens.length - 1; i++) {
            groupBy.append(caseWhens[i]);
        }
        groupBy.append(" else " + caseWhens.length + " end)");
        query.groupBy(groupBy.toString());
        List orders = new ArrayList();
        orders.add(new Order("evaluateTeacher.calendar.id"));
        orders.add(new Order("count(*) desc"));
        query.addOrder(orders);
        query.setSelect("evaluateTeacher.calendar.id, count(*)");
        List queryList = (List) utilDao.search(query);
        
        List calendarResults = new ArrayList();
        TeachCalendar beforeCalendar = null;
        Object[] newObj = null;
        int k = 1;
        for (Iterator it = queryList.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            TeachCalendar calendar = (TeachCalendar) utilDao.load(TeachCalendar.class, new Long(
                    obj[0].toString()));
            if (!ObjectUtils.equals(beforeCalendar, calendar)) {
                newObj = new Object[criteriaItems.size() + 1];
                EvaluateDepartmentStat evaluateDepartment = getEvaluateDepartmentStat(calendar,
                        department);
                if (null == evaluateDepartment) {
                    continue;
                }
                newObj[0] = evaluateDepartment;
                calendarResults.add(newObj);
                beforeCalendar = calendar;
                k = 1;
            }
            newObj[k++] = obj[1];
        }
        return calendarResults;
    }
    
    public Map getDepartResultList(TeachCalendar calendar, Department department) {
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        query.add(new Condition("evaluateTeacher.calendar = :calendar", calendar));
        if (null != department) {
            query
                    .add(new Condition("evaluateTeacher.teacher.department = :department",
                            department));
        }
        query.join("evaluateTeacher.questionsStat", "stat");
        String groupBy = "evaluateTeacher.teacher.id,evaluateTeacher.course.id,stat.question.type.id";
        query.groupBy(groupBy);
        query.addOrder(OrderUtils.parser("stat.question.type.id,sum(stat.question.score)"));
        String selectStatement = "stat.question.type.id,(case when sum(stat.evgPoints) >= sum(stat.question.score) * 0.9 then 1 "
                + "when sum(stat.evgPoints) >= sum(stat.question.score) * 0.8 and sum(stat.evgPoints) < sum(stat.question.score) * 0.9 then 2 "
                + "when sum(stat.evgPoints) >= sum(stat.question.score) * 0.7 and sum(stat.evgPoints) < sum(stat.question.score) * 0.8 then 3 "
                + "when sum(stat.evgPoints) >= sum(stat.question.score) * 0.6 and sum(stat.evgPoints) < sum(stat.question.score) * 0.7 then 4 "
                + "else 5 end), sum(stat.question.score)";
        query.setSelect(selectStatement);
        Collection groupItemResults = utilDao.search(query);
        
        Map resultMap = new HashMap();
        for (Iterator it = groupItemResults.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            String questionTypeId = obj[0].toString();
            if (!resultMap.containsKey(questionTypeId)) {
                resultMap.put(questionTypeId, new long[6]);
            }
            long[] questionTypeCount = (long[]) resultMap.get(questionTypeId);
            questionTypeCount[((Integer) obj[1]).intValue() - 1]++;
            questionTypeCount[5] = new Float(obj[2].toString()).longValue();
        }
        return resultMap;
    }
    
    public void sortQuestionsStat(EvaluateTeacherStat evaluateTeacher) {
        EntityQuery query = new EntityQuery(QuestionTeacherStat.class, "questionTeacherStat");
        query.add(new Condition("questionTeacherStat.evaluateTeacherStat.id = :evaluateTeacherId",
                evaluateTeacher.getId()));
        String orderBy = "questionTeacherStat.question.type.priority desc,questionTeacherStat.question.priority desc";
        query.addOrder(OrderUtils.parser(orderBy));
        query.setSelect("questionTeacherStat");
        evaluateTeacher.setQuestionsStat((List) utilDao.search(query));
    }
    
    public double[] getDepartmentResults(Collection teacherResults) {
        if (CollectionUtils.isEmpty(teacherResults)) {
            throw new RuntimeException("The parameter width teacherResults is empty or null.");
        }
        int n = 0;
        double[] departmentResults = new double[n];
        int[] count = new int[n];
        for (Iterator it = teacherResults.iterator(); it.hasNext();) {
            EvaluateTeacherStat evaluateTeacher = (EvaluateTeacherStat) it.next();
            if (n < evaluateTeacher.getQuestionsStat().size()) {
                if (n > 0) {
                    if (n < evaluateTeacher.getQuestionsStat().size()) {
                        n = evaluateTeacher.getQuestionsStat().size();
                    }
                    double[] temp1 = new double[n];
                    int[] temp2 = new int[n];
                    for (int i = 0; i < departmentResults.length; i++) {
                        temp1[i] = departmentResults[i];
                        temp2[i] = count[i];
                    }
                    departmentResults = temp1;
                    count = temp2;
                } else {
                    if (n < evaluateTeacher.getQuestionsStat().size()) {
                        n = evaluateTeacher.getQuestionsStat().size();
                    }
                    departmentResults = new double[n];
                    count = new int[n];
                }
            }
            int k = 0;
            sortQuestionsStat(evaluateTeacher);
            for (Iterator it2 = evaluateTeacher.getQuestionsStat().iterator(); it2.hasNext();) {
                QuestionTeacherStat questionStat = (QuestionTeacherStat) it2.next();
                departmentResults[k] += questionStat.getEvgPoints().doubleValue();
                if (departmentResults[k] != 0) {
                    count[k]++;
                }
                k++;
            }
        }
        for (int i = 0; i < n; i++) {
            departmentResults[i] /= count[i];
        }
        return departmentResults;
    }
    
    public void setUtilDao(UtilDao utilDao) {
        this.utilDao = utilDao;
    }
    
    public EvaluateTeacherStat getEvaluateTeacherStat(Long evaluateTeacherId) {
        return (EvaluateTeacherStat) utilDao.get(EvaluateTeacherStat.class, evaluateTeacherId);
    }
    
    public EvaluateDepartmentStat getEvaluateDepartmentStat(Long evaluateDepartmentId) {
        return (EvaluateDepartmentStat) utilDao.get(EvaluateDepartmentStat.class,
                evaluateDepartmentId);
    }
    
    public EvaluateCollegeStat getEvaluateCollegeStat(Long evaluateCollegeId) {
        return (EvaluateCollegeStat) utilDao.get(EvaluateCollegeStat.class, evaluateCollegeId);
    }
    
    public Boolean isEmptyOfEvaluateTeacherStat(TeachCalendar calendar, Course course,
            Teacher teacher) {
        return new Boolean(null == course
                || null == getEvaluateTeacherStat(calendar, course, teacher));
    }
}
