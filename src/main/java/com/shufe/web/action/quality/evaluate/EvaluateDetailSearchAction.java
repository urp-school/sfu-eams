//$Id: EvaluateDetailSearchAction.java,v 1.1 2008-12-8 下午01:52:19 zhouqi Exp $
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
 * zhouqi              2008-12-8             Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.evaluate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.model.quality.evaluate.EvaluateQuerySwitch;
import com.shufe.model.quality.evaluate.QuestionnaireStat;
import com.shufe.model.quality.evaluate.stat.EvaluateCollegeStat;
import com.shufe.model.quality.evaluate.stat.EvaluateDepartmentStat;
import com.shufe.model.quality.evaluate.stat.EvaluateTeacherStat;
import com.shufe.model.quality.evaluate.stat.QuestionStat;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.evaluate.EvaluationCriteriaService;
import com.shufe.service.evaluate.QuestionnairStatService;
import com.shufe.service.system.baseinfo.TeacherService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public class EvaluateDetailSearchAction extends CalendarRestrictionSupportAction {
    
    protected TeacherService teachService;
    
    protected TeachTaskService teachTaskService;
    
    protected QuestionnairStatService questionnairStatService;
    
    protected EvaluationCriteriaService evaluationCriteriaService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Teacher teacher = getTeacherFromSession(request.getSession());
        addSingleParameter(request, "teacher", teacher);
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        query.setSelect("distinct evaluateTeacher.calendar.studentType");
        query.add(new Condition("evaluateTeacher.teacher.id=:teacherId", teacher.getId()));
        List stdTypeList = (List) utilService.search(query);
        addCollection(request, "stdTypeList", stdTypeList);
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "evaluateTeacher.calendar.id");
        Long stdTypeId = getLong(request, "evaluateTeacher.calendar.studentType.id");
        String year = get(request, "evaluateTeacher.calendar.year");
        String term = get(request, "evaluateTeacher.calendar.term");
        if (null == calendarId && null == stdTypeId && StringUtils.isEmpty(year)
                && StringUtils.isEmpty(term)) {
            return forward(request);
        }
        TeachCalendar calendar = null;
        if (null != calendarId) {
            calendar = teachCalendarService.getTeachCalendar(calendarId);
        } else {
            calendar = teachCalendarService.getTeachCalendar(stdTypeId, year, term);
        }
        addCollection(request, "evaluateTeachers", utilService
                .search(buildQuery(request, calendar)));
        if (null == calendar) {
            return forward(request);
        }
        List evaluateDs = questionnairStatService.getEvaluateDepartments(calendar);
        Map evaluateDepartments = new HashMap();
        for (Iterator it = evaluateDs.iterator(); it.hasNext();) {
            EvaluateDepartmentStat evaluateDepartment = (EvaluateDepartmentStat) it.next();
            evaluateDepartments.put(evaluateDepartment.getDepartment().getId().toString(),
                    evaluateDepartment);
        }
        addSingleParameter(request, "evaluateDepartments", evaluateDepartments);
        addSingleParameter(request, "evaluateCollege", questionnairStatService
                .getEvaluateCollegeStat(calendar));
        return forward(request);
    }
    
    /**
     * 组建查询条件(教师个人-教师界面)
     * 
     * @param request
     * @param calendar
     *            TODO
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request, TeachCalendar calendar) {
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        query.add(new Condition("evaluateTeacher.teacher = (:teacher)",
                getTeacherFromSession(request.getSession())));
        populateConditions(request, query);
        if (null == calendar) {
            Collection opens = utilService.load(EvaluateQuerySwitch.class, "isOpen", Boolean.TRUE);
            if (CollectionUtils.isNotEmpty(opens)) {
                Long[] calendarIds = new Long[opens.size()];
                int k = 0;
                for (Iterator it = opens.iterator(); it.hasNext();) {
                    EvaluateQuerySwitch eqSwitch = (EvaluateQuerySwitch) it.next();
                    calendarIds[k++] = eqSwitch.getTeachCalendar().getId();
                }
                String hql1 = "evaluateTeacher.calendar.id in (:calendarIds)";
                query.add(new Condition(hql1, calendarIds));
            } else {
                query.add(new Condition("evaluateTeacher.calendar is null"));
            }
        } else {
            Collection switchs = utilService.load(EvaluateQuerySwitch.class, "teachCalendar.id",
                    calendar.getId());
            EvaluateQuerySwitch eqSwitch = null;
            if (CollectionUtils.isNotEmpty(switchs)) {
                eqSwitch = (EvaluateQuerySwitch) switchs.iterator().next();
            }
            // 如果不在开放时间，没不让其查出数据
            if (null == eqSwitch || !eqSwitch.getIsOpen().booleanValue()) {
                query.add(new Condition("evaluateTeacher.id is null"));
            }
        }
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        return query;
    }
    
    /**
     * 查看教师评教结果的详细信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // 管理员界面上传的ID（本模块）
        Long evaluateTeacherId = getLong(request, "evaluateTeacherId");
        EvaluateTeacherStat evaluateTeacher = null;
        // 教师界面上传的ID(远程模块)
        if (null == evaluateTeacherId) {
            Long teacherStatId = getLong(request, "teacherStatId");
            QuestionnaireStat teacherStat = (QuestionnaireStat) utilService.load(
                    QuestionnaireStat.class, teacherStatId);
            Teacher teacher = (Teacher) ((List) utilService.load(Teacher.class, "code", getUser(
                    request.getSession()).getName())).get(0);
            evaluateTeacher = questionnairStatService.getEvaluateTeacherStat(teacherStat
                    .getCalendar(), teacherStat.getCourse(), teacher);
        } else {
            evaluateTeacher = questionnairStatService.getEvaluateTeacherStat(evaluateTeacherId);
        }
        questionnairStatService.sortQuestionsStat(evaluateTeacher);
        addSingleParameter(request, "evaluateTeacher", evaluateTeacher);
        // Collection teacherResults = questionnairStatService.getEvaluateTeachers(evaluateTeacher
        // .getCalendar(), evaluateTeacher.getDepartment());
        // getDepartmentResults(teacherResults);
        addSingleParameter(request, "evaluateDepartment", questionnairStatService
                .getEvaluateDepartmentStat(evaluateTeacher.getCalendar(),
                        (com.shufe.model.system.baseinfo.Department) evaluateTeacher.getTeacher()
                                .getDepartment()));
        addSingleParameter(request, "evaluateCollege", questionnairStatService
                .getEvaluateCollegeStat(evaluateTeacher.getCalendar()));
        
        EvaluateDepartmentStat evaluateDepartment = questionnairStatService
                .getEvaluateDepartmentStat(evaluateTeacher.getCalendar(),
                        (com.shufe.model.system.baseinfo.Department) evaluateTeacher.getTeacher()
                                .getDepartment());
        addSingleParameter(request, "evaluateDepartment", evaluateDepartment);
        Map departQuestionResults = new HashMap();
        for (Iterator it = evaluateDepartment.getQuestionsStat().iterator(); it.hasNext();) {
            QuestionStat questionStat = (QuestionStat) it.next();
            departQuestionResults.put(((com.shufe.model.system.baseinfo.Department) evaluateTeacher
                    .getTeacher().getDepartment()).getId()
                    + "_" + questionStat.getQuestion().getId(), questionStat);
        }
        addSingleParameter(request, "departQuestionResults", departQuestionResults);
        
        EvaluateCollegeStat evaluateCollege = questionnairStatService
                .getEvaluateCollegeStat(evaluateTeacher.getCalendar());
        addSingleParameter(request, "evaluateCollege", evaluateCollege);
        Map collegeQuestionResults = new HashMap();
        for (Iterator it = evaluateCollege.getQuestionsStat().iterator(); it.hasNext();) {
            QuestionStat questionStat = (QuestionStat) it.next();
            collegeQuestionResults.put("college_" + questionStat.getQuestion().getId(),
                    questionStat);
        }
        addSingleParameter(request, "collegeQuestionResults", collegeQuestionResults);
        
        return forward(request);
    }
    
    public void setTeachService(TeacherService teachService) {
        this.teachService = teachService;
    }
    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    public void setQuestionnairStatService(QuestionnairStatService questionnairStatService) {
        this.questionnairStatService = questionnairStatService;
    }
    
    public void setEvaluationCriteriaService(EvaluationCriteriaService evaluationCriteriaService) {
        this.evaluationCriteriaService = evaluationCriteriaService;
    }
    
}