
package com.shufe.web.action.course.arrange.apply;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.shufe.web.action.common.DispatchBasicAction;

public class RoomApplyApprovedSearchAction extends DispatchBasicAction {
    
    /**
     * 归口审核
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
        return forward(request);
    }
    
    /**
     * 查询物管审核已通过的教室申请
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
        
        String sqlStr = "from RoomApply roomApply where 1=1";
        Map paramsMap = new HashMap();
        
        String dateBegin = request.getParameter("dateBegin");
        String dateEnd = request.getParameter("dateEnd");
        String timeBegin = request.getParameter("timeBegin");
        String timeEnd = request.getParameter("timeEnd");
        
        String datePattern = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern(datePattern);
        Boolean isMultimedia = getBoolean(request, "isMultimedia");
        
        if (null != isMultimedia) {
            sqlStr += " and roomApply.isMultimedia=:isMultimedia";
            paramsMap.put("isMultimedia", isMultimedia);
        }
        
        if (!dateBegin.equals("") && !dateEnd.equals("")) {
            Date sdate = df.parse(dateBegin);
            Date edate = df.parse(dateEnd);
            sqlStr += " and trunc(roomApply.approveAt)>=:sdate and trunc(roomApply.approveAt)<=:edate";
            paramsMap.put("sdate", sdate);
            paramsMap.put("edate", edate);
        }
        
        if (!timeBegin.equals("") && !timeEnd.equals("")) {
            sqlStr += " and to_char(roomApply.approveAt,'hh24:mi')>=:timeBegin and to_char(roomApply.approveAt,'hh24:mi')<=:timeEnd";
            paramsMap.put("timeBegin", timeBegin);
            paramsMap.put("timeEnd", timeEnd);
        }
        sqlStr += " and roomApply.isDepartApproved = true and roomApply.isApproved = true order by roomApply.approveAt desc";
        
        EntityQuery query = new EntityQuery();
        query.setQueryStr(sqlStr);
        query.setParams(paramsMap);
        query.setLimit(getPageLimit(request));
        addCollection(request, "roomApplies", utilService.search(query));
        return forward(request);
    }
}
