package com.shufe.service.course.achivement.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.baseinfo.Course;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.grade.GradeState;
import com.shufe.model.course.grade.MoralGrade;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.achivement.GradeResult;
import com.shufe.service.course.achivement.MoralGradeProvider;

public class MoralGradeProviderImpl extends BasicService implements MoralGradeProvider {

  @Override
  public GradeResult getGrade(Student std, List<TeachCalendar> calendars) {
    int gradeLevel = GaScoreProvider.getGradeLevel(std, calendars);
    if (gradeLevel < 3) {
      GradeResult result = getGradeFromCourseGrade(std, calendars);
      if (Float.compare(result.score, 0) <= 0) {
        result = getGradeFromMoral(std, calendars);
      }
      return result;
    } else {
      return getGradeFromMoral(std, calendars);
    }
  }

  GradeResult getGradeFromCourseGrade(Student std, List<TeachCalendar> calendars) {
    EntityQuery query = new EntityQuery(CourseGrade.class, "cg");
    query.add(new Condition("cg.std=:std", std));
    query.add(new Condition("cg.calendar in (:calendars)", calendars));
    query.add(new Condition("cg.majorType.id=:majorType", MajorType.FIRST));
    query.add(new Condition("cg.status=:status", Grade.PUBLISHED));
    query.add(new Condition("exists(from " + GradeState.class.getName()
        + " gs where gs.teachTask = cg.task and gs.moralGradePercent >0)"));

    List<CourseGrade> courseGrades = (List<CourseGrade>) utilService.search(query);
    Collections.sort(courseGrades, new PropertyComparator("calendar.id"));
    float ga = 0;
    float credit = 0;
    int unpassed = 0;
    StringBuilder sb = new StringBuilder();
    TeachCalendar calendar = null;
    for (CourseGrade courseGrade : courseGrades) {
      if (null == calendar || !courseGrade.getCalendar().equals(calendar)) {
        calendar = courseGrade.getCalendar();
        if (sb.length() > 0) sb.append("\n");
        sb.append(calendar.getYear()).append('(');
        sb.append(calendar.getTerm()).append(") \n");
      }
      Float score = GaScoreProvider.getScore(courseGrade);
      Float credtis = GaScoreProvider.getCredits(std, courseGrade.getCourse());
      if (score != null) ga += score * credtis;

      if (!GaScoreProvider.isPassed(score)) unpassed +=1;
      credit += credtis;
      sb.append(GradeSummary.summary(courseGrade, score)).append("\n");
    }
    ga = (credit != 0) ? ga / credit : 0;
    return new GradeResult(ga, unpassed, sb.toString());
  }

  GradeResult getGradeFromMoral(Student std, List<TeachCalendar> calendars) {
    EntityQuery query = new EntityQuery(MoralGrade.class, "grade");
    query.add(new Condition("grade.std=:std", std));
    query.add(new Condition("grade.calendar in (:calendars)", calendars));
    @SuppressWarnings("unchecked")
    List<MoralGrade> moralGrades = (List<MoralGrade>) utilService.search(query);
    Float score = null;
    if (moralGrades.size() > 1) {
      Float total = 0f;
      for (MoralGrade mg : moralGrades) {
        total += mg.getScore();
      }
      score = total / moralGrades.size();
    } else if (moralGrades.size() == 1) {
      score = moralGrades.get(0).getScore();
    }
    if (null == score) return new GradeResult(null, 0);
    else return new GradeResult(score, 0);
  }
}
