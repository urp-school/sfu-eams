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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.service.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.utils.persistence.UtilService;

public class ForeignerListener extends TransferConvertListener {
	protected UtilService utilService;

	Map foreigers = new HashMap();

	public ForeignerListener() {
		super();
		// TODO 自动生成构造函数存根
	}

	public ForeignerListener(UtilService utilService) {
		super();
		this.utilService = utilService;
	}

	public String parse(String values) {
		// TODO 自动生成方法存根
		return null;
	}

	public void startTransfer() {

	}

	public void endTransfer() {
		Object obj = getCSVDataTransfer().getCurTransfered();
		EntityUtils.evictEmptyProperty(obj);
	}

	public void addFailure(int lineNumber, String messages, String values) {

	}

	/**
	 * 
	 */
	public void tranferField(String field, String value) throws Exception {
		super.tranferField(field, value);
		if (StringUtils.contains(field, '.') && field.endsWith("code")&&StringUtils.isNotEmpty(value)) {
			Object nestedForeigner = PropertyUtils.getProperty(CSVDataTransfer
					.getCurTransfered(), StringUtils.substring(field, 0, field
					.lastIndexOf(".")));

			if (nestedForeigner instanceof Entity) {
				if (!ValidEntityPredicate.INSTANCE.evaluate(nestedForeigner)) {

					Map values = (Map) foreigers
							.get(nestedForeigner.getClass());
					if (null == values) {
						Map valueMap = new HashMap();
						foreigers.put(nestedForeigner.getClass(), valueMap);
						Object Id = valueMap.get(value);
						if (Id == null) {
							List foreigners = utilService.load(nestedForeigner
									.getClass(), "code", value);

							if (!foreigners.isEmpty()) {
								Id = PropertyUtils.getProperty(foreigners
										.iterator().next(), "id");
								valueMap.put(value, Id);
							} else {
								throw new PojoNotExistException("wrong code :"
										+ field + " with value:" + value);
							}
						}
						PropertyUtils.setProperty(nestedForeigner,
								((Entity) nestedForeigner).key(), Id);
					} else {
						Object Id = values.get(value);
						if (Id == null) {							
							List foreigners = utilService.load(nestedForeigner
									.getClass(), "code", value);

							if (!foreigners.isEmpty()) {
								Id = PropertyUtils.getProperty(foreigners
										.iterator().next(), "id");
								values.put(value, Id);
							} else {
								throw new PojoNotExistException("wrong code :"
										+ field + " with value:" + value);
							}							
						}
						PropertyUtils.setProperty(nestedForeigner,
								((Entity) nestedForeigner).key(), Id);
					}
				}
			}

		}
	}

	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

}
