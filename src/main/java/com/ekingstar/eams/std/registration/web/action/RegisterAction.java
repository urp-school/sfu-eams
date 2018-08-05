//$Id: StudentRegisterManager.java,v 1.6 2006/12/01 06:21:00 yd Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-11-21         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.registration.web.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.fee.service.TuitionFeeService;
import com.ekingstar.eams.std.info.Student;
import com.ekingstar.eams.std.registration.Register;
import com.ekingstar.eams.std.registration.service.RegisterService;
import com.ekingstar.eams.system.basecode.industry.RegisterState;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.TeachCalendar;

/**
 * 注册信息管理响应类
 * 
 * @author chaostone,dell,yang dong
 */
public class RegisterAction extends RegisterSearchAction {
    
    RegisterService registerService;
    
    TuitionFeeService tuitionFeeService;
    
    /**
     * 批量注册
     */
    public ActionForward register(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 判断是否拥有注册权限
        int state = registerService.getRoleState(getUser(request.getSession()));
        if (-1 == state) {
            // 没有注册权限
            return redirect(request, "search", "field.evaluate.errorsOfSelect");
        }
        if (0 == state) {
            // 不在维护日期内
            return redirect(request, "search", "std.regist.wrongTime");
        }
        
        Long registerStateId = getLong(request, "selectedStateId");
        RegisterState registerState = (RegisterState) utilService.get(RegisterState.class,
                registerStateId);
        String stdIds = request.getParameter("stdIds");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id"));
        List stds = utilService.load(com.shufe.model.std.Student.class, "id", SeqStringUtil
                .transformToLong(stdIds));
        
        registerService.register(calendar, stds, "", registerState);
        return redirect(request, "unregisterList", "info.action.success");
    }
    
    /**
     * 批量更新未完成的注册
     */
    public ActionForward updateRegister(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 判断是否拥有注册权限
        int state = registerService.getRoleState(getUser(request.getSession()));
        if (-1 == state) {
            // 没有注册权限
            return redirect(request, "search", "field.evaluate.errorsOfSelect");
        }
        if (0 == state) {
            // 不在维护日期内
            return redirect(request, "search", "std.regist.wrongTime");
        }
        
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "register.calendar.id"));
        Long registerStateId = getLong(request, "selectedStateId");
        RegisterState registerState = (RegisterState) utilService.get(RegisterState.class,
                registerStateId);
        String registerIds = request.getParameter("registerIds");
        List registers = utilService.load(com.ekingstar.eams.std.registration.model.Register.class,
                "id", SeqStringUtil.transformToLong(registerIds));
        List updateRegisters = new ArrayList();
        Boolean isTuitionFeeCompleted = Boolean.TRUE;
        for (int i = 0; i < registers.size(); i++) {
            com.ekingstar.eams.std.registration.model.Register register = (com.ekingstar.eams.std.registration.model.Register) registers
                    .get(i);
            isTuitionFeeCompleted = tuitionFeeService.isCompleted(register.getStd().getId(),
                    calendar.getId());
            System.out.println(registerState.getCode());
            if (registerState.getCode().equals("98")) {
                System.out.println(isTuitionFeeCompleted);
                if (isTuitionFeeCompleted.booleanValue()) {
                    register.setIsPassed(Boolean.TRUE);
                } else {
                    registerState = (RegisterState) utilService.get(RegisterState.class, new Long(
                            2));
                }
            } else {
                register.setIsPassed(Boolean.FALSE);
            }
          
            register.setState(registerState);
            register.setCalendar(calendar);
            register.setRegisterAt(new Timestamp(System.currentTimeMillis()));
            updateRegisters.add(register);
        }
        utilService.saveOrUpdate(updateRegisters);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 单个注册
     */
    public ActionForward singleRegister(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 判断是否拥有注册权限
        int state = registerService.getRoleState(getUser(request.getSession()));
        if (-1 == state) {
            // 没有注册权限
            return redirect(request, "search", "field.evaluate.errorsOfSelect");
        }
        if (0 == state) {
            // 不在维护日期内
            return redirect(request, "search", "std.regist.wrongTime");
        }
        
        Long registerStateId = getLong(request, "register.state.code");
        RegisterState registerState = (RegisterState) utilService.get(RegisterState.class,
                registerStateId);
        String stdCode = request.getParameter("stdCode");
        TeachCalendar calendar = teachCalendarService
                .getCurTeachCalendar(StudentType.UNDERGRADUATESTUDENTTYPID);
        EntityQuery query = new EntityQuery(Student.class, "std");
        query.add(new Condition("std.code=:stdCode", stdCode));
        List stds = (List) utilService.search(query);
        query = new EntityQuery(Register.class, "register");
        registerService.register(calendar, stds, "", registerState);
        return redirect(request, "add", "info.action.success");
    }
    
    // 未注册学生
    public ActionForward unregisterList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = buildQuery1(request);
        addCollection(request, "students", utilService.search(query));
        // 加入注册状态
        addCollection(request, "registerStates", baseCodeService.getCodes(RegisterState.class));
        return forward(request, "stdList");
    }
    
    /**
     * @param request
     * @return
     */
    protected EntityQuery buildQuery1(HttpServletRequest request) {
        EntityQuery query = stdSearchHelper.buildStdQuery(request, "calendar.studentType.id");
        // 添加学生学籍有效,并且学籍有效的查询条件
        query.add(new Condition("std.inSchool = true and std.active = true"));
        query
                .add(new Condition(
                        "not exists( from Register register where register.std.id=std.id and register.calendar.id=:calendarId)",
                        getLong(request, "register.calendar.id")));
        return query;
    }
    
    /**
     * 修改信息主页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    // public ActionForward register(ActionMapping mapping, ActionForm form,
    // HttpServletRequest request, HttpServletResponse response)
    // throws Exception {
    // String stdIds = request.getParameter("stdIds");
    // TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(
    // request, "calendar.id"));
    // List stds = utilService.load(Student.class, "id", SeqStringUtil
    // .transformToLong(stdIds));
    // registerService.register(calendar, stds, "");
    // return redirect(request, "search", "info.action.success");
    // }
    /**
     * 修改信息主页面
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
        String registerIds = request.getParameter("registerIds");
        List registers = utilService.load(Register.class, "id", SeqStringUtil
                .transformToLong(registerIds));
        utilService.remove(registers);
        return redirect(request, "search", "info.delete.success");
    }
    
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "registerStates", baseCodeService.getCodes(RegisterState.class));
        return forward(request);
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = null;
        if (null != request.getParameter("type")) {
            query = buildQuery1(request);
        } else {
            query = buildQuery2(request);
        }
        query.setLimit(null); // 不分页，取出所有数据
        return utilService.search(query);
    }
    
    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }
    
    public void setTuitionFeeService(TuitionFeeService tuitionFeeService) {
        this.tuitionFeeService = tuitionFeeService;
    }
    
}
