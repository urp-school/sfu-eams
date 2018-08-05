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
 * chaostone             2007-1-2            Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.grade.gp;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.eams.course.grade.converter.ConverterFactory;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.security.User;
import com.ekingstar.security.management.RoleManager;
import com.shufe.model.course.grade.gp.GPMapping;
import com.shufe.model.course.grade.gp.GradePointRule;
import com.shufe.service.course.grade.gp.GradePointRuleService;
import com.shufe.util.DataAuthorityUtil;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 绩点映射规则响应类
 * 
 * @author chaostone
 */
public class GradePointRuleAction extends RestrictionSupportAction {
    
    public GradePointRuleService gradePointRuleService;
    
    /**
     * 绩点映射管理主界面
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
        List gradePointRuleList = utilService.loadAll(GradePointRule.class);
        request.setAttribute("gradePointRuleList", gradePointRuleList);
        return forward(request);
    }
    
    /**
     * 列举某一学生类别的绩点对照表
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
        Long gpRuleId = getLong(request, "gradePointRuleId");
        GradePointRule gradePointRule = (GradePointRule) utilService.get(GradePointRule.class,
                gpRuleId);
        if (!inAuthority(request, gradePointRule)) {
            return forwardError(mapping, request, "error.dataRealm.insufficient");
        } else {
            utilService.remove(gradePointRule);
        }
        return redirect(request, "index", "info.delete.success");
    }
    
    /**
     * 修改或新建绩点映射规则
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
        Long gpRuleId = getLong(request, "gradePointRuleId");
        GradePointRule gradePointRule = null;
        if (null == gpRuleId) {
            gradePointRule = new GradePointRule();
        } else {
            gradePointRule = (GradePointRule) utilService.get(GradePointRule.class, gpRuleId);
            boolean inAuhority = inAuthority(request, gradePointRule);
            request.setAttribute("defaultConfig", ConverterFactory.getConverter().getConfig(
                    gradePointRule.getMarkStyle()));
            if (!inAuhority) {
                return forwardError(mapping, request, "error.dataRealm.insufficient");
            }
        }
        request.setAttribute("gradePointRule", gradePointRule);
        List stdTypes = studentTypeService.getStudentTypes(getStdTypeIdSeq(request));
        addCollection(request, "markStyles", baseCodeService.getCodes(MarkStyle.class));
        addCollection(request, "stdTypes", stdTypes);
        return forward(request);
    }
    
    /**
     * 查看绩点映射规则
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
        Long gpRuleId = getLong(request, "gradePointRuleId");
        GradePointRule gradePointRule = (GradePointRule) utilService.get(GradePointRule.class,
                gpRuleId);
        request.setAttribute("gradePointRule", gradePointRule);
        request.setAttribute("inAuthority", Boolean.valueOf(inAuthority(request, gradePointRule)));
        return forward(request);
    }
    
    /**
     * 保存新增或修改
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
        GradePointRule gradePointRule = (GradePointRule) populateEntity(request,
                GradePointRule.class, "gradePointRule");
        if (Boolean.TRUE.equals(gradePointRuleService.checkDuplicate(gradePointRule))) {
            return redirect(request, "index", "error.code.saveFailure");
        }
        utilService.saveOrUpdate(gradePointRule);
        return redirect(request, "index", "info.save.success");
    }
    
    /**
     * 删除选中的绩点对照表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeGPMapping(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String GPMappingIds = request.getParameter("GPMappingIds");
        utilService.remove(GPMapping.class, "id", SeqStringUtil.transformToLong(GPMappingIds));
        return redirect(request, "edit", "info.delete.success");
        
    }
    
    /**
     * 保存新增的单个绩点对照
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveGPMapping(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long gpRuleId = getLong(request, "gradePointRuleId");
        GradePointRule rule = (GradePointRule) utilService.get(GradePointRule.class, gpRuleId);
        
        GPMapping gPMapping = (GPMapping) RequestUtil.populate(request, GPMapping.class,
                "GPMapping");
        gPMapping.getRule().setId(gpRuleId);
        
        rule.getGPMappings().add(gPMapping);
        utilService.saveOrUpdate(rule);
        return redirect(request, "edit", "info.save.success");
        
    }
    
    /**
     * 批量保存修改的绩点对照
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchSaveGPMapping(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long gpRuleId = getLong(request, "gradePointRuleId");
        GradePointRule gradePointRule = (GradePointRule) utilService.get(GradePointRule.class,
                gpRuleId);
        for (Iterator iter = gradePointRule.getGPMappings().iterator(); iter.hasNext();) {
            GPMapping gpMapping = (GPMapping) iter.next();
            gpMapping.setMaxScore(getFloat(request, "maxScore" + gpMapping.getId()));
            gpMapping.setMinScore(getFloat(request, "minScore" + gpMapping.getId()));
            gpMapping.setGp(getFloat(request, "gp" + gpMapping.getId()));
        }
        utilService.saveOrUpdate(gradePointRule);
        return redirect(request, "edit", "info.save.success");
    }
    
    /**
     * 判断改绩点规则是否在用户权限范围内
     * 
     * @param request
     * @param rule
     * @return
     */
    private boolean inAuthority(HttpServletRequest request, GradePointRule rule) {
        if (rule.getStdType() == null || rule.getId().equals(GradePointRule.defaultRuleId)) {
            Long userId = getUserId(request.getSession());
            User user = (User) utilService.get(User.class, userId);
            if (!((RoleManager) user).isRoleAdmin())
                return false;
        } else {
            if (!DataAuthorityUtil.isInDataRealm(DataAuthorityUtil.predicateWithSimpleName, rule,
                    getStdTypeIdSeq(request), ""))
                return false;
        }
        return true;
    }
    
    /**
     * @param gradePointRuleService
     *            the gradePointRuleService to set
     */
    public void setGradePointRuleService(GradePointRuleService gradePointRuleService) {
        this.gradePointRuleService = gradePointRuleService;
    }
}
