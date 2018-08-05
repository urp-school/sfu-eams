//$Id: TeachTaskGenAction.java,v 1.1 2009-3-25 下午05:02:30 zhouqi Exp $
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
 * zhouqi              2009-3-25             Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.task;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.Constants;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.OnCampusTimeNotFoundException;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.service.course.task.TaskGenParams;

public class TeachTaskGenAction extends TeachTaskSearchAction {
    
    private TeachPlanService teachPlanService;
    
    public TeachTaskGenAction() {
        super();
    }
    
    /**
     * 初始化教学任务生成和确认的基础数据
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    /**
     * 加载培养计划列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     */
    public ActionForward teachPlanList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse) throws Exception {
        // get depart
        TeachPlan plan = (TeachPlan) populate(request, TeachPlan.class, Constants.TEACHPLAN);
        TeachCalendar calendar = (TeachCalendar) populate(request, TeachCalendar.class,
                Constants.CALENDAR);
        
        StudentType stdType = (StudentType) utilService.load(StudentType.class, calendar
                .getStudentType().getId());
        
        // get calendar from db
        if (StringUtils.isNotEmpty(calendar.getYear())
                && StringUtils.isNotEmpty(calendar.getTerm())) {
            calendar = teachCalendarService.getTeachCalendar(stdType, calendar.getYear(), calendar
                    .getTerm());
        } else {
            stdType.getSubTypes().size();
            // stdType.getTeachCalendars().size();
            calendar = teachCalendarService.getTeachCalendar(stdType);
        }
        // load plans
        Boolean active = getBoolean(request, "active");
        if (null == active) {
            active = Boolean.TRUE;
        }
        Boolean omitSmallTerm = getBoolean(request, "omitSmallTerm");
        if (null == omitSmallTerm) {
            omitSmallTerm = Boolean.TRUE;
        }
        try {
            Collection teachPlanList = teachPlanService.getTeachPlansOfTaskGeneration(plan,
                    calendar, getStdTypeIdSeq(request), getDepartmentIdSeq(request), active,
                    omitSmallTerm.booleanValue());
            Results.addObject("teachPlanList", teachPlanList);
        } catch (OnCampusTimeNotFoundException e) {
            debug("cannot find onCompus time :", e);
            return forward(mapping, request, e.getMessage().substring(0,
                    e.getMessage().indexOf(":")), "error");
        }
        
        // fill data
        request.setAttribute("calendar", calendar);
        request.setAttribute("stdType", stdType);
        return forward(request);
    }
    
    /**
     * 加载培养计划的教学任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward listTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String planIdSeq = request.getParameter("fromPlan.ids");
        if (StringUtils.isEmpty(planIdSeq))
            return forward(mapping, request, "error.teachPlan.id.needed", "error");
        EntityQuery query = new EntityQuery(TeachTask.class, "task");
        populateConditions(request, query);
        String teacherName = request.getParameter("teacher.name");
        if (StringUtils.isNotEmpty(teacherName)) {
            query.join("task.arrangeInfo.teachers", "teacher");
            query.add(new Condition("teacher.name like :teacherName", "%" + teacherName + "%"));
        }
        query.add(new Condition("task.fromPlan.id in(:ids)", SeqStringUtil
                .transformToLong(planIdSeq)));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "tasks", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 生成教学任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     */
    public ActionForward gen(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String calendarId = request.getParameter("task.calendar.id");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(new Long(calendarId));
        String cultivateSchemeIdSeq = request.getParameter("teachPlanIds");
        
        Long[] cultivateSchemeIds = SeqStringUtil.transformToLong(cultivateSchemeIdSeq);
        try {
            logHelper.info(request, "Generator TeachTask for cultivateScheme :"
                    + ArrayUtils.toString(cultivateSchemeIds));
            response.setContentType("text/html; charset=utf-8");
            TaskGenObserver observer = (TaskGenObserver) getOutputProcessObserver(mapping, request,
                    response, TaskGenObserver.class);
            TaskGenParams params = (TaskGenParams) populate(request, TaskGenParams.class, "params");
            if (null == params.getIgnoreClass()) {
                params.setIgnoreClass(Boolean.FALSE);
            }
            params.setOmmitedCourseCodeSeq("," + params.getOmmitedCourseCodeSeq() + ",");
            
            teachTaskService.genTeachTasks(cultivateSchemeIds, calendar, observer, params);
            response.getWriter().flush();
            response.getWriter().close();
            return null;
        } catch (Exception e) {
            logHelper.info(request, "Failure in generating TeachTask for cultivateScheme :"
                    + ArrayUtils.toString(cultivateSchemeIds), e);
            return forward(mapping, request, "error.occurred", "error");
        }
    }
    
    /**
     * 生成教学任务的提示<BR>
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward genSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addSingleParameter(request, "roomConfigList", baseCodeService.getCodes(ClassroomType.class));
        Long calendarId = getLong(request, "task.calendar.id");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
        TaskGenParams params = TaskGenParams.getDefault();
        params.setWeekStart(new Integer(1));
        params.setWeeks(calendar.getWeeks());
        request.setAttribute("params", params);
        addCollection(request, "courseTypes", baseCodeService.getCodes(CourseType.class));
        
        EntityQuery query = new EntityQuery(TeachPlan.class, "plan");
        query.join("plan.courseGroups", "courseGroup");
        query.add(new Condition("courseGroup.courseType.isPractice = 1"));
        addSingleParameter(request, "hasPractice", new Boolean(CollectionUtils.isNotEmpty(utilService
                .search(query))));
        return forward(request);
    }
    
    /**
     * @param teachPlanService
     *            The teachPlanService to set.
     */
    public void setTeachPlanService(TeachPlanService teachPlanService) {
        this.teachPlanService = teachPlanService;
    }
    
}