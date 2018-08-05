//$Id: QuestionTypeServiceImpl.java,v 1.1 2007-6-2 下午04:24:53 chaostone Exp $
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
 *chaostone      2007-6-2         Created
 *  
 ********************************************************************************/

package com.shufe.service.quality.evaluate.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.quality.evaluate.Question;
import com.shufe.model.quality.evaluate.QuestionType;
import com.shufe.service.BasicService;
import com.shufe.service.quality.evaluate.QuestionTypeService;

public class QuestionTypeServiceImpl extends BasicService implements QuestionTypeService {
    
    public List getQuestionTypes() {
        EntityQuery query = new EntityQuery(QuestionType.class, "type");
        query.add(new Condition("type.state=true"));
        return (List) utilDao.search(query);
    }
    
    public QuestionType getQuestionType(String questionTypeId) {
        if (StringUtils.isEmpty(questionTypeId)) {
            return null;
        }
        return (QuestionType) utilService.load(QuestionType.class, new Long(questionTypeId));
    }
    
    public QuestionType getQuestionType(Long questionTypeId) {
        return (QuestionType) utilService.load(QuestionType.class, questionTypeId);
    }
    
    public Map getQuestionTypesScore() {
        EntityQuery query = new EntityQuery(Question.class, "question");
        String groupBy = "question.type.id";
        query.groupBy(groupBy);
        query.setSelect(groupBy + ",sum(question.score)");
        Map result = new HashMap();
        for (Iterator it = utilService.search(query).iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            result.put(obj[0].toString(), obj[1]);
        }
        return result;
    }
    
    public Map getQuestionTypeMap() {
        Map result = new HashMap();
        for (Iterator it = utilService.loadAll(QuestionType.class).iterator(); it.hasNext();) {
            QuestionType questionType = (QuestionType) it.next();
            result.put(questionType.getId().toString(), questionType);
        }
        return result;
    }
}
