//$Id: CreditAwardCriteria.java,v 1.1 2006/08/02 00:52:51 duanth Exp $
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

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 奖励学分标准<br>
 * [floorAvgGrade,ceilAvgGrade)－>awardCredits
 * 
 * @author chaostone 2005-12-9
 */
public class CreditAwardCriteria extends LongIdObject {

	private static final long serialVersionUID = 8574528313999902227L;

	/** 平均绩点下限(包含) */
	private Float floorAvgGrade;

	/** 平均绩点上限(不包含) */
	private Float ceilAvgGrade;

	/** 奖励学分 */
	private Float awardCredits;

	/**
	 * @return Returns the awardCredits.
	 */
	public Float getAwardCredits() {
		return awardCredits;
	}

	/**
	 * @param awardCredits
	 *            The awardCredits to set.
	 */
	public void setAwardCredits(Float awardCredits) {
		this.awardCredits = awardCredits;
	}

	/**
	 * @return Returns the ceilAvgGrade.
	 */
	public Float getCeilAvgGrade() {
		return ceilAvgGrade;
	}

	/**
	 * @param ceilAvgGrade
	 *            The ceilAvgGrade to set.
	 */
	public void setCeilAvgGrade(Float ceilAvgGrade) {
		this.ceilAvgGrade = ceilAvgGrade;
	}

	/**
	 * @return Returns the floorAvgGrade.
	 */
	public Float getFloorAvgGrade() {
		return floorAvgGrade;
	}

	/**
	 * @param floorAvgGrade
	 *            The floorAvgGrade to set.
	 */
	public void setFloorAvgGrade(Float floorAvgGrade) {
		this.floorAvgGrade = floorAvgGrade;
	}
}
