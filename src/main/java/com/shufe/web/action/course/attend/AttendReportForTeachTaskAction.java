package com.shufe.web.action.course.attend;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.Constants;
import com.shufe.model.course.attend.AttendStatic;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.course.task.TeachTaskParam;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;
import com.shufe.web.helper.TeachTaskSearchHelper;

public class AttendReportForTeachTaskAction extends CalendarRestrictionSupportAction{
	protected TeachTaskService teachTaskService;    
    protected TeachTaskSearchHelper teachTaskSearchHelper;
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }    
    public void setTeachTaskSearchHelper(TeachTaskSearchHelper teachTaskSearchHelper) {
        this.teachTaskSearchHelper = teachTaskSearchHelper;
    }
	
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        TeachCalendar teachCalendar = (TeachCalendar) request.getAttribute(Constants.CALENDAR);
        EntityQuery query = new EntityQuery(TeachTaskParam.class, "teachTaskParam");
        String order = "Y";
        if (teachCalendar != null) {
            query.add(new Condition("teachTaskParam.calendar.id=" + teachCalendar.getId()));
        }
        List manualArrangeParamList = (List) utilService.search(query);
        Set users = getUser(request.getSession()).getRoles();
        for (Iterator iter = users.iterator(); iter.hasNext();) {
            EamsRole eamsRole = (EamsRole) iter.next();
            if (manualArrangeParamList.size() == 0 && eamsRole.getId().longValue() != 1L) {
                order = "N";
            } else {
                for (Iterator iter_ = manualArrangeParamList.iterator(); iter_.hasNext();) {
                    TeachTaskParam param = (TeachTaskParam) iter_.next();
                    Date dateNow = new Date(System.currentTimeMillis());
                    Date dateStart = param.getStartDate();
                    Date dateFinsh = param.getFinishDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dateFinsh);
                    cal.add(Calendar.DAY_OF_YEAR, +1);
                    if ((dateNow.before(dateStart) || dateNow.after(cal.getTime()) || param.getIsOpenElection().equals(
                            Boolean.valueOf(false)))
                            && eamsRole.getId().longValue() != 1L) {
                        order = "N";
                    }
                }
            }
        }
        addSingleParameter(request, "order", order);
        // 获得开课院系和上课教师列表
        String stdTypeDataRealm = getStdTypeIdSeq(request);
        String departDataRealm = getDepartmentIdSeq(request);
        if (request.getAttribute(Constants.CALENDAR) != null) {
            List departList = teachTaskService.getDepartsOfTask(stdTypeDataRealm, departDataRealm,
                    (TeachCalendar) request.getAttribute(Constants.CALENDAR));
            addCollection(request, "courseTypes", teachTaskService.getCourseTypesOfTask(
                    stdTypeDataRealm, departDataRealm,
                    (TeachCalendar) request.getAttribute(Constants.CALENDAR)));
            addCollection(request, "teachDepartList", teachTaskService.getTeachDepartsOfTask(
                    stdTypeDataRealm, departDataRealm,
                    (TeachCalendar) request.getAttribute(Constants.CALENDAR)));
            
            addCollection(request, Constants.DEPARTMENT_LIST, departList);
            addCollection(request, "weeks", WeekInfo.WEEKS);
        }
        
        initBaseCodes(request, "schoolDistricts", SchoolDistrict.class);
        /*----------------加载上课学生性别列表--------------------*/
        initBaseCodes(request, "genderList", Gender.class);
        initBaseCodes(request, "courseCategoryList", CourseCategory.class);
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "tasks", teachTaskSearchHelper.searchTask(request));
        String order = request.getParameter("order");
        addSingleParameter(request, "order", order);
        return forward(request);
    }
    
    public ActionForward setReportDate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String taskIds = get(request, "taskIds");
        if (StringUtils.isEmpty(taskIds)) {
            return forward(mapping, request, "error.teachTask.id.needed", "error");
        }
        request.setAttribute("taskIds",taskIds);        
    	return forward(request);
    }
    
	public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String taskIds = get(request, "taskIds");
		if (StringUtils.isEmpty(taskIds)) {
			return forward(mapping, request, "error.teachTask.id.needed", "error");
		}
		String startStr=request.getParameter("startDate").trim();
		String endStr=request.getParameter("endDate").trim();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date startDate=dateFormat.parse(startStr);	
        java.util.Date endDate=dateFormat.parse(endStr);
        EntityQuery query = new EntityQuery(AttendStatic.class, "d");
        StringBuffer sql=new StringBuffer();
        sql.append("select d.student.id,");         
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(endDate);
        cal2.add(Calendar.DAY_OF_YEAR, 1);
        List dateList=new ArrayList();
        if (!cal.getTime().before(cal2.getTime())) {
        	return forward(mapping, request, "error.date.finishBeforeStart", "error");
        }
        while (cal.getTime().before(cal2.getTime())) {
        	sql.append("sum(case when to_char(d.attenddate,'yyyy-MM-dd')='"+dateFormat.format(cal.getTime())+"' and d.attendtype='2' then 1 else 0 end),");
        	sql.append("sum(case when to_char(d.attenddate,'yyyy-MM-dd')='"+dateFormat.format(cal.getTime())+"' and d.attendtype='3' then 1 else 0 end),"); 
         	sql.append("sum((case when to_char(d.attenddate,'yyyy-MM-dd')='"+dateFormat.format(cal.getTime())+"' and d.attendtype='2' then 1 else 0 end)*ks)");
         	dateList.add(dateFormat.format(cal.getTime()));
         	cal.add(Calendar.DAY_OF_YEAR, 1);
         	if (cal.getTime().before(cal2.getTime())) {
         		sql.append(",");
         	}
        }
        query.add(new Condition("d.task.id in("+taskIds+")"));
        query.setSelect(sql.toString());
        query.groupBy("cube(d.student.id)");        
        List list=new ArrayList(utilService.search(query));
        if (!list.isEmpty()) {	
        	int num=0;
        	for (int i = 0; i < list.size(); i++) {
        		Object[] object =(Object[])list.get(i);
				if (object[0]==null) {
					object[0]="总计（教学任务）";
					num=i;
				}else {
					object[0]=utilService.get(Student.class,(Long)object[0]);
				}
        	}
        	if (num!=list.size()-1) {
        		Object[] objectLast=new Object[dateList.size()*3+1];
	 			Object[] objectnum=new Object[dateList.size()*3+1];
	 			objectLast=(Object[])list.get(list.size()-1);
	 			objectnum=(Object[])list.get(num);
	 			list.set(list.size()-1, objectnum);
	 			list.set(num, objectLast);
        	}
        	request.setAttribute("dlist", list);
        	request.setAttribute("dateList", dateList);
        }         
        request.setAttribute("task", utilService.get(TeachTask.class,Long.parseLong(taskIds)));
        return forward(request);
	}
	
}
