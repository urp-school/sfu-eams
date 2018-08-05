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
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2007-1-15            Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.Model;
import com.ekingstar.commons.mvc.struts.action.BaseAction;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.utils.persistence.UtilService;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.course.grade.converter.ConverterFactory;
import com.ekingstar.eams.course.grade.converter.DefaultConverterConfig;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.ExamStatus;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.eams.system.basecode.service.BaseCodeService;
import com.ekingstar.security.User;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.grade.GradeState;
import com.shufe.model.course.task.TeachTask;
import com.shufe.service.course.arrange.exam.ExamTakeService;
import com.shufe.service.course.grade.GradeLogService;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.grade.gp.GradePointRuleService;

/**
 * 成绩查询管理辅助类
 * 
 * @author chaostone
 */
public class CourseGradeHelper {
    
    protected UtilService utilService;
    
    protected BaseCodeService baseCodeService;
    
    protected GradePointRuleService gradePointRuleService;
    
    protected GradeService gradeService;
    
    protected ExamTakeService examTakeService;
    
    // protected GradeLogService gradeLogService;
    
    /**
     * 修改成绩(包括各种成绩成分)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public void editGrade(HttpServletRequest request) {
        Long courseGradeId = RequestUtils.getLong(request, "courseGradeId");
        CourseGrade courseGrade = (CourseGrade) utilService.get(CourseGrade.class, courseGradeId);
        request.setAttribute("courseGrade", courseGrade);
        request.setAttribute("courseTypes", baseCodeService.getCodes(CourseType.class));
        request.setAttribute("courseTakeTypes", baseCodeService.getCodes(CourseTakeType.class));
        request.setAttribute("examStatus", baseCodeService.getCodes(ExamStatus.class));
        
        List defaultConfigs = (List) utilService.loadAll(DefaultConverterConfig.class);
        // 查找系统中已配置的记录方式
        List markStyles = new ArrayList();
        for (Iterator it = defaultConfigs.iterator(); it.hasNext();) {
            DefaultConverterConfig defaultConfig = (DefaultConverterConfig) it.next();
            markStyles.add(defaultConfig.getMarkStyle());
        }
        request.setAttribute("defaultConfigs", defaultConfigs);
        request.setAttribute("markStyles", markStyles);
        request.setAttribute("converter", ConverterFactory.getConverter());
        
        List addedTypes = new ArrayList();
        GradeType usualType = (GradeType) baseCodeService.getCode(GradeType.class, GradeType.USUAL);
        // 添加平时成绩
        if (Boolean.TRUE.equals(usualType.getState())) {
            if (!courseGrade.hasGrade(GradeType.USUAL)) {
                addedTypes.add(usualType);
            }
        }
        // 添加期中成绩
        GradeType midType = (GradeType) baseCodeService.getCode(GradeType.class, GradeType.MIDDLE);
        if (Boolean.TRUE.equals(midType.getState())) {
            if (!courseGrade.hasGrade(GradeType.MIDDLE)) {
                addedTypes.add(midType);
            }
        }
        // 期末和缓考成绩
        GradeType endType = (GradeType) baseCodeService.getCode(GradeType.class, GradeType.END);
        if (!courseGrade.hasGrade(GradeType.END)) {
            // 如果没有缓考成绩但有需要缓考的,则增补缓考成绩
            if (!courseGrade.hasGrade(GradeType.DELAY)) {
                if (examTakeService.isTakeExam(courseGrade.getStd(), courseGrade.getCalendar(),
                        courseGrade.getTask(), new ExamType(ExamType.DELAY))) {
                    addedTypes.add(baseCodeService.getCode(GradeType.class, GradeType.DELAY));
                } else {
                    addedTypes.add(endType);
                }
            }
        }
        // 成绩不及格
        if (Boolean.FALSE.equals(courseGrade.getIsPass())) {
            // 没有补考成绩，又不需要缓考并且没有缓考成绩则增补补考成绩
            if (!addedTypes.contains(new GradeType(GradeType.DELAY))
                    && !courseGrade.hasGrade(GradeType.DELAY)
                    && !courseGrade.hasGrade(GradeType.MAKEUP)) {
                addedTypes.add((GradeType) baseCodeService.getCode(GradeType.class,
                        GradeType.MAKEUP));
            }
        }
        request.setAttribute("addedGradeTypes", addedTypes);
    }
    
    /**
     * 保存页面修改的课程成绩
     * 
     * @param request
     * @param user
     */
    public void saveGrade(HttpServletRequest request, User user) {
        // FIXME 不能恢复下面的语句，由于MarkStyle中的markStyle.isPass(score)执行出现NullPoint报错
        // CourseGrade courseGrade = (CourseGrade) populateEntity(request, CourseGrade.class,
        // "courseGrade");
        Long courseGradeId = RequestUtils.getLong(request, "courseGrade.id");
        CourseGrade courseGrade = (CourseGrade) utilService.get(CourseGrade.class, courseGradeId);
        // 更新课程类别和修读类别
        Long courseTypeId = RequestUtils.getLong(request, "courseGrade.courseType.id");
        courseGrade.setCourseType((CourseType) baseCodeService.getCode(CourseType.class,
                courseTypeId));
        Long courseTakeTypeId = RequestUtils.getLong(request, "courseGrade.courseTakeType.id");
        courseGrade.setCourseTakeType((CourseTakeType) baseCodeService.getCode(
                CourseTakeType.class, courseTakeTypeId));
        Long markStyleId = RequestUtils.getLong(request, "courseGrade.markStyle.id");
        courseGrade.setMarkStyle((MarkStyle) baseCodeService.getCode(MarkStyle.class, markStyleId));
        // 更新成绩,是否通过,以及计算总评成绩,并在必要的时候增加成绩修改记录
        List removedExamGrades = new ArrayList();
        
        Map inputGradeMap = new HashMap();
        Map scoreMap = new HashMap();
        String key = courseGrade.getId() + "_" + courseGrade.getScoreDisplay();
        scoreMap.put(courseGrade.getId(), courseGrade.getScore());
        
        for (Iterator iter = courseGrade.getExamGrades().iterator(); iter.hasNext();) {
            ExamGrade examGrade = (ExamGrade) iter.next();
            
            key += "_" + examGrade.getGradeType().getId() + ":" + examGrade.getScoreDisplay();
            
            Float eleScore = RequestUtils.getFloat(request, "examGrade" + examGrade.getId());
            String examStatusID = RequestUtils.get(request, "examStatus" + examGrade.getId());
            examGrade.setExamStatus(new ExamStatus(Long.valueOf(examStatusID)));
            // 空成绩要被删除
            if (null != eleScore) {
                examGrade.updateScore(eleScore, user);
            } else {
                removedExamGrades.add(examGrade);
            }
        }
        inputGradeMap.put(key, courseGrade);
        
        // 添加用户添加的其他考试成绩
        Long[] addGradeTypeIds = SeqStringUtil.transformToLong(request
                .getParameter("addGradeTypeIds"));
        if (null != addGradeTypeIds && addGradeTypeIds.length != 0) {
            for (int i = 0; i < addGradeTypeIds.length; i++) {
                Float score = RequestUtils.getFloat(request, "addGradeTypeId" + addGradeTypeIds[i]);
                // 空成绩忽略掉
                if (null != score) {
                    ExamGrade examGrade = new ExamGrade();
                    examGrade.setGradeType((GradeType) baseCodeService.getCode(GradeType.class,
                            addGradeTypeIds[i]));
                    examGrade.setScore(score);
                    examGrade.setStatus(new Integer(Grade.CONFIRMED));
                    examGrade.getExamStatus().setId(ExamStatus.NORMAL);
                    courseGrade.addExamGrade(examGrade);
                }
            }
        }
        courseGrade.getExamGrades().removeAll(removedExamGrades);
        // 首先计算总评成绩
        courseGrade.calcGA();
        Boolean autoCalcFinal = RequestUtils.getBoolean(request, "autoCalcFinal");
        // 再更新最终成绩
        if (Boolean.TRUE.equals(autoCalcFinal)) {
            courseGrade.calcScore();
        } else {
            Float score = RequestUtils.getFloat(request, "courseGrade.score");
            courseGrade.updateScore(score, user);
        }
        // 更新成绩是否合格
        courseGrade.updatePass();
        // 计算平均绩点
        courseGrade.calcGP(gradePointRuleService.getGradePointRule(courseGrade.getStd().getType(),
                courseGrade.getMarkStyle()));
        List grades = new ArrayList();
        grades.add(courseGrade);
        // grades.addAll(gradeLogService.buildGradeCatalogInfo(user, grades, inputGradeMap,
        // scoreMap));
        utilService.saveOrUpdate(grades);
        utilService.remove(removedExamGrades);
    }
    
    /**
     * 删除教学任务某一类型的所有成绩
     * 
     * @param request
     * @param user
     *            TODO
     * @param mapping
     * @param form
     * @param response
     * 
     * @return
     * @throws Exception
     */
    public String removeTaskGrade(HttpServletRequest request, User user) throws Exception {
        Long taskId = RequestUtils.getLong(request, "taskId");
        TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
        // 在必要时生成新的成绩状态
        if (null == task.getGradeState()) {
            task.setGradeState(new GradeState(task));
            utilService.saveOrUpdate(task);
        }
        // 检查成绩状态中可以录入的成绩类型
        GradeState state = task.getGradeState();
        GradeType GAGradeType = (GradeType) baseCodeService.getCode(GradeType.class, GradeType.GA);
        GradeType FinalGradeType = (GradeType) baseCodeService.getCode(GradeType.class,
                GradeType.FINAL);
        // 发布的成绩不能修改或删除
        if (state.isPublished(GAGradeType) || state.isPublished(FinalGradeType)) {
            return "error.grade.modifyPublished";
        }
        Long gradeTypeId = RequestUtils.getLong(request, "gradeTypeId");
        GradeType gradeType = (GradeType) baseCodeService.getCode(GradeType.class, gradeTypeId);
        if (gradeTypeId.equals(GradeType.GA) || gradeTypeId.equals(GradeType.FINAL)) {
            gradeService.removeGrades(task, user);
        } else {
            gradeService.removeExamGrades(task, gradeType, user);
        }
        
        return null;
    }
    
    /**
     * 删除教学任务几个学生所有成绩
     * 
     * @param request
     * @param user
     *            TODO
     * @param mapping
     * @param form
     * @param response
     * 
     * @return
     * @throws Exception
     */
    public String removeStdGrade(HttpServletRequest request, User user) throws Exception {
        Long taskId = RequestUtils.getLong(request, "taskId");
        TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
        // 在必要时生成新的成绩状态
        if (null == task.getGradeState()) {
            task.setGradeState(new GradeState(task));
            utilService.saveOrUpdate(task);
        }
        // 检查成绩状态中可以录入的成绩类型
        GradeState state = task.getGradeState();
        GradeType GAGradeType = (GradeType) baseCodeService.getCode(GradeType.class, GradeType.GA);
        GradeType FinalGradeType = (GradeType) baseCodeService.getCode(GradeType.class,
                GradeType.FINAL);
        // 发布的成绩不能修改或删除
        if (state.isPublished(GAGradeType) || state.isPublished(FinalGradeType)) {
            return "error.grade.modifyPublished";
        }
        Long[] gradeIds = SeqStringUtil.transformToLong(request.getParameter("courseGradeIds"));
        
        if (null != gradeIds && gradeIds.length != 0) {
            EntityQuery courseTakeQuery = new EntityQuery(CourseTake.class, "take");
            courseTakeQuery.add(new Condition("take.courseGrade.id in(:gradeIds)", gradeIds));
            List courseTakes = (List) utilService.search(courseTakeQuery);
            for (Iterator iter = courseTakes.iterator(); iter.hasNext();) {
                CourseTake take = (CourseTake) iter.next();
                take.setCourseGrade(null);
            }
            utilService.saveOrUpdate(courseTakes);
            List grades = utilService.load(CourseGrade.class, "id", gradeIds);
            // utilService.saveOrUpdate(gradeLogService.buildGradeCatalogInfo(user, grades, null,
            // null, true));
            utilService.remove(grades);
            // 更新成绩状态
            List gradeTypes = baseCodeService.getCodes(GradeType.class);
            for (Iterator iterator = gradeTypes.iterator(); iterator.hasNext();) {
                GradeType gradeType = (GradeType) iterator.next();
                state.removeConfirm(gradeType.getMark().intValue());
            }
            utilService.saveOrUpdate(state);
        }
        return null;
    }
    
    protected Entity populateEntity(HttpServletRequest request, Class entityClass, String entityName) {
        Map params = RequestUtils.getParams(request, entityName);
        Long entityId = RequestUtils.getLong(request, entityName + ".id");
        Entity entity = null;
        try {
            if (null == entityId) {
                entity = (Entity) BaseAction.populate(request, entityClass, entityName);
                Model.populator.populate(params, entity);
                EntityUtils.evictEmptyProperty(entity);
            } else {
                entity = (Entity) utilService.get(entityClass, entityId);
                Model.populator.populate(params, entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return entity;
    }
    
    public final void setUtilService(UtilService utilService) {
        this.utilService = utilService;
    }
    
    public final void setBaseCodeService(BaseCodeService baseCodeService) {
        this.baseCodeService = baseCodeService;
    }
    
    public final void setGradePointRuleService(GradePointRuleService gradePointRuleService) {
        this.gradePointRuleService = gradePointRuleService;
    }
    
    public final void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }
    
    public final void setExamTakeService(ExamTakeService examTakeService) {
        this.examTakeService = examTakeService;
    }
    
    // public final void setGradeLogService(GradeLogService gradeLogService) {
    // this.gradeLogService = gradeLogService;
    // }
}
