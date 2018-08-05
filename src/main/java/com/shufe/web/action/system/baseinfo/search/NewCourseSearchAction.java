package com.shufe.web.action.system.baseinfo.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping; //import org.beanfuse.lang.SeqStringUtil;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.NewCourse;
import com.shufe.web.action.system.baseinfo.BaseInfoAction;

/**
 * 课程信息管理的action.包括课程信息的增改查.
 * 
 * 
 */

public class NewCourseSearchAction extends BaseInfoAction {

	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.err.println("readonlylist");
		EntityQuery entityQuery = new EntityQuery(NewCourse.class, "newCourse");
		// QueryRequestSupport.populateConditions(request, entityQuery);
		entityQuery.setLimit(getPageLimit(request));
		//entityQuery.addOrder(OrderUtils.parser("newCourse.ordernum asc"));		
		entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		// newCourseQuery.add(new Condition("newCourse.priority=1"));
		addCollection(request, "newCourses", utilService.search(entityQuery));
		return forward(request);
	}

	// 查询部分
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		addCollection(request, "newCourses", utilService
				.search(baseInfoSearchHelper.buildNewCourseQuery(request)));
		return forward(request);
	}
}
