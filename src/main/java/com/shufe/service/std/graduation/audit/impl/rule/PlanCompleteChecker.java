package com.shufe.service.std.graduation.audit.impl.rule;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.engine.RuleExecutor;

import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditContext;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditInfo;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditStandard;
import com.ekingstar.eams.system.basecode.industry.MajorType;

/**
 * 计划完成情况 检查类
 * 
 * @author
 * 
 */
public class PlanCompleteChecker implements RuleExecutor {
    
    /**
     * 计划是否通过
     */
    private Boolean isCompletePlan;

    public boolean execute(Context context) {
        
        DegreeAuditContext degreeAuditContext = (DegreeAuditContext) context;
        DegreeAuditStandard standard = degreeAuditContext.getStandard();
        
        AuditResult result = degreeAuditContext.getResult();
        

        //FIXME
//        if (MajorType.FIRST.equals(standard.getMajorType().getId())) {
//            result.setIsCompletePlan(result.getStd().getGraduateAuditStatus());
//        } else {
//            result.setIsCompletePlan(result.getStd().getSecondGraduateAuditStatus());
//        }
        if (null == result.getIsCompletePlan()) {
            result.setIsCompletePlan(Boolean.FALSE);
        }
        // 审核计划完成情况
        DegreeAuditInfo info = new DegreeAuditInfo();
        info.setAuditResult(result);
        info.setStandard(standard);
        info.setRuleConfig(degreeAuditContext.getRuleConfig());
        info.setDescription("计划完成情况检查");
        if (null != isCompletePlan && isCompletePlan.booleanValue()) {
            if (!Boolean.TRUE.equals(result.getIsCompletePlan())) {
                info.setPass(false);
                result.getDegreeAuditInfos().put(degreeAuditContext.getRuleConfig().getId(), info);
                return false;
            } else { 
                info.setPass(true);
                result.getDegreeAuditInfos().put(degreeAuditContext.getRuleConfig().getId(), info);
                return true;    
            }
        } else {
            info.setPass(true);
            result.getDegreeAuditInfos().put(degreeAuditContext.getRuleConfig().getId(), info);
            return true;
        }
    }
    
    
    public Boolean getIsCompletePlan() {
        return isCompletePlan;
    }

    
    public void setIsCompletePlan(Boolean isCompletePlan) {
        this.isCompletePlan = isCompletePlan;
    }
}
