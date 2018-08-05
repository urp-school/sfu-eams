//$Id: AdminClassDAOListener.java,v 1.1 2007-3-17 下午06:51:36 chaostone Exp $
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

package com.shufe.service.system.baseinfo.importer;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.system.baseinfo.AdminClassService;

public class AdminClassImportListener extends ItemImporterListener {
	protected AdminClassService adminClassService;

	public AdminClassImportListener(AdminClassService adminClassService) {
		this.adminClassService = adminClassService;
	}

	public void startTransferItem(TransferResult tr) {
		Map params = importer.curDataMap();
		String code = (String) params.get("code");
		if (ValidEntityKeyPredicate.INSTANCE.evaluate(code)) {
			importer.setCurrent(adminClassService.getAdminClass(code));
		}
	}

	public void endTransferItem(TransferResult tr) {
		AdminClass adminClass = (AdminClass) importer.getCurrent();
		int errors = tr.errors();
		if (!checkEnrollTurn(adminClass.getEnrollYear())) {
			tr.addFailure("error.dataFormat", adminClass.getEnrollYear());
		}
		if (adminClass.getStdType() == null
				|| adminClass.getDepartment() == null) {
			tr.addFailure("error.parameters.needed",
					"department or studentType");
		}
		if (StringUtils.isBlank(adminClass.getCode())) {
			tr.addFailure("error.parameters.needed", "code");
		}
		if (tr.errors() == errors) {
			adminClassService.saveOrUpdate(adminClass);
		}
	}

	public boolean checkEnrollTurn(String enrollturn) {
		PatternCompiler compiler = new Perl5Compiler();
		Pattern pattern = null;
		try {
			pattern = compiler.compile("(\\d{4})[\\-]\\d+");
		} catch (MalformedPatternException e) {
			return false;
		}
		PatternMatcher matcher = new Perl5Matcher();
		return matcher.matches(enrollturn, pattern);
	}
}
