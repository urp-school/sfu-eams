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
 * chaostone             2006-8-25            Created
 *  
 ********************************************************************************/
package com.shufe.dao.system.message;

import com.shufe.dao.BasicDAO;

public interface SystemMessageDAO extends BasicDAO {

	/**
	 * 接受者消息某类消息的数量
	 * 
	 * @param recipient
	 * @param status
	 * @return
	 */
	public int getMessageCount(Long recipientId, Integer status);
}
