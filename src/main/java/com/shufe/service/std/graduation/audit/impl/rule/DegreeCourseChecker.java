package com.shufe.service.std.graduation.audit.impl.rule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.engine.RuleExecutor;

import com.ekingstar.commons.utils.persistence.UtilService;
import com.shufe.model.course.plan.PlanCourse;
import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditContext;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditInfo;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditStandard;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.grade.other.OtherGradeService;
import com.shufe.service.course.plan.TeachPlanService;

/**
 * 学位课程检查类
 * 
 * @author zhihe
 * 
 */
public class DegreeCourseChecker implements RuleExecutor {
    
    /** 是否大考一次性通过 */
    private Boolean oneOff;
    
    /** 分数 */
    private Float point;
    
    /** 课程串 */
    private String courses;
    
    private UtilService utilService;
    
    /** 其它考试服务类 */
    private OtherGradeService otherGradeService;
    
    /** 其它考试服务类 */
    private GradeService gradeService;
    
    /** 培养计划服务类 */
    private TeachPlanService teachPlanService;
    
    /** 检查学位课程要求 */
    public boolean execute(Context context) {
        
        DegreeAuditContext degreeAuditContext = (DegreeAuditContext) context;
        DegreeAuditStandard standard = degreeAuditContext.getStandard();
        AuditResult result = degreeAuditContext.getResult();
        boolean isPass = true;
        // 查找学生 学位课程
        List degreePlanCourse = teachPlanService.getPlanCourseOfDegree(result.getStd(), result
                .getMajorType());
        DegreeAuditInfo info = new DegreeAuditInfo();
        info.setAuditResult(result);
        info.setStandard(standard);
        info.setRuleConfig(degreeAuditContext.getRuleConfig());
        info.setDescription("学位课程:");
        for (Iterator iter = degreePlanCourse.iterator(); iter.hasNext();) {
            // 查询各学位课程对应成绩
            PlanCourse planCourse = (PlanCourse) iter.next();
            List gradeList = gradeService.getCourseGradeOfDegree(result.getStd().getId(),
                    planCourse.getCourse().getId());
            // 判断对应学位课程的成绩是否 为空 或为 多个
            if (null == gradeList || gradeList.size() != 1) {
                info.setDescription(info.getDescription() + " 该学位课程成绩不存在或多次修读("
                        + planCourse.getCourse().getName() + ")");
                info.setPass(false);
                result.getDegreeAuditInfos().put(degreeAuditContext.getRuleConfig().getId(), info);
                return false;
            } else {
                CourseGrade courseGrade = (CourseGrade) gradeList.get(0);
                if (courseGrade.getIsPass() == Boolean.TRUE) {
                    if (null != oneOff && oneOff == Boolean.TRUE) {
                        // 缓考成绩
                        ExamGrade delay = courseGrade.getExamGrade(new GradeType(GradeType.DELAY));
                        // 补考成绩
                        ExamGrade makeup = courseGrade
                                .getExamGrade(new GradeType(GradeType.MAKEUP));
                        if (null != delay || null != makeup) {
                            info.setDescription(info.getDescription() + " 该学位课程成绩存在补缓考信息("
                                    + planCourse.getCourse().getName() + ")");
                            info.setPass(false);
                            result.getDegreeAuditInfos().put(
                                    degreeAuditContext.getRuleConfig().getId(), info);
                            return false;
                        }
                    }
                } else {
                    Map degreeMap = new HashMap();
                    for (Iterator iter1 = degreePlanCourse.iterator(); iter.hasNext();) {
                        PlanCourse element = (PlanCourse) iter1.next();
                        degreeMap.put(element.getCourse().getId(), element.getCourse());
                    }
                    // 夜大高起本(51) 特殊处理 《中级财务会计》1 (11010) 和《中级财务会计》2 (11011) 成绩之和大于等于120
                    // 中级财务会计 (11009) 如果存在则不判断
                    if ("51".equals(result.getStd().getType().getId())) {
                        if (null != degreeMap.get("11009")) {
                            isPass = false;
                            return isPass;
                        } else if ("11010".equals(courseGrade.getCourse().getId())
                                || "11011".equals(courseGrade.getCourse().getId())) {
                            if (null != degreeMap.get("11010") && null != degreeMap.get("11011")) {
                                List degreeCourse1 = gradeService.getCourseGradeOfDegree(result
                                        .getStd().getId(), Long.valueOf((String) degreeMap
                                        .get("11010")));
                                List degreeCourse2 = gradeService.getCourseGradeOfDegree(result
                                        .getStd().getId(), Long.valueOf((String) degreeMap
                                        .get("11011")));
                                if (degreeCourse1.size() != 1 || degreeCourse2.size() != 1) {
                                    info.setDescription(info.getDescription()
                                            + " 该学位课程中级财务会计1和2成绩不存在或多次修读");
                                    info.setPass(false);
                                    result.getDegreeAuditInfos().put(
                                            degreeAuditContext.getRuleConfig().getId(), info);
                                    return false;
                                } else {
                                    CourseGrade course1 = (CourseGrade) degreeCourse1.get(0);
                                    CourseGrade course2 = (CourseGrade) degreeCourse2.get(0);
                                    if (course1.getGA().floatValue() + course2.getGA().floatValue() < point
                                            .floatValue()) {
                                        info.setDescription(info.getDescription()
                                                + " 该学位课程中级财务会计1和2成绩之和小于:" + point.floatValue());
                                        info.setPass(false);
                                        result.getDegreeAuditInfos().put(
                                                degreeAuditContext.getRuleConfig().getId(), info);
                                        return false;
                                    }
                                }
                            }
                            
                        }
                    }
                    // 夜大专升本(53) 特殊处理 《中级财务会计》1 (11068) 和《中级财务会计》2 (11069) 成绩之和大于等于120
                    if ("53".equals(result.getStd().getType().getId())) {
                        if (null != degreeMap.get("11068") && null != degreeMap.get("11069")) {
                            List degreeCourse1 = gradeService.getCourseGradeOfDegree(result
                                    .getStd().getId(), Long
                                    .valueOf((String) degreeMap.get("11068")));
                            List degreeCourse2 = gradeService.getCourseGradeOfDegree(result
                                    .getStd().getId(), Long
                                    .valueOf((String) degreeMap.get("11069")));
                            if (degreeCourse1.size() != 1 || degreeCourse2.size() != 1) {
                                info.setDescription(info.getDescription()
                                        + " 该学位课程中级财务会计1和2成绩不存在或多次修读");
                                info.setPass(false);
                                result.getDegreeAuditInfos().put(
                                        degreeAuditContext.getRuleConfig().getId(), info);
                                return false;
                            } else {
                                CourseGrade course1 = (CourseGrade) degreeCourse1.get(0);
                                CourseGrade course2 = (CourseGrade) degreeCourse2.get(0);
                                if (course1.getGA().floatValue() + course2.getGA().floatValue() < point
                                        .floatValue()) {
                                    info.setDescription(info.getDescription()
                                            + " 该学位课程中级财务会计1和2成绩之和小于:" + point.floatValue());
                                    info.setPass(false);
                                    result.getDegreeAuditInfos().put(
                                            degreeAuditContext.getRuleConfig().getId(), info);
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        info.setPass(isPass);
        result.getDegreeAuditInfos().put(
                degreeAuditContext.getRuleConfig().getId(), info);
        return isPass;
    }
    
    public String getCourses() {
        return courses;
    }
    
    public void setCourses(String courses) {
        this.courses = courses;
    }
    
    public Boolean getOneOff() {
        return oneOff;
    }
    
    public void setOneOff(Boolean oneOff) {
        this.oneOff = oneOff;
    }
    
    public Float getPoint() {
        return point;
    }
    
    public void setPoint(Float point) {
        this.point = point;
    }
    
    public void setOtherGradeService(OtherGradeService otherGradeService) {
        this.otherGradeService = otherGradeService;
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
    
    public GradeService getGradeService() {
        return gradeService;
    }
    
    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }
    
    public TeachPlanService getTeachPlanService() {
        return teachPlanService;
    }
    
    public void setTeachPlanService(TeachPlanService teachPlanService) {
        this.teachPlanService = teachPlanService;
    }
    
}
