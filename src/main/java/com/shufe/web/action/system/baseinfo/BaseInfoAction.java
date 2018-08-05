//$Id: BaseInfoAction.java,v 1.1 2007-2-3 上午04:35:25 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-2-3         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.baseinfo;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.baseinfo.BaseInfo;
import com.shufe.service.system.codeGen.CodeGenerator;
import com.shufe.web.action.system.baseinfo.search.BaseInfoSearchAction;
import com.shufe.web.helper.BaseInfoSearchHelper;

public class BaseInfoAction extends BaseInfoSearchAction {
    
    protected BaseInfoSearchHelper baseInfoSearchHelper;
    
    protected CodeGenerator codeGenerator;
    
    /**
     * 查看课程信息
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
        Entity entity = getOpEntity(request);
        if (null == entity) {
            return forward(mapping, request, new String[] { "entity.course",
                    "error.model.id.needed" }, "error");
        }
        addEntity(request, entity);
        return forward(request);
    }
    
    public Entity getOpEntity(HttpServletRequest request) throws ClassNotFoundException {
        String type = get(request, "type");
        Long id = getLong(request, "id");
        if (null == id) {
            id = getLong(request, StringUtils.lowerCase(type) + "Id");
        }
        if (null == id) {
            return null;
        }
        String className = "com.shufe.model.system.baseinfo." + StringUtils.capitalize(type);
        Entity entity = (Entity) utilService.get(getClass().forName(className), id);
        return entity;
    }
    
    /**
     * 查看基本信息
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
        Entity entity = getOpEntity(request);
        try {
            utilService.remove(Collections.singleton(entity));
        } catch (Exception e) {
            return redirect(request, "search", "info.delete.failure");
        }
        return redirect(request, "search", "info.delete.success");
    }
    
    /**
     * 保存基本信息,没有错误发生时返回null
     * 
     * @param request
     * @param baseInfo
     * @return
     */
    public ActionForward saveOrUpdate(HttpServletRequest request, BaseInfo baseInfo) {
        if (!codeGenerator.isValidCode(baseInfo.getCode())) {
            String code = codeGenerator.gen(baseInfo, null);
            if (codeGenerator.isValidCode(code)) {
                baseInfo.setCode(code);
            } else {
                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        "system.codeGen.failure"));
                addErrors(request, messages);
                return forward(request, new Action(this.getClass(), "edit"));
            }
        }
        baseInfo.setModifyAt(new java.sql.Date(System.currentTimeMillis()));
        if (!baseInfo.isPO()) {
            baseInfo.setCreateAt(new java.sql.Date(System.currentTimeMillis()));
        }
        onSave(baseInfo);
        return null;
    }
    
    /**
     * 批量修改状态
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateState(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] ids = SeqStringUtil.transformToLong(get(request, "ids"));
        Boolean status = getBoolean(request, "status");
        
        List infos = (List) utilService.load(getEntityClazz(), "id", ids);
        
        for (Iterator it = infos.iterator(); it.hasNext();) {
            BaseInfo info = (BaseInfo) it.next();
            info.setState(status);
        }
        utilService.saveOrUpdate(infos);
        
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 设置要设置状态的Entity
     * 
     * @return
     */
    protected Class getEntityClazz() {
        return BaseInfo.class;
    }
    
    protected void onSave(Entity entity) {
        utilService.saveOrUpdate(entity);
    }
    
    public void setBaseInfoSearchHelper(BaseInfoSearchHelper baseInfoSearchHelper) {
        this.baseInfoSearchHelper = baseInfoSearchHelper;
    }
    
    public void setCodeGenerator(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }
    
}
