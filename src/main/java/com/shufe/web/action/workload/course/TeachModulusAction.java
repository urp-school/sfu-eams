//$Id: TeachModulusAction.java,v 1.2 2006/08/17 06:44:25 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-17         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.workload.course;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.workload.course.TeachModulus;
import com.shufe.service.workload.ModulusService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.RestrictionSupportAction;

public class TeachModulusAction extends RestrictionSupportAction {
    
    private ModulusService teachModulusService;
    
    /**
     * @return Returns the teachModulusService.
     */
    public ModulusService getTeachModulusService() {
        return teachModulusService;
    }
    
    /**
     * @param teachModulusService
     *            The teachModulusService to set.
     */
    public void setTeachModulusService(ModulusService teachModulusService) {
        this.teachModulusService = teachModulusService;
    }
    
    /**
     * 列出查询得到的所有的结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        String stdTypeIdSeq = getStdTypeIdSeq(request);
        TeachModulus teachModulus = (TeachModulus) RequestUtil.populate(request,
                TeachModulus.class, "teachModulus");
        List conditions = teachModulusService.getPropertyOfModulus(new TeachModulus(),
                stdTypeIdSeq, "studentType,courseCategory,department");
        Set stdTypes = new HashSet();
        Set categorys = new HashSet();
        Set departments = new HashSet();
        for (Iterator iter = conditions.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            stdTypes.add(element[0]);
            categorys.add(element[1]);
            departments.add(element[2]);
        }
        request.setAttribute("stdTypes", stdTypes);
        request.setAttribute("categorys", categorys);
        request.setAttribute("departments", departments);
        Pagination teachModulusPagi = teachModulusService.findModulus(teachModulus, stdTypeIdSeq,
                getPageNo(request), getPageSize(request));
        addOldPage(request, "teachModuluses", teachModulusPagi);
        return forward(request);
    }
    
    /**
     * 在修改前的准备
     * 
     * @param teachModulus
     * @return
     */
    public TeachModulus prepareForModify(TeachModulus teachModulus) {
        Department department = (Department) utilService.load(Department.class, teachModulus
                .getDepartment().getId());
        teachModulus.setDepartment(department);
        CourseCategory courseCategory = (CourseCategory) utilService.load(CourseCategory.class,
                teachModulus.getCourseCategory().getId());
        teachModulus.setCourseCategory(courseCategory);
        StudentType studentType = (StudentType) utilService.load(StudentType.class, teachModulus
                .getStudentType().getId());
        teachModulus.setStudentType(studentType);
        return teachModulus;
    }
    
    /**
     * 添加或修改的form
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        prepare(request);
        String teachModulusId = request.getParameter("teachModulusId");
        if (StringUtils.isEmpty(teachModulusId) == false) {
            request.setAttribute("teachModulus", utilService.load(TeachModulus.class, new Long(
                    teachModulusId)));
        }
        return forward(request);
    }
    
    /**
     * 得到一个更新系数的页面
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
        TeachModulus teachModulus = (TeachModulus) populateEntity(request, TeachModulus.class,
                "teachModulus");
        
        EntityQuery query = new EntityQuery(TeachModulus.class, "teachModulus");
        query.add(new Condition("teachModulus.studentType.id = (:stdTypeId)", teachModulus
                .getStudentType().getId()));
        query.add(new Condition("teachModulus.courseCategory.id = (:courseCategoryId)",
                teachModulus.getCourseCategory().getId()));
        query.add(new Condition("teachModulus.maxPeople > :start", teachModulus.getMinPeople()));
        query.add(new Condition("teachModulus.minPeople < :end", teachModulus.getMaxPeople()));
        if (teachModulus.getId() != null) {
            query.add(new Condition("teachModulus.id != (:id)", teachModulus.getId()));
        }
        if (((List) utilService.search(query)).size() > 0) {
            return redirect(request, "index", "info.save.failure.overlapAcross");
        }
        
        List departmentList = getDeparts(request);
        if (departmentList.contains(teachModulus.getDepartment())) {
            utilService.saveOrUpdate(teachModulus);
            return redirect(request, "index", "info.action.success");
        } else {
            Results.addObject("departmentName", teachModulus.getDepartment().getName());
            return forward(request, new Action("", "index"), "workloadModuleUpdateOrDeleteErrors");
        }
        
    }
    
    /**
     * 为添加和更新准备数据
     * 
     * @param request
     */
    private void prepare(HttpServletRequest request) {
        List adminList = getDeparts(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        List studentTypeList = studentTypeService.getStudentTypes(studentTypeIds);
        List courseCategoryList = baseCodeService.getCodes(CourseCategory.class);
        Results.addList("adminList", adminList).addList("studentTypeList", studentTypeList)
                .addList("courseCategoryList", courseCategoryList);
    }
    
    /**
     * 删除一个或者一堆教学系数
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
        Long[] teachModulusIds = SeqStringUtil.transformToLong(request
                .getParameter("teachModulusIds"));
        try {
            Collection entities = utilService.load(TeachModulus.class, "id", teachModulusIds);
            utilService.remove(entities);
            return redirect(request, "index", "info.action.success");
        } catch (Exception e) {
            return redirect(request, "index", "info.action.failure");
        }
    }
    
    /**
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        String stdTypeIdSeq = getStdTypeIdSeq(request);
        TeachModulus teachModulus = (TeachModulus) RequestUtil.populate(request,
                TeachModulus.class, "teachModulus");
        return teachModulusService.getModulus(teachModulus, stdTypeIdSeq);
    }
    
    /**
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportKeys(javax.servlet.http.HttpServletRequest)
     */
    protected String getExportKeys(HttpServletRequest request) {
        String keys = "studentType.name,courseCategory.name,minPeople,maxPeople,modulusValue,department.name,remark";
        return keys;
    }
    
    /**
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportTitles(javax.servlet.http.HttpServletRequest)
     */
    protected String getExportTitles(HttpServletRequest request) {
        String titles = "学生类别,课程种类,最小人数,最大人数,系数值,创建部门,备注";
        return titles;
    }
}
