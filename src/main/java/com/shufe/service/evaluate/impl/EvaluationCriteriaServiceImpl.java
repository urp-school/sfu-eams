//$Id: EvaluationCriteriaServiceImpl.java,v 1.1 2008-5-26 下午02:07:59 zhouqi Exp $
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

package com.shufe.service.evaluate.impl;

import java.util.Iterator;
import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.shufe.model.quality.evaluate.EvaluationCriteria;
import com.shufe.model.quality.evaluate.EvaluationCriteriaItem;
import com.shufe.service.evaluate.EvaluationCriteriaService;

/**
 * @author zhouqi
 */
public class EvaluationCriteriaServiceImpl implements EvaluationCriteriaService {
    
    protected UtilDao utilDao;
    
    public EvaluationCriteria getEvaluationCriteria(Long evaluationCriteriaId) {
        return (EvaluationCriteria) utilDao.load(EvaluationCriteria.class, evaluationCriteriaId);
    }
    
    public List getEvaluationCriterias() {
        return (List) utilDao.loadAll(EvaluationCriteria.class);
    }
    
    public List getCriteriaItems(EvaluationCriteria evaluationCriteria) {
        EntityQuery queryItem = new EntityQuery(EvaluationCriteria.class, "criteria");
        queryItem.add(new Condition("criteria.id = (:criteriaId)", evaluationCriteria.getId()));
        queryItem.join("criteria.criteriaItems", "criteriaItem");
        queryItem.addOrder(new Order("criteriaItem.max desc"));
        queryItem.setSelect("criteriaItem");
        return (List) utilDao.search(queryItem);
    }
    
    public float[][] getConfigPoints(EvaluationCriteria evaluationCriteria) {
        float[][] configPoints = new float[evaluationCriteria.getCriteriaItems().size()][2];
        int k = 0;
        for (Iterator it = getCriteriaItems(evaluationCriteria).iterator(); it.hasNext();) {
            EvaluationCriteriaItem criteriaItem = (EvaluationCriteriaItem) it.next();
            configPoints[k][0] = criteriaItem.getMax().floatValue();
            configPoints[k++][1] = criteriaItem.getMin().floatValue();
        }
        return configPoints;
    }
    
    public void setUtilDao(UtilDao utilDao) {
        this.utilDao = utilDao;
    }
}
