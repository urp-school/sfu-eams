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
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-12-18            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange.exam;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;

/**
 * 监考信息
 * 
 * @author chaostone
 * 
 */
public class ExamMonitor extends LongIdObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7734720828900446321L;

	/** 排考活动 */
	private ExamActivity examActivity;

	/** 监考老师 */
	protected Teacher invigilator = new Teacher();

	/** 自定主考姓名 */
	private String examinerName;

	/** 自定监考姓名 */
	private String invigilatorName;

	/** 监考院系 */
	protected Department depart = new Department();

	public Department getDepart() {
		return depart;
	}

	public void setDepart(Department depart) {
		this.depart = depart;
	}

	public ExamActivity getExamActivity() {
		return examActivity;
	}

	public void setExamActivity(ExamActivity examActivity) {
		this.examActivity = examActivity;
	}

	public Teacher getExaminer() {
		if (null != getExamActivity()) {
			return getExamActivity().getTeacher();
		} else
			return null;
	}

	/**
	 * 设置主考老师
	 * 
	 * @param examiner
	 */
	public void setExaminer(Teacher examiner) {
		if (null != getExamActivity()) {
			getExamActivity().setTeacher(examiner);
		}
	}

	public String getExaminerName() {
		return examinerName;
	}

	public String getExaminerNames() {
		String names = "";
		if (null != getExaminer()) {
			names += getExaminer().getName();
		}
		if (StringUtils.isNotEmpty(getExaminerName())) {
			if (names.length() != 0) {
				names += " ";
			}
			names += getExaminerName();
		}
		return names;
	}

	public void setExaminerName(String examinerNames) {
		this.examinerName = examinerNames;
	}

	public Teacher getInvigilator() {
		return invigilator;
	}

	public void setInvigilator(Teacher invigilator) {
		this.invigilator = invigilator;
	}

	public String getInvigilatorName() {
		return invigilatorName;
	}

	public String getInvigilatorNames() {
		String names = "";
		if (null != getInvigilator()) {
			names += getInvigilator().getName();
		}
		if (StringUtils.isNotEmpty(getInvigilatorName())) {
			if (names.length() != 0) {
				names += " ";
			}
			names += getInvigilatorName();
		}
		return names;
	}

	public void setInvigilatorName(String invigilatorNames) {
		this.invigilatorName = invigilatorNames;
	}

	/**
	 * 是否监考信息相同
	 * 
	 * @param other
	 * @return
	 */
	public boolean isSameMonitor(ExamMonitor other) {
		boolean sameTeacher = ObjectUtils.equals(getExaminer(), other
				.getExaminer());
		sameTeacher &= ObjectUtils.equals(getInvigilator(), other
				.getInvigilator());
		sameTeacher &= ObjectUtils.equals(getExaminerName(), other
				.getExaminerName());
		sameTeacher &= ObjectUtils.equals(getInvigilatorName(), other
				.getInvigilatorName());

		return sameTeacher;
	}

	/**
	 * 把所有的信息克隆一遍
	 */
	public Object clone() {
		ExamMonitor monitor = new ExamMonitor();
		monitor.setDepart(getDepart());
		monitor.setInvigilator(getInvigilator());
		monitor.setExaminerName(getExaminerName());
		monitor.setInvigilatorName(getInvigilatorName());
		return monitor;
	}
}
