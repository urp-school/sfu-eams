package com.shufe.dao.course.task.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.task.TeachTaskParamDAO;
import com.shufe.model.course.task.TeachTaskParam;
import com.shufe.model.system.calendar.TeachCalendar;

public class TeachTaskParamDAOHibernate extends BasicHibernateDAO implements TeachTaskParamDAO {
    
    public List getTeachTaskParam(Long[] stdTypeIds, TeachCalendar calendar) {
        Map params = new HashMap();
        params.put("stdTypeIds", stdTypeIds);
        params.put("calendar", calendar);
        return find("getElectParams", params);
    }
    
    public void saveTeachTaskParam(TeachTaskParam params) {
        getSessionFactory().evict(TeachTaskParam.class);
        saveOrUpdate(params);
    }
    
}
