//$Id: StudyProduct.java,v 1.1 2007-3-5 下午03:39:55 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-5         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.study;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.std.Student;

/**
 * 科研成果
 * 
 * @author chaostone
 */
public class StudyProduct extends LongIdObject {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 306409289752835052L;

	private static String SIMPLEDATEFORMATE = "yyyy-MM-dd";
    
    /** 名称 */
    protected String name;
    
    /** 学生 */
    protected Student student = new Student();
    
    /** 独立完成字数 */
    protected Float wordCount;
    
    /** 作者人数 */
    protected Integer authorCount;
    
    /** 科研排序 */
    protected Integer authorIndex;
    
    /** 项目获奖 */
    private Set awards = new HashSet();
    
    /** 是否通过审核 */
    private Boolean isPassCheck = Boolean.FALSE;
    
    /** 创建时间 */
    private Date createOn = new Date();
    
    /** 备注 */
    private String remark;
    
    public Boolean getIsAwarded() {
        return Boolean.valueOf(null != getAwards() && getAwards().size() > 0);
    }
    
    /** 科研排序字符串描述 */
    public String getRate() {
        if (null != getAuthorCount() && null != getAuthorIndex()) {
            return getAuthorIndex() + "/" + getAuthorCount();
        } else
            return "";
    }
    
    public static Class getStudyProductType(String type) {
        if (type.equals("studyThesis")) {
            return StudyThesis.class;
        } else if (type.equals("literature")) {
            return Literature.class;
        } else if (type.equals("project")) {
            return Project.class;
        } else if ("studyMeeting".equals(type)) {
            return StudyMeeting.class;
        } else {
            throw new RuntimeException("not supported StudyProduct type:" + type);
        }
    }
    
    public static StudyProduct getStudyProduct(String type) {
        try {
            return (StudyProduct) getStudyProductType(type).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("not supported StudyProduct type");
        }
        
    }
    
    public Set getAwards() {
        return awards;
    }
    
    public void setAwards(Set awards) {
        this.awards = awards;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    /**
     * @return Returns the wordCount.
     */
    public Float getWordCount() {
        return wordCount;
    }
    
    /**
     * @param wordCount The wordCount to set.
     */
    public void setWordCount(Float wordCount) {
        this.wordCount = wordCount;
    }
    
    public Boolean getIsPassCheck() {
        return isPassCheck;
    }
    
    public void setIsPassCheck(Boolean isPassCheck) {
        this.isPassCheck = isPassCheck;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * @return Returns the createOn.
     */
    public Date getCreateOn() {
        return createOn;
    }
    
    public Integer getAuthorCount() {
        return authorCount;
    }
    
    public void setAuthorCount(Integer authorCount) {
        this.authorCount = authorCount;
    }
    
    public Integer getAuthorIndex() {
        return authorIndex;
    }
    
    public void setAuthorIndex(Integer authorIndex) {
        this.authorIndex = authorIndex;
    }
    
    /**
     * @param createOn The createOn to set.
     */
    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }
    
    public String getDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(SIMPLEDATEFORMATE);
        return null != date ? dateFormat.format(date) : "";
    }
}
