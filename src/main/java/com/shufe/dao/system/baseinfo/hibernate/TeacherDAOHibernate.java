//$Id: TeacherDAOHibernate.java,v 1.8 2007/01/04 08:09:35 duanth Exp $
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
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2005-10-28         Created
 *  
 ********************************************************************************/

package com.shufe.dao.system.baseinfo.hibernate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.system.baseinfo.TeacherDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.util.DataRealmLimit;

/**
 * 教师信息存取实现
 * 
 * @author chaostone 2005-10-28
 */
public class TeacherDAOHibernate extends BasicHibernateDAO implements TeacherDAO {
    
    /**
     * @see com.shufe.dao.system.baseinfo.TeacherDAO#getTeacherById(java.lang.String)
     */
    public Teacher getTeacherById(Long id) {
        Teacher teacher = (Teacher) getHibernateTemplate().get(Teacher.class, id);
        if (null == teacher) {
            Teacher tea = (Teacher) load(Teacher.class, id);
            return tea;
        }
        return teacher;
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.TeacherDAO#getTeacherByNO(java.lang.String)
     */
    public Teacher getTeacherByNO(String TeacherNO) {
        List teachers = getExampleCriteria(getSession(), null).add(
                Restrictions.eq("code", TeacherNO)).list();
        if (teachers.isEmpty())
            return null;
        else
            return (Teacher) teachers.get(0);
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.TeacherDAO#getTeachers()
     */
    public List getTeachers() {
        Criteria criteria = getExampleCriteria(getSession(), null);
        return criteria.list();
    }
    
    public Collection getTeachers(Teacher teacher, DataRealmLimit limit, List sortList) {
        Criteria criteria = getExampleCriteria(getSession(), teacher);
        limit.getDataRealm().setStudentTypeIdSeq(null);
        addDataRealmLimt(criteria, new String[] { "", "department.id" }, limit);
        addSortListFor(criteria, sortList);
        if (null != limit || null != limit.getPageLimit()) {
            return dynaSearch(criteria, limit.getPageLimit());
        } else
            return criteria.list();
        
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.TeacherDAODWRFacade#getTeacherNamesByDepart(java.lang.Long)
     */
    public List getTeacherNamesByDepart(Long departId, Boolean isTeaching) {
        String teachingCondition = "";
        if (null != isTeaching) {
            if (Boolean.TRUE.equals(isTeaching)) {
                teachingCondition = "and teacher.isTeaching=true ";
            }
            if (Boolean.FALSE.equals(isTeaching)) {
                teachingCondition = "and teacher.isTeaching=false ";
            }
        }
        String hql = "select teacher.id,teacher.name,teacher.code from Teacher as teacher"
                + " where teacher.department.id =" + departId + teachingCondition
                + " order by teacher.name";
        Query query = getSession().createQuery(hql);
        return query.list();
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.TeacherDAO#getTeachers(com.shufe.model.system.baseinfo.Teacher)
     */
    public List getTeachers(Teacher teacher) {
        return getExampleCriteria(getSession(), teacher).list();
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.TeacherDAO#getTeachersByDepartment(java.lang.String[])
     */
    public List getTeachersByDepartment(Long[] departIds) {
        return getExampleCriteria(getSession(), null).add(
                Restrictions.in("department.id", departIds)).list();
        
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.TeacherDAO#getTeachersById(java.lang.String[])
     */
    public List getTeachersById(Long[] teacherIds) {
        return getExampleCriteria(getSession(), null).add(Restrictions.in("id", teacherIds)).list();
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.TeacherDAO#getTeachersById(java.util.Collection)
     */
    public List getTeachersById(Collection teacherIds) {
        return getExampleCriteria(getSession(), null).add(Restrictions.in("id", teacherIds)).list();
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.TeacherDAO#getTeachersByNO(java.lang.String[])
     */
    public List getTeachersByNO(String[] teacherNOs) {
        return getExampleCriteria(getSession(), null).add(Restrictions.in("code", teacherNOs))
                .list();
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.TeacherDAO#getTeachers(com.shufe.model.system.baseinfo.Teacher,
     *      int, int)
     */
    public Pagination getTeachers(Teacher teacher, int pageNo, int pageSize) {
        return dynaSearch(getExampleCriteria(getSession(), teacher), pageNo, pageSize);
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.TeacherDAO#getTeacherNames(java.lang.String)
     */
    public List getTeacherNames(Long[] teacherIds) {
        Map params = new HashMap();
        params.put("teacherIds", teacherIds);
        return find("getTeacherNames", params);
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.TeacherDAO#getTeachers(com.shufe.model.system.baseinfo.Teacher,
     *      java.lang.String[], int, int)
     */
    public Pagination getTeachers(final Teacher teacher, Long[] departIds, int pageNo,
            int pageSize) {
        Criteria criteria = genTeacherInDepartCriteria(teacher, departIds);
        return dynaSearch(criteria, pageNo, pageSize);
    }
    
    /**
     * @param teacher
     * @param departIds
     * @return
     */
    private Criteria genTeacherInDepartCriteria(final Teacher teacher, Long[] departIds) {
        Criteria criteria = getSession().createCriteria(Teacher.class);
        List criterions = CriterionUtils.getEntityCriterions(teacher);
        if (null != departIds)
            criterions.add(Restrictions.in("department.id", departIds));
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            Criterion element = (Criterion) iter.next();
            criteria.add(element);
        }
        return criteria;
    }
    
    public List getTeachers(Teacher teacher, Long[] departIds) {
        return genTeacherInDepartCriteria(teacher, departIds).list();
    }
    
    /**
     * @see TeacherDAO#removeTeacher(String)
     */
    public void removeTeacher(String id) {
        remove(Teacher.class, id);
    }
    
    /**
     * 根据对象构造一个动态查询
     * 
     * @param teacher
     * @return
     */
    public static Criteria getExampleCriteria(Session session, Teacher teacher) {
        Criteria criteria = session.createCriteria(Teacher.class);
        if (null != teacher) {
            List criterions = CriterionUtils.getEntityCriterions(teacher);
            for (Iterator iter = criterions.iterator(); iter.hasNext();) {
                Criterion element = (Criterion) iter.next();
                criteria.add(element);
            }
        }
        return criteria;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.system.baseinfo.TeacherDAO#getTeacherIdsByTeacher(com.shufe.model.system.baseinfo.Teacher,
     *      java.lang.String)
     */
    public List getTeacherIdsByTeacher(Teacher teacher, String deparmentsq) {
        Long[] departmentIds = SeqStringUtil.transformToLong(deparmentsq);
        Criteria criteria = genTeacherInDepartCriteria(teacher, departmentIds);
        criteria.setProjection(Property.forName("id"));
        return criteria.list();
    }
}
