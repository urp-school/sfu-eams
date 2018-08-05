//$Id: ThesisManageStdAction.java,v 1.1 2007-4-12 16:49:27 Administrator Exp $
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
 * chenweixiong              2007-4-12         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.thesis;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.transfer.exporter.Context;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.degree.study.StudyThesis;
import com.shufe.model.degree.subject.Level2Subject;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.ThesisStore;
import com.shufe.model.degree.thesis.process.Schedule;
import com.shufe.model.std.Student;
import com.shufe.model.system.file.DegreeDocument;
import com.shufe.model.system.file.FilePath;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.degree.study.StudyProductService;
import com.shufe.service.degree.thesis.ThesisManageService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.web.action.system.file.FileAction;

public class ThesisManageStdAction extends FileAction {
    
    private ThesisManageService thesisManageService;
    
    private TeachCalendarService teachCalendarService;
    
    private StudyProductService studyProductService;
    
    private GradeService gradeService;
    
    /**
     * @param gradeService
     *            The gradeService to set.
     */
    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }
    
    /**
     * @param studyProductService
     *            The studyProductService to set.
     */
    public void setStudyProductService(StudyProductService studyProductService) {
        this.studyProductService = studyProductService;
    }
    
    /**
     * @param thesisManageService
     *            The thesisManageService to set.
     */
    public void setThesisManageService(ThesisManageService thesisManageService) {
        this.thesisManageService = thesisManageService;
    }
    
    /**
     * @param teachCalendarService
     *            The teachCalendarService to set.
     */
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
    
    /**
     * 学生进入进度列表页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doLoadProcessPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = getUser(request.getSession()).getName();
        Student student = (Student) utilService.load(Student.class, "code", name).get(0);
        ThesisManage thesisManage = thesisManageService.getThesisManage(student);
        /**
         * 论文管理里面 如果管理是空的 或者说 管理所对应的进度是空的时候 就进入修改论文管理界面。
         */
        // if (null == thesisManage || null == thesisManage.getSchedule()) {
        StudentType stdType = student.getType();
        Schedule schedule = null;
        while (null != stdType && null == schedule) {
            List schedules = utilService.load(Schedule.class, new String[] { "studyLength",
                    "enrollYear", "studentType.id" }, new Object[] {
                    String.valueOf(student.getSchoolingLength()), student.getEnrollYear(),
                    stdType.getId() });
            schedule = (schedules.size() > 0) ? (Schedule) schedules.get(0) : null;
            stdType = (StudentType) stdType.getSuperType();
        }
        if (null == schedule) {
            request.setAttribute("student", student);
            return forward(request, "noscheduleErrors");
        }
        if (null == thesisManage) {
            thesisManage = new ThesisManage(student, new MajorType(MajorType.FIRST));
            thesisManage.setTeachCalendar(teachCalendarService
                    .getNearestCalendar(student.getType()));
            EntityUtils.evictEmptyProperty(thesisManage);
        }
        if (!ObjectUtils.equals(schedule, thesisManage.getSchedule())) {
            thesisManage.setSchedule(schedule);
            utilService.saveOrUpdate(thesisManage);
        }
        request.setAttribute("thesisManage", thesisManage);
        request.setAttribute("schedule", thesisManage.getSchedule());
        return forward(request);
    }
    
    /**
     * 下载文件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public void doDownLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long documentId = getLong(request, "document.id");
        if (!ValidEntityKeyPredicate.getInstance().evaluate(documentId)) {
            response.getWriter().write("without docuemnt with id:" + documentId);
            return;
        }
        DegreeDocument degreeDocument = (DegreeDocument) utilService.load(DegreeDocument.class,
                documentId);
        if (null == degreeDocument) {
            response.getWriter().write("without docuemnt with id:" + documentId);
            return;
        }
        String filePath = getFileRealPath(FilePath.DEGREE, request);
        download(request, response, filePath + degreeDocument.getPath(), degreeDocument.getName());
    }
    
    /**
     * 得到上传页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward loadUploadPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    /**
     * 上传 文件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doUpload(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward f = upload(request, getFileRealPath(FilePath.DEGREE, request));
        if (f != null)
            return f;
        return redirect(request, "search", "info.upload.success");
    }
    
    /**
     * 上传文件后做的操作
     * 
     * @see com.shufe.web.action.system.file.FileAction#afterUpload(javax.servlet.http.HttpServletRequest,
     *      java.io.File, java.lang.String)
     */
    protected void afterUpload(HttpServletRequest request, File file, String updloadFilePath) {
        super.afterUpload(request, file, updloadFilePath);
        updloadFilePath = updloadFilePath.substring(updloadFilePath.lastIndexOf("\\") + 1);
        updloadFilePath = updloadFilePath.substring(updloadFilePath.lastIndexOf("/") + 1);
        String manageType = request.getParameter("manageType");
        Long thesisManageId = getLong(request, "thesisManage.id");
        ThesisManage thesisManage = (ThesisManage) utilService.get(ThesisManage.class,
                thesisManageId);
        ThesisStore thesisStore = thesisManage.setProcessObject(manageType, file.getName(),
                updloadFilePath);
        utilService.saveOrUpdate(thesisStore);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#configExportContext(javax.servlet.http.HttpServletRequest,
     *      com.ekingstar.commons.transfer.exporter.Context)
     */
    protected void configExportContext(HttpServletRequest request, Context context) {
        Student student = getStudentFromSession(request.getSession());
        context.put("student", student);
        context.put("thesisManage", thesisManageService.getThesisManage(student));
        String exportFlag = request.getParameter("exportFlag");
        if ("degreeInfo".equals(exportFlag)) {
            Map studyResults = studyProductService.getStudyProducts(student.getId(), Boolean.FALSE,
                    null);
            context.put("studyResults", studyResults);
            context.put("totleMark", gradeService.getCredit(student.getCode()));
            context.put("speciaMark", gradeService.getCredit(student.getCode()));// 学位课学分
        }
        if ("doctorDegreeCheck".equals(exportFlag)) {
            context.put("studyThesiss", utilService.load(StudyThesis.class, new String[] {
                    "student.id", "isPassCheck" }, new Object[] { student.getId(), Boolean.TRUE }));
        }
        EntityQuery query = new EntityQuery(Level2Subject.class, "level2Subject");
        query.add(new Condition("level2Subject.speciality.id = (:specialityId)", student
                .getFirstMajor().getId()));
        List list = (List) utilService.search(query);
        if (!list.isEmpty()) {
            Level2Subject level2Subject = (Level2Subject) list.get(0);
            context.put("level2Subject", level2Subject);
        }
        context.put("systemConfig", getSystemConfig());
    }
}
