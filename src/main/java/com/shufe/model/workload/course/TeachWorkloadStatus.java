//$Id: TeachWorkloadLogStatus.java,v 1.1 2008-3-4 下午03:58:14 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2008-3-4         	Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.course;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * @author zhouqi
 */
public class TeachWorkloadStatus extends LongIdObject {
    
    private static final long serialVersionUID = 1L;
    
    /** 上课人数 */
    private Integer studentNumber;
    
    /** 工作量系统 */
    private TeachModulus modulus;
    
    /** 上课周数 */
    private Float weeks;
    
    /** 总课时 */
    private Float totleCourses;
    
    /** 周课时 */
    private Float classNumberOfWeek;
    
    /** 授课工作量 */
    private Float totleWorkload;
    
    /** 已支付报酬 */
    private Boolean payReward;
    
    /** 计工作量 */
    private Boolean calcWorkload;
    
    /** 备注 */
    private String remark;
    
    /**
     * @return the calcWorkload
     */
    public Boolean getCalcWorkload() {
        return calcWorkload;
    }
    
    /**
     * @param calcWorkload the calcWorkload to set
     */
    public void setCalcWorkload(Boolean calcWorkload) {
        this.calcWorkload = calcWorkload;
    }
    
    /**
     * @return the classNumberOfWeek
     */
    public Float getClassNumberOfWeek() {
        return classNumberOfWeek;
    }
    
    /**
     * @param classNumberOfWeek the classNumberOfWeek to set
     */
    public void setClassNumberOfWeek(Float classNumberOfWeek) {
        this.classNumberOfWeek = classNumberOfWeek;
    }
    
    /**
     * @return the modulus
     */
    public TeachModulus getModulus() {
        return modulus;
    }
    
    /**
     * @param modulus the modulus to set
     */
    public void setModulus(TeachModulus modulus) {
        this.modulus = modulus;
    }
    
    /**
     * @return the payReward
     */
    public Boolean getPayReward() {
        return payReward;
    }
    
    /**
     * @param payReward the payReward to set
     */
    public void setPayReward(Boolean payReward) {
        this.payReward = payReward;
    }
    
    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * @return the studentNumber
     */
    public Integer getStudentNumber() {
        return studentNumber;
    }
    
    /**
     * @param studentNumber the studentNumber to set
     */
    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }
    
    /**
     * @return the totleCourses
     */
    public Float getTotleCourses() {
        return totleCourses;
    }
    
    /**
     * @param totleCourses the totleCourses to set
     */
    public void setTotleCourses(Float totleCourses) {
        this.totleCourses = totleCourses;
    }
    
    /**
     * @return the weeks
     */
    public Float getWeeks() {
        return weeks;
    }
    
    /**
     * @param weeks the weeks to set
     */
    public void setWeeks(Float weeks) {
        this.weeks = weeks;
    }
    
    /**
     * @return the totleWorkload
     */
    public Float getTotleWorkload() {
        return totleWorkload;
    }
    
    /**
     * @param totleWorkload the totleWorkload to set
     */
    public void setTotleWorkload(Float totleWorkload) {
        this.totleWorkload = totleWorkload;
    }
}
