//$Id: PressAction.java,v 1.1 2007-3-12 下午01:03:54 chaostone Exp $
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
 *chaostone      2007-3-12         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.textbook;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.Press;
import com.ekingstar.eams.system.basecode.industry.PressLevel;
import com.shufe.service.system.codeGen.CodeGenerator;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 出版社管理
 * 
 * @author chaostone
 */
public class PressAction extends DispatchBasicAction {
    
    private CodeGenerator codeGenerator;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = buildQuery(request);
        addCollection(request, "presses", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 建立查询语句
     * 
     * @param request
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(Press.class, "press");
        populateConditions(request, query);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return query;
    }
    
    /**
     * 编辑出版社
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
        addCollection(request, "pressLevels", baseCodeService.getCodes(PressLevel.class));
        Long codeId = getLong(request, "pressId");
        Press press = null;
        if (null != codeId)
            press = (Press) baseCodeService.getCode(Press.class, codeId);
        if (null == press) {
            press = new Press();
        }
        request.setAttribute("press", press);
        return forward(request);
    }
    
    /**
     * 保存基础代码信息，
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
        Press press = (Press) populateEntity(request, Press.class, "press");
        if (StringUtils.isEmpty(press.getCode()) || CodeGenerator.MARK.equals(press.getCode())) {
            String code = codeGenerator.gen(press, null);
            if (codeGenerator.isValidCode(code)) {
                press.setCode(code);
            } else {
                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE,
                        new ActionMessage("info.action.failure"));
                addErrors(request, messages);
                return forward(request, new Action(this.getClass(), "edit"));
            }
        }
        try {
            baseCodeService.saveOrUpdate(press);
        } catch (PojoExistException e) {
            return forwardError(mapping, request, new String[] { "entity.baseCode",
                    "error.model.existed" });
        }
        return redirect(request, "search", "info.save.success");
    }
    
    /**
     * 出版社导出
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = buildQuery(request);
        query.setLimit(null);
        return utilService.search(query);
    }
    
    public void setCodeGenerator(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }
}
