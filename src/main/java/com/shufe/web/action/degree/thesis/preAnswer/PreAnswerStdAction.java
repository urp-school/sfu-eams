//$Id: PreAnswerStdAction.java,v 1.3 2007/01/26 10:02:13 cwx Exp $
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

import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.eams.system.basecode.state.Degree;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.security.User;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.answer.AnswerTeam;
import com.shufe.model.degree.thesis.answer.PreAnswer;
import com.shufe.model.std.Student;
import com.shufe.service.degree.thesis.ThesisManageService;
import com.shufe.service.degree.thesis.preAnswer.PreAnswerService;
import com.shufe.service.std.StudentService;
import com.shufe.web.action.common.RestrictionSupportAction;

public class PreAnswerStdAction extends RestrictionSupportAction {

	private StudentService studentService;
	private ThesisManageService thesisManageService;
	private PreAnswerService preAnswerService;
	
	
	/**
	 * 学生预答辩申请表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward answer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Student student = getStudentFromSession(request.getSession());
		StudentType stdType = student.getType();
		// 首先检查他是否是博士 只有博士才需要参加预答辩
		if (!Degree.DOCTOR.equals(stdType.getDegree().getId())) {
			request.setAttribute("reason", "error.preAnswer.notDoctor");
			return forward(request, "error");
		}
		ThesisManage thesisManage = null;
		List thesisManages = utilService.load(ThesisManage.class,
				"student.id", student.getId());
		if (thesisManages.size() > 0) {
			thesisManage = (ThesisManage) thesisManages.get(0);
		}
		if (null == thesisManage) {
			request.setAttribute("reason", "field.answer.noThesisTopicOpen");
			request.setAttribute("flag", "topicOpen");
			return forward(request, "error");
		}
		request.setAttribute("topicOpen", thesisManage.getTopicOpen());
		request.setAttribute("preAnswer", thesisManage.getPreAnswer());
		request.setAttribute("student", student);
		return forward(request, "stdAnswer");
	}
	
	/**
	 * 学生申请论文预答辩
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward apply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		Student student = getStudentFromSession(request.getSession());
		ThesisManage thesisManage = null;
		List thesisManages = utilService.load(ThesisManage.class,
				"student.id", student.getId());
		if (thesisManages.size() > 0) {
			thesisManage = (ThesisManage) thesisManages.get(0);
		}
		String applyNum = request.getParameter("applyNum");
		PreAnswer preAnswer = new PreAnswer();
		preAnswer.setThesisManage(thesisManage);
		preAnswer.setAnswerNum(Integer.valueOf(applyNum));
		preAnswer.setFinishOn(new Date(System.currentTimeMillis()));
		utilService.saveOrUpdate(preAnswer);
		return redirect(request, "answer", "info.action.success");
	}
	
	
	
	/**
	 * 确认预答辩　信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @deprecated
	 * @throws Throwable
	 */
	public ActionForward affirmPreAnswer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		Long preAnswerId = getLong(request,"preAnswerId");
		PreAnswer preAnwer =(PreAnswer)utilService.get(PreAnswer.class, preAnswerId);
		preAnwer.setFinishOn(new Date(System.currentTimeMillis()));
		utilService.saveOrUpdate(preAnwer);
		return redirect(request, "answer", "field.preAnswer.affirmSuccess");
	}
	/**
	 * 打印预览
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward previewPrint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String preAnswerNum = request.getParameter("preAnswerNum");
		String fontSize = request.getParameter("fontSize");
		request.setAttribute("fontSize", fontSize);
		User std = getUser(request.getSession());
		String stdCode = std.getName();
		Student student = studentService.getStd(stdCode);
		request.setAttribute("student", student);
		if (StringUtils.isNotBlank(preAnswerNum)
				&& preAnswerNum.indexOf(",") != -1) {
			ThesisManage thesisManage = thesisManageService
					.getThesisManage(student);
			if (null != thesisManage && null != thesisManage.getTopicOpen()) {
				request.setAttribute("topicOpen", thesisManage.getTopicOpen());
			}
			String[] temNum = StringUtils.split(preAnswerNum, ",");
			Integer num = Integer.valueOf(temNum[1]);
			num = new Integer(num.intValue() + 1);
			request.setAttribute("num", num);
		} else {
			PreAnswer preAnswer = preAnswerService.getPreAnswerByNum(student,
					Integer.valueOf(preAnswerNum));
			Set answerTeamSet = preAnswer.getAnswerTeamSet();
			for (Iterator iter = answerTeamSet.iterator(); iter.hasNext();) {
				AnswerTeam team = (AnswerTeam) iter.next();
				if (null != team.getIsChairMan()
						&& team.getIsChairMan().booleanValue()) {
					request.setAttribute("answerChairMan", team);
					answerTeamSet.remove(team);
					break;
				}
			}
			request.setAttribute("answerTeamSet", answerTeamSet);
			request.setAttribute("preAnswer", preAnswer);
			request.setAttribute("topicOpen", preAnswer.getThesisManage()
					.getTopicOpen());
			request.setAttribute("num", preAnswer.getAnswerNum());
		}
		return forward(request);
	}

	/**
	 * @param studentService The studentService to set.
	 */
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	/**
	 * @param thesisManageService The thesisManageService to set.
	 */
	public void setThesisManageService(ThesisManageService thesisManageService) {
		this.thesisManageService = thesisManageService;
	}

	/**
	 * @param preAnswerService The preAnswerService to set.
	 */
	public void setPreAnswerService(PreAnswerService preAnswerService) {
		this.preAnswerService = preAnswerService;
	}
}
