//$Id: Tutor.java,v 1.2 2006/08/09 02:25:42 hc Exp $
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
 * chaostone             2005-10-17         Created
 *  
 ********************************************************************************/

package com.shufe.model.system.baseinfo;

import java.sql.Date;

import com.ekingstar.eams.system.basecode.industry.TutorType;

/**
 * 导师信息
 * 
 * @author chaostone
 * 
 */
public class Tutor extends Teacher {

	private static final long serialVersionUID = 4899261255972413506L;

	/** 导师类别 */
	private TutorType tutorType = new TutorType();

	/** 研导任职年月 */
	private Date dateOfTutor;

	/** 研导专业 */
	private Speciality speciality = new Speciality();//

	/** 研导方向 */
	private SpecialityAspect aspect = new SpecialityAspect();//

	public SpecialityAspect getAspect() {
		return aspect;
	}

	public void setAspect(SpecialityAspect aspect) {
		this.aspect = aspect;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public Date getDateOfTutor() {
		return dateOfTutor;
	}
	
	public void setDateOfTutor(Date dateOfTutor) {
		this.dateOfTutor = dateOfTutor;
	}

	public TutorType getTutorType() {
		return tutorType;
	}

	public void setTutorType(TutorType tutorType) {
		this.tutorType = tutorType;
	}
}
