//$Id: EvaluateSwitchServiceImpl.java,v 1.1 2007-6-4 10:07:57 Administrator Exp $
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
 * chenweixiong              2007-6-4         Created
 *  
 ********************************************************************************/

package com.shufe.service.quality.evaluate.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.quality.evaluate.EvaluateSwitch;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.quality.evaluate.EvaluateSwitchService;

public class EvaluateSwitchServiceImpl extends BasicService implements EvaluateSwitchService {
    
    /**
     * @see com.shufe.service.quality.evaluate.EvaluateSwitchService#getEvaluateSwitch(java.lang.Long,
     *      java.lang.Long)
     */
    public EvaluateSwitch getEvaluateSwitch(TeachCalendar calendar, StudentType stdType,
            Department depart) {
        Map params = new HashMap();
        params.put("calendar", calendar);
        params.put("stdType", stdType);
        params.put("depart", depart);
        List switchs = (List) utilDao.searchNamedQuery("getEvaluateSwitchs", params, true);
        return switchs.size() > 0 ? (EvaluateSwitch) switchs.get(0) : null;
    }
    
    public List getOpenCalendars() {
        return getOpenCalendars(true);
    }
    
    public List getOpenCalendars(boolean isSwitch) {
        EntityQuery query = new EntityQuery(EvaluateSwitch.class, "switch");
        if (isSwitch) {
            query.add(new Condition("switch.isOpen=true"));
        }
        query.add(new Condition(":date between switch.openAt and switch.closeAt", new Date()));
        query.setSelect("distinct switch.calendar");
        return (List) utilDao.search(query);
    }
}
