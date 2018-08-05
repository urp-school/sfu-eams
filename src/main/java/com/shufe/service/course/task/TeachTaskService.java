//$Id: TeachTaskService.java,v 1.21 2006/12/26 09:48:33 duanth Exp $
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
 * chaostone             2005-10-21         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.task;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.commons.query.limit.PageLimit;
import com.shufe.dao.course.task.TeachTaskDAO;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.model.course.arrange.ArrangeInfo;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.web.action.course.task.TaskGenObserver;

/**
 * 教学任务服务类 教学任务默认以开课院系为权限区分
 * 
 * @author chaostone 2005-10-21
 */
public interface TeachTaskService {
    
    /**
     * 根据id查找对应的教学任务.
     * 
     * @param taskId
     * @return
     */
    public TeachTask getTeachTask(final Long taskId);
    
    /**
     * 根据id加载对应的教学任务.
     * 
     * @param taskId
     * @return
     */
    public TeachTask loadTeachTask(final Long taskId);
    
    public Collection getTeachTasks(final List conditions, final PageLimit limit, final List orders);
    
    /**
     * 查询学生指定学期和课程的教学任务.
     * 
     * @param stdNo
     * @param courseId
     * @param calendar
     * @deprecated using getTeachTask(Long taskId),instead;
     * @return
     */
    public TeachTask getTeachTaskByStd(final String stdNo, final Long teachTaskId,
            final TeachCalendar calendar);
    
    public TeachTask genTeachTaskDetail(final PlanCourse planCourse, final TeachCalendar calendar,
            final TeachPlan plan, final AdminClass adminClass, final TaskGenParams params);
    
    /**
     * 按照指定的类别获得学年度学期的所有教学任务
     * 
     * @param id
     * @param category
     * @param calendar
     * @return
     */
    public List getTeachTasksByCategory(final Serializable id,
            final TeachTaskFilterCategory category, TeachCalendar calendar);
    
    /**
     * 按照指定的类别获得多个学年度学期的所有教学任务
     * 
     * @param id
     * @param category
     * @param calendars
     * @return
     */
    public List getTeachTasksByCategory(final Serializable id,
            final TeachTaskFilterCategory category, final Collection calendars);
    
    /**
     * 查询与单个学生关联的任务
     * 
     * @param stdId
     * @param calendars
     * @return
     */
    public List getTeachTasksOfStd(final Serializable stdId, final List calendars);
    
    /**
     * 按照指定的类别获取指定学年度学期的固定页面的教学任务
     * 
     * @param id
     * @param category
     * @param calendar
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getTeachTasksByCategory(final Serializable id,
            final TeachTaskFilterCategory category, final TeachCalendar calendar, final int pageNo,
            final int pageSize);
    
    /**
     * 按照教学任务中设定的条件查询所有符合的数据.
     * 
     * @param task
     * @return
     */
    public List getTeachTasks(final TeachTask task);
    
    /**
     * 查询用逗号串起来的教学任务id对应的教学任务
     * 
     * @param taskIds
     * @return
     */
    public List getTeachTasksByIds(final String taskIds);
    
    /**
     * 用教学任务id数组中对应的教学任务.
     * 
     * @param taskIds
     * @return
     */
    public List getTeachTasksByIds(final Long[] taskIds);
    
    /**
     * 按照教学任务中设定的条件查询符合的固定页面的数据.
     * 
     * @param task
     * @return
     */
    public Pagination getTeachTasks(final TeachTask task, final int pageNo, final int pageSize);
    
    /**
     * 返回开课部门列表范围内的教学任务
     * 
     * @param task
     * @param teachDepartIdSeq
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getTeachTasksOfTeachDepart(final TeachTask task, final String stdTypeIds,
            final String teachDepartIdSeq, final int pageNo, final int pageSize);
    
    /**
     * 获得未编组（包括挂牌组和课程组）的任务. （是否挂牌和是否排完和教学日历参数，可以设在第一个参数教学任务中）.
     * 
     * @param task
     * @param stdTypeIds
     * @param teachDepartIds
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getTeachTasksOfLonely(final TeachTask task, final Long[] stdTypeIds,
            final Long[] teachDepartIds, final int pageNo, final int pageSize);
    
    /**
     * 查询培养计划中生成教学任务
     * 
     * @param teachPlanIdSeq
     * @param task
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getTeachTasksOfTeachPlan(final String teachPlanIdSeq, final TeachTask task,
            final int pageNo, final int pageSize);
    
    /**
     * 教学任务确认/取消
     * 
     * @param taskIds
     * @param confirm
     */
    public void teachTasksConfirm(final String taskIds, final boolean confirm);
    
    /**
     * 教学任务确认/取消
     * 
     * @param taskIds
     * @param confirm
     */
    public void teachTasksConfirm(final Long[] taskIds, final boolean isConfirm);
    
    /**
     * 教学任务确认/取消
     * 
     */
    public void teachTasksConfirm(final TeachTask task, final boolean isConfirm);
    
    /**
     * 针对某个部门的教学任务的确认/取消
     * 
     * @param departId
     * @param confirm
     * @param calendar
     */
    public void teachTasksConfirmOfDepart(final Long departId, final boolean isConfirm,
            final TeachCalendar calendar);
    
    /**
     * 将数组中的指定id教学任务按照第一个教学任务，合并成一个新的教学任务
     * 
     * @param taskIds
     * @return
     */
    public TeachTask merge(final Long taskIds[]);
    
    /**
     * 将数组中的指定id教学任务按照reservedId对应教学任务，合并成一个新的教学任务
     * 
     * @param taskIds
     * @param reservedId
     * @return
     */
    public TeachTask merge(final Long taskIds[], final Long reservedId);
    
    /**
     * 按照指定的模式拆分教学任务
     * 
     * @param task
     * @param num
     * @param category
     * @return
     */
    public TeachTask[] split(final TeachTask task, final int num, final TeachClassSplitMode mode,
            final Integer[] splitUnitNums);
    
    /**
     * 按照学年度学期和给定的类别统计
     * 
     * @param category
     * @return
     */
    public int[] statTeachTask(final String[] ids, final TeachTaskFilterCategory category,
            final TeachCalendar calendar);
    
    /**
     * 保存新的教学任务
     * 
     * @param task
     */
    public void saveTeachTask(final TeachTask task);
    
    /**
     * 更新已有的教学任务
     * 
     * @param task
     */
    public void updateTeachTask(final TeachTask task);
    
    public void saveOrUpdateTask(final TeachTask task);
    
    public void saveOrUpdateTasks(final Collection tasks);
    
    /**
     * 批量更新教学任务中的指定属性的值
     * 
     * @param taskIds
     * @param attr
     * @param value
     */
    public void batchUpdateTeachTask(final Long[] taskIds, final String attr, final Object value);
    
    /**
     * 删除单个教学任务，教学任务中已经排课的删除排课结果
     * 
     * @param taskId
     */
    public void removeTeachTask(final Long taskId);
    
    /**
     * 删除单个教学任务，教学任务中已经排课的删除排课结果
     * 
     * @param taskId
     */
    public void removeTeachTask(final TeachTask task);
    
    /**
     * 删除用逗号串起来的教学任务id对应的教学任务
     * 
     * @param taskIds
     */
    public void removeTeachTaskByIds(final String taskIds);
    
    /**
     * 删除用教学任务id数组中对应的教学任务
     * 
     * @param taskId
     */
    public void removeTeachTaskByIds(final Long[] taskIds);
    
    /**
     * 根据一批培养计划生成指定学年度学期的教学任务
     * 
     * @param schemeIds
     * @param calendar
     * @return
     */
    public int genTeachTasks(final Long[] schemeIds, final TeachCalendar calendar);
    
    /**
     * 根据一批培养计划生成指定学年度学期的教学任务
     * 
     * @param planIds
     * @param calendar
     * @param observer
     * @param params
     * @see TaskGenParams
     * @return
     */
    public int genTeachTasks(final Long[] planIds, final TeachCalendar calendar,
            final TaskGenObserver observer, final TaskGenParams params);
    
    /**
     * 返回指定权限范围内的教学任务中的上课院系列表
     * 
     * @param stdTypeIdSeq
     * @param departIdSeq
     *            开课院系id串
     * @param calendar
     * @return
     */
    public List getDepartsOfTask(final String stdTypeIdSeq, final String teachDepartIdSeq,
            final TeachCalendar calendar);
    
    /**
     * 返回指定权限范围内的教学任务中的开课院系列表
     * 
     * @param stdTypeIdSeq
     * @param departIdSeq
     *            开课院系id串
     * @param calendar
     * @return
     */
    public List getTeachDepartsOfTask(final String stdTypeIdSeq, final String teachDepartIdSeq,
            final TeachCalendar calendar);
    
    /**
     * 返回指定权限范围内的教学任务中的开课院系列表
     * 
     * @param stdTypeIdSeq
     * @param departIdSeq
     *            开课院系id串
     * @param calendars
     * @return
     */
    public List getTeachDepartsOfTask(final String stdTypeIdSeq, final String teachDepartIdSeq,
            final Collection calendars);
    
    /**
     * 返回指定权限范围内的教学任务中的课程类别列表
     * 
     * @param stdTypeIdSeq
     * @param departIdSeq
     *            开课院系id串
     * @param calendar
     * @return
     */
    public List getCourseTypesOfTask(final String stdTypeIdSeq, final String teachDepartIdSeq,
            final TeachCalendar calendar);
    
    /**
     * 返回指定权限范围内的教学任务中的上课教师列表
     * 
     * @param stdTypeIdSeq
     * @param departIdSeq
     * @param calendar
     * @return
     */
    public List getTeachersOfTask(final String stdTypeIdSeq, final String departIdSeq,
            final TeachCalendar calendar);
    
    /**
     * 查询某教师所教授的学生类别
     * 
     * @param teacher
     * @return
     */
    public List getStdTypesForTeacher(final Teacher teacher);
    
    /**
     * 设置教学任务存取实现对象
     * 
     * @param taskDAO
     */
    public void setTeachTaskDAO(final TeachTaskDAO taskDAO);
    
    /**
     * 得到指定教学日历下的所有教学任务ID串
     * 
     * @param stdTypeIds
     * @param departIds
     * @param calendar
     * @return
     */
    public List getTeachTasksByCalendar(final String stdTypeIds, final String departIds,
            final TeachCalendar calendar);
    
    /**
     * 查询得到指定学期,权限范围内的班级开课情况表中的
     * 
     * @param calendar
     * @param stdTypeIds
     * @param departIds
     * @return
     */
    public List getTaskOfCourseTypes(final TeachCalendar calendar, final String stdTypeIds,
            final String departIds, final Collection courseTypes);
    
    /**
     * 批量设置教学任务内的安排信息<br>
     * 包括：起始周,占用周，周课时，总周数，总课时
     * 
     * @param tasks
     * @param info
     */
    public void batchUpdateArrangeInfo(final Collection tasks, final ArrangeInfo info);
    
    /**
     * 教学任务拷贝
     * 
     * @param tasks
     * @param params
     * @return
     */
    public Collection copy(final Collection tasks, final TaskCopyParams params);
    
    /**
     * 合班汇总的教学任务
     * 
     * @param task
     * @param stdTypeIds
     * @param departIds
     * @return
     */
    public Collection getTeachTasksWithMultiClass(final TeachTask task, final String stdTypeIds,
            final String departIds);
    
    /**
     * 统计教学班中的人数
     * 
     * @param calendar
     * @return
     */
    public int statStdCount(final TeachCalendar calendar);
    
    /**
     * 统计指定任务中的教学班中的人数
     * 
     * @param taskIdSeq
     * @return
     */
    public int statStdCount(final String taskIdSeq);
    
    /**
     * 根据课程序号和教学日历条件，获得教学任务信息
     * 
     * @param taskSeqNo
     * @param stdTypeId
     * @param year
     * @param term
     * @return
     */
    public Map getTeachTaskDWR(final String taskSeqNo, final String stdTypeId, final String year,
            final String term);
    
    public TeachTask getTeachTask(final String seqNo, final TeachCalendar calendar);
    
}
