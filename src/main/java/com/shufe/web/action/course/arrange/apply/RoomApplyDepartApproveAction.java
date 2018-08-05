package com.shufe.web.action.course.arrange.apply;

import java.util.Collection;
import java.util.Date;

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
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.shufe.model.course.arrange.apply.ApplyTime;
import com.shufe.model.course.arrange.apply.RoomApply;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.service.course.arrange.apply.RoomApplyService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.helper.LogHelper;

/**
 * 归口审核和管理界面
 * 
 * @author zhouqi
 */
public class RoomApplyDepartApproveAction extends RoomApplyAction {
    
    protected RoomApplyService roomApplyService;
    
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
        initBaseCodes(request, "roomConfigTypes", ClassroomType.class);
        initBaseCodes(request, "schoolDistricts", SchoolDistrict.class);
        return forward(request);
    }
    
    /**
     * 修改教室申请
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        getRoomApplyDatas(request);
        request.setAttribute("departments", getDeparts(request));
        return forward(request);
    }
    
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
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = getRoomApplySearch(request);
        addCollection(request, "roomApplies", utilService.search(query));
        return forward(request);
    }
    
    protected EntityQuery getRoomApplySearch(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(RoomApply.class, "roomApply");
        // query.getConditions().add(ConditionUtils.extractConditions("roomApply",
        // new
        // RoomApply()));
        populateConditions(request, query);
        Classroom room = (Classroom) populateEntity(request, Classroom.class, "classroom");
        DataRealmUtils.addDataRealm(query, new String[] { null, "roomApply.auditDepart.id" },
                getDataRealm(request));
        ApplyTime applyTime = (ApplyTime) populate(request, ApplyTime.class, "applyTime");
        if (null != applyTime.getDateBegin() && null != applyTime.getDateEnd()) {
            query
                    .add(new Condition(
                            "roomApply.applyTime.dateBegin <= :dateEnd  and :dateBeign <= roomApply.applyTime.dateEnd",
                            applyTime.getDateEnd(), applyTime.getDateBegin()));
        }
        if (StringUtils.isNotEmpty(applyTime.getTimeBegin())
                && StringUtils.isNotEmpty(applyTime.getTimeEnd())) {
            query
                    .add(new Condition(
                            "roomApply.applyTime.timeBegin <= :timeEnd  and :timeBeign <= roomApply.applyTime.timeEnd",
                            applyTime.getTimeEnd(), applyTime.getTimeBegin()));
        }
        String lookContent = request.getParameter("lookContent");
        if ("".equals(lookContent) || lookContent == null) {
            query.add(new Condition(
                    "roomApply.isDepartApproved is null and roomApply.isApproved is null"));
        } else if (lookContent.equals("0")) {
            query.add(new Condition("roomApply.isDepartApproved = 0 and roomApply.isApproved = 0"));
        } else if (lookContent.equals("1")) {
            query.add(new Condition("roomApply.isDepartApproved = 1 and roomApply.isApproved = 0"));
        } else if (lookContent.equals("2")) {
            query.add(new Condition("roomApply.isDepartApproved = 1 and roomApply.isApproved = 1"));
        }
        if (room.getName() != null) {
            if (!room.getName().equals("") || null != room.getConfigType()) {
                query.join("right", "roomApply.activities", "activity");
                if (!room.getName().equals("")) {
                    query.add(Condition.like("activity.room.name", room.getName()));
                }
                if (null != room.getConfigType()) {
                    query.add(new Condition("activity.room.configType.id=:id", room.getConfigType()
                            .getId()));
                }
            }
        }
        
        EntityQuery parentQuery = new EntityQuery(RoomApply.class, "roomApply");
        parentQuery.add(new Condition("roomApply.id in ("
                + query.toQueryString().replaceAll("roomApply", "subRoomApply") + ")"));
        parentQuery.setSelect("roomApply");
        parentQuery.setParams(query.getParams());
        parentQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        parentQuery.setLimit(getPageLimit(request));
        
        return parentQuery;
    }
    
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
        roomApply.reset();
        roomApply.setHours(roomApply.getApplyTime().calcHours());
        utilService.saveOrUpdate(roomApply);
        // 日志记录
        logHelper.info(request, LogHelper.UPDATE);
        return redirect(request, "search", "info.action.success");
    }
    
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
    public ActionForward departApprove(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String departApprovedRemark = request.getParameter("remark");
        RoomApply roomApply = (RoomApply) utilService.load(RoomApply.class, new Long(request
                .getParameter("roomApplyId")));
        roomApply.setDepartApprovedRemark(departApprovedRemark);
        roomApplyService.departApprove(roomApply, getUser(request.getSession()));
        // 日志记录
        logHelper.info(request, LogHelper.UPDATE);
        return redirect(request, "search", "info.action.success");
    }
    
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
    public ActionForward departCancel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String departApprovedRemark = request.getParameter("remark");
        RoomApply roomApply = (RoomApply) utilService.load(RoomApply.class, new Long(request
                .getParameter("roomApplyId")));
        roomApply.setDepartApprovedRemark(departApprovedRemark);
        roomApplyService.departCancel(roomApply, getUser(request.getSession()));
        // 日志记录
        logHelper.info(request, LogHelper.UPDATE);
        return redirect(request, "search", "info.action.success");
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
        utilService.remove(utilService.load(RoomApply.class, new Long(request
                .getParameter("roomApplyIds"))));
        logHelper.info(request, LogHelper.DELETE);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 查看审批表
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
        return forward(request, new Action(RoomApplyAction.class, "info"));
    }
    
    /**
     * 打印所有选择的已批准教室记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // 从页面获得 id 集
        Long[] roomApplyIds = SeqStringUtil.transformToLong(request.getParameter("roomApplyIds"));
        // 查询所有指定 id 记录
        addCollection(request, "printRoomApplieds", utilService.load(RoomApply.class, "id",
                roomApplyIds));
        addCollection(request, "catalogues", getRoomPriceCatalogues(request));
        request.setAttribute("nowDate", new Date());
        
        return request.getParameter("selectStyle").equals("1") ? forward(request, "print1")
                : forward(request, "print2");
    }
    
    /**
     * @param roomApplyService
     *            要设置的 roomApplyService.
     */
    public void setRoomApplyService(RoomApplyService roomApplyService) {
        this.roomApplyService = roomApplyService;
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = getRoomApplySearch(request);
        return utilService.search(query);
    }
    
}
