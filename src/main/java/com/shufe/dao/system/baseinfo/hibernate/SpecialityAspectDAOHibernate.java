//$Id: SpecialityAspectDAOHibernate.java,v 1.1 2006/08/02 00:53:06 duanth Exp $
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

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.system.baseinfo.SpecialityAspectDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.dao.util.NotEmptyPropertySelector;
import com.shufe.model.system.baseinfo.SpecialityAspect;

public class SpecialityAspectDAOHibernate extends BasicHibernateDAO implements SpecialityAspectDAO {

  /**
   * @see SpecialityAspectDAO#getSpecialityAspect(String)
   */
  public SpecialityAspect getSpecialityAspect(Long id) {
    return (SpecialityAspect) load(SpecialityAspect.class, id);
  }

  /**
   * @see SpecialityAspectDAO#getSpecialityAspects()
   */
  public List getSpecialityAspects() {
    return genSpecialityAspectCriteria(getSession(), null).list();
  }

  /**
   * @see SpecialityAspectDAO#getSpecialityAspects(SpecialityAspect)
   */
  public List getSpecialityAspects(SpecialityAspect aspect) {
    return genSpecialityAspectCriteria(getSession(), aspect).list();
  }

  /**
   * @see SpecialityAspectDAO#getSpecialityAspects(SpecialityAspect, int, int)
   */
  public Pagination getSpecialityAspects(SpecialityAspect aspect, int pageNo, int pageSize) {
    Criteria criteria = genSpecialityAspectCriteria(getSession(), aspect);
    return dynaSearch(criteria, pageNo, pageSize);
  }

  /**
   * @see com.shufe.dao.system.baseinfo.SpecialityAspectDAODWRFacade#getSpecialityAspectNames(java.lang.Long)
   */
  public List getSpecialityAspectNames(Long specialityId) {
    if (null == specialityId) return Collections.EMPTY_LIST;
    else {
      String sql = "select d.id,d.code || d.name,d.en_name from edu_base.directions d "
          + " where d.end_on is null and d.major_id=" + specialityId + " order by d.name";
      Query query = getSession().createSQLQuery(sql);
      return query.list();
    }
  }

  public List getSpecialityAspectNames2(Long specialityId, Long stdTypeId) {
    if (null == specialityId) return Collections.EMPTY_LIST;
    else {
      Long educationId = null;
      if (null != stdTypeId) {
        if (stdTypeId.equals(5L)) educationId = 1L;
        else if (stdTypeId.equals(9L)) educationId = 2L;
        else if (stdTypeId.equals(1L)) educationId = 3L;
      }

      String sql = "select d.id,d.code || d.name,d.en_name from edu_base.directions d ,edu_base.direction_journals dj"
          + " where d.end_on is null and dj.end_on is null and dj.direction_id=d.id and d.major_id="
          + specialityId;

      if (null != educationId) {
        sql += " and dj.education_id=" + educationId;
      }
      sql += " order by d.name";

      Query query = getSession().createSQLQuery(sql);
      return query.list();
    }
  }

  /**
   * @see com.shufe.dao.system.baseinfo.SpecialityAspectDAO#getAllSpecialityAspects(com.shufe.model.system.baseinfo.SpecialityAspect,
   *      int, int)
   */
  public Pagination getAllSpecialityAspects(SpecialityAspect aspect, int pageNo, int pageSize) {
    Criteria criteria = genSpecialityAspectCriteria(getSession(), aspect);
    criteria.add(Restrictions.eq("state", true));
    return dynaSearch(criteria, pageNo, pageSize);
  }

  /**
   * @see SpecialityAspectDAO#removeSpecialityAspect(String)
   */
  public void removeSpecialityAspect(Long id) {
    remove(SpecialityAspect.class, id);
  }

  /**
   * 根据对象构造一个动态查询
   * 
   * @param direction
   * @return
   */
  public static Criteria genSpecialityAspectCriteria(Session session, SpecialityAspect direction) {

    Criteria criteria = session.createCriteria(SpecialityAspect.class);
    if (null != direction) {
      criteria.add(Example.create(direction).setPropertySelector(new NotEmptyPropertySelector())
          .excludeProperty("name").excludeProperty("code"));
      if (null != direction.getId()) {
        criteria.add(Restrictions.eq("id", direction.getId()));
      }
      if (StringUtils.isNotEmpty(direction.getCode())) {
        criteria.add(Restrictions.like("code", direction.getCode(), MatchMode.ANYWHERE));
      }
      if (StringUtils.isNotEmpty(direction.getName())) {
        criteria.add(Restrictions.like("name", direction.getName(), MatchMode.ANYWHERE));
      }
      // 查找同一部门的所有专业方向
      List specialityCriterions = CriterionUtils
          .getEntityCriterions("speciality.", direction.getSpeciality());
      if (!specialityCriterions.isEmpty()) {
        Criteria specialityCriteria = criteria.createCriteria("speciality", "speciality");
        for (Iterator iter = specialityCriterions.iterator(); iter.hasNext();)
          specialityCriteria.add((Criterion) iter.next());
      }
    }
    criteria.add(Restrictions.eq("state", true));
    return criteria;
  }
}
