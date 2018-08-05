package com.shufe.web.action.course.attend;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.eams.system.baseinfo.Department;
import com.ekingstar.eams.system.baseinfo.model.StudentType;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.attend.AttendStatic;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.attend.AttendStaticImportListener;
import com.shufe.service.course.attend.AttendStaticService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 考勤统计
 * 
 * @author SongXiangwen
 * 
 */
public class AttendStaticAction extends CalendarRestrictionSupportAction {
    
    private AttendStaticService attendStaticService;
    
    public void setAttendStaticService(AttendStaticService attendStaticService) {
        this.attendStaticService = attendStaticService;
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(AttendStatic.class, "attendStatic");
        populateConditions(request, query);
        List<Condition> conditions = query.getConditions();
        Condition con=null;
        for (Condition condition : conditions) {
			if (condition.getContent().indexOf("attendStatic.student.type.id")!=-1 && condition.getValues().contains(2L)) {
				con=condition;
				break;
			}
		}
        conditions.remove(con);
        String adminClassName = get(request, "adminClass.name");
        if (StringUtils.isNotBlank(adminClassName)) {
            query.add(new Condition(
                    "exists (from attendStatic.student.adminClasses adminClass where adminClass.name like :adminClassName)",
                    "%" + adminClassName + "%"));
        }
        String teacherName = get(request, "teacher.name");
        if (StringUtils.isNotBlank(teacherName)) {
            query.add(new Condition(
                    "exists (from attendStatic.task task where exists (from task.arrangeInfo.teachers teacher where teacher.code like :teacherName or teacher.name like :teacherName))",
                    "%" + teacherName + "%"));
        }
        String attenddate = get(request, "attenddate");
	 	if (StringUtils.isNotBlank(attenddate)) {
	 		query.add(new Condition("instr(to_char(attendStatic.attenddate, :geshi),'"+attenddate+"',1) <> 0","yyyy-MM-dd HH24:mi:ss"));
	 	}
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "attendStatics", utilService.search(query));
        return forward(request);
    }
    
    public ActionForward stuSeach(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        return forward(request);
    }
    
    /**
     * 保存
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
        AttendStatic attendStatic = (AttendStatic) populateEntity(request, AttendStatic.class,
                "attendStatic");
        if (attendStatic.getId() == null) {
            TeachCalendar calendar = new TeachCalendar();
            List calslist = teachCalendarService.getCurTeachCalendars();
            if (calslist.size() > 0) {
                calendar = (TeachCalendar) calslist.get(0);
            } else {
                StudentType studentType = (StudentType) utilService.get(StudentType.class,
                        new Long(2));
                calendar = teachCalendarService.getNearestCalendar(studentType);
            }
            Long getTakeId = getLong(request, "takeid");
            CourseTake courseTake = (CourseTake) utilService.load(CourseTake.class, getTakeId);
            attendStatic.setCourse(courseTake.getTask().getCourse());
            attendStatic.setDepartment(courseTake.getStudent().getDepartment());
            attendStatic.setStudent(courseTake.getStudent());
            attendStatic.setTask(courseTake.getTask());
            attendStatic.setCalendar(calendar);
        }
        utilService.saveOrUpdate(attendStatic);
        return redirect(request, "search", "info.save.success");
    }
    
    /**
     * 删除
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
        Long[] attendStaticIds = SeqStringUtil.transformToLong(get(request, "attendStaticIds"));
        for (Long id : attendStaticIds) {
            utilService.remove(utilService.load(AttendStatic.class, id));
        }
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 添加考勤信息，对教学任务进行编辑
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
        String stuCode = request.getParameter("studentCode");
        EntityQuery entityQuery = new EntityQuery(Student.class, "student");
        entityQuery.add(new Condition("student.code=(:code)", stuCode));
        List<Student> stuList = (List<Student>) utilService.search(entityQuery);
        if (stuList.size() > 0) {
            request.setAttribute("student", stuList.get(0));
            // 根绝学生查找对应的教学日历
            List<CourseTake> takes = getCourseTakes(request, response, stuList.get(0).getCode());
            addCollection(request, "takes", takes);
        } else {
            addSingleParameter(request, "isExist", "没有对应的学生,请重新输入");
            return forward(request, "stuSeach");
        }
        return forward(request);
    }
    
    public ActionForward editSingle(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long attendStaticId = getLong(request, "attendStatic.id");
        addEntity(request, "attendStatic", utilService.load(AttendStatic.class, attendStaticId));
        return forward(request);
    }
    
    /**
     * 导入考勤明细
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
        Transfer transfer = ImporterServletSupport.buildEntityImporter(request, AttendStatic.class,
                tr);
        if (null == transfer) {
            return forward(request, "/pages/components/importData/error");
        }
        transfer.addListener(new ImporterForeignerListener(utilService)).addListener(
                new AttendStaticImportListener(utilService.getUtilDao(), attendStaticService,
                        teachCalendarService));
        transfer.transfer(tr);
        if (tr.hasErrors()) {
            return forward(request, "/pages/components/importData/error");
        } else {
            return redirect(request, "search", "info.import.success",
                    "gradeState.confirmGA&calendar.id");
        }
    }
    
    /**
     * 单个销假
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateAttendtype(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long attendStaticId = getLong(request, "attendStatic.id");
        AttendStatic attendStatic = (AttendStatic) utilService.load(AttendStatic.class,
                attendStaticId);
        attendStatic.setAttendtype("5");// 将缺勤改为请假
        utilService.saveOrUpdate(attendStatic);
        return redirect(request, "search", "销假成功");
        
    }
    
    /**
     * 批量销假
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] attendStaticIds = SeqStringUtil.transformToLong(get(request, "attendStaticIds"));
        for (Long id : attendStaticIds) {
            AttendStatic attendStatic = (AttendStatic) utilService.load(AttendStatic.class, id);
            attendStatic.setAttendtype("5");// 将缺勤改为请假
            utilService.saveOrUpdate(attendStatic);
        }
        
        return redirect(request, "search", "批量销假成功");
    }
    
    public List<CourseTake> getCourseTakes(HttpServletRequest request,
            HttpServletResponse response, String stdNo) {
        TeachCalendar calendar = new TeachCalendar();
        List calslist = teachCalendarService.getCurTeachCalendars();
        if (calslist.size() > 0) {
            calendar = (TeachCalendar) calslist.get(0);
        } else {
            StudentType studentType = (StudentType) utilService.get(StudentType.class, new Long(2));
            calendar = teachCalendarService.getNearestCalendar(studentType);
        }
        EntityQuery query = new EntityQuery(CourseTake.class, "courseTake");
        // query.setSelect("task.course,task.courseType,task.teachClass,task.arrangeInfo");
        query.join("courseTake.task", "task");
        query.join("courseTake.student", "student");
        query.add(new Condition("student.code=(:code)", stdNo));
        query.add(new Condition("task.calendar.id=(:calendarId)", calendar.getId()));
        List<CourseTake> list = (List<CourseTake>) utilService.search(query);
        return list;
    }
    
}
