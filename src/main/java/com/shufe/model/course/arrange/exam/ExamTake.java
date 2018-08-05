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
 * chaostone             2006-11-6            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange.exam;



import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.ExamDelayReason;
import com.ekingstar.eams.system.basecode.industry.ExamStatus;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 应考记录
 * 
 * @author chaostone
 * 
 */
public class ExamTake extends LongIdObject {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -1593583921052845498L;

	/** 上课记录 */
    private CourseTake courseTake;
    
    /** 教学任务 */
    private TeachTask task;
    
    /** 学生 */
    private Student std;
    
    /** 教学日历 */
    private TeachCalendar calendar;
    
    /** 考试安排 */
    private ExamActivity activity;
    
    /** 考试类型 */
    private ExamType examType = new ExamType();
    
    /** 考试情况 */
    private ExamStatus examStatus = new ExamStatus();
    
    /** 申请缓考原因 */
    private ExamDelayReason delayReason;
    
    /** 缓考申请原因/记录处分 */
    private String remark;
    
    /** 申请时间 */
    private Date applyDate;
    
    /** 申请IP */
    private String userIp;
    
    /** 申请用户名 */
    private String applyUser;
    
    public ExamTake() {
        super();
    }
    
    public ExamTake(CourseTake take, ExamActivity activity) {
        this(take);
        this.activity = activity;
        this.calendar = activity.getCalendar();
        this.examType = activity.getExamType();
    }
    
    public ExamTake(CourseTake take) {
        this.examStatus = new ExamStatus(ExamStatus.NORMAL);
        this.delayReason = null;
        this.setCourseTake(take);
        this.setStd(take.getStudent());
        this.task = take.getTask();
        if (null != task) {
            this.calendar = task.getCalendar();
        }
    }
    
    public ExamActivity getActivity() {
        return activity;
    }
    
    public void setActivity(ExamActivity activity) {
        this.activity = activity;
    }
    
    public CourseTake getCourseTake() {
        return courseTake;
    }
    
    public void setCourseTake(CourseTake courseTake) {
        this.courseTake = courseTake;
    }
    
    public ExamStatus getExamStatus() {
        return examStatus;
    }
    
    public void setExamStatus(ExamStatus examStatus) {
        this.examStatus = examStatus;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public ExamType getExamType() {
        return examType;
    }
    
    public void setExamType(ExamType examType) {
        this.examType = examType;
    }
    
    public ExamDelayReason getDelayReason() {
        return delayReason;
    }
    
    public void setDelayReason(ExamDelayReason delayReason) {
        this.delayReason = delayReason;
    }
    
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    public Student getStd() {
        return std;
    }
    
    public void setStd(Student std) {
        this.std = std;
    }
    
    public TeachTask getTask() {
        return task;
    }
    
    public void setTask(TeachTask task) {
        this.task = task;
    }

    
    public Date getApplyDate() {
        return applyDate;
    }

    
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    
    public String getUserIp() {
        return userIp;
    }

    
    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    
    public String getApplyUser() {
        return applyUser;
    }

    
    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }
    
}
