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
 * chaostone             2006-11-12            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.exam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ekingstar.commons.lang.BitStringUtil;
import com.ekingstar.eams.system.i18n.StrutsMessageResources;
import com.ekingstar.eams.system.time.CourseUnit;
import com.ekingstar.eams.system.time.TimeUnitUtil;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.task.CourseActivityDigestor;

public class ExamActivityDigestor {
    
    static String delimeter = ",";
    
    static public final String singleTeacher = ":teacher1";
    
    static public final String multiTeacher = ":teacher+";
    
    static public final String moreThan1Teacher = ":teacher2";
    
    static public final String day = ":day";
    
    static public final String date = ":date";
    
    static public final String units = ":units";
    
    static public final String weeks = ":weeks";
    
    static public final String time = ":time";
    
    static public final String room = ":room";
    
    static public final String building = ":building";
    
    static public final String district = ":district";
    
    static public final String defaultFormat = ":date :time 第:weeks周 :day";
    
    static public final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    
    protected static final Logger logger = LoggerFactory.getLogger(CourseActivityDigestor.class);
    
    public static String digest(TeachCalendar calendar, Collection activities,
            MessageResources resourses, Locale locale) {
        return digest(calendar, activities, resourses, locale, defaultFormat);
    }
    
    /**
     * 输出教学活动安排
     * 
     * @param task
     * @param resourses
     * @param locale
     * @param format
     *            teacher[2|1|+] day units time weeks room building district
     * 
     * @return
     */
    public static String digest(TeachCalendar calendar, Collection activities,
            MessageResources resourses, Locale locale, String format) {
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
        boolean hasRoom = StringUtils.contains(format, room);
        boolean hasTeacher = StringUtils.contains(format, "teacher");
        
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            // 克隆一个课程安排
            ExamActivity activity = (ExamActivity) iter.next();
            boolean merged = false;
            for (Iterator iterator = mergedActivities.iterator(); iterator.hasNext();) {
                ExamActivity added = (ExamActivity) iterator.next();
                if (added.isSameActivityExcept(activity, hasTeacher, hasRoom)) {
                    added.getTime().setValidWeeks(
                            BitStringUtil.or(added.getTime().getValidWeeks(), activity.getTime()
                                    .getValidWeeks()));
                    merged = true;
                }
            }
            if (!merged) {
                mergedActivities.add(activity.clone());
            }
        }
        StringBuffer arrangeInfoBuf = new StringBuffer();
        // 合并后的教学活动
        for (Iterator iter = mergedActivities.iterator(); iter.hasNext();) {
            ExamActivity activity = (ExamActivity) iter.next();
            arrangeInfoBuf.append(format);
            int replaceStart = 0;
            replaceStart = arrangeInfoBuf.indexOf(day);
            if (-1 != replaceStart) {
                arrangeInfoBuf.replace(replaceStart, replaceStart + day.length(),
                        ((WeekInfo) WeekInfo.WEEKS
                                .get(activity.getTime().getWeekId().intValue() - 1)).getName());
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
            replaceStart = arrangeInfoBuf.indexOf(date);
            if (-1 != replaceStart) {
                arrangeInfoBuf.replace(replaceStart, replaceStart + date.length(), df
                        .format(activity.getDate()));
            }
            replaceStart = arrangeInfoBuf.indexOf(weeks);
            if (-1 != replaceStart) {
                // 以本年度的最后一周(而不是从教学日历周数计算而来)作为结束周进行缩略.
                // 是因为很多日历指定的周数,仅限于教学使用了.
                if (activity.getTime().needShiftWeeks(endAtSat, year)) {
                    activity.getTime().leftShiftWeeks();
                }
                arrangeInfoBuf.replace(replaceStart, replaceStart + weeks.length(), TimeUnitUtil
                        .digest(activity.getTime().getValidWeeks(), calendar.getWeekStart()
                                .intValue(), 1, TeachCalendar.OVERALLWEEKS,
                                new StrutsMessageResources(resourses), locale));
            }
            replaceStart = arrangeInfoBuf.indexOf(room);
            if (-1 != replaceStart) {
                arrangeInfoBuf.replace(replaceStart, replaceStart + room.length(),
                        (null != activity.getRoom()) ? activity.getRoom().getName() : "");
                
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
            arrangeInfoBuf.append(delimeter);
        }
        if (arrangeInfoBuf.lastIndexOf(delimeter) != -1)
            arrangeInfoBuf.delete(arrangeInfoBuf.lastIndexOf(delimeter), arrangeInfoBuf.length());
        return arrangeInfoBuf.toString();
    }
    
    public static String getDelimeter() {
        return delimeter;
    }
    
    public static void setDelimeter(String delimeter) {
        ExamActivityDigestor.delimeter = delimeter;
    }
}
