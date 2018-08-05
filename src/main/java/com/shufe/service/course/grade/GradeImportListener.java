//$Id: OtherGradeImportListener.java,v 1.1 2007-3-19 下午12:36:00 chaostone Exp $
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
 *chaostone      2007-3-19         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.grade;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.ekingstar.eams.course.grade.converter.ConverterFactory;
import com.ekingstar.eams.system.basecode.industry.ExamStatus;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.grade.gp.GradePointRuleService;

/**
 * 成绩导入监听器,实现全部数据导入的完整性。<br>
 * 依照学生、学期和考试类型作为唯一标识
 * 
 * @author chaostone
 * 
 */
public class GradeImportListener extends ItemImporterListener {
    
    private UtilDao utilDao;
    
    private GradePointRuleService gradePointRuleService;
    
    /** 缓存记录方式 */
    private Map markStyles = new HashMap();
    
    /** 缓存日历 */
    private Map calendarMap = new HashMap();
    
    /** 成绩类型 */
    private Map gradeTypeMap = new HashMap();
    
    /** 考试情况 */
    private Map examStatusMap = new HashMap();
    
    public GradeImportListener() {
        super();
    }
    
    public GradeImportListener(UtilDao utilDao, GradePointRuleService gradePointRuleService) {
        super();
        this.utilDao = utilDao;
        List marks = utilDao.loadAll(MarkStyle.class);
        for (Iterator it = marks.iterator(); it.hasNext();) {
            MarkStyle markStyle = (MarkStyle) it.next();
            markStyles.put(markStyle.getCode(), markStyle);
        }
        List calendars = utilDao.loadAll(TeachCalendar.class);
        for (Iterator it = calendars.iterator(); it.hasNext();) {
            TeachCalendar calendar = (TeachCalendar) it.next();
            calendarMap.put(calendar.getStudentType().getId() + "_" + calendar.getYear() + "_"
                    + calendar.getTerm(), calendar);
        }
        List gradeTypes = utilDao.loadAll(GradeType.class);
        for (Iterator it = gradeTypes.iterator(); it.hasNext();) {
            GradeType gradeType = (GradeType) it.next();
            gradeTypeMap.put(gradeType.getId(), gradeType);
        }
        List examStatuses = utilDao.loadAll(ExamStatus.class);
        for (Iterator it = examStatuses.iterator(); it.hasNext();) {
            ExamStatus examStatus = (ExamStatus) it.next();
            examStatusMap.put(examStatus.getId(), examStatus);
        }
        this.gradePointRuleService = gradePointRuleService;
    }
    
    public void startTransferItem(TransferResult tr) {
        String scoreValue = (String) importer.curDataMap().get("score");
        String markStyleCode = (String) importer.curDataMap().get("markStyle.code");
        // if (StringUtils.isEmpty(scoreValue)) {
        // tr.addFailure("error.parameters.illegal", "没填成绩");
        // }
        if (StringUtils.isEmpty(markStyleCode)) {
            tr.addFailure("error.parameters.illegal", "没填成绩记录方式");
        }
        String courseCode = String.valueOf(importer.curDataMap().get("course.code"));
        if (StringUtils.isEmpty(courseCode)) {
            tr.addFailure("error.parameters.illegal", "课程代码[" + courseCode + "]");
        }
        String stdCode = String.valueOf(importer.curDataMap().get("std.code"));
        if (StringUtils.isEmpty(stdCode)) {
            tr.addFailure("error.parameters.illegal", "学号[" + stdCode + "]");
        }
        String year = String.valueOf(importer.curDataMap().get("calendar.year"));
        String term = String.valueOf(importer.curDataMap().get("calendar.term"));
        if (StringUtils.isEmpty(year) || StringUtils.isEmpty(term)) {
            tr.addFailure("error.parameters.illegal", "教学日历["
                    + importer.curDataMap().get("calendar.year") + " "
                    + importer.curDataMap().get("calendar.term") + "]");
        }
        String courseTakeTypeCode = String
                .valueOf(importer.curDataMap().get("courseTakeType.code"));
        if (StringUtils.isEmpty(courseTakeTypeCode)) {
            tr.addFailure("error.parameters.illegal", "修读类别没填写");
        }
        if (tr.errors() == 0) {
            if (StringUtils.isNotEmpty(scoreValue)) {
                importer.curDataMap().put(
                        "markStyle.code",
                        ConverterFactory.getConverter().convert(scoreValue,
                                (MarkStyle) markStyles.get(markStyleCode)));
            }
            // 如果出现已有的则进行更新
            EntityQuery query = new EntityQuery(CourseGrade.class, "grade");
            query.add(new Condition("grade.std.code = :stdCode", stdCode));
            //query.add(new Condition("grade.calendar.studentType.id = grade.std.type.id"));
            query.add(new Condition("grade.calendar.year = :year", year));
            query.add(new Condition("grade.calendar.term = :term", term));
            query.add(new Condition("grade.course.code = :courseCode", courseCode));
            List results = (List) utilDao.search(query);
            if (CollectionUtils.isNotEmpty(results)) {
                importer.setCurrent(results.get(0));
            }
        }
    }
    
    public void endTransferItem(TransferResult tr) {
        if (tr.errors() == 0) {
            CourseGrade courseGrade = (CourseGrade) importer.getCurrent();
            if (null == importer.curDataMap().get("score")) {
                courseGrade.setScore(null);
            }
            if (courseGrade.isVO()) {
                StudentType studentType = (StudentType)courseGrade.getStd().getType();
                String calendarMapId = studentType.getId() + "_"
                        + courseGrade.getCalendar().getYear() + "_"
                        + courseGrade.getCalendar().getTerm();
                TeachCalendar teachCalendar = (TeachCalendar) calendarMap.get(calendarMapId);
                //查找教学日历
                if (null == teachCalendar) {
                    while (null != studentType.getSuperType()) {
                        studentType = studentType.getSuperType();
                        calendarMapId = courseGrade.getStd().getType().getSuperType().getId() + "_"
                        + courseGrade.getCalendar().getYear() + "_"
                        + courseGrade.getCalendar().getTerm();
                        teachCalendar = (TeachCalendar) calendarMap.get(calendarMapId);
                    }
                }
                if (null == courseGrade.getCalendar()) {
                    tr.addFailure("error.parameters.illegal", "该学生对应日历["
                            + importer.curDataMap().get("calendar.year") + " "
                            + importer.curDataMap().get("calendar.term") + "]");
                }
                courseGrade.setCalendar((TeachCalendar) calendarMap.get(calendarMapId));
                if (null == courseGrade.getStatus()) {
                    courseGrade.setStatus(new Integer(Grade.PUBLISHED));
                }
                if (null == courseGrade.getIsPass()) {
                    courseGrade.setIsPass(Boolean.TRUE);
                }
            }
            GradeType endType = (GradeType) gradeTypeMap.get(GradeType.END);
            ExamGrade examGrade = courseGrade.getExamGrade(endType);
            if (null == examGrade) {
                if (null == courseGrade.getScore()) {
                    examGrade = new ExamGrade(endType, (ExamStatus) examStatusMap
                            .get(ExamStatus.CHEAT), null);
                    courseGrade.addExamGrade(examGrade);
                } else {
                    examGrade = new ExamGrade(endType, (ExamStatus) examStatusMap
                            .get(ExamStatus.NORMAL), courseGrade.getScore());
                    courseGrade.addExamGrade(examGrade);
                }
            } else {
                if (null == courseGrade.getScore()) {
                    examGrade.setExamStatus((ExamStatus) examStatusMap.get(ExamStatus.CHEAT));
                    examGrade.setScore(null);
                } else {
                    examGrade.setExamStatus((ExamStatus) examStatusMap.get(ExamStatus.NORMAL));
                    examGrade.setScore(courseGrade.getScore());
                }
            }
            examGrade.setStatus(courseGrade.getStatus());
            courseGrade.updateConfirmed();
            courseGrade.calcGA();
            courseGrade.updatePass();
            // 计算平均绩点
            courseGrade.calcGP(gradePointRuleService.getGradePointRule(courseGrade.getStd()
                    .getType(), courseGrade.getMarkStyle()));
            // 如果有课程序号，就把系统中已有的教学任务赋给这个成绩对象
            if (StringUtils.isNotEmpty(courseGrade.getTaskSeqNo())) {
                EntityQuery query = new EntityQuery(TeachTask.class, "task");
                query.add(new Condition("task.seqNo = :seqNo", courseGrade.getTaskSeqNo()));
                query.add(new Condition("task.calendar.id = :calendarId", courseGrade.getCalendar()
                        .getId()));
                List results = (List) utilDao.search(query);
                if (CollectionUtils.isNotEmpty(results)) {
                    courseGrade.setTask((TeachTask) results.get(0));
                }
            }
            
            utilDao.saveOrUpdate(courseGrade);
        }
    }
    
    public void setUtilDao(UtilDao utilDao) {
        this.utilDao = utilDao;
    }
    
    public void setGradePointRuleService(GradePointRuleService gradePointRuleService) {
        this.gradePointRuleService = gradePointRuleService;
    }
    
    public void setMarkStyles(Map markStyles) {
        this.markStyles = markStyles;
    }
    
    public void setCalendarMap(Map calendarMap) {
        this.calendarMap = calendarMap;
    }
    
    public void setGradeTypeMap(Map gradeTypeMap) {
        this.gradeTypeMap = gradeTypeMap;
    }
    
    public void setExamStatusMap(Map examStatusMap) {
        this.examStatusMap = examStatusMap;
    }
}
