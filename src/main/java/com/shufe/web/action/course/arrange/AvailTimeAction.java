//$Id: AvailTimeAction.java,v 1.9 2006/12/30 01:29:02 duanth Exp $
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
 * chaostone            2005-11-16          Created
 * zq                   2007-09-18          修改或替换了本Action中的所有info()方法
 ********************************************************************************/

package com.shufe.web.action.course.arrange;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.course.arrange.AvailTimeService;
import com.shufe.service.system.baseinfo.ClassroomService;
import com.shufe.service.system.baseinfo.DepartmentService;
import com.shufe.service.system.baseinfo.TeacherService;
import com.shufe.service.system.calendar.TimeSettingService;
import com.shufe.util.DataAuthorityUtil;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 排课可用时间设置界面相应类
 * 
 * @author chaostone 2005-11-16
 */
public class AvailTimeAction extends RestrictionSupportAction {
    
    private AvailTimeService availTimeService;
    
    private TeacherService teacherService;
    
    private ClassroomService roomService;
    
    private DepartmentService departmentService;
    
    private TimeSettingService timeSettingService;
    
    /**
     * 教师可用时间主界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward teacherAvailTimeHome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(Teacher.class, "teacher");
        query.add(new Condition("teacher.isTeaching=true"));
        query.setSelect("distinct teacher.department");
        request.setAttribute("departList", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 教室可用时间主界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward roomAvailTimeHome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String departDataRealm = "";
        departDataRealm = getDepartmentIdSeq(request);
        if (!StringUtils.contains(departDataRealm, String.valueOf(Department.SCHOOLID)))
            departDataRealm += "," + String.valueOf(Department.SCHOOLID);
        
        List classroomConfigTypeList = baseCodeService.getCodes(ClassroomType.class);
        Results.addObject("classroomConfigTypeList", classroomConfigTypeList);
        initBaseCodes(request, "districtList", SchoolDistrict.class);
        Results.addObject("departList", departmentService.getDepartments(departDataRealm));
        return forward(request);
    }
    
    /**
     * 加载查询教师
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward teacherList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) populate(request, Teacher.class);
        addOldPage(request, "teacherList", teacherService.getTeachers(teacher, getPageNo(request),
                getPageSize(request)));
        return forward(request);
    }
    
    /**
     * 加载查询教室
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward roomList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Classroom room = (Classroom) populate(request, Classroom.class);
        // check room
        ActionForward errorForward = checkRoom(mapping, request, room);
        if (null != errorForward)
            return errorForward;
        // query
        int pageNo = getPageNo(request);
        int pageSize = getPageSize(request);
        String departIds = request.getParameter("depart.id");
        if (StringUtils.isEmpty(departIds)) {
            departIds = getDepartmentIdSeq(request);
            // add schoolID to allowed school
            if (!StringUtils.contains(departIds, String.valueOf(Department.SCHOOLID)))
                departIds = departIds + "," + String.valueOf(Department.SCHOOLID);
        }
        
        addOldPage(request, "classroomList", roomService.getClassrooms(room, departIds, pageNo,
                pageSize));
        return forward(request);
    }
    
    /**
     * 教室可用时间信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward roomAvailTimeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long roomId = getLong(request, "classroom.id");
        if (null == roomId)
            return forward(request, "roomAvailTimePrompt");
        // check room
        boolean isPublicRoom = false;
        Classroom room = roomService.getClassroom(roomId);
        for (Iterator iter = room.getDepartments().iterator(); iter.hasNext();) {
            Department element = (Department) iter.next();
            if (element.getId().equals(Department.SCHOOLID)) {
                isPublicRoom = true;
            }
        }
        if (!isPublicRoom) {
            ActionForward errorForward = checkRoom(mapping, request, room);
            if (null != errorForward) {
                return errorForward;
            }
        }
        AvailableTime time = room.getAvailableTime();
        if (null == time || null == time.getAvailable()) {
            time = new AvailableTime(AvailableTime.commonRoomAvailTime);
            logHelper.info(request, "Create default availTime for room id:" + room.getId());
            availTimeService.saveRoomAvailTime(roomId, time);
        }
        prepareTimeInfo(request, time);
        addEntity(request, room);
        return forward(request);
    }
    
    /**
     * 教师可用时间信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward teacherAvailTimeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long teacherId = getLong(request, "teacher.id");
        if (null == teacherId) {
            return forward(request, "teacherAvailTimePrompt");
        }
        Teacher teacher = teacherService.getTeacherById(teacherId);
        AvailableTime time = teacher.getAvailableTime();
        if (null == time) {
            time = new AvailableTime(AvailableTime.commonTeacherAvailTime);
            logHelper.info(request, "Create default availTime for teacher id:" + teacher.getId());
            try {
                availTimeService.saveTeacherAvailTime(teacherId, time);
            } catch (Exception e) {
                logHelper.info(request, "Failure Create default availTime for teacher id:", e);
                return forward(mapping, request, "error.model.id.collision", "error");
            }
        }
        prepareTimeInfo(request, time);
        request.setAttribute("teacher", teacher);
        return forward(request);
    }
    
    /**
     * 准备时间信息
     * 
     * @param request
     * @param time
     */
    private void prepareTimeInfo(HttpServletRequest request, AvailableTime time) {
        request.setAttribute("weekList", WeekInfo.WEEKS);
        request.setAttribute("availTime", time);
        request.setAttribute("unitList", timeSettingService.getDefaultSetting().getCourseUnits());
    }
    
    /**
     * 修改教师可用时间
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editTeacherAvailTime(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long teacherId = getLong(request, "teacher.id");
        if (null == teacherId)
            return forward(mapping, request, "prompt");
        Teacher teacher = teacherService.getTeacherById(teacherId);
        ActionForward errorForward = checkTeacher(mapping, request, teacher);
        if (null != errorForward)
            return errorForward;
        AvailableTime time = availTimeService.getTeacherAvailTime(teacherId);
        if (null == time) {
            time = new AvailableTime(AvailableTime.commonTeacherAvailTime);
            availTimeService.saveTeacherAvailTime(teacherId, time);
        }
        
        prepareTimeInfo(request, time);
        request.setAttribute("teacher", teacher);
        return forward(request);
    }
    
    /**
     * 修改教室可用时间
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editRoomAvailTime(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long roomId = getLong(request, "classroom.id");
        if (null == roomId)
            return forward(mapping, request, "prompt");
        // check room
        Classroom room = roomService.getClassroom(roomId);
        ActionForward errorForward = checkRoom(mapping, request, room);
        if (null != errorForward)
            return errorForward;
        AvailableTime time = availTimeService.getRoomAvailTime(roomId);
        if (null == time) {
            time = new AvailableTime(AvailableTime.commonRoomAvailTime);
            availTimeService.saveRoomAvailTime(roomId, time);
        }
        prepareTimeInfo(request, time);
        addEntity(request, room);
        return forward(request);
    }
    
    /**
     * 保存教室可用时间设置
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveRoomAvailTime(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long roomId = getLong(request, "classroom.id");
        if (null == roomId)
            return forward(mapping, request, new String[] { "entity.classroom",
                    "error.model.id.needed" }, "error");
        // check room
        Classroom room = roomService.getClassroom(roomId);
        ActionForward errorForward = checkRoom(mapping, request, room);
        if (null != errorForward)
            return errorForward;
        logHelper.info(request, "Update avaliTime of room with id:" + room.getId());
        saveAvailTime(mapping, request);
        return redirect(request, "roomAvailTimeInfo", "info.save.success", "&classroom.id="
                + roomId);
        
    }
    
    /**
     * 保存教师可用时间设置
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveTeacherAvailTime(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // check teacher
        Long teacherId = getLong(request, "teacher.id");
        if (null == teacherId)
            return forward(mapping, request, new String[] { "entity.teacher",
                    "error.model.id.needed" }, "error");
        Teacher teacher = teacherService.getTeacherById(teacherId);
        ActionForward errorForward = checkTeacher(mapping, request, teacher);
        if (null != errorForward)
            return errorForward;
        // log
        logHelper.info(request, "Update avaliTime of teacher with id:" + teacher.getId());
        saveAvailTime(mapping, request);
        return redirect(request, "teacherAvailTimeInfo", "info.save.success", "&teacher.id="
                + teacherId);
    }
    
    private void saveAvailTime(ActionMapping mapping, HttpServletRequest request) throws Exception {
        AvailableTime time = new AvailableTime(get(request, "availTime"));
        // 备注一百字
        time.setRemark(StringUtils.abbreviate(get(request, "availTime.remark"), 50));
        time.setId(getLong(request, "availTime.id"));
        if (!time.isValid())
            throw new Exception("availTime.not.corrected");
        availTimeService.updateAvailTime(time);
    }
    
    /**
     * 检查教师是否在权限范围内
     * 
     * @param mapping
     * @param request
     * @param teacher
     * @return
     */
    private ActionForward checkTeacher(ActionMapping mapping, HttpServletRequest request,
            Teacher teacher) {
        try {
            teacher.getName();
        } catch (Exception e) {
            return forward(mapping, request, new String[] { "entity.teacher",
                    "error.model.notExist" }, "error");
        }
        String departDataRealm = getDepartmentIdSeq(request);
        if (StringUtils.isEmpty(departDataRealm))
            return forward(mapping, request, "error.depart.dataRealm.notExists", "error");
        if (!DataAuthorityUtil.isInDataRealm("Teacher", teacher, "", departDataRealm))
            return forward(mapping, request, "error.dataRealm.insufficient", "error");
        return null;
    }
    
    private ActionForward checkRoom(ActionMapping mapping, HttpServletRequest request,
            Classroom room) {
        try {
            room.getName();
        } catch (Exception e) {
            return forward(mapping, request, new String[] { "entity.classroom",
                    "error.model.notExist" }, "error");
        }
        // check room
        String departDataRealm = getDepartmentIdSeq(request);
        if (StringUtils.isEmpty(departDataRealm))
            return forward(mapping, request, "error.depart.dataRealm.notExists", "error");
        if (!DataAuthorityUtil.isInDataRealm("Classroom", room, "", departDataRealm))
            return forward(mapping, request, "error.dataRealm.insufficient", "error");
        return null;
    }
    
    
    public void setAvailTimeService(AvailTimeService availTimeService) {
        this.availTimeService = availTimeService;
    }

    /**
     * @param roomService
     *            The roomService to set.
     */
    public void setRoomService(ClassroomService roomService) {
        this.roomService = roomService;
    }
    
    /**
     * @param teacherService
     *            The teacherService to set.
     */
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    
    /**
     * @param departmentService
     *            The departmentService to set.
     */
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    
    public void setTimeSettingService(TimeSettingService timeSettingService) {
        this.timeSettingService = timeSettingService;
    }
    
}
