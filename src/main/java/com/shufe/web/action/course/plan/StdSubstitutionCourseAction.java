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
 * chaostone             2006-3-31            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.plan;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.eams.course.program.model.DefaultSubstitutionCourse;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

/**
 * 可代替课程的维护响应类
 * 
 * @author James
 * 
 */
public class StdSubstitutionCourseAction extends RestrictionExampleTemplateAction{

	protected ActionForward saveAndForwad(HttpServletRequest request, Entity entity) throws Exception {
		
		DefaultSubstitutionCourse sc = (DefaultSubstitutionCourse)entity;
		
		if((sc.getId())!=null){
			sc.setModifyAt(new Date(System.currentTimeMillis()));
		}else {
			sc.setCreateAt(new Date(System.currentTimeMillis()));
		}
		utilService.saveOrUpdate(sc);
		return redirect(request, "search", "info.save.success");
	}
}
