//$Id: TopicOpen.java,v 1.11 2007/01/22 10:42:44 cwx Exp $
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
 * hc             2005-12-2         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.thesis.topicOpen;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.eams.system.basecode.industry.ThesisType;
import com.shufe.model.degree.thesis.ThesisStore;

/**
 * 论文开题
 * 
 * @author hc
 * 
 */
public class TopicOpen extends ThesisStore {

    private static final long serialVersionUID = 3122790725622782305L;

    private String thesisTopic;// 论文题目(初选)

    private ThesisType thesisType = new ThesisType(); // 论文类别

    private TaskSource taskSource = new TaskSource();// 课题来源

    private String referrenceLiterature; // （查阅主要文献）

    private Content content = new Content();// 论文开题－内容表

    private String fare;// 目前进展情况

    private ThesisPlan thesisPlan = new ThesisPlan();// 论文工作计划表

    private String memo;// 备注

    private OpenReport openReport = new OpenReport();// 开题报告情况

    private Boolean isTutorAgree;// 导师是否同意言重进入论文工作阶段

    private Boolean isDepartAgree;// 院系是否同意言重进入论文工作阶段

    private Boolean departmentAffirm;// 院系确认

    private Boolean tutorAffirm;// 导师确认

    private String thesisUpName; // 上传的论文名字.

    private Integer isPassed; // 开题答辩是否通过 1 表示通过 2表示修改后答辩 3表示答辩不通过

    /**
     * @return Returns the departmentValidate.
     */
    public Boolean getDepartmentAffirm() {
        return departmentAffirm;
    }

    /**
     * @param departmentValidate
     *            The departmentValidate to set.
     */
    public void setDepartmentAffirm(Boolean departmentValidate) {
        this.departmentAffirm = departmentValidate;
    }

    /**
     * @return Returns the tutorValidate.
     */
    public Boolean getTutorAffirm() {
        return tutorAffirm;
    }

    /**
     * @param tutorValidate
     *            The tutorValidate to set.
     */
    public void setTutorAffirm(Boolean tutorValidate) {
        this.tutorAffirm = tutorValidate;
    }

    /**
     * @return Returns the fare.
     */
    public String getFare() {
        return fare;
    }

    /**
     * @param fare
     *            The fare to set.
     */
    public void setFare(String fare) {
        this.fare = fare;
    }

    /**
     * @return Returns the thesisTopic.
     */
    public String getThesisTopic() {
        return thesisTopic;
    }

    /**
     * @param thesisTopic
     *            The thesisTopic to set.
     */
    public void setThesisTopic(String thesisTopic) {
        this.thesisTopic = thesisTopic;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    /**
     * @return Returns the referrenceLiterature.
     */
    public String getReferrenceLiterature() {
        return referrenceLiterature;
    }

    /**
     * @param referrenceLiterature
     *            The referrenceLiterature to set.
     */
    public void setReferrenceLiterature(String referrenceLiterature) {
        this.referrenceLiterature = referrenceLiterature;
    }

    public TaskSource getTaskSource() {
        return taskSource;
    }

    public void setTaskSource(TaskSource taskSource) {
        this.taskSource = taskSource;
    }

    public ThesisPlan getThesisPlan() {
        return thesisPlan;
    }

    public void setThesisPlan(ThesisPlan thesisPlan) {
        this.thesisPlan = thesisPlan;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * @return Returns the thesisType.
     */
    public ThesisType getThesisType() {
        return thesisType;
    }

    /**
     * @param thesisType
     *            The thesisType to set.
     */
    public void setThesisType(ThesisType thesisType) {
        this.thesisType = thesisType;
    }

    /**
     * @return Returns the isDepartAgree.
     */
    public Boolean getIsDepartAgree() {
        return isDepartAgree;
    }

    /**
     * @param isDepartAgree
     *            The isDepartAgree to set.
     */
    public void setIsDepartAgree(Boolean isDepartAgree) {
        this.isDepartAgree = isDepartAgree;
    }

    /**
     * @return Returns the isTutorAgree.
     */
    public Boolean getIsTutorAgree() {
        return isTutorAgree;
    }

    /**
     * @param isTutorAgree
     *            The isTutorAgree to set.
     */
    public void setIsTutorAgree(Boolean isTutorAgree) {
        this.isTutorAgree = isTutorAgree;
    }

    /**
     * @return Returns the openReport.
     */
    public OpenReport getOpenReport() {
        return openReport;
    }

    /**
     * @param openReport
     *            The openReport to set.
     */
    public void setOpenReport(OpenReport openReport) {
        this.openReport = openReport;
    }

    /**
     * @return Returns the thesisUpName.
     */
    public String getThesisUpName() {
        return thesisUpName;
    }

    /**
     * @param thesisUpName
     *            The thesisUpName to set.
     */
    public void setThesisUpName(String thesisUpName) {
        this.thesisUpName = thesisUpName;
    }

    /**
     * @return Returns the isPassed.
     */
    public Integer getIsPassed() {
        return isPassed;
    }

    /**
     * @param isPassed
     *            The isPassed to set.
     */
    public void setIsPassed(Integer isPassed) {
        this.isPassed = isPassed;
    }

    /**
     * 得到论文题目
     * 
     * @return
     */
    public String getTopicOpenName() {
        if (null != this.getThesisPlan() && null != this.getThesisPlan().getThesisTopicArranged()) {
            return this.getThesisPlan().getThesisTopicArranged();
        } else if (StringUtils.isNotBlank(this.getThesisTopic())) {
            return this.getThesisTopic();
        } else {
            return "";
        }
    }
}
