/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-12-26            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.ekingstar.eams.course.grade.converter.ConverterFactory;
import com.ekingstar.eams.course.grade.course.GradeTask;
import com.ekingstar.eams.course.grade.course.calculator.CalculatorFactory;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.security.User;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.grade.alter.CourseGradeAlterInfo;
import com.shufe.model.course.grade.gp.GradePointRule;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Course;

/**
 * 课程成绩
 * 
 * @author chaostone
 */
public class CourseGrade extends AbstractGrade implements com.ekingstar.eams.course.grade.course.CourseGrade {

  private static final long serialVersionUID = 1L;

  /**
   * 课程序号,向后兼容
   */
  private String taskSeqNo;

  /**
   * 教学任务
   */
  private TeachTask task;

  /**
   * 课程
   */
  private com.ekingstar.eams.system.baseinfo.Course course;

  /**
   * 学分
   */
  private Float credit;

  /**
   * grade point绩点
   */
  private Float GP;

  /**
   * grade average 总评成绩
   */
  private Float GA;

  /**
   * 课程类别
   */
  private CourseType courseType;

  /**
   * 上课信息
   */
  private CourseTakeType courseTakeType;

  /**
   * 考试成绩
   */
  private Set examGrades;

  /**
   * 上浮比例
   */
  private Integer ratio;

  public String getExamStatusNames() {
    if (null != getExamGrades() && !getExamGrades().isEmpty()) {
      StringBuffer buf = new StringBuffer(10);
      for (Iterator iter = getExamGrades().iterator(); iter.hasNext();) {
        ExamGrade examGrade = (ExamGrade) iter.next();
        buf.append(examGrade.getExamStatus().getName()).append(",");
      }
      buf.deleteCharAt(buf.length() - 1);
      return buf.toString();
    } else {
      return "";
    }
  }

  /**
   * 1:第一专业;2：第二专业
   * 
   * @see MajorType#FIRST
   * @see MajorType#SECOND
   */
  private MajorType majorType;

  private transient CourseTake courseTake;

  public CourseGrade() {
    examGrades = new HashSet();
  }

  /**
   * 依照上课名单进行实例化课程成绩
   * 
   * @param take
   */
  public CourseGrade(CourseTake take) {
    setStd(take.getStudent());
    setTask(take.getTask());
    setTaskSeqNo(task.getSeqNo());
    setCalendar(task.getCalendar());
    setCourse(task.getCourse());
    setCourseType(task.getCourseType());
    setCourseTakeType(take.getCourseTakeType());
    this.examGrades = new HashSet();
  }

  /**
   * 更新分数,在不一致时添加更改记录
   * 
   * @see CourseGradeAlterInfo
   */
  public void updateScore(Float score, User who) {
    boolean dif = false;
    if (getScore() != score) {
      if (null != getScore() && null != score) {
        if (0 != getScore().compareTo(score)) {
          dif = true;
        }
      } else {
        dif = true;
      }
    }
    if (dif) {
      getAlterInfos().add(new CourseGradeAlterInfo(this, getScore(), score, who));
    }
    setScore(score);
  }

  /**
   * 更新是否及格的状态
   * 
   * @see MarkStyle#isPass(Float)
   */
  public CourseGrade updatePass() {
    setIsPass(markStyle.isPass(score));
    for (Iterator iter = examGrades.iterator(); iter.hasNext();) {
      ExamGrade examGrade = (ExamGrade) iter.next();
      examGrade.setIsPass(markStyle.isPass(examGrade.getScore()));
    }
    return this;
  }

  /**
   * 以及考试成绩的确认状况,更新总成绩的确认状况
   */
  public CourseGrade updateConfirmed() {
    boolean pass = true;
    for (Iterator iter = examGrades.iterator(); iter.hasNext();) {
      ExamGrade examGrade = (ExamGrade) iter.next();
      if (Boolean.FALSE.equals(examGrade.getIsConfirmed())) {
        pass = false;
        break;
      }
    }
    if (pass) {
      // 如果已经发布了则不再更改状态
      if (Boolean.FALSE.equals(this.getIsPublished())) {
        setStatus(new Integer(Grade.CONFIRMED));
      }
    } else {
      setStatus(new Integer(Grade.NEW));
    }
    return this;
  }

  /**
   * 依照考试成绩计算最终成绩<br>
   * 请在调用该方法之前调用 this{@link #calcGA(int)} 有补考成绩，算补考成绩 如果没有总评成绩则查找最好成绩
   */
  public CourseGrade calcScore() {
    CalculatorFactory.getCalculator().calcFinal(this);
    return this;
  }

  /**
   * 得到最好的可以转化为最终成绩的考试成绩
   * 
   * @return
   */
  public Float getBestScoreCanConvertToFinal() {
    Float best = null;
    for (Iterator iter = getExamGrades().iterator(); iter.hasNext();) {
      ExamGrade examGrade = (ExamGrade) iter.next();
      if (!GradeType.EQ_FINAL_IDS.contains(examGrade.getGradeType().getId())) continue;
      if (null == best) {
        best = examGrade.getScore();
      }
      if (null != examGrade.getScore()) {
        if (examGrade.getScore().compareTo(best) > -1) {
          best = examGrade.getScore();
        }
      }
    }
    return best;
  }

  /**
   * 计算总评成绩<br>
   * 在要求的精度下，对期末转入总评的成绩进行格式化 {@link #calcScore()}
   */
  public CourseGrade calcGA() {
    CalculatorFactory.getCalculator().calcGA(this);
    return this;
  }

  /**
   * 依照绩点规则计算平均绩点
   * 
   * @param rule
   */
  public CourseGrade calcGP(GradePointRule rule) {
    if (null != rule) {
      setGP(rule.calcGP(this));
    }
    return this;
  }

  /**
   * 找到第一个出现该成绩类别的成绩
   * 
   * @param gradeType
   * @return
   */
  public ExamGrade getExamGrade(GradeType gradeType) {
    if (null == examGrades || examGrades.isEmpty()) { return null; }
    for (Iterator iter = examGrades.iterator(); iter.hasNext();) {
      ExamGrade examGrade = (ExamGrade) iter.next();
      if (examGrade.getGradeType().equals(gradeType)) { return examGrade; }
    }
    return null;
  }

  /**
   * 是否含有某种类型的成绩
   * 
   * @param gradeType
   * @return
   */
  public boolean hasGrade(Long gradeTypeId) {
    if (null == examGrades || examGrades.isEmpty()) { return false; }
    GradeType gradeType = new GradeType(gradeTypeId);
    for (Iterator iter = examGrades.iterator(); iter.hasNext();) {
      ExamGrade examGrade = (ExamGrade) iter.next();
      if (examGrade.getGradeType().equals(gradeType)) { return true; }
    }
    return false;
  }

  /**
   * 添加考试成绩
   * 
   * @param examGrade
   */
  public void addExamGrade(ExamGrade examGrade) {
    this.getExamGrades().add(examGrade);
    examGrade.setCourseGrade(this);
  }

  /**
   * 查询各种成绩
   * 
   * @param gradeType
   * @return
   */
  public Float getScore(GradeType gradeType, Integer status) {
    Float score = null;
    if (gradeType.getId().equals(GradeType.GA)) {
      score = getGA();
    } else if (gradeType.getId().equals(GradeType.FINAL)) {
      score = getScore();
    } else {
      if (null == examGrades || examGrades.isEmpty()) { return null; }
      for (Iterator iter = examGrades.iterator(); iter.hasNext();) {
        ExamGrade examGrade = (ExamGrade) iter.next();
        if (examGrade.getGradeType().equals(gradeType)) {
          if (null == status || status.equals(examGrade.getStatus())) {
            score = examGrade.getScore();
          }
          break;
        }
      }
    }
    return score;
  }

  /**
   * 查询各种成绩
   * 
   * @param gradeType
   * @return
   */
  public Float getScore(GradeType gradeType) {
    return getScore(gradeType, null);
  }

  /**
   * 查询各种成绩的显示值 {@link #getScore(GradeType)}
   * 
   * @param gradeType
   * @return
   */
  public String getPublishedScoreDisplay(GradeType gradeType) {
    return ConverterFactory.getConverter().convert(getScore(gradeType, new Integer(Grade.PUBLISHED)),
        this.getMarkStyle());
  }

  /**
   * 查询各种成绩的显示值 {@link #getScore(GradeType)}
   * 
   * @param gradeType
   * @return
   */
  public String getScoreDisplay(GradeType gradeType) {
    return ConverterFactory.getConverter().convert(getScore(gradeType), this.getMarkStyle());
  }

  /**
   * 是否最终成绩就是总评成绩
   * 
   * @return
   */
  public Boolean getScoreIsGA() {
    if (getScore() == null && getGA() == null) {
      return Boolean.TRUE;
    } else if (getScore() != null && getGA() != null) {
      return Boolean.valueOf(getScore().equals(getGA()));
    } else {
      return Boolean.FALSE;
    }
  }

  /**
   * 查询各种成绩的显示值
   * 
   * @param gradeType
   * @return
   */
  public String getScoreDisplay(Long gradeTypeId) {
    return getScoreDisplay(new GradeType(gradeTypeId));
  }

  public com.ekingstar.eams.system.baseinfo.Course getCourse() {
    return course;
  }

  public void setCourse(com.ekingstar.eams.system.baseinfo.Course course) {
    this.course = course;
  }

  public TeachTask getTask() {
    return task;
  }

  public GradeTask getGradeTask() {
    return task;
  }

  public void setTask(TeachTask task) {
    this.task = task;
  }

  public CourseType getCourseType() {
    return courseType;
  }

  public void setCourseType(CourseType courseType) {
    this.courseType = courseType;
  }

  public Float getCredit() {
    return credit;
  }

  public Float getCreditAcquired() {
    if (Boolean.TRUE.equals(getIsPass())) {
      return getCredit();
    } else {
      return new Float(0);
    }
  }

  public void setCredit(Float credit) {
    this.credit = credit;
  }

  public String getTeacherNames() {
    if (null == getTask()) {
      return "";
    } else {
      return task.getArrangeInfo().getTeacherNames();
    }
  }

  public Float getGA() {
    return GA;
  }

  public void setGA(Float ga) {
    GA = ga;
  }

  public Float getGP() {
    return GP;
  }

  public void setGP(Float gp) {
    GP = gp;
  }

  public Set getExamGrades() {
    return examGrades;
  }

  public void setExamGrades(Set examGrades) {
    this.examGrades = examGrades;
  }

  public CourseTakeType getCourseTakeType() {
    return courseTakeType;
  }

  public void setCourseTakeType(CourseTakeType courseTakeType) {
    this.courseTakeType = courseTakeType;
  }

  public MajorType getMajorType() {
    return majorType;
  }

  public void setMajorType(MajorType majorType) {
    this.majorType = majorType;
  }

  public String getTaskSeqNo() {
    return taskSeqNo;
  }

  public void setTaskSeqNo(String taskSeqNo) {
    this.taskSeqNo = taskSeqNo;
  }

  public CourseTake getCourseTake() {
    return courseTake;
  }

  public void setCourseTake(CourseTake courseTake) {
    this.courseTake = courseTake;
  }

  public Integer getRatio() {
    return ratio;
  }

  public void setRatio(Integer ratio) {
    this.ratio = ratio;
  }

}
