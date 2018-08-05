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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.service.util;

import com.ekingstar.commons.utils.persistence.UtilService;

public class TransferDAOListener extends ForeignerListener {

	public void endTransfer() {
		super.endTransfer();
	}

	public TransferDAOListener() {
		super();
		// TODO 自动生成构造函数存根
	}

	public TransferDAOListener(UtilService utilService) {
		super(utilService);
		// TODO 自动生成构造函数存根
	}

}
