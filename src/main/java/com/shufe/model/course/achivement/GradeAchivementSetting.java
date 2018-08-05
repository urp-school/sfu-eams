package com.shufe.model.course.achivement;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 综合测评设置
 * 
 * @author chaostone
 */
public class GradeAchivementSetting extends LongIdObject {

  private static final long serialVersionUID = 7478054358992611793L;

  /** 名称 */
  private String name;

  /** 适应年级 */
  private String grades;

  /** 开始学年学期 */
  private TeachCalendar fromSemester;

  /** 截至学年学期 */
  private TeachCalendar toSemester;
  
  /** 是否发布 */
  private boolean published;

  public TeachCalendar getFromSemester() {
    return fromSemester;
  }

  public void setFromSemester(TeachCalendar fromSemester) {
    this.fromSemester = fromSemester;
  }

  public TeachCalendar getToSemester() {
    return toSemester;
  }

  public void setToSemester(TeachCalendar toSemester) {
    this.toSemester = toSemester;
  }

  public boolean isPublished() {
    return published;
  }

  public void setPublished(boolean published) {
    this.published = published;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGrades() {
    return grades;
  }

  public void setGrades(String grades) {
    this.grades = grades;
  }

}
