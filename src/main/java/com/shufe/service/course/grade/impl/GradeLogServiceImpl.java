//$Id: GradeLogServiceImpl.java,v 1.1 2009-7-9 下午02:03:54 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2009-7-9             Created
 *  
 ********************************************************************************/

package com.shufe.service.course.grade.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.security.User;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.model.course.grade.GradeLog;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.service.BasicService;
import com.shufe.service.course.grade.GradeLogService;

/**
 * @author zhouqi
 * 
 */
public class GradeLogServiceImpl extends BasicService implements GradeLogService {
    
    public GradeLog getGradeLog(final String catalogId) {
        return getGradeLog(new Long(catalogId));
    }
    
    public GradeLog getGradeLog(final Long catalogId) {
        return (GradeLog) utilService.load(GradeLog.class, catalogId);
    }
    
    /**
     * @param user
     * @param grades
     * @param inputGradeMap
     * @param scoreMap
     * @return
     */
    public Collection buildGradeCatalogInfo(final User user, final List grades,
            final Map inputGradeMap, final Map scoreMap) {
        Collection catalogs = new HashSet();
        if (null == inputGradeMap || null == scoreMap) {
            return catalogs;
        }
        Map keyMap = new HashMap();
        for (Iterator it = inputGradeMap.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            String[] keys = StringUtils.split(key, "_");
            keyMap.put(new Long(keys[0]), key);
        }
        String key = "";
        for (Iterator it1 = grades.iterator(); it1.hasNext();) {
            CourseGrade grade = (CourseGrade) it1.next();
            Long gradeId = grade.getId();
            StringBuffer context = new StringBuffer();
            context.append("现在：");
            if (null != gradeId || null != grade.getScore()) {
                context.append("最终成绩: " + grade.getScoreDisplay());
                context.append(" [");
                key = gradeId + "_" + grade.getScoreDisplay();
                for (Iterator it2 = grade.getExamGrades().iterator(); it2.hasNext();) {
                    ExamGrade exam = (ExamGrade) it2.next();
                    key += "_" + exam.getGradeType().getId() + ":" + exam.getScoreDisplay();
                    context.append(exam.getGradeType().getName() + ": " + exam.getScoreDisplay());
                    if (it2.hasNext()) {
                        context.append(", ");
                    }
                }
                if (null != gradeId && (null != inputGradeMap && inputGradeMap.containsKey(key))) {
                    continue;
                }
                context.append("]");
            }
            Long status = null;
            if (null == gradeId) {
                if (null == grade.getScore()) {
                    status = GradeLog.START;
                } else {
                    status = GradeLog.ADD;
                }
            } else {
                if (null == scoreMap.get(gradeId)) {
                    status = GradeLog.ADD;
                } else if (null == inputGradeMap.get(key)) {
                    status = GradeLog.EDIT;
                }
            }
            if (null != status) {
                TeachTask task = grade.getTask();
                if (null == grade.getTask()) {
                    task = new TeachTask();
                }
                Course course = (Course) utilService.load(Course.class, grade.getCourse().getId());
                task.setCourse(course);
                Student student = (Student) utilService.load(Student.class, grade.getStd().getId());
                catalogs.add(new GradeLog(student, grade.getCalendar(), task, status, user, context
                        .length() > 0 ? "当初：" + keyMap.get(gradeId) + "<br>" + context.toString()
                        : "成绩还未录入。"));
            }
        }
        return catalogs;
    }
    
    public Collection buildGradeCatalogInfo(final User user, final List grades,
            final Map inputGradeMap, final Map scoreMap, String content, boolean isRemove) {
        Collection catalogs = buildGradeCatalogInfo(user, grades, inputGradeMap, scoreMap, isRemove);
        if (StringUtils.isNotEmpty(content)) {
            for (Iterator it = catalogs.iterator(); it.hasNext();) {
                GradeLog catalog = (GradeLog) it.next();
                catalog.setContext(content + "<br>" + catalog.getContext());
            }
        }
        return catalogs;
    }
    
    public Collection buildGradeCatalogInfo(final User user, final List grades,
            final Map inputGradeMap, final Map scoreMap, boolean isRemove) {
        if (!isRemove) {
            return buildGradeCatalogInfo(user, grades, inputGradeMap, scoreMap);
        }
        
        Collection catalogs = new HashSet();
        String key = "";
        for (Iterator it1 = grades.iterator(); it1.hasNext();) {
            CourseGrade grade = (CourseGrade) it1.next();
            StringBuffer context = new StringBuffer();
            context.append("最终成绩: " + grade.getScoreDisplay());
            context.append(" [");
            key = grade.getId() + "_" + grade.getScoreDisplay();
            for (Iterator it2 = grade.getExamGrades().iterator(); it2.hasNext();) {
                ExamGrade exam = (ExamGrade) it2.next();
                key += "_" + exam.getGradeType().getId() + ":" + exam.getScoreDisplay();
                context.append(exam.getGradeType().getName() + ": " + exam.getScoreDisplay());
                if (it2.hasNext()) {
                    context.append(", ");
                }
            }
            context.append("]");
            TeachTask task = grade.getTask();
            if (null == grade.getTask()) {
                task = new TeachTask();
                task.setCourse((Course)grade.getCourse());
            }
            Course course = (Course) utilService.load(Course.class, task.getCourse().getId());
            task.setCourse(course);
            Student student = (Student) utilService.load(Student.class, grade.getStd().getId());
            catalogs.add(new GradeLog(student, grade.getCalendar(), task, GradeLog.REMOVE, user,
                    context.toString()));
        }
        return catalogs;
    }
    
}
