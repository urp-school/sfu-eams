//$Id: WorkloadButtonAction.java,v 1.7 2007/01/10 06:16:39 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-19         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.workload.course;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.workload.course.WorkloadOpenSwitch;
import com.shufe.service.workload.course.WorkloadOpenSwitchService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 评教开关响应类
 * 
 * @author chaostone
 * 
 */
public class WorkloadOpenSwitchAction extends CalendarRestrictionSupportAction {
    
    protected WorkloadOpenSwitchService workloadOpenSwitchService;
    
    /**
     * 工作量查询的开关主页面。
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        EntityQuery query = new EntityQuery(TeachCalendar.class, "calendar");
        DataRealmUtils.addDataRealm(query, new String[] { "calendar.studentType.id" },
                getDataRealm(request));
        query.setSelect("distinct calendar.studentType");
        addCollection(request, "stdTypes", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 开关列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward switchList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(WorkloadOpenSwitch.class, "switch");
        populateConditions(request, query);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        Long stdTypeId = getLong(request, "switch.teachCalendar.studentType.id");
        addCollection(request, "switchs", (null == stdTypeId) ? Collections.EMPTY_LIST
                : utilService.search(query));
        return forward(request);
    }
    
    /**
     * 没有设置开关的列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward noSwitchCalendarList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(TeachCalendar.class, "calendar");
        populateConditions(request, query);
        Long stdTypeId = getLong(request, "switch.teachCalendar.studentType.id");
        query.add(new Condition("calendar.studentType.id=" + stdTypeId));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        query.add(new Condition(
                "not exists(from WorkloadOpenSwitch ws where ws.teachCalendar.id=calendar.id)"));
        addCollection(request, "calendars", (null == stdTypeId) ? Collections.EMPTY_LIST
                : utilService.search(query));
        return forward(request);
    }
    
    /**
     * 设置开放和关闭状态
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward setStatus(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List switchs = new ArrayList();
        String switchIds = request.getParameter("switchIds");
        if (StringUtils.isNotEmpty(switchIds)) {
            switchs = utilService.load(WorkloadOpenSwitch.class, "id", SeqStringUtil
                    .transformToLong(switchIds));
        } else {
            String calendarIdSeq = request.getParameter("calendarIds");
            if (StringUtils.isNotEmpty(calendarIdSeq)) {
                Long[] calendarIds = SeqStringUtil.transformToLong(calendarIdSeq);
                for (int i = 0; i < calendarIds.length; i++) {
                    WorkloadOpenSwitch openSwitch = new WorkloadOpenSwitch();
                    switchs.add(openSwitch);
                    openSwitch.setTeachCalendar(new TeachCalendar(calendarIds[i]));
                }
            }
        }
        Date openTime = new Date(System.currentTimeMillis());
        Boolean isOpen = getBoolean(request, "isOpen");
        if (null == isOpen)
            isOpen = Boolean.FALSE;
        
        for (Iterator iterator = switchs.iterator(); iterator.hasNext();) {
            WorkloadOpenSwitch openSwitch = (WorkloadOpenSwitch) iterator.next();
            openSwitch.setIsOpen(isOpen);
            if (Boolean.TRUE.equals(isOpen)) {
                openSwitch.setOpenTime(openTime);
            } else {
                openSwitch.setOpenTime(null);
            }
        }
        utilService.saveOrUpdate(switchs);
        return redirect(request, new Action("", "switchList"), "info.action.success");
    }
    
    public void setWorkloadOpenSwitchService(WorkloadOpenSwitchService workloadOpenSwitchService) {
        this.workloadOpenSwitchService = workloadOpenSwitchService;
    }
    
}
