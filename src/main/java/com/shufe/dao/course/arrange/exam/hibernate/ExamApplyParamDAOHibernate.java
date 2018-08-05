package com.shufe.dao.course.arrange.exam.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.arrange.exam.ExamApplyParamDAO;
import com.shufe.model.course.arrange.exam.ExamApplyParam;
import com.shufe.model.std.Student;


public class ExamApplyParamDAOHibernate extends BasicHibernateDAO implements ExamApplyParamDAO {
    
    public List getExamApplyParam(Long calendarId) {
        String HQL = "from ExamApplyParam eap where eap.calendar.id="+calendarId;
        Query query = getSession().createQuery(HQL);
        return query.list();
    }

    public void removeExamApplyParam(Object[] idStr) {
        utilDao.remove(utilDao.load(ExamApplyParam.class, "id", idStr));
    }

    public void saveExamApplyParam(ExamApplyParam param) {
        getSessionFactory().evict(ExamApplyParam.class);
        saveOrUpdate(param);
    }
    
    public List getExamApplyParams(Student std) {
        Map params = new HashMap();
        return utilDao.searchNamedQuery("getExamApplyParams", params, true);
    }

}
