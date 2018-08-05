//$Id: TeacherImportListener.java,v 1.1 2007-4-18 下午03:45:28 chaostone Exp $
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
 *chaostone      2007-4-18         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.baseinfo.importer;

import java.util.Map;

import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.system.baseinfo.TeacherService;

public class TeacherImportListener extends ItemImporterListener {
	protected TeacherService teacherService;

	public TeacherImportListener(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void startTransferItem(TransferResult tr) {
		Map params = importer.curDataMap();
		String code = (String) params.get("code");
		if (ValidEntityKeyPredicate.INSTANCE.evaluate(code)) {
			importer.setCurrent(teacherService.getTeacherByNO(code));
		}
	}

	public void endTransferItem(TransferResult tr) {
		Teacher teacher = (Teacher) importer.getCurrent();
		int errors = tr.errors();
		if (teacher.getCode() == null) {
			tr.addFailure("error.parameters.needed", "teacher no");
		}
		if (teacher.getName() == null) {
			tr.addFailure("error.parameters.needed", "name");
		}
		if (tr.errors() == errors) {
			try {
				teacherService.saveOrUpdate(teacher);
			} catch (Exception e) {
				tr.addFailure(e.getMessage(), teacher.getCode());
			}
		}
	}
}
