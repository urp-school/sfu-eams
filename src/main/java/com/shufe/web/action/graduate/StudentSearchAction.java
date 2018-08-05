//$Id: StudentSearchAction.java,v 1.1 2008-3-21 上午11:05:28 zhouqi Exp $
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
 * zhouqi              2008-3-21         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.graduate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.commons.query.limit.SinglePage;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.baseinfo.model.StudentType;
import com.shufe.model.Constants;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.PlanAuditResult;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.std.StudentService;
import com.shufe.service.system.baseinfo.SpecialityService;
import com.shufe.util.DataRealmUtils;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * @author zhouqi
 */
public class StudentSearchAction extends CalendarRestrictionSupportAction {

	protected StudentService studentService;

	protected SpecialityService specialityService;

	protected TeachTaskService teachTaskService;

	/**
	 * 搜索学生页面操作，当含搜索标示时向Results添加学生搜索结果
	 * 
	 * @param form
	 * @param request
	 */
	protected void searchStudent(ActionForm form, HttpServletRequest request) {
		String departmentIds = getDepartmentIdSeq(request);
		String studentTypeIds = getStdTypeIdSeq(request);
		initSearchStudentBar(request, departmentIds, studentTypeIds);
		String searchFalg = request.getParameter("searchFalg");
		if (StringUtils.isNotEmpty(searchFalg) && searchFalg.equals("search")) {
			EntityQuery query = buildQuery(request);
			SinglePage rs = (SinglePage) utilService.search(query);
			Pagination stds = new Pagination(query.getLimit().getPageNo(),
					query.getLimit().getPageSize(), new Result(rs.getTotal(),
							(List) rs.getPageDatas()));
			Results.addObject("studentList", stds);
			addOldPage(request, "studentList", stds);
		}
	}

	/**
	 * 收集学生查询条件
	 * 
	 * @param request
	 * @return
	 */
	protected EntityQuery buildQuery(HttpServletRequest request) {
		EntityQuery query = new EntityQuery(Student.class, "std");
		populateConditions(request, query, "std.type.id");
		String studenGraduateAuditStatus = get(request,
				"studentGraduateAuditStatus");
		// 查询审核状态
		if (null != studenGraduateAuditStatus) {
			query.add(new Condition(
					"exists(from "
							+ PlanAuditResult.class.getName()
							+ " par where par.std.id=std.id and  par.graduateAuditStatus = " + studenGraduateAuditStatus+")"));
		}
		Long stdTypeId = getLong(request, "std.type.id");
		query.addOrder(OrderUtils.parser(get(request, "orderBy")));
		DataRealmUtils.addDataRealms(query, new String[] { "std.type.id",
				"std.department.id" }, getDataRealmsWith(stdTypeId, request));
		Long adminClasssId = getLong(request, "adminClasssId1");
		if (null == adminClasssId) {
			adminClasssId = getLong(request, "adminClasssId2");
		}
		query.setLimit(getPageLimit(request));
		if (null != adminClasssId) {
			query.join("std.adminClasses", "adminClass");
			query.add(new Condition("adminClass.id=" + adminClasssId));
		}
		return query;
	}

	/**
	 * 获取学生搜索样例对象
	 * 
	 * @param request
	 * @return
	 */
	protected Student getStdExample(HttpServletRequest request) {
		Student std = (Student) RequestUtil.populate(request, Student.class,
				"student");
		String adminClasssId1String = request.getParameter("adminClasssId1");
		String adminClasssId2String = request.getParameter("adminClasssId2");
		Set adminClassSet = new HashSet();
		if (StringUtils.isNotEmpty(adminClasssId1String)) {
			AdminClass adminClass = new AdminClass(Long
					.valueOf(adminClasssId1String));
			adminClassSet.add(adminClass);
		}
		if (StringUtils.isNotEmpty(adminClasssId2String)) {
			AdminClass adminClass = new AdminClass(Long
					.valueOf(adminClasssId2String));
			adminClassSet.add(adminClass);
		}
		std.setAdminClasses(adminClassSet);
		return std;
	}

	/**
	 * 通过request获取学生<code>EntityQuery</code>对象
	 * 
	 * @param request
	 * @param moduleName
	 * @return
	 */
	protected EntityQuery getStdQuery(HttpServletRequest request,
			String moduleName) {
		EntityQuery entityQuery = new EntityQuery(Student.class, "student");
		if (StringUtils.isNotEmpty(moduleName)) {
			entityQuery.add(new Condition(
					"student.department in (:departDataRealm)",
					getDeparts(request)));
			entityQuery.add(new Condition("student.type in (:departDataRealm)",
					getStdTypes(request)));
		}
		QueryRequestSupport.populateConditions(request, entityQuery);
		Long adminClasssId1 = getLong(request, "adminClasssId1");
		Long adminClasssId2 = getLong(request, "adminClasssId2");
		if (adminClasssId1 != null && adminClasssId2 != null) {
			entityQuery.add(new Condition("exists (select class"
					+ adminClasssId1 + " from AdminClass class"
					+ adminClasssId1 + " join class" + adminClasssId1
					+ ".students std where std.id=student.id and class"
					+ adminClasssId1 + ".id = :classId" + adminClasssId1 + ")"
					+ " and exists (select class" + adminClasssId2
					+ " from AdminClass class" + adminClasssId2 + " join class"
					+ adminClasssId2
					+ ".students std where std.id=student.id and class"
					+ adminClasssId2 + ".id = :classId" + adminClasssId2 + ")",
					adminClasssId1, adminClasssId2));
		} else if (adminClasssId1 != null) {
			addAdminClassCondtion(entityQuery, adminClasssId1);
		} else if (adminClasssId2 != null) {
			addAdminClassCondtion(entityQuery, adminClasssId2);
		}
		return entityQuery;
	}

	/**
	 * 添加一个班级条件
	 * 
	 * @param entityQuery
	 * @param adminClasssId
	 */
	private void addAdminClassCondtion(EntityQuery entityQuery,
			Long adminClasssId) {
		entityQuery.add(new Condition("exists (select class" + adminClasssId
				+ " from AdminClass class" + adminClasssId + " join class"
				+ adminClasssId + ".students std where std.id=id and class"
				+ adminClasssId + ".id = :classId" + adminClasssId + ")",
				adminClasssId));
	}

	/**
	 * 搜索双专业学生页面操作，当含搜索标示时向Results添加双专业学生搜索结果
	 * 
	 * @param form
	 * @param request
	 * @param moduleName
	 * @param is2ndMajor
	 * @param andOr
	 */
	protected void searchStudentWith2ndSpeciality(ActionForm form,
			HttpServletRequest request, String moduleName, Boolean is2ndMajor,
			String andOr) {
		String departmentIds = getDepartmentIdSeq(request);
		String studentTypeIds = getStdTypeIdSeq(request);
		initSearchStudentBar(request, departmentIds, studentTypeIds);
		Speciality speciality = new Speciality();
		speciality.setMajorType(new MajorType(MajorType.SECOND));
		Results.addObject("secondSpecialityList", specialityService
				.getSpecialities(speciality, studentTypeIds, departmentIds));
		String searchFalg = request.getParameter("searchFalg");
		if (StringUtils.isNotEmpty(searchFalg) && searchFalg.equals("search")) {
			DynaActionForm dynaForm = (DynaActionForm) form;
			int pageNo = ((Integer) dynaForm.get("pageNo")).intValue();
			int pageSize = ((Integer) dynaForm.get("pageSize")).intValue();
			Student std = getStdExample(request);
			if (Boolean.TRUE.equals(is2ndMajor)) {
				Collection stds = studentService.search2ndSpecialityStudent(
						std, new PageLimit(pageNo, pageSize), studentTypeIds,
						"", departmentIds, "");
				addCollection(request, "studentList", stds);
			} else if (Boolean.FALSE.equals(is2ndMajor)) {
				Pagination stds = null;
				stds = studentService.searchStudent(std, pageNo, pageSize,
						studentTypeIds, departmentIds);
				Results.addObject("studentList", stds);
			} else {
				Collection stds = studentService.search2ndSpecialityStudent(
						std, new PageLimit(pageNo, pageSize), studentTypeIds,
						departmentIds, departmentIds, StringUtils
								.isBlank(andOr) ? "or" : andOr);
				addCollection(request, "studentList", stds);
			}

		}
	}

	/**
	 * 初始化学生信息搜索框
	 * 
	 * @param request
	 *            TODO
	 * @param departmentIds
	 * @param studentTypeIds
	 */
	protected void initSearchStudentBar(HttpServletRequest request,
			String departmentIds, String studentTypeIds) {
		List stdTypes = studentTypeService.getStudentTypes(studentTypeIds);
		Collections.sort(stdTypes, new PropertyComparator("code"));
		addCollection(request, "stdTypeList", stdTypes);
		List departs = departmentService.getColleges(departmentIds);
		Collections.sort(departs, new PropertyComparator("code"));
		addCollection(request, "departmentList", departs);
	}

	/**
	 * 搜索Form页面初始化
	 * 
	 * @param form
	 * @param request
	 */
	protected void initSearchBar(ActionForm form, HttpServletRequest request) {
		String departmentIds = getDepartmentIdSeq(request);
		String studentTypeIds = getStdTypeIdSeq(request);
		initSearchStudentBar(request, departmentIds, studentTypeIds);
		setOtherSearch(request, studentTypeIds, departmentIds);
		String searchFalg = request.getParameter("searchFalg");
		if (StringUtils.isNotEmpty(searchFalg) && searchFalg.equals("search")) {
			searchBar(form, request, studentTypeIds, departmentIds);
		}
	}

	/**
	 * 搜索学生并向Results添加学生搜索结果
	 * 
	 * @param form
	 * @param request
	 * @param studentTypeIds
	 * @param departmentIds
	 */
	protected void searchBar(ActionForm form, HttpServletRequest request,
			String studentTypeIds, String departmentIds) {
		EntityQuery entityQuery = buildQuery(request);
		addCollection(request, "studentList", utilService.search(entityQuery));
	}

	/**
	 * 添加额外搜索项数据<br>
	 * 供子类重写
	 * 
	 * @param request
	 * @param studentTypeIds
	 * @param departmentIds
	 */
	protected void setOtherSearch(HttpServletRequest request,
			String studentTypeIds, String departmentIds) {
	}

	/**
	 * 搜索行政班级页面操作，当含搜索标示时向Results添加班级搜索结果
	 * 
	 * @param form
	 * @param request
	 * @param moduleName
	 */
	protected void searchAdminClass(ActionForm form,
			HttpServletRequest request, String moduleName) {
		String departmentIds = getDepartmentIdSeq(request);
		String studentTypeIds = getStdTypeIdSeq(request);
		initSearchStudentBar(request, departmentIds, studentTypeIds);
		String searchFalg = request.getParameter("searchFalg");
		if (StringUtils.isNotEmpty(searchFalg) && searchFalg.equals("search")) {
			DynaActionForm dynaForm = (DynaActionForm) form;
			int pageNo = ((Integer) dynaForm.get("pageNo")).intValue();
			int pageSize = ((Integer) dynaForm.get("pageSize")).intValue();
			AdminClass adminClass = (AdminClass) RequestUtil.populate(request,
					AdminClass.class, "adminClass");
			Pagination adminClassList = studentService
					.searchStudentClass(adminClass, pageNo, pageSize,
							studentTypeIds, departmentIds);
			Results.addObject("adminClassList", adminClassList);
		}
	}

	/**
	 * 搜索教学任务页面操作，当含搜索标示时向Results添加教学任务搜索结果
	 * 
	 * @param form
	 * @param request
	 * @throws Exception
	 */
	protected void searchTeachTask(ActionForm form, HttpServletRequest request)
			throws Exception {

		setCalendarDataRealm(request, hasStdTypeCollege);

		String studentTypeIds = getStdTypeIdSeq(request);
		String departmentIds = getDepartmentIdSeq(request);
		Object obj = request.getAttribute(Constants.CALENDAR);
		if (obj != null) {
			Results.addList("courseTypeList", teachTaskService
					.getCourseTypesOfTask(studentTypeIds, departmentIds,
							(TeachCalendar) obj));
			addCollection(request, "teachDepartList", getTeachDeparts(request));
			request.removeAttribute(Constants.DEPARTMENT_LIST);
			Results.addList(Constants.DEPARTMENT_LIST, getColleges(request));
			Results.addList("teacherList", teachTaskService.getTeachersOfTask(
					studentTypeIds, departmentIds, (TeachCalendar) obj));

			String searchFalg = request.getParameter("searchFalg");
			if (StringUtils.isNotEmpty(searchFalg)
					&& searchFalg.equals("search")) {
				int pageNo = QueryRequestSupport.getPageNo(request);
				int pageSize = QueryRequestSupport.getPageSize(request);
				TeachTask task = (TeachTask) RequestUtil.populate(request,
						TeachTask.class, "teachTask");
				Teacher teacher = (Teacher) RequestUtil.populate(request,
						Teacher.class, Constants.TEACHER);
				task.getArrangeInfo().getTeachers().add(teacher);
				/*-------------获得学年度学期信息--------------------*/
				Long stdTypeId = getLong(request, "calendar.studentType.id");
				task.getTeachClass().setStdType(
						(StudentType) EntityUtils.getEntity(StudentType.class,
								stdTypeId));
				if (task.getCalendar().getId() == null) {
					String year = request.getParameter("calendar.year");
					String term = request.getParameter("calendar.term");
					TeachCalendar calendar = null;
					if (StringUtils.isNotEmpty(year)
							&& StringUtils.isNotEmpty(term)) {
						calendar = getTeachCalendarService().getTeachCalendar(
								new StudentType(stdTypeId), year, term);
						task.setCalendar(calendar);
					} else {
					}
					if (null == calendar) {
					}
				}

				Pagination tasks = null;
				/*-------------查询数据--------------------------*/
				if (task.getArrangeInfo().getTeachDepart().isVO()) {
					String departIds = getDepartmentIdSeq(request);

					tasks = teachTaskService.getTeachTasksOfTeachDepart(task,
							getStdTypeIdSeqOf(stdTypeId, request), departIds,
							pageNo, pageSize);
				} else
					tasks = teachTaskService.getTeachTasks(task, pageNo,
							pageSize);
				Results.addPagination("teachTaskList", tasks);
				// 查询页面需要日历信息
				Results.addObject("calendar", task.getCalendar());
				addEntity(request, task);
			}
		}
	}

	/**
	 * @param buf
	 */
	private void loggerDebug(StringBuffer buf) {
		if (log.isDebugEnabled()) {
			log.debug(buf.toString());
		}
	}

	/**
	 * @param request
	 */
	protected void addResults(HttpServletRequest request) {
		if (Results != null) {
			request.setAttribute(DETAIL_RESULT, Results.getDetail());
		}
	}

	/**
	 * <pre>
	 *  获取学生的BeanMap
	 *  TODO 现在的BeanMap为手动添加的
	 * </pre>
	 * 
	 * @param stdNoMap
	 * @param request
	 * @return
	 */
	public Map getStdBeanMap(Map stdNoMap, HttpServletRequest request) {
		String stdCodes = (String) stdNoMap.get("record.student.code");
		String[] stdCodeArray = StringUtils.split(stdCodes, ",");
		if (ArrayUtils.isEmpty(stdCodeArray)) {
			return Collections.EMPTY_MAP;
		}
		String departmentIds = getDepartmentIdSeq(request);
		String studentTypeIds = getStdTypeIdSeq(request);
		List stdList = utilService.load(Student.class, "code", stdCodeArray);
		if (stdList.size() == 0) {
			return Collections.EMPTY_MAP;
		} else {
			Map resultMap = new HashMap();
			for (Iterator iter = stdList.iterator(); iter.hasNext();) {
				Student std = (Student) iter.next();
				if ((departmentIds.indexOf(String.valueOf(std.getDepartment()
						.getId())) == -1)
						|| (studentTypeIds.indexOf(String.valueOf(std.getType()
								.getId())) == -1)) {
					continue;
				} else {
					Map stdMap = new HashMap();
					stdMap.put("student.id", std.getId());
					stdMap.put("student.code", std.getCode());
					stdMap.put("student.name", std.getName());
					stdMap.put("student.engName", std.getEngName());
					stdMap.put("student.enrollYear", std.getEnrollYear());
					stdMap.put("student.type.name", std.getType().getName());
					stdMap.put("student.type.engName", std.getType()
							.getEngName());
					stdMap.put("student.state.name", std.isActive() ? "有效"
							: "无效");
					stdMap.put("student.state.engName", std.isActive() ? "有效"
							: "无效");
					stdMap.put("student.department.name", std.getDepartment()
							.getName());
					stdMap.put("student.department.engName", std
							.getDepartment().getEngName());
					stdMap.put("student.firstMajor.name", (null == std
							.getFirstMajor()) ? null : std.getFirstMajor()
							.getName());
					stdMap.put("student.firstMajor.engName", (null == std
							.getFirstMajor()) ? null : std.getFirstMajor()
							.getEngName());
					stdMap.put("student.firstAspect.name", (null == std
							.getFirstAspect()) ? null : std.getFirstAspect()
							.getName());
					stdMap.put("student.firstAspect.engName", (null == std
							.getFirstAspect()) ? null : std.getFirstAspect()
							.getEngName());
					stdMap.put("student.basicInfo.homeAddress", (null == std
							.getBasicInfo().getHomeAddress()) ? null : std
							.getBasicInfo().getHomeAddress());
					stdMap.put("student.basicInfo.postCode", (null == std
							.getBasicInfo().getPostCode()) ? null : std
							.getBasicInfo().getPostCode());
					stdMap.put("student.basicInfo.phone", (null == std
							.getBasicInfo().getPhone()) ? null : std
							.getBasicInfo().getPhone());
					resultMap.put(std.getId(), stdMap);
				}
			}
			return resultMap;
		}
		// return Collections.EMPTY_MAP;
	}

	public void setSpecialityService(SpecialityService specialityService) {
		this.specialityService = specialityService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public void setTeachTaskService(TeachTaskService teachTaskService) {
		this.teachTaskService = teachTaskService;
	}
}
