//$Id: AvailTimeDAO.java,v 1.1 2006/08/02 00:53:05 duanth Exp $
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

package com.shufe.dao.course.arrange;

import com.shufe.dao.BasicDAO;
import com.shufe.dao.system.baseinfo.ClassroomDAO;
import com.shufe.dao.system.baseinfo.TeacherDAO;
import com.shufe.model.course.arrange.AvailableTime;

public interface AvailTimeDAO extends BasicDAO {
	/**
	 * 获得教师的可用时间
	 * 
	 * @param teacherId
	 * @return
	 */
	public AvailableTime getTeacherAvailTime(Long teacherId);

	/**
	 * 获得教室的可用时间
	 * 
	 * @param teacherId
	 * @return
	 */
	public AvailableTime getRoomAvailTime(Long roomId);

	/**
	 * 新建教师的可用时间
	 * 
	 * @param teacherId
	 * @param time
	 */
	public void saveTeacherAvailTime(Long teacherId, AvailableTime time);

	/**
	 * 新建教室的可用时间
	 * 
	 * @param roomId
	 * @param time
	 */
	public void saveRoomAvailTime(Long roomId, AvailableTime time);

	/**
	 * 更新已有的可用时间
	 * 
	 * @param time
	 */
	public void updateAvailTime(AvailableTime time);

	/**
	 * 
	 * @param roomDAO
	 */
	public void setClassroomDAO(ClassroomDAO roomDAO);

	/**
	 * 
	 * @param teacherDAO
	 */
	public void setTeacherDAO(TeacherDAO teacherDAO);
}
