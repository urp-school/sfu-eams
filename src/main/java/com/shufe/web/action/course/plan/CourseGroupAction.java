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
 * chaostone             2006-3-26            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.plan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.HSKDegree;
import com.shufe.model.Constants;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.plan.TeachPlanArrangeAlteration;
import com.shufe.service.course.plan.CourseGroupService;
import com.shufe.service.course.plan.TeachPlanService;

/**
 * 课程组维护界面响应类
 * 
 * @author chaostone
 * 
 */
public class CourseGroupAction extends PlanUpdateAction {
    
    /**
     * 为修改或新建课程组显示界面
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
        Long groupId = getLong(request, Constants.COURSEGROUP_KEY);
        Long planId = getLong(request, Constants.TEACHPLAN_KEY);
        TeachPlan plan = teachPlanService.getTeachPlan(planId);
        CourseGroup group = null;
        if (null != groupId) {
            group = courseGroupService.getCourseGroup(groupId);
            group = courseGroupService.keepUniqueGroupFor(group, plan);
        } else
            group = new CourseGroup();
        
        List planCoursesList = new ArrayList(group.getPlanCourses());
        String field = get(request, "orderBy");
        if (StringUtils.isEmpty(field)) {
            field = "terms";
        }
        PropertyComparator propertry = new PropertyComparator(field);
        Collections.sort(planCoursesList, propertry);
        request.setAttribute("planCoursesList", planCoursesList);
        request.setAttribute(Constants.TEACHPLAN, plan);
        request.setAttribute(Constants.COURSEGROUP, group);
        request.setAttribute(Constants.PLANCOURSE, new PlanCourse());
        addCollection(request, "HSKDegreeList", baseCodeService.getCodes(HSKDegree.class));
        List courseTypeList = baseCodeService.getCodes(CourseType.class);
        request.setAttribute("couseTypeList", courseTypeList);
        return forward(request);
    }
//    
//    public ActionForward refactGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
//        HttpServletResponse response) throws Exception {
//     Collection<String> plangroups=  utilService.search(
//new SqlQuery("select pyjhid||','||kczid from pyjh_kczgl_t a where exists(select * from pyjh_kczgl_t b where b.kczid=a.kczid and b.pyjhid< a.pyjhid)"));
//     int i=0;
//      for(String plangroup:plangroups){
//        System.out.println("Processing "+i +" ...");
//        TeachPlan plan = teachPlanService.getTeachPlan(Long.valueOf(StringUtils.substringBefore(plangroup,",")));
//        CourseGroup group = courseGroupService.getCourseGroup(Long.valueOf(StringUtils.substringAfter(plangroup,",")));
//        group = courseGroupService.keepUniqueGroupFor(group, plan);
//        i++;
//      }
//      return forward(request);
//    }
    /**
     * 新增课程组
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long teachPlanId = getLong(request, Constants.TEACHPLAN_KEY);
        if (null == teachPlanId)
            throw new PojoNotExistException("teachplan");
        TeachPlan plan = teachPlanService.getTeachPlan(teachPlanId);
        Collection unusedCourseTypeList = teachPlanService.getUnusedCourseTypes(plan);
        List courseTypeList = baseCodeService.getCodes(CourseType.class);
        request.setAttribute("unusedCourseTypeList", unusedCourseTypeList);
        request.setAttribute("couseTypeList", courseTypeList);
        request.setAttribute(Constants.TEACHPLAN, plan);
        return forward(request);
    }
    
    /**
     * 保存课程组和设置的课程
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
        Long planId = getLong(request, Constants.TEACHPLAN_KEY);
        if (null == planId) {
            return forwardError(mapping, request, "error.model.notExist");
        }
        TeachPlan plan = teachPlanService.getTeachPlan(planId);
        
        TeachPlanArrangeAlteration alteration = new TeachPlanArrangeAlteration(
                TeachPlanArrangeAlteration.EDIT);
        TeachPlan tPlan = (TeachPlan) plan.clone();
        tPlan.setIsConfirm(plan.getIsConfirm());
        tPlan.setStd(plan.getStd());
        alteration.setBeforePlan(tPlan);
        alteration.getBeforePlanInfo().setIsConfirm(plan.getIsConfirm());
        alteration.setAlterationBy(getUser(request));
        alteration.setAlterationFrom(request.getRequestURI() + "?" + request.getQueryString());
        
        CourseGroup group = (CourseGroup) populateEntity(request, CourseGroup.class,
                Constants.COURSEGROUP);
        
        EntityUtils.evictEmptyProperty(group);
        boolean isNew = false;
        if (group.isPO()) {
            courseGroupService.updateCourseGroup(group, plan);
        } else {
            courseGroupService.addCourseGroup(group, plan);
            isNew = true;
        }
        
        StringBuffer content = new StringBuffer();
        content.append(TeachPlanArrangeAlteration.COURSEGROUPS + ":");
        CourseType courseType = (CourseType) utilService.load(CourseType.class, group
                .getCourseType().getId());
        content.append(courseType.getName() + "=" + courseType.getCode());
        content.append(",," + TeachPlanArrangeAlteration.BEEN_ADDED + ";");
        alteration.setDescription(content.toString());
        
        alteration.setIsModifyGroup(new Boolean(isNew));
        alteration.setAfterPlan(plan);
        utilService.saveOrUpdate(alteration);
        
        if (isNew) {
            String extra = "&" + Constants.TEACHPLAN_KEY + "="
                    + request.getParameter(Constants.TEACHPLAN_KEY);
            return redirect(request, new Action(TeachPlanAction.class, "edit"),
                    "info.save.success", extra);
        } else {
            addSingleParameter(request, Constants.COURSEGROUP, courseGroupService
                    .getCourseGroup(group.getId()));
            addSingleParameter(request, Constants.PLANCOURSE, new PlanCourse());
            StringBuffer sbf = new StringBuffer(20);
            sbf.append("&" + Constants.TEACHPLAN_KEY + "="
                    + request.getParameter(Constants.TEACHPLAN_KEY));
            sbf.append("&courseGroup.id=" + group.getId());
            return redirect(request, "edit", "info.save.success", sbf.toString());
        }
    }
    
    /**
     * 设置合并的组
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward mergeEdit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long planId = getLong(request, Constants.TEACHPLAN_KEY);
        if (null == planId) {
            return forwardError(mapping, request, "error.model.notExist");
        }
        addSingleParameter(request, "plan", teachPlanService.getTeachPlan(planId));
        return forward(request, "mergeForm");
    }
    
    /**
     * 将组合并
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward merge(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        TeachPlan plan = teachPlanService.getTeachPlan(getLong(request, "planId"));
        Long[] courseGroupIds = SeqStringUtil.transformToLong(get(request, "courseGroupIds"));
        Collection mergeObjects = utilService.load(CourseGroup.class, "id", courseGroupIds);
        CourseType courseType = (CourseType) utilService.load(CourseType.class, getLong(request,
                "mergeToId"));
        CourseGroup courseGroup = plan.getCourseGroup(courseType);
        CourseGroup newGroup = courseGroupService.keepUniqueGroupFor(courseGroup, plan);
        
        Integer creditHour = getInteger(request, "V_2");
        Float credit = getFloat(request, "V_1");
        int i = 1;
        StringBuilder creditPerTerms = new StringBuilder();
        creditPerTerms.append(",");
        for (; i <= plan.getTermsCount().intValue(); i++) {
            creditPerTerms.append(get(request, "V" + i) + ",");
        }
        StringBuilder weekHourPerTerms = new StringBuilder();
        weekHourPerTerms.append(",");
        for (; i <= plan.getTermsCount().intValue() * 2; i++) {
            weekHourPerTerms.append(get(request, "V" + i) + ",");
        }
        
        newGroup.setCreditHour(creditHour);
        newGroup.setCredit(credit);
        newGroup.setCreditPerTerms(creditPerTerms.toString());
        newGroup.setWeekHourPerTerms(weekHourPerTerms.toString());
        mergeObjects.remove(courseGroup);
        for (Iterator it = mergeObjects.iterator(); it.hasNext();) {
            CourseGroup courseGroup1 = (CourseGroup) it.next();
            for (Iterator it1 = courseGroup1.getPlanCourses().iterator(); it1.hasNext();) {
                PlanCourse planCourse = (PlanCourse) it1.next();
                newGroup.addPlanCourse(planCourse);
            }
            CourseGroup newGroup1 = courseGroupService.keepUniqueGroupFor(courseGroup1, plan);
            plan.getCourseGroups().remove(newGroup1);
        }
        utilService.saveOrUpdate(plan);
        
        return redirect(request, new Action("teachPlan", "edit"), "info.action.success");
    }
    
    /**
     * 删除课程组
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeGroup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long groupId = getLong(request, Constants.COURSEGROUP_KEY);
        Long planId = getLong(request, Constants.TEACHPLAN_KEY);
        if (null == planId || null == groupId) {
            return forwardError(mapping, request, "error.model.notExist");
        }
        plan = teachPlanService.getTeachPlan(planId);
        
        TeachPlanArrangeAlteration alteration = new TeachPlanArrangeAlteration(
                TeachPlanArrangeAlteration.EDIT);
        TeachPlan tPlan = (TeachPlan) plan.clone();
        tPlan.setIsConfirm(plan.getIsConfirm());
        tPlan.setStd(plan.getStd());
        alteration.setBeforePlan(tPlan);
        alteration.getBeforePlanInfo().setIsConfirm(plan.getIsConfirm());
        alteration.setAlterationBy(getUser(request));
        alteration.setAlterationFrom(request.getRequestURI() + "?" + request.getQueryString());
        
        courseGroupService.removeCourseGroup(groupId, planId);
        
        Collection c1 = new ArrayList();
        c1.addAll(tPlan.getCourseGroups());
        Collection c2 = new ArrayList();
        c2.addAll(plan.getCourseGroups());
        
        StringBuffer content = new StringBuffer();
        // 课程组
        content.append(TeachPlanArrangeAlteration.COURSEGROUPS + ":");
        CourseGroup group = courseGroupService.getCourseGroup(groupId);
        content.append(group.getCourseType().getName() + "=" + group.getCourseType().getCode());
        content.append(",," + TeachPlanArrangeAlteration.BEEN_REMOVED + ";");
        // 课程
        Collection c11c22 = group.getPlanCourses();
        if (CollectionUtils.isNotEmpty(c11c22)) {
            content.append(TeachPlanArrangeAlteration.PLANCOURSES + ":");
            for (Iterator it = c11c22.iterator(); it.hasNext();) {
                PlanCourse planCourse = (PlanCourse) it.next();
                content.append(planCourse.getCourse().getName() + "="
                        + planCourse.getCourse().getCode());
                if (it.hasNext()) {
                    content.append(",");
                }
            }
            content.append(",," + TeachPlanArrangeAlteration.BEEN_REMOVED + ";");
            alteration.setIsModifyCourse(Boolean.TRUE);
        }
        alteration.setDescription(content.toString());
        
        alteration.setIsModifyGroup(new Boolean(c1.size() != c2.size()));
        alteration.setAfterPlan(plan);
        utilService.saveOrUpdate(alteration);
        
        return redirect(request, new Action(TeachPlanAction.class, "edit"), "info.delete.success",
                "&teachPlan.id=" + request.getParameter(Constants.TEACHPLAN_KEY));
    }
    
    /**
     * @param courseGroupService
     *            The courseGroupService to set.
     */
    public void setCourseGroupService(CourseGroupService courseGroupService) {
        this.courseGroupService = courseGroupService;
    }
    
    /**
     * @param teachPlanService
     *            The teachPlanService to set.
     */
    public void setTeachPlanService(TeachPlanService teachPlanService) {
        this.teachPlanService = teachPlanService;
    }
}
