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
 * chaostone             2006-5-23            Created
 *  
 ********************************************************************************/
package com.shufe.dao.course.task;

import java.util.Collection;

import com.shufe.model.course.task.TeachTask;

/**
 * 教学任务课程序号产生器
 * 
 * @author chaostone
 * 
 */
public interface TaskSeqNoGenerator {
    
    /**
     * 依照教学任务的教学日历和学生类别进行生成序号
     * 
     * @param task
     * @return
     */
    public void genTaskSeqNo(TeachTask task);
    
    public void genTaskSeqNos(Collection tasks);
}
