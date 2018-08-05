package com.shufe.service.course.arrange.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ekingstar.commons.lang.BitStringUtil;
import com.ekingstar.eams.system.i18n.StrutsMessageResources;
import com.ekingstar.eams.system.time.CourseUnit;
import com.ekingstar.eams.system.time.TimeUnitUtil;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.course.arrange.ArrangeInfo;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 输出一个教学任务教学活动的字符串表示
 * 
 * @author chaostone
 */
public class CourseActivityDigestor {
    
    static String delimeter = ",";
    
    static public final String singleTeacher = ":teacher1";
    
    static public final String multiTeacher = ":teacher+";
    
    static public final String moreThan1Teacher = ":teacher2";
    
    static public final String day = ":day";
    
    static public final String units = ":units";
    
    static public final String weeks = ":weeks";
    
    static public final String time = ":time";
    
    static public final String room = ":room";
    
    static public final String building = ":building";
    
    static public final String district = ":district";
    
    static public final String defaultFormat = ":teacher2 :day :units :weeks :room";
    
    protected static final Logger logger = LoggerFactory.getLogger(CourseActivityDigestor.class);
    
    /**
     * 默认输出课程安排信息
     * 
     * @param calendar
     * @param arrangeInfo
     * @param resourses
     * @param locale
     * @param format
     * @return
     */
    public static String digest(TeachCalendar calendar, ArrangeInfo arrangeInfo,
            MessageResources resourses, Locale locale, String format) {
        return digest(calendar, arrangeInfo, arrangeInfo.getActivities(), resourses, locale, format);
    }
    
    /**
     * 输出教学任务的课程安排信息
     * 
     * @param task
     * @param resourses
     * @param locale
     * @return
     */
    public static String digest(TeachTask task, MessageResources resourses, Locale locale) {
        return digest(task.getCalendar(), task.getArrangeInfo(), task.getArrangeInfo()
                .getActivities(), resourses, locale, defaultFormat);
    }
    
    /**
     * 输出教学活动安排
     * 
     * @param task
     * @param resourses
     * @param locale
     * @param format
     *            teacher[2|1|+] day units time weeks room building district
     * @return
     */
    public static String digest(TeachCalendar calendar, ArrangeInfo arrangeInfo,
            Collection activities, MessageResources resourses, Locale locale, String format) {
        if (null == activities || activities.isEmpty()) {
            return "";
        }
        if (StringUtils.isEmpty(format)) {
            format = defaultFormat;
        }
        
        int year = calendar.getStartYear();
        // 本年度以周六结尾
        boolean endAtSat = calendar.endAtSat();
        
        List mergedActivities = new ArrayList();
        Set teachers = new HashSet();
        boolean hasRoom = StringUtils.contains(format, room);
        boolean hasTeacher = StringUtils.contains(format, "teacher");
        // 合并相同时间点(不计年份)的教学活动
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            CourseActivity activity = (CourseActivity) iter.next();
            if (hasTeacher && null != activity.getTeacher()
                    && !teachers.contains(activity.getTeacher())) {
                teachers.add(activity.getTeacher());
            }
            boolean merged = false;
            for (Iterator iterator = mergedActivities.iterator(); iterator.hasNext();) {
                CourseActivity added = (CourseActivity) iterator.next();
                if (added.isSameActivityExcept(activity, hasTeacher, hasRoom)) {
                    if (activity.getTime().needShiftWeeks(endAtSat, year)) {
                        activity = (CourseActivity) activity.clone();
                        activity.getTime().leftShiftWeeks();
                    }
                    added.getTime().setValidWeeks(
                            BitStringUtil.or(added.getTime().getValidWeeks(), activity.getTime()
                                    .getValidWeeks()));
                    merged = true;
                }
            }
            if (!merged) {
                if (activity.getTime().needShiftWeeks(endAtSat, year)) {
                    activity = (CourseActivity) activity.clone();
                    activity.getTime().leftShiftWeeks();
                }
                mergedActivities.add(activity);
            }
        }
        
        // 是否添加老师
        boolean addTeacher = false;
        if (hasTeacher) {
            addTeacher = true;
            if (format.indexOf(singleTeacher) != -1 && teachers.size() != 1) {
                addTeacher = false;
            }
            if (format.indexOf(moreThan1Teacher) != -1 && teachers.size() < 2) {
                addTeacher = false;
            }
            if (format.indexOf(multiTeacher) != -1 && teachers.size() == 0) {
                addTeacher = false;
            }
        }
        StringBuffer arrangeInfoBuf = new StringBuffer();
        // 合并后的教学活动
        for (Iterator iter = mergedActivities.iterator(); iter.hasNext();) {
            CourseActivity activity = (CourseActivity) iter.next();
            arrangeInfoBuf.append(format);
            int replaceStart = arrangeInfoBuf.indexOf(":teacher");
            if (addTeacher) {
                arrangeInfoBuf.replace(replaceStart, replaceStart + 9, (null == activity
                        .getTeacher()) ? "" : activity.getTeacher().getName());
            } else if (-1 != replaceStart) {
                arrangeInfoBuf.replace(replaceStart, replaceStart + 9, "");
            }
            replaceStart = arrangeInfoBuf.indexOf(day);
            if (-1 != replaceStart) {
                if (null != locale && locale.getLanguage().equals("en")) {
                    arrangeInfoBuf.replace(replaceStart, replaceStart + day.length(),
                            ((WeekInfo) WeekInfo.WEEKS.get(activity.getTime().getWeekId()
                                    .intValue() - 1)).getEngName()
                                    + ".");
                } else {
                    arrangeInfoBuf.replace(replaceStart, replaceStart + day.length(),
                            ((WeekInfo) WeekInfo.WEEKS.get(activity.getTime().getWeekId()
                                    .intValue() - 1)).getName());
                }
            }
            replaceStart = arrangeInfoBuf.indexOf(units);
            if (-1 != replaceStart) {
                arrangeInfoBuf.replace(replaceStart, replaceStart + units.length(), activity
                        .getTime().getStartUnit()
                        + "-" + activity.getTime().getEndUnit());
            }
            replaceStart = arrangeInfoBuf.indexOf(time);
            if (-1 != replaceStart) {
                // 如果教学活动中有具体时间
                if (null != activity.getTime().getStartTime()) {
                    arrangeInfoBuf.replace(replaceStart, replaceStart + time.length(), CourseUnit
                            .getTimeStr(activity.getTime().getStartTime())
                            + "-" + CourseUnit.getTimeStr(activity.getTime().getEndTime()));
                }// 否则按照小节,查找对应日历中的时间
                else {
                    arrangeInfoBuf.replace(replaceStart, replaceStart + time.length(), CourseUnit
                            .getTimeStr(calendar.getTimeSetting().getCourseUnit(
                                    activity.getTime().getStartUnit().intValue()).getStartTime())
                            + "-"
                            + CourseUnit.getTimeStr(calendar.getTimeSetting().getCourseUnit(
                                    activity.getTime().getEndUnit().intValue()).getFinishTime()));
                }
            }
            replaceStart = arrangeInfoBuf.indexOf(weeks);
            if (-1 != replaceStart) {
                // 以本年度的最后一周(而不是从教学日历周数计算而来)作为结束周进行缩略.
                // 是因为很多日历指定的周数,仅限于教学使用了.
                arrangeInfoBuf.replace(replaceStart, replaceStart + weeks.length(), TimeUnitUtil
                        .digest(activity.getTime().getValidWeeks(), calendar.getWeekStart()
                                .intValue(), arrangeInfo.getWeekStart().intValue(),
                                TeachCalendar.OVERALLWEEKS, new StrutsMessageResources(resourses),
                                locale));
            }
            replaceStart = arrangeInfoBuf.indexOf(room);
            if (-1 != replaceStart) {
                arrangeInfoBuf
                        .replace(replaceStart, replaceStart + room.length(), ((null != activity
                                .getRoom() && null != activity.getRoom().getName()) ? activity
                                .getRoom().getName() : ""));
                
                replaceStart = arrangeInfoBuf.indexOf(building);
                if (-1 != replaceStart) {
                    if (null != activity.getRoom() && null != activity.getRoom().getBuilding()) {
                        arrangeInfoBuf.replace(replaceStart, replaceStart + building.length(),
                                activity.getRoom().getBuilding().getName());
                    } else {
                        arrangeInfoBuf.replace(replaceStart, replaceStart + building.length(), "");
                    }
                    
                }
                replaceStart = arrangeInfoBuf.indexOf(district);
                if (-1 != replaceStart) {
                    if (null != activity.getRoom() && null != activity.getRoom().getBuilding()
                            && null != activity.getRoom().getBuilding().getSchoolDistrict()) {
                        arrangeInfoBuf.replace(replaceStart, replaceStart + district.length(),
                                activity.getRoom().getBuilding().getSchoolDistrict().getName());
                    } else {
                        arrangeInfoBuf.replace(replaceStart, replaceStart + district.length(), "");
                    }
                }
            }
            
            arrangeInfoBuf.append(" ").append(delimeter);
        }
        if (arrangeInfoBuf.lastIndexOf(delimeter) != -1)
            arrangeInfoBuf.delete(arrangeInfoBuf.lastIndexOf(delimeter), arrangeInfoBuf.length());
        return arrangeInfoBuf.toString();
    }
    
    public static String getDelimeter() {
        return delimeter;
    }
    
    public static void setDelimeter(String delimeter) {
        CourseActivityDigestor.delimeter = delimeter;
    }
    
}
