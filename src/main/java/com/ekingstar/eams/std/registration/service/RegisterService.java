//$Id: RegistrationService.java,v 1.1 2007-5-26 下午02:38:17 chaostone Exp $
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

package com.ekingstar.eams.std.registration.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ekingstar.eams.std.info.Student;
import com.ekingstar.eams.system.basecode.industry.RegisterState;
import com.ekingstar.eams.system.time.TeachCalendar;
import com.ekingstar.security.User;
/**
 * 学生注册服务类
 * 
 * @author chaostone
 * 
 */
public interface RegisterService {

	/**
	 * 在某学年学期增加学生的注册信息
	 * 
	 * @param calendar
	 * @param stds
	 */
	public void register(TeachCalendar calendar, Collection stds,String remark,RegisterState registerState);
	
	/**
	 * 根据学号找到相关注册信息
	 */
	public Map getRegister(String stdCode,String calendarId);
	/**
	 * 根据用户名找到用户
	 */
	public Map getUser(String userName);
	/**
	 *获得权限状态 
	 */
	public int getRoleState(User user);
	
	/**
	 * 注册,日统计
	 */
	public List dailyStat(TeachCalendar calendar);
	
	/**
	 * 注册,总统计
	 */
	public List totalStat(TeachCalendar calendar);
	
	/**
	 * 根据学号查询学生
	 * 
	 */
	public Student getStudent(String stdCode);
}
