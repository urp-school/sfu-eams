package com.shufe.service.std.graduation.audit.impl.rule;

import java.util.List;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.engine.RuleExecutor;

import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditContext;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditInfo;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditStandard;
import com.shufe.service.course.grade.GradeService;

/**
 * 补考及格门次 限制
 * 
 * @author zhihe
 * 
 */
public class MakeUpPassChecker implements RuleExecutor {
    
    private Integer sum;
    
    private GradeService gradeService;
    
    public boolean execute(Context context) {
        
        DegreeAuditContext degreeAuditContext = (DegreeAuditContext) context;
        DegreeAuditStandard standard = degreeAuditContext.getStandard();
        
        AuditResult result = degreeAuditContext.getResult();
        
        // 审核 由补考考试获得的及格成绩
        if (null != sum) {
            List makeUpList = (List) gradeService.getWithMakeUpExamIsPass(result.getStd(), standard
                    .getMajorType());
            DegreeAuditInfo info = new DegreeAuditInfo();
            info.setAuditResult(result);
            info.setStandard(standard);
            info.setRuleConfig(degreeAuditContext.getRuleConfig());
            info.setDescription("补考及格要求门次不超过" + sum + "; 实际补考及格门次为" + makeUpList.size());
            if (makeUpList.size() > sum.intValue()) {
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
    
    public Integer getSum() {
        return sum;
    }
    
    public void setSum(Integer sum) {
        this.sum = sum;
    }
    
    public GradeService getGradeService() {
        return gradeService;
    }
    
    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }
}
