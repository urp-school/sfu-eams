package com.ekingstar.eams.std.graduation.audit.model;

import org.beanfuse.rule.model.RuleConfig;
import org.beanfuse.rule.model.SimpleContext;


public class DegreeAuditContext extends SimpleContext {
    
    /** 毕业审核(含学位审核)结果 */
    private AuditResult result;
    
    /** 学位审核标准 */
    private DegreeAuditStandard standard;
    
    /** 学位审核标准 */
    private RuleConfig ruleConfig;
    
    
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


    public AuditResult getResult() {
        return result;
    }

    
    public void setResult(AuditResult result) {
        this.result = result;
    }
    
}
