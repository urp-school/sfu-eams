//$Id: AdjustCourseAction.java,v 1.1 2007-7-6 下午01:27:58 chaostone Exp $
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
 * chenweixiong              2007-7-6         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.arrange.task;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.task.ManualArrangeParam;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 课程调整action
 * 
 * @author chaostone
 * 
 */
public class AdjustCourseAction extends ManualArrangeAction {

	/**
	 * 教学活动调整主界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        TeachCalendar teachCalendar = (TeachCalendar) request.getAttribute(Constants.CALENDAR);
		EntityQuery query = new EntityQuery(ManualArrangeParam.class, "manualArrangeParam");
        String order = "Y";
        if (teachCalendar != null) {
            query.add(new Condition("manualArrangeParam.calendar.id=" + teachCalendar.getId()));
        }
        List manualArrangeParamList = (List) utilService.search(query);
        Set users = getUser(request.getSession()).getRoles();
        for (Iterator iter = users.iterator(); iter.hasNext();) {
            EamsRole eamsRole = (EamsRole) iter.next();
            if (manualArrangeParamList.size() == 0 && eamsRole.getId().longValue() != 1L) {
                order = "N";
            } else {
                for (Iterator iter_ = manualArrangeParamList.iterator(); iter_.hasNext();) {
                    ManualArrangeParam param = (ManualArrangeParam) iter_.next();
                    Date dateNow = new Date(System.currentTimeMillis());
                    Date dateStart = param.getStartDate();
                    Date dateFinsh = param.getFinishDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dateFinsh);
                    cal.add(Calendar.DAY_OF_YEAR, +1);
                    if ((dateNow.before(dateStart) || dateNow.after(cal.getTime()) || param
                            .getIsOpenElection().equals(Boolean.valueOf(false)))
                            && eamsRole.getId().longValue() != 1L) {
                        order = "N";
                    }
                }
            }
        }
        addSingleParameter(request, "order", order);
		return arrangeHome(mapping, form, request, response);
	}
}
