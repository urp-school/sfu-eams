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
package com.shufe.model.duty;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 学生考勤统计对象
 */
public class DutyRecordStatisticsStd {
	public static final String totalNo = "total";
	public static final String totalName = "合计";
	public static final String totalEngName = "total";
	private String id;
	private String code;
	private String name;
	private String engName;
	
	public DutyRecordStatisticsStd(){
		super();
	}
	public DutyRecordStatisticsStd(String stdId){
		this.id=stdId;
	}
	public DutyRecordStatisticsStd(String id, String code, String name, String engName){
		this.id=id;
		this.code=code;
		this.name=name;
		this.engName=engName;
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
	/**
	 * @return 返回 id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id 要设置的 id.
	 */
	public void setId(String stdId) {
		this.id = stdId;
	}
	public boolean equals(Object object) {
		if (!(object instanceof DutyRecordStatisticsStd)) {
			return false;
		}
		DutyRecordStatisticsStd dutyRecordStatisticsStd = (DutyRecordStatisticsStd) object;
		return new EqualsBuilder().append(this.getId(), dutyRecordStatisticsStd.getId()).isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder(-1351925265, -947066349).append(this.getId()).toHashCode();
	}
	public String toString() {
		return new ToStringBuilder(this).append("id", this.getId()).append("Name", this.getName()).append("EngName", this.getEngName()).toString();
	}
	/**
	 * @return 返回 code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code 要设置的 code.
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
