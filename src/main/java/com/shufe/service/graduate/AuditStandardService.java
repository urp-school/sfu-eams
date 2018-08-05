//$Id: AuditStandardService.java,v 1.2 2006/08/02 12:58:32 yd Exp $
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

package com.shufe.service.graduate;

import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.eams.std.graduation.audit.model.AuditStandard;
import com.shufe.model.std.Student;

public interface AuditStandardService {

	/**
	 * 检索毕业审核标准
	 * 
	 * @param auditStandard
	 * @param pageNo
	 * @param pageSize
	 * @param departmentIds
	 * @param studentTypeIds
	 * @return Pagination
	 */
	public Pagination searchAuditStandard(AuditStandard auditStandard, int pageNo, int pageSize,
			String departmentIds, String studentTypeIds);		public List searchAuditStandard(AuditStandard auditStandard, String studentTypeIds);

	/**
	 * 列出给定毕业审核标准下的校外开始审核标准
	 * 
	 * @param auditStandardId
	 * @return List
	 */
	public List listOuterExamAuditStandard(Long auditStandardId);

	/**
	 * 检查符合条件的毕业审核标准是否存在
	 * 
	 * @param auditStandard
	 * @return boolean
	 */
	public boolean isAuditStandardExists(AuditStandard auditStandard);
	/**
	 * 查询学生所属的毕业审核标准
	 * 
	 * @param student
	 * @return Map
	 */
	public Map searchStudentAuditStandard(Student student, Long auditType);	
}
