//$Id: MakeupGradeAction.java Feb 29, 2008 9:11:09 PM chaostone Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2008. All rights reserved.
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
 * ============   ============  ============
 * chaostone      Feb 29, 2008  Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.mvc.struts.misc.ForwardSupport;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.ExamStatus;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.baseinfo.model.Course;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.grade.gp.GradePointRuleService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 缓补考成绩打印、管理
 * 
 * @author chaostone
 * 
 */
public class MakeupGradeAction extends CalendarRestrictionSupportAction {
    
    protected GradeService gradeService;
    
    protected TeachTaskService teachTaskService;
    
    protected GradePointRuleService gradePointRuleService;
    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    /**
     * 管理信息主页面
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
        String stdTypeDataRealm = getStdTypeIdSeq(request);
        String departDataRealm = getDepartmentIdSeq(request);
        addCollection(request, "teachDepartList", teachTaskService.getTeachDepartsOfTask(
                stdTypeDataRealm, departDataRealm, (TeachCalendar) request
                        .getAttribute(Constants.CALENDAR)));
        return forward(request);
    }
    
    /**
     * 查询补缓考课程列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward courseList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "calendar.id");
        EntityQuery query = new EntityQuery(ExamTake.class, "examTake");
        populateConditions(request, query);
        String selectStatement = "select distinct examTake.task.course,examTake.task.arrangeInfo.teachDepart,examTake.activity.time";
        query.setSelect(selectStatement);
        query.add(new Condition("examTake.examType.id in (:examTypeId)", new Object[] {
                ExamType.DELAY_AGAIN, ExamType.AGAIN, ExamType.DELAY }));
        query.add(new Condition(" examTake.calendar.id=:calendarId", calendarId));
        query.join("left", "examTake.activity", "activity");
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "courseList", utilService.search(query));
        return forward(request);
        
    }
    
    /**
     * 查询补缓考课程 -- 学生成绩录入状态
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getExamTakeState(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "calendar.id");
        EntityQuery query = new EntityQuery(ExamActivity.class, "activity");
        populateConditions(request, query);
        String selectStatement = "select distinct activity.task.course,activity.task.arrangeInfo.teachDepart,activity.time";
        query.setSelect(selectStatement);
        query.add(new Condition("activity.examType.id=:examTypeId", ExamType.DELAY_AGAIN));
        query.add(new Condition(" activity.calendar.id=:calendarId", calendarId));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "courseList", utilService.search(query));
        return forward(request);
        
    }
    
    /**
     * 补缓考 登分册
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward gradeTable(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String courseIds = get(request, "courseIds");
        Long calendarId = getLong(request, "calendar.id");
        String courseId[] = StringUtils.split(courseIds, ",");
        List examTasks = new ArrayList();
        for (int i = 0; i < courseId.length; i++) {
            String params[] = StringUtils.split(courseId[i], "@");
            EntityQuery query = new EntityQuery(ExamTake.class, "examTake");
            query.add(new Condition("examTake.examType.id in (:examTypeId)", new Object[] {
                    ExamType.DELAY_AGAIN, ExamType.AGAIN, ExamType.DELAY }));
            query.add(new Condition(" examTake.calendar.id=:calendarId", calendarId));
            query.add(new Condition(" examTake.task.course.id=:courseId", Long.valueOf(params[0])));
            query.add(new Condition(" examTake.task.arrangeInfo.teachDepart.id=:teachDepartId",
                    Long.valueOf(params[1])));
            examTasks.add(utilService.search(query));
        }
        addCollection(request, "examTasks", examTasks);
        addSingleParameter(request, "calendar", teachCalendarService.getTeachCalendar(calendarId));
        return forward(request);
    }
    
    /**
     * 查看
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward gradeAllInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(CourseGrade.class, "courseGrade");
        populateConditions(request, query);
        query.join("courseGrade.examGrades", "examGrade");
        query.add(new Condition("examGrade.gradeType.id in (:gradeTypeId)", new Long[] {
                GradeType.DELAY, GradeType.MAKEUP, GradeType.FINAL, GradeType.GA }));
        query.add(new Condition("courseGrade.score is not null"));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "courseGrades", utilService.search(query));
        addSingleParameter(request, "calendar", teachCalendarService.getTeachCalendar(getLong(
                request, "courseGrade.calendar.studentType.id"), get(request,
                "courseGrade.calendar.year"), get(request, "courseGrade.calendar.term")));
        addSingleParameter(request, "DELAY", baseCodeService.getCode(GradeType.class,
                GradeType.DELAY));
        addSingleParameter(request, "MAKEUP", baseCodeService.getCode(GradeType.class,
                GradeType.MAKEUP));
        addSingleParameter(request, "FINAL", baseCodeService.getCode(GradeType.class,
                GradeType.FINAL));
        addSingleParameter(request, "GA", baseCodeService.getCode(GradeType.class, GradeType.GA));
        return forward(request);
    }
    
    protected List getExamTakes(String courseIds, Long examTypeId, Long calendarId) {
        String params[] = StringUtils.split(courseIds, "@");
        /*
         * List examTakeList = utilService .searchHQLQuery("from ExamTake examTake where
         * examTake.calendar.id=" + calendarId + " and examTake.task.course.id=" + params[0] + " and
         * examTake.task.arrangeInfo.teachDepart.id=" + params[1] + " and examTake.examType.id=" +
         * examTypeId);
         */
        EntityQuery query = new EntityQuery(ExamTake.class, "examTake");
        query.add(new Condition("examTake.examType.id in (:examTypeId)", new Object[] {
                ExamType.DELAY_AGAIN, ExamType.AGAIN, ExamType.DELAY }));
        query.add(new Condition(" examTake.calendar.id=:calendarId", calendarId));
        query.add(new Condition(" examTake.task.course.id=:courseId", Long.valueOf(params[0])));
        query.add(new Condition(" examTake.task.arrangeInfo.teachDepart.id=:teachDepartId", Long
                .valueOf(params[1])));
        
        return (List) utilService.search(query);
    }
    
    /**
     * 仅录入成绩
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchAddGrade(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        getExamTakeAndMakeupExam(request);
        addSingleParameter(request, "MAKEUP", baseCodeService.getCode(ExamType.class,
                ExamType.AGAIN));
        return forward(request);
    }
    
    /**
     * 查看成绩
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward gradeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        getExamTakeAndMakeupExam(request);
        return forward(request);
    }
    
    protected void getExamTakeAndMakeupExam(HttpServletRequest request) {
        String courseIds = get(request, "courseIds");
        Long examTypeId = ExamType.DELAY_AGAIN;
        Long calendarId = getLong(request, "calendar.id");
        List examTakeList = getExamTakes(courseIds, examTypeId, calendarId);
        Map examGradeMap = new HashMap();
        for (Iterator iter = examTakeList.iterator(); iter.hasNext();) {
            ExamTake examTake = (ExamTake) iter.next();
            CourseGrade grade = examTake.getCourseTake().getCourseGrade();
            GradeType gradeType = null;
            if (examTake.getExamType().getId().equals(ExamType.DELAY)) {
                gradeType = new GradeType(GradeType.DELAY);
            } else {
                gradeType = new GradeType(GradeType.MAKEUP);
            }
            if (null != grade) {
                ExamGrade examGrade = grade.getExamGrade(gradeType);
                examGradeMap.put(examTake.getId().toString(), examGrade);
            }
        }
        addCollection(request, "examTakeList", examTakeList);
        addSingleParameter(request, "examGradeMap", examGradeMap);
        addSingleParameter(request, "calendar", teachCalendarService.getTeachCalendar(calendarId));
        String params[] = StringUtils.split(courseIds, "@");
        addSingleParameter(request, "course", utilService.load(
                com.shufe.model.system.baseinfo.Course.class, Long.valueOf(params[0].toString())));
        addSingleParameter(request, "teachDepart", utilService.load(Department.class, Long
                .valueOf(params[1].toString())));
    }
    
    /**
     * 发布补考成绩
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward publishGradeBack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String courseIds = get(request, "courseIds");
        String courseId[] = StringUtils.split(courseIds, ",");
        Long calendarId = getLong(request, "calendar.id");
        Long examTypeId = ExamType.DELAY_AGAIN;
        for (int i = 0; i < courseId.length; i++) {
        	 List examTakeList = getExamTakes(courseId[i], examTypeId, calendarId);
             GradeType gradeType = new GradeType(new Long(4));
             for (Iterator iter = examTakeList.iterator(); iter.hasNext();) {
                 ExamTake examTake = (ExamTake) iter.next();
                 CourseGrade grade = examTake.getCourseTake().getCourseGrade();
                 ExamGrade examGrade = grade.getExamGrade(gradeType);
                 if (null == examGrade) {
                     gradeType = new GradeType(new Long(6));
                     examGrade = grade.getExamGrade(gradeType);
                 }
                 if(null == examGrade){
                	 return forwardError(mapping, request, new String[] { "exam.apply.f",""});
                 }
                 examGrade.setStatus(new Integer(2));
                 utilService.saveOrUpdate(examGrade);
             }
        }
        return redirect(request, "courseList", "info.save.success", "calendar.id=" + calendarId);
    }
    
    /**
     * 保存批量录入的成绩
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchSaveCourseGrade(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String courseIds = request.getParameter("courseIds");
        String gradeState = request.getParameter("grade.state");
        Long examTypeId = ExamType.DELAY_AGAIN;
        Long calendarId = getLong(request, "calendar.id");
        List activities = getExamTakes(courseIds, examTypeId, calendarId);
        List grades = new ArrayList();
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            ExamTake examTake = (ExamTake) iter.next();
            Float score = RequestUtils.getFloat(request, examTake.getId().toString());
            if (null != score) {
                CourseGrade grade = getCourseGrade(examTake.getStd(), examTake.getTask()
                        .getCalendar(), examTake.getTask().getCourse());
                GradeType gradeType = null;
                if (examTake.getExamType().getId().equals(ExamType.DELAY)) {
                    gradeType = new GradeType(GradeType.DELAY);
                } else {
                    gradeType = new GradeType(GradeType.MAKEUP);
                }
                if (null != grade) {
                    ExamGrade examGrade = grade.getExamGrade(gradeType);
                    if (null == examGrade) {
                        examGrade = new ExamGrade();
                        examGrade.setGradeType(gradeType);
                        examGrade.setStatus(Integer.valueOf(gradeState));
                        examGrade.getExamStatus().setId(ExamStatus.NORMAL);
                        examGrade.setScore(score);
                        grade.addExamGrade(examGrade);
                    } else {
                        examGrade.setStatus(Integer.valueOf(gradeState));
                        examGrade.updateScore(score, getUser(request.getSession()));
                    }
                    grade.calcGA().calcScore().updatePass().calcGP(
                            gradePointRuleService.getGradePointRule(grade.getStd().getType(), grade
                                    .getMarkStyle()));
                    grades.add(grade);
                }
            }
        }
        utilService.saveOrUpdate(grades);
        saveErrors(request.getSession(), ForwardSupport
                .buildMessages(new String[] { "info.save.success" }));
        return mapping.findForward("actionResult");
    }
    
    private CourseGrade getCourseGrade(Student std, TeachCalendar calendar, Course course) {
        EntityQuery query = new EntityQuery(CourseGrade.class, "cg");
        query.add(new Condition("cg.std =:std and cg.calendar=:calendar and cg.course=:course",
                std, calendar, course));
        List grades = (List) utilService.search(query);
        if (grades.isEmpty()) {
            return null;
        } else {
            return (CourseGrade) grades.get(0);
        }
    }
    
    public void setGradePointRuleService(GradePointRuleService gradePointRuleService) {
        this.gradePointRuleService = gradePointRuleService;
    }
    
    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }
}
