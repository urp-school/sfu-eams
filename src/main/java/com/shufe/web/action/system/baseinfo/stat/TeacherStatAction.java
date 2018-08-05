//$Id: TeacherStatAction.java,v 1.1 2007-4-3 下午03:53:24 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-4-3         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.baseinfo.stat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.state.TeacherTitle;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.security.Authentication;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.course.task.TeachTaskStatService;
import com.shufe.service.system.baseinfo.stat.TeacherStatService;
import com.shufe.service.util.stat.FloatSegment;
import com.shufe.service.util.stat.StatGroup;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 教师统计
 * 
 * @author chaostone
 * 
 */
public class TeacherStatAction extends CalendarRestrictionSupportAction {
    
    TeacherStatService teacherStatService;
    
    TeachTaskStatService teachTaskStatService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    public ActionForward statByAge(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        int start = getInteger(request, "start").intValue();
        int span = getInteger(request, "span").intValue();
        int count = getInteger(request, "count").intValue();
        List segs = new ArrayList();
        segs.add(new FloatSegment(0, start - 1));
        segs.addAll(FloatSegment.buildSegments(start, span, count));
        segs.add(new FloatSegment(start + (span * count) - 1, Integer.MAX_VALUE));
        EntityQuery query = new EntityQuery(Teacher.class, "teacher");
        query.setSelect("teacher.birthday");
        populateConditions(request, query);
        query.add(new Condition("teacher.isTeaching=true"));
        query.add(new Condition("teacher.birthday is not null"));
        List birthday = (List) utilService.search(query);
        List ages = new ArrayList();
        for (Iterator it = birthday.iterator(); it.hasNext();) {
            Date date = (Date) it.next();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            ages.add(new Integer(Calendar.getInstance().get(Calendar.YEAR) - c.get(Calendar.YEAR)));
        }
        FloatSegment.countSegments(segs, ages);
        addCollection(request, "ageSegs", segs);
        return forward(request);
    }
    
    public ActionForward statByDegree(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List stats = teacherStatService.statByDegree(getDataRealm(request));
        addCollection(request, "stats", stats);
        return forward(request);
    }
    
    public ActionForward statByEduDegree(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List stats = teacherStatService.statByEduDegree(getDataRealm(request));
        addCollection(request, "stats", stats);
        return forward(request);
    }
    
    public ActionForward statByTitle(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List stats = teacherStatService.statByTitle(getDataRealm(request));
        sortStatItem(request, stats);
        addCollection(request, "stats", stats);
        return forward(request);
    }
    
    /**
     * 学缘结构
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statByGraduateSchool(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List stats = teacherStatService.statByGraduateSchool(getDataRealm(request));
        sortStatItem(request, stats);
        addCollection(request, "stats", stats);
        return forward(request);
    }
    
    /**
     * 统计各个支撑在某一时间段的上课情况
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long stdTypeId = getLong(request, "studentType.id");
        // 没有输入，转入输入界面
        if (null == stdTypeId) {
            List stdTypeList = null;
            if (request.getSession().getAttribute(Authentication.USERID) != null) {
                stdTypeList = getStdTypes(request);
            } else {
                EntityQuery query = new EntityQuery(StudentType.class, "stdType");
                query.add(new Condition("stdType.state=true"));
                query.addOrder(new Order("stdType.code"));
                stdTypeList = (List) utilService.search(query);
            }
            request.setAttribute("stdTypeList", stdTypeList);
            return forward(request, "statTaskForm");
        }
        // 查找条件
        List calendars = teachCalendarService.getTeachCalendars(getLong(request, "studentType.id"),
                get(request, "startYear"), get(request, "startTerm"), get(request, "endYear"), get(
                        request, "endTerm"));
        if (calendars.isEmpty()) {
            request.setAttribute("stat", new StatGroup(null, Collections.EMPTY_LIST));
        } else {
            List stats = teachTaskStatService.statTeacherTitle(calendars);
            TeacherTitle nullTitle = new TeacherTitle();
            nullTitle.setId(new Long(0));
            nullTitle.setName(" ");
            for (Iterator iter = stats.iterator(); iter.hasNext();) {
                Object[] datas = (Object[]) iter.next();
                if (null == datas[1]) {
                    datas[1] = nullTitle;
                }
            }
            List statGroups = StatGroup.buildStatGroups(stats);
            request.setAttribute("stat", new StatGroup(null, statGroups));
        }
        return forward(request);
    }
    
    private void sortStatItem(HttpServletRequest request, List stats) {
        List orders = OrderUtils.parser(request.getParameter("orderBy"));
        if (!orders.isEmpty()) {
            Order order = (Order) orders.get(0);
            Collections.sort(stats, new PropertyComparator("what.name", Order.ASC == order
                    .getDirection()));
        }
    }
    
    public void setTeacherStatService(TeacherStatService teacherStatService) {
        this.teacherStatService = teacherStatService;
    }
    
    public void setTeachTaskStatService(TeachTaskStatService teachTaskStatService) {
        this.teachTaskStatService = teachTaskStatService;
    }
    
}
