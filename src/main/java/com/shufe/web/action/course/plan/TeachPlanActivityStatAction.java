//$Id: TeachPlanActivityStatAction.java,v 1.1 2009-3-2 上午10:41:49 zhouqi Exp $
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
 * zhouqi              2009-3-2             Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.plan;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachTask;

/**
 * @author zhouqi
 * 
 */
public class TeachPlanActivityStatAction extends TeachPlanSearchAction {
    
    /**
     * 查询
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
        Collection teachPlans = utilService.search(buildQuery(request));
        addCollection(request, "teachPlans", teachPlans);
        Map resultMap = new HashMap();
        for (Iterator it = teachPlans.iterator(); it.hasNext();) {
            TeachPlan plan = (TeachPlan) it.next();
            Collection courses = plan.getCourses();
            if (CollectionUtils.isEmpty(courses)) {
                resultMap.put(plan.getId().toString(), new Integer(0));
                continue;
            }
            EntityQuery query = new EntityQuery(TeachTask.class, "task");
            query.add(new Condition("task.teachClass.enrollTurn = :enrollTurn", plan
                    .getEnrollTurn()));
            query.add(new Condition("task.teachClass.stdType = :stdType", plan.getStdType()));
            
            query.add(new Condition("task.course in (:courses)", courses));
            query.groupBy("task.course.id");
            query.setSelect("count(*)");
            resultMap.put(plan.getId().toString(), new Integer(utilService.search(query).size()));
        }
        addSingleParameter(request, "statResult", resultMap);
        return forward(request);
    }
    
    /**
     * 查询单个培养计划的信息
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
        Long planId = getLong(request, "teachPlanId");
        if (null == planId) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        TeachPlan plan = teachPlanService.getTeachPlan(planId);
        Map detailMap = new HashMap();
        Collection courses = plan.getCourses();
        if (CollectionUtils.isNotEmpty(courses)) {
            EntityQuery query = new EntityQuery(TeachTask.class, "task");
            query.add(new Condition("task.teachClass.enrollTurn = :enrollTurn", plan
                    .getEnrollTurn()));
            query.add(new Condition("task.teachClass.stdType = :stdType", plan.getStdType()));
            query.add(new Condition("task.course in (:courses)", plan.getCourses()));
            query.groupBy("task.course.id");
            query.setSelect("task.course.id,count(*)");
            
            Collection activities = utilService.search(query);
            for (Iterator it = activities.iterator(); it.hasNext();) {
                Object[] obj = (Object[]) it.next();
                detailMap.put(obj[0].toString(), obj[1]);
            }
            addSingleParameter(request, "detailMap", detailMap);
        }
        
        addSingleParameter(request, "plan", plan);
        return forward(request);
    }
    
}
