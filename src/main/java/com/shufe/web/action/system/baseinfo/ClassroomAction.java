//$Id: ClassroomAction.java,v 1.8 2006/12/30 01:29:01 duanth Exp $
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
 * chaostone            2005-09-15          Created
 * zq                   2007-09-18          修改或替换了下面所有的info()方法
 *  
 ********************************************************************************/

package com.shufe.web.action.system.baseinfo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.util.DataAuthorityUtil;
import com.shufe.web.action.system.baseinfo.search.ClassRoomSearchAction;

/**
 * 教室信息管理的action.包括教室信息的增改查.
 * 
 * @author chaostone 2005-9-22
 */
public class ClassroomAction extends ClassRoomSearchAction {
    
    /**
     * 修改和新建教室时调用的动作.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Classroom classroom = (Classroom) getEntity(request, Classroom.class, "classroom");
        if (ValidEntityPredicate.INSTANCE.evaluate(classroom)) {
            if (!DataAuthorityUtil.isInDataRealm("Classroom", classroom, null,
                    getDepartmentIdSeq(request)))
                return forwardError(mapping, request, "error.depart.dataRealm.insufficient");
        }
        prepare(request);
        addEntity(request, classroom);
        return forward(request);
    }
    
    /**
     * 保存教室信息，新建的教室或修改的教室. <br>
     * 接受主键冲突异常，跳转到异常页面.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long roomId = getLong(request, "classroom.id");
        
        // 检查是否重复
        if (utilService.duplicate(Classroom.class, roomId, "code", request
                .getParameter("classroom.code"))) {
            return forward(request, new Action("", "edit"), "error.code.existed");
        }
        
        Classroom classroom = null;
        Map params = getParams(request, "classroom");
        /*-----------------准备教室的使用部门----------------------------------*/
        String departIdSeq = request.getParameter("useDepartIds");
        if (StringUtils.isEmpty(departIdSeq))
            return forwardError(mapping, request, "useDepart.notExists");
        
        Long[] departIds = SeqStringUtil.transformToLong(departIdSeq);
        Set userDeparts = new HashSet();
        for (int i = 0; i < departIds.length; i++) {
            Department oneDepart = new Department();
            oneDepart.setId(departIds[i]);
            userDeparts.add(oneDepart);
        }
        try {
            if (null == roomId) {
                classroom = new Classroom();
                populate(params, classroom);
                logHelper.info(request, "Create a classroom with name:" + classroom.getName());
                classroom.setDepartments(userDeparts);
                //EntityUtils.evictEmptyProperty(classroom);
            } else {
                classroom = classroomService.getClassroom(roomId);
                logHelper.info(request, "Update a classroom with name:" + classroom.getName());
                populate(params, classroom);
                if (null == classroom.getDepartments())
                    classroom.setDepartments(userDeparts);
                else {
                    Collection tobeDeleted = CollectionUtils.subtract(classroom.getDepartments(),
                            userDeparts);
                    Collection tobeAdded = CollectionUtils.subtract(userDeparts, classroom
                            .getDepartments());
                    classroom.getDepartments().removeAll(tobeDeleted);
                    classroom.getDepartments().addAll(tobeAdded);
                }
            }
            ActionForward f = super.saveOrUpdate(request, classroom);
            if (null != f)
                return f;
        } catch (PojoExistException e) {
            logHelper.info(request, "Failure save or update a classroom with name:"
                    + classroom.getName(), e);
            return forward(mapping, request, new String[] { "entity.classroom",
                    "error.model.existed" }, "error");
        } catch (Exception e) {
            logHelper.info(request, "Failure save or update a classroom with name:"
                    + classroom.getName(), e);
            return forward(mapping, request, "error.occurred", "error");
        }
        if (null != request.getParameter("addAnother")) {
            return redirect(request, "edit", "info.save.success");
        } else
            return redirect(request, "search", "info.save.success");
    }
    
}
