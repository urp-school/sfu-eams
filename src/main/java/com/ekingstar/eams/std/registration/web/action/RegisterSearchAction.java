//$Id: RegisterSearchAction.java,v 1.1 2007-5-26 下午01:46:33 chaostone Exp $
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
 *chaostone      2007-5-26         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.registration.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.std.registration.model.Register;
import com.ekingstar.eams.system.basecode.industry.RegisterState;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;
import com.shufe.web.helper.StdSearchHelper;

/**
 * 注册信息查询管理
 * 
 * @author chaostone
 * 
 */
public class RegisterSearchAction extends CalendarRestrictionSupportAction {
    
    protected StdSearchHelper stdSearchHelper;
    
    /**
     * 管理人员查看单个学生在各个学期的注册情况
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        Long registerId = getLong(request, "registerId");
        Register register = (Register) utilService.get(Register.class, registerId);
        EntityQuery query = new EntityQuery(Register.class, "register");
        query.add(Condition.eq("register.std.id", register.getStd().getId()));
        request.setAttribute("register", register);
        request.setAttribute("registers", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 查询学生注册信息
     * 
     * @param form
     * @param request
     * @param moduleName
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        Boolean registed = getBoolean(request, "registed");
        EntityQuery query = buildQuery2(request);
        // 注册完成
        if (Boolean.TRUE.equals(registed)) {
            addCollection(request, "registers", utilService.search(query));
            return forward(request);
        } else {
            // 未注册完成
            addCollection(request, "registers", utilService.search(query));
            // 加入注册状态
            addCollection(request, "registerStates", baseCodeService.getCodes(RegisterState.class));
            return forward(request, "unregisterList");
        }
    }
    
    protected EntityQuery buildQuery2(HttpServletRequest request) {
        Boolean registed = getBoolean(request, "registed");
        EntityQuery query = stdSearchHelper.buildStdQuery(request, "calendar.studentType.id");
        // 添加学生学籍有效,并且学籍有效的查询条件
        query.add(new Condition("std.inSchool = true and std.active = true"));
        query.setAlias("register");
        query.setSelect("register");
        query.setEntityClass(Register.class);
        populateConditions(request, query);
        query.setFrom("from Register  register join register.std std "
                + StringUtils.substringAfter(query.getFrom(), " std"));
        // 注册完成
        if (null != registed) {
            query.add(new Condition("register.isPassed = :isPassed", registed));
        }
        return query;
    }
    
    public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
        this.stdSearchHelper = stdSearchHelper;
    }
}
