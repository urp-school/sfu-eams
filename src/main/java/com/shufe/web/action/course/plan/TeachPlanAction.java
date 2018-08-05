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
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.dao.DataIntegrityViolationException;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.baseinfo.service.CourseService;
import com.shufe.model.Constants;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.plan.TeachPlanArrangeAlteration;
import com.shufe.model.course.plan.Terms;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.service.course.plan.CourseGroupService;
import com.shufe.util.DataAuthorityUtil;

/**
 * 培养计划管理服务类
 * 
 * @author chaostone
 */
public class TeachPlanAction extends TeachPlanSearchAction {
    
    private CourseGroupService courseGroupService;
    
    private CourseService courseService;
    
    /**
     * 培养计划管理主界面
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
     * 新建培养计划
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeCollege);
        request.setAttribute(Constants.TEACHPLAN, new TeachPlan());
        return forward(request);
    }
    
    /**
     * 保存培养计划的基本信息
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
        Long planId = getLong(request, "teachPlan.id");
        
        TeachPlan tPlan = null;
        if (null != planId) {
            tPlan = (TeachPlan) teachPlanService.getTeachPlan(planId).clone();
        }
        TeachPlan plan = (TeachPlan) populateEntity(request, TeachPlan.class, "teachPlan");
        if (teachPlanService.isDuplicate(plan)) {
            return forwardError(mapping, request, new String[] { "entity.teachPlan",
                    "error.model.existed" });
        }
        if (null != planId) {
            tPlan.setIsConfirm(plan.getIsConfirm());
            tPlan.setStd(plan.getStd());
            
            utilService.initialize(tPlan);
            utilService.initialize(tPlan.getCourseGroups());
            for (Iterator it = tPlan.getCourseGroups().iterator(); it.hasNext();) {
                CourseGroup group = (CourseGroup) it.next();
                utilService.initialize(group.getPlanCourses());
            }
            request.getSession().setAttribute("tPlan", tPlan);
        }
        teachPlanService.saveTeachPlan(plan);
        return redirect(request, new Action(this, "saveLog"), "info.save.success", "&teachPlan.id="
                + plan.getId() + "&planId=" + planId + "&rPath=" + request.getRequestURI() + "?"
                + request.getQueryString());
    }
    
    /**
     * 保存日志
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveLog(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long teachPlanId = new Long(get(request, "teachPlan.id"));
        TeachPlan tPlan = (TeachPlan) request.getSession().getAttribute("tPlan");
        
        // 记录日志（新建/修改）
        boolean isNew = null == tPlan;
        TeachPlanArrangeAlteration alteration = new TeachPlanArrangeAlteration(
                isNew ? TeachPlanArrangeAlteration.NEW : TeachPlanArrangeAlteration.EDIT);
        if (!isNew) {
            alteration.setBeforePlan(tPlan);
        }
        alteration.setAfterPlan(teachPlanService.getTeachPlan(teachPlanId));
        alteration.setAlterationBy(getUser(request));
        alteration.setAlterationFrom(get(request, "rPath"));
        utilService.saveOrUpdate(alteration);
        request.getSession().removeAttribute("tPlan");
        return redirect(request, "edit", "info.save.success", "&teachPlan.id=" + teachPlanId);
    }
    
    /**
     * 删除培养计划
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
        Long planId = getLong(request, Constants.TEACHPLAN_KEY);
        TeachPlan plan = teachPlanService.getTeachPlan(planId);
        if (null == plan) {
            return forwardError(mapping, request, "error.model.notExist");
        }
        TeachPlanArrangeAlteration alteration = new TeachPlanArrangeAlteration(
                TeachPlanArrangeAlteration.DELETE);
        try {
            
            StringBuffer content = new StringBuffer();
            // 课程组
            Collection c1c2 = plan.getCourseGroups();
            if (CollectionUtils.isNotEmpty(c1c2)) {
                content.append(TeachPlanArrangeAlteration.COURSEGROUPS + ":");
                for (Iterator it = c1c2.iterator(); it.hasNext();) {
                    CourseGroup group = (CourseGroup) it.next();
                    content.append(group.getCourseType().getName() + "="
                            + group.getCourseType().getCode());
                    if (it.hasNext()) {
                        content.append(",");
                    }
                }
                content.append(",," + TeachPlanArrangeAlteration.BEEN_REMOVED + ";");
                alteration.setIsModifyGroup(Boolean.TRUE);
            }
            // 课程
            Collection c11c22 = plan.getPlanCourses();
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
            
            alteration.setBeforePlan(plan);
            alteration.setAlterationBy(getUser(request));
            alteration.setAlterationFrom(request.getRequestURI() + "?" + request.getQueryString());
            teachPlanService.removeTeachPlan(plan.getId());
        } catch (DataIntegrityViolationException e) {
            return forwardError(mapping, request, "info.delete.failure");
        }
        utilService.saveOrUpdate(alteration);
        
        return redirect(request, new Action(TeachPlanAction.class, "search"),
                "info.delete.success", (String) null);
    }
    
    /**
     * 修改培养计划
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
        Long planId = getLong(request, Constants.TEACHPLAN_KEY);
        TeachPlan plan = teachPlanService.getTeachPlan(planId);
        if (null == plan) {
            return forwardError(mapping, request, "error.model.notExist");
        }
        String stdTypeDataRealm = getStdTypeIdSeq(request);
        String departDataRealm = getDepartmentIdSeq(request);
        if (!DataAuthorityUtil.isInDataRealm("TeachPlan", plan, stdTypeDataRealm, departDataRealm)) {
            return forwardError(mapping, request, "error.dataRealm.insufficient");
        }
        
        request.setAttribute(Constants.DEPARTMENT_LIST, utilService.load(Department.class,
                "isTeaching", Boolean.TRUE));
        request.setAttribute("stdTypeList", studentTypeService.getStudentTypes(stdTypeDataRealm));
        request.setAttribute(Constants.TEACHPLAN, plan);
        return forward(request);
    }
    
    /**
     * 生成培养计划的提示和选择界面.<br>
     * （从专业生成个人/专业、或者从个人生成个人）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward genPompt(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long planId = getLong(request, "targetPlan.id");
        if (null == planId)
            throw new PojoNotExistException("teachPlan");
        
        TeachPlan plan = teachPlanService.getTeachPlan(planId);
        String genPlanType = request.getParameter("genPlanType");
        
        if (plan.isStdTeachPlan() && genPlanType.equals("speciality"))
            return forwardError(mapping, request, "error.genSpecialityPlanFromStd.notsupported");
        if (genPlanType.equals("speciality")) {
            setDataRealm(request, hasStdTypeCollege);
            request.setAttribute(Constants.TEACHPLAN, plan);
            return forward(request, "genSpecialityPlanForm");
        } else if (genPlanType.equals("std")) {
            request.setAttribute(Constants.TEACHPLAN, plan);
            request.setAttribute("stdList", teachPlanService.getStdsWithoutPersonalPlan(plan));
            return forward(request, "genStdPlanForm");
        } else
            throw new RuntimeException("not supported teachPlan type");
    }
    
    /**
     * 生成培养计划.<br>
     * （从专业生成个人/专业、或者从个人生成个人）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward gen(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long planId = getLong(request, "targetPlan.id");
        if (null == planId)
            throw new PojoNotExistException("teachPlan");
        
        TeachPlan targetPlan = teachPlanService.getTeachPlan(planId);
        String genPlanType = request.getParameter("genPlanType");
        List genPlans = null;
        if (genPlanType.equals("speciality")) {
            TeachPlan genPlan = (TeachPlan) populate(request, TeachPlan.class, Constants.TEACHPLAN);
            if (teachPlanService.isTeachPlanExists(genPlan))
                return forwardError(mapping, request, new String[] { "entity.teachPlan",
                        "error.model.existed" });
            else {
                genPlan.addCourseGroups(targetPlan.getCourseGroups());
                genPlan.setStdType((StudentType) utilService.load(StudentType.class, genPlan
                        .getStdType().getId()));
                genPlan.setDepartment((Department) utilService.load(Department.class, genPlan
                        .getDepartment().getId()));
                if (genPlan.getSpeciality().isPO()) {
                    genPlan.setSpeciality((Speciality) utilService.load(Speciality.class, genPlan
                            .getSpeciality().getId()));
                }
                if (null != genPlan.getAspect() && genPlan.getAspect().isPO()) {
                    genPlan.setAspect((SpecialityAspect) utilService.load(SpecialityAspect.class,
                            genPlan.getAspect().getId()));
                }
                teachPlanService.saveTeachPlan(genPlan);
                genPlans = Collections.singletonList(genPlan);
            }
        } else if (genPlanType.equals("std")) {
            String stdIdSeq = request.getParameter("stdIdSeq");
            genPlans = teachPlanService.genTeachPlanForStd(stdIdSeq, targetPlan);
        } else
            throw new RuntimeException("not supported teachPlan type");
        request.setAttribute(Constants.TEACHPLAN_LIST, genPlans);
        
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.gen.success"));
        saveErrors(request, messages);
        
        return forward(request, "genPlanList");
    }
    
    public ActionForward batchGen(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String enrollTurn = request.getParameter("enrollTurn");
        if (StringUtils.isEmpty(enrollTurn)) {
            return forwardError(mapping, request, "error.parameters.illegal");
        }
        String planIdSeq = request.getParameter("planIdSeq");
        if (StringUtils.isEmpty(planIdSeq)) {
            return forward(mapping, request, "error.parameters.needed");
        } else {
            List plans = teachPlanService.getTeachPlans(planIdSeq);
            List genPlans = teachPlanService.genTeachPlanOfSpecialityBy(plans, enrollTurn);
            
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.gen.success"));
            saveErrors(request, messages);
            request.setAttribute(Constants.TEACHPLAN_LIST, genPlans);
            return forward(request, "genPlanList");
        }
    }
    
    public ActionForward batchProcessGroupSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List plans = utilService.load(TeachPlan.class, "id", SeqStringUtil.transformToLong(request
                .getParameter("teachPlanIds")));
        request.setAttribute("teachPlans", plans);
        // 找到这些计划的共同学期数
        Integer termsCount = ((TeachPlan) (plans.get(0))).getTermsCount();
        for (int i = 1; i < plans.size(); i++) {
            TeachPlan plan = (TeachPlan) plans.get(i);
            if (!termsCount.equals(plan.getTermsCount())) {
                termsCount = null;
                break;
            }
        }
        if (null != termsCount) {
            request.setAttribute("termsCount", termsCount);
        }
        addCollection(request, "courseTypes", baseCodeService.getCodes(CourseType.class));
        addCollection(request, Constants.DEPARTMENT_LIST, departmentService.getTeachDeparts());
        return forward(request);
    }
    
    /**
     * 培养计划课程组批量处理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchProcessGroup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = get(request, "batchAction");
        List plans = utilService.load(TeachPlan.class, "id", SeqStringUtil.transformToLong(request
                .getParameter("teachPlanIds")));
        Boolean autoCalcCredit = getBoolean(request, "autoCalcCredit");
        if (null == autoCalcCredit) {
            autoCalcCredit = Boolean.FALSE;
        }
        if (ObjectUtils.equals(action, "remove")) {
            Long courseTypeId = getLong(request, "courseType.id");
            CourseType courseType = (CourseType) baseCodeService.getCode(CourseType.class,
                    courseTypeId);
            for (Iterator iter = plans.iterator(); iter.hasNext();) {
                TeachPlan plan = (TeachPlan) iter.next();
                CourseGroup group = plan.getCourseGroup(courseType);
                if (null != group) {
                    courseGroupService.removeCourseGroup(group.getId(), plan.getId());
                    if (Boolean.TRUE.equals(autoCalcCredit)) {
                        plan.setCredit(new Float(plan.statOverallCredit()));
                    }
                }
            }
            if (Boolean.TRUE.equals(autoCalcCredit)) {
                utilService.saveOrUpdate(plans);
            }
        } else if (ObjectUtils.equals(action, "add")) {
            CourseGroup courseGroup = (CourseGroup) populateEntity(request, CourseGroup.class,
                    "courseGroup");
            utilService.saveOrUpdate(courseGroup);
            for (Iterator iter = plans.iterator(); iter.hasNext();) {
                TeachPlan plan = (TeachPlan) iter.next();
                CourseGroup existed = plan.getCourseGroup(courseGroup.getCourseType());
                if (null == existed) {
                    plan.getCourseGroups().add(courseGroup);
                    if (Boolean.TRUE.equals(autoCalcCredit)) {
                        plan.setCredit(new Float(plan.statOverallCredit()));
                    }
                }
            }
            utilService.saveOrUpdate(plans);
        }
        return redirect(request, new Action(TeachPlanAction.class, "search"), "info.action.success");
    }
    
    public ActionForward batchProcessCourseSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    public ActionForward showUserChoiceInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List courseList = new ArrayList();
        String courseCodes = get(request, "courseCodes");
        String[] codes = courseCodes.split(",");
        for (int i = 0; i < codes.length; i++) {
            courseList.add(courseService.getCourse(codes[i].trim().toString()));
        }
        request.setAttribute("teachPlans", utilService.load(TeachPlan.class, "id", SeqStringUtil
                .transformToLong(request.getParameter("teachPlanIds"))));
        request.setAttribute("courseType", (CourseType) baseCodeService.getCode(CourseType.class,
                getLong(request, "courseType.id")));
        request.setAttribute("courseList", courseList);
        request.setAttribute("courseCodes", courseCodes);
        request.setAttribute("department", departmentService.getDepartment(getLong(request,
                "department.id")));
        request.setAttribute("termSeq", get(request, "termSeq"));
        return forward(request);
    }
    
    public ActionForward saveTeachPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List plans = utilService.load(TeachPlan.class, "id", SeqStringUtil.transformToLong(request
                .getParameter("teachPlanIds")));
        Long courseTypeId = getLong(request, "courseType.id");
        CourseType courseType = (CourseType) baseCodeService
                .getCode(CourseType.class, courseTypeId);
        Department department = departmentService.getDepartment(getLong(request, "department.id"));
        String courseCodes = get(request, "courseCodes");
        String termSeq = get(request, "termSeq");
        String[] codes = courseCodes.split(",");
        TeachPlan plan = null;
        CourseGroup group = null;
        PlanCourse planCourse = null;
        List newPlans = new ArrayList();
        for (Iterator iter = plans.iterator(); iter.hasNext();) {
            plan = (TeachPlan) iter.next();
            for (Iterator iterator = plan.getCourseGroups().iterator(); iterator.hasNext();) {
                group = (CourseGroup) iterator.next();
                if (group.getCourseType().getId().equals(courseTypeId)) {
                    for (int i = 0; i < codes.length; i++) {
                        planCourse = new PlanCourse();
                        planCourse.setCourse((Course) courseService.getCourse(codes[i].trim()
                                .toString()));
                        planCourse.setTerms(new Terms(termSeq));
                        planCourse.setTeachDepart(department);
                        planCourse.setCourseGroup(group);
                        group.getPlanCourses().add(planCourse);
                        if (Boolean.TRUE.equals(courseType.getIsCompulsory())) {
                            group.statCreditAndHour(plan.getTermsCount().intValue());
                        }
                    }
                }
            }
            newPlans.add(plan);
        }
        utilService.saveOrUpdate(newPlans);
        return redirect(request, new Action(TeachPlanAction.class, "search"), "info.action.success");
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        Long[] planIds = (Long[]) SeqStringUtil.transformToLong(get(request, "planIds"));
        EntityQuery query = new EntityQuery(TeachPlan.class, "plan");
        query.add(new Condition("plan.id in (:ids)", planIds));
        query.join("left", "plan.speciality", "speciality");
        query.join("left", "plan.aspect", "aspect");
        query.join("plan.courseGroups", "courseGroup");
        query.join("courseGroup.planCourses", "planCourse");
        query
                .setSelect("plan.enrollTurn,speciality.name,aspect.name,planCourse.course.code,planCourse.course.name,planCourse.course.credits,planCourse.terms");
        query.addOrder(OrderUtils.parser("speciality.name,aspect.name"));
        return utilService.search(query);
    }
    
    public ActionForward batchProcessCourse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    public void setCourseGroupService(CourseGroupService courseGroupService) {
        this.courseGroupService = courseGroupService;
    }
    
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
    
}
