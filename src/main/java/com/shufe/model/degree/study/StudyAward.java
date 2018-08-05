//$Id: Award.java,v 1.1 2007-2-28 17:28:43 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-2-28         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.study;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.ResearchAwardType;
import com.shufe.model.std.Student;

/**
 * 科研成果获奖
 * @author cwx,chaostone
 *
 */
public class StudyAward extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -641062475188360388L;

	private static String SIMPLEDATEFORMATE = "yyyy-MM-dd";

	/** 学生 */
	private Student student = new Student();

	/** 获奖名称 */
	private String awardName;

	/** 获奖时间 */
	private Date awardedOn = new Date();

	/** 颁奖单位 */
	private String departmentName;

	/** 是否通过审核 */
	private Boolean isPassCheck = Boolean.FALSE;

	/** 备注 */
	private String remark;

	/** 科研获奖类别 */
	private ResearchAwardType type = new ResearchAwardType();

	public StudyProduct getStudyProduct() {
		throw new RuntimeException("abstract award.");
	}

	public void setStudyProduct(StudyProduct product) {
		throw new RuntimeException("abstract award.");
	}

	public static Class getStudyAwardType(String awardType) {
		if ("studyThesis".equals(awardType)) {
			return StudyThesisAward.class;
		} else if ("literature".equals(awardType)) {
			return LiteratureAward.class;
		} else if ("project".equals(awardType)) {
			return ProjectAward.class;
		} else if ("studyMeeting".equals(awardType)) {
			return StudyMeetingAward.class;
		} else {
			throw new RuntimeException("not supported StudyAward type:"
					+ awardType);
		}
	}

	public static StudyAward getStudyAward(String awardType) {
		try {
			return (StudyAward) getStudyAwardType(awardType).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("not supported StudyProduct type");
		}

	}

	/**
	 * @return Returns the awardName.
	 */
	public String getAwardName() {
		return awardName;
	}

	/**
	 * @param awardName
	 *            The awardName to set.
	 */
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	/**
	 * @return Returns the awardTime.
	 */
	public Date getAwardedOn() {
		return awardedOn;
	}

	/**
	 * @param awardTime
	 *            The awardTime to set.
	 */
	public void setAwardedOn(Date awardTime) {
		this.awardedOn = awardTime;
	}

	/**
	 * @return Returns the departmentName.
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName
	 *            The departmentName to set.
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return Returns the student.
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            The student to set.
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return Returns the researchAwardType.
	 */
	public ResearchAwardType getType() {
		return type;
	}

	/**
	 * @param researchAwardType
	 *            The researchAwardType to set.
	 */
	public void setType(ResearchAwardType researchAwardType) {
		this.type = researchAwardType;
	}

	public Boolean getIsPassCheck() {
		return isPassCheck;
	}

	public void setIsPassCheck(Boolean isPassCheck) {
		this.isPassCheck = isPassCheck;
	}

	public String getDateString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(SIMPLEDATEFORMATE);
		return dateFormat.format(date);
	}
}
