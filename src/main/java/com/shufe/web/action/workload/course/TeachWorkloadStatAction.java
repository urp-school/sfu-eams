//$Id: TeachWorkloadStatAction.java,v 1.22 2007/01/21 10:47:21 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong         2005-11-28          Created
 * zq                   2007-10-15          增加了search()方法，作明细查询；
 *                                          为了查询需要，更改了doQueryMutiCalendar()
 *                                          方法中获取页面数据的key值；
 ********************************************************************************/

package com.shufe.web.action.workload.course;

import java.util.ArrayList;
import java.util.Collections;
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

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.TeacherType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.workload.Workload;
import com.shufe.model.workload.course.TeachWorkload;
import com.shufe.model.workload.stat.DepartmentRegisterTeacher;
import com.shufe.model.workload.stat.TotalAndRegisterWorkload;
import com.shufe.service.util.stat.StatUtils;
import com.shufe.service.workload.course.TeachWorkloadStatService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public class TeachWorkloadStatAction extends CalendarRestrictionSupportAction {
    
    protected TeachWorkloadStatService teachWorkloadStatService;
    
    /**
     * 各类统计的主界面
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
        return forward(request);
    }
    
    /**
     * 工作量多学期明细查询
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
        Long stdTypeId = getLong(request, "stdTypeId");
        
        String yearStart = request.getParameter("yearStart");
        String termStart = request.getParameter("termStart");
        String yearEnd = request.getParameter("yearEnd");
        String termEnd = request.getParameter("termEnd");
        
        List calendars = teachCalendarService.getTeachCalendars(stdTypeId, yearStart, termStart,
                yearEnd, termEnd);
        
        EntityQuery entityQuery = new EntityQuery(TeachWorkload.class, "teachWorkload");
        populateConditions(request, entityQuery);
        if (null != stdTypeId) {
            List stdTypes = new ArrayList();
            StudentType stdType = (StudentType) utilService.load(StudentType.class, stdTypeId);
            stdTypes.add(stdType);
            stdTypes.addAll(stdType.getDescendants());
            entityQuery.add(new Condition("teachWorkload.studentType in (:stdTypes)", stdTypes));
        }
        DataRealmUtils.addDataRealms(entityQuery, new String[] { "teachWorkload.studentType.id",
                "teachWorkload.college.id" }, getDataRealms(request));
        entityQuery.add(new Condition("teachWorkload.teachCalendar in (:calendars)", calendars));
        Order order = new Order("teachWorkload.teachCalendar.start", Order.DESC);
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        entityQuery.addOrder(order);
        addCollection(request, "teachWorkloads", utilService.search(entityQuery));
        return forward(request);
    }
    
    /**
     * 全校统计为了教师(教师学历学位统计)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doStatisticForTeacher(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String teacherAge = request.getParameter("teacherAge");
        String yearNo = request.getParameter("year");
        String termNo = request.getParameter("term");
        String stdTypeId = request.getParameter("stdTypeId");
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        List stdTypeList = getStdTypes(request);
        if (StringUtils.isEmpty(stdTypeId)) {
            Results.addList("stdTypeList", stdTypeList);
            return forward(request, "teacher/degreeAndTitle/loadConditions");
        }
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(Long.valueOf(stdTypeId),
                yearNo, termNo);
        if (null == calendar) {
            StudentType stdType = (StudentType) EntityUtils.getInstance(StudentType.class);
            if (StringUtils.isNotBlank(stdTypeId)) {
                stdType = (StudentType) utilService.get(StudentType.class, Long.valueOf(stdTypeId));
            } else {
                stdType = (StudentType) stdTypeList.get(0);
            }
            calendar = teachCalendarService.getNearestCalendar(stdType);
        }
        Integer age = new Integer(0);
        if (StringUtils.isNotEmpty(teacherAge)) {
            age = Integer.valueOf(teacherAge);
            Results.addObject("teacherAge", teacherAge);
        }
        String degreeFlag = request.getParameter("degreeFlag");
        if (StringUtils.isEmpty(degreeFlag)) {
            degreeFlag = "1";
        }
        String workloadString = "teacherInfo.degree";
        String teacherString = "degreeInfo.degree";
        if ("2".equals(degreeFlag)) {
            workloadString = "teacherInfo.eduDegree";
            teacherString = "degreeInfo.eduDegreeInside";
        }
        List propertylist = teachWorkloadStatService.getPropertyOfTeachWorkload(workloadString,
                calendar.getId());
        Map workloadMap = teachWorkloadStatService.getEduDegreeAndTitle(departmentIds,
                workloadString, calendar.getId(), age, studentTypeIds);
        Map teacherMap = teachWorkloadStatService.getEduDegreeAndTitleInTeacher(teacherString,
                departmentIds, age);
        request.setAttribute("calendar", calendar);
        request.setAttribute("templist", propertylist);
        request.setAttribute("degreeFlag", degreeFlag);
        request.setAttribute("workloadMap", workloadMap);
        request.setAttribute("teacherMap", teacherMap);
        return forward(request, "teacher/degreeAndTitle/list");
    }
    
    /**
     * 统计教师分布，人数以及工作量(教师机关分布:查询包括人数和工作量)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doTeacherStdTypeDepartAndTitle(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String flag = request.getParameter("flag");
        if (StringUtils.isBlank(flag)) {
            List stdTypeList = getStdTypes(request);
            List teacherTypeList = baseCodeService.getCodes(TeacherType.class);
            Results.addList("stdTypeList", stdTypeList).addList("teacherTypeList", teacherTypeList);
            return forward(request, "teacher/stdTypeAndDepartAndTitle/loadConditions");
        }
        String year = request.getParameter("year");
        String term = request.getParameter("term");
        Long teacherTypeId = getLong(request, "teacherTypeId");
        Long studentTypeId = getLong(request, "stdTypeId");
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(studentTypeId, year,
                term);
        String statisticContextId = request.getParameter("statisticContextId");
        List teacherTitleList = teachWorkloadStatService.getPropertyOfTeachWorkload(
                "teacherInfo.teacherTitle", teachCalendar.getId());
        Results.addObject("teachCalendar", teachCalendar);
        Map dataMap = new HashMap();
        // 第一种情况 求教师的人数
        // 筛选不是很严格 应该把学生类别对影的教学日历有老师的筛选出来。
        if ("1".equals(statisticContextId)) {
            dataMap = teachWorkloadStatService.getTeacherNumberByConditions(
                    getDepartmentIdSeq(request), getStdTypeIdSeqOf(studentTypeId, request),
                    teacherTypeId, teachCalendar);
            // 第二种,求教师所有的工作量
        } else {
            dataMap = teachWorkloadStatService.getTotalWorkloadByConditions(
                    getDepartmentIdSeq(request), getStdTypeIdSeqOf(studentTypeId, request),
                    teacherTypeId, teachCalendar);
        }
        Results.addList("teacherTitleList", teacherTitleList).addObject("statisticContextId",
                statisticContextId).addList("collegeList", getColleges(request));
        request.setAttribute("dataMap", dataMap);
        return forward(request, "teacher/stdTypeAndDepartAndTitle/dataList");
    }
    
    /**
     * 教师 工作量人数比例
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doGetTeacherTitleOfWorkload(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long stdTypeId = getLong(request, "stdTypeId");
        String yearNo = request.getParameter("year");
        String termNo = request.getParameter("term");
        if (null == stdTypeId) {
            Results.addList("stdTypeList", getStdTypes(request));
            return forward(request, "teacher/numberAndWorkloadOfTitle/loadConditions");
        }
        String studentTypeIdSeq = getStdTypeIdSeqOf(stdTypeId, request);
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(stdTypeId, yearNo,
                termNo);
        List teacherTitleAndWorkloadList = teachWorkloadStatService.getNumberAndWorkloadOfTitle(
                teachCalendar, studentTypeIdSeq);
        Results.addList("teacherTitleAndWorkloadList", teacherTitleAndWorkloadList).addObject(
                "teachCalendar", teachCalendar);
        return forward(request, "teacher/numberAndWorkloadOfTitle/dataList");
    }
    
    /**
     * 根据当前学期得到教学日历和学生类别的工作量的对比.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doRateOfDepartment(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long studentTypeId = getLong(request, "stdTypeId");
        String yearNo = request.getParameter("year");
        String termNo = request.getParameter("term");
        if (null == studentTypeId) {
            List stdTypeList = getStdTypes(request);
            Results.addList("stdTypeList", stdTypeList);
            return forward(request, "teacher/registerNumberAndWorkload/loadConditions");
        }
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(studentTypeId, yearNo,
                termNo);
        String stdTypeIdRealmSeq = getStdTypeIdSeqOf(studentTypeId, request);
        String departmentIds = getDepartmentIdSeq(request);
        List departmentList = departmentService.getDepartments(departmentIds);
        // String teacherTypeId = request.getParameter("teacherTypeId");
        Map registerTeacherMap = new HashMap();
        Map workloadTeacherMap = new HashMap();
        Map registerTeacherWorkloadMap = new HashMap();
        Map registerWorkloadMap = new HashMap();
        Map teacherWorkloadMap = new HashMap();
        List tempList = teachWorkloadStatService.getRegisterWorkloadOfDepartment(teachCalendar,
                stdTypeIdRealmSeq, departmentIds);
        for (Iterator iter = tempList.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            TeacherType teacherType = (TeacherType) element[2];
            Department department = (Department) element[3];
            String departIdSeq = department.getId().toString();
            StatUtils.setValueToMap(departIdSeq, element[0], "integer", workloadTeacherMap);
            if (null != teacherType && TeacherType.REGISTERTYPEID.equals(teacherType.getId())) {
                StatUtils.setValueToMap(departIdSeq, element[1], "float", registerWorkloadMap);
            }
            StatUtils.setValueToMap(departIdSeq, element[1], "float", teacherWorkloadMap);
            if (null != teacherType && TeacherType.REGISTERTYPEID.equals(teacherType.getId())) {
                StatUtils.setValueToMap(departIdSeq, element[0], "integer",
                        registerTeacherWorkloadMap);
            }
        }
        teachWorkloadStatService.getRegisterTeacherOfDepartment(departmentIds, registerTeacherMap);
        Results.addObject("teachCalendar", teachCalendar);
        request.setAttribute("registerTeacherMap", registerTeacherMap);
        request.setAttribute("workloadTeacherMap", workloadTeacherMap);
        request.setAttribute("registerTeacherWorkloadMap", registerTeacherWorkloadMap);
        request.setAttribute("registerWorkloadMap", registerWorkloadMap);
        request.setAttribute("teacherWorkloadMap", teacherWorkloadMap);
        request.setAttribute("departmentList", departmentList);
        return forward(request, "teacher/registerNumberAndWorkload/dataList");
    }
    
    /**
     * 部门课程类别合计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doGetCourseTypeNumber(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long studentTypeId = getLong(request, "stdTypeId");
        String yearNo = request.getParameter("year");
        String termNo = request.getParameter("term");
        if (studentTypeId == null) {
            List stdTypeList = getStdTypes(request);
            Results.addList("stdTypeList", stdTypeList);
            return forward(request, "teacher/courseTypeOfDepart/loadConditions");
        }
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(studentTypeId, yearNo,
                termNo);
        if (null != teachCalendar) {
            Results.addObject("teachCalendar", teachCalendar);
        }
        String stdTypeIdSeq = getStdTypeIdSeqOf(studentTypeId, request);
        String departIdSeq = getDepartmentIdSeq(request);
        List departmentList = departmentService.getColleges(departIdSeq);
        List courseTypeList = teachWorkloadStatService.getPropertyOfTeachWorkload("courseType",
                teachCalendar.getId());
        Map courseTypeCollegeMap = teachWorkloadStatService.getCourseTypesOfDepartment(
                teachCalendar, stdTypeIdSeq, departIdSeq);
        request.setAttribute("departmentList", departmentList);
        request.setAttribute("courseTypeList", courseTypeList);
        request.setAttribute("courseTypeCollegeMap", courseTypeCollegeMap);
        return forward(request, "teacher/courseTypeOfDepart/dataList");
    }
    
    /**
     * 部门教职工类别统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doGetRateOfTitleAndStdType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long studentTypeId = getLong(request, "stdTypeId");
        String yearNo = request.getParameter("year");
        String termNo = request.getParameter("term");
        String departmentIds = getDepartmentIdSeq(request);
        if (null == studentTypeId) {
            List stdTypeList = getStdTypes(request);
            Results.addList("stdTypeList", stdTypeList);
            return forward(request, "teacher/titleAndStdType/loadConditions");
        }
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(studentTypeId, yearNo,
                termNo);
        if (null != teachCalendar) {
            Results.addObject("teachCalendar", teachCalendar);
        }
        String studentTypeIds = getStdTypeIdSeqOf(studentTypeId, request);
        List teacherTitleList = teachWorkloadStatService.getPropertyOfTeachWorkload(
                "teacherInfo.teacherTitle", teachCalendar.getId());
        Map stdTypeAndTitleMap = teachWorkloadStatService.getTitleAndStdTypesNo(teachCalendar,
                departmentIds, studentTypeIds);
        request.setAttribute("stdTypeList", studentTypeService.getStudentTypes(studentTypeIds));
        request.setAttribute("teacherTitleList", teacherTitleList);
        request.setAttribute("stdTypeAndTitleMap", stdTypeAndTitleMap);
        return forward(request, "teacher/titleAndStdType/dataList");
    }
    
    /**
     * 得到单位群体考核数据表.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doGetWorkloadByTeacherTypeAndDepartmentId(ActionMapping mapping,
            ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        Long studentTypeId = getLong(request, "stdTypeId");
        if (null == studentTypeId) {
            setDataRealm(request, hasStdType);
            return forward(request, "teacher/teacherTypeAndDepart/loadConditions");
        }
        String yearNo = request.getParameter("year");
        String termNo = request.getParameter("term");
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(studentTypeId, yearNo,
                termNo);
        request.setAttribute("teachCalendar", teachCalendar);
        String departmentIds = getDepartmentIdSeq(request);
        String isCaculate = request.getParameter("isCaculate");
        Boolean iscalc = null;
        if (StringUtils.isNotBlank(isCaculate)) {
            iscalc = Boolean.valueOf(isCaculate);
        }
        Map teacherTypeAndDeparMap = teachWorkloadStatService.getWorkloadByDepartAndTeacherType(
                teachCalendar, getStdTypeIdSeqOf(studentTypeId, request), departmentIds, iscalc);
        request.setAttribute("departmentList", departmentService.getDepartments(departmentIds));
        request.setAttribute("teacherTypeList", teachWorkloadStatService
                .getPropertyOfTeachWorkload("teacherInfo.teacherType", teachCalendar.getId()));
        request.setAttribute("teacherTypeAndDeparMap", teacherTypeAndDeparMap);
        return forward(request, "teacher/teacherTypeAndDepart/dataList");
    }
    
    /**
     * 根据部门类别和学生类别
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doGetTeacherAcgWorkload(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long stdTypeId = getLong(request, "stdTypeId");
        String yearNo = request.getParameter("year");
        String termNo = request.getParameter("term");
        String departIds = getDepartmentIdSeq(request);
        List collegeList = departmentService.getDepartments(departIds);
        if (null == stdTypeId) {
            Results.addList("stdTypeList", getStdTypes(request));
            return forward(request, "teacher/departmentAvaCourseTime/loadConditions");
        }
        String studentTypeIds = getStdTypeIdSeqOf(stdTypeId, request);
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(stdTypeId, yearNo,
                termNo);
        if (null != teachCalendar) {
            request.setAttribute("teachCalendar", teachCalendar);
        }
        Map registerNoMap = new HashMap();
        Map registerCourseNoMap = new HashMap();
        Map teacherNoMap = new HashMap();
        Map teacherCourseNoMap = new HashMap();
        List teacherAvgWorkloadList = teachWorkloadStatService.getAvgCourseNo(departIds,
                studentTypeIds, teachCalendar);
        for (Iterator iter = teacherAvgWorkloadList.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            Department college = (Department) element[2];
            TeacherType teacherType = (TeacherType) element[3];
            String departKey = college.getId().toString();
            if (null != teacherType && TeacherType.REGISTERTYPEID.equals(teacherType.getId())) {
                StatUtils.setValueToMap(departKey, element[0], "integer", registerNoMap);
                StatUtils.setValueToMap(departKey, element[1], "float", registerCourseNoMap);
            }
            StatUtils.setValueToMap(departKey, element[0], "integer", teacherNoMap);
            StatUtils.setValueToMap(departKey, element[1], "float", teacherCourseNoMap);
        }
        request.setAttribute("collegeList", collegeList);
        request.setAttribute("registerNoMap", registerNoMap);
        request.setAttribute("registerCourseNoMap", registerCourseNoMap);
        request.setAttribute("teacherNoMap", teacherNoMap);
        request.setAttribute("teacherCourseNoMap", teacherCourseNoMap);
        return forward(request, "teacher/departmentAvaCourseTime/dataList");
    }
    
    /**
     * 统计用户所负责的所有的部门的信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doStatisticTeachAllDeparts(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String departIdSeq = getDepartmentIdSeq(request);
        String stdTypeIdSeq = getStdTypeIdSeq(request);// 这个在权限中得到学生类别是基础学生类别
        String year = request.getParameter("year");
        String term = request.getParameter("term");
        Long stdTypeId = getLong(request, "stdTypeId");
        if (null == stdTypeId) {
            Results.addList("stdTypeList", getStdTypes(request));
            return forward(request, "teacher/departmentStdTypeAll/loadConditions");
        }
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(stdTypeId, year, term);
        if (null != calendar) {
            request.setAttribute("calendar", calendar);
        }
        request.setAttribute("departmentList", departmentService.getDepartments(departIdSeq));
        request.setAttribute("stdTypeList", studentTypeService.getStudentTypes(getStdTypeIdSeqOf(
                stdTypeId, request)));
        Map deaprtAndStdTypeWorkloadMap = teachWorkloadStatService.getWorkloadByDataRealm(
                departIdSeq, stdTypeIdSeq, calendar);
        request.setAttribute("deaprtAndStdTypeWorkloadMap", deaprtAndStdTypeWorkloadMap);
        return forward(request, "teacher/departmentStdTypeAll/dataList");
    }
    
    /**
     * 过滤部门和学生类别 得到相应的一个对像
     * 
     * @param list
     * @param departmentId
     * @param depatId
     * @param studentTypeId
     * @param stdTypeId
     * @return
     */
    public Object getObjectFromList(List list, Long departmentId, int depatId, Long studentTypeId,
            int stdTypeId) {
        int count = -1;
        for (int i = 0; i < list.size(); i++) {
            Object[] objectArray = (Object[]) list.get(i);
            boolean temp = false;
            if (depatId == 0 && stdTypeId > 0) {
                StudentType stdType = (StudentType) objectArray[stdTypeId];
                temp = studentTypeId.equals(stdType.getId());
            } else if (stdTypeId == 0 && depatId > 0) {
                Department deprtment = (Department) objectArray[depatId];
                temp = departmentId.equals(deprtment.getId());
            } else if (depatId > 0 && stdTypeId > 0) {
                Department deprtment = (Department) objectArray[depatId];
                StudentType stdType = (StudentType) objectArray[stdTypeId];
                temp = departmentId.equals(deprtment.getId())
                        && studentTypeId.equals(stdType.getId());
            } else {
                break;
            }
            if (temp) {
                count = i;
                break;
            }
        }
        return count == -1 ? null : list.get(count);
    }
    
    /**
     * 得到部门教师的比例.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doTotalAndRegisterWorkload(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String departmentIds = getDepartmentIdSeq(request);
        List departmentList = departmentService.getColleges(departmentIds);
        String studentTypeIds = getStdTypeIdSeq(request);// 这个在权限中得到学生类别是基础学生类别
        List studentTypeList = studentTypeService.getStudentTypes(studentTypeIds);
        String year = request.getParameter("year");
        String term = request.getParameter("term");
        String stdTypeId = request.getParameter("stdTypeId");
        if (StringUtils.isEmpty(stdTypeId)) {
            Results.addList("stdTypeList", studentTypeList);
            return forward(mapping, request, "loadTotalAndRegisterWorkloadPage");
        }
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(Long.valueOf(stdTypeId),
                year, term);
        List totleAndregisterList = teachWorkloadStatService.getTotalAndRegisterWorkload(
                departmentList, calendar.getId());
        List totalAndRegisterWorkloadList = new ArrayList();
        for (int i = 0; i < departmentList.size(); i++) {
            Department department = (Department) departmentList.get(i);
            TotalAndRegisterWorkload totalAndRegisterWorkload = new TotalAndRegisterWorkload();
            Object[] objectTemp = (Object[]) getObjectFromList(totleAndregisterList, department
                    .getId(), 4, new Long(0), 0);
            if (objectTemp != null) {
                totalAndRegisterWorkload.setTotalWorkload((Float) objectTemp[0]);
                totalAndRegisterWorkload.setTotalPeople((Integer) objectTemp[1]);
                totalAndRegisterWorkload.setTotalRegisterWorkload((Float) objectTemp[2]);
                totalAndRegisterWorkload.setTotalRegisterPeople((Integer) objectTemp[3]);
            }
            totalAndRegisterWorkload.setDepartment(department);
            totalAndRegisterWorkloadList.add(totalAndRegisterWorkload);
        }
        Results.addList("registerList", totalAndRegisterWorkloadList);
        return forward(mapping, request, "doTotalAndRegisterWorkload");
    }
    
    /**
     * 导出统计出来的信息（教师职称对应相应的工作量）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public void doExpTeacherTitleOfWorkload(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long studentTypeId = getLong(request, "stdTypeId");
        String yearNo = request.getParameter("year");
        String termNo = request.getParameter("term");
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(studentTypeId, yearNo,
                termNo);
        String studentTypeIds = getStdTypeIdSeq(request);
        List teacherTitleAndWorkloadList = teachWorkloadStatService.getNumberAndWorkloadOfTitle(
                teachCalendar, studentTypeIds);
        outputExcelFile(request, response, teacherTitleAndWorkloadList, "teacherTitleAndWorkload");
    }
    
    /**
     * 根据教学日历得到各类职称教师对应学生类别的教学情况.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doGetTeacherTitleOfStudentType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long studentTypeId = getLong(request, "stdTypeId");
        String yearNo = request.getParameter("year");
        String termNo = request.getParameter("term");
        List stdTypeList = getStdTypes(request);
        if (studentTypeId == null) {
            Results.addList("stdTypeList", stdTypeList);
            return forward(mapping, request, "searchPage");
        }
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(studentTypeId, yearNo,
                termNo);
        StudentType studentType = (StudentType) utilService.load(StudentType.class, studentTypeId);
        Results.addObject("teachCalendar", teachCalendar).addObject("studentType", studentType);
        return forward(mapping, request, "doGetTeacherTitleOfStudentType");
    }
    
    /**
     * 得到各部门的教师授课情况
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doGetDepartRegisterNumber(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String studentTypeId = request.getParameter("stdTypeId");
        String yearNo = request.getParameter("year");
        String termNo = request.getParameter("term");
        String studentTypeIds = getStdTypeIdSeq(request);
        if (StringUtils.isEmpty(studentTypeId)) {
            List stdTypeList = getStdTypes(request);
            Results.addList("stdTypeList", stdTypeList);
            return forward(mapping, request, "registerPeopleSearchPage");
        }
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(Long
                .valueOf(studentTypeId), yearNo, termNo);
        String departmentIds = getDepartmentIdSeq(request);
        List departmentList = departmentService.getDepartments(departmentIds);
        List list = new ArrayList();
        List tempList = teachWorkloadStatService.getPeopleOfDepartment(teachCalendar,
                studentTypeIds, departmentIds);
        List teacherList = Collections.EMPTY_LIST; // TODO
        for (int i = 0; i < departmentList.size(); i++) {
            Department departement = (Department) departmentList.get(i);
            DepartmentRegisterTeacher drt = new DepartmentRegisterTeacher();
            drt.setDepartment(departement);
            transValue(tempList, teacherList, drt);
            list.add(drt);
        }
        Results.addList("departmentNumberList", list).addObject("teachCalendar", teachCalendar);
        return forward(mapping, request, "doGetDepartRegisterNumber");
    }
    
    /**
     * 统计部门教学工作量
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doStatForDepart(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        EntityQuery yearQuery = new EntityQuery(TeachCalendar.class, "calendar");
        yearQuery.setSelect("distinct calendar.year");
        request.setAttribute("yearList", utilService.search(yearQuery));
        
        EntityQuery termQuery = new EntityQuery(TeachCalendar.class, "calendar");
        termQuery.setSelect("distinct calendar.term");
        request.setAttribute("termList", utilService.search(termQuery));
        
        request.setAttribute("departmentList", departmentService.getDepartments());
        return forward(request);
    }
    
    /**
     * 得到部门对应的工作量
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward departList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        EntityQuery query = new EntityQuery(Workload.class, "departWorkload");
        query.add(new Condition("departWorkload.teacherInfo.teachDepart.id=:id", Long
                .valueOf(request.getParameter("department"))));
        query.add(new Condition("departWorkload.teachCalendar.year=:year", request
                .getParameter("year")));
        query.add(new Condition("departWorkload.teachCalendar.term=:term", request
                .getParameter("term")));
        query
                .setSelect("departWorkload.calcWorkload,departWorkload.payReward,sum(departWorkload.totleWorkload)");
        query.groupBy("departWorkload.calcWorkload,departWorkload.payReward");
        request.setAttribute("workloadDepartStats", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 传固定的值到一个对象里面.
     * 
     * @param tempList
     * @param teacherList
     * @param depart
     */
    public void transValue(List tempList, List teacherList, DepartmentRegisterTeacher depart) {
        for (int i = 0; i < tempList.size(); i++) {
            Object[] element = (Object[]) tempList.get(i);
            Department department = (Department) element[0];
            if (department.getId().equals(depart.getDepartment().getId())) {
                depart.setActualTeachTeacher((Integer) element[1]);
                depart.setRegisterTeacherNumber((Integer) element[2]);
                tempList.remove(i);
                break;
            }
        }
        for (int i = 0; i < teacherList.size(); i++) {
            Object[] element = (Object[]) teacherList.get(i);
            Department department = (Department) element[1];
            if (department.getId().equals(depart.getDepartment().getId())) {
                depart.setAllTeacherOfDepartment((Integer) element[0]);
                teacherList.remove(i);
                break;
            }
        }
    }
    
    // public static void setALLCHECKDATA(String allcheckdata) {
    // ALLCHECKDATA = allcheckdata;
    // }
    
    public void setTeachWorkloadStatService(TeachWorkloadStatService teachWorkloadStatService) {
        this.teachWorkloadStatService = teachWorkloadStatService;
    }
}
