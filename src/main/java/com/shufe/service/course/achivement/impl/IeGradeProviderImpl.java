package com.shufe.service.course.achivement.impl;

import java.util.Collections;
import java.util.List;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.grade.GradeState;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.achivement.GradeResult;
import com.shufe.service.course.achivement.IeGradeProvider;

/**
 * 智育总评成绩提供者
 * 
 * @author chaostone
 */
public class IeGradeProviderImpl extends BasicService implements IeGradeProvider {

  @SuppressWarnings("unchecked")
  @Override
  public GradeResult getGrade(Student std, List<TeachCalendar> calendars) {
    EntityQuery query = new EntityQuery(CourseGrade.class, "cg");
    query.add(new Condition("cg.std=:std", std));
    query.add(new Condition("cg.calendar in (:calendars)", calendars));
    query.add(new Condition("cg.majorType.id=:majorType", MajorType.FIRST));
    query.add(new Condition("cg.course.category.id != :courseCategoryId", CourseCategory.PHYCIALID));
    query.add(new Condition("cg.status=:status", Grade.PUBLISHED));
    query.add(new Condition("not exists(from " + GradeState.class.getName()
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
      Float credits = GaScoreProvider.getCredits(std, courseGrade.getCourse());
      Float score = GaScoreProvider.getScore(courseGrade);
      if (score != null) ga += score * credits;

      if (!GaScoreProvider.isPassed(score)) unpassed += 1;
      credit += credits;
      sb.append(GradeSummary.summary(courseGrade, score)).append("\n");
    }
    ga = (credit != 0) ? ga / credit : 0;
    return new GradeResult(ga, unpassed, sb.toString());
  }
}
