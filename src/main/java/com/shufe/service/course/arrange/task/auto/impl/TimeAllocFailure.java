//$Id: TimeAllocFailure.java,v 1.1 2006/11/09 11:22:28 duanth Exp $
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
 * chaostone             2005-11-8         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto.impl;

import com.shufe.service.course.arrange.task.auto.ResourceAllocFailure;

public class TimeAllocFailure extends ResourceAllocFailure {

    private static final long serialVersionUID = 6577468642455845550L;

    public TimeAllocFailure() {
        super();
        // TODO Auto-generated constructor stub
    }

    public TimeAllocFailure(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

	public TimeAllocFailure(String failureKey, String message) {
		super(failureKey, message);
		// TODO Auto-generated constructor stub
	}

}
