//$Id: SubstituteCourseGrade.java,v 1.1 2009-2-15 下午01:20:25 zhouqi Exp $
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
 * zhouqi              2009-2-15             Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.teach.program.service.impl;

import java.util.Set;

public class SubstituteGroupGrade implements Comparable {
    
    float gpa;
    
    float ga;
    
    float score;
    
    Set courses;
    
    boolean fullGrade;
    
    public SubstituteGroupGrade(float score, float credit, float gpa, float ga, Set courses) {
        super();
        this.score = score;
        if (0 != credit) {
            this.gpa = gpa / credit;
            this.ga = ga / credit;
        } else {
            this.gpa = gpa;
            this.ga = ga;
        }
        this.courses = courses;
    }
    
    public float getGpa() {
        return gpa;
    }
    
    public Set getCourses() {
        return courses;
    }
    
    public void setGpa(float gpa) {
        this.gpa = gpa;
    }
    
    public void setCourses(Set substitute) {
        this.courses = substitute;
    }
    
    public float getGa() {
        return ga;
    }
    
    public void setGa(float ga) {
        this.ga = ga;
    }
    
    public boolean isFullGrade() {
        return fullGrade;
    }
    
    public void setFullGrade(boolean fullGrade) {
        this.fullGrade = fullGrade;
    }
    
    /**
     * 小的放前面<br>
     * fullGrade,gpa,ga
     */
    public int compareTo(Object arg0) {
        SubstituteGroupGrade other = (SubstituteGroupGrade) arg0;
        if (!fullGrade && other.fullGrade) {
            return -1;
        } else if (fullGrade && !other.fullGrade) {
            return 1;
        } else {
            int gpaCmp = Float.compare(gpa, other.gpa);
            if (gpaCmp == 0) {
                int gaCmp = Float.compare(ga, other.ga);
                if (gaCmp > 0) {
                    return Float.compare(score, other.score);
                }
                return gaCmp;
            } else {
                return gpaCmp;
            }
        }
    }
    
    public float getScore() {
        return score;
    }
    
    public void setScore(float score) {
        this.score = score;
    }
    
}
