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
 * chaostone             2007-1-15            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.grade;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.eams.system.basecode.industry.ExamStatus;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.teach.program.service.GradeFilter;
import com.ekingstar.eams.teach.program.service.SubstituteCourseService;
import com.ekingstar.eams.teach.program.service.impl.SubstituteGradeFilter;
import com.shufe.model.course.arrange.exam.ExamApplyParam;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.CourseGradeComparator;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.grade.report.StdGrade;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.exam.ExamApplyParamService;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.grade.gp.GradePointService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.std.StudentService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.DispatchBasicAction;
import com.shufe.web.action.course.grade.report.GradeReportSetting;
import com.shufe.web.helper.StdGradeHelper;
import com.shufe.web.helper.StdSearchHelper;

/**
 * 学生个人成绩
 * 
 * @author chaostone
 */
public class PersonGradeAction extends DispatchBasicAction {
  GradePointService gradePointService;

  GradeService gradeService;

  StdGradeHelper stdGradeHelper;

  TeachTaskService teachTaskService;

  StudentService studentService;

  TeachCalendarService teachCalendarService;

  ExamApplyParamService examApplyParamService;

  public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Student std = getStudentFromSession(request.getSession());
    this.stdGradeHelper.searchStdGrade(request, std, this.gradeService, this.baseCodeService,
        this.gradePointService);
    request.setAttribute("hasSpeciality2ndGrade", this.gradeService.hasSpeciality2ndGrade(std));
    return forward(request);
  }

  public ActionForward examApply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long stdId = getLong(request, "std");
    Long taskId = getLong(request, "task");
    Long calendarId = getLong(request, "calendar");
    Student std = getStudentFromSession(request.getSession());
    TeachTask teachTask = teachTaskService.getTeachTask(taskId);
    Student student = studentService.getStudent(stdId);
    TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(calendarId);
    ExamApplyParam param = examApplyParamService.getExamApplyParams(std);
    CourseTake courseTake = null;
    if (null == param) {
      return forwardError(mapping, request, new String[] { "exam.applytime", "exam.applytime.pass" });
    } else {
      EntityQuery query = new EntityQuery(ExamTake.class, "examTake");
      query.add(new Condition("examTake.task.id=" + taskId));
      query.add(new Condition("examTake.std.id=" + stdId));
      query.add(new Condition("examTake.calendar.id=" + calendarId));
      List list = (List) utilService.search(query);
      if (list.size() != 0) {
        return forwardError(mapping, request, new String[] { "exam.apply", "exam.apply.d" });
      } else {
        ExamTake examTake = new ExamTake();
        EntityQuery entityQuery = new EntityQuery(CourseTake.class, "courseTake");
        entityQuery.add(new Condition("courseTake.task.id=" + taskId));
        entityQuery.add(new Condition("courseTake.student.id=" + stdId));
        List courseTakeList = (List) utilService.search(entityQuery);
        if (courseTakeList.size() != 0) {
          courseTake = (CourseTake) courseTakeList.get(0);
        } else {
          return forwardError(mapping, request, new String[] { "exam.apply.z", "exam.apply.x" });
        }
        examTake.setCourseTake(courseTake);
        examTake.setCalendar(teachCalendar);
        examTake.setTask(teachTask);
        examTake.setStd(student);
        examTake.setExamType(new ExamType(new Long(3)));
        examTake.setExamStatus(new ExamStatus(new Long(1)));
        Date dateNow = new Date();
        String userIp = request.getRemoteAddr();
        String applyUser = std.getCode();
        examTake.setUserIp(userIp);
        examTake.setApplyUser(applyUser);
        examTake.setApplyDate(dateNow);
        utilService.saveOrUpdate(examTake);
        return redirect(request, "index", "exam.apply.save", "");
      }

    }

  }

  protected StdSearchHelper stdSearchHelper;
  protected SubstituteCourseService substituteCourseService;

  public void setSubstituteCourseService(SubstituteCourseService substituteCourseService) {
    this.substituteCourseService = substituteCourseService;
  }

  public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
    this.stdSearchHelper = stdSearchHelper;
  }

  /**
   * 打印学生最好成绩（中英文模版）
   */
  public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Student std = getStudentFromSession(request.getSession());
    CourseGradeComparator cmp = new CourseGradeComparator("calendar.yearTerm", Order.ASC == 1,
        baseCodeService.getCodes(GradeType.class));

    Long majorTypeId = getLong(request, "majorTypeId");
    if (null == majorTypeId) majorTypeId = 1L;
    final MajorType majorType = (MajorType) utilService.load(MajorType.class, majorTypeId);

    // 找到参数和成绩比较器
    GradeReportSetting setting = new GradeReportSetting();
    RequestUtil.populate(request, setting, "reportSetting");
    if (StringUtils.isEmpty(setting.getOrder().getProperty())) {
      setting.getOrder().setProperty("calendar.yearTerm");
      setting.getOrder().setDirection(Order.ASC);
    }
    // 默认80
    if (setting.getPageSize().intValue() < 0) setting.setPageSize(new Integer(80));

    StdGrade stdGrade = new StdGrade(std, loadCourseGradeInStudent(std, majorType, true), cmp,GradeReportSetting.PASS_GRADE);
    Map stdGPMap = new HashMap();
    stdGPMap.put(std.getId().toString(), gradePointService.statStdGPA(std, stdGrade.getGrades()));
    request.setAttribute("stdGPMap", stdGPMap);
    request.setAttribute("stdGradeReports", Collections.singleton(stdGrade));
    request.setAttribute("gradeType", utilService.load(GradeType.class, new Long(0)));
    request.setAttribute("now", new Date());
    request.setAttribute("setting", setting);
    return forward(request);
  }

  public List<CourseGrade> loadCourseGradeInStudent(Student std, MajorType majorType, boolean isBest) {
    EntityQuery query = new EntityQuery(CourseGrade.class, "grade");
    query.add(new Condition("grade.std = :std", std));
    query.add(new Condition("grade.majorType=:majorTypeId", majorType));
    query.add(new Condition("grade.status = :status", new Integer(Grade.PUBLISHED)));
    List grades = (List) utilService.search(query);
    if (isBest) {
      GradeFilter filter = new SubstituteGradeFilter(substituteCourseService.getStdSubstituteCourses(std,
          majorType));
      grades = filter.filter(grades);
    }
    return grades;
  }

  public void setGradePointService(GradePointService gradePointService) {
    this.gradePointService = gradePointService;
  }

  public void setGradeService(GradeService gradeService) {
    this.gradeService = gradeService;
  }

  public void setStdGradeHelper(StdGradeHelper stdGradeHelper) {
    this.stdGradeHelper = stdGradeHelper;
  }

  public void setExamApplyParamService(ExamApplyParamService examApplyParamService) {
    this.examApplyParamService = examApplyParamService;
  }

  public void setTeachTaskService(TeachTaskService teachTaskService) {
    this.teachTaskService = teachTaskService;
  }

  public void setStudentService(StudentService studentService) {
    this.studentService = studentService;
  }

  public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
    this.teachCalendarService = teachCalendarService;
  }

}
