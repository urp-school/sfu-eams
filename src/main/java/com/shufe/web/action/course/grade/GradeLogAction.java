//$Id: GradeCatalogAction.java,v 1.1 2009-7-6 上午11:13:06 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2009-7-6             Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.grade;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.security.User;
import com.shufe.model.course.grade.GradeLog;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.grade.GradeLogService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.std.StudentService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 成绩日志
 * 
 * @author zhouqi
 * 
 */
public class GradeLogAction extends CalendarRestrictionSupportAction {
    
    protected StudentService studentService;
    
    protected TeachTaskService teachTaskService;
    
    protected SimpleDateFormat dateFormatFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    protected GradeLogService gradeLogService;
    
    /**
     * 日志查询
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
        // /////////////// //
        // 准备数据 //
        // /////////////// //
        // 处理教学日历条件
        Long stdTypeId = getLong(request, "stdTypeId");
        String year = get(request, "year");
        String term = get(request, "term");
        TeachCalendar calendar = null;
        if (null != stdTypeId && StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(term)) {
            calendar = teachCalendarService.getTeachCalendar(stdTypeId, year, term);
        }
        // 处理学生条件
        String stdCode = get(request, "stdCode");
        String stdName = get(request, "stdName");
        Student student = null;
        if (StringUtils.isNotEmpty(stdCode)) {
            try {
                student = studentService.getStudent(stdCode);
            } catch (Exception e) {
                String logInfo = stdCode
                        + " of Student CODE been removed or not exists when recording GradeLog!";
                logHelper.info(request, logInfo);
            }
        }
        // 处理教学任务条件
        String taskSeqNo = get(request, "taskSeqNo");
        String courseCode = get(request, "courseCode");
        String courseName = get(request, "courseName");
        TeachTask task = null;
        Course course = null;
        if (null != calendar && StringUtils.isNotEmpty(taskSeqNo)) {
            try {
                task = teachTaskService.getTeachTask(taskSeqNo, calendar);
            } catch (Exception e) {
                String logInfo = taskSeqNo + " of Course CODE in ["
                        + calendar.getStudentType().getName() + " " + calendar.getYear() + " "
                        + calendar.getTerm()
                        + "] of TeachCalendar been removed or not exists when recording GradeLog!";
                logHelper.info(request, logInfo);
            }
            course = task.getCourse();
        } else if (StringUtils.isNotEmpty(courseCode)) {
            EntityQuery query = new EntityQuery(Course.class, "course");
            query.add(new Condition("course.code like :code", courseCode));
            if (CollectionUtils.isEmpty(utilService.search(query))) {
                String logInfo = courseCode
                        + " of Course CODE been removed or not exists when recording GradeLog!";
                logHelper.info(request, logInfo);
            }
        }
        // 处理记录时间条件
        String beginOn = get(request, "beginOn");
        String beginAt = get(request, "beginAt");
        String endOn = get(request, "endOn");
        String endAt = get(request, "endAt");
        Date begin = null;
        if (StringUtils.isNotEmpty(beginOn)) {
            if (StringUtils.isEmpty(beginAt)) {
                beginAt = "00:00:00";
            } else {
                beginAt += ":00";
            }
            begin = dateFormatFull.parse(beginOn + " " + beginAt);
        }
        Date end = null;
        if (StringUtils.isNotEmpty(endOn)) {
            if (StringUtils.isEmpty(endAt)) {
                endAt = "00:00:00";
                Calendar dateCalendar = Calendar.getInstance();
                dateCalendar.setTime(dateFormatFull.parse(endOn + " " + endAt));
                dateCalendar.set(Calendar.DATE, dateCalendar.get(Calendar.DATE) + 1);
                end = dateCalendar.getTime();
            } else {
                endAt += ":00";
                end = dateFormatFull.parse(endOn + " " + endAt);
            }
        }
        // 处理记录生产者条件
        String userCode = get(request, "userCode");
        String userName = get(request, "userName");
        User generateBy = null;
        if (StringUtils.isNotEmpty(userCode)) {
            try {
                generateBy = (User) utilService.load(User.class, "code", userCode).get(0);
            } catch (Exception e) {
                String logInfo = userCode
                        + " of User CODE been removed or not exists when recording GradeLog!";
                logHelper.info(request, logInfo);
            }
        }
        // /////////////// //
        // /////////////// //
        
        EntityQuery query = new EntityQuery(GradeLog.class, "gradeLog");
        populateConditions(request, query);
        // 日历条件查询
        if (null != calendar) {
            String hqlTemp = "gradeLog.generateAt between :beginAt and :endAt and gradeLog.generateAt != :endAt";
            query.add(new Condition(hqlTemp, calendar.getStart(), calendar.getFinish()));
        } else if (null != stdTypeId) {
            String hqlTemp = "gradeLog.calendar.studentType.id = :stdTypeId or gradeLog.calendarValue like :calendarValue";
            query.add(new Condition(hqlTemp, stdTypeId, stdTypeId + ""));
        }
        // 学生条件查询
        if (null != student) {
            String hqlTemp = "gradeLog.student = :student or gradeLog.stdCode = :stdCode";
            query.add(new Condition(hqlTemp, student, student.getCode()));
        } else {
            if (StringUtils.isNotEmpty(stdCode)) {
                query.add(new Condition("gradeLog.stdCode like :stdCode", stdCode));
            }
            if (StringUtils.isNotEmpty(stdName)) {
                query.add(new Condition("gradeLog.stdName like :stdName", stdName));
                if (null != stdTypeId) {
                    query.add(new Condition("gradeLog.student.type = :stdTypeId", stdTypeId));
                }
            }
        }
        // 教学任务条件查询
        if (null != task) {
            String hqlTemp = "gradeLog.task = :task or gradeLog.taskSeqNo = :taskSeqNo";
            query.add(new Condition(hqlTemp, task, task.getSeqNo()));
        } else {
            if (StringUtils.isNotEmpty(taskSeqNo)) {
                query.add(new Condition("gradeLog.taskSeqNo like :taskSeqNo", taskSeqNo));
            }
            if (null != course) {
                query.add(new Condition("gradeLog.course = :course", course));
            } else {
                if (StringUtils.isNotEmpty(courseCode)) {
                    query.add(new Condition("gradeLog.courseCode like :courseCode", courseCode));
                }
                if (StringUtils.isNotEmpty(courseName)) {
                    query.add(new Condition("gradeLog.courseName like :courseName", courseName));
                }
            }
        }
        // 记录时间条件
        if (null != begin) {
            query.add(new Condition("gradeLog.generateAt >= :beginAt", begin));
        }
        if (null != end) {
            query.add(new Condition("gradeLog.generateAt < :endAt", end));
        }
        // 记录生产者条件
        if (null == calendar) {
            if (null != generateBy) {
                String hqlTemp = "gradeLog.generateBy = :generateBy or gradeLog.userCode = :userCode";
                query.add(new Condition(hqlTemp, generateBy, generateBy.getName()));
            } else {
                if (StringUtils.isNotEmpty(userCode)) {
                    query.add(new Condition("gradeLog.userCode like :userCode", userCode));
                }
                if (StringUtils.isNotEmpty(userName)) {
                    query.add(new Condition("gradeLog.userName like :userName", userName));
                }
            }
        }
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        query.setLimit(getPageLimit(request));
        addCollection(request, "gradeLogs", utilService.search(query));
        addCollection(request, "gradeTypes", utilService.loadAll(GradeType.class));
        
        return forward(request);
    }
    
    /**
     * 查看日志
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
        Long gradeCatalogId = getLong(request, "gradeLogId");
        if (null == gradeCatalogId) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        addSingleParameter(request, "gradeLog", gradeLogService.getGradeLog(gradeCatalogId));
        addCollection(request, "gradeTypes", utilService.loadAll(GradeType.class));
        return forward(request);
    }
    
    /**
     * 删除日志
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
        Long[] gradeCatalogIds = null;
        try {
            gradeCatalogIds = SeqStringUtil.transformToLong(get(request, "gradeCatalogIds"));
            if (null == gradeCatalogIds || gradeCatalogIds.length == 0) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            return forwardError(mapping, request, "error.model.ids.needed");
        }
        Collection catalogs = utilService.load(GradeLog.class, "id", gradeCatalogIds);
        for (Iterator it = catalogs.iterator(); it.hasNext();) {
            GradeLog gradeLog = (GradeLog) it.next();
            logHelper.info(request, "GradeCatalog with No."
                    + StringUtils.repeat("0", 18 - String.valueOf(gradeLog.getId()).length())
                    + gradeLog.getId() + " been removed!");
        }
        utilService.remove(catalogs);
        return redirect(request, "search", "info.delete.success");
    }
    
    public final void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    
    public final void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    public final SimpleDateFormat getDateFormatFull() {
        return dateFormatFull;
    }
    
    public void setDateFormatFull(SimpleDateFormat dateFormatFull) {
        this.dateFormatFull = dateFormatFull;
    }
    
    public void setGradeLogService(GradeLogService gradeLogService) {
        this.gradeLogService = gradeLogService;
    }
}
