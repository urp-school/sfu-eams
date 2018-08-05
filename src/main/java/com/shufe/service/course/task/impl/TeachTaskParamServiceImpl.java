package com.shufe.service.course.task.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.dao.course.task.TeachTaskParamDAO;
import com.shufe.model.course.task.TeachTaskParam;
import com.shufe.service.BasicService;
import com.shufe.service.course.task.TeachTaskParamService;

public class TeachTaskParamServiceImpl extends BasicService implements TeachTaskParamService {
    
    TeachTaskParamDAO teachTaskParamDAO;
    
    public TeachTaskParam getTeachTaskParam(Serializable id) {
        return (TeachTaskParam) teachTaskParamDAO.load(TeachTaskParam.class, id);
    }
    
    public List getTeachTaskParam(Long calendarId) {
        if (null != calendarId) {
            EntityQuery entityQuery = new EntityQuery(TeachTaskParam.class, "teachTaskParam");
            entityQuery.add(new Condition("teachTaskParam.calendar.id=" + calendarId));
            List list = (List) utilDao.search(entityQuery);
            return list;
        } else
            return Collections.EMPTY_LIST;
    }
    
    public void saveTeachTaskParam(TeachTaskParam params) throws PojoExistException {
        if (params == null) {
            return;
        }
        teachTaskParamDAO.saveTeachTaskParam(params);
    }
    
    public void setTeachTaskParamDAO(TeachTaskParamDAO teachTaskParamDAO) {
        this.teachTaskParamDAO = teachTaskParamDAO;
    }
    
}
