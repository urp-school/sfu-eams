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
package com.ekingstar.eams.teach.program.std;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.security.User;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;

/**
 * 学生可代替课程实体类.<br>
 * <p>
 * 指定了学生专业培养计划中的某些课程可以由某些课程替代.<br>
 * 
 * @author James
 * 
 */
public class DefaultStdSubstituteCourse extends LongIdObject implements StdSubstituteCourse {
    
    private static final long serialVersionUID = -3619832967631930149L;
    
    /** 学生 */
    private Student std;
    
    /** 被替代的课程 */
    private Set origins = new HashSet();
    
    /** 已替代的课程 */
    private Set substitutes = new HashSet();
    
    /** 制定时间 */
    private Date createAt;
    
    /** 修改时间 */
    private Date modifyAt;
    
    /** 备注 */
    private String remark;
    
    private User operateBy;
    
    public User getOperateBy() {
        return operateBy;
    }
    
    public void setOperateBy(User operateBy) {
        this.operateBy = operateBy;
    }
    
    public Student getStd() {
        return std;
    }
    
    public void setStd(Student std) {
        this.std = std;
    }
    
    public Set getOrigins() {
        return origins;
    }
    
    public void setOrigins(Set origins) {
        this.origins = origins;
    }
    
    public Set getSubstitutes() {
        return substitutes;
    }
    
    public void setSubstitutes(Set substitutes) {
        this.substitutes = substitutes;
    }
    
    public Date getCreateAt() {
        return createAt;
    }
    
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    
    public Date getModifyAt() {
        return modifyAt;
    }
    
    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public void addOrigin(Course course) {
        this.origins.add(course);
    }
    
    public void addOrigin(Collection courses) {
        this.origins.addAll(courses);
    }
    
    public void addSubstitute(Course course) {
        this.substitutes.add(course);
    }
    
    public void addSubstitute(Collection courses) {
        this.substitutes.addAll(courses);
    }
}
