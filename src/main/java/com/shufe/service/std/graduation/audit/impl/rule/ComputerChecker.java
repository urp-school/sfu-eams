package com.shufe.service.std.graduation.audit.impl.rule;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.rule.Context;
import org.beanfuse.rule.engine.RuleExecutor;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.utils.persistence.UtilService;
import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditContext;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditInfo;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditStandard;
import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;
import com.shufe.model.course.grade.other.OtherGrade;
import com.shufe.service.course.grade.other.OtherGradeService;

/**
 * 计算机考试要求检查类
 * 
 * @author zhihe
 * 
 */
public class ComputerChecker implements RuleExecutor {
    
    /** 计算机考试要求 */
    private String computerExams;
    
    private UtilService utilService;
    
    /** 其它考试服务类 */
    private OtherGradeService otherGradeService;
    
    /** 查找计算机水平 */
    public boolean execute(Context context) {
        
        DegreeAuditContext degreeAuditContext = (DegreeAuditContext) context;
        DegreeAuditStandard standard = degreeAuditContext.getStandard();
        
        AuditResult result = degreeAuditContext.getResult();
        List computerExamList = utilService.load(OtherExamCategory.class, "id", SeqStringUtil
                .transformToLong(computerExams));
        if (!degreeAuditContext.getRuleConfig().getParams().isEmpty()) {
            result.setComputerGrades(new HashSet(otherGradeService.getPassGradesOf(result.getStd(),
                    computerExamList)));
        }
        
        // 审核计算机水平
        if (null != computerExams && !"".equals(computerExams)) {
            DegreeAuditInfo info = new DegreeAuditInfo();
            info.setAuditResult(result);
            info.setStandard(standard);
            info.setRuleConfig(degreeAuditContext.getRuleConfig());
            if (null == result.getComputerGrades()) {
                info.setDescription("未通过");
                info.setPass(false);
                result.getDegreeAuditInfos().put(degreeAuditContext.getRuleConfig().getId(), info);
                return false;
            }
            boolean pass = false;
            for (Iterator iter = computerExamList.iterator(); iter.hasNext();) {
                OtherExamCategory examCategory = (OtherExamCategory) iter.next();
                info.setDescription(StringUtils.defaultIfEmpty(info.getDescription(), "")
                        + examCategory.getName());
                for (Iterator iterator = result.getComputerGrades().iterator(); iterator.hasNext();) {
                    OtherGrade grade = (OtherGrade) iterator.next();
                    if (examCategory.equals(grade.getCategory())
                            && grade.getIsPass().booleanValue()) {
                        pass = true;
                        info.setDescription(info.getDescription() + " : 通过;");
                        break;
                    }
                }
                if (pass) {
                    break;
                } else {
                    info.setDescription(info.getDescription() + " : 未通过;");
                }
            }
            if (!pass) {
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
    
    public void setOtherGradeService(OtherGradeService otherGradeService) {
        this.otherGradeService = otherGradeService;
    }
    
    public String getComputerExams() {
        return computerExams;
    }
    
    public void setComputerExams(String computerExams) {
        this.computerExams = computerExams;
    }
    
    public UtilService getUtilService() {
        return utilService;
    }
    
    public void setUtilService(UtilService utilService) {
        this.utilService = utilService;
    }
    
    public OtherGradeService getOtherGradeService() {
        return otherGradeService;
    }
    
}
