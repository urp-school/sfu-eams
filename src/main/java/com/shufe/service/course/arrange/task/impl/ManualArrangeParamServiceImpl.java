package com.shufe.service.course.arrange.task.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.dao.course.task.ManualArrangeParamDao;
import com.shufe.model.course.arrange.task.ManualArrangeParam;
import com.shufe.service.BasicService;


public class ManualArrangeParamServiceImpl extends BasicService implements
        com.shufe.service.course.arrange.task.ManualArrangeParamService {
    
    ManualArrangeParamDao manualArrangeParamDao;

    
    public void setManualArrangeParamDao(ManualArrangeParamDao manualArrangeParamDao) {
        this.manualArrangeParamDao = manualArrangeParamDao;
    }

    public ManualArrangeParam getManualArrangeParam(Serializable id) {
        return (ManualArrangeParam) manualArrangeParamDao.load(ManualArrangeParam.class, id);
    }

    public List getManualArrangeParam(Long calendarId) {
        if (null != calendarId) {
            EntityQuery entityQuery = new EntityQuery(ManualArrangeParam.class,
                    "manualArrangeParam");
            entityQuery.add(new Condition("manualArrangeParam.calendar.id=" + calendarId));
            List list = (List) utilDao.search(entityQuery);
            return list;
        } else
            return Collections.EMPTY_LIST;
    }

    public void saveManualArrangeParam(ManualArrangeParam params) throws PojoExistException {
        if (params == null) {
            return;
        }
        manualArrangeParamDao.saveManualArrangeParam(params);
    }

}
