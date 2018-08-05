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
 * 塞外狂人             2006-9-5            Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.tutorManager.tutor;

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.TutorType;
import com.ekingstar.security.User;
import com.shufe.model.system.baseinfo.Teacher;

/**
 * 导师申请
 * 
 * @author cwx
 * @param cwx2007-2-25修改
 */
public class TutorApply extends LongIdObject {
    
    private static final long serialVersionUID = 5124863339049919381L;
    
    private Teacher teacher;// 申请人信息
    
    private TutorType tutorType = new TutorType();// 申请导师类别
    
    private Boolean isPass; // 是否通过申请
    
    private String accessoryName; // 上传文件名
    
    private Date applyTime; // 申请时间
    
    private Date passTime; // 通过时间
    
    private User user; // 授予通过的用户
    
    /**
     * @return Returns the tutorType.
     */
    public TutorType getTutorType() {
        return tutorType;
    }
    
    /**
     * @param tutorType The tutorType to set.
     */
    public void setTutorType(TutorType tutorType) {
        this.tutorType = tutorType;
    }
    
    /**
     * @return Returns the teacher.
     */
    public Teacher getTeacher() {
        return teacher;
    }
    
    /**
     * @param teacher The teacher to set.
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    /**
     * @return Returns the accessoryName.
     */
    public String getAccessoryName() {
        return accessoryName;
    }
    
    /**
     * @param accessoryName The accessoryName to set.
     */
    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }
    
    /**
     * @return Returns the isPass.
     */
    public Boolean getIsPass() {
        return isPass;
    }
    
    /**
     * @param isPass The isPass to set.
     */
    public void setIsPass(Boolean isPass) {
        this.isPass = isPass;
    }
    
    /**
     * @return Returns the applyTime.
     */
    public Date getApplyTime() {
        return applyTime;
    }
    
    /**
     * @param applyTime The applyTime to set.
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
    
    /**
     * @return Returns the passTime.
     */
    public Date getPassTime() {
        return passTime;
    }
    
    /**
     * @param passTime The passTime to set.
     */
    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }
    
    /**
     * @return Returns the user.
     */
    public User getUser() {
        return user;
    }
    
    /**
     * @param user The user to set.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
