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
 * zq                   2007-09-18          修改或替换了下面所有的info()方法
 ********************************************************************************/

package com.shufe.web.action.system.baseinfo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.web.action.system.baseinfo.search.StudentTypeSearchAction;

/**
 * 学生类别信息管理action,包括增改查.
 * 
 * @author chaostone
 */
public class StudentTypeAction extends StudentTypeSearchAction {
    
    /**
     * 修改和新建学生类别时调用的动作.
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
        StudentType studentType = (StudentType) getEntity(request, StudentType.class, "studentType");
        if (ValidEntityPredicate.INSTANCE.evaluate(studentType)) {
            String stdTypeIds = getStdTypeIdSeq(request);
            if (!StringUtils.contains(stdTypeIds, studentType.getId().toString())) {
                return forwardError(mapping, request, "error.stdType.dataRealm.insufficient");
            }
        }
        Long superTypeId = getLong(request, "superTypeId");
        if (null != superTypeId) {
            studentType.setSuperType(studentTypeService.getStudentType(superTypeId));
        }
        addEntity(request, studentType);
        List superStdTypes = getStdTypes(request);
        if (studentType.isPO()) {
            superStdTypes.remove(studentType);
            for (Iterator iter = studentType.getSubTypes().iterator(); iter.hasNext();) {
                superStdTypes.remove(iter.next());
            }
        }
        addCollection(request, "superStdTypes", superStdTypes);
        
        return forward(request);
    }
    
    /**
     * 保存学生类别信息，新建的学生类别或修改的学生类别. <br>
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
        Long studentTypeId = getLong(request, "studentType.id");
        
        // 检查是否重复
        if (utilService.duplicate(StudentType.class, studentTypeId, "code", request
                .getParameter("studentType.code"))) {
            return forward(request, new Action("", "edit"), "error.code.existed");
        }
        
        StudentType studentType = null;
        Map params = getParams(request, "studentType");
        try {
            if (null == studentTypeId) {
                studentType = (StudentType) EntityUtils.getInstance(StudentType.class);
            } else {
                studentType = studentTypeService.getStudentType(studentTypeId);
            }
            String departIdSeq = request.getParameter("departIdSeq");
            List departs = utilService.load(Department.class, "id", SeqStringUtil
                    .transformToLong(departIdSeq));
            populate(params, studentType);
            if (!studentType.isPO()) {
                logHelper.info(request, "Create a studentType with name:" + studentType.getName());
                EntityUtils.evictEmptyProperty(studentType);
            } else {
                logHelper.info(request, "Update a studentType with name:" + studentType.getName());
            }
            // 添加学生类别相关的部门
            // studentType.getDeparts().clear();
            // studentType.getDeparts().addAll(departs);
            
            ActionForward f = super.saveOrUpdate(request, studentType);
            if (null != f)
                return f;
        } catch (PojoExistException e) {
            logHelper.info(request, "Failure save or update a studentType with name:"
                    + studentType.getName(), e);
            return forward(mapping, request, new String[] { "entity.studentType",
                    "error.model.existed" }, "error");
        } catch (Exception e) {
            logHelper.info(request, "Failure save or update a studentType with name:"
                    + studentType.getName(), e);
            return forward(mapping, request, "error.occurred", "error");
        }
        return redirect(request, "search", "info.save.success");
    }
    
    public Entity getOpEntity(HttpServletRequest request) throws ClassNotFoundException {
        Long id = getLong(request, "id");
        if (null == id) return null;
        Entity entity = (Entity) utilService.get(StudentType.class, id);
        return entity;
    }
    
}
