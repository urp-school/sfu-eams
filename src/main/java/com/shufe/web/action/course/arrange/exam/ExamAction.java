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
 * chaostone             2006-11-20            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.arrange.exam;

import java.util.ArrayList;
import java.util.Collection;
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
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.course.arrange.exam.ExamGroup;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public abstract class ExamAction extends CalendarRestrictionSupportAction {
    
    TeachTaskService teachTaskService;
    
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
        setCalendarDataRealm(request, hasStdType);
        String stdTypeDataRealm = getStdTypeIdSeq(request);
        String departDataRealm = getDepartmentIdSeq(request);
        // 获得主考院系和监考教师列表
        request.setAttribute("examDeparts", getExamDeparts(request));
        request.setAttribute("invigilateDeparts", getInvigilateDeparts(request));
        
        StudentType stdType = (StudentType) request.getAttribute("studentType");
        TeachCalendar calendar = (TeachCalendar) request.getAttribute("calendar");
        // 查找排考组
        EntityQuery entityQuery = new EntityQuery(ExamGroup.class, "examGroup");
        entityQuery.join("examGroup.tasks", "task");
        entityQuery.add(new Condition("task.teachClass.stdType in (:stdTypes)", getStdTypesOf(
                stdType.getId(), request)));
        // 不限制部门了。因为该类的子类监考设置action，是根据监考院系划分权限的。
        // entityQuery.add(new Condition("task.arrangeInfo.teachDepart in(:teachDeparts)",
        // getDeparts(request)));
        entityQuery.add(new Condition("task.calendar.id=" + calendar.getId()));
        entityQuery.setSelect("select distinct examGroup");
        
        addCollection(request, "groups", utilService.search(entityQuery));
        addCollection(request, "courseTypes", teachTaskService.getCourseTypesOfTask(
                stdTypeDataRealm, departDataRealm, (TeachCalendar) request
                        .getAttribute(Constants.CALENDAR)));
        addCollection(request, "examTypes", baseCodeService.getCodes(ExamType.class));
        // 设置星期
        addCollection(request, "weeks", WeekInfo.WEEKS);
        return forward(request);
    }
    
    /**
     * 查找考务中教学任务
     * 
     * @param request
     * @param arranged
     *            是否已经安排排考
     * @param queryGrouped
     *            是否查询中考虑任务已经编组
     * @return
     * @throws Exception
     */
    protected Collection getTasks(HttpServletRequest request, Boolean arranged, Boolean queryGrouped)
            throws Exception {
        return utilService.search(buildExamTaskQuery(request, arranged, queryGrouped));
    }
    
    /**
     * 查询教学任务<br>
     * 如果查询有补缓考的教学任务时，首先是看在排考记录中存在补缓考名单
     * 
     * @param form
     * @param request
     * @param arranged
     *            是否已经安排排考
     * @param queryGrouped
     *            是否查询中考虑任务已经编组
     * @return
     * @throws Exception
     */
    protected EntityQuery buildExamTaskQuery(HttpServletRequest request, Boolean arranged,
            Boolean queryGrouped) throws Exception {
        Long examTypeId = getLong(request, "examType.id");
        Long calendarId = getLong(request, "calendar.id");
        EntityQuery query = new EntityQuery(TeachTask.class, "task");
        QueryRequestSupport.populateConditions(request, query, "task.teachClass.stdType.id");
        
        // 含有补缓考的教学任务
        if (StringUtils.contains(ExamType.delayAndAgainExamTypeIds.toString(), examTypeId
                .toString())) {
            query
                    .add(new Condition(
                            "task.id in(select distinct take.task.id from ExamTake take where  instr(:examTypeIds,','||take.examType.id||',')>0 and take.calendar.id="
                                    + calendarId + ")", ExamType.delayAndAgainExamTypeIds
                                    .toString()));
        }
        // 查询班级
        String adminClassName = request.getParameter("teachClass.adminClass.name");
        if (StringUtils.isNotEmpty(adminClassName)) {
            query.join("task.teachClass.adminClasses", "adminClass");
            query.add(new Condition(" adminClass.name like :adminClassName", "%" + adminClassName
                    + "%"));
        }
        // 查询结束周
        Integer endWeek = getInteger(request, "arrangeInfo.endWeek");
        if (null != endWeek && endWeek.intValue() != 0) {
            query.add(new Condition(
                    " task.arrangeInfo.weekStart + task.arrangeInfo.weeks-1 <= :endWeek", endWeek));
        }
        // 查找如果考试没有安排
        if (Boolean.FALSE.equals(arranged)) {
            query
                    .add(new Condition(
                            " not exists (from ExamActivity as exam where exam.task=task and exam.examType.id=:examTypeId)",
                            examTypeId));
        }
        // 添加权限
        Long stdTypeId = getLong(request, "task.teachClass.stdType.id");
        if (null == stdTypeId) {
            stdTypeId = getLong(request, "calendar.studentType.id");
        }
        Collection stdTypes = null;
        if (null != stdTypeId && stdTypeId.intValue() != 0) {
            stdTypes = getStdTypesOf(stdTypeId, request);
        } else {
            stdTypes = getStdTypes(request);
        }
        Condition authorityCondition = getAuthorityCondition(stdTypes, getDeparts(request));
        if (null != authorityCondition) {
            query.add(authorityCondition);
        }
        
        query.add(new Condition("task.calendar.id=" + getLong(request, "calendar.id")));
        // 查询课程安排情况
        String week = request.getParameter("time.week");
        String startUnit = request.getParameter("time.startUnit");
        if (StringUtils.isNotEmpty(week) || StringUtils.isNotEmpty(startUnit)) {
            List activityParams = new ArrayList();
            String activitySubQuery = " exists (from CourseActivity activity where activity.task=task ";
            if (StringUtils.isNotEmpty(week)) {
                activitySubQuery += " and activity.time.weekId = :weekId";
                activityParams.add(Integer.valueOf(week));
            }
            if (StringUtils.isNotEmpty(startUnit)) {
                activitySubQuery += " and (activity.time.startUnit <= :startUnit and activity.time.endUnit >= :endUnit)";
                activityParams.add(Integer.valueOf(startUnit));
                activityParams.add(Integer.valueOf(startUnit));
            }
            activitySubQuery += ")";
            Condition activityCondition = new Condition(activitySubQuery);
            activityCondition.setValues(activityParams);
            query.add(activityCondition);
        }
        // 查询考试安排情况,此时不在查询教学活动的日历，因为考试可能安排在下个学期
        if (Boolean.TRUE.equals(arranged)) {
            String activitySubQuery = "exists( from ExamActivity examActivity"
                    + " where examActivity.task=task and examActivity.examType.id=:examTypeId";
            
            List activityParams = new ArrayList();
            activityParams.add(examTypeId);
            String examWeek = request.getParameter("exam.time.week");
            if (StringUtils.isNotEmpty(examWeek)) {
                activitySubQuery += " and examActivity.time.weekId = :weekId";
                activityParams.add(Integer.valueOf(examWeek));
            }
            String examStartUnit = request.getParameter("exam.time.startUnit");
            if (StringUtils.isNotEmpty(examStartUnit)) {
                activitySubQuery += " and (examActivity.time.startUnit <= :startUnit and examActivity.time.endUnit >= :endUnit)";
                activityParams.add(Integer.valueOf(examStartUnit));
                activityParams.add(Integer.valueOf(examStartUnit));
            }
            String examRoom = request.getParameter("exam.room.name");
            if (StringUtils.isNotEmpty(examRoom)) {
                activitySubQuery += " and examActivity.room.name like :roomName";
                activityParams.add("%" + examRoom + "%");
            }
            String departId = request.getParameter("exam.examMonitor.depart.id");
            if (StringUtils.isNotEmpty(departId)) {
                activitySubQuery += " and examActivity.examMonitor.depart.id = :invigilateDepartId";
                activityParams.add(Long.valueOf(departId));
            }
            activitySubQuery += ")";
            Condition activityCondition = new Condition(activitySubQuery);
            activityCondition.setValues(activityParams);
            query.add(activityCondition);
        }
        // 查询排考组
        if (null == queryGrouped) {
            String groupId = request.getParameter("examGroup.id");
            if (StringUtils.isNotEmpty(groupId)) {
                query.join("task.arrangeInfo.examGroups", "examGroup");
                query.add(new Condition("examGroup.id=:examGroupId", Long.valueOf(groupId)));
            }
        } else {
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
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return query;
        
    }
    
    /**
     * 查询权限范围内的主考院系<br>
     * 从任务的角度
     * 
     * @param request
     * @return
     */
    protected Collection getExamDeparts(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery.add(new Condition("task.calendar= :calendar", request
                .getAttribute(Constants.CALENDAR)));
        entityQuery.add(new Condition("task.arrangeInfo.teachDepart in(:departs)",
                getDeparts(request)));
        entityQuery.setSelect("select distinct task.arrangeInfo.teachDepart");
        return utilService.search(entityQuery);
    }
    
    /**
     * 查询权限范围内的监考院系
     * 
     * @param request
     * @return
     */
    protected Collection getInvigilateDeparts(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(ExamActivity.class, "activity");
        entityQuery.add(new Condition("activity.calendar= :calendar", request
                .getAttribute(Constants.CALENDAR)));
        entityQuery.add(new Condition("activity.examMonitor.depart in(:departs)",
                getDeparts(request)));
        entityQuery.setSelect("select distinct activity.examMonitor.depart");
        return utilService.search(entityQuery);
    }
    
    /**
     * 限制权限,用以重载
     * 
     * @param stdTypes
     * @param departs
     * @return
     */
    protected abstract Condition getAuthorityCondition(Collection stdTypes, Collection departs);
    
    public TeachTaskService getTeachTaskService() {
        return teachTaskService;
    }
    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
}
