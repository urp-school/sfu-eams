//$Id: TeachCalendarAction.java,v 1.11 2006/12/30 01:29:01 duanth Exp $
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
 * chaostone             2005-9-13         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.TeachCalendarScheme;
import com.shufe.model.Constants;
import com.shufe.model.course.grade.GradeLog;
import com.shufe.model.system.calendar.OnCampusTime;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.calendar.TimeSetting;
import com.shufe.service.course.arrange.TaskActivityService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 学生教学日历和入学日历的界面响应类
 * 
 * @author chaostone 2005-12-16
 */
public class TeachCalendarAction extends RestrictionSupportAction {
    
    protected TeachCalendarService calendarService;
    
    protected TaskActivityService taskActivityService;
    
    /**
     * 日历管理主界面
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
        setDataRealm(request, hasStdType);
        addCollection(request, "schemes", utilService.loadAll(TeachCalendarScheme.class));
        return forward(request);
    }
    
    /**
     * 方案信息管理主界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward schemeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long schemeId = getLong(request, "scheme.id");
        request.setAttribute("scheme", utilService.get(TeachCalendarScheme.class, schemeId));
        return forward(request);
    }
    
    /**
     * 列举教学日历
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward calendarList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long schemeId = getLong(request, "scheme.id");
        if (null == schemeId)
            return forward(mapping, request, "error.stdType.id.needed", "error");
        
        EntityQuery query = new EntityQuery(TeachCalendar.class, "calendar");
        query.add(new Condition("calendar.scheme.id=:schemeId", schemeId));
        query.addOrder(OrderUtils.parser(request.getParameter(Order.ORDER_STR)));
        query.setLimit(getPageLimit(request));
        addCollection(request, "calendars", utilService.search(query));
        request.setAttribute("scheme", utilService.get(TeachCalendarScheme.class, schemeId));
        return forward(request);
    }
    
    /**
     * 查看学生的在校日历
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward onCampusTimeList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stdTypeId = request.getParameter(Constants.STUDENTTYPE_KEY);
        if (StringUtils.isEmpty(stdTypeId))
            return forward(mapping, request, "error.stdType.id.needed", "error");
        
        StudentType stdType = studentTypeService.getStudentType(Long.valueOf(stdTypeId));
        if (null == stdType)
            return forward(mapping, request, "error.stdType.notExists");
        
        Collection onCampusTimes = calendarService.getOnCampusTimesFor(stdType,
                getPageLimit(request));
        // 如果没有对应的在校日历，则返回页面提示用户，该学生类别使用拥有教学日历的最近上级类别的入学日历.
        // if (onCampusTimes.isEmpty() && stdType.getTeachCalendars().isEmpty()) {
        // StudentType superType = stdType;
        // while (null != superType && superType.getTeachCalendars().isEmpty())
        // superType = (StudentType)superType.getSuperType();
        // if (null == stdType)
        // return forward(mapping, request, "error.stdType.noCorresponsiveCalendar", "error");
        // else
        // request.setAttribute("superType", superType);
        // }
        request.setAttribute(Constants.STUDENTTYPE, stdType);
        addCollection(request, "onCampusTimes", onCampusTimes);
        return forward(request);
        
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editCalendar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, Constants.CALENDAR_KEY);
        Long schemeId = getLong(request, "scheme.id");
        TeachCalendarScheme scheme = (TeachCalendarScheme) utilService.get(
                TeachCalendarScheme.class, schemeId);
        TeachCalendar calendar = null;
        if (null == calendarId) {
            calendar = (TeachCalendar) RequestUtil.populate(request, TeachCalendar.class,
                    Constants.CALENDAR);
            calendar.setStudentType((StudentType) scheme.getStdTypes().iterator().next());
            // 获取新增教学日历中是否有前后日立的参数
            Long nextCalendarId = getLong(request, "nextCalendar.id");
            Long preCalendarId = getLong(request, "preCalendar.id");
            TeachCalendar preCalendar = null, nextCalendar = null;
            if (null != nextCalendarId) {
                nextCalendar = calendarService.getTeachCalendar(nextCalendarId);
                calendar.setNext(nextCalendar);
            }
            if (null != preCalendarId) {
                preCalendar = calendarService.getTeachCalendar(preCalendarId);
                calendar.setPrevious(preCalendar);
            }
        } else {
            calendar = calendarService.getTeachCalendar(calendarId);
        }
        request.setAttribute("scheme", scheme);
        request.setAttribute(Constants.CALENDAR, calendar);
        request.setAttribute("timeSettingList", utilService.loadAll(TimeSetting.class));
        return forward(request);
    }
    
    /**
     * 教学日历方案
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editSchemeForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long schemeId = getLong(request, "scheme.id");
        Collection studentTypes = getStdTypes(request);
        if (null != schemeId) {
            // 得到该方案已经选学生类别
            TeachCalendarScheme scheme = (TeachCalendarScheme) utilService.load(
                    TeachCalendarScheme.class, schemeId);
            request.setAttribute("scheme", scheme);
            studentTypes = CollectionUtils.subtract(studentTypes, scheme.getStdTypes());
        }
        // 过滤已关联方案的学生类别
        Collection schemes = utilService.loadAll(TeachCalendarScheme.class);
        Set schemeStudentTypes = new HashSet();
        for (Iterator it = schemes.iterator(); it.hasNext();) {
            TeachCalendarScheme calendarScheme = (TeachCalendarScheme) it.next();
            schemeStudentTypes.addAll(calendarScheme.getStdTypes());
        }
        studentTypes = CollectionUtils.subtract(studentTypes, schemeStudentTypes);
        addCollection(request, "studentTypes", studentTypes);
        addCollection(request, "timeSettings", utilService.loadAll(TimeSetting.class));
        return forward(request);
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editOnCampusTime(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String onCampusTimeId = request.getParameter(Constants.ONCAMPUSTIME_KEY);
        OnCampusTime time = null;
        if (StringUtils.isEmpty(onCampusTimeId)) {
            time = (OnCampusTime) RequestUtil.populate(request, OnCampusTime.class,
                    Constants.ONCAMPUSTIME);
            time.setStdType(studentTypeService.getStudentType(Long.valueOf(request
                    .getParameter(Constants.STUDENTTYPE_KEY))));
        } else {
            time = (OnCampusTime) utilService
                    .load(OnCampusTime.class, Long.valueOf(onCampusTimeId));
        }
        List stdTypeList = new ArrayList();
        stdTypeList.add(time.getStdType());
        request.setAttribute("stdTypeList", stdTypeList);
        request.setAttribute(Constants.ONCAMPUSTIME, time);
        return forward(request);
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveCalendar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachCalendar calendar = (TeachCalendar) populateEntity(request, TeachCalendar.class,
                Constants.CALENDAR);
        Long schemeId = getLong(request, "scheme.id");
        TeachCalendarScheme scheme = (TeachCalendarScheme) utilService.get(
                TeachCalendarScheme.class, schemeId);
        if (null == calendar.getScheme()) {
            calendar.setScheme(scheme);
        }
        calendar.setStudentType((StudentType) scheme.getStdTypes().iterator().next());
        ActionMessages messages = new ActionMessages();
        if (null == calendar.getDisplayTimeDetail()) {
            calendar.setDisplayTimeDetail(Boolean.FALSE);
        }
        // 持久化，如果失败跳转到失败页面
        try {
            if (!calendar.isPO()) {
                if (calendarService.checkDateCollision(calendar))
                    return forward(mapping, request, "error.calendar.dateCollision", "error");
                Long nextCalendarId = getLong(request, "nextCalendar.id");
                Long preCalendarId = getLong(request, "preCalendar.id");
                TeachCalendar preCalendar = null, nextCalendar = null;
                if (null != nextCalendarId) {
                    nextCalendar = calendarService.getTeachCalendar(nextCalendarId);
                    calendar.setNext(nextCalendar);
                    calendarService.saveTeachCalendarWithNext(calendar, nextCalendar);
                } else if (null != preCalendarId) {
                    preCalendar = calendarService.getTeachCalendar(preCalendarId);
                    calendar.setPrevious(preCalendar);
                    calendarService.saveTeachCalendarWithPrevious(calendar, preCalendar);
                } else {
                    calendarService.saveTeachCalendarWithPrevious(calendar, preCalendar);
                }
            } else {
                if (calendarService.checkDateCollision(calendar))
                    return forward(mapping, request, "error.calendar.dateCollision", "error");
                calendarService.updateTeachCalendar(calendar);
            }
        } catch (PojoExistException e) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("calendar.existed"));
            addErrors(request, messages);
            return forward(mapping, request, "error");
        }
        logHelper.info(request, "add or update calendar");
        return redirect(request, "calendarList", "info.save.success", "&scheme.id="
                + request.getParameter("scheme.id"));
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveOnCampusTime(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        OnCampusTime time = (OnCampusTime) RequestUtil.populate(request, OnCampusTime.class,
                Constants.ONCAMPUSTIME);
        if (!ValidEntityPredicate.INSTANCE.evaluate(time)) {
            time.setId(null);
        }
        
        // 准备成功/失败的消息
        ActionMessages messages = new ActionMessages();
        // 持久化，如果失败跳转到失败页面
        try {
            time.setEnrollCalendar(calendarService.getTeachCalendar(time.getEnrollCalendar()
                    .getStudentType(), time.getEnrollCalendar().getYear(), time.getEnrollCalendar()
                    .getTerm()));
            time.setGraduateCalendar(calendarService.getTeachCalendar(time.getGraduateCalendar()
                    .getStudentType(), time.getGraduateCalendar().getYear(), time
                    .getGraduateCalendar().getTerm()));
            if (!time.isPO()) {
                if (calendarService.checkOnCampusTimeExist(time))
                    return forwardError(mapping, request, new String[] { "entity.onCampusTime",
                            "error.model.existed" });
                utilService.saveOrUpdate(time);
            } else {
                OnCampusTime savedTime = (OnCampusTime) utilService.load(OnCampusTime.class, time
                        .getId());
                EntityUtils.merge(savedTime, time);
                utilService.saveOrUpdate(savedTime);
            }
        } catch (PojoExistException e) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("onCampusTime.existed"));
            addErrors(request, messages);
            return forward(mapping, request, "error");
        }
        return redirect(request, "onCampusTimeList", "info.save.success", "&studentType.id="
                + time.getStdType().getId());
    }
    
    /**
     * 保存教学日历方案
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveScheme(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachCalendarScheme scheme = (TeachCalendarScheme) populateEntity(request,
                TeachCalendarScheme.class, "scheme");
        List stdTypes = studentTypeService.getStudentTypes(get(request, "schemeStudentTypeIds"));
        if (CollectionUtils.isEmpty(scheme.getStdTypes())) {
            scheme.setStdTypes(new HashSet(stdTypes));
        } else {
            scheme.getStdTypes().clear();
            scheme.getStdTypes().addAll(stdTypes);
        }
        utilService.saveOrUpdate(scheme);
        if (null == scheme.getId()) {
            return redirect(request, "index", "info.save.success");
        } else {
            return redirect(request, "index", "info.save.success", "&scheme.id=" + scheme.getId());
        }
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeCalendar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String calendarId = request.getParameter(Constants.CALENDAR_KEY);
        if (StringUtils.isNotEmpty(calendarId)) {
            TeachCalendar calendar = calendarService.getTeachCalendar(Long.valueOf(calendarId));
            try {
                // 与成绩日志脱离关系
                EntityQuery query = new EntityQuery(GradeLog.class, "gradeCatalog");
                query.add(new Condition("gradeCatalog.calendar = :calendar", calendar));
                Collection gradeCatalogs = utilService.search(query);
                for (Iterator it = gradeCatalogs.iterator(); it.hasNext();) {
                    GradeLog gradeCatalog = (GradeLog) it.next();
                    gradeCatalog.setCalendar(null);
                }
                calendarService.removeTeachCalendar(calendar);
                utilService.saveOrUpdate(gradeCatalogs);
            } catch (Exception e) {
                return forward(request, new Action("", "calendarList"),
                        "error.calendar.deletedFailure");
            }
            return redirect(request, "calendarList", "info.save.success", "&studentType.id="
                    + calendar.getStudentType().getId());
        } else {
            return forward(request, new Action("", "calendarList"), "error.parameters.needed");
        }
        
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeOnCampusTime(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String onCampusTimeId = request.getParameter(Constants.ONCAMPUSTIME_KEY);
        if (StringUtils.isNotEmpty(onCampusTimeId)) {
            OnCampusTime time = (OnCampusTime) utilService.load(OnCampusTime.class, Long
                    .valueOf(onCampusTimeId));
            try {
                utilService.remove(time);
            } catch (Exception e) {
                return forward(mapping, request, "error.calendar.deletedFailure", "error");
            }
            return redirect(request, "onCampusTimeList", "info.delete.success", "&studentType.id="
                    + time.getStdType().getId());
        } else {
            return forwardError(mapping, request, "error.parameters.needed");
        }
    }
    
    /**
     * 查看教学日历详细信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward calendarInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String calendarId = request.getParameter(Constants.CALENDAR_KEY);
        if (StringUtils.isEmpty(calendarId))
            return forwardError(mapping, request, new String[] { "entity.calendar",
                    "error.model.id.needed" });
        TeachCalendar calendar = calendarService.getTeachCalendar(Long.valueOf(calendarId));
        
        if (null == calendar)
            return forwardError(mapping, request, new String[] { "entity.calendar",
                    "error.model.notExist" });
        
        List dates = new ArrayList();
        Calendar start = new GregorianCalendar();
        start.setTime(calendar.getStart());
        Date finish = calendar.getFinish();
        int i = 0;
        List weekDates = null;
        while (!start.getTime().after(finish)) {
            if (i == 0) {
                weekDates = new ArrayList();
                dates.add(weekDates);
            }
            weekDates.add(start.getTime());
            i++;
            i %= 7;
            start.set(Calendar.DAY_OF_YEAR, start.get(Calendar.DAY_OF_YEAR) + 1);
        }
        addCollection(request, "dates", dates);
        addEntity(request, calendar);
        return forward(request);
    }
    
    /**
     * @param teachCalendarService
     *            The teachCalendarService to set.
     */
    public void setCalendarService(TeachCalendarService calendarService) {
        this.calendarService = calendarService;
    }
    
    public void setTaskActivityService(TaskActivityService taskActivityService) {
        this.taskActivityService = taskActivityService;
    }
}
