//$Id: FineCourse.java,v 1.2 2006/12/27 07:16:08 duanth Exp $
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
 * hc             2005-11-19         Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.fineCourse;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.FineCourseLevel;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;

/**
 * 精品课程<br>
 * 精品课程中，采取课程名称而不是Course对象的原因是因为<br>
 * 学校实际运作中是很多相似的课程(多个课程代码)申报一个精品课程。
 * 
 * @author hc,chaostone
 * 
 */
public class FineCourse extends LongIdObject {

	private static final long serialVersionUID = 7727596256032516635L;

	/** 精品课程等级 */
	private FineCourseLevel level = new FineCourseLevel();

	/** 负责人 */
	private Set charges;

	/** 课程名称 */
	private String courseName;

	/** 所属部门 */
	private Department department = new Department();

	/** 批准年度 */
	private Integer passedYear;

	/** 备注 */
	private String remark;

	/**
	 * @return Returns the department.
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            The department to set.
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * @return Returns the memo.
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param memo
	 *            The memo to set.
	 */
	public void setRemark(String memo) {
		this.remark = memo;
	}

	public Integer getPassedYear() {
		return passedYear;
	}

	public void setPassedYear(Integer passYear) {
		this.passedYear = passYear;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public FineCourseLevel getLevel() {
		return level;
	}

	public void setLevel(FineCourseLevel level) {
		this.level = level;
	}

	public String getChargeNames() {
		if (null == getCharges() || getCharges().isEmpty())
			return "";
		StringBuffer names = new StringBuffer();
		for (Iterator iter = getCharges().iterator(); iter.hasNext();) {
			Teacher charge = (Teacher) iter.next();
			names.append(charge.getName()).append(" ");
		}
		names.deleteCharAt(names.length() - 1);
		return names.toString();
	}

	public String getChargeIds() {
		if (null == getCharges() || getCharges().isEmpty())
			return "";
		StringBuffer names = new StringBuffer(",");
		for (Iterator iter = getCharges().iterator(); iter.hasNext();) {
			Teacher charge = (Teacher) iter.next();
			names.append(charge.getId()).append(",");
		}
		names.deleteCharAt(names.length() - 1);
		return names.toString();
	}

	public void setChargeIds(String chargeIdSeq) {
		Set newCharges = new HashSet();
		setCharges(newCharges);
		if (StringUtils.isNotEmpty(chargeIdSeq)) {
			Long[] changeIds = SeqStringUtil.transformToLong(chargeIdSeq);
			for (int i = 0; i < changeIds.length; i++) {
				newCharges.add(new Teacher(changeIds[i]));
			}
		}
	}

	public Set getCharges() {
		return charges;
	}

	public void setCharges(Set charges) {
		this.charges = charges;
	}

}
