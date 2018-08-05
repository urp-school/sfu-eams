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
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2007-1-8            Created
 *  
 ********************************************************************************/

package com.shufe.service.course.grade.gp;

import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.course.grade.gp.GradePointRuleDAO;
import com.shufe.model.course.grade.gp.GradePointRule;

/**
 * 绩点规则服务类
 * 
 * @author chaostone
 */
public interface GradePointRuleService {
    
    /**
     * 查询到学生类别对应的绩点规则
     * 
     * @param stdType
     * @return
     */
    public GradePointRule getGradePointRule(StudentType stdType, MarkStyle markStyle);
    
    public void setGradePointRuleDAO(GradePointRuleDAO gradePointRuleDAO);
    
    /**
     * 判断是否重复
     * 
     * @param gradePointRule
     * @return
     */
    public Boolean checkDuplicate(GradePointRule gradePointRule);
}
