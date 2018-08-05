//$Id: EvaluationCriteriaService.java,v 1.1 2008-5-26 下午02:06:49 zhouqi Exp $
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
 * zhouqi              2008-5-26         	Created
 *  
 ********************************************************************************/

package com.shufe.service.evaluate;

import java.util.List;

import com.shufe.model.quality.evaluate.EvaluationCriteria;

/**
 * @author zhouqi
 */
public interface EvaluationCriteriaService {
    
    /**
     * 得到一个EvaluationCriteria
     * 
     * @param evaluationCriteriaId
     * @return
     */
    public EvaluationCriteria getEvaluationCriteria(Long evaluationCriteriaId);
    
    /**
     * 得到所有的EvaluationCriteria
     * 
     * @return
     */
    public List getEvaluationCriterias();
    
    /**
     * 得到其中某一项分值集合形式
     * 
     * @param evaluationCriteria
     * @return
     */
    public List getCriteriaItems(EvaluationCriteria evaluationCriteria);
    
    /**
     * 得到其中某一项分值的数组形式
     * 
     * @param evaluationCriteria
     * @return
     */
    public float[][] getConfigPoints(EvaluationCriteria evaluationCriteria);
}
