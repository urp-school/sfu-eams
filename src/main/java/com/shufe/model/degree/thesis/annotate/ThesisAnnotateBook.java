//$Id: ThesisAnnotateBook.java,v 1.6 2006/12/04 10:21:15 cwx Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-11-7         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.thesis.annotate;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.answer.AnswerTeam;

/**
 * 论文评阅书
 * 
 * @author chaostone
 */
public class ThesisAnnotateBook extends LongIdObject implements Comparable {
    
    private static final long serialVersionUID = 7251408052756773328L;
    
    /** 评阅书编号 */
    private String serial;
    
    private AnswerTeam answerTem = new AnswerTeam();
    
    /** 对论文的学术评价 */
    private String thesisAppraise;
    
    // 分为3个方面 同意 不同意 修改后答辩 所有还是用String A 表示 同意 D表示不同意 M表示修改后答辩
    /** 可否答辩 */
    private String isReply;// 评阅人意见: 可否答辩
    
    private Boolean isPublish;// 评阅人意见:可否发表
    
    /*
     * 专家对创新点的结论 其中创新点用a,b,c,d表示
     */
    private String innovateOne;// 创新点１
    
    private String innovateTwo;// 创新点２
    
    private String innovateThree;// 创新点３
    
    private String lack;// 不足A(确切)、B（较确切）、C（一般）、D（不确切）
    
    /*
     * 这个是博士特别有的评阅
     */
    private EvaluateIdea evaluateIdea = new EvaluateIdea();// 博士学位论文专家评价意见表
    
    /** 论文管理 */
    private ThesisManage thesisManage = new ThesisManage();
    
    /**
     * @return Returns the isPublish.
     */
    public Boolean getIsPublish() {
        return isPublish;
    }
    
    /**
     * @param isPublish The isPublish to set.
     */
    public void setIsPublish(Boolean isPublish) {
        this.isPublish = isPublish;
    }
    
    /**
     * @return Returns the isReply.
     */
    public String getIsReply() {
        return isReply;
    }
    
    /**
     * @param isReply The isReply to set.
     */
    public void setIsReply(String isReply) {
        this.isReply = isReply;
    }
    
    /**
     * @return Returns the serial.
     */
    public String getSerial() {
        return serial;
    }
    
    /**
     * @param serial The serial to set.
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }
    
    /**
     * @return Returns the thesisAppraise.
     */
    public String getThesisAppraise() {
        return thesisAppraise;
    }
    
    /**
     * @param thesisAppraise The thesisAppraise to set.
     */
    public void setThesisAppraise(String thesisAppraise) {
        this.thesisAppraise = thesisAppraise;
    }
    
    /**
     * @return Returns the answerTem.
     */
    public AnswerTeam getAnswerTem() {
        return answerTem;
    }
    
    /**
     * @param answerTem The answerTem to set.
     */
    public void setAnswerTem(AnswerTeam answerTem) {
        this.answerTem = answerTem;
    }
    
    /**
     * @return Returns the thesisManage.
     */
    public ThesisManage getThesisManage() {
        return thesisManage;
    }
    
    /**
     * @param thesisManage The thesisManage to set.
     */
    public void setThesisManage(ThesisManage thesisManage) {
        this.thesisManage = thesisManage;
    }
    
    /**
     * @return Returns the innovateOne.
     */
    public String getInnovateOne() {
        return innovateOne;
    }
    
    /**
     * @param innovateOne The innovateOne to set.
     */
    public void setInnovateOne(String innovateOne) {
        this.innovateOne = innovateOne;
    }
    
    /**
     * @return Returns the innovateThree.
     */
    public String getInnovateThree() {
        return innovateThree;
    }
    
    /**
     * @param innovateThree The innovateThree to set.
     */
    public void setInnovateThree(String innovateThree) {
        this.innovateThree = innovateThree;
    }
    
    /**
     * @return Returns the innovateTwo.
     */
    public String getInnovateTwo() {
        return innovateTwo;
    }
    
    /**
     * @param innovateTwo The innovateTwo to set.
     */
    public void setInnovateTwo(String innovateTwo) {
        this.innovateTwo = innovateTwo;
    }
    
    /**
     * @return Returns the lack.
     */
    public String getLack() {
        return lack;
    }
    
    /**
     * @param lack The lack to set.
     */
    public void setLack(String lack) {
        this.lack = lack;
    }
    
    /**
     * @return Returns the evaluateIdea.
     */
    public EvaluateIdea getEvaluateIdea() {
        return evaluateIdea;
    }
    
    /**
     * @param evaluateIdea The evaluateIdea to set.
     */
    public void setEvaluateIdea(EvaluateIdea evaluateIdea) {
        this.evaluateIdea = evaluateIdea;
    }
    
    /**
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(Object object) {
        ThesisAnnotateBook annotateBook = (ThesisAnnotateBook) object;
        return new CompareToBuilder().append(this.getSerial(), annotateBook.getSerial())
                .toComparison();
    }
    
}
