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
 * chaostone             2006-4-6            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.model.course.task.TeachClass;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.course.task.SimpleAdminClassSplitMode;
import com.shufe.service.course.task.TeachClassSplitMode;
import com.shufe.util.DataAuthorityPredicate;

/**
 * 教学任务拆分合并界面相应类
 * 
 * @author chaostone
 * 
 */
public class TeachTaskMergeSplitAction extends TeachTaskSearchAction {
    
    /**
     * 编辑合并的教学任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward mergeSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = get(request, "taskIds");
        if (StringUtils.isEmpty(taskIds)) {
            return forward(mapping, request, "error.teachTask.id.needed", "error");
        }
        if (taskIds.indexOf(",") == -1) {
            return forward(mapping, request, "error.teachTask.id.multiNeeded", "error");
        }
        List taskList = teachTaskService.getTeachTasksByIds(taskIds);
        Long courseId = null;
        List teachers = null;
        for (Iterator iter = taskList.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            if (null == courseId) {
                courseId = task.getCourse().getId();
            }
            if (!courseId.equals(task.getCourse().getId())) {
                request.setAttribute("courseDiff", "true");
            }
            if (null == teachers) {
                teachers = task.getArrangeInfo().getTeachers();
            }
            if (!teachers.equals(task.getArrangeInfo().getTeachers())) {
                request.setAttribute("teacherDiff", "true");
            }
            if (task.getArrangeInfo().getIsArrangeComplete().equals(Boolean.TRUE)) {
                request.setAttribute("arrangeCompelte", "true");
            }
        }
        addCollection(request, "taskList", taskList);
        return forward(request);
    }
    
    /**
     * 教学任务和并
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward affirmSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List list = teachTaskService.getTeachTasksByIds(get(request, "taskIds"));
        List taskList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            task.setIsConfirm(Boolean.TRUE);
            taskList.add(task);
        }
        utilService.saveOrUpdate(taskList);
        request.setAttribute("method", "search");
        return forward(mapping, request, "info.confirm.success", "redirector");
    }
    
    /**
     * 教学任务和并
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward merge(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String reservedId = get(request, "reservedId");
        String taskIdSeq = get(request, "taskIds");
        
        if (StringUtils.isEmpty(reservedId) || StringUtils.isEmpty(taskIdSeq)) {
            return forward(mapping, request, "error.teachTask.id.Needed", "error");
        }
        try {
            logHelper.info(request, "try to merge tasks " + taskIdSeq + " to " + reservedId);
            teachTaskService.merge(SeqStringUtil.transformToLong(taskIdSeq), new Long(reservedId));
        } catch (Exception e) {
            logHelper.info(request, "Failure in merge tasks " + taskIdSeq + " to " + reservedId, e);
            return forward(mapping, request, "error.teachTask.mergedFailure", "error");
        }
        addSingleParameter(request, "method", "search");
        return forward(mapping, request, "info.merge.success", "redirector");
    }
    
    /**
     * 编辑拆分的教学任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward splitSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long taskId = getLong(request, "task.id");
        if (null == taskId) {
            return forward(mapping, request, "error.teachTask.id.needed", "error");
        }
        TeachTask task = teachTaskService.getTeachTask(taskId);
        try {
            task.getTeachClass().getName();
            if (!new DataAuthorityPredicate(getStdTypeIdSeq(request), getDepartmentIdSeq(request),
                    "teachClass.stdType", "arrangeInfo.teachDepart").evaluate(task)) {
                return forward(mapping, request, "error.dataRealm.insufficient", "error");
            }
        } catch (Exception e) {
            return forward(mapping, request, "error.teachTask.id.notExists", "error");
        }
        String splitNum = get(request, "splitNum");
        if (!StringUtils.isNumeric(splitNum)) {
            return forwardError(mapping, request, "error.splitNum.notNum");
        }
        int splits = new Integer(splitNum).intValue();
        String tag = "";
        TeachClass tc = task.getTeachClass();
        // first char in tag means if has adminclass or not
        // second char means if has elective stds
        if (tc.getAdminClasses().isEmpty() && tc.getCourseTakes().isEmpty()) {
            tag = "00";// tip num directely
        } else if (tc.getLonelyCourseTakes().isEmpty()) {
            if (tc.getAdminClasses().size() == splits) {
                tag = "10";
            } else {
                tag = "20";
            }
        }// tip num and display list;(keep)}
        else if (tc.getAdminClasses().isEmpty()) {
            tag = "01";// avg and custome(custome share)
        } else {
            if (splits == tc.getAdminClasses().size()) {
                tag = "11";
            } else if (splits > tc.getAdminClasses().size()) {
                tag = "12";
            } else {
                tag = "13";
            }
        }// keep;keepshare;share;customeshare
        addSingleParameter(request, "splitTag", tag);
        addSingleParameter(request, "task", task);
        return forward(request);
    }
    
    /**
     * 教学任务拆分
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward split(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long taskId = getLong(request, "task.id");
        if (null == taskId) {
            return forward(mapping, request, "error.teachTask.id.needed", "error");
        }
        TeachTask task = teachTaskService.loadTeachTask(taskId);
        try {
            // task.getTeachClass().getName();
            if (!new DataAuthorityPredicate(getStdTypeIdSeq(request), getDepartmentIdSeq(request),
                    "teachClass.stdType", "arrangeInfo.teachDepart").evaluate(task)) {
                return forward(mapping, request, "error.dataRealm.Insufficient", "error");
            }
        } catch (Exception e) {
            return forward(mapping, request, "error.teachTask.notExists", "error");
        }
        String splitNum = get(request, "splitNum");
        if (!StringUtils.isNumeric(splitNum)) {
            return forward(mapping, request, "error.splitNum.notNum", "error");
        }
        String mode = get(request, "splitMode");
        
        if (StringUtils.isEmpty(mode)) {
            mode = "avgShare";
        }
        String splitUnitNums = get(request, "splitUnitNums");
        try {
            logHelper.info(request, "try to split task " + task.getId());
            TeachClassSplitMode splitMode = TeachClassSplitMode.getMode(mode);
            if (mode.equals("adminClassSplit")) {
                Set secondClasses = new HashSet(utilService.load(AdminClass.class, "id",
                        SeqStringUtil.transformToLong(get(request, "secondClassIds"))));
                ((SimpleAdminClassSplitMode) splitMode).setSecondClasses(secondClasses);
            }
            teachTaskService.split(task, Integer.parseInt(splitNum), splitMode, SeqStringUtil
                    .transformToInteger(splitUnitNums));
        } catch (Exception e) {
            logHelper.info(request, "Failure in split task " + task.getId(), e);
            e.printStackTrace();
            return forward(mapping, request, "error.occurred", "error");
        }
        request.setAttribute("method", "search");
        return forward(mapping, request, "info.split.success", "redirector");
    }
}
