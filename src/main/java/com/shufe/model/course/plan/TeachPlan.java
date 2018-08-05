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
 * chaostone             2006-3-24            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.plan;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.task.TeachCommon;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;

/**
 * 培养计划/教学计划实体类<br>
 * 培养计划作为教学活动的开始，记录了什么学生，上什么课程的大致要求.<br>
 * 该实体类作为专业培养计划和个人培养计划的信息载体，以std字段是否为空区分该计划是否为学生的个人培养计划.<br>
 * <p>
 * 学生规定的范围为:所在年级/学生类别/院系/专业/专业方向/学生.<br>
 * 其中所在年级和学生类别以及院系不能为空.<br>
 * 培养计划中，每一个类课程建立一个课程组<code>CourseGroup</code>对应一类课程性质.<br>
 * 不能有重复的课程性质对应的课程组.课程组内记录了该类课程要求的学时\学分和每学期要求的学分.<br>
 * 培养计划对课程组的关系是一种多对多的关联关系.当没有培养计划关联课程组时，则删除该课程组.<br>
 * 课程组内的课程有差别(包括总的学时学分要求的一切信息)，则创建一个课程组，与原来的课程组脱离关系.
 * <p>
 * 课程组内在规定有什么课程<code>PlanCourse</code>,每个课程的可替代课程，以及hsk(汉语水平考试)约束. 该课程的先修课程和可替代课程.
 * <p>
 * 
 * @author chaostone
 */
public class TeachPlan extends LongIdObject implements Cloneable {

  private static final long serialVersionUID = -4605978447556423617L;

  /** 所在年级 */
  private String enrollTurn;

  /** 计划所属学生类别 */
  private StudentType stdType;

  /** 计划所属院系 */
  private Department department;

  /** 计划所属专业 */
  private Speciality speciality;

  /** 计划所属专业方向 */
  private SpecialityAspect aspect;

  /** 学期数量 */
  private Integer termsCount;

  /** 要求的总学时 */
  private Integer creditHour = new Integer(0);

  /** 要求的总学分（不是所有课程类别的总学分,有些学分是可选的） */
  private Float credit = new Float(0);

  /** 指导教师名称串 */
  private String teacherNames;


  /** 培养计划课程组 */
  private Set courseGroups = new HashSet();

  /** 对应学生 若不为空则为个人培养计划 */
  private Student std;

  /** 制定时间 */
  private Date createAt;

  /** 修改时间 */
  private Date modifyAt;

  /** 备注 */
  private String remark;

  /** 是否确认 */
  private Boolean isConfirm;

  public TeachPlan() {
    super();
  }

  /**
   * 依照teachcommon实例化培养计划
   * 
   * @param common
   */
  public TeachPlan(TeachCommon common) {
    this.setEnrollTurn(common.getEnrollTurn());
    this.setStdType(common.getStdType());
    this.setDepartment(common.getDepart());
    this.setSpeciality(common.getSpeciality());
    this.setAspect(common.getAspect());
  }

  /**
   * 依照班级属性,实例化一个培养计划
   * 
   * @param adminClass
   */
  public TeachPlan(AdminClass adminClass) {
    this.setEnrollTurn(adminClass.getEnrollYear());
    this.setStdType(adminClass.getStdType());
    this.setDepartment(adminClass.getDepartment());
    this.setSpeciality(adminClass.getSpeciality());
    this.setAspect(adminClass.getAspect());
  }

  /**
   * 依照各个计划主要元素实例化培养计划
   * 
   * @param enrollTurn
   * @param stdTypeId
   * @param departmentId
   * @param specialityId
   * @param aspectId
   * @param stdId
   */
  public TeachPlan(String enrollTurn, Long stdTypeId, Long departmentId, Long specialityId, Long aspectId,
      Long stdId) {
    this.setEnrollTurn(enrollTurn);
    this.setStdType((StudentType) EntityUtils.getEntity(StudentType.class, stdTypeId));
    this.setDepartment(new Department(departmentId));
    this.setSpeciality(new Speciality(specialityId));
    this.setAspect(new SpecialityAspect(aspectId));
    this.setStd(new Student(stdId));
  }

  /**
   * 复制培养计划的主要属性（入学年份，学生类别、院系、专业、方向、学生）
   * 
   * @return
   */
  public TeachPlan getSimpleIdCopy() {
    TeachPlan plan = new TeachPlan();
    plan.setEnrollTurn(getEnrollTurn());
    if (null != getStdType()) {
      plan.setStdType((StudentType) EntityUtils.getEntity(StudentType.class, getStdType().getId()));
    }
    if (null != getDepartment()) {
      plan.setDepartment(new Department(getDepartment().getId()));
    }
    if (null != getSpeciality()) {
      plan.setSpeciality(new Speciality(getSpeciality().getId()));
    }
    if (null != getAspect()) {
      plan.setAspect(new SpecialityAspect(getAspect().getId()));
    }
    if (null != getStd()) {
      plan.setStd(new Student(getStd().getId()));
    }
    return plan;
  }

  public Set getCourseGroups() {
    return courseGroups;
  }

  public void setCourseGroups(Set coursesGroups) {
    this.courseGroups = coursesGroups;
  }

  public Integer getCreditHour() {
    return creditHour;
  }

  public void setCreditHour(Integer creditHours) {
    this.creditHour = creditHours;
  }

  public Float getCredit() {
    return credit;
  }

  public void setCredit(Float credit) {
    this.credit = credit;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public String getEnrollTurn() {
    return enrollTurn;
  }

  public void setEnrollTurn(String enrollTurn) {
    this.enrollTurn = enrollTurn;
  }

  public Boolean getIsConfirm() {
    return isConfirm;
  }

  public void setIsConfirm(Boolean isConfirm) {
    this.isConfirm = isConfirm;
  }

  public Integer getTermsCount() {
    return termsCount;
  }

  public void setTermsCount(Integer termsCount) {
    this.termsCount = termsCount;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Speciality getSpeciality() {
    return speciality;
  }

  public void setSpeciality(Speciality speciality) {
    this.speciality = speciality;
  }

  public SpecialityAspect getAspect() {
    return aspect;
  }

  public void setAspect(SpecialityAspect specialityAspect) {
    this.aspect = specialityAspect;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student student) {
    this.std = student;
  }

  public StudentType getStdType() {
    return stdType;
  }

  public void setStdType(StudentType studentType) {
    this.stdType = studentType;
  }

  public String getTeacherNames() {
    return teacherNames;
  }

  public void setTeacherNames(String teacherNames) {
    this.teacherNames = teacherNames;
  }

  public Date getCreateAt() {
    return createAt;
  }

  public Date getModifyAt() {
    return modifyAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }

  public void setModifyAt(Date modifyAt) {
    this.modifyAt = modifyAt;
  }

  /**
   * 添加课程组
   * 
   * @param courseGroup
   */
  public void addCourseGroup(CourseGroup courseGroup) {
    this.getCourseGroups().add(courseGroup);
    courseGroup.teachPlan = this;
  }

  /**
   * 添加一组课程组
   * 
   * @param groups
   */
  public void addCourseGroups(Collection groups) {
    for (Iterator iter = groups.iterator(); iter.hasNext();)
      addCourseGroup((CourseGroup) iter.next());
  }

  /**
   * 删除课程组
   * 
   * @param courseGroup
   */
  public void removeCourseGroup(CourseGroup courseGroup) {
    this.getCourseGroups().remove(courseGroup);
  }

  public void removeAllCourseGroup() {
    getCourseGroups().clear();
  }

  /**
   * 统计总学分
   */
  public float statOverallCredit() {
    float overallCredit = 0;
    for (Iterator iter = getCourseGroups().iterator(); iter.hasNext();) {
      CourseGroup group = (CourseGroup) iter.next();
      overallCredit += group.getCredit().floatValue();
    }
    return overallCredit;
  }

  /**
   * 统计总学时
   */
  public int statOverallCreditHour() {
    int overallCreditHour = 0;
    for (Iterator iter = getCourseGroups().iterator(); iter.hasNext();) {
      CourseGroup group = (CourseGroup) iter.next();
      overallCreditHour += group.getCreditHour().intValue();
    }
    return overallCreditHour;
  }

  public boolean isStdTeachPlan() {
    return null != getStd();
  }

  /**
   * 根据自动计算，调整总学分和总学时
   * 
   * @return true 表明做过调整
   */
  public void autoAdjustCreditAndHour() {
    int creditHour = statOverallCreditHour();
    float credit = statOverallCredit();
    setCredit(new Float(credit));
    setCreditHour(new Integer(creditHour));
  }

  /**
   * 比较两个培养计划是否在业务上是同一个培养计划<br>
   * 没有比较学号<br>
   * 
   * @param plan
   * @return
   */
  public boolean isSamePlan(TeachPlan plan) {
    if (!(plan instanceof TeachPlan)) { return false; }
    TeachPlan rhs = (TeachPlan) plan;
    return new EqualsBuilder().append(getEnrollTurn(), rhs.getEnrollTurn())
        .append(getStdType(), rhs.getStdType()).append(getDepartment(), rhs.getDepartment())
        .append(ValidEntityPredicate.INSTANCE.evaluate(getSpeciality()) ? getSpeciality() : null,
            ValidEntityPredicate.INSTANCE.evaluate(rhs.getSpeciality()) ? rhs.getSpeciality() : null)
        .append(ValidEntityPredicate.INSTANCE.evaluate(getAspect()) ? getAspect() : null,
            ValidEntityPredicate.INSTANCE.evaluate(rhs.getAspect()) ? rhs.getAspect() : null)
        .isEquals();
  }

  /**
   * 克隆一个相似的培养计划<br>
   * 使它们引用相同的组<br>
   */
  public Object clone() throws CloneNotSupportedException {
    TeachPlan plan = new TeachPlan();
    plan.setEnrollTurn(getEnrollTurn());
    plan.setStdType(getStdType());
    plan.setDepartment(getDepartment());
    plan.setSpeciality(getSpeciality());
    plan.setAspect(getAspect());
    plan.setCredit(getCredit());
    plan.setCreditHour(getCreditHour());
    plan.setTeacherNames(getTeacherNames());
    plan.setRemark(getRemark());
    plan.setTermsCount(getTermsCount());

    plan.addCourseGroups(getCourseGroups());

    plan.setIsConfirm(Boolean.FALSE);
    plan.setCreateAt(new Date(System.currentTimeMillis()));
    plan.setModifyAt(new Date(System.currentTimeMillis()));
    return plan;
  }

  /**
   * 获得培养计划中的课程<code>PlanCourse</code><br>
   * 
   * @return
   */
  public List getPlanCourses() {
    if (null == getCourseGroups()) {
      return Collections.EMPTY_LIST;
    } else {
      List planCourses = new ArrayList();
      for (Iterator iter = getCourseGroups().iterator(); iter.hasNext();) {
        CourseGroup group = (CourseGroup) iter.next();
        planCourses.addAll(group.getPlanCourses());
      }
      return planCourses;
    }
  }

  // FIXME 2006-9-23
  // FIXME 2008-7-16
  public List getCourses(String terms) {
    if (StringUtils.isEmpty(terms)) { return Collections.EMPTY_LIST; }
    Set courses = new HashSet();
    Integer[] findTerm = SeqStringUtil.transformToInteger(terms);
    if (null == findTerm || findTerm.length == 0) { return Collections.EMPTY_LIST; }
    for (Iterator it = getPlanCourses().iterator(); it.hasNext();) {
      PlanCourse planCourse = (PlanCourse) it.next();
      List<Integer> planTerms = planCourse.getTerms().getTermList();
      if (planTerms.isEmpty()) {
        continue;
      }
      for (int i = 0; i < findTerm.length; i++) {
        for (int j = 0; j < planTerms.size(); j++) {
          if (findTerm[i].equals(planTerms.get(j))) {
            courses.add(planCourse.getCourse());
          }
        }
      }
    }
    return new ArrayList(courses);
  }

  /**
   * 根据课程类别查找课程组
   * 
   * @param type
   * @return
   */
  public CourseGroup getCourseGroup(CourseType type) {
    if (null == getCourseGroups()) return null;
    for (Iterator iter = getCourseGroups().iterator(); iter.hasNext();) {
      CourseGroup group = (CourseGroup) iter.next();
      if (group.getCourseType().equals(type)) return group;
    }
    return null;
  }

  public PlanCourse getPlanCourse(Course course) {
    if (null == getCourseGroups()) return null;
    else {
      for (Iterator iter = getCourseGroups().iterator(); iter.hasNext();) {
        CourseGroup group = (CourseGroup) iter.next();
        for (Iterator iterator = group.getPlanCourses().iterator(); iterator.hasNext();) {
          PlanCourse planCourse = (PlanCourse) iterator.next();
          if (planCourse.getCourse().equals(course)) { return planCourse; }
        }
      }
      return null;
    }
  }

  public List getOrderedGroups() {
    List groups = new ArrayList(getCourseGroups());
    Collections.sort(groups, new PropertyComparator("courseType.priority"));
    return groups;
  }

  public boolean hasCourse(Course course) {
    for (Iterator iterator = getCourseGroups().iterator(); iterator.hasNext();) {
      CourseGroup group = (CourseGroup) iterator.next();
      for (Iterator iterator2 = group.getPlanCourses().iterator(); iterator2.hasNext();) {
        PlanCourse planCourse = (PlanCourse) iterator2.next();
        if (planCourse.getCourse().equals(course)) return true;
      }
    }
    return false;
  }

  public List getCourses() {
    List courses = new ArrayList();
    for (Iterator it = getPlanCourses().iterator(); it.hasNext();) {
      PlanCourse planCourse = (PlanCourse) it.next();
      courses.add(planCourse.getCourse());
    }
    return courses;
  }
}
