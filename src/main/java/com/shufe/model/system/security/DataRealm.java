//$Id: DataRealm.java,v 1.1 2006/10/12 14:40:10 duanth Exp $
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
 * chaostone             2005-9-26         Created
 *  
 ********************************************************************************/

package com.shufe.model.system.security;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Component;

/**
 * 数据操作范围，定义模块的数据使用范围.
 * 
 * 学生类别和部门的id串采用两端不加逗号的逗号川表示
 * 
 * @author dell,chaostone 2005-9-26
 */
public class DataRealm implements Component, Serializable {

	private static final long serialVersionUID = -75303778825269630L;

	/** 学生类别id串 */
	private String studentTypeIdSeq;

	/** 部门id串 */
	private String departmentIdSeq;

	public Long getId() {
		return null;
	}

	public Boolean getIsValid() {
		return null;
	}

	public Set getItems() {
		return null;
	}

	public Serializable getEntityId() {
		return null;
	}

	public boolean isSaved() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isValidEntity() {
		// TODO Auto-generated method stub
		return false;
	}

	public String key() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isPO() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isVO() {
		// TODO Auto-generated method stub
		return false;
	}

	public DataRealm() {
		super();
	}

	public Object clone() {
		return new DataRealm(getStudentTypeIdSeq(), getDepartmentIdSeq());
	}

	public DataRealm(String studentTypeIdSeq, String departmentIdSeq) {
		super();
		this.studentTypeIdSeq = studentTypeIdSeq;
		this.departmentIdSeq = departmentIdSeq;
	}

	/**
	 * @return Returns the departmentIdSeq.
	 */
	public String getDepartmentIdSeq() {
		return departmentIdSeq;
	}

	/**
	 * @param departmentIdSeq
	 *            The departmentIdSeq to set.
	 */
	public void setDepartmentIdSeq(String departmentIdSeq) {
		this.departmentIdSeq = departmentIdSeq;
	}

	/**
	 * @return Returns the studentTypeIdSeq.
	 */
	public String getStudentTypeIdSeq() {
		return studentTypeIdSeq;
	}

	/**
	 * @param studentTypeIdSeq
	 *            The studentTypeIdSeq to set.
	 */
	public void setStudentTypeIdSeq(String studentTypeIdSeq) {
		this.studentTypeIdSeq = studentTypeIdSeq;
	}

	public static DataRealm mergeAll(List realms) {
		DataRealm realm = new DataRealm();
		if (CollectionUtils.isEmpty(realms)) {
			return realm;
		}
		for (Iterator iter = realms.iterator(); iter.hasNext();) {
			DataRealm thisRealm = (DataRealm) iter.next();
			realm.merge(thisRealm);
		}
		return realm;
	}

	public DataRealm merge(DataRealm other) {
		if (null == other)
			return this;
		else {
			setDepartmentIdSeq(evictComma(SeqStringUtil.mergeSeq(getDepartmentIdSeq(), other
					.getDepartmentIdSeq())));
			setStudentTypeIdSeq(evictComma(SeqStringUtil.mergeSeq(getStudentTypeIdSeq(), other
					.getStudentTypeIdSeq())));
			return this;
		}
	}

	public DataRealm shrink(DataRealm other) {
		if (null == other)
			return this;
		else {
			setDepartmentIdSeq(evictComma(SeqStringUtil.subtractSeq(getDepartmentIdSeq(), other
					.getDepartmentIdSeq())));
			setStudentTypeIdSeq(evictComma(SeqStringUtil.subtractSeq(getStudentTypeIdSeq(), other
					.getStudentTypeIdSeq())));
			return this;
		}
	}

	private static String evictComma(String str) {
		if (StringUtils.isEmpty(str))
			return str;
		else {
			if (str.startsWith(",") && str.endsWith(","))
				return str.substring(1, str.length() - 1);
			else if (str.startsWith(",")) {
				return str.substring(1);
			} else if (str.endsWith(",")) {
				return str.substring(0, str.length() - 1);
			} else {
				return str;
			}
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("studentTypeIdSeq",
				this.studentTypeIdSeq).append("departmentIdSeq", this.departmentIdSeq).toString();
	}
}
