//$Id: TransferDAOListener.java,v 1.1 2007-3-17 下午03:34:00 chaostone Exp $
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
 *chaostone      2007-3-17         Created
 *  
 ********************************************************************************/

package com.shufe.service.util;

import com.ekingstar.commons.transfer.AbstractTransferListener;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.persistence.UtilService;

public class ImporterDAOListener extends AbstractTransferListener {
	protected UtilService utilService;

	public ImporterDAOListener() {
		super();
	}

	public ImporterDAOListener(UtilService utilService) {
		this.utilService = utilService;
	}

	public void endTransferItem(TransferResult tr) {
		this.utilService.saveOrUpdate(transfer.getCurrent());
	}
}
