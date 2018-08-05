//$Id: DataRealmUtils.java,v 1.1 2007-2-9 下午10:25:00 chaostone Exp $
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
 *chaostone      2007-2-9         Created
 *  
 ********************************************************************************/

package com.shufe.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.system.security.DataRealm;

public class DataRealmUtils {

	public static void addDataRealm(EntityQuery query, String[] attrs, DataRealm dataRealm) {
		addDataRealms(query, attrs, Collections.singletonList(dataRealm));
	}

	/**
	 * 添加数据权限<br>
	 * 空权限不添加到查询语句里
	 * 
	 * @param query
	 * @param attrs
	 * @param dataRealm
	 */
	public static void addDataRealms(EntityQuery query, String[] attrs, List dataRealms) {
		if (CollectionUtils.isEmpty(dataRealms))
			return;
		List conditions = new ArrayList();
		List datas = new ArrayList();
		for (int i = 0; i < dataRealms.size(); i++) {
			DataRealm dataRealm = (DataRealm) dataRealms.get(i);
			StringBuffer buffer = new StringBuffer("");
			if (attrs.length > 0) {
				if (StringUtils.isNotEmpty(dataRealm.getStudentTypeIdSeq())
						&& StringUtils.isNotEmpty(attrs[0])) {
					buffer.append(" exists (from StudentType mytype where mytype.id =" + attrs[0]);
					buffer.append(" and mytype.id in(:mytypeIds" + RandomUtils.nextInt() + "))");
					datas.add(SeqStringUtil.transformToLong(dataRealm.getStudentTypeIdSeq()));
				}
			}
			if (attrs.length > 1) {
				if (StringUtils.isNotEmpty(dataRealm.getDepartmentIdSeq())
						&& StringUtils.isNotEmpty(attrs[1])) {
					if (buffer.length() > 0) {
						buffer.append(" and ");
					}
					buffer.append(" exists (from Department mydepart where mydepart.id ="
							+ attrs[1]);
					buffer
							.append(" and mydepart.id in(:myDepartIds" + RandomUtils.nextInt()
									+ "))");
					datas.add(SeqStringUtil.transformToLong(dataRealm.getDepartmentIdSeq()));
				}
			}
			if (buffer.length() > 0) {
				conditions.add(new Condition(buffer.toString()));
			}
		}
		StringBuffer buffer = new StringBuffer("(");
		for (int i = 0; i < conditions.size(); i++) {
			Condition condition = (Condition) conditions.get(i);
			if (i != 0) {
				buffer.append(" or ");
			}
			buffer.append(condition.getContent());
		}
		buffer.append(")");
		Condition con = new Condition(buffer.toString());
		con.setValues(datas);
		query.add(con);
	}
}
