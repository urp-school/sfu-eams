//$Id: PunishmentRecord.java,v 1.1 2006/08/02 00:53:02 duanth Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-11-25         Created
 *  
 ********************************************************************************/

package com.shufe.model.std.awardPunish;

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.PunishmentType;
import com.ekingstar.eams.system.time.TeachCalendar;
import com.shufe.model.std.Student;

/**
 * 处分记录
 * 
 * @author dell
 */
public class Punishment extends LongIdObject {
    
    private static final long serialVersionUID = 1648452714242979752L;
    
    /**
     * 处分名称
     */
    private String name;
    
    /**
     * 处分类别
     */
    private PunishmentType type = new PunishmentType();
    
    /**
     * 学生
     */
    protected Student std = new Student();
    
    /** 教学日历 */
    private TeachCalendar calendar;
    
    /** 日期 */
    protected Date presentOn;
    
    /**
     * 撤销日期
     */
    protected Date withdrawOn;
    
    /**
     * 部门
     */
    protected String depart;
    
    /**
     * 备注
     */
    protected String remark;
    
    /**
     * 是否有效
     */
    private Boolean isValid;
    
    public String getDepart() {
        return depart;
    }
    
    public void setDepart(String depart) {
        this.depart = depart;
    }
    
    public Date getPresentOn() {
        return presentOn;
    }
    
    public void setPresentOn(Date presentOn) {
        this.presentOn = presentOn;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Student getStd() {
        return std;
    }
    
    public void setStd(Student std) {
        this.std = std;
    }
    
    public PunishmentType getType() {
        return type;
    }
    
    public void setType(PunishmentType type) {
        this.type = type;
    }
    
    public Date getWithdrawOn() {
        return withdrawOn;
    }
    
    public void setWithdrawOn(Date withdrawOn) {
        this.withdrawOn = withdrawOn;
    }
    
    public Boolean getIsValid() {
        return isValid;
    }
    
    public void setIsValid(Boolean isEffective) {
        this.isValid = isEffective;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
}
