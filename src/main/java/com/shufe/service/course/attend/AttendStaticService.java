package com.shufe.service.course.attend;

import java.sql.Date;
import java.util.Map;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.dao.course.attend.AttendStaticDAO;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 考勤统计 
 * @author SongXiangwen
 *
 */
public interface AttendStaticService {
	public void setAttendStaticDAO(AttendStaticDAO attendStaticDAO);
    
	
	public Map getTeachTaskDWR(String stdTypeId,String stdNO,String year,
            String term);
    
	public Student getStudent(String code);
	
	public TeachTask getTeachTask(String seqNo);
}
