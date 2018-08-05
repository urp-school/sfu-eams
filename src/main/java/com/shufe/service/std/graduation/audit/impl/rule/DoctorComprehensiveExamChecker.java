package com.shufe.service.std.graduation.audit.impl.rule;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.engine.RuleExecutor;

import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditContext;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditInfo;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditStandard;
import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;
import com.shufe.service.course.grade.other.OtherGradeService;

/**
 * 博士综合考试 检查类
 * 
 * @author zhihe
 * 
 */
public class DoctorComprehensiveExamChecker implements RuleExecutor {
    
    /** 通过博士综合考试 */
    private Boolean isPassDoctorComprehensiveExam;
    
    /** 其它考试服务类 */
    private OtherGradeService otherGradeService;
    
    public boolean execute(Context context) {
        
        DegreeAuditContext degreeAuditContext = (DegreeAuditContext) context;
        DegreeAuditStandard standard = degreeAuditContext.getStandard();
        
        AuditResult result = degreeAuditContext.getResult();
        
        result.setIsPassDoctorComprehensiveExam(Boolean.valueOf(otherGradeService.isPass(result
                .getStd(),
                new OtherExamCategory(OtherExamCategory.DOCTOR_COMPREHENSIVE_EXAMINATION))));
        
        DegreeAuditInfo info = new DegreeAuditInfo();
        info.setAuditResult(result);
        info.setStandard(standard);
        info.setRuleConfig(degreeAuditContext.getRuleConfig());
        info.setDescription("博士综合考试检查");
        if (null !=isPassDoctorComprehensiveExam && isPassDoctorComprehensiveExam.booleanValue()) {
            if (!(Boolean.TRUE == result.getIsPassDoctorComprehensiveExam())) {
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
    
    
    public Boolean getIsPassDoctorComprehensiveExam() {
        return isPassDoctorComprehensiveExam;
    }

    
    public void setIsPassDoctorComprehensiveExam(Boolean isPassDoctorComprehensiveExam) {
        this.isPassDoctorComprehensiveExam = isPassDoctorComprehensiveExam;
    }

    public OtherGradeService getOtherGradeService() {
        return otherGradeService;
    }
    
    public void setOtherGradeService(OtherGradeService otherGradeService) {
        this.otherGradeService = otherGradeService;
    }
    
}
