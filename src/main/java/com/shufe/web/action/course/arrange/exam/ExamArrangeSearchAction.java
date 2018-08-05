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
 * chaostone            2006-11-12          Created
 * zq                   2007-10-16          在examActivityList()方法中，更正了大
 *                                          类查询；
 ********************************************************************************/

package com.shufe.web.action.course.arrange.exam;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.eams.system.basecode.industry.ExamType;

/**
 * 设置监考老师
 * 
 * @author chaostone
 */
public class ExamArrangeSearchAction extends ExamAction {
    
    /**
     * 安排考试主界面
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
        return super.index(mapping, form, request, response);
    }
    
    /**
     * 已经安排的教学任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward arrangeList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long examTypeId = getLong(request, "examType.id");
        addCollection(request, "tasks", getTasks(request, Boolean.TRUE, null));
        request.setAttribute("examType", new ExamType(examTypeId));
        return forward(request);
    }
    
    /**
     * 没有进行安排的教学任务
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
        addCollection(request, "tasks", getTasks(request, Boolean.FALSE, null));
        return forward(request);
        
    }
    
    /**
     * 查询排考结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward examActivityList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long examTypeId = getLong(request, "examType.id");
        addCollection(request, "tasks", getTasks(request, Boolean.TRUE, null));
        request.setAttribute("examType", new ExamType(examTypeId));
        return forward(request);
    }
    
    /**
     * 开课院系权限限制范围
     * 
     * @param stdTypes
     * @param departs
     * @return
     */
    protected Condition getAuthorityCondition(Collection stdTypes, Collection departs) {
        return new Condition(
                "task.teachClass.stdType in (:stdTypes) and task.arrangeInfo.teachDepart in(:teachDeparts)",
                stdTypes, departs);
    }
}
