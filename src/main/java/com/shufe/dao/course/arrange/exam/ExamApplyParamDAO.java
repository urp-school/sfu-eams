package com.shufe.dao.course.arrange.exam;

import java.util.List;

import com.shufe.dao.BasicDAO;
import com.shufe.model.course.arrange.exam.ExamApplyParam;
import com.shufe.model.std.Student;


public interface ExamApplyParamDAO  extends BasicDAO{
    
    public List getExamApplyParam(Long calendarId);
    
    public void saveExamApplyParam(ExamApplyParam param);
    
    public void removeExamApplyParam(Object[] idStr);
    
    public List getExamApplyParams(Student std);

}
