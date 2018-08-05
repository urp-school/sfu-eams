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
 * chaostone             2006-1-7            Created
 *  
 ********************************************************************************/
package com.shufe.dao.system.calendar;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.calendar.OnCampusTime;
import com.shufe.model.system.calendar.OnCampusTimeNotFoundException;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.system.calendar.TeachCalendarService;

/**
 * 计算相对学期的工具类
 * 
 * @author chaostone
 * 
 */
public class TermCalculator {
    
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private TeachCalendarDAO calendarDAO;
    
    private Map onCampusTimeMap;
    
    private Map termsMap;
    
    private TeachCalendar calendar;
    
    public TermCalculator(TeachCalendarService calendarService, TeachCalendar calendar) {
        this.calendarDAO = calendarService.getTeachCalendarDAO();
        onCampusTimeMap = new HashMap();
        termsMap = new HashMap();
        this.calendar = calendar;
    }
    
    public TermCalculator(TeachCalendarDAO teachCalendarDAO, TeachCalendar calendar) {
        this.calendarDAO = teachCalendarDAO;
        onCampusTimeMap = new HashMap();
        termsMap = new HashMap();
        this.calendar = calendar;
    }
    
    /**
     * 计算first到second教学日历之间的学期数.<br>
     * first在second之前则返回正整数，否则返回1或负整数.<br>
     * [first,second]包含两段的学期数.<br>
     * 如果给出两个教学日历中的学生类别不一致，则返回null<br>
     * 相同教学日历,则返回1<br>
     * 
     * @param pre
     * @param post
     * @param omitSmallTerm
     * @return
     */
    public int getTermBetween(TeachCalendar pre, TeachCalendar post, Boolean omitSmallTerm) {
        Integer terms = calendarDAO.getTermsBetween(pre, post, omitSmallTerm);
        return (null == terms) ? -1 : terms.intValue();
    }
    
    /**
     * 返回[onCampusTime.getEnrollCalendar(), calendar]包含两段的学期数.<br>
     * 
     * @param stdType
     * @param enrollTurn
     * @param omitSmallTerm
     * @return
     */
    public int getTerm(StudentType stdType, String enrollTurn, Boolean omitSmallTerm) {
        // 获取培养计划中学生的入学日历
        OnCampusTime onCampusTime = (OnCampusTime) onCampusTimeMap
                .get(stdType.getId() + enrollTurn);
        if (onCampusTime == null) {
            onCampusTime = calendarDAO.getOnCampusTime(stdType, enrollTurn);
        }
        if (null == onCampusTime) {
            OnCampusTimeNotFoundException ex = new OnCampusTimeNotFoundException(
                    "error.onCampusTime.notExists:stdType is " + stdType.getId()
                            + " for enroll turn " + enrollTurn);
            logger.info("error.onCampusTime.notExists:" + ex.getMessage());
            throw ex;
        } else {
            onCampusTimeMap.put(stdType.getId() + enrollTurn, onCampusTime);
        }
        // 获取预先计算的学期差额
        Integer term = (Integer) termsMap.get(onCampusTime.getEnrollCalendar());
        if (null == term) {
            if (logger.isDebugEnabled()) {
                logger.debug("calculate a term [stdtypeid:" + stdType.getName() + ", enrollTurn:"
                        + enrollTurn + "]");
            }
            term = calendarDAO.getTermsBetween(onCampusTime.getEnrollCalendar(), calendar,
                    omitSmallTerm);
            termsMap.put(onCampusTime.getEnrollCalendar(), term);
            
            if (term == null)
                return -1;
        }
        return term.intValue();
    }
}
