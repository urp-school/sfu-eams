//$Id: TeachModulus.java,v 1.1 2006/08/02 00:52:56 duanth Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-17         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.course;

import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.shufe.model.workload.Modulus;

public class TeachModulus extends Modulus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -350707564971431317L;
	private CourseCategory courseCategory = new CourseCategory();
	private Integer maxPeople; //人数上限
	private Integer minPeople; //人数下限
	
	/**
	 * @return Returns the maxPeople.
	 */
	public Integer getMaxPeople() {
		return maxPeople;
	}
	/**
	 * @param maxPeople The maxPeople to set.
	 */
	public void setMaxPeople(Integer maxPeople) {
		this.maxPeople = maxPeople;
	}

	/**
	 * @return Returns the minPeople.
	 */
	public Integer getMinPeople() {
		return minPeople;
	}
	/**
	 * @param minPeople The minPeople to set.
	 */
	public void setMinPeople(Integer minPeople) {
		this.minPeople = minPeople;
	}
	/**
	 * @return Returns the courseCategory.
	 */
	public CourseCategory getCourseCategory() {
		return courseCategory;
	}
	/**
	 * @param courseCategory The courseCategory to set.
	 */
	public void setCourseCategory(CourseCategory courseCategory) {
		this.courseCategory = courseCategory;
	}
}
