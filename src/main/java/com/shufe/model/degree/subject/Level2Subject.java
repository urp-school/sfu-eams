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
 * 塞外狂人             2006-8-7            Created
 *  
 ********************************************************************************/
package com.shufe.model.degree.subject;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.baseinfo.Speciality;

/**
 * 学科点信息表
 * 
 * @author 塞外狂人,chaostone,lzs
 * 
 */
public class Level2Subject extends LongIdObject implements Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9087872031226336761L;

	/**
	 * 该学科点对应的专业
	 */
	private Speciality speciality = new Speciality();

	/**
	 * 一级学科
	 */
	private Level1Subject level1Subject = new Level1Subject();

	/**
	 * 是否专业学位
	 */
	private Boolean isSpecial;

	/**
	 * 是否一级学科点内自主设置的专业
	 */
	private Boolean isReserved;

	/**
	 * 是否博士点
	 */
	private Boolean forDoctor;

	/**
	 * 是否硕士点
	 */
	private Boolean forMaster;

	/**
	 * 批准时间
	 */
	private Date ratifyTime;
	private Date dateForDoctor; // 博士点设置时间
	private Date dateForMaster;// 硕士点设置时间

	private Boolean isSelfForDoctor = Boolean.FALSE;// 是否自主设置的博士点
	private Boolean isSelfForMaster = Boolean.FALSE;// 是否支主设置的硕士点
	/**
	 * 类别说明表
	 */
	private Set types = new HashSet();

	public Boolean getForDoctor() {
		return forDoctor;
	}

	public void setForDoctor(Boolean isDoctor) {
		this.forDoctor = isDoctor;
	}

	public Boolean getIsReserved() {
		return isReserved;
	}

	public void setIsReserved(Boolean isFreedom) {
		this.isReserved = isFreedom;
	}

	public Boolean getForMaster() {
		return forMaster;
	}

	public void setForMaster(Boolean isMaster) {
		this.forMaster = isMaster;
	}

	public Date getRatifyTime() {
		return ratifyTime;
	}

	public void setRatifyTime(Date passTime) {
		this.ratifyTime = passTime;
	}

	public Set getTypes() {
		return types;
	}

	public void setTypes(Set typeInfoSet) {
		this.types = typeInfoSet;
	}

	public Level1Subject getLevel1Subject() {
		return level1Subject;
	}

	public void setLevel1Subject(Level1Subject firstSubject) {
		this.level1Subject = firstSubject;
	}

	public Boolean getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(Boolean isSpecialityDegree) {
		this.isSpecial = isSpecialityDegree;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		Level2Subject myClass = (Level2Subject) object;
		return new CompareToBuilder().append(this.level1Subject, myClass.level1Subject)
				.toComparison();
	}

	/**
	 * @return Returns the dateForDoctor.
	 */
	public Date getDateForDoctor() {
		return dateForDoctor;
	}

	/**
	 * @param dateForDoctor
	 *            The dateForDoctor to set.
	 */
	public void setDateForDoctor(Date dateForDoctor) {
		this.dateForDoctor = dateForDoctor;
	}

	/**
	 * @return Returns the dateForMaster.
	 */
	public Date getDateForMaster() {
		return dateForMaster;
	}

	/**
	 * @param dateForMaster
	 *            The dateForMaster to set.
	 */
	public void setDateForMaster(Date dateForMaster) {
		this.dateForMaster = dateForMaster;
	}

	/**
	 * @return Returns the isSelfForDoctor.
	 */
	public Boolean getIsSelfForDoctor() {
		return isSelfForDoctor;
	}

	/**
	 * @param isSelfForDoctor
	 *            The isSelfForDoctor to set.
	 */
	public void setIsSelfForDoctor(Boolean isSelfForDoctor) {
		this.isSelfForDoctor = isSelfForDoctor;
	}

	/**
	 * @return Returns the isSelfForMaster.
	 */
	public Boolean getIsSelfForMaster() {
		return isSelfForMaster;
	}

	/**
	 * @param isSelfForMaster
	 *            The isSelfForMaster to set.
	 */
	public void setIsSelfForMaster(Boolean isSelfForMaster) {
		this.isSelfForMaster = isSelfForMaster;
	}

}
