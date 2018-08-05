package com.shufe.service.course.achivement.impl;

import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.course.grade.other.OtherGrade;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.achivement.Cet4GradeProvider;
import com.shufe.service.course.achivement.GradeResult;

public class Cet4GradeProviderImpl extends BasicService implements Cet4GradeProvider {

  @Override
  public GradeResult getGrade(Student std, List<TeachCalendar> calendars) {
    EntityQuery query = new EntityQuery(OtherGrade.class, "grade");
    query.add(new Condition("grade.std.id=" + std.getId()));
    // FIXME 全国大学英语四级 id=2
    query.add(new Condition("grade.category.id=2"));

    Float score = null;
    boolean passed = false;
    @SuppressWarnings("unchecked")
    List<OtherGrade> rs = (List<OtherGrade>) utilService.search(query);
    for (OtherGrade grade : rs) {
      if (grade.getIsPass()) passed = true;
      if (score == null) {
        score = grade.getScore();
      } else {
        if (Float.compare(grade.getScore(), score) > 0) score = grade.getScore();
      }
    }
    GradeResult result = new GradeResult(score, 0);
    result.passed = passed;
    return result;
  }
}
