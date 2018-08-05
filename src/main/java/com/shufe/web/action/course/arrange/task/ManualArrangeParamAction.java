package com.shufe.web.action.course.arrange.task;

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
import com.shufe.model.course.arrange.task.ManualArrangeParam;
import com.shufe.service.course.arrange.task.ManualArrangeParamService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;


public class ManualArrangeParamAction extends CalendarRestrictionSupportAction {
    
    protected ManualArrangeParamService manualArrangeParamService;

    
    public void setManualArrangeParamService(ManualArrangeParamService manualArrangeParamService) {
        this.manualArrangeParamService = manualArrangeParamService;
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        get(request, "calendar.id");
        setDataRealm(request, hasStdTypeCollege);
        ManualArrangeParam manualArrangeParam = new ManualArrangeParam();
        Long paramsId = getLong(request, "paramsId");
        if (null != paramsId) {
            manualArrangeParam = (ManualArrangeParam) utilService.load(ManualArrangeParam.class, paramsId);
        }
        request.setAttribute("manualArrangeParam", manualArrangeParam);
        return forward(request);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ManualArrangeParam params = (ManualArrangeParam) populateEntity(request, ManualArrangeParam.class,
                "manualArrangeParam");
        Long calendarId = getLong(request, "calendar.id");
        if (null == params.getId()) {
            EntityQuery query = new EntityQuery(ManualArrangeParam.class, "manualArrangeParam");
            query.add(new Condition("manualArrangeParam.calendar.id=" + calendarId));
            List manualArrangeParamList = (List) utilService.search(query);
            if (manualArrangeParamList.size() != 0) {
                return forward(request, "error");
            }
        }
        
        if (null == params.getCalendar()) {
            params.setCalendar(teachCalendarService.getTeachCalendar(calendarId));
        }
        try {
            manualArrangeParamService.saveManualArrangeParam(params);
        } catch (PojoExistException e) {
            logHelper.info(request, "save params", e);
            return forwardError(mapping, request, new String[] { "entity.electParams",
                    "error.model.existed" });
        }
        return redirect(request, "list", "info.save.success", "&manualArrangeParam.calendar.id="
                + params.getCalendar().getId());
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long[] paramsIds = SeqStringUtil.transformToLong(get(request, "paramsIds"));
        if (null == paramsIds || paramsIds.length == 0) {
            return forwardError(mapping, request, "error.electParams.id.needed");
        }
        utilService.remove(utilService.load(ManualArrangeParam.class, "id", paramsIds));
        String calendarId = get(request, "manualArrangeParam.calendar.id");
        return redirect(request, "list", "info.delete.success", "&manualArrangeParam.calendar.id="
                + calendarId);
    }
    
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "manualArrangeParamList", manualArrangeParamService
                .getManualArrangeParam(getLong(request, "manualArrangeParam.calendar.id")));
        return forward(request);
    }

}
