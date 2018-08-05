//$Id: DegreeDAO.java,v 1.2 2006/11/04 10:01:20 cwx Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-10-26         Created
 *  
 ********************************************************************************/

package com.shufe.dao.degree.apply;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.model.degree.apply.DegreeApply;

public interface DegreeDAO {

	public List getDegreeApplyList(DegreeApply degreeApply, Long[] departmentIds, Long[] stdTypeIds);

	public Pagination getPaginas(DegreeApply degreeApply, Long[] stdTypeIdSeq,
			Long[] departmentSeq, int pageNo, int pageSize);

	public void affirmById(String idSeq, Boolean affirm);
}
