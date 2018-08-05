package com.shufe.dao.course.task;

import java.util.List;

import com.shufe.dao.BasicDAO;
import com.shufe.model.course.task.TeachTaskParam;
import com.shufe.model.system.calendar.TeachCalendar;


public interface TeachTaskParamDAO extends BasicDAO {

    public List getTeachTaskParam(Long[] stdTypeIds, TeachCalendar calendar);
    
    public void saveTeachTaskParam(TeachTaskParam params);
}
