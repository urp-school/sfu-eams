package com.shufe.dao.course.task.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.task.CourseTakeForTaskParamDao;
import com.shufe.model.course.task.CourseTakeForTaskParam;
import com.shufe.model.system.calendar.TeachCalendar;

public class CourseTakeForTaskParamDaoHibernate extends BasicHibernateDAO implements
        CourseTakeForTaskParamDao {
    
    public List getCourseTakeForTaskParam(Long[] stdTypeIds, TeachCalendar calendar) {
        Map params = new HashMap();
        params.put("stdTypeIds", stdTypeIds);
        params.put("calendar", calendar);
        return find("getElectParams", params);
    }
    
    public void saveCourseTakeForTaskParam(CourseTakeForTaskParam params) {
        getSessionFactory().evict(CourseTakeForTaskParam.class);
        saveOrUpdate(params);
    }
    
}
