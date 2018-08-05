//$Id: SpecialityAspectAction.java,v 1.5 2006/12/30 01:29:01 duanth Exp $
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
 ********************************************************************************/

package com.shufe.web.action.system.baseinfo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.util.DataAuthorityUtil;
import com.shufe.web.action.system.baseinfo.search.SpecialityAspectSearchAction;

public class SpecialityAspectAction extends SpecialityAspectSearchAction {
    
    /**
     * s 修改和新建专业方向时调用的动作.<br>
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
        String departDataRealm = getDepartmentIdSeq(request);
        String stdTypeDataRealm = getStdTypeIdSeq(request);
        SpecialityAspect specialityAspect = (SpecialityAspect) getEntity(request,
                SpecialityAspect.class, "specialityAspect");
        if (ValidEntityPredicate.INSTANCE.evaluate(specialityAspect)) {
            if (!DataAuthorityUtil.isInDataRealm("Speciality", specialityAspect.getSpeciality(),
                    stdTypeDataRealm, departDataRealm))
                return forwardError(mapping, request, "error.depart.dataRealm.insufficient");
        }
        addCollection(request, "departmentList", getDeparts(request));
        addCollection(request, "stdTypeList", getStdTypes(request));
        addEntity(request, specialityAspect);
        return forward(request);
    }
    
    /**
     * 保存专业方向信息.<br>
     * 新建的专业方向或修改的专业方向.<br>
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
        Long specialityAspectId = getLong(request, "specialityAspect.id");
        
        // 检查是否重复
        if (utilService.duplicate(SpecialityAspect.class, specialityAspectId, "code", request
                .getParameter("specialityAspect.code"))) {
            return forward(request, new Action("", "edit"), "error.code.existed");
        }
        
        SpecialityAspect specialityAspect = null;
        Map params = getParams(request, "specialityAspect");
        try {
            if (null == specialityAspectId) {
                specialityAspect = new SpecialityAspect();
                populate(params, specialityAspect);
                logHelper.info(request, "Create a specialityAspect with name:"
                        + specialityAspect.getName());
            } else {
                specialityAspect = specialityAspectService.getSpecialityAspect(specialityAspectId);
                logHelper.info(request, "Update a specialityAspect with name:"
                        + specialityAspect.getName());
                populate(params, specialityAspect);
            }
            ActionForward f = super.saveOrUpdate(request, specialityAspect);
            if (null != f)
                return f;
        } catch (PojoExistException e) {
            logHelper.info(request, "Failure save or update a specialityAspect with name:"
                    + specialityAspect.getName(), e);
            return forward(mapping, request, new String[] { "entity.specialityAspect",
                    "error.model.existed" }, "error");
        } catch (Exception e) {
            logHelper.info(request, "Failure save or update a specialityAspect with name:"
                    + specialityAspect.getName(), e);
            return forward(mapping, request, "error.occurred", "error");
        }
        return redirect(request, "search", "info.save.success");
    }
}
