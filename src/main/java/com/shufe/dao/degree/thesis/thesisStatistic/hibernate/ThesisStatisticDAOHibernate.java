//$Id: ThesisStatisticDAOHibernate.java,v 1.2 2006/11/28 11:20:10 duanth Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong         2006-11-28          Created
 * zq                   2007-09-18          修改了getstdGroupInfo()方法
 ********************************************************************************/

package com.shufe.dao.degree.thesis.thesisStatistic.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.thesis.ThesisManageDAO;
import com.shufe.dao.degree.thesis.thesisStatistic.ThesisStatisticDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.std.Student;

public class ThesisStatisticDAOHibernate extends BasicHibernateDAO implements ThesisStatisticDAO {
    
    private ThesisManageDAO thesisManageDAO;
    
    public List getstdGroupInfo(Student student, String departmentIdSeq, String stdTypeIdSeq) {
        Criteria criteria = getSession().createCriteria(Student.class);
        List criterions = CriterionUtils.getEntityCriterions("", student, new String[] { "type" });
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            Criterion criterion = (Criterion) iter.next();
            criteria.add(criterion);
        }
        if (StringUtils.isNotBlank(departmentIdSeq)) {
            criteria.add(Restrictions.in("department.id", SeqStringUtil
                    .transformToLong(departmentIdSeq)));
        }
        stdTypeIdSeq = intersectStdTypeIdSeq(student.getType(), stdTypeIdSeq);
        if (StringUtils.isNotBlank(stdTypeIdSeq)) {
            criteria.add(Restrictions.in("type.id", SeqStringUtil.transformToLong(stdTypeIdSeq)));
        }
        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()).add(
                Projections.groupProperty("department")).add(Projections.groupProperty("type")));
        return criteria.list();
    }
    
    public List getTOpicOpenGroupInfo(ThesisManage thesisManage, String departmentIdSeq,
            String stdTypeIdSeq) {
        Criteria criteria = thesisManageDAO.getThesisManageCriteria(thesisManage, departmentIdSeq,
                stdTypeIdSeq);
        criteria.add(Restrictions.isNotNull("topicOpen"));
        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()).add(
                Projections.groupProperty("student.department.id")).add(
                Projections.groupProperty("student.type.id")));
        return criteria.list();
    }
    
    /**
     * @param thesisManageDAO
     *            The thesisManageDAO to set.
     */
    public void setThesisManageDAO(ThesisManageDAO thesisManageDAO) {
        this.thesisManageDAO = thesisManageDAO;
    }
    
    /*
     * 统计
     * 
     * @see com.shufe.dao.degree.thesis.thesisStatistic.ThesisStatisticDAO#getThesisAndTeachers(java.lang.String,
     *      java.util.Set)
     */
    public List getThesisAndTeachers(String stdTypeIdSeq, Set stdTypes, String requireYear) {
        Criteria criteria = getSession().createCriteria(ThesisManage.class);
        criteria.createAlias("student", "student");
        criteria.createAlias("student.teacher", "teacher");
        if (StringUtils.isNotEmpty(stdTypeIdSeq)) {
            criteria.add(Restrictions.in("student.type.id", SeqStringUtil
                    .transformToLong(stdTypeIdSeq)));
        }
        if (stdTypes.size() > 0) {
            criteria.add(Restrictions.in("student.type", stdTypes));
        }
        if (StringUtils.isNotBlank(requireYear)) {
            criteria.add(Restrictions.ge("student.enrollYear", requireYear));
        }
        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()).add(
                Projections.countDistinct("student.teacher")).add(
                Projections.groupProperty("student.enrollYear")).add(
                Projections.groupProperty("teacher.title")));
        return criteria.list();
    }
    
}
