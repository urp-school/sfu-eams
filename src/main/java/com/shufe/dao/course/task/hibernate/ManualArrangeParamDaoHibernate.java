package com.shufe.dao.course.task.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.task.ManualArrangeParamDao;
import com.shufe.model.course.arrange.task.ManualArrangeParam;
import com.shufe.model.system.calendar.TeachCalendar;


public class ManualArrangeParamDaoHibernate extends BasicHibernateDAO implements
        ManualArrangeParamDao {

    public List getManualArrangeParam(Long[] stdTypeIds, TeachCalendar calendar) {
        Map params = new HashMap();
        params.put("stdTypeIds", stdTypeIds);
        params.put("calendar", calendar);
        return find("getElectParams", params);
    }

    public void saveManualArrangeParam(ManualArrangeParam params) {
        getSessionFactory().evict(ManualArrangeParam.class);
        saveOrUpdate(params);
    }

}
