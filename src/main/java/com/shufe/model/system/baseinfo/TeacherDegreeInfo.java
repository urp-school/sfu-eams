//$Id: TeacherDegreeInfo.java,v 1.4 2006/12/15 12:06:26 duanth Exp $
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
 * chaostone             2005-10-31         Created
 *  
 ********************************************************************************/

package com.shufe.model.system.baseinfo;

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.School;
import com.ekingstar.eams.system.basecode.state.Degree;
import com.ekingstar.eams.system.basecode.state.EduDegree;

/**
 * 教师学历学位信息
 * 
 * @author chaostone
 * 
 */
public class TeacherDegreeInfo extends LongIdObject {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2226562395222175752L;
    
    /** 国内最高学历 */
    protected EduDegree eduDegreeInside = new EduDegree();
    
    /** 国外最高学历 */
    protected EduDegree eduDegreeOutside;
    
    /** 国内现学历年月 */
    protected Date dateOfEduDegreeInside;
    
    /** 国外现学历年月 */
    protected Date dateOfEduDegreeOutside;
    
    /** 现学位 */
    protected Degree degree = new Degree();
    
    /** 现学位年月 */
    protected Date dateOfDegree;
    
    /** 最后毕业学校 */
    protected School graduateSchool = new School();
    
    /** 教学经历 */
    protected String experience;
    
    /** 科研成果 */
    protected String achievements;
    
    /** 学术兼职 */
    protected String partTimeJob;
    
    /**
     * @return Returns the dateOfDegree.
     */
    public Date getDateOfDegree() {
        return dateOfDegree;
    }
    
    /**
     * @param dateOfDegree
     *            The dateOfDegree to set.
     */
    public void setDateOfDegree(Date dateOfDegree) {
        this.dateOfDegree = dateOfDegree;
    }
    
    public Date getDateOfEduDegreeInside() {
        return dateOfEduDegreeInside;
    }
    
    public void setDateOfEduDegreeInside(Date dateOfEduDegreeInside) {
        this.dateOfEduDegreeInside = dateOfEduDegreeInside;
    }
    
    public Date getDateOfEduDegreeOutside() {
        return dateOfEduDegreeOutside;
    }
    
    public void setDateOfEduDegreeOutside(Date dateOfEduDegreeOutside) {
        this.dateOfEduDegreeOutside = dateOfEduDegreeOutside;
    }
    
    /**
     * @return Returns the degree.
     */
    public Degree getDegree() {
        return degree;
    }
    
    /**
     * @param degree
     *            The degree to set.
     */
    public void setDegree(Degree degree) {
        this.degree = degree;
    }
    
    /**
     * @return Returns the graduateSchool.
     */
    public School getGraduateSchool() {
        return graduateSchool;
    }
    
    /**
     * @param graduateSchool
     *            The graduateSchool to set.
     */
    public void setGraduateSchool(School graduateSchool) {
        this.graduateSchool = graduateSchool;
    }
    
    public EduDegree getEduDegreeInside() {
        return eduDegreeInside;
    }
    
    public void setEduDegreeInside(EduDegree eduDegreeInside) {
        this.eduDegreeInside = eduDegreeInside;
    }
    
    public EduDegree getEduDegreeOutside() {
        return eduDegreeOutside;
    }
    
    public void setEduDegreeOutside(EduDegree eduDegreeOutside) {
        this.eduDegreeOutside = eduDegreeOutside;
    }
    
    public String getExperience() {
        return experience;
    }
    
    public void setExperience(String experience) {
        this.experience = experience;
    }
    
    public String getAchievements() {
        return achievements;
    }
    
    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }
    
    public String getPartTimeJob() {
        return partTimeJob;
    }
    
    public void setPartTimeJob(String partTimeJob) {
        this.partTimeJob = partTimeJob;
    }
}
