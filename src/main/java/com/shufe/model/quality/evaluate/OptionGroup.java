//$Id: OptionGroup.java,v 1.1 2007-6-2 上午10:34:26 chaostone Exp $
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
 *chaostone      2007-6-2         Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.evaluate;

import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.baseinfo.Department;

/**
 * 选项组
 * 
 * @author chaostone
 * 
 */
public class OptionGroup extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -90789553839142305L;

	/** 名称 */
	private String name;

	/** 描述 */
	private String description;

	/** 各类选项 */
	private Set options = new HashSet();

	/** 创建部门 */
	private Department depart = new Department();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getOptions() {
		return options;
	}

	public void setOptions(Set options) {
		this.options = options;
	}

	public Department getDepart() {
		return depart;
	}

	public void setDepart(Department depart) {
		this.depart = depart;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addOption(Option option) {
		option.setOptionGroup(this);
		getOptions().add(option);
	}

}
