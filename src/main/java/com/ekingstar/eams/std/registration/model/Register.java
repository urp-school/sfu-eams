//$Id: Registration.java,v 1.1 2007-1-30 下午08:47:46 chaostone Exp $
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
 *chaostone      2007-1-30         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.registration.model;

import java.sql.Timestamp;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.std.info.Student;
import com.ekingstar.eams.system.basecode.industry.RegisterState;
import com.ekingstar.eams.system.time.TeachCalendar;

/**
 * 学生注册信息
 * 
 * @author chaostone
 */
public class Register extends LongIdObject implements
		com.ekingstar.eams.std.registration.Register {
	private static final long serialVersionUID = -7131131119889421960L;

	/** 注册学年学期 */
	private TeachCalendar calendar;

	/** 注册学生 */
	private Student std;

	/** 注册时间 */
	private Timestamp registerAt;

	/** 注册状态 */
	private RegisterState state;

	/** 是否学费缴清 */
	private Boolean isTuitionFeeCompleted;

	/** 是否注册完成 */
	private Boolean isPassed;

	/** 备注 */
	private String remark;

	public Boolean getIsTuitionFeeCompleted() {
		return isTuitionFeeCompleted;
	}

	public void setIsTuitionFeeCompleted(Boolean isTuitionFeeCompleted) {
		this.isTuitionFeeCompleted = isTuitionFeeCompleted;
	}

	public RegisterState getState() {
		return state;
	}

	public void setState(RegisterState state) {
		this.state = state;
	}

	public Register() {
		super();
	}

	public Register(TeachCalendar calendar, Student std, Timestamp registerAt,
			String remark, RegisterState state, Boolean isPassed,
			Boolean isTuitionFeeCompleted) {
		super();
		this.calendar = calendar;
		this.std = std;
		this.registerAt = registerAt;
		this.remark = remark;
		this.isPassed = isPassed;
		this.state = state;
		this.isTuitionFeeCompleted = isTuitionFeeCompleted;
	}

	public Register(TeachCalendar calendar, Student std, Timestamp registerAt,
			String remark) {
		super();
		this.calendar = calendar;
		this.std = std;
		this.registerAt = registerAt;
		this.remark = remark;
		this.isPassed = Boolean.TRUE;
	}

	public Register(TeachCalendar calendar, Student std) {
		this(calendar, std, new Timestamp(System.currentTimeMillis()), "");
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public Boolean getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(Boolean status) {
		this.isPassed = status;
	}

	public Student getStd() {
		return std;
	}

	public void setStd(Student std) {
		this.std = std;
	}

	public Timestamp getRegisterAt() {
		return registerAt;
	}

	public void setRegisterAt(Timestamp registerAt) {
		this.registerAt = registerAt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
