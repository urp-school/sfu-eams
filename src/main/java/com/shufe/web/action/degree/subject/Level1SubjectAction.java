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
 * chaostone            2006-08-10          Created
 * zq                   2007-09-18          修改或替换了下面所有的info()方法
 ********************************************************************************/

package com.shufe.web.action.degree.subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.state.SubjectCategory;
import com.shufe.model.degree.subject.Level1Subject;
import com.shufe.service.degree.subject.Level1SubjectService;
import com.shufe.service.system.codeGen.CodeGenerator;
import com.shufe.web.action.common.DispatchBasicAction;

public class Level1SubjectAction extends DispatchBasicAction {
    
    private Level1SubjectService subjectService;
    
    private CodeGenerator codeGenerator;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addOldPage(request, "level1Subjects", subjectService.getLevel1Subject(
                (Level1Subject) populate(request, Level1Subject.class), getPageNo(request),
                getPageSize(request)));
        return forward(request);
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Level1Subject level1Subject = null;
        String level1SubjectId = request.getParameter("level1Subject.id");
        if (StringUtils.isNotEmpty(level1SubjectId)) {
            level1Subject = (Level1Subject) utilService.get(Level1Subject.class, new Long(
                    level1SubjectId));
            
        } else {
            level1Subject = (Level1Subject) populate(request, Level1Subject.class);
        }
        Results.addList("categoryList", baseCodeService.getCodes(SubjectCategory.class));
        addEntity(request, level1Subject);
        return forward(request);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // 获取form中的信息
        Level1Subject level1Subject = (Level1Subject) populate(request, Level1Subject.class);
        try {
            if (!codeGenerator.isValidCode(level1Subject.getCode())) {
                String code = codeGenerator.gen(level1Subject, null);
                if (codeGenerator.isValidCode(code)) {
                    level1Subject.setCode(code);
                } else {
                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                            "system.codeGen.failure"));
                    addErrors(request, messages);
                    return forward(request, new Action(this, "edit"));
                }
            }
            Level1Subject savedSubject = null;
            if (null != level1Subject.getId()) {
                savedSubject = (Level1Subject) utilService.get(Level1Subject.class, level1Subject
                        .getId());
            } else {
                savedSubject = new Level1Subject();
            }
            if (utilService.duplicate(Level1Subject.class, level1Subject.getId(), "code",
                    level1Subject.getCode())) {
                return redirect(request, "index", "error.code.saveFailure");
            }
            
            if (!level1Subject.isPO()) {
                logHelper.info(request, "Create a level1Subject with name:"
                        + level1Subject.getName());
                EntityUtils.evictEmptyProperty(level1Subject);
                utilService.saveOrUpdate(level1Subject);
            } else {
                EntityUtils.merge(savedSubject, level1Subject);
                utilService.saveOrUpdate(savedSubject);
                logHelper.info(request, "Update a level1Subject with name:"
                        + level1Subject.getName());
            }
        } catch (PojoExistException e) {
            logHelper.info(request, "Failure save or update a level1Subject with name:"
                    + level1Subject.getName(), e);
            return forward(mapping, request, new String[] { "entity.level1Subject",
                    "error.model.existed" }, "error");
        }
        if (null != request.getParameter("addAnother")) {
            return redirect(request, "edit", "info.save.success");
        } else
            return redirect(request, "index", "info.save.success");
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        DynaActionForm dynaForm = (DynaActionForm) form;
        
        int pageNo = ((Integer) dynaForm.get("pageNo")).intValue();
        int pageSize = ((Integer) dynaForm.get("pageSize")).intValue();
        
        Level1Subject level1Subject = (Level1Subject) populate(request, Level1Subject.class);
        Pagination level1SubjectList = subjectService.getLevel1Subject(level1Subject, pageSize,
                pageNo);
        
        Results.addPagination("level1SubjectList", level1SubjectList);
        return forward(request);
    }
    
    public void setLevel1SubjectService(Level1SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String level1SubjectId = request.getParameter("level1Subject.id");
        if (StringUtils.isEmpty(level1SubjectId))
            return forward(mapping, request, new String[] { "entity.level1Subject",
                    "error.model.id.needed" }, "error");
        Level1Subject level1Subject = (Level1Subject) utilService.get(Level1Subject.class,
                new Long(level1SubjectId));
        request.setAttribute("level1Subject", level1Subject);
        return forward(request);
    }
    
    public void setCodeGenerator(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }
    
}
