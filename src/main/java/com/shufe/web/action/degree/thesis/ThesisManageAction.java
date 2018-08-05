//$Id: ThesisManageAction.java,v 1.1 2006/10/23 03:20:53 cwx Exp $
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
 * chenweixiong         2006-10-18          Created
 * zq                   2007-09-17          修改了search()方法
 ********************************************************************************/

package com.shufe.web.action.degree.thesis;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.process.Schedule;
import com.shufe.model.std.Student;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;

public class ThesisManageAction extends RestrictionSupportAction {

    /**
     * 得到论文管理的查询页面
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
        setDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }

    /**
     * 根据条件查询对应的信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(ThesisManage.class, "thesisManage");
        populateConditions(request, query, "thesisManage.student.type.id");
        DataRealmUtils.addDataRealms(query, new String[] { "thesisManage.student.type.id",
                "thesisManage.student.department.id" }, getDataRealmsWith(getLong(request,
                "thesisManage.student.type.id"), request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        query.setLimit(getPageLimit(request));
        addCollection(request, "thesisManages", utilService.search(query));
        return forward(request, "thesisManageList");
    }

    /**
     * 查看对应的进度信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward loadProcessPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long thesisManageId = getLong(request, "thesisManageId");
        ThesisManage thesisManage = (ThesisManage) utilService.get(ThesisManage.class,
                thesisManageId);
        Student student = thesisManage.getStudent();
        if (null == thesisManage.getSchedule()) {
            StudentType stdType = student.getType();
            Schedule schedule = null;
            while (null != stdType && null == schedule) {
                List schedules = utilService.load(Schedule.class, new String[] {
                        "studyLength", "enrollYear", "studentType.id" }, new Object[] {
                        String.valueOf(student.getSchoolingLength()), student.getEnrollYear(),
                        stdType.getId() });
                schedule = (schedules.size() > 0) ? (Schedule) schedules.get(0) : null;
                stdType =(StudentType) stdType.getSuperType();
            }
            if (null == schedule) {
                request.setAttribute("student", student);
                return forward(request, "../thesisManageStd/noscheduleErrors");
            }
            thesisManage.setSchedule(schedule);
            utilService.saveOrUpdate(thesisManage);
        }
        request.setAttribute("thesisManage", thesisManage);
        request.setAttribute("schedule", thesisManage.getSchedule());
        return forward(request);
    }
}
