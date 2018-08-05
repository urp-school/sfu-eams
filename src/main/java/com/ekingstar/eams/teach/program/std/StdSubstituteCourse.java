//$Id: SubstitutionCourse.java Apr 16, 2008 9:34:35 PM chaostone Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2008. All rights reserved.
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
 * ============   ============  ============
 * chaostone      Apr 16, 2008  Created
 *  
 ********************************************************************************/
package com.ekingstar.eams.teach.program.std;

import com.ekingstar.commons.model.LongIdEntity;
import com.ekingstar.eams.teach.program.SubstituteCourse;
import com.shufe.model.std.Student;

/**
 * 学生替代课程.
 * 
 * @author new
 * 
 */
public interface StdSubstituteCourse extends SubstituteCourse, LongIdEntity {
    
    public Student getStd();
    
    public void setStd(Student std);
    
}
