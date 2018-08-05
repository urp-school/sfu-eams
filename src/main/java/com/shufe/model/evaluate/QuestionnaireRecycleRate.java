//$Id: QuestionnaireRecycleRate.java,v 1.3 2007/01/11 08:56:03 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-10-25         Created
 *  
 ********************************************************************************/

package com.shufe.model.evaluate;

import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 评教回收率
 * 
 * @author hj 2005-10-25 RecycleRate.java has been created
 */
public class QuestionnaireRecycleRate extends LongIdObject {
    
    private static final long serialVersionUID = 2739027252093759533L;
    
    /** 上课院系（学生所属院系） */
    private Department department = new Department();
    
    /** 实评人次 */
    private Long evaluateAmount;
    
    /** 总评人次 */
    private Long totleEvaluateAmount;
    
    /** 人次比例 */
    private Float amountRate;
    
    /** 实评人数 */
    private Long evaluatePerson;
    
    /** 总评人数 */
    private Long totlePerson;
    
    /** 人数比例 */
    private Float totleRate;
    
    /** 统计时间 */
    private Date statisticTime;
    
    /** 学生类别 */
    private StudentType studentType;
    
    /** 教学日历 */
    private TeachCalendar teachCalendar = new TeachCalendar();
    
    /**
     * @return 返回 department.
     */
    public Department getDepartment() {
        return department;
    }
    
    /**
     * @return 返回 evaluateAmount.
     */
    public Long getEvaluateAmount() {
        return evaluateAmount;
    }
    
    /**
     * @param department
     *            要设置的 department.
     */
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    /**
     * @param evaluateAmount
     *            要设置的 evaluateAmount.
     */
    public void setEvaluateAmount(Long evaluateAmount) {
        this.evaluateAmount = evaluateAmount;
    }
    
    /**
     * @return 返回 statisticTime.
     */
    public Date getStatisticTime() {
        return statisticTime;
    }
    
    /**
     * @return 返回 totleEvaluateAmount.
     */
    public Long getTotleEvaluateAmount() {
        return totleEvaluateAmount;
    }
    
    /**
     * @param statisticTime
     *            要设置的 statisticTime.
     */
    public void setStatisticTime(Date statisticTime) {
        this.statisticTime = statisticTime;
    }
    
    /**
     * @param totleEvaluateAmount
     *            要设置的 totleEvaluateAmount.
     */
    public void setTotleEvaluateAmount(Long totleEvaluateAmount) {
        this.totleEvaluateAmount = totleEvaluateAmount;
    }
    
    /**
     * @return 返回 studentType.
     */
    public StudentType getStudentType() {
        return studentType;
    }
    
    /**
     * @param studentType
     *            要设置的 studentType.
     */
    public void setStudentType(StudentType studentType) {
        this.studentType = studentType;
    }
    
    /**
     * @return Returns the evaluatePerson.
     */
    public Long getEvaluatePerson() {
        return evaluatePerson;
    }
    
    /**
     * @param evaluatePerson
     *            The evaluatePerson to set.
     */
    public void setEvaluatePerson(Long evaluatePerson) {
        this.evaluatePerson = evaluatePerson;
    }
    
    /**
     * @return Returns the totlePerson.
     */
    public Long getTotlePerson() {
        return totlePerson;
    }
    
    /**
     * @param totlePerson
     *            The totlePerson to set.
     */
    public void setTotlePerson(Long totlePerson) {
        this.totlePerson = totlePerson;
    }
    
    /**
     * @param amountRate
     *            The amountRate to set.
     */
    public void setAmountRate(Float amountRate) {
        this.amountRate = amountRate;
    }
    
    /**
     * @param totleRate
     *            The totleRate to set.
     */
    public void setTotleRate(Float totleRate) {
        this.totleRate = totleRate;
    }
    
    /**
     * @return Returns the amountRate.
     */
    public Float getAmountRate() {
        return amountRate;
    }
    
    /**
     * @return Returns the totleRate.
     */
    public Float getTotleRate() {
        return totleRate;
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
}
