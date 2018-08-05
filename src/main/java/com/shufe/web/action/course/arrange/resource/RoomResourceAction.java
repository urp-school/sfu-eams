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
 * chaostone             2006-12-2            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.arrange.resource;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.Activity;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.service.system.baseinfo.ClassroomService;

public class RoomResourceAction extends ResourceAction {
    
    private ClassroomService roomService;
    
    /**
     * 
     * 教室查询的主界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     * 
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        ((List) request.getAttribute(Constants.DEPARTMENT_LIST)).add(departmentService
                .getDepartment(Department.SCHOOLID));
        List classroomConfigTypeList = baseCodeService.getCodes(ClassroomType.class);
        List schoolDistrictList = baseCodeService.getCodes(SchoolDistrict.class);
        addCollection(request, "classroomConfigTypeList", classroomConfigTypeList);
        addCollection(request, "districtList", schoolDistrictList);
        return forward(request);
    }
    
    /**
     * 加载教室列表（分页）
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
        EntityQuery entityQuery = new EntityQuery(Classroom.class, "classroom");
        entityQuery.setSelect("select classroom");
        QueryRequestSupport.populateConditions(request, entityQuery);
        String departIds = request.getParameter("depart.id");
        if (StringUtils.isNotEmpty(departIds)) {
            entityQuery.add(new Condition(
                    "exists (from Department depart join depart.classrooms room "
                            + "where room.id=classroom.id and depart.id in(:departIds))",
                    SeqStringUtil.transformToLong(departIds)));
        }
        entityQuery.add(new Condition("classroom.state = true"));
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "rooms", utilService.search(entityQuery));
        return forward(request);
    }
    
    protected List getOccupyInfos(Object id, Long validWeeksNum, Integer year) {
        return teachResourceService.getRoomOccupyInfos((Long) id, validWeeksNum, year);
    }
    
    protected List getActivities(Object id, TimeUnit timeUnit) {
        return teachResourceService.getRoomActivities((Serializable) id, timeUnit, Activity.class,
                null);
    }
    
    protected Entity getResourceEntity(HttpServletRequest request) {
        return roomService.getClassroom(getLong(request, "classroom.id"));
    }
    
    protected List getResourceList(HttpServletRequest request) {
        return utilService.load(Classroom.class, "id", SeqStringUtil.transformToLong(get(request,
                "classroomIds")));
    }
    
    /**
     * @param roomService
     *            The roomService to set.
     */
    public void setRoomService(ClassroomService roomService) {
        this.roomService = roomService;
    }
}
