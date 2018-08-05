//$Id: DataAuthorityPredicate.java,v 1.1 2006/10/12 14:40:28 duanth Exp $
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
 * chaostone             2006-1-2         Created
 *  
 ********************************************************************************/

package com.shufe.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 数据权限判定谓词.<br>
 * 判定实体中的学生类别和部门信息是否在给定的数据范围内.<br>
 * 数据范围由<code>stdTypeAttrName</code>限定学生类别范围，""表示忽略学生范围限制.<br>
 * <code>departAttrName</code>限定部门(院系)范围,""表示忽略院系部门范围限制.<br>
 * 当实体中要检查的学生类别字段由<code>stdTypeAttrName</code>说明，<br>
 * 部门字段由<code>departAttrName</code>说明.当要检查的为空时，则认为不违反数据范围限制.<br>
 * The null data belong to anybody.
 * 
 * @author chaostone
 * 
 */
public class DataAuthorityPredicate implements Predicate {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	

	protected String stdTypeDataRealm = "";

	protected String departDataRealm = "";

	protected String stdTypeAttrName = "studentType";

	protected String departAttrName = "department";

	public DataAuthorityPredicate(String stdTypeIdSeq, String departIdSeq) {
		this.stdTypeDataRealm = stdTypeIdSeq;
		this.departDataRealm = departIdSeq;
	}

	public DataAuthorityPredicate() {

	}

	public DataAuthorityPredicate(String stdTypeIdSeq, String departIdSeq,
			String studentTypeName, String departAttrName) {
		this.stdTypeDataRealm = stdTypeIdSeq;
		this.departDataRealm = departIdSeq;
		this.stdTypeAttrName = studentTypeName;
		this.departAttrName = departAttrName;
	}

	public boolean evaluate(Object arg0) {
		try {
			// null belong to everybody
			if (null == arg0)
				return true;

			if (StringUtils.isNotEmpty(stdTypeDataRealm)) {
				Long stdTypeId = (Long) PropertyUtils.getProperty(arg0,
						stdTypeAttrName + ".id");
				if ((null != stdTypeId)
						&& !StringUtils.contains(stdTypeDataRealm, stdTypeId
								.toString()))
					return false;
			}
			if (StringUtils.isNotEmpty(departDataRealm)) {
				Long departId = (Long) PropertyUtils.getProperty(arg0,
						departAttrName + ".id");
				if ((null != departId)
						&& !StringUtils.contains(departDataRealm, departId
								.toString()))
					return false;
			}
			return true;
		} catch (Exception e) {
			logger.info("exception occurred in judge dataAuthorty of "
					+ arg0.getClass().getName(), e);
			return false;
		}
	}

	public String getDepartAttrName() {
		return departAttrName;
	}

	public void setDepartAttrName(String departAttrName) {
		this.departAttrName = departAttrName;
	}

	public String getDepartDataRealm() {
		return departDataRealm;
	}

	public void setDepartDataRealm(String departDataRealm) {
		this.departDataRealm = departDataRealm;
	}

	public String getStdTypeAttrName() {
		return stdTypeAttrName;
	}

	public void setStdTypeAttrName(String stdTypeAttrName) {
		this.stdTypeAttrName = stdTypeAttrName;
	}

	public String getStdTypeDataRealm() {
		return stdTypeDataRealm;
	}

	public void setStdTypeDataRealm(String stdTypeDataRealm) {
		this.stdTypeDataRealm = stdTypeDataRealm;
	}

}
