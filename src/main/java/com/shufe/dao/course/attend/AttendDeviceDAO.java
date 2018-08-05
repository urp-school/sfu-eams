package com.shufe.dao.course.attend;

import com.shufe.dao.BasicDAO;
import com.shufe.model.course.attend.AttendDevice;

public interface AttendDeviceDAO extends BasicDAO {
	public void saveAttendDevice(AttendDevice attendDevice);
}
