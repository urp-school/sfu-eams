//$Id: ScheduleServiceImpl.java,v 1.1 2007-6-2 12:27:52 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-6-2         Created
 *  
 ********************************************************************************/

package com.shufe.service.degree.thesis.process.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.degree.thesis.process.Schedule;
import com.shufe.model.std.Student;
import com.shufe.service.BasicService;
import com.shufe.service.degree.thesis.process.ScheduleService;

public class ScheduleServiceImpl extends BasicService implements ScheduleService {
    
    public Schedule getScheduleByStd(Student std) {
        Schedule schedule = null;
        StudentType stdType = std.getType();
        String stdLength = String.valueOf(std.getSchoolingLength());
        String enrollYear = std.getEnrollYear();
        if (null != std && null != std.getType() && StringUtils.isNotBlank(stdLength)
                && StringUtils.isNotBlank(enrollYear)) {
            while (null != stdType && null == schedule) {
                List schedules = utilService.load(Schedule.class, new String[] { "studyLength",
                        "enrollYear", "studentType.id" }, new Object[] { stdLength, enrollYear,
                        stdType.getId() });
                schedule = (schedules.size() > 0) ? (Schedule) schedules.get(0) : null;
                stdType = (StudentType) stdType.getSuperType();
            }
        }
        return schedule;
    }
    
}
