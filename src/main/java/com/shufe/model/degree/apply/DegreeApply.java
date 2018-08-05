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
 * 塞外狂人             2006-8-8            Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.apply;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.degree.subject.Level1Subject;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.std.Student;

/**
 * 学生学位申请记录
 * 
 * @author 塞外狂人 cwx
 */
public class DegreeApply extends LongIdObject {
    
    private static final long serialVersionUID = 8623080130879491332L;
    
    /** 学生论文 */
    private ThesisManage thesisManage = new ThesisManage();
    
    /** 学生 */
    private Student student = new Student();
    
    /** 论文发表数量 */
    private ThesisNumStandard thesisNumStandard = new ThesisNumStandard();
    
    /** 答辩委员会人员 */
    private Set committeeSet = new HashSet();
    
    /** 是否同意授予学位 */
    private Boolean isAgree;
    
    /** 一级学科 */
    private Level1Subject level1Subject = new Level1Subject();
    
    /** 学位提交时间 */
    private Date commitOn;
    
    /** 总学分 */
    private Float totalMark;
    
    /** 专业课学分 */
    private Float specialityMark;
    
    /** 学生备注 */
    private String stdRemark;
    
    /** 管理员备注 */
    private String adminRemark;
    
    public Set getCommitteeSet() {
        return committeeSet;
    }
    
    public void setCommitteeSet(Set committeeSet) {
        this.committeeSet = committeeSet;
    }
    
    /**
     * @return Returns the isAgree.
     */
    public Boolean getIsAgree() {
        return isAgree;
    }
    
    /**
     * @param isAgree The isAgree to set.
     */
    public void setIsAgree(Boolean isAgree) {
        this.isAgree = isAgree;
    }
    
    /**
     * @return Returns the student.
     */
    public Student getStudent() {
        return student;
    }
    
    /**
     * @param student The student to set.
     */
    public void setStudent(Student student) {
        this.student = student;
    }
    
    /**
     * @return Returns the thesisManage.
     */
    public ThesisManage getThesisManage() {
        return thesisManage;
    }
    
    /**
     * @param thesisManage The thesisManage to set.
     */
    public void setThesisManage(ThesisManage thesisManage) {
        this.thesisManage = thesisManage;
    }
    
    /**
     * @return Returns the adminRemark.
     */
    public String getAdminRemark() {
        return adminRemark;
    }
    
    /**
     * @param adminRemark The adminRemark to set.
     */
    public void setAdminRemark(String adminRemark) {
        this.adminRemark = adminRemark;
    }
    
    /**
     * @return Returns the stdRemark.
     */
    public String getStdRemark() {
        return stdRemark;
    }
    
    /**
     * @param stdRemark The stdRemark to set.
     */
    public void setStdRemark(String stdRemark) {
        this.stdRemark = stdRemark;
    }
    
    /**
     * @return Returns the thesisNumStandard.
     */
    public ThesisNumStandard getThesisNumStandard() {
        return thesisNumStandard;
    }
    
    /**
     * @param thesisNumStandard The thesisNumStandard to set.
     */
    public void setThesisNumStandard(ThesisNumStandard thesisNumStandard) {
        this.thesisNumStandard = thesisNumStandard;
    }
    
    /**
     * @return Returns the commitOn.
     */
    public Date getCommitOn() {
        return commitOn;
    }
    
    /**
     * @param commitOn The commitOn to set.
     */
    public void setCommitOn(Date commitOn) {
        this.commitOn = commitOn;
    }
    
    /**
     * @return Returns the level1Subject.
     */
    public Level1Subject getLevel1Subject() {
        return level1Subject;
    }
    
    /**
     * @param level1Subject The level1Subject to set.
     */
    public void setLevel1Subject(Level1Subject level1Subject) {
        this.level1Subject = level1Subject;
    }
    
    /**
     * @return Returns the specialityMark.
     */
    public Float getSpecialityMark() {
        return specialityMark;
    }
    
    /**
     * @param specialityMark The specialityMark to set.
     */
    public void setSpecialityMark(Float specialityMark) {
        this.specialityMark = specialityMark;
    }
    
    /**
     * @return Returns the totalMark.
     */
    public Float getTotalMark() {
        return totalMark;
    }
    
    /**
     * @param totalMark The totalMark to set.
     */
    public void setTotalMark(Float totalMark) {
        this.totalMark = totalMark;
    }
    
}
