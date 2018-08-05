//$Id: MultimediaRequirement.java,v 1.1 2012-5-21 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi				2012-5-21             Created
 *  
 ********************************************************************************/

/**
 * 
 */
package com.shufe.model.course.task;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 多媒体教室要求
 * 
 * @author zhouqi
 * 
 */
public class MultimediaRequirement extends LongIdObject {
    
    private static final long serialVersionUID = -7140247169365891309L;
    
    /** 教学任务 */
    private TeachTask task;
    
    /** 授课地点 */
    private String addressRequirement;
    
    /** 上课所需教学软件及环境要求 */
    private String environmentRequirement;
    
    public TeachTask getTask() {
        return task;
    }
    
    public void setTask(TeachTask task) {
        this.task = task;
    }
    
    public String getAddressRequirement() {
        return addressRequirement;
    }
    
    public void setAddressRequirement(String addressRequirement) {
        this.addressRequirement = addressRequirement;
    }
    
    public String getEnvironmentRequirement() {
        return environmentRequirement;
    }
    
    public void setEnvironmentRequirement(String environmentRequirement) {
        this.environmentRequirement = environmentRequirement;
    }
}
