//$Id: SimpleAdminClassSplitMode.java 2008-10-29 下午07:12:36 chaostone Exp $
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
 * chaostone      2008-10-29  Created
 *  
 ********************************************************************************/
package com.shufe.service.course.task;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.task.TeachClass;
import com.shufe.model.system.baseinfo.AdminClass;

public class SimpleAdminClassSplitMode extends TeachClassSplitMode {
    
    public SimpleAdminClassSplitMode(String name) {
        super(name);
    }
    
    private Set secondClasses = new HashSet();
    
    public TeachClass[] splitClass(TeachClass target, int num) {
        Set firstClasses = new HashSet(target.getAdminClasses());
        firstClasses.removeAll(secondClasses);
        // 组装两个教学任务班级
        TeachClass[] classes = new TeachClass[num];
        classes[0] = target;
        classes[0].getAdminClasses().clear();
        classes[0].getAdminClasses().addAll(firstClasses);
        
        classes[1] = (TeachClass) target.clone();
        classes[1].getAdminClasses().clear();
        classes[1].getAdminClasses().addAll(secondClasses);
        
        // 如果原教学班已经指定学生了，则按已拆分的班级拆分学生
        if (Boolean.TRUE.equals(target.isStdAssigned())) {
            Set secondTakes = new HashSet();
            for (Iterator it = classes[0].getCourseTakes().iterator(); it.hasNext();) {
                CourseTake take = (CourseTake) it.next();
                for (Iterator iterator = secondClasses.iterator(); iterator.hasNext();) {
                    AdminClass clazz = (AdminClass) iterator.next();
                    if (clazz.getStudents().contains(take.getStudent())) {
                        secondTakes.add(take);
                    }
                }
            }
            classes[0].getCourseTakes().removeAll(secondTakes);
            classes[0].calcStdCount();
            
            classes[1].getCourseTakes().addAll(secondTakes);
            classes[1].calcStdCount();
        }
        classes[0].calcPlanStdCount(true);
        classes[1].calcPlanStdCount(true);
        secondClasses.clear();
        return classes;
    }
    
    public void setSecondClasses(Set secondClasses) {
        this.secondClasses = secondClasses;
    }
    
}
