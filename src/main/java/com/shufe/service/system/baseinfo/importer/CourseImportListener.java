//$Id: CourseImportListener.java,v 1.1 2007-3-18 下午05:18:25 chaostone Exp $
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
 *chaostone      2007-3-18         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.baseinfo.importer;

import java.util.Map;

import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.eams.system.baseinfo.service.CourseService;
import com.shufe.model.system.baseinfo.Course;

public class CourseImportListener extends ItemImporterListener {
	protected CourseService courseService;

	public CourseImportListener(CourseService courseService) {
		this.courseService = courseService;
	}

	public void startTransferItem(TransferResult tr) {
		Map params = importer.curDataMap();
		String code = (String) params.get("code");
		if (ValidEntityKeyPredicate.INSTANCE.evaluate(code)) {
			importer.setCurrent(courseService.getCourse(code));
		}
	}

	public void endTransferItem(TransferResult tr) {
		Course course = (Course) importer.getCurrent();
		int errors = tr.errors();
		if (course.getStdType() == null) {
			tr.addFailure("error.parameters.needed", "studentType");
		}
		if (course.getCredits() == null) {
			tr.addFailure("error.parameters.needed", "credit");
		}
		if (course.getExtInfo().getPeriod() == null) {
			tr.addFailure("error.parameters.needed", "overall credit hour");
		}
		if (course.getWeekHour() == null) {
			tr.addFailure("error.parameters.needed", "week hour");
		}
		if (tr.errors() == errors) {
			courseService.saveOrUpdate(course);
		}
	}
}
