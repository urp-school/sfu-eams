//$Id: CourseActivityDAO.java,v 1.1 2006/11/09 11:22:02 duanth Exp $
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
 * chaostone             2005-11-9         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.arrange.task;

import java.util.Collection;

import com.shufe.dao.BasicDAO;

public interface CourseActivityDAO extends BasicDAO {
    /**
     * 存储一批教学任务中的教学活动
     * @param tasks
     */
    public void saveActivities(Collection tasks);
    /**
     * 批量删除排课结果
     * @param tasks
     */
    public void removeActivities(Collection tasks);
    /**
     * 批量删除排课结果
     * @param taskIds
     */
    public void removeActivities(Long[] taskIds);
}
