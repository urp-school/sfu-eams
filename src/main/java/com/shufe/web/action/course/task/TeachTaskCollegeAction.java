//$Id: TeachTaskCollegeAction.java,v 1.1 2008-3-26 下午04:06:53 zhouqi Exp $
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
 * zhouqi              2008-3-26         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.task;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.ekingstar.commons.bean.comparators.MultiPropertyComparator;
import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.mvc.struts.misc.ForwardSupport;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.ExcelTools;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.exporter.Context;
import com.ekingstar.commons.transfer.exporter.Exporter;
import com.ekingstar.commons.transfer.exporter.PropertyExtractor;
import com.ekingstar.commons.transfer.exporter.TemplateExporter;
import com.ekingstar.commons.transfer.exporter.writer.ExcelTemplateWriter;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.ExamMode;
import com.ekingstar.eams.system.basecode.industry.HSKDegree;
import com.ekingstar.eams.system.basecode.industry.MessageType;
import com.ekingstar.eams.system.basecode.industry.TeachLangType;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.config.SystemConfig;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.eams.system.time.WeekInfo;
import com.ekingstar.security.model.User;
import com.shufe.dao.system.calendar.TermCalculator;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.ArrangeInfo;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.grade.GradeLog;
import com.shufe.model.course.grade.GradeState;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachClass;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.course.task.TeachTaskParam;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.OnCampusTimeNotFoundException;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.file.FilePath;
import com.shufe.model.system.message.Message;
import com.shufe.model.system.message.SystemMessage;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.course.arrange.task.CourseActivityDigestor;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.service.course.task.TaskCopyParams;
import com.shufe.service.course.task.TeachTaskPropertyExtractor;
import com.shufe.service.system.baseinfo.AdminClassService;
import com.shufe.util.DataAuthorityPredicate;
import com.shufe.util.DataAuthorityUtil;
import com.shufe.util.DataRealmUtils;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.course.grade.TeacherGradeAction;
import com.shufe.web.helper.BaseInfoSearchHelper;

/**
 * @author zhouqi
 */
public class TeachTaskCollegeAction extends TeachTaskMergeSplitAction {
    
    protected AdminClassService adminClassService;
    
    protected BaseInfoSearchHelper baseInfoSearchHelper;
    
    protected TeachPlanService teachPlanService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        TeachCalendar teachCalendar = (TeachCalendar) request.getAttribute(Constants.CALENDAR);
        EntityQuery query = new EntityQuery(TeachTaskParam.class, "teachTaskParam");
        String order = "Y";
        if (teachCalendar != null) {
            query.add(new Condition("teachTaskParam.calendar.id=" + teachCalendar.getId()));
        }
        List manualArrangeParamList = (List) utilService.search(query);
        Set users = getUser(request.getSession()).getRoles();
        for (Iterator iter = users.iterator(); iter.hasNext();) {
            EamsRole eamsRole = (EamsRole) iter.next();
            if (manualArrangeParamList.size() == 0 && eamsRole.getId().longValue() != 1L) {
                order = "N";
            } else {
                for (Iterator iter_ = manualArrangeParamList.iterator(); iter_.hasNext();) {
                    TeachTaskParam param = (TeachTaskParam) iter_.next();
                    Date dateNow = new Date(System.currentTimeMillis());
                    Date dateStart = param.getStartDate();
                    Date dateFinsh = param.getFinishDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dateFinsh);
                    cal.add(Calendar.DAY_OF_YEAR, +1);
                    if ((dateNow.before(dateStart) || dateNow.after(cal.getTime()) || param.getIsOpenElection().equals(
                            Boolean.valueOf(false)))
                            && eamsRole.getId().longValue() != 1L) {
                        order = "N";
                    }
                }
            }
        }
        addSingleParameter(request, "order", order);
        // 获得开课院系和上课教师列表
        String stdTypeDataRealm = getStdTypeIdSeq(request);
        String departDataRealm = getDepartmentIdSeq(request);
        if (request.getAttribute(Constants.CALENDAR) != null) {
            List departList = teachTaskService.getDepartsOfTask(stdTypeDataRealm, departDataRealm,
                    (TeachCalendar) request.getAttribute(Constants.CALENDAR));
            addCollection(request, "courseTypes", teachTaskService.getCourseTypesOfTask(
                    stdTypeDataRealm, departDataRealm,
                    (TeachCalendar) request.getAttribute(Constants.CALENDAR)));
            addCollection(request, "teachDepartList", teachTaskService.getTeachDepartsOfTask(
                    stdTypeDataRealm, departDataRealm,
                    (TeachCalendar) request.getAttribute(Constants.CALENDAR)));
            
            addCollection(request, Constants.DEPARTMENT_LIST, departList);
            addCollection(request, "weeks", WeekInfo.WEEKS);
        }
        
        initBaseCodes(request, "schoolDistricts", SchoolDistrict.class);
        /*----------------加载上课学生性别列表--------------------*/
        initBaseCodes(request, "genderList", Gender.class);
        initBaseCodes(request, "courseCategoryList", CourseCategory.class);
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "tasks", teachTaskSearchHelper.searchTask(request));
        String order = request.getParameter("order");
        addSingleParameter(request, "order", order);
        return forward(request);
    }
    
    public ActionForward listSiva(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "tasks", teachTaskSearchHelper.searchTask(request));
        String order = request.getParameter("order");
        addSingleParameter(request, "order", order);
        return forward(request);
    }
    
    /**
     * 一次添加多个教学任务（同一门课程）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addMultiTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long taskId = getLong(request, "task.id");
        Long courseId = getLong(request, "task.course.id");
        if (null == courseId && null == taskId) {
            return selectCourse(mapping, form, request, response);
        }
        TeachTask task = null;
        
        Course course = (Course) utilService.load(Course.class, courseId);
        TeachCalendar calendar = (TeachCalendar) utilService.load(TeachCalendar.class,
                new Long(get(request, "task.calendar.id")));
        task = (TeachTask) populate(request, TeachTask.class, Constants.TEACHTASK);
        EntityUtils.merge(task, TeachTask.getDefault());
        task.getArrangeInfo().setWeeks(calendar.getWeeks());
        task.setCourse(course);
        task.setCalendar(calendar);
        task.getTeachClass().setName(course.getName());
        task.getTeachClass().setStdType(
                studentTypeService.getStudentType(getLong(request, "stdType.id")));
        
        addSingleParameter(request, "task", task);
        addCollection(request, "courseTypeList", baseCodeService.getCodes(CourseType.class));
        addCollection(request, "configTypeList", baseCodeService.getCodes(ClassroomType.class));
        setDeparts(request, task);
        List stdTypes = new ArrayList();
        stdTypes.add(task.getTeachClass().getStdType());
        stdTypes.addAll(task.getTeachClass().getStdType().getSubTypes());
        addCollection(request, "stdTypeList", stdTypes);
        initBaseCodes(request, "teachLangTypes", TeachLangType.class);
        List teacherResults = utilService.load(Teacher.class, "code", getUser(request).getName());
        if (CollectionUtils.isNotEmpty(teacherResults)) {
            addSingleParameter(request, "teacher", teacherResults.get(0));
        }
        return forward(request);
    }
    
    public ActionForward arrangeInfoSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = get(request, "taskIds");
        List tasks = Collections.EMPTY_LIST;
        if (StringUtils.isNotEmpty(taskIds)) {
            tasks = teachTaskService.getTeachTasksByIds(taskIds);
            CollectionUtils.filter(tasks, new Predicate() {
                
                public boolean evaluate(Object arg0) {
                    if (((TeachTask) arg0).getIsConfirm().equals(Boolean.FALSE))
                        return true;
                    else
                        return false;
                }
            });
        }
        addCollection(request, "taskList", tasks);
        return forward(request);
    }
    
    /**
     * 批量修改教学任务的任课教师、教师设备配置和是否双语挂牌
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchEdit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = get(request, "taskIds");
        if (StringUtils.isEmpty(taskIds)) {
            return forward(mapping, request, "error.teachTask.id.needed", "error");
        }
        Collection taskList = taskListQuery(request, taskIds);
        
        taskList = DataAuthorityUtil.select(taskList, "TeachTaskForTeachDepart",
                getStdTypeIdSeq(request), getDepartmentIdSeq(request));
        addCollection(request, "roomConfigTypeList", baseCodeService.getCodes(ClassroomType.class));
        // 获得每个教学任务上课教师所在的部门<br>
        // 没有上课教师的则将用户权限内的第一个部门放在map中<br>
        // 这里不能处理多个教师的情况
        Map departMap = new HashMap();
        for (Iterator iter = taskList.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            if (task.getArrangeInfo().isSingleTeacher()) {
                Teacher teacher = (Teacher) task.getArrangeInfo().getTeachers().iterator().next();
                departMap.put(task.getId().toString(), teacher.getDepartment());
            } else {
                departMap.put(task.getId().toString(), task.getArrangeInfo().getTeachDepart());
            }
        }
        addSingleParameter(request, "departMap", departMap);
        addCollection(request, Constants.TEACHTASK_LIST, taskList);
        addCollection(request, Constants.DEPARTMENT_LIST, departmentService.getDepartments());
        initBaseCodes(request, "teachLangTypes", TeachLangType.class);
        return forward(request);
    }
    
    public ActionForward batchEditSiva(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = get(request, "taskIds");
        if (StringUtils.isEmpty(taskIds)) {
            return forward(mapping, request, "error.teachTask.id.needed", "error");
        }
        Collection taskList = taskListQuery(request, taskIds);
        
        taskList = DataAuthorityUtil.select(taskList, "TeachTaskForTeachDepart",
                getStdTypeIdSeq(request), getDepartmentIdSeq(request));
        addCollection(request, "roomConfigTypeList", baseCodeService.getCodes(ClassroomType.class));
        // 获得每个教学任务上课教师所在的部门<br>
        // 没有上课教师的则将用户权限内的第一个部门放在map中<br>
        // 这里不能处理多个教师的情况
        Map departMap = new HashMap();
        for (Iterator iter = taskList.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            if (task.getArrangeInfo().isSingleTeacher()) {
                Teacher teacher = (Teacher) task.getArrangeInfo().getTeachers().iterator().next();
                departMap.put(task.getId().toString(), teacher.getDepartment());
            } else {
                departMap.put(task.getId().toString(), task.getArrangeInfo().getTeachDepart());
            }
        }
        addSingleParameter(request, "departMap", departMap);
        addCollection(request, Constants.TEACHTASK_LIST, taskList);
        addCollection(request, Constants.DEPARTMENT_LIST, departmentService.getDepartments());
        initBaseCodes(request, "teachLangTypes", TeachLangType.class);
        return forward(request);
    }
    
    /**
     * 批量修改
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] taskIds = request.getParameterValues("taskId");
        List tasks = teachTaskService.getTeachTasksByIds(SeqStringUtil.transformToLong(taskIds));
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            setTeachersAndRooms(get(request, "teacherId" + task.getId()), task);
            Boolean isGuaPai = getBoolean(request, "isGuaPai" + task.getId());
            task.getRequirement().setRoomConfigType(
                    new ClassroomType(getLong(request, "roomConfigTypeId" + task.getId())));
            task.getRequirement().setIsGuaPai(
                    (null == isGuaPai || Boolean.FALSE.equals(isGuaPai)) ? Boolean.FALSE : isGuaPai);
            task.getRequirement().setTeachLangType(
                    new TeachLangType(getLong(request, "teachLangTypeId" + task.getId())));
            Boolean isStdNeedAssign = getBoolean(request, "isStdNeedAssign" + task.getId());
            if (null == isStdNeedAssign) {
                isStdNeedAssign = Boolean.FALSE;
            }
            Long examModeId = getLong(request, "examModeId" + task.getId());
            if (null != examModeId) {
                ExamMode examMode = (ExamMode) baseCodeService.getCode(ExamMode.class, examModeId);
                task.setExamMode(examMode);
            } else {
                task.setExamMode(null);
            }
            String remark = get(request, "taskRemark" + task.getId());
            task.setRemark(remark);
            task.getTeachClass().setIsStdNeedAssign(isStdNeedAssign);
            Integer planStdCount = getInteger(request, "planStdCount" + task.getId());
            if (null != planStdCount) {
                task.getTeachClass().setPlanStdCount(planStdCount);
            }
            teachTaskService.saveTeachTask(task);
        }
        // 提示成功信息
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("entity.teachTask"));
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.save.success"));
        saveErrors(request, messages);
        return mapping.findForward("actionResult");
    }
    
    /**
     * 批量课程安排
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateArrangeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] taskIds = request.getParameterValues("taskId");
        if (null != taskIds) {
            teachTaskService.batchUpdateArrangeInfo(
                    teachTaskService.getTeachTasksByIds(SeqStringUtil.transformToLong(taskIds)),
                    (ArrangeInfo) populate(request, ArrangeInfo.class));
        }
        return redirect(request, "search", "info.update.success");
    }
    
    /**
     * 批量更改课程，学分
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateCourse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] taskIds = SeqStringUtil.transformToLong(get(request, "taskIds"));
        // 查找课程
        String courseNo = get(request, "course.code");
        Course course = null;
        if (StringUtils.isNotEmpty(courseNo)) {
            List courses = utilService.load(Course.class, "code", courseNo);
            if (courses.size() != 1) {
                return redirect(request, "search", "error.parameters.illegal");
            } else {
                course = (Course) courses.get(0);
            }
        }
        // 获取课程类别
        Long courseType = getLong(request, "course.type.code");
        // 备注
        String remark = get(request, "task.remark");
        
        if (null != taskIds) {
            List tasks = teachTaskService.getTeachTasksByIds(taskIds);
            for (Iterator iter = tasks.iterator(); iter.hasNext();) {
                TeachTask task = (TeachTask) iter.next();
                if (null != course) {
                    task.setCourse(course);
                }
                if (null != courseType) {
                    task.setCourseType((CourseType) baseCodeService.getCode(CourseType.class,
                            courseType));
                }
                if (null != remark) {
                    task.setRemark(remark);
                }
            }
            utilService.saveOrUpdate(tasks);
        }
        return redirect(request, "search", "info.update.success");
    }
    
    /**
     * 转向批量更改课程、学分页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateCourseSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] taskIds = SeqStringUtil.transformToLong(get(request, "taskIds"));
        addCollection(request, "tasks", utilService.load(TeachTask.class, "id", taskIds));
        addCollection(request, "courseTypes", baseCodeService.getCodes(CourseType.class));
        return forward(request);
    }
    
    /**
     * 课程要求保存
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateElectInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] taskIds = SeqStringUtil.transformToLong(get(request, "taskIds"));
        Long hskId = getLong(request, "HSKDegreeId");
        HSKDegree hskDegree = null;
        if (null != hskId) {
            hskDegree = (HSKDegree) utilService.get(HSKDegree.class, hskId);
        }
        String code = get(request, "courseCodes");
        if (taskIds == null || hskId == null && StringUtils.isEmpty(code)) {
            return forward(request, new Action("", "search"), "info.save.failure");
        }
        // check params
        String[] courseCodes = StringUtils.split(code, ",");
        List courses = new ArrayList();
        if (courseCodes != null && courseCodes.length > 0) {
            for (int i = 0; i < courseCodes.length; i++) {
                List list = utilService.load(Course.class, "code", courseCodes[i]);
                if (!list.isEmpty()) {
                    courses.add(list.get(0));
                }
            }
        }
        List teachTasks = new ArrayList();
        for (int i = 0; i < taskIds.length; i++) {
            TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskIds[i]);
            if (null != hskDegree) {
                task.getElectInfo().setHSKDegree(hskDegree);
            }
            if (courses.size() > 0) {
                task.getElectInfo().getPrerequisteCourses().addAll(courses);
            }
            task.getTeachClass().processTaskForClass();
            teachTasks.add(task);
        }
        utilService.saveOrUpdate(teachTasks);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 课程要求设置
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateElectInfoSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = get(request, "taskIds");
        if (StringUtils.isEmpty(taskIds)) {
            return forward(mapping, request, "error.teachTask.id.needed", "error");
        }
        addSingleParameter(request, "tasks", taskListQuery(request, taskIds));
        initBaseCodes(request, "hskLevels", HSKDegree.class);
        return forward(request);
    }
    
    /**
     * 批量更改课程要求页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateRequirement(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] taskIds = SeqStringUtil.transformToLong(get(request, "taskIds"));
        Long teachLangTypeId = getLong(request, "teachLangTypeId");
        Long courseCategoryId = getLong(request, "courseCategoryId");
        Long examModeId = getLong(request, "task.examMode.id");
        TeachLangType type = (TeachLangType) baseCodeService.getCode(TeachLangType.class,
                teachLangTypeId);
        CourseCategory type1 = (CourseCategory) baseCodeService.getCode(CourseCategory.class,
                courseCategoryId);
        ExamMode examMode = (ExamMode) baseCodeService.getCode(ExamMode.class, examModeId);
        List tasks = (List) utilService.load(TeachTask.class, "id", taskIds);
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            task.getRequirement().setTeachLangType(type);
            task.getRequirement().setCourseCategory(type1);
            task.getTeachClass().processTaskForClass();
            task.setExamMode(examMode);
        }
        utilService.saveOrUpdate(tasks);
        return redirect(request, "search", "info.update.success");
    }
    
    /**
     * 批量更改课程要求页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateRequirementSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] taskIds = SeqStringUtil.transformToLong(get(request, "taskIds"));
        addCollection(request, "tasks", utilService.load(TeachTask.class, "id", taskIds));
        addCollection(request, "teachLangTypes", baseCodeService.getCodes(TeachLangType.class));
        initBaseCodes(request, "courseCategoryList", CourseCategory.class);
        return forward(request);
    }
    
    /**
     * 根据行政班计划人数计算任务的计划人数<br>
     * 如果没有对应的行政班，则忽略。
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward calcPlanStdCountByAdminClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = get(request, "taskIds");
        if (StringUtils.isEmpty(taskIds)) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        Integer inputNum = getInteger(request, "inputNum");
        List tasks = teachTaskService.getTeachTasksByIds(taskIds);
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            task.getTeachClass().calcPlanStdCount(inputNum.intValue() == 2);
            task.getTeachClass().processTaskForClass();
        }
        utilService.saveOrUpdate(tasks);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 根据行政班实际人数计算任务的计划人数<br>
     * 如果没有对应的行政班，则忽略。
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward calcRealStdCountByAdminClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = get(request, "taskIds");
        if (StringUtils.isEmpty(taskIds)) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        List tasks = teachTaskService.getTeachTasksByIds(taskIds);
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            task.getTeachClass().calcStdCount();
            task.getTeachClass().processTaskForClass();
        }
        utilService.saveOrUpdate(tasks);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 检查安排信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward checkArrangeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        entityQuery.add(new Condition("task.teachClass.stdType in(:stdTypes) and task.arrangeInfo.teachDepart in(:departs)", getStdTypesOf(
                stdTypeId, request), getDeparts(request)));
        entityQuery.add(new Condition("task.calendar.id=" + get(request, "calendar.id")));
        entityQuery.add(new Condition("task.arrangeInfo.weeks * task.arrangeInfo.weekUnits <> task.arrangeInfo.overallUnits"));
        addCollection(request, "tasks", utilService.search(entityQuery));
        return forward(request);
    }
    
    /**
     * 检查任务中的计划人数是否大于工作量中的最大人数
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward checkInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        Long calendarId = getLong(request, "calendar.id");
        List teachTaskList = utilService.searchHQLQuery("from TeachTask tt where tt.calendar.id ="
                + calendarId
                + " and tt.teachClass.stdType.id ="
                + stdTypeId
                + " and tt.teachClass.stdCount>= (select max(tm.maxPeople) from TeachModulus tm where tt."
                + "requirement.courseCategory.id=tm.courseCategory.id)");
        request.setAttribute("teachTaskList", teachTaskList);
        return forward(request);
    }
    
    /**
     * 复制教学任务到指定学期
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward copy(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String[] taskIds = request.getParameterValues("taskId");
        if (null != taskIds) {
            List tasks = teachTaskService.getTeachTasksByIds(SeqStringUtil.transformToLong(taskIds));
            TeachCalendar calendar = (TeachCalendar) populate(request, TeachCalendar.class);
            calendar = teachCalendarService.getTeachCalendar(calendar.getStudentType().getId(),
                    calendar.getYear(), calendar.getTerm());
            TaskCopyParams params = new TaskCopyParams();
            RequestUtil.populate(request, params, true);
            params.setCalendar(calendar);
            for (int i = 0; i < params.getCopyCount().intValue(); i++) {
                teachTaskService.copy(tasks, params);
            }
        }
        return redirect(request, "search", "info.update.success");
    }
    
    /**
     * 拷贝教学任务设置
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward copySetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "calendar.id");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
        addSingleParameter(request, "calendar", calendar);
        String taskIds = get(request, "taskIds");
        List tasks = Collections.EMPTY_LIST;
        if (StringUtils.isNotEmpty(taskIds)) {
            tasks = teachTaskService.getTeachTasksByIds(taskIds);
        }
        addCollection(request, "stdTypeList", getStdTypes(request));
        addCollection(request, "taskList", tasks);
        return forward(request);
    }
    
    /**
     * 课程列表
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
        addCollection(request, "courses",
                utilService.search(baseInfoSearchHelper.buildCourseQuery(request)));
        return forward(request);
    }
    
    public ActionForward declarationForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = teachTaskSearchHelper.buildTaskQuery(request, Boolean.TRUE);
        query.setLimit(null);
        addCollection(request, "tasks", utilService.search(query));
        addSingleParameter(request, "perPageRows", getLong(request, "perPageRows"));
        return forward(request);
    }
    
    /**
     * 导出任务申报表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward exportDeclarationForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = teachTaskSearchHelper.buildTaskQuery(request, Boolean.TRUE);
        query.setLimit(null);
        
        // 查找导出参数
        String format = Transfer.EXCEL;
        String fileName = get(request, "fileName");
        String template = get(request, "template");
        
        if (StringUtils.isEmpty(fileName)) {
            fileName = "exportResult";
        }
        
        // 配置导出上下文
        Context context = new Context();
        context.getDatas().put("format", format);
        context.getDatas().put("exportFile", fileName);
        SystemConfig config = (SystemConfig) request.getSession().getServletContext().getAttribute(
                SystemConfig.SYSTEM_CONFIG);
        String defaultPath = request.getSession().getServletContext().getRealPath(
                FilePath.fileDirectory);
        String filePath = FilePath.getRealPath(config, FilePath.TEMPLATE_DOWNLOAD, defaultPath);
        template = filePath + template;
        context.getDatas().put("templatePath", template);
        context.getDatas().put("items", utilService.search(query));
        
        // 构造合适的输出器
        Exporter exporter = new TemplateExporter();
        exporter.setWriter(new ExcelTemplateWriter(response.getOutputStream()));
        
        response.setContentType("application/vnd.ms-excel;charset=GBK");
        response.setHeader("Content-Disposition", "attachment;filename="
                + RequestUtils.encodeAttachName(request,
                        context.getDatas().get("exportFile").toString()) + ".xls");
        
        // 进行输出
        exporter.setContext(context);
        exporter.transfer(new TransferResult());
        return null;
    }
    
    /**
     * 编辑教学任务
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
        Long taskId = getLong(request, "task.id");
        Long courseId = getLong(request, "task.course.id");
        if (null == courseId && null == taskId) {
            return selectCourse(mapping, form, request, response);
        }
        
        TeachTask task = null;
        if (null == taskId) {
            Course course = (Course) utilService.load(Course.class, courseId);
            TeachCalendar calendar = (TeachCalendar) utilService.load(TeachCalendar.class,
                    new Long(get(request, "task.calendar.id")));
            task = (TeachTask) populate(request, TeachTask.class, Constants.TEACHTASK);
            EntityUtils.merge(task, TeachTask.getDefault());
            task.setCourse(course);
            if (null != course.getExtInfo().getCourseType())
                task.setCourseType(course.getExtInfo().getCourseType());
            task.setCalendar(calendar);
            task.getTeachClass().setName(course.getName());
            task.getTeachClass().setStdType(
                    studentTypeService.getStudentType(getLong(request, "stdType.id")));
            task.getArrangeInfo().setWeeks(calendar.getWeeks());
            task.getArrangeInfo().setOverallUnits(course.getExtInfo().getPeriod());
            task.getArrangeInfo().calcWeekUnits();
        } else {
            task = teachTaskService.loadTeachTask(taskId);
            if (!new DataAuthorityPredicate(getStdTypeIdSeq(request), getDepartmentIdSeq(request), "teachClass.stdType", "arrangeInfo.teachDepart").evaluate(task))
                return forward(mapping, request, "error.dataRealm.insufficient", "error");
        }
        if (task.getArrangeInfo().isSingleTeacher()) {
            Teacher teacher = (Teacher) task.getArrangeInfo().getTeachers().iterator().next();
            addSingleParameter(request, "teacher", teacher);
            addSingleParameter(request, "teacherDepart", teacher.getDepartment());
        }
        addSingleParameter(request, "task", task);
        initBaseCodes(request, "courseTypeList", CourseType.class);
        initBaseCodes(request, "courseCategoryList", CourseCategory.class);
        initBaseCodes(request, "configTypeList", ClassroomType.class);
        initBaseCodes(request, "teachLangTypeList", TeachLangType.class);
        initBaseCodes(request, "schoolDistrictList", SchoolDistrict.class);
        initBaseCodes(request, "genderList", Gender.class);
        setDeparts(request, task);
        
        List stdTypes = studentTypeService.getStudentTypes(studentTypeService.getStdTypeIdSeqUnder(task.getCalendar().getStudentType().getId()));
        addSingleParameter(request, "stdTypeList", CollectionUtils.intersection(stdTypes,
                getStdTypes(request)));
        initBaseCodes(request, "HSKDegreeList", HSKDegree.class);
        initBaseCodes(request, "teachLangTypes", TeachLangType.class);
        initBaseCodes(request, "genderList", Gender.class);
        initBaseCodes(request, "roomTypes", ClassroomType.class);
        
        List teacherResults = utilService.load(Teacher.class, "code", getUser(request).getName());
        if (CollectionUtils.isNotEmpty(teacherResults)) {
            addSingleParameter(request, "teacher", teacherResults.get(0));
        }
        return forward(request);
    }
    
    public ActionForward teachTaskCheck(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long taskId = getLong(request, "task.id");
        Long courseId = getLong(request, "task.course.id");
        if (null == courseId && null == taskId) {
            return selectCourse(mapping, form, request, response);
        }
        TeachTask task = teachTaskService.loadTeachTask(taskId);
        
        List list = (List) utilService.searchHQLQuery("from CourseTake ct where ct.task.id ="
                + task.getId());
        String stdString = "";
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            CourseTake courseTake = (CourseTake) iter.next();
            if (iter.hasNext()) {
                stdString += courseTake.getStudent().getId() + ",";
            } else {
                stdString += courseTake.getStudent().getId();
            }
        }
        List stdList = (List) utilService.searchHQLQuery("from Student std where std.id not in ("
                + stdString
                + ") and exists (from AdminClass ac where ac.students.id=std.id and exists"
                + " (from TeachTask tt where tt.teachClass.adminClasses.id=ac.id and tt.id ="
                + taskId + "))");
        
        List teachTaskForClassList = (List) utilService.searchHQLQuery("from TeachTask tt where tt.id ="
                + taskId
                + " "
                + "and exists (from AdminClass ac where tt.teachClass.adminClasses.id=ac.id)");
        
        request.setAttribute("studentList", stdList);
        request.setAttribute("task", task);
        request.setAttribute("teachTaskForClassList", teachTaskForClassList);
        return forward(request);
    }
    
    /**
     * 选择该导出的字段
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward exportSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        MessageResources message = getResources(request, "excelconfig");
        
        String[] keys = StringUtils.split(message.getMessage(getLocale(request), "teachTask.keys"),
                ",");
        String[] titles = StringUtils.split(message.getMessage(getLocale(request),
                "teachTask.showkeys"), ",");
        Map keyMap = new HashMap();
        for (int i = 0; i < titles.length; i++) {
            keyMap.put(keys[i], titles[i]);
        }
        addSingleParameter(request, "keyMap", keyMap);
        return forward(request);
    }
    
    public ActionForward exportStdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teachTaskIds = get(request, "teachTaskIds");
        if (StringUtils.isEmpty(teachTaskIds)) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        String attrs = get(request, "attrs");
        String attrNames = get(request, "attrNames");
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        entityQuery.join("task.teachClass.courseTakes", "courseTake").join("courseTake.student",
                "std");
        entityQuery.join("left", "std.firstMajor", "firstMajor").join("left", "std.firstAspect",
                "firstAspect");
        entityQuery.add(new Condition("task.id in (:taskIds)", SeqStringUtil.transformToLong(teachTaskIds)));
        entityQuery.setSelect("select " + attrs + " ");
        entityQuery.addOrder(OrderUtils.parser("task.seqNo"));
        entityQuery.addOrder(OrderUtils.parser("std.code"));
        ExcelTools et = new ExcelTools();
        HSSFWorkbook wb = et.toExcel(utilService.search(entityQuery), attrNames);
        response.setContentType("application/vnd.ms-excel;charset=GBK");
        response.setHeader("Content-Disposition", "attachment;filename=" + "stdList.xls");
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
        return null;
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = teachTaskSearchHelper.buildTaskQuery(request, Boolean.TRUE);
        query.setLimit(null);
        return utilService.search(query);
    }
    
    protected PropertyExtractor getPropertyExtractor(HttpServletRequest request) {
        return new TeachTaskPropertyExtractor(getLocale(request), getResources(request), utilService);
    }
    
    /**
     * 列举教学任务中可以使用的班级列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward listClassForTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(AdminClass.class, "adminClass");
        populateConditions(request, query, "adminClass.stdType.id");
        Long stdTypeId = getLong(request, "adminClass.stdType.id");
        if (stdTypeId != null) {
            DataRealmUtils.addDataRealm(query, new String[] { "adminClass.stdType.id", null },
                    new DataRealm(studentTypeService.getStdTypeIdSeqUnder(stdTypeId), null));
        }
        query.setLimit(getPageLimit(request));
        addCollection(request, Constants.ADMINCLASS_LIST, utilService.search(query));
        return forward(request);
    }
    
    /**
     * 打印一个教学任务的学生名单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward printStdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teachTaskIds = get(request, "teachTaskIds");
        if (StringUtils.isEmpty(teachTaskIds)) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        printStdListPrepare(teachTaskIds, request);
        return forward(request);
    }
    
    /**
     * 打印一个或多个教学任务的考勤表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward printStdListForDuty(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teachTaskIds = get(request, "teachTaskIds");
        if (StringUtils.isEmpty(teachTaskIds)) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        
        List tasks = utilService.load(TeachTask.class, "id",
                SeqStringUtil.transformToLong(teachTaskIds));
        
        Map arrangeInfos = new HashMap();
        Map courseTakes = new HashMap();
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            arrangeInfos.put(task.getId().toString(), CourseActivityDigestor.digest(task,
                    getResources(request), getLocale(request)));
            ArrayList myCourseTakes = new ArrayList();
            myCourseTakes.addAll(task.getTeachClass().getCourseTakes());
            courseTakes.put(task.getId().toString(), myCourseTakes);
        }
        addCollection(request, "tasks", tasks);
        addSingleParameter(request, "arrangeInfos", arrangeInfos);
        addSingleParameter(request, "courseTakes", courseTakes);
        return forward(request);
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
     */
    public ActionForward printStdListForGrade(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action(TeacherGradeAction.class, "printEmptyGradeTable"));
    }
    
    /**
     * 获取学生名单
     * 
     * @param taskIds
     * @param request
     * @throws Exception
     */
    public void printStdListPrepare(String taskIds, HttpServletRequest request) throws Exception {
        List tasks = utilService.load(TeachTask.class, "id", SeqStringUtil.transformToLong(taskIds));
        Map courseTakes = new HashMap();
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            ArrayList myCourseTakes = new ArrayList();
            myCourseTakes.addAll(task.getTeachClass().getCourseTakes());
            courseTakes.put(task.getId().toString(), myCourseTakes);
        }
        addCollection(request, "tasks", tasks);
        addSingleParameter(request, "courseTakes", courseTakes);
    }
    
    /**
     * 打印教学任务的任务书
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward printTaskForTeacher(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teachTaskIds = get(request, "teachTaskIds");
        if (StringUtils.isEmpty(teachTaskIds)) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        
        List tasks = utilService.load(TeachTask.class, "id",
                SeqStringUtil.transformToLong(teachTaskIds));
        Map arrangeInfos = new HashMap();
        CourseActivityDigestor.setDelimeter("<br>");
        String format = get(request, "format");
        if (StringUtils.isEmpty(format)) {
            format = ":teacher2:day:time :weeks:room(:district :building)";
        }
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            arrangeInfos.put(task.getId().toString(), CourseActivityDigestor.digest(
                    task.getCalendar(), task.getArrangeInfo(), getResources(request),
                    getLocale(request), format));
            ArrayList myCourseTakes = new ArrayList();
            myCourseTakes.addAll(task.getTeachClass().getCourseTakes());
        }
        addCollection(request, "tasks", tasks);
        addSingleParameter(request, "arrangeInfos", arrangeInfos);
        return forward(request);
    }
    
    /**
     * 打印教学任务列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward printTaskList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teachTaskIds = get(request, "teachTaskIds");
        if (StringUtils.isEmpty(teachTaskIds)) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        EntityQuery query = new EntityQuery(TeachTask.class, "task");
        query.add(new Condition("task.id in (:ids)", SeqStringUtil.transformToLong(teachTaskIds)));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        query.setLimit(null);
        addCollection(request, "tasks", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 汇总表打印预览
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward printTaskSummary(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = teachTaskSearchHelper.buildTaskQuery(request, Boolean.TRUE);
        query.setLimit(null);
        List tasks = (List) utilService.search(query);
        String properties = "arrangeInfo.teachDepart.code,course.code,teachClass.enrollTurn,teachClass.depart.code";
        MultiPropertyComparator multiCmp = new MultiPropertyComparator(properties);
        Collections.sort(tasks, multiCmp);
        addCollection(request, "tasks", tasks);
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id"));
        addSingleParameter(request, "systemConfig", SystemConfigLoader.getConfig());
        addSingleParameter(request, "calendar", calendar);
        return forward(request);
    }
    
    /**
     * 删除教学任务
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
        Long[] taskIds = SeqStringUtil.transformToLong(get(request, "taskIds"));
        List dd = teachTaskService.getTeachTasksByIds(taskIds);
        TeachCalendar calendar = ((TeachTask) dd.iterator().next()).getCalendar();
        TermCalculator tc = new TermCalculator(teachCalendarService, calendar);
        List warnTasks = new ArrayList();
        for (Iterator iterator = dd.iterator(); null == getBoolean(request, "isRemove")
                && iterator.hasNext();) {
            TeachTask task = (TeachTask) iterator.next();
            for (Iterator classIter = task.getTeachClass().getAdminClasses().iterator(); classIter.hasNext();) {
                AdminClass adminClass = (AdminClass) classIter.next();
                TeachPlan plan = teachPlanService.getTeachPlan(adminClass);
                if (null == plan) {
                    continue;
                }
                int term = 0;
                try {
                    term = tc.getTerm(plan.getStdType(), plan.getEnrollTurn(), Boolean.TRUE);
                } catch (OnCampusTimeNotFoundException e) {
                    term = 0;
                }
                Set courses = new HashSet(plan.getCourses(String.valueOf(term)));
                if (courses.contains(task.getCourse())) {
                    EntityQuery queryTask = new EntityQuery(TeachTask.class, "task");
                    queryTask.add(new Condition("task.course = :course", task.getCourse()));
                    queryTask.add(new Condition("task.calendar=:calendar", calendar));
                    queryTask.add(new Condition("task.id not in (:taskIds)", taskIds));
                    queryTask.join("task.teachClass.adminClasses", "adminClass");
                    queryTask.add(new Condition("adminClass.id=:classId", adminClass.getId()));
                    queryTask.setSelect("count(*)");
                    List rs = (List) utilService.search(queryTask);
                    if (((Number) rs.get(0)).intValue() == 0) {
                        warnTasks.add(task);
                    }
                }
            }
        }
        Set tasksCopy = new HashSet(dd);
        if (!warnTasks.isEmpty()) {
            tasksCopy.removeAll(warnTasks);
            addCollection(request, "tasks", tasksCopy);
            addCollection(request, "problemTasks", warnTasks);
            return forward(request, "toRemove");
        }
        
        Set tasks = new HashSet(teachTaskService.getTeachTasksByIds(get(request, "taskIds")));
        if (CollectionUtils.isEmpty(tasks)) {
            return forward(mapping, request, "task.id.NotExists", "error");
        }
        Date now = new Date(System.currentTimeMillis());
        
        boolean isToText = StringUtils.equals(get(request, "toBeText"), "toBeText");
        for (Iterator it = tasks.iterator(); it.hasNext();) {
            TeachTask task = (TeachTask) it.next();
            try {
                if (null != task) {
                    task.getIsConfirm();
                }
            } catch (Exception e) {
                return forward(mapping, request, "task.id.NotExists", "error");
            }
            // 以开课院系做权限划分
            if (!SeqStringUtil.isIn(getDepartmentIdSeq(request),
                    task.getArrangeInfo().getTeachDepart().getId().toString())) {
                return forward(mapping, request, "error.depart.dataRealm.insufficient", "error");
            }
            try {
                if (isToText) {
                    Message msg = new Message();
                    MessageType msgType = new MessageType();
                    msg.setType(msgType);
                    msgType.setId(MessageType.COMMON);
                    msg.setCreateAt(now);
                    msg.setActiveOn(now);
                    msg.setSender(getUser(request.getSession()));
                    msg.setTitle("《" + task.getCourse().getName() + "》被退课");
                    msg.setBody("[" + task.getCalendar().getYear() + " "
                            + task.getCalendar().getTerm() + "]学期，课程序号为 " + task.getSeqNo()
                            + " ，课程代码为 " + task.getCourse().getCode() + " 的《"
                            + task.getCourse().getName() + "》被退课。");
                    for (Iterator it2 = task.getTeachClass().getStds().iterator(); it2.hasNext();) {
                        Student student = (Student) it2.next();
                        SystemMessage sysMsg = new SystemMessage();
                        sysMsg.setRecipient((User) utilService.load(User.class, "name",
                                student.getCode()).get(0));
                        sysMsg.setStatus(SystemMessage.newly);
                        msg.getReceiptors().add(sysMsg);
                    }
                    if (CollectionUtils.isNotEmpty(msg.getReceiptors())) {
                        utilService.saveOrUpdate(msg);
                    }
                }
                // 与成绩日志脱离关系
                // EntityQuery query = new EntityQuery(GradeLog.class, "gradeCatalog");
                // query.add(new Condition("gradeCatalog.task = :task", task));
                // Collection gradeCatalogs = utilService.search(query);
                // for (Iterator it2 = gradeCatalogs.iterator(); it2.hasNext();) {
                // GradeLog gradeCatalog = (GradeLog) it2.next();
                // gradeCatalog.setTask(null);
                // }
                
                teachTaskService.removeTeachTask(task);
                // utilService.saveOrUpdate(gradeCatalogs);
                logHelper.info(request, "Remove teachTask with No.:" + task.getSeqNo()
                        + " course No.:" + task.getCourse().getCode());
            } catch (Exception e) {
                logHelper.info(request, "Failure in deleting teachTask with id:" + task.getId());
                return forward(mapping, request, "error.teachTask.deleteFailure", "error");
            }
        }
        ActionMessages messages = new ActionMessages();
        if (isToText) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.action.success"));
            saveErrors(request, messages);
            return mapping.findForward("actionResult");
        }
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("entity.teachTask"));
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.delete.success"));
        saveErrors(request, messages);
        addSingleParameter(request, "method", "search");
        
        if (Boolean.TRUE.equals(getBoolean(request, "practiceCourse"))) {
            return redirect(request, new Action("courseTakeForTaskDuplicate", "taskList"),
                    "info.save.success");
        }
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 保存教学任务信息<br>
     * 如果教学任务的计划人数为零，则从行政班计算计划人数.<br>
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
        TeachTask task = (TeachTask) populateEntity(request, TeachTask.class, Constants.TEACHTASK);
        // 获得教学任务关联的行政班级
        Set adminClassSet = new HashSet(utilService.load(AdminClass.class, "id",
                SeqStringUtil.transformToLong(get(request, Constants.ADMINCLASS_KEYSEQ))));
        Boolean autoReName = getBoolean(request, "autoReName");
        // 存储教学任务信息
        Boolean calcStdCount = getBoolean(request, "calcStdCount");
        // 是否需要用户进一步确认后保存，默认为不需要。
        try {
            String teacherIdSeq = get(request, "teacherIds");
            String preCourseCodeSeq = get(request, "preCourseCodeSeq");
            if (task.isVO()) {
                TeachTask defaultTask = TeachTask.getDefault();
                EntityUtils.evictEmptyProperty(defaultTask);
                
                defaultTask.setGradeState(new GradeState(defaultTask));
                
                // 计算总学时
                task.getArrangeInfo().calcOverallUnits();
                EntityUtils.merge(defaultTask, task);
                defaultTask.getTeachClass().getAdminClasses().addAll(adminClassSet);
                if (null != calcStdCount && calcStdCount.equals(Boolean.TRUE)) {
                    defaultTask.getTeachClass().calcPlanStdCount(true);
                }
                
                setTeachersAndRooms(teacherIdSeq, defaultTask);
                setPreCourses(preCourseCodeSeq, defaultTask);
                if (Boolean.TRUE.equals(autoReName)) {
                    defaultTask.getTeachClass().reNameByClass();
                }
                // FIXME
                if (null != task.getArrangeInfo().getSuggest()) {
                    task.getArrangeInfo().getSuggest().setTime(
                            new AvailableTime(AvailableTime.commonTeacherAvailTime));
                }
                teachTaskService.saveTeachTask(defaultTask);
                logHelper.info(request, "Create a teachTask with id:" + defaultTask.getId()
                        + " and course name:" + defaultTask.getCourse().getName());
            } else {
                Set taskAdminClasses = task.getTeachClass().getAdminClasses();
                // 统计在保存前的班级
                Set adminClasses1 = new HashSet();
                adminClasses1.addAll(taskAdminClasses);
                addCollection(request, "taskAdminClasses", adminClasses1);
                taskAdminClasses.clear();
                taskAdminClasses.addAll(adminClassSet);
                if (null != calcStdCount && calcStdCount.equals(Boolean.TRUE)) {
                    task.getTeachClass().calcPlanStdCount(true);
                }
                setTeachersAndRooms(teacherIdSeq, task);
                setPreCourses(preCourseCodeSeq, task);
                if (Boolean.TRUE.equals(autoReName)) {
                    task.getTeachClass().reNameByClass();
                }
                if (null != task.getArrangeInfo().getSuggest()
                        && null != task.getArrangeInfo().getSuggest().getTime()
                        && StringUtils.isNotEmpty(task.getArrangeInfo().getSuggest().getTime().getStruct())) {
                    task.getArrangeInfo().getSuggest().setTime(
                            new AvailableTime(AvailableTime.commonTeacherAvailTime));
                }
                // 否则就直接保存操作
                teachTaskService.updateTeachTask(task);
                logHelper.info(request, "Update a teachTask with id:" + task.getId()
                        + " and course name:" + task.getCourse().getName());
                
                // 统计在保存后的班级
                Set adminClasses2 = new HashSet();
                adminClasses2.addAll(taskAdminClasses);
                
                Collection c1 = CollectionUtils.subtract(adminClasses1, adminClasses2);
                Collection c2 = CollectionUtils.subtract(adminClasses2, adminClasses1);
                // 是否要显示同步已指定的学生
                if (CollectionUtils.isNotEmpty(c1) || CollectionUtils.isNotEmpty(c2)) {
                    saveErrors(request.getSession(),
                            ForwardSupport.buildMessages(new String[] { "info.save.success" }));
                    addSingleParameter(request, "task", task);
                    addCollection(request, "adminClassSet", adminClassSet);
                    return forward(request, "../teachTaskCollege/taskOperationConfirm");
                }
            }
        } catch (Exception e) {
            logHelper.info(request, "Failure occured in create or update teachTask with id:"
                    + task.getId() + " and course name:" + task.getCourse().getName(), e);
            return forward(mapping, request, "error.occurred", "error");
        }
        if (Boolean.TRUE.equals(getBoolean(request, "practiceCourse"))) {
            return redirect(request, new Action("courseTakeForTaskDuplicate", "taskList"),
                    "info.save.success");
        }
        if (StringUtils.isNotEmpty(get(request, "forward"))) {
            saveErrors(request.getSession(),
                    ForwardSupport.buildMessages(new String[] { "info.save.success" }));
            return mapping.findForward(get(request, "forward"));
        } else {
            return redirect(request, "search", "info.save.success");
        }
    }
    
    /**
     * 按用户确认保存任务的教学班
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveTaskAdminClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachTask task = teachTaskService.getTeachTask(getLong(request, "taskId"));
        String operations = get(request, "operations");
        Set takes = task.getTeachClass().getCourseTakes();
        if (Boolean.TRUE.equals(getBoolean(request, "isToDo"))
                && StringUtils.isNotEmpty(operations)) {
            // 移除学生
            String removeAdminClassIds = get(request, "removeAdminClassIds");
            Set deleteObject = new HashSet();
            if (StringUtils.contains("," + operations + ",", ",1,")
                    && StringUtils.isNotEmpty(removeAdminClassIds)) {
                for (Iterator it1 = adminClassService.getAdminClassesById(removeAdminClassIds).iterator(); it1.hasNext();) {
                    AdminClass adminClass = (AdminClass) it1.next();
                    for (Iterator it2 = adminClass.getStudents().iterator(); it2.hasNext();) {
                        Student std = (Student) it2.next();
                        CourseTake take = task.getTeachClass().getCourseTake(std);
                        if (null != take) {
                            takes.remove(take);
                            deleteObject.add(take);
                        }
                    }
                }
            }
            // 加入学生
            String addAdminClassIds = get(request, "addAdminClassIds");
            if (StringUtils.contains("," + operations + ",", ",2,")
                    && StringUtils.isNotEmpty(addAdminClassIds)) {
                for (Iterator it1 = adminClassService.getAdminClassesById(addAdminClassIds).iterator(); it1.hasNext();) {
                    AdminClass adminClass = (AdminClass) it1.next();
                    for (Iterator it2 = adminClass.getStudents().iterator(); it2.hasNext();) {
                        Student std = (Student) it2.next();
                        CourseTake take = new CourseTake(task, std, (CourseTakeType) utilService.load(
                                CourseTakeType.class, CourseTakeType.COMPULSORY));
                        task.getTeachClass().addCourseTake(take);
                    }
                }
            }
            utilService.remove(deleteObject);
        }
        task.getTeachClass().processTaskForClass();
        utilService.saveOrUpdate(task);
        if (Boolean.TRUE.equals(getBoolean(request, "practiceCourse"))) {
            return redirect(request, new Action("courseTakeForTaskDuplicate", "taskList"),
                    "info.save.success");
        }
        return redirect(request, "search", "info.save.success");
    }
    
    /**
     * 保存批量添加的教学任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveMultiTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 准备任务
        TeachTask task = (TeachTask) populate(request, TeachTask.class, Constants.TEACHTASK);
        EntityUtils.evictEmptyProperty(task);
        Course course = (Course) utilService.load(Course.class, task.getCourse().getId());
        task.getArrangeInfo().setOverallUnits(course.getExtInfo().getPeriod());
        task.getArrangeInfo().calcWeekUnits();
        
        task.setGradeState(new GradeState(task));
        Integer teacherNum = getInteger(request, "teacherNum");
        List tasks = new ArrayList();
        Integer adminClassNum = getInteger(request, "adminClassNum");
        for (int i = 0; i < teacherNum.intValue(); i++) {
            String teacherIdNo = "teacherId_" + i;
            Long stdTypeId = getLong(request, teacherIdNo + "stdTypeOfSpecialityId_" + i);
            if (null == stdTypeId) {
                continue;
            }
            StudentType stdType = studentTypeService.getStudentType(stdTypeId);
            Long teacherId = getLong(request, teacherIdNo);
            // 针对教师对应的班级进行实例化教学任务
            for (int j = 0, start = i; j < adminClassNum.intValue(); j++, start++) {
                String teachClassId = teacherIdNo + "teachClassId_" + start;
                Long teachDepartId = getLong(request, teachClassId + ".depart.id");
                if (null == teachDepartId) {
                    continue;
                }
                // 获得教学班
                TeachClass teachClass = (TeachClass) populate(request, TeachClass.class,
                        teachClassId);
                teachClass.setStdType(stdType);
                teachClass.setStdState(new Integer(TeachClass.STD_NULL));
                TeachTask newTask = (TeachTask) task.clone();
                EntityUtils.merge(newTask.getTeachClass(), teachClass);
                EntityUtils.evictEmptyProperty(newTask.getTeachClass());
                Long adminClassId = getLong(request, teacherIdNo + "adminClassId_" + start);
                // 设置行政班，以及教学班的名称
                if (null != adminClassId && adminClassId.intValue() != 0) {
                    AdminClass adminClass = (AdminClass) utilService.load(AdminClass.class,
                            adminClassId);
                    newTask.getTeachClass().getAdminClasses().add(adminClass);
                    if (newTask.getTeachClass().getName().equals(newTask.getCourse().getName())) {
                        newTask.getTeachClass().setName(adminClass.getName());
                    }
                }
                newTask.getArrangeInfo().getTeachers().add(new Teacher(teacherId));
                newTask.getElectInfo().setMaxStdCount(new Integer(0));
                newTask.getElectInfo().setMinStdCount(new Integer(0));
                newTask.getElectInfo().setIsElectable(Boolean.FALSE);
                newTask.getElectInfo().setIsCancelable(Boolean.FALSE);
                newTask.getRequirement().setEvaluateByTeacher(Boolean.FALSE);
                if (null != course.getCategory()) {
                    task.getRequirement().setCourseCategory(course.getCategory());
                }
                newTask.setIsConfirm(Boolean.FALSE);
                newTask.getTeachClass().processTaskForClass();
                tasks.add(newTask);
            }
        }
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask teachTask = (TeachTask) iter.next();
            teachTaskService.saveTeachTask(teachTask);
        }
        // 提示成功信息
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("entity.teachTask"));
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.save.success"));
        saveErrors(request, messages);
        addSingleParameter(request, "method", "search");
        return mapping.findForward("redirector");
    }
    
    /**
     * 为了添加教学任务，进入选择课程.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward selectCourse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 检查要添加的教学任务的学生类别是否在权限范围内
        TeachCalendar calendar = (TeachCalendar) populateEntity(request, TeachCalendar.class,
                Constants.CALENDAR);
        if (null == calendar.getStudentType().getId()) {
            return forward(mapping, request, "error.stdType.id.needed", "error");
        }
        String stdTypeDataRealm = getStdTypeIdSeq(request);
        if (StringUtils.isEmpty(stdTypeDataRealm)) {
            return forward(mapping, request, "error.stdType.dataRealm.notExists", "error");
        }
        if (!StringUtils.contains(stdTypeDataRealm, calendar.getStudentType().getId().toString())) {
            return forward(mapping, request, "error.stdType.dataRealm.insufficient", "error");
        }
        addSingleParameter(request, "stdTypeList", baseInfoService.getBaseInfos(StudentType.class));
        addSingleParameter(request, Constants.STUDENTTYPE, utilService.load(StudentType.class,
                calendar.getStudentType().getId()));
        addSingleParameter(request, Constants.CALENDAR, calendar);
        
        return forward(request);
    }
    
    /**
     * 依照周课时和周数,调整排课信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward selfAdjustArrangeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] taskIds = SeqStringUtil.transformToLong(get(request, "taskIds"));
        if (null != taskIds || taskIds.length > 0) {
            List tasks = utilService.load(TeachTask.class, "id", taskIds);
            for (Iterator iter = tasks.iterator(); iter.hasNext();) {
                TeachTask task = (TeachTask) iter.next();
                task.getArrangeInfo().setOverallUnits(
                        new Integer(task.getArrangeInfo().getWeeks().intValue()
                                * task.getArrangeInfo().getWeekUnits().intValue()));
            }
            utilService.saveOrUpdate(tasks);
        }
        saveErrors(request.getSession(),
                ForwardSupport.buildMessages(new String[] { "info.action.success" }));
        return mapping.findForward("actionResult");
    }
    
    public void setAdminClassService(AdminClassService adminClassService) {
        this.adminClassService = adminClassService;
    }
    
    public void setBaseInfoSearchHelper(BaseInfoSearchHelper baseInfoSearchHelper) {
        this.baseInfoSearchHelper = baseInfoSearchHelper;
    }
    
    /**
     * 是指填写任务所需的院系列表<br>
     * 开课院系(teachDepartList)<br>
     * 上课院系(departmentList)<br>
     * 教师所在院系(teacherDepartList),其中teacherDepart为缺省教师所在院系
     * 
     * @param request
     * @param task
     */
    protected void setDeparts(HttpServletRequest request, TeachTask task) {
        /**
         * 如果开课院系和上课院系为空时,默认是指为权限范围内的第一个院系
         */
        List departs = getDeparts(request);
        // 教学班要有院系
        if (!task.getTeachClass().getDepart().isPO()) {
            task.getTeachClass().setDepart((Department) departs.get(0));
        }
        // 开课院系也不能为空
        if (!task.getArrangeInfo().getTeachDepart().isPO()) {
            task.getArrangeInfo().setTeachDepart((Department) departs.get(0));
        }
        addSingleParameter(request, "teacherDepart", departs.get(0));
        // 学生所在院系
        Collection departList = departmentService.getRelatedDeparts(getStdTypeIdSeqOf(
                task.getTeachClass().getStdType().getId(), request));
        
        // 开课院系列表
        addCollection(request, "teachDepartList", departmentService.getTeachDeparts());
        addCollection(request, Constants.DEPARTMENT_LIST, departList);
        addTeacherDepartList(request);
    }
    
    /**
     * @param request
     */
    protected void addTeacherDepartList(HttpServletRequest request) {
        // 老师所在院系列表
        EntityQuery entityQuery = new EntityQuery(Teacher.class, "teacher");
        entityQuery.add(new Condition("teacher.isTeaching= true"));
        entityQuery.setSelect("select distinct teacher.department ");
        addCollection(request, "teacherDepartList", utilService.search(entityQuery));
    }
    
    protected void setPreCourses(String preCourseCodeSeq, TeachTask task) {
        task.getElectInfo().getPrerequisteCourses().clear();
        if (StringUtils.isNotEmpty(preCourseCodeSeq)) {
            task.getElectInfo().getPrerequisteCourses().addAll(
                    utilService.load(Course.class, "code", StringUtils.split(preCourseCodeSeq, ",")));
        }
    }
    
    /**
     * 根据教师Id串，设置教师<br>
     * 
     * @param request
     * @param task
     */
    protected void setTeachersAndRooms(String teacherIdSeq, TeachTask task) {
        // 任务中教师
        Set oldTeachers = new HashSet(task.getArrangeInfo().getTeachers());
        // 修改后教师
        Set newTeacherIds = new HashSet();
        task.getArrangeInfo().getTeachers().clear();
        Long[] staffIds = SeqStringUtil.transformToLong(teacherIdSeq);
        // 先清空教师
        task.getArrangeInfo().getTeachers().clear();
        if (null == staffIds || staffIds.length == 0) {
            return;
        }
        List newTeachers = new ArrayList();
        for (int i = 0; null != staffIds && i < staffIds.length; i++) {
            newTeacherIds.add(staffIds[i]);
            newTeachers.add(utilService.load(Teacher.class, "id", staffIds[i]));
            task.getArrangeInfo().getTeachers().addAll(
                    utilService.load(Teacher.class, "id", staffIds[i]));
        }
        // 如果已经排课，处理要替换的教师
        List oldTemps = (List) CollectionUtils.subtract(oldTeachers, newTeachers);
        List newTemps = (List) CollectionUtils.subtract(newTeachers, oldTeachers);
        if (oldTemps.size() < 1 || newTemps.size() < 1) {
            return;
        }
        for (Iterator it = task.getArrangeInfo().getActivities().iterator(); it.hasNext();) {
            CourseActivity activity = (CourseActivity) it.next();
            if (null == activity.getTeacher()
                    || ObjectUtils.equals(activity.getTeacher(),
                            (Teacher) oldTemps.iterator().next())) {
                activity.setTeacher((Teacher) ((List) newTemps.iterator().next()).get(0));
            }
        }
    }
    
    /**
     * 得到学生所查记录列表
     * 
     * @param request
     * @param taskIds
     * @return
     */
    protected Collection taskListQuery(HttpServletRequest request, String taskIds) {
        EntityQuery query = new EntityQuery(TeachTask.class, "task");
        query.add(new Condition("task.id in (:ids)", SeqStringUtil.transformToLong(taskIds)));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        return utilService.search(query);
    }
    
    /**
     * 班级课程类别的开课情况 (此功能主要用于限选模块汇总)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward taskOfCourseTypeList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "calendar.id");
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        EntityQuery query = new EntityQuery(CourseType.class, "type");
        query.add(new Condition("type.state=true")).add(new Condition("type.isModuleType=true"));
        Collection courseTypes = utilService.search(query);
        courseTypes.add(utilService.get(CourseType.class, CourseType.PUBLIC_COURSID));
        TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, calendarId);
        List taskOfCourseTypes = teachTaskService.getTaskOfCourseTypes(calendar, getStdTypeIdSeqOf(
                stdTypeId, request), getDepartmentIdSeq(request), courseTypes);
        String orderBy = get(request, "orderBy");
        if (StringUtils.isEmpty(orderBy)) {
            orderBy = "courseType.name";
        }
        Collections.sort(taskOfCourseTypes, new PropertyComparator(orderBy));
        addCollection(request, "taskOfCourseTypes", taskOfCourseTypes);
        return forward(request);
    }
    
    /**
     * 任务书模版
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward taskTemplate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teachTaskIds = get(request, "teachTaskIds");
        addSingleParameter(request, "systemConfig", SystemConfigLoader.getConfig());
        if (StringUtils.isEmpty(teachTaskIds)) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        addSingleParameter(request, "taskCount",
                new Integer(StringUtils.split(teachTaskIds, ",").length));
        return forward(request);
    }
    
    public void setTeachPlanService(TeachPlanService teachPlanService) {
        this.teachPlanService = teachPlanService;
    }
}
