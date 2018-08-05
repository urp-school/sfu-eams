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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * Zhouqi               2007-12-29            Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.grade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.shufe.model.course.grade.GradeState;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * @author zhouqi
 */
public class GradeStateStatAction extends CalendarRestrictionSupportAction {
    
    /**
     * 成绩录入统计查询首页
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
        setCalendarDataRealm(request, hasStdTypeCollege);
        addCollection(request, "stdTypeList", getStdTypes(request));
        return forward(request);
    }
    
    /**
     * 成绩录入状态统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statusStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List gradeTypes = (List) utilService.search(buildhGradeTypeQuery());
        List results = new ArrayList();
        for (Iterator iter = gradeTypes.iterator(); iter.hasNext();) {
            GradeType gradeType = (GradeType) iter.next();
            // 统计未录入和录入一次的记录
            EntityQuery queryInput = new EntityQuery(GradeState.class, "gradeState");
            populateConditions(request, queryInput, "gradeState.teachTask.calendar.studentType.id");
            DataRealmUtils.addDataRealms(queryInput, new String[] {
                    "gradeState.teachTask.calendar.studentType.id",
                    "gradeState.teachTask.arrangeInfo.teachDepart.id" }, getDataRealmsWith(getLong(
                    request, "gradeState.teachTask.calendar.studentType.id"), request));
            List groupBy = new ArrayList();
            groupBy.add("bitand(gradeState.inputStatus, " + gradeType.getMark().intValue() + ")");
            queryInput.setGroups(groupBy);
            queryInput.setSelect("count(*)");
            queryInput.addOrder(OrderUtils.parser("bitand(gradeState.inputStatus, "
                    + gradeType.getMark().intValue() + ")"));
            List list1 = (List) utilService.search(queryInput);
            Object[] obj = new Object[4];
            obj[0] = gradeType;
            if (list1.size() > 0) {
                obj[1] = list1.get(0);
                if (list1.size() > 1) {
                    obj[2] = list1.get(1);
                } else {
                    obj[2] = new Integer("0");
                }
            } else {
                obj[1] = new Integer("0");
                obj[2] = new Integer("0");
            }
            
            // 统计录入两次成绩的记录
            EntityQuery queryComfired = new EntityQuery(GradeState.class, "gradeState");
            populateConditions(request, queryComfired,
                    "gradeState.teachTask.calendar.studentType.id");
            DataRealmUtils.addDataRealms(queryComfired, new String[] {
                    "gradeState.teachTask.calendar.studentType.id",
                    "gradeState.teachTask.arrangeInfo.teachDepart.id" }, getDataRealmsWith(getLong(
                    request, "gradeState.teachTask.calendar.studentType.id"), request));
            queryComfired.add(new Condition("bitand(gradeState.confirmStatus, "
                    + gradeType.getMark().intValue() + ") > 0"));
            queryComfired.setSelect("count(*)");
            obj[3] = ((List) utilService.search(queryComfired)).get(0);
            
            results.add(obj);
        }
        addCollection(request, "results", results);
        return forward(request);
    }
    
    /**
     * 成绩类型查询
     * 
     * @return
     */
    protected EntityQuery buildhGradeTypeQuery() {
        EntityQuery query = new EntityQuery(GradeType.class, "gradeType");
        query.add(new Condition("gradeType.state = 1"));
        return query;
    }
    
    /**
     * 成绩发布统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward publishStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List gradeTypes = (List) utilService.search(buildhGradeTypeQuery());
        List results = new ArrayList();
        for (Iterator iter = gradeTypes.iterator(); iter.hasNext();) {
            GradeType gradeType = (GradeType) iter.next();
            Object[] obj = new Object[3];
            obj[0] = gradeType;
            // 统计成绩发布状态情况
            EntityQuery queryPublish = new EntityQuery(GradeState.class, "gradeState");
            populateConditions(request, queryPublish,
                    "gradeState.teachTask.calendar.studentType.id");
            DataRealmUtils.addDataRealms(queryPublish, new String[] {
                    "gradeState.teachTask.calendar.studentType.id",
                    "gradeState.teachTask.arrangeInfo.teachDepart.id" }, getDataRealmsWith(getLong(
                    request, "gradeState.teachTask.calendar.studentType.id"), request));
            List groupBy = new ArrayList();
            queryPublish.add(new Condition("bitand(gradeState.confirmStatus, "
                    + gradeType.getMark().intValue() + ") > 0"));
            groupBy.add("bitand(gradeState.publishStatus, " + gradeType.getMark().intValue() + ")");
            queryPublish.setGroups(groupBy);
            queryPublish.setSelect("count(*)");
            queryPublish.addOrder(OrderUtils.parser("bitand(gradeState.publishStatus, "
                    + gradeType.getMark().intValue() + ")"));
            List list2 = (List) utilService.search(queryPublish);
            if (list2.size() > 0) {
                obj[1] = list2.get(0);
                if (list2.size() > 1) {
                    obj[2] = list2.get(1);
                } else {
                    obj[2] = new Integer("0");
                }
            } else {
                obj[1] = new Integer("0");
                obj[2] = new Integer("0");
            }
            
            results.add(obj);
        }
        addCollection(request, "results", results);
        return forward(request);
    }
    
    /**
     * 成绩百分比统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward percentStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List groupByList = new ArrayList();
        
        EntityQuery query = new EntityQuery(GradeState.class, "gradeState");
        populateConditions(request, query, "gradeState.teachTask.calendar.studentType.id");
        DataRealmUtils
                .addDataRealms(query, new String[] {
                        "gradeState.teachTask.calendar.studentType.id",
                        "gradeState.teachTask.arrangeInfo.teachDepart.id" }, getDataRealmsWith(
                        getLong(request, "gradeState.teachTask.calendar.studentType.id"), request));
        groupByList.add("gradeState.percents");
        query.setGroups(groupByList);
        query.addOrder(OrderUtils.parser("gradeState.percents"));
        query.setSelect("gradeState.percents, count(*)");
        List queryResult = (List) utilService.search(query);
        List result = new ArrayList();
        int nullValue = 0;
        for (Iterator it = queryResult.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            Object[] newObj = new Object[2];
            if (obj[0] == null || StringUtils.isEmpty(obj[0].toString()) == false
                    && obj[0].toString().equals(";")) {
                nullValue += Integer.parseInt(obj[1].toString());
                continue;
            } else {
                String dealValue = String.valueOf(obj[0]);
                String[] settingValues = StringUtils.split(dealValue, ";");
                Object[] typePercent = new Object[settingValues.length * 2];
                for (int i = 0, j = 0; i < settingValues.length; i++) {
                    Object[] detailValues = StringUtils.split(settingValues[i], "=");
                    if (detailValues.length == 0) {
                        typePercent[j++] = null;
                        typePercent[j++] = null;
                    } else {
                        typePercent[j++] = baseCodeService.getCode(GradeType.class, new Long(String
                                .valueOf(detailValues[0])));
                        typePercent[j++] = new Float(String.valueOf(detailValues[1]));
                    }
                }
                newObj[0] = typePercent;
                newObj[1] = obj[1];
            }
            result.add(newObj);
        }
        if (nullValue != 0) {
            Object[] newObj = new Object[2];
            newObj[0] = new Object[] { "IsEmpty", null, null, null };
            newObj[1] = String.valueOf(nullValue);
            result.add(newObj);
        }
        addCollection(request, "results", result);
        
        return forward(request);
    }
    
    /**
     * @param gradeTypeId
     * @return
     */
    protected GradeType gettingGradeType(Long gradeTypeId) {
        return (GradeType) utilService.load(GradeType.class, gradeTypeId);
    }
}
