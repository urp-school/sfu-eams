package com.shufe.model.course.achivement;

import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 综合测评结果
 * 
 * @author chaostone
 */
public class GradeAchivement extends LongIdObject {

  private static final long serialVersionUID = -8040820831348670731L;

  /**
   * 开始学期
   */
  private TeachCalendar fromSemester;
  /**
   * 截至学期
   */
  private TeachCalendar toSemester;

  /**
   * 学生
   */
  private Student std;

  /** 当时学生所在年级 */
  private String grade;

  /** 当时学生所在院系 */
  private Department department;

  /** 当时学生所在专业 */
  private Speciality major;

  /** 当时学生所在班级 */
  private AdminClass adminClass;

  /** 德育成绩 */
  private Float moralScore = new Float(0);

  /** 智育成绩 */
  private Float ieScore = new Float(0);

  /** CET4成绩 */
  private Float cet4Score = new Float(0);

  /** 体育成绩 */
  private Float peScore = new Float(0);

  /** 总成绩 */
  private Float score = new Float(0);

  /** 总绩点 */
  private Float gpa = new Float(0);

  /** 智育成绩是否全部及格(intellectual education) */
  private Boolean iePassed;

  /** 体育成绩是否全部及格 */
  private Boolean pePassed;

  /** 不及格门数 */
  private Integer unpassed;

  /** CET4是否通过 */
  private Boolean cet4Passed;

  private Date createdAt;

  private Date updatedAt;

  private boolean confirmed;

  private String remark;

  /**
   * 体育和智育是否全部及格
   * 
   * @return
   */
  public boolean getGradePassed() {
    return iePassed && pePassed;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public Float getScore() {
    return score;
  }

  public void setScore(Float score) {
    this.score = score;
  }

  public Float getMoralScore() {
    return moralScore;
  }

  public void setMoralScore(Float moralScore) {
    this.moralScore = moralScore;
  }

  public Float getIeScore() {
    return ieScore;
  }

  public void setIeScore(Float ieScore) {
    this.ieScore = ieScore;
  }

  public Float getPeScore() {
    return peScore;
  }

  public void setPeScore(Float peScore) {
    this.peScore = peScore;
  }

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

  public Boolean getIePassed() {
    return iePassed;
  }

  public void setIePassed(Boolean iePassed) {
    this.iePassed = iePassed;
  }

  public Boolean getPePassed() {
    return pePassed;
  }

  public void setPePassed(Boolean pePassed) {
    this.pePassed = pePassed;
  }

  public Float getCet4Score() {
    return cet4Score;
  }

  public void setCet4Score(Float cet4Score) {
    this.cet4Score = cet4Score;
  }

  public Boolean getCet4Passed() {
    return cet4Passed;
  }

  public void setCet4Passed(Boolean cet4Passed) {
    this.cet4Passed = cet4Passed;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public Speciality getMajor() {
    return major;
  }

  public void setMajor(Speciality major) {
    this.major = major;
  }

  public AdminClass getAdminClass() {
    return adminClass;
  }

  public void setAdminClass(AdminClass adminClass) {
    this.adminClass = adminClass;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public boolean isConfirmed() {
    return confirmed;
  }

  public void setConfirmed(boolean confirmed) {
    this.confirmed = confirmed;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Float getGpa() {
    return gpa;
  }

  public void setGpa(Float gpa) {
    this.gpa = gpa;
  }

  public Integer getUnpassed() {
    return unpassed;
  }

  public void setUnpassed(Integer unpassed) {
    this.unpassed = unpassed;
  }

}
