//$Id: TacheSettingDao.java Mar 17, 2008 9:15:28 PM chaostone Exp $
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
 * chaostone      Mar 17, 2008  Created
 *  
 ********************************************************************************/
package com.shufe.dao.degree.thesis;

public interface TacheSettingDao {

	public void updateTacheDocuments(Long documentId, Long[] documentTacheSettingIds,
			Long[] modelTacheSettingIds);
}
