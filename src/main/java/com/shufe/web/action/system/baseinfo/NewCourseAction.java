package com.shufe.web.action.system.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.NewCourse;
import com.shufe.web.action.system.baseinfo.search.NewCourseSearchAction;

/**
 * 课程信息管理的action.包括课程信息的增改查.
 * 
 * 
 */

public class NewCourseAction extends NewCourseSearchAction {

	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		addCollection(request, "course", baseInfoService
				.getBaseInfos(Course.class));
		return forward(request);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long id = getLong(request, "newCourseId");
		if (id != null) {
			NewCourse nCourse = (NewCourse) utilService
					.get(NewCourse.class, id);
			request.setAttribute("newCourse", nCourse);
		}
		return forward(request);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String newCourseCode = get(request, "newCourse.course.code");// 课程号
		Long newCourseId = getLong(request, "newCourse.id");// newCourseId
		NewCourse nc = null;
		Map m = null;
		Course c = null;
		try {
			m = new HashMap();
			m.put("code", newCourseCode);
			List l = utilService.load(Course.class, m);
			if (!l.isEmpty()) {// 说明有对应课程
				for (int i = 0; i < l.size(); i++) {
					c = (Course) l.get(i);
					if (newCourseId == null) {// 为新增操作
						nc = new NewCourse();
						Boolean b = Boolean.valueOf(utilService.exist(
								NewCourse.class, "course.code", newCourseCode));
						if (b.booleanValue()) {// 新开课表存在相同课程
							return forward(request, new Action("", "edit"),
									"error.code.existed");
						} else {
						}// 可以新增新开课程
					} else {// 为修改操作
						nc = (NewCourse) utilService.get(NewCourse.class,
								newCourseId);
					}
					break;
				}
				nc.setCourse(c);
				nc.setOrdernum(getInteger(request, "newCourse.ordernum"));
				nc.setPriority(getInteger(request, "newCourse.priority"));
				utilService.saveOrUpdate(nc);
			} else {// 没有找到对应课程号
				return forward(request, new Action("", "edit"),
						"error.code.notfind");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return forward(mapping, request, "error.occurred", "error");
		}

		return redirect(request, "search", "info.save.success");
	}

}
