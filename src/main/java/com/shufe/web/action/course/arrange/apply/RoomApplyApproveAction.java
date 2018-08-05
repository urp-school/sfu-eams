// $Id: Activity.java,v 1.3 2007/01/17 15:06:30 duanth Exp $
/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved. This source code is the property of
 * KINGSTAR MEDIA SOLUTIONS LTD. It is intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source, or other redistribution of this source is
 * not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zq                   2007-10-10          修改了changeFee()方法
 * zq                   2007-10-29          修复了applyRoomSetting()方法中的列表
 *                                          问题
 ********************************************************************************/

package com.shufe.web.action.course.arrange.apply;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.baseinfo.Building;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.security.User;
import com.ekingstar.security.service.UserService;
import com.shufe.model.course.arrange.Activity;
import com.shufe.model.course.arrange.apply.RoomApply;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.calendar.TimeSetting;
import com.shufe.web.helper.LogHelper;

/**
 * 教室申请管理
 * 
 * @author dell
 */
public class RoomApplyApproveAction extends RoomApplyDepartApproveAction {
    
    protected UserService userService;
    
    /**
     * 保存--被修改的申请记录
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
        roomApply.setIsDepartApproved(Boolean.TRUE);
        roomApply.setIsApproved(Boolean.FALSE);
        roomApply.setApproveBy(null);
        roomApply.setApproveAt(null);
        roomApply.getActivities().clear();
        roomApply.setHours(roomApply.getApplyTime().calcHours());
        utilService.saveOrUpdate(roomApply);
        
        // 日志记录
        logHelper.info(request, "修改教室借用");
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 给申请分配教室
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward applyRoomSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        RoomApply roomApply = getRoomApplyDepartApproved(request);
        request.setAttribute("roomApply", roomApply);
        return forward(request);
    }
    
    public ActionForward quickApplySetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String roomIds = get(request, "classroomIds");
        String requestParamter = request.getParameter("requestParamter");
        request.setAttribute("roomIds", roomIds);
        addCollection(request, "classrooms", utilService.load(Classroom.class, "id", SeqStringUtil
                .transformToLong(roomIds)));
        applyFormInitParams(request);
        request.setAttribute("roomApply", populateEntity(request, RoomApply.class, "roomApply"));
        request.setAttribute("requestParamter", requestParamter);
        return forward(request);
    }
    
    /**
     * 得到所有空闲教室<br>
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getFreeRooms(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = getLong(request, "roomApplyId");
        RoomApply roomApply = (RoomApply) utilService.get(RoomApply.class, id);
        
        initBaseCodes(request, "buildings", Building.class);
        initBaseCodes(request, "types", ClassroomType.class);
        initBaseInfos(request, "schoolDistricts", SchoolDistrict.class);
        TimeUnit[] times = roomApply.getApplyTime().convert(
                (TimeSetting) utilService.get(TimeSetting.class, TimeSetting.DEFAULT_ID));
        Classroom room = (Classroom) populate(request, Classroom.class, "room");
        Long schoolDistrictId = getLong(request, "room.schoolDistrict.id");
        if (null != schoolDistrictId) {
            room.setSchoolDistrict((SchoolDistrict) baseInfoService.getBaseInfo(
                    SchoolDistrict.class, schoolDistrictId));
        }
        net.ekingstar.common.detail.Pagination rooms = teachResourceService.getFreeRoomsOf(null,
                times, room, Activity.class, getPageNo(request), getPageSize(request));
        addOldPage(request, "rooms", rooms);
        return forward(request, "freeRoomList");
    }
    
    /**
     * 得到选择物管审核的申请记录
     * 
     * @param request
     * @return
     */
    private RoomApply getRoomApplyDepartApproved(HttpServletRequest request) {
        Long id = getLong(request, "roomApplyId");
        RoomApply roomApply = (RoomApply) utilService.get(RoomApply.class, id);
        request.setAttribute("roomApply", roomApply);
        addCollection(request, "roomConfigTypes", baseCodeService.getCodes(ClassroomType.class));
        return roomApply;
    }
    
    
    /**
     * 审批教室借用
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward approve(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] classroomIds = SeqStringUtil.transformToLong(request.getParameter("roomIds"));
        List classroomList = utilService.load(Classroom.class, "id", classroomIds);
        Set classroomSet = new HashSet(classroomList);
        
        RoomApply roomApply = (RoomApply) populateEntity(request, RoomApply.class, "roomApply");
        roomApply.setApprovedRemark(null);
        roomApplyService.approve(roomApply, getUser(request.getSession()), classroomSet);
        
        logHelper.info(request, LogHelper.DELETE + " + " + LogHelper.UPDATE + " + "
                + LogHelper.INSERTE);
        return redirect(request, "adjustFeeForm", "", "&roomApplyId=" + roomApply.getId());
    }
    
    /**
     * 取消已批准的教室
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long id = getLong(request, "roomApplyId");
        String approvedRemark = request.getParameter("remark");
        RoomApply roomApply = (RoomApply) utilService.get(RoomApply.class, id);
        User approveBy = getUser(request.getSession());
        roomApply.setDepartApproveBy(approveBy);
        roomApply.setApprovedRemark(approvedRemark);
        roomApply.getActivities().clear();
        roomApply.setIsApproved(Boolean.FALSE);
        utilService.saveOrUpdate(roomApply);
        
        return redirect(request, "search", "info.action.success", "&lookContent=" + 2);
    }
    
    /**
     * 修改费用页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward adjustFeeForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("roomApply", utilService.get(RoomApply.class, getLong(request,
                "roomApplyId")));
        return forward(request);
    }
    
    /**
     * 修改教室借用费
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward changeFee(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        RoomApply roomApply = (RoomApply) utilService.get(RoomApply.class, getLong(request,
                "roomApplyId"));
        roomApply.setMoney(getFloat(request, "money"));
        roomApply.setWaterFee(getFloat(request, "waterFee"));
        utilService.saveOrUpdate(roomApply);
        logHelper.info(request, "修改教室借用" + roomApply.getId() + "的费用");
        return redirect(request, "search", "info.action.success", "&lookContent=" + 2);
    }
}
