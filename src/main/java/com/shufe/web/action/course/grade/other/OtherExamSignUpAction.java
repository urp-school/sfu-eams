//$Id: OtherExamSignUpAction.java,v 1.1 2007-2-25 下午10:38:26 chaostone Exp $
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
 * Name             Date            Description 
 * ============     ============    ============
 * chaostone        2007- 2-25      Created
 * zq               2007-09-13      在buildQuery()方法中，添加了addStdTypeTreeDataRealm(...)
 *                                  方法
 * zq               2007-10-08      增加一删除方法
 * zq               2007-10-29      在保存的方法中，增加了判断保存数据是否重复的
 *                                  功能
 ********************************************************************************/

package com.shufe.web.action.course.grade.other;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;
import com.shufe.model.course.grade.other.OtherExamSignUp;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 其他考试报名管理
 * 
 * @author chaostone
 */
public class OtherExamSignUpAction extends CalendarRestrictionSupportAction {
    
    /**
     * 主界面
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
        setCalendarDataRealm(request, hasStdTypeCollege);
        addCollection(request, "otherExamCategories", baseCodeService
                .getCodes(OtherExamCategory.class));
        return forward(request);
    }
    
    /**
     * 查询
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
        addCollection(request, "otherExamSignUps", utilService.search(buildQuery(request)));
        return forward(request);
    }
    
    /**
     * 跳转到手工添加
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
        setCalendarDataRealm(request, hasStdType);
        initBaseCodes(request, "otherExamCategories", OtherExamCategory.class);
        return forward(request);
    }
    
    /**
     * 手工删除
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
        Long[] ids = SeqStringUtil.transformToLong(request.getParameter("otherExamSignUpIds"));
        if (ids == null || ids.length == 0) {
            return forward(request, new Action("", "search"), "info.action.failure");
        }
        List otherExamSignUps = new ArrayList();
        for (int i = 0; i < ids.length; i++) {
            otherExamSignUps.add(utilService.load(OtherExamSignUp.class, ids[i]));
        }
        utilService.remove(otherExamSignUps);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 保存手工添加记录
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
        OtherExamSignUp otherExamSignUp = (OtherExamSignUp) populateEntity(request,
                OtherExamSignUp.class, "otherExamSignUp");
        otherExamSignUp.setSignUpAt(new Date());
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.studentType.id"), get(request, "calendar.year"), get(request,
                "calendar.term"));
        otherExamSignUp.setCalendar(calendar);
        
        // 保存前的记录重要验证
        EntityQuery query = new EntityQuery(OtherExamSignUp.class, "otherExam");
        query.add(new Condition("otherExam.std.id = (:stdId)", otherExamSignUp.getStd().getId()));
        query.add(new Condition("otherExam.calendar.id = (:calendarId)", otherExamSignUp
                .getCalendar().getId()));
        query.add(new Condition("otherExam.category.id = (:categoryId)", otherExamSignUp
                .getCategory().getId()));
        List list = (List) utilService.search(query);
        if (otherExamSignUp.getId() == null && list.size() > 0 || otherExamSignUp.getId() != null
                && list.size() > 1) {
            return forward(request, new Action("", "edit"), "error.model.existed");
        }
        
        utilService.saveOrUpdate(otherExamSignUp);
        
        return redirect(request, "search", "info.action.success");
    }
    
    private EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(OtherExamSignUp.class, "signUp");
        populateConditions(request, query, "signUp.std.type.id");
        
        Date startAt = (Date) RequestUtils.get(request, Date.class, "startAt");
        if (null != startAt) {
            query.add(new Condition("signUp.signUpAt >=:startAt", startAt));
        }
        Date endAt = (Date) RequestUtils.get(request, Date.class, "endAt");
        if (null != endAt) {
            query.add(new Condition("signUp.signUpAt <=:endAt", endAt));
        }
        query.setLimit(getPageLimit(request));
        String stdAdminClassName = get(request, "stdAdminClass");
        if (StringUtils.isNotEmpty(stdAdminClassName)) {
            query.join("signUp.std.adminClasses", "adminClass");
            query.add(Condition.like("adminClass.name", stdAdminClassName));
        }
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        DataRealmUtils.addDataRealms(query, new String[] { "signUp.std.type.id",
                "signUp.std.department.id" }, getDataRealmsWith(getLong(request,
                "signUp.std.type.id"), request));
        return query;
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = buildQuery(request);
        query.setLimit(null);
        return utilService.search(query);
    }
    
}
