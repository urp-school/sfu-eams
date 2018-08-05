/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is
 * intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source,
 * or other redistribution of this source is not permitted without written
 * permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/*******************************************************************************
 * @author chaostone MODIFICATION DESCRIPTION Name Date Description ============
 *         ============ ============ 塞外狂人 2006-8-12 Created
 ******************************************************************************/
package com.ekingstar.eams.std.graduation.audit.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;
import com.ekingstar.eams.system.basecode.industry.PunishmentType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.grade.other.OtherGrade;

/**
 * 学位审核标准
 * 
 * @author 塞外狂人,chaostone
 */
public class DegreeAuditStandard extends LongIdObject {
    
    private static final long serialVersionUID = -4523121519445786497L;
    
    /** 学位审核失败的原因 */
    public static final String NOT_COMPLETEPLAN = "failure.audit.notCompletePlan";
    
    public static final String NOTPASS_LANGUAGEEXAM = "failure.audit.notPassLanguageExam";
    
    public static final String NOTPASS_COMPUTEREXAM = "failure.audit.notPassComputerExam";
    
    public static final String TOO_SERIOUSPUNISHMENT = "failure.audit.tooSeriousPunishment";
    
    public static final String LOW_GPA = "failure.audit.lowGPA";
    
    public static final String LOW_THESISSCORE = "failure.audit.lowThesisScore";
    
    public static final String NOTPASS_DOCTORCOMPREHENSIVEEXAM = "failure.audit.notPassDoctorComprehensiveExam";
    
    public static final String LOW_THESISINCOREMAGAZINE = "failure.audit.lowThesisInCoreMagazine";
    
    /** 标准名称 */
    private String name;
    
    /**
     * 学生类别
     */
    private StudentType stdType;
    
    /**
     * 专业类别
     */
    private MajorType majorType;
    
    /**
     * 计划是否通过
     */
    private Boolean isCompletePlan;
    
    /**
     * 论文答辩成绩
     */
    private Float thesisScore;
    
    /**
     * 平均绩点
     */
    private Float GPA;
    
    /**
     * 最低处分级别
     */
    private PunishmentType lowestPunishType;
    
    /**
     * 外语标准要求
     */
    private Set languageExams = new HashSet();
    
    /**
     * 计算机水平
     */
    private Set computerExams = new HashSet();
    
    /**
     * 通过博士综合考试
     */
    private Boolean isPassDoctorComprehensiveExam;
    
    /**
     * 核心期刊的论文数（或者折合数）
     */
    private Integer thesisInCoreMagazine;
    
    /**
     * 标准对应业务规则集合
     */
    private Set ruleConfigs = new HashSet();

    
    public Set getRuleConfigs() {
        return ruleConfigs;
    }

    
    public void setRuleConfigs(Set ruleConfigs) {
        this.ruleConfigs = ruleConfigs;
    }

    public Float getGPA() {
        return GPA;
    }
    
    public void setGPA(Float GPA) {
        this.GPA = GPA;
    }
    
    public Boolean getIsCompletePlan() {
        return isCompletePlan;
    }
    
    public void setIsCompletePlan(Boolean isGraduatePass) {
        this.isCompletePlan = isGraduatePass;
    }
    
    public PunishmentType getLowestPunishType() {
        return lowestPunishType;
    }
    
    public void setLowestPunishType(PunishmentType lowestPunishType) {
        this.lowestPunishType = lowestPunishType;
    }
    
    public Set getLanguageExams() {
        return languageExams;
    }
    
    public void setLanguageExams(Set outSchoolSet) {
        this.languageExams = outSchoolSet;
    }
    
    public StudentType getStdType() {
        return stdType;
    }
    
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
    public Set getComputerExams() {
        return computerExams;
    }
    
    public void setComputerExams(Set computerExams) {
        this.computerExams = computerExams;
    }
    
    public Float getThesisScore() {
        return thesisScore;
    }
    
    public void setThesisScore(Float thesisScore) {
        this.thesisScore = thesisScore;
    }
    
    public Boolean getIsPassDoctorComprehensiveExam() {
        return isPassDoctorComprehensiveExam;
    }
    
    public void setIsPassDoctorComprehensiveExam(Boolean isPassDoctorComprehensiveExam) {
        this.isPassDoctorComprehensiveExam = isPassDoctorComprehensiveExam;
    }
    
    public Integer getThesisInCoreMagazine() {
        return thesisInCoreMagazine;
    }
    
    public void setThesisInCoreMagazine(Integer thesisInCoreMagazine) {
        this.thesisInCoreMagazine = thesisInCoreMagazine;
    }
    
    public MajorType getMajorType() {
        return majorType;
    }
    
    public void setMajorType(MajorType majorType) {
        this.majorType = majorType;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setLanguageExamIds(String ids) {
        getLanguageExams().clear();
        Long[] longIds = SeqStringUtil.transformToLong(ids);
        for (int i = 0; i < longIds.length; i++) {
            getLanguageExams().add(new OtherExamCategory(longIds[i]));
        }
    }
    
    public void setComputerExamIds(String ids) {
        getComputerExams().clear();
        Long[] longIds = SeqStringUtil.transformToLong(ids);
        for (int i = 0; i < longIds.length; i++) {
            getComputerExams().add(new OtherExamCategory(longIds[i]));
        }
    }
    
    /**
     * 进行毕业审核<br>
     * 
     * @param result
     * @return 如果失败返回消息，成功返回空
     */
    public String audit(AuditResult result) {
        if (result == null) {
            return "";
        }
        // 审核培养计划
        if (Boolean.TRUE.equals(getIsCompletePlan())) {
            if (!Boolean.TRUE.equals(result.getIsCompletePlan())) {
                return NOT_COMPLETEPLAN;
            }
        }
        // 审核GPA
        if (null != getGPA()) {
            if (null == result.getGPA())
                return LOW_GPA;
            if (result.getGPA().floatValue() - getGPA().floatValue() < 0)
                return LOW_GPA;
        }
        // 审核处分
        if (null != getLowestPunishType() && null != result.getPunishmentType()) {
            if (result.getPunishmentType().equals(getLowestPunishType())
                    || result.getPunishmentType().isSeriousThan(getLowestPunishType())) {
                return TOO_SERIOUSPUNISHMENT;
            }
        }
        // 审核外语水平
        if (!getLanguageExams().isEmpty()) {
            if (null == result.getLanguageGrades())
                return NOTPASS_LANGUAGEEXAM;
            boolean pass = false;
            for (Iterator iter = getLanguageExams().iterator(); iter.hasNext();) {
                OtherExamCategory examCategory = (OtherExamCategory) iter.next();
                for (Iterator iterator = result.getLanguageGrades().iterator(); iterator.hasNext();) {
                    OtherGrade grade = (OtherGrade) iterator.next();
                    if (examCategory.equals(grade.getCategory())
                            && grade.getIsPass().booleanValue()) {
                        pass = true;
                        break;
                    }
                }
                if (pass)
                    break;
            }
            if (!pass)
                return NOTPASS_LANGUAGEEXAM;
        }
        // 审核计算机水平
        if (!getComputerExams().isEmpty()) {
            if (null == result.getComputerGrades())
                return NOTPASS_COMPUTEREXAM;
            boolean pass = false;
            for (Iterator iter = getComputerExams().iterator(); iter.hasNext();) {
                OtherExamCategory examCategory = (OtherExamCategory) iter.next();
                for (Iterator iterator = result.getComputerGrades().iterator(); iterator.hasNext();) {
                    OtherGrade grade = (OtherGrade) iterator.next();
                    if (examCategory.equals(grade.getCategory())
                            && grade.getIsPass().booleanValue()) {
                        pass = true;
                        break;
                    }
                }
                if (pass)
                    break;
            }
            if (!pass)
                return NOTPASS_COMPUTEREXAM;
        }
        // 审核论文答辩成绩
        if (null != getThesisScore()) {
            if (null == result.getThesisScore())
                return LOW_THESISSCORE;
            if (result.getThesisScore().floatValue() - getThesisScore().floatValue() < 0)
                return LOW_THESISSCORE;
        }
        // 审核博士综合考试
        if (Boolean.TRUE.equals(getIsPassDoctorComprehensiveExam())) {
            if (!Boolean.TRUE.equals(result.getIsPassDoctorComprehensiveExam())) {
                return NOTPASS_DOCTORCOMPREHENSIVEEXAM;
            }
        }
        // 审核核心刊物发表数
        if (null != getThesisInCoreMagazine()) {
            if (null == result.getThesisInCoreMagazine())
                return LOW_THESISINCOREMAGAZINE;
            if (result.getThesisInCoreMagazine().intValue() - getThesisInCoreMagazine().intValue() < 0)
                return LOW_THESISINCOREMAGAZINE;
        }
        return "";
    }
    
}
