package com.shufe.service.std.graduation.audit.impl.rule;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.engine.RuleExecutor;

import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditContext;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditInfo;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditStandard;
import com.shufe.service.course.grade.gp.GradePointService;

/**
 * 平均绩点 检查类
 * 
 * @author zhihe
 * 
 */
public class GPAChecker implements RuleExecutor {
    
    private Float GPA;
    
    private GradePointService gradePointService;
    
    public boolean execute(Context context) {
        
        DegreeAuditContext degreeAuditContext = (DegreeAuditContext) context;
        DegreeAuditStandard standard = degreeAuditContext.getStandard();
        
        AuditResult result = degreeAuditContext.getResult();
        
        // 查找GPA
        if (null != GPA) {
            result.setGPA(gradePointService.statStdGPA(result.getStd(), null, standard
                    .getMajorType(), Boolean.TRUE, Boolean.TRUE));
        }
        // 审核GPA
        if (null != GPA) {
            DegreeAuditInfo info = new DegreeAuditInfo();
            info.setAuditResult(result);
            info.setStandard(standard);
            info.setRuleConfig(degreeAuditContext.getRuleConfig());
            info.setDescription("GPA最低要求为" + GPA + "; 实际GPA为" + result.getGPA());
            if ((null == result.getGPA()) || ((null != result.getGPA())
                    && (result.getGPA().floatValue() - getGPA().floatValue() < 0))) {
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
    
    public Float getGPA() {
        return GPA;
    }
    
    public void setGPA(Float gpa) {
        GPA = gpa;
    }
    
    public GradePointService getGradePointService() {
        return gradePointService;
    }
    
    public void setGradePointService(GradePointService gradePointService) {
        this.gradePointService = gradePointService;
    }
    
}
