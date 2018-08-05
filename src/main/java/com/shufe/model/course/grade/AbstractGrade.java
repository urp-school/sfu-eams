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
 * chaostone             2006-12-13            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.course.grade.converter.ConverterFactory;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.shufe.model.course.grade.other.OtherGrade;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 成绩的抽象类
 * 
 * @see Grade
 * @see CourseGrade
 * @see OtherGrade
 * @author chaostone
 * 
 */
public abstract class AbstractGrade extends LongIdObject implements Grade {
    
    /**
     * 学生
     */
    protected Student std;
    
    /**
     * 教学日历
     */
    protected TeachCalendar calendar;
    
    /**
     * 得分
     */
    protected Float score;
    
    /**
     * 是否合格
     */
    protected Boolean isPass;
    
    /**
     * 修改记录
     */
    protected Set alterInfos;
    
    /**
     * 成绩状态<br>
     * 0:新增<br>
     * 1:确认<br>
     * 2:已发布
     */
    protected Integer status;
    
    /**
     * 成绩记录方式
     */
    protected MarkStyle markStyle;
    
    /**
     * 创建时间
     */
    private Date createAt = new Date();
    
    public AbstractGrade() {
        alterInfos = new HashSet();
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
    
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public Boolean getIsConfirmed() {
        if (null != getStatus()) {
            return Boolean.valueOf((getStatus().intValue() & CONFIRMED) > 0);
        } else {
            return Boolean.FALSE;
        }
    }
    
    public Boolean getIsPass() {
        return isPass;
    }
    
    public Boolean getIsPublished() {
        if (null != getStatus()) {
            return Boolean.valueOf((getStatus().intValue() & PUBLISHED) > 0);
        } else {
            return Boolean.FALSE;
        }
    }
    
    public MarkStyle getMarkStyle() {
        return markStyle;
    }
    
    public Float getScore() {
        return score;
    }
    
    public String getScoreDisplay() {
        if (null == score) {
            return "";
        }
        return ConverterFactory.getConverter().convert(score, markStyle);
    }
    
    public String getScoreDisplay(MarkStyle markStyle) {
        return ConverterFactory.getConverter().convert(score, markStyle);
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public Student getStd() {
        return std;
    }
    
    public void setAlterInfos(Set alterInfos) {
        this.alterInfos = alterInfos;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    public void setIsPass(Boolean isPass) {
        this.isPass = isPass;
    }
    
    public void setMarkStyle(MarkStyle markStyle) {
        this.markStyle = markStyle;
    }
    
    public void setScore(Float score) {
        this.score = score;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public void setStd(Student std) {
        this.std = std;
    }
    
    public Date getCreateAt() {
        return createAt;
    }
    
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
