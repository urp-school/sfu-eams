package com.shufe.service.course.achivement;

/**
 * 成绩结果
 * 
 * @author chaostone
 */
public class GradeResult {

  /** 得分 */
  public final Float score;

  public final int unpassedCourse;

  /** 是否通过 */
  public boolean passed;
  
  public final String content;

  public GradeResult(Float score, int unpassedCourse, String content) {
    super();
    this.score = score;
    this.unpassedCourse = unpassedCourse;
    this.content = content;
    this.passed = (unpassedCourse ==0);
  }

  public GradeResult(Float score, int unpassed) {
    this(score, unpassed, null);
  }
}
