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
 * chaostone             2006-7-13            Created
 *  
 ********************************************************************************/
package com.shufe.model.system.calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.CourseUnit;

/**
 * 每个小节的时间设置<br>
 * 
 * <pre>
 * 时间设置用来设置每个小节的起始时间和结束时间。
 *  可以给教学日历指定时间设置来实现每个学期的上课小节的具体时间范围。
 *  如果小节的时间范围比较稳定，多个学期可以指定同一个时间设置。
 * </pre>
 * 
 * @author chaostone
 * 
 */
public class TimeSetting extends LongIdObject implements com.ekingstar.eams.system.time.TimeSetting {
    
    private static final long serialVersionUID = 3359358617662801996L;
    
    private String name;
    
    private StudentType stdType;
    
    private Set courseUnits = new HashSet();
    
    public Set getCourseUnits() {
        return courseUnits;
    }
    
    public void setCourseUnits(Set courseUnits) {
        this.courseUnits = courseUnits;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public StudentType getStdType() {
        return stdType;
    }
    
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
    /**
     * 查询在指定时间范围内的开始小节和结束小节的编号.
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public Integer[] getUnitIndexes(Integer startTime, Integer finishTime) {
        CourseUnit example = new CourseUnit(startTime, finishTime);
        List units = new ArrayList(getCourseUnits());
        Collections.sort(units);
        Integer[] unitIndexes = new Integer[2];
        for (int i = 0; i < units.size(); i++) {
            CourseUnit unit = (CourseUnit) units.get(i);
            if (unit.crossWith(example)) {
                unitIndexes[0] = unit.getIndex();
                break;
            }
        }
        for (int i = units.size() - 1; i >= 0; i--) {
            CourseUnit unit = (CourseUnit) units.get(i);
            if (unit.crossWith(example)) {
                unitIndexes[1] = unit.getIndex();
                break;
            }
        }
        return unitIndexes;
    }
    
    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof TimeSetting)) {
            return false;
        }
        TimeSetting rhs = (TimeSetting) object;
        return new EqualsBuilder().append(this.id, rhs.id).isEquals();
    }
    
    /**
     * 查找指定节次的时间([1..TeachCalendar.MAXUNITS])
     * 
     * @param unitIndex
     * @return
     */
    public CourseUnit getCourseUnit(int unitIndex) {
        for (Iterator iter = getCourseUnits().iterator(); iter.hasNext();) {
            CourseUnit unit = (CourseUnit) iter.next();
            if (unit.getIndex().intValue() == unitIndex) {
                return unit;
            }
        }
        return null;
    }
    
}
