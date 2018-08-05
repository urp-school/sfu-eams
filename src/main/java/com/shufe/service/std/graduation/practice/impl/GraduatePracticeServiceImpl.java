package com.shufe.service.std.graduation.practice.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.shufe.dao.std.graduation.practice.GraduatePracticeDAO;
import com.shufe.model.std.graduation.practice.GraduatePractice;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.BasicService;
import com.shufe.service.std.graduation.practice.GraduatePracticeService;
import com.shufe.service.util.stat.StatUtils;

public class GraduatePracticeServiceImpl extends BasicService implements
		GraduatePracticeService {
	private GraduatePracticeDAO graduatePracticeDAO;

	public void setGraduatePracticeDAO(GraduatePracticeDAO graduatePracticeDAO) {
		this.graduatePracticeDAO = graduatePracticeDAO;
	}

	/**
	 * @see com.shufe.service.std.graduation.practice.GraduatePracticeService#getPropertyOfPractices(com.shufe.model.std.graduation.practice.GraduatePractice, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	public List getPropertyOfPractices(GraduatePractice graduatePractice,
			String departIdSeq, String stdTypeIdSeq, String properties,
			Boolean isCount) {
		return graduatePracticeDAO.getPropertyOfPractices(graduatePractice,
				departIdSeq, stdTypeIdSeq, properties, isCount);
	}

	/**
	 * @see com.shufe.service.std.graduation.practice.GraduatePracticeService#getDatasOfDepartStat(java.lang.String, java.lang.String)
	 */
	public Map getDatasOfDepartStat(DataRealm dataRealm,
			TeachCalendar teachCalendar) {
		GraduatePractice graduatePractice = new GraduatePractice();
		if (null != teachCalendar
				&& null != teachCalendar.getStudentType().getId()) {
			graduatePractice.getStudent().getType().setId(
					teachCalendar.getStudentType().getId());
		}
		graduatePractice.setTeachCalendar(teachCalendar);
		List departDatas = getPropertyOfPractices(graduatePractice, dataRealm
				.getDepartmentIdSeq(), dataRealm.getStudentTypeIdSeq(),
				"student.department.id,practiceSource.id", Boolean.TRUE);
		Map returnMap = new HashMap();
		for (Iterator iter = departDatas.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			Integer count = (Integer) element[0];
			Long departId = (Long) element[1];
			Long sourceId = (Long) element[2];
			String keyValue = departId + "-" + sourceId;
			StatUtils.setValueToMap(keyValue, count,
					"integer", returnMap);
			StatUtils.setValueToMap("0-" + sourceId,
					count, "integer", returnMap);
			StatUtils.setValueToMap(departId + "-0",
					count, "integer", returnMap);
			StatUtils.setValueToMap("0-0", count,
					"integer", returnMap);
		}
		graduatePractice.setIsPractictBase(Boolean.TRUE);
		List isBaseDatas = getPropertyOfPractices(graduatePractice, dataRealm
				.getDepartmentIdSeq(), dataRealm.getStudentTypeIdSeq(),
				"student.department.id", Boolean.TRUE);
		for (Iterator iter = isBaseDatas.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			Integer count = (Integer) element[0];
			Long departId = (Long) element[1];
			StatUtils.setValueToMap(departId + "-base",
					count, "integer", returnMap);
			StatUtils.setValueToMap("0-base", count,
					"integer", returnMap);
		}
		return returnMap;
	}
}
