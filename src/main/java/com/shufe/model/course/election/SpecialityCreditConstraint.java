//$Id: SpecialityCreditConstraint.java,v 1.1 2006/08/02 00:52:51 duanth Exp $
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
 * chaostone             2005-12-9         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.election;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;

/**
 * 专业学分上限
 * 
 * @author chaostone 2005-12-9
 */
public class SpecialityCreditConstraint extends CreditConstraint {

	private static final long serialVersionUID = -2910667724760412427L;

	/** 所在年级 */
	private String enrollTurn;

	/** 学生类别 */
	private StudentType stdType;

	/** 院系 */
	private Department depart = new Department();

	/** 专业 */
	private Speciality speciality = new Speciality();

	/** 专业方向 */
	private SpecialityAspect aspect = new SpecialityAspect();

	/**
	 * @return Returns the enrollTurn.
	 */
	public String getEnrollTurn() {
		return enrollTurn;
	}

	/**
	 * @param enrollTurn
	 *            The enrollTurn to set.
	 */
	public void setEnrollTurn(String enrollTurn) {
		this.enrollTurn = enrollTurn;
	}

	/**
	 * @return Returns the depart.
	 */
	public Department getDepart() {
		return depart;
	}

	/**
	 * @param depart
	 *            The depart to set.
	 */
	public void setDepart(Department depart) {
		this.depart = depart;
	}

	/**
	 * @return Returns the aspect.
	 */
	public SpecialityAspect getAspect() {
		return aspect;
	}

	/**
	 * @param aspect
	 *            The aspect to set.
	 */
	public void setAspect(SpecialityAspect aspect) {
		this.aspect = aspect;
	}

	/**
	 * @return Returns the speciality.
	 */
	public Speciality getSpeciality() {
		return speciality;
	}

	/**
	 * @param speciality
	 *            The speciality to set.
	 */
	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public StudentType getStdType() {
		return stdType;
	}

	public void setStdType(StudentType stdType) {
		this.stdType = stdType;
	}

}
