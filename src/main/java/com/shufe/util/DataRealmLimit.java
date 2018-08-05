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
 * chaostone             2006-8-16            Created
 *  
 ********************************************************************************/
package com.shufe.util;

import com.ekingstar.commons.query.limit.Limit;
import com.ekingstar.commons.query.limit.PageLimit;
import com.shufe.model.system.security.DataRealm;

/**
 * 数据权限限制<br>
 * 建议在使用时stdTypeIds,departIds为null或""时忽略权限.<br>
 * 
 * @author chaostone
 * 
 */
public class DataRealmLimit implements Limit {
	private PageLimit pageLimit = new PageLimit();

	private DataRealm dataRealm = new DataRealm();

	public DataRealmLimit() {
		super();
	}

	public DataRealmLimit(String stdTypeIds, String departIds) {
		this.dataRealm.setStudentTypeIdSeq(stdTypeIds);
		this.dataRealm.setDepartmentIdSeq(departIds);
	}

	public PageLimit getPageLimit() {
		return pageLimit;
	}

	public void setPageLimit(PageLimit limit) {
		this.pageLimit = limit;
	}

	public DataRealm getDataRealm() {
		return dataRealm;
	}

	public void setDataRealm(DataRealm dataRealm) {
		this.dataRealm = dataRealm;
	}

}
