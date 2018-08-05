//$Id: RequirementSearchAction.java,v 1.1 2007-3-12 下午09:03:02 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-12         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.textbook;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.PropertyExtractor;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.course.textbook.BookRequirement;
import com.shufe.service.course.task.TeachTaskPropertyExtractor;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public class RequirementSearchAction extends CalendarRestrictionSupportAction {
    
    public EntityQuery buildRequireQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(BookRequirement.class, "require");
        QueryRequestSupport.populateConditions(request, query);
        String checkStatus = get(request, "checkStatus");
        if (StringUtils.isNotEmpty(checkStatus)) {
            query.add(new Condition("require.checked "
                    + ("null".equals(checkStatus) ? "is null" : ("= " + checkStatus))));
        }
        DataRealmUtils.addDataRealms(
                query,
                new String[] { "require.task.teachClass.stdType.id", "require.task.arrangeInfo.teachDepart.id" },
                getDataRealms(request));
        String teacherName = request.getParameter("arrangeInfo.teacherName");
        if (StringUtils.isNotBlank(teacherName)) {
            query.join("require.task.arrangeInfo.teachers", "teacher");
            query.add(Condition.like("teacher.name", teacherName));
        }
        String adminClassName = request.getParameter("teachClass.adminClassName");
        if (StringUtils.isNotBlank(adminClassName)) {
            query.join("require.task.teachClass.adminClasses", "adminClass");
            query.add(Condition.like("adminClass.name", adminClassName));
        }
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        query.setLimit(getPageLimit(request));
        return query;
    }
    
    /**
     * 组建未指定教材条件
     * 
     * @param request
     * @return
     */
    protected EntityQuery buildUnrequirementQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(TeachTask.class, "task");
        QueryRequestSupport.populateConditions(request, query);
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        query.add(new Condition("task.teachClass.stdType in (:stdTypes)", getStdTypesOf(stdTypeId,
                request)));
        DataRealmUtils.addDataRealms(query,
                new String[] { "task.teachClass.stdType.id", "task.arrangeInfo.teachDepart.id" },
                getDataRealms(request));
        query.add(new Condition(" not exists (from BookRequirement as require where require.task=task)"));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return query;
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        String searchWhat = get(request, "searchWhat");
        EntityQuery entityQuery = null;
        if (StringUtils.isNotEmpty(searchWhat) && searchWhat.equals("taskList")) {
            entityQuery = buildUnrequirementQuery(request);
        } else {
            entityQuery = buildRequireQuery(request);
        }
        entityQuery.setLimit(null);
        return utilService.search(entityQuery);
    }
    
    protected PropertyExtractor getPropertyExtractor(HttpServletRequest request) {
        String searchWhat = get(request, "searchWhat");
        if (StringUtils.isNotEmpty(searchWhat) && searchWhat.equals("taskList")) {
            return new TeachTaskPropertyExtractor(getLocale(request), getResources(request), utilService);
        } else {
            return super.getPropertyExtractor(request);
        }
        
    }
    
}
