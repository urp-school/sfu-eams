//$Id: Report.java,v 1.1 2007-2-13 下午03:53:51 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-2-13         Created
 *  
 ********************************************************************************/

package com.shufe.model.system.report;

import java.util.HashMap;
import java.util.Map;

import com.ekingstar.commons.model.pojo.LongIdObject;
/**
 * 报表模板对象
 * @author chaostone
 *
 */
public class Report extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1257652759662461101L;

	private String name;

	private String template;

	private Map params = new HashMap();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
