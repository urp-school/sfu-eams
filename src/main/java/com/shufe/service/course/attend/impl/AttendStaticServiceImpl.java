package com.shufe.service.course.attend.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.shufe.dao.course.attend.AttendStaticDAO;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.attend.AttendStaticService;
import com.shufe.service.system.calendar.TeachCalendarService;

/**
 * 考勤统计 
 * @author SongXiangwen
 *
 */
public class AttendStaticServiceImpl extends BasicService implements AttendStaticService{
	private AttendStaticDAO attendStaticDAO;
	
	protected TeachCalendarService calendarService;

	public void setAttendStaticDAO(AttendStaticDAO attendStaticDAO) {
		this.attendStaticDAO = attendStaticDAO;
	}
	
	public Map getTeachTaskDWR(String stdTypeId,String stdNO, String year,
            String term) {
		 	Long studentTypeId = new Long(stdTypeId);
			TeachCalendar calendar = calendarService.getTeachCalendar(studentTypeId, year, term);
            EntityQuery query = new EntityQuery(CourseTake.class, "courseTake");
            query.setSelect("task");
            query.join("courseTake.task", "task");
            query.join("courseTake.student", "student");
            query.add(new Condition("student.code=(:code)",stdNO));
            query.add(new Condition("task.calendar.id",calendar.getId()));
            List<TeachTask> list = (List<TeachTask>) utilDao.search(query);
            if (list == null || list.isEmpty()) {
                return null;
            }
            for (TeachTask task : list) {
              Map taskMap = new HashMap();
              taskMap.put("id", task.getId());
              taskMap.put("course.code", task.getCourse().getCode());
              taskMap.put("course.name", task.getCourse().getName());
              taskMap.put("task.gradeState.precision", task.getGradeState().getPrecision());
              taskMap
                      .put("task.gradeState.markStyle.id", task.getGradeState().getMarkStyle()
                              .getId());
              taskMap.put("task.gradeState.markStyle.name", task.getGradeState().getMarkStyle()
                      .getName());
			}
            
        return null;
    }
    
	/**
	 * 根据学号查找学生
	 * @param code
	 * @return
	 */
	public Student getStudent(String code){
		if(code==null){
			return null;
		}
		EntityQuery query=new EntityQuery(Student.class,"student");
		query.add(new Condition("student.code=(:code)",code));
		List<Student> students=(List<Student>) utilDao.search(query);
		return students.size()>0?students.get(0):null;
	}
	
	/**
	 * 根据课程序号查找教学任务
	 */
	public TeachTask getTeachTask(String seqNo){
		if(seqNo==null){
			return null;
		}
		EntityQuery query=new EntityQuery(TeachTask.class,"teachTask");
		query.add(new Condition("teachTask.seqNo=(:seqNo)",seqNo));
		List<TeachTask> tasks=(List<TeachTask>) utilDao.search(query);
		return tasks.size()>0?tasks.get(0):null;
	}
    
}
