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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.service.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class TransferConvertListener implements CSVTransferListener {
	protected CSVTransfer CSVDataTransfer;

	public TransferConvertListener() {
		super();
		// TODO 自动生成构造函数存根
	}

	public String parse(String values) {
		// TODO 自动生成方法存根
		return null;
	}

	public void startTransfer() {
		// TODO 自动生成方法存根

	}

	public void tranferField(String field, String value) throws Exception{
		Class type = PropertyUtils.getPropertyType(CSVDataTransfer
				.getCurTransfered(), field);
		PropertyUtils.setProperty(CSVDataTransfer.getCurTransfered(), field,
				ConvertUtils.convert(value, type));
	}

	public void endTransfer() {
		// TODO 自动生成方法存根

	}

	public void addFailure(int lineNumber, String messages, String values) {
		// TODO 自动生成方法存根

	}

	public void setCSVDataTransfer(CSVTransfer CSVDataTransfer) {
		this.CSVDataTransfer = CSVDataTransfer;
	}

	/**
	 * @return 返回 cSVDataTransfer.
	 */
	public CSVTransfer getCSVDataTransfer() {
		return CSVDataTransfer;
	}

}
