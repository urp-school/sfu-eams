//$Id: TeachCalendarDAOHibernate.java,v 1.8 2006/12/07 11:02:11 duanth Exp $
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
 * chaostone             2005-9-14         Created
 *  
 ********************************************************************************/

package com.shufe.dao.system.calendar.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.CalendarNotExistException;
import com.ekingstar.eams.system.time.TeachCalendarScheme;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.system.calendar.TeachCalendarDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.system.calendar.OnCampusTime;
import com.shufe.model.system.calendar.TeachCalendar;

public class TeachCalendarDAOHibernate extends BasicHibernateDAO implements TeachCalendarDAO {
    
    /**
     * 
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#getTeachCalendar(java.lang.Long)
     */
    public TeachCalendar getTeachCalendar(Long id) {
        try {
            TeachCalendar calendar = (TeachCalendar) load(TeachCalendar.class, id);
            return calendar;
        } catch (ObjectRetrievalFailureException e) {
            throw new PojoNotExistException("calendar not exist with id:" + id);
        }
    }
    
    /**
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#getCurTeachCalendars()
     */
    public List getCurTeachCalendars() {
        return find("getCurTeachCalendars");
    }
    
    public List getTeachCalendarsOfOverlapped(TeachCalendar calendar) {
        Query query = getSession().getNamedQuery("getCalendarsOfOverlapped");
        query.setParameter("start", calendar.getStart());
        query.setParameter("finish", calendar.getFinish());
        query.setParameter("stdTypeId", calendar.getStudentType().getId());
        query.setCacheable(true);
        return query.list();
    }
    
    /**
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#getTeachCalendars(com.shufe.model.system.calendar.TeachCalendar)
     */
    public List getTeachCalendars(TeachCalendar calendar) {
        return genTeachCalendarCriteria(getSession(), calendar).list();
    }
    
    /**
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#getCurTeachCalendar(java.lang.String)
     */
    public TeachCalendar getCurTeachCalendar(Long stdTypeId) {
        Map params = new HashMap();
        params.put("studentTypeId", stdTypeId);
        List rs = utilDao.searchNamedQuery("getCurTeachCalendar", params, true);
        if (rs.size() < 1){
         return null;
         //throw new CalendarNotExistException("without academicYear for student type id:"            + stdTypeId);
        }
        return (TeachCalendar) rs.get(0);
    }
    
    /**
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#getCurTeachCalendar(java.lang.String)
     */
    public TeachCalendar getNextTeachCalendar(Long stdTypeId) {
        Map params = new HashMap();
        params.put("studentTypeId", stdTypeId);
        List rs = find("getNextTeachCalendar", params);
        if (rs.size() < 1){
            return null;
        	//throw new CalendarNotExistException("without academicYear for student type id:"                  + stdTypeId);
        }
        return (TeachCalendar) rs.get(0);
    }
    
    public List getActiveEnrollTurns(TeachCalendar calendar, Long[] stdTypeIds) {
        String hql = "select distinct enrollTurn from OnCampusTime "
                + "where enrollCalendar.start <=:start and graduateCalendar.finish >=:finish"
                + " and stdType.id in(:stdTypeIds)";
        
        Query query = getSession().createQuery(hql);
        query.setParameter("start", calendar.getStart());
        query.setParameter("finish", calendar.getFinish());
        query.setParameterList("stdTypeIds", stdTypeIds);
        return query.list();
    }
    
    /**
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#getCurTeachCalendar(java.lang.String)
     */
    public TeachCalendar getPreviousTeachCalendar(Long stdTypeId) {
        Map params = new HashMap();
        params.put("studentTypeId", stdTypeId);
        List rs = find("getPreviousTeachCalendar", params);
        if (rs.size() < 1){
           return null;
           //throw new CalendarNotExistException("without academicYear for student type id:"                    + stdTypeId);
        }
        return (TeachCalendar) rs.get(0);
    }
    
    /**
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#removeTeachCalendar(java.lang.Long)
     */
    public void removeTeachCalendar(Long id) {
        remove(TeachCalendar.class, id);
    }
    
    public List getTeachCalendars() {
        return this.find("getAllTeachCalendars");
    }
    
    private OnCampusTime getOnCampusTimeOf(StudentType stdType, String enrollTurn) {
        Map params = new HashMap();
        params.put("stdTypeId", stdType.getId());
        params.put("enrollTurn", enrollTurn);
        List enrollCalendars = utilDao.searchNamedQuery("getOnCampusTime", params, true);
        if (enrollCalendars.isEmpty())
            return null;
        else
            return (OnCampusTime) enrollCalendars.get(0);
    }
    
    public OnCampusTime getOnCampusTime(StudentType stdType, String enrollTurn) {
        OnCampusTime time = getOnCampusTimeOf(stdType, enrollTurn);
        while (null == time) {
            stdType = (StudentType) stdType.getSuperType();
            if (null == stdType)
                return null;
            else
                time = getOnCampusTimeOf(stdType, enrollTurn);
        }
        return time;
    }
    
    /**
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#getOnCampusTimes(java.lang.String[], int,
     *      int)
     */
    public Collection getOnCampusTimes(Long[] stdTypeIds, PageLimit limit) {
        EntityQuery query = new EntityQuery(OnCampusTime.class, "time");
        query.add(new Condition("time.stdType.id  in (:stdTypeIds)", stdTypeIds));
        query.setLimit(limit);
        query.addOrder(new com.ekingstar.commons.query.Order("time.enrollTurn",
                com.ekingstar.commons.query.Order.DESC));
        return utilDao.search(query);
    }
    
    /**
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#getOnCampusTimes(java.lang.String[])
     */
    public List getOnCampusTimes(Long[] stdTypeIds) {
        Map params = new HashMap();
        params.put("stdTypeIds", stdTypeIds);
        return find("getOnCampusTimes", params);
    }
    
    /**
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#getOnCampusTime(java.lang.String)
     */
    public List getOnCampusTimesFor(Long stdTypeId) {
        Map params = new HashMap();
        params.put("stdTypeId", stdTypeId);
        return find("getOnCampusTimesFor", params);
    }
    
    /**
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#getAllStdTypes()
     */
    public List getAllStdTypes() {
        return find("getAllStdTypes");
    }
    
    /**
     * 产生一个教学日历的条件查询对象
     * 
     * @param session
     * @param calendar
     * @return
     */
    public static Criteria genTeachCalendarCriteria(Session session, TeachCalendar calendar) {
        Criteria criteria = session.createCriteria(TeachCalendar.class);
        if (null != calendar) {
            List criterions = CriterionUtils.getEntityCriterions(calendar);
            for (Iterator iter = criterions.iterator(); iter.hasNext();) {
                Criterion criterion = (Criterion) iter.next();
                criteria.add(criterion);
            }
        }
        criteria.addOrder(Order.desc("start"));
        return criteria;
    }
    
    /**
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#getStdTypesHasCalendar(java.lang.String[])
     */
    public List getStdTypesHasCalendar(Long[] stdTypeIds) {
        Map params = new HashMap();
        params.put("stdTypeIds", stdTypeIds);
        return find("getStdTypesHasCalendar", params);
    }
    
    public Integer getTermsBetween(TeachCalendar first, TeachCalendar second, Boolean omitSmallTerm) {
        Query query = getSession().getNamedQuery("getTermsBetween");
        query.setParameter("studentType", first.getStudentType());
        query.setParameter("omitSmallTerm", omitSmallTerm);
        if (first.after(second)) {
            query.setParameter("firstStart", second.getStart());
            query.setParameter("secondStart", first.getStart());
            return new Integer(-((Number) query.uniqueResult()).intValue());
        } else {
            query.setParameter("firstStart", first.getStart());
            query.setParameter("secondStart", second.getStart());
            return new Integer(((Number) query.uniqueResult()).intValue());
        }
    }
    
    public Map getWeekTimeMap(Long stdTypeId, String year, String term) {
        TeachCalendar calendar = getTeachCalendar(stdTypeId, year, term);
        if (null == calendar)
            return Collections.EMPTY_MAP;
        else {
            Map weekTimeMap = new HashMap();
            for (int i = 0; i < calendar.getWeeks().intValue(); i++) {
                weekTimeMap.put(new Integer(i), calendar.getWeekTime(i));
            }
            return weekTimeMap;
        }
    }
    
    public TeachCalendar getTeachCalendar(Long stdTypeId, String year, String term) {
        StudentType superType = (StudentType) utilDao.get(StudentType.class, stdTypeId);
        TeachCalendar calendar = null;
        while (null != superType && null == calendar) {
            List rs = getTeachCalendarDirectly(superType.getId(), year, term);
            if (rs.size() < 1) {
                superType = (StudentType) superType.getSuperType();
            } else {
                calendar = (TeachCalendar) rs.get(0);
                break;
            }
        }
        return calendar;
    }
    
    private List getTeachCalendarDirectly(Long stdTypeId, String year, String term) {
        StringBuffer buf = new StringBuffer(
                "from TeachCalendar as calendar where calendar.studentType.id=");
        buf.append(stdTypeId).append(" and calendar.year ='").append(year).append(
                "' and calendar.term='").append(term).append("'");
        Query query = getSession().createQuery(buf.toString());
        return query.list();
        
    }
    
    public List getTeachCalendarNames(Long schemeId) {
        TeachCalendarScheme scheme = (TeachCalendarScheme) utilDao.get(TeachCalendarScheme.class,
                schemeId);
        List calendars = scheme.getCalendars();
        List rs = new ArrayList();
        for (Iterator iter = calendars.iterator(); iter.hasNext();) {
            TeachCalendar calendar = (TeachCalendar) iter.next();
            Map data = new HashMap(2);
            data.put("id", calendar.getId());
            data.put("name", calendar.getYearTerm());
            rs.add(data);
        }
        return rs;
    }
    
    public List getTermsOrderByDistance(Long stdTypeId, String year) {
        StudentType stdType = (StudentType) utilDao.get(StudentType.class, stdTypeId);
        while (null != stdType) {
            Map params = new HashMap();
            params.put("studentTypeId", stdType.getId());
            params.put("year", year);
            List rs = utilDao.searchNamedQuery("getTermsOrderByDistance", params, true);
            if (rs.isEmpty()) {
                stdType = (StudentType) stdType.getSuperType();
                continue;
            } else {
                return rs;
            }
        }
        return Collections.EMPTY_LIST;
    }
    
    public List getYearsOrderByDistance(Long stdTypeId) {
        StudentType stdType = (StudentType) utilDao.get(StudentType.class, stdTypeId);
        while (null != stdType) {
            Map params = new HashMap();
            params.put("studentTypeId", stdType.getId());
            List years = utilDao.searchNamedQuery("getYearsOrderByDistance", params, true);
            if (years.isEmpty()) {
                stdType = (StudentType) stdType.getSuperType();
                continue;
            } else {
                List newYears = new ArrayList();
                for (Iterator iter = years.iterator(); iter.hasNext();) {
                    String year = (String) iter.next();
                    if (!newYears.contains(year)) {
                        newYears.add(year);
                    }
                }
                return newYears;
            }
        }
        return Collections.EMPTY_LIST;
    }
    
    public TeachCalendar getNearestCalendar(Long stdTypeId) {
        StudentType stdType = (StudentType) utilDao.get(StudentType.class, stdTypeId);
        while (null != stdType) {
            Map params = new HashMap();
            params.put("studentTypeId", stdType.getId());
            List calendars = utilDao.searchNamedQuery("getNearestCalendar", params, true);
            if (calendars.isEmpty()) {
                stdType = (StudentType) stdType.getSuperType();
                continue;
            } else {
                return (TeachCalendar) calendars.get(0);
            }
        }
        return null;
    }
    
    /**
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#saveTeachCalendar(com.shufe.model.system.calendar.TeachCalendar)
     */
    public void saveTeachCalendar(TeachCalendar calendar) {
        try {
            save(calendar);
        } catch (DataIntegrityViolationException e) {
            throw new PojoExistException("teacher already exits:" + calendar);
        }
    }
    
    /**
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#updateTeachCalendar(com.shufe.model.system.calendar.TeachCalendar)
     */
    public void updateTeachCalendar(TeachCalendar calendar) {
        update(calendar);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.system.calendar.TeachCalendarDAO#gerCalendarsByStdTypeIdSeq(java.lang.String)
     */
    public List gerCalendarsByStdTypeIdSeq(String stdTypeIdSeq) {
        Criteria criteria = getSession().createCriteria(TeachCalendar.class);
        if (StringUtils.isNotEmpty(stdTypeIdSeq)) {
            Long[] stdTypeIds = SeqStringUtil.transformToLong(stdTypeIdSeq);
            criteria.add(Restrictions.in("studentType.id", stdTypeIds));
        }
        return criteria.list();
    }
}
