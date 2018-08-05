//$Id: StudentDetailWithAudit.java,v 1.3 2006/12/19 13:08:41 duanth Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-11-16         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.graduation.audit.model;

import org.beanfuse.rule.model.RuleConfig;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 学位审核 审核信息
 * 
 * @author zhihe,chaostone
 */
public class DegreeAuditInfo extends LongIdObject {

    /** 审核结果 */
    private AuditResult auditResult;
    
    /** 审核标准 */
    private DegreeAuditStandard standard;
    
    /** 审核规则 */
    private RuleConfig ruleConfig;
    
    /** 审核结果描述 */
    private String description;
    
    /** 审核结果 */
    private boolean pass;
    
    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public AuditResult getAuditResult() {
        return auditResult;
    }
    
    public void setAuditResult(AuditResult auditResult) {
        this.auditResult = auditResult;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RuleConfig getRuleConfig() {
        return ruleConfig;
    }
    
    public void setRuleConfig(RuleConfig ruleConfig) {
        this.ruleConfig = ruleConfig;
    }

    public DegreeAuditStandard getStandard() {
        return standard;
    }
    
    public void setStandard(DegreeAuditStandard standard) {
        this.standard = standard;
    }
	
}
