//$Id: StudentTypeAndTeacherTitle.java,v 1.1 2006/08/02 00:53:08 duanth Exp $
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
 * chenweixiong              2006-2-20         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.stat;

import java.util.ArrayList;
import java.util.List;

import com.ekingstar.eams.system.baseinfo.StudentType;

public class StudentTypeAndTeacherTitle {
    
    private StudentType stdType;
    
    private List titleValueList = new ArrayList();
    
    private Integer totleValue = new Integer(0);
    
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
    
    /**
     * @return Returns the titleValueList.
     */
    public List getTitleValueList() {
        return titleValueList;
    }
    
    /**
     * @param titleValueList
     *            The titleValueList to set.
     */
    public void setTitleValueList(List titleValueList) {
        this.titleValueList = titleValueList;
    }
    
    /**
     * @return Returns the totleValue.
     */
    public Integer getTotleValue() {
        return totleValue;
    }
    
    /**
     * @param totleValue
     *            The totleValue to set.
     */
    public void setTotleValue(Integer totleValue) {
        this.totleValue = totleValue;
    }
    
}
