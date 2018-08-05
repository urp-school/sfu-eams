package com.shufe.web.action.course.attend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.print.attribute.standard.Finishings;
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
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.Department;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.WeekInfo;
import com.ekingstar.security.Resource;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.model.Constants;
import com.shufe.model.course.attend.AttendStatic;
import com.shufe.model.course.attend.AttendStaticReport;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.attend.AttendStaticImportListener;
import com.shufe.service.course.attend.AttendStaticService;
import com.shufe.service.course.grade.GradeImportListener;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;
import com.shufe.web.helper.BaseInfoSearchHelper;
import com.shufe.web.helper.RestrictionHelper;
import com.shufe.web.helper.TeachTaskSearchHelper;

/**
 * 教师考勤统计报表
 * @author SongXiangwen
 *
 */
public class AttendReportFuncAction extends CalendarRestrictionSupportAction{
	private AttendStaticService attendStaticService;
	
	 protected TeachTaskSearchHelper teachTaskSearchHelper;
	 
	 protected BaseInfoSearchHelper  baseInfoSearchHelper;
	
	 protected RestrictionHelper dataRealmHelper;
	 
	 
	public void setDataRealmHelper(RestrictionHelper dataRealmHelper) {
		this.dataRealmHelper = dataRealmHelper;
	}


	public BaseInfoSearchHelper getBaseInfoSearchHelper() {
		return baseInfoSearchHelper;
	}


	public void setBaseInfoSearchHelper(BaseInfoSearchHelper baseInfoSearchHelper) {
		this.baseInfoSearchHelper = baseInfoSearchHelper;
	}


	public void setAttendStaticService(AttendStaticService attendStaticService) {
		this.attendStaticService = attendStaticService;
	}
	 

	public void setTeachTaskSearchHelper(TeachTaskSearchHelper teachTaskSearchHelper) {
		this.teachTaskSearchHelper = teachTaskSearchHelper;
	}


	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
			EntityQuery query = new EntityQuery(TeachCalendar.class, "teachCalendar");
			query.setSelect("select min(teachCalendar.start),max(teachCalendar.finish)");
			List tc=(List)utilService.search(query);
			if (tc!=null && !tc.isEmpty()) {
				Object[] date=(Object[])tc.get(0);
				Date dateMin=(Date)date[0];
				Date dateMax=(Date)date[1];
				SimpleDateFormat f=new SimpleDateFormat("yyyy");
				addSingleParameter(request, "dateMin", Integer.parseInt(f.format(dateMin)));
				addSingleParameter(request, "dateMax", Integer.parseInt(f.format(dateMax)));
			}
			Long calId=getLong(request, "calendar.id");
			
			TeachCalendar calendar=teachCalendarService.getTeachCalendar(getLong(request,
	                "calendar.studentType.id"), get(request, "calendar.year"), get(request,
	                "calendar.term"));
			if(calendar==null){
				List list=teachCalendarService.getCurTeachCalendars();
				if(list.size()>0){
					calendar=(TeachCalendar) list.get(0);
				}else{
					StudentType studentType=(StudentType) utilService.get(StudentType.class, new Long(2));
					calendar=teachCalendarService.getNearestCalendar(studentType);
				}
			}
			
			addSingleParameter(request, "studentType", calendar.getStudentType());
			addSingleParameter(request, "calendar", calendar);
			
			initBaseInfos(request, "stdTypeList", StudentType.class);
	        return forward(request);
	}
	
	
	
	 public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 	EntityQuery query=new EntityQuery(AttendStaticReport.class, "attendStaticReport");
		 	populateConditions(request, query);
		 	String monthAbesenceStart_str=request.getParameter("monthAbesenceStart");
		 	String monthAbesenceEnd_str=request.getParameter("monthAbesenceEnd");
		 	String termAbesenceStart_str=request.getParameter("termAbesenceStart");
		 	String termAbesenceEnd_str=request.getParameter("termAbesenceEnd");
		 	/*if((monthAbesenceStart_str!=""&&monthAbesenceStart_str!=null)&&(monthAbesenceEnd_str!=""&&monthAbesenceEnd_str!=null)){
		 		query.add(new Condition("attendStaticReport.monthAbesence between "+Double.parseDouble(monthAbesenceStart_str)+" and "+Double.parseDouble(monthAbesenceEnd_str)));
		 	}
		 	if((termAbesenceStart_str!=""&&termAbesenceStart_str!=null)&&(termAbesenceEnd_str!=""&&termAbesenceEnd_str!=null)){
		 		query.add(new Condition("attendStaticReport.termAbesence between "+Double.parseDouble(termAbesenceStart_str)+" and "+Double.parseDouble(termAbesenceEnd_str)));
		 	}*/
		 	if (StringUtils.isNotBlank(monthAbesenceStart_str)) {
		 		query.add(new Condition("attendStaticReport.monthAbesence >= "+Double.parseDouble(monthAbesenceStart_str.trim())));
			}
		 	if (StringUtils.isNotBlank(monthAbesenceEnd_str)) {
		 		query.add(new Condition("attendStaticReport.monthAbesence <= "+Double.parseDouble(monthAbesenceEnd_str.trim())));
			}
		 	if (StringUtils.isNotBlank(termAbesenceStart_str)) {
		 		query.add(new Condition("attendStaticReport.termAbesence >= "+Double.parseDouble(termAbesenceStart_str.trim())));
			}
		 	if (StringUtils.isNotBlank(termAbesenceEnd_str)) {
		 		query.add(new Condition("attendStaticReport.termAbesence <= "+Double.parseDouble(termAbesenceEnd_str.trim())));
			}
		 	String adminClass=request.getParameter("student.adminClasses");
		 	if (StringUtils.isNotBlank(adminClass)) {
		 		query.join("attendStaticReport.student.adminClasses", "adminClass");
		 		query.add(new Condition("adminClass.name like '%"+adminClass.trim()+"%' or adminClass.code like '%"+adminClass.trim()+"%'"));
			}
		 	query.addOrder(new Order("attendStaticReport.student.department"));
		 	query.setLimit(getPageLimit(request));
		 	addCollection(request, "attendStaticReports",  utilService.search(query));
		 	//addSingleParameter(request, "calendar", utilService.load(TeachCalendar.class, getLong(request, "calendar.id")));
	       return forward(request);
	 }
	
	 
	 
	 	//总缺勤
		Integer absenseTotal=0;
		//总迟到
		Integer lateTotal=0;
		
		
		public ActionForward selectTerm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
			EntityQuery query = new EntityQuery(TeachCalendar.class, "teachCalendar");
			
			TeachCalendar calendar=teachCalendarService.getTeachCalendar(getLong(request,
	                "calendar.studentType.id"), get(request, "calendar.year"), get(request,
	                "calendar.term"));
			if(calendar==null){
				List calslist=teachCalendarService.getCurTeachCalendars();
				if(calslist.size()>0){
					calendar=(TeachCalendar) calslist.get(0);
				}else{
					StudentType studentType=(StudentType) utilService.get(StudentType.class, new Long(2));
					calendar=teachCalendarService.getNearestCalendar(studentType);
				}
			}
			
			addSingleParameter(request, "studentType", calendar.getStudentType());
			addSingleParameter(request, "calendar", calendar);
			
			initBaseInfos(request, "stdTypeList", StudentType.class);
			return forward(request);
		}
		
	 /**
	  * 统计报表
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 public ActionForward reportStatic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 	TeachCalendar calendar=teachCalendarService.getTeachCalendar(getLong(request, "calendar.studentType.id"), get(request, "calendar.year"), get(request, "calendar.term"));
			 //查询学生
			 EntityQuery queryAttendStu=new EntityQuery(AttendStatic.class,"attendStatic");
			 queryAttendStu.setSelect("attendStatic.student.id");
			 queryAttendStu.add(new Condition("attendStatic.calendar.id=(:cid)",calendar.getId()));
			 queryAttendStu.groupBy("attendStatic.student.id");
			 List<Long> stuList=(List<Long>) utilService.search(queryAttendStu);
			
			 List<AttendStaticReport> attendStaticReports=new ArrayList<AttendStaticReport>();
			 for (Long stuId : stuList) {
				 //按学期查找总出勤记录
				 String queryTCbyTermHql="select count(*) from AttendStatic dr " +
					 		" where dr.student.id= "+stuId +
					 		" and dr.calendar.id='"+calendar.getId()+"'" ;
					 List queryTCbyTermList=utilService.searchHQLQuery(queryTCbyTermHql);
					 Long  queryTCbyTermcount=(Long) queryTCbyTermList.get(0);
					 Integer queryTCbyTermcountInt=Integer.parseInt(queryTCbyTermcount.toString());
				 
				 //按学期查找缺勤记录
				 EntityQuery queryAbesenceTCbyTerm=new EntityQuery(AttendStatic.class,"attendStatic");
				 String queryAbesenceTCbyTermHql="select count(*) from AttendStatic dr " +
					 		" where dr.student.id= "+stuId +
					 		" and dr.attendtype='2' " +
					 		" and dr.calendar.id='"+calendar.getId()+"'" ;
					 List queryAbesenceTCbyTermList=utilService.searchHQLQuery(queryAbesenceTCbyTermHql);
					 Long  queryAbesenceTCbyTermcount=(Long) queryAbesenceTCbyTermList.get(0);
					 Integer queryAbesenceTCbyTermcountInt=Integer.parseInt(queryAbesenceTCbyTermcount.toString());
					 
					 
				//查询日期
				 EntityQuery queryAttendDate=new EntityQuery(AttendStatic.class,"attendStatic");
				 queryAttendDate.setSelect("to_char(attendStatic.attenddate,'yyyy-MM')");
				 queryAttendDate.add(new Condition("attendStatic.student.id=(stuId)",stuId));
				 queryAttendStu.add(new Condition("attendStatic.calendar.id=(:cid)",calendar.getId()));
				 queryAttendDate.groupBy("to_char(attendStatic.attenddate,'yyyy-MM')");
				 List<String> dateList=(List<String>) utilService.search(queryAttendDate);
				 
				 for (String date : dateList) {
					 AttendStaticReport attendStaticReport=new AttendStaticReport();
					 Student student=(Student) utilService.load(Student.class, stuId);
					 attendStaticReport.setStudent(student);
					 TeachCalendar calendar1=getCurCalendar(date);
					 attendStaticReport.setCalendar(calendar);
					 attendStaticReport.setAttendYear(Integer.parseInt(date.substring(0, 4)));
					 attendStaticReport.setAttendMonth(Integer.parseInt(date.substring(5, 7)));
					 attendStaticReport.setTermAbesence((queryAbesenceTCbyTermcountInt*10000/queryTCbyTermcountInt)/100f);
					 Teacher teacher=null;
					 for (AdminClass obj : (Set<AdminClass>)student.getAdminClasses()) {
						 teacher=obj.getInstructor();
					}
					 attendStaticReport.setTeacher(teacher);
					 
					 //按月查找总考勤记录的条数
					 String queryTCbyMonthHql="select count(*) from AttendStatic dr " +
					 		" where dr.student.id= "+stuId +
					 		" and to_char(dr.attenddate,'yyyy-MM')='"+date+"'" ;
					 List queryTCbyMonthList=utilService.searchHQLQuery(queryTCbyMonthHql);
					 Long  queryTCbyMonthcount=(Long) queryTCbyMonthList.get(0);
					 Integer queryTCbyMonthcountInt=Integer.parseInt(queryTCbyMonthcount.toString());
					 
					 //按月查找缺勤记录
					 String queryAbesenceTCbyMonthHql="select count(*) from AttendStatic dr " +
						 		" where dr.student.id= "+stuId +
						 		" and dr.attendtype='2' " +
						 		" and to_char(dr.attenddate,'yyyy-MM')='"+date+"'" ;
						 List queryAbesenceTCbyMonthList=utilService.searchHQLQuery(queryAbesenceTCbyMonthHql);
						 Long  queryAbesenceTCbyMonthcount=(Long) queryAbesenceTCbyMonthList.get(0);
						 Integer queryAbesenceTCbyMonthcountInt=Integer.parseInt(queryAbesenceTCbyMonthcount.toString());
						 if(queryTCbyMonthcountInt==0){
							 attendStaticReport.setMonthAbesence(0f);
						 }else{
							 attendStaticReport.setMonthAbesence((queryAbesenceTCbyMonthcountInt*10000/queryTCbyMonthcountInt)/100f);
						 }
						 
					 attendStaticReports.add(attendStaticReport);
					 //判断统计的该学期是否存在该条信息，存在更新，否则添加
					 AttendStaticReport onlyReport=  getOnly(attendStaticReport);
					 if(onlyReport!=null){
						 utilService.saveOrUpdate(onlyReport);
					 }else{
						 utilService.saveOrUpdate(attendStaticReport);
					 }
				 }
			 }
			 
		 
		 return redirect(request, "search","统计成功");
	 }
	 
	 
	 public AttendStaticReport getOnly(AttendStaticReport attendStaticReport){
		 EntityQuery query=new EntityQuery(AttendStaticReport.class, "attendStaticReport");
		 query.add(new Condition("attendStaticReport.calendar.id=(:cid)",attendStaticReport.getCalendar().getId()));
		 query.add(new Condition("attendStaticReport.student.id=(:tid)",attendStaticReport.getStudent().getId()));
		 query.add(new Condition("attendStaticReport.attendYear=(:attendYear)",attendStaticReport.getAttendYear()));
		 query.add(new Condition("attendStaticReport.attendMonth=(:attendMonth)",attendStaticReport.getAttendMonth()));
		 List<AttendStaticReport> attendStaticReports=(List<AttendStaticReport>) utilService.search(query);
		 if(attendStaticReports.size()>0){
			 AttendStaticReport attendStaticReportNew=attendStaticReports.get(0);
			 attendStaticReportNew.setMonthAbesence(attendStaticReport.getMonthAbesence());
			 attendStaticReportNew.setTermAbesence(attendStaticReport.getTermAbesence());
			 return attendStaticReportNew;
		 }else{
			 return  null;
		 }
		 
	 }
	 
	 public TeachCalendar getCurCalendar(String date){
		 String newDate_str=date+"-28";
		 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		 Date newDate=null;
		 try {
			 newDate=format.parse(newDate_str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 TeachCalendar calendar=teachCalendarService.getTeachCalendar(newDate);
		 return calendar;
	 }
	 
	 
	 public void addMonthBean(HttpServletRequest request,Integer i,Long taskid,List<MonthBean> monthBeanSet){
			MonthBean monthBean=new MonthBean();
			monthBean.setMonth(i);
			String absenceCountHql="select count(*) from AttendStatic dr  " +
					" where dr.task.id= "+taskid +
					" and to_char(dr.attenddate,'MM')="+i+" and dr.attendtype='2' ";
			List absenceList=utilService.searchHQLQuery(absenceCountHql);
			Long  count=(Long) absenceList.get(0);
			Integer countInt=Integer.parseInt(count.toString());
			absenseTotal+=countInt;
			monthBean.setAbsenceCount(countInt);
			
			String lateCountHql="select count(*) from AttendStatic dr " +
					" where dr.task= "+taskid +
					" and to_char(dr.attenddate,'MM')="+i+" and dr.attendtype='3'  ";
			List lateList=utilService.searchHQLQuery(lateCountHql);
			Long  count1=(Long) lateList.get(0);
			Integer countInt1=Integer.parseInt(count1.toString());
			lateTotal+=countInt1;
			monthBean.setLatCounet(countInt1);
			monthBeanSet.add(monthBean);
	 }
	 
	 
}
