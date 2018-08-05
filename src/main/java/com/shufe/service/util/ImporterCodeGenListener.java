//$Id: TransferCodeGenListener.java,v 1.1 2007-3-17 下午03:36:53 chaostone Exp $
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

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.transfer.AbstractTransferListener;
import com.ekingstar.commons.transfer.TransferResult;
import com.shufe.service.system.codeGen.CodeGenerator;

public class ImporterCodeGenListener extends AbstractTransferListener {

	protected CodeGenerator codeGenerator;

	public ImporterCodeGenListener(CodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}

	public void endTransferItem(TransferResult tr) {
		codeGenerator.gen((Entity) transfer.getCurrent(), null);
	}

}
