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
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.eams.system.baseinfo.Department;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.model.course.attend.AttendStatic;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.attend.AttendStaticImportListener;
import com.shufe.service.course.attend.AttendStaticService;
import com.shufe.service.course.grade.GradeImportListener;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 教师考勤统计报表
 * @author SongXiangwen
 *
 */
public class AttendReportTeaAction extends CalendarRestrictionSupportAction{
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
			
			Teacher user=getTeacherFromSession(request.getSession());
			if(user!=null){
				
			//查找当前教师在本学期的教学任务
			 List taskList = teachTaskService.getTeachTasksByCategory(user.getId(),
                    TeachTaskFilterCategory.TEACHER, teachCalendarService
                            .getTeachCalendarsOfOverlapped(calendar));
			addCollection(request, "taskList", taskList);
			}
			
	        return forward(request);
	}
	
	//总缺勤
	Integer absenseTotal=0;
	//总迟到
	Integer lateTotal=0;
	
	/**
	 * 制作学生考勤报表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	 public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 Teacher user=getTeacherFromSession(request.getSession());
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
					addMonthBean(user.getId(),i,taskid,monthBeanSet);
				}
			}else{
				for (int j = startMonth; j <=12 ; j++) {
					addMonthBean(user.getId(),j,taskid,monthBeanSet);
				}
				
				for (int i = 1; i <=finishMonth ; i++) {
					addMonthBean(user.getId(),i,taskid,monthBeanSet);
				}
			}
			
			//查询当前学期所有出勤数
			String queryTotalHql="select count(*) from AttendStatic dr  "+
					" left join dr.task.arrangeInfo.teachers t  " +
					" where dr.task= "+taskid +
					" and dr.calendar.id="+calendarId+" and dr.attendtype='1'  "+
					" and t.id="+user.getId();
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
	
	 
	 
	 
	 public void addMonthBean(Long userId,Integer i,Long taskid,List<MonthBean> monthBeanSet){
			MonthBean monthBean=new MonthBean();
			monthBean.setMonth(i);
			String absenceCountHql="select count(*) from AttendStatic dr  " +
					" left join dr.task.arrangeInfo.teachers t  " +
					" where dr.task.id= "+taskid +
					" and to_char(dr.attenddate,'MM')="+i+" and dr.attendtype='2'"+
							" and t.id="+userId;
			List absenceList=utilService.searchHQLQuery(absenceCountHql);
			Long  count=(Long) absenceList.get(0);
			Integer countInt=Integer.parseInt(count.toString());
			absenseTotal+=countInt;
			monthBean.setAbsenceCount(countInt);
			String lateCountHql="select count(*) from AttendStatic dr " +
					" left join dr.task.arrangeInfo.teachers t  " +
					" where dr.task= "+taskid +
					" and to_char(dr.attenddate,'MM')="+i+" and dr.attendtype='3'  "+
					" and t.id="+userId;
			List lateList=utilService.searchHQLQuery(lateCountHql);
			Long  count1=(Long) lateList.get(0);
			Integer countInt1=Integer.parseInt(count1.toString());
			lateTotal+=countInt1;
			monthBean.setLatCounet(countInt1);
			monthBeanSet.add(monthBean);
	 }
	 
	 
	 public ActionForward showDetailList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 	//Long calId=getLong(request, "calendar.id");
		 	//Teacher user=getTeacherFromSession(request.getSession());
		 	EntityQuery query = new EntityQuery(AttendStatic.class, "attendStatic");
		 	/*if(calId!=null){
		 		query.join("attendStatic.task.arrangeInfo.teachers", "teacher");
		 		query.add(new Condition("attendStatic.calendar.id=(:id)",calId));
		 		query.add(new Condition("teacher.id=(:tid)",user.getId()));
		 	}*/
		 	query.add(new Condition("attendStatic.task.id in("+get(request, "taskIds")+")"));
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
		 	String attenddate = get(request, "attenddate");
		 	if (StringUtils.isNotBlank(attenddate)) {
		 		query.add(new Condition("instr(to_char(attendStatic.attenddate, :geshi),'"+attenddate+"',1) <> 0","yyyy-MM-dd HH24:mi:ss"));
		 	}
		 	query.setLimit(getPageLimit(request));
	        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		 	//List<AttendStatic> attendStatics = (List<AttendStatic>) utilService.search(query);
			addCollection(request, "attendStatics",utilService.search(query));
			addSingleParameter(request, "taskIds", get(request, "taskIds"));
			return forward(request);
	 }
	 
	 
	 public String changeToString(Integer month){
		 String month_str=month.toString();
		 if(month_str.length()<2){
			 month_str="0"+month_str;
		 }
		 return month_str;
	 }
	 
	 public ActionForward editAttendtype(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 String sql="update "+AttendStatic.class.getName()+" a set a.attendtype="+get(request, "attendtype")+" where a.id in("+get(request, "attendStaticIds")+")";
		 utilService.getUtilDao().executeUpdateHql(sql, (Object[])null);
		 return redirect(request, "showDetailList", "info.update.success", "&taskIds="+get(request, "taskIds"));
	 }
	    
}
