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
 * ============         ============        ===================================
 * zq                   2007-09-18          修改或替换了下面所有的info()方法
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
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.BookType;
import com.ekingstar.eams.system.basecode.industry.Press;
import com.ekingstar.eams.system.basecode.industry.TextbookAwardLevel;
import com.shufe.model.course.textbook.Textbook;
import com.shufe.service.course.textbook.TextbookService;
import com.shufe.service.system.codeGen.CodeGenerator;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 教材管理响应类
 * 
 * @author chaostone
 * 
 */
public class TextbookAction extends DispatchBasicAction {
    
    private TextbookService textbookService;
    
    private CodeGenerator codeGenerator;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setAttribute("presses", baseCodeService.getCodes(Press.class));
        addCollection(request, "awardLevels", baseCodeService.getCodes(TextbookAwardLevel.class));
        return forward(request);
    }
    
    /**
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
        EntityQuery entityQuery = buildQuery(request);
        addCollection(request, "textbooks", utilService.search(entityQuery));
        return forward(request);
    }
    
    private EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(Textbook.class, "textbook");
        QueryRequestSupport.populateConditions(request, entityQuery);
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return entityQuery;
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery entityQuery = buildQuery(request);
        entityQuery.setLimit(null);
        return utilService.search(entityQuery);
    }
    
    /**
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
        Long textbookId = getLong(request, "textbookId");
        Textbook textbook = null;
        if (null == textbookId) {
            textbook = new Textbook();
            RequestUtil.populate(request, textbook, "textbook", true);
        } else {
            textbook = textbookService.getTextbook(textbookId);
        }
        addCollection(request, "presses", baseCodeService.getCodes(Press.class));
        addCollection(request, "bookTypes", baseCodeService.getCodes(BookType.class));
        addCollection(request, "awardLevels", baseCodeService.getCodes(TextbookAwardLevel.class));
        addSingleParameter(request, "textbook", textbook);
        return forward(request);
    }
    
    /**
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
        Long textbookId = getLong(request, "textbook.id");
        Textbook textbook = null;
        if (null == textbookId) {
            textbook = new Textbook();
        } else {
            textbook = textbookService.getTextbook(textbookId);
        }
        populate(getParams(request, "textbook"), textbook);
        if (!codeGenerator.isValidCode(textbook.getCode())) {
            String code = codeGenerator.gen(textbook, null);
            if (codeGenerator.isValidCode(code)) {
                textbook.setCode(code);
            } else {
                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        "system.codeGen.failure"));
                addErrors(request, messages);
                return forward(request, new Action(this, "edit"));
            }
        }
        
        try {
            textbookService.saveTextbook(textbook);
        } catch (PojoExistException e) {
            logHelper.info(request, "Failure save or update a textbook named" + textbook.getName(),
                    e);
            return forward(mapping, request, "failure");
        } catch (Exception e) {
            logHelper.info(request, "Failure save or update a textbook named" + textbook.getName(),
                    e);
            return forward(mapping, request, "failure");
        }
        return redirect(request, "search", "info.save.success");
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String textbookId = request.getParameter("textbookId");
        if (StringUtils.isEmpty(textbookId))
            return forward(mapping, request, new String[] { "entity.classroom",
                    "error.model.id.needed" }, "error");
        Textbook textbook = textbookService.getTextbook(Long.valueOf(textbookId));
        request.setAttribute("textbook", textbook);
        return forward(request);
    }
    
    /**
     * 删除教材信息
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
        Long textbookId = getLong(request, "textbookId");
        if (null == textbookId) {
            return forward(mapping, request, "error.teachTask.id.needed", "error");
        }
        utilService.remove(textbookService.getTextbook(textbookId));
        return redirect(request, "search", "info.save.success");
    }
    
    /**
     * @param textbookService
     *            The textbookService to set.
     */
    public void setTextbookService(TextbookService textbookService) {
        this.textbookService = textbookService;
    }
    
    public void setCodeGenerator(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }
    
}