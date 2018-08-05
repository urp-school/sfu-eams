package com.shufe.service.course.achivement;

import java.util.List;

import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 德育成绩提供者
 * @author chaostone
 *
 */
public interface MoralGradeProvider {
	GradeResult getGrade(Student std, List<TeachCalendar> calendars);
}
