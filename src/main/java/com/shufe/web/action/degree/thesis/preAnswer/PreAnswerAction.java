//$Id: PreAnswerAction.java,v 1.1 2007/01/24 14:16:43 cwx Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-10-24         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.thesis.preAnswer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.answer.AnswerTeam;
import com.shufe.model.degree.thesis.answer.PreAnswer;
import com.shufe.model.std.Student;
import com.shufe.service.degree.thesis.preAnswer.PreAnswerService;
import com.shufe.util.RequestUtil;

public class PreAnswerAction extends PreAnswerSearchAction {

	private PreAnswerService preAnswerService;

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setDataRealm(request, hasStdTypeCollege);
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
	public ActionForward stdList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String hasApply = request.getParameter("hasApply");
		if ("true".equals(hasApply)) {
			addCollection(request, "preAnswerPage", utilService.search(buildPreAnswerQuery(request)));
		} else {
			Student student = (Student) RequestUtil.populate(request, Student.class, "student");
			String stdTypeIdSeq = getStdTypeIdSeq(request);
			String departIdSeq = getDepartmentIdSeq(request);
			ThesisManage thesisManage = new ThesisManage();
			thesisManage.setStudent(student);
			Pagination preAnswerPage = preAnswerService.getPaginationOfNoApply(thesisManage,
					departIdSeq, stdTypeIdSeq, getPageNo(request), getPageSize(request));
			addOldPage(request, "preAnswerPage", preAnswerPage);
		}
		request.setAttribute("hasApplyFlag", hasApply);
		request.setAttribute("flag", "admin");
		return forward(request);
	}

	/**
	 * 保存修改结果
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
		return redirect(request, "index", "field.answer.tutor.success");
	}

	/**
	 * 批量更新时间和地点
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String time = request.getParameter("time");
		String adress = request.getParameter("address");
		String flag = request.getParameter("flag");
		String preAnswerIdSeq = "";
		if ("all".equals(flag)) {
			Student student = (Student) RequestUtil.populate(request, Student.class, "student");
			ThesisManage thesisManage = new ThesisManage();
			thesisManage.setStudent(student);
			PreAnswer preAnswer = (PreAnswer) populate(request, PreAnswer.class);
			preAnswer.setThesisManage(thesisManage);
			List preAnswerIdList = preAnswerService.getPropertyList(preAnswer,
					getDepartmentIdSeq(request), getStdTypeIdSeq(request), "id");
			for (Iterator iter = preAnswerIdList.iterator(); iter.hasNext();) {
				Long element = (Long) iter.next();
				if (iter.hasNext()) {
					preAnswerIdSeq += element + ",";
				} else {
					preAnswerIdSeq += element;
				}
			}
		} else if ("select".equals(flag)) {
			preAnswerIdSeq = request.getParameter("answerIdSeq");
		}
		preAnswerService.batchUpdateTimeAndAddress(preAnswerIdSeq, time, adress);
		String paramter = request.getParameter("parameterss");
		return redirect(request, "stdList", "field.answer.tutor.success", paramter);
	}

	/**
	 * 填写论文预答辩评阅书
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @deprecated
	 * @throws Exception
	 */
	public ActionForward doWriteBook(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getParameter("methodFlag");
		String preAnswerId = request.getParameter("preAnswerId");
		PreAnswer preAnswer = (PreAnswer) utilService.get(PreAnswer.class, Long
				.valueOf(preAnswerId));
		if ("book".equals(method)) {
			Set answerSet = preAnswer.getAnswerTeamSet();
			for (Iterator iter = answerSet.iterator(); iter.hasNext();) {
				AnswerTeam element = (AnswerTeam) iter.next();
				if (Boolean.TRUE.equals(element.getIsChairMan())) {
					request.setAttribute("answerChairMan", element);
					answerSet.remove(element);
					break;
				}
			}
			String parameters = request.getParameter("parameters");
			request.setAttribute("answerTeamSet", answerSet);
			request.setAttribute("student", preAnswer.getThesisManage().getStudent());
			request.setAttribute("thesisTopicOpen", preAnswer.getThesisManage().getTopicOpen());
			request.setAttribute("preAnswer", preAnswer);
			request.setAttribute("flag", "admin");
			request.setAttribute("preAnswerId", preAnswerId);
			request.setAttribute("parameters", parameters);
			return forward(request, "writeAnswerBook");
		}
		Map valueMap = RequestUtils.getParams(request, "preAnswer");
		ThesisManage thesisManage = preAnswer.getThesisManage();
		EntityUtils.populate(valueMap, preAnswer);
		preAnswer.setThesisManage(thesisManage);
		Set answerTeamSet = new HashSet();
		int temp = 5;// 从零开始加入5个答辩小组成员.如果现有的保存 如果新添加的更新.
		for (int i = 0; i < temp; i++) {
			AnswerTeam answerTeam = (AnswerTeam) RequestUtil.populate(request, AnswerTeam.class,
					"answerTeam" + i);
			if (i == 0) {
				answerTeam.setIsChairMan(new Boolean(true));
			}
			if (answerTeam.checkedId()) {
				utilService.saveOrUpdate(answerTeam);
			}
			answerTeam.getThesisAnswers().add(preAnswer);
			answerTeamSet.add(answerTeam);
		}
		preAnswer.getAnswerTeamSet().clear();
		preAnswer.getAnswerTeamSet().addAll(answerTeamSet);
		utilService.saveOrUpdate(preAnswer);
		String parameters = request.getParameter("parameters");
		return redirect(request, "stdList", "field.answer.tutor.success", parameters);
	}

	/**
	 * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
	 */
	protected Collection getExportDatas(HttpServletRequest request) {
		String isApply = request.getParameter("isApply");
		String stdTypeIdSeq = getStdTypeIdSeq(request);
		String departIdSeq = getDepartmentIdSeq(request);
		Student student = (Student) RequestUtil.populate(request, Student.class, "student");
		ThesisManage thesisManage = new ThesisManage();
		thesisManage.setStudent(student);
		if ("true".equals(isApply)) {
			EntityQuery query = buildPreAnswerQuery(request);
			query.setLimit(null);
			return utilService.search(query);
		} else {
			return preAnswerService.getStdsOfNoApply(thesisManage, departIdSeq, stdTypeIdSeq);
		}
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward configPass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean isPassed = getBoolean(request, "isPassed");
		String preAnswerIds = request.getParameter("preAnswerIds");
		List preAnswers = utilService.load(PreAnswer.class, "id", SeqStringUtil
				.transformToLong(preAnswerIds));
		for (Iterator iter = preAnswers.iterator(); iter.hasNext();) {
			PreAnswer element = (PreAnswer) iter.next();
			element.setIsPassed(isPassed);
		}
		utilService.saveOrUpdate(preAnswers);
		return redirect(request, "stdList", "info.action.success");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancleAffirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preAnswerIds = request.getParameter("preAnswerId");
		List preAnswers = utilService.load(PreAnswer.class, "id", SeqStringUtil
				.transformToLong(preAnswerIds));
		for (Iterator iter = preAnswers.iterator(); iter.hasNext();) {
			PreAnswer element = (PreAnswer) iter.next();
			element.setFinishOn(null);
		}
		utilService.saveOrUpdate(preAnswers);
		return redirect(request, "stdList", "info.action.success");
	}

	/**
	 * @param preAnswerService
	 *            The preAnswerService to set.
	 */
	public void setPreAnswerService(PreAnswerService preAnswerService) {
		this.preAnswerService = preAnswerService;
	}

}
