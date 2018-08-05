//$Id: CourseTableCheckServiceImpl.java,v 1.1 2007-3-20 下午08:49:59 chaostone Exp $
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
 *chaostone      2007-3-20         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.arrange.task.CourseTableCheck;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.BasicService;
import com.shufe.service.course.arrange.task.CourseTableCheckService;
import com.shufe.service.util.stat.StatGroup;
import com.shufe.service.util.stat.StatHelper;
import com.shufe.util.DataRealmUtils;

public class CourseTableCheckServiceImpl extends BasicService implements CourseTableCheckService {
    
    public List statCheckBy(TeachCalendar calendar, DataRealm dataRealm, String attr, Class clazz) {
        return statCheckBy(calendar, dataRealm, new String[] { "check.std.type.id",
                "check.std.department.id" }, attr, clazz);
    }
    
    private List statCheckBy(TeachCalendar calendar, DataRealm dataRealm, String[] dataRealmAttrs,
            String attr, Class clazz) {
        EntityQuery entityQuery = new EntityQuery(CourseTableCheck.class, "check");
        entityQuery
                .setSelect("select new  com.shufe.service.util.stat.StatItem("
                        + attr
                        + ",count(check.isConfirm),sum(CASE WHEN check.isConfirm=true THEN 1 ELSE 0 END ),sum(CASE WHEN check.isConfirm=true THEN 0 ELSE 1 END))");
        entityQuery.add(new Condition("check.calendar=:calendar", calendar));
        DataRealmUtils.addDataRealm(entityQuery, dataRealmAttrs, dataRealm);
        entityQuery.setGroups(Collections.singletonList(attr));
        Collection stats = utilService.search(entityQuery);
        return new StatHelper(utilService).setStatEntities(stats, clazz);
    }
    
    public List statCheckByDepart(TeachCalendar calendar, DataRealm dataRealm, MajorType majorType) {
        EntityQuery query = new EntityQuery(CourseTableCheck.class, "check");
        query
                .setSelect("check.std.type.id,check.std.department.id,check.std.enrollYear,count(check.isConfirm),sum(CASE WHEN check.isConfirm=true THEN 1 ELSE 0 END )");
        query.add(new Condition("check.calendar=:calendar", calendar));
        DataRealmUtils.addDataRealm(query, new String[] { "check.std.type.id",
                "check.std.department.id" }, dataRealm);
        if (null != majorType && null != majorType.getId()) {
            if (MajorType.FIRST.equals(majorType.getId())) {
                query.add(new Condition("check.std.firstMajor.majorType.id =:majorType", MajorType.FIRST));
            } else if (MajorType.SECOND.equals(majorType.getId())){
                query.add(new Condition("check.std.secondMajor.majorType.id =:majorType", MajorType.SECOND));
            }
        }
        query.groupBy("check.std.type.id").groupBy("check.std.department.id").groupBy(
                "check.std.enrollYear");
        List datas = (List) utilDao.search(query);
        new StatHelper(utilService).replaceIdWith(datas, new Class[] { StudentType.class,
                Department.class });
        List statGroups = StatGroup.buildStatGroups(datas, 2);
        return statGroups;
    }
}
