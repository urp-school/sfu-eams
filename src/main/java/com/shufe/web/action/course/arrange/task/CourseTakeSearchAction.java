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
 * chaostone            2006-12-12          Created
 * zq                   2007-09-14          在search()中，添加了QueryRequestSupport.populateConditions(...)
 *                                          方法和addStdTypeTreeDataRealm(...)，且
 *                                          因学生类别权限不足，故在Oracle做了update操
 *                                          作；
 ********************************************************************************/

package com.shufe.web.action.course.arrange.task;

import java.util.Collection;
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
import com.ekingstar.commons.utils.query.QueryPage;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.std.Student;
import com.shufe.service.course.arrange.task.CourseActivityDigestor;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 上课记录查询
 * 
 * @author chaostone
 */
public class CourseTakeSearchAction extends CalendarRestrictionSupportAction {
    
    /**
     * 查询主页面
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
        List stdTypes = baseInfoService.getBaseInfos(StudentType.class);
        request.setAttribute("stdTypeList", stdTypes);
        request.setAttribute("departmentList", departmentService.getColleges());
        StudentType stdType = (StudentType) stdTypes.get(0);
        request.setAttribute("studentType", stdType);
        setCalendar(request, stdType);
        request.setAttribute("courseTakeTypes", baseCodeService.getCodes(CourseTakeType.class));
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
        EntityQuery query = new EntityQuery(CourseTake.class, "courseTake");
        QueryRequestSupport.populateConditions(request, query, "courseTake.student.type.id");
        restrictionHelper.addStdTypeTreeDataRealm(query, "courseTake.student.type.id", request,
                null);
        String adminClassName = get(request, "adminClassName");
        if (StringUtils.isNotEmpty(adminClassName)) {
            query.join("courseTake.student.adminClasses", "adminClass");
            query.add(new Condition("adminClass.name like :adminClassName", adminClassName));
        }
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        
        addCollection(request, "courseTakes", utilService.search(query));
        CourseActivityDigestor.setDelimeter("<br>");
        String stdCode = request.getParameter("courseTake.student.code");
        if (StringUtils.isNotBlank(stdCode)) {
            List stds = utilService.load(Student.class, "code", stdCode);
            if (stds.size() == 1) {
                request.setAttribute("searchStd", stds.get(0));
            }
        }
        return forward(request);
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(CourseTake.class, "courseTake");
        QueryRequestSupport.populateConditions(request, entityQuery);
        Long[] courseTakeIds = SeqStringUtil.transformToLong(get(request, "courseTakeIds"));
        if (null != courseTakeIds && courseTakeIds.length > 0) {
            entityQuery.add(new Condition("courseTake.id in (:courseTakeIds)", courseTakeIds));
        }
        //entityQuery.setLimit(getPageLimit(request));
        entityQuery.setLimit(null);
        return new QueryPage(entityQuery, utilService);
    }
}
