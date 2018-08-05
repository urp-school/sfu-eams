package com.shufe.service.course.task;

import java.io.Serializable;
import java.util.List;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.model.course.task.CourseTakeForTaskParam;


public interface CourseTakeForTaskParamService {
    
    public CourseTakeForTaskParam getCourseTakeForTaskParam(Serializable id);
    /**
     * 
     * @param stdTypeIds
     * @param calendar
     * @return
     */
    
    public List getCourseTakeForTaskParam(Long calendarId);
    /**
     * 
     * @param params
     * @throws PojoExistException
     */
    public void saveCourseTakeForTaskParam(CourseTakeForTaskParam params) throws PojoExistException;
}
