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
 * chaostone             2006-6-14            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.election;

import java.io.Serializable;

import com.ekingstar.eams.system.basecode.industry.CourseType;

public class CreditNeed implements Serializable {
    
    private static final long serialVersionUID = 3227060458839519618L;
    
    private CourseType type;
    
    private Float credit;
    
    public CreditNeed(CourseType type, Float credit) {
        this.type = type;
        this.credit = credit;
    }
    
    public Float getCredit() {
        return credit;
    }
    
    public void setCredit(Float credit) {
        this.credit = credit;
    }
    
    public CourseType getType() {
        return type;
    }
    
    public void setType(CourseType type) {
        this.type = type;
    }
    
}
