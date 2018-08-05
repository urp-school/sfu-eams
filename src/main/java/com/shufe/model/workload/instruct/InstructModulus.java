//$Id: InstructModulus.java,v 1.2 2006/08/25 06:48:40 cwx Exp $
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
 * chenweixiong              2005-11-17         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.instruct;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.shufe.model.workload.Modulus;

public class InstructModulus extends Modulus {
    
    /**
     * 
     */
    private static final long serialVersionUID = 6364902741261251064L;
    
    public static String PARCTICE = "practice";
    
    public static String THESIS = "thesis";
    
    public static String USUAL = "usual";
    
    private String itemType; // 工作量系数类别
    
    /**
     * @return Returns the itemType.
     */
    public String getItemType() {
        return itemType;
    }
    
    /**
     * @param itemType
     *            The itemType to set.
     */
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    
    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean isSame(Object object) {
        if (!(object instanceof InstructModulus)) {
            return false;
        }
        InstructModulus rhs = (InstructModulus) object;
        return new EqualsBuilder().append(getStudentType(), rhs.getStudentType()).append(
                getItemType(), rhs.getItemType()).isEquals();
    }
    
}
