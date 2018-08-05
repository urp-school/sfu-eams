//$Id: ThesisNumStandard.java,v 1.1 2007-2-12 12:02:58 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-2-12         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.apply;

import com.ekingstar.commons.model.Component;

/**
 * 论文发表数量
 * 
 * @author chaostone
 * 
 */
public class ThesisNumStandard implements Component {
	private Integer inSideMagzas; // 国内杂志
	private Integer outSideMagzas; // 国外杂志
	private Integer inSideMeeting; // 国内会议
	private Integer outSideMeeting; // 国外会议
	private Integer scicount; // SCI收录数
	private Integer eicount; // EI收录数
	private Integer istpcount; // ISTP收录数
	private Integer nationNO1count; // 国家级一等奖
	private Integer nationNO2count; // 国家级二等奖
	private Integer nationNO3count; // 国家级三等奖
	private Integer nationNO4count; // 国家级四等奖
	private Integer provinceNo1count; // 省级一等奖
	private Integer provinceNo2count; // 省级二等奖
	private Integer provinceNo3count; // 省级三等奖

	/**
	 * @return Returns the eicount.
	 */
	public Integer getEicount() {
		return eicount;
	}

	/**
	 * @param eicount
	 *            The eicount to set.
	 */
	public void setEicount(Integer eicount) {
		this.eicount = eicount;
	}

	/**
	 * @return Returns the inSideMagzas.
	 */
	public Integer getInSideMagzas() {
		return inSideMagzas;
	}

	/**
	 * @param inSideMagzas
	 *            The inSideMagzas to set.
	 */
	public void setInSideMagzas(Integer inSideMagzas) {
		this.inSideMagzas = inSideMagzas;
	}

	/**
	 * @return Returns the inSideMeeting.
	 */
	public Integer getInSideMeeting() {
		return inSideMeeting;
	}

	/**
	 * @param inSideMeeting
	 *            The inSideMeeting to set.
	 */
	public void setInSideMeeting(Integer inSideMeeting) {
		this.inSideMeeting = inSideMeeting;
	}

	/**
	 * @return Returns the istpcount.
	 */
	public Integer getIstpcount() {
		return istpcount;
	}

	/**
	 * @param istpcount
	 *            The istpcount to set.
	 */
	public void setIstpcount(Integer istpcount) {
		this.istpcount = istpcount;
	}

	/**
	 * @return Returns the nationNO1count.
	 */
	public Integer getNationNO1count() {
		return nationNO1count;
	}

	/**
	 * @param nationNO1count
	 *            The nationNO1count to set.
	 */
	public void setNationNO1count(Integer nationNO1count) {
		this.nationNO1count = nationNO1count;
	}

	/**
	 * @return Returns the nationNO2count.
	 */
	public Integer getNationNO2count() {
		return nationNO2count;
	}

	/**
	 * @param nationNO2count
	 *            The nationNO2count to set.
	 */
	public void setNationNO2count(Integer nationNO2count) {
		this.nationNO2count = nationNO2count;
	}

	/**
	 * @return Returns the nationNO3count.
	 */
	public Integer getNationNO3count() {
		return nationNO3count;
	}

	/**
	 * @param nationNO3count
	 *            The nationNO3count to set.
	 */
	public void setNationNO3count(Integer nationNO3count) {
		this.nationNO3count = nationNO3count;
	}

	/**
	 * @return Returns the nationNO4count.
	 */
	public Integer getNationNO4count() {
		return nationNO4count;
	}

	/**
	 * @param nationNO4count
	 *            The nationNO4count to set.
	 */
	public void setNationNO4count(Integer nationNO4count) {
		this.nationNO4count = nationNO4count;
	}

	/**
	 * @return Returns the outSideMagzas.
	 */
	public Integer getOutSideMagzas() {
		return outSideMagzas;
	}

	/**
	 * @param outSideMagzas
	 *            The outSideMagzas to set.
	 */
	public void setOutSideMagzas(Integer outSideMagzas) {
		this.outSideMagzas = outSideMagzas;
	}

	/**
	 * @return Returns the outSideMeeting.
	 */
	public Integer getOutSideMeeting() {
		return outSideMeeting;
	}

	/**
	 * @param outSideMeeting
	 *            The outSideMeeting to set.
	 */
	public void setOutSideMeeting(Integer outSideMeeting) {
		this.outSideMeeting = outSideMeeting;
	}

	/**
	 * @return Returns the provinceNo1count.
	 */
	public Integer getProvinceNo1count() {
		return provinceNo1count;
	}

	/**
	 * @param provinceNo1count
	 *            The provinceNo1count to set.
	 */
	public void setProvinceNo1count(Integer provinceNo1count) {
		this.provinceNo1count = provinceNo1count;
	}

	/**
	 * @return Returns the provinceNo2count.
	 */
	public Integer getProvinceNo2count() {
		return provinceNo2count;
	}

	/**
	 * @param provinceNo2count
	 *            The provinceNo2count to set.
	 */
	public void setProvinceNo2count(Integer provinceNo2count) {
		this.provinceNo2count = provinceNo2count;
	}

	/**
	 * @return Returns the provinceNo3count.
	 */
	public Integer getProvinceNo3count() {
		return provinceNo3count;
	}

	/**
	 * @param provinceNo3count
	 *            The provinceNo3count to set.
	 */
	public void setProvinceNo3count(Integer provinceNo3count) {
		this.provinceNo3count = provinceNo3count;
	}

	/**
	 * @return Returns the scicount.
	 */
	public Integer getScicount() {
		return scicount;
	}

	/**
	 * @param scicount
	 *            The scicount to set.
	 */
	public void setScicount(Integer scicount) {
		this.scicount = scicount;
	}

}
