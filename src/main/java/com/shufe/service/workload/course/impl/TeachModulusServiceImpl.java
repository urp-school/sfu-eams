//$Id: TeachModulusServiceImpl.java,v 1.3 2006/08/25 06:48:40 cwx Exp $
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

package com.shufe.service.workload.course.impl;

import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.workload.ModulusDAO;
import com.shufe.model.workload.Modulus;
import com.shufe.model.workload.course.TeachModulus;
import com.shufe.service.BasicService;
import com.shufe.service.workload.ModulusService;

public class TeachModulusServiceImpl extends BasicService implements
		ModulusService {
	private ModulusDAO modulusDAO;
	

	/**
	 * @param modulusDAO The modulusDAO to set.
	 */
	public void setModulusDAO(ModulusDAO modulusDAO) {
		this.modulusDAO = modulusDAO;
	}

	/**
	 * @see com.shufe.service.workload.ModulusService#findModulus(org.hibernate.Criteria, int, int)
	 */
	public Pagination findModulus(Modulus modulus, String stdTypeIdSeq, int pageNo, int pageSize) {
		return modulusDAO.getPagiOfModulus(modulus, stdTypeIdSeq, pageNo, pageSize);
	}
	
	/**
	 * @see com.shufe.service.workload.ModulusService#getUniqueModulus(com.shufe.model.workload.course.TeachModulus, java.lang.String)
	 */
	public List getModulus(Modulus modulus, String stdTypeIdSeq) {
		return modulusDAO.getModulus(modulus, stdTypeIdSeq);
	}

	/**
	 * @see com.shufe.service.workload.ModulusService#getModulusByConditions(TeachModulus, java.lang.Integer)
	 */
	public Boolean getModulusByConditions(Modulus modulus,
			Integer personNum) {
		Boolean flag = Boolean.FALSE;
		if (null == modulus || null == personNum) {
			return flag;
		}
		String stdTypeIdSeq = null;
		if (null != modulus.getStudentType()
				&& null != modulus.getStudentType().getId()) {
			stdTypeIdSeq = modulus.getStudentType().getId().toString();
		}
		List tempModulus = getModulus(modulus, stdTypeIdSeq);
		for (Iterator iter = tempModulus.iterator(); iter.hasNext();) {
			TeachModulus element = (TeachModulus) iter.next();
			if (element.getMinPeople().compareTo(personNum) <= 0
					&& element.getMaxPeople().compareTo(personNum) > 0) {
				flag = Boolean.TRUE;
				break;
			}
		}
		return flag;
	}

	/**
	 * 判断教学任务是否合法
	 * @param teachModulus
	 * @return
	 */
	public boolean checkModulus(Modulus modulus) {
		boolean flag = true;
		TeachModulus teachModulus = (TeachModulus) modulus;
		if (teachModulus != null) {
			Integer maxPerson = teachModulus.getMaxPeople();
			Integer minPerson = teachModulus.getMinPeople();
			if (getModulusByConditions(teachModulus, minPerson).booleanValue()) {
				flag = false;
			}
			if (getModulusByConditions(teachModulus, maxPerson).booleanValue()) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * @see com.shufe.service.workload.ModulusService#getModulus(java.lang.String, java.lang.String)
	 */
	public Modulus getUniqueModulus(Modulus modulus, String studentTypeId) {
		List teachModulus = getModulus(modulus, studentTypeId);
		return (teachModulus.size() > 0) ? (TeachModulus) teachModulus.get(0)
				: null;
	}

	/**
	 * @see com.shufe.service.workload.ModulusService#getPropertyOfModulus(java.lang.Object, java.lang.String, java.lang.String)
	 */
	public List getPropertyOfModulus(Object mdulus, String stdTypeIdSeq,
			String properties) {
		return modulusDAO
				.getPropertyOfModulus(mdulus, stdTypeIdSeq, properties);
	}
}
