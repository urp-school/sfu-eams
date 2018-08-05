//$Id: ThesisStatisticAction.java,v 1.3 2006/11/29 01:31:31 duanth Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong         2006-11-28          Created
 * zq                   2007-09-17          见 com.shufe.dao.degree.thesis.thesisStatistic.hibernate.ThesisStatisticDAOHibernate
 *                                          的 FIXME 处
 ********************************************************************************/

package com.shufe.web.action.degree.thesis;

import java.util.ArrayList;
import java.util.Calendar;
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

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.annotate.ThesisAnnotateBook;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.degree.thesis.annotate.AnnotateService;
import com.shufe.service.degree.thesis.thesisStatistic.ThesisStatisticService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.service.util.stat.FloatSegment;
import com.shufe.util.DataRealmUtils;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.RestrictionSupportAction;

public class ThesisStatisticAction extends RestrictionSupportAction {
    
    private ThesisStatisticService thesisStatisticService;
    
    private AnnotateService annotateService;
    
    private TeachCalendarService teachCalendarService;
    
    /**
     * 关于开题报告的统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward topicOpenStatistic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String flag = request.getParameter("flag");// 该标签确认是跳转到查询条件页面还是信息列表页面
        
        // 如果不是条件 跳转到条件 不然就跳转到列表
        if ("conditions".equals(flag)) {
            setDataRealm(request, hasStdTypeCollege);
            return forward(request, "topicOpenConditions");
        } else if ("list".equals(flag)) {
            String departmentIdSeq = getDepartmentIdSeq(request);
            
            String stdTypeIdSeq = getStdTypeIdSeq(request);
            
            Student student = (Student) RequestUtil.populate(request, Student.class, "student");
            ThesisManage thesisManage = new ThesisManage();
            thesisManage.setStudent(student);
            if (null != student.getDepartment().getId()
                    && !new Long(0).equals(student.getDepartment().getId())) {
                departmentIdSeq = String.valueOf(student.getDepartment().getId());
            }
            List stdDeparts = thesisStatisticService.getstdGroupInfo(student, departmentIdSeq,
                    stdTypeIdSeq);
            List thesisManages = thesisStatisticService.getTOpicOpenGroupInfo(thesisManage,
                    departmentIdSeq, stdTypeIdSeq);
            Map thesisMap = new HashMap();
            for (Iterator iter = thesisManages.iterator(); iter.hasNext();) {
                Object[] element = (Object[]) iter.next();
                thesisMap.put(element[1] + "," + element[2], element[0]);
            }
            // 查询院系确认的数据
            thesisManage.getTopicOpen().setDepartmentAffirm(Boolean.TRUE);
            List collegeAffirms = thesisStatisticService.getTOpicOpenGroupInfo(thesisManage,
                    departmentIdSeq, stdTypeIdSeq);
            Map collegeAffirmsMap = new HashMap();
            for (Iterator iter = collegeAffirms.iterator(); iter.hasNext();) {
                Object[] element = (Object[]) iter.next();
                collegeAffirmsMap.put(element[1] + "," + element[2], element[0]);
            }
            // 查询导师确认的数据
            thesisManage.getTopicOpen().setDepartmentAffirm(null);
            thesisManage.getTopicOpen().setTutorAffirm(Boolean.TRUE);
            List tutorAffirms = thesisStatisticService.getTOpicOpenGroupInfo(thesisManage,
                    departmentIdSeq, stdTypeIdSeq);
            Map tutorAffirmsMap = new HashMap();
            for (Iterator iter = tutorAffirms.iterator(); iter.hasNext();) {
                Object[] element = (Object[]) iter.next();
                tutorAffirmsMap.put(element[1] + "," + element[2], element[0]);
            }
            request.setAttribute("stdDeparts", stdDeparts);
            request.setAttribute("thesisMap", thesisMap);
            request.setAttribute("collegeAffirmMap", collegeAffirmsMap);
            request.setAttribute("tutorAffirmMap", tutorAffirmsMap);
            return forward(request, "topicOpenList");
        }
        return forward(request);
    }
    
    /**
     * 论文评阅的统计(学生双盲统计)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward annotateStatistic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String departmentIdSeq = getDepartmentIdSeq(request);
        String stdTypeIdSeq = getStdTypeIdSeq(request);
        Long stdTypeId = getLong(request, "stdTypeId");
        if (StringUtils.isNotBlank(stdTypeIdSeq)) {
            setDataRealm(request, hasStdType);
        }
        if (null == stdTypeId) {
            stdTypeId = StudentType.DOCTORSTDTYPEID;// 博士学生
        }
        StudentType stdType = (StudentType) utilService.get(StudentType.class, stdTypeId);
        if (null == stdType) {
            List stdTypes = getStdTypes(request);
            stdType = (StudentType) stdTypes.get(0);
        }
        
        String year = request.getParameter("year");
        String term = request.getParameter("term");
        TeachCalendar teachcalendar = teachCalendarService.getTeachCalendar(stdType.getId(), year,
                term);
        if (null == teachcalendar) {
            teachcalendar = teachCalendarService.getNearestCalendar(stdType);
        }
        request.setAttribute("teachcalendar", teachcalendar);
        ThesisAnnotateBook thesisAnnotateBook = new ThesisAnnotateBook();
        Student std = new Student();
        std.setType(stdType);
        ThesisManage thesismanage = new ThesisManage();
        thesismanage.setTeachCalendar(teachcalendar);
        thesismanage.setStudent(std);
        thesisAnnotateBook.setThesisManage(thesismanage);
        // 从页面得到评分标准
        Map segMentMap = getSgeMentsFromForm(request);
        request.setAttribute("segMentMap", segMentMap);
        // 等级
        Map levelMap = annotateService.getStdNumOfLevel(thesisAnnotateBook, departmentIdSeq,
                stdTypeIdSeq);
        request.setAttribute("levelMap", levelMap);
        
        // 平均分
        List avgMarkList = annotateService.getStdNumOfAvgMark(thesisAnnotateBook, departmentIdSeq,
                stdTypeIdSeq);
        Map avgMarkMap = getSegMentMap(avgMarkList, segMentMap);
        request.setAttribute("avgMarkMap", avgMarkMap);
        Set keySet = segMentMap.keySet();
        // 评分标准初始化
        for (Iterator iter = keySet.iterator(); iter.hasNext();) {
            String element = (String) iter.next();
            FloatSegment scoreSeg = (FloatSegment) segMentMap.get(element);
            scoreSeg.setCount(0);
        }
        List markList = annotateService.getStdNumOfMark(thesisAnnotateBook, departmentIdSeq,
                stdTypeIdSeq);
        Map markMap = getSegMentMap(markList, segMentMap);
        request.setAttribute("markMap", markMap);
        // 修改的书list
        thesisAnnotateBook.setIsReply("M");
        List modifyAnnotateBooks = annotateService.getAnnotateBooks(thesisAnnotateBook,
                departmentIdSeq, stdTypeIdSeq, "");
        request.setAttribute("modify", modifyAnnotateBooks);
        // 删除的书list
        thesisAnnotateBook.setIsReply("D");
        List noPassedBooks = annotateService.getAnnotateBooks(thesisAnnotateBook, departmentIdSeq,
                stdTypeIdSeq, "");
        request.setAttribute("noPassed", noPassedBooks);
        List standards = new ArrayList();
        standards.add("A");
        standards.add("B");
        standards.add("C");
        standards.add("D");
        request.setAttribute("standards", standards);
        return forward(request, "loadAnnotateBooksStatistic");
    }
    
    /**
     * 得到分段标准类
     * 
     * @param request
     * @return
     */
    public Map getSgeMentsFromForm(HttpServletRequest request) {
        String[] level = new String[] { "A", "B", "C", "D" };
        Map returnMap = new HashMap();
        returnMap.put("A",
                new FloatSegment(new Float(90).floatValue(), new Float(100).floatValue()));
        returnMap
                .put("B", new FloatSegment(new Float(75).floatValue(), new Float(89).floatValue()));
        returnMap
                .put("C", new FloatSegment(new Float(61).floatValue(), new Float(74).floatValue()));
        returnMap.put("D", new FloatSegment(new Float(0).floatValue(), new Float(60).floatValue()));
        for (int i = 0; i < level.length; i++) {
            String minValue = request.getParameter(level[i] + "Min");
            String maxValue = request.getParameter(level[i] + "Max");
            if (StringUtils.isNotBlank(minValue) || StringUtils.isNotBlank(maxValue)) {
                FloatSegment scoreSegment = new FloatSegment(Float.valueOf(minValue).floatValue(),
                        Float.valueOf(maxValue).floatValue());
                returnMap.put(level[i], scoreSegment);
            }
        }
        return returnMap;
    }
    
    /**
     * 根据
     * 
     * @param markList
     * @param segMentList
     * @return
     */
    public Map getSegMentMap(List markList, Map segMentMap) {
        Map returnMap = new HashMap();
        for (Iterator iter = markList.iterator(); iter.hasNext();) {
            Float score = (Float) iter.next();
            if (null == score) {
                score = new Float(0);
            }
            Set keySet = segMentMap.keySet();
            for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                FloatSegment scoreSeg = (FloatSegment) segMentMap.get(key);
                if (scoreSeg.add(score)) {
                    break;
                }
            }
        }
        Set keySet = segMentMap.keySet();
        for (Iterator iter = keySet.iterator(); iter.hasNext();) {
            String element = (String) iter.next();
            returnMap
                    .put(element, new Integer(((FloatSegment) segMentMap.get(element)).getCount()));
        }
        return returnMap;
    }
    
    /**
     * @see com.shufe.web.action.common.DispatchBasicAction#execute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward scheduleStatistic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String statistic = request.getParameter("statistic");
        
        if (StringUtils.isBlank(statistic) || !"statistic".equals(statistic)) {
            setDataRealm(request, hasStdTypeDepart);
            return forward(request, "schedule/index");
        }
        
        EntityQuery thesisManageQuery = buildQuery(request, null, null, null);
        thesisManageQuery.addOrder(OrderUtils
                .parser("thesisManage.student.department.id,thesisManage.student.type.id"));
        List thesisManages = (List) utilService.search(thesisManageQuery);
        for (Iterator it = thesisManages.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            obj[0] = utilService.load(Department.class, new Long(obj[0].toString()));
            obj[1] = utilService.load(StudentType.class, new Long(obj[1].toString()));
        }
        
        // TopicOpen
        EntityQuery queryOpen = buildQuery(request, "topicOpen");
        List thesisOpens = (List) utilService.search(queryOpen);
        Map thesisOpenMap = new HashMap();
        for (Iterator it = thesisOpens.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            thesisOpenMap.put(obj[0] + "-" + obj[1], new Object[] { obj[2], obj[3] });
        }
        
        // TopicWrite
        EntityQuery queryWrite = buildQuery(request, "thesis");
        List thesisWrites = (List) utilService.search(queryWrite);
        Map thesisWriteMap = new HashMap();
        for (Iterator it = thesisWrites.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            thesisWriteMap.put(obj[0] + "-" + obj[1], new Object[] { obj[2], obj[3] });
        }
        
        // TopicAnnotate
        EntityQuery queryAnnotate = buildQuery(request, "annotate");
        List thesisAnnotates = (List) utilService.search(queryAnnotate);
        Map thesisAnnotateMap = new HashMap();
        for (Iterator it = thesisAnnotates.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            thesisAnnotateMap.put(obj[0] + "-" + obj[1], new Object[] { obj[2], obj[3] });
        }
        
        // TopicPreAnswer
        EntityQuery queryPreAnswer = buildQuery(request, "preAnswerSet", "preAnswer", null);
        List thesisPreAnswers = (List) utilService.search(queryPreAnswer);
        Map thesisPreAnswerMap = new HashMap();
        for (Iterator it = thesisPreAnswers.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            thesisPreAnswerMap.put(obj[0] + "-" + obj[1], new Object[] { obj[2], obj[3] });
        }
        
        // TopicFormalAnswer
        EntityQuery queryFormalAnswer = buildQuery(request, "formalAnswer");
        List thesisFormalAnswers = (List) utilService.search(queryFormalAnswer);
        Map thesisFormalAnswerMap = new HashMap();
        for (Iterator it = thesisFormalAnswers.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            thesisFormalAnswerMap.put(obj[0] + "-" + obj[1], new Object[] { obj[2], obj[3] });
        }
        
        request.setAttribute("thesisManages", thesisManages);
        request.setAttribute("thesisOpenMap", thesisOpenMap);
        request.setAttribute("thesisWriteMap", thesisWriteMap);
        request.setAttribute("thesisAnnotateMap", thesisAnnotateMap);
        request.setAttribute("thesisPreAnswerMap", thesisPreAnswerMap);
        request.setAttribute("thesisFormalAnswerMap", thesisFormalAnswerMap);
        
        return forward(request, "schedule/statisicList");
    }
    
    /**
     * @param request
     * @param attr
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request, String attr) {
        return buildQuery(request, attr, attr, "left");
    }
    
    /**
     * @param request
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request, String attr, String alias,
            String joinMode) {
        EntityQuery query = new EntityQuery(ThesisManage.class, "thesisManage");
        populateConditions(request, query, "thesisManage.student.type.id");
        DataRealmUtils.addDataRealms(query, new String[] { "thesisManage.student.type.id",
                "thesisManage.student.department.id" }, restrictionHelper.getDataRealmsWith(
                getLong(request, "thesisManage.student.type.id"), request));
        String groupby = "thesisManage.student.department.id, thesisManage.student.type.id";
        query.groupBy(groupby);
        if (StringUtils.isEmpty(attr)) {
            query.setSelect(groupby);
            return query;
        }
        if (StringUtils.isNotEmpty(joinMode)) {
            query.join(joinMode, "thesisManage." + attr, alias);
        } else {
            query.join("thesisManage." + attr, alias);
        }
        query.setSelect(groupby + ", sum(case when " + alias
                + ".finishOn is null then 0 else 1 end), count(*)");
        query.add(new Condition(alias + " is not null"));
        return query;
    }
    
    /**
     * 统计论文里面的带论文的教师树目
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward thesisAndTeachers(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long stdTypeId = getLong(request, "stdTypeId");
        String stdTypeIdSeq = getDepartmentIdSeq(request);
        if (null == stdTypeId) {
            request.setAttribute("stdTypes", studentTypeService.getStudentTypes(stdTypeIdSeq));
            return forward(request, "loadThesisAndTeachers");
        }
        Long displayNum = getLong(request, "displayName");
        if (null == displayNum) {
            displayNum = new Long(3);
        }
        StudentType stdType = (StudentType) utilService.load(StudentType.class, stdTypeId);
        request.setAttribute("stdType", stdType);
        Set requireSet = new HashSet();
        requireSet.addAll(stdType.getDescendants());
        requireSet.add(stdType);
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);
        Map valueMap = thesisStatisticService.getThesisAndTeacherMap(stdTypeIdSeq, requireSet,
                String.valueOf(nowYear - displayNum.intValue() + 1));
        request.setAttribute("valueMap", valueMap);
        
        List years = new ArrayList();
        for (int i = displayNum.intValue() - 1; i >= 0; i--) {
            years.add(String.valueOf(nowYear - i));
        }
        request.setAttribute("needYears", years);
        return forward(request, "statisticThesisAndTeachers");
    }
    
    /**
     * @param annotateService
     *            The annotateService to set.
     */
    public void setAnnotateService(AnnotateService annotateService) {
        this.annotateService = annotateService;
    }
    
    /**
     * @param thesisStatisticService
     *            The thesisStatisticService to set.
     */
    public void setThesisStatisticService(ThesisStatisticService thesisStatisticService) {
        this.thesisStatisticService = thesisStatisticService;
    }
    
    /**
     * @param teachCalendarService
     *            The teachCalendarService to set.
     */
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
    
}
