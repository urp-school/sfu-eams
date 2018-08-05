//$Id: TeachTaskUtil.java,v 1.5 2006/11/04 08:48:56 duanth Exp $
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
 * chaostone             2005-11-18         Created
 *  
 ********************************************************************************/
package com.shufe.service.course.task;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.shufe.model.course.election.ElectStdScope;
import com.shufe.model.course.task.TaskElectInfo;
import com.shufe.model.course.task.TeachClass;
import com.shufe.model.course.task.TeachTask;

/**
 * 教学任务辅助类
 * 
 * @author chaostone 2005-11-18
 */
public class TeachTaskUtil {
    
    /**
     * 主要是合并教学班和教师、教室 1)将教学班中涉及到的行政班级合并到目标教学班班级中.<br>
     * 2)将教学班中的所有上课学生的修读信息合并到目标教学班班级中.<br>
     * 3)将两个教学班中涉及到的教师名单和教室名单合并到目标教学班班级中.<br>
     * 4)将两个教学班的实际人数和计划人数相加合并到目标教学班班级中.<br>
     * 
     * 次要合并学生选课信息<br>
     * 1)将选课的人数上下限,和实选人数，相加合并到目标教学任务的选课信息中<br>
     * 2)将两个教学任务的选课学生范围相加合并到目标任务中<br>
     * 
     * @param target
     *            合并目标
     * @param orig
     *            合并的另一个对象
     * @return
     */
    public static TeachTask merge(TeachTask target, TeachTask orig) {
        /*-----------------合并教师和教室------------------------*/
        Set teachers = new HashSet();
        teachers.addAll(target.getArrangeInfo().getTeachers());
        teachers.addAll(orig.getArrangeInfo().getTeachers());
        target.getArrangeInfo().getTeachers().clear();
        target.getArrangeInfo().getTeachers().addAll(teachers);
        target.getArrangeInfo().getSuggest().mergeRoom(orig.getArrangeInfo().getSuggest());
        
        TeachClass teachClass = orig.getTeachClass();
        /*-------------合并学生修读信息-----------------------------*/
        if (null != teachClass.getCourseTakes()) {
            target.getTeachClass().addCourseTakes(orig.getTeachClass().getCourseTakes());
            orig.getTeachClass().getCourseTakes().clear();
        }
        /*-------------合并行政班-------------------------------*/
        if (null != teachClass.getAdminClasses()) {
            target.getTeachClass().getAdminClasses().addAll(teachClass.getAdminClasses());
        }
        /*-------------合并实际人数和计划人数-------------------------*/
        int count = 0;
        // count += target.getTeachClass().getStdCount().intValue();
        // count += teachClass.getStdCount().intValue();
        // target.getTeachClass().setStdCount(new Integer(count));
        // count = 0;
        count += target.getTeachClass().getPlanStdCount().intValue();
        count += teachClass.getPlanStdCount().intValue();
        target.getTeachClass().setPlanStdCount(new Integer(count));
        
        if (target.getTeachClass().getName().indexOf(teachClass.getName()) == -1) {
            target.getTeachClass().setName(
                    StringUtils.abbreviate(target.getTeachClass().getName() + ","
                            + teachClass.getName(), 50));
        }
        /*-------------合并选课信息-------------------------*/
        TaskElectInfo electInfo = orig.getElectInfo();
        count = 0;
        count += target.getElectInfo().getMaxStdCount().intValue();
        count += electInfo.getMaxStdCount().intValue();
        target.getElectInfo().setMaxStdCount(new Integer(count));
        count = 0;
        count += target.getElectInfo().getMinStdCount().intValue();
        count += electInfo.getMinStdCount().intValue();
        target.getElectInfo().setMinStdCount(new Integer(count));
        
        for (Iterator iter = orig.getElectInfo().getElectScopes().iterator(); iter.hasNext();) {
            ElectStdScope scope = (ElectStdScope) iter.next();
            ElectStdScope clonedScope = (ElectStdScope) scope.clone();
            clonedScope.setTask(target);
            target.getElectInfo().getElectScopes().add(clonedScope);
        }
        return target;
    }
}
