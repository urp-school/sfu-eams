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
 * chaostone             2006-8-15            Created
 *  
 ********************************************************************************/
package com.shufe.dao.fee;

import java.util.List;

import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;

public interface FeeDefaultDAO {

	/**
	 * 查找收费默认值
	 * 
	 * @param feeType
	 * @param stdTypes
	 * @param department
	 * @param speciality
	 *            可选条件,如果有值则考虑查询范围为（speciality,null)<br>
	 *            否则不考虑该条件
	 * @return
	 */
	public List getFeeDefaults(FeeType feeType, List stdTypes, Department department,
			Speciality speciality);
}
