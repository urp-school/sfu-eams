package com.shufe.web.action.course.attend;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.shufe.model.course.task.TeachTask;
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
public class AttendReportDeptAction extends CalendarRestrictionSupportAction{
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
			Long calId=getLong(request, "calendar.id");
			
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
			addCollection(request, Constants.DEPARTMENT_LIST, departmentService.getColleges());
	        addCollection(request, "weeks", WeekInfo.WEEKS);
	        initBaseCodes(request, "courseTypes", CourseType.class);
	        initBaseInfos(request, "schoolDistricts", SchoolDistrict.class);
	        return forward(request);
	}
	
	
	
	 public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 	addCollection(request, "tasks", teachTaskSearchHelper.searchTask(request));
	        String order = request.getParameter("order");
	        addSingleParameter(request, "order", order);
	       return forward(request);
	 }
	
	 
	 	//总缺勤
		Integer absenseTotal=0;
		//总迟到
		Integer lateTotal=0;
		
	 /**
	  * 按教学任务查看报表
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 public ActionForward showReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 Long[] taskids = SeqStringUtil.transformToLong(get(request, "taskids"));
		 Long calendarId=getLong(request, "calendarId");
		 TeachCalendar calendar=(TeachCalendar) utilService.load(TeachCalendar.class, calendarId);
		 Date startCal=calendar.getStart();
		 Date endCal=calendar.getFinish();
		 Integer startMonth=startCal.getMonth()+1;
		 Integer finishMonth=endCal.getMonth()+1;
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
		 //获取当前学期的月份
		 List monthList=new ArrayList();
		 if(startMonth<finishMonth){
				for(int i=startMonth;i<=finishMonth;i++){
					monthList.add(sdf.format(endCal)+"年-"+i);
				}
			}else{
				for (int j = startMonth; j <=12 ; j++) {
					monthList.add(sdf.format(startCal)+"年-"+j);
				}
				
				for (int i = 1; i <=finishMonth ; i++) {
					monthList.add(sdf.format(endCal)+"年-"+i);
				}
			}
		 
		 //获取对应教学任务对应月份的对应考勤信息
		 List<TaskBean> taskBeans=new ArrayList<TaskBean>();
		 for (Long taskid : taskids) {
			TaskBean taskBean=new TaskBean();
			TeachTask teachTask=(TeachTask) utilService.load(TeachTask.class, taskid);
			taskBean.setTask(teachTask);
			List<MonthBean> monthBeanSet=new ArrayList<MonthBean>();
			
			absenseTotal=0;
			lateTotal=0;
			
			if(startMonth<finishMonth){
				for(int i=startMonth;i<=finishMonth;i++){
					addMonthBean(request,i,taskid,monthBeanSet);
				}
			}else{
				for (int j = startMonth; j <=12 ; j++) {
					addMonthBean(request,j,taskid,monthBeanSet);
				}
				
				for (int i = 1; i <=finishMonth ; i++) {
					addMonthBean(request,i,taskid,monthBeanSet);
				}
			}
			
			//查询当前学期所有出勤数
			String queryTotalHql="select count(*) from AttendStatic dr  "+
					" where dr.task= "+taskid +
					" and dr.calendar.id="+calendarId+" and dr.attendtype='1'  ";
			List totalList=utilService.searchHQLQuery(queryTotalHql);
			Long  count=(Long) totalList.get(0);
			Integer countInt=Integer.parseInt(count.toString());
			PercentBean percentBean=new PercentBean(0.0f, 0.0f, 0.0f);
			Integer total=absenseTotal+lateTotal+countInt;
			if(total>0){
				percentBean=new PercentBean((absenseTotal*10000/total)/100f, (lateTotal*10000/total)/100f, (countInt*10000/total)/100f);
			}
			taskBean.setPercentBean(percentBean);
			taskBean.setMonthBeans(monthBeanSet);
			taskBeans.add(taskBean);
		}
		 addCollection(request, "monthList", monthList);
		 addCollection(request, "taskBeans", taskBeans);
		 return forward(request);
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
	 
	 /**
	  * 按班级查看考勤
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 public ActionForward showByClassPrepare(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 addCollection(request, "departmentList", getTeachDeparts(request));
	      addCollection(request, "stdTypeList", getStdTypes(request));
	      setCalendarDataRealm(request, hasStdTypeCollege);
		 return forward(request);
	 }
	 
	 public ActionForward showByClass(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 	addCollection(request, "adminClasses", baseInfoSearchHelper.searchAdminClass(request));
	        return forward(request);
	 }
	 
	 
	 /**
	  * 根据班级查看报表
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 public ActionForward showReportByClass(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 Long[] adminClassIds = SeqStringUtil.transformToLong(get(request, "adminClassIds"));
			/*List calslist=teachCalendarService.getCurTeachCalendars();
			TeachCalendar calendar=new TeachCalendar();
			if(calslist.size()>0){
				calendar=(TeachCalendar) calslist.get(0);
			}else{
				StudentType studentType=(StudentType) utilService.get(StudentType.class, new Long(2));
				calendar=teachCalendarService.getNearestCalendar(studentType);
			}*/
		 TeachCalendar calendar=(TeachCalendar)utilService.get(TeachCalendar.class, getLong(request, "calendar.id"));
		 Date startCal=calendar.getStart();
		 Date endCal=calendar.getFinish();
		 Integer startMonth=startCal.getMonth()+1;
		 Integer finishMonth=endCal.getMonth()+1;
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
		 //获取当前学期的月份
		 List monthList=new ArrayList();
		 if(startMonth<finishMonth){
				for(int i=startMonth;i<=finishMonth;i++){
					monthList.add(sdf.format(endCal)+"年-"+i);
				}
			}else{
				for (int j = startMonth; j <=12 ; j++) {
					monthList.add(sdf.format(startCal)+"年-"+j);
				}
				
				for (int i = 1; i <=finishMonth ; i++) {
					monthList.add(sdf.format(endCal)+"年-"+i);
				}
			} 	
		 
		 //获取对应班级对应的月份的考勤信息
		 List<ClassBean> classBeans=new ArrayList<ClassBean>();
		 for (Long adminClassId : adminClassIds) {
			 ClassBean classBean=new ClassBean();
			 AdminClass adminClass=(AdminClass) utilService.load(AdminClass.class, adminClassId);
			 classBean.setAdminClass(adminClass);
			 
			 List<MonthBean> monthBeanSet=new ArrayList<MonthBean>();
				
				absenseTotal=0;
				lateTotal=0;
				
				if(startMonth<finishMonth){
					for(int i=startMonth;i<=finishMonth;i++){
						addMonthBeanByClass(request,i,adminClassId,monthBeanSet);
					}
				}else{
					for (int j = startMonth; j <=12 ; j++) {
						addMonthBeanByClass(request,j,adminClassId,monthBeanSet);
					}
					
					for (int i = 1; i <=finishMonth ; i++) {
						addMonthBeanByClass(request,i,adminClassId,monthBeanSet);
					}
				}
				
				//查询当前学期所有出勤数
				String queryTotalHql="select count(*) from AttendStatic dr  "+
						" left join  dr.student.adminClasses ac " +
						" where ac.id= "+adminClassId +
						" and dr.calendar.id="+calendar.getId()+" and dr.attendtype='1'  ";
				List totalList=utilService.searchHQLQuery(queryTotalHql);
				Long  count=(Long) totalList.get(0);
				Integer countInt=Integer.parseInt(count.toString());
				PercentBean percentBean=new PercentBean(0.0f, 0.0f, 0.0f);
				Integer total=absenseTotal+lateTotal+countInt;
				if(total>0){
					percentBean=new PercentBean((absenseTotal*10000/total)/100f, (lateTotal*10000/total)/100f, (countInt*10000/total)/100f);
				}
				classBean.setPercentBean(percentBean);
				classBean.setMonthBeans(monthBeanSet);
				classBeans.add(classBean);
		 }
		 addCollection(request, "monthList", monthList);
		 addCollection(request, "classBeans", classBeans);
		 return forward(request);
	 }
	 
	 
	 public void addMonthBeanByClass(HttpServletRequest request,Integer i,Long adminClassId,List<MonthBean> monthBeanSet){
			MonthBean monthBean=new MonthBean();
			monthBean.setMonth(i);
			String absenceCountHql="select count(*) from AttendStatic dr " +
					" left join  dr.student.adminClasses ac " +
					" where ac.id= "+adminClassId +
					" and to_char(dr.attenddate,'MM')="+i+" and dr.attendtype='2' ";
			List absenceList=utilService.searchHQLQuery(absenceCountHql);
			Long  count=(Long) absenceList.get(0);
			Integer countInt=Integer.parseInt(count.toString());
			absenseTotal+=countInt;
			monthBean.setAbsenceCount(countInt);
			
			String lateCountHql="select count(*) from AttendStatic dr " +
					" left join  dr.student.adminClasses ac " +
					" where ac.id= "+adminClassId +
					" and to_char(dr.attenddate,'MM')="+i+" and dr.attendtype='3'  ";
			List lateList=utilService.searchHQLQuery(lateCountHql);
			Long  count1=(Long) lateList.get(0);
			Integer countInt1=Integer.parseInt(count1.toString());
			lateTotal+=countInt1;
			monthBean.setLatCounet(countInt1);
			monthBeanSet.add(monthBean);
	 }
	 
	 public ActionForward showAttendDetailPrepare(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 EntityQuery query = new EntityQuery(TeachCalendar.class, "teachCalendar");
			Long calId=getLong(request, "calendar.id");
			
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
			initBaseInfos(request, "departmentList", Department.class);
		 return forward(request);
	 }
	 
	 public ActionForward showAttendDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 	Teacher user=getTeacherFromSession(request.getSession());
		    Long calId=getLong(request, "calendar.id");
		 	EntityQuery query = new EntityQuery(AttendStatic.class, "attendStatic");
		 	if(calId!=null){
		 		query.add(new Condition("attendStatic.calendar.id=(:id)",calId));
		 	}
		 	String attenddate = get(request, "attenddate");
		 	if (StringUtils.isNotBlank(attenddate)) {
		 		query.add(new Condition("instr(to_char(attendStatic.attenddate, :geshi),'"+attenddate+"',1) <> 0","yyyy-MM-dd HH24:mi:ss"));
		 	}
		 	addDeptDataRealm(request,query);
		 	populateConditions(request, query);
		 	query.setLimit(getPageLimit(request));
			addCollection(request, "attendStatics",utilService.search(query));
		 return forward(request);
	 } 
	 
	 /**
	  * 根据部门按数据权限查询
	  * @param request
	  * @param query
	  */
	 public void addDeptDataRealm( HttpServletRequest request,EntityQuery query){
		 Long stdTypeId = RequestUtils.getLong(request, "calendar.studentType.id");
		 String resourceName = getResourceName(request);
         Resource resource = (Resource) authorityService.getResource(resourceName);
         if (null != resource && !resource.getPatterns().isEmpty()) {
        	    DataRealmUtils.addDataRealms(query, new String[] { "attendStatic.task.teachClass.stdType.id",
                "attendStatic.task.arrangeInfo.teachDepart.id" }, dataRealmHelper.getDataRealmsWith(
                stdTypeId, request));
         }
	 }
	 
}
