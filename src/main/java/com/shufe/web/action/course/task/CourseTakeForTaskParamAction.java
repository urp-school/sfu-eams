package com.shufe.web.action.course.task;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.course.task.CourseTakeForTaskParam;
import com.shufe.service.course.task.CourseTakeForTaskParamService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public class CourseTakeForTaskParamAction extends CalendarRestrictionSupportAction {
    
    protected CourseTakeForTaskParamService courseTakeForTaskParamService;
    
    public void setCourseTakeForTaskParamService(
            CourseTakeForTaskParamService courseTakeForTaskParamService) {
        this.courseTakeForTaskParamService = courseTakeForTaskParamService;
    }
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        get(request, "calendar.id");
        setDataRealm(request, hasStdTypeCollege);
        CourseTakeForTaskParam courseTakeForTaskParam = new CourseTakeForTaskParam();
        Long paramsId = getLong(request, "paramsId");
        if (null != paramsId) {
            courseTakeForTaskParam = (CourseTakeForTaskParam) utilService.load(
                    CourseTakeForTaskParam.class, paramsId);
        }
        request.setAttribute("courseTakeForTaskParam", courseTakeForTaskParam);
        return forward(request);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        CourseTakeForTaskParam params = (CourseTakeForTaskParam) populateEntity(request,
                CourseTakeForTaskParam.class, "courseTakeForTaskParam");
        Long calendarId = getLong(request, "calendar.id");
        if (null == params.getId()) {
            EntityQuery query = new EntityQuery(CourseTakeForTaskParam.class,
                    "courseTakeForTaskParam");
            query.add(new Condition("courseTakeForTaskParam.calendar.id=" + calendarId));
            List courseTakeForTaskParamList = (List) utilService.search(query);
            if (courseTakeForTaskParamList.size() != 0) {
                return forward(request, "error");
            }
        }
        if (null == params.getCalendar()) {
            params.setCalendar(teachCalendarService.getTeachCalendar(calendarId));
        }
        try {
            courseTakeForTaskParamService.saveCourseTakeForTaskParam(params);
        } catch (PojoExistException e) {
            logHelper.info(request, "save params", e);
            return forwardError(mapping, request, new String[] { "entity.electParams",
                    "error.model.existed" });
        }
        return redirect(request, "list", "info.save.success",
                "&courseTakeForTaskParam.calendar.id=" + params.getCalendar().getId());
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long[] paramsIds = SeqStringUtil.transformToLong(get(request, "paramsIds"));
        if (null == paramsIds || paramsIds.length == 0) {
            return forwardError(mapping, request, "error.electParams.id.needed");
        }
        utilService.remove(utilService.load(CourseTakeForTaskParam.class, "id", paramsIds));
        String calendarId = get(request, "courseTakeForTaskParam.calendar.id");
        return redirect(request, "list", "info.delete.success",
                "&courseTakeForTaskParam.calendar.id=" + calendarId);
    }
    
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "courseTakeForTaskParamList", courseTakeForTaskParamService
                .getCourseTakeForTaskParam(getLong(request, "courseTakeForTaskParam.calendar.id")));
        return forward(request);
    }
}
