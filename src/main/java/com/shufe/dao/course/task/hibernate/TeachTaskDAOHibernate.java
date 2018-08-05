//$Id: TeachTaskDAOHibernate.java,v 1.30 2006/12/26 09:48:33 duanth Exp $
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
 * chaostone             2005-10-22         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.task.hibernate;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.ConditionUtils;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.query.hibernate.HibernateQuerySupport;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.task.TaskSeqNoGenerator;
import com.shufe.dao.course.task.TeachTaskDAO;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.course.arrange.task.TaskInDepartment;
import com.shufe.model.course.election.ElectState;
import com.shufe.model.course.election.SimpleStdInfo;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 教学任务实体存取实现类
 * 
 * @author chaostone 2005-10-22
 */
public class TeachTaskDAOHibernate extends BasicHibernateDAO implements TeachTaskDAO {
    
    private TaskSeqNoGenerator generator;
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#getTeachTask(java.lang.Long)
     */
    public TeachTask getTeachTask(Long taskId) {
        TeachTask task = (TeachTask) getHibernateTemplate().get(TeachTask.class, taskId);
        if (null == task)
            task = (TeachTask) load(TeachTask.class, taskId);
        return task;
    }
    
    public TeachTask loadTeachTask(Long taskId) {
        String hql = "from TeachTask where id=" + taskId;
        List tasks = utilDao.searchHQLQuery(hql);
        if (tasks.isEmpty())
            return null;
        else
            return (TeachTask) tasks.get(0);
    }
    
    public TeachTask getTeachTask(Teacher teacher, Course course, AdminClass adminClass,
            TeachCalendar calendar) {
        String taskHql = "select task from TeachTask as task "
                + " join task.arrangeInfo.teachers as teacher"
                + " join task.teachClass.adminClasses as adminClass " + " where task.course.id ="
                + course.getId() + " and task.calendar.id=" + calendar.getId()
                + " and adminClass.id=" + adminClass.getId() + " and teacher.id=" + teacher.getId();
        ;
        Query query = getSession().createQuery(taskHql);
        List tasks = query.list();
        return (TeachTask) (tasks.isEmpty() ? null : tasks.iterator().next());
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#getTeachTasksByCategory(java.lang.String,
     *      com.shufe.dao.course.task.TeachTaskFilterCategory,
     *      com.shufe.model.system.calendar.TeachCalendar, int, int)
     */
    public Pagination getTeachTasksByCategory(Serializable id, TeachTaskFilterCategory category,
            TeachCalendar calendar, int pageNo, int pageSize) {
        Map params = new HashMap(3);
        if (category.getCategoryName().equals("teacher"))
            id = "%" + id + "%";
        params.put("id", id);
        params.put("calendarId", calendar.getId());
        category.setPostfix(" and task.calendar.id= :calendarId ");
        
        return search(category.getQueryString(), params, pageNo, pageSize);
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#getTeachTasksByCategory(java.lang.String,
     *      com.shufe.dao.course.task.TeachTaskFilterCategory,
     *      com.shufe.model.system.calendar.TeachCalendar)
     */
    public List getTeachTasksByCategory(Serializable id, TeachTaskFilterCategory category,
            Collection calendars) {
        category.setPrefix("select distinct task.id from TeachTask as task ");
        category.setPostfix(" and task.calendar in (:calendars) ");
        Query taskQuery = category.createQuery(getSession());
        taskQuery.setParameter("id", id);
        taskQuery.setParameterList("calendars", calendars);
        
        List taskIds = taskQuery.list();
        List rs = new ArrayList(taskIds.size());
        for (Iterator iter = taskIds.iterator(); iter.hasNext();) {
            rs.add(get(TeachTask.class, (Long) iter.next()));
        }
        return rs;
    }
    
    public List getTeachTasksOfStd(Serializable stdId, List calendars) {
        String hql = "select task.id from TeachTask as task join task.teachClass.courseTakes as takeInfo"
                + " where (takeInfo.student.id= :id)  and task.calendar in (:calendars) ";
        Query taskIdQuery = getSession().createQuery(hql);
        taskIdQuery.setParameter("id", stdId);
        taskIdQuery.setParameterList("calendars", calendars);
        List taskIds = taskIdQuery.list();
        List tasks = new ArrayList();
        // 把人数放入已经缓存的教学任务中
        for (Iterator iter = taskIds.iterator(); iter.hasNext();) {
            TeachTask tt = (TeachTask) get(TeachTask.class, (Long) iter.next());
            tasks.add(tt);
        }
        return tasks;
    }
    
    /**
     * 返回开课部门列表范围内的教学任务
     * 
     * @param task
     * @param teachDepartIdSeq
     * @param pageNo
     * @param pageSize
     * @return
     */
    
    public Pagination getTeachTasksOfTeachDepart(TeachTask task, Long[] stdTypeIds,
            Long[] teachDepartIds, int pageNo, int pageSize) {
        Criteria criteria = getExampleCriteria(getSession(), task);
        criteria.add(Restrictions.in("arrangeInfo.teachDepart.id", teachDepartIds));
        criteria.add(Restrictions.in("teachClass.stdType.id", stdTypeIds));
        return dynaSearch(criteria, pageNo, pageSize);
    }
    
    public Pagination getTeachTasksOfLonely(TeachTask task, Long[] stdTypeIds,
            Long[] teachDepartIds, int pageNo, int pageSize) {
        Criteria criteria = getExampleCriteria(getSession(), task, new String[] {
                "arrangeInfo.teachDepart", "taskGroup" }, true);
        criteria.add(Restrictions.isNull("taskGroup.id"));
        criteria.add(Restrictions.in("arrangeInfo.teachDepart.id", teachDepartIds));
        criteria.add(Restrictions.in("teachClass.stdType.id", stdTypeIds));
        return dynaSearch(criteria, pageNo, pageSize);
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#getTeachTasksOfTeachPlan(java.lang.Long[],
     *      com.shufe.model.course.task.TeachTask, int, int)
     */
    public Pagination getTeachTasksOfTeachPlan(Long[] teachPlanIds, TeachTask task, int pageNo,
            int pageSize) {
        Criteria criteria = getExampleCriteria(getSession(), task);
        criteria.add(Restrictions.in("fromPlan.id", teachPlanIds));
        return dynaSearch(criteria, pageNo, pageSize);
    }
    
    public Collection getTeachTasks(List conditions, PageLimit limit, List orders) {
        String hql = "from TeachTask as task join where ";
        hql += ConditionUtils.toQueryString(conditions);
        hql += OrderUtils.toSortString(orders);
        Query query = getSession().createQuery(hql);
        if (null == limit) {
            HibernateQuerySupport.bindValues(query, conditions);
            return query.list();
        } else {
            return search(query, ConditionUtils.getParamMap(conditions), limit);
        }
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#getTeachTasks(com.shufe.model.course.task.TeachTask)
     */
    public List getTeachTasks(TeachTask task) {
        return getExampleCriteria(getSession(), task).list();
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#getTeachTasksByIds(java.lang.Long[])
     */
    public List getTeachTasksByIds(Long[] taskIds) {
        return utilDao.load(TeachTask.class, "id", taskIds);
    }
    
    public List getTeachTasksByIds(List taskIds) {
        String hql = "from TeachTask where id in (:taskIds)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("taskIds", taskIds);
        query.setCacheable(true);
        return query.list();
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#getTeachTasks(com.shufe.model.course.task.TeachTask,
     *      int, int)
     */
    public Pagination getTeachTasks(TeachTask task, int pageNo, int pageSize) {
        Criteria criteria = getExampleCriteria(getSession(), task);
        return dynaSearch(criteria, pageNo, pageSize);
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#getTeachTasks(java.lang.String,
     *      com.shufe.model.course.task.TeachTask, com.shufe.model.course.election.ElectParams,
     *      com.ekingstar.eams.system.time.TimeUnit, java.util.Collection, int, int)
     */
    public Pagination getTeachTasksOfElectable(TeachTask task, ElectState state,
            Collection courseIds, TimeUnit time, boolean isScopeConstraint, int pageNo, int pageSize) {
        if (null == task)
            task = new TeachTask();
        task.getElectInfo().setIsElectable(Boolean.TRUE);
        Criteria criteria = getExampleCriteria(getSession(), task, null, false);
        criteria.add(Restrictions.in("calendar", state.calendars));
        if (!CollectionUtils.isEmpty(courseIds)) {
            criteria.add(Restrictions.in("course.id", courseIds));
        }
        // 增加男女生限制
        if (null != state.std.getGenderId()) {
            criteria.add(Restrictions.or(Restrictions.isNull("teachClass.gender.id"), Restrictions
                    .eq("teachClass.gender.id", state.std.getGenderId())));
        }
        // time condition
        if (isScopeConstraint)
            addScopeCriterion(state.std, criteria);
        addTimeCriterion(time, criteria);
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("id"));
        pl.add(Projections.property("teachClass.stdCount"));
        criteria.setProjection(pl);
        Pagination taskIds = dynaSearch(criteria, pageNo, pageSize);
        
        List rs = new ArrayList(taskIds.getItems().size());
        // 把人数放入已经缓存的教学任务中
        for (Iterator iter = taskIds.getItems().iterator(); iter.hasNext();) {
            Object[] taskIdAndStdCount = (Object[]) iter.next();
            TeachTask tt = (TeachTask) get(TeachTask.class, (Long) taskIdAndStdCount[0]);
            rs.add(tt);
            tt.getTeachClass().setStdCount((Integer) taskIdAndStdCount[1]);
        }
        return new Pagination(taskIds.getPageNumber(), taskIds.getPageSize(), new Result(taskIds
                .getResult().getItemCount(), rs));
    }
    
    public Pagination getTeachTasksOfElectable(TeachTask task, TimeUnit time, Collection calendars,
            int pageNo, int pageSize) {
        if (null == task)
            task = new TeachTask();
        task.getElectInfo().setIsElectable(Boolean.TRUE);
        Criteria criteria = getExampleCriteria(getSession(), task);
        criteria.add(Restrictions.in("calendar", calendars));
        // time condition
        addTimeCriterion(time, criteria);
        
        return dynaSearch(criteria, pageNo, pageSize);
    }
    
    /**
     * 添加学生范围查询条件
     * 
     * @param std
     * @param criteria
     */
    private void addScopeCriterion(SimpleStdInfo std, Criteria criteria) {
        List types = new ArrayList();
        List params = new ArrayList();
        StringBuffer scopseRestriction = new StringBuffer(
                "exists( select *  from XK_FW_T  where {alias}.id=jxrwid and ( (qsxh <= '"
                        + std.getStdNo() + "'AND zzxh >= '" + std.getStdNo()
                        + "')    OR  ( (BJIDC is not null and ");
        if (null == std.getAdminClassIds() || std.getAdminClassIds().isEmpty())
            scopseRestriction.append(" instr(BJIDC,(',,'))>0 ");
        else {
            scopseRestriction.append("( (1=2)  ");
            // 有多个班级
            for (Iterator iter = std.getAdminClassIds().iterator(); iter.hasNext();) {
                Long adminClassId = (Long) iter.next();
                scopseRestriction.append(" or instr(BJIDC,(?))>0 ");
                params.add("," + adminClassId + ",");
                types.add(Hibernate.STRING);
            }
            scopseRestriction.append(")");
        }
        scopseRestriction.append(")");
        
        scopseRestriction.append(" or ( BJIDC is null and BMIDC=',1,' and"
                + " (RXPCC is null or instr(RXPCC,(?))>0) and"
                + "(XSLBIDC is null or instr(XSLBIDC,(?))>0) ) ");
        
        scopseRestriction
                .append(" or ( BJIDC is null and (XSLBIDC is null or instr(XSLBIDC,(?))>0) "
                        + "and (RXPCC is null or instr(RXPCC,(?))>0)"
                        + "and (BMIDC is null or instr(BMIDC,(?))>0)"
                        + "and (ZYIDC is null or instr(ZYIDC,(?))>0)"
                        + "and (ZYFXIDC is null or instr(ZYFXIDC,(?))>0)");
        scopseRestriction.append(" ))))");
        
        params.add("," + std.getEnrollTurn() + ",");
        params.add("," + std.getStdTypeId() + ",");
        params.add("," + std.getStdTypeId() + ",");
        params.add("," + std.getEnrollTurn() + ",");
        params.add("," + std.getDepartId() + ",");
        params.add(std.getSpecialityId() == null ? ",," : "," + std.getSpecialityId() + ",");
        params.add(std.getAspectId() == null ? ",," : "," + std.getAspectId() + ",");
        
        for (int i = 0; i < 7; i++) {
            types.add(Hibernate.STRING);
        }
        ArrayUtils.toString(params.toArray());
        Type[] typeArray = new Type[types.size()];
        Criterion scopeCriterion = Restrictions.sqlRestriction(scopseRestriction.toString(), params
                .toArray(), (Type[]) types.toArray(typeArray));
        criteria.add(scopeCriterion);
    }
    
    /**
     * @param time
     * @param criteria
     */
    private void addTimeCriterion(TimeUnit time, Criteria criteria) {
        final Integer zero = new Integer(0);
        if (null != time && null != time.getWeekId() && !zero.equals(time.getWeekId())
                && null != time.getStartUnit() && !zero.equals(time.getStartUnit())) {
            // Criterion timeCriterion = Restrictions.sqlRestriction(
            // "({alias}.ZJ=(?) and {alias}.KSXJ<=(?) and {alias}.JSXJ >=(?))",
            // new Object[] {
            // time.getWeekId(), time.getStartUnit(), time.getStartUnit() },
            // new Type[] { Hibernate.INTEGER, Hibernate.INTEGER,
            // Hibernate.INTEGER });
            // criteria.createCriteria("arrangeInfo.activities",
            // "activity").add(timeCriterion);
            Criterion timeCriterion = Restrictions
                    .sqlRestriction(
                            "exists (select * from jxhd_T jxhd where {alias}.id=jxhd.jxrwid and jxhd.ZJ=(?) and jxhd.KSXJ<=(?) and jxhd.JSXJ >=(?))",
                            new Object[] { time.getWeekId(), time.getStartUnit(),
                                    time.getStartUnit() }, new Type[] { Hibernate.INTEGER,
                                    Hibernate.INTEGER, Hibernate.INTEGER });
            criteria.add(timeCriterion);
        }
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#getDepartsOfTask(java.lang.String[],
     *      java.lang.String[], com.shufe.model.system.calendar.TeachCalendar)
     */
    public List getDepartsOfTask(Long[] stdTypeIds, Long[] departIds, TeachCalendar calendar) {
        Map params = new HashMap();
        params.put("stdTypeIds", stdTypeIds);
        params.put("departIds", departIds);
        params.put("calendarId", calendar.getId());
        return find("getDepartsOfTask", params);
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#getTeachDepartsOfTask(java.lang.String[],
     *      java.lang.String[], com.shufe.model.system.calendar.TeachCalendar)
     */
    public List getTeachDepartsOfTask(Long[] stdTypeIds, Long[] departIds, TeachCalendar calendar) {
        Map params = new HashMap();
        params.put("stdTypeIds", stdTypeIds);
        params.put("departIds", departIds);
        params.put("calendars", Collections.singletonList(calendar));
        return find("getTeachDepartsOfTask", params);
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#getTeachDepartsOfTask(java.lang.String[],
     *      java.lang.String[], com.shufe.model.system.calendar.TeachCalendar)
     */
    public List getTeachDepartsOfTask(Long[] stdTypeIds, Long[] departIds, Collection calendars) {
        Map params = new HashMap();
        params.put("stdTypeIds", stdTypeIds);
        params.put("departIds", departIds);
        params.put("calendars", calendars);
        return find("getTeachDepartsOfTask", params);
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#getCourseTypesOfTask(java.lang.String[],
     *      java.lang.String[], com.shufe.model.system.calendar.TeachCalendar)
     */
    public List getCourseTypesOfTask(Long[] stdTypeIds, Long[] departIds, TeachCalendar calendar) {
        Map params = new HashMap();
        params.put("stdTypeIds", stdTypeIds);
        params.put("departIds", departIds);
        params.put("calendarId", calendar.getId());
        return find("getCourseTypesOfTask", params);
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#getTeacherIdsOfTask(java.lang.String,
     *      java.lang.String, com.shufe.model.system.calendar.TeachCalendar)
     */
    public List getTeachersOfTask(Long[] stdTypeIds, Long[] departIds, TeachCalendar calendar) {
        Map params = new HashMap();
        params.put("stdTypeIds", stdTypeIds);
        params.put("departIds", departIds);
        params.put("calendarId", calendar.getId());
        return find("getTeachersOfTask", params);
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#statTeachTask(java.lang.String,
     *      com.shufe.dao.course.task.TeachTaskFilterCategory,
     *      com.shufe.model.system.calendar.TeachCalendar)
     */
    public int countTeachTask(Serializable id, TeachTaskFilterCategory category,
            TeachCalendar calendar) {
        category.setPrefix("select count(task.id) from TeachTask as task ");
        category.setPostfix(" and task.calendar.id  = :calendarId");
        
        Query countQuery = category.createQuery(getSession());
        if (category.getCategoryName().equals("teacher"))
            id = "%" + id + "%";
        countQuery.setParameter("id", id);
        countQuery.setParameter("calendarId", calendar.getId());
        
        List rsList = countQuery.list();
        return ((Number) rsList.get(0)).intValue();
    }
    
    public void updateTasks(Collection tasks) {
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            update(task);
        }
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#updateTeachTaskByIds(java.lang.String,
     *      java.lang.Object, java.lang.Long[])
     */
    public int updateTeachTaskByIds(String attr, Object value, Long[] taskIds) {
        // 清除二级缓存中的数据
        for (int i = 0; i < taskIds.length; i++)
            getSessionFactory().evict(TeachTask.class, taskIds[i]);
        
        if (attr.indexOf('.') == -1) {
            String updateHql = "update TeachTask set " + attr + " = :value where id in (:taskIds)";
            Query updateQuery = getSession().createQuery(updateHql);
            updateQuery.setParameter("value", value);
            updateQuery.setParameterList("taskIds", taskIds);
            
            return updateQuery.executeUpdate();
        } else {
            int iCount = 0;
            List tasks = getTeachTasksByIds(taskIds);
            try {
                for (Iterator iter = tasks.iterator(); iter.hasNext();) {
                    TeachTask task = (TeachTask) iter.next();
                    iCount++;
                    PropertyUtils.setProperty(task, attr, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return iCount;
            }
            getSession().flush();
            return iCount;
        }
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#updateTeachTaskByMode(java.lang.String,
     *      java.lang.Object, com.shufe.dao.course.task.TeachTaskFilterCategory,
     *      com.shufe.model.system.calendar.TeachCalendar) TODO for id not used
     */
    public int updateTeachTaskByCategory(String attr, Object value, Long id,
            TeachTaskFilterCategory category, TeachCalendar calendar) {
        getSessionFactory().evict(TeachTask.class);
        category.setPrefix("update TeachTask set " + attr + " = :value ");
        category.setPostfix(" and calendar.id = :calendarId");
        return utilDao.executeUpdateHql(category.getQueryString(), new Object[] { value,
                calendar.getId() });
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#updateTeachTaskByCriteria(java.lang.String,
     *      java.lang.Object, com.shufe.model.course.task.TeachTask)
     */
    public int updateTeachTaskByCriteria(String attr, Object value, TeachTask task)
            throws Exception {
        if (attr.indexOf('.') != -1)
            return updateTeachTaskOneByOne(attr, value, task);
        else {
            return updateTeachTaskByCriteria(attr, value, task, null, null);
        }
    }
    
    private int updateTeachTaskOneByOne(String attr, Object value, TeachTask task) throws Exception {
        Pagination page = getTeachTasks(task, 1, 25);
        if (null != page) {
            String setMethodName = "set" + StringUtils.capitalize(attr);
            Method setMethod = null;
            Method[] setors = TeachTask.class.getMethods();
            Object[] values = new Object[] { value };
            for (int i = 0; i < setors.length; i++)
                if (setors[i].getName().equals(setMethodName))
                    setMethod = setors[i];
            for (int i = 1; i <= page.getPageNumber(); i++) {
                page = getTeachTasks(task, i, 25);
                List tasks = page.getItems();
                for (Iterator iter = tasks.iterator(); iter.hasNext();) {
                    TeachTask one = (TeachTask) iter.next();
                    setMethod.invoke(one, values);
                    update(one);
                }
            }
            getSession().flush();
            getSession().clear();
        }
        return page.getItemCount();
        
    }
    
    /**
     * 
     */
    public int updateTeachTaskByCriteria(String attr, Object value, TeachTask task,
            Long[] stdTypeIds, Long[] departIds) {
        getSessionFactory().evict(TeachTask.class);
        Map newParamsMap = new HashMap();
        String updateSql = getUpdateQueryString(attr, value, task, stdTypeIds, departIds,
                newParamsMap);
        Query query = getSession().createQuery(updateSql);
        HibernateQuerySupport.setParameter(query, newParamsMap);
        return query.executeUpdate();
    }
    
    /**
     * 生成批量更新的动态hql
     * 
     * @param attr
     * @param value
     * @param task
     * @param stdTypeIds
     * @param departIds
     * @param newParamsMap
     * @return
     */
    private String getUpdateQueryString(String attr, Object value, TeachTask task,
            Long[] stdTypeIds, Long[] departIds, Map newParamsMap) {
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery.setConditions(ConditionUtils.extractConditions("task", task));
        if (null != stdTypeIds && 0 != stdTypeIds.length) {
            entityQuery.add(new Condition("task.teachClass.stdType.id in (:stdTypeIds) ",
                    stdTypeIds));
        }
        if (null != departIds && 0 != departIds.length) {
            entityQuery.add(new Condition("task.arrangeInfo.teachDepart.id in (:departIds) ",
                    departIds));
        }
        StringBuffer updateSql = new StringBuffer("update TeachTask set " + attr + "=(:" + attr
                + ") where id in (");
        updateSql.append(entityQuery.toQueryString()).append(")");
        debug("[updateTeachTaskByCriteria]:" + updateSql);
        newParamsMap.put(attr, value);
        newParamsMap.putAll(entityQuery.getParams());
        return updateSql.toString();
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#removeTeachTask(java.lang.Long)
     */
    public void removeTeachTask(Long taskId) {
        TeachTask task = (TeachTask) load(TeachTask.class, taskId);
        removeTeachTask(task);
    }
    
    /**
     * @see TeachTaskDAO#removeTeachTask(TeachTask)
     */
    public void removeTeachTask(TeachTask task) {
        super.remove(task);
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#removeTeachTaskOfPlan(com.shufe.model.course.plan.TeachPlan)
     */
    public void removeTeachTaskOfPlan(TeachPlan plan, TeachCalendar calendar) {
        TeachTask task = new TeachTask();
        task.setFromPlan(plan);
        task.setCalendar(calendar);
        List tasks = getExampleCriteria(getSession(), task).list();
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask oneTask = (TeachTask) iter.next();
            remove(oneTask);
        }
    }
    
    public void saveOrUpdateTask(TeachTask task) {
        if (task.isPO()) {
            generator.genTaskSeqNo(task);
            update(task);
        } else {
            generator.genTaskSeqNo(task);
            save(task);
        }
    }
    
    /**
     * @see TeachTaskDAO#saveGenResult(CultivateScheme, List)
     */
    public void saveGenResult(TeachPlan plan, List tasks) {
        saveOrUpdate(tasks);
        update(plan);
    }
    
    /**
     * 产生一个基于教学任务对象的条件查询
     * 
     * @param session
     * @param task
     * @return
     */
    public Criteria getExampleCriteria(Session session, TeachTask task) {
        return getExampleCriteria(session, task, null, true);
    }
    
    /**
     * @param session
     * @param task
     * @param additionExcludedProperties
     *            额外的要排除的属性
     * @return
     */
    public Criteria getExampleCriteria(Session session, TeachTask task,
            String[] additionExcludedProperties, boolean defaultOrder) {
        /*--------------基本的要排除的属性------------------------*/
        List excluded = new ArrayList();
        if (null != additionExcludedProperties && additionExcludedProperties.length > 0) {
            for (int i = 0; i < additionExcludedProperties.length; i++) {
                excluded.add(additionExcludedProperties[i]);
            }
        }
        
        Criteria criteria = session.createCriteria(TeachTask.class);
        if (null != task) {
            // 学生类别大类查询
            Long stdTypeId = null;
            if (ValidEntityPredicate.INSTANCE.evaluate(task.getTeachClass().getStdType())) {
                stdTypeId = task.getTeachClass().getStdType().getId();
            }
            if (null == stdTypeId) {
                if (null != task.getCalendar() && null != task.getCalendar().getStudentType()) {
                    stdTypeId = task.getCalendar().getStudentType().getId();
                }
            }
            if (null != stdTypeId) {
                StudentType stdType = (StudentType) utilDao.get(StudentType.class, stdTypeId);
                List stdTypes = stdType.getDescendants();
                stdTypes.add(stdType);
                Long[] stdTypeIds = new Long[stdTypes.size()];
                for (int i = 0; i < stdTypes.size(); i++) {
                    StudentType one = (StudentType) stdTypes.get(i);
                    stdTypeIds[i] = one.getId();
                }
                criteria.add(Restrictions.in("teachClass.stdType.id", stdTypeIds));
                task.getTeachClass().setStdType(null);
            }
            
            String[] excludedPros = new String[excluded.size()];
            excluded.toArray(excludedPros);
            List criterions = CriterionUtils.getEntityCriterions(task, excludedPros);
            
            /*---------添加对课程的模糊查询--------------*/
            Criteria courseCriteria = criteria.createCriteria("course", "course");
            if (defaultOrder) {
                courseCriteria.addOrder(Order.asc("name"));
            }
            List courseCriterons = CriterionUtils.getEntityCriterions("course.", task.getCourse());
            for (Iterator iter = courseCriterons.iterator(); iter.hasNext();) {
                courseCriteria.add((Criterion) iter.next());
            }
            
            // 查询教师
            if (!task.getArrangeInfo().getTeachers().isEmpty()) {
                Teacher teacher = (Teacher) task.getArrangeInfo().getTeachers().iterator().next();
                List teacherCriterions = new ArrayList();
                if (StringUtils.isNotEmpty(teacher.getName())) {
                    teacherCriterions.add(Restrictions.like("teacher.name", teacher.getName(),
                            MatchMode.ANYWHERE));
                }
                if (teacher.checkId()) {
                    teacherCriterions.add(Restrictions.eq("teacher.id", teacher.getId()));
                }
                if (!teacherCriterions.isEmpty()) {
                    Criteria teacherCriteria = criteria.createCriteria("arrangeInfo.teachers",
                            "teacher");
                    for (Iterator iter = teacherCriterions.iterator(); iter.hasNext();) {
                        teacherCriteria.add((Criterion) iter.next());
                    }
                }
            }
            /*---------添加对教室设备类型要求的模糊查询--------------*/
            if (null != task.getRequirement().getRoomConfigType()
                    && StringUtils.isNotEmpty(task.getRequirement().getRoomConfigType().getName())) {
                criteria.createAlias("requirement.roomConfigType", "configType").add(
                        Restrictions.like("configType.name", task.getRequirement()
                                .getRoomConfigType().getName().trim(), MatchMode.ANYWHERE));
            }
            /*---------添加对开课院系名称的模糊查询--------------*/
            if (null != task.getArrangeInfo().getTeachDepart()
                    && StringUtils.isNotEmpty(task.getArrangeInfo().getTeachDepart().getName())) {
                criteria.createAlias("arrangeInfo.teachDepart", "teachDepart").add(
                        Restrictions.like("teachDepart.name", task.getArrangeInfo()
                                .getTeachDepart().getName().trim(), MatchMode.ANYWHERE));
            }
            /*---------添加对课程性质名称模糊查询--------------*/
            if (null != task.getCourseType()
                    && StringUtils.isNotEmpty(task.getCourseType().getName())) {
                criteria.createAlias("courseType", "courseType").add(
                        Restrictions.like("courseType.name", task.getCourseType().getName().trim(),
                                MatchMode.ANYWHERE));
            }
            /*--------------发布课程组-----------*/
            if (null != task.getTaskGroup()
                    && StringUtils.isNotEmpty(task.getTaskGroup().getName())) {
                criteria.createAlias("taskGroup", "taskGroup").add(
                        Restrictions.like("taskGroup.name", task.getTaskGroup().getName(),
                                MatchMode.ANYWHERE));
            }
            for (Iterator iter = criterions.iterator(); iter.hasNext();) {
                criteria.add((Criterion) iter.next());
            }
        }
        if (defaultOrder) {
            criteria.addOrder(Order.asc("teachClass.name"));
        }
        return criteria;
    }
    
    public List getExample(Session session, TeachTask task) {
        Criteria criteria = session.createCriteria(TeachTask.class);
        List criterions = CriterionUtils.getEntityCriterions(task);
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            criteria.add((Criterion) iter.next());
        }
        return criteria.list();
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#getStdTypesForTeacher(com.shufe.model.system.baseinfo.Teacher)
     */
    public List getStdTypesForTeacher(Teacher teacher) {
        Criteria criteria = getSession().createCriteria(TeachTask.class);
        criteria.setProjection(Projections.groupProperty("teachClass.stdType"));
        criteria.createCriteria("arrangeInfo.teachers", "teacher").add(
                Restrictions.like("teacher.id", teacher.getId()));
        return criteria.list();
    }
    
    public void saveMergeResult(TeachTask[] tasks, int target) {
        Collection toBeRemoved = new ArrayList();
        TeachCalendar calendar = null;
        for (int i = 0; i < tasks.length; i++) {
            if (i == target) {
                continue;
            }
            calendar = tasks[i].getCalendar();
            toBeRemoved.add(tasks[i]);
        }
        // 找出要被删除的任务所排课分配归属院系，取消分配
        EntityQuery query = new EntityQuery(TaskInDepartment.class, "taskIn");
        query.add(new Condition("taskIn.calendar = :calendar", calendar));
        query.join("taskIn.tasks", "task");
        query.add(new Condition("task in (:tasks)", toBeRemoved));
        Collection taskIns = utilDao.search(query);
        for (Iterator it = taskIns.iterator(); it.hasNext();) {
            TaskInDepartment taskIn = (TaskInDepartment) it.next();
            taskIn.getTasks().removeAll(toBeRemoved);
        }
        if (CollectionUtils.isNotEmpty(taskIns)) {
            utilDao.saveOrUpdate(taskIns);
        }
        utilDao.saveOrUpdate(tasks[target]);
        utilDao.remove(toBeRemoved);
    }
    
    public void saveOrUpdate(TeachTask[] tasks) {
        // TODO
        // getSession().setFlushMode(FlushMode.COMMIT);
        Collection teachTasks = new ArrayList();
        for (int i = 0; i < tasks.length; i++) {
            teachTasks.add(tasks[i]);
        }
        // generator.genTaskSeqNos(teachTasks);
        saveOrUpdate(teachTasks);
    }
    
    public void saveOrUpdate(Collection tasks) {
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask)iter.next();
            generator.genTaskSeqNo(task);
            super.saveOrUpdate(task);
            //getSession().flush();
        }
    }
    
    /**
     * 得到指定教学日历下的所有教学任务ID串
     * 
     * @param stdTypeIds
     * @param departIds
     * @param calendar
     * @return
     */
    public List getTeachTasksByCalendar(TeachCalendar calendar, Long[] stdTypeIds, Long[] departIds) {
        Map params = new HashMap();
        params.put("stdTypeIds", stdTypeIds);
        params.put("departIds", departIds);
        params.put("calendarId", calendar.getId());
        return find("getTeachTasksByCalendar", params);
    }
    
    /**
     * @see com.shufe.dao.course.task.TeachTaskDAO#setTaskSeqNoGenerator(com.shufe.dao.course.task.TaskSeqNoGenerator)
     */
    public void setTaskSeqNoGenerator(TaskSeqNoGenerator generator) {
        this.generator = generator;
    }
    
    public Collection getTeachTasksWithMultiClass(TeachTask task, String stdTypeIds,
            String departIds) {
        String hql = "from TeachTask as task" + " where size(task.teachClass.adminClasses)>1"
                + " and task.teachClass.stdType.id in (:stdTypeIds) "
                + " and task.arrangeInfo.teachDepart.id in (:teachDepartIds) "
                + " and task.calendar.id=:calendarId" + " and task.requirement.isGuaPai =false";
        Query query = getSession().createQuery(hql);
        query.setParameterList("stdTypeIds", SeqStringUtil.transformToLong(stdTypeIds));
        query.setParameterList("teachDepartIds", SeqStringUtil.transformToLong(departIds));
        query.setParameter("calendarId", task.getCalendar().getId());
        return query.list();
    }
    
    public int statStdCount(TeachCalendar calendar) {
        int updateTaskCount = 0;
        Connection con = getSession().connection();
        // 起初所有缓存的教学任务
        getSessionFactory().evict(TeachTask.class);
        String strProcedure = "{? = call statStdCount(?)}";
        CallableStatement cstmt = null;
        try {
            cstmt = con.prepareCall(strProcedure);
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.setLong(2, calendar.getId().longValue());
            cstmt.execute();
            updateTaskCount = cstmt.getInt(1);
            con.commit();
            cstmt.close();
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                if (null != cstmt)
                    cstmt.close();
                con.rollback();
            } catch (Exception e1) {
                info("execproduct is failed" + "in save_electresult"
                        + ExceptionUtils.getStackTrace(e));
                throw new RuntimeException(e1.getMessage());
            }
        }
        return updateTaskCount;
    }
    
    public int statStdCount(String taskIdSeq) {
        int updateTaskCount = 0;
        Connection con = getSession().connection();
        // 起初所有缓存的教学任务
        getSessionFactory().evict(TeachTask.class);
        PreparedStatement ps = null;
        try {
            String state = "update jxrw_t rw set rw.bjrs="
                    + "(select count(*) from jxbxs_T jxb where jxb.jxrwid=rw.id) where instr(',"
                    + taskIdSeq + ",',','||rw.id||',')>0";
            ps = con.prepareStatement(state);
            updateTaskCount = ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (SQLException e) {
            try {
                if (null != ps)
                    ps.close();
                con.rollback();
            } catch (Exception e1) {
            }
            info("exec sql is failed" + "in statStdCount" + ExceptionUtils.getStackTrace(e));
            throw new RuntimeException(e.getMessage());
        }
        return updateTaskCount;
    }
    
    public Long[] getTeachTasksByIds(Collection tasks) {
        Long[] taskIds = new Long[tasks.size()];
        int k = 0;
        for (Iterator it = tasks.iterator(); it.hasNext();) {
            TeachTask task = (TeachTask) it.next();
            taskIds[k++] = task.getId();
        }
        return taskIds;
    }
}
