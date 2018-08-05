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
package com.shufe.service.duty;

import java.util.Map;

import org.apache.commons.collections.Closure;

import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.shufe.model.duty.RecordDetail;

public class StatusClosure implements Closure {
	
	private String status;
	private Map studentStatisticsMap;
	private String dutyRecordStatisticsCourseId;
//	private Map totalStatisticsMap;
	private CourseTakeType courseTakeType;
	
	public StatusClosure() {
		super();
	}
	public StatusClosure(String status, Map studentStatisticsMap, String dutyRecordStatisticsCourseId, CourseTakeType courseTakeType) {
		this.status = status;
		this.studentStatisticsMap = studentStatisticsMap;
		this.dutyRecordStatisticsCourseId = dutyRecordStatisticsCourseId;
//		this.totalStatisticsMap = totalStatisticsMap;
		this.courseTakeType = courseTakeType;
	}

	public void execute(Object object) {
		RecordDetail recordDetail = (RecordDetail) object;
		courseDutyCount(recordDetail, studentStatisticsMap, dutyRecordStatisticsCourseId);
//		courseDutyCount(s, totalStatisticsMap, dutyRecordStatisticsCourse);
	}
	
	private void courseDutyCount(RecordDetail recordDetail, Map studentStatisticsMap, String dutyRecordStatisticsCourseId) {/*
		Object statusCount = studentStatisticsMap.get(dutyRecordStatisticsCourseId);
		if(statusCount==null){
			DutyRecordStatisticsStatus dutyRecordStatisticsStatus = new DutyRecordStatisticsStatus();
			try{
				PropertyUtils.setProperty(dutyRecordStatisticsStatus, status+"Count", (Integer)s[1]);
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("dutyRecordStatisticsStatus NoSuchMethodException");
			}
			if(status.equals(AttendanceType.late)||status.equals(AttendanceType.leaveEarly)){
				dutyRecordStatisticsStatus.doTotalConvert((Integer)s[1]);
			}
			dutyRecordStatisticsStatus.setTotalCount(dutyRecordStatisticsStatus.getTotalCount()==null?(Integer)s[1]:new Integer(dutyRecordStatisticsStatus.getTotalCount().intValue()+((Integer)s[1]).intValue()));
			studentStatisticsMap.put(dutyRecordStatisticsCourseId,dutyRecordStatisticsStatus);
		}else{
			DutyRecordStatisticsStatus dutyRecordStatisticsStatus = (DutyRecordStatisticsStatus)statusCount;
			Object count;
			try{
				count = PropertyUtils.getProperty(dutyRecordStatisticsStatus,status+"Count");
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("dutyRecordStatisticsStatus InvocationTargetException");
			}
			try{
				if(count==null){
					PropertyUtils.setProperty(dutyRecordStatisticsStatus, status+"Count", (Integer)s[1]);
				}else{
					PropertyUtils.setProperty(dutyRecordStatisticsStatus, status+"Count", new Integer(((Integer)count).intValue()+((Integer)s[1]).intValue()));						
				}
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("dutyRecordStatisticsStatus NoSuchMethodException");
			}
			if(status.equals(AttendanceType.late)||status.equals(AttendanceType.leaveEarly)){
				dutyRecordStatisticsStatus.doTotalConvert(new Integer((dutyRecordStatisticsStatus.getTotalConvert()==null?0:dutyRecordStatisticsStatus.getTotalConvert().intValue())+((Integer)s[1]).intValue()));
			}
			dutyRecordStatisticsStatus.setTotalCount(dutyRecordStatisticsStatus.getTotalCount()==null?(Integer)s[1]:new Integer(dutyRecordStatisticsStatus.getTotalCount().intValue()+((Integer)s[1]).intValue()));
			studentStatisticsMap.put(dutyRecordStatisticsCourseId,dutyRecordStatisticsStatus);
		}
	*/}
	
}
