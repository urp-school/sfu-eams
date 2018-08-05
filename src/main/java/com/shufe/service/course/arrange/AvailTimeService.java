//$Id: AvailTimeService.java,v 1.1 2006/08/02 00:53:13 duanth Exp $
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
 * chaostone             2005-11-16         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange;

import com.shufe.dao.course.arrange.AvailTimeDAO;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.system.baseinfo.Teacher;

/**
 * 排课可用时间设置服务
 * 
 * @author chaostone 2005-11-16
 */
public interface AvailTimeService {
    /**
     * 获得教师的可用时间
     * 
     * @param teacherId
     * @return
     */
    public AvailableTime getTeacherAvailTime(Long teacherId);
    /**
     * 获得教师的可用时间
     * 
     * @param teacherId
     * @return
     */
    public AvailableTime getTeacherAvailTime(Teacher teacher);
    /**
     * 获得教室的可用时间
     * 
     * @param teacherId
     * @return
     */
    public AvailableTime getRoomAvailTime(Long roomId);
    /**
     * 新建教师的可用时间
     * @param teacherId
     * @param time
     */
    public void saveTeacherAvailTime(Long teacherId,AvailableTime time);
    /**
     * 新建教师的可用时间
     * @param teacherId
     * @param time
     */
    public void saveTeacherAvailTime(Teacher teacher,AvailableTime time);
    /**
     * 新建教室的可用时间
     * @param roomId
     * @param time
     */
    public void saveRoomAvailTime(Long roomId,AvailableTime time);
    /**
     * 更新已有的可用时间
     * @param time
     */
    public void updateAvailTime(AvailableTime time);
    /**
     * 设置可用时间的存取对象
     * @param dao
     */
    public void setAvailTimeDAO(AvailTimeDAO dao);
}
