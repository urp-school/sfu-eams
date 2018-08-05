//$Id: BasicInfo.java,v 1.9 2007/01/23 01:13:43 duanth Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-10-31         Created
 *  
 ********************************************************************************/

package com.shufe.model.std;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.MaritalStatus;
import com.ekingstar.eams.system.basecode.state.Country;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.basecode.state.Nation;
import com.ekingstar.eams.system.basecode.state.PoliticVisage;

/**
 * 学生基本信息
 * 
 * @author dell
 */
public class BasicInfo extends LongIdObject implements Serializable {

	private static final long serialVersionUID = -507997924590348564L;

	public static List notNullPropertyList = new ArrayList();
	static {
		notNullPropertyList.add("gender");
		Collections.unmodifiableList(notNullPropertyList);
	}
	/** 出生年月 */
	private Date birthday;

	/** 民族 */
	private Nation nation = new Nation();

	/** 性别 */
	private Gender gender = new Gender();

	/** 政治面貌 */
	private PoliticVisage politicVisage = new PoliticVisage();

	/** 身份证 */
	private String idCard;

	/** 家庭地址 */
	private String homeAddress;

	/** 家长姓名 */
	private String parentName;

	/** 家庭地址邮编 */
	private String postCode;

	/** 家庭电话 */
	private String phone;

	/** 移动电话 */
	private String mobile;

	/** 工作地址 */
	private String workAddress;

	/** 工作单位 */
	private String workPlace;

	/** 工作地址邮编 */
	private String workPlacePostCode;

	/** 工作单位电话 */
	private String workPhone;

	/** 籍贯 */
	private String ancestralAddress;

	/** 电子邮箱 */
	private String mail;

	/** 照片文件名 */
	private String photoName;

	/** 国家地区 */
	private Country country = new Country();

	/** 婚姻状况 */
	private MaritalStatus maritalStatus = new MaritalStatus();

	/**
	 * @return Returns the ancestralAddress.
	 */
	public String getAncestralAddress() {
		return ancestralAddress;
	}

	/**
	 * @param ancestralAddress
	 *            The ancestralAddress to set.
	 */
	public void setAncestralAddress(String ancestralAddress) {
		this.ancestralAddress = ancestralAddress;
	}

	/**
	 * @return Returns the birthday.
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 *            The birthday to set.
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return Returns the country.
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            The country to set.
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * @return Returns the gender.
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            The gender to set.
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @return Returns the homeAddress.
	 */
	public String getHomeAddress() {
		return homeAddress;
	}

	/**
	 * @param homeAddress
	 *            The homeAddress to set.
	 */
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	/**
	 * @return Returns the idCard.
	 */
	public String getIdCard() {
		return idCard;
	}

	/**
	 * @param idCard
	 *            The idCard to set.
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	/**
	 * @return Returns the mail.
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail
	 *            The mail to set.
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return Returns the nation.
	 */
	public Nation getNation() {
		return nation;
	}

	/**
	 * @param nation
	 *            The nation to set.
	 */
	public void setNation(Nation nation) {
		this.nation = nation;
	}

	/**
	 * @return Returns the parentName.
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @param parentName
	 *            The parentName to set.
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return Returns the politicVisage.
	 */
	public PoliticVisage getPoliticVisage() {
		return politicVisage;
	}

	/**
	 * @param politicVisage
	 *            The politicVisage to set.
	 */
	public void setPoliticVisage(PoliticVisage politicVisage) {
		this.politicVisage = politicVisage;
	}

	/**
	 * @return Returns the postCode.
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * @param postCode
	 *            The postCode to set.
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * @return 返回 workAddress.
	 */
	public String getWorkAddress() {
		return workAddress;
	}

	/**
	 * @param workAddress
	 *            要设置的 workAddress.
	 */
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	/**
	 * @return 返回 workPhone.
	 */
	public String getWorkPhone() {
		return workPhone;
	}

	/**
	 * @param workPhone
	 *            要设置的 workPhone.
	 */
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	/**
	 * @return 返回 workPlace.
	 */
	public String getWorkPlace() {
		return workPlace;
	}

	/**
	 * @param workPlace
	 *            要设置的 workPlace.
	 */
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	/**
	 * @return 返回 workPlacePostCode.
	 */
	public String getWorkPlacePostCode() {
		return workPlacePostCode;
	}

	/**
	 * @param workPlacePostCode
	 *            要设置的 workPlacePostCode.
	 */
	public void setWorkPlacePostCode(String workPlacePostCode) {
		this.workPlacePostCode = workPlacePostCode;
	}

	/**
	 * @return 返回 maritalStatus.
	 */
	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * @param maritalStatus
	 *            要设置的 maritalStatus.
	 */
	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	/**
	 * @return photoName
	 */
	public String getPhotoName() {
		return photoName;
	}

	/**
	 * @param photoName
	 *            要设置的 photoName
	 */
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
