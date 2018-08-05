package com.shufe.service.course.achivement.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.baseinfo.Course;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;

public class GaScoreProvider  {

  private static final GradeType Makeup = new GradeType(GradeType.MAKEUP);

  public static Float getScore(CourseGrade grade) {
    ExamGrade m = grade.getExamGrade(Makeup);
    if (null == m) return grade.getScore();
    else return grade.getGA();
  }

  public static boolean isPassed(Float score) {
    return (null != score && Float.compare(score, 60.0f) >= 0);
  }

  public static Float getCredits(Student std, Course course) {
    if (course.getName().contains("形势与政策")) {
      if (std.getType().getName().contains("专科")) {
        return 0.33f;
      } else {
        return 0.25f;
      }
    } else {
      return course.getCredits();
    }
  }
  /**
   * 算出学生的年级
   * 
   * @param std
   * @param calendars
   * @return
   */
  static int getGradeLevel(Student std, List<TeachCalendar> calendars) {
    Calendar last = Calendar.getInstance();
    last.set(Calendar.YEAR, 1970);
    for (TeachCalendar c : calendars) {
      if (c.getEndOn().after(last.getTime())) last.setTime(c.getEndOn());
    }

    String grade = std.getGrade();

    if (grade.contains("-")) grade += "-01";
    else grade += "-09-01";
    Calendar enrollOn = Calendar.getInstance();
    enrollOn.setTime(Date.valueOf(grade));
//  if (std.getStdType().getId().equals(9L)){ 
//  enrollOn.add(Calendar.YEAR, -2);
//  }
    int month = 0;
    while (enrollOn.before(last)) {
      enrollOn.add(Calendar.MONTH, 1);
      month += 1;
    }
    return (int) Math.ceil(month / 12.0);
  }



}
