package com.shufe.dao.course.attend.hibernate;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.attend.AttendDeviceDAO;
import com.shufe.model.course.attend.AttendDevice;

public class AttendDeviceDAOHibernate extends BasicHibernateDAO implements AttendDeviceDAO {
	public void saveAttendDevice(AttendDevice attendDevice) {
		getHibernateTemplate().save(attendDevice);
	}
}
