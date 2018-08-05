package com.shufe.web.action.course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.query.limit.Page;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.system.calendar.TermCalculator;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.service.util.stat.StatGroup;
import com.shufe.service.util.stat.StatHelper;
import com.shufe.service.util.stat.StatItem;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 课程统计
 * 
 * @author chaostone
 * 
 */
public class CourseStatAction extends CalendarRestrictionSupportAction {
    
    TeachPlanService teachPlanService;
    
    /**
     * 培养计划内课程查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward planCourseSearchHome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    /**
     * 培养计划内课程查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward planCourseStatHome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    /**
     * 培养计划内查询课程列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward planCourseList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long stdTypeId = getLong(request, "stdType.id");
        List stdTypes = getCalendarStdTypesOf(stdTypeId, request);
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(stdTypeId, get(request,
                "calendar.year"), request.getParameter("calendar.term"));
        EntityQuery query = buildPlanCourseQuery(request, calendar, stdTypes);
        addCollection(request, "planCourses", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 培养计划内课程统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward planCourseStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long stdTypeId = getLong(request, "stdType.id");
        List stdTypes = getCalendarStdTypesOf(stdTypeId, request);
        
        TeachCalendar startCalendar = teachCalendarService.getTeachCalendar(stdTypeId, get(request,
                "yearStart"), request.getParameter("termStart"));
        TeachCalendar endCalendar = teachCalendarService.getTeachCalendar(stdTypeId, get(request,
                "yearEnd"), request.getParameter("termEnd"));
        
        EntityQuery taskQuery = new EntityQuery(TeachTask.class, "task");
        taskQuery.setSelect("task.arrangeInfo.teachDepart.id,count(distinct task.course.id)");
        taskQuery.groupBy("task.arrangeInfo.teachDepart.id");
        DataRealmUtils.addDataRealm(taskQuery, new String[] { "task.teachClass.stdType.id",
                "task.arrangeInfo.teachDepart.id" }, getDataRealm(request));
        Condition taskCalendarCondition = new Condition("task.calendar.id =:calendarId");
        taskQuery.add(taskCalendarCondition);
        StatGroup group = new StatGroup(null, new ArrayList());
        if (null != startCalendar && null != endCalendar) {
            while (true) {
                if (startCalendar.after(endCalendar)) {
                    break;
                }
                StatGroup calendarGroup = new StatGroup(startCalendar, new ArrayList());
                
                EntityQuery planQuery = buildPlanCourseQuery(request, startCalendar, stdTypes);
                planQuery.setOrders(null);
                planQuery.setLimit(null);
                // planQuery.setSelect("planCourse.teachDepart.id,count(distinct
                // planCourse.course.id||planCourse.course.credits||planCourse.weekHour||planCourse.course.period),1");
                planQuery
                        .setSelect("planCourse.teachDepart.id,count(distinct planCourse.course.id),1");
                planQuery.groupBy("planCourse.teachDepart.id");
                List rs = (List) utilService.search(planQuery);
                for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
                    Object[] data = (Object[]) iterator.next();
                    data[2] = null;
                    data[0] = utilService.get(Department.class, (Long) data[0]);
                    calendarGroup.addData(data, 0, 2, 2);
                }
                // 任务查询
                taskCalendarCondition.setValues(Collections.singletonList(startCalendar.getId()));
                List taskRs = (List) utilService.search(taskQuery);
                for (Iterator iterator = taskRs.iterator(); iterator.hasNext();) {
                    Object[] data = (Object[]) iterator.next();
                    data[0] = utilService.get(Department.class, (Long) data[0]);
                    StatItem item = (StatItem) calendarGroup.getItem(data[0]);
                    if (null != item) {
                        item.getCountors()[item.getCountors().length - 1] = (Comparable) data[1];
                    } else {
                        calendarGroup.getItems().add(
                                new StatItem(data[0], null, (Comparable) data[1]));
                    }
                }
                startCalendar = startCalendar.getNext();
                group.getItems().add(calendarGroup);
            }
        }
        List datas = new ArrayList();
        for (Iterator iterator = group.getItems().iterator(); iterator.hasNext();) {
            StatGroup statGroup = (StatGroup) iterator.next();
            for (Iterator iterator2 = statGroup.getItems().iterator(); iterator2.hasNext();) {
                StatItem item = (StatItem) iterator2.next();
                datas.add(new Object[] { item.getWhat(), statGroup.getWhat(),
                        item.getCountors()[0], item.getCountors()[1] });
            }
        }
        List statGroups = StatGroup.buildStatGroups(datas, 2);
        request.setAttribute("courseStat", new StatGroup(null, statGroups));
        return forward(request);
    }
    
    /**
     * 构建培养计划内课程查询语句
     * 
     * @param request
     * @param calendar
     * @param stdTypes
     * @return
     */
    private EntityQuery buildPlanCourseQuery(HttpServletRequest request, TeachCalendar calendar,
            List stdTypes) {
        StringBuffer stdTypeIdBuf = new StringBuffer(",");
        for (int i = 0; i < stdTypes.size(); i++) {
            stdTypeIdBuf.append(((StudentType) stdTypes.get(i)).getId()).append(",");
        }
//        List enrollYears = teachCalendarService.getActiveEnrollTurns(calendar, stdTypeIdBuf
//                .toString());
//        StringBuffer conditio1n = new StringBuffer(" and planCourse.termSeq=(CASE ");
//        TermCalculator calc = new TermCalculator(teachCalendarService, calendar);
//        for (Iterator iterator = enrollYears.iterator(); iterator.hasNext();) {
//            String year = (String) iterator.next();
//            try {
//                int term = calc.getTerm(calendar.getStudentType(), year, Boolean.TRUE);
//                if (term > 0) {
//                    conditio1n.append("when teachPlan.enrollTurn='" + year + "' then '" + term
//                            + "' ");
//                }
//            } catch (Exception e) {
//            }
//        }
        EntityQuery query = new EntityQuery(TeachPlan.class, "teachPlan");
        query.join("teachPlan.courseGroups", "cgroup");
        query.join("cgroup.planCourses", "planCourse");
        query
                .setSelect("distinct planCourse.teachDepart,planCourse.course,planCourse.course.credits,planCourse.course.weekHour,planCourse.course.extInfo.period");
        Long teachDepartId = getLong(request, "planCourse.teachDepart.id");
        if (null != teachDepartId) {
            query.add(new Condition("planCourse.teachDepart.id=:teachDepartId", teachDepartId));
        }
        DataRealm dataRealm = new DataRealm(stdTypeIdBuf.toString(), getDepartmentIdSeq(request));
        DataRealmUtils.addDataRealm(query, new String[] { "teachPlan.stdType.id",
                "planCourse.teachDepart.id" }, dataRealm);
        query.setLimit(getPageLimit(request));
        Long majorTypeId = getLong(request, "majorTypeId");
        query.add(new Condition("teachPlan.speciality.majorType.id=:majorTypeId",majorTypeId));
        query.add(new Condition("teachPlan.std is null"));
        populateConditions(request, query);
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return query;
    }
    
    /**
     * 教学任务内查询课程列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward taskCourseList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(TeachTask.class, "teachTask");
        populateConditions(request, query, "teachTask.teachClass.stdType.id");
        Long stdTypeId = getLong(request, "teachTask.teachClass.stdType.id");
        String yearStart = request.getParameter("beginYear");
        String termStart = request.getParameter("beginTerm");
        String yearEnd = request.getParameter("endYear");
        String termEnd = request.getParameter("endTerm");
        List conditionCalendars = new ArrayList();
        TeachCalendar startCalendar = teachCalendarService.getTeachCalendar(stdTypeId, yearStart,
                termStart);
        TeachCalendar endCalendar = teachCalendarService.getTeachCalendar(stdTypeId, yearEnd,
                termEnd);
        if (null == startCalendar || null == endCalendar) {
            addCollection(request, "courses", Page.EMPTY_PAGE);
        } else {
            while (true) {
                if (startCalendar.after(endCalendar)) {
                    break;
                }
                conditionCalendars.add(startCalendar.getId());
                startCalendar = startCalendar.getNext();
            }
            query.add(new Condition("teachTask.calendar.id in (:conditionCalendars)",
                    conditionCalendars));
            query
                    .setSelect("distinct teachTask.course.code,teachTask.course.name,teachTask.course.credits,teachTask.arrangeInfo.weekUnits,teachTask.arrangeInfo.overallUnits");
            query.setLimit(getPageLimit(request));
            restrictionHelper.addStdTypeTreeDataRealm(query, stdTypeId,
                    "teachTask.teachClass.stdType.id", request);
            query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
            addCollection(request, "courses", utilService.search(query));
        }
        return forward(request);
    }
    
    /**
     * 教学任务内课程统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward taskCourseHome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery yearQuery = new EntityQuery(TeachCalendar.class, "calendar");
        yearQuery.setSelect("distinct calendar.year");
        request.setAttribute("yearList", utilService.search(yearQuery));
        
        EntityQuery termQuery = new EntityQuery(TeachCalendar.class, "calendar");
        termQuery.setSelect("distinct calendar.term");
        request.setAttribute("termList", utilService.search(termQuery));
        
        setDataRealm(request, hasStdTypeCollege);
        
        request.setAttribute("courseTypeList", baseCodeService.getCodes(CourseType.class));
        return forward(request);
    }
    
    /**
     * 教学任务内课程统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward taskCourseStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        EntityQuery query = new EntityQuery(TeachTask.class, "teachTask");
        populateConditions(request, query, "teachTask.teachClass.stdType.id");
        Long stdTypeId = getLong(request, "teachTask.teachClass.stdType.id");
        String yearStart = request.getParameter("beginYear");
        String termStart = request.getParameter("beginTerm");
        String yearEnd = request.getParameter("endYear");
        String termEnd = request.getParameter("endTerm");
        List conditionCalendars = new ArrayList();
        TeachCalendar startCalendar = teachCalendarService.getTeachCalendar(stdTypeId, yearStart,
                termStart);
        TeachCalendar endCalendar = teachCalendarService.getTeachCalendar(stdTypeId, yearEnd,
                termEnd);
        if (null == startCalendar || null == endCalendar) {
            addCollection(request, "courses", Page.EMPTY_PAGE);
        } else {
            while (true) {
                if (startCalendar.after(endCalendar)) {
                    break;
                }
                conditionCalendars.add(startCalendar.getId());
                startCalendar = startCalendar.getNext();
            }
            query.add(new Condition("teachTask.calendar.id in (:conditionCalendars)",
                    conditionCalendars));
            query.setSelect("teachTask.teachClass.depart.id,count(distinct teachTask.course.code)");
            DataRealm dataRealm = getDataRealm(request);
            if (null != dataRealm) {
                DataRealmUtils.addDataRealm(query, new String[] {
                        "teachTask.teachClass.stdType.id", "teachTask.teachClass.depart.id" },
                        dataRealm);
            }
            query.groupBy("teachTask.teachClass.depart.id");
            restrictionHelper.addStdTypeTreeDataRealm(query, stdTypeId,
                    "teachTask.teachClass.stdType.id", request);
            List courses = (List) utilService.search(query);
            new StatHelper(utilService).replaceIdWith(courses, new Class[] { Department.class });
            request.setAttribute("courses", courses);
        }
        return forward(request);
    }
    
    public void setTeachPlanService(TeachPlanService teachPlanService) {
        this.teachPlanService = teachPlanService;
    }
    
}