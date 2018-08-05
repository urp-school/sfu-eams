package com.shufe.service.system.security.impl;

import javax.servlet.http.HttpServletRequest;

import com.ekingstar.security.monitor.providers.AbstractSsoUserProvider;
import com.neusoft.education.tp.sso.client.CASReceipt;
import com.neusoft.education.tp.sso.client.filter.CASFilter;

public class CasSsoUserProvider  extends AbstractSsoUserProvider {

	@Override
	protected String doGetUserLoginName(HttpServletRequest request) {
		CASReceipt receipt = (CASReceipt) request.getSession().getAttribute(CASFilter.SSO_FILTER_RECEIPT);
		return (null==receipt)?null:receipt.getUserName();
	}

}
