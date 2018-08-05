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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-09          Created
 * zq					2007-09-27			修改了保存方法－增加了修改前和修改后的
 * 											记录
 ********************************************************************************/

package com.shufe.web.action.std.alteration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.query.limit.Page;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.AlterMode;
import com.ekingstar.eams.system.basecode.industry.AlterReason;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.ekingstar.security.model.User;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.std.Student;
import com.shufe.model.std.alteration.StdStatus;
import com.shufe.model.std.alteration.StudentAlteration;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.task.CourseTakeService;
import com.shufe.service.std.StudentService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

/**
 * 学籍变动管理界面
 * 
 * @author chaostone
 */
public class StudentAlterationAction extends RestrictionExampleTemplateAction {
    
    protected StudentService studentService;
    
    protected TeachCalendarService teachCalendarService;
    
    protected CourseTakeService courseTakeService;
    
    protected void editSetting(HttpServletRequest request, Entity entity) throws Exception {
        if (entity.isVO()) {
            String stdNo = get(request, "stdNo");
            Student std = studentService.getStudent(stdNo);
            if (null != std) {
                StudentAlteration alteration = (StudentAlteration) entity;
                StdStatus afterStatus = new StdStatus();
                alteration.setStd(std);
                afterStatus.setEnrollYear(std.getEnrollYear());
                afterStatus.setStdType(std.getStdType());
                afterStatus.setDepartment(std.getDepartment());
                afterStatus.setSpeciality(std.getFirstMajor());
                afterStatus.setAspect(std.getFirstAspect());
                afterStatus.setGraduateOn(std.getGraduateOn());
                afterStatus.setState(std.getState());
                afterStatus.setAdminClass(std.getFirstMajorClass());
                alteration.setAfterStatus(afterStatus);
                request.setAttribute("alteration", alteration);
            }
        }
        addCollection(request, "modes", baseCodeService.getCodes(AlterMode.class));
        addCollection(request, "reasons", baseCodeService.getCodes(AlterReason.class));
        addCollection(request, "statuses", baseCodeService.getCodes(StudentState.class));
        setDataRealm(request, hasStdTypeCollege);
    }
    
    public ActionForward searchStuNo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    /**
     * 学生学籍变得的其它处理：成绩、选课信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward processOthers(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long alterationId = getLong(request, "alterationId");
        StudentAlteration alteration = (StudentAlteration) utilService.load(
                StudentAlteration.class, alterationId);
        
        TeachCalendar calendar = teachCalendarService
                .getTeachCalendar(alteration.getAlterBeginOn());
        if (null == calendar) {
            return forward(mapping, request, "error.calendar.onTime.notExists", "error");
        }
        // 获得学生的成绩，内容是发生变动学期和下学期
        EntityQuery query1 = new EntityQuery(CourseGrade.class, "grade");
        Set calendars = new HashSet();
        calendars.add(calendar);
        if (null != calendar.getNext()) {
            calendars.add(calendar.getNext());
        }
        query1.add(new Condition("grade.calendar in (:calendars)", calendars));
        query1.add(new Condition("grade.std = :student", alteration.getStd()));
        query1.addOrder(OrderUtils.parser("grade.calendar.year,grade.calendar.term"));
        addCollection(request, "courseGrades", utilService.search(query1));
        query1.groupBy("grade.calendar.id");
        String selectHQL = "grade.calendar.id,count(*)";
        query1.getOrders().clear();
        query1.setSelect(selectHQL);
        Collection gradeCalendars = utilService.search(query1);
        Map calendarGradeMap = new HashMap();
        for (Iterator it = gradeCalendars.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            calendarGradeMap.put(obj[0].toString(), obj[1]);
        }
        addSingleParameter(request, "calendarGradeMap", calendarGradeMap);
        
        // 获得学生的选课记录，内容同上
        EntityQuery query2 = new EntityQuery(CourseTake.class, "take");
        query2.add(new Condition("take.task.calendar in (:calendars)", calendars));
        query2.add(new Condition("take.student = :student", alteration.getStd()));
        query2.addOrder(OrderUtils.parser("take.task.calendar.year,take.task.calendar.term"));
        addCollection(request, "courseTakes", utilService.search(query2));
        query2.groupBy("take.task.calendar.id");
        query2.getOrders().clear();
        selectHQL = "take.task.calendar.id,count(*)";
        query2.setSelect(selectHQL);
        Collection takeCalendars = utilService.search(query2);
        Map calendarTakeMap = new HashMap();
        for (Iterator it = takeCalendars.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            calendarTakeMap.put(obj[0].toString(), obj[1]);
        }
        addSingleParameter(request, "calendarTakeMap", calendarTakeMap);
        
        addSingleParameter(request, "alteration", alteration);
        addCollection(request, "calendars", calendars);
        return forward(request);
    }
    
    public ActionForward removeRelation(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String courseGradeSeq = get(request, "courseGradeIds");
        String courseTakeSeq = get(request, "courseTakeIds");
        Collection courseGrades = null;
        if (StringUtils.isNotEmpty(courseGradeSeq)) {
            Long[] courseGradeIds = SeqStringUtil.transformToLong(courseGradeSeq);
            courseGrades = utilService.load(CourseGrade.class, "id", courseGradeIds);
        }
        Collection courseTakes = null;
        if (StringUtils.isNotEmpty(courseTakeSeq)) {
            Long[] courseTakeIds = SeqStringUtil.transformToLong(courseTakeSeq);
            courseTakes = utilService.load(CourseTake.class, "id", courseTakeIds);
        }
        Student student = (Student) utilService.load(Student.class, getLong(request, "stdId"));
        
        if (null != courseGrades) {
            utilService.remove(courseGrades);
        }
        if (null != courseTakes) {
            User sender = (User) utilService.load(User.class, "name", student.getCode()).iterator()
                    .next();
            courseTakeService.sendWithdrawMessage((List) courseTakes, sender);
            courseTakeService.withdraw((List) courseTakes, sender);
        }
        
        return redirect(request, "search", "info.action.success");
    }
    
    protected ActionForward saveAndForwad(HttpServletRequest request, Entity entity)
            throws Exception {
        StudentAlteration alteration = (StudentAlteration) entity;
        StdStatus stdStatus = (StdStatus) populateEntity(request, StdStatus.class,
                "alteration.afterStatus");
        alteration.setAfterStatus(stdStatus);
        if (null != alteration.getStd().getStudentStatusInfo()) {
            request.setAttribute("std_outof_shool", new Boolean(!alteration.getStd().isInSchool()));
        }
        if (!ValidEntityPredicate.INSTANCE.evaluate(alteration.getStd())) {
            return forward(request, new Action("", "search"), "error.std.notExists");
        }
        Student std = (Student) utilService.load(Student.class, alteration.getStd().getId());
        alteration.setStd(std);
        if (alteration.getId() == null || alteration.getBeforeStatus() == null) {
            alteration.beforeStatusSetting();
        }
        alteration.setAlterBy(getUser(request.getSession()));
        
        List toBeSaved = new ArrayList();
        // 新增或修改并设置为应用时，修改
        Boolean isApply = getBoolean(request, "isApply");
        if (isApply != null && Boolean.TRUE.equals(isApply)) {
            alteration.apply();
            AdminClass adminClassBefore = std.getFirstMajorClass();
            if (null != adminClassBefore) {
                adminClassBefore.getStudents().remove(std);
            }
            if (null != stdStatus.getAdminClass()) {
                AdminClass adminClassAfter = (AdminClass) utilService.load(AdminClass.class,
                        stdStatus.getAdminClass().getId());
                adminClassAfter.getStudents().add(std);
                std.getAdminClasses().add(adminClassAfter);
            }else{
                std.getAdminClasses().add(null);
            }
        }
        toBeSaved.add(alteration);
        utilService.saveOrUpdate(toBeSaved);
        
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 按变动日期进行统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward stat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            EntityQuery query = buildQuery(request);
            query.setLimit(null);
            query.getOrders().clear();
            List groupBy = new ArrayList();
            groupBy.add("alteration.mode.id");
            query.setGroups(groupBy);
            query.setSelect("alteration.mode.id, count(*)");
            
            List statResult = (List) utilService.search(query);
            List results = new ArrayList();
            for (Iterator it = statResult.iterator(); it.hasNext();) {
                Object[] obj = (Object[]) it.next();
                obj[0] = utilService.load(AlterMode.class, new Long(String.valueOf(obj[0])));
                results.add(obj);
            }
            
            addCollection(request, "results", results);
            return forward(request, "statResult");
        } catch (Exception e) {
            addCollection(request, entityName + "s", Page.EMPTY_PAGE);
            return forward(request);
        }
    }
    
    /**
     * 查找标准
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            EntityQuery query = buildQuery(request);
            addCollection(request, entityName + "s", utilService.search(query));
            return forward(request);
        } catch (Exception e) {
            addCollection(request, entityName + "s", Page.EMPTY_PAGE);
            return forward(request);
        }
    }
    
    protected EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(StudentAlteration.class, getEntityName());
        populateConditions(request, query);
        query.getConditions()
                .addAll(
                        QueryRequestSupport.extractConditions(request, Student.class, "std",
                                "std.type.id"));
        Long stdTypeId = getLong(request, "std.type.id");
        DataRealmUtils.addDataRealms(query, new String[] { "alteration.std.type.id",
                "alteration.std.department.id" }, getDataRealmsWith(stdTypeId, request));
        // 加入变动日期查询条件
        Date alterFormDate = RequestUtils.getDate(request, "alterFromDate");
        Date alterToDate = RequestUtils.getDate(request, "alterToDate");
        if (null != alterFormDate) {
            query.add(new Condition("alteration.alterBeginOn >= (:fromDate)", alterFormDate));
        }
        if (null != alterToDate) {
            query.add(new Condition("alteration.alterBeginOn <= (:toDate)", alterToDate));
        }
        
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return query;
    }
    
    protected void indexSetting(HttpServletRequest request) throws Exception {
        addCollection(request, "modes", baseCodeService.getCodes(AlterMode.class));
    }
    
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
    
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    
    public void setCourseTakeService(CourseTakeService courseTakeService) {
        this.courseTakeService = courseTakeService;
    }
}
