/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is
 * intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source,
 * or other redistribution of this source is not permitted without written
 * permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/*******************************************************************************
 * @author chaostone MODIFICATION DESCRIPTION Name Date Description ============
 *         ============ ============ chaostone 2006-11-10 Created
 ******************************************************************************/
package com.shufe.web.action.course.arrange.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.CourseUnit;
import com.ekingstar.security.User;
import com.ekingstar.security.management.RoleManager;
import com.shufe.model.course.arrange.exam.ExamTurn;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 考试场次响应类
 * 
 * @author chaostone
 */
public class ExamTurnAction extends RestrictionSupportAction {

	/**
	 * 场次管理主界面
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
		List stdTypes = getStdTypes(request);
		addCollection(request, "stdTypes", stdTypes);
		return forward(request);
	}

	/**
	 * 查询场次列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EntityQuery entityQuery = new EntityQuery(ExamTurn.class, "turn");
		Long stdTypeId = getLong(request, "examTurn.stdType.id");
		if (ValidEntityKeyPredicate.INSTANCE.evaluate(stdTypeId)) {
			StudentType stdType = (StudentType) utilService.get(StudentType.class, stdTypeId);
			entityQuery.add(new Condition("turn.stdType.id=" + stdTypeId));
			addEntity(request, stdType);
		} else {
			entityQuery.add(new Condition("turn.stdType.id is null"));
		}
		addCollection(request, "examTurns", utilService.search(entityQuery));
		User user = getUser(request.getSession());
		user = (User) utilService.get(User.class, user.getId());
		request.setAttribute("isAdminUser", Boolean.valueOf(((RoleManager)user).isRoleAdmin()));
		return forward(request);
	}

	/**
	 * 保存新增或者修改的场次信息
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
		Long examTurnId = getLong(request, "examTurn.id");
		ExamTurn turn = null;
		Long stdTypeId = getLong(request, "examTurn.stdType.id");
		if (!ValidEntityKeyPredicate.INSTANCE.evaluate(examTurnId)) {
			turn = new ExamTurn();
			if (ValidEntityKeyPredicate.INSTANCE.evaluate(stdTypeId)) {
                turn.setStdType(new com.ekingstar.eams.system.baseinfo.model.StudentType(stdTypeId));
			}
		} else {
			turn = (ExamTurn) utilService.get(ExamTurn.class, examTurnId);
		}

		turn.setName(request.getParameter("examTurn.name"));
		turn.setBeginTime(CourseUnit.getTimeNumber(request.getParameter("beginTime")));
		turn.setEndTime(CourseUnit.getTimeNumber(request.getParameter("endTime")));
		EntityUtils.evictEmptyProperty(turn);
		utilService.saveOrUpdate(turn);
		return redirect(request, "list", "info.save.success", "&examTurn.stdType.id="
				+ ((stdTypeId == null) ? "" : stdTypeId.toString()));
	}

	/**
	 * 删除考试场次
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
		Long examTurnId = getLong(request, "examTurn.id");
		ExamTurn turn = (ExamTurn) utilService.get(ExamTurn.class, examTurnId);
		if (ValidEntityPredicate.INSTANCE.evaluate(turn)) {
			User user = (User) utilService.get(User.class, getUserId(request
					.getSession()));
			if (null == turn.getStdType() && !((RoleManager)user).isRoleAdmin()) {
				return forwardError(mapping, request, "error.dataRealm.insufficient");
			} else {
				utilService.remove(turn);
				return redirect(request, "list", "info.delete.success", "&examTurn.stdType.id="
						+ ((null == turn.getStdType()) ? "" : turn.getStdType().getId().toString()));
			}
		} else {
			return forwardError(mapping, request, "error.parameters.needed");
		}
	}

	/**
	 * 修改排考场次
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
		Long examTurnId = getLong(request, "examTurn.id");
		Long stdTypeId = getLong(request, "examTurn.stdType.id");
		ExamTurn turn = null;
		if (!ValidEntityKeyPredicate.INSTANCE.evaluate(stdTypeId)) {
			User user = (User) utilService.get(User.class, getUserId(request
					.getSession()));
			if (!((RoleManager)user).isRoleAdmin()) {
				return forwardError(mapping, request, "error.dataRealm.insufficient");
			}
		}
		if (!ValidEntityKeyPredicate.INSTANCE.evaluate(examTurnId)) {
			turn = new ExamTurn();
			if (null != stdTypeId) {
				StudentType stdType = (StudentType) utilService.get(StudentType.class,
						stdTypeId);
				turn.setStdType(stdType);
			}
		} else {
			turn = (ExamTurn) utilService.load(ExamTurn.class, examTurnId);
		}
		addEntity(request, turn);
		return forward(request);
	}

}
