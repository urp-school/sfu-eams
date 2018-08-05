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

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class CSVTransfer {

	Class clazz = null;

	String[] keys = null;

	List listeners = new ArrayList();

	LineNumberReader reader;

	Object cur = null;

	String curData = null;

	int lineNumber = 0;

	public CSVTransfer(String className, String keySeq, LineNumberReader reader) {
		try {
			clazz = Class.forName(className);
		} catch (Exception e) {
			throw new RuntimeException("unfined class:" + className);
		}
		this.reader = reader;
		keys = StringUtils.split(keySeq, ",");
	}

	public void addLinstener(CSVTransferListener listener) {
		listener.setCSVDataTransfer(this);
		listeners.add(listener);
	}

	public void transfer() {
		do {

			try {
				curData = reader.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
				break;
			}
			
			if (null == curData)
				break;
			
			String[] propertyValues = StringUtils.splitPreserveAllTokens(curData,",");
			if (propertyValues.length != keys.length) {
				reportFailure(lineNumber, 
						"value's count not equal cloum's count", curData);
			}			
			
			if(lineNumber==0){
//				if(!propertyValues.equals(keys)){
//					reportFailure(lineNumber, 
//							"!propertyValues.equals(keys)", curData);
//				}
				lineNumber++;
				continue;
			}			

			for (Iterator iter = listeners.iterator(); iter.hasNext();) {
				CSVTransferListener listener = (CSVTransferListener) iter
						.next();
				listener.startTransfer();
			} 
			
			try {
				cur = clazz.newInstance();
				for (int i = 0; i < propertyValues.length; i++) {
					for (Iterator iter = listeners.iterator(); iter.hasNext();) {
						CSVTransferListener listener = (CSVTransferListener) iter
								.next();
						listener.tranferField(keys[i], propertyValues[i]);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				reportFailure(lineNumber, e.getMessage(), curData);
			}
			try {
				for (Iterator iter = listeners.iterator(); iter.hasNext();) {
					CSVTransferListener listener = (CSVTransferListener) iter
							.next();
					listener.endTransfer();
				}
			} catch (Exception e) {
				e.printStackTrace();
				reportFailure(lineNumber, e.getMessage(), curData);
			}
			lineNumber++;
			
		} while (true);		
		
	}

	private void reportFailure(int lineNumber, String message, String values) {
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			CSVTransferListener listener = (CSVTransferListener) iter.next();
			listener.addFailure(lineNumber, message, values);
		}
	}

	public Object getCurTransfered() {
		return cur;
	}

	/**
	 * @param reader
	 *            要设置的 reader.
	 */
	public void setReader(LineNumberReader reader) {
		this.reader = reader;
	}

	/**
	 * @return 返回 lineNumber.
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * @param lineNumber
	 *            要设置的 lineNumber.
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return 返回 curData.
	 */
	public String getCurData() {
		return curData;
	}
}
