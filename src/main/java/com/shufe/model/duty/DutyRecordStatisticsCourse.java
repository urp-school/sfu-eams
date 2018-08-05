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
 * 课程考勤统计类
 */
public class DutyRecordStatisticsCourse {
	public static final String totalId = "total";
	public static final String courseTakeType = "courseTakeType";
	public static final String totalName = "合计";
	public static final String totalEngName = "total";
	
	private String dutyRecordId;
	private String teachTaskId;
	private String courseId;
	private String name;
	private String engName;
	
	public DutyRecordStatisticsCourse(){
		super();
	}
	public DutyRecordStatisticsCourse(String teachTaskId){
		this.teachTaskId=teachTaskId;
	}
	public DutyRecordStatisticsCourse(String teachTaskId, String name, String engName){
		this.teachTaskId=teachTaskId;
		this.name=name;
		this.engName=engName;
	}
	public DutyRecordStatisticsCourse(String dutyRecordId, String teachTaskId, String courseId, String name, String engName){
		this.dutyRecordId=dutyRecordId;
		this.teachTaskId=teachTaskId;
		this.courseId=courseId;
		this.name=name;
		this.engName=engName;
	}
	public DutyRecordStatisticsCourse(DutyRecord dutyRecord){
		this(String.valueOf(dutyRecord.getId()), String.valueOf(dutyRecord.getTeachTask().getId()), String.valueOf(dutyRecord.getTeachTask().getCourse().getId()), dutyRecord.getTeachTask().getCourse().getName(), dutyRecord.getTeachTask().getCourse().getEngName());
	}
	/**
	 * @return 返回 courseId.
	 */
	public String getCourseId() {
		return courseId;
	}
	/**
	 * @param courseId 要设置的 courseId.
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	/**
	 * @return 返回 dutyRecordId.
	 */
	public String getDutyRecordId() {
		return dutyRecordId;
	}
	/**
	 * @param dutyRecordId 要设置的 dutyRecordId.
	 */
	public void setDutyRecordId(String dutyRecordId) {
		this.dutyRecordId = dutyRecordId;
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
	 * @return 返回 teachTaskId.
	 */
	public String getTeachTaskId() {
		return teachTaskId;
	}
	/**
	 * @param teachTaskId 要设置的 teachTaskId.
	 */
	public void setTeachTaskId(String teachTaskId) {
		this.teachTaskId = teachTaskId;
	}
	public boolean equals(Object object) {
		if (!(object instanceof DutyRecordStatisticsCourse)) {
			return false;
		}
		DutyRecordStatisticsCourse dutyRecordStatisticsStd = (DutyRecordStatisticsCourse) object;
		return new EqualsBuilder().append(this.getTeachTaskId(), dutyRecordStatisticsStd.getTeachTaskId()).isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder(-1351925265, -947066349).append(this.getTeachTaskId()).toHashCode();
	}
	public String toString() {
		return new ToStringBuilder(this).append("DutyRecordId", this.getDutyRecordId()).append("TeachTaskId", this.getTeachTaskId()).append("CourseId", this.getCourseId()).append("Name", this.getName()).append("EngName", this.getEngName()).toString();
	}
}
