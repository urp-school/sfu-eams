// $Id: ApplyRoomOperation.java,v 1.3 2006/12/02 08:48:36 duanth Exp $
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
 *chaostone      2007-5-22         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.arrange.apply;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.mvc.struts.misc.ForwardSupport;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.ActivityType;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.baseinfo.Building;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.TimeUnitUtil;
import com.shufe.model.course.arrange.Activity;
import com.shufe.model.course.arrange.apply.RoomApply;
import com.shufe.model.course.arrange.resource.RoomPriceCatalogue;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TimeSetting;
import com.shufe.service.course.arrange.resource.TeachResourceService;
import com.shufe.web.action.common.RestrictionSupportAction;
import com.shufe.web.helper.LogHelper;

public class RoomApplyAction extends RestrictionSupportAction {
    
    protected TeachResourceService teachResourceService;
    
    protected List getRoomPriceCatalogues(HttpServletRequest request) {
        List roomPriceCatalogues = utilService.loadAll(RoomPriceCatalogue.class);
        if (CollectionUtils.isEmpty(roomPriceCatalogues)) {
            saveErrors(request, ForwardSupport
                    .buildMessages(new String[] { "roomApply.withoutRoomPriceCatalogues" }));
            throw new RuntimeException("without roomPriceCatalogues");
        } else {
            return roomPriceCatalogues;
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
    public ActionForward searchRoom(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        initBaseCodes("buildingList", Building.class);
        initBaseCodes("configTypeList", ClassroomType.class);
        if ("search".equals(request.getParameter("flag"))) {
            DynaActionForm dynaForm = (DynaActionForm) form;
            TimeUnit time = TimeUnitUtil.constructTime(java.sql.Date.valueOf(request
                    .getParameter("dateBegin")), java.sql.Date.valueOf(request
                    .getParameter("dateEnd")), Integer.valueOf(request.getParameter("weekDay")),
                    Integer.valueOf(request.getParameter("startUnit")), Integer.valueOf(request
                            .getParameter("unitCount")), Integer.valueOf(request
                            .getParameter("weekType")));
            Classroom room = (Classroom) dynaForm.get("room");
            Results.addObject("roomList", teachResourceService.getFreeRoomsOf(null,
                    new TimeUnit[] { time }, room, CourseActivity.class));
        }
        
        return this.forward(mapping, request, "searchRoom");
    }
    
    /**
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
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward applyNotice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
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
    public ActionForward pricesReview(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("catalogues", getRoomPriceCatalogues(request));
        return forward(request);
    }
    
    /**
     * 查看365校区教室借用价目
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward pricesReview369(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    /**
     * 我已申请的教室记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward myApplied(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(RoomApply.class, "roomApply");
        query.add(new Condition("roomApply.borrower.user.id = (:uid)",
                getUser(request.getSession()).getId()));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "roomApplies", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 使用教室情况
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward searchHome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "roomConfigTypes", baseCodeService.getCodes(ClassroomType.class));
        return forward(request);
    }
    
    /**
     * 跳转到申请教室界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward toApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "roomConfigTypes", baseCodeService.getCodes(ClassroomType.class));
        return forward(request, "searchHome");
    }
    
    /**
     * 教室空闲查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward freeRoomList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String requestParamter = request.getParameter("requestParamter");
        RoomApply roomApply = (RoomApply) populateEntity(request, RoomApply.class, "roomApply");
        Classroom room = (Classroom) populateEntity(request, Classroom.class, "classroom");
        
        TimeUnit[] times = roomApply.getApplyTime().convert(
                (TimeSetting) utilService.get(TimeSetting.class, TimeSetting.DEFAULT_ID));
        if (times != null) {
            net.ekingstar.common.detail.Pagination rooms = teachResourceService.getFreeRoomsOf(
                    null, times, room, Activity.class, getPageNo(request), getPageSize(request));
            addOldPage(request, "rooms", rooms);
        }
        
        addCollection(request, "roomConfigTypes", baseCodeService.getCodes(ClassroomType.class));
        addCollection(request, "districtList", baseCodeService.getCodes(SchoolDistrict.class));
        request.setAttribute("requestParamter", requestParamter);
        return forward(request, "freeRoomHome");
    }
    
    /**
     * 查看申请教室空闲情况
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward freeRoomHome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        initBaseCodes(request, "roomConfigTypes", ClassroomType.class);
        initBaseInfos(request, "districtList", SchoolDistrict.class);
        String requestParamter = "&applyTime.dateBegin="
                + request.getParameter("applyTime.dateBegin") + "&applyTime.dateEnd="
                + request.getParameter("applyTime.dateEnd") + "&applyTime.timeBegin="
                + request.getParameter("applyTime.timeBegin") + "&applyTime.timeEnd="
                + request.getParameter("applyTime.timeEnd") + "&roomApply.applyTime.cycleCount="
                + request.getParameter("roomApply.applyTime.cycleCount")
                + "&roomApply.applyTime.cycleType="
                + request.getParameter("roomApply.applyTime.cycleType") + "&classroom.name="
                + request.getParameter("classroom.name") + "&classroom.configType.id="
                + request.getParameter("classroom.configType.id") + "&roomApply.isMultimedia="
                + request.getParameter("roomApply.isMultimedia") + "&roomApply.schoolDistrict.id="
                + request.getParameter("roomApply.schoolDistrict.id");
        // log.info("aaa-->"+requestParamter);
        request.setAttribute("requestParamter", requestParamter);
        // addCollection(request, "roomConfigTypes", baseCodeService.getCodes(ClassroomType.class));
        // addCollection(request, "districtList", baseCodeService.getCodes(SchoolDistrict.class));
        return forward(request);
    }
    
    /**
     * 新增教室申请form
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        applyFormInitParams(request);
        return forward(request);
    }
    
    /**
     * @param request
     */
    protected void applyFormInitParams(HttpServletRequest request) {
        initBaseCodes(request, "activityTypes", ActivityType.class);
        request.setAttribute("user", getUser(request.getSession()));
        request.setAttribute("applyAt", new Date());
        request.setAttribute("departments", ((RoomPriceCatalogue) getRoomPriceCatalogues(request)
                .get(0)).getAuditDeparts());
        initBaseInfos(request, "schoolDistricts", SchoolDistrict.class);
    }
    
    /**
     * 复制教室申请
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward copyApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        getRoomApplyDatas(request);
        return forward(request);
    }
    
    /**
     * 自定义方法<br>
     * 得到修改或复制审核页面中的数据
     * 
     * @param request
     * @return
     */
    protected void getRoomApplyDatas(HttpServletRequest request) {
        RoomApply roomApply = (RoomApply) utilService.load(RoomApply.class, new Long(request
                .getParameter("roomApplyId")));
        request.setAttribute("roomApply", roomApply);
        initBaseCodes(request, "activityTypes", ActivityType.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        request.setAttribute("applyAt", sdf.format(new Date()));
        request.setAttribute("departments", ((RoomPriceCatalogue) getRoomPriceCatalogues(request)
                .get(0)).getAuditDeparts());
        request.setAttribute("requestAction", RequestUtils.getRequestAction(request));
        initBaseCodes(request, "schoolDistricts", SchoolDistrict.class);
    }
    
    /**
     * 保存--新建的申请记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward apply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        RoomApply roomApply = (RoomApply) populateEntity(request, RoomApply.class, "roomApply");
        roomApply.setIsDepartApproved(null);
        roomApply.setIsApproved(null);
        roomApply.setDepartApproveBy(null);
        roomApply.setDepartApproveAt(null);
        roomApply.setHours(roomApply.getApplyTime().calcHours());
        utilService.saveOrUpdate(roomApply);
        return redirect(request, "myApplied", "info.action.success");
    }
    
    /**
     * 删除教室的借用申请
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String roomApplyId = request.getParameter("roomApplyId");
        List applies = utilService.load(RoomApply.class, "id", SeqStringUtil
                .transformToLong(roomApplyId));
        
        List canceled = new ArrayList();
        for (Iterator iterator = applies.iterator(); iterator.hasNext();) {
            RoomApply apply = (RoomApply) iterator.next();
            if (!Boolean.TRUE.equals(apply.getIsApproved())) {
                canceled.add(apply);
            }
        }
        utilService.remove(canceled);
        logHelper.info(request, LogHelper.DELETE);
        return redirect(request, "myApplied", "info.action.success");
    }
    
    /**
     * 查看打印版本
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        buildRoomApplyInfo(request);
        return forward(request);
    }
    
    /**
     * @param request
     */
    protected void buildRoomApplyInfo(HttpServletRequest request) {
        Long id = getLong(request, "roomApplyId");
        RoomApply roomApply = (RoomApply) utilService.get(RoomApply.class, id);
        request.setAttribute("roomApply", roomApply);
        String department;
        if (roomApply.getBorrower().getUser().getDefaultCategory().getId()
                .equals(EamsRole.STD_USER)) {
            EntityQuery stdQuery = new EntityQuery(Student.class, "std");
            stdQuery.setSelect("std.department.name");
            stdQuery.add(new Condition("std.code=:stdCode", roomApply.getBorrower().getUser()
                    .getName()));
            List stdlist = (List) utilService.search(stdQuery);
            if (stdlist.isEmpty()) {
                department = null;
            } else {
                department = (String) stdlist.get(0);
            }
        } else {
            EntityQuery teacherQuery = new EntityQuery(Teacher.class, "teacher");
            teacherQuery.setSelect("teacher.department.name");
            teacherQuery.add(new Condition("teacher.code=:teacherCode", roomApply.getBorrower()
                    .getUser().getName()));
            List list = (List) utilService.search(teacherQuery);
            if (list.isEmpty()) {
                department = null;
            } else {
                department = (String) list.get(0);
            }
        }
        request.setAttribute("department", department);
    }
    
    /**
     * @param teachResourceService
     *            The teachResourceService to set.
     */
    public void setTeachResourceService(TeachResourceService teachResourceService) {
        this.teachResourceService = teachResourceService;
    }
}
