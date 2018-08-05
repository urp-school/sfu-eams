//$Id: Level1Subject.java,v 1.5 2006/08/15 12:21:50 lzs Exp $
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
 * hc             2005-11-22         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.subject;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.state.SubjectCategory;

/**
 * 一级学科
 * 
 * @author hc
 * 
 */
public class Level1Subject extends LongIdObject implements Comparable {

	private static final long serialVersionUID = 1507895063544667780L;

	/**
	 * 学科门类代码
	 */
	private String code;

	/**
	 * 学科门类
	 */
	private SubjectCategory category = new SubjectCategory();

	/**
	 * 一级学科中文名
	 */
	private String name;

	/**
	 * 一级学科英文名
	 */
	private String engName;

	/**
	 * 是否博士学位授予权一级学科点
	 */
	private Boolean forDoctor;

	/**
	 * 是否硕士学位授予权一级学科点
	 */
	private Boolean forMaster;

	/**
	 * 批准时间
	 */
	private Date ratifyTime;

	private Set level2Subjects = new HashSet(0);// 二级学科

	public SubjectCategory getCategory() {
		return category;
	}

	public void setCategory(SubjectCategory category) {
		this.category = category;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set getLevel2Subjects() {
		return level2Subjects;
	}

	/**
	 * @return Returns the forDoctor.
	 */
	public Boolean getForDoctor() {
		return forDoctor;
	}

	/**
	 * @param forDoctor
	 *            The forDoctor to set.
	 */
	public void setForDoctor(Boolean doctorFirst) {
		this.forDoctor = doctorFirst;
	}

	public Date getRatifyTime() {
		return ratifyTime;
	}

	public void setRatifyTime(Date passTime) {
		this.ratifyTime = passTime;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the engName.
	 */
	public String getEngName() {
		return engName;
	}

	/**
	 * @param engName
	 *            The engName to set.
	 */
	public void setEngName(String nameEn) {
		this.engName = nameEn;
	}

	public void setLevel2Subjects(Set specialitySet) {
		this.level2Subjects = specialitySet;
	}

	public Boolean getForMaster() {
		return forMaster;
	}

	public void setForMaster(Boolean forMaster) {
		this.forMaster = forMaster;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		Level1Subject myClass = (Level1Subject) object;
		return new CompareToBuilder().append(this.getCategory(),
				myClass.getCategory()).append(this.name, myClass.name)
				.toComparison();
	}

}
