//$Id: CourseAndFee.java,v 1.2 2006/08/25 06:48:40 cwx Exp $
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
 * chenweixiong              2005-12-12         Created
 *  
 ********************************************************************************/

package com.shufe.model.fee;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;

public class CourseAndFee {
    
    private String stdCode; // 学号
    
    private int number; // 选课 门数
    
    private List feeList = new ArrayList(); // 收费list
    
    /**
     * @return Returns the feeList.
     */
    public List getFeeList() {
        return feeList;
    }
    
    /**
     * @param feeList
     *            The feeList to set.
     */
    public void setFeeList(List feeList) {
        this.feeList = feeList;
    }
    
    /**
     * @return Returns the number.
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * @param number
     *            The number to set.
     */
    public void setNumber(int number) {
        this.number = number;
    }
    
    /**
     * @param stdCode
     *            The stdCode to set.
     */
    public void setStdCode(String stdCode) {
        this.stdCode = stdCode;
    }
    
    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof CourseAndFee)) {
            return false;
        }
        CourseAndFee rhs = (CourseAndFee) object;
        return new EqualsBuilder().append(this.stdCode, rhs.stdCode).isEquals();
    }
    
}
