package com.shufe.dao.degree.instruct;

import java.util.List;

import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;

public interface InstructionDAO {

	/**
	 * 统计每个老师带每类学生类别的学生数量
	 * 
	 * @param calendar
	 * @param dataRealm
	 * @param isGraduateInstruction
	 * @return[学生类别id,教师id和人数]
	 */
	public List statStdTypeTeacher(TeachCalendar calendar, DataRealm dataRealm,
			Boolean isGraduateInstruction);

}
