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
 * chaostone             2006-8-16            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.fee;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.collection.predicates.NotZeroNumberPredicate;
import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.security.User;
import com.shufe.model.fee.FeeDetail;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.fee.FeeDetailService;
import com.shufe.service.std.StudentService;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 学生查询自己费用
 * 
 * @author chaostone
 * 
 */
public class FeeOfStdAction extends DispatchBasicAction {
    
    private FeeDetailService feeDetailService;
    
    private StudentService studentService;
    
    /**
     * @param studentService
     *            The studentService to set.
     */
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        User user = getUser(request.getSession());
        if (null == user) {
            return forwardError(mapping, request, "");
        } else {
            if (!user.isCategory(EamsRole.STD_USER))
                return forwardError(mapping, request, "");
        }
        Student std = (Student) studentService.getStudent(user.getName());
        if (null == std) {
            return forwardError(mapping, request, "");
        }
        Long feeTypeId = getLong(request, "feeType.id");
        FeeType type = null;
        Long calendarId = getLong(request, "calendar.id");
        TeachCalendar calendar = null;
        if (NotZeroNumberPredicate.INSTANCE.evaluate(feeTypeId)) {
            type = (FeeType) utilService.get(FeeType.class, feeTypeId);
            request.setAttribute("conditionFeeType", type);
        }
        if (NotZeroNumberPredicate.INSTANCE.evaluate(calendarId)) {
            calendar = (TeachCalendar) utilService.get(TeachCalendar.class, calendarId);
            request.setAttribute("conditionCalendar", calendar);
        }
        List fees = feeDetailService.getFeeDetails(std, calendar, type);
        Set feeTypes = new HashSet();
        Set calendars = new HashSet();
        for (Iterator iter = fees.iterator(); iter.hasNext();) {
            FeeDetail feeDetail = (FeeDetail) iter.next();
            feeTypes.add(feeDetail.getType());
            calendars.add(feeDetail.getCalendar());
        }
        addCollection(request, "fees", fees);
        addCollection(request, "feeTypes", feeTypes);
        addCollection(request, "calendars", calendars);
        return forward(request);
    }
    
    public void setFeeDetailService(FeeDetailService feeDetailService) {
        this.feeDetailService = feeDetailService;
    }
    
}
