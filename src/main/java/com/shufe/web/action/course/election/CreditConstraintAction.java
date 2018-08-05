//$Id: CreditConstraintAction.java,v 1.24 2006/12/21 12:29:19 duanth Exp $
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
 * chaostone             2005-12-11         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.election;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ekingstar.commons.collection.predicates.NotZeroNumberPredicate;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.model.Constants;
import com.shufe.model.course.election.CreditAwardCriteria;
import com.shufe.model.course.election.CreditConstraint;
import com.shufe.model.course.election.CreditInitParams;
import com.shufe.model.course.election.SpecialityCreditConstraint;
import com.shufe.model.course.election.StdCreditConstraint;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.course.election.CreditConstraintService;
import com.shufe.util.DataRealmLimit;
import com.shufe.util.DataRealmUtils;
import com.shufe.util.RequestUtil;
import com.shufe.web.OutputProcessObserver;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 选课的学分限制界面响应类（专业学分上下限，个人学分上下限，奖励学分标准）
 * 
 * @author chaostone 2005-12-11
 */
public class CreditConstraintAction extends CalendarRestrictionSupportAction {

	private CreditConstraintService creditService;

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward awardCriteriaList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List awardCriterias = utilService.loadAll(CreditAwardCriteria.class);
		request.setAttribute(Constants.CREDITAWARDCRITERIA_LIST, awardCriterias);
		return forward(request);
	}

	/**
	 * 为没有学分上线的学生，批量添加的学分上限
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchAddCredit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String stdIdSeq = request.getParameter("stdIds");
		CreditConstraint constraint = (CreditConstraint) RequestUtil.populate(request,
				CreditConstraint.class, true);
		if (null == constraint.getMaxCredit() || null == constraint.getCalendar().getId()) {
			return forwardError(mapping, request, "error.parameters.needed");
		}
		Long stdTypeId = getLong(request, "calendar.studentType.id");
		if (StringUtils.isEmpty(stdIdSeq)) {
			DataRealm realm = new DataRealm();
			realm.setDepartmentIdSeq(getDepartmentIdSeq(request));
			realm.setStudentTypeIdSeq(getStdTypeIdSeqOf(stdTypeId, request));
			Student std = (Student) RequestUtil.populate(request, Student.class,
					"creditConstraint.std", true);
			creditService.batchAddCredit(std, realm, constraint);
		} else {
			Long[] stdIds = SeqStringUtil.transformToLong(stdIdSeq);
			creditService.batchAddCredit(Arrays.asList(stdIds), constraint);
		}
		return redirect(request, "creditConstraintList", "info.set.success");
	}

	/**
	 * 学分上限列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward creditConstraintList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		EntityQuery query = null;
		Boolean isSpecialityConstraint = getBoolean(request, "isMajorConstraint");
		if (isSpecialityConstraint.equals(Boolean.TRUE)) {
			query = new EntityQuery(SpecialityCreditConstraint.class, "creditConstraint");
			populateConditions(request, query, "creditConstraint.stdType.id");
			Float maxFrom = getFloat(request, "creditConstraintMaxFrom");
			if (null != maxFrom) {
				query.add(new Condition("creditConstraint.maxCredit >=:maxFrom", maxFrom));
			}
			Float maxTo = getFloat(request, "creditConstraintMaxTo");
			if (null != maxTo) {
				query.add(new Condition("creditConstraint.maxCredit <=:maxTo", maxTo));
			}

			Long stdTypeId = getLong(request, "creditConstraint.stdType.id");
			DataRealmUtils.addDataRealms(query, new String[] { "creditConstraint.stdType.id",
					"creditConstraint.depart.id" }, getDataRealmsWith(stdTypeId, request));
		} else {
			query = new EntityQuery(StdCreditConstraint.class, "creditConstraint");
			populateConditions(request, query, "creditConstraint.std.type.id");
			Float maxFrom = getFloat(request, "maxFrom");
			if (null != maxFrom) {
				query.add(new Condition("creditConstraint.maxCredit >=:maxFrom", maxFrom));
			}
			Float maxTo = getFloat(request, "maxTo");
			if (null != maxTo) {
				query.add(new Condition("creditConstraint.maxCredit <=:maxTo", maxTo));
			}
			Float electedFrom = getFloat(request, "electedFrom");
			if (null != electedFrom) {
				query.add(new Condition("creditConstraint.electedCredit >=:electedFrom",
						electedFrom));
			}
			Float electedTo = getFloat(request, "electedTo");
			if (null != electedTo) {
				query.add(new Condition("creditConstraint.electedCredit <=:electedTo", electedTo));
			}
			Float awardFrom = getFloat(request, "awardFrom");
			if (null != awardFrom) {
				query.add(new Condition("creditConstraint.awardedCredit >=:awardFrom", awardFrom));
			}
			Float awardTo = getFloat(request, "awardTo");
			if (null != awardTo) {
				query.add(new Condition("creditConstraint.awardedCredit <=:awardTo", awardTo));
			}
			Float gpaFrom = getFloat(request, "gpaFrom");
			if (null != gpaFrom) {
				query.add(new Condition("creditConstraint.GPA >=:gpaFrom", gpaFrom));
			}
			Float gpaTo = getFloat(request, "gpaTo");
			if (null != gpaTo) {
				query.add(new Condition("creditConstraint.GPA <=:gpaTo", gpaTo));
			}
			Long stdTypeId = getLong(request, "creditConstraint.std.type.id");
			DataRealmUtils.addDataRealms(query, new String[] { "creditConstraint.std.type.id",
					"creditConstraint.std.department.id" }, getDataRealmsWith(stdTypeId, request));
		}
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(get(request, "orderBy")));
		addCollection(request, "creditConstrains", utilService.search(query));
		return forward(request);
	}

	/**
	 * 修改学生选分限制信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editStdCreditConstraint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String whichCalendar = request.getParameter("whichCalendar");
		if (StringUtils.isNotEmpty(whichCalendar)) {
			Long calendarId = getLong(request, "calendar.id");
			Long studentId = getLong(request, "student.id");
			TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
			Student std = (Student) utilService.get(Student.class, studentId);
			TeachCalendar whichTeachCalendar = null;
			if (StringUtils.equals(whichCalendar, "preCalendar")) {
				whichTeachCalendar = calendar.getPrevious();
			} else {
				whichTeachCalendar = calendar.getNext();
			}
			if (null != whichTeachCalendar) {
				StdCreditConstraint sc = creditService.getStdCreditConstraint(std.getId(),
						whichTeachCalendar);
				if (null == sc) {
					sc = new StdCreditConstraint();
					sc.setStd(std);
				}
				request.setAttribute(Constants.CALENDAR, whichTeachCalendar);
				request.setAttribute("constraint", sc);
			} else {
				return forwardError(mapping, request, new String[] { "entity.calendar",
						"error.model.notExist" });
			}
		} else {
			Long stdCreditConstraintId = getLong(request, "stdCreditConstraint.id");
			StdCreditConstraint constraint = null;
			if (NotZeroNumberPredicate.INSTANCE.evaluate(stdCreditConstraintId)) {
				constraint = (StdCreditConstraint) utilService.get(StdCreditConstraint.class,
						stdCreditConstraintId);
			} else {
				constraint = new StdCreditConstraint();
			}
			request.setAttribute(Constants.CALENDAR, teachCalendarService.getTeachCalendar(getLong(
					request, "creditConstraint.calendar.id")));
			request.setAttribute("constraint", constraint);
		}
		return forward(request);
	}

	/**
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
		return forward(request);
	}

	/**
	 * 专业学分上限下限初始化<br>
	 * 学生个人学分上限、下限、以修、奖励、平均绩点初始化
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCreditConstraint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		ActionForward processDisplay = mapping.findForward("processDisplay");
		String path = request.getSession().getServletContext().getRealPath("")
				+ processDisplay.getPath();

		OutputProcessObserver observer = new OutputProcessObserver(response.getWriter(),
				getResources(request), getLocale(request), path);
		// 进行初始化
		creditService.initCreditConstraint(request.getParameter("stdTypeIds"),
				getDepartmentIdSeq(request), teachCalendarService.getTeachCalendar(getLong(request,
						"calendar.id")), observer);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * 选课学分初始化提示界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSpecialityCreditPrompt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long stdTypeId = getLong(request, "calendar.studentType.id");
		addCollection(request, "stdTypes", getCalendarStdTypesOf(stdTypeId, request));
		addCollection(request, "departList", getColleges(request));
		request.setAttribute("calendar", teachCalendarService.getTeachCalendar(getLong(request,
				"calendar.id")));
		return forward(request);
	}

	/**
	 * 没有学分上限的学生
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward noCreditStdList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long calendarId = getLong(request, "creditConstraint.calendar.id");
		TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);

		Long stdTypeId = getLong(request, "creditConstraint.std.type.id");
		Student std = (Student) RequestUtil.populate(request, Student.class,
				"creditConstraint.std", true);
		if (null == calendar) {
			return forwardError(mapping, request, "error.parameters.illegal");
		} else {
			DataRealmLimit limit = getDataRealmLimit(request);
			if (null != stdTypeId) {
				limit.getDataRealm().setStudentTypeIdSeq(getStdTypeIdSeqOf(stdTypeId, request));
			} else {
				limit.getDataRealm().setStudentTypeIdSeq(getStdTypeIdSeq(request));
			}
			List orderList = new ArrayList();
			orderList.add(new Order("code"));
			Collection stds = creditService.getStdWithoutCredit(std, calendar, limit, orderList);
			addCollection(request, "stds", stds);
		}
		return forward(request);
	}

	/**
	 * 重新初始化学分限制
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reInitCreditConstraint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long[] specialityCreditConstraintIds = SeqStringUtil.transformToLong(request
				.getParameter("specialityCreditConstraintIds"));
		if (null == specialityCreditConstraintIds)
			return forwardError(mapping, request, "error.model.id.needed");
		creditService.initCreditConstraint(specialityCreditConstraintIds, teachCalendarService
				.getTeachCalendar(getLong(request, "calendar.id")));
		request.setAttribute("method", "creditConstraintList");
		return forward(mapping, request, "info.update.success", "redirector");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAwardCriteria(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deletedIds = request.getParameter("deletedIds");

		if (StringUtils.isNotEmpty(deletedIds))
			utilService.remove(CreditAwardCriteria.class, "id", SeqStringUtil
					.transformToLong(deletedIds));
		Integer newOrModifies = getInteger(request, "newOrModifies");

		Enumeration paramNames = request.getParameterNames();
		for (int i = 1; i <= newOrModifies.intValue(); i++) {
			boolean hasThis = false;
			while (paramNames.hasMoreElements()) {
				String scopeName = (String) paramNames.nextElement();
				if (scopeName.indexOf(Constants.CREDITAWARDCRITERIA + i) == 0) {
					hasThis = true;
					break;
				}
			}
			if (hasThis) {
				CreditAwardCriteria criteria = (CreditAwardCriteria) RequestUtil.populate(request,
						CreditAwardCriteria.class, Constants.CREDITAWARDCRITERIA + i);
				// if(null==criteria.getId()||criteria.getId().intValue()==0)
				utilService.saveOrUpdate(criteria);
			}
		}
		return redirect(request, "awardCriteriaList", "info.save.success");
	}

	/**
	 * 保存学生选分限制信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveStdCreditConstraint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		StdCreditConstraint constraint = (StdCreditConstraint) RequestUtil.populate(request,
				StdCreditConstraint.class, "constraint");
		StdCreditConstraint saved = creditService.getStdCreditConstraint(constraint.getStd()
				.getId(), constraint.getCalendar());
		// 如果是个新实体
		if (!constraint.isPO()) {
			// 如果已经存在了对应的学分
			if (null != saved) {
				request.setAttribute("constraint", saved);
				request.setAttribute("calendar", saved.getCalendar());
				return forward(mapping, request, "error.model.existed", "stdCreditForm");
			} else {
				List stds = utilService.load(Student.class, "code", constraint.getStd().getCode());
				TeachCalendar calendar = teachCalendarService.getTeachCalendar(constraint
						.getCalendar().getId());
				if (stds.isEmpty()) {
					request.setAttribute("calendar", calendar);
					request.setAttribute("constraint", constraint);
					ActionMessages actionMessages = new ActionMessages();
					actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"error.parameters.illegal"));
					addErrors(request, actionMessages);
					return forward(request, "editStdCreditConstraint");
				}

				Student std = (Student) (stds.get(0));
				if (null != creditService.getStdCreditConstraint(std.getId(), calendar)) {
					return forwardError(mapping, request, "error.model.existed");
				}
				// 如果学生的学号是错误的
				constraint.setStd(std);
				utilService.saveOrUpdate(constraint);
				return redirect(request, "creditConstraintList", "info.save.success");
			}
		} else {
			EntityUtils.evictEmptyProperty(constraint);
			EntityUtils.merge(saved, constraint);
			utilService.saveOrUpdate(saved);
			return redirect(request, "creditConstraintList", "info.save.success");
		}
	}

	/**
	 * @param creditService
	 *            The creditService to set.
	 */
	public void setCreditService(CreditConstraintService creditService) {
		this.creditService = creditService;
	}

	/**
	 * 对于选中的专业和学生学分进行选中批量设置<br>
	 * 更新学分限制（专业学分或者学生的个人学分） isMaxConstraint参数表示是否要更新上限
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String constraintIds = request.getParameter("constraintIds");
		Boolean isMaxConstraint = getBoolean(request, "isMaxConstraint");
		Boolean isMajorConstraint = getBoolean(request, "isMajorConstraint");
		Float value = getFloat(request, "creditValue");

		if (isMajorConstraint.equals(Boolean.TRUE)) {
			Boolean isUpdateStdCascade = getBoolean(request, "isUpdateStdCascade");
			creditService.updateSpecialityCreditConstraints(SeqStringUtil
					.transformToLong(constraintIds), value, isMaxConstraint.booleanValue(),
					isUpdateStdCascade.booleanValue());
		} else {
			creditService.updateStdCreditConstraints(SeqStringUtil.transformToLong(constraintIds),
					value, isMaxConstraint.booleanValue());
		}
		return redirect(request, "creditConstraintList", "info.update.success");
	}

	/**
	 * 统计已修学分和奖励学分,平均绩点
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statCreditConstraint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String stdContraintIdSeq = request.getParameter("stdContraintIdSeq");
		CreditInitParams params = getCreditInitparams(request);

		params.setCalendarId(getLong(request, "creditConstraint.calendar.id"));
		TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, params
				.getCalendarId());
		List calendars = teachCalendarService.getTeachCalendarsOfOverlapped(calendar);
		String overlappedCalendarIds = "";
		for (Iterator iter = calendars.iterator(); iter.hasNext();) {
			TeachCalendar one = (TeachCalendar) iter.next();
			overlappedCalendarIds += one.getId().toString() + ",";
		}
		params.setOverlappedCalendarIds(overlappedCalendarIds);
		// 如果将初始化奖励学分，必须有绩点的参照学期
		if (null != params.getInitAwardCredit() && params.getInitAwardCredit().equals(Boolean.TRUE)
				&& null == params.getPreCalendarId()) {
			return forwardError(mapping, request, new String[] { "entity.teachCalendar",
					"error.model.id.needed" });
		}
		if (StringUtils.isEmpty(stdContraintIdSeq)) {
			StdCreditConstraint constraint = (StdCreditConstraint) RequestUtil.populate(request,
					StdCreditConstraint.class, "creditConstraint");

			if (!constraint.getCalendar().checkId()) {
				return forwardError(mapping, request, new String[] { "entity.teachCalendar",
						"error.model.id.needed" });
			}
			OutputProcessObserver observer = getOutputProcessObserver(mapping, request, response);
			creditService.statStdCreditConstraint(constraint, params, observer);
			return null;
		} else {
			creditService.statStdCreditConstraint(SeqStringUtil.transformToLong(stdContraintIdSeq),
					params);
			return redirect(request, "creditConstraintList", "info.update.success");
		}
	}

	/**
	 * 提示统计学生的已选学分，奖励学分和平均绩点
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statStdCreditPrompt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long calendarId = getLong(request, "creditConstraint.calendar.id");
		request.setAttribute(Constants.CALENDAR, teachCalendarService.getTeachCalendar(calendarId));
		request.setAttribute("stdTypeList", getStdTypes(request));
		return forward(request);
	}

	/**
	 * 提示更新学生学分限制的界面
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
		Long[] creditConstraintIds = SeqStringUtil.transformToLong(request
				.getParameter("creditConstraintIds"));
		Boolean isMajorConstraint = getBoolean(request, "isMajorConstraint");
		if (Boolean.TRUE.equals(isMajorConstraint)) {
			utilService.remove(SpecialityCreditConstraint.class, "id", creditConstraintIds);
		} else {
			utilService.remove(StdCreditConstraint.class, "id", creditConstraintIds);
		}
		return redirect(request, "creditConstraintList", "info.delete.success");
	}

	private CreditInitParams getCreditInitparams(HttpServletRequest request) {
		CreditInitParams params = (CreditInitParams) RequestUtil.populate(request,
				CreditInitParams.class, "params");
		if (null != params.getInitAwardCredit() && params.getInitAwardCredit().equals(Boolean.TRUE)) {
			TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
					"preCalendar.studentType.id"), request.getParameter("preCalendar.year"),
					request.getParameter("preCalendar.term"));
			params.setPreCalendarId(calendar.getId());
		}
		return params;
	}

}
