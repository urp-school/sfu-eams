//$Id: Speciality2ndSignUpDAO.java,v 1.1 2007-5-7 上午11:04:01 chaostone Exp $
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
 *chaostone      2007-5-7         Created
 *  
 ********************************************************************************/

package com.shufe.dao.std.speciality2nd;

import java.util.List;

import com.shufe.model.std.speciality2nd.SignUpSetting;

public interface Speciality2ndSignUpDAO {

	/**
	 * 录取初始化<br>
	 * 1.更新每个报名学生的录取绩点,归零录取志愿,是否调剂录取，录取专业<br>
	 * 2.归零报名详情中的录取状态<br>
	 * 3.清除学生信息中的专业信息
	 * 
	 * @param setting
	 */
	public void initMatriculate(SignUpSetting setting);

	/**
	 * 按照报名记录详情，批量录取<br>
	 * 录取专业为记录对应的专业。主要动作为<br>
	 * 1.设置详情中的录取状态。<br>
	 * 2.设置报名记录中的录取专业设置和录取志愿以及是否调剂录取。<br>
	 * 3.设置学生的双专业属性。<br>
	 * 
	 * @see #batchMatriculateSignUpStudent 如果学生录取的专业不是报名的专业，可以使用改方法进行录取，一般为调剂
	 * @param records
	 */
	public void batchMatriculateSignUpStudentRecords(SignUpSetting setting, List records);

	/**
	 * 按照报名记录，批量录取学生。<br>
	 * 其中录取的专业是报名记录设置好的。主要动作：<br>
	 * 1.设置报名记录的录取状态<br>
	 * 2.设置学生的双专业属性。<br>
	 * 
	 * @param records
	 */
	public void batchMatriculateSignUpStudent(List signUpStds);

}
