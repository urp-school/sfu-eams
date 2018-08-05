//$Id: EvaluateDetailStatAction.java,v 1.1 2008-5-15 下午04:15:27 zhouqi Exp $
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
 * zhouqi              2008-5-15         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.evaluate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.Context;
import com.ekingstar.commons.transfer.exporter.DefaultEntityExporter;
import com.ekingstar.commons.transfer.exporter.Exporter;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.security.User;
import com.shufe.model.quality.evaluate.EvaluationCriteria;
import com.shufe.model.quality.evaluate.Questionnaire;
import com.shufe.model.quality.evaluate.stat.EvaluateCollegeStat;
import com.shufe.model.quality.evaluate.stat.EvaluateDepartmentStat;
import com.shufe.model.quality.evaluate.stat.EvaluateTeacherStat;
import com.shufe.model.quality.evaluate.stat.QuestionStat;
import com.shufe.model.quality.evaluate.stat.QuestionTeacherStat;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.task.CourseTakeService;
import com.shufe.service.quality.evaluate.QuestionTypeService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.EvaluateExporter;

/**
 * 评教明细统计
 * 
 * @author zhouqi
 */
public class EvaluateDetailStatAction extends EvaluateDetailSearchAction {
    
    protected QuestionTypeService questionTypeService;
    
    protected CourseTakeService courseTakeService;
    
    public void setCourseTakeService(CourseTakeService courseTakeService) {
        this.courseTakeService = courseTakeService;
    }
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String searchFormFlag = get(request, "searchFormFlag");
        if (StringUtils.isEmpty(searchFormFlag)) {
            searchFormFlag = "beenStat";
        }
        List stdTypes = baseInfoService.getBaseInfos(StudentType.class);
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        
        // 超找最适合用户的学生类别
        User user = getUser(request.getSession());
        StudentType stdType = (StudentType) stdTypes.get(0);
        if (null == stdTypeId) {
            if (user != null && user.isCategory(EamsRole.STD_USER)) {
                Student std = getStudentFromSession(request.getSession());
                if (null != std) {
                    stdType = std.getType();
                }
            }
        } else {
            stdType = (StudentType) utilService.get(StudentType.class, stdTypeId);
        }
        List calendarStdTypes = teachCalendarService.getCalendarStdTypes(stdType.getId());
        List rs = (List) CollectionUtils.intersection(calendarStdTypes, stdTypes);
        Collections.sort(rs, new PropertyComparator("code"));
        addCollection(request, "stdTypes", rs);
        addCollection(request, "departments", departmentService.getColleges());
        addSingleParameter(request, "searchFormFlag", searchFormFlag);
        setCalendarDataRealm(request, hasStdTypeCollege);
        
        // // teachCalendar.id 是从 noStatList 页面传入或 index 页面初始化时获得的，即未统计页面
        // Long CalendarId = getLong(request, "teachCalendar.id");
        // if (null != CalendarId) {
        // TeachCalendar calendar = teachCalendarService.getTeachCalendar(CalendarId);
        // request.setAttribute("calendar", calendar);
        // }
        
        addCollection(request, "departments", baseCodeService.getCodes(Department.class));
        addCollection(request, "questionnaires", utilService.load(Questionnaire.class, "state",
                Boolean.TRUE));
        return forward(request);
    }
    
    public ActionForward isNotStdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "calendar.id");
        List stdList = (List) courseTakeService.getNotStdList(request, calendarId);
        addCollection(request, "stdList", stdList);
        return forward(request);
    }
    
    /**
     * 统计评教记录<br>
     * 包括：
     * <li>初始化统计：是全部统计，即不传 id 的统计
     * <li>选择性统计：用户选择未统计的评教记录进行统计，即有 id 的统计
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
        // removeBeenStat(request, true);
        Long[] stdTypeIds = SeqStringUtil.transformToLong(get(request, "stdTypeIds"));
        Long[] departmentIds = SeqStringUtil.transformToLong(get(request, "departmentIds"));
        Long calendarId = getLong(request, "evaluateResult.teachCalendar.id");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
        List results = questionnairStatService.buildStatResult(stdTypeIds, departmentIds, calendar);
        
        if (CollectionUtils.isNotEmpty(results)) {
            utilService.saveOrUpdate(results);
            utilService.saveOrUpdate(questionnairStatService.buildEvaluateRanks(calendar));
            Map valueMap = new HashMap();
            valueMap.put("calendarId", calendarId);
            utilService.getUtilDao().executeUpdateNamedQuery("insertQuestionResults", valueMap);
            return redirect(request, "index", "info.action.success");
        } else {
            return redirect(request, "index", "info.onData.action.failure");
        }
    }
    
    /**
     * 设置院系课程评教统计的参考分值
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward departmentChoiceConfig(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String forwardPage = get(request, "forwardPage");
        if (StringUtils.isEmpty(forwardPage)) {
            forwardPage = "depertmentCourseStat";
        }
        List criterias = getCriteriaList();
        if (criterias.size() == 1) {
            EvaluationCriteria criteria = (EvaluationCriteria) criterias.get(0);
            return forward(request, new Action(this.getClass(), forwardPage,
                    "&evaluationCriteriaId=" + criteria.getId()));
        } else {
            addCollection(request, "evaluationCriterias", criterias);
            return forward(request);
        }
    }
    
    /**
     * @return
     */
    protected List getCriteriaList() {
        List criterias = (List) utilService.loadAll(EvaluationCriteria.class);
        for (Iterator iter = criterias.iterator(); iter.hasNext();) {
            EvaluationCriteria criteria = (EvaluationCriteria) iter.next();
            if (criteria.getCriteriaItems().isEmpty()) {
                iter.remove();
            }
        }
        return criterias;
    }
    
    /**
     * 个人历史评价对比分析
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward teacherHistory(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long evaluateTeacherId = getLong(request, "evaluateTeacherId");
        
        // 准备表头数据
        EvaluateTeacherStat evaluateTeacher = questionnairStatService
                .getEvaluateTeacherStat(evaluateTeacherId);
        request.setAttribute("evaluateTeacher", evaluateTeacher);
        
        // 准备某教师在各个学期的被评教情况
        EntityQuery queryTeacher = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        queryTeacher.add(new Condition("evaluateTeacher.teacher = (:teacher)", evaluateTeacher
                .getTeacher()));
        queryTeacher.add(new Condition("evaluateTeacher.calendar.start <= (:calendarStart)",
                evaluateTeacher.getCalendar().getStart()));
        List orders = new ArrayList();
        orders.add(new Order("evaluateTeacher.calendar.start"));
        orders.add(new Order("evaluateTeacher.calendar.term"));
        orders.add(new Order("evaluateTeacher.course.name"));
        queryTeacher.addOrder(orders);
        List evaluateTeachers = (List) utilService.search(queryTeacher);
        request.setAttribute("evaluateTeachers", evaluateTeachers);
        
        // 准备某教师于所在院系中的统计情况
        List evaluateDepartments = questionnairStatService
                .getEvaluateDepartments((com.shufe.model.system.baseinfo.Department) evaluateTeacher
                        .getTeacher().getDepartment());
        Map evaluateDepartMap = new HashMap();
        for (Iterator it2 = evaluateDepartments.iterator(); it2.hasNext();) {
            EvaluateDepartmentStat evaluateDepartment = (EvaluateDepartmentStat) it2.next();
            evaluateDepartMap
                    .put(evaluateDepartment.getCalendar().getId() + "", evaluateDepartment);
        }
        request.setAttribute("evaluateDepartMap", evaluateDepartMap);
        
        // 准备全校所有学期的统计情况
        List evaluateColleges = questionnairStatService.getAllEvaluateColleges();
        Map evaluateCollegeMap = new HashMap();
        for (Iterator it = evaluateColleges.iterator(); it.hasNext();) {
            EvaluateCollegeStat evaluateCollege = (EvaluateCollegeStat) it.next();
            evaluateCollegeMap.put(evaluateCollege.getCalendar().getId() + "", evaluateCollege);
        }
        request.setAttribute("evaluateCollegeMap", evaluateCollegeMap);
        return forward(request);
    }
    
    /**
     * 部门历史评教
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward departmentHistory(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Department department = (Department) utilService.load(Department.class, getLong(request,
                "departmentId"));
        addSingleParameter(request, "department", department);
        Collection criteriaItems = evaluationCriteriaService
                .getCriteriaItems((EvaluationCriteria) getCriteriaList().get(0));
        addCollection(request, "criteriaItems", criteriaItems);
        addCollection(request, "calendarDepartmentResults", questionnairStatService
                .getDepartResultList((List) criteriaItems, department));
        addCollection(request, "calendarCollegeResults", questionnairStatService
                .getCollegeResultList(criteriaItems));
        return forward(request);
    }
    
    /**
     * 按评教“问题选项”统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward departmentStatReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id"));
        
        EntityQuery query = new EntityQuery(QuestionTeacherStat.class, "questionTeacherStat");
        query.add(new Condition("questionTeacherStat.evaluateTeacherStat.calendar = :calendar",
                calendar));
        query.join("questionTeacherStat.questionResults", "questionResult");
        query.setSelect("distinct questionResult.option");
        addCollection(request, "options", utilService.search(query));
        
        String groupBy = "questionTeacherStat.evaluateTeacherStat.department.id,questionResult.option.id";
        query.groupBy(groupBy);
        // 院系，选项，人数，人次
        query.setSelect(groupBy + ",count(distinct questionResult.result.student),"
                + "count(questionResult.result.student)");
        addCollection(request, "departmentResults", utilService.search(query));
        
        addCollection(request, "evaluateDepartments", questionnairStatService
                .getEvaluateDepartments(calendar));
        return forward(request);
    }
    
    /**
     * 部门本次评教
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward departmentHistoryDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Department department = (Department) utilService.load(Department.class, getLong(request,
                "departmentId"));
        addSingleParameter(request, "department", department);
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id"));
        addSingleParameter(request, "calendar", calendar);
        Collection teacherInDepartmentResults = questionnairStatService.getEvaluateTeachers(
                calendar, department);
        addCollection(request, "teacherResults", teacherInDepartmentResults);
        
        addSingleParameter(request, "departmentResult", questionnairStatService
                .getDepartmentResults(teacherInDepartmentResults));
        Collection teacherInCollegeResults = questionnairStatService.getEvaluateTeachers(calendar);
        addSingleParameter(request, "collegeResult", questionnairStatService
                .getDepartmentResults(teacherInCollegeResults));
        return forward(request);
    }
    
    /**
     * 历史至今所有课程评教统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward historyCollegeStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EvaluationCriteria evaluationCriteria = evaluationCriteriaService
                .getEvaluationCriteria(getLong(request, "evaluationCriteriaId"));
        addSingleParameter(request, "evaluationCriteria", evaluationCriteria);
        addCollection(request, "evaluateCollegeCalendars", questionnairStatService
                .getCollegeResultList(teachCalendarService.getTeachCalendar(getLong(request,
                        "calendar.id")), evaluationCriteriaService
                        .getCriteriaItems(evaluationCriteria)));
        return forward(request);
    }
    
    /**
     * 学院分项汇总
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward departmentGroupItemInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Department department = (Department) utilService.load(Department.class, getLong(request,
                "departmentId"));
        addSingleParameter(request, "department", department);
        
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id"));
        addSingleParameter(request, "calendar", calendar);
        
        addSingleParameter(request, "groupItemResults", questionnairStatService
                .getDepartResultList(calendar, department));
        addSingleParameter(request, "collegeResults", questionnairStatService
                .getCollegeResultList(calendar));
        addSingleParameter(request, "questionTypeMap", questionTypeService.getQuestionTypeMap());
        return forward(request);
    }
    
    /**
     * 学校分项汇总
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward collegeGroupItemInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id"));
        addSingleParameter(request, "calendar", calendar);
        
        addSingleParameter(request, "collegeResults", questionnairStatService
                .getCollegeResultList(calendar));
        addSingleParameter(request, "questionTypeMap", questionTypeService.getQuestionTypeMap());
        return forward(request);
    }
    
    /**
     * 问卷分类评教统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward questionnaireCollege(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id"));
        addSingleParameter(request, "calendar", calendar);
        
        Questionnaire questionnaire = (Questionnaire) utilService.load(Questionnaire.class,
                getLong(request, "questionnaireId"));
        addSingleParameter(request, "questionnaire", questionnaire);
        
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        query.add(new Condition("evaluateTeacher.calendar = :calendar", calendar));
        String existsStatement = "exists (from QuestionResult questionResult where questionResult.result.task.id in (select task.id from TeachTask task where task.course.id = evaluateTeacher.course.id and task.calendar.id = evaluateTeacher.calendar.id) and questionResult.result.questionnaire.id = :questionnaireId)";
        query.add(new Condition(existsStatement, questionnaire.getId()));
        addCollection(request, "teacherResults", utilService.search(query));
        
        addSingleParameter(request, "collegeEvaluate", questionnairStatService
                .getEvaluateCollegeStat(calendar));
        return forward(request);
    }
    
    /**
     * 院系课程评教统计－按参考分值统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward depertmentCourseStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long evaluationCriteriaId = getLong(request, "evaluationCriteriaId");
        EvaluationCriteria evaluationCriteria = evaluationCriteriaService
                .getEvaluationCriteria(evaluationCriteriaId);
        request.setAttribute("evaluationCriteria", evaluationCriteria);
        List criteriaItems = evaluationCriteriaService.getCriteriaItems(evaluationCriteria);
        List departmentResults = questionnairStatService.getDepartResultList(criteriaItems,
                teachCalendarService.getTeachCalendar(getLong(request, "calendar.id")));
        request.setAttribute("departmentResults", departmentResults);
        request.setAttribute("collegeResult", questionnairStatService
                .getCollegeResultList(departmentResults));
        return forward(request);
    }
    
    /**
     * 清除或删除已统计的评教结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        removeBeenStat(request, false);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 清除或删除已统计的评教结果－过程
     * 
     * @param request
     * @param isAll
     */
    protected void removeBeenStat(HttpServletRequest request, boolean isAll) {
        // 当前学期的统计结果全部清除
        Long calendarId = getLong(request, "calendar.id");
        Collection records = new ArrayList();
        Collection recordTs = utilService
                .load(EvaluateTeacherStat.class, "calendar.id", calendarId);
        Collection recordDs = utilService.load(EvaluateDepartmentStat.class, "calendar.id",
                calendarId);
        Collection recordCs = utilService
                .load(EvaluateCollegeStat.class, "calendar.id", calendarId);
        
        // 选择性删除
        if (!isAll) {
            Long[] evaluateTeacherIds = SeqStringUtil.transformToLong(get(request,
                    "evaluateTeacherIds"));
            if (null != evaluateTeacherIds && evaluateTeacherIds.length > 0) {
                // 清除选择的教师评教结果记录
                recordTs = utilService.load(EvaluateTeacherStat.class, "id", evaluateTeacherIds);
                Long[] teacherIds = new Long[recordTs.size()];
                int k = 0;
                for (Iterator it = recordTs.iterator(); it.hasNext();) {
                    EvaluateTeacherStat evaluateTeacher = (EvaluateTeacherStat) it.next();
                    teacherIds[k++] = evaluateTeacher.getTeacher().getId();
                }
                // 不清除使用中的开课院系
                EntityQuery queryD = new EntityQuery(EvaluateDepartmentStat.class,
                        "evaluateDepartment");
                queryD.add(new Condition("evaluateDepartment.calendar.id = (:calendarId)",
                        calendarId));
                queryD
                        .add(new Condition(
                                "evaluateDepartment.department in (select evaluateTeacher.task.arrangeInfo.teachDepart from EvaluateTeacherStat evaluateTeacher where evaluateTeacher.calendar.id = evaluateDepartment.calendar.id and evaluateTeacher.teacher.id not in (:teacherIds))",
                                teacherIds));
                Collection evaluateDepartments = utilService.search(queryD);
                for (Iterator itD1 = recordDs.iterator(); itD1.hasNext();) {
                    EvaluateDepartmentStat evaluateDepartment = (EvaluateDepartmentStat) itD1
                            .next();
                    for (Iterator itD2 = evaluateDepartments.iterator(); itD2.hasNext();) {
                        EvaluateDepartmentStat evaluateD = (EvaluateDepartmentStat) itD2.next();
                        if (ObjectUtils.equals(evaluateDepartment, evaluateD)) {
                            itD1.remove();
                            // 不清除使用中的全校统计结果记录
                            recordCs = Collections.EMPTY_LIST;
                            break;
                        }
                    }
                }
            }
        }
        records.addAll(recordTs);
        records.addAll(recordDs);
        records.addAll(recordCs);
        utilService.remove(records);
    }
    
    /**
     * 组建查询条件(教师个人-管理员界面)
     * 
     * @param request
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request, TeachCalendar calendar) {
        EntityQuery query = new EntityQuery(EvaluateTeacherStat.class, "evaluateTeacher");
        DataRealmUtils.addDataRealms(query, new String[] { null, "evaluateTeacher.department.id" },
                restrictionHelper.getDataRealmsWith(null, request));
        populateConditions(request, query);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        return query;
    }
    
    /**
     * 明细导出
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#configExportContext(javax.servlet.http.HttpServletRequest,
     *      com.ekingstar.commons.transfer.exporter.Context)
     */
    protected void configExportContext(HttpServletRequest request, Context context) {
        EntityQuery query = buildQuery(request, null);
        query.addOrder(new Order("evaluateTeacher.rank"));
        query.setLimit(null);
        Collection evaluateTeachers = utilService.search(query);
        
        int maxQuestionCount = 0;
        List exportData = new ArrayList();
        for (Iterator it1 = evaluateTeachers.iterator(); it1.hasNext();) {
            EvaluateTeacherStat evaluateTeacher = (EvaluateTeacherStat) it1.next();
            Object[] obj = new Object[3];
            obj[0] = evaluateTeacher;
            Map questionMap = new HashMap();
            obj[1] = questionMap;
            List questionsStat = new ArrayList(evaluateTeacher.getQuestionsStat());
            if (maxQuestionCount < questionsStat.size()) {
                maxQuestionCount = questionsStat.size();
            }
            String property = "question.priority desc";
            PropertyComparator cmp = new PropertyComparator(property);
            Collections.sort(questionsStat, cmp);
            int questionK = 0;
            for (Iterator it2 = questionsStat.iterator(); it2.hasNext();) {
                QuestionStat questionStat = (QuestionStat) it2.next();
                questionMap.put(String.valueOf(questionK++), questionStat);
            }
            exportData.add(obj);
        }
        for (Iterator it = exportData.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            obj[2] = maxQuestionCount + "";
        }
        context.getDatas().put("items", exportData);
        context.getDatas().put("maxQuestionCount", new Integer(maxQuestionCount));
    }
    
    /**
     * @see com.shufe.web.action.common.DispatchBasicAction#getExporter(javax.servlet.http.HttpServletRequest,
     *      com.ekingstar.commons.transfer.exporter.Context)
     */
    protected Exporter getExporter(HttpServletRequest request, Context context) {
        EvaluateExporter exporter = new EvaluateExporter();
        ((DefaultEntityExporter) exporter).setAttrs(StringUtils.split(getExportKeys(request), ","));
        ((DefaultEntityExporter) exporter).setPropertyExtractor(getPropertyExtractor(request));
        return exporter;
    }
    
    public void setQuestionTypeService(QuestionTypeService questionTypeService) {
        this.questionTypeService = questionTypeService;
    }
}
