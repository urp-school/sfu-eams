//$Id: CourseTakeService.java,v 1.11 2007/01/17 06:18:10 duanth Exp $
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

package com.shufe.service.course.arrange.task;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.security.User;
import com.shufe.dao.course.arrange.resource.TeachResourceDAO;
import com.shufe.dao.course.arrange.task.CourseTakeDAO;
import com.shufe.dao.course.election.CreditConstraintDAO;
import com.shufe.dao.course.election.ElectRecordDAO;
import com.shufe.dao.course.grade.GradeDAO;
import com.shufe.dao.system.calendar.TeachCalendarDAO;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.OutputProcessObserver;

public interface CourseTakeService {
    
    public CourseTake getCourseTask(Long teachTaskId, Long stdId);
    
    public void setCourseTakeDAO(CourseTakeDAO courseTakeDAO);
    
    /**
     * 计算学生的已选学分
     * 
     * @param std
     * @param calendar
     * @return
     */
    public Float statCreditFor(Student std, List calendars);
    
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
     * @param calendar
     *            可以为null
     * @return
     */
    public List getCourseTakes(Long stdId, TimeUnit timeUnit, TeachCalendar calendar);
    
    /**
     * 查询某几个学期的指定学生上的所有课程
     * 
     * @param std
     * @param calendar
     * @return
     */
    List getTakedTasks(Student std, Collection calendars);
    
    /**
     * 向教学任务中指定学生
     * 
     * @param calendar
     * @param common
     */
    public void assignStds(Collection tasks, Boolean deleteExisted, OutputProcessObserver observer);
    
    /**
     * 加课<br>
     * 1)新增courseTake<br>
     * 2)更新教学任务所在学期的courseTake中学生的已选学分<br>
     * 3)更新教学任务的实际人数<br>
     * 4)自动判断修读类别<br>
     * 5)将最后一次选课状态更新为"选中"<br>
     * 6)不允许超过学分上限<br>
     * 7)语言等级,HSK等级判断<br>
     * 
     * @param calendar
     * @param std
     * @param tasks
     * @param checkConstraint
     * @return
     */
    public String add(TeachTask task, Student std, boolean checkConstraint);
    
    /**
     * 退课<br>
     * 1)删除courseTake<br>
     * 2)更新教学任务所在学期的courseTake中学生的已选学分<br>
     * 3)更新教学任务的实际人数<br>
     * 4)*更新选中状态(如果有选课记录)<br>
     * 5)*新增退课记录(如果有退课人)<br>
     * 
     * @param courseTakes
     * @param who
     *            退课人,如果退课人存在,则计入退课记录
     */
    public void withdraw(List courseTakes, User who);
    
    /**
     * 对上课名单按照选课结果进行筛选<br>
     * 
     * <pre>
     *   首先如果任务的实际人数低于或等于人数上限则不进行筛选,
     *   对于待筛选的任务,找出选课结果中&quot;指定轮次&quot;中,选中的学生名单.
     *   不筛选修读类别为&quot;指定的&quot;上课名单
     * </pre>
     * 
     * @param tasks
     * @param turn
     *            指定轮次,null默认为最大轮次
     */
    public void filter(List tasks, Integer turn);
    
    /**
     * 检测给定活动的任务中存在冲突的正常上课学生。<br>
     * 不包括免修不免试的学生。
     * 
     * @see CourseTakeType#NORMAL_TYPES
     * @param task
     * @param activities
     * @return
     */
    public Collection collisionTakes(TeachTask task, Collection activities);
    
    public void setTeachResourceDAO(TeachResourceDAO teachResourceDAO);
    
    public void setTeachTaskService(TeachTaskService teachTaskService);
    
    public void setElectRecordDAO(ElectRecordDAO electRecordDAO);
    
    public void setGradeDAO(GradeDAO gradeDAO);
    
    public void setTeachCalendarDAO(TeachCalendarDAO teachCalendarDAO);
    
    public void setCreditConstraintDAO(CreditConstraintDAO creditConstraintDAO);
    /**
     * 发送退课消息
     * @param courseTakes
     * @param sender
     */
    public void sendWithdrawMessage(List courseTakes, User sender);
    
    /**
     * 发送筛选消息
     * @param studentTaskMap
     * @param sender
     */
    public void sendFilterMessage(Map studentTaskMap, User sender);
    
    /**
     * 查询未评教学生名单
     */
    public List getNotStdList(HttpServletRequest request,Long calendarId);
}
