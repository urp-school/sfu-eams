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
 * chaostone             2006-9-18            Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.notice;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.notice.ManagerNotice;
import com.shufe.model.system.notice.Notice;
import com.shufe.model.system.notice.StudentNotice;
import com.shufe.model.system.notice.TeacherNotice;

/**
 * 系统公告
 * 
 * @author chaostone
 */
public class NoticeAction extends NoticeSearchAction {

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String kind = request.getParameter("kind");
		if (null == kind) {
			kind = "manager";
		}
		Long noticeId = getLong(request, "notice.id");
		Notice notice = null;
		if (!ValidEntityKeyPredicate.INSTANCE.evaluate(noticeId)) {
			if (kind.equals("std")) {
				notice = new StudentNotice();
				addCollection(request, "departments", getDeparts(request));
				addCollection(request, "studentTypes", getStdTypes(request));
			} else {
				notice = new Notice();
			}
		} else {
			notice = (Notice) utilService.get(Notice.class, noticeId);
			if (kind.equals("std")) {
				addCollection(request, "departments", CollectionUtils.subtract(getDeparts(request),
						((StudentNotice) notice).getDeparts()));
				addCollection(request, "studentTypes", CollectionUtils.subtract(
						getStdTypes(request), ((StudentNotice) notice).getStudentTypes()));
			}
		}
		request.setAttribute("notice", notice);

		return forward(request);
	}

	public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long noticeId = getLong(request, "notice.id");
		if (!ValidEntityKeyPredicate.INSTANCE.evaluate(noticeId)) {
			return forwardError(mapping, request, "error.parameters.illegal");
		} else {
			Notice notice = (Notice) utilService.get(Notice.class, noticeId);
			request.setAttribute("notice", notice);
			return forward(request);
		}
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String noticeIds = request.getParameter("noticeIds");
		List notices = utilService.load(Notice.class, "id", SeqStringUtil
				.transformToLong(noticeIds));
		for (Iterator iter = notices.iterator(); iter.hasNext();) {
			Notice notice = (Notice) iter.next();
			utilService.remove(notice);
		}
		return redirect(request, "search", "info.delete.success", "&kind="
				+ request.getParameter("kind"));

	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Notice notice = (Notice) populate(request, Notice.class);
		String kind = request.getParameter("kind");
		List stdTypes = null;
		List departs = null;
		if (kind.equals("std")) {
			Long[] stdTypeIds = SeqStringUtil.transformToLong(request.getParameter("stdTypeIds"));
			stdTypes = utilService.load(StudentType.class, "id", stdTypeIds);
			Long[] departIds = SeqStringUtil.transformToLong(request.getParameter("departIds"));
			departs = utilService.load(Department.class, "id", departIds);
		}
		if (notice.isPO()) {
			Notice saved = (Notice) utilService.get(Notice.class, notice.getId());
			saved.getContent().setContent(notice.getContent().getContent());
			saved.setTitle(notice.getTitle());
			saved.setModifyAt(new Date(System.currentTimeMillis()));
            saved.setStartDate(notice.getStartDate());
            saved.setFinishDate(notice.getFinishDate());
			saved.setPublisher(getUser(request.getSession()));
            saved.setIsUp(notice.getIsUp());
			notice = saved;
		} else {
			Notice newNotice = null;
			if (kind.equals("std")) {
				newNotice = new StudentNotice();
			} else if (kind.equals("teacher")) {
				newNotice = new TeacherNotice();
			} else if (kind.equals("manager")) {
				newNotice = new ManagerNotice();
			} else {
				throw new RuntimeException("unspported notice kind");
			}
			newNotice.setContent(notice.getContent());
			newNotice.setTitle(notice.getTitle());
            newNotice.setStartDate(notice.getStartDate());
            newNotice.setFinishDate(notice.getFinishDate());
			newNotice.setModifyAt(new Date(System.currentTimeMillis()));
			newNotice.setPublisher(getUser(request.getSession()));
            newNotice.setIsUp(notice.getIsUp());
			notice = newNotice;

		}
		utilService.saveOrUpdate(notice);
		if (kind.equals("std")) {
			StudentNotice stdNotice = (StudentNotice) notice;
			stdNotice.getDeparts().clear();
			stdNotice.getDeparts().addAll(departs);
			stdNotice.getStudentTypes().clear();
			stdNotice.getStudentTypes().addAll(stdTypes);
			utilService.saveOrUpdate(stdNotice);
		}
		return redirect(request, "search", "info.save.success", "&kind=" + kind);
	}

}
