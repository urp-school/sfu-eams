//$Id: TeachPlanArrangeAlteration.java,v 1.1 2009-2-1 上午09:23:51 zhouqi Exp $
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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.security.User;

/**
 * 培养计划产生/调整日志记录
 * 
 * @author zhouqi
 * 
 */
public class TeachPlanArrangeAlteration extends LongIdObject {
    
    private static final long serialVersionUID = -3223685301358337918L;
    
    public static Integer NEW = new Integer(1);
    
    public static Integer EDIT = new Integer(2);
    
    public static Integer DELETE = new Integer(3);
    
    public static String COURSEGROUPS = "courseGroups";
    
    public static String PLANCOURSES = "planCourses";
    
    public static String BEEN_ADDED = "been added";
    
    public static String BEEN_MODIFYED = "been modifyed";
    
    public static String BEEN_REMOVED = "been removed";
    
    /** 培养计划记录前信息 */
    private PlanSingleInfo beforePlanInfo;
    
    /** 培养计划记录后信息 */
    private PlanSingleInfo afterPlanInfo;
    
    private Boolean isModifyGroup = Boolean.FALSE;
    
    private Boolean isModifyCourse = Boolean.FALSE;
    
    /** 培养计划创建/修改人 */
    private User alterationBy;
    
    /** 访问路径 */
    private String alterationFrom;
    
    /** 计划制定时间 */
    private java.sql.Date createAt;
    
    /** 计划修改时间 */
    private java.sql.Date modifyAt;
    
    /** 日志发生时间 */
    private Date alterationAt;
    
    /** 记录产生状态 1:new，2:edit，3:delete */
    private Integer happenStatus;
    
    /** 详细信息 */
    private String description;
    
    public TeachPlanArrangeAlteration() {
        alterationAt = new Date();
    }
    
    public TeachPlanArrangeAlteration(Integer happenStatus) {
        this();
        this.happenStatus = happenStatus;
    }
    
    public void setBeforePlan(TeachPlan plan) {
        setPlanInfo(plan, Boolean.TRUE);
    }
    
    public void setAfterPlan(TeachPlan plan) {
        setPlanInfo(plan, null);
    }
    
    public void setPlanInfo(TeachPlan plan, Boolean isBefore) {
        if (null == isBefore) {
            isBefore = Boolean.FALSE;
        }
        if (Boolean.TRUE.equals(isBefore)) {
            if (null == beforePlanInfo) {
                beforePlanInfo = new PlanSingleInfo();
            }
            beforePlanInfo.setEnrollTurn(plan.getEnrollTurn());
            beforePlanInfo.setStdTypeCode(plan.getStdType().getCode());
            beforePlanInfo.setStdTypeName(plan.getStdType().getName());
            beforePlanInfo.setDepartmentCode(plan.getDepartment().getCode());
            beforePlanInfo.setDepartmentName(plan.getDepartment().getName());
            if (null != plan.getSpeciality()) {
                beforePlanInfo.setMajorCode(plan.getSpeciality().getCode());
                beforePlanInfo.setMajorName(plan.getSpeciality().getName());
            }
            if (null != plan.getAspect()) {
                beforePlanInfo.setMajorTypeCode(plan.getAspect().getCode());
                beforePlanInfo.setMajorTypeName(plan.getAspect().getName());
            }
            beforePlanInfo.setTerms(plan.getTermsCount());
            beforePlanInfo.setCreditHour(plan.getCreditHour());
            beforePlanInfo.setCredit(plan.getCredit());
            beforePlanInfo.setGroupCount(new Integer(plan.getCourseGroups().size()));
            int count = 0;
            for (Iterator it = plan.getCourseGroups().iterator(); it.hasNext();) {
                CourseGroup group = (CourseGroup) it.next();
                count += group.getPlanCourses().size();
            }
            beforePlanInfo.setAllCourseCount(new Integer(count));
            boolean isStudent = null != plan.getStd();
            beforePlanInfo.setIsStdPerson(new Boolean(isStudent));
            if (isStudent) {
                beforePlanInfo.setStdCode(plan.getStd().getCode());
                beforePlanInfo.setStdName(plan.getStd().getName());
            }
            beforePlanInfo.setIsConfirm(plan.getIsConfirm());
            beforePlanInfo.setRemark(plan.getRemark());
        } else {
            if (null == afterPlanInfo) {
                afterPlanInfo = new PlanSingleInfo();
            }
            afterPlanInfo.setEnrollTurn(plan.getEnrollTurn());
            afterPlanInfo.setStdTypeCode(plan.getStdType().getCode());
            afterPlanInfo.setStdTypeName(plan.getStdType().getName());
            afterPlanInfo.setDepartmentCode(plan.getDepartment().getCode());
            afterPlanInfo.setDepartmentName(plan.getDepartment().getName());
            if (null != plan.getSpeciality()) {
                afterPlanInfo.setMajorCode(plan.getSpeciality().getCode());
                afterPlanInfo.setMajorName(plan.getSpeciality().getName());
            }
            if (null != plan.getAspect()) {
                afterPlanInfo.setMajorTypeCode(plan.getAspect().getCode());
                afterPlanInfo.setMajorTypeName(plan.getAspect().getName());
            }
            afterPlanInfo.setTerms(plan.getTermsCount());
            afterPlanInfo.setCreditHour(plan.getCreditHour());
            afterPlanInfo.setCredit(plan.getCredit());
            afterPlanInfo.setGroupCount(new Integer(plan.getCourseGroups().size()));
            int count = 0;
            for (Iterator it = plan.getCourseGroups().iterator(); it.hasNext();) {
                CourseGroup group = (CourseGroup) it.next();
                count += group.getPlanCourses().size();
            }
            afterPlanInfo.setAllCourseCount(new Integer(count));
            boolean isStudent = null != plan.getStd();
            afterPlanInfo.setIsStdPerson(new Boolean(isStudent));
            if (isStudent) {
                afterPlanInfo.setStdCode(plan.getStd().getCode());
                afterPlanInfo.setStdName(plan.getStd().getName());
            }
            afterPlanInfo.setIsConfirm(plan.getIsConfirm());
            afterPlanInfo.setRemark(plan.getRemark());
        }
        this.createAt = plan.getCreateAt();
        this.modifyAt = plan.getModifyAt();
    }
    
    public PlanSingleInfo getBeforePlanInfo() {
        return beforePlanInfo;
    }
    
    public void setBeforePlanInfo(PlanSingleInfo beforePlanInfo) {
        this.beforePlanInfo = beforePlanInfo;
    }
    
    public PlanSingleInfo getAfterPlanInfo() {
        return afterPlanInfo;
    }
    
    public void setAfterPlanInfo(PlanSingleInfo afterPlanInfo) {
        this.afterPlanInfo = afterPlanInfo;
    }
    
    public User getAlterationBy() {
        return alterationBy;
    }
    
    public void setAlterationBy(User alterationBy) {
        this.alterationBy = alterationBy;
    }
    
    public String getAlterationFrom() {
        return alterationFrom;
    }
    
    public void setAlterationFrom(String alterationFrom) {
        this.alterationFrom = alterationFrom;
    }
    
    public Date getAlterationAt() {
        return alterationAt;
    }
    
    public void setAlterationAt(Date alterationAt) {
        this.alterationAt = alterationAt;
    }
    
    public java.sql.Date getCreateAt() {
        return createAt;
    }
    
    public void setCreateAt(java.sql.Date createAt) {
        this.createAt = createAt;
    }
    
    public java.sql.Date getModifyAt() {
        return modifyAt;
    }
    
    public void setModifyAt(java.sql.Date modifyAt) {
        this.modifyAt = modifyAt;
    }
    
    public Integer getHappenStatus() {
        return happenStatus;
    }
    
    public void setHappenStatus(Integer happenStatus) {
        this.happenStatus = happenStatus;
    }
    
    public Boolean getIsModifyGroup() {
        return isModifyGroup;
    }
    
    public void setIsModifyGroup(Boolean isModifyGroup) {
        this.isModifyGroup = isModifyGroup;
    }
    
    public Boolean getIsModifyCourse() {
        return isModifyCourse;
    }
    
    public void setIsModifyCourse(Boolean isModifyCourse) {
        this.isModifyCourse = isModifyCourse;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List getContents(String contentType) {
        List contentList = new ArrayList();
        if (StringUtils.isNotEmpty(contentType) && StringUtils.isNotEmpty(this.description)) {
            String[] items = this.description.split(";");
            for (int i = 0; i < items.length; i++) {
                String[] elements = items[i].split(":");
                if (StringUtils.equals(elements[0], contentType)) {
                    String[] contents = elements[1].split(",,");
                    String[] groupInfos = contents[0].split(",");
                    String operation = StringUtils.left(contents[1], contents[1].length());
                    if (StringUtils.equals(TeachPlanArrangeAlteration.BEEN_ADDED, operation)) {
                        operation = "添加";
                    }
                    if (StringUtils.equals(TeachPlanArrangeAlteration.BEEN_MODIFYED, operation)) {
                        operation = "修改";
                    }
                    if (StringUtils.equals(TeachPlanArrangeAlteration.BEEN_REMOVED, operation)) {
                        operation = "删除";
                    }
                    for (int j = 0; j < groupInfos.length; j++) {
                        String[] groupItems = groupInfos[j].split("=");
                        contentList
                                .add(new Object[] { groupItems[0],
                                        StringUtils.left(groupItems[1], groupItems[1].length()),
                                        operation });
                    }
                    break;
                }
            }
        }
        return contentList;
    }
}
