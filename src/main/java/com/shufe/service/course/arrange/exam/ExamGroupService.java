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
 * chaostone             2006-11-12            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.exam;

import java.util.Collection;
import java.util.List;

import com.ekingstar.commons.query.limit.PageLimit;
import com.shufe.model.course.arrange.exam.ExamGroup;
import com.shufe.model.system.security.DataRealm;
/**
 * 排考组服务接口
 * @author chaostone
 *
 */
public interface ExamGroupService {

	Collection getExamGroups(ExamGroup group, DataRealm ream, PageLimit limit,
			List orderList);
}
