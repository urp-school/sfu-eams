//$Id: SecondStudent.java,v 1.1 2007-6-25 下午07:47:27 chaostone Exp $
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
 * chenweixiong              2007-6-25         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.major2nd;

import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.std.info.Student;
import com.ekingstar.eams.system.baseinfo.Major;
import com.ekingstar.eams.system.baseinfo.MajorField;

/**
 * 学生双专业信息
 * 
 * @author chaostone
 * 
 */
public class StdMajor2nd extends LongIdObject {
    
    private static final long serialVersionUID = -4257610641021003109L;
    
    private Student std;
    
    /** 报读年级 */
    private String grade;
    
    /** 毕业时间 */
    private Date graduateOn;
    
    /** 第二专业代码 */
    private Major major;
    
    /** 第二专业方向代码 */
    private MajorField majorField;
    
    /** 第二专业是否就读 */
    private Boolean isStudy;
    
    /** 第二专业是否写论文 */
    private Boolean isThesisNeed;
    
    public Student getStd() {
        return std;
    }
    
    public void setStd(Student std) {
        this.std = std;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public Date getGraduateOn() {
        return graduateOn;
    }
    
    public void setGraduateOn(Date graduateOn) {
        this.graduateOn = graduateOn;
    }
    
    public Major getMajor() {
        return major;
    }
    
    public void setMajor(Major major) {
        this.major = major;
    }
    
    public MajorField getMajorField() {
        return majorField;
    }
    
    public void setMajorField(MajorField majorField) {
        this.majorField = majorField;
    }
    
    public Boolean getIsStudy() {
        return isStudy;
    }
    
    public void setIsStudy(Boolean isStudy) {
        this.isStudy = isStudy;
    }
    
    public Boolean getIsThesisNeed() {
        return isThesisNeed;
    }
    
    public void setIsThesisNeed(Boolean isThesisNeed) {
        this.isThesisNeed = isThesisNeed;
    }
}
