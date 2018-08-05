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
 * chaostone             2006-11-29            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.arrange.exam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.ExamDelayReason;
import com.ekingstar.eams.system.basecode.industry.ExamStatus;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.exam.ExamTakeService;
import com.shufe.web.action.common.CalendarSupportAction;

/**
 * 学生查看考试安排,申请缓考等功能
 * 
 * @author chaostone
 * 
 */
public class StdExamTableAction extends CalendarSupportAction {
    
    private ExamTakeService examTakeService;
    
    /**
     * 学生查看考试安排主界面
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
        Student std = getStudentFromSession(request.getSession());
        if (null == std)
            return forward(mapping, request, "error.std.stdNo.needed", "error");
        request.setAttribute("std", std);
        setCalendar(request, std.getType());
        addCollection(request, "stdTypeList", Collections.singletonList(std.getType()));
        addCollection(request, "examTypes", baseCodeService.getCodes(ExamType.class));
        return forward(request);
    }
    
    /**
     * 查看学生的考试安排
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward examTable(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long examTypeId = getLong(request, "examType.id");
        if (null == examTypeId)
            return forwardError(mapping, request, "error.parameters.needed");
        ExamType examType = (ExamType) utilService.get(ExamType.class, examTypeId);
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id"));
        List calendars = teachCalendarService.getTeachCalendarsOfOverlapped(calendar);
        // 找到上课记录
        EntityQuery entityQuery = new EntityQuery(CourseTake.class, "take");
        entityQuery.add(new Condition("take.task.calendar in(:calendars)", calendars));
        entityQuery.add(new Condition("take.student=:std", getStudentFromSession(request
                .getSession())));
        Collection takes = utilService.search(entityQuery);
        if (getSystemConfig().getBooleanParam("course.exam.seatNo")) {
            Map examMap = new HashMap();
            for (Iterator iter = takes.iterator(); iter.hasNext();) {
                CourseTake courseTake = (CourseTake) iter.next();
                ExamTake examTake = courseTake.getExamTake(examType);
                if (null != examTake) {
                    Integer seatNum = examTakeService.getSeatNum(examTake);
                    if (null != seatNum) {
                        examMap.put(examTake.getId().toString(), seatNum);
                    }
                }
            }
            request.setAttribute("examMap", examMap);
        }
        request.setAttribute("examType", examType);
        addCollection(request, "takes", takes);
        request.setAttribute("calendar", calendar);
        return forward(request);
    }
    
    /**
     * 填写缓考申请<br>
     * 只有正常考试和缓考申请的examTake才被允许为合法申请对象.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 检查参数
        String takeId = request.getParameter("examTake.id");
        if (StringUtils.isBlank(takeId)) {
            return forwardError(mapping, request, "error.parameter.needed");
        }
        Long examTypeId = getLong(request, "examType.id");
        Long calendarId = getLong(request, "calendar.id");
        if (!ValidEntityKeyPredicate.INSTANCE.evaluate(examTypeId)
                || !ValidEntityKeyPredicate.INSTANCE.evaluate(calendarId)) {
            return forwardError(mapping, request, "error.parameters.needed");
        }
        
        Long examTakeId = Long.valueOf(takeId);
        ExamTake examTake = (ExamTake) utilService.get(ExamTake.class, examTakeId);
        Student std = getStudentFromSession(request.getSession());
        Boolean canApplyDelay = Boolean.valueOf(examTakeService.canApplyDelayExam(std, examTake));
        request.setAttribute("canApplyDelay", canApplyDelay);
        if (Boolean.TRUE.equals(canApplyDelay)) {
            EntityQuery entityQuery = new EntityQuery(ExamDelayReason.class, "reason");
            entityQuery.add(new Condition("reason.canApply=true"));
            addCollection(request, "delayReasons", utilService.search(entityQuery));
            request.setAttribute("std", std);
            request.setAttribute("examTake", examTake);
        }
        return forward(request);
    }
    
    /**
     * 递交缓考申请
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward submitApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String takeId = request.getParameter("examTake.id");
        
        if (StringUtils.isBlank(takeId)) {
            return forwardError(mapping, request, "error.parameter.needed");
        }
        Student std = getStudentFromSession(request.getSession());
        ExamTake take = (ExamTake) utilService.get(ExamTake.class, Long.valueOf(takeId));
        if (examTakeService.canApplyDelayExam(std, take)) {
            take.setRemark("apply it At:" + new Date(System.currentTimeMillis()));
            take.setExamStatus(new ExamStatus(ExamStatus.DELAY));
            Long delayReasonId = getLong(request, "examTake.delayReason.id");
            ExamDelayReason reason = new ExamDelayReason();
            reason.setId(delayReasonId);
            take.setDelayReason(reason);
            utilService.saveOrUpdate(take);
        }
        Boolean print = getBoolean(request, "print");
        if (Boolean.TRUE.equals(print)) {
            return redirect(request, new Action(StdExamTableAction.class, "printDelayApply"),
                    "info.set.success", "&calendar.id=" + request.getParameter("calendar.id")
                            + "&examTakeIds=" + take.getId());
        } else {
            return redirect(request, new Action(StdExamTableAction.class, "examTable"),
                    "info.set.success", new String[] { "calendar", "examType" });
        }
    }
    
    /**
     * 打印缓考申请
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward printDelayApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String takeIds = request.getParameter("examTakeIds");
        if (StringUtils.isBlank(takeIds)) {
            return forwardError(mapping, request, "error.parameters.needed");
        }
        Long[] examTakeIds = SeqStringUtil.transformToLong(takeIds);
        
        Student std = getStudentFromSession(request.getSession());
        List examTakes = new ArrayList();
        for (int i = 0; i < examTakeIds.length; i++) {
            ExamTake take = (ExamTake) utilService.get(ExamTake.class, examTakeIds[i]);
            if (take.getStd().equals(std)
                    && take.getExamStatus().getId().equals(ExamStatus.APPLY_DELAY)) {
                examTakes.add(take);
            }
        }
        request.setAttribute("calendar", utilService.get(TeachCalendar.class, getLong(request,
                "calendar.id")));
        request.setAttribute("std", std);
        addCollection(request, "examTakes", examTakes);
        request.setAttribute("curDate", new Date(System.currentTimeMillis()));
        
        return forward(request);
    }
    
    /**
     * 取消缓考申请
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cancelApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String takeIds = request.getParameter("examTakeIds");
        if (StringUtils.isBlank(takeIds)) {
            return forwardError(mapping, request, "error.parameter.needed");
        }
        Long[] examTakeIds = SeqStringUtil.transformToLong(takeIds);
        Student std = getStudentFromSession(request.getSession());
        for (int i = 0; i < examTakeIds.length; i++) {
            ExamTake take = (ExamTake) utilService.get(ExamTake.class, examTakeIds[i]);
            if (take.getStd().equals(std)
                    && !take.getExamStatus().getId().equals(ExamStatus.NORMAL)) {
                take.setRemark("");
                take.setDelayReason(null);
                take.setExamStatus(new ExamStatus(ExamStatus.NORMAL));
                utilService.saveOrUpdate(take);
            }
        }
        return redirect(request, new Action(StdExamTableAction.class, "examTable"),
                "info.set.success", new String[] { "calendar", "examType" });
    }
    
    public void setExamTakeService(ExamTakeService examTakeService) {
        this.examTakeService = examTakeService;
    }
    
}
