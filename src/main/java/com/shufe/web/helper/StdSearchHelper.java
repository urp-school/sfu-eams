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
 * chaostone            2007-01-09          Created
 * zq                   2007-09-18          buildStdQuery()中，增加了学生大类查询；
 ********************************************************************************/
package com.shufe.web.helper;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.security.Resource;
import com.shufe.model.std.PlanAuditResult;
import com.shufe.model.std.Student;
import com.shufe.util.DataRealmUtils;

/**
 * 查询学生辅助类
 * 
 * @author chaostone
 * 
 */
public class StdSearchHelper extends SearchHelper {
    
    public EntityQuery buildStdQuery(HttpServletRequest request) {
        return buildStdQuery(request, null);
    }
    
    /**
     * 查询学生的界面参数一般规定为:std<br>
     * 其他查询班级项,采用adminClass开头<br>
     * majorTypeId表示是否为双专业<br>
     * std_state一专业学籍有效性和二专业是否就读的条件
     * 
     * @param request
     * @param extraStdTypeAttr
     * @return
     */
    public EntityQuery buildStdQuery(HttpServletRequest request, String extraStdTypeAttr) {
        EntityQuery query = new EntityQuery(Student.class, "std");
        QueryRequestSupport.populateConditions(request, query, "std.type.id,std.graduateAuditStatus");
        Long majorTypeId = RequestUtils.getLong(request, "majorTypeId");
        // 默认是第一专业
        if (null == majorTypeId)
            majorTypeId = MajorType.FIRST;
        Long stdTypeId = RequestUtils.getLong(request, "std.type.id");
        if (null == stdTypeId && StringUtils.isNotEmpty(extraStdTypeAttr)) {
            stdTypeId = RequestUtils.getLong(request, extraStdTypeAttr);
        }
        // 学生类别大类查询
        String resourceName = getResourceName(request);
        Resource resource = (Resource) authorityService.getResource(resourceName);
        if (null != resource && !resource.getPatterns().isEmpty()) {
            String departName = "std.department.id";
            if (MajorType.SECOND.equals(majorTypeId)) {
                departName = "std.secondMajor.department.id";
            }
            DataRealmUtils.addDataRealms(query, new String[] { "std.type.id", departName },
                    dataRealmHelper.getDataRealmsWith(stdTypeId, request));
        } else {
            dataRealmHelper.addStdTypeTreeDataRealm(query, stdTypeId, "std.type.id", request);
        }
        
        Long departId = RequestUtils.getLong(request, "department.id");
        Long specialityId = RequestUtils.getLong(request, "speciality.id");
        Long aspectId = RequestUtils.getLong(request, "specialityAspect.id");
        Boolean stdActive = RequestUtils.getBoolean(request, "stdActive");
        String graduateAuditStatus = RequestUtils.get(request, "std.graduateAuditStatus");
        if (MajorType.FIRST.equals(majorTypeId)) {
            if (null != aspectId) {
                query.add(new Condition("std.firstAspect.id=" + aspectId));
            }
            if (null != specialityId) {
                query.add(new Condition("std.firstMajor.id=" + specialityId));
                query.add(new Condition("std.department.id=" + departId));
            } else {
                if (null != departId)
                    query.add(new Condition("std.department.id=" + departId));
            }
            // 查询一专业就读的学生
            if (null != stdActive) {
                query.add(new Condition("std.active = (:stdActive)", stdActive));
            }
            // 查询一专业毕业审核情况
        		if (StringUtils.isNotEmpty(graduateAuditStatus)) {
        			query.add(new Condition(
        					"exists(from "
        							+ PlanAuditResult.class.getName()
        							+ " par where par.std.id=std.id and  par.graduateAuditStatus = " + graduateAuditStatus+")"));
        		}
        } else {
            query.add(new Condition("std.secondMajor.majorType.id = (:majorTypeId)",
                    MajorType.SECOND));
            if (null != aspectId) {
                query.add(new Condition("std.secondAspect is not null"));
                query.add(new Condition("std.secondAspect.id=" + aspectId));
            } else {
                if (null != specialityId) {
                    query.add(new Condition("std.secondMajor.id=" + specialityId));
                    query.add(new Condition("std.department.id=" + departId));
                } else {
                    if (null != departId)
                        query.add(new Condition("std.secondMajor.department.id=" + departId));
                }
            }
            // 查询双专业就读的学生
            if (Boolean.TRUE.equals(stdActive)) {
                query.add(new Condition("std.isSecondMajorStudy = true"));
            } else if (Boolean.FALSE.equals(stdActive)) {
                query.add(new Condition(
                        "std.isSecondMajorStudy is null or std.isSecondMajorStudy = false"));
            }
            // 查询二专业毕业审核情况
     		if (StringUtils.isNotEmpty(graduateAuditStatus)) {
    			query.add(new Condition(
    					"exists(from "
    							+ PlanAuditResult.class.getName()
    							+ " par where par.std.id=std.id and  par.secondGraduateAuditStatus="
    							+ graduateAuditStatus));
    		}
        }
        
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        String adminClassName = RequestUtils.get(request, "adminClassName");
        if (StringUtils.isNotBlank(adminClassName)) {
            query.join("std.adminClasses", "adminClass");
            query.add(new Condition("adminClass.name like :adminClassName", "%" + adminClassName
                    + "%"));
        }
        return query;
    }
    
    public static void addSpecialityConditon(HttpServletRequest request, EntityQuery entityQuery,
            String stdAttr) {
        Long majorTypeId = RequestUtils.getLong(request, "majorTypeId");
        Long departId = RequestUtils.getLong(request, "department.id");
        Long specialityId = RequestUtils.getLong(request, "speciality.id");
        Long aspectId = RequestUtils.getLong(request, "specialityAspect.id");
        if (MajorType.FIRST.equals(majorTypeId)) {
            entityQuery.join("left", stdAttr + ".firstMajor", "firstMajor");
            entityQuery.join("left", stdAttr + ".firstAspect", "firstAspect");
            if (null != aspectId) {
                entityQuery.add(new Condition("firstAspect.id=" + aspectId));
            }
            if (null != specialityId) {
                entityQuery.add(new Condition("firstMajor.id=" + specialityId));
            } else {
                if (null != departId)
                    entityQuery.add(new Condition(stdAttr + ".department.id=" + departId));
            }
        } else {
            entityQuery.join("left", stdAttr + ".secondAspect", "secondAspect");
            entityQuery.join("left", stdAttr + ".secondMajor", "secondMajor");
            entityQuery.add(new Condition("secondMajor is not null"));
            if (null != aspectId) {
                entityQuery.add(new Condition("secondAspect.id=" + aspectId));
            } else {
                if (null != specialityId) {
                    entityQuery.add(new Condition("secondMajor.id=" + specialityId));
                } else {
                    if (null != departId)
                        entityQuery.add(new Condition(stdAttr + ".secondMajor.department.id="
                                + departId));
                }
            }
        }
    }
    
}
