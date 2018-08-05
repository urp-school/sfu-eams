package com.shufe.service.std.graduation.audit.impl.rule;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.engine.RuleExecutor;

import com.ekingstar.commons.utils.persistence.UtilService;
import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditContext;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditInfo;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditStandard;
import com.ekingstar.eams.system.basecode.industry.PunishmentType;
import com.shufe.model.std.awardPunish.Punishment;
import com.shufe.service.std.awardPunish.PunishmentService;

/**
 * 处分 检查类
 * 
 * @author zhihe
 * 
 */
public class PunishmentChecker implements RuleExecutor {
    
    private PunishmentType lowestPunishType;
    
    /** 处分服务类 */
    private PunishmentService punishmentService;
    
    private UtilService utilService;
    
    public boolean execute(Context context) {
        
        DegreeAuditContext degreeAuditContext = (DegreeAuditContext) context;
        DegreeAuditStandard standard = degreeAuditContext.getStandard();
        
        AuditResult result = degreeAuditContext.getResult();
        
        // 查找处分
        if (null != lowestPunishType) {
            Punishment punishment = punishmentService.getWorstPunishment(result.getStd());
            result.setPunishmentType((null == punishment) ? null : punishment.getType());
        }
        DegreeAuditInfo info = new DegreeAuditInfo();
        info.setAuditResult(result);
        info.setStandard(standard);
        info.setRuleConfig(degreeAuditContext.getRuleConfig());
        info.setDescription("处分检查");
        // 审核处分
        if (null != getLowestPunishType()) {
            PunishmentType punishmentType = (PunishmentType)utilService.load(PunishmentType.class, lowestPunishType.getEntityId());
            if ((null != result.getPunishmentType()) && (result.getPunishmentType().equals(punishmentType)
                    || result.getPunishmentType().isSeriousThan(punishmentType))) {
                info.setPass(false);
                result.getDegreeAuditInfos().put(degreeAuditContext.getRuleConfig().getId(), info);
                return false;
            } else {
                info.setPass(true);
                result.getDegreeAuditInfos().put(degreeAuditContext.getRuleConfig().getId(), info);
                return true;
            }
        }
        info.setPass(true);
        result.getDegreeAuditInfos().put(degreeAuditContext.getRuleConfig().getId(), info);
        return true;
    }
    
    public PunishmentType getLowestPunishType() {
        return lowestPunishType;
    }
    
    public void setLowestPunishType(PunishmentType lowestPunishType) {
        this.lowestPunishType = lowestPunishType;
    }
    
    public PunishmentService getPunishmentService() {
        return punishmentService;
    }
    
    public void setPunishmentService(PunishmentService punishmentService) {
        this.punishmentService = punishmentService;
    }

    
    public UtilService getUtilService() {
        return utilService;
    }

    
    public void setUtilService(UtilService utilService) {
        this.utilService = utilService;
    }
    
}
