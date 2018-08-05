//$Id: AbroadInfoAction.java,v 1.1 2007-7-17 上午08:49:09 chaostone Exp $
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
 * chenweixiong              2007-7-17         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.info;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.shufe.model.std.AbroadStudentInfo;
import com.shufe.model.std.Student;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;
import com.shufe.web.helper.StdSearchHelper;

public class AbroadInfoAction extends RestrictionSupportAction {
    
    private StdSearchHelper stdSearchHelper;
    
    /**
     * 首页
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
        setDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    /**
     * 学生查询
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
        getStudents(request);
        return forward(request);
    }
    
    /**
     * 按条件查询留学生需要签证的记录
     * 
     * @param request
     * @return
     * @throws Exception
     */
    protected void getStudents(HttpServletRequest request) throws Exception {
        EntityQuery query = stdSearchHelper.buildStdQuery(request);
        query.add(new Condition("std.abroadStudentInfo is not null"));
        addCollection(request, "students", utilService.search(query));
        
    }
    
    /**
     * 学生查询
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
        Long id = new Long(request.getParameter("stdId"));
        request.setAttribute("student", utilService.get(Student.class, id));
        return forward(request);
    }
    
    /**
     * 保存修改的内容
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
        AbroadStudentInfo info = (AbroadStudentInfo) populateEntity(request,
                AbroadStudentInfo.class, "abroadStudentInfo");
        utilService.saveOrUpdate(info);
        return redirect(request, "search", "info.save.success");
    }
    
    /**
     * 保存修改的内容
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
        Long stdId = getLong(request, "stdId");
        request.setAttribute("std", utilService.get(Student.class, stdId));
        return forward(request);
    }
    
    /**
     * 转入统计到期时间
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statDeadline(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeCollege);
        request.setAttribute("defaultDate", new Date());
        return forward(request);
    }
    
    /**
     * 按到期类型统计到期时间，显示学生列表<br>
     * <li> 到期类型：<br>
     * &gt; visa - 签证到期<br>
     * &gt; passport - 护照到期<br>
     * &gt; resideCaed - 居住许可证到期<br>
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statAbroadStdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String deadlineType = get(request, "deadlineType");
        Date deadlineDate = RequestUtils.getDate(request, "deadlineDate");
        if (deadlineDate == null) {
            deadlineDate = new Date();
        }
        EntityQuery query = new EntityQuery(Student.class, "student");
        populateConditions(request, query, "student.type.id,student.department.id");
        DataRealmUtils.addDataRealms(query, new String[] { "student.type.id",
                "student.department.id" }, restrictionHelper.getDataRealmsWith(getLong(request,
                "student.type.id"), request));
        if (StringUtils.isEmpty(deadlineType) || StringUtils.equals(deadlineType, "visa")) {
            query.add(new Condition("student.abroadStudentInfo.visaDeadline <= (:visa)",
                    deadlineDate));
        } else if (StringUtils.equals(deadlineType, "passport")) {
            query.add(new Condition("student.abroadStudentInfo.passportDeadline <= (:passport)",
                    deadlineDate));
        } else {
            query.add(new Condition(
                    "student.abroadStudentInfo.resideCaedDeadline <= (:resideCaed)", deadlineDate));
        }
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "students", utilService.search(query));
        return forward(request);
    }
    
    public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
        this.stdSearchHelper = stdSearchHelper;
    }
}
