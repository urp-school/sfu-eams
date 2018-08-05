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
 * chaostone             2007-1-9            Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.grade;

import java.util.ArrayList;
import java.util.Collection;
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

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.eams.course.grade.converter.ConverterFactory;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.ExamStatus;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.security.User;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.exam.ExamTakeService;
import com.shufe.service.course.grade.gp.GradePointRuleService;

/**
 * 学生成绩(从学生角度出发,管理成绩)
 * 
 * @author chaostone
 */
public class StdGradeAction extends StdGradeSearchAction {
    
    protected GradePointRuleService gradePointRuleService;
    
    protected ExamTakeService examTakeService;
    
    // protected GradeLogService gradeLogService;
    
    protected CourseGradeHelper courseGradeHelper;
    
    /**
     * 添加录入成绩（按代码）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchAdd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "markStyles", baseCodeService.getCodes(MarkStyle.class));
        addCollection(request, "stdTypeList", getStdTypes(request));
        addCollection(request, "gradeTypes", utilService.search(queryGradeType()));
        return forward(request);
    }
    
    /**
     * 得到所有有效的成绩类别
     * 
     * @return
     */
    protected EntityQuery queryGradeType() {
        EntityQuery query = new EntityQuery(GradeType.class, "gradeType");
        query.add(new Condition("gradeType.examType is not null and gradeType.state=true"));
        return query;
    }
    
    /**
     * 得到录入教学任务时，符合条件的成绩类别
     * 
     * @return
     */
    private EntityQuery buildGradeTypeQuery() {
        Long[] gradeTypeIds = new Long[4];
        gradeTypeIds[0] = GradeType.DELAY;
        gradeTypeIds[1] = GradeType.MAKEUP;
        gradeTypeIds[2] = GradeType.FINAL;
        gradeTypeIds[3] = GradeType.GA;
        EntityQuery query = queryGradeType();
        query.add(new Condition("gradeType.id not in (:ids)", gradeTypeIds));
        query.add(new Condition("gradeType.state=true"));
        query.addOrder(new Order("gradeType.priority asc"));
        return query;
    }
    
    /**
     * 添加录入成绩（按序号）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addGrade(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "markStyles", baseCodeService.getCodes(MarkStyle.class));
        request.setAttribute("converter", ConverterFactory.getConverter());
        addCollection(request, "stdTypeList", getStdTypes(request));
        EntityQuery query = buildGradeTypeQuery();
        addCollection(request, "gradeTypes", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 显示批量修改界面
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
        String courseGradeIdSeq = request.getParameter("courseGradeIds");
        if (StringUtils.isEmpty(courseGradeIdSeq)) {
            return forwardError(mapping, request, "error.parameters.needed");
        }
        List grades = utilService.load(CourseGrade.class, "id", SeqStringUtil
                .transformToLong(courseGradeIdSeq));
        addCollection(request, "grades", grades);
        addCollection(request, "courseTypeList", baseCodeService.getCodes(CourseType.class));
        return forward(request);
    }
    
    /**
     * 保存批量修改成绩信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveBatchEdit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String courseGradeIdSeq = request.getParameter("courseGradeIds");
        if (StringUtils.isEmpty(courseGradeIdSeq)) {
            return forwardError(mapping, request, "error.parameters.needed");
        }
        String courseNo = request.getParameter("course.code");
        List courses = utilService.load(Course.class, "code", courseNo);
        Course course = null;
        if (courses.size() == 1) {
            course = (Course) courses.get(0);
        }
        Float credit = getFloat(request, "credit");
        Long courseTypeId = getLong(request, "courseType.id");
        List grades = utilService.load(CourseGrade.class, "id", SeqStringUtil
                .transformToLong(courseGradeIdSeq));
        for (Iterator iter = grades.iterator(); iter.hasNext();) {
            CourseGrade grade = (CourseGrade) iter.next();
            if (null != course) {
                grade.setCourse(course);
            }
            if (null != credit) {
                grade.setCredit(credit);
            }
            if (null != courseTypeId) {
                grade.setCourseType(new CourseType(courseTypeId));
            }
        }
        utilService.saveOrUpdate(grades);
        logHelper.info(request, "batch update grade");
        return redirect(request, "search", "info.update.success");
    }
    
    /**
     * 修改单个成绩
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
        courseGradeHelper.editGrade(request);
        return forward(request);
    }
    
    /**
     * 保存单个成绩
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
        courseGradeHelper.saveGrade(request, getUser(request.getSession()));
        return redirect(request, "search", "info.save.success");
    }
    
    /**
     * 按<font color="blue">课程序号</font>保存单个学生的课程成绩<br>
     * 1)新增总成绩为发布状态，其他考试成绩为确认状态<br>
     * 2)必须有课程序号对应的任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveAddGrade(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseGrade grade = (CourseGrade) populateEntity(request, CourseGrade.class, "courseGrade");
        
        Map inputGradeMap = new HashMap();
        Map scoreMap = new HashMap();
        String key = grade.getId() + "_" + grade.getScoreDisplay();
        scoreMap.put(grade.getId(), grade.getScore());
        
        // 新成绩
        if (!grade.isPO()) {
            Student std = grade.getStd();
            TeachTask task = (TeachTask) utilService.load(TeachTask.class, grade.getTask().getId());
            
            grade.setCalendar(task.getCalendar());
            grade.setCourse(task.getCourse());
            grade.setCredit(task.getCourse().getCredits());
            grade.setMarkStyle(task.getGradeState().getMarkStyle());
            grade.setStatus(new Integer(Grade.PUBLISHED));
            grade.setCourseType(task.getCourseType());
            
            // 查找修读类别
            EntityQuery query = new EntityQuery(CourseTake.class, "take");
            query.add(new Condition("take.student.id = :stdId", std.getId()));
            query.add(new Condition("take.task.id = :taskId", task.getId()));
            List stds = (List) utilService.search(query);
            CourseTake take = null;
            if (stds.size() == 1) {
                take = (CourseTake) stds.get(0);
                grade.setCourseTakeType(take.getCourseTakeType());
            } else {
                grade.setCourseTakeType(new CourseTakeType(CourseTakeType.COMPULSORY));
            }
        }
        
        List list = (List) utilService.search(buildGradeTypeQuery());
        for (Iterator it = list.iterator(); it.hasNext();) {
            GradeType gradeType = (GradeType) it.next();
            Long gradeTypeId = gradeType.getId();
            Float score = getFloat(request, "gradeType" + gradeTypeId + ""
                    + grade.getMarkStyle().getId());
            ExamGrade examGrade = grade.getExamGrade(gradeType);
            if (null != score) {
                if (null != examGrade) {
                    examGrade.updateScore(score, getUser(request.getSession()));
                } else {
                    examGrade = new ExamGrade(gradeType, new ExamStatus(ExamStatus.NORMAL), score);
                    examGrade.setStatus(new Integer(Grade.CONFIRMED));
                    grade.addExamGrade(examGrade);
                }
            } else if (null != examGrade) {
                grade.getExamGrades().remove(examGrade);
            }
            key += "_" + examGrade.getGradeType().getId() + ":" + examGrade.getScoreDisplay();
        }
        inputGradeMap.put(key, grade);
        
        Student std = (Student) utilService.get(Student.class, grade.getStd().getId());
        grade.calcGA().calcScore().updatePass().calcGP(
                gradePointRuleService.getGradePointRule(std.getType(), grade.getMarkStyle()));
        
        // 成绩日志
        List grades = new ArrayList();
        grades.add(grade);
        // grades.addAll(gradeLogService.buildGradeCatalogInfo(getUser(request), grades,
        // inputGradeMap, scoreMap));
        
        utilService.saveOrUpdate(grades);
        return redirect(request, "addGrade", "info.save.success");
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
        String courseGradeIdSeq = request.getParameter("courseGradeIds");
        if (StringUtils.isEmpty(courseGradeIdSeq)) {
            return forwardError(mapping, request, "error.parameters.needed");
        }
        EntityQuery gradeQuery = new EntityQuery(CourseGrade.class, "grade");
        gradeQuery.add(new Condition("instr('," + courseGradeIdSeq + ",',','||grade.id||',')>0"));
        List courseGrades = (List) utilService.search(gradeQuery);
        
        EntityQuery courseTakeQuery = new EntityQuery(CourseTake.class, "take");
        courseTakeQuery.add(new Condition("instr('," + courseGradeIdSeq
                + ",',','||take.courseGrade.id||',')>0"));
        List courseTakes = (List) utilService.search(courseTakeQuery);
        for (Iterator iter = courseTakes.iterator(); iter.hasNext();) {
            CourseTake take = (CourseTake) iter.next();
            take.setCourseGrade(null);
        }
        utilService.saveOrUpdate(courseTakes);
        utilService.remove(courseGrades);
        // utilService.saveOrUpdate(gradeLogService.buildGradeCatalogInfo(getUser(request),
        // courseGrades, null, null, true));
        return redirect(request, "search", "info.delete.success");
    }
    
    /**
     * 查询学生的某类考试的成绩<br>
     * [0]=gradeId<br>
     * [1]=stdName;<br>
     * [2]=score;<br>
     * [3]=examStatus;<br>
     * [4]=credit;<br>
     * [5]=taskSeqNo<br>
     * [6]=courseTypeName;<br>
     * [7]=courseTakeTypeName;<br>
     * [8]=courseTakeId
     * 
     * @param stdCode
     * @param stdTypeId
     * @param calendarYear
     * @param calendarTerm
     * @param taskSeqNo
     * @param courseCode
     * @param examTypeId
     * @return
     */
    public Object[] getCourseGradeInfo(String stdCode, Long stdTypeId, String calendarYear,
            String calendarTerm, String courseCode, Long gradeTypeId) {
        Object[] gradeInfo = new Object[9];
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(stdTypeId, calendarYear,
                calendarTerm);
        if (null == calendar)
            return gradeInfo;
        
        CourseGrade grade = gradeService.getCourseGrade(stdCode, calendar, courseCode, gradeTypeId);
        gradeInfo[0] = grade.getId();
        if (null != grade.getStd()) {
            gradeInfo[1] = grade.getStd().getName();
            ExamGrade examGrade = grade.getExamGrade(new GradeType(gradeTypeId));
            if (null != examGrade) {
                gradeInfo[2] = examGrade.getScore();
                gradeInfo[3] = examGrade.getExamStatus().getName();
            }
            gradeInfo[4] = grade.getCredit();
            gradeInfo[5] = grade.getTaskSeqNo();
            gradeInfo[6] = grade.getCourseType().getName();
            gradeInfo[7] = grade.getCourseTakeType().getName();
            if (null != grade.getCourseTake())
                gradeInfo[8] = grade.getCourseTake().getId();
        }
        return gradeInfo;
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
        Integer stdCount = getInteger(request, "stdCount");
        if (null == stdCount || stdCount.intValue() < 1) {
            return forwardError(mapping, request, "error.parameters.illegal");
        }
        User who = getUser(request.getSession());
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.studentType.id"), request.getParameter("calendar.year"), request
                .getParameter("calendar.term"));
        String courseCode = request.getParameter("courseCode");
        Long gradeTypeId = getLong(request, "gradeTypeId");
        Long markStyleId = getLong(request, "markStyleId");
        Integer courseGradeStatus = getInteger(request, "courseGradeStatus");
        if (null == courseGradeStatus) {
            courseGradeStatus = new Integer(Grade.CONFIRMED);
        }
        MarkStyle markStyle = (MarkStyle) baseCodeService.getCode(MarkStyle.class, markStyleId);
        GradeType gradeType = (GradeType) baseCodeService.getCode(GradeType.class, gradeTypeId);
        List grades = new ArrayList();
        MajorType majorType = new MajorType(getLong(request, "courseGrade.majorType.id"));
        Map inputGradeMap = new HashMap();
        Map scoreMap = new HashMap();
        for (int i = 0; i < stdCount.intValue(); i++) {
            String stdCode = request.getParameter("stdCode" + i);
            if (StringUtils.isEmpty(stdCode)) {
                continue;
            }
            CourseGrade grade = gradeService.getCourseGrade(stdCode, calendar, courseCode,
                    gradeTypeId);
            // 学生不存在
            if (null == grade.getStd()) {
                continue;
            }
            if (null == grade || null == grade.getId()) {
                inputGradeMap = null;
                scoreMap = null;
            } else {
                scoreMap.put(grade.getId(), grade.getScore());
                String key = grade.getId() + "_" + grade.getScoreDisplay();
                for (Iterator it2 = grade.getExamGrades().iterator(); it2.hasNext();) {
                    ExamGrade exam = (ExamGrade) it2.next();
                    key += "_" + exam.getGradeType().getId() + ":" + exam.getScoreDisplay();
                }
                inputGradeMap.put(key, grade);
            }
            
            // 有些成绩是外校过来的，或者其他路子，就是找不到任务的
            // if (!grade.isPO() && null == grade.getCourseTake())
            // continue;
            
            // 则按照指定的专业类别进行设置(不按照课程进行猜测)
            grade.setMajorType(majorType);
            ExamGrade examGrade = grade.getExamGrade(gradeType);
            
            Float score = getFloat(request, "score" + i);
            if (examGrade.isPO()) {
                examGrade.updateScore(score, who);
            } else {
                examGrade.setScore(score);
            }
            if (!grade.isPO()) {
                grade.setMarkStyle(markStyle);
            }
            examGrade.setStatus(courseGradeStatus);
            grade.calcGA().calcScore().updatePass().calcGP(
                    gradePointRuleService.getGradePointRule(grade.getStd().getType(), grade
                            .getMarkStyle()));
            grades.add(grade);
        }
        
        // 成绩日志
        Collection toSaveObjects = new ArrayList();
        toSaveObjects.addAll(grades);
        // toSaveObjects.addAll(gradeLogService.buildGradeCatalogInfo(getUser(request), grades,
        // inputGradeMap, scoreMap));
        
        utilService.saveOrUpdate(toSaveObjects);
        Boolean addAnother = getBoolean(request, "addAnother");
        
        logHelper.info(request, "Batch insert or update grade");
        if (Boolean.TRUE.equals(addAnother)) {
            return redirect(request, "batchAdd", "info.save.success");
        } else {
            addCollection(request, "grades", grades);
            addSingleParameter(request, "gradeType", gradeType);
            addSingleParameter(request, "calendar", calendar);
            return forward(request, "batchAddResult");
        }
    }
    
    /**
     * 统计
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
        EntityQuery query = buildGradeQuery(request);
        query.add(new Condition("courseGrade.isPass = false"));
        String hql = "not exists(from CourseGrade cg where cg.isPass = true and cg.std.id = courseGrade.std.id and cg.course.id = courseGrade.course.id)";
        query.add(new Condition(hql));
        addCollection(request, "courseGrades", utilService.search(query));
        return forward(request, "list");
    }
    
    public void setGradePointRuleService(GradePointRuleService gradePointRuleService) {
        this.gradePointRuleService = gradePointRuleService;
    }
    
    public void setExamTakeService(ExamTakeService examTakeService) {
        this.examTakeService = examTakeService;
    }
    
    // public void setGradeLogService(GradeLogService gradeLogService) {
    // this.gradeLogService = gradeLogService;
    // }
    
    public void setCourseGradeHelper(CourseGradeHelper courseGradeHelper) {
        this.courseGradeHelper = courseGradeHelper;
    }
    
}
