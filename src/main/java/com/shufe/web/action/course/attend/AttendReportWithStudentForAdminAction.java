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
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.attend.AttendStatic;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
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
public class AttendReportWithStudentForAdminAction extends CalendarRestrictionSupportAction {
    
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
            stuquery.add(new Condition("student.inSchool=1 and student.active=1 "));
            stuquery.add(new Condition("student.department in (:depart)",getDeparts(request)));
//            stuquery.join("student.adminClasses", "adminClass");
//            stuquery.add(new Condition("adminClass.instructor = :instructor", teacher));
            stuquery.setLimit(getPageLimit(request));
            stuquery.addOrder(OrderUtils.parser(get(request, "orderBy")));
            addCollection(request, "students", utilService.search(stuquery));
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
    	
    	//获取需要统计的院系下的所有行政班
    	Long stuId= getLong(request,"stuIds");
    	EntityQuery stuquery = new EntityQuery(Student.class, "student");
    	stuquery.add(new Condition("student.id =:id",stuId));
    	Student student=((List<Student>)utilService.search(stuquery)).get(0);
    	
    	String courseHql="select  courseTake  from CourseTake courseTake  " +
				" where courseTake.student.id= "+stuId;
    	
    	List<CourseTake> stuCourses=(List<CourseTake>)utilService.searchHQLQuery(courseHql);
    	
    	
    	HashMap<String, Integer> absenseDateMap=new HashMap<String, Integer>();//按天统计的所有缺勤数
    	HashMap<String, Integer> lateDateMap=new HashMap<String, Integer>();//按天统计的所有迟到数
    	HashMap<String, Integer> ksDateMap=new HashMap<String, Integer>();//按天统计的所有课时数
    	
    	List<CourseTakeBean> courseTakeBeans=new ArrayList<CourseTakeBean>();
    	for (CourseTake courseTake : stuCourses) {
    		Integer absenseTotal=0;//总缺勤数
    		Integer lateTotal=0;//总迟到数
    		Integer ksTotal=0;//总迟到数
    		
    		List<DateBean> dateBeans=new ArrayList<DateBean>();
			for (String  date : dateList) {
				addDateBean(stuId,date,courseTake,absenseTotal,lateTotal,ksTotal,dateBeans);
				
				//统计每天的合计
				if(absenseDateMap.get(date)==null){
					absenseDateMap.put(date, absenseTotal);
					lateDateMap.put(date, lateTotal);
					ksDateMap.put(date, ksTotal*absenseTotal); 
				}else{
					Integer cc1=absenseDateMap.get(date);
					absenseDateMap.put(date, cc1+absenseTotal);
					Integer cc2=lateDateMap.get(date);
					lateDateMap.put(date, cc2+lateTotal);
					Integer cc3=ksDateMap.get(date);
					ksDateMap.put(date, cc3+ksTotal*absenseTotal);
				}
			}
			
			Integer absenseAll=0;
			absenseAll+=absenseTotal;
			Integer lateAll=0;
			lateAll+=lateTotal;
			Integer ksAll=0;
			ksAll+=ksTotal;
			absenseDateMap.put("total", absenseAll);
			lateDateMap.put("total", lateAll);
			ksDateMap.put("total", ksAll*absenseAll);
			
			CourseTakeBean courseTakeBean=new CourseTakeBean(courseTake, dateBeans, absenseTotal,lateTotal,ksTotal*absenseTotal);
			courseTakeBeans.add(courseTakeBean);
		}
    	
    	
    	 addCollection(request, "courseTakeBeans", courseTakeBeans);
    	 addCollection(request, "dateList", dateList);
    	 addCollection(request, "stuCourses", stuCourses);
    	 request.setAttribute("absenseDateMap", absenseDateMap);
    	 request.setAttribute("lateDateMap", absenseDateMap);
    	 request.setAttribute("ksDateMap", ksDateMap);
    	 request.setAttribute("student", student);
    	
        return forward(request);
    }
    
    
    public void addDateBean( Long stuId,String date,CourseTake courseTake,Integer absenseTotal,Integer lateTotal,Integer ksTotal,List<DateBean> dateBeans){
		DateBean dateBean=new DateBean();
		String absenceCountHql="select count(*) from AttendStatic dr  " +
				" where dr.student.id= "+stuId+
				"  and dr.task.id ="+courseTake.getTask().getId()+" and to_char(dr.attenddate,'yyyy-MM-dd')='"+date+"' and dr.attendtype='2' ";
		List absenceList=utilService.searchHQLQuery(absenceCountHql);
		Long  count=(Long) absenceList.get(0);
		Integer countInt=Integer.parseInt(count.toString());
		absenseTotal+=countInt;
		dateBean.setAbsenceCount(countInt);
		
		String lateCountHql="select count(*) from AttendStatic dr  " +
				" where dr.student.id= "+stuId+
				" and dr.task.id ="+courseTake.getTask().getId()+" and to_char(dr.attenddate,'yyyy-MM-dd')='"+date+"' and dr.attendtype='3' ";
		List lateList=utilService.searchHQLQuery(lateCountHql);
		Long  count1=(Long) lateList.get(0);
		Integer countInt1=Integer.parseInt(count1.toString());
		lateTotal+=countInt1;
		dateBean.setLatCounet(countInt1);
		
		String ksCountHql="select sum(dr.ks) from AttendStatic dr  " +
				" where dr.student.id= "+stuId+
				" and dr.task.id ="+courseTake.getTask().getId()+" and to_char(dr.attenddate,'yyyy-MM-dd')='"+date+"'";
		List ksList=utilService.searchHQLQuery(ksCountHql);
		Long  count2=(Long) ksList.get(0);
		Integer countInt2=0;
		if(count2!=null){
			countInt2=Integer.parseInt(count2.toString());
		}
		ksTotal+=countInt2;
		dateBean.setKsCount(countInt2*countInt);
		
		dateBeans.add(dateBean);
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
    	String adminClassIds = get(request, "stuIds");
        if (StringUtils.isEmpty(adminClassIds)) {
            return forward(mapping, request, "error.model.id.needed", "error");
        }
        request.setAttribute("taskIds",adminClassIds);        
    	return forward(request);
    }
    
}
