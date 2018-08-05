package com.shufe.service.system.security.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.security.utils.SecretDesFactory;
import com.ekingstar.security.monitor.providers.AbstractSsoUserProvider;

public class DESSsoUserProvider extends AbstractSsoUserProvider {
    
    private SecretDesFactory f = null;
    
    public DESSsoUserProvider() {
        try {
            this.f = new SecretDesFactory("ABCDEFGH".getBytes(), "ABCDEFGH".getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    protected String doGetUserLoginName(HttpServletRequest request) {
        String encodeName = request.getParameter("userName");
        if (StringUtils.isNotEmpty(encodeName)) {
            String realName=new String(f.decrypt(SecretDesFactory.fromHexString(encodeName)));
            return realName;
        } else {
            return null;
        }
    }
}
