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

import java.lang.reflect.Field;

import org.apache.commons.collections.Predicate;

import com.ekingstar.eams.system.basecode.industry.AttendanceType;
import com.shufe.model.duty.RecordDetail;

class StatusPredicate implements Predicate {

	private String status;

	public StatusPredicate() {
		super();
	}

	public StatusPredicate(String status) {
		this.status = status;
	}

	public boolean evaluate(Object object) {
		RecordDetail recordDetail = (RecordDetail) object;
		Long statusId;
		try {
			Field statusField = AttendanceType.class.getField(status);
			Object statusObject = statusField.get(null);
			statusId = (Long) statusObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("AttendanceType InvocationTargetException");
		}
		return (recordDetail.getDutyStatus().getId().equals(statusId));
	}

}