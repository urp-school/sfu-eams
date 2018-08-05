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
 * chaostone             2006-12-27            Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.grade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.course.grade.GradeSetting;
import com.ekingstar.eams.course.grade.converter.ConverterFactory;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.ExamStatus;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.security.User;
import com.ekingstar.security.model.UserCategory;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.grade.GradeInputSwitch;
import com.shufe.model.course.grade.GradeState;
import com.shufe.model.course.grade.gp.GradePointRule;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.grade.gp.GradePointRuleService;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarSupportAction;
import com.shufe.web.action.course.grade.report.TeachClassGradeReportAction;
import com.shufe.web.action.course.task.TeachTaskAction;

/**
 * 教师管理成绩响应类
 * 
 * @author chaostone
 */
public class TeacherGradeAction extends CalendarSupportAction {
    
    protected TeachTaskService teachTaskService;
    
    protected GradeService gradeService;
    
    protected TeachPlanService teachPlanService;
    
    protected GradePointRuleService gradePointRuleService;
    
//    protected GradeLogService gradeLogService;
    
    protected CourseGradeHelper courseGradeHelper;
    
    /**
     * 加载录入成绩首页面
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
        Teacher teacher = getTeacherFromSession(request.getSession());
        List stdTypeList = teachTaskService.getStdTypesForTeacher(teacher);
        if (stdTypeList.isEmpty())
            return forward(mapping, request, "error.teacher.noTask", "error");
        addSingleParameter(request, "stdTypeList", stdTypeList);
        
        setCalendar(request, (StudentType) stdTypeList.iterator().next());
        List tasks = teachTaskService.getTeachTasksByCategory(teacher.getId(),
                TeachTaskFilterCategory.TEACHER, (TeachCalendar) request
                        .getAttribute(Constants.CALENDAR));
        addCollection(request, "tasks", tasks);
        GradeType gradeType = new GradeType();
        gradeType.setTeacherCanInputGrade(Boolean.TRUE);
        addCollection(request, "gradeTypes", baseCodeService.getCodes(gradeType));
        addSingleParameter(request, "GAGrade", baseCodeService.getCode(GradeType.class,
                GradeType.GA));
        EntityQuery query = new EntityQuery(GradeInputSwitch.class, "switch");
        query.add(new Condition("switch.calendar=:calendar", (TeachCalendar) request
                .getAttribute(Constants.CALENDAR)));
        List rs = (List) utilService.search(query);
        if (!rs.isEmpty()) {
            addSingleParameter(request, "canInput", new Boolean(null == getCannotInputSwitch(
                    request, (TeachCalendar) request.getAttribute(Constants.CALENDAR))));
        } else if (rs.isEmpty()) {
            addSingleParameter(request, "canInput", new Boolean(false));
        }
        return forward(request);
    }
    
    /**
     * 查询成绩不能录入的开关项
     * 
     * @param request
     * @return
     */
    protected GradeInputSwitch getCannotInputSwitch(HttpServletRequest request, TeachTask task) {
        return getCannotInputSwitch(request, task.getCalendar());
    }
    
    /**
     * 查询成绩不能录入的开关项
     * 
     * @param request
     * @return
     */
    protected GradeInputSwitch getCannotInputSwitch(HttpServletRequest request,
            TeachCalendar calendar) {
        User user = getUser(request.getSession());
        boolean isTeacher = user.isCategory(EamsRole.TEACHER_USER);
        if (!isTeacher) {
            return null;
        } else {
            EntityQuery query = new EntityQuery(GradeInputSwitch.class, "switch");
            query.add(new Condition("switch.calendar=:calendar", calendar));
            List rs = (List) utilService.search(query);
            if (!rs.isEmpty()) {
                GradeInputSwitch gradeInputSwitch = (GradeInputSwitch) rs.get(0);
                if (!gradeInputSwitch.checkOpen(new Date())) {
                    return gradeInputSwitch;
                }
            }
            return null;
        }
    }
    
    /**
     * 打印一个或多个教学任务的登分册
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see TeachTaskAction#printStdListForGrade
     */
    public ActionForward printEmptyGradeTable(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teachTaskIds = get(request, "teachTaskIds");
        if (StringUtils.isEmpty(teachTaskIds))
            return forwardError(mapping, request, "error.model.id.needed");
        EntityQuery query = new EntityQuery(GradeType.class, "gradeType");
        query.add(new Condition("gradeType.teacherCanInputGrade=true"));
        query.addOrder(new Order("gradeType.priority"));
        List gradeTypes = (List) utilService.search(query);
        addCollection(request, "gradeTypes", gradeTypes);
        printStdListPrepare(teachTaskIds, request);
        return forward(request);
    }
    
    /**
     * 获取学生名单
     * 
     * @param taskIds
     * @param request
     * @throws Exception
     */
    public void printStdListPrepare(String taskIds, HttpServletRequest request) throws Exception {
        List tasks = utilService
                .load(TeachTask.class, "id", SeqStringUtil.transformToLong(taskIds));
        Map courseTakes = new HashMap();
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            ArrayList myCourseTakes = new ArrayList();
            myCourseTakes.addAll(task.getTeachClass().getCourseTakes());
            courseTakes.put(task.getId().toString(), myCourseTakes);
        }
        addSingleParameter(request, "tasks", tasks);
        addSingleParameter(request, "courseTakes", courseTakes);
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
    public ActionForward input(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        TeachTask task = checkTeachTask(request);
        addSingleParameter(request, "task", task);
        if (null == task) {
            return forwardError(mapping, request, "error.parameters.illegal");
        }
        GradeInputSwitch gradeInputSwitch = getCannotInputSwitch(request, task);
        if (null != gradeInputSwitch) {
            addSingleParameter(request, "gradeInputSwitch", gradeInputSwitch);
            return forward(request, "cannotInput");
        }
        // 在必要时生成新的成绩状态
        if (null == task.getGradeState()) {
            task.setGradeState(new GradeState(task));
            utilService.saveOrUpdate(task);
        }
        // 检查成绩状态中可以录入的成绩类型
        GradeState state = task.getGradeState();
        GradeType GAGradeType = (GradeType) baseCodeService.getCode(GradeType.class, GradeType.GA);
        if (state.isConfirmed(GAGradeType)) {
                return forward(request, new Action(TeachClassGradeReportAction.class, "report"));
        }
        List gradeTypes = gradeService.getGradeTypes(state);
        // 判断可录入成绩类型是否为空. 若为空则取当前开关中允许录入成绩类型
        if (null == gradeTypes) {
            EntityQuery query = new EntityQuery(GradeInputSwitch.class, "switch");
            query.add(new Condition("switch.calendar=:calendar", task.getCalendar()));
            List rs = (List) utilService.search(query);
            if (!rs.isEmpty()) {
                GradeInputSwitch inputSwitch = (GradeInputSwitch) rs.get(0);
                gradeTypes = new ArrayList(inputSwitch.getGradeTypes());
                if (gradeTypes.contains(new GradeType(GradeType.MORAL))) {
                    gradeTypes.remove(new GradeType(GradeType.MORAL));
                }
            }
        }
        addCollection(request, "gradeTypes", gradeTypes);
        // 没有设置百分比转入设置百分比界面
        if (StringUtils.isEmpty(state.getPercents())) {
            addCollection(request, "markStyles", ConverterFactory.getConverter().getMarkStyles());
            addCollection(request, "gradePointRules", utilService.loadAll(GradePointRule.class));
            return forward(request, "gradeStateForm");
        }
        // 在必要时构建成绩和courseTake之间的关联
        gradeService.linkGradeAndTakeAsPossible(task);
        
        // 计算各种成绩类型，现在应该录入遍次
        Map enrolTimeMap = new HashMap();
        Integer minEnrol = new Integer(10);// 所有成绩最小录入次数
        for (Iterator iter = gradeTypes.iterator(); iter.hasNext();) {
            GradeType gradeType = (GradeType) iter.next();
            Integer enrolTime = gradeService.calcEnrolTime(task, gradeType);
            if (enrolTime.intValue() < minEnrol.intValue()) {
                minEnrol = enrolTime;
            }
            enrolTimeMap.put(gradeType.getId().toString(), enrolTime);
        }
        addSingleParameter(request, "enrolTimeMap", enrolTimeMap);
        addSingleParameter(request, "GA", GradeType.GA);
        addSingleParameter(request, "REEXAM", CourseTakeType.REEXAM);
        addSingleParameter(request, "RESTUDY", CourseTakeType.RESTUDY);
        addSingleParameter(request, "USUAL", GradeType.USUAL);
        addSingleParameter(request, "minEnrol", minEnrol);
        addSingleParameter(request, "gradeConverterConfig", ConverterFactory.getConverter()
                .getConfig(state.getMarkStyle()));
        // 开始录入
        return forward(request, (String) getSystemConfig().getConfigItemValue(
                GradeSetting.TEACHER_INPUT_MODE));
    }
    
    /**
     * 修改成绩记录方式
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateGradeState(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = get(request, "taskIds");
        addSingleParameter(request, "taskIds", taskIds);
        // 成绩记录方式
        addCollection(request, "markStyles", ConverterFactory.getConverter().getMarkStyles());
        return forward(request, "batchGradeStateForm");
    }
    
    /**
     * 保存 批量修改成绩记录方式
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchSaveGradeState(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = get(request, "taskIds");
        List taskList = teachTaskService.getTeachTasksByIds(taskIds);
        Long markStyleId = getLong(request, "gradeState.markStyle.id");
        List tasks = new ArrayList();
        for (Iterator iter = taskList.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            task.getGradeState().setMarkStyle(
                    (MarkStyle) baseCodeService.getCode(MarkStyle.class, markStyleId));
            tasks.add(task);
        }
        utilService.saveOrUpdate(tasks);
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.update.success"));
        saveErrors(request, messages);
        return mapping.findForward("actionResult");
    }
    
    /**
     * 修改百分比
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editGradeState(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachTask task = checkTeachTask(request);
        if (null == task) {
            return forwardError(mapping, request, "error.parameters.illegal");
        }
        GradeInputSwitch gradeInputSwitch = getCannotInputSwitch(request, task);
        if (null != gradeInputSwitch) {
            addSingleParameter(request, "gradeInputSwitch", gradeInputSwitch);
            return forward(request, "cannotInput");
        }
        // 在必要时生成新的成绩状态
        if (null == task.getGradeState()) {
            task.setGradeState(new GradeState(task));
            utilService.saveOrUpdate(task);
        }
        // 检查成绩状态中可以录入的成绩类型
        GradeState state = task.getGradeState();
        // 总评或最终成绩发布的不能修改百分比
        GradeType GAGradeType = (GradeType) baseCodeService.getCode(GradeType.class, GradeType.GA);
        GradeType ENDGradeType = (GradeType) baseCodeService
                .getCode(GradeType.class, GradeType.END);
        if (state.isPublished(GAGradeType) || state.isPublished(ENDGradeType)) {
            return forwardError(mapping, request, "error.grade.modifyPublished");
        }
        
        // 查询教师可以录入的成绩
        GradeType gradeType = new GradeType();
        gradeType.setTeacherCanInputGrade(Boolean.TRUE);
        addCollection(request, "gradeTypes", baseCodeService.getCodes(gradeType));
        // 成绩记录方式
        addCollection(request, "markStyles", ConverterFactory.getConverter().getMarkStyles());
        
        // 成绩绩点规则
        addCollection(request, "gradePointRules", utilService.loadAll(GradePointRule.class));
        addSingleParameter(request, "task", task);
        // 确定用户身份
        User user = getUser(request.getSession());
        addSingleParameter(request, "user", user);
        Boolean isAdmin = Boolean.FALSE;
        Set categories = user.getCategories();
        if (null != categories) {
            Iterator it = categories.iterator();
            while (it.hasNext()) {
                UserCategory temp = (UserCategory) it.next();
                if (temp.getId().equals(EamsRole.MANAGER_USER)) {
                    isAdmin = Boolean.TRUE;
                    break;
                }
            }
        }
        addSingleParameter(request, "isAdmin", isAdmin);
        addSingleParameter(request, "TEACHER_USER", EamsRole.TEACHER_USER);
        return forward(request, "gradeStateForm");
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
    public ActionForward removeGrade(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachTask task = checkTeachTask(request);
        if (null == task) {
            return forwardError(mapping, request, "error.parameters.illegal");
        }
        GradeInputSwitch gradeInputSwitch = getCannotInputSwitch(request, task);
        if (null != gradeInputSwitch) {
            addSingleParameter(request, "gradeInputSwitch", gradeInputSwitch);
            return forward(request, "cannotInput");
        }
        String msg = courseGradeHelper.removeTaskGrade(request, getUser(request));
        if (StringUtils.isEmpty(msg)) {
            logHelper.info(request, "delete grade");
            return redirect(request, "index", "info.delete.success");
        } else {
            return forwardError(mapping, request, msg);
        }
    }
    
    /**
     * 保存修改后的成绩状态
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveGradeState(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachTask task = checkTeachTask(request);
        if (null == task) {
            return forwardError(mapping, request, "error.parameters.illegal");
        }
        GradeInputSwitch gradeInputSwitch = getCannotInputSwitch(request, task);
        if (null != gradeInputSwitch) {
            addSingleParameter(request, "gradeInputSwitch", gradeInputSwitch);
            return forward(request, "cannotInput");
        }
        // 查询教师可以录入的成绩
        GradeType example = new GradeType();
        example.setTeacherCanInputGrade(Boolean.TRUE);
        Collection canInputTypes = baseCodeService.getCodes(example);
        
        StringBuffer percents = new StringBuffer(";");
        for (Iterator iter = canInputTypes.iterator(); iter.hasNext();) {
            GradeType gradeType = (GradeType) iter.next();
            Integer percent = getInteger(request, "percent" + gradeType.getId());
            if (percent.intValue() != 0) {
                percents.append(gradeType.getId()).append("=");
                percents.append(percent.intValue() / 100.0);
                percents.append(";");
            }
        }
        Long markStyleId = getLong(request, "gradeState.markStyle.id");
        if ((null != markStyleId) && !"".equals(markStyleId)) {
            task.getGradeState().setMarkStyle(
                    (MarkStyle) baseCodeService.getCode(MarkStyle.class, markStyleId));
        }
        task.getGradeState().setPercents(percents.toString());
        task.getGradeState().setPrecision(getInteger(request, "precision"));
        utilService.saveOrUpdate(task.getGradeState());
        gradeService.reCalcGrade(task, getUser(request));
        return redirect(request, "input", "info.set.success", "&taskId=" + task.getId());
    }
    
    /**
     * 保存修改后的成绩状态
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateGrade(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = get(request, "taskIds");
        List taskList = teachTaskService.getTeachTasksByIds(taskIds);
        for (Iterator iter = taskList.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            gradeService.reCalcGrade(task, getUser(request));
        }
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.update.success"));
        saveErrors(request, messages);
        return mapping.findForward("actionResult");
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
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Boolean isFinal = getBoolean(request, "isFinal");
        if (null == isFinal) {
            isFinal = Boolean.FALSE;
        }
        // 得到成绩的 教师录入模式
        String mode = (String) getSystemConfig()
                .getConfigItemValue(GradeSetting.TEACHER_INPUT_MODE);
        // 找到任务,专业类型,总评成绩精度
        TeachTask task = checkTeachTask(request);
        GradeInputSwitch gradeInputSwitch = getCannotInputSwitch(request, task);
        if (null != gradeInputSwitch) {
            addSingleParameter(request, "gradeInputSwitch", gradeInputSwitch);
            return forward(request, "cannotInput");
        }
        User user = getUser(request.getSession());
        boolean isTeacher = user.isCategory(EamsRole.TEACHER_USER);
        Long majorTypeId = MajorType.FIRST;
        if (Boolean.TRUE.equals(task.getCourse().getIs2ndSpeciality())) {
            majorTypeId = MajorType.SECOND;
        }
        // 查找成绩精确度
        Integer precision = getInteger(request, "precision");
        GradeState state = task.getGradeState();
        if (!state.getPrecision().equals(precision)) {
            state.setPrecision(precision);
            utilService.saveOrUpdate(state);
        }
        // 准备数据
        List grades = new ArrayList();
        List examTakes = new ArrayList();
        Map gradesMap = new HashMap();
        Map confirmMap = new HashMap();// 各类成绩的确认数量
        Map inputMap = new HashMap();// 各类成绩的等记数量
        List canInputTypes = gradeService.getGradeTypes(state);
        List gradeTypes = new ArrayList();
        for (Iterator iter = canInputTypes.iterator(); iter.hasNext();) {
            GradeType gradeType = (GradeType) iter.next();
            if (!state.isConfirmed(gradeType)) {
                gradeTypes.add(gradeType);
                confirmMap.put(gradeType, new Integer(0));
                inputMap.put(gradeType, new Integer(0));
            }
        }
        // 计算各种成绩类型，应该提交录入遍次
        int minEnrol = 10;// 所有成绩最小录入次数
        for (Iterator iter = gradeTypes.iterator(); iter.hasNext();) {
            GradeType gradeType = (GradeType) iter.next();
            Integer enrolTime = gradeService.calcEnrolTime(task, gradeType);
            if (enrolTime.intValue() < minEnrol) {
                minEnrol = enrolTime.intValue();
            }
        }
        
        Map inputGradeMap = new HashMap();
        Map scoreMap = new HashMap();
        // 遍历教学班中的每一个学生
        for (Iterator iter = task.getTeachClass().getCourseTakes().iterator(); iter.hasNext();) {
            CourseTake take = (CourseTake) iter.next();
            CourseGrade grade = take.getCourseGrade();
            if (null != grade) {
                scoreMap.put(grade.getId(), grade.getScore());
                String key = grade.getId() + "_" + grade.getScoreDisplay();
                for (Iterator it2 = grade.getExamGrades().iterator(); it2.hasNext();) {
                    ExamGrade exam = (ExamGrade) it2.next();
                    key += "_" + exam.getGradeType().getId() + ":" + exam.getScoreDisplay();
                }
                inputGradeMap.put(key, grade);
            }
            // 新增成绩
            if (null == grade) {
                Long stdMajorTypeId = majorTypeId;
                // 如果不是双专业学生,修读双专业课程的时候也算做一专业课程。
                if (MajorType.SECOND.equals(stdMajorTypeId)) {
                    if (!take.getStudent().isSecondMajorStd()) {
                        stdMajorTypeId = MajorType.FIRST;
                    }
                }
                grade = new CourseGrade(take);
                grade.setMarkStyle(task.getGradeState().getMarkStyle());
                grade.setMajorType(new MajorType(stdMajorTypeId));
                grade.setStatus(new Integer(Grade.NEW));
                // 依据计划查找课程类别和学分
                CourseType planCourseType = teachPlanService.getCourseTypeOfStd(take.getStudent()
                        .getCode(), task.getCourse(), task.getCourseType(), grade.getMajorType());
                grade.setCourseType(planCourseType);
                grade.setCredit(task.getCourse().getCredits());
                // 如果此时操作,hibernate在以下代码没有执行完成时,会抛出一个异常,报告该grade还未保存
                // take.setCourseGrade(grade);
            }
            // 针对每个成绩类型
            for (Iterator iterator = gradeTypes.iterator(); iterator.hasNext();) {
                GradeType gradeType = (GradeType) iterator.next();
                // 1.查找考试情况
                ExamStatus examStatus = (ExamStatus) utilService.load(ExamStatus.class,
                        ExamStatus.NORMAL);
                // new ExamStatus(ExamStatus.NORMAL);
                ExamTake examTake = null;
                if (null != gradeType.getExamType()) {
                    examTake = take.getExamTake(gradeType.getExamType());
                    if (null != examTake) {
                        examStatus = examTake.getExamStatus();
                    }
                }
                // 2.不参加考试 或者免修平时成绩(不录入平时成绩)
                if (Boolean.FALSE.equals(examStatus.getIsAttend())
                        || (gradeType.getId().equals(GradeType.USUAL)
                                && take.getCourseTakeType().getId().equals(CourseTakeType.REEXAM) && !(getSystemConfig()
                                .getBooleanParam("Grade.REEXAM.USUALISINPUT")))) {
                    incGradeCount(inputMap, gradeType);
                    incGradeCount(confirmMap, gradeType);
                    continue;
                }
                
                ExamGrade examGrade = grade.getExamGrade(gradeType);
                // 3.处理录入。录入的不是数字或者-1,统统算作缺考
                String scoreInputName = gradeType.getShortName() + "_" + take.getStudent().getId();
                Float examScore = null;
                String scoreStr = get(request, scoreInputName);
                if (StringUtils.isNotEmpty(scoreStr)
                        && ("-1".equals(scoreStr) || !NumberUtils.isNumber(scoreStr))) {
                    incGradeCount(inputMap, gradeType);
                    incGradeCount(confirmMap, gradeType);
                    // 录入缺考，居然还有成绩，更新为 null。
                    examStatus = new ExamStatus(ExamStatus.ABSENT);
                    if (null != examGrade) {
                        examGrade.setScore(null);
                        examGrade.setExamStatus(examStatus);
                        examGrade.setStatus(new Integer(Grade.CONFIRMED));
                    }
                    // continue;
                } else {
                    examScore = getFloat(request, scoreInputName);
                }
                
                if (null != examScore) {
                    incGradeCount(inputMap, gradeType);
                }
                
                // 4.更新已有成绩(区别授课教师和管理人员)
                if (null != examGrade) {
                    if (null != examScore) {
                        examGrade.setExamStatus(examStatus);
                        examGrade.setScore(examScore);
                        if (mode.equals(GradeSetting.TWICE_MODE)) {
                            examGrade.setStatus(new Integer(Grade.CONFIRMED));
                        } else if (Boolean.TRUE.equals(isFinal)) {
                            examGrade.setStatus(new Integer(Grade.CONFIRMED));
                        }
                    } else {
                        incGradeCount(inputMap, gradeType);
                    }
                    
                    if (mode.equals(GradeSetting.TWICE_MODE)) {
                        if (isTeacher) {
                            if (Boolean.TRUE.equals(examGrade.getIsConfirmed()))
                                incGradeCount(confirmMap, gradeType);
                        } else {
                            examGrade.setStatus(new Integer(Grade.CONFIRMED));
                            incGradeCount(confirmMap, gradeType);
                        }
                    } else {
                        if (Boolean.TRUE.equals(isFinal)) {
                            examGrade.setStatus(new Integer(Grade.CONFIRMED));
                            incGradeCount(confirmMap, gradeType);
                        }
                    }
                    
                }// 原来没有成绩
                else {
                    if (null != examScore || StringUtils.isNotEmpty(scoreStr)
                            && ("-1".equals(scoreStr) || !NumberUtils.isNumber(scoreStr))) {
                        examGrade = new ExamGrade(gradeType, examStatus, examScore);
                        if (mode.equals(GradeSetting.TWICE_MODE)) {
                            // 如果是老师录入，第一遍录入算作新的(补考算最终)。管理员录入，算做最终。
                            if (isTeacher) {
                                examGrade.setStatus((minEnrol == 1) ? new Integer(Grade.NEW)
                                        : new Integer(Grade.CONFIRMED));
                            } else {
                                examGrade.setStatus(new Integer(Grade.CONFIRMED));
                                incGradeCount(confirmMap, gradeType);
                            }
                        } else {
                            examGrade.setStatus(new Integer(Grade.NEW));
                            if (Boolean.TRUE.equals(isFinal)) {
                                examGrade.setStatus(new Integer(Grade.CONFIRMED));
                                incGradeCount(confirmMap, gradeType);
                            }
                        }
                        grade.addExamGrade(examGrade);
                    }
                    // 留至下次录入的部分录入情况
                }
            }
            grade.updateConfirmed();
            grade.calcGA();
            grade.calcScore();
            grade.updatePass();
            // 计算平均绩点
            grade.calcGP(gradePointRuleService.getGradePointRule(grade.getStd().getType(), grade
                    .getMarkStyle()));
            grades.add(grade);
            gradesMap.put(grade.getStd().getId(), grade);
        }
        
        // 更新各类成绩和总评状态中的输入和确认状态
        GradeType GAGradeType = (GradeType) baseCodeService.getCode(GradeType.class, GradeType.GA);
        GradeType FINAL = (GradeType) baseCodeService.getCode(GradeType.class, GradeType.FINAL);
        state.addConfirm(GAGradeType.getMark().intValue());
        state.addConfirm(FINAL.getMark().intValue());
        state.addInput(GAGradeType.getMark().intValue());
        state.addInput(FINAL.getMark().intValue());
        
        for (Iterator iter = gradeTypes.iterator(); iter.hasNext();) {
            GradeType gradeType = (GradeType) iter.next();
            int count = ((Integer) inputMap.get(gradeType)).intValue();
            if (count >= task.getTeachClass().getCourseTakes().size()) {
                state.addInput(gradeType.getMark().intValue());
            } else {
                state.removeInput(gradeType.getMark().intValue());
                state.removeInput(GAGradeType.getMark().intValue());
                state.removeInput(FINAL.getMark().intValue());
            }
            count = ((Integer) confirmMap.get(gradeType)).intValue();
            if (count >= task.getTeachClass().getCourseTakes().size()) {
                state.addConfirm(gradeType.getMark().intValue());
            } else {
                state.removeConfirm(gradeType.getMark().intValue());
                state.removeConfirm(GAGradeType.getMark().intValue());
                state.removeConfirm(FINAL.getMark().intValue());
            }
        }
        
        // 成绩日志记录
//        grades.addAll(gradeLogService.buildGradeCatalogInfo(user, grades, inputGradeMap, scoreMap));
        
        utilService.saveOrUpdate(grades);
        // 将成绩回填到教学班学生中
        List takes = new ArrayList();
        for (Iterator iter = task.getTeachClass().getCourseTakes().iterator(); iter.hasNext();) {
            CourseTake take = (CourseTake) iter.next();
            if (null == take.getCourseGrade()) {
                take.setCourseGrade((CourseGrade) gradesMap.get(take.getStudent().getId()));
                takes.add(take);
            }
        }
        utilService.saveOrUpdate(takes);
        utilService.saveOrUpdate(examTakes);
        utilService.saveOrUpdate(task.getGradeState());
        return redirect(request, "input", "info.save.success", "&taskId=" + task.getId());
    }
    
    /**
     * 增加对应成绩类别中的已经录入的成绩数量
     * 
     * @param countMap
     * @param gradeType
     */
    private void incGradeCount(Map countMap, GradeType gradeType) {
        Integer count = (Integer) countMap.get(gradeType);
        countMap.put(gradeType, new Integer(count.intValue() + 1));
    }
    
    /**
     * 分段统计
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
        return forward(request, new Action(CollegeGradeAction.class, "stat"));
        // String taskIdSeq = get(request,"taskIds");
        // if (StringUtils.isEmpty(taskIdSeq))
        // return forwardError(mapping, request, "error.parameters.needed");
        //        
        // new TeachClassGradeHelper(baseCodeService,
        // gradeService).statTask(taskIdSeq, new Long[] {
        // GradeType.END, GradeType.GA }, request);
        // addSingleParameter(request,"kind", GradeSegStats.TASK);
        // return forward(request);
    }
    
    /**
     * 试卷分析打印<br>
     * 分析期末成绩
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reportForExam(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIdSeq = get(request, "taskIds");
        if (StringUtils.isEmpty(taskIdSeq))
            return forwardError(mapping, request, "error.parameters.needed");
        new TeachClassGradeHelper(baseCodeService, gradeService).statTask(taskIdSeq,
                new Long[] { GradeType.END }, request);
        return forward(request);
    }
    
    /**
     * 打印教学班成绩
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return forward(request, new Action(TeachClassGradeReportAction.class, "report"));
    }
    
    /**
     * 查看单个教学任务所有成绩信息
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
        Long taskId = getLong(request, "taskId");
        TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
        new TeachClassGradeHelper(baseCodeService, gradeService).info(task, request);
        return forward(request);
    }
    
    /**
     * 跳转到编辑打印内容页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    /**
     * 提交编辑的打印内容，到打印页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reportContent(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action("", "reportForExam"));
    }
    
    /**
     * 检查任务是否存在和任务中的老师和当前用户是否一致
     * 
     * @param request
     * @return
     */
    private TeachTask checkTeachTask(HttpServletRequest request) {
        Long taskId = getLong(request, "taskId");
        TeachTask task = teachTaskService.getTeachTask(taskId);
        if (null == task)
            return null;
        return task;
    }
    
    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }
    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    public void setTeachPlanService(TeachPlanService teachPlanService) {
        this.teachPlanService = teachPlanService;
    }
    
    public void setGradePointRuleService(GradePointRuleService gradePointRuleService) {
        this.gradePointRuleService = gradePointRuleService;
    }
    
//    public final void setGradeLogService(GradeLogService gradeLogService) {
//        this.gradeLogService = gradeLogService;
//    }
//    
}
