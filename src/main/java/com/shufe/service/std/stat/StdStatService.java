//$Id: StdStatService.java,v 1.1 2007-4-2 上午09:28:07 chaostone Exp $
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
 *chaostone      2007-4-2         Created
 *  
 ********************************************************************************/

package com.shufe.service.std.stat;

import java.util.List;

import com.shufe.model.system.security.DataRealm;

public interface StdStatService {
	/**
	 * 按学生类别，统计各个入学年份分布比
	 * 
	 * @param dataRealm
	 * @param attr
	 * @param clazz
	 * @return
	 */
	public List statOnCampusByStdType(DataRealm dataRealm);
	/**
	 * 按院系统计各个入学年份学生分布比
	 * 
	 * @param dataRealm
	 * @param attr
	 * @param clazz
	 * @return
	 */
	public List statOnCampusByDepart(DataRealm dataRealm);
	/**
	 * 按学生类别，统计各个入学年份,各个院系的学生分布比例
	 * 
	 * @param dataRealm
	 * @param attr
	 * @param clazz
	 * @return
	 */
	public List statOnCampusByStdTypeDepart(DataRealm dataRealm);

	/**
	 * 按院系，统计各个入学年份,各个学生类别的学生分布比例
	 * 
	 * @param dataRealm
	 * @param attr
	 * @param clazz
	 * @return
	 */
	public List statOnCampusByDepartStdType(DataRealm dataRealm);

}
