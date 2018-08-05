//$Id: StudentTypeServiceImpl.java,v 1.2 2006/10/16 00:37:14 duanth Exp $
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

package com.shufe.service.system.baseinfo.impl;

import java.util.ArrayList;
import java.util.List;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.service.system.baseinfo.StudentTypeService;

public class StudentTypeServiceImpl extends
		com.ekingstar.eams.system.baseinfo.service.impl.StudentTypeServiceImpl
		implements StudentTypeService {

	public String getStdTypeIdSeqUnder(Long parenetId) {
		StudentType stdType = (StudentType) utilService.get(StudentType.class,
				parenetId);
		List stdTypes = stdType.getDescendants();
		stdTypes.add(stdType);
		List ids = EntityUtils.extractIds(stdTypes);
		if (ids.size() == 0) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < ids.size(); i++) {
				sb.append(ids.get(i)).append(",");
			}
			if (sb.length() != 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			return sb.toString();
		}
	}

	public List getStdTypesUp(Long stdTypeId) {
		List stdTypes = new ArrayList();
		StudentType stdType = getStudentType(stdTypeId);
		while (null != stdType) {
			stdTypes.add(stdType);
			stdType = (StudentType) stdType.getSuperType();
		}
		return stdTypes;
	}
	
}
