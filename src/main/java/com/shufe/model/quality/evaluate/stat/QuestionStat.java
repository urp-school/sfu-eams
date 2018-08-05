//$Id: QuestionPointStat.java,v 1.1 2008-5-15 上午11:34:28 zhouqi Exp $
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
 * zhouqi              2008-5-15         	Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.evaluate.stat;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.quality.evaluate.Question;

/**
 * 每个问题的明细得分统计
 * 
 * @author zhouqi
 */
public class QuestionStat extends LongIdObject {
    
    private static final long serialVersionUID = 874201581511308126L;
    
    private QuestionnaireState questionnaireState;
    
    private Question question;
    
    private Double evgPoints;
    
    public QuestionStat() {
    }
    
    /**
     * @param questionnaireState
     * @param question
     * @param evgPoints
     */
    public QuestionStat(QuestionnaireState questionnaireState, Question question, Double evgPoints) {
        this.questionnaireState = questionnaireState;
        this.question = question;
        this.evgPoints = evgPoints;
    }
    
    public Double getEvgPoints() {
        return evgPoints;
    }
    
    public void setEvgPoints(Double evgPoints) {
        this.evgPoints = evgPoints;
    }
    
    public Question getQuestion() {
        return question;
    }
    
    public void setQuestion(Question question) {
        this.question = question;
    }
    
    public QuestionnaireState getQuestionnaireState() {
        return questionnaireState;
    }
    
    public void setQuestionnaireState(QuestionnaireState questionnaireState) {
        this.questionnaireState = questionnaireState;
    }
}
