//$Id: EcuplSsoUserProviderImpl.java 2008-12-2 上午10:16:25 chaostone Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2008. All rights reserved.
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
 * ============   ============  ============
 * chaostone      2008-12-2  Created
 *  
 ********************************************************************************/
package com.ekingstar.eams.system.security.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ekingstar.commons.security.utils.EncryptUtil;
import com.ekingstar.commons.utils.web.RequestUtils;

public class EcuplSsoUserProviderImpl extends
        com.ekingstar.security.monitor.providers.AbstractSsoUserProvider {
    
    private boolean enableExpired = true;
    
    // default 10min second
    private int expiredTime = 600;
    
    private final static Logger logger = LoggerFactory.getLogger(EcuplSsoUserProviderImpl.class);
    
    protected String doGetUserLoginName(HttpServletRequest request) {
        String cid = request.getParameter("cid");
        String ip = RequestUtils.getIpAddr(request);
        Long t = RequestUtils.getLong(request, "t");
        String s = request.getParameter("s");
        if (null == t || null == s || null == cid || null == ip) {
            return null;
        }
        String full = cid + "," + ip + "," + t + "," + "123456!";
        String digest = EncryptUtil.encodePassword(full, "MD5");
        if (logger.isDebugEnabled()) {
            logger.info("ip:" + ip);
            logger.info("cid:" + cid);
            logger.info("t:" + t);
            logger.info("s:" + s);
            logger.info("full:" + full);
            logger.info("digest:" + digest);
        }
        if (digest.equals(s)) {
            long time = t.longValue() * 1000;
            Date now = new Date();
            if (enableExpired && (Math.abs(now.getTime() - time) > (expiredTime * 1000))) {
                logger.debug("user " + cid + " time expired:server time:{} and given time :{}",
                        now, new Date(time));
                return null;
            } else {
                logger.debug("user {} login at server time:{}", cid, now);
                return cid;
            }
        } else {
            return null;
        }
    }
    
    public boolean isEnableExpired() {
        return enableExpired;
    }
    
    public void setEnableExpired(boolean enableTime) {
        this.enableExpired = enableTime;
    }
    
    public int getExpiredTime() {
        return expiredTime;
    }
    
    public void setExpiredTime(int expiredTime) {
        this.expiredTime = expiredTime;
    }
}
