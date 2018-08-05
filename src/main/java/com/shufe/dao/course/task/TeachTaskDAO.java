//$Id: TeachTaskDAO.java,v 1.16 2006/12/26 09:48:33 duanth Exp $
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

package com.shufe.dao.course.task;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.dao.BasicDAO;
import com.shufe.model.course.election.ElectState;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 教学任务实体类存取接口
 * 
 * @author chaostone 2005-10-22
 */
public interface TeachTaskDAO extends BasicDAO {
    
    /**
     * 根据id查找对应的教学任务.
     * 
     * @param taskId
     * @return
     */
    public TeachTask getTeachTask(Long taskId);
    
    /**
     * 根据id加载对应的教学任务.
     * 
     * @param taskId
     * @return
     */
    public TeachTask loadTeachTask(Long taskId);
    
    /**
     * 查询指定学期教师\班级\课程的教学任务
     * 
     * @param teacher
     * @param course
     * @param adminClass
     * @param calendar
     * @return
     */
    public TeachTask getTeachTask(Teacher teacher, Course course, AdminClass adminClass,
            TeachCalendar calendar);
    
    /**
     * 按照指定的类别获得当前学期的所有教学任务
     * 
     * @param id
     * @param category
     * @return
     */
    public List getTeachTasksByCategory(Serializable id, TeachTaskFilterCategory category,
            Collection calendars);
    
    public List getTeachTasksOfStd(Serializable stdId, List calendars);
    
    /**
     * 按照指定的类别获取当前学期的固定页面的教学任务
     * 
     * @param id
     * @param viewEnum
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getTeachTasksByCategory(Serializable id, TeachTaskFilterCategory category,
            TeachCalendar calendar, int pageNo, int pageSize);
    
    /**
     * 按照教学任务中设定的条件查询所有符合的数据.
     * 
     * @param task
     * @return
     */
    public List getTeachTasks(TeachTask task);
    
    /**
     * 用教学任务id数组中对应的教学任务.
     * 
     * @param taskIds
     * @return
     */
    public List getTeachTasksByIds(Long[] taskIds);
    
    /**
     * 按照教学任务中设定的条件查询符合的固定页面的数据.
     * 
     * @param task
     * @return
     */
    public Pagination getTeachTasks(TeachTask task, int pageNo, int pageSize);
    
    /**
     * 返回开课部门列表范围内的教学任务
     * 
     * @param task
     * @param stdTypeIds
     * @param teachDepartIdSeq
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getTeachTasksOfTeachDepart(TeachTask task, Long[] stdTypeIds,
            Long[] teachDepartIds, int pageNo, int pageSize);
    
    /**
     * 获得学生可以选的课程
     * 
     * @param task
     * @param state
     * @param time
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getTeachTasksOfElectable(TeachTask task, ElectState state,
            Collection courseIds, TimeUnit time, boolean isScopeConstraint, int pageNo,
            int pageSize);
    
    /**
     * @param task
     * @param time
     * @param calendars
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getTeachTasksOfElectable(TeachTask task, TimeUnit time, Collection calendars,
            int pageNo, int pageSize);
    
    /**
     * 获得未编组的任务
     * 
     * @param task
     * @param teachDepartIds
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getTeachTasksOfLonely(TeachTask task, Long[] stdTypeIds,
            Long[] teachDepartIds, int pageNo, int pageSize);
    
    /**
     * 查询培养计划中生成教学任务
     * 
     * @param teachPlanIdSeq
     * @param task
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getTeachTasksOfTeachPlan(Long[] teachPlanIds, TeachTask task, int pageNo,
            int pageSize);
    
    public Collection getTeachTasks(List conditions, PageLimit limit, List orders);
    
    /**
     * 依照过滤的类别，批量更新
     * 
     * @param attr
     * @param value
     * @param category
     * @param calendar
     */
    public int updateTeachTaskByCategory(String attr, Object value, Long id,
            TeachTaskFilterCategory category, TeachCalendar calendar);
    
    /**
     * 依照教学任务的id串来批量更新
     * 
     * @param atrr
     * @param value
     * @param TaskIds
     * @param calendar
     */
    public int updateTeachTaskByIds(String attr, Object value, Long[] taskIds);
    
    /**
     * 通过更新条件查询的覆盖结果
     * 
     * @param attr
     * @param value
     * @param task
     * @return
     */
    public int updateTeachTaskByCriteria(String attr, Object value, TeachTask task)
            throws Exception;
    
    /**
     * 通过更新条件查询的覆盖结果
     * 
     * @param attr
     * @param value
     * @param task
     * @param stdTypeIds
     * @param departIds
     */
    public int updateTeachTaskByCriteria(String attr, Object value, TeachTask task,
            Long[] stdTypeIds, Long[] departIds);
    
    /**
     * 更新一批教学任务
     * 
     * @param tasks
     */
    public void updateTasks(Collection tasks);
    
    /**
     * 删除单个教学任务，教学任务中已经排课的删除排课结果
     * 
     * @param taskId
     */
    public void removeTeachTask(Long taskId);
    
    /**
     * 删除单个教学任务，教学任务中已经排课的删除排课结果
     * 
     * @param task
     */
    public void removeTeachTask(TeachTask task);
    
    /**
     * 删除从制定培养计划中生成的教学任务
     * 
     * @param plan
     */
    public void removeTeachTaskOfPlan(TeachPlan plan, TeachCalendar calendar);
    
    /**
     * 按照学年度学期和给定的类别统计
     * 
     * @param category
     * @return
     */
    public int countTeachTask(Serializable id, TeachTaskFilterCategory category,
            TeachCalendar calendar);
    
    /**
     * 返回指定权限范围内的教学任务中的上课院系列表
     * 
     * @param stdTypeIdSeq
     * @param departIdSeq
     * @param calendar
     * @return
     */
    public List getDepartsOfTask(Long[] stdTypeIds, Long[] teachDepartIds, TeachCalendar calendar);
    
    /**
     * 返回指定权限范围内的教学任务中的开课院系列表
     * 
     * @param stdTypeIdSeq
     * @param departIdSeq
     * @param calendar
     * @return
     */
    public List getTeachDepartsOfTask(Long[] stdTypeIds, Long[] teachDepartIds,
            TeachCalendar calendar);
    
    /**
     * 返回指定权限范围内的教学任务中的开课院系列表
     * 
     * @param stdTypeIdSeq
     * @param departIdSeq 开课院系id串
     * @param calendars
     * @return
     */
    public List getTeachDepartsOfTask(Long[] stdTypeIds, Long[] teachDepartIds, Collection calendars);
    
    /**
     * 返回指定权限范围内的教学任务中的课程类别列表
     * 
     * @param stdTypeIdSeq
     * @param departIdSeq
     * @param calendar
     * @return
     */
    public List getCourseTypesOfTask(Long[] stdTypeIds, Long[] teachDepartIds,
            TeachCalendar calendar);
    
    /**
     * 返回指定权限范围内的教学任务中的上课教师列表
     * 
     * @param stdTypeIdSeq
     * @param departIdSeq
     * @param calendar
     * @return
     */
    public List getTeachersOfTask(Long[] stdTypeIds, Long[] departIds, TeachCalendar calendar);
    
    /**
     * 查询某教师所教授的学生类别
     * 
     * @param teacher
     * @return
     */
    public List getStdTypesForTeacher(Teacher teacher);
    
    /**
     * tasks[target] is to be updated,and other's is to be deleted.
     * 
     * @param tasks
     * @param target
     */
    public void saveMergeResult(TeachTask[] tasks, int target);
    
    /**
     * 1)保存一个培养计划的生成结果:tasks<br>
     * 2)更新培养计划，记录已经生成的学期标记<br>
     * 
     * @param plan
     * @param tasks
     */
    public void saveGenResult(TeachPlan plan, List tasks);
    
    /**
     * tasks[0]is the target to be updated,and other's is to be saved.
     * 
     * @param tasks
     */
    public void saveOrUpdate(TeachTask[] tasks);
    
    public void saveOrUpdate(Collection tasks);
    
    /**
     * 得到指定教学日历下的所有教学任务ID串
     * 
     * @param stdTypeIds
     * @param departIds
     * @param calendar
     * @return
     */
    public List getTeachTasksByCalendar(TeachCalendar calendar, Long[] stdTypeIds, Long[] departIds);
    
    /**
     * 设置课程序号生成器
     * 
     * @param generator
     */
    public void setTaskSeqNoGenerator(TaskSeqNoGenerator generator);
    
    public Collection getTeachTasksWithMultiClass(TeachTask task, String stdTypeIds,
            String departIds);
    
    public int statStdCount(TeachCalendar calendar);
    
    public int statStdCount(String taskIdSeq);
    
    public void saveOrUpdateTask(TeachTask task);
}
