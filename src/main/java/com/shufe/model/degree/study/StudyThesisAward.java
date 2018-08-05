//$Id: StudyThesisAward.java,v 1.1 2007-3-6 16:06:41 Administrator Exp $
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
 * chenweixiong              2007-3-6         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.study;

import java.util.Iterator;

public class StudyThesisAward extends StudyAward {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2114197362983866592L;
	private StudyThesis studyThesis = new StudyThesis();

	public StudyProduct getStudyProduct() {
		return studyThesis;
	}

	public void setStudyProduct(StudyProduct product) {
		this.studyThesis = (StudyThesis) product;
	}

	/**
	 * @return Returns the studyThesis.
	 */
	public StudyThesis getStudyThesis() {
		return studyThesis;
	}

	/**
	 * @param studyThesis The studyThesis to set.
	 */
	public void setStudyThesis(StudyThesis studyThesis) {
		this.studyThesis = studyThesis;
	}
	
	/**
	 * 得到关于学生被收录的索引信息
	 * @return
	 */
	public String getThesisIndexInfo() {
		StringBuffer info = new StringBuffer();
		for (Iterator iter = studyThesis.getIndexes().iterator(); iter
				.hasNext();) {
			ThesisIndex element = (ThesisIndex) iter.next();
			info.append(element.getThesisIndexType().getName()).append(
					"收录,检索号:").append(element.getThesisIndexNo());
			if (iter.hasNext()) {
				info.append(",");
			}
		}
		return info.toString();
	}
}
