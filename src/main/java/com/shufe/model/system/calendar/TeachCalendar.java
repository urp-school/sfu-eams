//$Id: TeachCalendar.java,v 1.16 2007/01/23 09:23:31 duanth Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose start modification of the original source, or other redistribution of this source 
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
 * chaostone             2005-9-13         Created
 *  
 ********************************************************************************/

package com.shufe.model.system.calendar;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.math.NumberRange;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.ekingstar.eams.system.time.TeachCalendarScheme;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.TimeUnitUtil;

/**
 * 教学日历安排实体类<br>
 * 教学日历代表的是具体学年度的 学期设置，每个学期的起始时间和结束时间，教学周个数数.<br>
 * 以及每个教学周的具体起始、结束日期.<br>
 * [start,finish]
 * 
 * @hibernate.class table="JXRL_T"
 * @author chaostone 2005-9-13
 */
public class TeachCalendar extends LongIdObject implements Comparable,
        com.ekingstar.eams.system.time.TeachCalendar {
    
    private static final long serialVersionUID = 1418209086970834483L;
    
    public static int MAXUNITS = SystemConfigLoader.getConfig()
            .getIntParam("arrange.maxCourseUnit");
    
    /** 学生类别代码 */
    private StudentType studentType;
    
    /** 学年度,格式2005-2006 */
    private String year;
    
    /** 学期名称 */
    private String term;
    
    /** 起始日期 */
    private Date start;
    
    /** 截止日期 */
    private Date finish;
    
    /** 上课周数 */
    private Integer weeks;
    
    /** 对应该年份的起始自然周 */
    private Integer weekStart;
    
    /** 起始日期到结束日期的周跨度,不够一周按照一周算 */
    private Integer weekSpan;
    
    /** 上个教学日历 */
    private TeachCalendar previous;
    
    /** 下个教学日历 */
    private TeachCalendar next;
    
    /** 是否是小学期 */
    private Boolean isSmallTerm;
    
    /** 是否显示时间细节 */
    private Boolean displayTimeDetail = null;
    
    /** 时间设置 */
    private TimeSetting timeSetting = new TimeSetting();
    
    /** 教学日历方案类别 */
    private TeachCalendarScheme scheme;
    
    public TeachCalendarScheme getScheme() {
        return scheme;
    }
    
    public void setScheme(TeachCalendarScheme scheme) {
        this.scheme = scheme;
    }
    
    public TeachCalendar() {
        super();
    }
    
    public TeachCalendar(Long id) {
        this.id = id;
    }
    
    public String getCode() {
        return year + term;
    }
    
    public String getYearTerm() {
        return ((null == getYear()) ? "" : getYear()) + "        "
                + ((null == getTerm()) ? "" : getTerm());
    }
    
    public boolean checkId() {
        return !(null == id);
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("studentType",
                this.studentType.getId()).append("year", this.year).append("term", this.term)
                .append("start", this.getStart()).append("finish", this.getFinish()).append(
                        "weeks", this.getWeeks()).toString();
    }
    
    /**
     * 比较学生类别\学年度\起始日期
     * 
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(Object object) {
        TeachCalendar myClass = (TeachCalendar) object;
        return new CompareToBuilder().append(this.studentType, myClass.studentType).append(
                this.year, myClass.year).append(this.start, myClass.start).toComparison();
    }
    
    public boolean before(TeachCalendar calendar) {
        return finish.before(calendar.getStart());
    }
    
    public boolean after(TeachCalendar calendar) {
        return start.after(calendar.getFinish());
    }
    
    public java.util.Date getBeginOn() {
        return start;
    }
    
    public java.util.Date getEndOn() {
        return finish;
    }
    
    /**
     * 计算给定日期在教学周的次序<br>
     * 
     * @param date
     * @return 如果该日期在教学日历的开始日期和结束日期之外抛出错误.
     */
    public int getWeekIndex(java.util.Date date) {
        if (date.before(getStart()) || date.after(getFinish())) {
            throw new RuntimeException("Date outOf calendar bound!");
        } else {
            long length = date.getTime() - getStart().getTime();
            return (int) (length / (1000 * 60 * 60 * 24 * 7) + 1);
        }
    }
    
    /**
     * 返回该教学日历,从起始到结束总共多少周.
     * 
     * @return
     */
    public int getWeekLength() {
        if (null != getStart() && null != getFinish()) {
            long length = getFinish().getTime() - getStart().getTime();
            return (int) (length / (1000 * 60 * 60 * 24 * 7) + 1);
        } else {
            return weekSpan.intValue();
        }
    }
    
    /**
     * 返回指定的教学周内的开始时间和结束时间
     * 
     * @param weekIndex
     *            [1..weeks]
     * @return
     */
    public java.util.Date[] getWeekTime(int weekIndex) {
        NumberRange weekRange = new NumberRange(new Integer(1), this.getWeeks());
        if (!weekRange.containsInteger(weekIndex))
            throw new IllegalArgumentException("weekIndex");
        else {
            java.util.Date[] dates = new java.util.Date[2];
            GregorianCalendar gc = new GregorianCalendar();
            if (weekIndex == 1) {
                dates[0] = getStart();
            } else {
                gc.setTime(getStart());
                int day = gc.get(Calendar.DAY_OF_YEAR);
                day -= gc.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
                day += (weekIndex - 1) * 7;
                gc.set(Calendar.DAY_OF_YEAR, day);
                dates[0] = gc.getTime();
            }
            
            gc.setTime(getStart());
            int day = gc.get(Calendar.DAY_OF_YEAR);
            // 回到周日
            day -= gc.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            // 增至周六
            day += (weekIndex) * 7 - 1;
            gc.set(Calendar.DAY_OF_YEAR, day);
            dates[1] = gc.getTime();
            
            if (dates[1].after(getFinish()))
                dates[1] = getFinish();
            return dates;
        }
    }
    
    /**
     * 查找指定教学周的指定星期的日期
     * 
     * @deprecated no use
     * @param weekIndex[1..weeks]
     * @param dayInWeek[1..7]
     * @return
     */
    public GregorianCalendar getCalendarFor(int weekIndex, int dayInWeek) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(getStart());
        int day = gc.get(Calendar.DAY_OF_YEAR);
        day -= gc.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
        day += (weekIndex - 1) * 7 + dayInWeek;
        gc.set(Calendar.DAY_OF_YEAR, day);
        return gc;
    }
    
    /**
     * 获得该日历的真实起始年份(不是学年度的中的起始年份,例如第二个学期时)
     * 
     * @return
     */
    public int getStartYear() {
        if (null != getStart()) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(getStart());
            return gc.get(Calendar.YEAR);
        }
        return 0;
    }
    
    /**
     * 产生有效周的占用时间串<br>
     * 对于学年度跨越两个年份的情况,产生两个占有时间串.<br>
     * [beginWeek,endWeek]是指相对教学周内部的周数次序.起始为1.
     * 
     * @param beginWeek
     * @param endWeek
     * @return
     * @exception 当给定的周不在总周数的范围内.
     */
    public TimeUnit[] buildTimeUnits(int beginWeek, int endWeek) {
        if (beginWeek < 1 || endWeek > getWeekSpan().intValue()) {
            throw new RuntimeException("out of week range:[" + beginWeek + ":" + endWeek + "]");
        }
        return TimeUnitUtil.buildTimeUnits(getStartYear(), getWeekStart().intValue(), beginWeek,
                endWeek, TimeUnit.CONTINUELY);
    }
    
    public TimeUnit[] buildTimeUnits() {
        return TimeUnitUtil.buildTimeUnits(getStartYear(), getWeekStart().intValue(), 1, getWeeks()
                .intValue(), TimeUnit.CONTINUELY);
    }
    
    /**
     * 该学期所在的学年是否以周六结束.
     * 
     * @return
     */
    public boolean endAtSat() {
        return yearEndAtSat(getStartYear());
    }
    
    public static boolean yearEndAtSat(int year) {
        String LastDay = year + "-12-31";
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(java.sql.Date.valueOf(LastDay));
        return gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }
    
    /**
     * 计算并设置起始周. 重新计算日历的开始周数,最小值为1<br>
     */
    public void calcWeek() {
        Calendar a = new GregorianCalendar();
        a.setTime(getStart());
        setWeekStart(new Integer(a.get(Calendar.WEEK_OF_YEAR)));
        long length = getFinish().getTime() - getStart().getTime();
        setWeekSpan(new Integer(new Double(Math.ceil(length / (1000 * 3600 * 24 * 7.0))).intValue()));
    }
    
    /**
     * 判断日期是否在教学日历的范围内.
     * 
     * @param date
     * @return
     */
    public boolean contains(Date date) {
        if (date.before(getStart()) || date.after(getFinish()))
            return false;
        else
            return true;
    }
    
    /**
     * 得到日历对应的天数<br>
     * 起始日期和结束日期都是该日历范围内的.
     * 
     * @return
     */
    public int getDays() {
        long length = getFinish().getTime() - getStart().getTime();
        return (int) (length / (1000 * 60 * 60 * 24)) + 1;
    }
    
    public Boolean getDisplayTimeDetail() {
        return displayTimeDetail;
    }
    
    public void setDisplayTimeDetail(Boolean displayTimeDetail) {
        this.displayTimeDetail = displayTimeDetail;
    }
    
    public Date getStart() {
        return start;
    }
    
    public void setStart(Date begin) {
        this.start = begin;
    }
    
    public Date getFinish() {
        return finish;
    }
    
    public void setFinish(Date end) {
        this.finish = end;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getWeeks() {
        return weeks;
    }
    
    public void setWeeks(Integer length) {
        this.weeks = length;
    }
    
    public StudentType getStudentType() {
        return studentType;
    }
    
    public void setStudentType(StudentType studentType) {
        this.studentType = studentType;
    }
    
    public String getTerm() {
        return term;
    }
    
    public void setTerm(String term) {
        this.term = term;
    }
    
    public String getYear() {
        return year;
    }
    
    public void setYear(String year) {
        this.year = year;
    }
    
    public TeachCalendar getNext() {
        return next;
    }
    
    public void setNext(TeachCalendar next) {
        this.next = next;
    }
    
    public TeachCalendar getPrevious() {
        return previous;
    }
    
    public void setPrevious(TeachCalendar previous) {
        this.previous = previous;
    }
    
    public Integer getWeekStart() {
        return weekStart;
    }
    
    public Boolean getIsSmallTerm() {
        return isSmallTerm;
    }
    
    public void setIsSmallTerm(Boolean isSmallTerm) {
        this.isSmallTerm = isSmallTerm;
    }
    
    public void setWeekStart(Integer weekStart) {
        this.weekStart = weekStart;
    }
    
    public TimeSetting getTimeSetting() {
        return timeSetting;
    }
    
    public void setTimeSetting(TimeSetting timeSetting) {
        this.timeSetting = timeSetting;
    }
    
    public Integer getWeekSpan() {
        return weekSpan;
    }
    
    public void setWeekSpan(Integer weekSpan) {
        this.weekSpan = weekSpan;
    }
    
}
