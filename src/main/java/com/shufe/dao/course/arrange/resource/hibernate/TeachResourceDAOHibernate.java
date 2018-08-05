//$Id: TeachResourceDAOHibernate.java,v 1.7 2007/01/21 07:47:54 duanth Exp $
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
 * chaostone             2005-11-13         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.arrange.resource.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.type.Type;

import com.ekingstar.commons.lang.BitStringUtil;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.hibernate.HibernateQuerySupport;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.time.CourseUnit;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.arrange.resource.TeachResourceDAO;
import com.shufe.model.course.arrange.Activity;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.system.calendar.TimeSettingService;
import com.shufe.util.DataRealmLimit;
import com.shufe.util.ListUtils;

/**
 * 教学资源数据服务实现
 * 
 * @author chaostone 2005-11-13
 */
public class TeachResourceDAOHibernate extends BasicHibernateDAO implements TeachResourceDAO {
    
    TimeSettingService timeSettingService;
    
    /*--------------   教学资源是否占用查询 ---------------------*/
    public boolean isAdminClassesOccupied(TimeUnit time, Collection adminClasses) {
        if (adminClasses.isEmpty())
            return false;
        Query query = this.getSession().getNamedQuery("isAdminClassesOccupied");
        setTimeParameter(time, query);
        query.setParameterList("adminClassIds", adminClasses);
        return (((Number) query.uniqueResult()).intValue() != 0);
    }
    
    public boolean isAdminClassOccupied(TimeUnit time, Long adminClassId) {
        return isAdminClassesOccupied(time, Collections.singletonList(adminClassId));
    }
    
    public boolean isRoomOccupied(TimeUnit time, Serializable roomId) {
        Query query = this.getSession().getNamedQuery("isRoomOccupied");
        System.out.println(query.getQueryString());
        setTimeParameter(time, query);
        query.setParameter("roomId", roomId);
        Object object = query.uniqueResult();
        return (((Number) object).intValue() != 0);
    }
    
    public boolean isStdOccupied(TimeUnit time, Long stdId) {
        return isStdsOccupied(time, Collections.singletonList(stdId));
    }
    
    public boolean isStdsOccupied(TimeUnit time, Collection stdIds) {
        Query query = this.getSession().getNamedQuery("isStdsOccupied");
        setTimeParameter(time, query);
        query.setParameterList("stdIds", stdIds);
        return (((Number) query.uniqueResult()).intValue() != 0);
    }
    
    public boolean isStdsOccupied(TimeUnit time, Collection stdIds, TeachTask except) {
        Query query = this.getSession().getNamedQuery("isStdsOccupiedExcept");
        setTimeParameter(time, query);
        query.setParameterList("stdIds", stdIds);
        query.setParameter("taskId", except.getId());
        return (((Number) query.uniqueResult()).intValue() != 0);
    }
    
    public int occupiedStdCount(TimeUnit time, Collection stdIds, Class activityClass) {
        if (stdIds.size() < 700) {
            return detectOccupyStdLessThan(time, stdIds, activityClass);
        } else {
            List rs = ListUtils.split(new ArrayList(stdIds), 700);
            int collisionStdCount = 0;
            for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
                List subStdIds = (List) iterator.next();
                collisionStdCount += detectOccupyStdLessThan(time, subStdIds, activityClass);
            }
            return collisionStdCount;
        }
    }
    
    /**
     * 检测少于一定数目学生的冲突人数
     * 
     * @param time
     * @param stdIds
     * @param activityClass
     * @return
     */
    private int detectOccupyStdLessThan(TimeUnit time, Collection stdIds, Class activityClass) {
        Query query = null;
        if (ExamActivity.class.equals(activityClass)) {
            query = this.getSession().getNamedQuery("examOccupiedStdCount");
        } else if (CourseActivity.class.equals(activityClass)) {
            query = this.getSession().getNamedQuery("courseOccupiedStdCount");
        } else {
            throw new RuntimeException("not support query in all activity class");
        }
        setTimeParameter(time, query);
        query.setParameterList("stdIds", stdIds);
        return (((Number) query.uniqueResult()).intValue());
    }
    
    public boolean isTeachersOccupied(TimeUnit time, Collection teachers) {
        Query query = getSession().getNamedQuery("isTeachersOccupied");
        setTimeParameter(time, query);
        query.setParameterList("teacherIds", EntityUtils.extractIds(teachers));
        return (((Number) query.uniqueResult()).intValue() != 0);
    }
    
    public boolean isTeacherOccupied(TimeUnit time, Long teacherId) {
        Query query = this.getSession().getNamedQuery("isTeachersOccupied");
        setTimeParameter(time, query);
        query.setParameterList("teacherIds", Collections.singletonList(teacherId));
        return (((Number) query.uniqueResult()).intValue() != 0);
    }
    
    /**
     * 检查教学活动是否冲突
     * 
     * @param time
     * @return
     */
    public boolean isTeacherCollision(Long examActivityId, Long teacherId) {
        if (examActivityId == null) {
            return true;
        }
        
        Query query = null;
        try {
            ExamActivity activity = (ExamActivity) utilDao.load(ExamActivity.class, examActivityId);
            if (activity == null || activity.getId() == null) {
                return true;
            }
            query = this.getSession().getNamedQuery("isTeacherCollision");
            query.setParameter("V_NF", activity.getTime().getYear());
            query.setParameter("V_ZJ", activity.getTime().getWeekId());
            query.setParameter("V_JSSJ", activity.getTime().getEndTime());
            query.setParameter("V_QSSJ", activity.getTime().getStartTime());
            query.setParameter("V_YXZSZ", activity.getTime().getValidWeeksNum());
            query.setParameter("V_TEACHER_ID", (teacherId == null ? activity.getTeacher().getId()
                    : teacherId));
            query.setParameter("V_JXHDID", examActivityId);
            
            if (logger.isDebugEnabled()) {
                logger.debug("the SQL statement of isTeacherCollision method is below: \n"
                        + ("\t\t" + query.getQueryString()).replaceAll("\t", "    "));
                logger.debug("V_JXHDID:\t\t" + examActivityId);
                logger.debug("V_NF:\t\t" + activity.getTime().getYear());
                logger.debug("V_ZJ:\t\t" + activity.getTime().getWeekId());
                logger.debug("V_JSXJ:\t\t" + activity.getTime().getEndUnit());
                logger.debug("V_JSSJ:\t\t" + activity.getTime().getEndTime());
                logger.debug("V_YXZSZ:\t\t" + activity.getTime().getValidWeeksNum());
                logger.debug("V_TEACHER_ID:\t"
                        + (teacherId == null ? activity.getTeacher().getId() : teacherId));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        if (query.uniqueResult() == null || ((Number) query.uniqueResult()).longValue() == 0) {
            return false;
        }
        return true;
    }
    
    /*------------空闲教学资源查询--------------------*/
    public Classroom getFreeRoomIn(Collection roomIds, TimeUnit[] times, Classroom room,
            Class activityClass) {
        Criteria criteria = getFreeRoomsCriteria(roomIds, null, times, room, activityClass);
        criteria.add(Restrictions.sqlRestriction(" rownum < 2"));
        List rooms = criteria.list();
        return (rooms.isEmpty()) ? null : (Classroom) rooms.iterator().next();
    }
    
    public Collection getFreeRoomsIn(Collection roomIds, TimeUnit[] times, Classroom room,
            Class activityClass) {
        return getFreeRoomsCriteria(roomIds, null, times, room, activityClass).list();
    }
    
    public Collection getFreeRoomsOf(Long[] departIds, TimeUnit[] times, Classroom room,
            Class activityClass) {
        return getFreeRoomsCriteria(null, departIds, times, room, activityClass).list();
    }
    
    public Pagination getFreeRoomsOf(Long[] departIds, TimeUnit[] times, Classroom room,
            Class activityClass, int pageNo, int pageSize) {
        return dynaSearch(getFreeRoomsCriteria(null, departIds, times, room, activityClass),
                pageNo, pageSize);
        
    }
    
    public Collection getFreeTeachersOf(Long[] departIds, TimeUnit[] times, Teacher teacher,
            Class activityClass, PageLimit limit) {
        Criteria criteria = getSession().createCriteria(Teacher.class, "teacher");
        if (null != teacher) {
            if (ValidEntityPredicate.INSTANCE.evaluate(teacher.getDepartment())) {
                criteria.add(Restrictions.eq("department.id", teacher.getDepartment().getId()));
            }
            if (StringUtils.isNotBlank(teacher.getName())) {
                criteria.add(Restrictions.like("name", teacher.getName(), MatchMode.ANYWHERE));
            }
        }
        if (null != departIds) {
            criteria.createAlias("department", "depart");
            criteria.add(Restrictions.in("depart.id", departIds));
        }
        addTimesRestrictionsForTeacher(criteria, times, activityClass);
        return dynaSearch(criteria, limit);
    }
    
    public Collection getFreeTeacherIdsIn(Collection teacherIds, TimeUnit[] times, Teacher teacher,
            Class activityClass) {
        Criteria criteria = getSession().createCriteria(Teacher.class, "teacher");
        if (null != teacher) {
            if (ValidEntityPredicate.INSTANCE.evaluate(teacher.getDepartment())) {
                criteria.add(Restrictions.eq("department.id", teacher.getDepartment().getId()));
            }
            if (StringUtils.isNotBlank(teacher.getName())) {
                criteria.add(Restrictions.like("name", teacher.getName()));
            }
        }
        if (null != teacherIds && !teacherIds.isEmpty())
            criteria.add(Restrictions.in("id", teacherIds));
        addTimesRestrictionsForTeacher(criteria, times, activityClass);
        criteria.setProjection(Projections.groupProperty("id"));
        return criteria.list();
    }
    
    public Collection getFreeClassIdsIn(Collection adminClassIds, TimeUnit[] times) {
        if (adminClassIds.isEmpty())
            return Collections.EMPTY_LIST;
        Set ocuupiedIds = new HashSet();
        Query query = getSession().getNamedQuery("occupiedAdminClasses");
        for (int i = 0; i < times.length; i++) {
            setTimeParameter(times[i], query);
            query.setParameterList("adminClassIds", adminClassIds);
            ocuupiedIds.addAll(query.list());
        }
        return CollectionUtils.subtract(adminClassIds, ocuupiedIds);
    }
    
    /*------------获得教学资源的教学活动--------------------*/
    public List getAdminClassActivities(Serializable adminClassId, TimeUnit time,
            Class activityClass) {
        Criteria criteria = getSession().createCriteria(activityClass);
        criteria.createCriteria("task", "task").createCriteria("task.teachClass.adminClasses",
                "adminClass");
        criteria.add(Restrictions.eq("adminClass.id", adminClassId));
        setOccupyTimeParams(time, criteria);
        return criteria.list();
    }
    
    public List getRoomActivities(Serializable roomId, TimeUnit time, Class activityClass,
            TeachCalendar calendar) {
        Criteria criteria = getSession().createCriteria(activityClass);
        criteria.add(Restrictions.eq("room.id", roomId));
        if (null != calendar && null != calendar.getId()) {
            criteria.add(Restrictions.eq("calendar.id", calendar.getId()));
        }
        setOccupyTimeParams(time, criteria);
        return criteria.list();
    }
    
    public List getStdActivities(Long stdId, TimeUnit time, Class activityClass,
            TeachCalendar calendar) {
        Criteria criteria = getSession().createCriteria(activityClass);
        criteria.createCriteria("task", "task").createCriteria("task.teachClass.courseTakes",
                "courseTake");
        criteria.add(Restrictions.eq("courseTake.student.id", stdId));
        if (null != calendar && null != calendar.getId()) {
            criteria.add(Restrictions.eq("calendar.id", calendar.getId()));
        }
        setOccupyTimeParams(time, criteria);
        return criteria.list();
    }
    
    public List getTeacherActivities(Serializable teacherId, TimeUnit time, Class activityClass,
            TeachCalendar calendar) {
        Criteria criteria = getSession().createCriteria(activityClass);
        criteria.add(Restrictions.eq("teacher.id", teacherId));
        if (null != calendar && null != calendar.getId()) {
            criteria.add(Restrictions.eq("calendar.id", calendar.getId()));
        }
        setOccupyTimeParams(time, criteria);
        return criteria.list();
    }
    
    /*------------获得教学资源的占用信息--------------------*/

    public List getAdminClassOccupyInfos(Serializable adminClassId, Long validWeeksNum, Integer year) {
        Query query = getSession().getNamedQuery("getAdminClassOccupyInfos");
        return getResourceOccupyInfos(adminClassId, validWeeksNum, year, query);
    }
    
    public List getRoomOccupyInfos(Serializable roomId, Long validWeeksNum, Integer year) {
        Query query = getSession().getNamedQuery("getRoomOccupyInfos");
        return getResourceOccupyInfos(roomId, validWeeksNum, year, query);
    }
    
    public List getTeacherOccupyInfos(Serializable teacherId, Long validWeeksNum, Integer year) {
        Query query = getSession().getNamedQuery("getTeacherOccupyInfos");
        return getResourceOccupyInfos(teacherId, validWeeksNum, year, query);
    }
    
    public List getResourceOccupyInfos(Serializable resourceId, Long validWeeksNum, Integer year,
            Query query) {
        query.setParameter("id", resourceId);
        query.setParameter("validWeeksNum", validWeeksNum);
        query.setParameter("year", year);
        return query.list();
    }
    
    /*------------辅助函数--------------------*/
    public List getClassrooms(Long[] roomIds) {
        return getSession().getNamedQuery("getClassroomsByIdsSorted").setParameterList("roomIds",
                roomIds).list();
    }
    
    public List getTeachers(Collection teacherIds) {
        return getSession().getNamedQuery("getTeachersById").setParameterList("teacherIds",
                teacherIds).list();
    }
    
    public List getFreeRoomsIn(Collection roomIds, TimeUnit time) {
        Criteria criteria = getSession().createCriteria(Classroom.class);
        if (null != roomIds && !roomIds.isEmpty())
            criteria.add(Restrictions.in("id", roomIds));
        addTimesRestrictionsForRoom(criteria, new TimeUnit[] { time }, Activity.class);
        return criteria.list();
    }
    
    private Criteria getFreeRoomsCriteria(Collection roomIds, Long[] departIds, TimeUnit[] times,
            Classroom room, Class activityClass) {
        Criteria criteria = getSession().createCriteria(Classroom.class);
        List criterions = com.shufe.dao.util.CriterionUtils.getEntityCriterions(room, new String[] {
                "capacityOfCourse", "capacityOfExam" });
        criteria.add(Restrictions.eq("state", Boolean.TRUE));
        if (!criterions.isEmpty()) {
            for (Iterator iter = criterions.iterator(); iter.hasNext();) {
                criteria.add((Criterion) iter.next());
            }
        }
        if (null != room) {
            if (null != room.getCapacityOfCourse()) {
                criteria.add(Restrictions.ge("capacityOfCourse", room.getCapacityOfCourse()));
            }
            if (null != room.getCapacityOfExam()) {
                criteria.add(Restrictions.ge("capacityOfExam", room.getCapacityOfExam()));
            }
        }
        if (null != departIds) {
            String departIdS = "," + SeqStringUtil.transformToSeq(departIds, ",") + ",";
            // 为了防止查出权限内的重复数据.特此采用了原生的sql
            criteria
                    .add(Restrictions
                            .sqlRestriction(
                                    " {alias}.id in (select jsid from jcxx_bm_js_t where instr(?,','||bmid||',')>0)",
                                    departIdS, Hibernate.STRING));
        }
        if (null != roomIds && !roomIds.isEmpty()) {
            criteria.add(Restrictions.in("id", roomIds));
        }
        
        addTimesRestrictionsForRoom(criteria, times, activityClass);
        
        if (StringUtils.contains(activityClass.getName(), "ExamActivity")) {
            criteria.addOrder(Order.asc("building"));
        } else {
            criteria.addOrder(Order.asc("capacityOfCourse"));
        }
        criteria.addOrder(Order.asc("name"));
        
        return criteria;
    }
    
    /**
     * 添加时间约束
     * 
     * @param time
     * @param room
     * @param departIds
     * @return
     */
    private void addTimesRestrictionsFor(Criteria criteria, TimeUnit[] times, Class activityClass,
            String property, String column) {
        if (null == times || times.length == 0) {
            return;
        }
        final String ocuupy = "(bitand({alias}.YXZSZ,?)>0 and  {alias}.NF = ? and {alias}.ZJ = ? and {alias}.QSSJ <= ? and ? <= {alias}.JSSJ)";
        DetachedCriteria dc = DetachedCriteria.forClass(activityClass);
        dc.setProjection(Projections.property(property));
        
        StringBuffer occupyStr = new StringBuffer("(" + column + " is not null) and (");
        Object[] values = new Object[5 * times.length];
        Type[] types = new Type[5 * times.length];
        for (int i = 0; i < times.length; i++) {
            TimeUnit time = (TimeUnit) times[i];
            if (i > 0) {
                occupyStr.append(" or ");
            }
            values[i * 5] = time.getValidWeeksNum();
            values[i * 5 + 1] = time.getYear();
            values[i * 5 + 2] = time.getWeekId();
            // 判断时间冲突, 若时间为空, 则取小节默认起始时间.
            if (null == time.getEndTime()) {
                CourseUnit endCourseUnit = (CourseUnit) timeSettingService.getDefaultSetting()
                        .getCourseUnit(time.getEndUnit().intValue());
                values[i * 5 + 3] = endCourseUnit.getFinishTime();
            } else {
                values[i * 5 + 3] = time.getEndTime();
            }
            if (null == time.getStartTime()) {
                CourseUnit startCourseUnit = (CourseUnit) timeSettingService.getDefaultSetting()
                        .getCourseUnit(time.getStartUnit().intValue());
                values[i * 5 + 4] = startCourseUnit.getStartTime();
            } else {
                values[i * 5 + 4] = time.getStartTime();
            }
            types[i * 5] = Hibernate.LONG;
            types[i * 5 + 1] = Hibernate.INTEGER;
            types[i * 5 + 2] = Hibernate.INTEGER;
            types[i * 5 + 3] = Hibernate.INTEGER;
            types[i * 5 + 4] = Hibernate.INTEGER;
            occupyStr.append(ocuupy);
            if (logger.isDebugEnabled()) {
                logger.debug("year:" + time.getYear());
                logger.debug("validWeek:" + time.getValidWeeks());
                logger.debug("week:" + time.getWeekId());
                logger.debug("startUnit:" + time.getStartUnit());
                logger.debug("endUnit:" + time.getEndUnit());
            }
        }
        
        occupyStr.append(")");
        dc.add(Restrictions.sqlRestriction(occupyStr.toString(), values, types));
        if (StringUtils.equals(property, "room")) {
            // 当需要排课检查的时候，按真实空闲教室查询；不然忽略是否空闲教室
            criteria.add(Restrictions.or(Subqueries.propertyNotIn("id", dc), Restrictions.or(
                    Restrictions.eq("isCheckActivity", null), Restrictions.eq("isCheckActivity",
                            Boolean.FALSE))));
        } else {
            criteria.add(Subqueries.propertyNotIn("id", dc));
        }
    }
    
    private void addTimesRestrictionsForRoom(Criteria criteria, TimeUnit[] times,
            Class activityClass) {
        addTimesRestrictionsFor(criteria, times, activityClass, "room", "jsid");
    }
    
    private void addTimesRestrictionsForTeacher(Criteria criteria, TimeUnit[] times,
            Class activityClass) {
        addTimesRestrictionsFor(criteria, times, activityClass, "teacher", "jzgid");
    }
    
    /**
     * @param time
     * @param query
     */
    private void setTimeParameter(TimeUnit time, Query query) {
        query.setParameter("year", time.getYear());
        query.setParameter("startUnit", time.getStartUnit());
        query.setParameter("endUnit", time.getEndUnit());
        query.setParameter("validWeeksNum", BitStringUtil.BinValueOf(time.getValidWeeks()));
        query.setParameter("weekId", time.getWeekId());
        if (logger.isDebugEnabled()) {
            logger.debug("year:" + time.getYear());
            logger.debug("validWeek:" + time.getValidWeeks());
            logger.debug("week:" + time.getWeekId());
            logger.debug("startUnit:" + time.getStartUnit());
            logger.debug("endUnit:" + time.getEndUnit());
        }
    }
    
    /**
     * 时间的年份和星期以及节次
     * 
     * @param time
     * @param criteria
     */
    private void setOccupyTimeParams(TimeUnit time, Criteria criteria) {
        if (null != time.getWeekId() && time.getWeekId().intValue() != 0) {
            criteria.add(Restrictions.eq("time.weekId", time.getWeekId()));
        }
        if (null != time.getEndUnit() && time.getEndUnit().intValue() != 0) {
            criteria.add(Restrictions.le("time.startUnit", time.getEndUnit()));
        }
        if (null != time.getStartUnit() && time.getStartUnit().intValue() != 0) {
            criteria.add(Restrictions.ge("time.endUnit", time.getStartUnit()));
        }
        if (null != time.getYear()) {
            criteria.add(Restrictions.eq("time.year", time.getYear()));
        }
        Filter filter = getSession().enableFilter("validWeeksFilter");
        filter.setParameter("validWeeksNum", time.getValidWeeksNum());
    }
    
    public Collection getExamRoomUtilizations(TeachCalendar calendar, ExamType examType,
            DataRealmLimit limit, Float ratio) {
        Query query = getSession().getNamedQuery("getExamRoomUtilizations");
        Map params = new HashMap();
        params.put("calendarId", calendar.getId());
        params.put("examTypeId", examType.getId());
        params.put("ratio", ratio);
        
        params.put("stdTypeIds", SeqStringUtil.transformToLong(limit.getDataRealm()
                .getStudentTypeIdSeq()));
        params.put("departIds", SeqStringUtil.transformToLong(limit.getDataRealm()
                .getDepartmentIdSeq()));
        if (null == limit.getPageLimit()) {
            HibernateQuerySupport.setParameter(query, params);
            return query.list();
        } else {
            return utilDao.paginateQuery(query, params, limit.getPageLimit());
        }
    }
    
    /**
     * 按星期统计教室借用率
     * 
     * @see com.shufe.dao.course.arrange.resource.TeachResourceDAO#weekAccount(Integer)
     */
    public List weekAccount(Integer year) {
        return account(year, "weekAccount");
    }
    
    /**
     * 按地点统计教室借用率
     * 
     * @see com.shufe.dao.course.arrange.resource.TeachResourceDAO#placeAccount(java.lang.Integer)
     */
    public List placeAccount(Integer year) {
        return account(year, "placeAccount");
    }
    
    /**
     * 按活动类型统计教室借用率
     * 
     * @see com.shufe.dao.course.arrange.resource.TeachResourceDAO#activityAccount(java.lang.Integer)
     */
    public List activityAccount(Integer year) {
        return account(year, "activityAccount");
    }
    
    /**
     * 私有公共方法：统计教室借用率
     * 
     * @param year
     * @param queryName
     * @return
     */
    private List account(Integer year, String queryName) {
        if (year == null) {
            return null;
        }
        Query query = this.getSession().getNamedQuery(queryName);
        query.setParameter("V_YEAR", year);
        return query.list();
    }
    
    public void setTimeSettingService(TimeSettingService timeSettingService) {
        this.timeSettingService = timeSettingService;
    }
    
}
