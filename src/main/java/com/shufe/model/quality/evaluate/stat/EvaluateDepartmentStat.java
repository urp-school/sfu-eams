//$Id: EvaluateTeacherStat.java,v 1.1 2008-5-15 上午11:21:02 zhouqi Exp $
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

import com.shufe.model.system.baseinfo.Department;


/**
 * 院系问题评教统计
 * 
 * @author zhouqi
 */
public class EvaluateDepartmentStat extends QuestionnaireState {
    
    private static final long serialVersionUID = 8355028741190515253L;
    
    private Department department;
    
    private Integer count;
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
}
