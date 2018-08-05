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

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ekingstar.eams.system.config.SystemConfigLoader;

/**
 * 考勤状态计数类
 */
public class DutyRecordStatisticsStatus{
	private Integer presenceCount;
	private Integer absenteeismCount;
	private Integer lateCount;
	private Integer leaveEarlyCount;
	private Integer askedForLeaveCount;
	private Integer totalCount;
	
	/**
	 * @return 返回 absenteeismCount.
	 */
	public Integer getAbsenteeismCount() {
		return absenteeismCount;
	}
	/**
	 * @param absenteeismCount 要设置的 absenteeismCount.
	 */
	public void setAbsenteeismCount(Integer absenteeismCount) {
		this.absenteeismCount = absenteeismCount;
	}
	/**
	 * @return 返回 askedForLeaveCount.
	 */
	public Integer getAskedForLeaveCount() {
		return askedForLeaveCount;
	}
	/**
	 * @param askedForLeaveCount 要设置的 askedForLeaveCount.
	 */
	public void setAskedForLeaveCount(Integer askedForLeaveCount) {
		this.askedForLeaveCount = askedForLeaveCount;
	}
	/**
	 * @return 返回 lateCount.
	 */
	public Integer getLateCount() {
		return lateCount;
	}
	/**
	 * @param lateCount 要设置的 lateCount.
	 */
	public void setLateCount(Integer lateCount) {
		this.lateCount = lateCount;
	}
	/**
	 * @return 返回 leaveEarlyCount.
	 */
	public Integer getLeaveEarlyCount() {
		return leaveEarlyCount;
	}
	/**
	 * @param leaveEarlyCount 要设置的 leaveEarlyCount.
	 */
	public void setLeaveEarlyCount(Integer leaveEarlyCount) {
		this.leaveEarlyCount = leaveEarlyCount;
	}
	/**
	 * @return 返回 presenceCount.
	 */
	public Integer getPresenceCount() {
		return presenceCount;
	}
	
	public Integer getAbsenteeismCount(Boolean convert) {
		return absenteeismCount;
	}
	
	public Integer getPresenceCount(Boolean convert) {
		if(Boolean.TRUE.equals(convert)){
			return new Integer(((this.presenceCount==null?0:this.presenceCount.intValue())+(this.lateCount==null?0:this.lateCount.intValue())+(this.leaveEarlyCount==null?0:this.leaveEarlyCount.intValue()))-((this.lateCount==null?0:this.lateCount.intValue())+(this.leaveEarlyCount==null?0:this.leaveEarlyCount.intValue()))/Integer.parseInt(SystemConfigLoader.getConfig().getConfigItemValue("system.dutyRuleInf").toString()));
		}else if(Boolean.FALSE.equals(convert)){
			return new Integer((this.presenceCount==null?0:this.presenceCount.intValue())+(this.lateCount==null?0:this.lateCount.intValue())+(this.leaveEarlyCount==null?0:this.leaveEarlyCount.intValue()));
		}else{
			return presenceCount;
		}
	}
	/**
	 * @param presenceCount 要设置的 presenceCount.
	 */
	public void setPresenceCount(Integer presenceCount) {
		this.presenceCount = presenceCount;
	}
	public String toString() {
		return new ToStringBuilder(this).append("TotalCount", this.getTotalCount()).append("PresenceCount", this.getPresenceCount()).append("AbsenteeismCount", this.getAbsenteeismCount()).append("LateCount", this.getLateCount()).append("LeaveEarlyCount", this.getLeaveEarlyCount()).append("AskedForLeaveCount", this.getAskedForLeaveCount()).toString();
	}	
	/**
	 * @param totalConvert 要设置的 totalConvert.
	 */
	/*public void doTotalConvert(Integer totalConvert) {
		this.totalConvert = totalConvert;
		this.setConvertAbsenteeismCount(totalConvert==null?new Integer(0):new Integer(totalConvert.intValue()/AttendanceType.convertAbsenteeism));
	}*/
	
	/**
	 * @return 返回 totalCount.
	 */
	public Integer getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount 要设置的 totalCount.
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	public Float getAbsenteeismRatio(Boolean convert) {
		if(new Integer(0).compareTo(totalCount) < 0){
			return new Float((this.getAbsenteeismCount(convert)==null?0f:this.getAbsenteeismCount(convert).floatValue())/this.totalCount.floatValue());
		}else{
			return new Float(0);
		}
		
	}
	
	public Float getPresenceRatio(Boolean convert) {
		if(new Integer(0).compareTo(totalCount) < 0){
			return new Float(
					(this.getPresenceCount(convert) == null ? 0f : this.getPresenceCount(convert).floatValue())
							/ totalCount.floatValue());
		}else{
			return new Float(0);
		}
	}
	
}
