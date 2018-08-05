//$Id: TypeEvaluate.java,v 1.1 2007-3-19 上午09:47:14 chaostone Exp $
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
 *chaostone      2007-3-19         Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.evaluate;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 评教问题统计结果
 * 
 * @author chaostone
 */
public class QuestionTypeStat extends LongIdObject implements Cloneable {
    
    private static final long serialVersionUID = -4264032229139186906L;

    /** 问题类别 */
    private QuestionType type = new QuestionType();
    
    /** 问题类别统计的分值(百分制) */
    private Float score;
    
    /** 问卷评教结果 */
    private QuestionnaireStat questionnaireStat = new QuestionnaireStat();
    
    public QuestionTypeStat() {
        super();
    }
    
    public Object clone() {
        QuestionTypeStat typeStat = new QuestionTypeStat(this.type, this.score);
        typeStat.setQuestionnaireStat(this.questionnaireStat);
        return typeStat;
    }
    
    public QuestionTypeStat(QuestionType type, Float score) {
        this.type = type;
        this.score = score;
    }
    
    public Float getScore() {
        return score;
    }
    
    public void setScore(Float score) {
        this.score = score;
    }
    
    public QuestionType getType() {
        return type;
    }
    
    public void setType(QuestionType type) {
        this.type = type;
    }
    
    public QuestionnaireStat getQuestionnaireStat() {
        return questionnaireStat;
    }
    
    public void setQuestionnaireStat(QuestionnaireStat questionnaireStat) {
        this.questionnaireStat = questionnaireStat;
    }
}
