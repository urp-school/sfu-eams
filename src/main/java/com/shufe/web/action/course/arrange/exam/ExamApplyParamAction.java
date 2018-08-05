package com.shufe.web.action.course.arrange.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.model.course.arrange.exam.ExamApplyParam;
import com.shufe.service.course.arrange.exam.ExamApplyParamService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;


public class ExamApplyParamAction extends CalendarRestrictionSupportAction {
    
    protected ExamApplyParamService examApplyParamService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ExamApplyParam examApplyParam = new ExamApplyParam();
        Long paramsId = getLong(request, "paramsId");
        if (null != paramsId) {
            examApplyParam = (ExamApplyParam)examApplyParamService.getExamApplyParamById(paramsId);
        }
        request.setAttribute("examApplyParam", examApplyParam);
        return forward(request);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ExamApplyParam param = (ExamApplyParam)populateEntity(request,ExamApplyParam.class,"examApplyParam");
        Long calendarId =getLong(request,"calendar.id");
        if(null==param.getId()){
            List examApplyParamList = examApplyParamService.getExamApplyParam(calendarId);
            if(examApplyParamList.size()!=0){
                return forward(request, "error");
            }
        }
        if(null==param.getCalendar()){
            param.setCalendar(teachCalendarService.getTeachCalendar(calendarId));
        }
        try {
            examApplyParamService.saveExamApplyParam(param);
        } catch (PojoExistException e) {
            logHelper.info(request, "save params", e);
            return forwardError(mapping, request, new String[] { "entity.electParams",
                    "error.model.existed" });
        }
        return redirect(request, "list", "info.save.success", "&examApplyParam.calendar.id="+param.getCalendar().getId());
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long[] paramsIds = SeqStringUtil.transformToLong(get(request, "paramsIds"));
        examApplyParamService.removeExamApplyParam(paramsIds);
        String calendarId = get(request, "examApplyParam.calendar.id");
        return redirect(request, "list", "info.delete.success", "&examApplyParam.calendar.id="
                + calendarId);
    }

    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "examApplyParamList", examApplyParamService
                .getExamApplyParam(getLong(request, "examApplyParam.calendar.id")));
        return forward(request);
    }
    
    public void setExamApplyParamService(ExamApplyParamService examApplyParamService) {
        this.examApplyParamService = examApplyParamService;
    }

}
