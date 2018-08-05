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
 * 塞外狂人             2006-8-8            Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.thesis.answer;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.ThesisStore;

/**
 * 论文预答辩表
 * 
 * @author 塞外狂人,cwx,chaostone
 */
public class PreAnswer extends ThesisStore implements Comparable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 3375215189536585317L;

	public final static int answerTeacherNum = 5;
    
    /** 答辩小组成员 */
    private Set answerTeamSet = new HashSet();
    
    /** 导师确认 */
    private Boolean isTutorAffirm;
    
    /** 答辩次数 */
    private Integer answerNum;
    
    /** 预答辩时间 */
    private Date answerTime;
    
    /** 预答辩地点 */
    private String answerAddress;
    
    /** 专家意见和建议 */
    private String advice;
    
    /** 答辩结果(是否通过) */
    private Boolean isPassed;
    
    private ThesisManage thesisManage;
    
    public void addAnswerTeamSet(AnswerTeam answerTeam) {
        this.answerTeamSet.add(answerTeam);
        answerTeam.getThesisAnswers().add(this);
    }
    
    public Boolean getIsPassed() {
        return isPassed;
    }
    
    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }
    
    public String getAdvice() {
        return advice;
    }
    
    public void setAdvice(String advice) {
        this.advice = advice;
    }
    
    public Set getAnswerTeamSet() {
        return answerTeamSet;
    }
    
    public void setAnswerTeamSet(Set answerTeamSet) {
        this.answerTeamSet = answerTeamSet;
    }
    
    public Boolean getIsTutorAffirm() {
        return isTutorAffirm;
    }
    
    public void setIsTutorAffirm(Boolean isTutorAffirm) {
        this.isTutorAffirm = isTutorAffirm;
    }
    
    public String getAnswerAddress() {
        return answerAddress;
    }
    
    public void setAnswerAddress(String answerAddress) {
        this.answerAddress = answerAddress;
    }
    
    public Date getAnswerTime() {
        return answerTime;
    }
    
    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }
    
    public Integer getAnswerNum() {
        return answerNum;
    }
    
    public void setAnswerNum(Integer answerNum) {
        this.answerNum = answerNum;
    }
    
    public ThesisManage getThesisManage() {
        return thesisManage;
    }
    
    public void setThesisManage(ThesisManage thesisManage) {
        this.thesisManage = thesisManage;
    }
    
    public int compareTo(Object object) {
        PreAnswer myClass = (PreAnswer) object;
        return new CompareToBuilder().append(this.answerNum, myClass.answerNum).toComparison();
    }
}
