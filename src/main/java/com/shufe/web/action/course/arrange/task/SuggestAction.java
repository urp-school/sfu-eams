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
 * chaostone             2006-11-4            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.arrange.task;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.course.arrange.task.ArrangeSuggest;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.service.system.baseinfo.ClassroomService;
import com.shufe.service.system.calendar.TimeSettingService;
import com.shufe.web.action.common.DispatchBasicAction;
import com.shufe.web.action.course.arrange.TeacherTimeAction;

/**
 * 教学任务排课建议响应类
 * 
 * @author chaostone
 * 
 */
public class SuggestAction extends DispatchBasicAction {
    
    private ClassroomService classroomService;
    
    private TimeSettingService timeSettingService;
    
    /**
     * 编辑教学任务中的建议时间
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
        TeachTask task = (TeachTask) utilService.load(TeachTask.class, getLong(request,
                Constants.TEACHTASK_KEY));
        if (null == task) {
            return forwardError(mapping, request, "error.model.notExist");
        }
        request.setAttribute(Constants.TEACHTASK, task);
        addCollection(request, "unitList", timeSettingService.getDefaultSetting().getCourseUnits());
        addCollection(request, "weekList", WeekInfo.WEEKS);
        AvailableTime suggestTime = task.getArrangeInfo().getSuggest().getTime();
        if (null == suggestTime) {
            suggestTime = new AvailableTime();
        }
        if (null == suggestTime.getAvailable()) {
            suggestTime.setAvailable(AvailableTime.commonTaskAvailTime);
        }
        request.setAttribute("availTime", suggestTime);
        // 加载任务需要类型的教室
        Classroom room = new Classroom();
        room.setConfigType(task.getRequirement().getRoomConfigType());
        addCollection(request, "classroomList", classroomService.getClassrooms(room));
        return forward(request);
    }
    
    /**
     * 保存教学任务的建议排课时间
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
        TeachTask task = (TeachTask) utilService.load(TeachTask.class, getLong(request,
                Constants.TEACHTASK_KEY));
        if (null == task) {
            return forwardError(mapping, request, "error.model.notExist");
        }
        
        ArrangeSuggest suggest = task.getArrangeInfo().getSuggest();
        String availTime = get(request, "availTime");
        AvailableTime toBeDeletedTime = null;
        
        if (null == suggest.getTime()) {
            suggest.setTime(new AvailableTime());
        }
        suggest.getTime().setAvailable(availTime);
        if (!suggest.getTime().isValid()) {
            throw new Exception("availTime.not.corrected");
        }
        
        suggest.getTime().setRemark(StringUtils.abbreviate(get(request, "remark"), 100));
        suggest.getRooms().clear();
        String roomIds = get(request, "roomIds");
        if (StringUtils.isNotEmpty(roomIds)) {
            suggest.getRooms().addAll(
                    classroomService.getClassrooms(Arrays.asList(SeqStringUtil
                            .transformToLong(roomIds))));
        }
        utilService.saveOrUpdate(task);
        if (null != toBeDeletedTime) {
            utilService.remove(toBeDeletedTime);
        }
        // 如果是不是弹出窗口,则返回前一个页面
        if (StringUtils.isNotEmpty(get(request, "inIframe"))) {
            return redirect(request, new Action(TeacherTimeAction.class, "taskList"),
                    "info.save.success", new String[] { "task" });
        }
        return null;
    }
    
    public void setClassroomService(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }
    
    public void setTimeSettingService(TimeSettingService timeSettingService) {
        this.timeSettingService = timeSettingService;
    }
}
