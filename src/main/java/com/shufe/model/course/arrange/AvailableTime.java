//$Id: AvailableTime.java,v 1.5 2006/11/14 07:31:16 duanth Exp $
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
 * chaostone             2005-11-8         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.arrange;

import java.io.Serializable;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.util.MessageResources;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 可用时间.主要为教师和教室配置排课中的可用时间.
 * 
 * @author chaostone 2005-11-8
 */
public class AvailableTime extends LongIdObject implements Serializable {
    
    private static final long serialVersionUID = -3056451280716927057L;
    
    public static String STRUCTS = (String) SystemConfigLoader.getConfig().getConfigItemValue(
            "arrange.time.struct");
    
    /** 可用时间的最大长度 */
    public static int MAX_LENGTH = TeachCalendar.MAXUNITS * WeekInfo.MAX;
    
    /** 可用时间中的优先级 */
    public static String[] PRIORITIES = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8",
            "9" };
    
    /** 教师常用时间 */
    public static String commonTeacherAvailTime = null;
    
    /** 教室常用时间 */
    public static String commonRoomAvailTime = null;
    
    /** 任务常用时间 */
    public static String commonTaskAvailTime = null;
    
    public static String EMPTY = null;
    static {
        commonRoomAvailTime = StringUtils.repeat("1", WeekInfo.MAX * TeachCalendar.MAXUNITS);
        commonTeacherAvailTime = commonRoomAvailTime;
        commonTaskAvailTime = commonRoomAvailTime;
        EMPTY = StringUtils.repeat("0", WeekInfo.MAX * TeachCalendar.MAXUNITS);
    }
    
    public AvailableTime() {
    }
    
    public AvailableTime(String available) {
        this.available = available;
    }
    
    /** 可用时间描述 */
    private String available;
    
    /** 备注 */
    private String remark;
    
    /** 节数：每个分段的起始小节 */
    private String struct = AvailableTime.STRUCTS;
    
    private int units;
    
    /**
     * 依照具体的resource 进行缩略表示
     * 
     * @param locale
     *            可以为空
     * @param resource
     *            不可以为空
     * @return
     */
    public String abbreviate(Locale locale, MessageResources resource) {
        if (StringUtils.isEmpty(available))
            return "";
        if (available.indexOf("0") == -1) {
            return resource.getMessage(locale, "time.week");
        }
        StringBuffer sb = new StringBuffer();
        
        String[] segments = StringUtils.split(StringUtils.substringAfter(struct, ":"), ",");
        int[] segs = new int[segments.length];
        for (int i = 0; i < segments.length; i++) {
            segs[i] = NumberUtils.toInt(segments[i]);
        }
        
        String allZeroUnit = StringUtils.repeat("0", units);
        for (int i = 0; i < WeekInfo.MAX; i++) {
            String dayAvalible = available.substring(i * units, i * units + units);
            if (dayAvalible.equals(allZeroUnit))
                continue;
            sb.append(resource.getMessage(locale, (String) WeekInfo.MESSAGE_KEYS.get(new Integer(
                    i + 1))));
            if (!StringUtils.contains(dayAvalible, '0')) {
                sb.append(" ");
                continue;
            } else {
                sb.append("(");
                String morning = abbreviateUnit(resource.getMessage(locale, "time.morning"),
                        dayAvalible.substring(0, segs[0]), 0);
                String afternoon = abbreviateUnit(resource.getMessage(locale, "time.afternoon"),
                        dayAvalible.substring(segs[0], segs[1]), segs[0]);
                String evening = abbreviateUnit(resource.getMessage(locale, "time.evening"),
                        dayAvalible.substring(segs[1], units), segs[1]);
                boolean hasOne = false;
                if (!StringUtils.isEmpty(morning)) {
                    hasOne = true;
                    sb.append(morning);
                }
                if (!StringUtils.isEmpty(afternoon)) {
                    if (hasOne)
                        sb.append(" ");
                    sb.append(afternoon);
                    hasOne = true;
                }
                if (!StringUtils.isEmpty(evening)) {
                    if (hasOne)
                        sb.append(" ");
                    sb.append(evening);
                }
                sb.append(") ");
            }
            
        }
        return sb.toString();
    }
    
    /**
     * 对可用时间进行缩略显示
     * 
     * @return
     */
    public String abbreviate() {
        if (StringUtils.isEmpty(available))
            return "";
        StringBuffer sb = new StringBuffer();
        
        String[] segments = StringUtils.split(StringUtils.substringAfter(struct, ":"), ",");
        int[] segs = new int[segments.length];
        for (int i = 0; i < segments.length; i++) {
            segs[i] = NumberUtils.toInt(segments[i]);
        }
        
        String allZeroUnit = StringUtils.repeat("0", units);
        
        for (int i = 0; i < WeekInfo.MAX; i++) {
            String dayAvalible = available.substring(i * units, i * units + units);
            if (dayAvalible.equals(allZeroUnit))
                continue;
            sb.append(((WeekInfo) WeekInfo.WEEKS.get(i)).getName());
            if (!StringUtils.contains(dayAvalible, '0')) {
                sb.append(" ");
                continue;
            } else {
                sb.append("(");
                String morning = abbreviateUnit("上午", dayAvalible.substring(0, segs[0]), 0);
                String afternoon = abbreviateUnit("下午", dayAvalible.substring(segs[0], segs[1]),
                        segs[0]);
                String evening = abbreviateUnit("晚上", dayAvalible.substring(segs[1], units),
                        segs[1]);
                boolean hasOne = false;
                if (!StringUtils.isEmpty(morning)) {
                    hasOne = true;
                    sb.append(morning);
                }
                if (!StringUtils.isEmpty(afternoon)) {
                    if (hasOne)
                        sb.append(" ");
                    sb.append(afternoon);
                    hasOne = true;
                }
                if (!StringUtils.isEmpty(evening)) {
                    if (hasOne)
                        sb.append(" ");
                    sb.append(evening);
                }
                sb.append(") ");
            }
            
        }
        return sb.toString();
    }
    
    private String abbreviateUnit(String segInfo, String units, int start) {
        StringBuffer sb = new StringBuffer();
        if (units.equals(StringUtils.repeat("0", units.length())))
            return "";
        else
            sb.append(segInfo);
        if (StringUtils.contains(units, "0")) {
            for (int i = 0; i < units.length(); i++) {
                if (units.charAt(i) != '0')
                    sb.append(start + i + 1).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    
    /**
     * 设置某个星期的某个小节的占用情况
     * 
     * @param week[1..7]
     * @param unit[1..getUnits()]
     * @param available
     */
    public void setAvailableFor(int week, int unit, boolean available) {
        StringBuffer sb = new StringBuffer(this.available);
        sb.setCharAt((week - 1) * getUnits() + unit - 1, available ? '1' : '0');
        setAvailable(sb.toString());
    }
    
    /**
     * 合并占用时间<br>
     * 取两者之和者，但不允许超过9.<br>
     * 
     * @param time
     */
    public void mergeWith(AvailableTime time) {
        if (null != time && StringUtils.isNotEmpty(time.getAvailable())) {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < time.getAvailable().length(); i++) {
                int own = getAvailable().charAt(i) - 48;
                int other = time.getAvailable().charAt(i) - 48;
                if (own + other > 9)
                    buffer.append("9");
                else
                    buffer.append(String.valueOf(own + other));
            }
            setAvailable(buffer.toString());
        }
    }
    
    /**
     * 剥离占用时间<br>
     * 相应为进行相减，直到为零<br>
     * 
     * @param time
     */
    public void detachWith(AvailableTime time) {
        if (null != time && StringUtils.isNotEmpty(time.getAvailable())) {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < time.getAvailable().length(); i++) {
                int own = getAvailable().charAt(i) - 48;
                int other = time.getAvailable().charAt(i) - 48;
                if (own - other < 0)
                    buffer.append("0");
                else
                    buffer.append(String.valueOf(own - other));
            }
            setAvailable(buffer.toString());
        }
    }
    
    /**
     * 判断时间占用是否符合由0-9组成的字符串<br>
     * 该字符串长度为WeekInfo.max * getUnits()
     * 
     * @return
     */
    public boolean isValid() {
        if (StringUtils.isEmpty(getAvailable()))
            return false;
        if (getAvailable().length() != WeekInfo.MAX * getUnits())
            return false;
        /* 验证是否所有的可有时间在1-9之间 */
        for (int i = 0; i < WeekInfo.MAX * getUnits(); i++)
            if (getAvailable().charAt(i) < '0' || getAvailable().charAt(i) > '9')
                return false;
        return true;
    }
    
    public String getAvailable() {
        return available;
    }
    
    public void setAvailable(String available) {
        this.available = available;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getStruct() {
        return struct;
    }
    
    public void setStruct(String struct) {
        this.struct = struct;
        if (StringUtils.isNotEmpty(struct)) {
            units = NumberUtils.toInt(StringUtils.substringBefore(struct, ":"));
        }
    }
    
    public int getUnits() {
        if (0 == units) {
            return TeachCalendar.MAXUNITS;
        } else {
            return units;
        }
    }
    
    public void setUnits(int units) {
        this.units = units;
    }
    
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("available", this.available)
                .toString();
    }
}
