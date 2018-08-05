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
 * 英语考试要求 检查类
 * 
 * @author zhihe
 * 
 */
public class LanguageChecker implements RuleExecutor {
    
    /** 英语考试要求 */
    private String languageGrades;
    
    private UtilService utilService;
    
    /** 其它考试服务类 */
    private OtherGradeService otherGradeService;
    
    /** 查找英语水平 */
    public boolean execute(Context context) {
        
        DegreeAuditContext degreeAuditContext = (DegreeAuditContext) context;
        DegreeAuditStandard standard = degreeAuditContext.getStandard();
        
        AuditResult result = degreeAuditContext.getResult();
        List languageGradeList = utilService.load(OtherExamCategory.class, "id", SeqStringUtil
                .transformToLong(languageGrades));
        if (!degreeAuditContext.getRuleConfig().getParams().isEmpty()) {
            result.setLanguageGrades(new HashSet(otherGradeService.getPassGradesOf(result.getStd(),
                    languageGradeList)));
        }
        
        // 审核英语水平
        if (null != languageGrades && !"".equals(languageGrades)) {
            DegreeAuditInfo info = new DegreeAuditInfo();
            info.setAuditResult(result);
            info.setStandard(standard);
            info.setRuleConfig(degreeAuditContext.getRuleConfig());
            if (null == result.getLanguageGrades()) {
                info.setDescription("未通过");
                info.setPass(false);
                result.getDegreeAuditInfos().put(degreeAuditContext.getRuleConfig().getId(), info);
                return false;
            }
            boolean pass = false;
            for (Iterator iter = languageGradeList.iterator(); iter.hasNext();) {
                OtherExamCategory examCategory = (OtherExamCategory) iter.next();
                info.setDescription(StringUtils.defaultIfEmpty(info.getDescription(), "")
                        + examCategory.getName());
                for (Iterator iterator = result.getLanguageGrades().iterator(); iterator.hasNext();) {
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
    
    public String getLanguageGrades() {
        return languageGrades;
    }
    
    public void setLanguageGrades(String languageGrades) {
        this.languageGrades = languageGrades;
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
