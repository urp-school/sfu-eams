package com.shufe.web.action.course.textbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.basecode.industry.BookType;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.Press;
import com.ekingstar.eams.system.basecode.industry.TextbookAwardLevel;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.course.textbook.BookRequirement;
import com.shufe.model.course.textbook.Textbook;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.course.textbook.BookRequirementService;
import com.shufe.web.helper.BaseInfoSearchHelper;

public class BookRequirementAction extends RequirementSearchAction {

	private BookRequirementService bookRequirementService;

	private TeachTaskService teachTaskService;

	private BaseInfoSearchHelper baseInfoSearchHelper;

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String searchWhat = request.getParameter("searchWhat");
		if (searchWhat.equals("taskList")) {
			return taskList(mapping, form, request, response);
		} else {
			return requirementList(mapping, form, request, response);
		}
	}

	public ActionForward requirementList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		EntityQuery entityQuery = buildRequireQuery(request);
		addCollection(request, "requires", utilService.search(entityQuery));
		return forward(request, "requirementList");
	}

	/**
	 * 查询没有登记教材需求的教学任务.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taskList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		EntityQuery query = buildUnrequirementQuery(request);
		addCollection(request, "tasks", utilService.search(query));
		return forward(request, "taskList");
	}

	/**
	 * 教材需求主页面
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
		setCalendarDataRealm(request, hasStdTypeCollege);
		addCollection(request, "teachDeparts", getColleges(request));
		addCollection(request, "awardLevels", baseCodeService.getCodes(TextbookAwardLevel.class));
		addCollection(request, "courseTypes", baseCodeService.getCodes(CourseType.class));
		return forward(request);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ids = request.getParameter("requireIds");
		Long[] requireIds = SeqStringUtil.transformToLong(ids);
		for (int x = 0; x < requireIds.length; x++) {
			BookRequirement requirement = bookRequirementService.getBookRequirement(requireIds[x]);
			utilService.remove(requirement);
		}
		return redirect(request, "search", "info.delete.success");
	}

	/**
	 * 保存新增或修改的教材需求<br>
	 * 如果引用了没有的教材,系统将保存该教材.
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
		BookRequirement requirement = (BookRequirement) populateEntity(request,
				BookRequirement.class, "require");
		Textbook book = (Textbook) populateEntity(request, Textbook.class, "book");
		requirement.setTextbook(book);
		// 如果教学任务的要求中,没有该书,则添加进去.
		TeachTask task = teachTaskService.getTeachTask(requirement.getTask().getId());
		if (!task.getRequirement().getTextbooks().contains(book)) {
			task.getRequirement().getTextbooks().add(book);
			utilService.saveOrUpdate(task);
		}
		bookRequirementService.saveBookRequirement(requirement);
		return redirect(request, "search", "info.save.success");
	}

	/**
	 * 新增或修改教材需求<br>
	 * 如果一个任务需要两种以上的教材，则根据当前选择的教材需求 找到其他同一任务的教材需求。
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
		Long taskId = getLong(request, "task.id");
		TeachTask task = null;
		if (null != taskId) {
			task = teachTaskService.getTeachTask(taskId);

		} else {
			Long requireId = getLong(request, "requireId");
			BookRequirement require = (BookRequirement) utilService.get(BookRequirement.class,
					requireId);
			task = require.getTask();
		}
		addEntity(request, task);

		EntityQuery entityQuery = new EntityQuery(BookRequirement.class, "require");
		entityQuery.add(new Condition("require.task.id =" + task.getId()));
		addCollection(request, "requires", utilService.search(entityQuery));
		addCollection(request, "presses", baseCodeService.getCodes(Press.class));
		addCollection(request, "bookTypes", baseCodeService.getCodes(BookType.class));
		return forward(request);
	}

	public List getTeachTasks(HttpServletRequest request, Long stdTypeId, String year, String term,
			Long teachDepartId, String courseName) {

		TeachCalendar calendar = teachCalendarService.getTeachCalendar(stdTypeId, year, term);
		if (null != calendar) {
			TeachTask task = new TeachTask();
			task.setCalendar(calendar);
			task.getCourse().setName(courseName);
			task.getArrangeInfo().getTeachDepart().setId(teachDepartId);
			List tasks = teachTaskService.getTeachTasks(task);
			List rs = new ArrayList();
			Iterator iter = tasks.iterator();
			while (iter.hasNext()) {
				TeachTask oneTask = (TeachTask) iter.next();
				rs.add(new Object[] { oneTask.getId(),
						oneTask.getCourse().getName() + "(" + oneTask.getSeqNo() + ")" });
			}
			return rs;
		} else
			return null;
	}

	public ActionForward updateCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = Long.valueOf(request.getParameter("requireId"));
		BookRequirement requirement = bookRequirementService.getBookRequirement(id);
		requirement.setCountForStd(new Integer(NumberUtils.toInt(request
				.getParameter("require.countForStd"))));
		requirement.setCountForTeacher(new Integer(NumberUtils.toInt(request
				.getParameter("require.countForTeacher"))));
		requirement.setRemark(request.getParameter("require.remark"));
		utilService.saveOrUpdate(requirement);
		return redirect(request, "search", "info.save.success");
	}

	public ActionForward chooseTextbook(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		EntityQuery entityQuery = new EntityQuery(Textbook.class, "textbook");
		QueryRequestSupport.populateConditions(request, entityQuery);
		entityQuery.setLimit(getPageLimit(request));
		entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		addCollection(request, "textbooks", utilService.search(entityQuery));
		return forward(request, "textbookList");
	}

	/**
	 * 为选择的任务批量指定教材
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchSetBook(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long[] taskIds = SeqStringUtil.transformToLong(request.getParameter("taskIds"));
		List tasks = teachTaskService.getTeachTasksByIds(taskIds);
		Long textbookId = getLong(request, "textbookId");
		Textbook book = (Textbook) utilService.get(Textbook.class, textbookId);
		List newRequirements = new ArrayList(tasks.size());
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			TeachTask task = (TeachTask) iter.next();
			newRequirements.add(new BookRequirement(task, book));
		}
		utilService.saveOrUpdate(newRequirements);
		return redirect(request, "search", "info.set.success");
	}

	/**
	 * 班级教材发放清单主界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward adminClassHome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		setDataRealm(request, hasStdTypeCollege);
		TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
				"calendar.id"));
		request.setAttribute("calendar", calendar);
		return forward(request);
	}

	/**
	 * 查询班级
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
		addCollection(request, "adminClasses", baseInfoSearchHelper.searchAdminClass(request));
		return forward(request, "adminClassList");
	}

	/**
	 * 查询班级
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportForAdminClass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
				"calendar.id"));
		Long[] adminClassIds = SeqStringUtil.transformToLong(request.getParameter("adminClassIds"));

		List adminClasses = (List) utilService.load(AdminClass.class, "id", adminClassIds);
		Map adminClassMap = new HashMap(adminClasses.size());
		Map bookRequireMap = new HashMap(adminClasses.size());
		for (Iterator iter = adminClasses.iterator(); iter.hasNext();) {
			AdminClass adminClass = (AdminClass) iter.next();
			adminClassMap.put(adminClass.getId().toString(), adminClass);
			bookRequireMap.put(adminClass.getId().toString(), bookRequirementService
					.getBookRequirements(calendar, adminClass));
		}
		request.setAttribute("adminClassMap", adminClassMap);
		request.setAttribute("bookRequireMap", bookRequireMap);
		request.setAttribute("calendar", calendar);
		return forward(request);
	}

	public void setBookRequirementService(BookRequirementService bookRequirementService) {
		this.bookRequirementService = bookRequirementService;
	}

	public void setTeachTaskService(TeachTaskService teachTaskService) {
		this.teachTaskService = teachTaskService;
	}

	public void setBaseInfoSearchHelper(BaseInfoSearchHelper baseInfoSearchHelper) {
		this.baseInfoSearchHelper = baseInfoSearchHelper;
	}

}
