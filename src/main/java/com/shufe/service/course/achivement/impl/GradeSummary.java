package com.shufe.service.course.achivement.impl;

import com.ekingstar.eams.system.baseinfo.Course;
import com.shufe.model.course.grade.CourseGrade;

/**
 * 提取成绩中的摘要信息
 * 
 * @author chaostone
 */
public class GradeSummary {

  public static String summary(CourseGrade grade, Float score) {
    StringBuilder sb = new StringBuilder();
    Course course = grade.getCourse();
    sb.append(course.getCode()).append(",").append(course.getName()).append(",").append(GaScoreProvider.getCredits(grade.getStd(), grade.getCourse()))
        .append(":");

    if (score != null) sb.append(score);
    else sb.append("无");

    if (!GaScoreProvider.isPassed(score)) sb.append("  -- F");
    return sb.toString();
  }
}
