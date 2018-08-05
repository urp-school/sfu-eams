//$Id: TeachProduct.java,v 1.2 2006/12/19 10:08:45 duanth Exp $
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
 * chenweixiong              2005-11-10         Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.product;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.ProductionAwardLevel;
import com.ekingstar.eams.system.basecode.industry.ProductionAwardType;
import com.ekingstar.eams.system.basecode.industry.ProductionType;
import com.shufe.model.system.baseinfo.Department;

/**
 * 教师教学成果
 * 
 * @author hj,chaostone
 * 
 */
public class TeachProduct extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3413319326353199408L;

	/** 合作老师名称 */
	private String cooperateOfTeacher;

	/** 教师排名列表 */
	private Set teacherRank = new HashSet();

	/** 申请部门 */
	private Department department = new Department();

	/** 成果名称 */
	private String productName;

	/** 获奖名称 */
	private String awardName;

	/** 成果类型 */
	private ProductionType productionType = new ProductionType();

	/** 成果获奖类别 */
	private ProductionAwardType productionAwardType = new ProductionAwardType();

	/** 成果获奖等级 */
	private ProductionAwardLevel productionAwardLevel = new ProductionAwardLevel();

	/** 颁奖机构 */
	private String giveAwardPlace;

	/** 获奖时间 */
	private Date awardTime;

	/** 备注 */
	private String remark;

	/**
	 * @param rankOfTeachProduct
	 */
	public void addTeacherRank(RankOfTeachProduct rankOfTeachProduct) {
		rankOfTeachProduct.setTeachProduct(this);
		this.teacherRank.add(rankOfTeachProduct);

	}

	/**
	 * @return Returns the awardTime.
	 */
	public Date getAwardTime() {
		return awardTime;
	}

	/**
	 * @param awardTime
	 *            The awardTime to set.
	 */
	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}

	/**
	 * @return Returns the cooperateOfTeacher.
	 */
	public String getCooperateOfTeacher() {
		return cooperateOfTeacher;
	}

	/**
	 * @param cooperateOfTeacher
	 *            The cooperateOfTeacher to set.
	 */
	public void setCooperateOfTeacher(String cooperateOfTeacher) {
		this.cooperateOfTeacher = cooperateOfTeacher;
	}

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
	 * @return Returns the productName.
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            The productName to set.
	 */
	public void setProductName(String productName) {
		this.productName = productName;
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
	 * @return Returns the productionAwardLevel.
	 */
	public ProductionAwardLevel getProductionAwardLevel() {
		return productionAwardLevel;
	}

	/**
	 * @param productionAwardLevel
	 *            The productionAwardLevel to set.
	 */
	public void setProductionAwardLevel(
			ProductionAwardLevel productionAwardLevel) {
		this.productionAwardLevel = productionAwardLevel;
	}

	/**
	 * @return Returns the productionAwardType.
	 */
	public ProductionAwardType getProductionAwardType() {
		return productionAwardType;
	}

	/**
	 * @param productionAwardType
	 *            The productionAwardType to set.
	 */
	public void setProductionAwardType(ProductionAwardType productionAwardType) {
		this.productionAwardType = productionAwardType;
	}

	/**
	 * @return Returns the productionType.
	 */
	public ProductionType getProductionType() {
		return productionType;
	}

	/**
	 * @param productionType
	 *            The productionType to set.
	 */
	public void setProductionType(ProductionType productionType) {
		this.productionType = productionType;
	}

	/**
	 * @return Returns the giveAwardPlace.
	 */
	public String getGiveAwardPlace() {
		return giveAwardPlace;
	}

	/**
	 * @param giveAwardPlace
	 *            The giveAwardPlace to set.
	 */
	public void setGiveAwardPlace(String giveAwardPlace) {
		this.giveAwardPlace = giveAwardPlace;
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
	 * @return Returns the teacherRank.
	 */
	public Set getTeacherRank() {
		return teacherRank;
	}

	/**
	 * @param teacherRank
	 *            The teacherRank to set.
	 */
	public void setTeacherRank(Set teacherRank) {
		this.teacherRank = teacherRank;
	}

}
