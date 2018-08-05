//$Id: Option.java,v 1.1 2007-6-2 上午10:34:38 chaostone Exp $
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

import org.apache.commons.lang.builder.CompareToBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 选项
 * 
 * @author chaostone
 * 
 */
public class Option  extends LongIdObject implements Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2548909930855138001L;

	/** 选项名 */
	private String name;

	/** 选项所占比重（权重） */
	private Float proportion;

	/** 选项组 */
	private OptionGroup optionGroup;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OptionGroup getOptionGroup() {
		return optionGroup;
	}

	public void setOptionGroup(OptionGroup optionGroup) {
		this.optionGroup = optionGroup;
	}

	public Float getProportion() {
		return proportion;
	}

	public void setProportion(Float proportion) {
		this.proportion = proportion;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		Option myClass = (Option) object;
		return new CompareToBuilder().append(myClass.proportion,
				this.proportion).toComparison();
	}

}
