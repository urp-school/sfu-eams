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
package com.shufe.dao;

public class ModelPropertyNotNullException extends RuntimeException {
	private static final long serialVersionUID = -6591456789201256225L;

	/**
	 * 非空属性为空的异常
	 * 
	 */
	public ModelPropertyNotNullException() {
		super();
	}

	/**
	 * @param message
	 */
	public ModelPropertyNotNullException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ModelPropertyNotNullException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public ModelPropertyNotNullException(Throwable cause) {
		super(cause);
	}

}
