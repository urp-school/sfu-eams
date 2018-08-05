package com.shufe.service.course.arrange.exam.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.dao.course.arrange.exam.ExamApplyParamDAO;
import com.shufe.model.course.arrange.exam.ExamApplyParam;
import com.shufe.model.course.election.ElectParams;
import com.shufe.model.std.Student;
import com.shufe.service.BasicService;
import com.shufe.service.course.arrange.exam.ExamApplyParamService;


public class ExamApplyParamServiceImpl extends BasicService implements ExamApplyParamService {
    protected ExamApplyParamDAO examApplyParamDao;

    public ExamApplyParam getExamApplyParamById(Serializable id) {
        return (ExamApplyParam)examApplyParamDao.load(ExamApplyParam.class, id);
    }

    public List getExamApplyParam(Long calendarId) {
        return examApplyParamDao.getExamApplyParam(calendarId);
    }

    public void removeExamApplyParam(Object[] idStr) {
        examApplyParamDao.removeExamApplyParam(idStr);

    }

    public void saveExamApplyParam(ExamApplyParam param) throws PojoExistException {
        examApplyParamDao.saveExamApplyParam(param);
    }

    
    public void setExamApplyParamDao(ExamApplyParamDAO examApplyParamDao) {
        this.examApplyParamDao = examApplyParamDao;
    }
    
    public ExamApplyParam getExamApplyParams(Student std) {
        List existed = examApplyParamDao.getExamApplyParams(std);
        // 取时间最近的选择参数
        PropertyComparator pc = new PropertyComparator("startDate");
        Collections.sort(existed, pc);
        return existed.isEmpty() ? null : (ExamApplyParam) existed.get(0);
    }

}
