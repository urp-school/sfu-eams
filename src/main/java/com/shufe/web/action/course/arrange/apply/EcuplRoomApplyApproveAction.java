//$Id: EcuplRoomApplyApproveAction.java,v 1.1 2009-2-23 上午10:17:15 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2009-2-23             Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.arrange.apply;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.security.User;
import com.shufe.model.course.arrange.apply.RoomApply;
import com.shufe.model.system.baseinfo.Classroom;

/**
 * @author zhouqi
 * 
 */   
public class EcuplRoomApplyApproveAction extends RoomApplyApproveAction {
    
	
    public ActionForward quickApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        User borrower = (User) utilService.load(User.class, getLong(request, "userId"));
        RoomApply roomApply = (RoomApply) populateEntity(request, RoomApply.class, "roomApply");
        roomApply.getBorrower().setUser(borrower);
        roomApply.setApplicant(borrower.getName());
        User user = getUser(request);
        roomApply.setDepartApproveBy(user);
        roomApply.setDepartApproveAt(new Timestamp(System.currentTimeMillis()));
        roomApply.setIsDepartApproved(Boolean.TRUE);
        roomApply.setIsApproved(Boolean.TRUE);
        roomApply.getActivities().clear();
        String requestParamter = request.getParameter("requestParamter");
        roomApply.setHours(roomApply.getApplyTime().calcHours());
        if (!roomApplyService.approve(roomApply, user, new HashSet(utilService.load(
                Classroom.class, "id", SeqStringUtil.transformToLong(get(request, "roomIds")))))) {
            return forward(request, new Action(this, "search"), "error.timeHasBeen");
        }
        return redirect(request, "search", "info.action.success", "&lookContent=2"+requestParamter);
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        buildRoomApplyInfo(request);
        addSingleParameter(request, "now", new Date());
        return forward(request);
    }
    
}
