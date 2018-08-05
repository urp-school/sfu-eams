//$Id: Literature.java,v 1.1 2006/08/02 00:53:10 duanth Exp $
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
 *  
 ********************************************************************************/

package com.shufe.model.degree.study;

import java.sql.Date;

import com.ekingstar.eams.system.basecode.industry.LiteratureType;

/**
 * 科研成果--著作
 * 
 * @param cwx
 *            2007-3-2 修改
 * 
 */
public class Literature extends StudyProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5044092323413855632L;

	/** 出版社 */
	private String publishCompany;
	
	/** 出版时间 */
	private Date publishOn;
	
	/** 著作类别 */
	private LiteratureType literatureType = new LiteratureType();
	
	/** 总字数 */
	private Float totalCount;

	public void addAward(LiteratureAward literatureAward) {
		literatureAward.setLiterature(this);
		this.getAwards().add(literatureAward);
	}

	/**
	 * @return Returns the publishCompany.
	 */
	public String getPublishCompany() {
		return publishCompany;
	}

	/**
	 * @param publishCompany
	 *            The publishCompany to set.
	 */
	public void setPublishCompany(String publishCompany) {
		this.publishCompany = publishCompany;
	}

	/**
	 * @return Returns the publishDate.
	 */
	public Date getPublishOn() {
		return publishOn;
	}

	/**
	 * @param publishDate
	 *            The publishDate to set.
	 */
	public void setPublishOn(Date publishDate) {
		this.publishOn = publishDate;
	}

	/**
	 * @return Returns the totalCount.
	 */
	public Float getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            The totalCount to set.
	 */
	public void setTotalCount(Float totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return Returns the literatureType.
	 */
	public LiteratureType getLiteratureType() {
		return literatureType;
	}

	/**
	 * @param literatureType
	 *            The literatureType to set.
	 */
	public void setLiteratureType(LiteratureType literatureType) {
		this.literatureType = literatureType;
	}

}
