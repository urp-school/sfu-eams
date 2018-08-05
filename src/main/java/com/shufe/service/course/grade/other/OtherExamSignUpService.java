//$Id: OtherExamSignUpService.java,v 1.1 2007-2-27 下午07:35:54 chaostone Exp $
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
 *chaostone      2007-2-27         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.grade.other;

import java.util.Date;
import java.util.List;

import com.shufe.model.course.grade.other.OtherExamSignUp;
import com.shufe.model.course.grade.other.OtherExamSignUpSetting;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 其他考试报名服务接口
 * 
 * @author chaostone
 * 
 */
public interface OtherExamSignUpService {
	/**
	 * 没有完成要求的上级考试要求
	 */
	public static final String notPassSuperCategory = "error.otherExam.notPassSuperCategory";

	/**
	 * 重复报名
	 */
	public static final String existExamSignUp = "error.otherExam.existExamSignUp";

	/**
	 * 不再报名时间
	 */
	public static final String notInTime = "error.otherExam.notInTime";

	/**
	 * 通过的不能报名了
	 */
	public static final String hasPassed = "error.otherExam.isHasPassed";

	/**
	 * 报名(成功时，保存报名记录)<br>
	 * 1.检查报名时间是否合适<br>
	 * 2.检查如果在报名约束中说明应该完成的其他考试，则检查成绩。<br>
	 * 除非该学生的类别处在免考虑的学生类别范围内。<br>
	 * 3.不能重复报名<br>
	 * 4.在同一时间段内不能同时报两种以上(含)的考试类别
	 * --（5.不能报已经通过的科目,但是六级可以）
	 * @param std
	 * @param calendar
	 * @param setting
	 * @return 返回""如果成功,否则返回错误信息
	 */
	public String signUp(Student std, TeachCalendar calendar, OtherExamSignUpSetting setting);

	/**
	 * 取消报名<br>
	 * 1.检查报名时间是否合适
	 * 
	 * @param std
	 * @param setting
	 * @return
	 */
	public String cancelSignUp(Student std, OtherExamSignUpSetting setting);

	/**
	 * 查询在特定设置条件下的报名记录
	 * 
	 * @param std
	 * 
	 * @param setting
	 * @return
	 */
	public OtherExamSignUp getOtherExamSignUp(Student std, OtherExamSignUpSetting setting);

	/**
	 * 查询在一定时间段内的学生的报名记录
	 * 
	 * @param std
	 * @param start
	 * @param end
	 * @return
	 */
	public List getOtherExamSignUps(Student std, Date start, Date end);

	/**
	 * 返回现在开放，并且在时间内的设置
	 * 
	 * @return
	 */
	public List getOpenedSettings();

}
