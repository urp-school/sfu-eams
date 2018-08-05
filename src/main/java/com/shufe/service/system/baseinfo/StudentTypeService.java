//$Id: StudentTypeService.java,v 1.2 2006/10/16 00:37:08 duanth Exp $
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
 * chaostone             2005-9-20         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.baseinfo;

import java.util.List;

public interface StudentTypeService extends
		com.ekingstar.eams.system.baseinfo.service.StudentTypeService {

	public String getStdTypeIdSeqUnder(Long parenetId);

	/**
	 * 查询包括给定学生类别在内的所有上级类别
	 * 
	 * @param stdTypeId
	 * @return
	 */
	public List getStdTypesUp(Long stdTypeId);
}
