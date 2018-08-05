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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.web.action.std.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.shufe.model.std.Student;
import com.shufe.service.std.statResult.BaseValueBean;
import com.shufe.service.std.statResult.StudentGenderCountCollection;
import com.shufe.util.DataRealmLimit;
import com.shufe.util.DataRealmUtils;

/**
 * 学生人数统计类
 */
public class CountStatistics extends GeneralStudentStatisticsAction {
    
    /**
     * 学生人数统计表菜单页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward studentCountStatForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        DataRealmLimit dataRealmLimit = getDataRealmLimit(request);
        initStudentStatisticsBar(dataRealmLimit);
        Results.addObject("studentStateList", utilService.loadAll(StudentState.class));
        return forward(request);
    }
    
    /**
     * 学生人数统计表结果页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward studentCountStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        EntityQuery entityQuery = new EntityQuery(Student.class, "student");
        QueryRequestSupport.populateConditions(request, entityQuery,
                "student.firstMajor.id,student.firstAspect.id,student.type.id");
        addIdCondition(request, entityQuery, "student.firstMajor.id", "firstMajor.id");
        addIdCondition(request, entityQuery, "student.firstAspect.id", "firstAspect.id");
        DataRealmUtils.addDataRealms(entityQuery, new String[] { "student.type.id",
                "student.department.id" }, getDataRealmsWith(getLong(request, "student.type.id"),
                request));
        
        String adminClasssId1String = request.getParameter("adminClasssId1");
        String adminClasssId2String = request.getParameter("adminClasssId2");
        if (StringUtils.isNotBlank(adminClasssId1String)
                || StringUtils.isNotBlank(adminClasssId2String)) {
            entityQuery.join("student.adminClasses", "adminClass");
            List adminClassIds = new ArrayList();
            if (StringUtils.isNotBlank(adminClasssId1String)
                    && StringUtils.isNotBlank(adminClasssId2String)) {
                entityQuery.add(new Condition("exists (from AdminClass class"
                        + adminClasssId1String + " join class" + adminClasssId1String
                        + ".students std where std.id=student.id and class" + adminClasssId1String
                        + ".id = :classId" + adminClasssId1String + ")"
                        + " and exists (from AdminClass class" + adminClasssId2String
                        + " join class" + adminClasssId2String
                        + ".students std where std.id=student.id and class" + adminClasssId2String
                        + ".id = :classId" + adminClasssId2String + ")", new Long(
                        adminClasssId1String), new Long(adminClasssId2String)));
            }
            if (StringUtils.isNotBlank(adminClasssId1String)) {
                adminClassIds.add(new Long(adminClasssId1String));
            }
            if (StringUtils.isNotBlank(adminClasssId2String)) {
                adminClassIds.add(new Long(adminClasssId2String));
            }
            entityQuery.add(new Condition("adminClass.id in (:adminClassIds)", adminClassIds));
        }
        addGraduateAuditStatus(request, entityQuery, "graduateAuditStatus");
        addGraduateAuditStatus(request, entityQuery, "secondGraduateAuditStatus");
        String[] enrollTurnArray = StringUtils.split(request.getParameter("enrollTurns"), ",");
        String enrollYearCondition = "";
        List enrollYearList = new ArrayList();
        if (!ArrayUtils.isEmpty(enrollTurnArray)) {
            for (int i = 0; i < enrollTurnArray.length; i++) {
                String enrollTurn = enrollTurnArray[i];
                enrollYearCondition += "student.enrollYear like (:enrollTurn" + i + ")";
                if (i != enrollTurnArray.length - 1) {
                    enrollYearCondition += " or ";
                }
                enrollYearList.add("%" + enrollTurn + "%");
                // entityQuery.add(new Condition("student.enrollYear like (:enrollTurn"+i+")"
                // ,enrollTurn+"%"));
            }
            Condition condition = new Condition(enrollYearCondition);
            condition.setValues(enrollYearList);
            entityQuery.add(condition);
        }
        
        // TODO
        List propertyList = new ArrayList();
        propertyList.add(new BaseValueBean(null, "学生类别", "studentType", "student_type"));
        propertyList.add(new BaseValueBean(null, "院系", "department", "student_department"));
        propertyList.add(new BaseValueBean(null, "专业", "firstMajor", "firstMajor"));
        propertyList.add(new BaseValueBean(null, "专业方向", "firstAspect", "firstAspect"));
        propertyList.add(new BaseValueBean(null, "所在年级", "enrollYear", "student_enrollYear"));
        propertyList.add(new BaseValueBean(null, "性别", "gender", "student_basicInfo_gender"));
        List groups = new ArrayList();
        for (Iterator iter = propertyList.iterator(); iter.hasNext();) {
            BaseValueBean element = (BaseValueBean) iter.next();
            groups.add(StringUtils.replace(element.getDataName(), "_", "."));
        }
        entityQuery.setGroups(groups);
        /*
         * String propertyList =
         * "student.type,student.department,student.firstMajor,student.firstAspect,student.enrollYear,student.basicInfo.gender";
         * List groups = new ArrayList(); String[] propertyArray = StringUtils.split(propertyList,
         * ","); if(!ArrayUtils.isEmpty(propertyArray)){ for (int i = 0; i < propertyArray.length;
         * i++) { groups.add(propertyArray[i]); } }
         */
        // entityQuery.setGroups(groups);
        entityQuery
                .setSelect("select new map(student.type as student_type, student.department as student_department, firstMajor as firstMajor, firstAspect as firstAspect, student.enrollYear as student_enrollYear, student.basicInfo.gender as student_basicInfo_gender, count(*) as count)");
        entityQuery.join("left", "student.firstMajor", "firstMajor").join("left",
                "student.firstAspect", "firstAspect");
        Collection result = utilService.search(entityQuery);
        BaseValueBean allInfo = new BaseValueBean(new Long(0), "全部", "All", null);
        StudentGenderCountCollection countCollection = new StudentGenderCountCollection();
        countCollection.addAll(result);
        countCollection.setKey(allInfo);
        
        Set remPropertySet = new HashSet();
        
        for (Iterator iter = result.iterator(); iter.hasNext();) {
            Map element = (Map) iter.next();
            remPropertySet.add(element.get(((BaseValueBean) propertyList
                    .get(propertyList.size() - 2)).getDataName()));
        }
        List remPropertyList = new ArrayList(remPropertySet);
        Collections.sort(remPropertyList);
        
        /*
         * propertyList.remove(propertyList.size()-1);
         * remPropertyList.add(propertyList.get(propertyList.size()-1));
         * propertyList.remove(propertyList.size()-1);
         */

        request.setAttribute("countCollection", countCollection);
        request.setAttribute("propertyList", propertyList);
        request.setAttribute("remPropertyList", remPropertyList);
        request.setAttribute("currentDate", new GregorianCalendar().getTime());
        // request.setAttribute("remPropertyList",remPropertyList);
        
        return forward(request);
    }
    
    private void addIdCondition(HttpServletRequest request, EntityQuery entityQuery, String idName,
            String toName) {
        if (StringUtils.isNotBlank(request.getParameter(idName))) {
            entityQuery.add(new Condition(toName + " = (:" + StringUtils.replace(idName, ".", "_")
                    + ")", getLong(request, idName)));
        }
    }
    
    /**
     * 添加毕业审核结果条件
     * 
     * @param request
     * @param entityQuery
     * @param name
     */
    private void addGraduateAuditStatus(HttpServletRequest request, EntityQuery entityQuery,
            String name) {
        String graduateAuditStatus = request.getParameter(name);
        if (NumberUtils.isNumber(graduateAuditStatus)) {
            entityQuery.add(new Condition("student." + name + " = (:" + name + ")", ConvertUtils
                    .convert(graduateAuditStatus, Boolean.class)));
        } else if ("null".equals(graduateAuditStatus)) {
            entityQuery.add(new Condition("student." + name + " = null"));
        }
    }
    
    /**
     * 获取指定学生类别串权限范围内包括子类别的学生类别数组
     * 
     * @param stdTypeIds
     * @param request
     * @param moduleName
     * @return
     */
    protected Long[] getStdTypeIdArray(String stdTypeIds, HttpServletRequest request,
            String moduleName) {
        Set stdTypeIdSet = new HashSet();
        Long[] stdTypeIdArray = SeqStringUtil.transformToLong(stdTypeIds);
        if (ArrayUtils.isEmpty(stdTypeIdArray)) {
            return ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
        }
        for (int i = 0; i < stdTypeIdArray.length; i++) {
            stdTypeIdSet.addAll(EntityUtils.extractIds(getStdTypesOf(stdTypeIdArray[i], moduleName,
                    request)));
        }
        return (Long[]) stdTypeIdSet.toArray(new Long[stdTypeIdSet.size()]);
    }
    
}
