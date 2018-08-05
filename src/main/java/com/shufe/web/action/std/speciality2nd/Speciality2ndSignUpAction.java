//$Id: PersonSpeciality2ndSignUpAction.java,v 1.1 2007-5-4 下午04:17:18 chaostone Exp $
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

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.std.Student;
import com.shufe.model.std.speciality2nd.SignUpGPASetting;
import com.shufe.model.std.speciality2nd.SignUpSetting;
import com.shufe.model.std.speciality2nd.SignUpSpecialitySetting;
import com.shufe.model.std.speciality2nd.SignUpStudent;
import com.shufe.model.std.speciality2nd.SignUpStudentRecord;
import com.shufe.service.course.grade.gp.GradePointService;
import com.shufe.service.std.StudentService;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 学生在二专业界面，查询录取结果\报名
 * 
 * @author chaostone
 * 
 */
public class Speciality2ndSignUpAction extends DispatchBasicAction {
    
    private GradePointService gradePointService;
    
    private StudentService studentService;
    
    /**
     * 以往的报名和录取结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List signUpList = utilService.load(SignUpStudent.class, "std.code",
                new Object[] { getUser(request.getSession()).getName() });
        addCollection(request, "signUpStdList", signUpList);
        return forward(request);
    }
    
    /**
     * 显示能够报名的列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward signUpSettingList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        EntityQuery query = new EntityQuery(SignUpSetting.class, "setting");
        query.add(new Condition("setting.endOn>=:time", new Date(System.currentTimeMillis())));
        List signUpSettingList = (List) utilService.search(query);
        String code = getUser(request.getSession()).getName();
        Student std = studentService.getStudent(code);
        Float GPA = gradePointService.statStdGPA(std, null, new MajorType(MajorType.FIRST),
                Boolean.TRUE, Boolean.TRUE);
        addCollection(request, "signUpSettings", signUpSettingList);
        request.setAttribute("std", getStudentFromSession(request.getSession()));
        request.setAttribute("GPA", GPA);
        return forward(request);
    }
    
    /**
     * 显示双专业学生报名
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward signUp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        String code = getUser(request.getSession()).getName();
        Student std = studentService.getStudent(code);
        Long settingId = getLong(request, "signUpSettingId");
        EntityQuery query = new EntityQuery(SignUpStudent.class, "signUpStudent");
        query.add(new Condition("signUpStudent.setting.id=" + settingId));
        query.add(new Condition("signUpStudent.std.code=:stdCode", code));
        List signUpStudents = (List) utilService.search(query);
        SignUpStudent signUpStd = null;
        if (!signUpStudents.isEmpty()) {
            signUpStd = (SignUpStudent) signUpStudents.get(0);
            for (Iterator iter = signUpStd.getRecords().iterator(); iter.hasNext();) {
                SignUpStudentRecord element = (SignUpStudentRecord) iter.next();
                request.setAttribute("signUpRank" + element.getRank().intValue(), element);
            }
        } else {
            signUpStd = new SignUpStudent();
        }
        request.setAttribute("signUpStd", signUpStd);
        SignUpSetting setting = (SignUpSetting) utilService.get(SignUpSetting.class, settingId);
        request.setAttribute("setting", setting);
        request.setAttribute("std", std);
        addCollection(request, "GPASettingList", utilService.loadAll(SignUpGPASetting.class));
        Set specialitySettings_temp = setting.getSpecialitySettings();
        Set specialitySettings = new HashSet();
        if(Boolean.TRUE.equals(setting.getIsRestrictSubjectCategory())){
            for (Iterator iter = specialitySettings_temp.iterator(); iter.hasNext();) {
                SignUpSpecialitySetting element = (SignUpSpecialitySetting) iter.next();
                if(!std.getFirstMajor().getSubjectCategory().getId().equals(element.getSpeciality().getSubjectCategory().getId())){
                    specialitySettings.add(element);
                }
            }
            addCollection(request, "specialitySettings", specialitySettings);  
        }else{
            addCollection(request, "specialitySettings", specialitySettings_temp);     
        }
        return forward(request);
    }
    
    /**
     * 提交报名申请
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward submitSignUp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        SignUpStudent signUpStd = (SignUpStudent) populateEntity(request, SignUpStudent.class,
                "signUpStd");
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
        utilService.saveOrUpdate(signUpStd);
        return redirect(request, "index", "info.action.success");
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward cancelSignUp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long signUpStdId = getLong(request, "signUpStdId");
        SignUpStudent signUpStd = (SignUpStudent) utilService.get(SignUpStudent.class, signUpStdId);
        if (signUpStd.getStd().getCode().equals(getUser(request.getSession()).getName())) {
            utilService.remove(signUpStd);
        }
        return redirect(request, "index", "info.action.success");
    }
    
    public void setGradePointService(GradePointService gradePointService) {
        this.gradePointService = gradePointService;
    }
    
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    
}
