//$Id: TeachTaskRequirementAction.java,v 1.1 2012-6-5 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi				2012-6-5             Created
 *  
 ********************************************************************************/

/**
 * 
 */
package com.shufe.web.action.course.task;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 课程要求Action的父类
 * 
 * @author zhouqi
 * 
 */
public class TeachTaskRequirementAction extends CalendarRestrictionSupportAction {
    
    protected void addSystemParams(HttpServletRequest request) {
        addSingleParameter(request, "loginUser", getUser(request));
        addSingleParameter(request, "nowAt", new Date());
    }
}
