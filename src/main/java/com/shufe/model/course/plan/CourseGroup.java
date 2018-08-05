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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.math.NumberUtils;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.model.system.baseinfo.Course;

/**
 * 培养计划课程组<br>
 * 课程组包含了一组同类课程性质<code>CourseType</code>的课程<code>Course</code>,<br>
 * 和对该组的学时学分总的要求,以及每个学期要求的学分数量和每个学期的学时数量<br>
 * <p>
 * 一个课程组可以与多个培养计划关联.
 * 
 * @author chaostone
 */
public class CourseGroup extends LongIdObject implements Comparable, Cloneable {
  private static final long serialVersionUID = -1172447762663882759L;

  /** 课程类别 */
  private CourseType courseType = new CourseType();

  /** 父课程类别 */
  private CourseGroup parent = null;

  /** 要求的总学时 */
  private Integer creditHour = new Integer(0);

  /** 要求的总学分（不是总学分,有些学分是可选的） */
  private Float credit = new Float(0);

  /** 对应培养计划集合 */
  protected TeachPlan teachPlan = null;

  /** 课程组课程集合 */
  protected Set planCourses = new HashSet();

  /** 课程组每学期对应学分 */
  protected String creditPerTerms;

  /** 课程组每学期对应周课时 */
  protected String weekHourPerTerms;

  /** 备注 */
  private String remark;

  /**
   * 获得没学期学分列表
   * 
   * @return
   */
  public List getCreditList() {
    String[] credits = StringUtils.split(getCreditPerTerms(), ",");
    return Arrays.asList(credits);
  }

  public List getOrderedPlanCourses() {
    List courses = new ArrayList(getPlanCourses());
    Collections.sort(courses, new PropertyComparator("course.code"));
    return courses;
  }

  public Set getPlanCourses() {
    return planCourses;
  }

  public void setPlanCourses(Set courses) {
    this.planCourses = courses;
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

  public void setCredit(Float credit) {
    this.credit = credit;
  }

  public Integer getCreditHour() {
    return creditHour;
  }

  public void setCreditHour(Integer creditHour) {
    this.creditHour = creditHour;
  }

  public String getCreditPerTerms() {
    return creditPerTerms;
  }

  public void setCreditPerTerms(String creditPerTerms) {
    this.creditPerTerms = creditPerTerms;
  }

  public CourseType getParentCourseType() {
    return (null == parent) ? null : parent.getCourseType();
  }

  public CourseGroup getParent() {
    return parent;
  }

  public void setParent(CourseGroup parent) {
    this.parent = parent;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
 
  public TeachPlan getTeachPlan() {
    return teachPlan;
  }

  public void setTeachPlan(TeachPlan teachPlan) {
    this.teachPlan = teachPlan;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("id", this.id).append("courseType", this.courseType.getId())
        .append("credit", this.credit).append("creditPerTerms", this.creditPerTerms)
        .append("creditHour", this.creditHour).append("remark", this.remark).toString();
  }

  /**
   * @return Returns the weekHourPerTerms.
   */
  public String getWeekHourPerTerms() {
    return weekHourPerTerms;
  }

  /**
   * @param weekHourPerTerms
   *          The weekHourPerTerms to set.
   */
  public void setWeekHourPerTerms(String weekHourPerTerms) {
    this.weekHourPerTerms = weekHourPerTerms;
  }

  /**
   * 默认依据课程组所属的课程性质的优先级进行排序
   * 
   * @see java.lang.Comparable#compareTo(Object)
   */
  public int compareTo(Object object) {
    CourseGroup other = (CourseGroup) object;
    int myPriority = (null == getCourseType().getPriority()) ? 0 : getCourseType().getPriority().intValue();
    int otherPrioirty = (null == other.getCourseType().getPriority()) ? 0
        : other.getCourseType().getPriority().intValue();
    return myPriority - otherPrioirty;
  }

  /**
   * 全盘比较.
   * 
   * @see java.lang.Object#equals(Object)
   */
  public boolean isSameGroup(Object object) {
    if (!(object instanceof CourseGroup)) { return false; }
    CourseGroup other = (CourseGroup) object;
    // it will handle null value
    return new EqualsBuilder().append(getCredit(), other.getCredit())
        .append(getCreditHour(), other.getCreditHour()).append(getCourseType(), other.getCourseType())
        .append(getParentCourseType(), other.getParentCourseType()).append(getRemark(), other.getRemark())
        .append(getCreditPerTerms(), other.getCreditPerTerms())
        .append(getWeekHourPerTerms(), other.getWeekHourPerTerms())
        .append(getPlanCourses(), other.getPlanCourses()).isEquals();
  }

  public void addPlanCourse(PlanCourse course) {
    course.setCourseGroup(this);
    getPlanCourses().add(course);
  }

  public void removePlanCourse(PlanCourse course) {
    getPlanCourses().remove(course);
  }

  public void removePlanCourse(Course course) {
    PlanCourse find = null;
    for (Iterator iter = getPlanCourses().iterator(); iter.hasNext();) {
      PlanCourse planCourse = (PlanCourse) iter.next();
      if (planCourse.getCourse().equals(course)) {
        find = planCourse;
        break;
      }
    }
    if (null != find) {
      getPlanCourses().remove(find);
    }
  }

  /**
   * @see java.lang.Object#clone()
   */
  public Object clone() {
    CourseGroup group = new CourseGroup();
    try {
      PropertyUtils.copyProperties(group, this);
      group.setId(null);
      group.setPlanCourses(new HashSet());
      for (Iterator iter = this.getPlanCourses().iterator(); iter.hasNext();)
        group.addPlanCourse((PlanCourse) ((PlanCourse) iter.next()).clone());
    } catch (Exception e) {
      throw new RuntimeException("exception in clone course group");
    }
    return group;

  }

  /**
   * @see java.lang.Object#equals(Object)
   */
  public boolean equals(Object object) {
    if (!(object instanceof CourseGroup)) { return false; }
    CourseGroup rhs = (CourseGroup) object;
    return new EqualsBuilder().append(this.id, rhs.id).isEquals();
  }

  /**
   * 查询指定学期的课程.<br>
   * 开课学期描述为:整型数字列表
   * 
   * @param terms
   * @return
   */
  public List getPlanCourses(List terms) {
    if (null == terms || terms.isEmpty()) {
      return new ArrayList(this.getPlanCourses());
    } else {
      if (getPlanCourses().isEmpty()) return Collections.EMPTY_LIST;
      else {
        List planCourseList = new ArrayList();
        for (Iterator iter = getPlanCourses().iterator(); iter.hasNext();) {
          PlanCourse planCourse = (PlanCourse) iter.next();
          for (Integer oneTerm : planCourse.getTerms().getTermList()) {
            if (terms.contains(oneTerm)) {
              planCourseList.add(planCourse);
              break;
            }
          }
        }
        return planCourseList;
      }
    }
  }

  public List getPlanCourses(String terms) {
    if (null == terms || StringUtils.isEmpty(terms)) {
      return new ArrayList(this.getPlanCourses());
    } else {
      List termList = Arrays.asList(SeqStringUtil.transformToInteger(terms));
      return getPlanCourses(termList);
    }
  }

  /**
   * 得到指定学期的学分,低于1学期或者超过最大学期的参数将被忽略
   * 
   * @param terms
   *          整型数字列表
   * @return
   */
  public Float getCredit(List terms) {
    if (null == terms || terms.isEmpty()) return getCredit();

    if (null == getCreditPerTerms()) return new Float(0);
    String[] termCreditArray = StringUtils.split(getCreditPerTerms(), ",");
    float credit = 0;
    for (int i = 0; i < termCreditArray.length; i++) {
      String termCredit = termCreditArray[i];
      if (terms.contains(new Integer(i + 1))) {
        credit += NumberUtils.toFloat(termCredit);
      }
    }
    return new Float(credit);
  }

  /**
   * 根据每学期的学分、学时、周课时，<br>
   * 统计课程组内总学分、总学时、学分分布和周课时分布<br>
   * 
   * @param group
   * @param termCount
   */
  public void statCreditAndHour(int termsCount) {
    float[] credit = new float[termsCount];
    int[] creditHour = new int[termsCount];
    float[] weekHour = new float[termsCount];
    for (Iterator iter = getPlanCourses().iterator(); iter.hasNext();) {
      PlanCourse planCourse = (PlanCourse) iter.next();
      Iterator<Integer> terms = planCourse.getTerms().getTermList().iterator();
      while (terms.hasNext()) {
        int termIndex = terms.next();
        if (termIndex >= 1 && termIndex <= termsCount) {
          credit[termIndex - 1] += planCourse.getCourse().getCredits().floatValue();
          creditHour[termIndex - 1] += planCourse.getCourse().getExtInfo().getPeriod().intValue();
          weekHour[termIndex - 1] += planCourse.getCourse().getWeekHour().floatValue();
        }
      }
    }
    /**
     * 计算总学时、总学分和串接学分分布以及周课时分布
     */
    StringBuffer creditSeq = new StringBuffer(",");
    StringBuffer weekHourSeq = new StringBuffer(",");
    float credits = 0;
    int creditHours = 0;

    for (int i = 0; i < credit.length; i++) {
      String creditStr = String.valueOf(credit[i]);
      String weekHourStr = String.valueOf(weekHour[i]);
      if (creditStr.endsWith(".0")) creditStr = creditStr.substring(0, creditStr.length() - 2);

      if (weekHourStr.endsWith(".0")) weekHourStr = weekHourStr.substring(0, weekHourStr.length() - 2);

      creditSeq.append(creditStr).append(",");
      weekHourSeq.append(weekHourStr).append(",");
      credits += credit[i];
      creditHours += creditHour[i];
    }
    setCredit(new Float(credits));
    setCreditHour(new Integer(creditHours));
    setCreditPerTerms(creditSeq.toString());
    setWeekHourPerTerms(weekHourSeq.toString());
  }
}
