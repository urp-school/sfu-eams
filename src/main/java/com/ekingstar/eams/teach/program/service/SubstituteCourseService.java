//$Id: SubstituteCourseService.java,v 1.25 2008/11/14 09:43:18 cheneystar Exp $
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
 * @author cheneystar
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * cheneystar             2008-11-14         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.teach.program.service;

import java.util.List;

import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.std.Student;

public interface SubstituteCourseService {
    
    /**
     * 得到该学生指定专业类型的所有的替代课程
     * 
     * @param std
     * @param majorType
     * @return list<SubstitueCourse>
     */
    public List getStdSubstituteCourses(Student std, MajorType majorType);
    
    public List getStdSubstituteCourses(Student std);
}
