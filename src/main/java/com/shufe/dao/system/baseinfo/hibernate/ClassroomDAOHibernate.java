//$Id: ClassroomDAOHibernate.java,v 1.3 2006/11/24 00:53:26 duanth Exp $
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.hibernate.HibernateQuerySupport;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.system.baseinfo.ClassroomDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.system.baseinfo.Classroom;

public class ClassroomDAOHibernate extends BasicHibernateDAO implements ClassroomDAO {
    
    /**
     * @see ClassroomDAO#getClassroom(String)
     */
    public Classroom getClassroom(Long id) {
        Classroom room = (Classroom) getHibernateTemplate().get(Classroom.class, id);
        if (null == room)
            return (Classroom) load(Classroom.class, id);
        return room;
    }
    
    /**
     * @see ClassroomDAO#getClassrooms()
     */
    public List getClassrooms() {
        return genClassroomCriteria(getSession(), null).list();
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.ClassroomDAO#getAllClassrooms()
     */
    public List getAllClassrooms() {
        Criteria criteria = genClassroomCriteria(getSession(), null);
        return criteria.list();
    }
    
    /**
     * @see ClassroomDAO#getClassrooms(Classroom)
     */
    public List getClassrooms(Classroom classroom) {
        return genClassroomCriteria(getSession(), classroom).list();
    }
    
    /**
     * @see ClassroomDAO#getClassrooms(Classroom, int, int)
     */
    public Pagination getClassrooms(Classroom classroom, int pageNo, int pageSize) {
        Criteria criteria = genClassroomCriteria(getSession(), classroom);
        criteria.add(Restrictions.eq("state", true));
        return dynaSearch(criteria, pageNo, pageSize);
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.ClassroomDAO#getClassrooms(java.util.Collection)
     */
    public List getClassrooms(Collection roomIds) {
//        getSession().enableFilter("validClassroom");
        return getSession().getNamedQuery("getClassroomsByIdsSorted").setParameterList("roomIds",
                roomIds).list();
        
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.ClassroomDAO#getClassrooms(com.shufe.model.system.baseinfo.Classroom,
     *      java.lang.String[], int, int)
     */
    public Pagination getClassrooms(Classroom room, Long[] departIds, int pageNo, int pageSize) {
        Map params = new HashMap();
        String queryString = genClassroomOfDepartQuery(room, departIds, params);
//        getSession().enableFilter("validClassroom");
        return search(queryString, params, pageNo, pageSize);
    }
    
    /**
     * @param room
     * @param departIds
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getAllClassrooms(Classroom room, Long[] departIds, int pageNo,
            int pageSize) {
        Map params = new HashMap();
        String queryString = genClassroomOfDepartQuery(room, departIds, params);
        return search(queryString, params, pageNo, pageSize);
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.ClassroomDAO#getClassrooms(java.util.Collection,
     *      java.lang.Long[])
     */
    public List getClassrooms(Collection configTypeList, Long[] departIds) {
        Map params = new HashMap();
        String queryString = genClassroomOfDepartQuery(null, departIds, params);
        if (!configTypeList.isEmpty()) {
            params.put("configTypes", configTypeList);
            queryString += " and room.configType in (:configTypes)";
        }
        Query query = getSession().createQuery(queryString);
        HibernateQuerySupport.setParameter(query, params);
        return query.list();
    }
    
    private String genClassroomOfDepartQuery(Classroom room, Long[] departIds, Map params) {
        String roomQueryHsql = "select distinct room from Classroom as room join room.departments as depart ";
        Vector criterions = new Vector();
        if (null != room) {
            if (ValidEntityPredicate.INSTANCE.evaluate(room.getSchoolDistrict())) {
                criterions.add("(room.schoolDistrict.id = :districtId)");
                params.put("districtId", room.getSchoolDistrict().getId());
            }
            if (null != room.getId()) {
                criterions.add("(room.id = :id)");
                params.put("id", room.getId());
            }
            if (StringUtils.isNotEmpty(room.getCode())) {
                criterions.add("(room.code like :code)");
                params.put("code", "%" + room.getCode() + "%");
            }
            if (StringUtils.isNotEmpty(room.getName())) {
                criterions.add("(room.name like :name)");
                params.put("name", "%" + room.getName() + "%");
            }
            if (null != room.getConfigType() && room.getConfigType().isPO()) {
                criterions.add("(room.configType.id = :configId)");
                params.put("configId", room.getConfigType().getId());
            }
            
            if (ValidEntityPredicate.INSTANCE.evaluate(room.getBuilding())) {
                criterions.add("(room.building.id = :buildingId)");
                params.put("buildingId", room.getBuilding().getId());
            }
        }
        if (departIds != null) {
            criterions.add("(depart.id in (:departIds))");
            params.put("departIds", departIds);
        }
        
        roomQueryHsql += " where ";
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            roomQueryHsql += (String) iter.next() + " and ";
        }
        roomQueryHsql += " (1=1) ";
        return roomQueryHsql;
    }
    
    /**
     * @see com.shufe.dao.system.baseinfo.ClassroomDAO#getClassrooms(com.shufe.model.system.baseinfo.Classroom,
     *      java.lang.String[])
     */
    public List getClassrooms(Classroom classroom, Long[] departIds) {
        Map params = new HashMap();
        String queryString = genClassroomOfDepartQuery(classroom, departIds, params);
        Query query = getSession().createQuery(queryString);
        HibernateQuerySupport.setParameter(query, params);
//        getSession().enableFilter("validClassroom");
        return query.list();
        
    }
    
    public List getClassrooms(Long[] departIds) {
        Query query = getSession().getNamedQuery("getClassroomsOfDepart").setParameterList(
                "departIds", departIds);
//        getSession().enableFilter("validClassroom");
        return query.list();
        
    }
    
    /**
     * @see ClassroomDAO#removeClassroom(Long)
     */
    public void removeClassroom(Long id) {
        remove(Classroom.class, id);
    }
    
    /**
     * 根据对象构造一个动态查询
     * 
     * @param room
     * @return
     */
    public static Criteria genClassroomCriteria(Session session, Classroom room) {
        Criteria criteria = session.createCriteria(Classroom.class);
        if (null != room) {
            List criterions = CriterionUtils.getEntityCriterions(room);
            for (Iterator iter = criterions.iterator(); iter.hasNext();) {
                criteria.add((Criterion) iter.next());
            }
            // if(room.getDepartments())
            // 查找同一校区的所有教室
            if (ValidEntityPredicate.INSTANCE.evaluate(room.getBuilding())
                    && ValidEntityPredicate.INSTANCE.evaluate(room.getBuilding()
                            .getSchoolDistrict())) {
                criteria.createAlias("building.schoolDistrict", "district").add(
                        Restrictions.eq("district.id", room.getBuilding().getSchoolDistrict()
                                .getId()));
            }
        }
//        session.enableFilter("validClassroom");
        criteria.add(Restrictions.eq("state", true));
        return criteria;
    }
    
    public List getClassroomIdsByClassroom(Classroom classroom, String departIds) {
        Long[] departId = SeqStringUtil.transformToLong(departIds);
        Criteria criteria = getSession().createCriteria(Classroom.class);
        List criterions = CriterionUtils.getEntityCriterions(classroom);
        CriterionUtils.addCriterionsFor(criteria, criterions);
        
        if (null != departId) {
            criteria.createCriteria("departments", "depart").add(Restrictions.in("id", departId));
        }
        criteria.setProjection(Property.forName("id"));
        return criteria.list();
    }
    
}
