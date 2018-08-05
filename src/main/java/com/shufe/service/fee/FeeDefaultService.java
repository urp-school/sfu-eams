//$Id: FeeDefaultService.java,v 1.4 2006/10/12 12:18:54 duanth Exp $
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
 * chenweixiong              2006-7-11         Created
 *  
 ********************************************************************************/

package com.shufe.service.fee;

import com.ekingstar.eams.std.info.Student;
import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.shufe.dao.fee.FeeDefaultDAO;
import com.shufe.model.fee.FeeDefault;

public interface FeeDefaultService {

	/**
	 * @param feeDefaultDAO
	 */
	public void setFeeDefaultDAO(FeeDefaultDAO feeDefaultDAO);

	/**
	 * 首先按照学生的类别和部门进行查找对应的费用<br>
	 * 如果没有找到，则放宽条件，只按照学生类别查找<br>
	 * @param std
	 * @param type
	 * @return
	 */
	public FeeDefault getFeeDefault(Student std1, FeeType type);
}
