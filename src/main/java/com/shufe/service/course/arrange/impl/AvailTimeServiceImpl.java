//$Id: AvailTimeServiceImpl.java,v 1.1 2006/08/02 00:53:09 duanth Exp $
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

package com.shufe.service.course.arrange.impl;

import com.shufe.dao.course.arrange.AvailTimeDAO;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.course.arrange.AvailTimeService;

/**
 * 排课可用时间设置服务实现
 * 
 * @author chaostone 2005-11-16
 */
public class AvailTimeServiceImpl implements
        AvailTimeService {

    private AvailTimeDAO availTimeDAO;

    /**
     * @see com.shufe.service.course.arrange.AvailTimeService#getRoomAvailTime(java.lang.String)
     */
    public AvailableTime getRoomAvailTime(Long roomId) {
        return (null!=roomId) ? availTimeDAO
                .getRoomAvailTime(roomId) : null;

    }

    /**
     * @see com.shufe.service.course.arrange.AvailTimeService#getTeacherAvailTime(java.lang.String)
     */
    public AvailableTime getTeacherAvailTime(Long teacherId) {
        return (null!=teacherId) ? availTimeDAO
                .getTeacherAvailTime(teacherId) : null;
    }

    /**
	 * @see com.shufe.service.course.arrange.AvailTimeService#getTeacherAvailTime(com.shufe.model.system.baseinfo.Teacher)
	 */
	public AvailableTime getTeacherAvailTime(Teacher teacher) {
		return getTeacherAvailTime(teacher.getId());
	}

	/**
     * @see com.shufe.service.course.arrange.AvailTimeService#saveRoomAvailTime(java.lang.String,
     *      com.shufe.model.course.arrange.AvailableTime)
     */
    public void saveRoomAvailTime(Long roomId, AvailableTime time) {
        availTimeDAO.saveRoomAvailTime(roomId, time);
    }

    /**
     * @see com.shufe.service.course.arrange.AvailTimeService#saveTeacherAvailTime(java.lang.String,
     *      com.shufe.model.course.arrange.AvailableTime)
     */
    public void saveTeacherAvailTime(Long teacherId, AvailableTime time) {
        availTimeDAO.saveTeacherAvailTime(teacherId, time);
    }

    /**
	 * @see com.shufe.service.course.arrange.AvailTimeService#saveTeacherAvailTime(com.shufe.model.system.baseinfo.Teacher, com.shufe.model.course.arrange.AvailableTime)
	 */
	public void saveTeacherAvailTime(Teacher teacher, AvailableTime time) {
		  availTimeDAO.saveTeacherAvailTime(teacher.getId(), time);
	}

	/**
     * @see com.shufe.service.course.arrange.AvailTimeService#updateAvailTime(com.shufe.model.course.arrange.AvailableTime)
     */
    public void updateAvailTime(AvailableTime time) {
        availTimeDAO.updateAvailTime(time);

    }

    /**
     * @param availTimeDAO
     *            The availTimeDAO to set.
     */
    public void setAvailTimeDAO(AvailTimeDAO availTimeDAO) {
        this.availTimeDAO = availTimeDAO;
    }

}
