//$Id: DegreeAuditServiceImpl.java,v 1.1 2007-4-11 下午09:37:14 chaostone Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-4-11         Created
 *  
 ********************************************************************************/

package com.shufe.service.std.graduation.audit.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.beanfuse.rule.engine.RuleExecutor;
import org.beanfuse.rule.engine.RuleExecutorBuilder;
import org.beanfuse.rule.model.RuleConfig;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditContext;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditStandard;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;
import com.shufe.dao.std.graduation.audit.DegreeAuditDAO;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.degree.thesis.answer.FormalAnswer;
import com.shufe.model.std.PlanAuditResult;
import com.shufe.model.std.Student;
import com.shufe.model.std.awardPunish.Punishment;
import com.shufe.service.BasicService;
import com.shufe.service.course.grade.gp.GradePointService;
import com.shufe.service.course.grade.other.OtherGradeService;
import com.shufe.service.std.awardPunish.PunishmentService;
import com.shufe.service.std.graduation.audit.DegreeAuditService;

/**
 * 学位审核实现
 * 
 * @author chaostone
 */
public class DegreeAuditServiceImpl extends BasicService implements DegreeAuditService {
    
    DegreeAuditDAO degreeAuditDAO;
    
    GradePointService gradePointService;
    
    OtherGradeService otherGradeService;
    
    PunishmentService punishmentService;
    
    RuleExecutorBuilder ruleExecutorBuilder;
    
    public void setRuleExecutorBuilder(RuleExecutorBuilder ruleExecutorBuilder) {
        this.ruleExecutorBuilder = ruleExecutorBuilder;
    }
    
    public void reAudit(List auditResults, DegreeAuditStandard standard) {
        List results = new ArrayList();
        for (Iterator iter = auditResults.iterator(); iter.hasNext();) {
            AuditResult result = (AuditResult) iter.next();
            // populate Result
            // populateAuditResult(result, standard);
            // String msg = standard.audit(result);
            // result.setCredits(stateCourseCredits(result));
            // result.setIsPass(new Boolean(StringUtils.isEmpty(msg)));
            result.getDegreeAuditInfos().clear();
            
            populateAuditResult(result, standard);
            Set ruleConfigs = standard.getRuleConfigs();
            
            DegreeAuditContext context = new DegreeAuditContext();
            context.setResult(result);
            context.setStandard(standard);
            Boolean isPass = Boolean.TRUE;
            for (Iterator configs = ruleConfigs.iterator(); configs.hasNext();) {
                RuleConfig ruleConfig = (RuleConfig) configs.next();
                if (true == ruleConfig.isEnabled()) {
                    RuleExecutor exceutor = ruleExecutorBuilder.build(ruleConfig);
                    context.setRuleConfig(ruleConfig);
                    if (!exceutor.execute(context)) {
                        isPass = Boolean.FALSE;
                    }
                }
            }
            
            result.setCredits(stateCourseCredits(result));
            // String msg = standard.audit(result);
            result.setIsPass(isPass);
            
            results.add(result);
        }
        utilDao.saveOrUpdate(results);
    }
    
    public void audit(List stds, DegreeAuditStandard standard) {
        /*
         * List results = new ArrayList(); for (Iterator iter = stds.iterator(); iter.hasNext();) {
         * Student std = (Student) iter.next(); AuditResult result = getAuditResult(std,
         * standard.getMajorType()); if (null == result) { result = new AuditResult(std,
         * standard.getMajorType()); } // populate Result populateAuditResult(result, standard);
         * result.setCredits(stateCourseCredits(result)); String msg = standard.audit(result);
         * result.setIsPass(new Boolean(StringUtils.isEmpty(msg))); results.add(result); }
         * utilDao.saveOrUpdate(results);
         */

        List results = new ArrayList();
        for (Iterator iter = stds.iterator(); iter.hasNext();) {
            Student std = (Student) iter.next();
            AuditResult result = getAuditResult(std, standard.getMajorType());
            if (null == result) {
                result = new AuditResult(std, standard.getMajorType());
            }
            // populate Result
            populateAuditResult(result, standard);
            Set ruleConfigs = standard.getRuleConfigs();
            
            DegreeAuditContext context = new DegreeAuditContext();
            context.setResult(result);
            context.setStandard(standard);
            Boolean isPass = Boolean.TRUE;
            for (Iterator configs = ruleConfigs.iterator(); configs.hasNext();) {
                RuleConfig ruleConfig = (RuleConfig) configs.next();
                if (true == ruleConfig.isEnabled()) {
                    RuleExecutor exceutor = ruleExecutorBuilder.build(ruleConfig);
                    context.setRuleConfig(ruleConfig);
                    if (!exceutor.execute(context)) {
                        isPass = Boolean.FALSE;
                    }
                }
            }
            
            result.setCredits(stateCourseCredits(result));
            // String msg = standard.audit(result);
            result.setIsPass(isPass);
            results.add(result);
        }
        utilDao.saveOrUpdate(results);
        
    }
    
    /**
     * 统计总学分
     * 
     * @param result
     * @return
     */
    protected Float stateCourseCredits(AuditResult result) {
        EntityQuery query = new EntityQuery(CourseGrade.class, "courseGrade");
        query.add(new Condition("courseGrade.std.id = (:stdId)", result.getStd().getId()));
        query.add(new Condition("courseGrade.isPass = true"));
        query.setSelect("sum(courseGrade.credit)");
        List list = (List) utilDao.search(query);
        return new Float(list.get(0) == null ? "0" : list.get(0).toString());
    }
    
    private void populateAuditResult(AuditResult result, DegreeAuditStandard standard) {
    	EntityQuery pquery = new EntityQuery(PlanAuditResult.class,"r");
    	pquery.add(new Condition("r.std=:std",result.getStd()));
    	PlanAuditResult prs = ((List<PlanAuditResult>)utilDao.search(pquery)).get(0);
        // 获取毕业审核结果
        if (MajorType.FIRST.equals(standard.getMajorType().getId())) {
            result.setIsCompletePlan(prs.getGraduateAuditStatus());
        } else {
            result.setIsCompletePlan(prs.getSecondGraduateAuditStatus());
        }
        if (null == result.getIsCompletePlan()) {
            result.setIsCompletePlan(Boolean.FALSE);
        }
        // 查找GPA
        if (null != standard.getGPA()) {
            result.setGPA(gradePointService.statStdGPA(result.getStd(), null, standard
                    .getMajorType(), Boolean.TRUE, Boolean.TRUE));
        }
        // 查找处分
        if (null != standard.getLowestPunishType()) {
            Punishment punishment = punishmentService.getWorstPunishment(result.getStd());
            result.setPunishmentType((null == punishment) ? null : punishment.getType());
        }
        // 查找外语水平
        if (!standard.getLanguageExams().isEmpty()) {
            result.setLanguageGrades(new HashSet(otherGradeService.getPassGradesOf(result.getStd(),
                    standard.getLanguageExams())));
        }
        // 查找计算机水平
        if (!standard.getComputerExams().isEmpty()) {
            result.setComputerGrades(new HashSet(otherGradeService.getPassGradesOf(result.getStd(),
                    standard.getComputerExams())));
        }
        // 查找论文答辩成绩
        if (null != standard.getThesisScore()) {
            EntityQuery query = new EntityQuery(FormalAnswer.class, "answer");
            query.setSelect("answer.formelMark");
            query.add(new Condition("answer.student.id=:stdId", result.getStd().getId()));
            List rs = (List) utilService.search(query);
            if (!rs.isEmpty()) {
                result.setThesisScore((Float) rs.get(0));
            }
        }
        // 查找博士综合考试
        if (Boolean.TRUE.equals(standard.getIsPassDoctorComprehensiveExam())) {
            result.setIsPassDoctorComprehensiveExam(Boolean.valueOf(otherGradeService.isPass(result
                    .getStd(), new OtherExamCategory(
                    OtherExamCategory.DOCTOR_COMPREHENSIVE_EXAMINATION))));
        }
        // 查找核心刊物发表数
        if (null != standard.getThesisInCoreMagazine()) {
            
        }
    }
    
    private AuditResult getAuditResult(Student std, MajorType majorType) {
        for (Iterator iterator = std.getAuditResults().iterator(); iterator.hasNext();) {
            AuditResult one = (AuditResult) iterator.next();
            if (one.getMajorType().equals(majorType)) {
                return one;
            }
        }
        return null;
    }
    
    public List getAuditStandards(List stdTypes) {
        EntityQuery query = new EntityQuery(DegreeAuditStandard.class, "standard");
        query.add(new Condition("standard.stdType in(:stdTpes)", stdTypes));
        return (List) utilDao.search(query);
    }
    
    public void setDegreeAuditDAO(DegreeAuditDAO degreeAuditDAO) {
        this.degreeAuditDAO = degreeAuditDAO;
    }
    
    public void setGradePointService(GradePointService gradePointService) {
        this.gradePointService = gradePointService;
    }
    
    public void setOtherGradeService(OtherGradeService otherGradeService) {
        this.otherGradeService = otherGradeService;
    }
    
    public void setPunishmentService(PunishmentService punishmentService) {
        this.punishmentService = punishmentService;
    }
    
}
