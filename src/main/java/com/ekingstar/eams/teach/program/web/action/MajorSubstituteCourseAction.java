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
 * @author cheneystar
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * cheneystar             2008-11-7            Created
 *  
 ********************************************************************************/
package com.ekingstar.eams.teach.program.web.action;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.baseinfo.Department;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.teach.program.major.MajorSubstituteCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;
import com.shufe.web.helper.RestrictionHelper;

public class MajorSubstituteCourseAction extends RestrictionExampleTemplateAction {
    
    protected TeachPlanService teachPlanService;
    
    protected RestrictionHelper dataRealmHelper;
    
    /**
     * 培养计划查询主界面
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
     * 返回查询列表
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
        EntityQuery query = new EntityQuery(MajorSubstituteCourse.class, "majorCourse");
        populateConditions(request, query, "majorCourse.stdType.id,majorCourse.department.id");
        Long stdTypeId = getLong(request, "majorCourse.stdType.id");
        if (null != stdTypeId) {
            dataRealmHelper.addStdTypeTreeDataRealm(query, stdTypeId, "majorCourse.stdType.id",
                    request);
        }
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "majorCourses", utilService.search(query));
        return forward(request);
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addSingleParameter(request, "majorCourse", utilService.load(MajorSubstituteCourse.class,
                getLong(request, "majorCourseId")));
        return forward(request);
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long majorCourseId = getLong(request, "majorCourseId");
        if (null != majorCourseId) {
            addSingleParameter(request, "majorCourse", utilService.load(
                    MajorSubstituteCourse.class, majorCourseId));
        }
        initBaseInfos(request, "stdTypeList", StudentType.class);
        initBaseInfos(request, "departmentList", Department.class);
        return forward(request);
    }
    
    public ActionForward selector(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Boolean isWithPlan = getBoolean(request, "isWithPlan");
        String courseSeq = get(request, "courseIds");
        String pCourseSeq = get(request, "originIds");
        if (StringUtils.isEmpty(pCourseSeq)) {
            pCourseSeq = "";
        }
        if (StringUtils.isEmpty(courseSeq)) {
            courseSeq = pCourseSeq;
        } else {
            courseSeq += "," + pCourseSeq;
        }
        // 要替换的课程列表
        if (Boolean.TRUE.equals(isWithPlan)) {
            EntityQuery query = new EntityQuery(TeachPlan.class, "plan");
            populateConditions(request, query);
            query.add(new Condition("plan.std is null"));
            query.join("plan.courseGroups", "courseGroup");
            query.join("courseGroup.planCourses", "planCourse");
            query.join("planCourse.course", "course");
            if (StringUtils.isNotEmpty(courseSeq)) {
                query.add(new Condition("planCourse.course.id not in (:courseId)", SeqStringUtil
                        .transformToLong(courseSeq)));
            }
            query.add(QueryRequestSupport.extractConditions(request,
                    com.shufe.model.system.baseinfo.Course.class, "course", null));
            query.setSelect("select distinct planCourse.course");
            query.setLimit(getPageLimit(request));
            query.addOrder(OrderUtils.parser(get(request, "orderBy")));
            addCollection(request, "pCourses", utilService.search(query));
            return forward(request, "originCourseSelector");
        } else {
            EntityQuery query = new EntityQuery(Course.class, "course");
            populateConditions(request, query);
            if (StringUtils.isNotEmpty(courseSeq)) {
                query.add(new Condition("course.id not in (:courseId)", SeqStringUtil
                        .transformToLong(courseSeq)));
            }
            query.add(QueryRequestSupport.extractConditions(request,
                    com.shufe.model.system.baseinfo.Course.class, "course", null));
            query.setLimit(getPageLimit(request));
            query.addOrder(OrderUtils.parser(get(request, "orderBy")));
            addCollection(request, "courses", utilService.search(query));
            return forward(request, "courseSelector");
        }
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(MajorSubstituteCourse.class, "majorCourse");
        Long majorCourseId = getLong(request, "majorCourse.id");
        if (null != majorCourseId) {
            query.add(new Condition("majorCourse.id != (:majorCourseId)", majorCourseId));
        }
        query.add(new Condition("majorCourse.enrollTurn = :enrollTurn", get(request,
                "majorCourse.enrollTurn")));
        query.add(new Condition("majorCourse.stdType.id = :stdTypeId", getLong(request,
                "majorCourse.stdType.id")));
        Long departmentId = getLong(request, "majorCourse.department.id");
        if (null == departmentId) {
            query.add(new Condition("majorCourse.department is null"));
        } else {
            query.add(new Condition("majorCourse.department.id = :departmentId", departmentId));
        }
        Long majorId = getLong(request, "majorCourse.major.id");
        if (null == majorId) {
            query.add(new Condition("majorCourse.major is null"));
        } else {
            query.add(new Condition("majorCourse.major.id = :majorId", majorId));
        }
        Long majorFieldId = getLong(request, "majorCourse.majorField.id");
        if (null == majorFieldId) {
            query.add(new Condition("majorCourse.majorField is null"));
        } else {
            query.add(new Condition("majorCourse.majorField.id = :majorFieldId", majorFieldId));
        }
        Long[] originIds = SeqStringUtil.transformToLong(get(request, "originIds"));
        query.join("majorCourse.origins", "origin");
        query.add(new Condition("origin.id in (:originId)", originIds));
        Long[] substituteIds = SeqStringUtil.transformToLong(get(request, "courseIds"));
        query.join("majorCourse.substitutes", "substitute");
        query.add(new Condition("substitute.id in (:substituteId)", substituteIds));
        if (CollectionUtils.isNotEmpty(utilService.search(query))) {
            return forward(request, new Action(this, "edit"), "info.save.failure.overlap");
        }
        
        MajorSubstituteCourse majorCourse = (MajorSubstituteCourse) populateEntity(request,
                MajorSubstituteCourse.class, "majorCourse");
        majorCourse.getOrigins().clear();
        majorCourse.addOrigin(utilService.load(Course.class, "id", originIds));
        majorCourse.getSubstitutes().clear();
        majorCourse.addSubstitute(utilService.load(Course.class, "id", substituteIds));
        Date now = new Date();
        if (null == majorCourse.getCreateAt()) {
            majorCourse.setCreateAt(now);
        }
        majorCourse.setModifyAt(now);
        majorCourse.setOperateBy(getUser(request));
        utilService.saveOrUpdate(majorCourse);
        return redirect(request, "search", "info.save.success");
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        utilService.remove(utilService.load(MajorSubstituteCourse.class, "id", SeqStringUtil
                .transformToLong(get(request, "majorCourseIds"))));
        return redirect(request, "search", "info.action.success");
    }
    
    protected ActionForward removeAndForwad(HttpServletRequest request, Collection entities)
            throws Exception {
        try {
            utilService.remove(entities);
        } catch (Exception e) {
            return redirect(request, "teachPlanList", "info.delete.failure");
        }
        return redirect(request, "teachPlanList", "info.delete.success");
    }
    
    public void setTeachPlanService(TeachPlanService teachPlanService) {
        this.teachPlanService = teachPlanService;
    }
    
    public void setDataRealmHelper(RestrictionHelper dataRealmHelper) {
        this.dataRealmHelper = dataRealmHelper;
    }
}
