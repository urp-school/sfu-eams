//$Id: StudentUserAction.java,v 1.17 2006/12/12 11:00:10 duanth Exp $
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.eams.system.security.service.EamsUserService;
import com.ekingstar.security.Role;
import com.ekingstar.security.User;
import com.ekingstar.security.UserCategory;
import com.ekingstar.security.management.ManagedUser;
import com.ekingstar.security.management.UserManager;
import com.shufe.model.std.Student;
import com.shufe.service.std.StudentService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 学生账户管理响应类
 * 
 * @author chaostone 2005-10-12
 */
public class StudentUserAction extends RestrictionSupportAction {
    
    private EamsUserService userService;
    
    private StudentService studentService;
    
    /**
     * 分页显示登录用户管理的学生用户列表.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        EntityQuery entityQuery = new EntityQuery(Student.class, "student");
        QueryRequestSupport.populateConditions(request, entityQuery, "student.type.id");
        entityQuery.join("left", "student.firstMajor", "firstMajor");
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        Long stdTypeId = getLong(request, "student.type.id");
        DataRealmUtils.addDataRealms(entityQuery, new String[] { "student.type.id",
                "student.department.id" }, restrictionHelper.getDataRealmsWith(stdTypeId, request));
        Collection stds = utilService.search(entityQuery);
        Map stdUserMap = new HashMap();
        for (Iterator iter = stds.iterator(); iter.hasNext();) {
            Student std = (Student) iter.next();
            User user = userService.get(std.getCode());
            if (null != user) {
                stdUserMap.put(std.getId().toString(), user);
            }
        }
        addCollection(request, "stds", stds);
        request.setAttribute("stdUserMap", stdUserMap);
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
        String stdCodes = request.getParameter("stdCodes");
        Boolean activate = getBoolean(request, "isActivate");
        boolean isActivate = (activate == null) ? false : activate.booleanValue();
        try {
            List users = utilService.load(User.class, "name", StringUtils.split(stdCodes, ","));
            for (Iterator iter = users.iterator(); iter.hasNext();) {
                User user = (User) iter.next();
                user.setState(isActivate ? new Integer(1) : new Integer(0));
            }
            utilService.saveOrUpdate(users);
        } catch (Exception e) {
            logHelper.info(request, "Failure in alert status stdUser nos:" + stdCodes, e);
            return forward(mapping, request, "error.occurred", "error");
        }
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("entity.acount"));
        String msg = "info.activate.success";
        if (!isActivate)
            msg = "info.unactivate.success";
        return redirect(request, "search", msg);
    }
    
    /**
     * 进入学生用户管理界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        Results.addList("departmentList", getColleges(request));
        Results.addList("stdTypeList", getStdTypes(request));
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
     * 编辑角色信息，新建的或已有的.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        String stdCode = request.getParameter("stdCode");
        User stdUser = null;
        Student std = null;
        if (StringUtils.isNotEmpty(stdCode)) {
            std = studentService.getStudent(stdCode);
            if (null == std)
                return forward(mapping, request, new String[] { "entity.student",
                        "error.model.notExsits" }, "error");
            stdUser = userService.get(stdCode);
            if (null == stdUser) {
                User curUser = getUser(request.getSession());
                stdUser = userService.createStdUser(curUser, std);
            }
        }
        addEntity(request, stdUser);
        return forward(request);
    }
    
    /**
     * 保存已有的学生账户修改信息
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
                    "error.model.notExsits" }, "error");
        savedUser.setEmail(request.getParameter("user.email"));
        savedUser.setPassword(request.getParameter("user.password"));
        try {
            logHelper.info(request, "Update stdUser acount:" + savedUser.getName());
            userService.saveOrUpdate(savedUser);
        } catch (Exception e) {
            logHelper.info(request, "Failure in Update stdUser :" + savedUser.getName());
            redirect(request, "search", "info.save.failure");
        }
        return redirect(request, "search", "info.save.success");
    }
    
    /**
     * 创建一批学生账户
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
        String stdCodeSeq = request.getParameter("stdCodes");
        if (StringUtils.isEmpty(stdCodeSeq))
            return forward(mapping, request, new String[] { "entity.student",
                    "error.model.id.needed" }, "error");
        String stdCodes[] = StringUtils.split(stdCodeSeq, ",");
        User curUser = getUser(request.getSession());
        try {
            logHelper.info(request, "Add count for std Nos:" + stdCodeSeq);
            List stds = utilService.load(Student.class, "code", stdCodes);
            for (Iterator it = stds.iterator(); it.hasNext();) {
                Student one = (Student) it.next();
                userService.createStdUser(curUser, one);
            }
        } catch (Exception e) {
            logHelper.info(request, "Failure Add count for std Nos:" + stdCodes, e);
            return forward(mapping, request, "error.occurred", "error");
        }
        return redirect(request, "search", "info.add.success");
    }
    
    /**
     * 学生升级为管理人员
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
        String[] names = StringUtils.split(request.getParameter("stdCodes"), ",");
        UserManager manager = (UserManager) getUser(request.getSession());
        Role admin = (Role) utilService.load(Role.class, Role.ADMIN_ID);
        User curUser = getUser(request.getSession());
        for (int i = 0; i < names.length; i++) {
            User one = userService.get(names[i]);
            if (null == one) {
                logHelper.info(request, "Add teacher acount for:" + names[i]);
                Student std = studentService.getStd(names[i]);
                one = userService.createStdUser(curUser, std);
            }
            if (!one.isCategory(EamsRole.MANAGER_USER)) {
                one.getCategories().add(utilService.get(UserCategory.class, EamsRole.MANAGER_USER));
                ((ManagedUser) one).setCreator(manager);
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
    
    public void setuserService(EamsUserService userService) {
        this.userService = userService;
    }
    
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
}
