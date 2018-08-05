//$Id: GraduateDAO.java,v 1.1 2006/08/02 00:53:15 duanth Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-11-11         Created
 *  
 ********************************************************************************/

package com.shufe.dao.graduate;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.eams.std.graduation.audit.model.AuditStandard;
import com.shufe.dao.BasicDAO;

/**
 * @author dell
 */
public interface GraduateDAO extends BasicDAO{

	/**
	 * 根据所给实例检索毕业审核计划,如果实例中关联对象为null,那么忽略该条件
	 * 
	 * @param auditStandard
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination searchAuditStandard(AuditStandard auditStandard, int pageNo, int pageSize);
	
	/**
	 * 根据所给实例检索毕业审核计划,如果实例中关联对象为null,那么忽略该条件
	 * 
	 * @param auditStandard
	 * @return
	 */
	public List searchAuditStandard(AuditStandard auditStandard);

	/**
	 * 根据所给实例检索毕业审核计划,如果实例中关联对象为null 那么添加条件"=null"
	 * 
	 * @param auditStandard
	 * @return
	 */
	public List searchAuditStandardWithNull(AuditStandard auditStandard);

	/**
	 * 根据所给实例统计毕业审核计划,如果实例中关联对象为null 那么添加条件"=null"
	 * 
	 * @param auditStandard
	 * @return
	 */
	public int countAuditStandardWithNull(AuditStandard auditStandard);

}
