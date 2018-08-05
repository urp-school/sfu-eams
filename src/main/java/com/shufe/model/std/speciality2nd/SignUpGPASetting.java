//$Id: GPARankSetting.java,v 1.1 2007-4-26 下午05:29:05 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-4-26         Created
 *  
 ********************************************************************************/

package com.shufe.model.std.speciality2nd;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 报名各相邻志愿之间的GPA级差。
 * 
 * @author chaostone
 * 
 */
public class SignUpGPASetting extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1524890400500463215L;

	/** 从第几志愿 */
	private Integer fromRank;

	/** 到第几志愿 */
	private Integer toRank;

	/** GPA级差 */
	private Float GPAGap;

	public Integer getFromRank() {
		return fromRank;
	}

	public void setFromRank(Integer fromGPA) {
		this.fromRank = fromGPA;
	}

	public Float getGPAGap() {
		return GPAGap;
	}

	public void setGPAGap(Float rankGap) {
		this.GPAGap = rankGap;
	}

	public Integer getToRank() {
		return toRank;
	}

	public void setToRank(Integer toGPA) {
		this.toRank = toGPA;
	}

}
