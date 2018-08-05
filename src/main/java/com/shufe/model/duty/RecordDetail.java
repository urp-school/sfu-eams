//$Id: RecordDetail.java,v 1.8 2007/01/26 03:17:42 yd Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-12-1         Created
 *  
 ********************************************************************************/

package com.shufe.model.duty;

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.AttendanceType;
import com.ekingstar.eams.system.time.CourseUnit;

/**
 * 单条考勤记录（对应一条学生考勤记录的一条考勤记录详情，如考勤时间、出勤状态等）
 */
public class RecordDetail extends LongIdObject implements Cloneable {
    
    private static final long serialVersionUID = 5150851766374647749L;
    
    /** 对应考勤记录 */
    private DutyRecord dutyRecord;
    
    /** 考勤日期 */
    private Date dutyDate;
    
    /** 考勤课程开始时间 */
    private Integer beginTime;
    
    /** 考勤课程结束时间 */
    private Integer endTime;
    
    /** 考勤状态，例如出勤、旷课、迟到等 */
    private AttendanceType dutyStatus;
    
    /** 考勤课程起始小节信息 */
    private CourseUnit beginUnit = new CourseUnit();
    
    /** 考勤课程结束小节信息 */
    private CourseUnit endUnit = new CourseUnit();
    
    /** 考勤课程起始小节编号 */
    private Integer beginUnitIndex;
    
    /** 考勤课程结束小节编号 */
    private Integer endUnitIndex;
    
    /**
     * @return 返回 endUnit.
     */
    public CourseUnit getEndUnit() {
        return endUnit;
    }
    
    /**
     * @param endUnit 要设置的 endUnit.
     */
    public void setEndUnit(CourseUnit endUnit) {
        this.endUnit = endUnit;
    }
    
    /**
     * @return Returns the dutyDate.
     */
    public Date getDutyDate() {
        return dutyDate;
    }
    
    /**
     * @param dutyDate The dutyDate to set.
     */
    public void setDutyDate(Date dutyDate) {
        this.dutyDate = dutyDate;
    }
    
    /**
     * @return Returns the dutyRecord.
     */
    public DutyRecord getDutyRecord() {
        return dutyRecord;
    }
    
    /**
     * @param dutyRecord The dutyRecord to set.
     */
    public void setDutyRecord(DutyRecord dutyRecord) {
        this.dutyRecord = dutyRecord;
    }
    
    /**
     * @return 返回 dutyStatus.
     */
    public AttendanceType getDutyStatus() {
        return dutyStatus;
    }
    
    /**
     * @param dutyStatus 要设置的 dutyStatus.
     */
    public void setDutyStatus(AttendanceType dutyStatus) {
        this.dutyStatus = dutyStatus;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    /**
     * @return 返回 beginUnit.
     */
    public CourseUnit getBeginUnit() {
        return beginUnit;
    }
    
    /**
     * @param beginUnit 要设置的 beginUnit.
     */
    public void setBeginUnit(CourseUnit courseUnit) {
        this.beginUnit = courseUnit;
    }
    
    /**
     * @return 返回 beginTime.
     */
    public Integer getBeginTime() {
        return beginTime;
    }
    
    /**
     * @param beginTime 要设置的 beginTime.
     */
    public void setBeginTime(Integer begintime) {
        this.beginTime = begintime;
    }
    
    /**
     * @return 返回 endTime.
     */
    public Integer getEndTime() {
        return endTime;
    }
    
    /**
     * @param endTime 要设置的 endTime.
     */
    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }
    
    /**
     * @return 返回 beginUnitIndex.
     */
    public Integer getBeginUnitIndex() {
        return beginUnitIndex;
    }
    
    /**
     * @param beginUnitIndex 要设置的 beginUnitIndex.
     */
    public void setBeginUnitIndex(Integer beginUnitIndex) {
        this.beginUnitIndex = beginUnitIndex;
    }
    
    /**
     * @return 返回 endUnitIndex.
     */
    public Integer getEndUnitIndex() {
        return endUnitIndex;
    }
    
    /**
     * @param endUnitIndex 要设置的 endUnitIndex.
     */
    public void setEndUnitIndex(Integer endUnitIndex) {
        this.endUnitIndex = endUnitIndex;
    }
    
    public int compareDateTo(Date anotherDate) {
        return dutyDate.compareTo(anotherDate);
    }
    
}
