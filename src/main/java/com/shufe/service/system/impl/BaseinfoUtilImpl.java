//$Id: BaseinfoUtilImpl.java,v 1.1 2006/08/02 00:53:16 duanth Exp $
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
 * chenweixiong              2006-3-11         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.impl;

import com.shufe.service.BasicService;
import com.shufe.service.system.BaseinfoUtil;

public class BaseinfoUtilImpl extends BasicService implements BaseinfoUtil {

	public boolean checkCodeIfExists(String className, String keyName,
			String code) {
		return utilService.exist(className, keyName, code);
	}

}
