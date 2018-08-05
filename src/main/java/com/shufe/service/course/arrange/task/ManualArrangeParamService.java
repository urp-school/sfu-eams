package com.shufe.service.course.arrange.task;

import java.io.Serializable;
import java.util.List;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.model.course.arrange.task.ManualArrangeParam;


public interface ManualArrangeParamService {
    
    public ManualArrangeParam getManualArrangeParam(Serializable id);
    /**
     * 
     * @param stdTypeIds
     * @param calendar
     * @return
     */
    
    public List getManualArrangeParam(Long calendarId);
    /**
     * 
     * @param params
     * @throws PojoExistException
     */
    public void saveManualArrangeParam(ManualArrangeParam params) throws PojoExistException;

}
