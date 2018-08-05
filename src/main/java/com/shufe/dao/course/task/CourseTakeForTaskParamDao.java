package com.shufe.dao.course.task;

import java.util.List;

import com.shufe.dao.BasicDAO;
import com.shufe.model.course.task.CourseTakeForTaskParam;
import com.shufe.model.system.calendar.TeachCalendar;


public interface CourseTakeForTaskParamDao extends BasicDAO {
    
 public List getCourseTakeForTaskParam(Long[] stdTypeIds, TeachCalendar calendar);
    
    public void saveCourseTakeForTaskParam(CourseTakeForTaskParam params);
}
