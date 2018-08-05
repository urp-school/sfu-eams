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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.course.grade.converter.ConverterFactory;
import com.ekingstar.eams.system.basecode.industry.ExamStatus;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.security.User;
import com.shufe.model.course.grade.alter.ExamGradeAlterInfo;

/**
 * 考试成绩
 * 
 * @author chaostone
 */
public class ExamGrade extends LongIdObject implements Grade {
    
    private static final long serialVersionUID = 3737090012804400743L;
    
    /**
     * 成绩类型
     */
    private GradeType gradeType = new GradeType();
    
    /**
     * 考试情况
     */
    private ExamStatus examStatus = new ExamStatus();
    
    /**
     * 得分
     */
    private Float score;
    
    /**
     * 对应的课程成绩
     */
    private CourseGrade courseGrade = new CourseGrade();
    
    /**
     * 修改记录
     */
    private Set alterInfos = new HashSet();
    
    /**
     * 成绩状态
     */
    protected Integer status;
    
    /**
     * 是否通过
     */
    private Boolean isPass;
    
    /**
     * 创建时间
     */
    private Date createAt = new Date();
    
    public ExamGrade() {
    }
    
    public ExamGrade(GradeType gradeType, Float score) {
        this.gradeType = gradeType;
        this.score = score;
    }
    
    public ExamGrade(GradeType gradeType, ExamStatus examStatus, Float score) {
        this.gradeType = gradeType;
        this.examStatus = examStatus;
        this.score = score;
    }
    
    public CourseGrade getCourseGrade() {
        return courseGrade;
    }
    
    public void setCourseGrade(CourseGrade courseGrade) {
        this.courseGrade = courseGrade;
    }
    
    public GradeType getGradeType() {
        return gradeType;
    }
    
    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
    }
    
    public Float getScore() {
        return score;
    }
    
    public void setScore(Float score) {
        this.score = score;
    }
    
    public Boolean getIsPublished() {
        if (null != getStatus()) {
            return Boolean.valueOf((getStatus().intValue() & PUBLISHED) > 0);
        } else {
            return Boolean.FALSE;
        }
    }
    
    public Boolean getIsConfirmed() {
        if (null != getStatus()) {
            return Boolean.valueOf((getStatus().intValue() & CONFIRMED) > 0);
        } else {
            return Boolean.FALSE;
        }
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getScoreDisplay() {
        if (null == score)
            return "";
        if (null == getCourseGrade())
            return ConverterFactory.getConverter().convert(score, null);
        else {
            return ConverterFactory.getConverter().convert(score, getCourseGrade().getMarkStyle());
        }
    }
    
    public String getScoreDisplay(MarkStyle markStyle) {
        return ConverterFactory.getConverter().convert(score, markStyle);
    }
    
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
            getAlterInfos().add(new ExamGradeAlterInfo(this, getScore(), score, who));
        }
        setScore(score);
    }
    
    // 大的成绩放前面
    public int compareTo(Object arg0) {
        Grade grade = (Grade) arg0;
        if (null == getScore())
            return 1;
        else if (null == grade.getScore())
            return -1;
        return grade.getScore().compareTo(getScore());
    }
    
    public Set getAlterInfos() {
        return alterInfos;
    }
    
    public void setAlterInfos(Set modifyInfos) {
        this.alterInfos = modifyInfos;
    }
    
    public Boolean getIsPass() {
        return isPass;
    }
    
    public void setIsPass(Boolean isPass) {
        this.isPass = isPass;
    }
    
    public ExamStatus getExamStatus() {
        return examStatus;
    }
    
    public void setExamStatus(ExamStatus examStatus) {
        this.examStatus = examStatus;
    }
    
    public Date getCreateAt() {
        return createAt;
    }
    
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
