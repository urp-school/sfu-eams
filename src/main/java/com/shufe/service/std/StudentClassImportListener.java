package com.shufe.service.std;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilService;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.system.baseinfo.AdminClassService;

public class StudentClassImportListener extends ItemImporterListener {
	private Map classMap = new HashMap();

	private UtilService utilService;

	private AdminClassService adminClassService;

	public void endTransfer(TransferResult tr) {
		utilService.saveOrUpdate(classMap.values());
		List adminClassIdList = EntityUtils.extractIds(classMap.values());
		Long[] adminClassIds = new Long[adminClassIdList.size()];
		adminClassIdList.toArray(adminClassIds);
		adminClassService.batchUpdateStdCountOfClass(adminClassIds);
		super.endTransfer(tr);
	}

	public void endTransferItem(TransferResult tr) {
		Object datas[] = (Object[]) importer.getCurData();

		AdminClass adminClass = (AdminClass) classMap.get(datas[0]);
		if (adminClass == null) {
			List classList = utilService.load(AdminClass.class, "code", datas[0]);
			if (classList.size() == 1) {
				adminClass = (AdminClass) classList.get(0);
				classMap.put(datas[0], adminClass);
			} else {
				tr.addFailure("class not exists", datas[0]);
			}
		}

		List studentList = utilService.load(Student.class, "code", datas[1]);
		Student student = null;
		if (studentList.size() == 1) {
			student = (Student) studentList.get(0);
		} else {
			tr.addFailure("student not exists", datas[1]);
		}
		if (null != adminClass && null != student) {
			adminClass.getStudents().add(student);
		}
	}

	public void setAdminClassService(AdminClassService adminClassService) {
		this.adminClassService = adminClassService;
	}

	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

}
