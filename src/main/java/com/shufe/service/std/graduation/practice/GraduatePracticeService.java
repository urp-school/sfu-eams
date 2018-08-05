package com.shufe.service.std.graduation.practice;

import java.util.List;
import java.util.Map;

import com.shufe.model.std.graduation.practice.GraduatePractice;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;

public interface GraduatePracticeService {
	
	/**
	 * 得到练习的属性的列表
	 * @param graduatePractice
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @param properties
	 * @param isCount
	 * @return
	 */
	public List getPropertyOfPractices(GraduatePractice graduatePractice,
			String departIdSeq, String stdTypeIdSeq, String properties,
			Boolean isCount);
	
	/**
	 * 得到实习单位的统计
	 * @return
	 */
	public Map getDatasOfDepartStat(DataRealm dataRealm,TeachCalendar teachCalendar);

}
