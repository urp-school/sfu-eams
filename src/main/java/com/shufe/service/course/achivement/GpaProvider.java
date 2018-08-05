package com.shufe.service.course.achivement;

import java.util.List;

import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

public interface GpaProvider {

  Float getGPA(Student std, List<TeachCalendar> calendars);

}
