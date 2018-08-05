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
 * chaostone             2006-6-22            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.textbook;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.BookType;
import com.ekingstar.eams.system.basecode.industry.Press;
import com.ekingstar.eams.system.basecode.industry.TextbookAwardLevel;

/**
 * 教材
 * 
 * @author chaostone
 * 
 */
public class Textbook extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6059248193975178607L;

	/** 代码 */
	private String code;

	/** 名称 */
	private String name;

	/** 作者 */
	private String auth;

	/** 出版社 */
	private Press press = new Press();

	/** 版本 */
	private String version;

	/** 价格 */
	private Float price;

	/** ISBN号 */
	private String ISBN;

	/** 说明 */
	private String description;

	/** 备注 */
	private String remark;

	/** 教材类型 */
	private BookType bookType;

	/** 出版年月 */
	private Date publishedOn;

	/** 获奖等级 */
	private TextbookAwardLevel awardLevel = new TextbookAwardLevel();

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the auth.
	 */
	public String getAuth() {
		return auth;
	}

	/**
	 * @param auth
	 *            The auth to set.
	 */
	public void setAuth(String auth) {
		this.auth = auth;
	}

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return Returns the iSBN.
	 */
	public String getISBN() {
		return ISBN;
	}

	/**
	 * @param isbn
	 *            The iSBN to set.
	 */
	public void setISBN(String isbn) {
		ISBN = isbn;
	}

	/**
	 * @return Returns the price.
	 */
	public Float getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            The price to set.
	 */
	public void setPrice(Float price) {
		this.price = price;
	}

	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return Returns the version.
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            The version to set.
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return Returns the press.
	 */
	public Press getPress() {
		return press;
	}

	/**
	 * @param press
	 *            The press to set.
	 */
	public void setPress(Press press) {
		this.press = press;
	}

	public BookType getBookType() {
		return bookType;
	}

	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}

	public Date getPublishedOn() {
		return publishedOn;
	}

	public void setPublishedOn(Date publishedOn) {
		this.publishedOn = publishedOn;
	}

	public String getPublishedYearMonth() {
		if (null == publishedOn)
			return "";
		else {
			return new SimpleDateFormat("yyyy-MM").format(publishedOn);
		}
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Textbook)) {
			return false;
		}
		Textbook rhs = (Textbook) object;
		return new EqualsBuilder().append(this.getId(), rhs.getId()).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1123717217, -609864545)
				.append(this.getId()).toHashCode();
	}

	public TextbookAwardLevel getAwardLevel() {
		return awardLevel;
	}

	public void setAwardLevel(TextbookAwardLevel awardLevel) {
		this.awardLevel = awardLevel;
	}

}
