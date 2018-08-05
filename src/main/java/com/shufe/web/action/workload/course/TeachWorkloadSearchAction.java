//$Id: TeachWorkloadSearchAction.java,v 1.1 2007-4-2 9:17:10 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-4-2         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.workload.course;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.PropertyExtractor;
import com.ekingstar.eams.system.basecode.industry.TeacherType;
import com.ekingstar.eams.system.basecode.state.EduDegree;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.basecode.state.TeacherTitle;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.workload.course.TeachWorkload;
import com.shufe.service.workload.course.TeachWorkloadService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;
import com.shufe.web.action.workload.WorkloadPropertyExtractor;

public class TeachWorkloadSearchAction extends RestrictionSupportAction {
    
    protected TeachWorkloadService teachWorkloadService;
    
    /**
     * 得到查询条件的页面
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#execute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeDepart);
        request.setAttribute("genderList", baseCodeService.getCodes(Gender.class));
        request.setAttribute("teacherTitleList", baseCodeService.getCodes(TeacherTitle.class));
        request.setAttribute("eduDegreeList", baseCodeService.getCodes(EduDegree.class));
        request.setAttribute("teacherTypeList", baseCodeService.getCodes(TeacherType.class));
        request.setAttribute("openCollegeList", departmentService
                .getTeachDeparts(getDepartmentIdSeq(request)));
        return forward(request);
    }
    
    /**
     * 查询工作量
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
        EntityQuery entityQuery = buildEntityQuery(request);
        entityQuery.addOrder(new Order("college"));
        entityQuery.addOrder(new Order("teacherInfo.teacher.code"));
        entityQuery.setLimit(getPageLimit(request));
        addCollection(request, "teachWorkloads", utilService.search(entityQuery));
        return forward(request);
    }
    
    /**
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        return utilService.search(buildEntityQuery(request));
    }
    
    /**
     * @see com.shufe.web.action.common.DispatchBasicAction
     */
    protected PropertyExtractor getPropertyExtractor(HttpServletRequest request) {
        return new WorkloadPropertyExtractor(getResources(request), getLocale(request));
    }
    
    /**
     * @param request
     * @return
     */
    protected EntityQuery buildEntityQuery(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(TeachWorkload.class, "teachWorkload");
        populateConditions(request, entityQuery);
        
        // 教师年龄
        String age1 = get(request, "age1");
        if (StringUtils.isNotBlank(age1)) {
            entityQuery.add(new Condition("teachWorkload.teacherInfo.teacherAge>=:age1", age1));
        }
        String age2 = get(request, "age2");
        if (StringUtils.isNotBlank(age2)) {
            entityQuery.add(new Condition("teachWorkload.teacherInfo.teacherAge<=:age2", age2));
        }
        Long stdTypeId = getLong(request, "studentTypeId");
        if (null != stdTypeId) {
            List stdTypes = new ArrayList();
            StudentType stdType = (StudentType) utilService.load(StudentType.class, stdTypeId);
            stdTypes.add(stdType);
            stdTypes.addAll(stdType.getDescendants());
            entityQuery.add(new Condition("teachWorkload.studentType in(:stdTypes)", stdTypes));
        }
        DataRealmUtils.addDataRealms(entityQuery, new String[] { "teachWorkload.studentType.id",
                "teachWorkload.college.id" }, getDataRealms(request));
        
        // 排序
        String orderBy = get(request, "orderBy");
        if (StringUtils.isEmpty(orderBy)) {
            Order order = new Order("teachWorkload.teachCalendar.start", Order.DESC);
            entityQuery.addOrder(order);
        } else {
            entityQuery.addOrder(OrderUtils.parser(orderBy));
        }
        return entityQuery;
    }
    
    /**
     * 对单个或者多个选择的确认或者取消操作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward affirmSelect(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teachWorkloadIds = get(request, "teachWorkloadIds");
        String affirmType = get(request, "affirmType");
        String estate = get(request, "affirmEstate");
        teachWorkloadService.affirmType(affirmType, teachWorkloadIds, Boolean.valueOf(estate)
                .booleanValue());
        return redirect(request, "search", "info.confirm.success");
    }
    
    /**
     * 确认所有的选择对象的确认
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward affirmAll(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery entityQuery = buildEntityQuery(request);
        entityQuery.setSelect("select teachWorkload.id");
        Collection teachWorkloadList = utilService.search(entityQuery);
        StringBuffer teachWorkloadIds = new StringBuffer();
        for (Iterator iter = teachWorkloadList.iterator(); iter.hasNext();) {
            Long teachWorkloadId = (Long) iter.next();
            teachWorkloadIds.append(teachWorkloadId);
            if (iter.hasNext()) {
                teachWorkloadIds.append(",");
            }
        }
        String affirmType = get(request, "affirmType");
        String estate = get(request, "affirmEstate");
        teachWorkloadService.affirmType(affirmType, teachWorkloadIds.toString(), Boolean.valueOf(
                estate).booleanValue());
        return redirect(request, "search", "info.confirm.success");
    }
    
    /**
     * 查看单个统计工作量的详细信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        request.setAttribute("teachWorkload", (TeachWorkload) utilService.load(TeachWorkload.class,
                getLong(request, "teachWorkloadId")));
        return forward(request);
    }
    
    public void setTeachWorkloadService(TeachWorkloadService teachWorkloadService) {
        this.teachWorkloadService = teachWorkloadService;
    }
}
