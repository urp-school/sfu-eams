//$Id: StudentSignUpAction.java,v 1.1 2007-5-4 下午04:18:45 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-5-4         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.speciality2nd;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.collection.transformers.PropertyTransformer;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.Constants;
import com.shufe.model.std.Student;
import com.shufe.model.std.speciality2nd.MatriculateParams;
import com.shufe.model.std.speciality2nd.SignUpGPASetting;
import com.shufe.model.std.speciality2nd.SignUpSetting;
import com.shufe.model.std.speciality2nd.SignUpSpecialitySetting;
import com.shufe.model.std.speciality2nd.SignUpStudent;
import com.shufe.model.std.speciality2nd.SignUpStudentRecord;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.grade.gp.GradePointService;
import com.shufe.service.std.speciality2nd.Speciality2ndSignUpService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 管理人员管理，报名记录维护／录取相应类
 * 
 * @author chaostone
 * 
 */
public class SignUpStudentAction extends CalendarRestrictionSupportAction {
    
    private Speciality2ndSignUpService speciality2ndSignUpService;
    
    private GradePointService gradePointService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        TeachCalendar calendar = (TeachCalendar) request.getAttribute(Constants.CALENDAR);
        addCollection(request, "settings", speciality2ndSignUpService.getSignUpSettings(calendar));
        return forward(request);
    }
    
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
        EntityQuery query = buildQuery(request);
        addCollection(request, "signUpStds", utilService.search(query));
        SignUpSetting setting = (SignUpSetting) utilService.get(SignUpSetting.class, getLong(
                request, "signUpStd.setting.id"));
        request.setAttribute("signUpSetting", setting);
        return forward(request);
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = buildQuery(request);
        query.setLimit(null);
        return utilService.search(query);
    }
    
    private EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(SignUpStudent.class, "signUpStd");
        populateConditions(request, query, "signUpStd.std.type.id");
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        Long stdTypeId = getLong(request, "signUpStd.std.type.id");
        DataRealmUtils.addDataRealms(query, new String[] { "signUpStd.std.type.id",
                "signUpStd.std.department.id" }, getDataRealmsWith(stdTypeId, request));
        Long specialitySettingId = getLong(request, "specialitySetting.id");
        if (null != specialitySettingId) {
            query
                    .add(new Condition(
                            "exists(select r.id from SignUpStudentRecord r where r.signUpStd.id=signUpStd.id and r.specialitySetting.id="
                                    + specialitySettingId + ")"));
        }
        Boolean isMatriculated = getBoolean(request, "isMatriculated");
        if (null != isMatriculated) {
            query.add(new Condition("signUpStd.matriculated is"
                    + (Boolean.TRUE.equals(isMatriculated) ? " not " : "") + " null"));
        }
        query.setLimit(getPageLimit(request));
        return query;
    }
    
    /**
     * 专业设置列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward specialitySettingList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long settingId = getLong(request, "signUpStd.setting.id");
        SignUpSetting setting = (SignUpSetting) utilService.get(SignUpSetting.class, settingId);
        List settings = speciality2ndSignUpService.statSignUpAndMatriculate(setting);
        Collections.sort(settings, new PropertyComparator(request.getParameter("orderBy")));
        addCollection(request, "settings", settings);
        request.setAttribute("signUpSetting", setting);
        return forward(request);
    }
    
    /**
     * 录取名单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward matriculatedList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(SignUpStudent.class, "signUpStd");
        query.add(new Condition("signUpStd.matriculated.id in(:id)", SeqStringUtil
                .transformToLong(request.getParameter("specialitySettingIds"))));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        query.setLimit(getPageLimit(request));
        addCollection(request, "signUpStds", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 自动录取提示
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward matriculateSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    /**
     * 自动分班
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward assignClassSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long settingId = getLong(request, "signUpStd.setting.id");
        SignUpSetting setting = (SignUpSetting) utilService.get(SignUpSetting.class, settingId);
        List settings = speciality2ndSignUpService.statMatriculate(setting);
        Collections.sort(settings, new PropertyComparator(request.getParameter("orderBy")));
        
        Object[] adminClasses = new Object[settings.size()];
        int i = 0;
        for (Iterator iter = settings.iterator(); iter.hasNext();) {
            Object[] settingData = (Object[]) iter.next();
            adminClasses[i++] = speciality2ndSignUpService
                    .getCorrespondingAdminClasses((SignUpSpecialitySetting) settingData[0]);
        }
        addCollection(request, "settings", settings);
        request.setAttribute("adminClasses", adminClasses);
        request.setAttribute("signUpSetting", setting);
        return forward(request);
    }
    
    /**
     * 批量自动分配班级
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward autoAssignClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long settingId = getLong(request, "signUpStd.setting.id");
        String specialitySettingIds = request.getParameter("specialitySettingIds");
        List specialitySettings = utilService.load(SignUpSpecialitySetting.class, "id",
                SeqStringUtil.transformToLong(specialitySettingIds));
        speciality2ndSignUpService.autoAssignClass(specialitySettings);
        return redirect(request, "assignClassSetting", "info.action.success",
                "&signUpStd.setting.id=" + settingId);
    }
    
    /**
     * 批量自动录取学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward autoMatriculate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long settingId = getLong(request, "signUpStd.setting.id");
        SignUpSetting setting = (SignUpSetting) utilService.get(SignUpSetting.class, settingId);
        MatriculateParams params = (MatriculateParams) populate(request, MatriculateParams.class);
        speciality2ndSignUpService.autoMatriculate(setting, params);
        return redirect(request, "specialitySettingList", "info.action.success",
                "&signUpStd.setting.id=" + settingId);
    }
    
    /**
     * 批量取消某专业设置的下的录取名单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchCancelMatriculate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String signUpStdIds = request.getParameter("signUpStdIds");
        List signUpStds = utilService.load(SignUpStudent.class, "id", SeqStringUtil
                .transformToLong(signUpStdIds));
        for (Iterator iter = signUpStds.iterator(); iter.hasNext();) {
            SignUpStudent signUpStd = (SignUpStudent) iter.next();
            signUpStd.cancelMatriculate();
        }
        Collection stds = CollectionUtils.collect(signUpStds, new PropertyTransformer("std"));
        utilService.saveOrUpdate(signUpStds);
        utilService.saveOrUpdate(stds);
        return redirect(request, "matriculatedList", "info.action.success");
    }
    
    /**
     * 学生GPA重算
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reCalcGPA(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String signUpStdIds = request.getParameter("signUpStdIds");
        List signUpStds = utilService.load(SignUpStudent.class, "id", SeqStringUtil
                .transformToLong(signUpStdIds));
        for (Iterator iter = signUpStds.iterator(); iter.hasNext();) {
            SignUpStudent signUpStd = (SignUpStudent) iter.next();
            signUpStd.setGPA(gradePointService.statStdGPA(signUpStd.getStd(), null, new MajorType(
                    MajorType.FIRST), Boolean.TRUE, Boolean.TRUE));
        }
        utilService.saveOrUpdate(signUpStds);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 取消录取单个学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cancelMatriculate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long signUpStdId = getLong(request, "signUpStdId");
        SignUpStudent signUpStd = (SignUpStudent) utilService.get(SignUpStudent.class, signUpStdId);
        speciality2ndSignUpService.cancelMatriculate(signUpStd);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 录取单个学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward matriculate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long signUpStdId = getLong(request, "signUpStdId");
        SignUpStudent signUpStd = (SignUpStudent) utilService.get(SignUpStudent.class, signUpStdId);
        Long specialitySettingId = getLong(request, "specialitySettingId");
        SignUpSpecialitySetting specialitySetting = (SignUpSpecialitySetting) utilService.get(
                SignUpSpecialitySetting.class, specialitySettingId);
        signUpStd.matriculate(specialitySetting);
        
        utilService.saveOrUpdate(signUpStd);
        utilService.saveOrUpdate(signUpStd.getStd());
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 显示为学生手动报名界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addSignUp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long settingId = getLong(request, "signUpStd.setting.id");
        request.setAttribute("signUpStd", new SignUpStudent());
        SignUpSetting setting = (SignUpSetting) utilService.get(SignUpSetting.class, settingId);
        request.setAttribute("setting", setting);
        addCollection(request, "GPASettingList", utilService.loadAll(SignUpGPASetting.class));
        return forward(request);
    }
    
    /**
     * 保存为学生手动报名信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveSignUp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        SignUpStudent signUpStd = (SignUpStudent) populateEntity(request, SignUpStudent.class,
                "signUpStd");
        if (!signUpStd.isPO()) {
            EntityQuery query = new EntityQuery(SignUpStudent.class, "signUpStd");
            query.add(new Condition("signUpStd.calendar.id=:calendarId", signUpStd.getCalendar()
                    .getId()));
            query.add(new Condition("signUpStd.std.id=:stdId", signUpStd.getStd().getId()));
            List signUpStds = (List) utilService.search(query);
            if (!signUpStds.isEmpty()) {
                return redirect(request, "search", "speciality2nd.signUp.dupilcate");
            }
        }
        
        signUpStd.getRecords().clear();
        signUpStd.setSignUpAt(new Timestamp(System.currentTimeMillis()));
        Integer count = getInteger(request, "count");
        for (int i = 1; i <= count.intValue(); i++) {
            if (StringUtils.isEmpty(request.getParameter("specialitySettingId" + i)))
                break;
            Long specialitySettingId = getLong(request, "specialitySettingId" + i);
            signUpStd.addSignUpRecord(new SignUpStudentRecord(new SignUpSpecialitySetting(
                    specialitySettingId), new Integer(i)));
        }
        Student std=(Student) utilService.load(Student.class, signUpStd.getStd().getId());
        Float gpa = gradePointService.statStdGPA(std, null, new MajorType(
                MajorType.FIRST), Boolean.TRUE, Boolean.TRUE);
        signUpStd.setGPA(gpa);
        signUpStd.setMatriculateGPA(gpa);
        utilService.saveOrUpdate(signUpStd);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 保存为学生手动报名信息
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
        Long[] signUpIds = SeqStringUtil.transformToLong(request.getParameter("signUpStdIds"));
        List signUps = utilService.load(SignUpStudent.class, "id", signUpIds);
        utilService.remove(signUps);
        return redirect(request, "search", "info.delete.success");
        
    }
    
    /**
     * 学生报名信息
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
        Long signUpStdId = getLong(request, "signUpStdId");
        SignUpStudent signUpStd = (SignUpStudent) utilService.get(SignUpStudent.class, signUpStdId);
        request.setAttribute("signUpStd", signUpStd);
        
        // 查找其他可供调剂或者录取的专业设置
        Long settingId = getLong(request, "signUpStd.setting.id");
        SignUpSetting setting = (SignUpSetting) utilService.get(SignUpSetting.class, settingId);
        List specialitySettings_temp = speciality2ndSignUpService
                .getUnsaturatedSpecialitySettings(setting);
        Set specialitySettings = new HashSet();
        if(Boolean.TRUE.equals(setting.getIsRestrictSubjectCategory())){
            for (Iterator iter = specialitySettings_temp.iterator(); iter.hasNext();) {
                SignUpSpecialitySetting element = (SignUpSpecialitySetting) iter.next();
                if(!signUpStd.getStd().getFirstMajor().getSubjectCategory().getId().equals(element.getSpeciality().getSubjectCategory().getId())){
                    specialitySettings.add(element);
                }
            }
            addCollection(request, "specialitySettings", specialitySettings);  
        }else{
            addCollection(request, "specialitySettings", specialitySettings_temp);     
        }
        
        return forward(request);
    }
    
    public void setSpeciality2ndSignUpService(Speciality2ndSignUpService speciality2ndSignUpService) {
        this.speciality2ndSignUpService = speciality2ndSignUpService;
    }
    
    public void setGradePointService(GradePointService gradePointService) {
        this.gradePointService = gradePointService;
    }
    
}
