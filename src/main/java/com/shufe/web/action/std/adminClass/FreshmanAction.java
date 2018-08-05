//$Id: InstructorStdAction.java,v 1.1 2008-1-23 上午10:37:36 zhouqi Exp $
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
 * zhouqi              2008-1-23         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.adminClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.TimeUnitUtil;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.dao.system.calendar.TermCalculator;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.arrange.task.CourseTable;
import com.shufe.model.course.arrange.task.CourseTableSetting;
import com.shufe.model.course.arrange.task.MultiCourseTable;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.calendar.OnCampusTime;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.resource.TeachResourceService;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.system.baseinfo.AdminClassService;
import com.shufe.service.system.baseinfo.SpecialityService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.web.action.common.DispatchBasicAction;
import com.shufe.web.action.course.arrange.task.TableTaskGroup;

/**
 * 学生 教学信息查看
 * 
 * @author lzs
 */
public class FreshmanAction extends DispatchBasicAction {
    
	protected TeachCalendarService teachCalendarService;
	
	protected TeachPlanService teachPlanService;
	
	protected AdminClassService adminClassService;
	
	protected TeachTaskService teachTaskService;
	
	protected TeachResourceService teachResourceService;
	
	protected SpecialityService specialityService;

	public void setSpecialityService(SpecialityService specialityService) {
		this.specialityService = specialityService;
	}

	/**
     * 进入页面
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
        return forward(request);
    }
    
    /**
     * 课程表
     * 
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @return
     * @throws Exception
     */
    public ActionForward courseTable(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String adminClassCode = request.getParameter("adminClassCode");
		AdminClass adminClass = adminClassService.getAdminClass(adminClassCode);
		if (null == adminClass){
			return forwardError(mapping, request, "adminClass.notExists");
		}
    	// 查找课表设置参数
        TeachCalendar calendar = teachCalendarService.getNextTeachCalendar(adminClass.getStdType());
        if (calendar == null)
            return forwardError(mapping, request, "error.calendar.id.notExists");
        CourseTableSetting setting = new CourseTableSetting(calendar);
        setting.setTimes(getTimesFormPage(request, calendar));
        populate(request, setting, "setting");
        setting.setKind("class");
        // 查找课表对象
        String ids = adminClass.getId().toString();
        if (StringUtils.isEmpty(ids)) {
            request.setAttribute("prompt", "common.lessOneSelectPlease");
            return forward(request, "prompt");
        }
        EntityQuery entityQuery = new EntityQuery(CourseTable.getResourceClass(setting.getKind()),
                "resource");
        entityQuery.add(new Condition("instr('," + ids + ",', ','||resource.id||',')>0"));
        List resources = (List) utilService.search(entityQuery);
        List orders = OrderUtils.parser(request.getParameter("setting.orderBy"));
        if (!orders.isEmpty()) {
            Order order = (Order) orders.get(0);
            Collections.sort(resources, new PropertyComparator(order.getProperty(), order
                    .getDirection() == Order.ASC));
        }
        // 组装课表，区分单个课表和每页多个课表两种情况
        List courseTableList = new ArrayList();
        if (setting.getTablePerPage() == 1) {
            for (Iterator reourcesIter = resources.iterator(); reourcesIter.hasNext();) {
                Entity resource = (Entity) reourcesIter.next();
                courseTableList.add(buildCourseTable(setting, resource));
            }
        } else {
            int i = 0;
            MultiCourseTable multiTable = null;
            for (Iterator reourcesIter = resources.iterator(); reourcesIter.hasNext();) {
                if (i % setting.getTablePerPage() == 0) {
                    multiTable = new MultiCourseTable();
                    courseTableList.add(multiTable);
                }
                Entity resource = (Entity) reourcesIter.next();
                multiTable.getResources().add(resource);
                multiTable.getTables().add(buildCourseTable(setting, resource));
                i++;
            }
        }
        setting.setWeekInfos(WeekInfo.WEEKS);
        setting.setDisplayCalendarTime(true);
        request.setAttribute("courseTableList", courseTableList);
        request.setAttribute("setting", setting);
        request.setAttribute("calendar", calendar);
        if (1 == setting.getTablePerPage()) {
            return forward(request);
        } else {
            return null;
        }
    }
    
    /**
     * 构建一个课程表
     * 
     * @param setting
     * @param resource
     * @return
     */
    private CourseTable buildCourseTable(CourseTableSetting setting, Entity resource) {
        CourseTable table = new CourseTable(resource, setting.getKind());
        List taskList = null;
        if (CourseTable.CLASS.equals(setting.getKind())) {
            for (int j = 0; j < setting.getTimes().length; j++) {
                table.addActivities(teachResourceService.getAdminClassActivities(resource
                        .getEntityId(), setting.getTimes()[j], CourseActivity.class));
            }
            if (setting.getIgnoreTask())
                return table;
            Map taskGroupMap = new HashMap();
            // group by course with PlanCourse
            Map courseTypeMap = new HashMap();
            // 查询培养计划
            try {
                AdminClass adminClass = (AdminClass) resource;
                TeachPlan plan = teachPlanService.getTeachPlan(adminClass);
                OnCampusTime time = teachCalendarService.getOnCampusTime(adminClass.getStdType(),
                        adminClass.getEnrollYear());
                TermCalculator calc = new TermCalculator(teachCalendarService, setting
                        .getCalendar());
                int term = calc.getTermBetween(time.getEnrollCalendar(), setting.getCalendar(),
                        Boolean.TRUE);
                
                if (plan != null) {
                    for (Iterator iter = plan.getCourseGroups().iterator(); iter.hasNext();) {
                        CourseGroup group = (CourseGroup) iter.next();
                        String typeId = group.getCourseType().getId().toString();
                        for (Iterator it2 = group.getPlanCourses(String.valueOf(term)).iterator(); it2
                                .hasNext();) {
                            PlanCourse planCourse = (PlanCourse) it2.next();
                            courseTypeMap.put(planCourse.getCourse(), typeId);
                        }
                        TableTaskGroup taskGroup = (TableTaskGroup) taskGroupMap.get(typeId);
                        String[] credits = StringUtils.split(group.getCreditPerTerms(), ",");
                        if (!credits[term - 1].equals("0")) {
                            if (null == taskGroup) {
                                taskGroup = new TableTaskGroup(group.getCourseType());
                                taskGroupMap.put(typeId, taskGroup);
                            }
                            taskGroup.setCredit(Float.valueOf(credits[term - 1]));
                        }
                    }
                }
            } catch (Exception e) {
                log.debug("maybe some studentType with no plan,or on campusTime");
            }
            // 查询教学任务
            taskList = teachTaskService.getTeachTasksByCategory(resource.getEntityId(),
                    TeachTaskFilterCategory.ADMINCLASS, setting.getCalendar());
            // 不显示重复课程代码的课程
            Set courses = new HashSet();
            for (Iterator iter = taskList.iterator(); iter.hasNext();) {
                TeachTask task = (TeachTask) iter.next();
                if (!courses.contains(task.getCourse())) {
                    courses.add(task.getCourse());
                } else {
                    continue;
                }
                
                String typeId = task.getCourseType().getId().toString();
                if (MapUtils.isNotEmpty(courseTypeMap)
                        && null != courseTypeMap.get(task.getCourse())) {
                    typeId = (String) courseTypeMap.get(task.getCourse());
                }
                
                TableTaskGroup group = (TableTaskGroup) taskGroupMap.get(typeId);
                if (null == group) {
                    group = new TableTaskGroup(task.getCourseType());
                    taskGroupMap.put(typeId, group);
                }
                group.addTask(task);
            }
            taskList = new ArrayList();
            float credits = 0;
            for (Iterator iter = taskGroupMap.values().iterator(); iter.hasNext();) {
                TableTaskGroup taskGroup = (TableTaskGroup) iter.next();
                taskList.addAll(taskGroup.getTasks());
                credits += taskGroup.getCredit().floatValue();
            }
            table.setCredits(new Float(credits));
            List dd = new ArrayList(taskGroupMap.values());
            Collections.sort(dd);
            table.setTasksGroups(dd);
        } else if (CourseTable.TEACHER.equals(setting.getKind())) {
            for (int j = 0; j < setting.getTimes().length; j++) {
                table.addActivities(teachResourceService.getTeacherActivities(resource
                        .getEntityId(), setting.getTimes()[j], CourseActivity.class, setting
                        .getForCalendar() ? setting.getCalendar() : null));
            }
            if (setting.getIgnoreTask())
                return table;
            if (setting.getForCalendar()) {
                taskList = teachTaskService.getTeachTasksByCategory(resource.getEntityId(),
                        TeachTaskFilterCategory.TEACHER, setting.getCalendar());
            } else {
                taskList = teachTaskService.getTeachTasksByCategory(resource.getEntityId(),
                        TeachTaskFilterCategory.TEACHER, teachCalendarService
                                .getTeachCalendarsOfOverlapped(setting.getCalendar()));
            }
        } else if (CourseTable.STD.equals(setting.getKind())) {
            for (int j = 0; j < setting.getTimes().length; j++) {
                table.addActivities(teachResourceService.getStdActivities((Long) resource
                        .getEntityId(), setting.getTimes()[j], CourseActivity.class, setting
                        .getForCalendar() ? setting.getCalendar() : null));
            }
            if (setting.getIgnoreTask())
                return table;
            if (setting.getForCalendar()) {
                taskList = teachTaskService.getTeachTasksByCategory(resource.getEntityId(),
                        TeachTaskFilterCategory.STD, setting.getCalendar());
            } else {
                taskList = teachTaskService.getTeachTasksByCategory(resource.getEntityId(),
                        TeachTaskFilterCategory.STD, teachCalendarService
                                .getTeachCalendarsOfOverlapped(setting.getCalendar()));
            }
        } else if (CourseTable.ROOM.equals(setting.getKind())) {
            for (int j = 0; j < setting.getTimes().length; j++) {
                table.addActivities(teachResourceService.getRoomActivities(resource.getEntityId(),
                        setting.getTimes()[j], CourseActivity.class,
                        setting.getForCalendar() ? setting.getCalendar() : null));
            }
            if (setting.getIgnoreTask())
                return table;
            // 教室的教学任务列表从教学活动中抽取
            table.extractTaskFromActivity();
        }
        if (null == table.getTasks())
            table.setTasks(taskList);
        
        return table;
    }
    
    /**
     * 如果课程表要扩展到支持每周的课表显示， 则可以用次函数和resoureceService服务 查询、显示教学活动.
     * 
     * @param request
     * @param calendar
     * @return
     */
    private TimeUnit[] getTimesFormPage(HttpServletRequest request, TeachCalendar calendar) {
        Integer startWeek = getInteger(request, "startWeek");
        Integer endWeek = getInteger(request, "endWeek");
        if (null == startWeek)
            startWeek = new Integer(1);
        if (null == endWeek)
            endWeek = calendar.getWeeks();
        if (startWeek.intValue() < 1)
            startWeek = new Integer(1);
        if (endWeek.intValue() > calendar.getWeeks().intValue())
            endWeek = calendar.getWeeks();
        request.setAttribute("startWeek", startWeek);
        request.setAttribute("endWeek", endWeek);
        return TimeUnitUtil.buildTimeUnits(calendar.getStartYear(), calendar.getWeekStart()
                .intValue(), startWeek.intValue(), endWeek.intValue(), TimeUnit.CONTINUELY);
    }
    
    /**
	 * 新生查看 所在班级 培养计划
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward stdPlanInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String specialityCode = request.getParameter("specialityCode");
		String stdTypeCode = request.getParameter("stdTypeCode");
		String enrollTurn = request.getParameter("enrollTurn");
		Speciality speciality = specialityService.getSpeciality(specialityCode);
		if (null == speciality){
			return forwardError(mapping, request, "speciality.notExists");
		}
		request.setAttribute("teachPlanList", teachPlanService.getTeachPlan(speciality, stdTypeCode , enrollTurn));
		request.setAttribute("school", getSystemConfig().getSchool());
		return forward(request);
	}

	public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
		this.teachCalendarService = teachCalendarService;
	}

	public void setTeachPlanService(TeachPlanService teachPlanService) {
		this.teachPlanService = teachPlanService;
	}

	public void setAdminClassService(AdminClassService adminClassService) {
		this.adminClassService = adminClassService;
	}
	
    public void setTeachTaskService(TeachTaskService teachTaskService) {
		this.teachTaskService = teachTaskService;
	}

	public void setTeachResourceService(TeachResourceService teachResourceService) {
		this.teachResourceService = teachResourceService;
	}
}
