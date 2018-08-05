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
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.shufe.model.course.task.LaboratoryRequirement;
import com.shufe.model.course.task.TeachTask;

/**
 * 实验室要求
 * 
 **/
public class LaboratoryRequirementAction extends TeachTaskRequirementAction {
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(LaboratoryRequirement.class, "labRequire");
        query.add(new Condition("labRequire.task.calendar.id = :calendarId", getLong(request,
                "calendar.id")));
        query.join("labRequire.task.arrangeInfo.teachers", "teacher");
        query.add(new Condition("teacher = :teacher", getTeacherFromSession(request.getSession())));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        query.setSelect("distinct labRequire");
        addCollection(request, "labRequires", utilService.search(query));
        return forward(request);
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addSingleParameter(request, "labRequire", utilService.load(LaboratoryRequirement.class,
                getLong(request, "labRequireId")));
        addSystemParams(request);
        return forward(request);
    }
    
    public ActionForward teachTaskSearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(TeachTask.class, "task");
        populateConditions(request, query);
        query.add(new Condition("task.calendar.id = :calendarId", getLong(request, "calendarId")));
        query.join("task.arrangeInfo.teachers", "teacher");
        query.add(new Condition("teacher = :teacher", getTeacherFromSession(request.getSession())));
        query.add(new Condition("exists (from task.arrangeInfo.activities activity)"));
        query.add(new Condition("not exists (from LaboratoryRequirement labRequire where labRequire.task = task)"));
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
        Long labRequireId = getLong(request, "labRequireId");
        if (null == labRequireId) {
            addSingleParameter(request, "task", utilService.load(TeachTask.class, getLong(request,
                    "taskId")));
        } else {
            addSingleParameter(request, "labRequire", utilService.load(LaboratoryRequirement.class,
                    labRequireId));
        }
        request.setAttribute("classroomTypes", baseCodeService.getCodes(ClassroomType.class));
        return forward(request);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        utilService.saveOrUpdate(populateEntity(request, LaboratoryRequirement.class, "labRequire"));
        return redirect(request, "search", "info.save.success", "&calendar.id="
                + get(request, "calendarId"));
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        utilService.remove(utilService.load(LaboratoryRequirement.class, "id",
                SeqStringUtil.transformToLong(get(request, "labRequireIds"))));
        return redirect(request, "search", "info.action.success", "&calendar.id="
                + getLong(request, "calendarId"));
    }
}
