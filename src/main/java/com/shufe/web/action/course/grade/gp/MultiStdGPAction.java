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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.course.grade.gp.MultiStdGP;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.course.grade.gp.GradePointService;
import com.shufe.web.action.common.RestrictionSupportAction;
import com.shufe.web.helper.BaseInfoSearchHelper;

/**
 * 行政班绩点查询,打印和输出
 * 
 * @author chaostone
 */
public class MultiStdGPAction extends RestrictionSupportAction {
    
    private BaseInfoSearchHelper baseInfoSearchHelper;
    
    private GradePointService gradePointService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    /**
     * 查找行政班
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward adminClassList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "adminClasses", baseInfoSearchHelper.searchAdminClass(request));
        addCollection(request, "gradeTypes", baseCodeService.getCodes(GradeType.class));
        return forward(request);
    }
    
    /**
     * 多个学生在一张表格上
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward classGPReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        classGp(request);
        return forward(request);
    }

    private void classGp(HttpServletRequest request) {
        String adminClassIds = get(request, "adminClassIds");
        if (StringUtils.isEmpty(adminClassIds)) {
            adminClassIds = get(request, "adminClassId");
        }
        
        MajorType majorType = new MajorType(getLong(request, "majorTypeId"));
        int pageSize = getPageSize(request);
        
        List adminClasses = utilService.load(AdminClass.class, "id", SeqStringUtil
                .transformToLong(adminClassIds));
        List multiStdGPs = new ArrayList();
        for (Iterator iter = adminClasses.iterator(); iter.hasNext();) {
            AdminClass adminClass = (AdminClass) iter.next();
            MultiStdGP multiStdGP = gradePointService.statMultiStdGPA(adminClass.getStudents(),
                    majorType, Boolean.TRUE);
            multiStdGP.setAdminClass(adminClass);
            multiStdGPs.add(multiStdGP);
        }
        request.setAttribute("pageSize", new Integer(pageSize));
        // 对多个报表进行排序
        List orders = OrderUtils.parser(request.getParameter("orderBy"));
        if (!orders.isEmpty()) {
            Order order = (Order) orders.get(0);
            PropertyComparator orderCmp = new PropertyComparator(order.getProperty(),
                    Order.ASC == order.getDirection());
            Collections.sort(multiStdGPs, orderCmp);
        }
        request.setAttribute("multiStdGPs", multiStdGPs);
    }
    
    /**
     * 班级学生学年绩点排名
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward classGPReportByYear(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        classGp(request);
        return forward(request);
    }
    
    public void setBaseInfoSearchHelper(BaseInfoSearchHelper baseInfoSearchHelper) {
        this.baseInfoSearchHelper = baseInfoSearchHelper;
    }
    
    public void setGradePointService(GradePointService gradePointService) {
        this.gradePointService = gradePointService;
    }
    
}
