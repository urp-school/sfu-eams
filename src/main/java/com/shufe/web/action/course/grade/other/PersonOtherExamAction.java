//$Id: StdSignUpAction.java,v 1.1 2007-2-25 下午10:39:12 chaostone Exp $
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
 *chaostone      2007-2-25         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.grade.other;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.shufe.model.course.grade.other.OtherExamSignUp;
import com.shufe.model.course.grade.other.OtherExamSignUpSetting;
import com.shufe.model.course.grade.other.OtherGrade;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.grade.other.OtherExamSignUpService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 学生报名和查看报名记录响应类
 * 
 * @author chaostone
 * 
 */
public class PersonOtherExamAction extends DispatchBasicAction {
    
    TeachCalendarService teachCalendarService;
    
    OtherExamSignUpService otherExamSignUpService;
    
    /**
     * 其他考试报名和成绩主页面<br>
     * 显示报名记录和已有的成绩记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Student std = getStudentFromSession(request.getSession());
        utilService.initialize(std.getAdminClasses());
        EntityQuery query = new EntityQuery(OtherExamSignUp.class, "signUp");
        query.add(new Condition("signUp.std.id=" + std.getId()));
        request.setAttribute("signUps", utilService.search(query));
        query = new EntityQuery(OtherGrade.class, "grade");
        query.add(new Condition("grade.std.id=" + std.getId()));
        query.addOrder(new Order("grade.calendar.start"));
        request.setAttribute("grades", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 显示报名界面<br>
     * 对于所有开放的考试设置，加载已经报名的记录和未报名的项目
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward signUp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Student std = getStudentFromSession(request.getSession());
        utilService.initialize(std.getAdminClasses());
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(std.getType());
        request.setAttribute("calendar", calendar);
        request.setAttribute("std", std);
        List settings = otherExamSignUpService.getOpenedSettings();
        
        HashMap hasSignUpMap = new HashMap();
        for (Iterator iter = settings.iterator(); iter.hasNext();) {
            OtherExamSignUpSetting setting = (OtherExamSignUpSetting) iter.next();
            OtherExamSignUp signUp = otherExamSignUpService.getOtherExamSignUp(std, setting);
            if (null != signUp) {
                hasSignUpMap.put(signUp.getCategory().getId().toString(), Boolean.TRUE);
            } else {
                hasSignUpMap.put(setting.getExamCategory().getId().toString(), Boolean.FALSE);
            }
        }
        addCollection(request, "settings", settings);
        request.setAttribute("hasSignUpMap", hasSignUpMap);
        return forward(request);
    }
    
    /**
     * 提交报名记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward submitSignUp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Student std = getStudentFromSession(request.getSession());
        Long settingId = getLong(request, "settingId");
        OtherExamSignUpSetting setting = (OtherExamSignUpSetting) utilService.get(
                OtherExamSignUpSetting.class, settingId);
        EntityQuery query = new EntityQuery(OtherExamSignUp.class, "otherExamSignUp");
        query.add(new Condition("otherExamSignUp.std.id="+std.getId()));
        query.add(new Condition("otherExamSignUp.category.id="+setting.getExamCategory().getId()));
        query.add(new Condition("otherExamSignUp.calendar.id="+teachCalendarService
                .getTeachCalendar(getLong(request, "calendar.id")).getId()));
        List otherExamSignUpList = (List)utilService.search(query);
        if(otherExamSignUpList.size()!=0){
            request.setAttribute("calendar", teachCalendarService
                .getTeachCalendar(getLong(request, "calendar.id")));
            return forward(request, "error");
        }
//        EntityQuery queryGrade = new EntityQuery(OtherGrade.class,"otherGrade");
//        queryGrade.add(new Condition("otherGrade.std.id="+std.getId()));
//        queryGrade.add(new Condition("otherGrade.category.id="+setting.getExamCategory().getId()));
//        queryGrade.add(new Condition("otherGrade.isPass=1"));
//        List queryGradeList = (List)utilService.search(queryGrade);
//        if(queryGradeList.size()!=0){
//            request.setAttribute("calendar", teachCalendarService
//                .getTeachCalendar(getLong(request, "calendar.id")));
//            return forward(request, "error");
//        }
        String msg = otherExamSignUpService.signUp(std, teachCalendarService
                .getTeachCalendar(getLong(request, "calendar.id")), setting);
        if (StringUtils.isEmpty(msg)) {
            String idCard = RequestUtils.get(request, "idCard");
            String idCardPO = std.getBasicInfo().getIdCard();
            if ((idCardPO == null)
                    || ((idCardPO != null) && (!idCardPO.equalsIgnoreCase(idCard)) && StringUtils
                            .isNotEmpty(idCard))) {
                std.getBasicInfo().setIdCard(idCard);
                utilService.saveOrUpdate(std);
            }
            return redirect(request, "index", "info.action.success");
        } else {
            return redirect(request, "signUp", msg);
        }
    }
    
    /**
     * 取消报名记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cancelSignUp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Student std = getStudentFromSession(request.getSession());
        Long settingId = getLong(request, "settingId");
        OtherExamSignUpSetting setting = (OtherExamSignUpSetting) utilService.get(
                OtherExamSignUpSetting.class, settingId);
        String msg = otherExamSignUpService.cancelSignUp(std, setting);
        if (StringUtils.isEmpty(msg)) {
            return redirect(request, "index", "info.action.success");
        } else {
            return redirect(request, "signUp", msg);
        }
    }
    
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
    
    public void setOtherExamSignUpService(OtherExamSignUpService otherExamSignUpService) {
        this.otherExamSignUpService = otherExamSignUpService;
    }
    
}
