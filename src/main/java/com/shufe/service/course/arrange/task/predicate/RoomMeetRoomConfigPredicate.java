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
 * chaostone             2006-5-27            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.task.predicate;

import java.util.Iterator;

import org.apache.commons.collections.Predicate;

import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;

/**
 * 检查教学任务的建议教室是否符合教室要求<br>
 * 没有建议教室的返回true.
 * 
 * @author chaostone
 * 
 */
public class RoomMeetRoomConfigPredicate implements Predicate, EvaluateValueRemember {
    
    public boolean evaluate(Object object) {
        TeachTask task = (TeachTask) object;
        if (task.getArrangeInfo().getSuggest().getRooms().isEmpty()) {
            return true;
        } else {
            for (Iterator iter = task.getArrangeInfo().getSuggest().getRooms().iterator(); iter
                    .hasNext();) {
                Classroom room = (Classroom) iter.next();
                if (!room.getConfigType().equals(task.getRequirement().getRoomConfigType())) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * @see com.shufe.service.course.arrange.predicate.EvaluateValueRemember#getEvaluateValue(java.lang.Object)
     */
    public Object getEvaluateValue(Object obj) {
        TeachTask task = (TeachTask) obj;
        StringBuffer buf = new StringBuffer();
        buf.append(task.getRequirement().getRoomConfigType().getName()).append(": ");
        for (Iterator iter = task.getArrangeInfo().getSuggest().getRooms().iterator(); iter
                .hasNext();) {
            Classroom room = (Classroom) iter.next();
            if (buf.indexOf(room.getConfigType().getName()) == -1)
                buf.append(room.getConfigType().getName()).append(" ");
        }
        return buf;
    }
    
}
