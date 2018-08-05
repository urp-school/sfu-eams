//$Id: TeachWorkload.java,v 1.8 2006/12/26 14:11:56 cwx Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-6-9         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload;

import java.util.Calendar;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.workload.course.TeacherInfo;

/**
 * 工作量
 * 
 * @author chaostone
 * 
 */
public class Workload extends LongIdObject {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1476849121732140130L;
    
    public static final Long teacherTypeid = new Long(2);
    
    public static final Long titlelevelId = new Long(6);
    
    protected TeacherInfo teacherInfo = new TeacherInfo(); // 统计辅助信息
    
    protected StudentType studentType;
    
    protected Integer studentNumber; // 上课学生数
    
    protected Float totleWorkload; // 总工作量
    
    protected Boolean teacherAffirm; // 教师确认
    
    protected Boolean payReward; // 支付报酬
    
    protected Boolean calcWorkload; // 计工作量
    
    protected Calendar statisticTime; // 统计时间
    
    protected Boolean isTeachWorkload; // 是否是授课工作量
    
    protected TeachCalendar teachCalendar; // 教学日历
    
    protected Boolean isHandInput; // 是否手工输入
    
    protected String modifyTime; // 修改时间
    
    protected String modifyPerson; // 修改人
    
    protected String remark;
    
    public Workload() {
        
    }
    
    public Workload(Teacher teacher) {
        this.getTeacherInfo().setDegree(
                null == teacher.getDegreeInfo() ? null : teacher.getDegreeInfo().getDegree());
        this.getTeacherInfo().setEduDegree(
                null == teacher.getDegreeInfo() ? null : teacher.getDegreeInfo()
                        .getEduDegreeInside());
        this.getTeacherInfo().setGender(teacher.getGender());
        this.getTeacherInfo().setTeacherType(teacher.getTeacherType());
        this.getTeacherInfo().setTeachDepart((Department) teacher.getDepartment());
        this.getTeacherInfo().setTeacher(teacher);
        this.getTeacherInfo().setTeacherAge(teacher.getAge());
        this.getTeacherInfo().setTeacherName(teacher.getName());
        this.getTeacherInfo().setTeacherTitle(teacher.getTitle());
        this.getTeacherInfo().setTitleLevel(teacher.getTitleLevel());
        this.isHandInput = Boolean.FALSE;
        this.calcWorkload = Boolean.TRUE;
        this.payReward = Boolean.FALSE;
        this.teacherAffirm = Boolean.FALSE;
        this.statisticTime = Calendar.getInstance();
    }
    
    /**
     * @return Returns the calcWorkload.
     */
    public Boolean getCalcWorkload() {
        return calcWorkload;
    }
    
    /**
     * @param calcWorkload
     *            The calcWorkload to set.
     */
    public void setCalcWorkload(Boolean calcWorkload) {
        this.calcWorkload = calcWorkload;
    }
    
    /**
     * @return Returns the isTeachWorkload.
     */
    public Boolean getIsTeachWorkload() {
        return isTeachWorkload;
    }
    
    /**
     * @param isTeachWorkload
     *            The isTeachWorkload to set.
     */
    public void setIsTeachWorkload(Boolean isTeachWorkload) {
        this.isTeachWorkload = isTeachWorkload;
    }
    
    /**
     * @return Returns the payReward.
     */
    public Boolean getPayReward() {
        return payReward;
    }
    
    /**
     * @param payReward
     *            The payReward to set.
     */
    public void setPayReward(Boolean payReward) {
        this.payReward = payReward;
    }
    
    /**
     * @return Returns the teacherInfo.
     */
    public TeacherInfo getTeacherInfo() {
        return teacherInfo;
    }
    
    /**
     * @param teacherInfo
     *            The teacherInfo to set.
     */
    public void setTeacherInfo(TeacherInfo teacherInfo) {
        this.teacherInfo = teacherInfo;
    }
    
    /**
     * @return Returns the statisticTime.
     */
    public Calendar getStatisticTime() {
        return statisticTime;
    }
    
    /**
     * @param statisticTime
     *            The statisticTime to set.
     */
    public void setStatisticTime(Calendar statisticTime) {
        this.statisticTime = statisticTime;
    }
    
    /**
     * @return Returns the studentNumber.
     */
    public Integer getStudentNumber() {
        return studentNumber;
    }
    
    /**
     * @param studentNumber
     *            The studentNumber to set.
     */
    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }
    
    /**
     * @return Returns the studentType.
     */
    public StudentType getStudentType() {
        return studentType;
    }
    
    /**
     * @param studentType
     *            The studentType to set.
     */
    public void setStudentType(StudentType studentType) {
        this.studentType = studentType;
    }
    
    /**
     * @return Returns the teachCalendar.
     */
    public TeachCalendar getTeachCalendar() {
        return teachCalendar;
    }
    
    /**
     * @param teachCalendar
     *            The teachCalendar to set.
     */
    public void setTeachCalendar(TeachCalendar teachCalendar) {
        this.teachCalendar = teachCalendar;
    }
    
    /**
     * @return Returns the teacherAffirm.
     */
    public Boolean getTeacherAffirm() {
        return teacherAffirm;
    }
    
    /**
     * @param teacherAffirm
     *            The teacherAffirm to set.
     */
    public void setTeacherAffirm(Boolean teacherAffirm) {
        this.teacherAffirm = teacherAffirm;
    }
    
    /**
     * @return Returns the totleWorkload.
     */
    public Float getTotleWorkload() {
        return totleWorkload;
    }
    
    /**
     * @param totleWorkload
     *            The totleWorkload to set.
     */
    public void setTotleWorkload(Float totleWorkload) {
        this.totleWorkload = totleWorkload;
    }
    
    /**
     * @return Returns the isHandInput.
     */
    public Boolean getIsHandInput() {
        return isHandInput;
    }
    
    /**
     * @param isHandInput
     *            The isHandInput to set.
     */
    public void setIsHandInput(Boolean isHandInput) {
        this.isHandInput = isHandInput;
    }
    
    /**
     * @return Returns the remark.
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param remark
     *            The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * @return Returns the modifyPerson.
     */
    public String getModifyPerson() {
        return modifyPerson;
    }
    
    /**
     * @param modifyPerson
     *            The modifyPerson to set.
     */
    public void setModifyPerson(String modifyPerson) {
        this.modifyPerson = modifyPerson;
    }
    
    /**
     * @return Returns the modifyTime.
     */
    public String getModifyTime() {
        return modifyTime;
    }
    
    /**
     * @param modifyTime
     *            The modifyTime to set.
     */
    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
    
}
