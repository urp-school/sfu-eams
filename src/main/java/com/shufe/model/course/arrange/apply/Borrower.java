//$Id: Borrower.java,v 1.1 2007-7-2 上午09:58:36 chaostone Exp $
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
 * chenweixiong              2007-7-2         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.arrange.apply;

import com.ekingstar.security.User;

/**
 * 借用人
 * 
 * @author chaostone
 * 
 */
public class Borrower implements com.ekingstar.commons.model.Component {
	/** 申请人 */
	private User user;
	/** 手机 */
	private String mobile;
	/** 电子邮件 */
	private String email;
	/** 联系地址 */
	private String addr;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

}
