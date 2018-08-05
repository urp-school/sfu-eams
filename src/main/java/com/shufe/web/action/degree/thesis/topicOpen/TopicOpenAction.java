//$Id: TopicOpenAction.java,v 1.25 2007/01/26 10:02:13 cwx Exp $
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
 * chenweixiong              2006-10-18         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.thesis.topicOpen;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.state.Degree;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.topicOpen.TopicOpen;
import com.shufe.model.std.Student;
import com.shufe.service.degree.thesis.ThesisManageService;
import com.shufe.service.degree.thesis.topicOpen.TopicOpenService;
import com.shufe.service.std.StudentService;
import com.shufe.service.system.baseinfo.TeacherService;

/**
 * 管理员论文开题界面响应类
 * 
 * @author chaostone
 * 
 */
public class TopicOpenAction extends TopicOpenSearchAction {
    
    protected TopicOpenService topicOpenService;
    
    protected StudentService studentService;
    
    protected TeacherService teacherService;
    
    protected ThesisManageService thesisManageService;
    
    /**
     * 加载论文开题页面
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
        setDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    /**
     * 根据条件查询学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doStdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Student student = (Student) populate(request, Student.class);
        String isOpened = request.getParameter("hasThesisOpen");
        if (StringUtils.isEmpty(isOpened)) {
            isOpened = "true";
        }
        Boolean isOpen = Boolean.valueOf(isOpened);
        if (Boolean.TRUE.equals(isOpen)) {
            // FIXME -> NULL
            EntityQuery query = buildQuery(request);
            addCollection(request, "studentPage", utilService.search(query));
        } else {
            Pagination studentPage = topicOpenService.getstdsNoOpened(student,
                    getDepartmentIdSeq(request), getStdTypeIdSeq(request), getPageNo(request),
                    getPageSize(request));
            addOldPage(request, "studentPage", studentPage);
        }
        
        request.setAttribute("isOpen", isOpen);
        request.setAttribute("flag", "admin");
        return forward(request, "stdList");
    }
    
    /**
     * 得到某个学生的具体的开题
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doLoadThesisTopic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stdId = request.getParameter("stdId");
        Student std = studentService.getStudent(Long.valueOf(stdId));
        request.setAttribute("student", std);
        request.setAttribute("topicOpen", topicOpenService.getTopicOpen(std.getId()));
        request.setAttribute("topicOpenInfo", "info");
        StudentType stdType = std.getType();
        if (Degree.MASTER.equals(stdType.getDegree().getId())
                || Degree.EQUIVALENTMASTER.equals(stdType.getDegree().getId())) {
            request.setAttribute("stdType", "master");
        } else if (Degree.DOCTOR.equals(stdType.getDegree().getId())
                || Degree.EQUIVALENTDOCTOR.equals(stdType.getDegree().getId())) {
            request.setAttribute("stdType", "doctor");
        }
        return forward(request, "loadTopicOpenPage");
    }
    
    /**
     * 论文的确认和取消
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @deprecated
     * @throws Exception
     */
    public ActionForward researchValidate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stdId = request.getParameter("stdId");
        Student student = studentService.getStudent(Long.valueOf(stdId));
        List thesisManagerList = utilService
                .load(ThesisManage.class, "student.id", student.getId());
        ThesisManage thesisManage = new ThesisManage();
        if (thesisManagerList.size() > 0) {
            thesisManage = (ThesisManage) thesisManagerList.get(0);
        }
        String validate = request.getParameter("validate");
        topicOpenService.batchUpdate(new String[] { "departmentAffirm" }, new Object[] { Boolean
                .valueOf(validate) }, thesisManage.getTopicOpen().getId().toString());
        String parameters = request.getParameter("parameters");
        return redirect(request, "doStdList", "system.operation.success", parameters);
    }
    
    /**
     * 论文开题添加修改操作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward thesisOperation(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stdId = request.getParameter("stdId");
        Student student = studentService.getStudent(Long.valueOf(stdId));
        if (topicOpenService.checkDepartmentValidate(student)) {
            return doLoadThesisTopic(mapping, form, request, response);
        }
        Map conditionMap = RequestUtils.getParams(request, "thesisTopicOpen");
        List thesisManages = utilService.load(ThesisManage.class, "student.id", student.getId());
        if (thesisManages.size() > 0) {
            ThesisManage thesisManage = (ThesisManage) thesisManages.get(0);
            TopicOpen topicOpen = null != thesisManage ? thesisManage.getTopicOpen()
                    : new TopicOpen();
            EntityUtils.populate(conditionMap, topicOpen);
            utilService.saveOrUpdate(topicOpen);
        }
        return redirect(request, "doStdList", "info.confirm.success");
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doSettinTimeAndAddress(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String flag = request.getParameter("flag");
        String openTime = request.getParameter("time");
        String openAddress = request.getParameter("address");
        Long[] topicOpens = new Long[0];
        if ("select".equals(flag)) {
            topicOpens = SeqStringUtil.transformToLong(request.getParameter("selectSeq"));
        } else if ("all".equals(flag)) {
            ThesisManage thesisManage = (ThesisManage) populate(request, ThesisManage.class,
                    "thesisManage");
            thesisManage.setStudent((Student) populate(request, Student.class));
            thesisManage.setTopicOpen((TopicOpen) populate(request, TopicOpen.class));
            List topticOpenIds = thesisManageService.getProjectionConditions(thesisManage,
                    getDepartmentIdSeq(request), getStdTypeIdSeq(request), "topicOpen.id");
            topicOpens = new Long[topticOpenIds.size()];
            int k = 0;
            for (Iterator it = topticOpenIds.iterator(); it.hasNext();) {
                topicOpens[k++] = (Long) it.next();
            }
        }
        topicOpenService.batchAffirmTimeAndAddress(topicOpens, openTime, openAddress);
        return redirect(request, "doStdList", "info.action.success");
    }
    
    /**
     * 更新状态
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doUpdateIsPassed(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String estate = request.getParameter("isPassedValue");
        String topicOpenIdSeq = request.getParameter("topicOpenIdSeq");
        topicOpenService.batchUpdate(new String[] { "isPassed" }, new Object[] { Integer
                .valueOf(estate) }, topicOpenIdSeq);
        return redirect(request, "doStdList", "info.action.success");
    }
    
    /**
     * 院系批量更新确认情况
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doModifyTopicOpen(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String affirm = request.getParameter("isAffirm");
        String personType = request.getParameter("personType");
        String topicOpenIdSeq = request.getParameter("topicOpenIdSeq");
        if (personType.indexOf("teacher") > -1) {
            topicOpenService.batchUpdate(new String[] { "tutorAffirm" }, new Object[] { Boolean
                    .valueOf(affirm) }, topicOpenIdSeq);
        } else if (personType.indexOf("college") > -1) {
            topicOpenService.batchUpdate(new String[] { "departmentAffirm" },
                    new Object[] { Boolean.valueOf(affirm) }, topicOpenIdSeq);
        }
        return redirect(request, "doStdList", "info.action.success");
    }
    
    /**
     * 得到未开题的学生信息
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        Student student = (Student) populate(request, Student.class);
        List stds = topicOpenService.doGetNoOpenedList(student, getDepartmentIdSeq(request),
                getStdTypeIdSeq(request));
        return stds;
    }
    
    /**
     * 取消学生的论文开题提交时间
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cancelStdCommit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String topicOpenIdSeq = request.getParameter("topicOpenIdSeq");
        topicOpenService.batchUpdate(new String[] { "finishOn" }, new Object[] { null },
                topicOpenIdSeq);
        return redirect(request, "doStdList", "info.action.success");
    }
    
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    
    /**
     * @param thesisManageService
     *            The thesisManageService to set.
     */
    public void setThesisManageService(ThesisManageService thesisManageService) {
        this.thesisManageService = thesisManageService;
    }
    
    /**
     * @param topicOpenService
     *            The topicOpenService to set.
     */
    public void setTopicOpenService(TopicOpenService topicOpenService) {
        this.topicOpenService = topicOpenService;
    }
}
