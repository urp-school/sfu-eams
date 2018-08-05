// $Id: RoomApply.java,v 1.1 2006/12/01 09:47:12 duanth Exp $

package com.shufe.model.course.arrange.apply;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.ActivityType;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.security.User;
import com.shufe.model.course.arrange.Activity;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Department;

/**
 * 教室申请记录
 * 
 * @author dell
 */
public class RoomApply extends LongIdObject {
    
    private static final long serialVersionUID = 5131988227931656875L;
    
    /** 借用人 */
    private Borrower borrower = new Borrower();
    
    /** 活动名称 */
    private String activityName;
    
    /** 活动类型 */
    private ActivityType activityType = new ActivityType();
    
    /** 是否具有营利性 */
    private Boolean isFree;
    
    /** 主讲人：姓名及背景资料 */
    private String leading;
    
    /** 出席对象 */
    private String attendance;
    
    /** 出席人数 */
    private Integer attendanceNum;
    
    /** 借用场所要求 */
    private String roomRequest;
    
    /** 借用校区 */
    private SchoolDistrict schoolDistrict;
    
    /** 借用时间要求 */
    private String timeRequest;
    
    /** 申请人 */
    private String applicant;
    
    /** 提交申请时间 */
    private Date applyAt = new Date(System.currentTimeMillis());
    
    /** 申请占用时间 */
    private ApplyTime applyTime = new ApplyTime();
    
    /** 归口部门 */
    private Department auditDepart = new Department();
    
    /** 归口审核情况 */
    private Boolean isDepartApproved;
    
    /** 归口审核情况 */
    private String departApprovedRemark;
    
    /** 归口审核人 */
    private User departApproveBy;
    
    /** 归口审核时间 */
    private Timestamp departApproveAt;
    
    /** 物管审核人 */
    private User approveBy;
    
    /** 物管审核时间 */
    private Timestamp approveAt;
    
    /** 物管审核情况 */
    private Boolean isApproved;
    
    /** 物管审核情况 */
    private String approvedRemark;
    
    /** 小时数 */
    private Float hours;
    
    /** 金额 */
    private Float money;
    
    /** 水费 */
    private Float waterFee;
    
    /** 是否使用多媒体设备 */
    private Boolean isMultimedia;
    
    /** 对应的教室占用记录 */
    private Set activities = new HashSet();
    
    /* ------------------- */
    private List classrooms;
    
    public void reset() {
        setIsDepartApproved(Boolean.FALSE);
        setDepartApproveBy(null);
        setDepartApproveAt(null);
        cancelApprove();
    }
    
    /**
     * 取消物管审核通过
     */
    public void cancelApprove() {
        setIsApproved(Boolean.FALSE);
        setApproveBy(null);
        setApproveAt(null);
        getActivities().clear();
        setMoney(null);
        setWaterFee(null);
        setHours(null);
    }
    
    public Set getClassrooms() {
        Set classrooms = new HashSet();
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            Activity activity = (Activity) iter.next();
            classrooms.add(activity.getRoom());
        }
        return classrooms;
    }
    
    public String getClassroomNames() {
        if (null == classrooms) {
            classrooms = new ArrayList(getClassrooms());
        }
        StringBuffer classNames = new StringBuffer();
        for (Iterator iter = classrooms.iterator(); iter.hasNext();) {
            Classroom room = (Classroom) iter.next();
            classNames.append(room.getName());
            if (iter.hasNext())
                classNames.append(",");
        }
        return classNames.toString();
    }
    
    public String getClassroomTypeNames() {
        if (null == classrooms) {
            classrooms = new ArrayList(getClassrooms());
        }
        StringBuffer roomTypeNames = new StringBuffer();
        for (Iterator iter = classrooms.iterator(); iter.hasNext();) {
            Classroom room = (Classroom) iter.next();
            String value = room.getConfigType().getName();
            if (StringUtils.contains("," + roomTypeNames.toString() + ",", "," + value + ",")) {
                continue;
            } else if (iter.hasNext() && roomTypeNames.length() > 0) {
                roomTypeNames.append(",");
            }
            roomTypeNames.append(value);
        }
        return roomTypeNames.toString();
    }
    
    public String getCapacityOfCourse() {
        if (null == classrooms) {
            classrooms = new ArrayList(getClassrooms());
        }
        StringBuffer capacityOfCourses = new StringBuffer();
        for (Iterator iter = classrooms.iterator(); iter.hasNext();) {
            Classroom room = (Classroom) iter.next();
            capacityOfCourses.append(room.getCapacityOfCourse());
            if (iter.hasNext())
                capacityOfCourses.append(",");
        }
        return capacityOfCourses.toString();
    }
    
    public Set getActivities() {
        return activities;
    }
    
    public void setActivities(Set activities) {
        this.activities = activities;
    }
    
    public String getActivityName() {
        return activityName;
    }
    
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    
    public ActivityType getActivityType() {
        return activityType;
    }
    
    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }
    
    public String getApplicant() {
        return applicant;
    }
    
    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }
    
    public Date getApplyAt() {
        return applyAt;
    }
    
    public void setApplyAt(Date applyAt) {
        this.applyAt = applyAt;
    }
    
    public ApplyTime getApplyTime() {
        return applyTime;
    }
    
    public void setApplyTime(ApplyTime applyTime) {
        this.applyTime = applyTime;
    }
    
    public Timestamp getApproveAt() {
        return approveAt;
    }
    
    public void setApproveAt(Timestamp approveAt) {
        this.approveAt = approveAt;
    }
    
    public User getApproveBy() {
        return approveBy;
    }
    
    public void setApproveBy(User approveBy) {
        this.approveBy = approveBy;
    }
    
    public String getAttendance() {
        return attendance;
    }
    
    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
    
    public Integer getAttendanceNum() {
        return attendanceNum;
    }
    
    public void setAttendanceNum(Integer attendanceNum) {
        this.attendanceNum = attendanceNum;
    }
    
    public Department getAuditDepart() {
        return auditDepart;
    }
    
    public void setAuditDepart(Department auditDepart) {
        this.auditDepart = auditDepart;
    }
    
    public Borrower getBorrower() {
        return borrower;
    }
    
    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }
    
    public Boolean getIsApproved() {
        return isApproved;
    }
    
    public void setIsApproved(Boolean isApprove) {
        this.isApproved = isApprove;
    }
    
    public Boolean getIsDepartApproved() {
        return isDepartApproved;
    }
    
    public void setIsDepartApproved(Boolean isDepartApprove) {
        this.isDepartApproved = isDepartApprove;
    }
    
    public Boolean getIsFree() {
        return isFree;
    }
    
    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }
    
    public String getLeading() {
        return leading;
    }
    
    public void setLeading(String leading) {
        this.leading = leading;
    }
    
    public String getRoomRequest() {
        return roomRequest;
    }
    
    public void setRoomRequest(String locationRequest) {
        this.roomRequest = locationRequest;
    }
    
    public String getTimeRequest() {
        return timeRequest;
    }
    
    public void setTimeRequest(String timeRequest) {
        this.timeRequest = timeRequest;
    }
    
    public Timestamp getDepartApproveAt() {
        return departApproveAt;
    }
    
    public void setDepartApproveAt(Timestamp departApproveAt) {
        this.departApproveAt = departApproveAt;
    }
    
    public User getDepartApproveBy() {
        return departApproveBy;
    }
    
    public void setDepartApproveBy(User departApproveBy) {
        this.departApproveBy = departApproveBy;
    }
    
    public Float getHours() {
        return hours;
    }
    
    public void setHours(Float hours) {
        this.hours = hours;
    }
    
    public Float getMoney() {
        return money;
    }
    
    public void setMoney(Float money) {
        this.money = money;
    }
    
    /**
     * @return the waterFee
     */
    public Float getWaterFee() {
        return waterFee;
    }
    
    /**
     * @param waterFee
     *            the waterFee to set
     */
    public void setWaterFee(Float waterFee) {
        this.waterFee = waterFee;
    }
    
    /**
     * @return the isMultimedia
     */
    public Boolean getIsMultimedia() {
        return isMultimedia;
    }
    
    /**
     * @param isMultimedia
     *            the isMultimedia to set
     */
    public void setIsMultimedia(Boolean isMultimedia) {
        this.isMultimedia = isMultimedia;
    }
    
    public SchoolDistrict getSchoolDistrict() {
        return schoolDistrict;
    }
    
    public void setSchoolDistrict(SchoolDistrict school) {
        this.schoolDistrict = school;
    }
    
    public String getApprovedRemark() {
        return approvedRemark;
    }
    
    public void setApprovedRemark(String approvedRemark) {
        this.approvedRemark = approvedRemark;
    }
    
    public String getDepartApprovedRemark() {
        return departApprovedRemark;
    }
    
    public void setDepartApprovedRemark(String departApprovedRemark) {
        this.departApprovedRemark = departApprovedRemark;
    }
    
}
