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

/**
 * 
 * @author Administrator
 * 
 */
public interface CSVTransferListener {
	public String parse(String values);

	public void startTransfer();

	public void tranferField(String field,String value)throws Exception;
	
	public void endTransfer();

	public void addFailure(int lineNumber, String messages, String values);

	public void setCSVDataTransfer(CSVTransfer CSVDataTransfer);
}
