//$Id: CourseTableConfirm.java,v 1.1 2007-3-20 下午08:17:29 chaostone Exp $
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
 *chaostone      2007-3-20         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.arrange.task;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 学生课表核对记录
 * 
 * @author chaostone
 *
 */
public class CourseTableCheck extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8117374983277305725L;

	private Student std;

	private TeachCalendar calendar;

	/**
	 * 该学期的课程总数
	 */
	private Integer courseNum;

	/**
	 * 该学期的学分总数
	 */
	private Float credits;

	/**
	 * 是否确认
	 */
	private Boolean isConfirm = Boolean.TRUE;

	/**
	 * 填写备注
	 */
	private String remark;

	/**
	 * 核对确认时间
	 */
	private Date confirmAt;

	public CourseTableCheck() {
		super();
	}

	public CourseTableCheck(Student std, TeachCalendar calendar) {
		super();
		this.std = std;
		this.calendar = calendar;
		this.isConfirm = Boolean.FALSE;
		this.courseNum = new Integer(0);
		this.credits = new Float(0);
	}

	/**
	 * 比对教学任务和已有的课程数以及学分是否吻合，如果不吻合则更新。
	 * 并且返回true
	 * @param tasks
	 * @return
	 */
	public boolean updateCredit(List tasks) {
		boolean updated = false;
		int newCourseNum = 0;
		float newCredits = 0;
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			TeachTask element = (TeachTask) iter.next();
			newCourseNum++;
//			newCredits += element.getCredit().floatValue();
		}
		if (getCourseNum().intValue() != newCourseNum) {
			setCourseNum(new Integer(newCourseNum));
			updated = true;
		}
		if (getCredits().floatValue() != newCredits) {
			setCredits(new Float(newCredits));
			updated = true;
		}
		return updated;
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public Integer getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(Integer courseNum) {
		this.courseNum = courseNum;
	}

	public Float getCredits() {
		return credits;
	}

	public void setCredits(Float credits) {
		this.credits = credits;
	}

	public Student getStd() {
		return std;
	}

	public void setStd(Student std) {
		this.std = std;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(Boolean isConfirm) {
		this.isConfirm = isConfirm;
	}

	public Date getConfirmAt() {
		return confirmAt;
	}

	public void setConfirmAt(Date confirmAt) {
		this.confirmAt = confirmAt;
	}

}
