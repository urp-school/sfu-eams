//$Id: TeachCategoryAction.java,v 1.4 2006/12/27 00:53:48 cwx Exp $
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
 * chenweixiong              2006-3-13         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.workload.course;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.service.BaseCodeService;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.workload.course.TeachCategory;
import com.shufe.service.workload.course.TeachCategoryService;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * @deprecated
 * @author chaostone
 */
public class TeachCategoryAction extends RestrictionSupportAction {
    
    private TeachCategoryService teachCategoryService;
    
    private BaseCodeService baseCodeService;
    
    /**
     * @param baseCodeService
     *            The baseCodeService to set.
     */
    public void setBaseCodeService(BaseCodeService baseCodeService) {
        this.baseCodeService = baseCodeService;
    }
    
    /**
     * @param teachCategoryService
     *            The teachCategoryService to set.
     */
    public void setTeachCategoryService(TeachCategoryService teachCategoryService) {
        this.teachCategoryService = teachCategoryService;
    }
    
    /**
     * 得到列表的分页信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        DynaActionForm daf = (DynaActionForm) form;
        int pageNo = ((Integer) daf.get("pageNo")).intValue();
        int pageSize = ((Integer) daf.get("pageSize")).intValue();
        Pagination pageNation = teachCategoryService.getPaginationOfValue(pageNo, pageSize);
        Results.addPagination("teachCategoryPage", pageNation);
        return forward(mapping, request, "doList");
    }
    
    /**
     * 得到添加页面和保存 添加数据页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        DynaActionForm daf = (DynaActionForm) form;
        TeachCategory teachCategory = (TeachCategory) daf.get("teachCategory");
        String studentTypeIds = getStdTypeIdSeq(request);
        if (StringUtils.isEmpty(teachCategory.getCode())) {
            List studentTypeList = studentTypeService.getStudentTypes(studentTypeIds);
            List courseTypeList = baseCodeService.getCodes(CourseType.class);
            Results.addList("studentTypeList", studentTypeList).addList("courseTypeList",
                    courseTypeList);
            return forward(mapping, request, "addForm");
        }
        teachCategory.setMakeTime(new Date(System.currentTimeMillis()));
        teachCategory = prepare(teachCategory);
        utilService.saveOrUpdate(teachCategory);
        return forward(mapping, request, "doAdd");
    }
    
    /**
     * 得到更新页面和更新相应的数据
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String flag = request.getParameter("flag");
        DynaActionForm daf = (DynaActionForm) form;
        TeachCategory teachCategoryTemp = (TeachCategory) daf.get("teachCategory");
        Long teachCategoryId = Long.valueOf(request.getParameter("teachCategoryId"));
        String studentTypeIds = getStdTypeIdSeq(request);
        /*
         * 如果标志为空就去拿更新页面地信息.
         */
        if (StringUtils.isEmpty(flag)) {
            TeachCategory teachCategory = (TeachCategory) utilService.load(TeachCategory.class,
                    teachCategoryId);
            List studentTypeList = studentTypeService.getStudentTypes(studentTypeIds);
            List courseTypeList = baseCodeService.getCodes(CourseType.class);
            Results.addList("studentTypeList", studentTypeList).addList("courseTypeList",
                    courseTypeList).addObject("teachCategory", teachCategory);
            return forward(mapping, request, "addForm");
        }
        teachCategoryTemp.setModifyTime(new Date(System.currentTimeMillis()));
        teachCategoryTemp.setId(teachCategoryId);
        teachCategoryTemp = prepare(teachCategoryTemp);
        utilService.saveOrUpdate(teachCategoryTemp);
        return forward(mapping, request, "doUpdate");
    }
    
    /**
     * 更新数据前的准备
     * 
     * @param teachCategory
     * @return
     */
    public TeachCategory prepare(TeachCategory teachCategory) {
        teachCategory.setStudentType((StudentType) utilService.load(StudentType.class,
                teachCategory.getStudentType().getId()));
        if (teachCategory.getCourseType().getId().intValue() != 0) {
            teachCategory.setCourseType((CourseType) utilService.load(CourseType.class,
                    teachCategory.getCourseType().getId()));
        } else {
            teachCategory.setCourseType(null);
        }
        return teachCategory;
    }
}
