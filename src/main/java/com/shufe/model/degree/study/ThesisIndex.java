//$Id: LiteratureIndex.java,v 1.1 2007-4-4 下午04:16:34 chaostone Exp $
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
 *chaostone      2007-4-4         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.study;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.ThesisIndexType;

public class ThesisIndex extends LongIdObject implements Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3352350462312888584L;
	private ThesisIndexType thesisIndexType = new ThesisIndexType();
	private String thesisIndexNo;

	public String getThesisIndexNo() {
		return thesisIndexNo;
	}

	public void setThesisIndexNo(String thesisIndexNo) {
		this.thesisIndexNo = thesisIndexNo;
	}

	public ThesisIndexType getThesisIndexType() {
		return thesisIndexType;
	}

	public void setThesisIndexType(ThesisIndexType thesisIndexType) {
		this.thesisIndexType = thesisIndexType;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		ThesisIndex myClass = (ThesisIndex) object;
		return new CompareToBuilder().append(this.thesisIndexType.getCode(),
				myClass.thesisIndexType.getCode()).append(this.thesisIndexNo,
				myClass.thesisIndexNo).toComparison();
	}
}
