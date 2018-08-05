//$Id: TeacherAddressInfo.java,v 1.2 2006/12/15 12:06:26 duanth Exp $
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
 * chaostone             2005-10-31         Created
 *  
 ********************************************************************************/

package com.shufe.model.system.baseinfo;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 教师联系信息
 * 
 * @author chaostone 2005-10-31
 */
public class TeacherAddressInfo extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8386093985280403368L;

	/** 家庭地址邮编 */
	protected String postCodeOfFamily;

	/** 家庭通讯地址 */
	protected String familyAddress;

	/** 家庭电话 */
	protected String phoneOfHome;

	/** 单位邮编 */
	protected String postCodeOfCorporation;

	/** 单位通信地址 */
	protected String corporationAddress;

	/** 单位电话 */
	protected String phoneOfCorporation;

	/** 移动电话 */
	protected String mobilePhone;

	/** 传真 */
	protected String fax;

	/** 电子邮箱 */
	protected String email;

	/** 主页地址 */
	protected String homepage;

	/**
	 * @return Returns the corporationAddress.
	 */
	public String getCorporationAddress() {
		return corporationAddress;
	}

	/**
	 * @param corporationAddress
	 *            The corporationAddress to set.
	 */
	public void setCorporationAddress(String corporationAddress) {
		this.corporationAddress = corporationAddress;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the familyAddress.
	 */
	public String getFamilyAddress() {
		return familyAddress;
	}

	/**
	 * @param familyAddress
	 *            The familyAddress to set.
	 */
	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}

	/**
	 * @return Returns the fax.
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *            The fax to set.
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return Returns the homepage.
	 */
	public String getHomepage() {
		return homepage;
	}

	/**
	 * @param homepage
	 *            The homepage to set.
	 */
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	/**
	 * @return Returns the mobilePhone.
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * @param mobilePhone
	 *            The mobilePhone to set.
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * @return Returns the phoneOfCorporation.
	 */
	public String getPhoneOfCorporation() {
		return phoneOfCorporation;
	}

	/**
	 * @param phoneOfCorporation
	 *            The phoneOfCorporation to set.
	 */
	public void setPhoneOfCorporation(String phoneOfCorporation) {
		this.phoneOfCorporation = phoneOfCorporation;
	}

	/**
	 * @return Returns the phoneOfHome.
	 */
	public String getPhoneOfHome() {
		return phoneOfHome;
	}

	/**
	 * @param phoneOfHome
	 *            The phoneOfHome to set.
	 */
	public void setPhoneOfHome(String phoneOfHome) {
		this.phoneOfHome = phoneOfHome;
	}

	/**
	 * @return Returns the postCodeOfCorporation.
	 */
	public String getPostCodeOfCorporation() {
		return postCodeOfCorporation;
	}

	/**
	 * @param postCodeOfCorporation
	 *            The postCodeOfCorporation to set.
	 */
	public void setPostCodeOfCorporation(String postCodeOfCorporation) {
		this.postCodeOfCorporation = postCodeOfCorporation;
	}

	/**
	 * @return Returns the postCodeOfFamily.
	 */
	public String getPostCodeOfFamily() {
		return postCodeOfFamily;
	}

	/**
	 * @param postCodeOfFamily
	 *            The postCodeOfFamily to set.
	 */
	public void setPostCodeOfFamily(String postCodeOfFamily) {
		this.postCodeOfFamily = postCodeOfFamily;
	}
}
