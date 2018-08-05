//$Id: DefaultCategoryProducer.java,v 1.1 2006/09/04 03:48:45 cwx Exp $
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
 * chenweixiong              2006-8-30         Created
 *  
 ********************************************************************************/

package com.shufe.service.util.stat;

import java.util.Date;
import java.util.Map;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

public class DefaultCategoryProducer implements DatasetProducer {

	/**
	 * <code>serialVersionUID<code>
	 *serialVersionUID long
	 */
	private static final long serialVersionUID = 1L;

	private String producerId;

	private Dataset dataSet;

	private String seriesNames[];

	public String getProducerId() {
		return producerId;
	}

	public boolean hasExpired(Map params, Date since) {
		return true;
	}

	public Object produceDataset(Map params) throws DatasetProduceException {
		return dataSet;
	}

	public DefaultCategoryProducer() {
		super();
	}

	public DefaultCategoryProducer(String producerId, Dataset dataSet,
			String[] seriesNames) {
		this.producerId = producerId;
		this.dataSet = dataSet;
		this.seriesNames = seriesNames;
	}

	public String generateLink(Object data, int series, Object category) {
		return seriesNames[series];
	}

	public String generateToolTip(CategoryDataset arg0, int series, int arg2) {
		return seriesNames[series];
	}
	
	protected void finalize() throws Throwable {
		super.finalize();
	}

}
