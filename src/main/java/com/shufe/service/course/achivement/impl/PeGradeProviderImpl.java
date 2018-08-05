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
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.achivement.GradeResult;
import com.shufe.service.course.achivement.PeGradeProvider;

/**
 * 体育总评成绩提供者
 * 
 * @author chaostone
 */
public class PeGradeProviderImpl extends BasicService implements PeGradeProvider {

  @SuppressWarnings("unchecked")
  @Override
  public GradeResult getGrade(Student std, List<TeachCalendar> calendars) {

//	Student stdNew =IdCardStudent(std);
	int gradeLevel = GaScoreProvider.getGradeLevel(std, calendars);
    EntityQuery query = new EntityQuery(CourseGrade.class, "cg");
    query.add(new Condition("cg.std=:std", std));
    if (gradeLevel < 3) query.add(new Condition("cg.calendar in (:calendars)", calendars));
    query.add(new Condition("cg.majorType.id=:majorType", MajorType.FIRST));
    query.add(new Condition("cg.course.category.id = :courseCategoryId", CourseCategory.PHYCIALID));
    query.add(new Condition("cg.status=:status", Grade.PUBLISHED));
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
        sb.append(calendar.getYear()).append('(');
        sb.append(calendar.getTerm()).append(") \n");
      }

      Float score = GaScoreProvider.getScore(courseGrade);
      if (score != null) ga += score * courseGrade.getCourse().getCredits();

      if (!GaScoreProvider.isPassed(score)) unpassed += 1;
      credit += courseGrade.getCourse().getCredits();
      sb.append(GradeSummary.summary(courseGrade, score)).append("\n");
    }
    ga = (credit != 0) ? ga / credit : 0;
    return new GradeResult(ga, unpassed, sb.toString());
  }
  /**
   * 找出与专升本学生身份证号相同的专科学生,否则返回原学生
   * 
   * @param std
   * @param calendars
   * @return
   */
//  public  Student IdCardStudent(Student std) {
//	  if (std.getStdType().getId().equals(9L)){
//		  Student stdzk = new Student();
//		  String idCard =std.getBasicInfo().getIdCard();
//	  	  Long stdId=std.getId();
//	  	  EntityQuery entityQuery = new EntityQuery(Student.class, "student");
//	  	  entityQuery.add(new Condition("student.basicInfo.idCard = :idCard", idCard));
//	  	  entityQuery.add(new Condition("student.id != :stdId", stdId));
//	  	  List<Student> rs = (List<Student>) utilDao.search(entityQuery);
//	  	  if (rs.isEmpty()) return std;//外校专升本
//	  	  Student stds = rs.get(0);
//	  	  stdzk = (Student) stds;
//	  	  return stdzk;
//  	  }
//	  return std;
//  }
//
 }


