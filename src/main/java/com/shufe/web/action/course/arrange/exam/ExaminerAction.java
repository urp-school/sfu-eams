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
 * chaostone            2006-11-12          Created
 * zq                   2007-10-16          在examActivityList()方法中，更正了大
 *                                          类查询；
 ********************************************************************************/

package com.shufe.web.action.course.arrange.exam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ekingstar.commons.bean.comparators.ChainComparator;
import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.PropertyExtractor;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.course.arrange.exam.MonitorNotice;
import com.shufe.model.course.arrange.exam.MonitorTeacher;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.exam.ExamActivityPropertyExtractor;

/**
 * 设置监考老师
 * 
 * @author chaostone
 */
public class ExaminerAction extends ExamAction {
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return super.index(mapping, form, request, response);
    }
    
    /**
     * 查询排考结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward examActivityList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery entityQuery = new EntityQuery(ExamActivity.class, "activity");
        populateConditions(request, entityQuery,
                "calendar.studentType.id,task.teachClass.stdType.id");
        Long examTypeId = getLong(request, "examType.id");
        entityQuery.add(new Condition("activity.examType.id=:examTypeId", examTypeId));
        entityQuery.join("activity.task", "task");
        entityQuery.getConditions().addAll(
                QueryRequestSupport.extractConditions(request, TeachTask.class, "task",
                        "task.teachClass.stdType.id"));
        String adminClassName = get(request, "teachClass.adminClass.name");
        if (StringUtils.isNotEmpty(adminClassName)) {
            entityQuery.join("task.teachClass.adminClasses", "adminClass");
            entityQuery.add(new Condition(" adminClass.name like :adminClassName", adminClassName));
        }
        Long stdTypeId = getLong(request, "task.teachClass.stdType.id");
        if (stdTypeId == null) {
            stdTypeId = getLong(request, "calendar.studentType.id");
        }
        Collection stdTypes = null;
        if (null != stdTypeId && stdTypeId.intValue() != 0) {
            stdTypes = getStdTypesOf(stdTypeId, request);
        } else {
            stdTypes = getStdTypes(request);
        }
        entityQuery.add(new Condition("task.teachClass.stdType in (:stdTypes)", stdTypes));
        List departs = getDeparts(request);
        Condition departCondition = new Condition("task.arrangeInfo.teachDepart in(:departs)"
                + " or activity.examMonitor.depart in(:departs)", departs);
        entityQuery.add(departCondition);
        entityQuery.add(new Condition("activity.calendar.id=" + getLong(request, "calendar.id")));
        Long groupId = getLong(request, "examGroup.id");
        if (null != groupId) {
            entityQuery.join("task.arrangeInfo.examGroups", "examGroup");
            entityQuery.add(new Condition("examGroup.id=:examGroupId", groupId));
        }
        entityQuery.setLimit(getPageLimit(request));
        String orderBy = get(request, "orderBy");
        if (StringUtils.isEmpty(orderBy)) {
            orderBy = "activity.time.startTime desc,activity.time.year desc,activity.time.validWeeksNum,activity.time.weekId desc,activity.time.startTime desc";
        }
        entityQuery.addOrder(OrderUtils.parser(orderBy));
        addCollection(request, "examActivities", utilService.search(entityQuery));
        return forward(request);
    }
    
    /**
     * 查询排考结果--考场(地点时间)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward arrangeRoomTimeList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long examTypeId = getLong(request, "examType.id");
        Long calendarId = getLong(request, "calendar.id");
        EntityQuery query = new EntityQuery(ExamActivity.class, "activity");
        query.setSelect("select distinct activity.room,activity.time");
        query.add(new Condition("activity.examType.id=:examTypeId", examTypeId));
        query.add(new Condition(" activity.calendar.id=:calendarId", calendarId));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "examActivities", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 设置主考老师
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchSetExaminers(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String examActivityIds = request.getParameter("examActivityIds");
        if (StringUtils.isEmpty(examActivityIds))
            return forward(mapping, request, "error.model.id.needed", "error");
        
        // 按照查询条件进行排序
        EntityQuery query = new EntityQuery(ExamActivity.class, "activity");
        query.add(new Condition("activity.id in (:ids)", SeqStringUtil
                .transformToLong(examActivityIds)));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        List examActivities = (List) utilService.search(query);
        
        List departmentList = getDeparts(request);
        List rsActivities = new ArrayList();
        for (Iterator iter = examActivities.iterator(); iter.hasNext();) {
            ExamActivity activity = (ExamActivity) iter.next();
            // 判断主考院系权限
            if (departmentList.contains(activity.getTask().getArrangeInfo().getTeachDepart())) {
                rsActivities.add(activity);
            }
        }
        request.setAttribute("examActivities", rsActivities);
        return forward(request);
    }
    
    /**
     * 依据授课教师,设置主考教师
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward setExaminerByTeacher(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String examActivityIds = request.getParameter("examActivityIds");
        if (StringUtils.isEmpty(examActivityIds))
            return forward(mapping, request, "error.model.id.needed", "error");
        List departmentList = getDeparts(request);
        List examActivities = utilService.load(ExamActivity.class, "id", SeqStringUtil
                .transformToLong(examActivityIds));
        for (Iterator iter = examActivities.iterator(); iter.hasNext();) {
            ExamActivity activity = (ExamActivity) iter.next();
            // 判断是否是主考院系
            if (departmentList.contains(activity.getTask().getArrangeInfo().getTeachDepart())) {
                if (!activity.getTask().getArrangeInfo().getTeachers().isEmpty()) {
                    activity.getExamMonitor().setExaminer(
                            (Teacher) activity.getTask().getArrangeInfo().getTeachers().iterator()
                                    .next());
                }
            }
        }
        utilService.saveOrUpdate(examActivities);
        return redirect(request, "examActivityList", "info.set.success");
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveExaminers(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] activityIds = request.getParameterValues("examActivityId");
        Collection activities = utilService.load(ExamActivity.class, "id", SeqStringUtil
                .transformToLong(activityIds));
        
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            ExamActivity activity = (ExamActivity) iter.next();
            Long teacherId = getLong(request, "teacherId" + activity.getId());
            if (ValidEntityKeyPredicate.INSTANCE.evaluate(teacherId)) {
                activity.getExamMonitor().setExaminer(new Teacher(teacherId));
            } else {
                activity.getExamMonitor().setExaminer(null);
            }
            String examinerNames = request.getParameter("examinerName" + activity.getId());
            activity.getExamMonitor().setExaminerName(examinerNames);
        }
        utilService.saveOrUpdate(activities);
        // 提示成功信息
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.save.success"));
        saveErrors(request, messages);
        return mapping.findForward("actionResult");
    }
    
    /**
     * 设置监考老师
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchSetInvigilators(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String examActivityIds = request.getParameter("examActivityIds");
        if (StringUtils.isEmpty(examActivityIds))
            return forward(mapping, request, "error.model.id.needed", "error");
        // 按照指定的排序进行排列
        EntityQuery query = new EntityQuery(ExamActivity.class, "activity");
        query.add(new Condition("activity.id in (:ids)", SeqStringUtil
                .transformToLong(examActivityIds)));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        List examActivities = (List) utilService.search(query);
        
        List departmentList = getDeparts(request);
        // 获得每个教学任务上课教师所在的部门<br>
        // 没有上课教师的则将用户权限内的第一个部门放在map中<br>
        Map departMap = new HashMap();
        List rsActivities = new ArrayList();
        for (Iterator iter = examActivities.iterator(); iter.hasNext();) {
            ExamActivity activity = (ExamActivity) iter.next();
            // 判断是否是监考院系
            if (departmentList.contains(activity.getExamMonitor().getDepart())) {
                rsActivities.add(activity);
                if (null != activity.getExamMonitor().getInvigilator()) {
                    departMap.put(activity.getId().toString(), activity.getExamMonitor()
                            .getInvigilator().getDepartment());
                } else if (null == activity.getExamMonitor().getDepart()) {
                    departMap.put(activity.getId().toString(), activity.getTask().getTeachClass()
                            .getDepart());
                } else {
                    departMap.put(activity.getId().toString(), activity.getExamMonitor()
                            .getDepart());
                }
            }
        }
        request.setAttribute("departMap", departMap);
        request.setAttribute("examActivities", rsActivities);
        // 按照日期进行升序排列
        request.setAttribute(Constants.DEPARTMENT_LIST, departmentList);
        if("depart".equals(get(request, "type"))){
        	return forward(request, "batchSetIDepart");
        }
        return forward(request);
    }
    
    /**
     * 保存监考老师和监考院系设置.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveInvigilators(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] activityIds = request.getParameterValues("examActivityId");
        Collection activities = utilService.load(ExamActivity.class, "id", SeqStringUtil
                .transformToLong(activityIds));
        boolean depart=false;
        if("depart".equals(get(request, "type"))){
        	depart=true;
        }
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            ExamActivity activity = (ExamActivity) iter.next();
            if(depart){
            	Long departId = getLong(request, "departmentId" + activity.getId());
            	if (ValidEntityKeyPredicate.INSTANCE.evaluate(departId)) {
            		activity.getExamMonitor().setDepart(new Department(departId));
            	} else {
            		activity.getExamMonitor().setDepart(null);
            	}
            }else{
            	Long teacherId = getLong(request, "teacherId" + activity.getId());
            	if (ValidEntityKeyPredicate.INSTANCE.evaluate(teacherId)) {
            		activity.getExamMonitor().setInvigilator(new Teacher(teacherId));
            	} else {
            		activity.getExamMonitor().setInvigilator(null);
            	}
            	String invigilatorNames = request.getParameter("invigilatorName" + activity.getId());
            	activity.getExamMonitor().setInvigilatorName(invigilatorNames);
            }
        }
        utilService.saveOrUpdate(activities);
        // 提示成功信息
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.save.success"));
        saveErrors(request, messages);
        return mapping.findForward("actionResult");
    }
    /**
     * 设置主考老师
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchSetExaminers1(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
         String examActivityIds = request.getParameter("examActivityIds");
        if (StringUtils.isEmpty(examActivityIds))
            return forward(mapping, request, "error.model.id.needed", "error");
        // 按照指定的排序进行排列
        EntityQuery query = new EntityQuery(ExamActivity.class, "activity");
        query.add(new Condition("activity.id in (:ids)", SeqStringUtil
                .transformToLong(examActivityIds)));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        List examActivities = (List) utilService.search(query);
        
        List departmentList = getDeparts(request);
        // 获得每个教学任务上课教师所在的部门<br>
        // 没有上课教师的则将用户权限内的第一个部门放在map中<br>
        Map departMap = new HashMap();
        List rsActivities = new ArrayList();
        for (Iterator iter = examActivities.iterator(); iter.hasNext();) {
            ExamActivity activity = (ExamActivity) iter.next();
            // 判断是否是主考院系
            if (departmentList.contains(activity.getExamMonitor().getDepart())) {
                rsActivities.add(activity);
                if (null != activity.getTeacher()) {
                    departMap.put(activity.getId().toString(), activity.getTeacher().getDepartment());
                } else if (null == activity.getDepartment()) {
                    departMap.put(activity.getId().toString(), activity.getTask().getArrangeInfo().getTeachDepart());
                } else {
                    departMap.put(activity.getId().toString(), activity.getDepartment());
                }
            }
        }
        request.setAttribute("departMap", departMap);
        request.setAttribute("examActivities", rsActivities);
        // 按照日期进行升序排列
        request.setAttribute(Constants.DEPARTMENT_LIST, departmentList);
        if("depart".equals(get(request, "type"))){
        	return forward(request, "batchSetEDepart");
        }
        return forward(request);
    }
    public ActionForward saveExaminers1(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] activityIds = request.getParameterValues("examActivityId");
        Collection activities = utilService.load(ExamActivity.class, "id", SeqStringUtil
                .transformToLong(activityIds));
        boolean depart=false;
        if("depart".equals(get(request, "type"))){
        	depart=true;
        }
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            ExamActivity activity = (ExamActivity) iter.next();
            if(depart){
            	Long departId = getLong(request, "departmentId" + activity.getId());
            	if (ValidEntityKeyPredicate.INSTANCE.evaluate(departId)) {
            		activity.setDepartment(new Department(departId));
            	} else {
            		activity.setDepartment(null);
            	}
            }else{
            	Long teacherId = getLong(request, "teacherId" + activity.getId());
            	if (ValidEntityKeyPredicate.INSTANCE.evaluate(teacherId)) {
            		activity.getExamMonitor().setExaminer(new Teacher(teacherId));
            	} else {
            		activity.getExamMonitor().setExaminer(null);
            	}
            	String examinerNames = request.getParameter("examinerName" + activity.getId());
            	activity.getExamMonitor().setExaminerName(examinerNames);
            }
        }
        utilService.saveOrUpdate(activities);
        // 提示成功信息
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.save.success"));
        saveErrors(request, messages);
        return mapping.findForward("actionResult");
    }
    
    /**
     * 座位表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward seatReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action(ExamArrangeAction.class, "seatReport"));
    }
    
    /**
     * 座位表-按考场打印 补考用
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward seatReportByClassroom(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action(ExamArrangeAction.class, "seatReportByClassroom"));
    }
    
    /**
     * 监考通知
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward examinerReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List examActivities = (List) getExportDatas(request);
        List notices = MonitorNotice
                .builidNotices(examActivities, new HashSet(getDeparts(request)));
        addCollection(request, "notices", notices);
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id"));
        request.setAttribute("calendar", calendar);
        return forward(request);
    }
    
    /**
     * 打印试卷带标签
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward printPaperLabel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List examActivities = (List) getExportDatas(request);
        List orders = OrderUtils.parser(request.getParameter("orderBy"));
        
        if (!orders.isEmpty()) {
            ChainComparator comparator = new ChainComparator();
            for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
                Order order = (Order) iterator.next();
                comparator.addComparator(new PropertyComparator(StringUtils.replace(order
                        .getProperty(), "activity.", ""), (order.getDirection() == Order.ASC)));
            }
            Collections.sort(examActivities, comparator);
        }
        addCollection(request, "examActivities", examActivities);
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id"));
        request.setAttribute("calendar", calendar);
        // 每个考场多加的试卷份数
        Integer extraCount = getInteger(request, "extraCount");
        if (null == extraCount)
            extraCount = new Integer(0);
        request.setAttribute("extraCount", extraCount);
        // 每页的标签数目
        request.setAttribute("labelCount", getInteger(request, "labelCount"));
        return forward(request);
    }
    
    /**
     * 设置监考老师打印通知
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward examinerReportSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    /**
     * 学年监考统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward examinerStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "calendar.id");
        Long examTypeId = getLong(request, "examType.id");
        String examTeacherType = get(request, "examTeacherType");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
        
        Set monitorTeachers = new HashSet();
        
        // 主考教师
        EntityQuery query = new EntityQuery(ExamActivity.class, "examActivity");
        query.add(new Condition("examActivity.calendar.id = (:calendarId)", calendarId));
        if (StringUtils.equals("teacher", examTeacherType)) {
            query
                    .add(new Condition(
                            "task.teachClass.stdType.id in (:stdTypeIds) and task.arrangeInfo.teachDepart.id in(:departIds)",
                            SeqStringUtil.transformToLong(getStdTypeIdSeq(request)), SeqStringUtil
                                    .transformToLong(getDepartmentIdSeq(request))));
        } else if (StringUtils.equals("examMonitor", examTeacherType)) {
            query
                    .add(new Condition(
                            "task.teachClass.stdType.id in (:stdTypeIds) and examActivity.examMonitor.depart.id in(:departIds)",
                            SeqStringUtil.transformToLong(getStdTypeIdSeq(request)), SeqStringUtil
                                    .transformToLong(getDepartmentIdSeq(request))));
        }else {
        	query
            .add(new Condition(
                    "task.teachClass.stdType.id in (:stdTypeIds) and examActivity.examMonitor.depart.id in(:departIds) and task.arrangeInfo.teachDepart.id in(:departIds)",
                    SeqStringUtil.transformToLong(getStdTypeIdSeq(request)), SeqStringUtil
                            .transformToLong(getDepartmentIdSeq(request))));
        }
        List examActivityList = (List) utilService.search(query);
        for (Iterator it1 = examActivityList.iterator(); it1.hasNext();) {
            ExamActivity examActivity = (ExamActivity) it1.next();
            if (StringUtils.equals("teacher", examTeacherType)) {
                if (null != examActivity.getTeacher()) {
                    MonitorTeacher examinorTeacher = new MonitorTeacher();
                    examinorTeacher.setTeacher(examActivity.getTeacher());
                    examinorTeacher.setTask(examActivity.getTask());
                    examinorTeacher.setRoom(examActivity.getRoom());
                    monitorTeachers.add(examinorTeacher);
                }
            }
            if (StringUtils.equals("examMonitor", examTeacherType)) {
                if (null != examActivity.getExamMonitor()
                        && null != examActivity.getExamMonitor().getInvigilator()
                        && null != examActivity.getExamMonitor().getInvigilator().getId()) {
                    MonitorTeacher monitorTeacher = new MonitorTeacher();
                    monitorTeacher.setTeacher(examActivity.getExamMonitor().getInvigilator());
                    monitorTeacher.setTask(examActivity.getTask());
                    monitorTeacher.setRoom(examActivity.getRoom());
                    monitorTeachers.add(monitorTeacher);
                }
            }
        }
        List monitorTeacherList = new ArrayList(monitorTeachers);
        Collections.sort(monitorTeacherList, new PropertyComparator("teacher.code"));
        request.setAttribute("examActivities", monitorTeacherList);
        request.setAttribute("examActivitiesAll", examActivityList);
        request.setAttribute("calendar", calendar);
        request.setAttribute("examType", utilService.load(ExamType.class, examTypeId));
        return forward(request);
    }
    
    /**
     * 监考管理中的权限主要控制<br>
     * 主考院系和排考结果中的监考院系
     */
    protected Condition getAuthorityCondition(Collection stdTypes, Collection departs) {
        return new Condition(
                "task.teachClass.stdType in (:stdTypes) and (examActivity.examMonitor.depart in(:departs) or task.arrangeInfo.teachDepart in(:departs))",
                stdTypes, departs);
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        Long[] ids = SeqStringUtil.transformToLong(get(request, "examActivityIds"));
        EntityQuery query = new EntityQuery(ExamActivity.class, "activity");
        query.add(new Condition("activity.id in (:ids)", ids));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        return utilService.search(query);
    }
    
    protected PropertyExtractor getPropertyExtractor(HttpServletRequest request) {
        ExamActivityPropertyExtractor exporter = new ExamActivityPropertyExtractor(utilService,
                (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),
                getResources(request));
        return exporter;
    }
    
}
