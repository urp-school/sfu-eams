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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
public class DegreeAuditStandard_siva extends LongIdObject {
    
    private static final long serialVersionUID = -4523121519445786497L;
    
    /** 学位审核失败的原因 */
    public static final String NOT_COMPLETEPLAN = "failure.audit.notCompletePlan";
    
    public static final String NOTPASS_LANGUAGEEXAM = "failure.audit.notPassLanguageExam";
    
    public static final String NOTPASS_DegreeCourse="failure.audit.notPassDegreeCourse";
    
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
     * 学位课程是否通过
     */
    private Boolean isDegreeCoursePass;
    
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
    	List<String> resultList = new ArrayList<String>();
        if (result == null) {
            return "";
        }
        // 审核培养计划
        if (Boolean.TRUE.equals(getIsCompletePlan())) {
            if (!Boolean.TRUE.equals(result.getIsCompletePlan())) {
            	resultList.add("培养计划未完成");
            }
        }
        //学位课程
        if(!Boolean.TRUE.equals(result.getIsDegreeCoursePass())){
        	resultList.add("学位课程未通过");
        }
            
        // 审核GPA
        if (null != getGPA()) {
            if (null != result.getGPA()){
            	if (result.getGPA().floatValue() - getGPA().floatValue() < 0)
            		resultList.add("绩点审核未通过");
            }else{
            	resultList.add("绩点审核未通过");
            }
        }
        // 审核处分
        if (null != getLowestPunishType() && null != result.getPunishmentType()) {
            if (result.getPunishmentType().equals(getLowestPunishType())
                    || result.getPunishmentType().isSeriousThan(getLowestPunishType())) {
            	resultList.add("处分审核未通过");
            }
        }
        // 审核外语水平
        if (!getLanguageExams().isEmpty()) {
            if (null != result.getLanguageGrades()){
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
                	 resultList.add("外语审核未通过");
            }else{
            	resultList.add("外语审核未通过");
            }
        }
        // 审核计算机水平
        if (!getComputerExams().isEmpty()) {
            if (null == result.getComputerGrades()){
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
                	resultList.add("计算机审核未通过");
            }else{
            	resultList.add("计算机审核未通过");
            }
            
        }
        return "";
    }

    
    public Boolean getIsDegreeCoursePass() {
        return isDegreeCoursePass;
    }

    
    public void setIsDegreeCoursePass(Boolean isDegreeCoursePass) {
        this.isDegreeCoursePass = isDegreeCoursePass;
    }
    
}
