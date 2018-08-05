//$Id: AdminClassAction.java,v 1.7 2006/12/30 01:29:01 duanth Exp $
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
 * chaostone             2005-9-15         Created
 * zq                   2007-09-18          修改或替换了下面所有的info()方法
 *
 ********************************************************************************/

package com.shufe.web.action.system.baseinfo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.system.baseinfo.importer.AdminClassImportListener;
import com.shufe.service.util.ImporterCodeGenListener;
import com.shufe.util.DataAuthorityUtil;
import com.shufe.web.action.system.baseinfo.search.AdminClassSearchAction;

/**
 * 班级信息管理的action.包括班级信息的增改查.
 * 
 * @author chaostone 2005-9-22
 */
public class AdminClassAction extends AdminClassSearchAction {
    
    /**
     * 修改和新建班级时调用的动作.
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
        AdminClass adminClass = (AdminClass) getEntity(request, AdminClass.class, "adminClass");
        if (ValidEntityPredicate.INSTANCE.evaluate(adminClass)) {
            if (!DataAuthorityUtil.isInDataRealm("AdminClass", adminClass,
                    getStdTypeIdSeq(request), getDepartmentIdSeq(request))) {
                return forwardError(mapping, request, "error.dataRealm.insufficient");
            }
        }
        prepare(request);
        
        addEntity(request, adminClass);
        return forward(request);
    }
    
    /**
     * 保存班级信息，新建的班级或修改的班级.<br>
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
        Map params = getParams(request, "adminClass");
        Long adminClassId = getLong(request, "adminClass.id");
        
        // 检查是否重复
        if (utilService.duplicate(AdminClass.class, adminClassId, "code", request
                .getParameter("adminClass.code"))) {
            return forward(request, new Action("", "edit"), "error.code.existed");
        }
        
        try {
            AdminClass adminClass = null;
            if (null == adminClassId) {
                adminClass = new AdminClass();
                populate(params, adminClass);
                logHelper.info(request, "Create a adminClass with name:" + adminClass.getName());
            } else {
                adminClass = adminClassService.getAdminClass(adminClassId);
                logHelper.info(request, "Update a adminClass with name:" + adminClass.getName());
                populate(params, adminClass);
            }
            ActionForward f = super.saveOrUpdate(request, adminClass);
            if (null != f)
                return f;
        } catch (PojoExistException e) {
            logHelper.info(request, "Failure save a adminClass with name:" + params.get("name"), e);
            return forward(mapping, request, new String[] { "entity.adminClass",
                    "error.model.existed" }, "error");
        } catch (Exception e) {
            logHelper.info(request, "Failure to save adminClass ", e);
            return forward(mapping, request, "error.occurred", "error");
        }
        if (null != request.getParameter("addAnother")) {
            return redirect(request, "edit", "info.save.success");
        } else
            return redirect(request, "search", "info.save.success");
    }
    
    /**
     * 批量更新班级的学生数量
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateStdCount(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String adminClassIdSeq = request.getParameter("adminClassIds");
        adminClassService.batchUpdateStdCountOfClass(adminClassIdSeq);
        return redirect(request, "index", "info.update.success");
    }
    
    /**
     * 把Entity设为AdminClass，在要设置状态前
     * 
     * @return
     */
    protected Class getEntityClazz() {
        return AdminClass.class;
    }
    
    /**
     * 导入班级信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TransferResult tr = new TransferResult();
        Transfer transfer = ImporterServletSupport.buildEntityImporter(request, AdminClass.class,
                tr);
        if (null == transfer) {
            return forward(request, "/pages/components/importData/error");
        }
        
        transfer.addListener(new ImporterForeignerListener(utilService)).addListener(
                new ImporterCodeGenListener(codeGenerator)).addListener(
                new AdminClassImportListener(adminClassService));
        
        transfer.transfer(tr);
        if (tr.hasErrors()) {
            return forward(request, "/pages/components/importData/error");
        } else {
            // String dd= request.getParameter("params");
            return redirect(request, "search", "info.import.success");
        }
    }
    
    protected void onSave(Entity entity) {
        adminClassService.saveOrUpdate((AdminClass) entity);
    }
}
