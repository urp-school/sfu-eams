//$Id: MessageJob.java,v 1.2 2006/08/31 12:04:19 duanth Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-10-24         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.message;

import java.util.Map;

import com.shufe.service.BasicService;

/**
 * @author dell
 */
public class MessageJob extends BasicService {
    
    protected SystemMessageService messageService;
    
    /**
     * 归档历史消息
     */
    public void pigeonholeMessage() {
        utilDao.executeUpdateNamedQuery("pigeonholeMessage", (Map) null);
    }
    
    /**
     * 签证过期消息提醒
     */
    public void passportDeadLineMessage() {
    }
    
    public void setMessageService(SystemMessageService messageService) {
        this.messageService = messageService;
    }
    
}
