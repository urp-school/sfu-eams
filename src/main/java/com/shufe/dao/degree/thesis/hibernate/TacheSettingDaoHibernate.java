//$Id: TacheSettingDaoHibernate.java Mar 17, 2008 9:16:45 PM chaostone Exp $
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
package com.shufe.dao.degree.thesis.hibernate;

import org.hibernate.Query;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.thesis.TacheSettingDao;

public class TacheSettingDaoHibernate extends BasicHibernateDAO implements TacheSettingDao {

	public void updateTacheDocuments(Long documentId, Long[] documentTacheSettingIds,
			Long[] modelTacheSettingIds) {
		String sql = "delete from LW_HJSZ_WD_T where XWWDID=" + documentId;
		getSession().createSQLQuery(sql).executeUpdate();
		sql = "delete from LW_HJSZ_MB_T where XWMBID=" + documentId;
		getSession().createSQLQuery(sql).executeUpdate();
		if (null != documentTacheSettingIds) {
			sql = "insert into LW_HJSZ_WD_T(LWHJID,XWWDID) values(:tacheSettingId,:documentId)";
			for (int i = 0; i < documentTacheSettingIds.length; i++) {
				Query query = getSession().createSQLQuery(sql);
				query.setParameter("tacheSettingId", documentTacheSettingIds[i]);
				query.setParameter("documentId", documentId);
				query.executeUpdate();
			}
		}
		if (null != modelTacheSettingIds) {
			sql = "insert into LW_HJSZ_MB_T(LWHJID,XWMBID) values(:tacheSettingId,:documentId)";
			for (int i = 0; i < modelTacheSettingIds.length; i++) {
				Query query = getSession().createSQLQuery(sql);
				query.setParameter("tacheSettingId", modelTacheSettingIds[i]);
				query.setParameter("documentId", documentId);
				query.executeUpdate();
			}
		}
	}

}
