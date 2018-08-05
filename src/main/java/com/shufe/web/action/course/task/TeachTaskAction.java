//$Id: TeachTaskAction.java,v 1.52 2007/01/15 01:03:44 duanth Exp $
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
 * chaostone            2005-10-25          Created
 * zq                   2007-09-18          修改或替换了本Action中的所有info()方法 
 * zq                   2007-10-17          在edit()方法中，添加了“授课语言类型”
 *                                          和修改了更新了相关的初始化列表
 ********************************************************************************/

package com.shufe.web.action.course.task;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.course.task.TeachTask;

public class TeachTaskAction extends TeachTaskCollegeAction {
    
    /**
     * 对班级重命名(一般不用)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward renameTeachClasses(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "calendar.id");
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery.add(new Condition("task.calendar.id=" + calendarId));
        Collection tasks = utilService.search(entityQuery);
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            task.getTeachClass().reNameByClass();
        }
        utilService.saveOrUpdate(tasks);
        return null;
    }
    
    protected ActionForward forward(HttpServletRequest request) {
        String method = get(request, "method");
        if (null == method || StringUtils.equals("index", method)
                || StringUtils.equals("search", method)) {
            return super.forward(request);
        } else {
            if (method.equals("edit")) {
                method = "form";
            } else if (method.equals("impactReview")) {
                method = "impactReview";
            }
            return forward(request, "../teachTaskCollege/" + method);
        }
    }
}
