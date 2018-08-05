//$Id: StdTermCredit.java,v 1.1 2008-1-25 下午05:54:17 zhouqi Exp $
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
 * zhouqi              2008-1-25         	Created
 *  
 ********************************************************************************/

package com.shufe.model.course.grade.stat;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * @author zhouqi
 */
public class StdTermCredit extends LongIdObject {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5594296033065495823L;

	private TeachCalendar calendar;
    
    private Student std;
    
    private Float totalCredits;
    
    private Float credits;
    
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    public Float getCredits() {
        return credits;
    }
    
    public void setCredits(Float credits) {
        this.credits = credits;
    }
    
    public Student getStd() {
        return std;
    }
    
    public void setStd(Student std) {
        this.std = std;
    }
    
    public Float getTotalCredits() {
        return totalCredits;
    }
    
    public void setTotalCredits(Float totalCredits) {
        this.totalCredits = totalCredits;
    }
    
}
