//$Id: TeachCategoryService.java,v 1.2 2006/08/17 06:44:25 cwx Exp $
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
 * chenweixiong              2006-3-13         Created
 *  
 ********************************************************************************/

package com.shufe.service.workload.course;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.workload.course.TeachCategory;

public interface TeachCategoryService {
    
    /**
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getPaginationOfValue(int pageNo, int pageSize);
    
    /**
     * @param studentType
     * @param courseType
     * @return
     */
    public TeachCategory getCategoryByStdTypeAndCourseType(StudentType studentType,
            CourseType courseType);
}
