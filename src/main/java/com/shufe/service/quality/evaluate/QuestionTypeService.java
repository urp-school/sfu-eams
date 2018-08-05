//$Id: QuestionTypeService.java,v 1.1 2007-6-2 下午04:24:33 chaostone Exp $
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

package com.shufe.service.quality.evaluate;

import java.util.List;
import java.util.Map;

import com.shufe.model.quality.evaluate.QuestionType;

public interface QuestionTypeService {
    
    public List getQuestionTypes();
    
    public QuestionType getQuestionType(String questionTypeId);
    
    public QuestionType getQuestionType(Long questionTypeId);
    
    public Map getQuestionTypesScore();
    
    public Map getQuestionTypeMap();
}
