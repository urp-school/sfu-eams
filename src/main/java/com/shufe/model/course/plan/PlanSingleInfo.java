//$Id: PlanInfo.java,v 1.1 2009-2-1 下午03:21:29 zhouqi Exp $
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
 * zhouqi              2009-2-1             Created
 *  
 ********************************************************************************/

package com.shufe.model.course.plan;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * @author zhouqi
 * 
 */
public class PlanSingleInfo extends LongIdObject {
    
    private static final long serialVersionUID = -3514707738563781759L;
    
    private String enrollTurn;
    
    private String stdTypeCode;
    
    private String stdTypeName;
    
    private String departmentCode;
    
    private String departmentName;
    
    private String majorCode;
    
    private String majorName;
    
    private String majorTypeCode;
    
    private String majorTypeName;
    
    private Integer terms = new Integer(0);
    
    private String termValues;
    
    private Integer creditHour = new Integer(0);
    
    private Float credit = new Float(0);
    
    private Integer groupCount = new Integer(0);
    
    private Integer allCourseCount = new Integer(0);
    
    private String teacherNames;
    
    private Boolean isStdPerson = Boolean.FALSE;
    
    private String stdCode;
    
    private String stdName;
    
    private Boolean isConfirm = Boolean.FALSE;
    
    private String remark;
    
    public String getEnrollTurn() {
        return enrollTurn;
    }
    
    public void setEnrollTurn(String enrollTurn) {
        this.enrollTurn = enrollTurn;
    }
    
    public String getStdTypeCode() {
        return stdTypeCode;
    }
    
    public void setStdTypeCode(String stdTypeCode) {
        this.stdTypeCode = stdTypeCode;
    }
    
    public String getStdTypeName() {
        return stdTypeName;
    }
    
    public void setStdTypeName(String stdTypeName) {
        this.stdTypeName = stdTypeName;
    }
    
    public String getDepartmentCode() {
        return departmentCode;
    }
    
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
    
    public String getDepartmentName() {
        return departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    public String getMajorCode() {
        return majorCode;
    }
    
    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }
    
    public String getMajorName() {
        return majorName;
    }
    
    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }
    
    public String getMajorTypeCode() {
        return majorTypeCode;
    }
    
    public void setMajorTypeCode(String majorTypeCode) {
        this.majorTypeCode = majorTypeCode;
    }
    
    public String getMajorTypeName() {
        return majorTypeName;
    }
    
    public void setMajorTypeName(String majorTypeName) {
        this.majorTypeName = majorTypeName;
    }
    
    public Integer getTerms() {
        return terms;
    }
    
    public void setTerms(Integer terms) {
        this.terms = terms;
    }
    
    public String getTermValues() {
        return termValues;
    }
    
    public void setTermValues(String termValues) {
        this.termValues = termValues;
    }
    
    public Integer getCreditHour() {
        return creditHour;
    }
    
    public void setCreditHour(Integer creditHour) {
        this.creditHour = creditHour;
    }
    
    public Float getCredit() {
        return credit;
    }
    
    public void setCredit(Float credit) {
        this.credit = credit;
    }
    
    public Integer getGroupCount() {
        return groupCount;
    }
    
    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }
    
    public Integer getAllCourseCount() {
        return allCourseCount;
    }
    
    public void setAllCourseCount(Integer allCourseCount) {
        this.allCourseCount = allCourseCount;
    }
    
    public String getTeacherNames() {
        return teacherNames;
    }
    
    public void setTeacherNames(String teacherNames) {
        this.teacherNames = teacherNames;
    }
    
    public Boolean getIsStdPerson() {
        return isStdPerson;
    }
    
    public void setIsStdPerson(Boolean isStdPerson) {
        this.isStdPerson = isStdPerson;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Boolean getIsConfirm() {
        return isConfirm;
    }
    
    public void setIsConfirm(Boolean isConfirm) {
        this.isConfirm = isConfirm;
    }
    
    public String getStdCode() {
        return stdCode;
    }
    
    public void setStdCode(String stdCode) {
        this.stdCode = stdCode;
    }
    
    public String getStdName() {
        return stdName;
    }
    
    public void setStdName(String stdName) {
        this.stdName = stdName;
    }
}
