package com.shufe.service.course.achivement.impl;

import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.achivement.GpaProvider;

public class GpaProviderImpl extends BasicService implements GpaProvider {

  public Float getGPA(Student std, List<TeachCalendar> calendars) {

    EntityQuery entityQuery = new EntityQuery(CourseGrade.class, "grade");
    entityQuery.add(new Condition("grade.std=:std", std));
    entityQuery.add(new Condition("grade.majorType.id = :majorType", MajorType.FIRST));
    entityQuery.add(new Condition("grade.status = :status", Grade.PUBLISHED));
    entityQuery.add(new Condition("grade.calendar in (:calendars)", calendars));
    entityQuery.setSelect("select sum(grade.credit*grade.GP),sum(grade.credit)");
    List<?> rs = (List<?>) utilDao.search(entityQuery);
    if (rs.isEmpty()) {
      return new Float(0);
    } else {
      Object[] datas =  (Object[])rs.get(0);
      Number gp = (Number) datas[0];
      Number credits = (Number) datas[1];
      if (null == gp || null == credits || credits.intValue() == 0) {
        return new Float(0);
      } else {
        return new Float(gp.floatValue() / credits.floatValue());
      }
    }
  }
}
