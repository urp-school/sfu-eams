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
 * chaostone             2006-11-18            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange;

import java.util.List;

import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 教学活动服务接口
 * 
 * @author chaostone
 * 
 */
public interface TaskActivityService {
    
    /**
     * 如果日历的起始起始日期发生了变化,则根据日历的起始周和周数进行调整教学活动.<br>
     * 1.如果两个日历不一样,则将目标教学活动改成目标日历<br>
     * 2.将activity中的星期几和小节保持不变,将占用周和占用周数字以及占用时间按照新的<br>
     * 日历中的时间设置进行安排.
     * 
     * @param calendar
     */
    public void adjustTimeUnit(TeachCalendar from, TeachCalendar to);
    
    /**
     * 检测教师冲突
     * 
     * @param calendar
     * @param activityClass
     * @return
     */
    public List detectTeacherCollision(TeachCalendar calendar, Class activityClass);
    
    /**
     * 检测教室冲突
     * 
     * @param calendar
     * @param activityClass
     * @return
     */
    public List detectRoomCollision(TeachCalendar calendar, Class activityClass);
    
    /**
     * 检测班级时间冲突<br>
     * FIXME 暂时不支持检测挂牌课程
     * 
     * @param calendar
     * @param activityClass
     * @return
     */
    public List detectClassCollision(TeachCalendar calendar, Class activityClass);
    
    /**
     * 某学期上课时间冲突的学生名单
     * 
     * @param calendar
     * @return
     */
    public List collisionCourseTakes(TeachCalendar calendar);
    
    /**
     * 平移教学活动<br>
     * 平移教学任务和教学任务起始周
     * 
     * @param task
     * @param offset
     *            正的向后移动，负的向前移动
     * 
     */
    public void shift(TeachTask task, int offset);
    
    /**
     * DWR用<br>
     * 判断所选教师是否有课
     * 
     * @param calendarId
     * @param teacherId
     * @param thisTaskId
     * @return
     */
    public boolean isBeenTaskForTeacher(Long calendarId, Long teacherId, Long thisTaskId);
}
