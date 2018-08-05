package com.shufe.web.action.course.attend;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.baseinfo.Department;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.security.User;
import com.shufe.model.course.attend.AttendStatic;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.attend.AttendStaticService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 辅导员考勤统计报表
 * 
 * @author SongXiangwen
 * 
 */
public class AttendReportCoaAction extends CalendarRestrictionSupportAction {
    
    private AttendStaticService attendStaticService;
    
    private TeachTaskService teachTaskService;
    
    public void setAttendStaticService(AttendStaticService attendStaticService) {
        this.attendStaticService = attendStaticService;
    }
    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        initBaseInfos(request, "departments", Department.class);
        initBaseInfos(request, "stdTypeList", StudentType.class);
        setCalendarDataRealm(request, hasStdTypeCollege);
        return forward(request);
        
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Teacher teacher = getTeacherFromSession(request.getSession());
        if (teacher != null) {
            // 查找当前辅导员所带的学生
            EntityQuery stuquery = new EntityQuery(Student.class, "student");
            populateConditions(request, stuquery);
            List<Condition> conditions = stuquery.getConditions();
            Condition con=null;
            for (Condition condition : conditions) {
    			if (condition.getContent().indexOf("student.type.id")!=-1 && condition.getValues().contains(2L)) {
    				con=condition;
    				break;
    			}
    		}
            conditions.remove(con);            
            stuquery.join("student.adminClasses", "adminClass");
            stuquery.add(new Condition("adminClass.instructor = :instructor", teacher));
            stuquery.setLimit(getPageLimit(request));
            stuquery.addOrder(OrderUtils.parser(get(request, "orderBy")));
            addCollection(request, "students", utilService.search(stuquery));
        }else {
        	addCollection(request, "students", new ArrayList());
		}
        
        return forward(request);
    }
    
    // 总缺勤
    Integer absenseTotal = 0;
    
    // 总迟到
    Integer lateTotal = 0;
    
    /**
     * 制作学生考勤报表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward showReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendarId"));
        
        Collection<Student> students = utilService.load(Student.class, "id",
                SeqStringUtil.transformToLong(get(request, "stuIds")));
        
        Map<String, Map<String, Object>> statMap = new HashMap<String, Map<String, Object>>();
        Date earlyAt = new Date();
        for (Student student : students) {
            Map<String, Object> studentMap = new HashMap<String, Object>();
            studentMap.put("student", student);
            studentMap.put("coursesMap", new HashMap<String, Object>());
            if (student.getStudentStatusInfo().getEnrollDate().before(earlyAt)) {
                earlyAt = student.getStudentStatusInfo().getEnrollDate();
            }
            statMap.put(student.getCode(), studentMap);
        }
        
        EntityQuery query = new EntityQuery(AttendStatic.class, "attend");
        query.add(new Condition("attend.calendar = :calendar", calendar));
        query.add(new Condition("attend.student in (:students)", students));
        query.addOrder(OrderUtils
                .parser("attendtype.student.name, attend.task.course.name, attend.attenddate, attenddate.attendtype"));
        Collection<AttendStatic> attends = utilService.search(query);
        
        for (AttendStatic attend : attends) {
            Map<String, Object> studentMap = statMap.get(attend.getStudent().getCode());
            Map<String, Object> coursesMap = (Map<String, Object>) studentMap.get("coursesMap");
            Map<String, Object> courseMap = (Map<String, Object>) coursesMap.get(attend.getTask()
                    .getCourse().getCode());
            if (null == courseMap) {
                courseMap = new HashMap<String, Object>();
                courseMap.put("course", attend.getTask().getCourse());
                courseMap.put("attends", new ArrayList<AttendStatic>());
                coursesMap.put(attend.getTask().getCourse().getCode(), courseMap);
            }
            Collection<AttendStatic> iAttends = (Collection<AttendStatic>) courseMap.get("attends");
            iAttends.add(attend);
        }
        
        request.setAttribute("calendar", calendar);
        request.setAttribute("statMap", statMap);
        return forward(request);
    }
    
    public void addMonthBean(Long userId, Integer i, Long taskid, List<MonthBean> monthBeanSet) {
        MonthBean monthBean = new MonthBean();
        monthBean.setMonth(i);
        String absenceCountHql = "select count(*) from AttendStatic dr where dr.task= " + taskid + " and to_char(dr.attenddate,'MM')=" + i + " and dr.attendtype='2'  and dr.student.id=" + userId;
        List absenceList = utilService.searchHQLQuery(absenceCountHql);
        Long count = (Long) absenceList.get(0);
        Integer countInt = Integer.parseInt(count.toString());
        absenseTotal += countInt;
        monthBean.setAbsenceCount(countInt);
        String lateCountHql = "select count(*) from AttendStatic dr where dr.task= " + taskid + " and to_char(dr.attenddate,'MM')=" + i + " and dr.attendtype='3'  and dr.student.id=" + userId;
        List lateList = utilService.searchHQLQuery(lateCountHql);
        Long count1 = (Long) lateList.get(0);
        Integer countInt1 = Integer.parseInt(count1.toString());
        lateTotal += countInt1;
        monthBean.setLatCounet(countInt1);
        monthBeanSet.add(monthBean);
    }
    
    /**
     * 显示学生考勤明细
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward showDetailList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.index(mapping, form, request, response);
    }
    
    public void setCalendarDataRealm(HttpServletRequest request, int realmScope) throws Exception {
        setDataRealm(request, realmScope);
        setCalendar(request, null);
        Object obj = getAttribute(request, "studentType");
        if (obj == null || StringUtils.isEmpty(obj.toString())) {
            return;
        }
        StudentType stdType = (StudentType) obj;
        // 与教学日历相关的子类学生类别
        addSingleParameter(request, CALENDAR_STDTYPES_KEY, // 该值为calendarStdTypes
                getCalendarStdTypesOf(stdType.getId(), request));
    }
    
    public ActionForward showDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        EntityQuery query = new EntityQuery(AttendStatic.class, "attendStatic");
        populateConditions(request, query);
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
        User user = getUser(request.getSession());
        Collection<Teacher> teachers = utilService.load(Teacher.class, "code", user.getName());
        if (CollectionUtils.isEmpty(teachers)) {
            query.add(new Condition("attendStatic is null"));
        } else {
        	query.join("attendStatic.student.adminClasses","ac");
        	query.add(new Condition("ac.instructor=:instructor",teachers.iterator().next()));
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
    
    public String changeToString(Integer month) {
        String month_str = month.toString();
        if (month_str.length() < 2) {
            month_str = "0" + month_str;
        }
        return month_str;
    }
    
    public ActionForward resumption(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Collection<AttendStatic> attends = utilService.load(AttendStatic.class, "id",
                SeqStringUtil.transformToLong(get(request, "attendStaticIds")));
        
        //Date nowAt = new Date();
        //String attendtime = new SimpleDateFormat("HH:mm:ss").format(nowAt);
        for (AttendStatic attend: attends) {
            //attend.setAttenddate(nowAt);
            //attend.setAttendtime(attendtime);
            attend.setAttendtype("5");
        }
        utilService.saveOrUpdate(attends);
        return redirect(request, "showDetailList", "info.action.success");
    }
}
