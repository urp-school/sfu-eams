package com.shufe.web.action.course.attend;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.course.attend.AttendStatic;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.web.action.system.baseinfo.search.AdminClassSearchAction;

public class AttendReportForAdminClassAction extends AdminClassSearchAction{
	
	public ActionForward setReportDate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String adminClassIds = get(request, "adminClassIds");
        if (StringUtils.isEmpty(adminClassIds)) {
            return forward(mapping, request, "error.model.id.needed", "error");
        }
        request.setAttribute("taskIds",adminClassIds);        
    	return forward(request);
    }
    
	public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String adminClassIds = get(request, "taskIds");
		if (StringUtils.isEmpty(adminClassIds)) {
			return forward(mapping, request, "error.model.id.needed", "error");
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
        query.add(new Condition("exists(from "+AdminClass.class.getName()+" ac join ac.students std where std=d.student and ac.id in("+adminClassIds+"))"));
        query.setSelect(sql.toString());
        query.groupBy("cube(d.student.id)");        
        List list=new ArrayList(utilService.search(query));
        if (!list.isEmpty()) {	
        	int num=0;
        	for (int i = 0; i < list.size(); i++) {
        		Object[] object =(Object[])list.get(i);
				if (object[0]==null) {
					object[0]="总计（班级）";
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
        request.setAttribute("adminClass", utilService.get(AdminClass.class,Long.parseLong(adminClassIds)));
        return forward(request);
	}
	
}
