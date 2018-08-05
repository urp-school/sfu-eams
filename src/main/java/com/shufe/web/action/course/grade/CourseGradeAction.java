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
 * chaostone             2007-1-3            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.grade;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.GradeState;
import com.shufe.model.course.task.TeachTask;
import com.shufe.service.course.arrange.exam.ExamTakeService;
import com.shufe.service.course.grade.GradeImportListener;

/**
 * 从任务的角度按照学年度学期管理成绩(管理员界面)
 * 
 * @author chaostone
 * 
 */
public class CourseGradeAction extends CollegeGradeAction {
    
    protected ExamTakeService examTakeService;
    
    /**
     * 修改成绩(包括各种成绩成分)
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
     * 保存成绩(包括各种成绩成分)
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
        return redirect(request, "info", "info.save.success");
    }
    
    /**
     * 查看成绩状态
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
        Long taskId = getLong(request, "taskId");
        TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
        List gradeTypes = baseCodeService.getCodes(GradeType.class);
        Collections.sort(gradeTypes, new PropertyComparator("priority", true));
        addCollection(request, "gradeTypes", gradeTypes);
        addCollection(request, "gradeTypeInState", gradeService.getGradeTypes(task.getGradeState()));
        request.setAttribute("gradeState", task.getGradeState());
        request.setAttribute("task", task);
        return forward(request);
    }
    
    /**
     * 保存成绩状态
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
        Long taskId = getLong(request, "taskId");
        TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
        List gradeTypes = baseCodeService.getCodes(GradeType.class);
        GradeState state = task.getGradeState();
        Boolean status = null;
        for (Iterator iter = gradeTypes.iterator(); iter.hasNext();) {
            GradeType gradeType = (GradeType) iter.next();
            status = getBoolean(request, gradeType.getId() + "Input");
            if (Boolean.TRUE.equals(status)) {
                state.addInput(gradeType.getMark().intValue());
            } else {
                state.removeInput(gradeType.getMark().intValue());
            }
            status = getBoolean(request, gradeType.getId() + "Confirm");
            if (Boolean.TRUE.equals(status)) {
                state.addConfirm(gradeType.getMark().intValue());
            } else {
                state.removeConfirm(gradeType.getMark().intValue());
            }
            status = getBoolean(request, gradeType.getId() + "Publish");
            if (Boolean.TRUE.equals(status)) {
                state.addPublish(gradeType.getMark().intValue());
            } else {
                state.removePublish(gradeType.getMark().intValue());
            }
        }
        utilService.saveOrUpdate(state);
        return redirect(request, "search", "info.save.success");
    }
    
    /**
     * 发布成绩/取消发布
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward publishGrade(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = request.getParameter("taskIds");
        GradeType gradeType = null;
        Boolean isPublished = getBoolean(request, "isPublished");
        if (null == isPublished)
            isPublished = Boolean.TRUE;
        Boolean allGradeType = getBoolean(request, "allGradeType");
        if (Boolean.FALSE.equals(allGradeType)) {
            gradeType = (GradeType) baseCodeService.getCode(GradeType.class, GradeType.FINAL);
        }
        gradeService.publishGrade(taskIds, gradeType, isPublished);
        return redirect(request, "search", "info.save.success");
    }
    
    /**
     * 删除教学任务中的个别学生成绩
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
        String msg = courseGradeHelper.removeStdGrade(request, getUser(request));
        if (StringUtils.isEmpty(msg)) {
            return redirect(request, "info", "info.delete.success");
        } else {
            return forwardError(mapping, request, msg);
        }
    }
    
    public void setExamTakeService(ExamTakeService examTakeService) {
        this.examTakeService = examTakeService;
    }
    
    /**
     * 设置四六体系课程
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward setMoralGrade(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String moralGradePercent = request.getParameter("moralGradePercent");
        Long[] taskIds = SeqStringUtil.transformToLong(get(request, "taskIds"));
        for (int i = 0; i < taskIds.length; i++) {
            TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskIds[i]);
            GradeState state = task.getGradeState();
            state.setMoralGradePercent(Float.valueOf(moralGradePercent));
            utilService.saveOrUpdate(state);
        }
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 取消四六体系课程
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cancelMoralGrade(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] taskIds = SeqStringUtil.transformToLong(get(request, "taskIds"));
        for (int i = 0; i < taskIds.length; i++) {
            TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskIds[i]);
            GradeState state = task.getGradeState();
            state.setMoralGradePercent(null);
            utilService.saveOrUpdate(state);
        }
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 导入成绩
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TransferResult tr = new TransferResult();
        Transfer transfer = ImporterServletSupport.buildEntityImporter(request, CourseGrade.class,
                tr);
        if (null == transfer) {
            return forward(request, "/pages/components/importData/error");
        }
        transfer.addListener(new ImporterForeignerListener(utilService)).addListener(
                new GradeImportListener(utilService.getUtilDao(), gradePointRuleService));
        transfer.transfer(tr);
        if (tr.hasErrors()) {
            return forward(request, "/pages/components/importData/error");
        } else {
            return redirect(request, "search", "info.import.success",
                    "gradeState.confirmGA&calendar.id");
        }
    }
    
}
