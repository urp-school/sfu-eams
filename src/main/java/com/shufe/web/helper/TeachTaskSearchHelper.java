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
 * chaostone            2006-12-25          Created
 * zq                   2007-09-12          FIXME 仍未修改成功
 * zq                   2007-09-13          把course.teachTaskSearch改为：
 *                                          update xtmk_t set mkmc = 'course.task.teachTaskSearch' where mkdm = '010204'
 *                                          否则学生权限不足；
 *                                          searchTask()重载了一个；
 *                                          buildTaskQuery()增加了一个参数：Class clazz；
 *                                          但是仍然报错，原因是SeqStringUtil.intersectSeq()执行之后为空集，
 *                                          导致SQL语句报错：select task  from com.shufe.model.course.task.TeachTask task where  1=1  and (task.calendar.studentType.id=:calendar_studentType_id) and (task.calendar.id=:calendar_id) and (()) and (task.calendar.id=43)；
 *                                          所以，有关调用searchTask()方法的Action(s)，暂且无法修改，直至该错误修复后，再查询这些Action，它们是：
 *                                          CourseTakeForTaskAction
 *                                          ElectScopeAction
 *                                          TeachAccidentAction
 *  
 ********************************************************************************/

package com.shufe.web.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.security.Resource;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.util.DataRealmUtils;

public class TeachTaskSearchHelper extends SearchHelper {
    
    /**
     * 查找教学任务task<br>
     * 1)查找行政班级中以:adminClass.name为参数<br>
     * 2)查询排课情况以:courseActivity开头<br>
     * 3)查询排考情况以:examActivity开头<br>
     * 4)考试安排完成:task.arrangeInfo.isExamArrangeComplete<br>
     * 5)排考查询分组情况:arrangeInfo.examGrouped<br>
     * 6)日历以:calendar开头<br>
     * 7)查询教师以:teacher<br>
     * 8)特殊的选课属性:electInfo.electCountCompare标识选课人数上限和实际人数的比较<br>
     * 9)特殊的排课属性:arrangeInfo.endWeek
     * 
     * @param request
     * @param isDataRealm
     * @return
     */
    public EntityQuery buildTaskQuery(HttpServletRequest request, Boolean isDataRealm) {
        EntityQuery query = new EntityQuery(TeachTask.class, "task");
        String hasTeacher = request.getParameter("hasTeacher");
        if(null!=hasTeacher){
            query.add(new Condition(" not exists(from Teacher t where task.arrangeInfo.teachers.id=t.id)"));
        }
        String taskIdSeq = RequestUtils.get(request, "taskIds");
        if (StringUtils.isNotEmpty(taskIdSeq)) {
            query.add(new Condition("task.id in (:taskIds)", SeqStringUtil
                    .transformToLong(taskIdSeq)));
        }
        QueryRequestSupport.populateConditions(request, query, "task.teachClass.stdType.id,task.id");
        Long calendarId = RequestUtils.getLong(request, "calendar.id");
        if (null != calendarId) {
            query.add(new Condition("task.calendar.id=" + calendarId));
        }
        // 查询教师
        // FIXME 应该使用exists
        List teacherConditions = QueryRequestSupport.extractConditions(request, Teacher.class,
                "teacher", null);
        if (!teacherConditions.isEmpty()) {
            query.join("task.arrangeInfo.teachers", "teacher");
            query.getConditions().addAll(teacherConditions);
        }
        // 查询班级
        List adminClassConditions = QueryRequestSupport.extractConditions(request,
                AdminClass.class, "adminClass", null);
        if (CollectionUtils.isNotEmpty(adminClassConditions)) {
            query.join("task.teachClass.adminClasses", "adminClass");
            query.getConditions().addAll(adminClassConditions);
        }
        // 查询结束周
        Integer endWeek = RequestUtils.getInteger(request, "arrangeInfo.endWeek");
        if (null != endWeek && endWeek.intValue() != 0) {
            query.add(new Condition(
                    " task.arrangeInfo.weekStart + task.arrangeInfo.weeks-1 <= :endWeek", endWeek));
        }
        // 是否需要指定学生
        Integer stdState = RequestUtils.getInteger(request, "stdState");
        if (null != stdState) {
            query.add(new Condition("bitand(task.teachClass.stdState, :toStdState) > 0", stdState));
        }
        // 查询课程安排情况
        Map params = RequestUtils.getParams(request, "courseActivity");
        if (!params.isEmpty()) {
            String week = (String) params.get("time.week");
            String startUnit = (String) params.get("time.startUnit");
            String endUnit = (String) params.get("time.endUnit");
            if (StringUtils.isNotEmpty(week) || StringUtils.isNotEmpty(startUnit) || StringUtils.isNotEmpty(endUnit)) {
                List activityParams = new ArrayList();
                String activitySubQuery = " exists (from CourseActivity courseActivity where courseActivity.task=task ";
                if (StringUtils.isNotEmpty(week)) {
                    activitySubQuery += " and courseActivity.time.weekId = :weekId";
                    activityParams.add(Integer.valueOf(week));
                }
                if (Boolean.TRUE.equals(RequestUtils.getBoolean(request, "isInterval"))) {
                    if (StringUtils.isNotEmpty(startUnit)) {
                        activitySubQuery += " and courseActivity.time.startUnit >= :startUnit";
                        activityParams.add(Integer.valueOf(startUnit));
                    }
                    if (StringUtils.isNotEmpty(endUnit)) {
                        activitySubQuery += " and courseActivity.time.endUnit <= :endUnit";
                        activityParams.add(Integer.valueOf(endUnit));
                    }
                } else {
                    if (StringUtils.isNotEmpty(startUnit)) {
                        activitySubQuery += " and (courseActivity.time.startUnit <= :startUnit and courseActivity.time.endUnit >= :endUnit)";
                        activityParams.add(Integer.valueOf(startUnit));
                        activityParams.add(Integer.valueOf(startUnit));
                    }
                }
                activitySubQuery += ")";
                Condition activityCondition = new Condition(activitySubQuery);
                activityCondition.setValues(activityParams);
                query.add(activityCondition);
            }
        }
        // 查询考试安排情况
        Boolean examArranged = RequestUtils.getBoolean(request,
                "task.arrangeInfo.isExamArrangeComplete");
        if (Boolean.TRUE.equals(examArranged)) {
            String activitySubQuery = "exists( from ExamActivity examActivity"
                    + " where examActivity.task=task and examActivity.examType.id=:examTypeId";
            
            List activityParams = new ArrayList();
            Long examTypeId = RequestUtils.getLong(request, "examActivity.examType.id");
            activityParams.add(examTypeId);
            String examWeek = request.getParameter("examActivity.time.week");
            if (StringUtils.isNotEmpty(examWeek)) {
                activitySubQuery += " and examActivity.time.weekId = :weekId";
                activityParams.add(Integer.valueOf(examWeek));
            }
            String examStartUnit = request.getParameter("examActivity.time.startUnit");
            if (StringUtils.isNotEmpty(examStartUnit)) {
                activitySubQuery += " and (examActivity.time.startUnit <= :startUnit and examActivity.time.endUnit >= :endUnit)";
                activityParams.add(Integer.valueOf(examStartUnit));
                activityParams.add(Integer.valueOf(examStartUnit));
            }
            String examRoom = request.getParameter("examActivity.room.name");
            if (StringUtils.isNotEmpty(examRoom)) {
                activitySubQuery += " and examActivity.room.name like :roomName";
                activityParams.add("%" + examRoom + "%");
            }
            String departId = request.getParameter("examActivity.examMonitor.depart.id");
            if (StringUtils.isNotEmpty(departId)) {
                activitySubQuery += " and examActivity.examMonitor.depart.id = :invigilateDepartId";
                activityParams.add(Long.valueOf(departId));
            }
            activitySubQuery += ")";
            Condition activityCondition = new Condition(activitySubQuery);
            activityCondition.setValues(activityParams);
            query.add(activityCondition);
        } else if (Boolean.FALSE.equals(examArranged)) {
            Long examTypeId = RequestUtils.getLong(request, "examActivity.examType.id");
            query
                    .add(new Condition(
                            " not exists (from ExamActivity as exam where exam.task=task and exam.examType.id=:examTypeId)",
                            examTypeId));
        }
        // 查询排考分组
        Boolean queryGrouped = RequestUtils.getBoolean(request, "arrangeInfo.examGrouped");
        if (null == queryGrouped) {
            String groupId = request.getParameter("task.arrangeInfo.examGroups.id");
            if (StringUtils.isNotEmpty(groupId)) {
                query.join("task.arrangeInfo.examGroups", "examGroup");
                query.add(new Condition("examGroup.id=:examGroupId", Long.valueOf(groupId)));
            }
        } else {
            Long examTypeId = RequestUtils.getLong(request, "examActivity.examType.id");
            if (Boolean.FALSE.equals(queryGrouped)) {
                query
                        .add(new Condition(
                                "not exists( from ExamGroup as examGroup join examGroup.tasks as groupTask where	 task.id=groupTask.id and examGroup.examType.id =(:examTypeId)) ",
                                examTypeId));
            } else {
                query
                        .add(new Condition(
                                "exists( from ExamGroup as examGroup join examGroup.tasks as groupTask where	 task.id=groupTask.id and examGroup.examType.id =(:examTypeId)) ",
                                examTypeId));
            }
        }
        // 查询选课人数
        Integer compare = RequestUtils.getInteger(request, "electInfo.electCountCompare");
        if (null != compare) {
            String op = "";
            if (compare.intValue() == 0) {
                op = "=";
            } else if (compare.intValue() < 0) {
                op = "<";
            } else {
                op = ">";
            }
            query
                    .add(new Condition("task.teachClass.stdCount" + op
                            + "task.electInfo.maxStdCount"));
        }
        
        Integer planStdCountStart = RequestUtils.getInteger(request, "planStdCountStart");
        if (null != planStdCountStart) {
            query.add(new Condition("task.teachClass.planStdCount >= (:planStdCountStart)",
                    planStdCountStart));
        }
        Integer planStdCountEnd = RequestUtils.getInteger(request, "planStdCountEnd");
        if (null != planStdCountEnd) {
            query.add(new Condition("task.teachClass.planStdCount <= (:planStdCountEnd)",
                    planStdCountEnd));
        }
        Integer stdCountStart = RequestUtils.getInteger(request, "stdCountStart");
        if (null != stdCountStart) {
            query.add(new Condition("task.teachClass.stdCount >= (:stdCountStart)", stdCountStart));
        }
        Integer stdCountEnd = RequestUtils.getInteger(request, "stdCountEnd");
        if (null != stdCountEnd) {
            query.add(new Condition("task.teachClass.stdCount <= (:stdCountEnd)", stdCountEnd));
        }
        Long stdTypeId = RequestUtils.getLong(request, "task.teachClass.stdType.id");
        if (null == stdTypeId) {
            stdTypeId = RequestUtils.getLong(request, "calendar.studentType.id");
        }
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        if (Boolean.TRUE.equals(isDataRealm)) {
            String resourceName = getResourceName(request);
            Resource resource = (Resource) authorityService.getResource(resourceName);
            if (null != resource && !resource.getPatterns().isEmpty()) {
                DataRealmUtils.addDataRealms(query, new String[] { "task.teachClass.stdType.id",
                        "task.arrangeInfo.teachDepart.id" }, dataRealmHelper.getDataRealmsWith(
                        stdTypeId, request));
            } else if (null != stdTypeId) {
                dataRealmHelper.addStdTypeTreeDataRealm(query, stdTypeId,
                        "task.teachClass.stdType.id", request);
            }
        }
        return query;
    }
    
    public Collection searchTask(HttpServletRequest request) {
        EntityQuery query = buildTaskQuery(request, Boolean.TRUE);
        return utilService.search(query);
    }
}
