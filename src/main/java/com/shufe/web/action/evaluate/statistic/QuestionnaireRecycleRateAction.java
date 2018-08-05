//$Id: QuestionnaireRecycleRateAction.java,v 1.14 2007/01/18 01:48:43 cwx Exp $
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
 * chenweixiong              2005-10-25         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.evaluate.statistic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.evaluate.QuestionnaireRecycleRate;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.evaluate.QuestionnaireRecycleRateService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 查询回收率
 * 
 * @author hj 2005-10-25 QuestionnaireRecycleRateAction.java has been created
 */
public class QuestionnaireRecycleRateAction extends CalendarRestrictionSupportAction {
    
    private QuestionnaireRecycleRateService questionnaireRecycleRateService;
    
    // method
    /**
     * 得到统计页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doLoadStatisticPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeTeachDepart);
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        if (null == stdTypeId) {
            StudentType studentType = (StudentType) getStdTypes(request).get(0);
            stdTypeId = studentType.getId();
            request.setAttribute("studentType", studentType);
        }
        request.setAttribute("stdTypes", getStdTypesOf(stdTypeId,
                "evaluate.statistic.questionnaireRecycleRate", request));
        return forward(mapping, request, "doLoadStatisticPage");
    }
    
    /**
     * 统计评教回收率
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doStatisticInCourseTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String departmentIds = request.getParameter("departTempIdSeq");
        String studentTypeIds = request.getParameter("stdTypeTempIdSeq");
        String teachCalendarIds = request.getParameter("teachCalendarTempIdSeq");
        Collection teachCalendars = utilService.load(TeachCalendar.class, "id", SeqStringUtil
                .transformToLong(teachCalendarIds));
        List statisticRecycleRateList = questionnaireRecycleRateService.getAllRateByCourseTask(
                departmentIds, studentTypeIds, teachCalendars);
        List recycleObjectList = questionnaireRecycleRateService.getRecycleRateObject(
                departmentIds, studentTypeIds, teachCalendars);
        utilService.remove(recycleObjectList);
        
        List recycleRateList = new ArrayList();
        for (Iterator iter = statisticRecycleRateList.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            Department department = (Department) element[6];
            StudentType stdType = (StudentType) element[7];
            Integer amount = (Integer) element[1];
            Integer hasEvalAmount = (Integer) element[0];
            Float amountRate = (Float) element[2];
            Integer totleStudent = (Integer) element[4];
            Integer evaluateStudent = (Integer) element[3];
            Float totleRate = (Float) element[5];
            TeachCalendar teachCalendar = (TeachCalendar) element[8];
            QuestionnaireRecycleRate recycleObject = new QuestionnaireRecycleRate();
            recycleObject = new QuestionnaireRecycleRate();
            recycleObject.setDepartment(department);
            recycleObject.setTeachCalendar(teachCalendar);
            recycleObject.setStudentType(stdType);
            recycleObject.setTotleEvaluateAmount(new Long(amount.longValue()));
            recycleObject.setEvaluateAmount(new Long(hasEvalAmount.longValue()));
            recycleObject.setAmountRate(amountRate);
            recycleObject.setStatisticTime(Calendar.getInstance().getTime());
            recycleObject.setEvaluatePerson(new Long(evaluateStudent.longValue()));
            recycleObject.setTotlePerson(new Long(totleStudent.longValue()));
            recycleObject.setTotleRate(totleRate);
            recycleRateList.add(recycleObject);
        }
        utilService.saveOrUpdate(recycleRateList);
        List tempList = new ArrayList();
        tempList.addAll(teachCalendars);
        Collections.sort(tempList, new Comparator() {
            
            public int compare(Object arg0, Object arg1) {
                TeachCalendar teachCalendar0 = (TeachCalendar) arg0;
                TeachCalendar teachCalendar1 = (TeachCalendar) arg1;
                if (teachCalendar0.getStart().before(teachCalendar1.getStart())) {
                    return 1;
                }
                return -1;
            }
        });
        String parameters = "&teachCalendarId=" + ((TeachCalendar) tempList.get(0)).getId();
        return redirect(request, "doQuery", "", parameters);
    }
    
    /**
     * 根据统计好的结果查询对应的统计结果.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String query = request.getParameter("query");
        String departmentIds = getDepartmentIdSeq(request);
        if (StringUtils.isNotBlank(query) && "query".equals(query)) {
            String requireDeprtIdSeq = request.getParameter("departmentId");
            if (StringUtils.isBlank(requireDeprtIdSeq)) {
                requireDeprtIdSeq = departmentIds;
            }
            Long stdTypeId = getLong(request, "stdTypeId");
            String year = request.getParameter("year");
            String term = request.getParameter("term");
            String teachCalendarId = request.getParameter("teachCalendarId");
            TeachCalendar teachCalendar = null;
            if (StringUtils.isNotBlank(teachCalendarId)) {
                teachCalendar = (TeachCalendar) utilService.load(TeachCalendar.class, Long
                        .valueOf(teachCalendarId));
            } else {
                teachCalendar = teachCalendarService.getTeachCalendar(stdTypeId, year, term);
                if (null == teachCalendar) {
                    teachCalendar = teachCalendarService
                            .getNearestCalendar((StudentType) EntityUtils.getEntity(
                                    StudentType.class, stdTypeId));
                }
            }
            String stdTypeIdSeq = getStdTypeIdSeqOf(stdTypeId, request);
            List teachCalendars = new ArrayList();
            teachCalendars.add(teachCalendar);
            List recycleRateList = questionnaireRecycleRateService.getRecycleRateObject(
                    requireDeprtIdSeq, stdTypeIdSeq, teachCalendars);
            Results.addList("recycleRateList", recycleRateList);
            return forward(request, "recycleRateList");
        } else {
            String teachCalendarId = request.getParameter("teachCalendarId");
            TeachCalendar calendar = null;
            if (StringUtils.isNotBlank(teachCalendarId)) {
                calendar = (TeachCalendar) utilService.load(TeachCalendar.class, Long
                        .valueOf(teachCalendarId));
            } else {
                String stdTypeIdSeq = getStdTypeIdSeq(request);
                List stdTypeList = studentTypeService.getStudentTypes(stdTypeIdSeq);
                calendar = teachCalendarService
                        .getNearestCalendar((StudentType) stdTypeList.get(0));
            }
            request.setAttribute("teachCalendar", calendar);
            setDataRealm(request, hasStdTypeTeachDepart);
            return forward(request, "recycleRateQuery");
        }
    }
    
    /**
     * 根据提议得到具体的教学任务所对应的回收率.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doGetDetailInfoOfTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        String departmentId = request.getParameter("departmentId");
        String studentOrTeacher = request.getParameter("studentOrTeacher");
        List detailTaskInfo = questionnaireRecycleRateService.getDetailInfoOfTask(departmentIds,
                departmentId, studentTypeIds, Collections.singleton(teachCalendarService
                        .getTeachCalendar(getLong(request, "stdTypeId"), get(request, "year"), get(
                                request, "term"))), studentOrTeacher);
        Results.addList("detailTaskInfoList", detailTaskInfo);
        return forward(mapping, request, "doGetDetailInfoOfTask");
    }
    
    /**
     * @param questionnaireRecycleRateService
     *            The questionnaireRecycleRateService to set.
     */
    public void setQuestionnaireRecycleRateService(
            QuestionnaireRecycleRateService questionnaireRecycleRateService) {
        this.questionnaireRecycleRateService = questionnaireRecycleRateService;
    }
    
    /**
     * 根据部门和学生类别id 得到一个计算好的对象 包括实评人次，总评人次，比例，实评人数，总评人数，实评比例，部门，学生类别.
     * 
     * @param list
     * @param departmentId
     * @param studentTypeId
     * @return
     */
    public Object[] getHaveValueObject(List list, Long departmentId, Long studentTypeId) {
        Object[] object = null;
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            Department depart = (Department) element[6];
            StudentType stdType = (StudentType) element[7];
            if (depart.getId().equals(departmentId) && stdType.getId().equals(studentTypeId)) {
                object = element;
                list.remove(element);
                break;
            }
        }
        return object;
        
    }
}
