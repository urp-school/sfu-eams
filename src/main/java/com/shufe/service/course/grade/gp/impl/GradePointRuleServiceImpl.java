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

package com.shufe.service.course.grade.gp.impl;

import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.course.grade.gp.GradePointRuleDAO;
import com.shufe.model.course.grade.gp.GradePointRule;
import com.shufe.service.BasicService;
import com.shufe.service.course.grade.gp.GradePointRuleService;

public class GradePointRuleServiceImpl extends BasicService implements GradePointRuleService {
    
    GradePointRuleDAO gradePointRuleDAO;
    
    public GradePointRule getGradePointRule(StudentType stdType, MarkStyle markStyle) {
        if (null == stdType || stdType.isVO()) {
            return null;
        } else {
            GradePointRule rule = null;
            do {
                rule = gradePointRuleDAO.getGradePointRule(stdType, markStyle);
                if (rule == null) {
                    stdType = stdType.getSuperType();
                }
            } while (stdType != null && rule == null);
            if (null == rule) {
                rule = gradePointRuleDAO.getDefaultGradePointRule(markStyle);
            }
            return rule;
        }
    }
    
    public void setGradePointRuleDAO(GradePointRuleDAO gradePointRuleDAO) {
        this.gradePointRuleDAO = gradePointRuleDAO;
    }
    
    /**
     * 判断是否重复
     * 
     * @see com.shufe.service.course.grade.gp.GradePointRuleService#checkDuplicate(com.shufe.model.course.grade.gp.GradePointRule)
     */
    public Boolean checkDuplicate(GradePointRule gradePointRule) {
        EntityQuery query = new EntityQuery(GradePointRule.class, "gradePointRule");
        if (gradePointRule.getId() != null) {
            query.add(new Condition("gradePointRule.id != (:id)", gradePointRule.getId()));
        }
        if (gradePointRule.getStdType() == null || gradePointRule.getStdType().getId() == null) {
            query.add(new Condition("gradePointRule.stdType.id is null"));
        } else {
            query.add(new Condition("gradePointRule.stdType.id = (:stdTypeId)", gradePointRule
                    .getStdType().getId()));
        }
        query.add(new Condition("gradePointRule.markStyle.id = (:markStyleId)", gradePointRule
                .getMarkStyle().getId()));
        List list = (List) utilService.search(query);
        if (list.size() > 0) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
    
}
