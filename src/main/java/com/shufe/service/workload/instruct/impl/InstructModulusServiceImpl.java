//$Id: InstructModulusServiceImpl.java,v 1.3 2006/08/25 06:48:40 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-17         Created
 *  
 ********************************************************************************/

package com.shufe.service.workload.instruct.impl;

import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.dao.workload.ModulusDAO;
import com.shufe.model.workload.Modulus;
import com.shufe.model.workload.instruct.InstructModulus;
import com.shufe.service.BasicService;
import com.shufe.service.workload.ModulusService;

/**
 * @author hj
 * 
 */
public class InstructModulusServiceImpl extends BasicService implements ModulusService {
	private ModulusDAO modulusDAO;

	/**
	 * @param modulusDAO
	 *            The modulusDAO to set.
	 */
	public void setModulusDAO(ModulusDAO modulusDAO) {
		this.modulusDAO = modulusDAO;
	}

	/**
	 * @see com.shufe.service.workload.ModulusService#findModulus(com.shufe.model.workload.Modulus,
	 *      java.lang.String, int, int)
	 */
	public Pagination findModulus(Modulus modulus, String stdTypeIdSeq, int pageNo, int pageSize) {
		return modulusDAO.getPagiOfModulus(modulus, stdTypeIdSeq, pageNo, pageSize);
	}

	/**
	 * @see com.shufe.service.workload.ModulusService#getModulus(com.shufe.model.workload.Modulus,
	 *      java.lang.String)
	 */
	public List getModulus(Modulus modulus, String stdTypeIdSeq) {
		return modulusDAO.getModulus(modulus, stdTypeIdSeq);
	}

	/**
	 * 检查instructModulus的合法性
	 * 
	 * @see com.shufe.service.workload.ModulusService#checkModulus(java.lang.Object)
	 */
	public boolean checkModulus(Modulus modulus) {
		InstructModulus im = (InstructModulus) modulus;
		EntityQuery query = new EntityQuery(InstructModulus.class, "m");
		query.add(new Condition("m.studentType.id=:stdTypeId", im.getStudentType().getId()));
		query.add(new Condition("m.itemType=:type", im.getItemType()));
		List moduluses = (List) utilDao.search(query);
		for (Iterator iterator = moduluses.iterator(); iterator.hasNext();) {
			InstructModulus m = (InstructModulus) iterator.next();
			if (null == im.getId() || (!im.getId().equals(m.getId())))
				return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.service.workload.ModulusService#getModulus(java.lang.String,
	 *      java.lang.String)
	 */
	public Modulus getUniqueModulus(Modulus modulus, String studentTypeId) {
		List instructModulus = modulusDAO.getModulus(modulus, studentTypeId);
		return instructModulus.size() > 0 ? (InstructModulus) instructModulus.get(0) : null;
	}

	/**
	 * @see com.shufe.service.workload.ModulusService#getPropertyOfModulus(java.lang.Object,
	 *      java.lang.String, java.lang.String)
	 */
	public List getPropertyOfModulus(Object mdulus, String stdTypeIdSeq, String properties) {
		return modulusDAO.getPropertyOfModulus(mdulus, stdTypeIdSeq, properties);
	}
}
