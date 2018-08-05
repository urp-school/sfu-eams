//$Id: TeachPlanCourseAction.java,v 1.1 2009-3-3 上午10:40:03 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2009-3-3             Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.plan;

import java.util.Collection;
import java.util.Iterator;
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
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 从课程角度统计培养
 * 
 * @author zhouqi
 * 
 */
public class TeachPlanCourseStatAction extends RestrictionSupportAction {
    
    /**
     * 主界面
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
        addCollection(request, "stdTypeList", baseInfoService.getBaseInfos(StudentType.class));
        addCollection(request, "courseTypeList", baseCodeService.getCodes(CourseType.class));
        return forward(request);
    }
    
    /**
     * 查询/统计
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
        EntityQuery query = buildQuery(request);
        query.groupBy("planCourse.course.id");
        query.setSelect("select distinct planCourse.course.id, count(*)");
        Collection results = utilService.search(query);
        for (Iterator it = results.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            obj[0] = utilService.load(Course.class, (Long) obj[0]);
        }
        addCollection(request, "results", results);
        return forward(request);
    }
    
    /**
     * @param request
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(PlanCourse.class, "planCourse");
        populateConditions(request, query);
        query.join("planCourse.courseGroup.teachPlan", "teachPlan");
        List conditions = QueryRequestSupport.extractConditions(request, TeachPlan.class,
                "teachPlan", null);
        query.add(conditions);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        return query;
    }
    
    /**
     * 查询详情
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        courseStatInfo(request);
        return forward(request);
    }
    
    /**
     * 准备删除
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward toRemove(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        courseStatInfo(request);
        return forward(request);
    }
    
    /**
     * @param request
     */
    protected void courseStatInfo(HttpServletRequest request) {
        Course course = (Course) utilService.load(Course.class, getLong(request, "courseId"));
        
        Collection results = getAllTeachPlan(request, course);
        long majorCount = 0, stdCount = 0;
        for (Iterator it = results.iterator(); it.hasNext();) {
            TeachPlan plan = (TeachPlan) it.next();
            if (null == plan.getStd()) {
                majorCount++;
            } else {
                stdCount++;
            }
        }
        
        addSingleParameter(request, "course", course);
        addSingleParameter(request, "planCount", new Long[] { new Long(majorCount),
                new Long(stdCount) });
        addCollection(request, "results", results);
    }
    
    /**
     * 查出得到指定课程的培养计划
     * 
     * @param request
     * @param course
     * @return
     */
    protected Collection getAllTeachPlan(HttpServletRequest request, Course course) {
        EntityQuery query = buildQuery(request);
        query.setLimit(null);
        query.add(new Condition("planCourse.course.id = :courseId", course.getId()));
        query.setSelect("select distinct teachPlan");
        Collection results = utilService.search(query);
        return results;
    }
    
    /**
     * 从指定或全部培养计划中，删除指定课程
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
        Course course = (Course) utilService.load(Course.class, getLong(request, "courseId"));
        String planIdSeq = get(request, "teachPlanIds");
        Collection plans = null;
        if (StringUtils.isEmpty(planIdSeq)) {
            plans = getAllTeachPlan(request, course);
        } else {
            plans = utilService.load(TeachPlan.class, "id", SeqStringUtil
                    .transformToLong(planIdSeq));
        }
        for (Iterator it = plans.iterator(); it.hasNext();) {
            TeachPlan plan = (TeachPlan) it.next();
            CourseGroup group = plan.getCourseGroup(plan.getPlanCourse(course).getCourseGroup()
                    .getCourseType());
            group.removePlanCourse(course);
            group.statCreditAndHour(plan.getTermsCount().intValue());
        }
        utilService.saveOrUpdate(plans);
        
        return redirect(request, "search", "info.action.success");
    }
}
