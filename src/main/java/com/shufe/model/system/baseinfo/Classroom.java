//$Id: Classroom.java,v 1.3 2006/11/24 00:53:26 duanth Exp $
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
 * chaostone             2005-9-9         Created
 * hc                    2005-11-9        modify: add "preRoom" and "nextRoom"; del "nearRooms"
 ********************************************************************************/

package com.shufe.model.system.baseinfo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.baseinfo.Building;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.baseinfo.model.BaseInfo;
import com.shufe.model.course.arrange.AvailableTime;

/**
 * 教室基本信息
 * 
 * @author chaostone 2005-9-9
 */
public class Classroom extends BaseInfo implements Comparable, Serializable {
    
    private static final long serialVersionUID = 6012525336217957902L;
    
    /** 可容纳考试人数 */
    private Integer capacityOfExam;
    
    /** 可容纳听课人数 */
    private Integer capacityOfCourse;
    
    /** 真正容量 */
    private Integer capacity;
    
    /** 所在校区 */
    private SchoolDistrict schoolDistrict;
    
    /** 所在教学楼 */
    private Building building;
    
    /** 设备配置代码 */
    private ClassroomType configType;
    
    /** 教室所处楼层 */
    private Integer floor;
    
    /** 使用部门列表 */
    private Set departments = new HashSet();
    
    /** 教室可用时间 */
    private AvailableTime availableTime;
    
    /** 是否排课检查 */
    private Boolean isCheckActivity;
    
    private Set activities;
    
    public Classroom() {
    }
    
    public Classroom(Long id) {
        super(id);
    }
    
    public Classroom(String code) {
        super(Long.valueOf(code));
        setCode(code);
    }
    
    public Classroom(Long id, String name, Integer capacityOfCourse) {
        this.id = id;
        this.name = name;
        this.capacityOfCourse = capacityOfCourse;
    }
    
    /**
     * @hibernate.many-to-one column="JXLDM" not-null="true"
     * @return
     */
    public Building getBuilding() {
        return building;
    }
    
    public void setBuilding(Building building) {
        this.building = building;
    }
    
    /**
     * @hibernate.property column="SKRS"
     * @return
     */
    public Integer getCapacityOfCourse() {
        return capacityOfCourse;
    }
    
    public void setCapacityOfCourse(Integer capacityOfCourse) {
        this.capacityOfCourse = capacityOfCourse;
    }
    
    /**
     * @hibernate.property column="TKRS"
     * @return
     */
    public Integer getCapacityOfExam() {
        return capacityOfExam;
    }
    
    public void setCapacityOfExam(Integer capacityOfExam) {
        this.capacityOfExam = capacityOfExam;
    }
    
    /**
     * @hibernate.many-to-one column="SBPZDM" not-null="true"
     * @return
     */
    public ClassroomType getConfigType() {
        return configType;
    }
    
    public void setConfigType(ClassroomType configType) {
        this.configType = configType;
    }
    
    public Set getDepartments() {
        return departments;
    }
    
    public void setDepartments(Set departments) {
        this.departments = departments;
    }
    
    public Integer getFloor() {
        return floor;
    }
    
    public void setFloor(Integer floor) {
        this.floor = floor;
    }
    
    public boolean selfCheck() {
        return StringUtils.isNotEmpty(getName());
    }
    
    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        Classroom rhs = (Classroom) object;
        return new EqualsBuilder().append(this.getId(), rhs.getId()).isEquals();
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("id", this.getId())
                .append("name", this.getName()).toString();
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-1646052365, -1998381357).append(this.getId()).toHashCode();
    }
    
    /**
     * @return Returns the availableTime.
     */
    public AvailableTime getAvailableTime() {
        return availableTime;
    }
    
    /**
     * @param availableTime
     *            The availableTime to set.
     */
    public void setAvailableTime(AvailableTime availableTime) {
        this.availableTime = availableTime;
    }
    
    public Set getActivities() {
        return activities;
    }
    
    public void setActivities(Set activities) {
        this.activities = activities;
    }
    
    public SchoolDistrict getSchoolDistrict() {
        return schoolDistrict;
    }
    
    public void setSchoolDistrict(SchoolDistrict schoolDistrict) {
        this.schoolDistrict = schoolDistrict;
    }
    
    /**
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(Object object) {
        Classroom myClass = (Classroom) object;
        return new CompareToBuilder().append(this.getId(), myClass.getId()).toComparison();
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    
    public Boolean getIsCheckActivity() {
        return isCheckActivity;
    }

    
    public void setIsCheckActivity(Boolean isCheckActivity) {
        this.isCheckActivity = isCheckActivity;
    }
    
}
