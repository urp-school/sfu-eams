//$Id: RewardRecord.java,v 1.1 2006/08/02 00:53:03 duanth Exp $
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
import com.ekingstar.eams.system.basecode.industry.AwardType;
import com.ekingstar.eams.system.time.TeachCalendar;
import com.shufe.model.std.Student;

/**
 * 奖励记录
 * 
 * @author dell,yangdong,chaostone
 */
/**
 * @author zhouqi
 */
public class Award extends LongIdObject {
    
    private static final long serialVersionUID = 6383253267483581655L;
    
    /**
     * 奖励类别
     */
    private AwardType type = new AwardType();
    
    /**
     * 奖项名称
     */
    private String name;
    
    /**
     * 学生
     */
    private Student std = new Student();
    
    /** 教学日历 */
    private TeachCalendar calendar;
    
    /**
     * 颁发日期
     */
    private Date presentOn;
    
    /**
     * 撤销日期
     */
    private Date withdrawOn;
    
    /**
     * 颁发部门
     */
    private String depart;
    
    /**
     * 备注
     */
    private String remark;
    
    private Boolean isValid;
    
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
    
    public AwardType getType() {
        return type;
    }
    
    public void setType(AwardType type) {
        this.type = type;
    }
    
    public Date getWithdrawOn() {
        return withdrawOn;
    }
    
    public void setWithdrawOn(Date withdrawOn) {
        this.withdrawOn = withdrawOn;
    }
    
    public String getDepart() {
        return depart;
    }
    
    public void setDepart(String depart) {
        this.depart = depart;
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
