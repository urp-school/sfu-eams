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
 * chaostone             2006-6-13            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.task;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;

/**
 * 教学任务生成参数<br>
 * 此类不是实体类
 * 
 * @author chaostone
 * 
 */
public class TaskGenParams extends LongIdObject {
    
    private static final long serialVersionUID = 7046399461690215375L;
    
    private ClassroomType configType = new ClassroomType();
    
    private Integer courseUnits;
    
    /**
     * 小节的输入/产生方式<br>
     * 1：小节数=学分数,2：自定义
     */
    private Integer unitStatus;
    
    private Integer weeks;
    
    private Integer weekStart;
    
    private boolean removeGenerated;
    
    private Boolean ignoreClass;
    
    private Boolean isOmitSmallTerm = Boolean.TRUE;
    
    private String courseCodeSeq;
    
    private String courseTypeIds;
    
    /**
     * 指定生成某一个学期,0表示系统自动计算学期
     */
    private Integer forTerm;
    
    /**
     * 被忽略的课程代码串
     */
    private String ommitedCourseCodeSeq;
    
    /**
     * 被忽略的课程名称(%courseName%)
     */
    private String ommitedCourseName;
    
    public static TaskGenParams getDefault() {
        TaskGenParams params = new TaskGenParams();
        params.configType.setId(ClassroomType.COMMON);
        params.courseUnits = new Integer(2);
        params.ignoreClass = Boolean.TRUE;
        params.forTerm = new Integer(0);
        return params;
    }
    
    public ClassroomType getConfigType() {
        return configType;
    }
    
    public void setConfigType(ClassroomType configType) {
        this.configType = configType;
    }
    
    public Integer getCourseUnits() {
        return courseUnits;
    }
    
    public void setCourseUnits(Integer courseUnits) {
        this.courseUnits = courseUnits;
    }
    
    public Boolean getIgnoreClass() {
        return ignoreClass;
    }
    
    public void setIgnoreClass(Boolean ignoreClass) {
        this.ignoreClass = ignoreClass;
    }
    
    public boolean isRemoveGenerated() {
        return removeGenerated;
    }
    
    public void setRemoveGenerated(boolean removeGenerated) {
        this.removeGenerated = removeGenerated;
    }
    
    public Integer getWeekStart() {
        return weekStart;
    }
    
    public void setWeekStart(Integer startWeek) {
        this.weekStart = startWeek;
    }
    
    public Integer getWeeks() {
        return weeks;
    }
    
    public void setWeeks(Integer weeks) {
        this.weeks = weeks;
    }
    
    public Boolean getIsOmitSmallTerm() {
        return isOmitSmallTerm;
    }
    
    public void setIsOmitSmallTerm(Boolean isOmitSmallTerm) {
        this.isOmitSmallTerm = isOmitSmallTerm;
    }
    
    public String getOmmitedCourseCodeSeq() {
        return ommitedCourseCodeSeq;
    }
    
    public void setOmmitedCourseCodeSeq(String ommitedCourseCodeSeq) {
        this.ommitedCourseCodeSeq = ommitedCourseCodeSeq;
    }
    
    public String getOmmitedCourseName() {
        return ommitedCourseName;
    }
    
    public void setOmmitedCourseName(String ommitedCourseName) {
        this.ommitedCourseName = ommitedCourseName;
    }
    
    public Integer getForTerm() {
        return forTerm;
    }
    
    public void setForTerm(Integer forTerm) {
        this.forTerm = forTerm;
    }
    
    public String getCourseCodeSeq() {
        return courseCodeSeq;
    }
    
    public void setCourseCodeSeq(String courseSeq) {
        this.courseCodeSeq = courseSeq;
    }
    
    public String getCourseTypeIds() {
        return courseTypeIds;
    }
    
    public void setCourseTypeIds(String courseTypeSeq) {
        this.courseTypeIds = courseTypeSeq;
    }
    
    public Integer getUnitStatus() {
        return unitStatus;
    }
    
    public void setUnitStatus(Integer unitStatus) {
        this.unitStatus = unitStatus;
    }
    
}
