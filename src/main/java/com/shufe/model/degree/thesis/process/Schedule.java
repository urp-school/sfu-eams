//$Id: Schedule.java,v 1.1 2007-3-8 13:21:30 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-3-8         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.thesis.process;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.StudentType;

public class Schedule extends LongIdObject implements Cloneable {
    
    private static final long serialVersionUID = 1334700267610588222L;
    
    private StudentType studentType;// 学生类别
    
    private String enrollYear; // 所在年级
    
    private String studyLength; // 学制
    
    private Set tacheSettings = new HashSet(); // 进度中的环节
    
    private String remark; // 进度描述
    
    /**
     * @return Returns the enrollYear.
     */
    public String getEnrollYear() {
        return enrollYear;
    }
    
    /**
     * @param enrollYear
     *            The enrollYear to set.
     */
    public void setEnrollYear(String enrollYear) {
        this.enrollYear = enrollYear;
    }
    
    /**
     * @return Returns the remark.
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param remark
     *            The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * @return Returns the studentType.
     */
    public StudentType getStudentType() {
        return studentType;
    }
    
    /**
     * @param studentType
     *            The studentType to set.
     */
    public void setStudentType(StudentType studentType) {
        this.studentType = studentType;
    }
    
    /**
     * @return Returns the studyLength.
     */
    public String getStudyLength() {
        return studyLength;
    }
    
    /**
     * @param studyLength
     *            The studyLength to set.
     */
    public void setStudyLength(String studyLength) {
        this.studyLength = studyLength;
    }
    
    /**
     * @return Returns the tacheSettings.
     */
    public Set getTacheSettings() {
        return tacheSettings;
    }
    
    /**
     * @param tacheSettings
     *            The tacheSettings to set.
     */
    public void setTacheSettings(Set tacheSettings) {
        this.tacheSettings = tacheSettings;
    }
    
    public Object clone() {
        Schedule schedule = new Schedule();
        schedule.setId(null);
        schedule.setEnrollYear(this.getEnrollYear());
        schedule.setRemark(this.getRemark());
        schedule.setStudentType(this.getStudentType());
        schedule.setStudyLength(this.getStudyLength());
        schedule.setTacheSettings(this.getTacheSettings());
        return schedule;
    }
    
    /**
     * 根据环节code 得到某一个环节的环节设置
     * 
     * @param tacheCode
     * @return
     */
    public TacheSetting getSetting(String tacheCode) {
        TacheSetting tacheSetting = new TacheSetting();
        for (Iterator iter = this.getTacheSettings().iterator(); iter.hasNext();) {
            TacheSetting element = (TacheSetting) iter.next();
            if (tacheCode.equals(element.getTache().getCode())) {
                tacheSetting = element;
                break;
            }
        }
        return tacheSetting;
    }
    
    /**
     * 判断条件里面是否有对应的环节
     * 
     * @param tacheCode
     * @return
     */
    public boolean containTache(String tacheCode) {
        boolean flag = false;
        for (Iterator iter = this.getTacheSettings().iterator(); iter.hasNext();) {
            TacheSetting element = (TacheSetting) iter.next();
            if (tacheCode.equals(element.getTache().getCode())) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * 进度设置的复制功能
     * 
     * @param schedule
     * @return
     * @throws CloneNotSupportedException
     */
    public Schedule copy() throws CloneNotSupportedException {
        Schedule copeSchedule = (Schedule) this.clone();
        Set settings = new HashSet();
        for (Iterator iter = copeSchedule.getTacheSettings().iterator(); iter.hasNext();) {
            TacheSetting setting = (TacheSetting) iter.next();
            TacheSetting copySetting = (TacheSetting) setting.clone();
            copySetting.setSchedule(copeSchedule);
            settings.add(copySetting);
        }
        copeSchedule.setTacheSettings(settings);
        return copeSchedule;
    }
    
    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof Schedule)) {
            return false;
        }
        Schedule rhs = (Schedule) object;
        return new EqualsBuilder().append(this.studyLength, rhs.studyLength).append(
                this.studentType, rhs.studentType).append(this.enrollYear, rhs.enrollYear)
                .isEquals();
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-774925725, 1497404811).append(this.studyLength).append(
                this.studentType).append(this.enrollYear).toHashCode();
    }
}
