//$Id: InstructStdAction.java,v 1.1 2007-7-20 上午08:48:24 chaostone Exp $
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
 * chenweixiong              2007-7-20         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.instruct;

import java.util.Collection;
import java.util.Collections;
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

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.exam.ExamTakeService;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.grade.gp.GradePointService;
import com.shufe.service.graduate.GraduateAuditService;
import com.shufe.service.system.baseinfo.DepartmentService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.web.action.common.DispatchBasicAction;
import com.shufe.web.helper.RestrictionHelper;
import com.shufe.web.helper.StdGradeHelper;
import com.shufe.web.helper.StdSearchHelper;

/**
 * 导师查看自己所带学生信息的界面响应类
 * 
 * @author chaostone
 * 
 */
public class TutorStdAction extends DispatchBasicAction {

	private StdSearchHelper stdSearchHelper;

	/** 部门服务对象 */
	private DepartmentService departmentService;

	/** 学生成绩服务对象 */
	private StdGradeHelper stdGradeHelper;

	/** 学生成绩点服务对象 */
	private GradePointService gradePointService;

	/** 成绩服务对象 */
	private GradeService gradeService;

	/** 毕业审核服务 */
	private GraduateAuditService graduateAuditService;
	
	/** 考试安排服务 */
	private ExamTakeService examTakeService;
	
	/** 教学日历服务对象 */
	private TeachCalendarService teachCalendarService;

	/**
	 * 我的学生首页
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
		EntityQuery query = new EntityQuery(
				"select distinct i.std.type from Instruction i where i.tutor.code=:code");
		Map params = new HashMap();
		params.put("code", getUser(request.getSession()).getName());
		query.setParams(params);
		List stdTypes = (List) utilService.search(query);
		Collections.sort(stdTypes, new PropertyComparator("code"));
		addCollection(request, RestrictionHelper.STDTYPES_KEY, stdTypes);
		addCollection(request, RestrictionHelper.DEPARTS_KEY, departmentService.getDepartments());
		return forward(request);
	}

	/**
	 * 学生查询
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
		EntityQuery query = stdSearchHelper.buildStdQuery(request);
		query.add(new Condition("std.teacher.code = (:name)", getUser(
				request.getSession()).getName()));
		addCollection(request, "students", utilService.search(query));
		return forward(request);
	}

	/**
	 * 显示某位学生的成绩
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
		Long stdId = getLong(request, "stdId");
		Student std = (Student) utilService.load(Student.class, stdId);
		stdGradeHelper.searchStdGrade(request, std, gradeService, baseCodeService,
				gradePointService);
		return forward(request);
	}

	/**
	 * 显示学生的考试安排
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
		Long stdId = getLong(request, "stdId");
		Student std = (Student) utilService.load(Student.class, stdId);
		Long examTypeId = getLong(request, "examType.id");
        if (null == examTypeId)
            return forwardError(mapping, request, "error.parameters.needed");
        ExamType examType = (ExamType) utilService.get(ExamType.class, examTypeId);
        
        TeachCalendar calendar = teachCalendarService.getCurTeachCalendar(std.getType());
        List calendars = teachCalendarService.getTeachCalendarsOfOverlapped(calendar);
        // 找到上课记录
        EntityQuery entityQuery = new EntityQuery(CourseTake.class, "take");
        entityQuery.add(new Condition("take.task.calendar in(:calendars)", calendars));
        entityQuery.add(new Condition("take.student=:std", std));
        Collection takes = utilService.search(entityQuery);
        if (getSystemConfig().getBooleanParam("course.exam.seatNo")) {
            Map examMap = new HashMap();
            for (Iterator iter = takes.iterator(); iter.hasNext();) {
                CourseTake courseTake = (CourseTake) iter.next();
                ExamTake examTake = courseTake.getExamTake(examType);
                if (null != examTake) {
                    Integer seatNum = examTakeService.getSeatNum(examTake);
                    if (null != seatNum) {
                        examMap.put(examTake.getId().toString(), seatNum);
                    }
                }
            }
            request.setAttribute("examMap", examMap);
        }
        request.setAttribute("examType", examType);
        addCollection(request, "takes", takes);
        return forward(request);
	}
	
	/**
	 * 显示学生的上课记录
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
		Long stdId = getLong(request, "stdId");
		EntityQuery query = new EntityQuery(CourseTake.class, "courseTake");
		query.add(new Condition("courseTake.student.id = (:stdId)", stdId));
		query.setLimit(getPageLimit(request));
		String orderBy = request.getParameter("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "courseTake.task.calendar.start desc";
		}
		query.setOrders(OrderUtils.parser(orderBy));
		request.setAttribute("std", utilService.get(Student.class, stdId));
		addCollection(request, "courseTakes", utilService.search(query));
		return forward(request);
	}

	/**
	 * 显示学生培养计划完成情况
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
		Long stdId = getLong(request, "stdId");
		Student student = (Student) utilService.load(Student.class, stdId);
		if (student == null) {
			return this.forwardError(mapping, request, "error.dataRealm.insufficient");
		}
		try {
			List resultList = graduateAuditService.getStudentTeachPlanAuditDetail(student, null,
					null, graduateAuditService.getAuditTermList(request.getParameter("auditTerm")),
					new Boolean(true), Boolean.TRUE);
			Results.addObject("resultList", resultList);
		} catch (PojoNotExistException e) {
			if (e.getMessage() != null
					&& e.getMessage().equals(com.shufe.model.Constants.TEACHPLAN)) {
				return this.forwardError(mapping, request, "error.teachPlan.notExists");
			}
		}
		return forward(request);
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
		this.stdSearchHelper = stdSearchHelper;
	}

	public void setStdGradeHelper(StdGradeHelper stdGradeHelper) {
		this.stdGradeHelper = stdGradeHelper;
	}

	public void setGradePointService(GradePointService gradePointService) {
		this.gradePointService = gradePointService;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public void setGraduateAuditService(GraduateAuditService graduateAuditService) {
		this.graduateAuditService = graduateAuditService;
	}

	public void setExamTakeService(ExamTakeService examTakeService) {
		this.examTakeService = examTakeService;
	}

	public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
		this.teachCalendarService = teachCalendarService;
	}

}
