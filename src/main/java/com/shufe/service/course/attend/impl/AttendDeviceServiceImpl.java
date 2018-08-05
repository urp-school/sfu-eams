package com.shufe.service.course.attend.impl;

import com.shufe.dao.course.attend.AttendDeviceDAO;
import com.shufe.model.course.attend.AttendDevice;
import com.shufe.service.BasicService;
import com.shufe.service.course.attend.AttendDeviceService;

public class AttendDeviceServiceImpl extends BasicService implements AttendDeviceService {
	private AttendDeviceDAO attendDeviceDAO = null;	
	public void setAttendDeviceDAO(AttendDeviceDAO attendDeviceDAO) {
		this.attendDeviceDAO = attendDeviceDAO;
	}

	public void saveAttendDevice(AttendDevice attendDevice) {
		attendDeviceDAO.saveAttendDevice(attendDevice);
	}
}
