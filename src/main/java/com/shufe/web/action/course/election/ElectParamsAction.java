//$Id: ElectParamsAction.java,v 1.7 2006/12/25 12:27:50 duanth Exp $
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
 * chaostone            2005-12-12          Created
 * zq                   2007-09-18          修改或替换了本Action中的所有info()方法 
 ********************************************************************************/

package com.shufe.web.action.course.election;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.Constants;
import com.shufe.model.course.election.ElectParams;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.election.ElectParamsService;
import com.shufe.service.system.baseinfo.SpecialityService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public class ElectParamsAction extends CalendarRestrictionSupportAction {
    
    private ElectParamsService paramsService;
    
    private SpecialityService specialityService;
    
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
        addCollection(request, "majors", specialityService.getSpecialities(new MajorType(
                MajorType.FIRST)));
        addCollection(request, "majorFields", specialityService.getSpecialities(new MajorType(
                MajorType.FIRST)));
        initBaseCodes(request, "courseTypes", CourseType.class);
        ElectParams params = new ElectParams();
        Long paramsId = getLong(request, "paramsId");
        if (null != paramsId) {
            params = (ElectParams) utilService.load(ElectParams.class, paramsId);
        }
        request.setAttribute(Constants.ELECTPARAMS, params);
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
        ElectParams params = (ElectParams) populateEntity(request, ElectParams.class, "electParams");
        if (null == params.getCalendar()) {
            params.setCalendar(teachCalendarService
                    .getTeachCalendar(getLong(request, "calendar.id")));
        }
        setCollectionFields(request, params);
        try {
            paramsService.saveElectParams(params);
        } catch (PojoExistException e) {
            logHelper.info(request, "save params", e);
            return forwardError(mapping, request, new String[] { "entity.electParams",
                    "error.model.existed" });
        }
        return redirect(request, "list", "info.save.success", "&electParams.calendar.id="
                + params.getCalendar().getId());
    }
    
    private void setCollectionFields(HttpServletRequest request, ElectParams params) {
        params.getDeparts().clear();
        params.getDeparts().addAll(
                utilService.load(Department.class, "id", SeqStringUtil.transformToLong(get(request,
                        "departIds"))));
        params.getStdTypes().clear();
        params.getStdTypes().addAll(
                utilService.load(StudentType.class, "id", SeqStringUtil.transformToLong(get(
                        request, "stdTypeIds"))));
        params.getEnrollTurns().clear();
        params.getEnrollTurns().addAll(
                Arrays.asList(StringUtils.split(get(request, "enrollTurns"), ",")));
        params.getMajors().clear();
        params.getMajors().addAll(
                utilService.load(Speciality.class, "id", SeqStringUtil.transformToLong(get(request,
                        "majorIds"))));
        params.getMajorFields().clear();
        params.getMajorFields().addAll(
                utilService.load(SpecialityAspect.class, "id", SeqStringUtil.transformToLong(get(
                        request, "majorFieldIds"))));
        
        params.getStds().clear();
        String stdCodes = get(request, "stdCodes");
        if (StringUtils.isNotBlank(stdCodes)) {
            params.getStds().addAll(
                    utilService.load(Student.class, "code", StringUtils.split(stdCodes, ",")));
        }
        params.getNotCurrentCourseTypes().clear();
        params.getNotCurrentCourseTypes().addAll(
                utilService.load(CourseType.class, "id", SeqStringUtil.transformToLong(get(request,
                        "courseTypeIds"))));
        
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
        addCollection(request, Constants.ELECTPARAMS_LIST, paramsService.getElectParams(
                getStdTypeIdSeq(request), (TeachCalendar) teachCalendarService
                        .getTeachCalendar(getLong(request, "electParams.calendar.id"))));
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
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long[] paramsIds = SeqStringUtil.transformToLong(get(request, "paramsIds"));
        if (null == paramsIds || paramsIds.length == 0) {
            return forwardError(mapping, request, "error.electParams.id.needed");
        }
        utilService.remove(utilService.load(ElectParams.class, "id", paramsIds));
        String calendarId = get(request, "electParams.calendar.id");
        return redirect(request, "list", "info.delete.success", "&electParams.calendar.id="
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
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setAttribute(Constants.ELECTPARAMS, utilService.load(ElectParams.class, getLong(
                request, "paramsId")));
        return forward(request);
    }
    
    public void setParamsService(ElectParamsService paramsService) {
        this.paramsService = paramsService;
    }
    
    public SpecialityService getSpecialityService() {
        return specialityService;
    }
    
    public void setSpecialityService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }
}
