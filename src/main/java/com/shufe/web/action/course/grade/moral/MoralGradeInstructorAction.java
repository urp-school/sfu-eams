package com.shufe.web.action.course.grade.moral;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.mvc.struts.misc.ForwardSupport;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.security.User;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.grade.GradeInputSwitch;
import com.shufe.model.course.grade.MoralGrade;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.web.action.common.CalendarRestrictionExampleTemplateAction;
import com.shufe.web.helper.BaseInfoSearchHelper;
import com.shufe.web.helper.RestrictionHelper;

public class MoralGradeInstructorAction extends CalendarRestrictionExampleTemplateAction {
  protected BaseInfoSearchHelper baseInfoSearchHelper;

  public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Teacher teacher = getTeacherFromSession(request.getSession());
    EntityQuery query = new EntityQuery(AdminClass.class, "adminClass");
    query.add(new Condition("adminClass.instructor.id=:instructId", teacher.getId()));
    query.setSelect("distinct adminClass.stdType");
    List stdTypes = (List) utilService.search(query);
    query = new EntityQuery(AdminClass.class, "adminClass");
    query.add(new Condition("adminClass.instructor.id=:instructId", teacher.getId()));
    query.setSelect("distinct adminClass.department");
    List departs = (List) utilService.search(query);
    request.setAttribute(RestrictionHelper.STDTYPES_KEY, stdTypes);
    request.setAttribute(RestrictionHelper.DEPARTS_KEY, departs);
    if (!CollectionUtils.isEmpty(stdTypes)) {
      setCalendar(request, (StudentType) stdTypes.get(0));
    }
    return forward(request);
  }

  public ActionForward classList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    EntityQuery query = baseInfoSearchHelper.buildAdminClassQuery(request);
    query.add(new Condition("adminClass.instructor.id=:instructId", getTeacherFromSession(
        request.getSession()).getId()));
    addCollection(request, "adminClasses", utilService.search(query));
    return forward(request);
  }

  /**
   * 查询成绩不能录入的开关项
   * 
   * @param request
   * @return
   */
  protected GradeInputSwitch getCannotInputSwitch(HttpServletRequest request, TeachCalendar calendar) {
    User user = getUser(request.getSession());
    boolean isTeacher = user.isCategory(EamsRole.STD_USER);
    if (!isTeacher) return null;
    else {
      EntityQuery query = new EntityQuery(GradeInputSwitch.class, "switch");
      query.add(new Condition("switch.calendar=:calendar", calendar));
      List rs = (List) utilService.search(query);
      if (!rs.isEmpty()) {
        GradeInputSwitch gradeInputSwitch = (GradeInputSwitch) rs.get(0);
        if (!gradeInputSwitch.checkOpen(new Date())
            && gradeInputSwitch.getGradeTypes().contains(new GradeType(GradeType.MORAL))) { return gradeInputSwitch; }
      }
      return null;
    }
  }

  /**
   * 录入成绩
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward inputGrade(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long adminClassId = getLong(request, "adminClassId");
    Long calendarId = getLong(request, "calendarId");
    AdminClass adminClass = (AdminClass) utilService.get(AdminClass.class, adminClassId);
    TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, calendarId);
    // 判断是否有不允许录入的开关
    GradeInputSwitch gradeInputSwitch = getCannotInputSwitch(request, calendar);
    if (null != gradeInputSwitch) {
      request.setAttribute("gradeInputSwitch", gradeInputSwitch);
      return forward(request, "cannotInput");
    }
    List rs = getMoralGrades(adminClassId, calendarId);
    Map gradeMap = new HashMap();
    for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
      MoralGrade grade = (MoralGrade) iterator.next();
      gradeMap.put(grade.getStd().getId().toString(), grade);
    }
    request.setAttribute("calendar", calendar);
    request.setAttribute("adminClass", adminClass);
    request.setAttribute("gradeMap", gradeMap);
    return forward(request);
  }

  /**
   * 保存成绩
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward saveGrade(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long adminClassId = getLong(request, "adminClassId");
    Long calendarId = getLong(request, "calendarId");

    AdminClass adminClass = (AdminClass) utilService.get(AdminClass.class, adminClassId);
    TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, calendarId);
    MarkStyle markStyle = (MarkStyle) baseCodeService.getCode(MarkStyle.class, MarkStyle.PERCENT);
    List rs = getMoralGrades(adminClassId, calendarId);
    Map gradeMap = new HashMap();
    for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
      MoralGrade grade = (MoralGrade) iterator.next();
      gradeMap.put(grade.getStd().getId(), grade);
    }
    List removed = new ArrayList();
    List toBeSaved = new ArrayList();
    boolean hasGradeToInput = false;
    for (Iterator iterator = adminClass.getStudents().iterator(); iterator.hasNext();) {
      Student std = (Student) iterator.next();
      String scoreStr = request.getParameter("std_" + std.getId());
      if (null != scoreStr) {
        Float score = getFloat(request, "std_" + std.getId());
        MoralGrade grade = (MoralGrade) gradeMap.get(std.getId());
        if (null != score) {
          if (null == grade) {
            grade = new MoralGrade(std, calendar, markStyle);
          }
          grade.updateScore(score);
          grade.setStatus(new Integer(Grade.CONFIRMED));
          toBeSaved.add(grade);
        } else {
          if (null != grade) {
            removed.add(grade);
          }
          hasGradeToInput = true;
        }
      }
    }
    utilService.saveOrUpdate(toBeSaved);
    utilService.remove(removed);
    saveErrors(request, ForwardSupport.buildMessages(new String[] { "info.save.success" }));
    if (!hasGradeToInput) {
      return redirect(request, "gradeReport", "info.save.success", "&adminClassId=" + adminClassId
          + "&calendarId=" + calendarId);
    } else {
      return redirect(request, "inputGrade", "info.save.success", "&adminClassId=" + adminClassId
          + "&calendarId=" + calendarId);
    }
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
  public ActionForward gradeInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long[] adminClassIds = new Long[] { getLong(request, "adminClassId") };
    if (null == adminClassIds[0]) {
      adminClassIds = SeqStringUtil.transformToLong(request.getParameter("adminClassIds"));
    }
    Long calendarId = getLong(request, "calendarId");
    List rs = getMoralGrades(adminClassIds, calendarId);
    addCollection(request, "moralGrades", rs);
    return forward(request);
  }

  /**
   * 打印报表
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward gradeReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long[] adminClassIds = new Long[] { getLong(request, "adminClassId") };
    if (null == adminClassIds[0]) {
      adminClassIds = SeqStringUtil.transformToLong(request.getParameter("adminClassIds"));
    }
    Long calendarId = getLong(request, "calendarId");

    TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, calendarId);
    List rs = getMoralGrades(adminClassIds, calendarId);
    Map gradeMap = new HashMap();
    for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
      MoralGrade grade = (MoralGrade) iterator.next();
      gradeMap.put(grade.getStd().getId().toString(), grade);
    }
    request.setAttribute("calendar", calendar);
    request.setAttribute("adminClasses", utilService.load(AdminClass.class, "id", adminClassIds));
    request.setAttribute("gradeMap", gradeMap);
    return forward(request);
  }

  private List getMoralGrades(Long[] adminClassIds, Long calendarId) {
    EntityQuery query = new EntityQuery(MoralGrade.class, "moralGrade");
    query.join("moralGrade.std.adminClasses", "adminClass");
    query.add(new Condition("adminClass.id in(:adminClassIds)", adminClassIds));
    query.add(new Condition("moralGrade.calendar.id=:calendarId", calendarId));
    List rs = (List) utilService.search(query);
    return rs;
  }

  private List getMoralGrades(Long adminClassId, Long calendarId) {
    return getMoralGrades(new Long[] { adminClassId }, calendarId);
  }

  /**
   * 删除成绩
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward removeReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long[] adminClassIds = SeqStringUtil.transformToLong(request.getParameter("adminClassIds"));
    Long calendarId = getLong(request, "calendarId");
    List rs = getMoralGrades(adminClassIds, calendarId);
    boolean hasPublished = false;
    for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
      MoralGrade grade = (MoralGrade) iterator.next();
      if (Boolean.TRUE.equals(grade.getIsPublished())) {
        hasPublished = true;
      }
    }
    if (!hasPublished) {
      utilService.remove(rs);
      return redirect(request, "classList", "info.action.success");
    } else {
      return redirect(request, "classList", "info.action.failure");
    }
  }

  public void setBaseInfoSearchHelper(BaseInfoSearchHelper baseInfoSearchHelper) {
    this.baseInfoSearchHelper = baseInfoSearchHelper;
  }
}
