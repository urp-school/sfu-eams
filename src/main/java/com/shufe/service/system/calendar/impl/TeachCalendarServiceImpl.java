//$Id: TeachCalendarServiceImpl.java,v 1.8 2007/01/24 03:25:17 duanth Exp $
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
 * chaostone             2005-9-13         Created
 *  Hc					 2005-9-17	       add getCalendar method	
 *  
 ********************************************************************************/

package com.shufe.service.system.calendar.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.system.calendar.TeachCalendarDAO;
import com.shufe.model.system.calendar.OnCampusTime;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.system.calendar.TeachCalendarService;

public class TeachCalendarServiceImpl extends BasicService implements TeachCalendarService {
    
    private TeachCalendarDAO teachCalendarDAO = null;
    
    /**
     * @see com.shufe.service.system.calendar.TeachCalendarService#getTeachCalendar(java.lang.Long)
     */
    public TeachCalendar getTeachCalendar(Long id) {
        if (null == id) {
            return null;
        }
        try {
            TeachCalendar calendar = teachCalendarDAO.getTeachCalendar(id);
            calendar.getYear();
            // utilDao.initEntity(calendar);
            return calendar;
        } catch (PojoNotExistException e) {
            return null;
        }
    }
    
    /**
     * @see com.shufe.service.system.calendar.TeachCalendarService#getTeachCalendars(com.shufe.model.system.calendar.TeachCalendar)
     */
    public List getTeachCalendars(TeachCalendar calendar) {
        return teachCalendarDAO.getTeachCalendars(calendar);
    }
    
    public List getTeachCalendarsOfOverlapped(TeachCalendar calendar) {
        if (null != calendar.getStart() && null != calendar.getFinish()) {
            List calendars = teachCalendarDAO.getTeachCalendarsOfOverlapped(calendar);
            calendars.add(utilDao.get(TeachCalendar.class, calendar.getId()));
            return calendars;
        } else {
            return Collections.singletonList(utilDao.get(TeachCalendar.class, calendar.getId()));
        }
    }
    
    /**
     * @see com.shufe.service.system.calendar.TeachCalendarService#getCurTeachCalendars()
     */
    public List getCurTeachCalendars() {
        return teachCalendarDAO.getCurTeachCalendars();
    }
    
    /**
     * @see com.shufe.service.system.calendar.TeachCalendarService#getTeachCalendar(com.shufe.model.system.baseinfo.StudentType,
     *      java.lang.Integer, java.lang.Integer)
     */
    public TeachCalendar getTeachCalendar(Long stdTypeId, String year, String term) {
        if (null == stdTypeId || StringUtils.isEmpty(year) || StringUtils.isEmpty(term)) {
            return null;
        } else {
            return teachCalendarDAO.getTeachCalendar(stdTypeId, year, term);
        }
    }
    
    public TeachCalendar getTeachCalendar(StudentType stdType, String year, String term) {
        return getTeachCalendar(stdType.getId(), year, term);
    }
    
    private List getCalendars(StudentType stdType) {
        EntityQuery query = new EntityQuery(TeachCalendar.class, "c");
        query.add(new Condition("c.studentType=:stdType", stdType));
        query.addOrder(new Order("c.start desc"));
        query.setCacheable(true);
        return (List) utilDao.search(query);
    }
    
    public List getTeachCalendars(StudentType stdType) {
        while (null != stdType && getCalendars(stdType).isEmpty()) {
            stdType = (StudentType) stdType.getSuperType();
        }
        if (null == stdType) {
            return Collections.EMPTY_LIST;
        } else {
            return new ArrayList(getCalendars(stdType));
        }
    }
    
    /**
     * @see com.shufe.service.system.calendar.TeachCalendarService#getCurTeachCalendar(com.shufe.model.system.baseinfo.StudentType)
     */
    public TeachCalendar getCurTeachCalendar(StudentType stdType) {
        if (null == stdType || stdType.isVO()) {
            return null;
        } else {
            TeachCalendar calendar = null;
            do {
                try {
                    calendar = teachCalendarDAO.getCurTeachCalendar(stdType.getId());
                } catch (PojoNotExistException e) {
                    calendar = null;
                    // TODO for why
                }
                if (calendar == null) {
                    stdType = (StudentType) stdType.getSuperType();
                }
            } while (stdType != null && calendar == null);
            return calendar;
        }
    }
    
    public TeachCalendar getNearestCalendar(StudentType stdType) {
        return teachCalendarDAO.getNearestCalendar(stdType.getId());
    }
    
    public TeachCalendar getNextTeachCalendar(StudentType stdType) {
        if (null == stdType || stdType.isVO()) {
            return null;
        } else {
            TeachCalendar calendar = null;
            try {
                calendar = teachCalendarDAO.getNextTeachCalendar(stdType.getId());
            } catch (PojoNotExistException e) {
                calendar = null;
            }
            if (null == calendar) {
                return getNextTeachCalendar((StudentType) stdType.getSuperType());
            }
            return calendar;
        }
    }
    
    public TeachCalendar getPreviousTeachCalendar(StudentType stdType) {
        
        if (null == stdType || stdType.isVO()) {
            return null;
        } else {
            TeachCalendar calendar = null;
            try {
                calendar = teachCalendarDAO.getPreviousTeachCalendar(stdType.getId());
            } catch (PojoNotExistException e) {
                calendar = null;
            }
            if (null == calendar) {
                return getPreviousTeachCalendar((StudentType) stdType.getSuperType());
            }
            
            return calendar;
        }
    }
    
    public TeachCalendar getCurTeachCalendar(Long stdTypeId) {
        StudentType stdType = (StudentType) utilDao.load(StudentType.class, stdTypeId);
        return getCurTeachCalendar(stdType);
    }
    
    public TeachCalendar getNextTeachCalendar(Long stdTypeId) {
        StudentType stdType = (StudentType) utilDao.load(StudentType.class, stdTypeId);
        return getNextTeachCalendar(stdType);
    }
    
    public TeachCalendar getPreviousTeachCalendar(Long stdTypeId) {
        StudentType stdType = (StudentType) utilDao.load(StudentType.class, stdTypeId);
        return getPreviousTeachCalendar(stdType);
    }
    
    public TeachCalendar getTeachCalendar(StudentType stdType) {
        // TODO for reason
        // utilService.initialize(stdType.getTeachCalendars());
        TeachCalendar calendar = getCurTeachCalendar(stdType);
        if (null == calendar) {
            calendar = getNextTeachCalendar(stdType);
        }
        if (null == calendar) {
            calendar = getPreviousTeachCalendar(stdType);
        }
        return calendar;
    }
    
    public List getTeachCalendars() {
        return teachCalendarDAO.getTeachCalendars();
    }
    
    /**
     * @see com.shufe.service.system.calendar.TeachCalendarService#getTeachCalendars(java.lang.String,
     *      int, int)
     */
    public Collection getTeachCalendars(String stdTypeIdSeq, PageLimit limit) {
        if (StringUtils.isNotBlank(stdTypeIdSeq)) {
            EntityQuery query = new EntityQuery(TeachCalendar.class, "teachCalendar");
            query.add(new Condition("teachCalendar.studentType.id in(:stdTypeIds)", SeqStringUtil
                    .transformToLong(stdTypeIdSeq)));
            query.addOrder(new Order("teachCalendar.start desc"));
            query.setLimit(limit);
            return utilDao.search(query);
        } else
            return Collections.EMPTY_LIST;
    }
    
    /**
     * @see TeachCalendarService#getOnCampusTimesFor(StudentType, int, int)
     */
    public Collection getOnCampusTimesFor(StudentType stdType, PageLimit limit) {
        if (stdType.isPO()) {
            return teachCalendarDAO.getOnCampusTimes(new Long[] { stdType.getId() }, limit);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
    
    public List getActiveEnrollTurns(TeachCalendar calendar, String stdTypeIds) {
        if (calendar.isPO()) {
            return teachCalendarDAO.getActiveEnrollTurns(calendar, SeqStringUtil
                    .transformToLong(stdTypeIds));
        } else {
            return Collections.EMPTY_LIST;
        }
    }
    
    /**
     * @see TeachCalendarService#getOnCampusTime(StudentType, String)
     */
    public OnCampusTime getOnCampusTime(StudentType stdType, String enrollTurn) {
        if (stdType.isVO() || StringUtils.isEmpty(enrollTurn)) {
            return null;
        }
        return teachCalendarDAO.getOnCampusTime(stdType, enrollTurn);
    }
    
    /**
     * @see com.shufe.service.system.calendar.TeachCalendarService#termsBetween(com.shufe.model.system.calendar.TeachCalendar,
     *      com.shufe.model.system.calendar.TeachCalendar)
     */
    public Integer getTermsBetween(TeachCalendar first, TeachCalendar second, Boolean omitSmallTerm) {
        if (first.getStudentType().equals(second.getStudentType())) {
            return teachCalendarDAO.getTermsBetween(first, second, omitSmallTerm);
        } else {
            return null;
        }
    }
    
    /**
     * @see com.shufe.service.system.calendar.TeachCalendarService#removeTeachCalendar(com.shufe.model.system.calendar.TeachCalendar)
     */
    public void removeTeachCalendar(TeachCalendar calendar) {
        TeachCalendar preCalendar = calendar.getPrevious();
        TeachCalendar nextCalendar = calendar.getNext();
        if (null != preCalendar) {
            preCalendar.setNext(nextCalendar);
            updateTeachCalendar(preCalendar);
        }
        if (null != nextCalendar) {
            nextCalendar.setPrevious(preCalendar);
            updateTeachCalendar(nextCalendar);
        }
        teachCalendarDAO.remove(calendar);
    }
    
    /**
     * @see com.shufe.service.system.calendar.TeachCalendarService#saveTeachCalendarWithPrevious(TeachCalendar,
     *      TeachCalendar)
     */
    public void saveTeachCalendarWithPrevious(TeachCalendar calendar, TeachCalendar preCalendar) {
        calendar.calcWeek();
        TeachCalendar next = null;
        if (null != preCalendar) {
            calendar.setPrevious(preCalendar);
            calendar.setNext(preCalendar.getNext());
            next = preCalendar.getNext();
            if (null != next) {
                next.setPrevious(calendar);
            }
            preCalendar.setNext(calendar);
        }
        teachCalendarDAO.saveTeachCalendar(calendar);
        if (null != preCalendar) {
            teachCalendarDAO.update(preCalendar);
        }
        if (null != next) {
            teachCalendarDAO.update(next);
        }
    }
    
    /**
     * @see com.shufe.service.system.calendar.TeachCalendarService#saveTeachCalendarWithNext(TeachCalendar,
     *      TeachCalendar)
     */
    public void saveTeachCalendarWithNext(TeachCalendar calendar, TeachCalendar nextCalendar) {
        calendar.calcWeek();
        TeachCalendar pre = null;
        if (null != nextCalendar) {
            calendar.setNext(nextCalendar);
            calendar.setPrevious(nextCalendar.getPrevious());
            pre = nextCalendar.getPrevious();
            if (null != pre) {
                pre.setNext(calendar);
            }
            nextCalendar.setPrevious(calendar);
        }
        teachCalendarDAO.saveTeachCalendar(calendar);
        if (null != nextCalendar) {
            teachCalendarDAO.update(nextCalendar);
        }
        if (null != pre) {
            teachCalendarDAO.update(pre);
        }
    }
    
    /**
     * @see com.shufe.service.system.calendar.TeachCalendarService#updateTeachCalendar(com.shufe.model.system.calendar.TeachCalendar)
     */
    public void updateTeachCalendar(TeachCalendar calendar) {
        if (null == calendar) {
            return;
        }
        calendar.calcWeek();
        teachCalendarDAO.updateTeachCalendar(calendar);
    }
    
    /**
     * @see com.shufe.service.system.calendar.TeachCalendarService#checkDateCollision(com.shufe.model.system.calendar.TeachCalendar)
     */
    public boolean checkDateCollision(TeachCalendar calendar) {
        if (null == calendar) {
            return false;
        }
        EntityQuery entityQuery = new EntityQuery(TeachCalendar.class, "calendar");
        entityQuery.add(new Condition("calendar.studentType.id="
                + calendar.getStudentType().getId()));
        entityQuery.add(new Condition("id <> " + calendar.getId()));
        entityQuery.add(new Condition("calendar.year=:year", calendar.getYear()));
        
        Collection calendarList = utilDao.search(entityQuery);
        for (Iterator iter = calendarList.iterator(); iter.hasNext();) {
            TeachCalendar one = (TeachCalendar) iter.next();
            if (calendar.getStart().before(one.getFinish())
                    && one.getStart().before(calendar.getFinish())) {
                return true;
            }
        }
        return false;
    }
    
    public List getTeachCalendars(Long stdTypeId, String startYear, String startTerm,
            String endYear, String endTerm) {
        TeachCalendar start = getTeachCalendar(stdTypeId, startYear, startTerm);
        TeachCalendar end = getTeachCalendar(stdTypeId, endYear, endTerm);
        List rs = new ArrayList();
        while (true && null != start) {
            if (start.after(end)) {
                break;
            }
            rs.add(start);
            start = start.getNext();
        }
        return rs;
    }
    
    public List getCalendarStdTypes(Long stdTypeId) {
        StudentType stdType = (StudentType) utilService.get(StudentType.class, stdTypeId);
        Set stdTypes = stdType.getSubTypes();
        List calendarStdTypes = new ArrayList();
        calendarStdTypes.add(stdType);
        for (Iterator iter = stdTypes.iterator(); iter.hasNext();) {
            StudentType subType = (StudentType) iter.next();
            if (getCalendars(subType).isEmpty()) {
                calendarStdTypes.addAll(getCalendarStdTypes(subType.getId()));
            }
        }
        return calendarStdTypes;
    }
    
    /**
     * @see com.shufe.service.system.calendar.TeachCalendarService#setTeachCalendarDAO(com.shufe.dao.system.calendar.TeachCalendarDAO)
     */
    public void setTeachCalendarDAO(TeachCalendarDAO calendarDAO) {
        this.teachCalendarDAO = calendarDAO;
    }
    
    /**
     * @see com.shufe.service.system.calendar.TeachCalendarService#checkOnCampusTimeExist(com.shufe.model.system.calendar.OnCampusTime)
     */
    public boolean checkOnCampusTimeExist(OnCampusTime time) {
        return getOnCampusTime(time.getStdType(), time.getEnrollTurn()) == null ? false : true;
    }
    
    public List getTermsOrderByDistance(Long stdTypeId, String year) {
        return teachCalendarDAO.getTermsOrderByDistance(stdTypeId, year);
    }
    
    public List getYearsOrderByDistance(Long stdTypeId) {
        return teachCalendarDAO.getYearsOrderByDistance(stdTypeId);
    }
    
    public List getTeachCalendars(String stdTypeIdSeq) {
        if (StringUtils.isEmpty(stdTypeIdSeq)) {
            return Collections.EMPTY_LIST;
        }
        return teachCalendarDAO.gerCalendarsByStdTypeIdSeq(stdTypeIdSeq);
    }
    
    public TeachCalendar getTeachCalendar(Date currentAt) {
        if (null == currentAt) {
            return null;
        }
        EntityQuery query = new EntityQuery(TeachCalendar.class, "calendar");
        // 为了书写格式，先作声明
        String hql = ":currentAt between calendar.start and calendar.finish";
        query.add(new Condition(hql, currentAt));
        Collection results = utilDao.search(query);
        if (CollectionUtils.isEmpty(results)) {
            return null;
        }
        return (TeachCalendar) results.iterator().next();
    }
    
    public TeachCalendarDAO getTeachCalendarDAO() {
        return teachCalendarDAO;
    }
    
}
