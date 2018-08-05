package com.shufe.service.course.task.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.dao.course.task.CourseTakeForTaskParamDao;
import com.shufe.model.course.task.CourseTakeForTaskParam;
import com.shufe.service.BasicService;
import com.shufe.service.course.task.CourseTakeForTaskParamService;

public class CourseTakeForTaskParamServiceImpl extends BasicService implements
        CourseTakeForTaskParamService {
    
    CourseTakeForTaskParamDao courseTakeForTaskParamDao;
    
    public void setCourseTakeForTaskParamDao(CourseTakeForTaskParamDao courseTakeForTaskParamDao) {
        this.courseTakeForTaskParamDao = courseTakeForTaskParamDao;
    }
    
    public CourseTakeForTaskParam getCourseTakeForTaskParam(Serializable id) {
        return (CourseTakeForTaskParam) courseTakeForTaskParamDao.load(
                CourseTakeForTaskParam.class, id);
    }
    
    public List getCourseTakeForTaskParam(Long calendarId) {
        if (null != calendarId) {
            EntityQuery entityQuery = new EntityQuery(CourseTakeForTaskParam.class,
                    "courseTakeForTaskParam");
            entityQuery.add(new Condition("courseTakeForTaskParam.calendar.id=" + calendarId));
            List list = (List) utilDao.search(entityQuery);
            return list;
        } else
            return Collections.EMPTY_LIST;
    }
    
    public void saveCourseTakeForTaskParam(CourseTakeForTaskParam params) throws PojoExistException {
        if (params == null) {
            return;
        }
        courseTakeForTaskParamDao.saveCourseTakeForTaskParam(params);
    }
    
}
