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
 * chaostone             2006-12-4            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.util.MessageResources;

import com.ekingstar.commons.utils.persistence.UtilService;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;

public class TaskPerClassPropertyExtractor extends TeachTaskPropertyExtractor {
    
    Map courseAndClassMap = new HashMap();
    
    public TaskPerClassPropertyExtractor(Locale locale, MessageResources resources, UtilService utilService) {
        super(locale, resources, utilService);
    }
    
    public Object getPropertyValue(Object target, String property) throws Exception {
        TeachTask task = (TeachTask) target;
        if ("teachClass.adminClasses".equals(property)) {
            for (Iterator iter = task.getTeachClass().getAdminClasses().iterator(); iter.hasNext();) {
                AdminClass adminClass = (AdminClass) iter.next();
                if (null == courseAndClassMap.get(adminClass.getId() + ","
                        + task.getCourse().getId())) {
                    courseAndClassMap.put(adminClass.getId() + "," + task.getCourse().getId(),
                            Boolean.TRUE);
                    return adminClass.getName();
                }
            }
            return null;
        } else
            return super.getPropertyValue(target, property);
    }
    
}
