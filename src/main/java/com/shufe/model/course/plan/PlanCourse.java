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
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.HSKDegree;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Department;

/**
 * 培养计划课程实体类.<br>
 * <p>
 * 规定了培养计划中课程组内的课程可能在那个学期开课，有什么院系开课.<br>
 * 学生在上这门课时，必须先上过什么课，需要的汉语水平要多高.<br>
 * 如果这么可不通过，可以有什么课程替代.<br>
 * <p>
 * 一个课程设置只能属于一个课程组.
 * 
 * @author chaostone
 * 
 */
public class PlanCourse extends LongIdObject implements Comparable, Cloneable {
    
    private static final long serialVersionUID = -3619832967631930144L;
    
    /** 课程 */
    private Course course;
    
    /** 开课院系 */
    private Department teachDepart;
    
    /** 开课的学期 */
    private Terms terms;
    
    /** 所属课程组 */
    private CourseGroup courseGroup;
    
    /** 备注 */
    private String remark;
    /**
     * 是否学位课程
     */
    
    private Boolean isDegreeCourse;
    
    /**
     * 全盘比较?? FIXME for reason?
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean isSame(Object object) {
        if (!(object instanceof PlanCourse)) {
            return false;
        }
        PlanCourse rhs = (PlanCourse) object;
        return new EqualsBuilder().append(getTerms(),
                rhs.getTerms()).append(getRemark(), rhs.getRemark()).append(
                getTeachDepart().getId(), rhs.getTeachDepart().getId()).append(getCourse().getId(),
                rhs.getCourse().getId()).append(getId(), rhs.getId()).isEquals();
    }
    
    /**
     * 默认按照学分进行排序
     * 
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(Object object) {
        PlanCourse myClass = (PlanCourse) object;
        return new CompareToBuilder().append(getCourse().getCredits(),
                myClass.getCourse().getCredits()).toComparison();
    }
    
    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        PlanCourse planCourse = new PlanCourse();
        try {
            PropertyUtils.copyProperties(planCourse, this);
            planCourse.setCourseGroup(null);
            planCourse.setId(null);
        } catch (Exception e) {
            throw new RuntimeException("error in clone planCourse:"
                    + ExceptionUtils.getStackTrace(e));
        }
        return planCourse;
    }
    
    public Terms getTerms() {
      return terms;
    }

    public void setTerms(Terms terms) {
      this.terms = terms;
    }

    public boolean inTerm(String terms) {
        if (StringUtils.isEmpty(terms)) {
            return true;
        } else {
            String termArray[] = StringUtils.split(terms, ",");
            for (int i = 0; i < termArray.length; i++) {
              if(this.terms.contains(Integer.parseInt(termArray[i]))) return true;
            }
            return false;
        }
        
    }
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public CourseGroup getCourseGroup() {
        return courseGroup;
    }
    
    public void setCourseGroup(CourseGroup courseGroup) {
        this.courseGroup = courseGroup;
    }
    
    public Department getTeachDepart() {
        return teachDepart;
    }
    
    public void setTeachDepart(Department department) {
        this.teachDepart = department;
    }
    
    public String getTermSeq() {
        return terms.toString();
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Boolean getIsDegreeCourse() {
        return isDegreeCourse;
    }
    
    public void setIsDegreeCourse(Boolean isDegreeCourse) {
        this.isDegreeCourse = isDegreeCourse;
    }
    
}
