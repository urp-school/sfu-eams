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
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.basecode.industry.ExamDelayReason;
import com.ekingstar.eams.system.basecode.industry.ExamStatus;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.exam.ExamTakeService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 考试记录响应类
 * 
 * @author chaostone
 * 
 */
public class ExamTakeAction extends CalendarRestrictionSupportAction {
    
    private int courseCodeCount = 10;
    
    private ExamTakeService examTakeService;
    
    private TeachTaskService teachTaskService;
    
    /**
     * 考试结果管理主页面
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
        String stdTypeDataRealm = getStdTypeIdSeq(request);
        String departDataRealm = getDepartmentIdSeq(request);
        setCalendarDataRealm(request, hasStdTypeCollege);
        addCollection(request, "examTypes", baseCodeService.getCodes(ExamType.class));
        addCollection(request, "examStatuses", baseCodeService.getCodes(ExamStatus.class));
        addCollection(request, "courseTypes", teachTaskService.getCourseTypesOfTask(
                stdTypeDataRealm, departDataRealm, (TeachCalendar) request
                        .getAttribute(Constants.CALENDAR)));
        return forward(request);
    }
    
    public ActionForward getExamTake(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long examTakeId = getLong(request,"examTakeId");
        EntityQuery entityQuery = new EntityQuery(ExamTake.class,"examTake");
        entityQuery.add(new Condition("examTake.id="+examTakeId));
        List list = (List)utilService.search(entityQuery);
        ExamTake examTake = (ExamTake)list.get(0);
        request.setAttribute("examTake", examTake);
        return forward(request);
    }
    
    /**
     * 考试名单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward examTakeList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery entityQuery = buildExamTakeQuery(request);
        addCollection(request, "takes", utilService.search(entityQuery));
        addCollection(request, "examStatuses", baseCodeService.getCodes(ExamStatus.class));
        return forward(request);
    }
    
    private EntityQuery buildExamTakeQuery(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(ExamTake.class, "take");
        QueryRequestSupport.populateConditions(request, entityQuery,
                "take.examType.id,take.std.type.id");
        entityQuery.add(new Condition("take.calendar.id =" + request.getParameter("calendar.id")));
        Long examTypeId = getLong(request, "take.examType.id");
        if (ExamType.DELAY_AGAIN.equals(examTypeId)) {
            entityQuery.add(new Condition(
                    "instr(:depayAgainExamTypeId,','||take.examType.id||',')>0",
                    ExamType.delayAndAgainExamTypeIds));
        } else {
            entityQuery.add(new Condition("take.examType.id=" + examTypeId));
        }
        Long stdTypeId = getLong(request, "take.std.type.id");
        if (null != stdTypeId) {
            entityQuery.add(new Condition("take.std.type.id in(:stdTypeTreeIds)", restrictionHelper
                    .getStdTypeIdsOf(stdTypeId)));
        }
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.setOrders(OrderUtils.parser(request.getParameter("orderBy")));
        return entityQuery;
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery entityQuery = buildExamTakeQuery(request);
        entityQuery.setLimit(null);
        return utilService.search(entityQuery);
    }
    
    /**
     * 处理缓考申请列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward checkApplies(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] takeIds = SeqStringUtil.transformToLong((request.getParameter("examTakeIds")));
        Collection takes = utilService.load(ExamTake.class, "id", takeIds);
        Boolean agree = getBoolean(request, "agree");
        for (Iterator iter = takes.iterator(); iter.hasNext();) {
            ExamTake take = (ExamTake) iter.next();
            if (Boolean.TRUE.equals(agree)) {
                take.setExamStatus(new ExamStatus(ExamStatus.DELAY));
            } else {
                take.setExamStatus(new ExamStatus(ExamStatus.NORMAL));
                take.setRemark(null);
                take.setDelayReason(null);
            }
        }
        utilService.saveOrUpdate(takes);
        return redirect(request, "examTakeList", "info.set.success");
    }
    
    /**
     * 没有参加考试学生名单
     */
    public ActionForward noTakeStdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long examTypeId = getLong(request, "take.examType.id");
        request.setAttribute("examType", baseCodeService.getCode(ExamType.class, examTypeId));
        Long calendarId = getLong(request, "calendar.id");
        EntityQuery entityQuery = null;
        if (ExamType.AGAIN.equals(examTypeId)) {
            entityQuery = new EntityQuery();
            entityQuery.setSelect("take,grade");
            entityQuery.setFrom("CourseGrade grade,CourseTake take");
            entityQuery.add(new Condition("take.task=grade.task and take.student=grade.std"));
            entityQuery.add(new Condition("grade.calendar.id=:calendarId", calendarId));
            String againCondition = (String) SystemConfigLoader.getConfig().getConfigItemValue(
                    "course.arrange.exam.againCondition");
            entityQuery.add(new Condition(againCondition));
            // "grade.score is not null and grade.score>0 and grade.isPass =false"));
            request.setAttribute("delayExamId", ExamType.DELAY);
        } else if (ExamType.DELAY.equals(examTypeId)) {
            entityQuery = new EntityQuery(CourseTake.class, "take");
            entityQuery
                    .add(new Condition(
                            "exists(from ExamTake eTake where eTake.calendar.id="
                                    + calendarId
                                    + " and eTake.task=take.task and eTake.std=take.student and eTake.delayReason != null)"));
        } else {
            entityQuery = new EntityQuery(CourseTake.class, "take");
            entityQuery
                    .add(new Condition(
                            " take.task.calendar.id=:calendarId "
                                    + " and  exists (select activity.id from ExamActivity activity where activity.task.id=take.task.id"
                                    + " and activity.examType.id in (:examTypeId))", calendarId,
                            new Object[] { ExamType.DELAY_AGAIN, ExamType.AGAIN, ExamType.DELAY }));
        }
        entityQuery
                .add(new Condition(
                        " not exists( from ExamTake examTake where examTake.task=take.task and examTake.std=take.student and examTake.calendar.id=:calendarId and examTake.examType.id in (:examTypeId))",
                        calendarId, new Object[] { ExamType.DELAY_AGAIN, ExamType.AGAIN,
                                ExamType.DELAY }));
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "courseTakes", utilService.search(entityQuery));
        if (ExamType.AGAIN.equals(examTypeId)) {
            return forward(request, "noTakeAgainStdList");
        } else {
            return forward(request);
        }
    }
    
    /**
     * 冲突学生名单<br>
     * TODO 暂时没有考虑取消考试资格和缓考的考试情况
     */
    public ActionForward collisionStdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "calendar.id");
        Long examTypeId = getLong(request, "take.examType.id");
        TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, calendarId);
        ExamType examType = (ExamType) utilService.get(ExamType.class, examTypeId);
        Collection stds = examTakeService.collisionStds(calendar, examType);
        addCollection(request, "stds", stds);
        return forward(request);
    }
    
    /**
     * 批量设置学生的考试情况
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editExamStatus(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String examTakeIdSeq = request.getParameter("examTakeIds");
        Long[] examTakeIds = SeqStringUtil.transformToLong(examTakeIdSeq);
        Collection examTakes = utilService.load(ExamTake.class, "id", examTakeIds);
        addCollection(request, "examTakes", examTakes);
        Long examStatusId = getLong(request, "examTake.examStatus.id");
        ExamStatus examStatus = (ExamStatus) baseCodeService
                .getCode(ExamStatus.class, examStatusId);
        request.setAttribute("examStatus", examStatus);
        if (examStatusId.equals(ExamStatus.DELAY)) {
            addCollection(request, "examDelayReasons", baseCodeService
                    .getCodes(ExamDelayReason.class));
        }
        return forward(request);
    }
    
    /**
     * 批量更新学生参加考试情况
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateExamStatus(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String examTakeIdSeq = request.getParameter("examTakeIds");
        Long[] examTakeIds = SeqStringUtil.transformToLong(examTakeIdSeq);
        Collection examTakes = utilService.load(ExamTake.class, "id", examTakeIds);
        String remark = request.getParameter("examTake.remark");
        Long examStatusId = getLong(request, "examTake.examStatus.id");
        ExamStatus examStatus = null;
        if (ValidEntityKeyPredicate.INSTANCE.evaluate(examStatusId)) {
            examStatus = (ExamStatus) baseCodeService.getCode(ExamStatus.class, examStatusId);
        }
        Long delayReasonId = getLong(request, "examTake.delayReason.id");
        ExamDelayReason examDelayReason = null;
        if (ValidEntityKeyPredicate.INSTANCE.evaluate(delayReasonId)) {
            examDelayReason = (ExamDelayReason) baseCodeService.getCode(ExamDelayReason.class,
                    delayReasonId);
        }
        for (Iterator iter = examTakes.iterator(); iter.hasNext();) {
            ExamTake take = (ExamTake) iter.next();
            take.setRemark(remark);
            take.setExamStatus(examStatus);
            take.setDelayReason(examDelayReason);
        }
        utilService.saveOrUpdate(examTakes);
        return redirect(request, "examTakeList", "info.update.success");
    }
    
    /**
     * 删除参考信息
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
        String examTakeIdSeq = get(request, "examTakeIds");
        Long[] examTakeIds = SeqStringUtil.transformToLong(examTakeIdSeq);
        Collection examTakes = utilService.load(ExamTake.class, "id", examTakeIds);
        utilService.remove(examTakes);
        return redirect(request, "examTakeList", "info.delete.success");
    }
    
    /**
     * 生成考试名单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward genExamTake(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String courseTakeIds = request.getParameter("courseTakeIds");
        Long calendarId = getLong(request, "calendar.id");
        Long examTypeId = getLong(request, "take.examType.id");
        List courseTakes = utilService.load(CourseTake.class, "id", SeqStringUtil
                .transformToLong(courseTakeIds));
        List examTakes = new ArrayList();
        for (Iterator iter = courseTakes.iterator(); iter.hasNext();) {
            CourseTake take = (CourseTake) iter.next();
            ExamTake newTake = new ExamTake();
            newTake.setActivity(null);
            newTake.setCalendar(new TeachCalendar(calendarId));
            newTake.setCourseTake(take);
            newTake.setTask(take.getTask());
            newTake.setStd(take.getStudent());
            newTake.setDelayReason(null);
            newTake.setExamType(new ExamType(examTypeId));
            newTake.setExamStatus(new ExamStatus(ExamStatus.NORMAL));
            examTakes.add(newTake);
        }
        utilService.saveOrUpdate(examTakes);
        return redirect(request, "noTakeStdList", "info.gen.success", "&calendar.id=" + calendarId
                + "&take.examType.id=" + examTypeId);
    }
    
    public ActionForward statTake(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "calendar.id");
        Long examTypeId = getLong(request, "take.examType.id");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
        ExamType examType = (ExamType) baseCodeService.getCode(ExamType.class, examTypeId);
        addCollection(request, "takeOfTurn", examTakeService.statTakeCountWithTurn(calendar,
                examType));
        addCollection(request, "takeOfCourse", examTakeService.statTakeCountInCourse(calendar,
                examType));
        request.setAttribute("calendar", calendar);
        request.setAttribute("examType", examType);
        return forward(request);
    }
    
    public void setExamTakeService(ExamTakeService examTakeService) {
        this.examTakeService = examTakeService;
    }
    
    /**
     * 添加应考记录
     * 
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    /**
     * 添加应考记录
     * 
     */
    public ActionForward editNextForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "courseTask.task.calendar.id");
        Long examTypeId = getLong(request, "examType.id");
        String stdNo = get(request, "courseTask.student.code");
        List courseTaskList = new ArrayList();
        List courseTakeList = new ArrayList();
        CourseTake courseTake = null;
        for (int i = 1; i <= courseCodeCount; i++) {
            String courseCodei = get(request, "courseCode" + i);
            if (null != courseCodei && !courseCodei.equals("")) {
                EntityQuery courseTakeQyery = new EntityQuery(CourseTake.class, "courseTask");
                courseTakeQyery.add(new Condition("courseTask.student.code=:stdNo", stdNo));
                courseTakeQyery
                        .add(new Condition("courseTask.task.course.code=:code", courseCodei));
                courseTakeQyery.add(new Condition("courseTask.task.calendar.id=:calendarId",
                        calendarId));
                courseTaskList = (List) utilService.search(courseTakeQyery);
                if (courseTaskList.isEmpty()) {
                    return redirect(request, "edit", "field.couserAndstdNo.notExists",
                            "&calendar.id=" + calendarId + "&take.examType.id=" + examTypeId);
                }
                for (Iterator iter = courseTaskList.iterator(); iter.hasNext();) {
                    courseTake = (CourseTake) iter.next();
                    courseTakeList.add(courseTake);
                    request.setAttribute("courseTakes", courseTakeList);
                    ExamTake take = courseTake.getExamTake(new ExamType(examTypeId));
                    if (null != take) {
                        return redirect(request, "edit", "field.examTake.Exists", "&calendar.id="
                                + calendarId + "&take.examType.id=" + examTypeId);
                    }
                }
            } else {
                continue;
            }
        }
        if (courseTakeList.isEmpty()) {
            return redirect(request, "edit", "field.notCourserCode", "&calendar.id=" + calendarId
                    + "&take.examType.id=" + examTypeId);
        }
        request.setAttribute("courseTake", courseTake);
        request.setAttribute("examTypeId", examTypeId);
        request.setAttribute("calendarId", calendarId);
        return forward(request);
    }
    
    /**
     * 保存应考记录
     * 
     */
    public ActionForward saveExamTake(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long examTypeId = getLong(request, "examTypeId");
        Long calendarId = getLong(request, "calendarId");
        for (int i = 1; i <= courseCodeCount; i++) {
            Long courseIdi = getLong(request, "cousrseId" + i);
            if (null != courseIdi) {
                CourseTake courseTake = (CourseTake) utilService.load(CourseTake.class, courseIdi);
                ExamType examType = (ExamType) utilService.load(ExamType.class, examTypeId);
                ExamTake examTake = new ExamTake(courseTake);
                examTake.setExamType(examType);
                utilService.saveOrUpdate(examTake);
            }
        }
        return redirect(request, "examTakeList", "info.save.success", "&calendar.id=" + calendarId
                + "&take.examType.id=" + examTypeId);
    }
    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
}
