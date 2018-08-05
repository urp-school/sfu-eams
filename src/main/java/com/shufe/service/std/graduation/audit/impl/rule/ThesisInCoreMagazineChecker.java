package com.shufe.service.std.graduation.audit.impl.rule;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.engine.RuleExecutor;

import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditContext;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditInfo;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditStandard;

public class ThesisInCoreMagazineChecker implements RuleExecutor {
    
    /**
     * 核心期刊的论文数（或者折合数）
     */
    private Integer thesisInCoreMagazine;
    
    public boolean execute(Context context) {
        
        DegreeAuditContext degreeAuditContext = (DegreeAuditContext) context;
        DegreeAuditStandard standard = degreeAuditContext.getStandard();
        
        AuditResult result = degreeAuditContext.getResult();
        
        // 查找核心刊物发表数
        if (null != thesisInCoreMagazine) {
            
        }
        // 审核核心刊物发表数
        if (null != thesisInCoreMagazine) {
            DegreeAuditInfo info = new DegreeAuditInfo();
            info.setAuditResult(result);
            info.setStandard(standard);
            info.setRuleConfig(degreeAuditContext.getRuleConfig());
            info.setDescription("核心刊物发表数要求不低于" + thesisInCoreMagazine + "; 实际核心刊物发表数为:"
                    + (null == result.getThesisScore() ? "0" : result.getThesisScore().toString()));
            if ((null == result.getThesisInCoreMagazine()) || ((null != result.getThesisInCoreMagazine())
                    && result.getThesisInCoreMagazine().intValue()
                            - thesisInCoreMagazine.intValue() < 0)) {
                info.setPass(false);
                result.getDegreeAuditInfos().put(degreeAuditContext.getRuleConfig().getId(), info);
                return false;
            } else {
                info.setPass(true);
                result.getDegreeAuditInfos().put(degreeAuditContext.getRuleConfig().getId(), info);
                return true;
            }
        }
        return true;
    }
    
    public Integer getThesisInCoreMagazine() {
        return thesisInCoreMagazine;
    }
    
    public void setThesisInCoreMagazine(Integer thesisInCoreMagazine) {
        this.thesisInCoreMagazine = thesisInCoreMagazine;
    }
    
}
