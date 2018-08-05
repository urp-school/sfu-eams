//$Id: ResourceAction.java,v 1.4 2006/12/03 06:59:48 duanth Exp $
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
 * chaostone             2005-11-12         Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.arrange.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.resource.OccupyInfo;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.resource.TeachResourceService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 
 * 教学资源占用查询
 * 
 * @author chaostone 2005-11-12
 * 
 */
public abstract class ResourceAction extends CalendarRestrictionSupportAction {
    
    protected TeachResourceService teachResourceService;
    
    /**
     * 获得页面的时间信息
     * 
     * @param request
     * @param calendar
     * @return
     */
    protected TimeUnit[] getTimesParam(HttpServletRequest request, TeachCalendar calendar) {
        Integer startWeek = getInteger(request, "startWeek");
        Integer endWeek = getInteger(request, "endWeek");
        if (null == startWeek || startWeek.intValue() < 1) {
            startWeek = new Integer(1);
        }
        if (null == endWeek || endWeek.intValue() > calendar.getWeekSpan().intValue()) {
            endWeek = calendar.getWeekSpan();
        }
        request.setAttribute("startWeek", startWeek);
        request.setAttribute("endWeek", endWeek);
        return calendar.buildTimeUnits(startWeek.intValue(), endWeek.intValue());
    }
    
    /**
     * 时间占用表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward occupyTables(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List resources = getResourceList(request);
        TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, getLong(
                request, "calendar.id"));
        TimeUnit[] times = getTimesParam(request, calendar);
        
        Boolean isMerged = getBoolean(request, "isMerged");
        List occupyTables = new ArrayList();
        if (!Boolean.TRUE.equals(isMerged)) {
            for (Iterator iter = resources.iterator(); iter.hasNext();) {
                Entity resource = (Entity) iter.next();
                Map occupyTable = new HashMap();
                occupyTable.put("resource", resource);
                List activities = new ArrayList();
                Long resourceId = (Long) PropertyUtils.getProperty(resource, resource.key());
                for (int i = 0; i < times.length; i++) {
                    activities.addAll(getActivities(resourceId, times[i]));
                }
                occupyTable.put("activities", activities);
                occupyTables.add(occupyTable);
            }
        } else {
            Map occupyTable = new HashMap();
            List activities = new ArrayList();
            for (Iterator iter = resources.iterator(); iter.hasNext();) {
                Entity resource = (Entity) iter.next();
                
                Long resourceId = (Long) PropertyUtils.getProperty(resource, resource.key());
                for (int i = 0; i < times.length; i++) {
                    activities.addAll(getActivities(resourceId, times[i]));
                }
            }
            occupyTable.put("activities", activities);
            occupyTables.add(occupyTable);
            addCollection(request, "resources", resources);
        }
        request.setAttribute("occupyTables", occupyTables);
        request.setAttribute(Constants.CALENDAR, calendar);
        request.setAttribute("weekList", WeekInfo.WEEKS);
        return forward(request);
    }
    
    /**
     * 列出资源的占用/空闲情况的缩略表示
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward digestTime(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, getLong(
                request, "calendar.id"));
        Boolean isOccupy = getBoolean(request, "isOccupy");
        Integer startWeek = getInteger(request, "startWeek");
        Integer endWeek = getInteger(request, "endWeek");
        if (null == startWeek || startWeek.intValue() < 1)
            startWeek = new Integer(1);
        if (null == endWeek || endWeek.intValue() > calendar.getWeeks().intValue())
            endWeek = calendar.getWeeks();
        List resources = getResourceList(request);
        Map occupyMap = new HashMap();
        int from = calendar.getWeekStart().intValue();
        for (Iterator iter = resources.iterator(); iter.hasNext();) {
            Entity one = (Entity) iter.next();
            Object entityId = PropertyUtils.getProperty(one, one.key());
            TimeUnit[] times = calendar.buildTimeUnits(startWeek.intValue(), endWeek.intValue());
            List occupies = new ArrayList();
            for (int i = 0; i < times.length; i++) {
                occupies.addAll(getOccupyInfos(entityId, times[i].getValidWeeksNum(), times[i]
                        .getYear()));
            }
            occupyMap.put(entityId.toString(), new OccupyInfo(occupies, calendar).digestTime(from,
                    startWeek.intValue(), endWeek.intValue(), isOccupy.booleanValue()));
        }
        request.setAttribute("resources", resources);
        request.setAttribute("occupyMap", occupyMap);
        return forward(request);
    }
    
    protected abstract List getOccupyInfos(Object id, Long validWeeksNum, Integer year);
    
    protected abstract List getActivities(Object id, TimeUnit timeUnit);
    
    protected abstract Entity getResourceEntity(HttpServletRequest request);
    
    protected abstract List getResourceList(HttpServletRequest request);
    
    /**
     * 
     * @param teachResourceService
     *            The teachResourceService to set.
     * 
     */
    public void setTeachResourceService(TeachResourceService teachResourceService) {
        this.teachResourceService = teachResourceService;
    }
    
}
