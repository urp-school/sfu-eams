//$Id: Teacher.java,v 1.4 2006/12/19 10:07:07 duanth Exp $
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
 * chaostone            2005-09-09          Created
 * ZQ                   2007-10-17          增加了教师在职状态属性
 ********************************************************************************/

package com.shufe.model.system.baseinfo;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.EngagementType;
import com.ekingstar.eams.system.basecode.industry.TeacherType;
import com.ekingstar.eams.system.basecode.industry.TeacherWorkState;
import com.ekingstar.eams.system.basecode.state.Country;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.basecode.state.Nation;
import com.ekingstar.eams.system.basecode.state.PoliticVisage;
import com.ekingstar.eams.system.basecode.state.TeacherTitle;
import com.ekingstar.eams.system.basecode.state.TeacherTitleLevel;
import com.ekingstar.eams.system.baseinfo.TeacherContactInfo;
import com.shufe.model.course.arrange.AvailableTime;

/**
 * 学校教职员工基础信息，包括教师、工作人员的基类
 * 
 * @author chaostone 2005-9-9
 */
public class Teacher extends LongIdObject implements Serializable,
        com.ekingstar.eams.system.baseinfo.Teacher {
    
    private static final long serialVersionUID = 3465703203389846255L;
    
    /** 教师职工号 */
    protected String code;
    
    /** 部门 */
    protected com.ekingstar.eams.system.baseinfo.Department department;
    
    /** 姓名 */
    protected String name;
    
    /** 英文姓名 */
    protected String engName;
    
    /** 性别 */
    protected Gender gender = new Gender();
    
    /** 国籍 */
    protected Country country = new Country();
    
    /** 证件号 */
    protected String credentialNumber;
    
    /** 出生日期 */
    protected Date birthday;
    
    /** 民族 */
    protected Nation nation = new Nation();
    
    /** 职称 */
    protected TeacherTitle title = new TeacherTitle();
    
    /** 现职称年月 */
    protected Date dateOfTitle;
    
    /** 教师职称等级 */
    protected TeacherTitleLevel titleLevel = new TeacherTitleLevel();
    
    /** 行政职务 */
    protected String duty;
    
    /** 教职工类别 */
    protected TeacherType teacherType = new TeacherType();
    
    /** 聘任情况 */
    protected EngagementType engagementType = new EngagementType();
    
    /** 来校年月 */
    protected Date dateOfJoin;
    
    /** 个人简介 */
    protected String resume;
    
    /** 是否退休返聘 */
    protected Boolean isEngageFormRetire;
    
    /** 制定时间 */
    protected Date createAt;
    
    /** 修改时间 */
    protected Date modifyAt;
    
    /** 是否兼职 */
    protected Boolean isConcurrent;
    
    /** 备注 */
    protected String remark;
    
    /** 教师在职状态 */
    private TeacherWorkState workState;
    
    /** 教师联系信息 */
    protected TeacherAddressInfo addressInfo = new TeacherAddressInfo();
    
    /** 教师学历学位信息 */
    protected TeacherDegreeInfo degreeInfo = new TeacherDegreeInfo();
    
    /** 是否任课 */
    protected Boolean isTeaching;
    
    /**
     * 参加学术团体、任何职务
     * 
     * @deprecated
     */
    protected String jobInfo;
    
    /**
     * 上课情况
     * 
     * @deprecated
     */
    protected String summaryOfTeaching;
    
    /**
     * 额定工作量
     * 
     * @deprecated
     */
    private Float ratingWorkload;
    
    /** 教师可用时间 */
    private AvailableTime availableTime;
    
    public Teacher() {
    }
    
    public Teacher(Long id) {
        this.id = id;
    }
    
    /**
     * @return
     */
    public Date getBirthday() {
        return birthday;
    }
    
    public Integer getAge() {
        if (null == getBirthday())
            return null;
        else {
            GregorianCalendar calendar = new GregorianCalendar();
            int year = calendar.get(Calendar.YEAR);
            calendar.setTime(getBirthday());
            return new Integer(year - calendar.get(Calendar.YEAR));
        }
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public Country getCountry() {
        return country;
    }
    
    public void setCountry(Country country) {
        this.country = country;
    }
    
    public String getCredentialNumber() {
        return credentialNumber;
    }
    
    public void setCredentialNumber(String credentialNumber) {
        this.credentialNumber = credentialNumber;
    }
    
    public Date getDateOfJoin() {
        return dateOfJoin;
    }
    
    public void setDateOfJoin(Date dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }
    
    public Date getDateOfTitle() {
        return dateOfTitle;
    }
    
    public void setDateOfTitle(Date dateOfTitle) {
        this.dateOfTitle = dateOfTitle;
    }
    
    public com.ekingstar.eams.system.baseinfo.Department getDepartment() {
        return department;
    }
    
    public void setDepartment(com.ekingstar.eams.system.baseinfo.Department department) {
        this.department = department;
    }
    
    public String getDuty() {
        return duty;
    }
    
    public void setDuty(String duty) {
        this.duty = duty;
    }
    
    public EngagementType getEngagementType() {
        return engagementType;
    }
    
    public void setEngagementType(EngagementType engagementType) {
        this.engagementType = engagementType;
    }
    
    public String getResume() {
        return resume;
    }
    
    public void setResume(String introduction) {
        this.resume = introduction;
    }
    
    public Boolean getIsConcurrent() {
        return isConcurrent;
    }
    
    public void setIsConcurrent(Boolean isConcurrent) {
        this.isConcurrent = isConcurrent;
    }
    
    public Boolean getIsEngageFormRetire() {
        return isEngageFormRetire;
    }
    
    public void setIsEngageFormRetire(Boolean isEngageFormRetire) {
        this.isEngageFormRetire = isEngageFormRetire;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Nation getNation() {
        return nation;
    }
    
    public void setNation(Nation nation) {
        this.nation = nation;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public TeacherType getTeacherType() {
        return teacherType;
    }
    
    public void setTeacherType(TeacherType teacherType) {
        this.teacherType = teacherType;
    }
    
    public TeacherTitle getTitle() {
        return title;
    }
    
    public void setTitle(TeacherTitle title) {
        this.title = title;
    }
    
    public TeacherTitleLevel getTitleLevel() {
        return titleLevel;
    }
    
    public void setTitleLevel(TeacherTitleLevel titleLevel) {
        this.titleLevel = titleLevel;
    }
    
    public Date getCreateAt() {
        return createAt;
    }
    
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    
    public Date getModifyAt() {
        return modifyAt;
    }
    
    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }
    
    public int hashCode() {
        return new HashCodeBuilder(-1686949871, 1096576027).append(this.id).toHashCode();
    }
    
    public boolean checkId() {
        return (null != id) && (id.intValue() != 0);
    }
    
    public boolean equals(Object object) {
        if (!(object instanceof Teacher)) {
            return false;
        }
        Teacher rhs = (Teacher) object;
        return new EqualsBuilder().append(this.id, rhs.getId()).isEquals();
    }
    
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("name", this.name).toString();
    }
    
    public String getEngName() {
        return engName;
    }
    
    public void setEngName(String engName) {
        this.engName = engName;
    }
    
    public TeacherWorkState getWorkState() {
        return workState;
    }
    
    public void setWorkState(TeacherWorkState workState) {
        this.workState = workState;
    }
    
    /**
     * @deprecated 先返回null,否则很多基于creteria的查询会误以为有这个属性
     */
    public Boolean getState() {
        return null;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public TeacherContactInfo getContactInfo() {
        return null;
    }
    
    public String getCredentialNo() {
        return null;
    }
    
    public Date getJoinOn() {
        return getDateOfJoin();
    }
    
    public PoliticVisage getPoliticVisage() {
        return null;
    }
    
    public Integer getSource() {
        return null;
    }
    
    public String getAbbreviation() {
        return null;
    }
    
    public int compareTo(Object arg0) {
        Teacher other = (Teacher) arg0;
        return getCode().compareTo(other.getCode());
    }
    
    public TeacherAddressInfo getAddressInfo() {
        return addressInfo;
    }
    
    public void setAddressInfo(TeacherAddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }
    
    public TeacherDegreeInfo getDegreeInfo() {
        return degreeInfo;
    }
    
    public void setDegreeInfo(TeacherDegreeInfo degreeInfo) {
        this.degreeInfo = degreeInfo;
    }
    
    public Boolean getIsTeaching() {
        return isTeaching;
    }
    
    public void setIsTeaching(Boolean isTeaching) {
        this.isTeaching = isTeaching;
    }
    
    public String getSummaryOfTeaching() {
        return summaryOfTeaching;
    }
    
    public void setSummaryOfTeaching(String summaryOfTeaching) {
        this.summaryOfTeaching = summaryOfTeaching;
    }
    
    public AvailableTime getAvailableTime() {
        return availableTime;
    }
    
    public void setAvailableTime(AvailableTime availableTime) {
        this.availableTime = availableTime;
    }
    
    public Float getRatingWorkload() {
        return ratingWorkload;
    }
    
    public void setRatingWorkload(Float ratingWorkload) {
        this.ratingWorkload = ratingWorkload;
    }
    
    public String getJobInfo() {
        return jobInfo;
    }
    
    public void setJobInfo(String jobInfo) {
        this.jobInfo = jobInfo;
    }
    
    public void setAbbreviation(String abbreviation) {
        // TODO Auto-generated method stub
        
    }
    
    public void setState(Boolean state) {
        // TODO Auto-generated method stub
        
    }
}
