package com.shufe.dao.course.task;

import java.util.List;

import com.shufe.dao.BasicDAO;
import com.shufe.model.course.arrange.task.ManualArrangeParam;
import com.shufe.model.system.calendar.TeachCalendar;


public interface ManualArrangeParamDao extends BasicDAO {
    
public List getManualArrangeParam(Long[] stdTypeIds, TeachCalendar calendar);
    
    public void saveManualArrangeParam(ManualArrangeParam params);

}
