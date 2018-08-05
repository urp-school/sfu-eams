//$Id: RequirePreferServiceImpl.java,v 1.1 2006/08/22 10:16:32 duanth Exp $
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

package com.shufe.service.course.task.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.shufe.dao.course.task.RequirePreferDAO;
import com.shufe.dao.course.task.TeachTaskDAO;
import com.shufe.model.course.task.RequirePrefer;
import com.shufe.model.course.task.TaskRequirement;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.BasicService;
import com.shufe.service.course.task.RequirePreferService;

/**
 * 教师任务要求使用偏好服务实现
 * 
 * @author chaostone 2005-12-18
 */
public class RequirePreferServiceImpl extends BasicService implements RequirePreferService {
    
    private RequirePreferDAO preferenceDAO;
    
    private TeachTaskDAO taskDAO;
    
    /**
     * @see com.shufe.service.course.task.RequirePreferService#getPreference(java.lang.Long)
     */
    public RequirePrefer getPreference(Long preferenceId) {
        return preferenceDAO.getPreference(preferenceId);
    }
    
    /**
     * @see com.shufe.service.course.task.RequirePreferService#savePreferenceForTask(com.shufe.model.course.task.TeachTask)
     */
    public void savePreferenceForTask(TeachTask task) {
        if (task.getArrangeInfo().isSingleTeacher()) {
            Teacher teacher = (Teacher) task.getArrangeInfo().getTeachers().iterator().next();
            RequirePrefer preference = getPreference(teacher, task.getCourse());
            TaskRequirement requireInPrefer = null;
            if (null == preference) {
                preference = new RequirePrefer(teacher, task.getCourse());
                requireInPrefer = preference.getRequire();
            } else {
                requireInPrefer = preference.getRequire();
            }
            TaskRequirement requireInTask = task.getRequirement();
            copyRequirement(requireInPrefer, requireInTask);
            utilDao.saveOrUpdate(preference);
        }
    }
    
    private void copyRequirement(TaskRequirement requireInPrefer, TaskRequirement requireInTask) {
        requireInPrefer.setRoomConfigType(requireInTask.getRoomConfigType());
        requireInPrefer.setTeachLangType(requireInTask.getTeachLangType());
        requireInPrefer.setCases(requireInTask.getCases());
        requireInPrefer.setReferenceBooks(requireInTask.getReferenceBooks());
        requireInPrefer.setTextbooks(new HashSet(requireInTask.getTextbooks()));
        requireInPrefer.setRequireRemark(requireInTask.getRequireRemark());
    }
    
    /**
     * 
     */
    public RequirePrefer getPreference(Teacher teacher, Course course) {
        List preferences = getPreferences(new RequirePrefer(teacher, course));
        if (preferences.isEmpty())
            return null;
        else
            return (RequirePrefer) preferences.iterator().next();
    }
    
    /**
     * @see com.shufe.service.course.task.RequirePreferService#getPreference(com.shufe.model.system.baseinfo.Teacher)
     */
    public List getPreferences(RequirePrefer preference) {
        if (null == preference.getTeacher())
            return Collections.EMPTY_LIST;
        else
            return preferenceDAO.getPreferences(preference);
    }
    
    /**
     * @see com.shufe.service.course.task.RequirePreferService#savePreference(com.shufe.model.course.task.RequirePrefer)
     */
    public void savePreference(RequirePrefer preference) {
        if (null == getPreference(preference.getTeacher(), preference.getCourse()))
            preferenceDAO.savePreference(preference);
    }
    
    /**
     * @see com.shufe.service.course.task.RequirePreferService#setPreferenceFor(java.util.Collection)
     */
    public void setPreferenceFor(Collection tasks) {
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            if (task.getArrangeInfo().isSingleTeacher()) {
                Teacher teacher = (Teacher) task.getArrangeInfo().getTeachers().iterator().next();
                RequirePrefer preference = getPreference(teacher, task.getCourse());
                EntityUtils.evictEmptyProperty(preference);
                if (null != preference) {
                    copyRequirement(task.getRequirement(), preference.getRequire());
                } else {
                    preference = new RequirePrefer(teacher, task.getCourse());
                    preference.setRequire(task.getRequirement());
                    preferenceDAO.savePreference(preference);
                }
                taskDAO.saveOrUpdate(task);
            }
        }
    }
    
    /**
     * @see com.shufe.service.course.task.RequirePreferService#setPreferenceFor(java.lang.String)
     */
    public void setPreferenceFor(String taskIdSeq) {
        if (StringUtils.isNotEmpty(taskIdSeq))
            setPreferenceFor(taskDAO.getTeachTasksByIds(SeqStringUtil.transformToLong(taskIdSeq)));
    }
    
    /**
     * @see com.shufe.service.course.task.RequirePreferService#updatePreference(com.shufe.model.course.task.RequirePrefer)
     */
    public void updatePreference(RequirePrefer preference) {
        preferenceDAO.updatePreference(preference);
    }
    
    /**
     * @param preferenceDAO
     *            The preferenceDAO to set.
     */
    public void setPreferenceDAO(RequirePreferDAO preferenceDAO) {
        this.preferenceDAO = preferenceDAO;
    }
    
    /**
     * @param taskDAO
     *            The taskDAO to set.
     */
    public void setTaskDAO(TeachTaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }
    
}
