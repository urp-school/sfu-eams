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
 * chaostone             2007-1-3            Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.grade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.GradeState;
import com.shufe.model.course.grade.stat.GradeSegStats;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.grade.gp.GradePointRuleService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;
import com.shufe.web.action.course.grade.report.TeachClassGradeReportAction;

/**
 * 院系成绩添加,输入,打印,分段统计页面界面相应类
 * 
 * @author chaostone
 */
public class CollegeGradeAction extends CalendarRestrictionSupportAction {
    
    protected GradeService gradeService;
    
    protected GradePointRuleService gradePointRuleService;
    
    protected TeachTaskService teachTaskService;
    
    protected CourseGradeHelper courseGradeHelper;
    
    /**
     * 录入成绩主界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeDepart);
        String stdTypeDataRealm = getStdTypeIdSeq(request);
        String departDataRealm = getDepartmentIdSeq(request);
        addCollection(request, "courseTypes", teachTaskService.getCourseTypesOfTask(
                stdTypeDataRealm, departDataRealm, (TeachCalendar) request
                        .getAttribute(Constants.CALENDAR)));
        addCollection(request, "teachDepartList", teachTaskService.getTeachDepartsOfTask(
                stdTypeDataRealm, departDataRealm, (TeachCalendar) request
                        .getAttribute(Constants.CALENDAR)));
        return forward(request);
    }
    
    /**
     * 依据成绩录入情况,查找任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(TeachTask.class, "task");
        QueryRequestSupport.populateConditions(request, query, "task.teachClass.stdType.id");
        Boolean confirmGA = getBoolean(request, "gradeState.confirmGA");
        GradeType FINAL = (GradeType) baseCodeService.getCode(GradeType.class, GradeType.FINAL);
        query.add(new Condition("bitand(task.gradeState.confirmStatus,"
                + FINAL.getMark().intValue() + ")" + (Boolean.TRUE.equals(confirmGA) ? "=" : "<>")
                + FINAL.getMark().intValue()));
        
        String teacherName = get(request, "teacher.name");
        if (!StringUtils.isEmpty(teacherName)) {
            query.join("task.arrangeInfo.teachers", "teacher");
            query.add(new Condition("teacher.name like :teacherName", "%" + teacherName + "%"));
        }
        query.add(new Condition("task.calendar.id=" + getLong(request, "calendar.id")));
        query.setLimit(getPageLimit(request));
        Long stdTypeId = getLong(request, "task.teachClass.stdType.id");
        if (null == stdTypeId) {
            stdTypeId = getLong(request, "calendar.studentType.id");
        }
        DataRealmUtils.addDataRealms(query, new String[] { "task.teachClass.stdType.id",
                "task.arrangeInfo.teachDepart.id" }, getDataRealmsWith(stdTypeId, request));
        
        String publishStatus = get(request, "publishStatus");
        if (!StringUtils.isEmpty(publishStatus)) {
            if (StringUtils.equals("1", publishStatus)) {
                query.add(new Condition("bitand(task.gradeState.publishStatus, "
                        + FINAL.getMark().intValue() + ") > 0"));
            } else {
                query.add(new Condition("bitand(task.gradeState.publishStatus, "
                        + FINAL.getMark().intValue() + ") = 0"));
            }
            // query.addOrder(OrderUtils.parser("bitand(task.gradeState.publishStatus, "
            // + FINAL.getMark().intValue() + " ) desc"));
        }
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        
        addCollection(request, "tasks", utilService.search(query));
        addCollection(request, "gradeTypes", baseCodeService.getCodes(GradeType.class));
        request.setAttribute("action", RequestUtils.getRequestAction(request));
        request.setAttribute("FINAL", FINAL);
        return forward(request);
    }
    
    /**
     * 批量设置百分比
     * 
     * @return
     */
    public ActionForward editBatchPercent(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "tasks", utilService.load(TeachTask.class, "id", SeqStringUtil
                .transformToLong(get(request, "taskIds"))));
        addCollection(request, "markStyles", baseCodeService.getCodes(MarkStyle.class));
        addCollection(request, "gradeTypes", getCanInputGradeType());
        return forward(request, "batchPercentForm");
    }
    
    /**
     * @return
     */
    protected Collection getCanInputGradeType() {
        EntityQuery query = new EntityQuery(GradeType.class, "gradeType");
        query.add(new Condition("gradeType.state = true"));
        query.add(new Condition("gradeType.teacherCanInputGrade = true"));
        Collection gradeTypes = utilService.search(query);
        return gradeTypes;
    }
    
    public ActionForward saveBatchPercent(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 准备所选课程对应的教学任务
        Long[] taskIds = SeqStringUtil.transformToLong(get(request, "taskIds"));
        Collection tasks = utilService.load(TeachTask.class, "id", taskIds);
        Collection gradeTypes = getCanInputGradeType();
        
        Long GAMarkStyleId = getLong(request, "GAMarkStyleId");
        Integer precision = getInteger(request, "precision");
        StringBuilder percents = new StringBuilder(";");
        for (Iterator iter = gradeTypes.iterator(); iter.hasNext();) {
            GradeType gradeType = (GradeType) iter.next();
            Integer percent = getInteger(request, "percent" + gradeType.getId());
            if (percent.intValue() != 0) {
                percents.append(gradeType.getId()).append("=");
                percents.append(percent.intValue() / 100.0);
                percents.append(";");
            }
        }
        
        for (Iterator it = tasks.iterator(); it.hasNext();) {
            TeachTask task = (TeachTask) it.next();
            if (null == task.getGradeState()) {
                task.setGradeState(new GradeState());
            }
            GradeState gradeState = task.getGradeState();
            gradeState.setMarkStyle(new MarkStyle(GAMarkStyleId));
            gradeState.setPrecision(precision);
            gradeState.setPercents(percents.toString());
        }
        Collection toBeSave = new ArrayList();
        toBeSave.addAll(gradeService.reCalcGrade(tasks, getUser(request)));
        toBeSave.addAll(tasks);
        utilService.saveOrUpdate(toBeSave);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 没有成绩的学生名单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward noGradeTakeList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery takeQuery = buildNoGradeCourseTakeQuery(request);
        addCollection(request, "noGradeTakes", utilService.search(takeQuery));
        return forward(request);
    }
    
    /**
     * 组装一个没有成绩的上课名单的查询
     * 
     * @param request
     * @return
     */
    private EntityQuery buildNoGradeCourseTakeQuery(HttpServletRequest request) {
        Long calendarId = getLong(request, "calendar.id");
        EntityQuery takeQuery = new EntityQuery(CourseTake.class, "take");
        takeQuery.add(new Condition("take.task.calendar.id=" + calendarId
                + "  and take.courseGrade is null"));
        takeQuery.setLimit(getPageLimit(request));
        DataRealmUtils.addDataRealms(takeQuery, new String[] { "take.student.type.id",
                "take.student.department.id" }, getDataRealms(request));
        takeQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return takeQuery;
    }
    
    /**
     * 导出没有成绩的学生上课名单
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        String kind = get(request, "kind");
        if (StringUtils.isEmpty(kind) || kind.equals("noGradeTakeList")) {
            EntityQuery takeQuery = buildNoGradeCourseTakeQuery(request);
            takeQuery.setLimit(null);
            return utilService.search(takeQuery);
        } else if (kind.equals("noPassCourseGradeList")) {
            EntityQuery noPassQuery = noPassCourseQuery(request);
            noPassQuery.setLimit(null);
            return utilService.search(noPassQuery);
        }
        return super.getExportDatas(request);
    }
    
    /**
     * 成绩不及格的学生成绩名单<br>
     * TODO 没有区分各种成绩登记方式
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward noPassCourseGradeList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery gradeQuery = noPassCourseQuery(request);
        addCollection(request, "noPassCourseGrades", utilService.search(gradeQuery));
        return forward(request);
    }
    
    /**
     * @param request
     * @param calendarId
     * @return
     */
    protected EntityQuery noPassCourseQuery(HttpServletRequest request) {
        Long calendarId = getLong(request, "calendar.id");
        EntityQuery gradeQuery = new EntityQuery(CourseGrade.class, "grade");
        gradeQuery.add(new Condition("grade.calendar.id=" + calendarId
                + "  and (grade.score  is null or grade.score<60)"));
        gradeQuery.setLimit(getPageLimit(request));
        DataRealmUtils.addDataRealms(gradeQuery, new String[] { "grade.std.type.id",
                "grade.std.department.id" }, getDataRealms(request));
        gradeQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return gradeQuery;
    }
    
    /**
     * 删除教学任务的课程成绩
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeGrade(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String msg = courseGradeHelper.removeTaskGrade(request, getUser(request));
        if (StringUtils.isEmpty(msg)) {
            logHelper.info(request, "delete grade");
            return redirect(request, "search", "info.delete.success");
        } else {
            return forwardError(mapping, request, msg);
        }
    }
    
    /**
     * 显示分段统计报告<br>
     * 支持按照课程／任务进行分段统计
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
        String kind = get(request, "kind");
        if (!ObjectUtils.equals(kind, GradeSegStats.COURSE)) {
            String taskIdSeq = request.getParameter("taskIds");
            if (StringUtils.isEmpty(taskIdSeq)) {
                return forwardError(mapping, request, "error.parameters.needed");
            }
            new TeachClassGradeHelper(baseCodeService, gradeService).statTask(taskIdSeq,
                    new Long[] { GradeType.END, GradeType.GA }, request);
            addSingleParameter(request, "kind", GradeSegStats.TASK);
            Long onEaxmStdCount = (Long) utilService.searchHQLQuery(
                    "select count(*) from CourseGrade cg where cg.task.id in (" + taskIdSeq
                            + ") and cg.score is null").get(0);
            Long onEaxmStdCountNotNormal = (Long) utilService
                    .searchHQLQuery(
                            "select count(*) from CourseGrade cg where cg.task.id in ("
                                    + taskIdSeq
                                    + ") and exists(from ExamGrade grade where grade.courseGrade.id=cg.id and grade.examStatus.id in (4,5))")
                    .get(0);
            Long examCount = (Long) utilService.searchHQLQuery(
                    "select count(*) from CourseTake ct where ct.task.id in (" + taskIdSeq + ")")
                    .get(0);
            addSingleParameter(request, "examCount", examCount);
            addSingleParameter(request, "onEaxmStdCount", onEaxmStdCount);
            addSingleParameter(request, "onEaxmStdCountNotNormal", onEaxmStdCountNotNormal);
        } else {
            String taskIdSeq = get(request, "taskIds");
            List courses = null;
            TeachCalendar calendar = null;
            if (StringUtils.isNotEmpty(taskIdSeq)) {
                EntityQuery query = new EntityQuery(TeachTask.class, "task");
                query.setSelect("distinct task.course");
                query.add(new Condition("task.id in(:taskIds)", SeqStringUtil
                        .transformToLong(taskIdSeq)));
                courses = (List) utilService.search(query);
                query = new EntityQuery(TeachTask.class, "task");
                query.setSelect("distinct task.calendar");
                query.add(new Condition("task.id in(:taskIds)", SeqStringUtil
                        .transformToLong(taskIdSeq)));
                calendar = (TeachCalendar) ((List) utilService.search(query)).get(0);
            }
            new TeachClassGradeHelper(baseCodeService, gradeService).statCourse(courses, calendar,
                    new Long[] { GradeType.END, GradeType.GA }, request);
            addSingleParameter(request, "kind", GradeSegStats.COURSE);
            Long courseId = (Long) utilService.searchHQLQuery(
                    "select tt.course.id from TeachTask tt where tt.id=" + taskIdSeq).get(0);
            Long onEaxmStdCount = (Long) utilService.searchHQLQuery(
                    "select count(*) from CourseGrade cg where cg.course.id=" + courseId
                            + " and cg.score is null and cg.calendar.id=" + calendar.getId())
                    .get(0);
            Long onEaxmStdCountNotNormal = (Long) utilService
                    .searchHQLQuery(
                            "select count(*) from CourseGrade cg where cg.course.id in ("
                                    + courseId
                                    + ") and cg.calendar.id="
                                    + calendar.getId()
                                    + "and exists(from ExamGrade grade where grade.courseGrade.id=cg.id and grade.examStatus.id in (4,5))")
                    .get(0);
            Long examCount = (Long) utilService.searchHQLQuery(
                    "select count(*) from CourseTake ct where ct.task.course.id=" + courseId
                            + " and ct.task.calendar.id=" + calendar.getId()).get(0);
            addSingleParameter(request, "examCount", examCount);
            addSingleParameter(request, "onEaxmStdCount", onEaxmStdCount);
            addSingleParameter(request, "onEaxmStdCountNotNormal", onEaxmStdCountNotNormal);
        }
        return forward(request);
    }
    
    /**
     * 打印成绩
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return forward(request, new Action(TeachClassGradeReportAction.class, "report"));
    }
    
    /**
     * 查看单个教学任务所有成绩信息
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
        Long taskId = getLong(request, "taskId");
        TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
        new TeachClassGradeHelper(baseCodeService, gradeService).info(task, request);
        return forward(request);
    }
    
    /**
     * 查看成绩状态
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward gradeStateInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long taskId = getLong(request, "taskId");
        TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
        List gradeTypes = baseCodeService.getCodes(GradeType.class);
        Collections.sort(gradeTypes, new PropertyComparator("priority", true));
        addCollection(request, "gradeTypes", gradeTypes);
        addCollection(request, "gradeTypeInState", gradeService.getGradeTypes(task.getGradeState()));
        request.setAttribute("gradeState", task.getGradeState());
        request.setAttribute("task", task);
        return forward(request);
    }
    
    /**
     * 查看每学期所开课程列表
     * 
     * @deprecated
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward courseList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(TeachTask.class, "task");
        query.setSelect("distinct task.course");
        QueryRequestSupport.populateConditions(request, query, "task.teachClass.stdType.id");
        query.add(new Condition("task.calendar.id=" + getLong(request, "calendar.id")));
        query.setLimit(getPageLimit(request));
        Long stdTypeId = getLong(request, "task.teachClass.stdType.id");
        if (null == stdTypeId) {
            stdTypeId = getLong(request, "calendar.studentType.id");
        }
        DataRealmUtils.addDataRealms(query, new String[] { "task.teachClass.stdType.id",
                "task.arrangeInfo.teachDepart.id" }, getDataRealmsWith(stdTypeId, request));
        
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return forward(request);
    }
    
    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }
    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    public void setGradePointRuleService(GradePointRuleService gradePointRuleService) {
        this.gradePointRuleService = gradePointRuleService;
    }
    
    public void setCourseGradeHelper(CourseGradeHelper courseGradeHelper) {
        this.courseGradeHelper = courseGradeHelper;
    }
}
