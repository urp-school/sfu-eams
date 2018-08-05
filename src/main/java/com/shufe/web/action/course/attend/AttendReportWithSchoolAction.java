package com.shufe.web.action.course.attend;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
 * 考勤统计报表(全校)
 * 
 * @author SongXiangwen
 * 
 */
public class AttendReportWithSchoolAction extends CalendarRestrictionSupportAction {
    
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
    	EntityQuery deptquery = new EntityQuery(Department.class, "department");
    	deptquery.add(new Condition("department.isCollege = 1"));
    	deptquery.addOrder(OrderUtils.parser(get(request, "orderBy")));
    	addCollection(request, "departments", utilService.search(deptquery));
    	
        return forward(request);
    }
    
    
    public ActionForward showReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String startDatestr= get(request, "startDate");
    	String endDatestr=get(request, "endDate");
    	Calendar startCalendar=Calendar.getInstance();
    	Calendar endCalendar=Calendar.getInstance();
    	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
    	Date startDate=sf.parse(startDatestr);
    	startCalendar.setTime(startDate);
    	Date endDate=sf.parse(endDatestr);
    	endCalendar.setTime(endDate);
    	//获取日期集合
    	List<String> dateList=new ArrayList<String>();
    	while(true){
           
            if(startCalendar.getTimeInMillis() <= endCalendar.getTimeInMillis()){//TODO 转数组或是集合，楼主看着写吧
            	dateList.add(sf.format(startCalendar.getTime()));
        }else{
            break;
        }
            startCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    	//查询所有是院系的department
    	EntityQuery deptquery = new EntityQuery(Department.class, "department");
    	deptquery.add(new Condition("department.isCollege = 1"));
    	deptquery.addOrder(OrderUtils.parser(get(request, "orderBy")));
    	List<Department> departments=(List<Department>) utilService.search(deptquery);
    	
    	HashMap<String, Integer> totalMap=new HashMap<String, Integer>();//所有考勤数
    	HashMap<String, Integer> normalTotalMap=new HashMap<String, Integer>();//所有出勤数
    	
    	List<DepartmentBean> departmentBeans=new ArrayList<DepartmentBean>();
    	for (Department department : departments) {
    		Integer totalByDept=0;//总考勤数
    		Integer normalTotalByDept=0;//总出勤数
    		
    		List<DateBean> dateBeans=new ArrayList<DateBean>();
			for (String  date : dateList) {
				String absenceCountHql="select count(*) from AttendStatic dr  " +
						" where dr.department.id= "+department.getId() +
						" and to_char(dr.attenddate,'yyyy-MM-dd')='"+date+"' and dr.attendtype='1' ";
				List normalList=utilService.searchHQLQuery(absenceCountHql);
				Long  count=(Long) normalList.get(0);
				Integer countInt=Integer.parseInt(count.toString());
				Integer totalByDate=getTotalPrecentByDeptDate(department,date);//获取所有考勤数
				Float percent=0f;
				if(totalByDate>0){
					percent=(countInt*10000/totalByDate)/100f;
				}		
				dateBeans.add(new DateBean(date,percent));
				totalByDept+=totalByDate;//所有考勤数
				normalTotalByDept+=countInt;//所有出勤数
				//统计每天的合计
				if(totalMap.get(date)==null){
					totalMap.put(date, totalByDate);
					normalTotalMap.put(date, countInt);
				}else{
					Integer cc1=totalMap.get(date);
					totalMap.put(date, cc1+totalByDate);
					Integer cc2=normalTotalMap.get(date);
					normalTotalMap.put(date, cc2+countInt);
				}
			}
			Float percentBydept=0f;
			if(totalByDept>0)
				percentBydept=(normalTotalByDept*10000/totalByDept)/100f;
			
			DepartmentBean departmentBean=new DepartmentBean(department, dateBeans, percentBydept);
			departmentBeans.add(departmentBean);
		}
    	
    	HashMap<String, Float> normalMap=new HashMap<String, Float>();//统计出勤率
    	Integer total=0;
		Integer normalTotal=0;
    	for (String  date : dateList) {
    		Float normal=0f;
    		if(totalMap.get(date)>0){
    			normal=(normalTotalMap.get(date)*10000/totalMap.get(date))/100f;
    		}
    		total+=totalMap.get(date);
    		normalTotal+=normalTotalMap.get(date);
    		normalMap.put(date, normal);
    	}
    	if(total>0){
    		normalMap.put("total", (normalTotal*10000/total)/100f);
    	}else{
    		normalMap.put("total", 0f);
    	}
    	
    	 addCollection(request, "departmentBeans", departmentBeans);
    	 addCollection(request, "dateList", dateList);
    	 addCollection(request, "departments", departments);
    	 request.setAttribute("dateNormalMap", normalMap);
    	 
        return forward(request);
    }
    
    public Integer getTotalPrecentByDeptDate(Department department,String date) {
    	String absenceCountHql="select count(*) from AttendStatic dr  " +
				" where dr.department.id= "+department.getId() +
				" and to_char(dr.attenddate,'yyyy-MM-dd')='"+date+"'";
		List normalList=utilService.searchHQLQuery(absenceCountHql);
		Long  count=(Long) normalList.get(0);
		return Integer.parseInt(count.toString());
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
    
    public ActionForward setReportDate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	return forward(request);
    }
    
}
