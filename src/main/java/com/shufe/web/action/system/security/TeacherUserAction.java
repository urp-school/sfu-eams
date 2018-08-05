//$Id: TeacherUserAction.java,v 1.12 2006/12/30 01:29:02 duanth Exp $
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
 * chaostone            2005-10-07          Created
 * zq                   2007-09-18          修改或替换了下面所有的info()方法
 *  
 ********************************************************************************/

package com.shufe.web.action.system.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.eams.system.basecode.industry.TeacherWorkState;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.eams.system.security.service.EamsUserService;
import com.ekingstar.security.Role;
import com.ekingstar.security.User;
import com.ekingstar.security.UserCategory;
import com.ekingstar.security.management.UserManager;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.system.baseinfo.DepartmentService;
import com.shufe.service.system.baseinfo.TeacherService;
import com.shufe.web.action.common.RestrictionSupportAction;
import com.shufe.web.helper.BaseInfoSearchHelper;

public class TeacherUserAction extends RestrictionSupportAction {
    
    private EamsUserService userService;
    
    private TeacherService teacherService;
    
    private DepartmentService departmentService;
    
    private BaseInfoSearchHelper baseInfoSearchHelper;
    
    /**
     * 进入教师用户管理界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        String departIdSeq = getDepartmentIdSeq(request);
        if (StringUtils.isEmpty(departIdSeq)) {
            return forwardError(mapping, request, "error.depart.DataRealm.insufficient");
        }
        addCollection(request, "departments", departmentService.getDepartments(departIdSeq));
        initBaseCodes(request, "teacherWorkStateList", TeacherWorkState.class);
        return forward(request);
    }
    
    /**
     * 分页显示登录用户管理的教师用户列表.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        Collection teachers = baseInfoSearchHelper.searchTeacher(request);
        Map teacherUserMap = new HashMap();
        for (Iterator iter = teachers.iterator(); iter.hasNext();) {
            Teacher teacher = (Teacher) iter.next();
            User user = userService.get(teacher.getCode());
            if (null != user) {
                teacherUserMap.put(teacher.getId().toString(), user);
            }
        }
        request.setAttribute("teacherUserMap", teacherUserMap);
        addCollection(request, "teachers", teachers);
        return forward(request);
    }
    
    /**
     * 禁用或激活一个或多个用户
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward activate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teacherNoSeq = request.getParameter("teacherNos");
        String[] teacherNos = StringUtils.split(teacherNoSeq, ",");
        Boolean activate = getBoolean(request, "isActivate");
        boolean isActivate = (activate == null) ? false : activate.booleanValue();
        try {
            List users = utilService.load(User.class, "name", teacherNos);
            for (Iterator iter = users.iterator(); iter.hasNext();) {
                User user = (User) iter.next();
                user.setState(isActivate ? new Integer(1) : new Integer(0));
            }
            utilService.saveOrUpdate(users);
        } catch (Exception e) {
            logHelper.info(request, "Failure in alert status teacherUser ids:" + teacherNoSeq, e);
            return forward(mapping, request, "error.occurred", "error");
        }
        String msg = "info.activate.success";
        if (!isActivate)
            msg = "info.unactivate.success";
        return redirect(request, "search", msg);
    }
    
    /**
     * 升级为管理人员
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward promptToManager(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] names = StringUtils.split(request.getParameter("teacherNos"), ",");
        UserManager manager = (UserManager) getUser(request.getSession());
        Role admin = (Role) utilService.load(Role.class, Role.ADMIN_ID);
        for (int i = 0; i < names.length; i++) {
            User one = userService.get(names[i]);
            if (null == one) {
                logHelper.info(request, "Add teacher acount for:" + names[i]);
                Teacher teacher = teacherService.getTeacherByNO(names[i]);
                // FIXME
                one = userService.createTeacherUser((User) manager, teacher);
            }
            if (!one.isCategory(EamsRole.MANAGER_USER)) {
                one.getCategories().add(utilService.get(UserCategory.class, EamsRole.MANAGER_USER));
                one.setCreator(manager);
                manager.getMngUsers().add(one);
                // 让每一个超级管理员知道
                for (Iterator iter = admin.getUsers().iterator(); iter.hasNext();) {
                    UserManager oneAdmin = (UserManager) iter.next();
                    oneAdmin.getMngUsers().add(one);
                    userService.saveOrUpdate((User) oneAdmin);
                }
            }
            userService.saveOrUpdate(one);
        }
        userService.saveOrUpdate((User) manager);
        return redirect(request, "search", "info.update.success");
    }
    
    /**
     * 编辑教师用户信息，新建的或已有的.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        String teacherNo = request.getParameter("teacherNo");
        User teacherUser = null;
        Teacher teacher = null;
        if (StringUtils.isNotEmpty(teacherNo)) {
            teacher = teacherService.getTeacherByNO(teacherNo);
            if (null == teacher)
                return forward(mapping, request, new String[] { "entity.teacher",
                        "error.model.notExist" }, "error");
            teacherUser = userService.get(teacherNo);
            if (null == teacherUser) {
                User curUser = getUser(request.getSession());
                teacherUser = userService.createTeacherUser(curUser, teacher);
            }
        }
        addEntity(request, teacherUser);
        return forward(request);
    }
    
    /**
     * 查看用户详细信息，不能修改.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        Long userId = getLong(request, "userId");
        User user = null;
        if (null != userId || userId.intValue() != 0)
            user = (User) utilService.get(User.class, userId);
        else {
            return forwardError(mapping, request, "error.model.notExist");
        }
        addEntity(request, user);
        return forward(request);
    }
    
    /**
     * 保存已有的教师账户修改信息
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
        Long userId = getLong(request, "user.id");
        
        User savedUser = userService.get(userId);
        if (null == savedUser)
            return forward(mapping, request, new String[] { "entity.student",
                    "error.model.notExist" }, "error");
        try {
            logHelper.info(request, "Update teacher acountId:" + userId);
            savedUser.setEmail(request.getParameter("user.email"));
            savedUser.setPassword(request.getParameter("user.password"));
            userService.saveOrUpdate(savedUser);
        } catch (Exception e) {
            logHelper.info(request, "Failure update teacher acountId:" + userId, e);
            return forward(mapping, request, "error.occurred", "error");
        }
        return redirect(request, "search", "info.save.success");
    }
    
    /**
     * 创建一批教师账户
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String teacherNoSeq = request.getParameter("teacherNos");
        if (StringUtils.isEmpty(teacherNoSeq))
            return forward(mapping, request, new String[] { "entity.teacher",
                    "error.model.notExist" }, "error");
        String teacherNos[] = teacherNoSeq.split(",");
        User curUser = getUser(request.getSession());
        try {
            logHelper.info(request, "Add teacher acount for:" + teacherNoSeq);
            List teachers = teacherService.getTeachersByNO(teacherNos);
            for (Iterator it = teachers.iterator(); it.hasNext();) {
                Teacher one = (Teacher) it.next();
                userService.createTeacherUser(curUser, one);
            }
        } catch (Exception e) {
            logHelper.info(request, "Failure Add teacher acount for:" + teacherNoSeq, e);
            return forward(mapping, request, "error.occurred", "error");
        }
        return redirect(request, "search", "info.add.success");
    }
    
    public void setuserService(EamsUserService userService) {
        this.userService = userService;
    }
    
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    
    public void setBaseInfoSearchHelper(BaseInfoSearchHelper baseInfoSearchHelper) {
        this.baseInfoSearchHelper = baseInfoSearchHelper;
    }
    
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    
}
