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
public class DutyRecordStatisticsVO {
	private DutyRecordStatisticsStatus partStatistics;
	private DutyRecordStatisticsStatus totalStatistics;
	/**
	 * @return 返回 partStatistics.
	 */
	public DutyRecordStatisticsStatus getPartStatistics() {
		return partStatistics;
	}
	/**
	 * @param partStatistics 要设置的 partStatistics.
	 */
	public void setPartStatistics(DutyRecordStatisticsStatus partStatistics) {
		this.partStatistics = partStatistics;
	}
	/**
	 * @return 返回 totalStatistics.
	 */
	public DutyRecordStatisticsStatus getTotalStatistics() {
		return totalStatistics;
	}
	/**
	 * @param totalStatistics 要设置的 totalStatistics.
	 */
	public void setTotalStatistics(DutyRecordStatisticsStatus totalStatistics) {
		this.totalStatistics = totalStatistics;
	}

}
