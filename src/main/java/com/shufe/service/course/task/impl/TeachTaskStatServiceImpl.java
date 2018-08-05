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
 * chaostone             2006-12-9            Created
 *  
 ********************************************************************************/

package com.shufe.service.course.task.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.TeachLangType;
import com.ekingstar.eams.system.basecode.state.TeacherTitle;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.course.task.TeachTaskStatDAO;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.BasicService;
import com.shufe.service.course.task.TeachTaskStatService;
import com.shufe.service.util.stat.StatGroup;
import com.shufe.service.util.stat.StatHelper;
import com.shufe.service.util.stat.StatItem;
import com.shufe.util.DataRealmLimit;
import com.shufe.util.DataRealmUtils;

public class TeachTaskStatServiceImpl extends BasicService implements TeachTaskStatService {
    
    TeachTaskStatDAO teachTaskStatDAO;
    
    /**
     * @see TeachTaskStatService#statClassTask(TeachCalendar, DataRealmLimit)
     */
    public List statClassTask(TeachCalendar calendar, DataRealm dataRealm) {
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery
                .setSelect("select new  com.shufe.service.util.stat.StatItem(adminClass.id,count(*),sum(task.arrangeInfo.weekUnits),"
                        + "sum(task.arrangeInfo.overallUnits)," + "sum(task.course.credits))");
        entityQuery.add(new Condition("task.calendar=:calendar", calendar));
        entityQuery.add(new Condition("task.requirement.isGuaPai=false", calendar));
        entityQuery.join("task.teachClass.adminClasses", "adminClass");
        addAdminClassDataRealm(entityQuery, dataRealm);
        entityQuery.setGroups(Collections.singletonList("adminClass.id"));
        Collection commonTasks = utilDao.search(entityQuery);
        
        entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery
                .setSelect("select distinct new com.shufe.service.util.stat.StatItem( adminClass.id,task.course.id,task.arrangeInfo.weekUnits,task.arrangeInfo.overallUnits,task.course.credits)");
        entityQuery.add(new Condition("task.calendar=:calendar", calendar));
        entityQuery.add(new Condition("task.requirement.isGuaPai=true", calendar));
        entityQuery.join("task.teachClass.adminClasses", "adminClass");
        addAdminClassDataRealm(entityQuery, dataRealm);
        Collection gpTasks = utilDao.search(entityQuery);
        
        Map statMap = new HashMap();
        for (Iterator iter = commonTasks.iterator(); iter.hasNext();) {
            StatItem element = (StatItem) iter.next();
            statMap.put(element.getWhat(), element);
        }
        for (Iterator iter = gpTasks.iterator(); iter.hasNext();) {
            StatItem pgStat = (StatItem) iter.next();
            StatItem stat = (StatItem) statMap.get(pgStat.getWhat());
            if (null == stat) {
                statMap.put(pgStat.getWhat(), new StatItem(pgStat.getWhat(), new Integer(1), pgStat
                        .getCountors()[1], pgStat.getCountors()[2], pgStat.getCountors()[3]));
            } else {
                stat.getCountors()[0] = new Integer(((Number) stat.getCountors()[0]).intValue() + 1);
                stat.getCountors()[1] = new Float(((Number) pgStat.getCountors()[1]).floatValue()
                        + ((Number) stat.getCountors()[1]).floatValue());
                
                stat.getCountors()[2] = new Integer(((Number) pgStat.getCountors()[2]).intValue()
                        + ((Number) stat.getCountors()[2]).intValue());
                
                stat.getCountors()[3] = new Float(((Number) pgStat.getCountors()[3]).floatValue()
                        + ((Number) stat.getCountors()[3]).floatValue());
            }
        }
        Collection adminClasses = utilService.load(AdminClass.class, "id", statMap.keySet());
        for (Iterator iter = adminClasses.iterator(); iter.hasNext();) {
            AdminClass adminClass = (AdminClass) iter.next();
            StatItem stat = (StatItem) statMap.get(adminClass.getId());
            stat.setWhat(adminClass);
        }
        return new ArrayList(statMap.values());
    }
    
    public List statRoomTask(TeachCalendar calendar, DataRealm dataRealm) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @see TeachTaskStatService#statTeacherTask(TeachCalendar, DataRealmLimit)
     */
    public List statTeacherTask(TeachCalendar calendar, DataRealm dataRealm) {
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery
                .setSelect("select new  com.shufe.service.util.stat.StatItem(teacher.id,count(*),sum(task.arrangeInfo.weekUnits),"
                        + "sum(task.arrangeInfo.overallUnits)," + "sum(task.course.credits))");
        entityQuery.add(new Condition("task.calendar=:calendar", calendar));
        entityQuery.join("task.arrangeInfo.teachers", "teacher");
        addDataRealm(entityQuery, dataRealm);
        entityQuery.setGroups(Collections.singletonList("teacher.id"));
        Collection stats = utilDao.search(entityQuery);
        return setStatEntities(stats, Teacher.class);
    }
    
    public List statCourseTypeTask(TeachCalendar calendar, DataRealm dataRealm) {
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery
                .setSelect("select new  com.shufe.service.util.stat.StatItem(task.courseType.id,count(*),sum(task.arrangeInfo.weekUnits),"
                        + "sum(task.arrangeInfo.overallUnits)," + "sum(task.course.credits))");
        entityQuery.add(new Condition("task.calendar=:calendar", calendar));
        addDataRealm(entityQuery, dataRealm);
        entityQuery.setGroups(Collections.singletonList("task.courseType.id"));
        Collection stats = utilDao.search(entityQuery);
        return setStatEntities(stats, CourseType.class);
    }
    
    public List statDepartTask(TeachCalendar calendar, DataRealm dataRealm) {
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery
                .setSelect("select new  com.shufe.service.util.stat.StatItem(task.teachClass.depart.id,count(*),sum(task.arrangeInfo.weekUnits),"
                        + "sum(task.arrangeInfo.overallUnits)," + "sum(task.course.credits))");
        entityQuery.add(new Condition("task.calendar=:calendar", calendar));
        addDataRealm(entityQuery, dataRealm);
        entityQuery.setGroups(Collections.singletonList("task.teachClass.depart.id"));
        Collection stats = utilDao.search(entityQuery);
        return setStatEntities(stats, Department.class);
    }
    
    public List statStdTypeTask(TeachCalendar calendar, DataRealm dataRealm) {
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery
                .setSelect("select new  com.shufe.service.util.stat.StatItem(task.teachClass.stdType.id,count(*),sum(task.arrangeInfo.weekUnits),"
                        + "sum(task.arrangeInfo.overallUnits)," + "sum(task.course.credits))");
        entityQuery.add(new Condition("task.calendar=:calendar", calendar));
        addDataRealm(entityQuery, dataRealm);
        entityQuery.setGroups(Collections.singletonList("task.teachClass.stdType.id"));
        Collection stats = utilDao.search(entityQuery);
        return setStatEntities(stats, StudentType.class);
    }
    
    public List statTeachDepartTask(TeachCalendar calendar, DataRealm dataRealm) {
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery
                .setSelect("select new  com.shufe.service.util.stat.StatItem(task.arrangeInfo.teachDepart.id,count(*),sum(task.arrangeInfo.weekUnits),"
                        + "sum(task.arrangeInfo.overallUnits)," + "sum(task.course.credits))");
        entityQuery.add(new Condition("task.calendar=:calendar", calendar));
        addDataRealm(entityQuery, dataRealm);
        entityQuery.setGroups(Collections.singletonList("task.arrangeInfo.teachDepart.id"));
        Collection stats = utilDao.search(entityQuery);
        return setStatEntities(stats, Department.class);
    }
    
    public List statCourseTypeConfirm(TeachCalendar calendar, DataRealm dataRealm) {
        return statTaskConfirm(calendar, dataRealm, "task.courseType.id", CourseType.class);
    }
    
    public List statTeachDepartConfirm(TeachCalendar calendar, DataRealm dataRealm) {
        return statTaskConfirm(calendar, dataRealm, "task.arrangeInfo.teachDepart.id",
                Department.class);
    }
    
    private List statTaskConfirm(TeachCalendar calendar, DataRealm dataRealm, String item,
            Class entityClass) {
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery.setSelect("select new  com.shufe.service.util.stat.StatItem(" + item
                + ",count(*),0)");
        entityQuery.add(new Condition("task.calendar=:calendar", calendar));
        addDataRealm(entityQuery, dataRealm);
        Condition confirmCondition = new Condition("task.isConfirm=true");
        entityQuery.add(confirmCondition);
        entityQuery.setGroups(Collections.singletonList(item));
        
        Collection confirmStats = utilDao.search(entityQuery);
        Map statMap = buildStatMap(confirmStats);
        entityQuery.setQueryStr(null);
        confirmCondition.setContent("task.isConfirm=false");
        
        Collection unConfirmStats = utilDao.search(entityQuery);
        for (Iterator iter = unConfirmStats.iterator(); iter.hasNext();) {
            StatItem unConfirmed = (StatItem) iter.next();
            StatItem confirmed = (StatItem) statMap.get(unConfirmed.getWhat());
            if (null == confirmed) {
                Comparable tmp = unConfirmed.getCountors()[0];
                unConfirmed.getCountors()[0] = unConfirmed.getCountors()[1];
                unConfirmed.getCountors()[1] = tmp;
                statMap.put(unConfirmed.getWhat(), unConfirmed);
            } else {
                confirmed.getCountors()[1] = unConfirmed.getCountors()[0];
            }
        }
        return setStatEntities(statMap, entityClass);
    }
    
    public StatGroup statBilingual(TeachCalendar calendar, DataRealm dataRealm) {
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery.setSelect("select task.arrangeInfo.teachDepart.id,"
                + "sum(CASE WHEN task.requirement.teachLangType.id=" + TeachLangType.BILINGUAL
                + " THEN 1 ELSE 0 END),count(*)");
        entityQuery.add(new Condition("task.calendar=:calendar", calendar));
        addDataRealm(entityQuery, dataRealm);
        entityQuery.setGroups(Collections.singletonList("task.arrangeInfo.teachDepart.id"));
        List stats = (List) utilDao.search(entityQuery);
        new StatHelper(utilService).replaceIdWith(stats, new Class[] { Department.class });
        return StatGroup.buildStatGroup(stats, 2);
    }
    
    public StatGroup statRoomConfigType(TeachCalendar calendar, DataRealm dataRealm) {
        EntityQuery query = new EntityQuery(CourseActivity.class, "activity");
        query.add(new Condition("activity.calendar.id= " + calendar.getId()));
        query
                .setSelect("activity.task.arrangeInfo.teachDepart.id,activity.room.configType.id,"
                    + "sum( (activity.time.endUnit-activity.time.startUnit+1) * (length(activity.time.validWeeks)-length(replace(activity.time.validWeeks,'1',null))) )");
        query.groupBy("activity.task.arrangeInfo.teachDepart.id,activity.room.configType.id");
        
        List stats = (List) utilService.search(query);
        new StatHelper(utilService).replaceIdWith(stats, new Class[] { Department.class,
                ClassroomType.class });
        return new StatGroup(null, StatGroup.buildStatGroups(stats));
    }
    
    private Map buildStatMap(Collection stats) {
        Map statMap = new HashMap();
        for (Iterator iter = stats.iterator(); iter.hasNext();) {
            StatItem element = (StatItem) iter.next();
            statMap.put(element.getWhat(), element);
        }
        return statMap;
    }
    
    private List setStatEntities(Map statMap, Class entityClass) {
        Collection entities = utilService.load(entityClass, "id", statMap.keySet());
        for (Iterator iter = entities.iterator(); iter.hasNext();) {
            Entity entity = (Entity) iter.next();
            StatItem stat = (StatItem) statMap.get(entity.getEntityId());
            stat.setWhat(entity);
        }
        return new ArrayList(statMap.values());
    }
    
    private List setStatEntities(Collection stats, Class entityClass) {
        Map statMap = buildStatMap(stats);
        return setStatEntities(statMap, entityClass);
    }
    
    private void addAdminClassDataRealm(EntityQuery entityQuery, DataRealm dataRealm) {
        DataRealmUtils.addDataRealm(entityQuery, new String[] { "adminClass.stdType.id",
                "adminClass.department.id" }, dataRealm);
    }
    
    private void addDataRealm(EntityQuery entityQuery, DataRealm dataRealm) {
        if (null != dataRealm) {
            if (StringUtils.isNotBlank(dataRealm.getStudentTypeIdSeq())) {
                entityQuery.add(new Condition("task.teachClass.stdType.id in(:stdTypeIds)",
                        SeqStringUtil.transformToLong(dataRealm.getStudentTypeIdSeq())));
            }
            if (StringUtils.isNotBlank(dataRealm.getDepartmentIdSeq())) {
                entityQuery.add(new Condition("task.arrangeInfo.teachDepart.id in(:departIds)",
                        SeqStringUtil.transformToLong(dataRealm.getDepartmentIdSeq())));
            }
        }
    }
    
    public void setTeachTaskStatDAO(TeachTaskStatDAO teachTaskStatDAO) {
        this.teachTaskStatDAO = teachTaskStatDAO;
    }
    
    public List statTeacherTitle(List calendars) {
        List stats = teachTaskStatDAO.statTeacherTitle(calendars);
        new StatHelper(utilService).replaceIdWith(stats, new Class[] { TeachCalendar.class,
                TeacherTitle.class });
        return stats;
    }
    
}
