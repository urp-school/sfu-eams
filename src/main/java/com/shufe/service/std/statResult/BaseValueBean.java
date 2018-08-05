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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.service.std.statResult;
public class BaseValueBean {

	private Long id;
	private String name;
	private String engName;
	private String dataName;
	private Object value;
	
	public BaseValueBean(Long id, String name, String engName, String dataName) {
		this.id = id;
		this.name = name;
		this.engName = engName;
		this.dataName = dataName;
	}
	
	/**
	 * @return 返回 dataName.
	 */
	public String getDataName() {
		return dataName;
	}
	/**
	 * @param dateName 要设置的 dataName.
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	
	/**
	 * @return 返回 engName.
	 */
	public String getEngName() {
		return engName;
	}
	/**
	 * @param engName 要设置的 engName.
	 */
	public void setEngName(String engName) {
		this.engName = engName;
	}
	/**
	 * @return 返回 id.
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id 要设置的 id.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return 返回 name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name 要设置的 name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	public BaseValueBean() {
		super();
	}

	/**
	 * @return 返回 value.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value 要设置的 value.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

}
