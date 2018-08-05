//$Id: FeeDefaultComparator.java,v 1.1 2007-9-23 下午05:21:33 chaostone Exp $
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
 * chenweixiong              2007-9-23         Created
 *  
 ********************************************************************************/

package com.shufe.service.fee;

import java.util.Comparator;

import com.shufe.model.fee.FeeDefault;
import com.shufe.service.system.baseinfo.StudentTypeComparor;

/**
 * 学生类别处于子类别的,专业不为null的优先排在前面
 * 
 * @author chaostone
 * 
 */
public class FeeDefaultComparator implements Comparator {

	StudentTypeComparor stdTypeComparor = new StudentTypeComparor();

	public int compare(Object o1, Object o2) {
		FeeDefault first = (FeeDefault) o1;
		FeeDefault second = (FeeDefault) o2;
		int rs = stdTypeComparor.compare(second.getStudentType(), first.getStudentType());
		if (rs == 0) {
			if (null == first.getSpeciality() && null != second.getSpeciality())
				rs = 1;
			else if (null != first.getSpeciality() && null == second.getSpeciality())
				rs = -1;
		}
		return rs;
	}

}
