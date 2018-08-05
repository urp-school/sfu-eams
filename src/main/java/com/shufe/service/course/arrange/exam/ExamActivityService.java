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
 * chaostone             2006-12-18            Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.exam;

import java.util.Collection;

import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 监考活动服务接口
 * 
 * @author chaostone
 */
public interface ExamActivityService {
    
    public static int COURSE = 1;
    
    public static int EXAMINER = 2;
    
    public static int INVIGILATOR = 3;
    
    public static int ALL = 4;
    
    /**
     * @param calendar
     * @param teacher
     * @param kind 得到教师的什么考试安排
     * @return
     */
    public Collection getExamActivities(TeachCalendar calendar, Teacher teacher, int kind);
    
    /**
     * 检查教学活动是否冲突
     * 
     * @param examActivityId
     * @param teacherId
     * @return
     */
    public boolean isTeacherCollision(Long examActivityId, Long teacherId);
    
    /**
     * 是监考教师的监考次数
     * 
     * @param invigilatorId
     * @param calendarId
     * @return
     */
    public int invigilationTimes(Long invigilatorId, Long calendarId);
}
