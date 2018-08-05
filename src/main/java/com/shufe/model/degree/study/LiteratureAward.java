//$Id: LiteratureAward.java,v 1.1 2007-3-6 16:06:03 Administrator Exp $
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

/**
 * 著作获奖
 * 
 * @author chaostone
 * 
 */
public class LiteratureAward extends StudyAward {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4394467058417411156L;
	private Literature literature = new Literature();

	public StudyProduct getStudyProduct() {
		return literature;
	}

	public void setStudyProduct(StudyProduct product) {
		this.literature = (Literature) product;
	}

	/**
	 * @return Returns the literature.
	 */
	public Literature getLiterature() {
		return literature;
	}

	/**
	 * @param literature
	 *            The literature to set.
	 */
	public void setLiterature(Literature literature) {
		this.literature = literature;
	}

}
