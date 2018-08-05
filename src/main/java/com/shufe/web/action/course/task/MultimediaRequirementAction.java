//$Id: MultimediaRequirementAction.java,v 1.1 2012-5-18 zhouqi Exp $
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
 * zhouqi				2012-5-18             Created
 *  
 ********************************************************************************/

/**
 * 
 */
package com.shufe.web.action.course.task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.beanfuse.lang.SeqStringUtil;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.model.course.task.MultimediaRequirement;
import com.shufe.model.course.task.TeachTask;

/**
 * 多媒体教室要求
 * 
 * @author zhouqi
 * 
 */
public class MultimediaRequirementAction extends TeachTaskRequirementAction {
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(MultimediaRequirement.class, "multiRequire");
        query.add(new Condition("multiRequire.task.calendar.id = :calendarId", getLong(request,
                "calendar.id")));
        query.join("multiRequire.task.arrangeInfo.teachers", "teacher");
        query.add(new Condition("teacher = :teacher", getTeacherFromSession(request.getSession())));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        query.setSelect("distinct multiRequire");
        addCollection(request, "multiRequires", utilService.search(query));
        return forward(request);
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addSingleParameter(request, "multiRequire", utilService.load(MultimediaRequirement.class,
                getLong(request, "multiRequireId")));
        addSystemParams(request);
        return forward(request);
    }
    
    /**
     * 显示要添加配置教室的教学任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward teachTaskSearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(TeachTask.class, "task");
        populateConditions(request, query);
        query.add(new Condition("task.calendar.id = :calendarId", getLong(request, "calendarId")));
        query.join("task.arrangeInfo.teachers", "teacher");
        query.add(new Condition("teacher = :teacher", getTeacherFromSession(request.getSession())));
        query.add(new Condition("exists (from task.arrangeInfo.activities activity)"));
        query.add(new Condition("not exists (from MultimediaRequirement multiRequire where multiRequire.task = task)"));
        String className = get(request, "className");
        if (StringUtils.isNotBlank(className)) {
            query.add(new Condition("exists (from task.teachClass.adminClasses adminClass where adminClass.name like '%' || :className || '%')", className));
        }
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        query.setSelect("distinct task");
        addCollection(request, "tasks", utilService.search(query));
        return forward(request, "teachTaskList");
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long multiRequireId = getLong(request, "multiRequireId");
        if (null == multiRequireId) {
            addSingleParameter(request, "task", utilService.load(TeachTask.class, getLong(request,
                    "taskId")));
        } else {
            addSingleParameter(request, "multiRequire", utilService.load(
                    MultimediaRequirement.class, multiRequireId));
        }
        return forward(request);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        utilService.saveOrUpdate(populateEntity(request, MultimediaRequirement.class,
                "multiRequire"));
        return redirect(request, "search", "info.save.success", "&calendar.id="
                + get(request, "calendarId"));
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        utilService.remove(utilService.load(MultimediaRequirement.class, "id",
                SeqStringUtil.transformToLong(get(request, "multiRequireIds"))));
        return redirect(request, "search", "info.action.success", "&calendar.id="
                + getLong(request, "calendarId"));
    }
}
