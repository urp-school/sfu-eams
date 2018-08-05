//$Id: Thesis.java,v 1.2 2006/11/09 10:57:01 cwx Exp $
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
 *  
 ********************************************************************************/

package com.shufe.model.degree.study;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ekingstar.eams.system.basecode.industry.PublicationLevel;

/**
 * 科研成果--论文
 * 
 * @param cwx
 *            2007-3-2 修改
 * 
 */
public class StudyThesis extends StudyProduct implements Serializable {

	private static final long serialVersionUID = -6473350779050098702L;

	/** 刊物名称 */
	private String publicationName;

	/** 刊物级别 */
	private PublicationLevel publicationLevel = new PublicationLevel();

	/** 期刊号 */
	private String publicationNo;

	/** 发表时间 */
	private Date publishOn;

	/** 论文总字数 */
	private Float totalCount;

	/** 论文索引类别 */
	private Set indexes = new HashSet();

	public void addAward(StudyThesisAward studyThesisAward) {
		studyThesisAward.setStudyThesis(this);
		this.getAwards().add(studyThesisAward);
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
	 * @return Returns the publicationNo.
	 */
	public String getPublicationNo() {
		return publicationNo;
	}

	/**
	 * @param publicationNo
	 *            The publicationNo to set.
	 */
	public void setPublicationNo(String publicationNo) {
		this.publicationNo = publicationNo;
	}

	/**
	 * @return Returns the publicationLevel.
	 */
	public PublicationLevel getPublicationLevel() {
		return publicationLevel;
	}

	/**
	 * @param publicationLevel
	 *            The publicationLevel to set.
	 */
	public void setPublicationLevel(PublicationLevel publicationLevel) {
		this.publicationLevel = publicationLevel;
	}

	/**
	 * @return Returns the publicName.
	 */
	public String getPublicationName() {
		return publicationName;
	}

	/**
	 * @param publicName
	 *            The publicName to set.
	 */
	public void setPublicationName(String publicName) {
		this.publicationName = publicName;
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
	 * @return Returns the thesisIndexSet.
	 */
	public Set getIndexes() {
		return indexes;
	}

	/**
	 * @param thesisIndexSet
	 *            The thesisIndexSet to set.
	 */
	public void setIndexes(Set thesisIndexSet) {
		this.indexes = thesisIndexSet;
	}

	public String getIndexNo(Long indexId) {
		String indexNo = "";
		for (Iterator iter = this.getIndexes().iterator(); iter.hasNext();) {
			ThesisIndex thesisIndex = (ThesisIndex) iter.next();
			if (indexId.equals(thesisIndex.getThesisIndexType().getId())) {
				indexNo = thesisIndex.getThesisIndexNo();
				break;
			}
		}
		return indexNo;
	}

	/**
	 * 得到论文索引的详细信息
	 * 
	 * @return
	 */
	public String getThesisIndexInfo() {
		String info = "";
		List tempList = new ArrayList();
		tempList.addAll(this.getIndexes());
		Collections.sort(tempList);
		for (Iterator iter = tempList.iterator(); iter.hasNext();) {
			ThesisIndex thesisIndex = (ThesisIndex) iter.next();
			info += thesisIndex.getThesisIndexType().getName() + ":"
					+ thesisIndex.getThesisIndexNo() + ";";
		}
		return info;
	}
}
