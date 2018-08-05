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
 * chaostone             2007-1-3            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade.alter;

import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.course.grade.converter.ConverterFactory;
import com.ekingstar.security.User;
import com.shufe.model.course.grade.CourseGrade;

/**
 * 总评或其他成绩新增/修改记录
 * 
 * @author chaostone
 * 
 */
public class CourseGradeAlterInfo extends LongIdObject implements GradeAlterInfo {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1637944981542903095L;
    
    /**
     * 被修改的课程成绩
     */
    private CourseGrade grade;
    
    /**
     * 修改前成绩
     */
    private Float scoreBefore;
    
    /**
     * 修改后成绩
     */
    private Float scoreAfter;
    
    /**
     * 修改于
     */
    private Date modifyAt;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 修改人
     */
    private User modifyBy;
    
    public CourseGradeAlterInfo() {
        super();
    }
    
    public CourseGradeAlterInfo(CourseGrade grade, Float scoreBefore, Float scoreAfter,
            User modifyBy) {
        this.grade = grade;
        this.scoreBefore = scoreBefore;
        this.scoreAfter = scoreAfter;
        this.modifyBy = modifyBy;
        this.modifyAt = new Date();
    }
    
    /**
     * 查询各种成绩的显示值
     * 
     * @param score
     * @return
     */
    public String getScoreDisplay(Float score) {
        return ConverterFactory.getConverter().convert(null == score ? new Float(0) : score,
                grade.getMarkStyle());
    }
    
    public Date getModifyAt() {
        return modifyAt;
    }
    
    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }
    
    public User getModifyBy() {
        return modifyBy;
    }
    
    public void setModifyBy(User modifyBy) {
        this.modifyBy = modifyBy;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Float getScoreAfter() {
        return scoreAfter;
    }
    
    public void setScoreAfter(Float scoreAfter) {
        this.scoreAfter = scoreAfter;
    }
    
    public Float getScoreBefore() {
        return scoreBefore;
    }
    
    public void setScoreBefore(Float scoreBefore) {
        this.scoreBefore = scoreBefore;
    }
    
    public CourseGrade getGrade() {
        return grade;
    }
    
    public void setGrade(CourseGrade grade) {
        this.grade = grade;
    }
    
}
