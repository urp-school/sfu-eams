//$Id: TopicOpenStatistic.java,v 1.1 2006/11/05 09:47:42 cwx Exp $
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
 * chenweixiong              2006-11-5         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.thesis.topicOpen;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.Department;

public class TopicOpenStatistic {
    
    private Department department = new Department();
    
    private StudentType stdType;
    
    private Integer personNo;
    
    private Integer hasFinishNo;
    
    private Float hasFinishRate;
    
    /**
     * @return Returns the department.
     */
    public Department getDepartment() {
        return department;
    }
    
    /**
     * @param department
     *            The department to set.
     */
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    /**
     * @return Returns the hasFinishNo.
     */
    public Integer getHasFinishNo() {
        return hasFinishNo;
    }
    
    /**
     * @param hasFinishNo
     *            The hasFinishNo to set.
     */
    public void setHasFinishNo(Integer hasFinishNo) {
        this.hasFinishNo = hasFinishNo;
    }
    
    /**
     * @return Returns the hasFinishRate.
     */
    public Float getHasFinishRate() {
        return hasFinishRate;
    }
    
    /**
     * @param hasFinishRate
     *            The hasFinishRate to set.
     */
    public void setHasFinishRate(Float hasFinishRate) {
        this.hasFinishRate = hasFinishRate;
    }
    
    /**
     * @return Returns the personNo.
     */
    public Integer getPersonNo() {
        return personNo;
    }
    
    /**
     * @param personNo
     *            The personNo to set.
     */
    public void setPersonNo(Integer personNo) {
        this.personNo = personNo;
    }
    
    /**
     * @return Returns the stdType.
     */
    public StudentType getStdType() {
        return stdType;
    }
    
    /**
     * @param stdType
     *            The stdType to set.
     */
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
}
