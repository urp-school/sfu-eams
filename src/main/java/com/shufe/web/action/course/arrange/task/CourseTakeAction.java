//$Id: CourseTakeAction.java,v 1.12 2006/12/25 09:43:40 duanth Exp $
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
 * hc             2005-12-17         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.arrange.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.mvc.struts.misc.ForwardSupport;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.security.User;
import com.ekingstar.security.service.UserService;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.election.ElectRecord;
import com.shufe.model.course.election.WithdrawRecord;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.quality.evaluate.Questionnaire;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.TaskActivityService;
import com.shufe.service.course.arrange.task.CourseTakeService;
import com.shufe.service.course.election.ElectCourseService;

/**
 * 上课名单
 * 
 * @author chaostone
 */
public class CourseTakeAction extends CourseTakeSearchAction {
    
    protected CourseTakeService courseTakeService;
    
    protected TaskActivityService taskActivityService;
    
    protected UserService userService;
    
    /**
     * 上课名单主界面
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
        request.setAttribute("courseTakeTypes", baseCodeService.getCodes(CourseTakeType.class));
        List questionnaireList = utilService.load(Questionnaire.class, "depart.id", SeqStringUtil
                .transformToLong(getDepartmentIdSeq(request)));
        request.setAttribute("questionnaireList", questionnaireList);
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "courseTakeTypes", baseCodeService.getCodes(CourseTakeType.class));
        return super.search(mapping, form, request, response);
    }
    
    public ActionForward taskList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        QueryRequestSupport.populateConditions(request, entityQuery);
        entityQuery
                .add(new Condition("task.teachClass.stdType in(:stdTypes)", getStdTypes(request)));
        entityQuery.add(new Condition("task.arrangeInfo.teachDepart in(:departs)",
                getDeparts(request)));
        entityQuery.setLimit(getPageLimit(request));
        addCollection(request, "tasks", utilService.search(entityQuery));
        return forward(request);
    }
    
    /**
     * 为学生加课
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String seqNo = request.getParameter("seqNo");
        Long stdId = getLong(request, "stdId");
        Student std = (Student) utilService.get(Student.class, stdId);
        Long calendarId = getLong(request, "calendarId");
        
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery.add(new Condition("task.calendar.id=" + calendarId));
        entityQuery.add(new Condition("task.seqNo=:seqNo", seqNo));
        
        Collection rsTasks = utilService.search(entityQuery);
        if (rsTasks.isEmpty()) {
            return redirect(request, "search", "error.model.notExist");
        }
        TeachTask task = (TeachTask) rsTasks.iterator().next();
        Boolean checkConstraint = getBoolean(request, "checkConstraint");
        if (null == checkConstraint) {
            checkConstraint = Boolean.TRUE;
        }
        
        String message = courseTakeService.add(task, std, checkConstraint.booleanValue());
        if (!ElectCourseService.selectSuccess.equals(message)) {
            addCollection(request, "tasks", Collections.singleton(task));
            saveErrors(request.getSession(), ForwardSupport.buildMessages(new String[] { message }));
            return forward(request, "constraintViolation");
        } else {
            return redirect(request, "search", message);
        }
        
    }
    
    /**
     * 退课
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward withdraw(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] courseTakeIds = SeqStringUtil.transformToLong(get(request, "courseTakeIds"));
        List<CourseTake> courseTakes = utilService.load(CourseTake.class, "id", courseTakeIds);
        List reservedTakes = new ArrayList();
        for(CourseTake take:courseTakes){
            TeachTask task = take.getTask();
            if(null!=task.getElectInfo().getMinStdCount() && task.getTeachClass().getStdCount()<=task.getElectInfo().getMinStdCount()){
                reservedTakes.add(take);
            }
        }
        if(!reservedTakes.isEmpty()){
            return redirect(request, "search","不能低于人数下限" );
        }else{
            User sender = getUser(request.getSession());
            courseTakeService.sendWithdrawMessage(courseTakes, sender);

            Boolean log = getBoolean(request, "log");
            courseTakeService.withdraw(courseTakes, Boolean.TRUE.equals(log) ? sender : null);
            return redirect(request, "search", "info.delete.success");
        }
    }
    
    /**
     * 选课和退课信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward electWithdrawInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Long stdId = getLong(request, "stdId");
        Long calendarId = getLong(request, "courseTake.task.calendar.id");
        if (!ValidEntityKeyPredicate.INSTANCE.evaluate(stdId)) {
            Long courseTakeId = getLong(request, "courseTakeId");
            CourseTake take = (CourseTake) utilService.get(CourseTake.class, courseTakeId);
            stdId = take.getStudent().getId();
            calendarId = take.getTask().getCalendar().getId();
        }
        // 选课信息
        EntityQuery entityQuery = new EntityQuery(ElectRecord.class, "electRecord");
        entityQuery.add(new Condition("electRecord.student.id=:stdId", stdId));
        entityQuery.add(new Condition("electRecord.task.calendar.id=:calendarId", calendarId));
        entityQuery.addOrder(new Order("electRecord.electTime"));
        addCollection(request, "electRecords", utilService.search(entityQuery));
        
        // 退课信息
        entityQuery = new EntityQuery(WithdrawRecord.class, "withdrawRecord");
        entityQuery.add(new Condition("withdrawRecord.student.id=:stdId", stdId));
        entityQuery.add(new Condition("withdrawRecord.task.calendar.id=:calendarId", calendarId));
        entityQuery.addOrder(new Order("withdrawRecord.time"));
        addCollection(request, "withdraws", utilService.search(entityQuery));
        request.setAttribute("student", utilService.get(Student.class, stdId));
        return forward(request);
    }
    
    /**
     * 批量设置修读类别
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchSetTakeType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String courseTakeIdSeq = request.getParameter("courseTakeIds");
        CourseTakeType type = (CourseTakeType) baseCodeService.getCode(CourseTakeType.class,
                getLong(request, "courseTakeTypeId"));
        List courseTakes = utilService.load(CourseTake.class, "id", SeqStringUtil
                .transformToLong(courseTakeIdSeq));
        for (Iterator iter = courseTakes.iterator(); iter.hasNext();) {
            CourseTake take = (CourseTake) iter.next();
            take.setCourseTakeType(type);
        }
        utilService.saveOrUpdate(courseTakes);
        return redirect(request, "search", "info.set.success");
    }
    
    /**
     * 上课冲突学生名单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward collisionStdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "courseTake.task.calendar.id");
        TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, calendarId);
        List courseTakes = taskActivityService.collisionCourseTakes(calendar);
        addCollection(request, "courseTakes", courseTakes);
        addCollection(request, "courseTakeTypes", baseCodeService.getCodes(CourseTakeType.class));
        return forward(request);
    }
    
    /**
     * @param courseTakeService
     *            The courseTakeService to set.
     */
    public void setCourseTakeService(CourseTakeService courseTakeService) {
        this.courseTakeService = courseTakeService;
    }
    
    public void setTaskActivityService(TaskActivityService taskActivityService) {
        this.taskActivityService = taskActivityService;
    }
    
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
