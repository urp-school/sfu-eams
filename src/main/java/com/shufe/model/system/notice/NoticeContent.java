//$Id: NoticeContent.java,v 1.1 2008-1-21 下午04:56:28 zhouqi Exp $
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
 * zhouqi              2008-1-21         	Created
 *  
 ********************************************************************************/

package com.shufe.model.system.notice;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * @author zhouqi
 */
public class NoticeContent extends LongIdObject {
    
    private static final long serialVersionUID = -7253317143477603483L;
    
    /** 内容 */
    private String content;
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
}
