//$Id: SystemLog.java,v 1.1 2007-6-28 上午11:25:08 chaostone Exp $
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
 * chenweixiong              2007-6-28         Created
 *  
 ********************************************************************************/

package com.shufe.model.system.log;

import java.sql.Timestamp;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.security.User;

/**
 * 系统日志
 * 
 * @author chaostone
 * 
 */
public class SystemLog extends LongIdObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6768191458008240550L;

	/** 操作用户 */
    private User user;

    /** 操作内容 */
    private String operation;

    /** 操作URI */
    private String URI;

    /** 操作时间 */
    private Timestamp time;

    /** 操作参数 */
    private String params;

    /** 操作主机地址 */
    private String host;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String uri) {
        URI = uri;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}
