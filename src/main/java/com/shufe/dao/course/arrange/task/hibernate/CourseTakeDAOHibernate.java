//$Id: CourseTakeDAOHibernate.java,v 1.14 2007/01/17 07:01:34 duanth Exp $
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
 * hc             2005-12-17         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.arrange.task.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.arrange.task.CourseTakeDAO;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

public class CourseTakeDAOHibernate extends BasicHibernateDAO implements CourseTakeDAO {
    
    public boolean isTake(Student std, TeachTask task) {
        Query query = getSession().getNamedQuery("getTakeCount");
        query.setParameter("stdNo", std);
        query.setParameter("task", task);
        return ((Number) query.uniqueResult()).intValue() > 0;
    }
    
    /**
     * 根据学生的学号和教学日历id 得到这个学生的在这个教学日历下面的问卷不为空的courseTakeList
     * 
     * @param studentId
     * @param teachCalendarId
     * @return
     */
    public List getCourseTakeNeedEvaluate(Long studentId, Long teachCalendarId) {
        Criteria criteria = getSession().createCriteria(CourseTake.class);
        if (null != studentId) {
            criteria.add(Restrictions.eq("student.id", studentId));
        }
        criteria.createAlias("task", "task");
        if (teachCalendarId != null && !teachCalendarId.equals(new Long(0))) {
            criteria.add(Restrictions.eq("task.calendar.id", teachCalendarId));
        }
        criteria.add(Restrictions.ne("isCourseEvaluated", Boolean.TRUE));
        criteria.add(Restrictions.isNotNull("task.questionnaire"));
        return criteria.list();
    }
    
    public List getCourseTakeIdsNeedEvaluate(Long studentId, Collection teachCalendars) {
        if (teachCalendars == null || teachCalendars.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        EntityQuery query = new EntityQuery(CourseTake.class, "courseTake");
        query.add(new Condition("courseTake.student.id=:stdId", studentId));
        query.add(new Condition("courseTake.task.calendar in (:calendars)", teachCalendars));
        query.add(new Condition("courseTake.task.questionnaire != null"));
        query.add(new Condition(
                "courseTake.isCourseEvaluated is null or courseTake.isCourseEvaluated = false"));
        query.setSelect("courseTake.id");
        return (List) utilDao.search(query);
    }
    
    public CourseTake getCourseTask(Long teachTaskId, Long stdId) {
        String sql = "from CourseTake where task = :taskId and student.id= :stdId";
        Query query = this.getSession().createQuery(sql);
        query.setLong("taskId", teachTaskId.longValue());
        query.setLong("stdId", stdId.longValue());
        return (CourseTake) query.uniqueResult();
    }
    
    public List getCourseTakes(Long stdId, TimeUnit timeUnit, TeachCalendar calendar) {
        EntityQuery entityQuery = new EntityQuery(CourseTake.class, "take");
        entityQuery.add(Condition.eq("take.student.id", stdId));
        List conditions = new ArrayList();
        if (null != timeUnit) {
            if (null != timeUnit.getWeekId() && timeUnit.getWeekId().intValue() != 0)
                conditions.add(Condition.eq("activity.time.weekId", timeUnit.getWeekId()));
            if (null != timeUnit.getYear())
                conditions.add(Condition.eq("activity.time.year", timeUnit.getYear()));
            if (null != timeUnit.getEndUnit() && timeUnit.getEndUnit().intValue() != 0)
                conditions.add(Condition.le("activity.time.startUnit", timeUnit.getEndUnit()));
            if (null != timeUnit.getStartUnit() && timeUnit.getStartUnit().intValue() != 0)
                conditions.add(Condition.ge("activity.time.endUnit", timeUnit.getStartUnit()));
            if (null != timeUnit.getValidWeeksNum() && timeUnit.getValidWeeksNum().intValue() != 0)
                conditions.add(new Condition(
                        "bitand(activity.time.validWeeksNum, :validWeeksNum)>0)", timeUnit
                                .getValidWeeksNum()));
        }
        if (conditions.isEmpty())
            entityQuery.add(new Condition("task.calendar=:calendar", calendar));
        else {
            entityQuery.join("take.task.arrangeInfo.activities", "activity");
            entityQuery.getConditions().addAll(conditions);
        }
        return (List) utilDao.search(entityQuery);
        
    }
    
    /**
     * @see com.shufe.dao.course.election.ElectRecordDAO#calcElectCredit(com.shufe.model.std.Student,
     *      com.shufe.model.system.calendar.TeachCalendar)
     */
    public Float statCreditFor(Student std, List calendars) {
        Query query = getSession().getNamedQuery("statCreditFor");
        query.setParameter("stdId", std.getId());
        query.setParameterList("calendarIds", calendars);
        Number credit = (Number) query.uniqueResult();
        return (credit == null) ? new Float(0) : new Float(credit.floatValue());
    }
    
    public List getCourseTakes(Student std) {
        Query query = getSession().getNamedQuery("getCourseTakesOfStd");
        query.setParameter("std", std);
        return query.list();
    }
    
    public List getTakeCourseIdsOfStd(Long stdId, List calendars) {
        Query query = getSession().getNamedQuery("getTakeCourseIdsOfStd");
        query.setParameter("stdId", stdId);
        query.setParameterList("calendars", calendars);
        return query.list();
    }
    
    public List getTakedTasks(Student std, Collection calendars) {
        String sql = "select take.task form  CourseTake take where take.student.id =:stdId "
                + "and task.calendar in(:calendars)";
        Query query = getSession().createQuery(sql);
        query.setParameter("stdId", std.getId());
        query.setParameterList("calendars", calendars);
        return query.list();
    }
    
    /**
     * 指定学生
     */
    public int assignStds(TeachTask task) {
        Connection con = getSession().connection();
        String strProcedure = "{?=call init_courseTake(?)}";
        int count = 0;
        try {
            CallableStatement cstmt = con.prepareCall(strProcedure);
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.setLong(2, task.getId().longValue());
            count = cstmt.executeUpdate();
            count = cstmt.getInt(1);
            con.commit();
            cstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (Exception e1) {
            }
            info("execproduct is failed" + "in delete init CourseTake and electRecords");
        }
        getSession().refresh(task);
        return count;
    }
    
    public void batchRemove(TeachTask task, Collection stdIds) {
        if (stdIds.isEmpty())
            return;
        EntityQuery query = new EntityQuery(CourseTake.class, "take");
        query.add(new Condition("take.task.id = (:taskId)", task.getId()));
        query.add(new Condition("take.student.id in (:ids)", stdIds));
        List list = (List) utilDao.search(query);
        utilDao.remove(list);
    }
    
    /**
     * FIXME poor name
     */
    public Map stdCourseValidator(String taskId, String stdId) {
        Map data = new HashMap();
        if (StringUtils.isEmpty(taskId) || StringUtils.isEmpty(stdId)) {
            data.put("message", "inputError");
            return data;
        }
        Long tId = new Long(taskId);
        Long sId = new Long(stdId);
        TeachTask task = (TeachTask) utilDao.load(TeachTask.class, tId);
        EntityQuery courseGradeQuery = new EntityQuery(CourseGrade.class, "grade");
        courseGradeQuery.add(new Condition("grade.task.id = (:taskId)", task.getId()));
        courseGradeQuery.add(new Condition("grade.std.id = (:stdId)", sId));
        List grades = (List) utilDao.search(courseGradeQuery);
        if (null == grades || grades.isEmpty()) {
            data.put("message", "stdNoCourse");
            return data;
        } else {
            data.put("message", "stdBeenCourse");
            EntityQuery gradeTypeQuery = new EntityQuery(GradeType.class, "gradeType");
            gradeTypeQuery.add(new Condition(
                    "gradeType.examType is not null and gradeType.state=true"));
            List gradeTypes = (List) utilDao.search(gradeTypeQuery);
            for (Iterator it = gradeTypes.iterator(); it.hasNext();) {
                GradeType gradeType = (GradeType) it.next();
                data.put("gradeType" + gradeType.getId(), null);
            }
            CourseGrade grade = (CourseGrade) grades.get(0);
            data.put("id", grade.getId());
            Long gradeMarkStyleId = grade.getMarkStyle().getId();
            data.put("markStyle.id", gradeMarkStyleId);
            int hasGradeCount = 0;
            for (Iterator it = grade.getExamGrades().iterator(); it.hasNext();) {
                ExamGrade examGrade = (ExamGrade) it.next();
                Float score = examGrade.getScore();
                if (null != score) {
                    hasGradeCount++;
                }
                data.put("gradeType" + examGrade.getGradeType().getId() + "" + gradeMarkStyleId,
                        score);
                data.put("examGradeId" + examGrade.getGradeType().getId() + "" + gradeMarkStyleId,
                        examGrade.getId());
            }
            if (hasGradeCount == 0) {
                data.put("message", "stdNoGrade");
            }
            return data;
        }
    }
}
