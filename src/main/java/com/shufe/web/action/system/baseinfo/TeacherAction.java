//$Id: TeacherAction.java,v 1.11 2006/12/30 01:29:01 duanth Exp $
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
 * chaostone            2005-10-30          Created
 * zq                   2007-09-18          修改或替换了下面所有的info()方法
 * zq                   2007-10-17          在prepareEditData()增加了TeacherWorkState.class
 *                                          列表初始化语句；
 *                                          更改了home()中的列表初始化语句
 ********************************************************************************/

package com.shufe.web.action.system.baseinfo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.EngagementType;
import com.ekingstar.eams.system.basecode.industry.School;
import com.ekingstar.eams.system.basecode.industry.TeacherType;
import com.ekingstar.eams.system.basecode.industry.TeacherWorkState;
import com.ekingstar.eams.system.basecode.industry.TutorType;
import com.ekingstar.eams.system.basecode.state.Country;
import com.ekingstar.eams.system.basecode.state.Degree;
import com.ekingstar.eams.system.basecode.state.EduDegree;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.basecode.state.Nation;
import com.ekingstar.eams.system.basecode.state.TeacherTitle;
import com.ekingstar.eams.system.basecode.state.TeacherTitleLevel;
import com.ekingstar.eams.system.security.service.EamsUserService;
import com.ekingstar.security.Authentication;
import com.ekingstar.security.User;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.system.baseinfo.importer.TeacherImportListener;
import com.shufe.util.DataAuthorityUtil;
import com.shufe.web.action.system.baseinfo.search.TeacherSearchAction;

/**
 * 教师基本信息管理类
 * 
 * @author chaostone
 */
public class TeacherAction extends TeacherSearchAction {
	
	private EamsUserService userService;
    
    public void setUserService(EamsUserService userService) {
		this.userService = userService;
	}

	/**
     * 修改和新建教职工时调用的动作.<br>
     * 从request接受一个教职工的teacherId，从库中找出其信息.
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
        Teacher teacher = (Teacher) getEntity(request, Teacher.class, "teacher");
        if (ValidEntityPredicate.INSTANCE.evaluate(teacher)) {
            if (!DataAuthorityUtil.isInDataRealm("Teacher", teacher, null,
                    getDepartmentIdSeq(request)))
                return forwardError(mapping, request, "error.depart.dataRealm.insufficient");
        } else {
            teacher = (Teacher) populate(request, Teacher.class);
            // 默认为中国教师
            if (!ValidEntityPredicate.getInstance().evaluate(teacher.getCountry())) {
                teacher.setCountry(new Country(Country.CHINA));
            }
            // 默认为汉族
            if (!ValidEntityPredicate.getInstance().evaluate(teacher.getNation())) {
                teacher.setNation(new Nation(Nation.HAN));
            }
            // 默认教师任课
            teacher.setIsTeaching(Boolean.TRUE);
        }
        prepareEditData(request);
        request.setAttribute("teacher", teacher);
        return forward(request);
    }
    
    /**
     * 保存教职工信息.<br>
     * 新建的教职工或修改的教职工. <br>
     * 接受主键冲突异常，跳转到异常页面.
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
        Long teacherId = getLong(request, "teacher.id");
        
        // 检查是否重复
        if (utilService.duplicate(Teacher.class, teacherId, "code", request
                .getParameter("teacher.code"))) {
            return forward(request, new Action("", "edit"), "error.code.existed");
        }
        Teacher teacher = null;
        Map params = getParams(request, "teacher");
        String code = request.getParameter("teacher.code");
        // 首先检查教师是否存在
        if (null == teacherId && StringUtils.isNotEmpty(code)) {
            Teacher existedTeacher = teacherService.getTeacherByNO(code);
            if (null != existedTeacher) {
                return forward(mapping, request, new String[] { "entity.teacher",
                        "error.model.existed" }, "error");
            }
        }
        try {
            if (null == teacherId) {
                teacher = new Teacher();
            } else {
                teacher = teacherService.getTeacherById(teacherId);
            }
            populate(params, teacher);
            Department depart = null;
            if (!teacher.isPO()) {
                logHelper.info(request, "Create a teacher with name:" + teacher.getName());
                depart = (Department) utilService.load(Department.class, teacher.getDepartment()
                        .getId());
                depart.getTeachers().add(teacher);
            } else {
                logHelper.info(request, "Update a teacher with name:" + teacher.getName());
            }
            // 自动生成工号
            if (!codeGenerator.isValidCode(teacher.getCode())) {
                String teacherNO = codeGenerator.gen(teacher, null);
                if (codeGenerator.isValidCode(teacherNO)) {
                    teacher.setCode(teacherNO);
                } else {
                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                            "error.codeGen.failure"));
                    addErrors(request, messages);
                    return forward(request, new Action(this.getClass(), "edit"));
                }
            }
            teacherService.saveOrUpdate(teacher);
            if (null != depart) {
                utilService.saveOrUpdate(depart);
            }
            User user = getUser(request.getSession());
            userService.createTeacherUser(user, teacher);
        } catch (PojoExistException e) {
            logHelper.info(request, "Failure save or upate a teacher with name:"
                    + teacher.getName(), e);
            return forward(mapping, request,
                    new String[] { "entity.teacher", "error.model.existed" }, "error");
        } catch (Exception e) {
            logHelper.info(request, "Failure save or upate a teacher with name:"
                    + teacher.getName(), e);
            return forward(mapping, request, "error.occurred", "error");
        }
        if (null != request.getParameter("addAnother")) {
            return redirect(request, "edit", "info.save.success");
        } else {
            return redirect(request, "search", "info.save.success");
        }
    }
    
    /**
     * 导入班级信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TransferResult tr = new TransferResult();
        Transfer transfer = ImporterServletSupport.buildEntityImporter(request, Teacher.class, tr);
        if (null == transfer) {
            return forward(request, "/pages/components/importData/error");
        }
        transfer.addListener(new ImporterForeignerListener(utilService)).addListener(
                new TeacherImportListener(teacherService));
        transfer.transfer(tr);
        if (tr.hasErrors()) {
            return forward(request, "/pages/components/importData/error");
        } else {
            return redirect(request, "search", "info.import.success");
        }
    }
    
    private void prepareEditData(HttpServletRequest request) {
        initBaseCodes(request, "departmentList", Department.class);
        initBaseCodes(request, "genderList", Gender.class);
        initBaseCodes(request, "countryList", Country.class);
        initBaseCodes(request, "nationList", Nation.class);
        initBaseCodes(request, "titleList", TeacherTitle.class);
        initBaseCodes(request, "titleLevelList", TeacherTitleLevel.class);
        initBaseCodes(request, "teacherTypeList", TeacherType.class);
        initBaseCodes(request, "tutorTypeList", TutorType.class);
        initBaseCodes(request, "eduDegreeList", EduDegree.class);
        initBaseCodes(request, "degreeList", Degree.class);
        initBaseCodes(request, "schoolList", School.class);
        initBaseCodes(request, "engagementTypeList", EngagementType.class);
        initBaseCodes(request, "teacherWorkStateList", TeacherWorkState.class);
    }
    
    protected com.ekingstar.security.User getUser(HttpSession session) {
        return (User) utilService.get(User.class, (Long) session
                .getAttribute(Authentication.USERID));
    }
}
