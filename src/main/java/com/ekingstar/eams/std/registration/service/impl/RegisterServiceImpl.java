//$Id: RegistrationServiceImpl.java,v 1.1 2007-5-26 下午02:41:41 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-5-26         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.registration.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.fee.service.TuitionFeeService;
import com.ekingstar.eams.std.info.Student;
import com.ekingstar.eams.std.registration.model.Register;
import com.ekingstar.eams.std.registration.model.RegisterUserGroup;
import com.ekingstar.eams.std.registration.service.RegisterService;
import com.ekingstar.eams.system.basecode.industry.RegisterState;
import com.ekingstar.eams.system.time.TeachCalendar;
import com.ekingstar.security.User;
import com.shufe.service.BasicService;

public class RegisterServiceImpl extends BasicService implements RegisterService {
	private TuitionFeeService tuitionFeeService;

	public void register(TeachCalendar calendar, Collection stds, String remark,
			RegisterState registerState) {
		Boolean isTuitionFeeCompleted = Boolean.TRUE;
		
		Boolean isPassed = Boolean.FALSE;
		List registrations = new ArrayList();
		Timestamp registerAt = new Timestamp(System.currentTimeMillis());
		for (Iterator iter = stds.iterator(); iter.hasNext();) {
			Student std = (Student) iter.next();
			Register register = getRegister(std, calendar);
			// 从交费表TuitionFee中获得该学生是否交费完成
			isTuitionFeeCompleted = tuitionFeeService.isCompleted(std.getId(), calendar.getId());
			if (registerState.getCode().equals("98")) {
				if (isTuitionFeeCompleted.booleanValue()) {
					isPassed = Boolean.TRUE;
				} else {
					// 根据暂缓注册的ID查找到暂缓注册对象
					registerState = (RegisterState) utilService.get(RegisterState.class, new Long(
							2));
				}
			}
			// 如果没有注册记录
			if (null == register) {
				registrations.add(new Register(calendar, std, registerAt, remark, registerState,
						isPassed, isTuitionFeeCompleted));
			} else {
				// 存在注册记录
				register.setIsPassed(isPassed);
				register.setIsTuitionFeeCompleted(isTuitionFeeCompleted);
				register.setCalendar(calendar);
				register.setState(registerState);
				registrations.add(register);
			}
		}
		utilDao.saveOrUpdate(registrations);
	}

	public Register getRegister(Student std, TeachCalendar calendar) {
		EntityQuery query = new EntityQuery(Register.class, "register");
		query.add(new Condition("register.std=:std", std));
		query.add(new Condition("register.calendar=:calendar", calendar));
		Collection collection = utilService.search(query);
		if (!collection.isEmpty()) {
			return (Register) (((List) collection).get(0));
		}
		return null;
	}

	public Map getRegister(String stdCode, String calendarId) {
		Map registerMap = new HashMap();
		EntityQuery query = new EntityQuery(Register.class, "register");
		query.add(new Condition("register.std.code=:code", stdCode));
		query.add(new Condition("register.calendar.id=:calendarId", new Long(calendarId)));
		Collection collection = utilService.search(query);
		// 找到学生的收费信息
		Student std = getStudent(stdCode);
		if (null == std) {
			return null;
		}
		Boolean registerIsTuitionFeeCompleted = tuitionFeeService.isCompleted(std.getId(),
				new Long(calendarId));
		// 如果没有注册信息，从学生信息表中取得学生信息。
		if (collection.isEmpty()) {
			registerMap.put("stdName", std.getName());
			registerMap.put("stateName", "99");
			registerMap.put("speciality", std.getMajor().getName());
			registerMap.put("sex", std.getGender().getName());
			registerMap.put("registerIsTuitionFeeCompleted", registerIsTuitionFeeCompleted);
			registerMap.put("registerIsPassed", Boolean.FALSE);
		} else {
			Register register = (Register) ((List) collection).get(0);
			registerMap.put("stdName", register.getStd().getName());
			registerMap.put("stateName", register.getState().getCode());
			registerMap.put("registerIsTuitionFeeCompleted", registerIsTuitionFeeCompleted);
			registerMap.put("registerIsPassed", register.getIsPassed());
			registerMap.put("speciality", std.getMajor().getName());
			registerMap.put("sex", std.getGender().getName());
		}
		return registerMap;
	}

	public Student getStudent(String stdCode) {
		EntityQuery query = new EntityQuery(Student.class, "student");
		query.add(new Condition("student.code =:code", stdCode));
		List rs = (List) utilService.search(query);
		if (rs.size() != 1)
			return null;
		else {
			return (Student) rs.get(0);
		}
	}

	public Map getUser(String userName) {
		Map userMap = new HashMap();
		EntityQuery query = new EntityQuery(User.class, "user");
		query.add(new Condition("user.name=:userName", userName));
		List rs = (List) utilService.search(query);
		if (rs.size() != 1)
			return null;
		else {
			userMap.put("name", ((User) rs.get(0)).getUserName());
			return userMap;
		}
	}

	public RegisterUserGroup getUserGroup(User user) {
		EntityQuery query = new EntityQuery(RegisterUserGroup.class, "userGroup");
		query.join("userGroup.users", "registerUser");
		query.add(new Condition("registerUser.name=:name", user.getName()));
		List rs = (List) utilService.search(query);
		if (!rs.isEmpty()) {
			return (RegisterUserGroup) rs.get(0);
		} else {
			return null;
		}
		// 高级教务员
		// query.add(new Condition("userGroup.name=:name", "高级教务员"));
		// List rs = (List) utilService.search(query);
		// RegisterUserGroup userGroup = (RegisterUserGroup) rs.get(0);
		// for (Iterator iter = userGroup.getUsers().iterator();
		// iter.hasNext();) {
		// User element = (User) iter.next();
		// if (user.getName().equals( element.getName()))
		// return userGroup;
		// }
		// 普通教务员
		// query.add(new Condition("userGroup.name=:name", "普通教务员"));
		// rs = (List) utilService.search(query);
		// userGroup = (RegisterUserGroup) rs.get(0);
		// for (Iterator iter = userGroup.getUsers().iterator();
		// iter.hasNext();) {
		// User element = (User) iter.next();
		// if (user.getName().equals( element.getName()))
		// return userGroup;
		// }
		// return null;
	}

	public int getRoleState(User user) {
		RegisterUserGroup userGroup = getUserGroup(user);
		// 没有教务员权限
		if (null == userGroup) {
			return -1;
		}
		// 不在维护日期内
		Timestamp now = new Timestamp(new Date().getTime());
		if (now.before(userGroup.getBeginAt()) || now.after(userGroup.getEndAt())) {
			return 0;
		}
		// 在维护权限内
		return 1;
	}

	public List dailyStat(TeachCalendar calendar) {

		return null;
	}

	public List totalStat(TeachCalendar calendar) {
        // Map departMap = new HashMap();
        // String hql = "select yzyid,count(*) from xsxx_t group by yzyid";
        // EntityQuery query = new EntityQuery(hql);
        //		List departStats = (List) utilService.search(query);
		// for (int i = 0; i < depar.length; i++) {
		// array_type array_element = depar[i];
		//			
		// }
		return null;
	}

	public void setTuitionFeeService(TuitionFeeService tuitionFeeService) {
		this.tuitionFeeService = tuitionFeeService;
	}
}
