//$Id: TeachWorkloadStatDAOHibernate.java,v 1.20 2007/01/21 10:47:21 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-28         Created
 *  
 ********************************************************************************/

package com.shufe.dao.workload.course.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.workload.course.TeachWorkloadStatDAO;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.workload.course.TeachWorkload;

public class TeachWorkloadStatDAOHibernate extends BasicHibernateDAO implements
        TeachWorkloadStatDAO {

    public static Long TEACHERTYPEID = new Long(2); // 教职工类别代码，2

    public static Long TEACHERTYPEINSCHOOL = new Long(24); // 校内职工

    // 教职工类别表里面校本部职工的序号.

    public static Long[] TEACHERTITLEIDS = new Long[] { new Long(2), new Long(258) }; // 教师职称代码数组，2

    // 代表副教授，258
    // 代表教授

    /***************************************************************************
     * 求教师学历学历里面的结构组成
     * 
     * 
     * *******************************************************
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.workload.TeachWorkloadStatDAO12#getEduDegreeAndTitle(java.lang.String,
     *      java.util.List, java.lang.String, java.lang.Long, java.lang.Integer)
     */
    public List getEduDegreeAndTitle(String deaprtmentIds, String propertyName, Long calendarId,
            Integer age, String stdTypeIdSeq) {
        if (StringUtils.isBlank(deaprtmentIds) || StringUtils.isBlank(stdTypeIdSeq)
                || null == calendarId || new Long(0).equals(calendarId)) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = getSession().createCriteria(TeachWorkload.class);
        criteria.add(Restrictions.eq("teachCalendar.id", calendarId));
        criteria.add(Restrictions.in("teacherInfo.teachDepart.id", SeqStringUtil
                .transformToLong(deaprtmentIds)));
        criteria
                .add(Restrictions.in("studentType.id", SeqStringUtil.transformToLong(stdTypeIdSeq)));
        if (null != age && age.intValue() > 0) {
            criteria.add(Restrictions.le("teacherInfo.teacherAge", age));
        }
        criteria.setProjection(Projections.projectionList().add(
                Projections.countDistinct("teacherInfo.teacher.id")).add(
                Projections.groupProperty(propertyName)));
        return criteria.list();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.workload.TeachWorkloadStatDAO12#getEduDegreeAndTitleInTeacher(java.util.List,
     *      java.lang.String, java.lang.String, java.lang.Integer)
     */
    public List getEduDegreeAndTitleInTeacher(String propertyName, String departmentIds, Integer age) {
        Criteria criteria = getSession().createCriteria(Teacher.class);
        if (null != age && age.intValue() > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - age.intValue());
            criteria.add(Restrictions.ge("birthday", calendar.getTime()));
        }
        if (StringUtils.isBlank(departmentIds) || StringUtils.isEmpty(propertyName)
                || propertyName.indexOf(",") > -1) {
            return Collections.EMPTY_LIST;
        }
        if (propertyName.indexOf("degreeInfo") > -1) {
            criteria.createCriteria("degreeInfo", "degreeInfo");
        }
        criteria
                .add(Restrictions.in("department.id", SeqStringUtil.transformToLong(departmentIds)));
        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()).add(
                Projections.groupProperty(propertyName)));
        return criteria.list();
    }

    /***************************************************************************
     * 学历学位的结束
     */
    /***************************************************************************
     * 教师机关兼职分布
     */
    /*
     * (non-Javadoc)
     * 
     */
    public List getTeacherNumberByConditions(String departIdSeq, String stdTypeIdSeq,
            Long teacherTypeId, TeachCalendar teachCalendar) {
        if (StringUtils.isBlank(departIdSeq) || StringUtils.isBlank(stdTypeIdSeq)
                || null == teacherTypeId || null == teachCalendar) {
            return Collections.EMPTY_LIST;
        }
        List calendars = new ArrayList();
        calendars.add(teachCalendar);
        Criteria criteria = getExampleWorkloadOfCalendars(departIdSeq, stdTypeIdSeq, calendars);
        criteria.add(Restrictions.eq("teacherInfo.teacherType.id", teacherTypeId));
        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()).add(
                Projections.groupProperty("college")).add(
                Projections.groupProperty("teacherInfo.teacherTitle")));
        return criteria.list();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.workload.TeachWorkloadStatDAO12#getTeacherNumberOfDepartTeacherTypeTimeOfWorkload(java.util.List,
     *      java.lang.Long, java.util.List, java.util.List, java.lang.Long)
     */
    public List getTotalWorkloadByConditions(String departIdSeq, String stdTypeIdSeq,
            Long teacherTypeId, TeachCalendar teachCalendar) {
        if (StringUtils.isBlank(departIdSeq) || StringUtils.isBlank(stdTypeIdSeq)
                || null == teachCalendar || null == teacherTypeId) {
            return Collections.EMPTY_LIST;
        }
        List calendars = new ArrayList();
        calendars.add(teachCalendar);
        Criteria criteria = getExampleWorkloadOfCalendars(departIdSeq, stdTypeIdSeq, calendars);
        criteria.add(Restrictions.eq("teacherInfo.teacherType.id", teacherTypeId));
        criteria.setProjection(Projections.projectionList().add(Projections.sum("totleWorkload"))
                .add(Projections.groupProperty("college")).add(
                        Projections.groupProperty("teacherInfo.teacherTitle")));
        return criteria.list();
    }

    /***************************************************************************
     * 教师机关兼职分布end
     */

    /***************************************************************************
     * 教职工工作量合计
     */
    /**
     * @see com.shufe.dao.workload.TeachWorkloadStatDAO12#getHunmanNumberAndTotleWorkloadByCalendarAndStudentType(com.shufe.model.system.calendar.TeachCalendar,
     *      java.util.Collection)
     */
    public List getNumberAndWorkloadOfTitle(TeachCalendar teachCalendar, String stdTypeIdSeq) {
        Criteria criteria = transMethod(teachCalendar, stdTypeIdSeq, Boolean.TRUE);
        criteria.setProjection(Projections.projectionList().add(
                Projections.countDistinct("teacherInfo.teacher").as("teacherNumber")).add(
                Projections.sum("totleWorkload").as("totleWorkload")).add(
                Projections.groupProperty("teacherInfo.teacherTitle").as("teacherTitle")));
        criteria.addOrder(Order.asc("teacherInfo.teacherTitle.id"));
        return criteria.list();
    }

    /***************************************************************************
     * 教职工工作量合计end
     */

    /***************************************************************************
     * 教职工 各个职称的人数的总量。
     */

    public List getWorkloadOfDepartment(TeachCalendar teachCalendar, String studentTypeIds,
            String departmentIds) {
        if (null == teachCalendar || StringUtils.isBlank(studentTypeIds)
                || StringUtils.isBlank(departmentIds)) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = transMethod(teachCalendar, studentTypeIds, Boolean.TRUE);
        criteria.add(Restrictions.in("college.id", SeqStringUtil.transformToLong(departmentIds)));
        criteria.setProjection(Projections.projectionList().add(
                Projections.countDistinct("teacherInfo.teacher")).add(
                Projections.sum("totleWorkload")).add(
                Projections.groupProperty("teacherInfo.teacherType")).add(
                Projections.groupProperty("teacherInfo.teachDepart")));
        criteria.addOrder(Order.asc("teacherInfo.teachDepart.id"));
        return criteria.list();
    }

    /**
     * @see com.shufe.dao.workload.TeachWorkloadStatDAO12#getRegisterPeopleOfDepartment(java.lang.String)
     */
    public List getRegisterPeopleOfDepartment(String departmentIds) {
        if (StringUtils.isBlank(departmentIds)) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = getSession().createCriteria(Teacher.class);
        criteria
                .add(Restrictions.in("department.id", SeqStringUtil.transformToLong(departmentIds)));
        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()).add(
                Projections.groupProperty("teacherType"))
                .add(Projections.groupProperty("department")));
        return criteria.list();
    }

    /***************************************************************************
     * end
     */

    /***************************************************************************
     * 部门课程类别分布
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.workload.TeachWorkloadStatDAO12#getCourseNumberOfDepartment(com.shufe.model.system.calendar.TeachCalendar,
     *      java.lang.String, java.util.List, java.util.List)
     */
    public List getCourseNumberOfDepartment(TeachCalendar teachCalendar, String studentTypeIds,
            String collegeIdSeq) {
        if (null == teachCalendar || StringUtils.isBlank(studentTypeIds)
                || StringUtils.isBlank(collegeIdSeq)) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = transMethod(teachCalendar, studentTypeIds, Boolean.TRUE);
        criteria.add(Restrictions.in("college.id", SeqStringUtil.transformToLong(collegeIdSeq)));
        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()).add(
                Projections.groupProperty("college")).add(Projections.groupProperty("courseType")));
        criteria.addOrder(Order.asc("college.id"));
        return criteria.list();
    }

    /***************************************************************************
     * end
     */

    /***************************************************************************
     * 学生类别教师职称合计
     */
    /**
     * @see com.shufe.dao.workload.TeachWorkloadStatDAO12#getTitleAndStdTypesNo(com.shufe.model.system.calendar.TeachCalendar,
     *      java.lang.String, java.lang.String)
     */
    public List getTitleAndStdTypes(TeachCalendar teachCalendar, String deprtIdSeq,
            String stdTypeIdSeq) {
        if (StringUtils.isBlank(deprtIdSeq) || StringUtils.isBlank(stdTypeIdSeq)
                || null == teachCalendar) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = transMethod(teachCalendar, stdTypeIdSeq, Boolean.TRUE);
        criteria.add(Restrictions.in("college.id", SeqStringUtil.transformToLong(deprtIdSeq)));
        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()).add(
                Projections.groupProperty("studentType")).add(
                Projections.groupProperty("teacherInfo.teacherTitle")));
        return criteria.list();
    }

    /***************************************************************************
     * 学生类别教师职称合计 end
     */

    /***************************************************************************
     * 教职工类别 工作量部门分布
     */

    /*
     * (non-Javadoc)
     * 
     */
    public List getWorkloadOfTeacherTypeAndDepart(TeachCalendar teachCalendar, String studentTypeIds,
            String departIdSeq, Boolean isCaculate) {
        if (StringUtils.isBlank(studentTypeIds) || StringUtils.isBlank(departIdSeq)
                || null == teachCalendar) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = transMethod(teachCalendar, studentTypeIds, isCaculate);
        criteria.add(Restrictions.in("college.id", SeqStringUtil.transformToLong(departIdSeq)));
        criteria.setProjection(Projections.projectionList().add(Projections.sum("totleWorkload"))
                .add(Projections.groupProperty("college")).add(
                        Projections.groupProperty("teacherInfo.teacherType")));
        return criteria.list();
    }

    /***************************************************************************
     * end
     */

    /***************************************************************************
     * 部门平均工作量
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.workload.TeachWorkloadStatDAO12#getAllTeacherAvgWorkload(java.util.List,
     *      java.util.List, java.lang.Long)
     */
    public List getAvgCourseNoOfWokload(String departIdSeq, String stdTypeIdSeq,
            TeachCalendar teachCalendar) {
        if (StringUtils.isBlank(stdTypeIdSeq) || StringUtils.isBlank(departIdSeq)
                || null == teachCalendar) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = transMethod(teachCalendar, stdTypeIdSeq, Boolean.TRUE);
        criteria.add(Restrictions.in("college.id", SeqStringUtil.transformToLong(departIdSeq)));
        criteria.setProjection(Projections.projectionList().add(
                Projections.countDistinct("teacherInfo.teacher")).add(
                Projections.sum("classNumberOfWeek")).add(Projections.groupProperty("college"))
                .add(Projections.groupProperty("teacherInfo.teacherType")));
        return criteria.list();
    }

    /***************************************************************************
     * end
     */
    /**
     * @see com.shufe.dao.workload.TeachWorkloadStatDAO12#getWorkloadByDepartmentAndStudent(java.util.List,
     *      java.lang.String, java.lang.Long)
     */
    public List getWorkloadByDataRealm(String departmentIdSeq, String studentTypeIdSeq,
            TeachCalendar teachCalendar) {
        if (StringUtils.isBlank(studentTypeIdSeq) || StringUtils.isBlank(departmentIdSeq)
                || null == teachCalendar) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = transMethod(teachCalendar, studentTypeIdSeq, Boolean.TRUE);
        criteria.add(Restrictions.in("college.id", SeqStringUtil.transformToLong(departmentIdSeq)));
        criteria.setProjection(Projections.projectionList().add(Projections.sum("totleWorkload"))
                .add(Projections.groupProperty("college")).add(
                        Projections.groupProperty("studentType")));
        return criteria.list();
    }

    /**
     * @see com.shufe.dao.workload.course.TeachWorkloadStatDAO#getWorkloadByDataRealm(java.lang.String,
     *      java.lang.String, java.util.Collection)
     */
    public List getWorkloadByDataRealm(String departmentIdSeq, String studentTypeIdSeq,
            Collection teachCalendars) {
        if (StringUtils.isBlank(studentTypeIdSeq) || StringUtils.isBlank(departmentIdSeq)
                || null == teachCalendars || teachCalendars.size() < 1) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = getExampleWorkloadOfCalendars(departmentIdSeq, studentTypeIdSeq,
                teachCalendars);
        criteria.setProjection(Projections.projectionList().add(Projections.sum("totleWorkload"))
                .add(Projections.groupProperty("college")).add(
                        Projections.groupProperty("studentType")));
        return criteria.list();
    }

    /**
     * @param departmentIdSeq
     * @param studentTypeIdSeq
     * @param teachCalendars
     * @return
     */
    private Criteria getExampleWorkloadOfCalendars(String departmentIdSeq, String studentTypeIdSeq,
            Collection teachCalendars) {
        Criteria criteria = getSession().createCriteria(TeachWorkload.class);
        criteria.add(Restrictions.in("college.id", SeqStringUtil.transformToLong(departmentIdSeq)));
        criteria.add(Restrictions.in("studentType.id", SeqStringUtil
                .transformToLong(studentTypeIdSeq)));
        criteria.add(Restrictions.in("teachCalendar", teachCalendars));
        criteria.add(Restrictions.eq("calcWorkload", Boolean.TRUE));
        return criteria;
    }

    /**
     * @see com.shufe.dao.workload.course.TeachWorkloadStatDAO#getWorkloadOfTeacherAndCalendar(java.lang.String,
     *      java.lang.String, java.util.Collection)
     */
    public List getWorkloadOfTeacherAndCalendar(String departmentIdSeq, String studentTypeIdSeq,
            Collection teachCalendars) {
        Criteria criteria = getExampleWorkloadOfCalendars(departmentIdSeq, studentTypeIdSeq,
                teachCalendars);
        criteria.createAlias("teacherInfo.teacher", "teacher");
        criteria.setProjection(Projections.projectionList().add(Projections.sum("totleWorkload"))
                .add(Projections.groupProperty("teacherInfo.teacher")).add(
                        Projections.groupProperty("teachCalendar")));
        return criteria.list();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.workload.TeachWorkloadStatDAO12#getTotalAndRegisterWorkload(java.util.List,
     *      java.lang.Long)
     */
    public List getTotalAndRegisterWorkload(List collegeList, Long teachCalendarId) {
        Criteria criteria = getSession().createCriteria(TeachWorkload.class);
        if (teachCalendarId != null && !new Long(0).equals(teachCalendarId)) {
            criteria.add(Restrictions.eq("teachCalendar.id", teachCalendarId));
        }
        if (collegeList != null) {
            criteria.add(Restrictions.in("college", collegeList));
        }
        String sqlString = "sum(zgzl) as yxzgzl, SUM (case when jzglb_T_id =" + TEACHERTYPEID
                + " then 1 else 1 end) as yxzrs, SUM (case when jzglb_T_id =" + TEACHERTYPEID
                + " then zgzl else 0 end) as bbmzczgzl, SUM (case when jzglb_T_id ="
                + TEACHERTYPEID + " then 1 else 0 end) as zczrs";
        String[] alases = new String[] { "yxzgzl", "yxzrs", "bbmzczgzl", "zczrs" };
        Type[] types = new Type[] { Hibernate.FLOAT, Hibernate.INTEGER, Hibernate.FLOAT,
                Hibernate.INTEGER };
        criteria.add(Restrictions.eq("calcWorkload", Boolean.TRUE));
        criteria.setProjection(Projections.projectionList().add(
                Projections.sqlProjection(sqlString, alases, types)).add(
                Projections.groupProperty("college")));
        criteria.addOrder(Order.asc("college.id"));
        return criteria.list();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.workload.TeachWorkloadStatDAO12#getTitleOfBasicWorkload(com.shufe.model.system.calendar.TeachCalendar,
     *      java.lang.String, java.lang.Long)
     */

    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.workload.TeachWorkloadStatDAO12#getPeopleOfDepartment(com.shufe.model.system.calendar.TeachCalendar,
     *      java.lang.String, java.lang.String)
     */
    public List getPeopleOfDepartment(TeachCalendar teachCalendar, String studentTypeIds,
            String departmentIds) {
        Criteria criteria = transMethod(teachCalendar, studentTypeIds, Boolean.TRUE);
        Long[] departmentId = SeqStringUtil.transformToLong(departmentIds);
        criteria.add(Restrictions.in("teacherInfo.teachDepart.id", departmentId));
        String strSql = "count(distinct(jzg_T_id)) as syskjj, count(distinct(case when jzglb_T_id = 2 then jzg_T_id end)) as syzcjj";
        String[] strAs = new String[] { "syskjj", "syzcjj" };
        Type[] types = new Type[] { Hibernate.INTEGER, Hibernate.INTEGER };
        criteria.add(Restrictions.eq("calcWorkload", Boolean.TRUE));
        criteria.setProjection(Projections.projectionList().add(
                Projections.property("teacherInfo.teachDepart")).add(
                Projections.sqlGroupProjection(strSql, "jsssyx", strAs, types)));
        criteria.addOrder(Order.asc("teacherInfo.teachDepart.id"));
        return criteria.list();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.workload.TeachWorkloadStatDAO12#getWorkloadOfDepartment(com.shufe.model.system.calendar.TeachCalendar,
     *      java.lang.String, java.lang.String)
     */

    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.workload.TeachWorkloadStatDAO12#transMethod(com.shufe.model.system.calendar.TeachCalendar,
     *      java.lang.String)
     */
    public Criteria transMethod(TeachCalendar teachCalendar, String studentTypeIds,
            Boolean isCaculate) {
        Criteria criteria = getSession().createCriteria(TeachWorkload.class);
        criteria.createAlias("teachCalendar", "teachCalendar");
        criteria.add(Restrictions.eq("teachCalendar.year", teachCalendar.getYear()));
        criteria.add(Restrictions.eq("teachCalendar.term", teachCalendar.getTerm()));
        if (null != isCaculate) {
            criteria.add(Restrictions.eq("calcWorkload", isCaculate));
        }
        if (StringUtils.isNotBlank(studentTypeIds)) {
            criteria.add(Restrictions.in("studentType.id", SeqStringUtil
                    .transformToLong(studentTypeIds)));
        } else {
            criteria.add(Restrictions.eq("studentType.id", new Long(0)));
        }
        return criteria;
    }

    /**
     * @see com.shufe.dao.workload.course.TeachWorkloadStatDAO#getPropertyOfTeachWorkload(java.lang.String,
     *      java.lang.Long)
     */
    public List getPropertyOfTeachWorkload(String propertyOfTeachWorkload, Long teachCalendarId) {
        if (null == teachCalendarId || new Long(0).equals(teachCalendarId)) {
            return Collections.EMPTY_LIST;
        }
        if (StringUtils.isBlank(propertyOfTeachWorkload)) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = getSession().createCriteria(TeachWorkload.class);
        criteria.add(Restrictions.eq("teachCalendar.id", teachCalendarId));
        criteria.setProjection(Projections.groupProperty(propertyOfTeachWorkload));
        return criteria.list();
    }
}
