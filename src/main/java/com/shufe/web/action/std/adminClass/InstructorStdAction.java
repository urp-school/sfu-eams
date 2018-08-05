//$Id: InstructorStdAction.java,v 1.1 2008-1-23 上午10:37:36 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2008-1-23         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.adminClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.Constants;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.web.action.common.DispatchBasicAction;
import com.shufe.web.action.course.grade.report.MultiStdGradeReportAction;
import com.shufe.web.action.system.baseinfo.AdminClassAction;

/**
 * 查看辅导员的班级与学生
 * 
 * @author zhouqi
 */
public class InstructorStdAction extends DispatchBasicAction {
    
	TeachCalendarService teachCalendarService;
	
    /**
     * 进入页面
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
        return forward(request);
    }
    
    /**
     * 辅导员所带的班级
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward searchAdminClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(AdminClass.class, "adminClass");
        query.add(new Condition("adminClass.instructor.code = (:code)", getUser(
                request.getSession()).getName()));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "adminClasses", utilService.search(query));
        return forward(request, "listAdminClass");
    }
    
    /**
     * 班级学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward searchStudentByAdminClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Long adminClassId = getLong(request, "adminClassId");
        EntityQuery query = new EntityQuery(Student.class, "student");
        query.join("student.adminClasses", "adminClass");
        query.add(new Condition("adminClass.instructor.code = (:code)", getUser(
                request.getSession()).getName()));
        query.add(new Condition("adminClass.id = (:adminClassId)", adminClassId));
        query.setLimit(getPageLimit(request));
		String orderBy = get(request, "orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "student.code";
		}
		query.addOrder(OrderUtils.parser(orderBy));

        addCollection(request, "students", utilService.search(query));
        return forward(request, "listStudent");
    }
    
    /**
     * 辅导员所带学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward searchStudent(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(Student.class, "student");
        query.join("student.adminClasses", "adminClass");
        query.add(new Condition("adminClass.instructor.code = (:code)", getUser(
                request.getSession()).getName()));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "students", utilService.search(query));
        return forward(request, "listStudent");
    }
    
    /**
     * 查看班级的基本信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward adminClassInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action(AdminClassAction.class, "info"));
    }
    
    /**
     * 查看学生的基本信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward studentInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action("studentDetailByManager", "detail"));
    }
    
    /**
     * 查看学生成绩
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward stdGrade(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action("tutorStd", "stdGrade"));
    }
    
    /**
     * 查看学生考试安排
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward examTable(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action("tutorStd", "examTable"));
    }
    
    /**
     * 查看学生上课记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward courseTake(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action("tutorStd", "courseTake"));
    }
    
    /**
     * 查看计划完成情况
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward planFinished(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action("tutorStd", "planFinished"));
    }
    
    /**
     * 打印班级绩点
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward printMultiStdGP(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action("multiStdGP", "classGPReport"));
    }
    /**
     * 选择学年学期
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward selectCalendar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	addCollection(request, Constants.CALENDAR, teachCalendarService.getTeachCalendars());
    	return forward(request);
    }
    
    /**
     * 打印班级绩点 行政班成绩表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward classGradeReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action(MultiStdGradeReportAction.class, "classGradeReport"));
    }
    
    /**
     * 查看学生考勤
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward recordByStudentId(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action("dutyRecordSearch", "recordByStudentForm"));
    }
    
    public ActionForward recordByStudent(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action("dutyRecordSearch", "recordByStudent"));
    }

	public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
		this.teachCalendarService = teachCalendarService;
	}
}
