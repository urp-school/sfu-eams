//$Id: QuestionnaireState.java,v 1.1 2008-5-15 下午01:12:41 zhouqi Exp $
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

import java.util.Iterator;
import java.util.List;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.calendar.TeachCalendar;

public class QuestionnaireState extends LongIdObject {
    
    private static final long serialVersionUID = 8945194494314088621L;
    
    private TeachCalendar calendar;
    
    /** 问题明细统计 */
    private List questionsStat;
    
    /** 平均分 */
    private Double sumScore;
    
    public List getQuestionsStat() {
        return questionsStat;
    }
    
    public void setQuestionsStat(List questionsStat) {
        this.questionsStat = questionsStat;
    }
    
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    /**
     * 计算平均分
     */
    public void statSumSorce() {
        double sum = 0, total = 0;
        for (Iterator it = getQuestionsStat().iterator(); it.hasNext();) {
            QuestionStat questionStat = (QuestionStat) it.next();
            sum += questionStat.getEvgPoints().doubleValue();
            total += questionStat.getQuestion().getScore().doubleValue();
        }
        this.sumScore = new Double(sum / total * 100.0);
    }
    
    public Double getSumScore() {
        return sumScore;
    }
    
    public void setSumScore(Double sumScore) {
        this.sumScore = sumScore;
    }
}
