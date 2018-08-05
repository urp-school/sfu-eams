package com.shufe.service.course.task;

import java.io.Serializable;
import java.util.List;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.model.course.task.TeachTaskParam;

/**
 * 教学任务参数接口
 * 
 * @author Administrator
 * 
 */
public interface TeachTaskParamService {
    
    /**
     * 获得单个任务参数
     * 
     * @param id
     * @return
     */
    public TeachTaskParam getTeachTaskParam(Serializable id);
    
    /**
     * 
     * @param stdTypeIds
     * @param calendar
     * @return
     */
    
    public List getTeachTaskParam(Long calendarId);
    
    /**
     * 
     * @param params
     * @throws PojoExistException
     */
    public void saveTeachTaskParam(TeachTaskParam params) throws PojoExistException;
}
