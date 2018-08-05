// $Id: CourseActivity.java,v 1.5 2006/11/24 13:14:32 duanth Exp $
/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved. This source code is the property of
 * KINGSTAR MEDIA SOLUTIONS LTD. It is intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source, or other redistribution of this source is
 * not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/***********************************************************************************************************************
 * @author chaostone MODIFICATION DESCRIPTION Name Date Description ============ ============ ============ chaostone
 *         2005-11-22 Created
 **********************************************************************************************************************/
package com.shufe.model.course.arrange.task;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.util.MessageResources;

import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.eams.system.time.CourseUnit;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.course.arrange.TaskActivity;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.task.CourseActivityDigestor;

/**
 * 教学活动
 * 
 * @author chaostone 2005-11-22
 */
public class CourseActivity extends TaskActivity implements Comparable, Serializable {
    
    private static final long serialVersionUID = 2498530728105897805L;
    
    protected Teacher teacher = new Teacher();// 授课教师
    
    // 默认构造函数
    public CourseActivity() {
    }
    
    /**
     * 在批量生成教学活动,例如排课时,其他的要素均已具备,无须再初始化.
     * 
     * @param initAll
     *            是否初始化成员
     */
    public CourseActivity(boolean initAll) {
        
        if (initAll) {
            task = new TeachTask();
            teacher = new Teacher();
            room = new Classroom();
            time = new TimeUnit();
        }
    }
    
    /**
     * 不计较年份,比较是否是相近的教学活动.<br>
     * 
     * @param other
     * @param teacher
     *            是否考虑教师
     * @param room
     *            是否考虑教室
     * @return
     */
    public boolean isSameActivityExcept(CourseActivity other, boolean teacher, boolean room) {
        if (teacher) {
            if (null == getTeacher() && null != other.getTeacher()) {
                return false;
            }
            if (null != getTeacher() && null == other.getTeacher()) {
                return false;
            }
            if (null != getTeacher() && null != other.getTeacher()
                    && !getTeacher().equals(other.getTeacher())) {
                return false;
            }
        }
        
        if (room) {
            if (null == getRoom() && null != other.getRoom() || null != getRoom()
                    && null == other.getRoom() || null != getRoom() && null != other.getRoom()
                    && !getRoom().equals(other.getRoom())) {
                return false;
            }
        }
        if (!getTime().getStartUnit().equals(other.getTime().getStartUnit())
                || !getTime().getEndUnit().equals(other.getTime().getEndUnit())
                || !getTime().getWeekId().equals(other.getTime().getWeekId())) {
            return false;
        }
        return true;
    }
    
    public CourseActivity(Teacher teacher, Classroom room, TimeUnit time) {
        setTeacher(teacher);
        setRoom(room);
        this.time = new TimeUnit();
        this.time.setStartUnit(time.getStartUnit());
        this.time.setEndUnit(time.getEndUnit());
        this.time.setWeekId(time.getWeekId());
    }
    
    /**
     * year teacher room validWeeks startUnit null will be put first,if another is not null
     * 
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(Object object) {
        CourseActivity activity = (CourseActivity) object;
        int rs = time.getYear().compareTo(activity.getTime().getYear());
        // compare teacher
        if (rs == 0) {
            if (!ValidEntityPredicate.INSTANCE.evaluate(getTeacher())
                    && ValidEntityPredicate.INSTANCE.evaluate(activity.getTeacher())) {
                rs = -1;
            } else if (!ValidEntityPredicate.INSTANCE.evaluate(getTeacher())
                    && !ValidEntityPredicate.INSTANCE.evaluate(activity.getTeacher())) {
                rs = 0;
            } else if (ValidEntityPredicate.INSTANCE.evaluate(getTeacher())
                    && !ValidEntityPredicate.INSTANCE.evaluate(activity.getTeacher())) {
                rs = 1;
            } else {
                rs = getTeacher().getId().compareTo(activity.getTeacher().getId());
            }
        }
        
        // compare room
        if (rs == 0) {
            if ((null == getRoom() || getRoom().getId() == null) && null != activity.getRoom()
                    && activity.getRoom().getId() != null) {
                rs = -1;
            } else if ((null == getRoom() || getRoom().getId() == null)
                    && (null == activity.getRoom() || activity.getRoom().getId() == null)) {
                rs = 0;
            } else if (null != getRoom() && getRoom().getId() != null
                    && (null == activity.getRoom() || activity.getRoom().getId() == null)) {
                rs = 1;
            } else {
                rs = getRoom().getId().compareTo(activity.getRoom().getId());
            }
        }
        // compare weeks
        if (rs == 0) {
            rs = getTime().getValidWeeksNum().compareTo(activity.getTime().getValidWeeksNum());
        }
        if (rs == 0) {
            rs = getTime().getWeekId().compareTo(activity.getTime().getWeekId());
        }
        if (rs == 0) {
            rs = getTime().getStartUnit().compareTo(activity.getTime().getStartUnit());
        }
        return rs;
    }
    
    public Object clone() {
        CourseActivity activity = new CourseActivity(false);
        activity.setRoom(room);
        activity.setCalendar(calendar);
        activity.setTask(task);
        activity.setTeacher(teacher);
        activity.setTime((TimeUnit) time.clone());
        return activity;
    }
    
    /**
     * 判断该教学活动的时间段能否与目标教学活动在[相邻时间段]上合并
     * 
     * @param other
     * @return
     */
    public boolean canMergerWith(CourseActivity activity) {
        if (null == getRoom() && null != activity.getRoom())
            return false;
        else if (null != getRoom() && null == activity.getRoom())
            return false;
        else if (null != getRoom() && null != activity.getRoom()
                && !getRoom().equals(activity.getRoom()))
            return false;
        if (null == getTeacher() && null != activity.getTeacher())
            return false;
        else if (null != getTeacher() && null == activity.getTeacher())
            return false;
        else if (null != getTeacher() && null != activity.getTeacher()
                && !getTeacher().equals(activity.getTeacher()))
            return false;
        return getTime().canMergerWith(activity.getTime());
    }
    
    /**
     * 相邻小节的时间点合并成大时间点
     * 
     * @param other
     */
    public void mergeWith(CourseActivity other) {
        this.getTime().mergeWith(other.getTime());
    }
    
    /**
     * 合并在年份和教学周占用上,可以合并的教学活动<br>
     * 合并标准是年份,教学周,教室,教师,星期等相同,且小节相邻.
     * 
     * @param tobeMerged
     * @return
     * @see canMergerWith
     */
    public static List mergeActivites(List tobeMerged) {
        List mergedActivityList = new ArrayList();
        if (CollectionUtils.isEmpty(tobeMerged)) {
            return mergedActivityList;
        }
        Collections.sort(tobeMerged);
        Iterator activityIter = tobeMerged.iterator();
        CourseActivity toMerged = (CourseActivity) activityIter.next();
        mergedActivityList.add(toMerged);
        while (activityIter.hasNext()) {
            CourseActivity activity = (CourseActivity) activityIter.next();
            if (toMerged.canMergerWith(activity)) {
                toMerged.mergeWith(activity);
            } else {
                toMerged = activity;
                mergedActivityList.add(toMerged);
            }
        }
        return mergedActivityList;
    }
    
    /**
     * 第一次活动时间
     * 
     * @param calendar
     * @return
     */
    public Date getFirstActivityTime(TeachCalendar calendar) {
        if (null != time) {
            java.util.Date date = time.getFirstDay();
            if (null == date)
                return null;
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            CourseUnit unit = calendar.getTimeSetting().getCourseUnit(
                    time.getStartUnit().intValue());
            if (null != unit) {
                gc.set(Calendar.HOUR_OF_DAY, unit.getStartTime().intValue() / 100);
                gc.set(Calendar.MINUTE, unit.getStartTime().intValue() % 100);
            }
            return new Date(gc.getTimeInMillis());
        } else
            return null;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("room", this.room)
                .append("time", this.time).append("teacher", this.teacher).append("id", this.id)
                .toString();
    }
    
    public String digest(MessageResources resourses, Locale locale, String format) {
        Collection activities = new ArrayList();
        activities.add(this);
        return CourseActivityDigestor.digest(this.calendar, this.task.getArrangeInfo(), activities,
                resourses, locale, format);
    }
    
    /**
     * @return Returns the teacher.
     */
    public Teacher getTeacher() {
        return teacher;
    }
    
    /**
     * @param teacher
     *            The teacher to set.
     */
    public void setTeacher(Teacher teacher) {
        
        this.teacher = teacher;
    }
    
}
