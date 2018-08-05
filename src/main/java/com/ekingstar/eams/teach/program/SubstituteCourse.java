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
package com.ekingstar.eams.teach.program;

import java.util.Date;
import java.util.Set;

import com.ekingstar.security.User;

/**
 * 替代课程.
 * 
 * @author new
 * 
 */
public interface SubstituteCourse {
    
    public Set getOrigins();
    
    public void setOrigins(Set origins);
    
    public Set getSubstitutes();
    
    public void setSubstitutes(Set substitutes);
    
    public Date getCreateAt();
    
    public void setCreateAt(Date createAt);
    
    public Date getModifyAt();
    
    public void setModifyAt(Date modifyAt);
    
    public User getOperateBy();
    
    public void setOperateBy(User operateBy);
    
}
