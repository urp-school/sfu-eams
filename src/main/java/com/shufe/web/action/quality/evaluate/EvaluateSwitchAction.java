//$Id: EvaluateSwitchAction.java,v 1.1 2007-6-4 10:00:42 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-6-4         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.evaluate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.quality.evaluate.EvaluateSwitch;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.quality.evaluate.EvaluateResultService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionExampleTemplateAction;

/**
 * 评教开关
 * 
 * @author chaostone
 * 
 */
public class EvaluateSwitchAction extends CalendarRestrictionExampleTemplateAction {
    
    private EvaluateResultService evaluateResultService;
    
    /**
     * 
     * 显示开放评教开关的页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        return forward(request);
    }
    
    /**
     * 查询列表页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        EntityQuery query = builderEntityQuery(request);
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        query.setLimit(getPageLimit(request));
        addCollection(request, "switchs", utilService.search(query));
        request.setAttribute("switchState", request.getParameter("switch.isOpen"));
        return forward(request);
    }
    
    /**
     * 修改
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        EvaluateSwitch evaluateSwitch = (EvaluateSwitch) getEntity(request, EvaluateSwitch.class,
                "switch");
        List stdTypes = getStdTypes(request);
        if (!evaluateSwitch.isPO()) {
            setCalendar(request, (StudentType) stdTypes.get(0));
            request.setAttribute("stdTypeList", stdTypes);
        }
        Set calendars = new HashSet();
        for (Iterator it = utilService.loadAll(EvaluateSwitch.class).iterator(); it.hasNext();) {
            EvaluateSwitch onOff = (EvaluateSwitch) it.next();
            calendars.add(onOff.getCalendar());
        }
        addCollection(request, "calendars", calendars);
        List departs = getTeachDeparts(request);
        addCollection(request, "stdTypes", CollectionUtils.subtract(stdTypes, evaluateSwitch
                .getStdTypes()));
        addCollection(request, "departs",departs );
        request.setAttribute("switch", evaluateSwitch);
        return forward(request);
    }
    
    /**
     * @param request
     * @return
     */
    private EntityQuery builderEntityQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(EvaluateSwitch.class, "switch");
        query.join("switch.stdTypes", "stdType");
        query.join("switch.departs", "depart");
        populateConditions(request, query);
        DataRealmUtils.addDataRealms(query, new String[] { "stdType.id", "depart.id" },
                getDataRealms(request));
        query.setSelect("distinct switch ");
        return query;
    }
    
    /**
     * 保存
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EvaluateSwitch evaluateSwitch = (EvaluateSwitch) populateEntity(request,
                EvaluateSwitch.class, "switch");
        
        // 具体开放时间
        DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        String openTime = request.getParameter("openTime");
        String closeTime = request.getParameter("closeTime");
        if (StringUtils.isNotBlank(openTime)) {
            evaluateSwitch.setOpenAt(dformat.parse(openTime));
        }
        if (StringUtils.isNotBlank(closeTime)) {
            evaluateSwitch.setCloseAt(dformat.parse(closeTime));
        }
        
        // 教学日历
        if (!ValidEntityPredicate.INSTANCE.evaluate(evaluateSwitch.getCalendar())) {
            evaluateSwitch.setCalendar(teachCalendarService.getTeachCalendar(getLong(request,
                    "calendar.studentType.id"), get(request, "calendar.year"), get(request,
                    "calendar.term")));
        }
        // 关联的学生类别和部门
        evaluateSwitch.setStdTypes(new HashSet(studentTypeService.getStudentTypes(get(request,
                "stdTypeIdSeq"))));
        evaluateSwitch.setDeparts(new HashSet(departmentService.getDepartments(get(request,
                "departmentIdSeq"))));
        
        utilService.saveOrUpdate(evaluateSwitch);
        return redirect(request, "search", "info.save.success");
    }
    
    /**
     * 初始化评教数据 目的是为了删除历史的评教数据
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward clearData(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teachCalendarIdSeq = request.getParameter("teachCalendarIdSeq");
        if (StringUtils.isEmpty(teachCalendarIdSeq)) {
            List teachCalendars = utilService.load(TeachCalendar.class, "studentType.id",
                    SeqStringUtil.transformToLong(getStdTypeIdSeq(request)));
            Date date = Calendar.getInstance().getTime();
            List calendars = new ArrayList();
            for (int i = teachCalendars.size() - 1; i >= 0; i--) {
                TeachCalendar teachCalendar = (TeachCalendar) teachCalendars.get(i);
                if (teachCalendar.getFinish().before(date)) {
                    calendars.add(teachCalendar);
                }
            }
            request.setAttribute("teachCalendars", calendars);
            return forward(request, "clearData");
        }
        String name = getUser(request.getSession()).getName();
        evaluateResultService.batchDeleteEvaluateDatas(getDepartmentIdSeq(request),
                getStdTypeIdSeq(request), teachCalendarIdSeq);
        info(name + "删除了学生评教详细数据,教学日历id为(" + teachCalendarIdSeq + ")学生类别id为:("
                + getStdTypeIdSeq(request) + ")部门id为:(" + getDepartmentIdSeq(request) + ")");
        return redirect(request, "index", "field.evaluateButton.initializatedSuccess");
    }
    
    public void setEvaluateResultService(EvaluateResultService evaluateResultService) {
        this.evaluateResultService = evaluateResultService;
    }
    
}
