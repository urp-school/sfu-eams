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
 * chaostone             2006-6-27            Created
 *  
 ********************************************************************************/
package com.ekingstar.eams.course.election;

import java.util.HashSet;
import java.util.Set;

/**
 * 简要的学生信息
 * 
 * @author chaostone
 * 
 */
public class SimpleStdInfo {

	Long id;

	/** 学号 */
	String stdNo;

	/** 入学年份 */
	String enrollTurn;

	/** 学生类别id */
	Long stdTypeId;

	/** 院系id */
	Long departId;

	/** 专业id */
	Long specialityId;

	/** 专业方向id */
	Long aspectId;

	/** 班级ids */
	Set adminClassIds = new HashSet();

	/** HSK等级值 */
	Integer HSKDegree;

	/** 语言等级值 */
	Integer languageAbility;

	/**校区ID*/
	Long schoolDistrictId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set getAdminClassIds() {
		return adminClassIds;
	}

	public Long getStdTypeId() {
		return stdTypeId;
	}

	public void setStdTypeId(Long stdTypeId) {
		this.stdTypeId = stdTypeId;
	}

	public void setAdminClassIds(Set adminClassIds) {
		this.adminClassIds = adminClassIds;
	}

	public Long getAspectId() {
		return aspectId;
	}

	public void setAspectId(Long aspectId) {
		this.aspectId = aspectId;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public String getEnrollTurn() {
		return enrollTurn;
	}

	public void setEnrollTurn(String enrollTurn) {
		this.enrollTurn = enrollTurn;
	}

	public Long getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(Long specialityId) {
		this.specialityId = specialityId;
	}

	public String getStdNo() {
		return stdNo;
	}

	public void setStdNo(String stdNo) {
		this.stdNo = stdNo;
	}

	public Integer getHSKDegree() {
		return HSKDegree;
	}

	public void setHSKDegree(Integer degree) {
		HSKDegree = degree;
	}

	public Integer getLanguageAbility() {
		return languageAbility;
	}

	public void setLanguageAbility(Integer languageAbility) {
		this.languageAbility = languageAbility;
	}

	public Long getSchoolDistrictId() {
		return schoolDistrictId;
	}

	public void setSchoolDistrictId(Long schoolDistrictId) {
		this.schoolDistrictId = schoolDistrictId;
	}

}
