package com.ekingstar.eams.course.grade.course.calculator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.course.grade.course.CourseGrade;
import com.ekingstar.eams.course.grade.course.GradeState;
import com.ekingstar.eams.course.grade.course.MoralGradeState;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.shufe.model.course.grade.BonusLesson;
import com.shufe.model.course.grade.BonusStudent;
import com.shufe.model.course.grade.MoralGrade;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

public class MoralCalculator extends DefaultCalculator {

  private EntityQuery query = null;

  public MoralCalculator() {
    super();
    query = new EntityQuery("from MoralGrade g where g.std=:std and g.calendar=:calendar");
  }

  public void calcGA(CourseGrade grade) {
    com.shufe.model.course.grade.CourseGrade gradeImpl = (com.shufe.model.course.grade.CourseGrade) grade;
    if (null != gradeImpl.getTask() && null != gradeImpl.getGradeTask().getCourseGradeState()) {
      if (gradeImpl.getGradeTask().getCourseGradeState() instanceof MoralGradeState) {
        MoralGradeState state = (MoralGradeState) gradeImpl.getGradeTask().getCourseGradeState();
        if (null != state.getMoralGradePercent()) {
          myCalcGA(gradeImpl);
          Float GA = gradeImpl.getGA();
          if (null == GA) return;
          float moralGradePercent = state.getMoralGradePercent().floatValue();
          float ga = GA.floatValue() * (1 - moralGradePercent);
          MoralGrade moralGrade = getMoralGrade(gradeImpl.getStd(), gradeImpl.getCalendar());
          if (null != moralGrade) {
            ga += moralGrade.getScore().floatValue() * moralGradePercent;
            gradeImpl
                .setGA(reservedPrecision(ga, gradeImpl.getTask().getGradeState().getPrecision().intValue()));
          }
        } else {
          myCalcGA(grade);
        }
      } else {
        myCalcGA(grade);
      }
    }

    processBonusGrade(gradeImpl);
    processBonusTask(gradeImpl);
  }

  private void processBonusTask(com.shufe.model.course.grade.CourseGrade gradeImpl) {
    if (null != gradeImpl.getTask()) {
      EntityQuery bonusQuery = new EntityQuery(BonusLesson.class, "bl");
      bonusQuery.add(new Condition("bl.task=:task", gradeImpl.getTask()));
      List<BonusLesson> bonuses = (List<BonusLesson>) utilDao.search(bonusQuery);
      if (bonuses.isEmpty()) {
        gradeImpl.setRatio(null);
      } else {
        bonus(gradeImpl, bonuses.get(0).getRatio(), 100f);
      }
    }
  }

  private void processBonusGrade(com.shufe.model.course.grade.CourseGrade gradeImpl) {
    EntityQuery bonusQuery = new EntityQuery(BonusStudent.class, "bs");
    bonusQuery.add(new Condition("bs.std=:std", gradeImpl.getStd()));
    List bonuses = (List) utilDao.search(bonusQuery);
    if (bonuses.isEmpty()) {
      gradeImpl.setRatio(null);
    } else {
      bonus(gradeImpl, 1.2f, 75.0f);
    }
  }

  private void bonus(com.shufe.model.course.grade.CourseGrade gradeImpl, float ratio, float max) {
    // 不用加分了
    if (null != gradeImpl.getGA() && Float.compare(gradeImpl.getGA().floatValue(), max) >= 0) {
      gradeImpl.setRatio(null);
      return;
    }
    gradeImpl.setRatio((int) (ratio * 100));
    if (null != gradeImpl.getGA()) {
      float newGa = Double.valueOf(gradeImpl.getGA() * ratio).floatValue();
      if (null != gradeImpl.getTask() && null != gradeImpl.getGradeTask().getCourseGradeState()) {
        newGa = reservedPrecision(newGa, gradeImpl.getTask().getGradeState().getPrecision().intValue());
      }
      if (Double.compare(newGa, max) <= 0) {
        gradeImpl.setGA(newGa);
      } else {
        gradeImpl.setGA(max);
      }
    }
  }

  public void myCalcGA(CourseGrade grade) {
    if (null != grade.getGradeTask() && null != grade.getCourseTakeType()) {
      Float endScore = grade.getScore(new GradeType(GradeType.END));
      if (null == endScore) {
        endScore = grade.getScore(new GradeType(GradeType.DELAY));
      }

      // 按百分比计算
      GradeState state = grade.getGradeTask().getCourseGradeState();
      if (null == state) // 无法计算,放弃
        return;

      // 百分比是空的也不计算
      Map percentMap = state.getPercentMap();
      if (percentMap.isEmpty()) {
        grade.setGA(null);
        return;
      }
      // 期末或者缓考没有成绩不计算总评
      if (null == endScore) {
        Float endGradePercent = (Float) percentMap.get(new GradeType(GradeType.END));
        if (null != endGradePercent && endGradePercent.floatValue() != 0) {
          grade.setGA(null);
          return;
        }
      }
      float ga = 0;
      for (Iterator iter = percentMap.keySet().iterator(); iter.hasNext();) {
        GradeType gradeType = (GradeType) iter.next();
        Float gradeTypeScore = null;
        if (gradeType.getId().equals(GradeType.END)) {
          gradeTypeScore = endScore;
        } else {
          gradeTypeScore = grade.getScore(gradeType);
        }
        if (null != gradeTypeScore) {
          ga += state.getPercent(gradeType).floatValue() * gradeTypeScore.floatValue();
        }
      }
      grade.setGA(reservedPrecision(ga, state.getPrecision().intValue()));
    }
    return;
  }

  private MoralGrade getMoralGrade(Student std, TeachCalendar calendar) {
    Map params = new HashMap();
    params.put("std", std);
    params.put("calendar", calendar);
    query.setParams(params);
    List rs = (List) utilDao.search(query);
    if (rs.isEmpty()) return null;
    else {
      return (MoralGrade) rs.get(0);
    }
  }
}
