package com.shufe.web.action.course.arrange.apply;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.model.course.arrange.apply.RoomApply;
import com.shufe.web.action.common.DispatchBasicAction;

public class RoomApplyStatAction extends DispatchBasicAction {
    
    /**
     * 教室借用统计
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
        EntityQuery query = new EntityQuery(RoomApply.class, "roomApply");
        query.add(new Condition("roomApply.isApproved = true and roomApply.isDepartApproved=true" ));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "roomApplies", utilService.search(query));
        return forward(request);
      
    }
}
    