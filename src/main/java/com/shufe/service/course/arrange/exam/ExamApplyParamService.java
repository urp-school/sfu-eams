package com.shufe.service.course.arrange.exam;

import java.io.Serializable;
import java.util.List;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.model.course.arrange.exam.ExamApplyParam;
import com.shufe.model.course.election.ElectParams;
import com.shufe.model.std.Student;


public interface ExamApplyParamService {
    
    /**
     * g根据ID去开关参数
     * @param id
     * @return
     */
    public ExamApplyParam getExamApplyParamById(Serializable id);
    
    /**
     * 根据教学日历取开关参数集合
     * @param calendarId
     * @return
     */
    public List getExamApplyParam(Long calendarId);
    /**
     * 保存开关
     * @param param
     * @throws PojoExistException
     */
    public void saveExamApplyParam(ExamApplyParam param)throws PojoExistException;
    /**
     * 删除开关
     * @param idStr
     */
    public void removeExamApplyParam(Object idStr[]);
    /**
     * 根据学生对象找到参数
     * @param std
     * @return
     */
    public ExamApplyParam getExamApplyParams(Student std);

}
