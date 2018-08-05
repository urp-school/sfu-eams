//$Id: CourseTakeDAO.java,v 1.11 2007/01/17 06:18:10 duanth Exp $
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
 * duan           2006-2-24          add isTake
 ********************************************************************************/

package com.shufe.dao.course.arrange.task;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.dao.BasicDAO;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 上课名单存取接口
 * 
 * @author chaostone
 */
public interface CourseTakeDAO extends BasicDAO {
    
    /**
     * 检查学生是否修读了指定的教学任务
     * 
     * @return
     */
    boolean isTake(Student std, TeachTask task);
    
    /**
     * 查询学生某一个任务的上课名单
     * 
     * @param teachTaskId
     * @param stdId
     * @return
     */
    public CourseTake getCourseTask(Long teachTaskId, Long stdId);
    
    /**
     * 查询学生既往的上课修读纪录
     * 
     * @param std
     * @return
     */
    public List getCourseTakes(Student std);
    
    /**
     * 查询学生在指定时间内的冲突名单
     * 
     * @param stdId
     * @param timeUnit
     * @param calendar 可以为null
     * @return
     */
    public List getCourseTakes(Long stdId, TimeUnit timeUnit, TeachCalendar calendar);
    
    /**
     * 查询学生需要评教的courseTake<br>
     * 返回的名单中的教学任务应该是指定过问卷的
     * 
     * @param studentId
     * @param teachCalendarId
     * @return
     */
    public List getCourseTakeNeedEvaluate(Long studentId, Long teachCalendarId);
    
    /**
     * {@link #getCourseTakeNeedEvaluate(Long, Long)} 返回的名单中的教学任务应该是指定过问卷的
     * 
     * @param studentId
     * @param teachCalendars
     * @return
     */
    public List getCourseTakeIdsNeedEvaluate(Long studentId, Collection teachCalendars);
    
    /**
     * 查询学生在指定已经选的课程id
     * 
     * @param stdId
     * @return
     */
    public List getTakeCourseIdsOfStd(Long stdId, List calendars);
    
    /**
     * 查询某几个学期的指定学生上的所有课程
     * 
     * @param std
     * @param calendar
     * @return
     */
    List getTakedTasks(Student std, Collection calendars);
    
    /**
     * 指定行政班的学生到教学任务中.
     * 
     * @param task
     * @return 返回指定数量
     */
    public int assignStds(TeachTask task);
    
    /**
     * 批量删除学生
     * 
     * @param task
     * @param stdIds
     */
    public void batchRemove(TeachTask task, Collection stdIds);
    
    /**
     * 计算学生的已选学分
     * 
     * @param std
     * @param calendar
     * @return
     */
    public Float statCreditFor(Student std, List calendars);
    
    /**
     * 检查学生是否为有效状态，和学生有没有上该门课
     * 
     * @param taskId
     * @param stdId
     * @return
     */
    public Map stdCourseValidator(String taskId, String stdId);
}
