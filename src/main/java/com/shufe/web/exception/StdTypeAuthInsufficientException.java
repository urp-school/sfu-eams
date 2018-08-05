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
 * chaostone             2006-1-9            Created
 *  
 ********************************************************************************/
package com.shufe.web.exception;

import com.ekingstar.security.User;

/**
 * 学生类别权限不足异常.
 * 
 * @author chaostone
 * 
 */
public class StdTypeAuthInsufficientException extends RuntimeException {

	private static final long serialVersionUID = 7356207753232573651L;

	public StdTypeAuthInsufficientException(User user, String module) {
		super("StdTypeAuthInsufficientException->[User:]" + user.getName() + " [module:]" + module+"");
	}

}
