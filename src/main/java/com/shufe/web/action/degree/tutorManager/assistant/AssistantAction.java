//$Id: StudyAwardAction.java,v 1.1 2007-3-7 下午02:26:54 chaostone Exp $
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
 * zq                   2007-09-17          重载了buildQuery()方法，详见下面的 FIXME
 *                                          处
 * zq                   2007-10-24          修改了saveAndForwad()中，当错误时提示
 *                                          的信息：error.degree.noTutor
 ********************************************************************************/

package com.shufe.web.action.degree.tutorManager.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.degree.tutorManager.Assistant;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

/**
 * 助教管理
 * 
 * @author 塞外狂人
 * 
 */
public class AssistantAction extends RestrictionExampleTemplateAction {

    protected void indexSetting(HttpServletRequest request) throws Exception {
        setDataRealm(request, hasStdTypeCollege);
    }

    protected ActionForward saveAndForwad(HttpServletRequest request, Entity entity)
            throws Exception {
        Assistant assistant = (Assistant) entity;
        String tutorNo = request.getParameter("tutorNo");
        List teachers = (List) utilService.load(Teacher.class, "code", tutorNo);
        if (teachers.isEmpty())
            return forward(request, new Action(this, "edit"), "error.degree.noTutor");
        assistant.setTutor((Teacher) teachers.get(0));
        return super.saveAndForwad(request, entity);
    }

    /**
     * FIXME 重载了此方法
     * 
     * @see com.shufe.web.action.common.ExampleTemplateAction#buildQuery(javax.servlet.http.HttpServletRequest)
     */
    protected EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(Assistant.class, "assistant");
        // FIXME 增加了下面方法的参数
        populateConditions(request, query, "assistant.std.type.id");
        // FIXME 添加了下面的语句
        DataRealmUtils.addDataRealms(query, new String[] { "assistant.std.type.id",
                "assistant.std.department.id" }, getDataRealmsWith(getLong(request,
                "assistant.std.type.id"), request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        query.setLimit(getPageLimit(request));
        return query;
    }
}
