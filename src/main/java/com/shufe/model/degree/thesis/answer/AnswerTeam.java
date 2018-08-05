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
 * 塞外狂人             2006-8-8            Created
 *  
 ********************************************************************************/
package com.shufe.model.degree.thesis.answer;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.state.TeacherTitle;

/**
 * 预答辩小组成员
 * 
 * @author 塞外狂人
 * 
 */
public class AnswerTeam extends LongIdObject {

	private static final long serialVersionUID = 2236971165391332229L;
	private String name;// 姓名
	private String specialityAspect;// 专业方向
	private TeacherTitle teacherTitle; // 教师职称
	private String depart;// 院系所（单位）
	private Boolean isChairMan; // 是否主席
	private String remark; // 备注

	private Set thesisAnswers = new HashSet();

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialityAspect() {
		return specialityAspect;
	}

	public void setSpecialityAspect(String specialityAspect) {
		this.specialityAspect = specialityAspect;
	}

	/**
	 * @return Returns the thesisAnswers.
	 */
	public Set getThesisAnswers() {
		return thesisAnswers;
	}

	/**
	 * @param thesisAnswers
	 *            The thesisAnswers to set.
	 */
	public void setThesisAnswers(Set thesisAnswers) {
		this.thesisAnswers = thesisAnswers;
	}

	/**
	 * @return Returns the isChairMan.
	 */
	public Boolean getIsChairMan() {
		return isChairMan;
	}

	/**
	 * @param isChairMan
	 *            The isChairMan to set.
	 */
	public void setIsChairMan(Boolean isChairMan) {
		this.isChairMan = isChairMan;
	}

	/**
	 * 检查对象是否为持久化对象.
	 * 
	 * @return
	 */
	public boolean checkedId() {
		boolean flag = false;
		if (null != this && null != this.getId()
				&& !new Long(0).equals(this.getId())) {
			flag = true;
		}
		return flag;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AnswerTeam)) {
			return false;
		}
		AnswerTeam rhs = (AnswerTeam) object;
		return new EqualsBuilder().append(this.specialityAspect,
				rhs.specialityAspect).append(this.depart, rhs.depart).append(
				this.name, rhs.name).isEquals();
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
	 * @return Returns the teacherTitle.
	 */
	public TeacherTitle getTeacherTitle() {
		return teacherTitle;
	}

	/**
	 * @param teacherTitle
	 *            The teacherTitle to set.
	 */
	public void setTeacherTitle(TeacherTitle teacherTitle) {
		this.teacherTitle = teacherTitle;
	}
}
