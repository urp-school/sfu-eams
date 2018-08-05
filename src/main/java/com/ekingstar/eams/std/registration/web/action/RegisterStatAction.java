//$Id: RegisterStatAction.java,v 1.6 2006/12/01 06:21:00 duanth Exp $
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
 * @author maomao
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * maomao               2007-07-11          Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.registration.web.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.std.registration.Register;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.service.util.stat.StatGroup;
import com.shufe.service.util.stat.StatHelper;
import com.shufe.util.DataRealmUtils;

/**
 * 注册统计界面响应类
 * 
 * @author chaostone
 * 
 */
public class RegisterStatAction extends RegisterSearchAction {
    
    /**
     * 统计院系学生注册率
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statDepart(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 统计学籍
        EntityQuery stdQuery = new EntityQuery(Student.class, "std");
        List conditions = QueryRequestSupport.extractConditions(request, Student.class, "std",
                "std.type.id");
        stdQuery.add(conditions);
        stdQuery.add(new Condition("std.inSchool = true and std.active = true"));
        stdQuery.groupBy("std.department.id,std.enrollYear");
        stdQuery.setSelect("std.department.id,std.enrollYear, -1,count(*)");
        DataRealmUtils.addDataRealms(stdQuery, new String[] { "std.type.id", "std.department.id" },
                getDataRealmsWith(getLong(request, "std.type.id"), request));
        Collection stdCounts = utilService.search(stdQuery);
        
        // 统计注册
        EntityQuery regQuery = new EntityQuery(Register.class, "register");
        regQuery.add(new Condition(
                "register.calendar is null or register.calendar.id=(:calendarId)", getLong(request,
                        "register.calendar.id")));
        for (Iterator it = conditions.iterator(); it.hasNext();) {
            Condition condition = (Condition) it.next();
            condition.setContent("register." + condition);
        }
        regQuery.add(conditions);
        regQuery.setSelect("register.std.department.id,register.std.enrollYear,count(*)");
        // 支持学生大类查找
        DataRealmUtils.addDataRealms(regQuery, new String[] { "register.std.type.id",
                "register.std.department.id" }, getDataRealmsWith(getLong(request, "std.type.id"),
                request));
        // 添加学生学籍有效,并且学籍有效的查询条件
        regQuery.add(new Condition("register.std.inSchool = true and register.std.active = true"));
        regQuery.groupBy("register.std.department.id,register.std.enrollYear");
        List datas = (List) utilService.search(regQuery);
        Map regCountMap = new HashMap();
        for (Iterator it = datas.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            regCountMap.put(obj[0] + "_" + obj[1], obj[2]);
        }
        for (Iterator it = stdCounts.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            obj[2] = regCountMap.get(obj[0] + "_" + obj[1]);
            if (null == obj[2]) {
                obj[2] = new Long(0);
            }
        }
        
        // 分组
        new StatHelper(utilService).replaceIdWith(stdCounts, new Class[] { Department.class });
        request.setAttribute("departStat", new StatGroup(null, StatGroup.buildStatGroups(
                (List) stdCounts, 2)));
        return forward(request);
    }
}
