//$Id: RequirePreferService.java,v 1.1 2006/08/22 10:16:32 duanth Exp $
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
 * chaostone             2005-12-18         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.task;

import java.util.Collection;
import java.util.List;

import com.shufe.dao.course.task.RequirePreferDAO;
import com.shufe.dao.course.task.TeachTaskDAO;
import com.shufe.model.course.task.RequirePrefer;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Teacher;

// $Id: RequirePreferService.java,v 1.1 2006/08/22 10:16:32 duanth Exp $
/*
 * 
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is
 * intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source,
 * or other redistribution of this source is not permitted without written
 * permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/*******************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name Date Description ============ ============ ============ chaostone
 * 2005-12-18 Created
 * 
 ******************************************************************************/

/**
 * 教师教学任务要求偏好
 */
public interface RequirePreferService {
    /**
     * 
     * @param preferenceId
     * @return
     */
    public RequirePrefer getPreference(Long preferenceId);
    /**
     * 查询教师的使用偏好
     * 
     * @param teacher
     * @return
     */
    public RequirePrefer getPreference(Teacher teacher, Course course);

    /**
     * 
     * @param preference
     * @return
     */
    public List getPreferences(RequirePrefer preference);

    /**
     * 保存教师的使用偏好
     * 
     * @param preference
     */
    public void savePreference(RequirePrefer preference);
    /**
     * 将教学任务中的任务要求保存(更新)为使用偏好
     * @param task
     */
    public void savePreferenceForTask(TeachTask task);

    /**
     * 更新教师的使用偏好
     */
    public void updatePreference(RequirePrefer preference);

    /**
     * 对一批教学任务设置教师的使用偏好. 零或多个教师的任务不进行设置.<br>
     * 老师没有使用偏好的也不进行设置.<br>
     * 
     * @param taskIdSeq
     */
    public void setPreferenceFor(String taskIdSeq);

    /**
     * 
     * @param tasks
     */
    public void setPreferenceFor(Collection tasks);

    /**
     * 
     * @param taskDAO
     */
    public void setTaskDAO(TeachTaskDAO taskDAO);

    /**
     * @param preferenceDAO
     */
    public void setPreferenceDAO(RequirePreferDAO preferenceDAO);
}
