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
import com.shufe.model.course.task.TeachTaskParam;
import com.shufe.service.course.task.TeachTaskParamService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public class TeachTaskParamAction extends CalendarRestrictionSupportAction {
    
    protected TeachTaskParamService teachTaskParamService;
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    public void setTeachTaskParamService(TeachTaskParamService teachTaskParamService) {
        this.teachTaskParamService = teachTaskParamService;
    }
    
    /**
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
        get(request, "calendar.id");
        setDataRealm(request, hasStdTypeCollege);
        TeachTaskParam teachTaskParam = new TeachTaskParam();
        Long paramsId = getLong(request, "paramsId");
        if (null != paramsId) {
            teachTaskParam = (TeachTaskParam) utilService.load(TeachTaskParam.class, paramsId);
        }
        request.setAttribute("teachTaskParam", teachTaskParam);
        return forward(request);
    }
    
    /**
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
        TeachTaskParam params = (TeachTaskParam) populateEntity(request, TeachTaskParam.class,
                "teachTaskParam");
        Long calendarId = getLong(request, "calendar.id");
        if (null == params.getId()) {
            EntityQuery query = new EntityQuery(TeachTaskParam.class, "teachTaskParam");
            query.add(new Condition("teachTaskParam.calendar.id=" + calendarId));
            List teachTaskParamList = (List) utilService.search(query);
            if (teachTaskParamList.size() != 0) {
                return forward(request, "error");
            }
        }
        
        if (null == params.getCalendar()) {
            params.setCalendar(teachCalendarService.getTeachCalendar(calendarId));
        }
        try {
            teachTaskParamService.saveTeachTaskParam(params);
        } catch (PojoExistException e) {
            logHelper.info(request, "save params", e);
            return forwardError(mapping, request, new String[] { "entity.electParams",
                    "error.model.existed" });
        }
        return redirect(request, "list", "info.save.success", "&teachTaskParams.calendar.id="
                + params.getCalendar().getId());
    }
    
    /**
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
        Long[] paramsIds = SeqStringUtil.transformToLong(get(request, "paramsIds"));
        if (null == paramsIds || paramsIds.length == 0) {
            return forwardError(mapping, request, "error.electParams.id.needed");
        }
        utilService.remove(utilService.load(TeachTaskParam.class, "id", paramsIds));
        String calendarId = get(request, "teachTaskParam.calendar.id");
        return redirect(request, "list", "info.delete.success", "&teachTaskParam.calendar.id="
                + calendarId);
    }
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "teachTaskParamList", teachTaskParamService
                .getTeachTaskParam(getLong(request, "teachTaskParams.calendar.id")));
        return forward(request);
    }
}
