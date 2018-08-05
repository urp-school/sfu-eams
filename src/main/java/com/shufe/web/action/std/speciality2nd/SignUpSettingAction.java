//$Id: SignUpSettingAction.java,v 1.1 2007-5-4 下午04:13:52 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-5-4         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.speciality2nd;

import javax.servlet.http.HttpServletRequest;

import com.shufe.web.action.common.CalendarRestrictionExampleTemplateAction;

/**
 * 报名设置响应类
 * 
 * @author chaostone
 * 
 */
public class SignUpSettingAction extends CalendarRestrictionExampleTemplateAction {

	protected void indexSetting(HttpServletRequest request) throws Exception {
		setCalendarDataRealm(request, hasStdType);
	}

}
