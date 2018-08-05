//$Id: SpecialityDAOHibernate.java,v 1.2 2007/01/05 01:22:42 duanth Exp $
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
 * chaostone             2005-9-15         Created
 *  
 ********************************************************************************/

package com.shufe.dao.system.baseinfo.hibernate;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.system.baseinfo.SpecialityDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.system.baseinfo.Speciality;

public class SpecialityDAOHibernate extends BasicHibernateDAO implements SpecialityDAO {

  /**
   * @see SpecialityDAO#getSpeciality(String)
   */
  public Speciality getSpeciality(Long id) {
    return (Speciality) load(Speciality.class, id);
  }

  /**
   * @see SpecialityDAO#getSpecialities()
   */
  public List getSpecialities() {
    return genSpecialityCriteria(getSession(), null).list();
  }

  /**
   * @see SpecialityDAO#getSpecialities(speciality)
   */
  public List getSpecialities(Speciality speciality) {
    return genSpecialityCriteria(getSession(), speciality).list();
  }

  /**
   * @see SpecialityDAO#getSpecialities(Speciality, int, int)
   */
  public Pagination getSpecialities(Speciality speciality, int pageNo, int pageSize) {
    Criteria criteria = genSpecialityCriteria(getSession(), speciality);
    return dynaSearch(criteria, pageNo, pageSize);
  }

  public Pagination getSpecialities(Speciality speciality, Long[] stdTypeIds, Long[] departIds, int pageNo,
      int pageSize) {
    Criteria criteria = genSpecialityCriteria(getSession(), speciality);
    criteria.add(Restrictions.in("stdType.id", stdTypeIds));
    criteria.add(Restrictions.in("department.id", departIds));
    return dynaSearch(criteria, pageNo, pageSize);
  }

  public List getSpecialities(Speciality speciality, Long[] stdTypeIds, Long[] departIds) {
    Criteria criteria = genSpecialityCriteria(getSession(), speciality);
    criteria.add(Restrictions.in("stdType.id", stdTypeIds));
    criteria.add(Restrictions.in("department.id", departIds));
    return criteria.list();
  }

  /**
   * @see com.shufe.dao.system.baseinfo.SpecialityDAODWRFacade#getSpecialityNames(java.lang.Long,
   *      java.lang.Long)
   */
  public List getSpecialityNames(Long departId, Long stdTypeId, Long majorTypeId) {
    if (null == departId) return Collections.EMPTY_LIST;
    StringBuffer sql = new StringBuffer(
        "select m.id,m.code ||m.name,m.en_name from edu_base.majors m,edu_base.major_journals mj"
            + " where  mj.major_id=m.id and mj.end_on is null and mj.depart_id=" + departId);
    if (null != majorTypeId) {
      sql.append(" and m.project_id=" + majorTypeId);
    }
    if (null != stdTypeId) {
      Long educationId = null;
      if (stdTypeId.equals(5L)) educationId = 1L;
      else if (stdTypeId.equals(9L)) educationId = 2L;
      else if (stdTypeId.equals(1L)) educationId = 3L;
      if (null != educationId) sql.append(" and mj.education_id=" + educationId);
    }
    sql.append(" order by m.name");
    Query query = getSession().createSQLQuery(sql.toString());
    return query.list();

    // StringBuffer hql = new StringBuffer("select s.id,s.name,s.engName"
    // + " from Speciality as s where s.state=true and s.department.id=:departId");
    // if (null != majorTypeId) {
    // hql.append(" and s.majorType.id=:majorTypeId");
    // }
    // if (null != stdTypeId) hql.append(" and s.stdType.id=:stdTypeId");
    // hql.append(" order by s.name");
    // Query query = getSession().createQuery(hql.toString());
    // query.setParameter("departId", departId);
    // if (null != majorTypeId) {
    // query.setParameter("majorTypeId", majorTypeId);
    // }
    // // 级联查找,首先查找上级的基础类别
    // if (null != stdTypeId) {
    // StudentType type = (StudentType) get(StudentType.class, stdTypeId);
    // if (null == type) {
    // return Collections.EMPTY_LIST;
    // } else {
    // List rs = Collections.EMPTY_LIST;
    // while (type != null) {
    // query.setParameter("stdTypeId", type.getId());
    // rs = query.list();
    // if (!rs.isEmpty()) {
    // return rs;
    // } else {
    // if (type.getSuperType() == type) {
    // return rs;
    // } else {
    // type = (StudentType) type.getSuperType();
    // }
    // }
    // }
    // return rs;
    // }
    // }
    // throw new RuntimeException();
  }

  /**
   * @see SpecialityDAO#getSpecialities(Speciality, int, int)
   */
  public Pagination getAllSpecialities(Speciality speciality, int pageNo, int pageSize) {
    Criteria criteria = genSpecialityCriteria(getSession(), speciality);
    return dynaSearch(criteria, pageNo, pageSize);
  }

  /**
   * @see SpecialityDAO#removeSpeciality(String)
   */
  public void removeSpeciality(Long id) {
    remove(Speciality.class, id);
  }

  /**
   * @see com.shufe.dao.system.baseinfo.SpecialityDAO#getSpecialitiesByDepartmentIds(java.lang.String[])
   */
  public List getSpecialitiesByDepartmentIds(Long[] departmentIds) {
    Criteria criteria = getSession().createCriteria(Speciality.class);
    criteria.add(Restrictions.in("department.id", departmentIds));
    return criteria.list();
  }

  /**
   * 根据对象构造一个动态查询
   * 
   * @param speciality
   * @return
   */
  public static Criteria genSpecialityCriteria(Session session, Speciality speciality) {
    Criteria criteria = session.createCriteria(Speciality.class);
    List criterions = CriterionUtils.getEntityCriterions(speciality);
    for (Iterator iter = criterions.iterator(); iter.hasNext();) {
      criteria.add((Criterion) iter.next());
    }
    criteria.add(Restrictions.eq("state", true));
    return criteria;
  }
}
