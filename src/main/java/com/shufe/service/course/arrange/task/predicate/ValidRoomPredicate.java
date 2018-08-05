//$Id: ValidRoomPredicate.java,v 1.2 2006/11/11 06:14:07 duanth Exp $
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
 * chaostone             2005-11-11         Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.task.predicate;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 验证教室是否符合排课要求<br>
 * 1,教室是否符合给定的设备配置要求<br>
 * 2,教室可以容纳听课的人数,是否不小于要求座位数<br>
 * 3,(可选)给定的时间是否在教室的可用时间范围内<br>
 * 教室的可用时间必须用长度为CourseUnit.max*WeekInfo.max的01串
 * 
 * @see validRoomPredicateTest#testAvailableTime()
 * @author chaostone 2005-11-11
 */
public class ValidRoomPredicate implements Predicate {
    
    private TimeUnit[] timeUnits;
    
    private ClassroomType type;
    
    private int capacityOfCourse;
    
    private boolean considerAvailTime;
    
    public ValidRoomPredicate(TimeUnit timeUnit, ClassroomType type, int capacityOfCourse,
            boolean considerAvailTime) {
        this(new TimeUnit[] { timeUnit }, type, capacityOfCourse, considerAvailTime);
    }
    
    public ValidRoomPredicate(TimeUnit[] timeUnits, ClassroomType type, int capacityOfCourse,
            boolean considerAvailTime) {
        this.timeUnits = timeUnits;
        this.type = type;
        this.capacityOfCourse = capacityOfCourse;
        this.considerAvailTime = considerAvailTime;
    }
    
    /**
     * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
     */
    public boolean evaluate(Object arg0) {
        // TODO for verify hibernate proxy object
        Classroom room = (Classroom) arg0;
        if (null != room.getConfigType() && null != type
                && room.getConfigType().getId().equals(type.getId())) {
            // 检查容纳听课人数
            if (room.getCapacityOfCourse().intValue() < this.capacityOfCourse) {
                return false;
            }
            if (!considerAvailTime) {
                return true;
            }
            /*------如果教室没有设置可用时间，则默认所时间都可用--------------*/
            AvailableTime time = room.getAvailableTime();
            if (null == time || StringUtils.isEmpty(time.getAvailable())) {
                return true;
            }
            for (int i = 0; i < timeUnits.length; i++) {
                int startIndex = (timeUnits[i].getWeekId().intValue() - 1) * TeachCalendar.MAXUNITS;
                // 取出所要匹配的那一天
                String dayTime = time.getAvailable().substring(startIndex,
                        startIndex + TeachCalendar.MAXUNITS);
                int startUnitIndex = timeUnits[i].getStartUnit().intValue() - 1;
                if (startUnitIndex + timeUnits[i].getUnitCount() > TeachCalendar.MAXUNITS) {
                    return true;
                }
                if (!dayTime
                        .substring(startUnitIndex, startUnitIndex + timeUnits[i].getUnitCount())
                        .equals(StringUtils.repeat("1", timeUnits[i].getUnitCount()))) {
                    return false;
                }
            }
            return true;
        } else
            return false;
    }
}