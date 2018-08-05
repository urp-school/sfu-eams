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
 * chaostone             2006-8-12            Created
 *  
 ********************************************************************************/

package com.shufe.service.course.task;

import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 教学任务拷贝参数
 * 
 * @author chaostone
 */
public class TaskCopyParams {
    
    TeachCalendar calendar;
    
    /**
     * 复制学生名单
     */
    Boolean copyCourseTakes;
    
    /**
     * 是否拷贝课程序号
     */
    Boolean copySeqNo;
    
    /**
     * 删除已有的任务
     */
    Boolean deleteExistedTask;
    
    /**
     * 删除已有任务的学生名单
     */
    Boolean deleteExistedCourseTakes;
    
    /** 每个任务的复制数量 */
    Integer copyCount;
    
    public Integer getCopyCount() {
        return copyCount;
    }
    
    public void setCopyCount(Integer copyCount) {
        this.copyCount = copyCount;
    }
    
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    public Boolean getCopyCourseTakes() {
        return copyCourseTakes;
    }
    
    public void setCopyCourseTakes(Boolean copyCourseTakes) {
        this.copyCourseTakes = copyCourseTakes;
    }
    
    public Boolean getDeleteExistedCourseTakes() {
        return deleteExistedCourseTakes;
    }
    
    public void setDeleteExistedCourseTakes(Boolean deleteExistedCourseTakes) {
        this.deleteExistedCourseTakes = deleteExistedCourseTakes;
    }
    
    public Boolean getDeleteExistedTask() {
        return deleteExistedTask;
    }
    
    public void setDeleteExistedTask(Boolean deleteExistedTask) {
        this.deleteExistedTask = deleteExistedTask;
    }
    
    public Boolean getCopySeqNo() {
        return copySeqNo;
    }
    
    public void setCopySeqNo(Boolean copySeqNo) {
        this.copySeqNo = copySeqNo;
    }
    
}
