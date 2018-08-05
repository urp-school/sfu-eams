package com.shufe.service.std.graduation.audit.impl.rule;

import java.util.List;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.engine.RuleExecutor;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.utils.persistence.UtilService;
import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditContext;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditInfo;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditStandard;
import com.shufe.model.degree.thesis.answer.FormalAnswer;

/**
 * 论文答辩成绩 检查表
 * 
 * @author zhihe
 * 
 */
public class ThesisScoreChecker implements RuleExecutor {
    
    /**
     * 论文答辩成绩
     */
    private Float thesisScore;
    
    private UtilService utilService;
    
    public boolean execute(Context context) {
        
        DegreeAuditContext degreeAuditContext = (DegreeAuditContext) context;
        DegreeAuditStandard standard = degreeAuditContext.getStandard();
        
        AuditResult result = degreeAuditContext.getResult();
        
        // 查找论文答辩成绩
        if (null != thesisScore) {
            EntityQuery query = new EntityQuery(FormalAnswer.class, "answer");
            query.setSelect("answer.formelMark");
            query.add(new Condition("answer.student.id=:stdId", result.getStd().getId()));
            List rs = (List) utilService.search(query);
            if (!rs.isEmpty()) {
                result.setThesisScore((Float) rs.get(0));
            }
        }
        // 审核论文答辩成绩
        if (null != thesisScore) {
            DegreeAuditInfo info = new DegreeAuditInfo();
            info.setAuditResult(result);
            info.setStandard(standard);
            info.setRuleConfig(degreeAuditContext.getRuleConfig());
            info.setDescription("论文答辩成绩要求不低于" + thesisScore + "; 实际论文答辩成绩为"
                    + (null == result.getThesisScore() ? "0" : result.getThesisScore().toString()));
            if ((null == result.getThesisScore()) || ((null != result.getThesisScore())
                    && result.getThesisScore().floatValue() - thesisScore.floatValue() < 0)) {
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
    
    public UtilService getUtilService() {
        return utilService;
    }
    
    public void setUtilService(UtilService utilService) {
        this.utilService = utilService;
    }
    
    public Float getThesisScore() {
        return thesisScore;
    }
    
    public void setThesisScore(Float thesisScore) {
        this.thesisScore = thesisScore;
    }
    
}
