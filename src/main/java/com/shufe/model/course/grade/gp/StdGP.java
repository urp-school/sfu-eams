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
 * chaostone             2007-1-2            Created
 *  
 ********************************************************************************/

package com.shufe.model.course.grade.gp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 学生历学期的成绩绩点和这些学期的平均绩点
 * 
 * @author chaostone
 * @see StdGPPerTerm
 */
public class StdGP {
    
    private Student std;
    
    /**
     * 每学期平均绩点
     * 
     * @see StdGPPerTerm
     */
    private List GPList;
    
    /**
     * 总平均绩点
     */
    private Float GPA;
    
    /**
     * 总学分
     */
    private Float credits;
    
    /**
     * 成绩的门数
     */
    private Integer count;
    
    /**
     * 平均分
     */
    private Float GA;
    
    private Map calendarGP;
    
    public StdGP(Student std) {
        this.std = std;
        this.GPList = new ArrayList();
        this.credits = new Float(0);
        this.GA = new Float(0);
        this.count = new Integer(0);
        this.GPA = new Float(0);
    }
    
    public Float getGP(TeachCalendar calendar) {
        StdGPPerTerm gpterm = getStdGPPerTerm(calendar);
        if (null == gpterm) {
            return null;
        } else {
            return gpterm.getGPA();
        }
    }
    
    public StdGPPerTerm getStdGPPerTerm(TeachCalendar calendar) {
        if (null == calendarGP) {
            calendarGP = new HashMap();
            for (Iterator iter = getGPList().iterator(); iter.hasNext();) {
                StdGPPerTerm gpterm = (StdGPPerTerm) iter.next();
                calendarGP.put(gpterm.getCalendar(), gpterm);
            }
        }
        return (StdGPPerTerm) calendarGP.get(calendar);
    }
    
    public void add(StdGPPerTerm stdGPPerTerm) {
        if (null == GPList) {
            this.GPList = new ArrayList();
        }
        GPList.add(stdGPPerTerm);
    }
    
    public List getGPList() {
        return GPList;
    }
    
    public void setGPList(List list) {
        GPList = list;
    }
    
    public Student getStd() {
        return std;
    }
    
    public void setStd(Student student) {
        this.std = student;
    }
    
    public Float getGPA() {
        return GPA;
    }
    
    public void setGPA(Float totalGP) {
        this.GPA = totalGP;
    }
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
    
    public Float getCredits() {
        return credits;
    }
    
    public void setCredits(Float credits) {
        this.credits = credits;
    }
    
    public Float getGA() {
        return GA;
    }
    
    public void setGA(Float ga) {
        GA = ga;
    }
}
