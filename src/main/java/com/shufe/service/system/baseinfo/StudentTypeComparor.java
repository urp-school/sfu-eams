//$Id: StudentTypeComparor.java,v 1.2 2006/10/16 00:37:14 duanth Exp $
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
 * chaostone             2007-9-23         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.baseinfo;

import java.util.Comparator;

import com.ekingstar.eams.system.baseinfo.StudentType;

/**
 * 父类排在前面
 * 
 * @author chaostone
 * 
 */
public class StudentTypeComparor implements Comparator {

	public int compare(Object o1, Object o2) {
		StudentType first = (StudentType) o1;
		StudentType second = (StudentType) o2;
		if (first.isSuperTypeOf(second))
			return -1;
		if (second.isSuperTypeOf(first))
			return 1;
		else
			return first.getCode().compareTo(second.getCode());
	}
}