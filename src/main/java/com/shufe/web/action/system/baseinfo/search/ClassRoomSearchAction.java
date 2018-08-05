//$Id: ClassRoomSearchAction.java,v 1.1 2008-1-29 下午04:36:53 zhouqi Exp $
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
 * zhouqi              2008-1-29         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.baseinfo.search;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.shufe.service.system.baseinfo.ClassroomService;
import com.shufe.web.action.system.baseinfo.BaseInfoAction;

/**
 * @author zhouqi
 */
public class ClassRoomSearchAction extends BaseInfoAction {
    
    protected ClassroomService classroomService;
    
    /**
     * 教室信息管理主界面
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
        prepare(request);
        return forward(request);
    }
    
    /**
     * 准备数据
     */
    protected void prepare(HttpServletRequest request) {
        addCollection(request, "roomDepartList", getDeparts(request));
        addCollection(request, "classroomConfigTypeList", baseCodeService
                .getCodes(ClassroomType.class));
        addCollection(request, "districtList", baseCodeService.getCodes(SchoolDistrict.class));
    }
    
    /**
     * 查找信息action.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        addCollection(request, "classrooms", baseInfoSearchHelper.searchClassroom(request));
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
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String classroomId = request.getParameter("classroom.id");
        if (StringUtils.isEmpty(classroomId)) {
            classroomId = get(request, "classroomId");
        }
        if (StringUtils.isEmpty(classroomId))
            return forward(mapping, request, new String[] { "entity.classroom",
                    "error.model.id.needed" }, "error");
        addEntity(request, classroomService.getClassroom(Long.valueOf(classroomId)));
        return forward(request);
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = baseInfoSearchHelper.buildClassroomQuery(request);
        query.setLimit(null);
        return utilService.search(query);
    }
    
    public void setClassroomService(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }
    
}
