//$Id: UserDWRServiceImpl.java,v 1.1 2008-11-21 上午09:38:38 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2008-11-21             Created
 *  
 ********************************************************************************/

package com.shufe.service.system.security.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.security.User;
import com.ekingstar.security.service.impl.UserServiceImpl;
import com.shufe.service.system.security.UserDWRService;

/**
 * @author zhouqi
 * 
 */
public class UserDWRServiceImpl extends UserServiceImpl implements UserDWRService {
    
    /**
     * @see com.shufe.service.system.security.impl.UserDWRService#getEasmUser(java.lang.String)
     */
    public User getEamsUser(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return null;
        } else {
            List results = utilService.load(User.class, "name", userName);
            if (CollectionUtils.isEmpty(results)) {
                return null;
            } else {
                return (User) results.get(0);
            }
        }
    }
}
