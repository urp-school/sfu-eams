//$Id: TeachCategoryServiceImpl.java,v 1.2 2006/08/17 06:44:25 cwx Exp $
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

package com.shufe.service.workload.course.impl;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.workload.course.TeachCategoryDAO;
import com.shufe.model.workload.course.TeachCategory;
import com.shufe.service.BasicService;
import com.shufe.service.workload.course.TeachCategoryService;

public class TeachCategoryServiceImpl extends BasicService implements TeachCategoryService {
    
    private TeachCategoryDAO teachCategoryDAO;
    
    /**
     * @return Returns the teachCategoryDAO.
     */
    public TeachCategoryDAO getTeachCategoryDAO() {
        return teachCategoryDAO;
    }
    
    /**
     * @param teachCategoryDAO
     *            The teachCategoryDAO to set.
     */
    public void setTeachCategoryDAO(TeachCategoryDAO teachCategoryDAO) {
        this.teachCategoryDAO = teachCategoryDAO;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.workload.TeachCategoryService#getPaginationOfValue(int, int)
     */
    public Pagination getPaginationOfValue(int pageNo, int pageSize) {
        return teachCategoryDAO.dynaSearch(teachCategoryDAO.getExampleOfCategory(false), pageNo,
                pageSize);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.workload.TeachCategoryService#getCategoryByStdTypeAndCourseType(com.shufe.model.system.baseinfo.StudentType,
     *      CourseType)
     */
    public TeachCategory getCategoryByStdTypeAndCourseType(StudentType studentType,
            CourseType courseType) {
        TeachCategory teachCategory = null;
        if (courseType != null) {
            teachCategory = teachCategoryDAO.getTeachCategoryBystdTypeAndcourseType(studentType
                    .getId(), courseType.getId());
        }
        while (studentType != null && teachCategory == null) {
            teachCategory = teachCategoryDAO.getTeachCategoryBystdTypeAndcourseType(studentType
                    .getId(), new Long(0));
            studentType = (StudentType) studentType.getSuperType();
        }
        return teachCategory;
    }
    
}
