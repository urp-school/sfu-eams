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
 * chaostone             2006-11-12            Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.arrange.exam;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.course.arrange.exam.ExamGroup;
import com.shufe.model.course.task.TeachTask;

/**
 * 排考组管理
 * 
 * @author chaostone
 */
public class ExamGroupAction extends ExamAction {
    
    /**
     * 排考课程组管理主界面
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
        List stdTypeList = getStdTypes(request);
        String stdTypeId = get(request, "calendar.studentType.id");
        StudentType stdType = null;
        if (StringUtils.isEmpty(stdTypeId)) {
            stdType = (StudentType) stdTypeList.get(0);
        } else {
            stdType = (StudentType) utilService.load(StudentType.class, new Long(stdTypeId));
        }
        addSingleParameter(request, "studentType", stdType);
        addEntity(request, stdType);
        setCalendar(request, stdType);
        addCollection(request, "stdTypeList", stdTypeList);
        addCollection(request, "examTypes", baseCodeService.getCodes(ExamType.class));
        return forward(request);
    }
    
    /**
     * 查询排考课程组
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward groupList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery entityQuery = new EntityQuery(ExamGroup.class, "examGroup");
        populateConditions(request, entityQuery);
        entityQuery.join("examGroup.tasks", "task");
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        Collection stdTypes = null;
        if (null != stdTypeId && stdTypeId.intValue() != 0) {
            stdTypes = getStdTypesOf(stdTypeId, request);
        } else {
            stdTypes = getStdTypes(request);
        }
        entityQuery.add(new Condition("task.teachClass.stdType in (:stdTypes)", stdTypes));
        entityQuery.add(new Condition("task.arrangeInfo.teachDepart in(:teachDeparts)",
                getDeparts(request)));
        entityQuery.add(new Condition("task.calendar.id=" + getLong(request, "calendar.id")));
        entityQuery
                .setSelect("select examGroup.id,examGroup.name,count(*) as tasks,examGroup.isPublish");
        entityQuery.groupBy(" examGroup.id,examGroup.name,examGroup.isPublish");
        addCollection(request, "groups", utilService.search(entityQuery));
        return forward(request);
    }
    
    /**
     * 未编组的教学任务列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward taskList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addSingleParameter(request, "studentTypes", getStdTypes(request));
        ExamGroup group = (ExamGroup) populate(request, ExamGroup.class);
        addEntity(request, group);
        addCollection(request, "tasks", getTasks(request, null, Boolean.FALSE));
        addCollection(request, "weeks", WeekInfo.WEEKS);
        return forward(request);
    }
    
    /**
     * 修改和新建排考课程组
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long examTypeId = getLong(request, "examGroup.examType.id");
        ExamType examType = (ExamType) utilService.get(ExamType.class, examTypeId);
        ExamGroup group = new ExamGroup();
        group.setExamType(examType);
        addEntity(request, group);
        return forward(request);
    }
    
    /**
     * 保存排考组
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        utilService.saveOrUpdate(populate(request, ExamGroup.class));
        return redirect(request, new Action("", "groupList"), "info.save.success",
                new String[] { "calendar" });
    }
    
    /**
     * 添加任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExamGroup group = (ExamGroup) populate(request, ExamGroup.class);
        if (!group.isPO()) {
            group.setId(null);
        } else {
            group = (ExamGroup) utilService.get(ExamGroup.class, group.getId());
        }
        List tasks = utilService.load(TeachTask.class, "id", SeqStringUtil.transformToLong(get(
                request, "taskIds")));
        group.getTasks().addAll(tasks);
        utilService.saveOrUpdate(group);
        return redirect(request, new Action("", "groupList"), "info.save.success",
                new String[] { "calendar" });
    }
    
    /**
     * 查看排考组详细信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ExamGroup group = (ExamGroup) utilService.get(ExamGroup.class, getLong(request,
                "examGroup.id"));
        addEntity(request, group);
        EntityQuery entityQuery = new EntityQuery(ExamGroup.class, "examGroup");
        entityQuery.add(new Condition("examGroup.id=:examGroupId", group.getId()));
        entityQuery.join("examGroup.tasks", "task");
        entityQuery.setSelect("select task");
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "tasks", utilService.search(entityQuery));
        return forward(request);
    }
    
    /**
     * 去除任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExamGroup group = (ExamGroup) utilService.get(ExamGroup.class, getLong(request,
                "examGroup.id"));
        group.getTasks().removeAll(
                utilService.load(TeachTask.class, "id", SeqStringUtil.transformToLong(get(request,
                        "taskIds"))));
        utilService.saveOrUpdate(group);
        if (group.getTasks().isEmpty()) {
            utilService.remove(group);
        }
        return redirect(request, new Action("", "groupList"), "info.delete.success",
                new String[] { "calendar" });
    }
    
    /**
     * 发布排考结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward publish(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Boolean status = getBoolean(request, "status");
        if (!Boolean.TRUE.equals(status)) {
            status = Boolean.FALSE;
        }
        List groups = utilService.load(ExamGroup.class, "id", SeqStringUtil.transformToLong(get(
                request, "examGroupIds")));
        for (Iterator iter = groups.iterator(); iter.hasNext();) {
            ExamGroup group = (ExamGroup) iter.next();
            group.setIsPublish(status);
        }
        utilService.saveOrUpdate(groups);
        return redirect(request, new Action("", "groupList"), "info.set.success",
                new String[] { "calendar" });
    }
    
    /**
     * 删除排考组
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        utilService.remove(ExamGroup.class, "id", SeqStringUtil.transformToLong(get(request,
                "examGroupIds")));
        return redirect(request, new Action("", "groupList"), "info.delete.success",
                new String[] { "calendar" });
    }
    
    /**
     * 开课院系权限限制范围
     * 
     * @param stdTypes
     * @param departs
     * @return
     */
    protected Condition getAuthorityCondition(Collection stdTypes, Collection departs) {
        String condition = "task.teachClass.stdType in (:stdTypes) and task.arrangeInfo.teachDepart in(:teachDeparts)";
        return new Condition(condition, stdTypes, departs);
    }
}
