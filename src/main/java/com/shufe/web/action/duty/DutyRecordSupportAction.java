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
package com.shufe.web.action.duty;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.model.duty.RecordDetail;
import com.shufe.service.duty.DutyService;
import com.shufe.web.action.std.StudentSearchSupportAction;

class DutyRecordSupportAction extends StudentSearchSupportAction {

	protected DutyService dutyService;

	/**
	 * @param dutyService
	 *            要设置的 dutyService
	 */
	public void setDutyService(DutyService dutyService) {
		this.dutyService = dutyService;
	}

	/**
	 * 更新一个考勤记录的考勤详细记录
	 * 
	 * @param request
	 */
	protected void updateDetailOfRecord(HttpServletRequest request) {
		Long dutyRecordId = getLong(request, "recordId");
		List recordDetailList = utilService.load(RecordDetail.class, "dutyRecord.id",
				new Object[] { dutyRecordId });
		Long[] recordDetailIdArray = SeqStringUtil.transformToLong(request
				.getParameter("recordDetailIds"));
		for (Iterator iter = recordDetailList.iterator(); iter.hasNext();) {
			RecordDetail recordDetailListElement = (RecordDetail) iter.next();
			if (ArrayUtils.contains(recordDetailIdArray, recordDetailListElement.getId())) {
				Long dutyStatusId = getLong(request, "dutyStatus"
						+ recordDetailListElement.getId().longValue());
				if (!recordDetailListElement.getDutyStatus().getId().equals(dutyStatusId)) {
					if (dutyStatusId == null) {
						dutyService.deleteRecordDetail(recordDetailListElement.getId());
					} else {
						dutyService.updateRecordDetail(recordDetailListElement.getId(),
								dutyStatusId);
					}
				}
			}
		}
	}

}
