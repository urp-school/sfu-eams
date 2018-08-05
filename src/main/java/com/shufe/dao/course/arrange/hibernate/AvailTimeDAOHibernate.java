//$Id: AvailTimeDAOHibernate.java,v 1.1 2006/08/02 00:53:12 duanth Exp $
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

package com.shufe.dao.course.arrange.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.arrange.AvailTimeDAO;
import com.shufe.dao.system.baseinfo.ClassroomDAO;
import com.shufe.dao.system.baseinfo.TeacherDAO;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;

public class AvailTimeDAOHibernate extends BasicHibernateDAO implements
		AvailTimeDAO {
	private ClassroomDAO classroomDAO;

	private TeacherDAO teacherDAO;

	public AvailableTime getTeacherAvailTime(Long teacherId) {
		Map params = new HashMap();
		params.put("teacherId", teacherId);
		List timeList = find("getTeacherAvailTime", params);
		if (timeList.isEmpty())
			return null;
		else
			return (AvailableTime) timeList.iterator().next();
	}

	public AvailableTime getRoomAvailTime(Long roomId) {
		Map params = new HashMap();
		params.put("roomId", roomId);
		List timeList = find("getRoomAvailTime", params);
		if (timeList.isEmpty())
			return null;
		else
			return (AvailableTime) timeList.iterator().next();

	}

	public void saveTeacherAvailTime(Long teacherId, AvailableTime time) {
		Teacher teacher = teacherDAO.getTeacherById(teacherId);
		save(time);
		teacher.setAvailableTime(time);
		update(teacher);
	}

	public void saveRoomAvailTime(Long roomId, AvailableTime time) {
		Classroom room = classroomDAO.getClassroom(roomId);
		save(time);
		room.setAvailableTime(time);
		update(room);
	}

	public void updateAvailTime(AvailableTime time) {
		update(time);
	}

	public void setClassroomDAO(ClassroomDAO roomDAO) {
		this.classroomDAO = roomDAO;
	}

	public void setTeacherDAO(TeacherDAO teacherDAO) {
		this.teacherDAO = teacherDAO;
	}

}
