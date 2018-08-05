//$Id: Speciality2ndSignUpService.java,v 1.1 2007-5-4 下午05:37:17 chaostone Exp $
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
 *chaostone      2007-5-4         Created
 *  
 ********************************************************************************/

package com.shufe.service.std.speciality2nd;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.shufe.dao.std.speciality2nd.Speciality2ndSignUpDAO;
import com.shufe.model.std.speciality2nd.MatriculateParams;
import com.shufe.model.std.speciality2nd.SignUpSetting;
import com.shufe.model.std.speciality2nd.SignUpSpecialitySetting;
import com.shufe.model.std.speciality2nd.SignUpStudent;
import com.shufe.model.system.calendar.TeachCalendar;

public interface Speciality2ndSignUpService {

	/**
	 * 自动录取指定专业设置下的报名数据<br>
	 * 录取规则和算法：<br>
	 * 
	 * @param setting
	 * @throws IOException
	 */
	public void autoMatriculate(SignUpSetting setting, MatriculateParams params)
			throws IOException;

	/**
	 * 查询报名设置中的所有报名数据
	 * 
	 * @param setting
	 * @return
	 */
	public List getSignUpStudents(SignUpSetting setting);

	/**
	 * 查找某学期的报名参数
	 * 
	 * @param calendar
	 * @return
	 */
	public List getSignUpSettings(TeachCalendar calendar);

	/**
	 * 统计各个专业设置中的报名人数和录取人数
	 * 
	 * @param setting
	 * @return
	 */
	public void statStdCountOf(Collection specialitySettings);

	/**
	 * 查询录取未满的专业<br>
	 * 根据setting对象中的专业设置，进行统计和过滤。
	 * 
	 * @param setting
	 * @return
	 */
	public List getUnsaturatedSpecialitySettings(SignUpSetting setting);

	/**
	 * 统计报名设置中各个专业的各个志愿的报名和录取人数，以及整个专业的调剂录取人数
	 * 
	 * @param setting
	 * @return ［specialitySetting 第一志愿报名人数，第一志愿录取人数...调剂录取人数<br>
	 *         志愿的个数依照报名设置中的数值］
	 */
	public List statSignUpAndMatriculate(SignUpSetting setting);

	/**
	 * 统计报名设置中录取人数
	 * 
	 * @param setting
	 * @return [specialitySetting 录取人数]
	 */
	public List statMatriculate(SignUpSetting setting);

	/**
	 * 查找于录取专业想匹配的班级列表
	 * 
	 * @param specialitySetting
	 * @return
	 */
	public List getCorrespondingAdminClasses(
			SignUpSpecialitySetting specialitySetting);

	/**
	 * 自动分班<br>
	 * 首先查找每个报名专业对应的班级，计算空闲人数。<br>
	 * 然后对这些班级进行名称排序，依次按照空闲人数，随机分配人数。
	 * 
	 * @param specialitySettings
	 */
	public void autoAssignClass(List specialitySettings);

	/**
	 * 取消录取单个学生
	 * @param signUpStd
	 */
	public void cancelMatriculate(SignUpStudent signUpStd);

	public void setSpeciality2ndSignUpDAO(
			Speciality2ndSignUpDAO speciality2ndSignUpDAO);
}
