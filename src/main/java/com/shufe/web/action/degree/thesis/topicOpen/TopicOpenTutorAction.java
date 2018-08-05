//$Id: TopicOpenTutorAction.java,v 1.13 2007/01/26 10:02:13 cwx Exp $
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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.security.User;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.util.RequestUtil;

/**
 * 论文开题导师界面响应类
 * 
 * @author chaostone
 * 
 */
public class TopicOpenTutorAction extends TopicOpenAction {
    
    /**
     * 加载论文开题首页面
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
        User tutor = getUser(request.getSession());
        Results.addObject("tutorId", tutor.getName());
        Results.addObject("stdTypeList", baseInfoService.getBaseInfos(StudentType.class)).addObject(
                "departmentList", departmentService.getAllColleges());
        return forward(request);
    }
    
    /**
     * 得到老师教授的所有的学生根据开关
     * 
     * @see com.shufe.web.action.degree.thesis.topicOpen.TopicOpenAction#doStdList(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doStdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = getUser(request.getSession());
        Teacher teacher = teacherService.getTeacherByNO(user.getName());
        Student student = (Student) RequestUtil.populate(request, Student.class, "student");
        student.setTeacher(teacher);
        String isOpened = request.getParameter("hasThesisOpen");
        if (StringUtils.isEmpty(isOpened)) {
            isOpened = "true";
        }
        Boolean isOpen = Boolean.valueOf(isOpened);
        ThesisManage thesisManage = new ThesisManage();
        thesisManage.setStudent(student);
        if (Boolean.TRUE.equals(isOpen)) {
            EntityQuery query = buildQuery(request);
            query.add(new Condition("student.teacher=:teacher", teacher));
            super.addCollection(request, "studentPage", utilService.search(query));
        } else {
            Pagination studentPage = topicOpenService.getstdsNoOpened(student, null, null,
                    getPageNo(request), getPageSize(request));
            addOldPage(request, "studentPage", studentPage);
        }
        request.setAttribute("isOpen", isOpen);
        request.setAttribute("flag", "tutor");
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
        ActionForward tutorAction = super.doLoadThesisTopic(mapping, form, request, response);
        request.setAttribute("flag", "tutor");
        return tutorAction;
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
        topicOpenService.batchUpdate(new String[] { "tutorAffirm" }, new Object[] { Boolean
                .valueOf(validate) }, thesisManage.getTopicOpen().getId().toString());
        return redirect(request, "index", "system.operation.success");
    }
    
    /**
     * 论文开题添加修改操作
     * 
     * @deprecated
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
        Map valueMap = RequestUtils.getParams(request, "thesisTopicOpen");
        List thesisManages = utilService.load(ThesisManage.class, "student.id", student.getId());
        ThesisManage thesisManage = new ThesisManage(student, new MajorType(MajorType.FIRST));
        if (thesisManages.size() > 0) {
            thesisManage = (ThesisManage) thesisManages.get(0);
            EntityUtils.populate(valueMap, thesisManage.getTopicOpen());
        }
        EntityUtils.evictEmptyProperty(thesisManage);
        utilService.saveOrUpdate(thesisManage);
        this.saveErr("system.operation.success", request);
        // return doLoadThesisTopic(mapping,form,request,response);
        return redirect(request, "doStdList", "info.confirm.success");
    }
    
    private void saveErr(String errorTag, HttpServletRequest request) {
        ActionMessages actionMessges = new ActionMessages();
        actionMessges.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorTag));
        saveErrors(request, actionMessges);
    }
}
