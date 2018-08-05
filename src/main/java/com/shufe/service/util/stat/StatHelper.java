//$Id: StatHelper.java,v 1.1 2007-3-20 下午08:52:02 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-20         Created
 *  
 ********************************************************************************/

package com.shufe.service.util.stat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.ekingstar.commons.utils.persistence.UtilService;

/**
 * 统计帮助类
 * 
 * @author chaostone
 * 
 */
public class StatHelper {

	private UtilService utilService;

	public StatHelper(UtilService utilService) {
		super();
		this.utilService = utilService;
		utilService.getUtilDao();
	}

	public StatHelper() {
		super();
	}

	private List setStatEntities(Map statMap, Class entityClass) {
		Collection entities = utilService.load(entityClass, "id", statMap
				.keySet());
		for (Iterator iter = entities.iterator(); iter.hasNext();) {
			Entity entity = (Entity) iter.next();
			StatItem stat = (StatItem) statMap.get(entity.getEntityId());
			stat.setWhat(entity);
		}
		return new ArrayList(statMap.values());
	}

	private Map buildStatMap(Collection stats) {
		Map statMap = new HashMap();
		for (Iterator iter = stats.iterator(); iter.hasNext();) {
			StatItem element = (StatItem) iter.next();
			statMap.put(element.getWhat(), element);
		}
		return statMap;
	}

	/**
	 * 向只包含id的数组列表中填充实体对象 clazzes[0]对应这data[0]的实体类型
	 * 
	 * @param datas
	 * @param clazzes
	 */
	public void replaceIdWith(Collection datas, Class[] clazzes) {
		for (Iterator iter = datas.iterator(); iter.hasNext();) {
			Object[] data = (Object[]) iter.next();
			for (int i = 0; i < clazzes.length; i++) {
				if (null == clazzes[i])
					continue;
				if (null != data[i]) {
					long id = ((Number) data[i]).longValue();
					data[i] = utilService.get(clazzes[i], new Long(id));
				}
			}
		}
	}

	/**
	 * 向只包含id的数组列表中填充实体对象 clazzes[0]对应这data[0]的实体类型
	 * 
	 * @param datas
	 * @param clazzes
	 */
	public static void replaceIdWith(Collection datas, Class[] clazzes,
			UtilDao utilDao) {
		for (Iterator iter = datas.iterator(); iter.hasNext();) {
			Object[] data = (Object[]) iter.next();
			for (int i = 0; i < clazzes.length; i++) {
				if (null == clazzes[i])
					continue;
				if (null != data[i]) {
					long id = ((Number) data[i]).longValue();
					data[i] = utilDao.get(clazzes[i], new Long(id));
				}
			}
		}
	}

	public List setStatEntities(Collection stats, Class entityClass) {
		Map statMap = buildStatMap(stats);
		return setStatEntities(statMap, entityClass);
	}

	public UtilService getUtilService() {
		return utilService;
	}

	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

}
