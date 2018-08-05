//$Id: EvaluateCollegeStat.java,v 1.1 2008-5-15 下午01:22:36 zhouqi Exp $
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

/**
 * 全校问题评教统计
 * 
 * @author zhouqi
 */
public class EvaluateCollegeStat extends QuestionnaireState {
    
    private static final long serialVersionUID = -5471038010562302788L;
    
    private Integer count;
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
}
