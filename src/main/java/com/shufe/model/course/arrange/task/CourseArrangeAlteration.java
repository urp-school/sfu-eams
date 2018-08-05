//$Id: CourseArrangeAlteration.java,v 1.1 2008-3-3 上午09:14:13 zhouqi Exp $
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
 * zhouqi              2008-3-3         	Created
 *  
 ********************************************************************************/

package com.shufe.model.course.arrange.task;

import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.security.User;
import com.shufe.model.course.task.TeachTask;

/**
 * @author zhouqi
 */
public class CourseArrangeAlteration extends LongIdObject {
    
    private static final long serialVersionUID = 9072367832013686843L;
    
    private TeachTask task;
    
    /** 调课前信息 */
    private String alterationBefore;
    
    /** 调课后信息 */
    private String alterationAfter;
    
    /** 调课人 */
    private User alterBy;
    
    /** 访问路径 */
    private String alterFrom;
    
    /** 调课发生时间 */
    private Date alterationAt;
    
    /** 记录被发生位置： 0-手工排课 1-统计筛选 */
    private Integer happenPlace;
    
    /**
     * @return the alterationAfter
     */
    public String getAlterationAfter() {
        return alterationAfter;
    }
    
    /**
     * @param alterationAfter
     *            the alterationAfter to set
     */
    public void setAlterationAfter(String alterationAfter) {
        this.alterationAfter = alterationAfter;
    }
    
    /**
     * @return the alterationAt
     */
    public Date getAlterationAt() {
        return alterationAt;
    }
    
    /**
     * @param alterationAt
     *            the alterationAt to set
     */
    public void setAlterationAt(Date alterationAt) {
        this.alterationAt = alterationAt;
    }
    
    /**
     * @return the alterationBefore
     */
    public String getAlterationBefore() {
        return alterationBefore;
    }
    
    /**
     * @param alterationBefore
     *            the alterationBefore to set
     */
    public void setAlterationBefore(String alterationBefore) {
        this.alterationBefore = alterationBefore;
    }
    
    /**
     * @return the task
     */
    public TeachTask getTask() {
        return task;
    }
    
    /**
     * @param task
     *            the task to set
     */
    public void setTask(TeachTask task) {
        this.task = task;
    }
    
    /**
     * @return the alterBy
     */
    public User getAlterBy() {
        return alterBy;
    }
    
    /**
     * @param alterBy
     *            the alterBy to set
     */
    public void setAlterBy(User alterBy) {
        this.alterBy = alterBy;
    }
    
    /**
     * @return the alterFrom
     */
    public String getAlterFrom() {
        return alterFrom;
    }
    
    /**
     * @param alterFrom
     *            the alterFrom to set
     */
    public void setAlterFrom(String alterFrom) {
        this.alterFrom = alterFrom;
    }
    
    public Integer getHappenPlace() {
        return happenPlace;
    }
    
    public void setHappenPlace(Integer happenPlace) {
        this.happenPlace = happenPlace;
    }
}
