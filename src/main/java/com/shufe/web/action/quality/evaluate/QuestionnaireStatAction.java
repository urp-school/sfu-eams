//$Id: QuestionnaireStatAction.java,v 1.1 2007-3-19 上午10:35:55 chaostone Exp $
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

package com.shufe.web.action.quality.evaluate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.baseinfo.model.StudentType;
import com.ekingstar.security.User;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.quality.evaluate.QuestionnaireStat;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.util.stat.DefaultCategoryProducer;
import com.shufe.service.util.stat.GeneralDatasetProducer;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.helper.RestrictionHelper;

/**
 * 评教修改/统计/删除响应类
 * 
 * @author chaostone
 * 
 */
public class QuestionnaireStatAction extends QuestionnaireStatSearchAction {
    
    /**
     * 统计首页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statHome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeTeachDepart);
        return forward(request);
    }
    
    /**
     * 统计学生的评教结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward stat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String departIdSeq = request.getParameter("departIdSeq");
        String stdTypeIdSeq = request.getParameter("stdTypeIdSeq");
        String calendarIdSeq = request.getParameter("calendarIdSeq");
        if (StringUtils.isBlank(departIdSeq)) {
            departIdSeq = getDepartmentIdSeq(request);
        }
        if (StringUtils.isBlank(stdTypeIdSeq)) {
            stdTypeIdSeq = getStdTypeIdSeq(request);
        }
        User user = getUser(request.getSession());
        questionnaireStatService.statEvaluateResult(stdTypeIdSeq, departIdSeq, calendarIdSeq, user);
        return redirect(request, "index", "info.stat.success");
    }
    
    public ActionForward modifyTeacher(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionnaireStatId = getLong(request, "questionnaireStatId");
        QuestionnaireStat questionnaireStat = (QuestionnaireStat) utilService.load(
                QuestionnaireStat.class, questionnaireStatId);
        request.setAttribute("questionnaireStat", questionnaireStat);
        setDataRealm(request, hasDepart);
        return forward(request);
    }
    
    public ActionForward saveTeacher(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionnaireStatId = getLong(request, "questionnaireStatId");
        QuestionnaireStat questionnaireStat = (QuestionnaireStat) utilService.load(
                QuestionnaireStat.class, questionnaireStatId);
        questionnaireStat.setTeacher(new Teacher(getLong(request, "newTeacherId")));
        utilService.saveOrUpdate(questionnaireStat);
        return redirect(request, "search", "info.update.success");
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String questionnaireStatIds = request.getParameter("questionnaireStatIds");
        List questionnaireStats = utilService.load(QuestionnaireStat.class, "id", SeqStringUtil
                .transformToLong(questionnaireStatIds));
        utilService.remove(questionnaireStats);
        return redirect(request, "search", "info.delete.success");
    }
    
    /**
     * 管理部门的页面，查询的是自己所负责部门的总体情况。
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward departDistributeStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // 统计所有自己负责的开课院系
        setDataRealm(request, RestrictionHelper.hasTeachDepart);
        String studentTypeId = request.getParameter("questionnaireStat.stdType.id");
        String year = request.getParameter("questionnaireStat.calendar.year");
        String term = request.getParameter("questionnaireStat.calendar.term");
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(Long
                .valueOf(studentTypeId), year, term);
        if (null == teachCalendar && StringUtils.isNotBlank(studentTypeId)) {
            teachCalendar = teachCalendarService.getNearestCalendar((StudentType) EntityUtils
                    .getEntity(StudentType.class, Long.valueOf(studentTypeId)));
        }
        request.setAttribute("teachCalendar", teachCalendar);
        Map optionNameMap = getOptionMap();
        Map departRelatedMap = questionnaireStatService.getDataByDepartAndRelated(
                getStdTypeIdSeq(request), getDepartmentIdSeq(request), teachCalendar.getId(),
                optionNameMap);
        request.setAttribute("departRelatedMap", departRelatedMap);
        return forward(request);
    }
    
    /**
     * 饼图
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward pieOfEvaluate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String departmentId = request.getParameter("departmentId");
        if (StringUtils.isNotEmpty(departmentId)) {
            Department department = (Department) utilService.load(Department.class, Long
                    .valueOf(departmentId));
            request.setAttribute("department", department);
        }
        String teachCalendarId = request.getParameter("teachCalendarId");
        if (StringUtils.isNotEmpty(teachCalendarId)) {
            TeachCalendar teachCalendar = (TeachCalendar) utilService.load(TeachCalendar.class,
                    Long.valueOf(teachCalendarId));
            request.setAttribute("teachCalendar", teachCalendar);
        }
        // 定义4个变量来接收 优,良,中,差的值.
        Map optionNameMap = getOptionMap();
        Map returnMap = questionnaireStatService.getDatasOfPieChar(getStdTypeIdSeq(request), Long
                .valueOf(departmentId), Long.valueOf(teachCalendarId), optionNameMap);
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("优", returnMap.containsKey("A") ? (Integer) returnMap.get("A")
                : new Integer(0));
        dataset.setValue("良", returnMap.containsKey("B") ? (Integer) returnMap.get("B")
                : new Integer(0));
        dataset.setValue("中", returnMap.containsKey("C") ? (Integer) returnMap.get("C")
                : new Integer(0));
        dataset.setValue("差", returnMap.containsKey("D") ? (Integer) returnMap.get("D")
                : new Integer(0));
        request.setAttribute("pageViews", new GeneralDatasetProducer("pieChart", dataset));
        return forward(request);
    }
    
    /**
     * 线图。。。。。
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward lieOfEvaluate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String[] level = new String[] { "优", "良", "中", "差" };
        String studentTypeId = request.getParameter("questionnaireStat.stdType.id");
        String year = request.getParameter("questionnaireStat.calendar.year");
        String term = request.getParameter("questionnaireStat.calendar.term");
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(Long
                .valueOf(studentTypeId), year, term);
        request.setAttribute("teachCalendar", teachCalendar);
        Map optionNameMap = getOptionMap();
        List collegeList = departmentService.getColleges(getDepartmentIdSeq(request));
        List seriesNames = new ArrayList();
        Map lineChartMap = questionnaireStatService.getDataByDepartAndRelated(
                getStdTypeIdSeq(request), getDepartmentIdSeq(request), teachCalendar.getId(),
                optionNameMap);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < collegeList.size(); i++) {
            Department college = (Department) collegeList.get(i);
            seriesNames.add(college.getName());
            dataset.setValue(
                    lineChartMap.containsKey(college.getId() + "-A") ? (Integer) lineChartMap
                            .get(college.getId() + "-A") : new Integer(0), college.getName(),
                    level[0]);
            dataset.setValue(
                    lineChartMap.containsKey(college.getId() + "-B") ? (Integer) lineChartMap
                            .get(college.getId() + "-B") : new Integer(0), college.getName(),
                    level[1]);
            dataset.setValue(
                    lineChartMap.containsKey(college.getId() + "-C") ? (Integer) lineChartMap
                            .get(college.getId() + "-C") : new Integer(0), college.getName(),
                    level[2]);
            dataset.setValue(
                    lineChartMap.containsKey(college.getId() + "-D") ? (Integer) lineChartMap
                            .get(college.getId() + "-D") : new Integer(0), college.getName(),
                    level[3]);
        }
        // 定义4个变量来接收 优,良,中,差的值.
        String[] seriesName = new String[seriesNames.size()];
        for (int i = 0; i < seriesNames.size(); i++) {
            seriesName[i] = seriesNames.get(i).toString();
        }
        request.setAttribute("pageViews", new DefaultCategoryProducer("lieOfEvaluate", dataset,
                seriesName));
        return forward(request);
    }
    
    /**
     * 添加手工录入的详细信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward addDetailEvaluateInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("teachCalendars", teachCalendarService
                .getTeachCalendars(getStdTypeIdSeq(request)));
        List stdTypes = utilService.load(StudentType.class, "id", SeqStringUtil
                .transformToLong(getStdTypeIdSeq(request)));
        request.setAttribute("nearCalendar", teachCalendarService
                .getNearestCalendar((StudentType) stdTypes.get(0)));
        request.setAttribute("collegeList", departmentService
                .getTeachDeparts(getDepartmentIdSeq(request)));
        return forward(request);
    }
    
    /**
     * 得到教学任务所对应的问卷的问题
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward getQuestions(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long taskId = getLong(request, "taskId");
        TeachTask teachTask = (TeachTask) utilService.load(TeachTask.class, taskId);
        List questions = new ArrayList();
        questions.addAll(teachTask.getQuestionnaire().getQuestions());
        if (questions.size() > 0) {
            Collections.sort(questions);
            request.setAttribute("questions", questions);
        }
        return forward(request, "selectQuestions");
    }
    
    /**
     * 保存详细的评教信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveDetailEvaluateInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TeachTask teachTask = (TeachTask) utilService.get(TeachTask.class, getLong(request,
        // "taskId"));
        // Teacher teacher = (Teacher) utilService.get(Teacher.class, getLong(request,
        // "teacherId"));
        // Question question = (Question) utilService.get(Question.class, getLong(request,
        // "questionId"));
        // FIXME
        // EvaluateResult evaluateResult = new
        // EvaluateResult(teachTask,teacher,question);
        // evaluateResult.setOption(null);
        // evaluateResult.setStudent(null);
        // evaluateResult.setScore(Float.valueOf(request.getParameter("evaluateResults.score")));
        // utilService.saveOrUpdate(evaluateResult);
        return redirect(request, "addDetailEvaluateInfo", "info.add.success");
    }
    
    /**
     * 添加手工录入的统计信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward addStatisticEvaluateInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("teachCalendars", teachCalendarService
                .getTeachCalendars(getStdTypeIdSeq(request)));
        List stdTypes = utilService.load(StudentType.class, "id", SeqStringUtil
                .transformToLong(getStdTypeIdSeq(request)));
        request.setAttribute("nearCalendar", teachCalendarService
                .getNearestCalendar((StudentType) stdTypes.get(0)));
        request.setAttribute("collegeList", departmentService
                .getTeachDeparts(getDepartmentIdSeq(request)));
        return forward(request);
    }
    
    /**
     * 添加统计好的信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveStatisticEvaluateInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachTask teachTask = (TeachTask) utilService.get(TeachTask.class, getLong(request,
                "taskId"));
        Teacher teacher = (Teacher) utilService.get(Teacher.class, getLong(request, "teacherId"));
        List temList = utilService.load(QuestionnaireStat.class, new String[] { "task.seqNo",
                "task.calendar.id", "teacher.id" }, new Object[] { teachTask.getSeqNo(),
                teachTask.getCalendar().getId(), teacher.getId() });
        if (temList.size() > 0) {
            return redirect(request, "addStatisticEvaluateInfo",
                    "evaluate.addStatisticEvaluateInfo.restrictionError");
        }
        Integer validTicket = Integer.valueOf(request.getParameter("validTicket"));
        QuestionnaireStat questionnaireStat = new QuestionnaireStat(teachTask, teacher, validTicket);
        questionnaireStat.setScore(Float.valueOf(request.getParameter("totleEvaluate")));
        utilService.saveOrUpdate(questionnaireStat);
        return redirect(request, "addStatisticEvaluateInfo", "info.add.success");
    }
    
    /**
     * 得到查询的课程
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward getCourses(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String collegeId = request.getParameter("collegeId");
        String teachCalendarId = request.getParameter("teachCalendarId");
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery.add(new Condition("task.calendar.id=" + teachCalendarId));
        entityQuery.add(new Condition("task.arrangeInfo.teachDepart.id=" + collegeId));
        DataRealmUtils.addDataRealms(entityQuery, new String[] { "task.teachClass.stdType.id",
                "task.arrangeInfo.teachDepart.id" }, getDataRealms(request));
        entityQuery.setLimit(getPageLimit(request));
        addCollection(request, "tasks", utilService.search(entityQuery));
        return forward(request, "selectCourses");
    }
    
}
