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
 * chaostone             2006-3-31            Created
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
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.teach.program.std.DefaultStdSubstituteCourse;
import com.ekingstar.eams.teach.program.std.StdSubstituteCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.service.std.StudentService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

/**
 * 可代替课程的维护响应类
 * 
 * @author James
 * 
 */
public class StdSubstituteCourseAction extends RestrictionExampleTemplateAction {
	
	StudentService studentService;
    
    protected ActionForward saveAndForwad(HttpServletRequest request, Entity entity)
            throws Exception {
        EntityQuery query = new EntityQuery(DefaultStdSubstituteCourse.class, "substitutionCourse");
        Long substitutionCourseId = getLong(request, "substitutionCourse.id");
        if (null != substitutionCourseId) {
            query.add(new Condition("substitutionCourse.id != (:substitutionCourseId)",
                    substitutionCourseId));
        }
        query.add(new Condition("substitutionCourse.std.code = :stdCode", get(request,
                "substitutionCourse.std.code")));
        Long[] originIds = SeqStringUtil.transformToLong(get(request, "originIds"));
        query.join("substitutionCourse.origins", "origin");
        query.add(new Condition("origin.id in (:originId)", originIds));
        Long[] substituteIds = SeqStringUtil.transformToLong(get(request, "courseIds"));
        query.join("substitutionCourse.substitutes", "substitute");
        query.add(new Condition("substitute.id in (:substituteId)", substituteIds));
        if (CollectionUtils.isNotEmpty(utilService.search(query))) {
            return forward(request, new Action(this, "edit"), "info.save.failure.overlap");
        }
        DefaultStdSubstituteCourse sc = (DefaultStdSubstituteCourse) entity;
        sc.getOrigins().clear();
        sc.getSubstitutes().clear();
        sc.addOrigin(utilService.load(Course.class, "id", originIds));
        sc.addSubstitute(utilService.load(Course.class, "id", substituteIds));
        Date now = new Date();
        if (null == sc.getCreateAt()) {
            sc.setCreateAt(now);
        }
        sc.setModifyAt(now);
        sc.setOperateBy(getUser(request));
        utilService.saveOrUpdate(sc);
        return redirect(request, "search", "info.save.success");
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        MajorType majorType = new MajorType(getLong(request,
                "substitutionCourse.std.firstMajor.majorType.id"));
        EntityQuery query = new EntityQuery(StdSubstituteCourse.class, "substitutionCourse");
        populateConditions(request, query);
        // 添加权限
        String departName = "substitutionCourse.std.department.id";
        if (MajorType.SECOND.equals(majorType.getId())) {
            departName = "substitutionCourse.std.secondMajor.department.id";
        }
        DataRealmUtils.addDataRealms(query, new String[] { "substitutionCourse.std.type.id",
                departName }, getDataRealmsWith(getLong(request, "substitutionCourse.std.type.id"),
                request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        query.setLimit(getPageLimit(request));
        addCollection(request, "substitutionCourses", utilService.search(query));
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
            EntityQuery queryPerson = new EntityQuery(TeachPlan.class, "planPerson");
            queryPerson.add(new Condition("planPerson.std.code = :stdCode", get(request,
            "substitutionCourse.std.code")));
            Collection list = utilService.search(queryPerson);
            if(list.size()!=0){
            	query.add(new Condition("plan.std.code = :stdCode", get(request,
                "substitutionCourse.std.code")));
            }else{
            	Student std = studentService.getStudent(get(request,"substitutionCourse.std.code"));
                query.add(new Condition("plan.enrollTurn = :enrollTurn",std.getEnrollYear()));
                query.add(new Condition("plan.department.id = :department",std.getDepartment().getId()));
                query.add(new Condition("plan.speciality.id = :speciality",std.getFirstMajor().getId()));
                if(null!=std.getFirstAspect()){
                	query.add(new Condition("plan.aspect.id = :aspect",std.getFirstAspect().getId()));
                }
            }
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
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addSingleParameter(request, "substitutionCourse", utilService.load(
                DefaultStdSubstituteCourse.class, getLong(request, "substitutionCourseId")));
        return forward(request);
    }

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
}
