//$Id: TopicOpenStdAction.java,v 1.24 2007/01/23 09:38:22 cwx Exp $
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
 * chenweixiong         2006-10-18          Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.thesis.topicOpen;

import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.transfer.exporter.Context;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.Tache;
import com.ekingstar.eams.system.basecode.industry.ThesisTopicSource;
import com.ekingstar.eams.system.basecode.industry.ThesisType;
import com.ekingstar.eams.system.basecode.state.Degree;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.process.Schedule;
import com.shufe.model.degree.thesis.topicOpen.TopicOpen;
import com.shufe.model.std.Student;
import com.shufe.model.system.file.FilePath;
import com.shufe.service.degree.thesis.ThesisManageService;
import com.shufe.service.degree.thesis.process.ScheduleService;
import com.shufe.service.degree.thesis.topicOpen.TopicOpenService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.web.action.system.file.FileAction;

public class TopicOpenStdAction extends FileAction {
    
    private TopicOpenService topicOpenService;
    
    private TeachCalendarService teachCalendarService;
    
    private ThesisManageService thesisManageService;
    
    private ScheduleService scheduleService;
    
    /**
     * 得到一个学生的论文开题情况
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
        Student student = getStudentFromSession(request.getSession());
        request.setAttribute("student", student);
        ThesisManage thesisManage = thesisManageService.getThesisManage(student.getId());
        if (null == thesisManage || null == thesisManage.getSchedule()) {
            Schedule schedule = scheduleService.getScheduleByStd(student);
            if (null == schedule) {
                request.setAttribute("nofound", "noschedule");
                return forward(request, "../../noscheduleErrors");
            }
            if (null == thesisManage) {
                thesisManage = new ThesisManage(student, new MajorType(MajorType.FIRST));
                thesisManage.setTeachCalendar(teachCalendarService.getNearestCalendar(student
                        .getType()));
                EntityUtils.evictEmptyProperty(thesisManage);
            }
            thesisManage.setSchedule(schedule);
            utilService.saveOrUpdate(thesisManage);
        } else {
            request.setAttribute("thesisManage", thesisManage);
        }
        if (!thesisManage.containTache(Tache.TOPICOPEN)) {
            request.setAttribute("nofound", "topicOpen");
            return forward(request, "../../noscheduleErrors");
        }
        request.setAttribute("topicOpen", thesisManage.getTopicOpen());
        StudentType stdType = student.getType();
        if (Degree.MASTER.equals(stdType.getDegree().getId())
                || Degree.EQUIVALENTMASTER.equals(stdType.getDegree().getId())) {
            request.setAttribute("stdType", "master");
        } else if (Degree.DOCTOR.equals(stdType.getDegree().getId())
                || Degree.EQUIVALENTDOCTOR.equals(stdType.getDegree().getId())) {
            request.setAttribute("stdType", "doctor");
        } else if (StudentType.UNDERGRADUATESTUDENTTYPID.equals(stdType.getId())
                || StudentType.UNDERGRADUATESTUDENTTYPID.equals(stdType.getSuperType().getId())) {
            request.setAttribute("stdType", "undergraduate");
        } else {
            return forward(request, "error");
        }
        return forward(request, "index");
    }
    
    /**
     * 信息展示页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward topicOpenInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String topicOpenFlag = request.getParameter("topicOpenFlag");
        Student std = getStudentFromSession(request.getSession());
        request.setAttribute("topicOpen", topicOpenService.getTopicOpen(std.getId()));
        request.setAttribute("topicOpenFlag", topicOpenFlag);
        request.setAttribute("stdType", request.getParameter("stdType"));
        request.setAttribute("topicOpenInfo", "info");
        request.setAttribute("student", std);
        return forward(request);
    }
    
    /**
     * 编辑页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Student std = getStudentFromSession(request.getSession());
        String topicOpenFlag = request.getParameter("topicOpenFlag");
        request.setAttribute("topicOpen", topicOpenService.getTopicOpen(std.getId()));
        request.setAttribute("topicOpenFlag", topicOpenFlag);
        request.setAttribute("stdType", request.getParameter("stdType"));
        request.setAttribute("topicOpenInfo", "edit");
        request.setAttribute("curDate", new Date(System.currentTimeMillis()));
        request.setAttribute("thesisTypes", utilService.load(ThesisType.class, "state",
                Boolean.TRUE));
        request.setAttribute("thesisSources", utilService.load(ThesisTopicSource.class, "state",
                Boolean.TRUE));
        return forward(request);
    }
    
    /**
     * 基本信息的保存页面
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
        Student std = getStudentFromSession(request.getSession());
        ThesisManage thesisManage = thesisManageService.getThesisManage(std.getId());
        TopicOpen topicOpen = thesisManage.getTopicOpen();
        if (null == topicOpen) {
            topicOpen = new TopicOpen();
            thesisManage.setTopicOpen(topicOpen);
        }
        Map valueMap = RequestUtils.getParams(request, "topicOpen");
        EntityUtils.populate(valueMap, topicOpen);
        EntityUtils.evictEmptyProperty(topicOpen);
        utilService.saveOrUpdate(topicOpen);
        utilService.saveOrUpdate(thesisManage);
        return redirect(request, "topicOpenInfo", "info.confirm.success", "topicOpenFlag="
                + request.getParameter("topicOpenFlag") + "&stdType="
                + request.getParameter("stdType"));
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doUpThesis(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String flagValue = request.getParameter("isUp");
        if (StringUtils.isBlank(flagValue) || !"up".equals(flagValue)) {
            request.setAttribute("topicOpenId", request.getParameter("topicOpenId"));
            return forward(request, "thesisUp");
        }
        ActionForward f = upload(request, getFileRealPath(FilePath.THESIS, request));
        if (f != null)
            return f;
        return redirect(request, "doLoadThesisTopic", "info.confirm.success");
    }
    
    public void doDownloadThesis(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long topicOpenId = getLong(request, "topicId");
        TopicOpen topicOpen = (TopicOpen) utilService.load(TopicOpen.class, topicOpenId);
        String fileAbsolutePath = getFileRealPath(FilePath.THESIS, request)
                + topicOpen.getDownloadName();
        download(request, response, fileAbsolutePath, topicOpen.getDisplayName());
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.web.action.system.file.FileAction#afterUpload(javax.servlet.http.HttpServletRequest,
     *      java.io.File, java.lang.String)
     */
    protected void afterUpload(HttpServletRequest request, File file, String updloadFilePath) {
        Student student = getStudentFromSession(request.getSession());
        updloadFilePath = updloadFilePath.substring(updloadFilePath.lastIndexOf("\\") + 1);
        updloadFilePath = updloadFilePath.substring(updloadFilePath.lastIndexOf("/") + 1);
        String topicOpenId = request.getParameter("topicOpenId");
        TopicOpen topicOpen = (TopicOpen) utilService.load(TopicOpen.class, Long
                .valueOf(topicOpenId));
        topicOpen.setDownloadName(file.getName());
        topicOpen.setDisplayName(student.getName() + file.getName());
        utilService.saveOrUpdate(topicOpen);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.web.action.system.file.FileAction#getFileName(javax.servlet.http.HttpServletRequest,
     *      java.lang.String)
     */
    protected String getFileName(HttpServletRequest request, String uploadAbsolutePath) {
        String fileName = super.getFileName(request, uploadAbsolutePath);
        String stdCode = getUser(request.getSession()).getName();
        // FIXME
        fileName = "01_" + stdCode + fileName.substring(fileName.lastIndexOf("."));
        return fileName;
    }
    
    /**
     * 补充开题时候的信息。
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doAfterOpenExtraInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = getUser(request.getSession()).getName();
        List stdList = utilService.load(Student.class, "code", name);
        Student student = (stdList.size() > 0) ? (Student) stdList.get(0) : new Student();
        ThesisManage thesisManage = thesisManageService.getThesisManage(student);
        if (null != thesisManage) {
            TopicOpen topicOpen = thesisManage.getTopicOpen();
            Map valueMap = RequestUtils.getParams(request, "thesisTopicOpen");
            EntityUtils.populate(valueMap, topicOpen);
            utilService.saveOrUpdate(topicOpen);
        }
        return redirect(request, "doLoadThesisTopic", "filed.thesisOpen.updateExtraInfo");
    }
    
    /**
     * 学生同意确认
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doAffirm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Student student = getStudentFromSession(request.getSession());
        ThesisManage thesisManage = thesisManageService.getThesisManage(student);
        thesisManage.getTopicOpen().setFinishOn(new java.sql.Date(System.currentTimeMillis()));
        utilService.saveOrUpdate(thesisManage.getTopicOpen());
        return redirect(request, "doLoadThesisTopic", "");
    }
    
    protected void configExportContext(HttpServletRequest request, Context context) {
        Student student = getStudentFromSession(request.getSession());
        context.put("student", student);
        context.put("topicOpen", topicOpenService.getTopicOpen(student));
    }
    
    public void setTopicOpenService(TopicOpenService topicOpenService) {
        this.topicOpenService = topicOpenService;
    }
    
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
    
    public void setThesisManageService(ThesisManageService thesisManageService) {
        this.thesisManageService = thesisManageService;
    }
    
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    
}
