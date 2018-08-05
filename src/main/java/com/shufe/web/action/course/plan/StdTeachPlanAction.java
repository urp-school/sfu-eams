//$Id: StdTeachPlan.java,v 1.1 2007-8-26 上午10:48:45 chaostone Exp $
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
 * chenweixiong              2007-8-26         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.plan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.HSKDegree;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.Constants;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.plan.TeachPlanArrangeAlteration;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.service.course.plan.CourseGroupService;
import com.shufe.service.course.plan.PlanCourseService;
import com.shufe.service.course.plan.StdTeachPlanService;
import com.shufe.util.RequestUtil;

/**
 * 学生个人计划管理
 * 
 * @author chaostone
 */
public class StdTeachPlanAction extends TeachPlanSearchAction {
    
    private PlanCourseService planCourseService;
    
    private StdTeachPlanService stdTeachPlanService;
    
    private CourseGroupService courseGroupService;
    
    /**
     * 学生个人计划管理主界面
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
        return forward(request);
    }
    
    /**
     * 修改个人计划
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
        Long planId = getLong(request, "teachPlan.id");
        TeachPlan plan = null;
        // 如果没有指明培养计划id,则认为可能需要学号.
        if (null == planId) {
            String stdCode = request.getParameter("stdCode");
            if (StringUtils.isEmpty(stdCode)) {
                return forwardError(mapping, request, "error.parameters.needed");
            }
            List stds = utilService.load(Student.class, "code", stdCode);
            if (stds.isEmpty()) {
                return redirect(request, "index", "error.model.notExist");
            }
            Student std = (Student) stds.get(0);
            if (!StringUtils.contains("," + getStdTypeIdSeq(request) + ",", ","
                    + std.getType().getId() + ",")
                    || !StringUtils.contains("," + getDepartmentIdSeq(request) + ",", ","
                            + std.getDepartment().getId() + ",")) {
                return redirect(request, "index", "error.dataRealm.insufficient");
            }
            // 查找计划(如果没有个人计划则根据参数决定是否生成)
            plan = stdTeachPlanService.getTeachPlan(std, new MajorType(getLong(request,
                    "majorType.id")), getBoolean(request, "forceGen"));
            addEntity(request, std);
            if (null == plan) {
                return redirect(request, "index", "error.model.notExist");
            }
        } else {
            plan = (TeachPlan) utilService.get(TeachPlan.class, planId);
        }
        
        // TODO 临时加上为了自动计算学分
        plan.autoAdjustCreditAndHour();
        utilService.saveOrUpdate(plan);
        addEntity(request, plan);
        addCollection(request, "stdTypeList", getStdTypes(request));
        addCollection(request, "departmentList", getDeparts(request));
        return forward(request);
    }
    
    /**
     * 删除个人计划中的课程组课程
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removePlanCourse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachPlan plan = (TeachPlan) utilService.get(TeachPlan.class, getLong(request,
                "teachPlan.id"));
        
        TeachPlanArrangeAlteration alteration = new TeachPlanArrangeAlteration(
                TeachPlanArrangeAlteration.EDIT);
        TeachPlan tPlan = (TeachPlan) plan.clone();
        tPlan.setIsConfirm(plan.getIsConfirm());
        tPlan.setStd(plan.getStd());
        alteration.setBeforePlan(tPlan);
        Collection c11 = new ArrayList();
        c11.addAll(tPlan.getPlanCourses());
        alteration.getBeforePlanInfo().setIsConfirm(plan.getIsConfirm());
        alteration.setAlterationBy(getUser(request));
        alteration.setAlterationFrom(request.getRequestURI() + "?" + request.getQueryString());
        
        CourseGroup group = (CourseGroup) utilService.get(CourseGroup.class, getLong(request,
                "courseGroup.id"));
        group = courseGroupService.keepUniqueGroupFor(group, plan);
        PlanCourse planCourse = (PlanCourse) utilService.load(PlanCourse.class, getLong(request,
                "planCourse.id"));
        // 重新计算每学期学分分布和总学分
        Boolean autoStatCredit = getBoolean(request, "autoStatCredit");
        if (null == autoStatCredit) {
            autoStatCredit = group.getCourseType().getIsCompulsory();
        }
        group.removePlanCourse(planCourse.getCourse());
        if (Boolean.TRUE.equals(autoStatCredit)) {
            group.statCreditAndHour(plan.getTermsCount().intValue());
        }
        utilService.saveOrUpdate(group);
        
        alteration.setIsModifyGroup(Boolean.TRUE);
        alteration.setIsModifyCourse(Boolean.TRUE);
        
        StringBuffer content = new StringBuffer();
        // 课程
        content.append(TeachPlanArrangeAlteration.PLANCOURSES + ":");
        content.append(planCourse.getCourse().getName() + "=" + planCourse.getCourse().getCode());
        content.append(",," + TeachPlanArrangeAlteration.BEEN_REMOVED + ";");
        alteration.setIsModifyCourse(Boolean.TRUE);
        alteration.setDescription(content.toString());
        
        alteration.setAfterPlan(plan);
        utilService.saveOrUpdate(alteration);
        
        String extra = "&teachPlan.id=" + plan.getId();
        return redirect(request, new Action(this, "edit"), "info.delete.success", extra);
    }
    
    /**
     * 批量删除课程
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchRemovePlanCourse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachPlan plan = (TeachPlan) utilService.get(TeachPlan.class, getLong(request,
                "teachPlan.id"));
        
        TeachPlanArrangeAlteration alteration = new TeachPlanArrangeAlteration(
                TeachPlanArrangeAlteration.EDIT);
        TeachPlan tPlan = (TeachPlan) plan.clone();
        tPlan.setIsConfirm(plan.getIsConfirm());
        tPlan.setStd(plan.getStd());
        alteration.setBeforePlan(tPlan);
        Collection c11 = new ArrayList();
        c11.addAll(tPlan.getPlanCourses());
        alteration.getBeforePlanInfo().setIsConfirm(plan.getIsConfirm());
        alteration.setAlterationBy(getUser(request));
        alteration.setAlterationFrom(request.getRequestURI() + "?" + request.getQueryString());
        
        List planCourses = utilService.load(PlanCourse.class, "id", SeqStringUtil
                .transformToLong(request.getParameter("planCourseIds")));
        // 如果有多门课程是在一个共享组内,那么同时删除组内的两门以上的课程时,请按照课程类别查找组.
        // 因为在克隆组时,第二个被删除的课程中所引用的组,应竟是被克隆的了.这样在他调用keepUniqueGroupFor
        // 方法时,会抛出待克隆的组不再指定的计划中.
        for (Iterator iter = planCourses.iterator(); iter.hasNext();) {
            PlanCourse planCourse = (PlanCourse) iter.next();
            CourseGroup group = plan.getCourseGroup(planCourse.getCourseGroup().getCourseType());
            CourseGroup myGroup = courseGroupService.keepUniqueGroupFor(group, plan);
            myGroup.removePlanCourse(planCourse.getCourse());
            if (Boolean.TRUE.equals(myGroup.getCourseType().getIsCompulsory())) {
                myGroup.statCreditAndHour(plan.getTermsCount().intValue());
            }
            utilService.saveOrUpdate(myGroup);
        }
        
        Collection c1 = new ArrayList();
        c1.addAll(tPlan.getCourseGroups());
        Collection c2 = new ArrayList();
        c2.addAll(plan.getCourseGroups());
        alteration.setIsModifyGroup(new Boolean(c1.size() != c2.size()));
        Collection c22 = new ArrayList();
        c22.addAll(plan.getPlanCourses());
        
        StringBuffer content = new StringBuffer();
        // 课程组
        if (c1.size() != c2.size()) {
            content.append(TeachPlanArrangeAlteration.COURSEGROUPS + ":");
            for (Iterator it = CollectionUtils.subtract(c1, c2).iterator(); c1.size() != c2.size()
                    && it.hasNext();) {
                CourseGroup group = (CourseGroup) it.next();
                content.append(group.getCourseType().getName() + "="
                        + group.getCourseType().getCode());
                if (it.hasNext()) {
                    content.append(",");
                }
            }
            content.append(",," + TeachPlanArrangeAlteration.BEEN_REMOVED + ";");
        }
        // 课程
        content.append(TeachPlanArrangeAlteration.PLANCOURSES + ":");
        for (Iterator it = planCourses.iterator(); it.hasNext();) {
            PlanCourse planCourse = (PlanCourse) it.next();
            content.append(planCourse.getCourse().getName() + "="
                    + planCourse.getCourse().getCode());
            if (it.hasNext()) {
                content.append(",");
            }
        }
        content.append(",," + TeachPlanArrangeAlteration.BEEN_REMOVED + ";");
        alteration.setDescription(content.toString());
        
        alteration.setIsModifyCourse(Boolean.TRUE);
        alteration.setAfterPlan(plan);
        utilService.saveOrUpdate(alteration);
        
        String extra = "&teachPlan.id=" + plan.getId();
        return redirect(request, new Action(this, "edit"), "info.delete.success", extra);
    }
    
    /**
     * 直接编辑培养计划课程
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editPlanCourse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachPlan plan = getTeachPlan(request);
        CourseGroup group = getUniqueGroup(request, plan);
        
        Long planCourseId = getLong(request, "planCourse.id");
        PlanCourse planCourse = null;
        if (!ValidEntityKeyPredicate.INSTANCE.evaluate(planCourseId)) {
            planCourse = new PlanCourse();
            group.addPlanCourse(planCourse);
            addEntity(request, planCourse);
        } else {
            planCourse = (PlanCourse) utilService.get(PlanCourse.class, planCourseId);
            request.setAttribute("planCourse", plan.getPlanCourse(planCourse.getCourse()));
        }
        addEntity(request, "teachPlan", plan);
        addEntity(request, "courseGroup", group);
        
        // FIXME
        initBaseCodes(request, "HSKDegreeList", HSKDegree.class);
        Collection teachDepartList = departmentService.getTeachDeparts(getDepartmentIdSeq(request));
        addCollection(request, "teachDepartList", teachDepartList);
        return forward(request);
    }
    
    /**
     * 保存培养计划课程的修改或新增
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward savePlanCourse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long planId = getLong(request, Constants.TEACHPLAN_KEY);
        Long groupId = getLong(request, "planCourse.courseGroup.id");
        if (null == groupId) {
            groupId = getLong(request, "courseGroup.id");
        }
        if (null == planId || null == groupId) {
            throw new PojoNotExistException("plan id or planCourse id");
        }
        TeachPlan plan = getTeachPlan(request);
        CourseGroup group = (CourseGroup) utilService.load(CourseGroup.class, groupId);
        
        TeachPlanArrangeAlteration alteration = new TeachPlanArrangeAlteration(
                TeachPlanArrangeAlteration.EDIT);
        TeachPlan tPlan = (TeachPlan) plan.clone();
        tPlan.setIsConfirm(plan.getIsConfirm());
        tPlan.setStd(plan.getStd());
        alteration.setBeforePlan(tPlan);
        Collection c11 = new ArrayList();
        c11.addAll(tPlan.getPlanCourses());
        alteration.getBeforePlanInfo().setIsConfirm(plan.getIsConfirm());
        alteration.setAlterationBy(getUser(request));
        alteration.setAlterationFrom(request.getRequestURI() + "?" + request.getQueryString());
        
        PlanCourse planCourse = new PlanCourse();
        RequestUtil.populate(request, planCourse, Constants.PLANCOURSE);
        // 用来判断是否新增
        Long planCourseId = planCourse.getId();
        
        // EntityUtils.evictEmptyProperty(planCourse);
        // re calculate credit per term
        Boolean autoStatCredit = getBoolean(request, "autoStatCredit");
        if (null == autoStatCredit) {
            autoStatCredit = group.getCourseType().getIsCompulsory();
        }
        boolean autoStat = (null != autoStatCredit) ? autoStatCredit.booleanValue() : true;
        
        String extra = "&teachPlan.id=" + plan.getId();
        if (planCourse.isPO()) {
            planCourseService.updatePlanCourse(planCourse, plan, autoStat);
        } else {
            if (plan.hasCourse(planCourse.getCourse())) {
                return redirect(request, new Action(this, "edit"), "teachPlan.duplicate.course",
                        extra);
            }
            planCourseService.addPlanCourse(planCourse, plan, autoStat);
        }
        
        alteration.setIsModifyGroup(Boolean.TRUE);
        alteration.setIsModifyCourse(Boolean.TRUE);
        
        StringBuffer content = new StringBuffer();
        // 课程
        content.append(TeachPlanArrangeAlteration.PLANCOURSES + ":");
        Course course = (Course) utilService.load(Course.class, planCourse.getCourse().getId());
        content.append(course.getName() + "=" + course.getCode());
        content.append(",,"
                + (null == planCourseId ? TeachPlanArrangeAlteration.BEEN_ADDED
                        : TeachPlanArrangeAlteration.BEEN_MODIFYED) + ";");
        alteration.setDescription(content.toString());
        
        alteration.setAfterPlan(plan);
        utilService.saveOrUpdate(alteration);
        
        return redirect(request, new Action(this, "edit"), "info.save.success", extra);
    }
    
    /**
     * 修改课程组
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editCourseGroup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachPlan plan = getTeachPlan(request);
        CourseGroup group = getUniqueGroup(request, plan);
        Collection unusedCourseTypeList = teachPlanService.getUnusedCourseTypes(plan);
        List courseTypeList = baseCodeService.getCodes(CourseType.class);
        
        unusedCourseTypeList.add(group.getCourseType());
        request.setAttribute("unusedCourseTypeList", unusedCourseTypeList);
        request.setAttribute("couseTypeList", courseTypeList);
        addEntity(request, plan);
        addEntity(request, group);
        return forward(request);
    }
    
    /**
     * 添加课程组
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addCourseGroup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachPlan plan = getTeachPlan(request);
        Collection unusedCourseTypeList = teachPlanService.getUnusedCourseTypes(plan);
        List courseTypeList = baseCodeService.getCodes(CourseType.class);
        request.setAttribute("unusedCourseTypeList", unusedCourseTypeList);
        request.setAttribute("couseTypeList", courseTypeList);
        addEntity(request, plan);
        addEntity(request, new CourseGroup());
        return forward(request, "editCourseGroup");
    }
    
    /**
     * 保存课程组（添加/修改）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveCourseGroup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachPlan plan = getTeachPlan(request);
        
        TeachPlanArrangeAlteration alteration = new TeachPlanArrangeAlteration(
                TeachPlanArrangeAlteration.EDIT);
        TeachPlan tPlan = (TeachPlan) plan.clone();
        tPlan.setIsConfirm(plan.getIsConfirm());
        tPlan.setStd(plan.getStd());
        alteration.setBeforePlan(tPlan);
        alteration.getBeforePlanInfo().setIsConfirm(plan.getIsConfirm());
        alteration.setAlterationBy(getUser(request));
        alteration.setAlterationFrom(request.getRequestURI() + "?" + request.getQueryString());
        
        CourseGroup group = (CourseGroup) populate(request, CourseGroup.class,
                Constants.COURSEGROUP);
        Long groupId = group.getId();
        EntityUtils.evictEmptyProperty(group);
        if (group.isPO())
            courseGroupService.updateCourseGroup(group, plan);
        else {
            courseGroupService.addCourseGroup(group, plan);
        }
        
        alteration.setIsModifyGroup(Boolean.TRUE);
        
        StringBuffer content = new StringBuffer();
        // 课程组
        content.append(TeachPlanArrangeAlteration.COURSEGROUPS + ":");
        CourseType courseType = (CourseType) utilService.load(CourseType.class, group
                .getCourseType().getId());
        content.append(courseType.getName() + "=" + courseType.getCode());
        content.append(",,"
                + (null == groupId ? TeachPlanArrangeAlteration.BEEN_ADDED
                        : TeachPlanArrangeAlteration.BEEN_MODIFYED) + ";");
        alteration.setDescription(content.toString());
        
        alteration.setAfterPlan(plan);
        utilService.saveOrUpdate(alteration);
        
        String extra = "&" + Constants.TEACHPLAN_KEY + "=" + plan.getId();
        return redirect(request, new Action(this, "edit"), "info.save.success", extra);
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
    public ActionForward removeCourseGroup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachPlan plan = getTeachPlan(request);
        
        TeachPlanArrangeAlteration alteration = new TeachPlanArrangeAlteration(
                TeachPlanArrangeAlteration.EDIT);
        TeachPlan tPlan = (TeachPlan) plan.clone();
        tPlan.setIsConfirm(plan.getIsConfirm());
        tPlan.setStd(plan.getStd());
        alteration.setBeforePlan(tPlan);
        Collection c11 = new ArrayList();
        c11.addAll(tPlan.getPlanCourses());
        alteration.getBeforePlanInfo().setIsConfirm(plan.getIsConfirm());
        alteration.setAlterationBy(getUser(request));
        alteration.setAlterationFrom(request.getRequestURI() + "?" + request.getQueryString());
        
        CourseGroup group = getUniqueGroup(request, plan);
        boolean hasCourses = CollectionUtils.isNotEmpty(group.getPlanCourses());
        courseGroupService.removeCourseGroup(group.getId(), plan.getId());
        
        Collection c22 = new ArrayList();
        c22.addAll(plan.getPlanCourses());
        
        StringBuffer content = new StringBuffer();
        // 课程组
        content.append(TeachPlanArrangeAlteration.COURSEGROUPS + ":");
        content.append(group.getCourseType().getName() + "=" + group.getCourseType().getCode());
        content.append(",," + TeachPlanArrangeAlteration.BEEN_REMOVED + ";");
        alteration.setIsModifyGroup(Boolean.TRUE);
        // 课程
        Collection c11c22 = CollectionUtils.subtract(c11, c22);
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
        }
        alteration.setIsModifyCourse(new Boolean(hasCourses));
        alteration.setDescription(content.toString());
        
        alteration.setAfterPlan(plan);
        utilService.saveOrUpdate(alteration);
        
        return redirect(request, new Action(this, "edit"), "info.delete.success", "&teachPlan.id="
                + plan.getId());
    }
    
    /**
     * 按照各个课程组计算学生计划的要求学分/课时
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward calcPlanCredit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long teachPlanId = getLong(request, "teachPlan.id");
        TeachPlan plan = (TeachPlan) utilService.get(TeachPlan.class, teachPlanId);
        
        TeachPlanArrangeAlteration alteration = new TeachPlanArrangeAlteration(
                TeachPlanArrangeAlteration.EDIT);
        TeachPlan tPlan = (TeachPlan) plan.clone();
        tPlan.setIsConfirm(plan.getIsConfirm());
        tPlan.setStd(plan.getStd());
        alteration.setBeforePlan(tPlan);
        alteration.getBeforePlanInfo().setIsConfirm(plan.getIsConfirm());
        alteration.setAlterationBy(getUser(request));
        alteration.setAlterationFrom(request.getRequestURI() + "?" + request.getQueryString());
        
        plan.setCredit(new Float(plan.statOverallCredit()));
        plan.setCreditHour(new Integer(plan.statOverallCreditHour()));
        utilService.saveOrUpdate(plan);
        
        alteration.setAfterPlan(plan);
        utilService.saveOrUpdate(alteration);
        
        return redirect(request, "edit", "info.save.success", "&teachPlan.id=" + plan.getId());
    }
    
    private TeachPlan getTeachPlan(HttpServletRequest request) {
        Long planId = getLong(request, Constants.TEACHPLAN_KEY);
        
        if (null == planId)
            throw new PojoNotExistException("plan id or planCourse id");
        return (TeachPlan) utilService.load(TeachPlan.class, planId);
        
    }
    
    private CourseGroup getUniqueGroup(HttpServletRequest request, TeachPlan plan) {
        Long groupId = getLong(request, "planCourse.courseGroup.id");
        if (null == groupId) {
            groupId = getLong(request, "courseGroup.id");
        }
        CourseGroup group = (CourseGroup) utilService.load(CourseGroup.class, groupId);
        return courseGroupService.keepUniqueGroupFor(group, plan);
    }
    
    public void setPlanCourseService(PlanCourseService planCourseService) {
        this.planCourseService = planCourseService;
    }
    
    public void setStdTeachPlanService(StdTeachPlanService stdTeachPlanService) {
        this.stdTeachPlanService = stdTeachPlanService;
    }
    
    public void setCourseGroupService(CourseGroupService courseGroupService) {
        this.courseGroupService = courseGroupService;
    }
    
}
