//$Id: ArrangeInfo.java,v 1.18 2006/12/29 14:04:03 duanth Exp $
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
 * chaostone             2005-10-31         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.arrange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.util.MessageResources;

import com.ekingstar.commons.lang.StringUtil;
import com.ekingstar.commons.model.Component;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.course.arrange.exam.ExamGroup;
import com.shufe.model.course.arrange.task.ArrangeSuggest;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.exam.ExamActivityDigestor;
import com.shufe.service.course.arrange.task.CourseActivityDigestor;

/**
 * 课程安排概要信息
 * 
 * @author chaostone 2005-11-15
 */
public class ArrangeInfo implements Component, Serializable {
    
    private static final long serialVersionUID = 3067092503219100019L;
    
    public final static int coursePublished = 1;
    
    public final static int examPublished = 2;
    
    /**
     * 开课院系，一个学院
     * 
     */
    private Department teachDepart = new Department();
    
    /**
     * 教学任务中的教师
     */
    private List teachers = new ArrayList();
    
    /**
     * 起始周
     */
    private Integer weekStart;
    
    /**
     * 周数
     */
    private Integer weeks;
    
    /**
     * 教学周占用周期
     */
    private Integer weekCycle;
    
    /**
     * 周课时
     */
    private Float weekUnits;
    
    /**
     * 每次课的小节数
     */
    private Integer courseUnits;
    
    /**
     * 总课时
     */
    private Integer overallUnits;
    
    /**
     * 是否排完
     */
    private Boolean isArrangeComplete;
    
    /**
     * 具体排考结果
     */
    private Set examActivities;
    
    /**
     * 排课建议
     */
    private ArrangeSuggest suggest = new ArrangeSuggest();
    
    /**
     * 排考课程组
     */
    private Set examGroups = new HashSet();
    
    /**
     * 具体排课结果
     * 
     */
    private Set activities = new HashSet();
    
    /** 校区 */
    private SchoolDistrict schoolDistrict;
    
    /** 教学任务变动日志 */
    private Set arrangeAlterations;
    
    public ArrangeInfo() {
        
    }
    
    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        ArrangeInfo info = new ArrangeInfo();
        info.getTeachers().addAll(getTeachers());
        info.setTeachDepart(getTeachDepart());
        info.setOverallUnits(getOverallUnits());
        info.setWeekCycle(getWeekCycle());
        info.setWeeks(getWeeks());
        info.setWeekUnits(getWeekUnits());
        info.setIsArrangeComplete(new Boolean(false));
        info.setCourseUnits(getCourseUnits());
        info.setWeekStart(getWeekStart());
        info.setSuggest((ArrangeSuggest) getSuggest().clone());
        info.setSchoolDistrict(getSchoolDistrict());
        return info;
    }
    
    public static ArrangeInfo getDefault() {
        ArrangeInfo arrangeInfo = new ArrangeInfo();
        arrangeInfo.courseUnits = new Integer(2);
        arrangeInfo.weekCycle = new Integer(TimeUnit.CONTINUELY);
        arrangeInfo.isArrangeComplete = new Boolean(false);
        arrangeInfo.weekStart = new Integer(1);
        arrangeInfo.getSuggest().setTime(null);
        return arrangeInfo;
    }
    
    /**
     * 查询课程所安排的教室
     * 
     * @return
     */
    public Set getCourseArrangedRooms() {
        if (null != getActivities() && !getActivities().isEmpty()) {
            Set rooms = new HashSet();
            for (Iterator iter = getActivities().iterator(); iter.hasNext();) {
                CourseActivity activity = (CourseActivity) iter.next();
                if (null != activity.getRoom()) {
                    rooms.add(activity.getRoom());
                }
            }
            return rooms;
        }
        return Collections.EMPTY_SET;
    }
    
    /**
     * 根据周课时和周数，计算总课时
     * 
     */
    public void calcOverallUnits() {
        if (null != getWeeks() && !getWeeks().equals(new Integer(0)) && null != getWeekUnits()
                && !getWeekUnits().equals(new Integer(0))) {
            setOverallUnits(new Integer(getWeeks().intValue() * getWeekUnits().intValue()));
        }
    }
    
    public void calcWeekUnits() {
        if (null != getWeeks() && !getWeeks().equals(new Integer(0)) && null != getOverallUnits()
                && !getOverallUnits().equals(new Integer(0))) {
            setWeekUnits(new Float(getOverallUnits().intValue() / getWeeks().intValue()));
        } else {
            setWeekUnits(new Float(0));
        }
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("weekStart",
                this.weekStart).append("courseUnits", this.courseUnits).append("weekCycle",
                this.weekCycle).append("weekUnits", this.weekUnits).append("overallUnits",
                this.overallUnits).append("weeks", this.weeks).append("sugguest", this.suggest)
                .toString();
    }
    
    /**
     * 缩略表示课程安排
     * 
     * @param calendar
     * @return
     */
    public String digest(TeachCalendar calendar) {
        return CourseActivityDigestor.digest(calendar, this, null, null,
                CourseActivityDigestor.defaultFormat);
    }
    
    public String digest(TeachCalendar calendar, MessageResources resourses, Locale locale,
            String format) {
        return CourseActivityDigestor.digest(calendar, this, resourses, locale, format);
    }
    
    /**
     * 缩略表示排考安排
     * 
     * @param calendar
     * @param resourses
     * @param locale
     * @param format
     * @return
     */
    public String digestExam(TeachCalendar calendar, MessageResources resourses, Locale locale,
            String examTypeId, String format) {
        return ExamActivityDigestor.digest(calendar, getExamActivities(new ExamType(Long
                .valueOf(examTypeId))), resourses, locale, format);
    }
    
    /**
     * 缩略表示排考安排
     * 
     * @param calendar
     * @param resourses
     * @param locale
     * @param format
     * @return
     */
    public String digestExam(TeachCalendar calendar, MessageResources resourses, Locale locale,
            Long examTypeId, String format) {
        return ExamActivityDigestor.digest(calendar, getExamActivities(new ExamType(examTypeId)),
                resourses, locale, format);
    }
    
    /**
     * 返回排考教室
     * 
     * @param examType
     * @return
     */
    public List getExamRooms(ExamType examType) {
        if (null == examActivities)
            return Collections.EMPTY_LIST;
        else {
            List classrooms = new ArrayList();
            for (Iterator iter = examActivities.iterator(); iter.hasNext();) {
                ExamActivity activity = (ExamActivity) iter.next();
                if (examType.equals(activity.getExamType())) {
                    if (null != activity.getRoom())
                        classrooms.add(activity.getRoom());
                }
            }
            return classrooms;
        }
    }
    
    /**
     * 返回主考老师
     * 
     * @param examType
     * @return
     */
    public List getExamTeachers(ExamType examType) {
        if (null == examActivities)
            return Collections.EMPTY_LIST;
        else {
            Set teachers = new HashSet();
            for (Iterator iter = examActivities.iterator(); iter.hasNext();) {
                ExamActivity activity = (ExamActivity) iter.next();
                if (examType.equals(activity.getExamType())) {
                    if (null != activity.getTeacher())
                        teachers.add(activity.getTeacher());
                }
            }
            return new ArrayList(teachers);
        }
    }
    
    /**
     * 返回排考教室
     * 
     * @param examType
     * @return
     */
    public ExamGroup getExamGroup(ExamType examType) {
        if (null == getExamGroups())
            return null;
        else {
            for (Iterator iter = getExamGroups().iterator(); iter.hasNext();) {
                ExamGroup group = (ExamGroup) iter.next();
                if (group.getExamType().equals(examType)) {
                    return group;
                }
            }
            return null;
        }
    }
    
    /**
     * 返回排考学生名单
     * 
     * @param examType
     * @return
     */
    public Collection getExamTakes(ExamType examType) {
        if (null == examActivities)
            return Collections.EMPTY_LIST;
        else {
            List examTakes = new ArrayList();
            for (Iterator iter = examActivities.iterator(); iter.hasNext();) {
                ExamActivity activity = (ExamActivity) iter.next();
                if (examType.equals(activity.getExamType())) {
                    examTakes.addAll(activity.getExamTakes());
                }
            }
            return examTakes;
        }
    }
    
    public boolean isSingleTeacher() {
        return (null != teachers && teachers.size() == 1);
    }
    
    public boolean hasTeachers() {
        return (null != teachers && !teachers.isEmpty());
    }
    
    /**
     * @return Returns the examActivities.
     */
    public Collection getExamActivities(ExamType examType) {
        if (null == examActivities || examActivities.isEmpty())
            return Collections.EMPTY_LIST;
        else {
            List rs = new ArrayList();
            for (Iterator iterator = examActivities.iterator(); iterator.hasNext();) {
                ExamActivity activity = (ExamActivity) iterator.next();
                if (activity.getExamType().equals(examType))
                    rs.add(activity);
            }
            return rs;
        }
    }
    
    /**
     * 页面上显示某一时间段的考试活动
     * 
     * @param examType
     * @param unit
     * @return
     */
    public Collection getExamActivities(ExamType examType, TimeUnit unit) {
        if (null == examActivities || examActivities.isEmpty())
            return Collections.EMPTY_LIST;
        else {
            List rs = new ArrayList();
            for (Iterator iterator = examActivities.iterator(); iterator.hasNext();) {
                ExamActivity activity = (ExamActivity) iterator.next();
                if (activity.getExamType().equals(examType) && activity.getTime().equals(unit)) {
                    rs.add(activity);
                }
            }
            return rs;
        }
    }
    
    /**
     * added 2006-11-7
     * 
     * @return
     */
    public int getEndWeek() {
        if (null != getWeeks() && null != getWeekStart()) {
            return getWeeks().intValue() + getWeekStart().intValue() - 1;
        } else {
            return -1;
        }
    }
    
    /**
     * 获得上课教师名称
     * 
     * @return
     */
    public String getTeacherNames() {
        if (null != getTeachers() && !getTeachers().isEmpty()) {
            StringBuffer buf = new StringBuffer(10);
            for (Iterator iter = getTeachers().iterator(); iter.hasNext();) {
                Teacher teacher = (Teacher) iter.next();
                buf.append(teacher.getName()).append(",");
            }
            buf.deleteCharAt(buf.length() - 1);
            return buf.toString();
        } else {
            return "";
        }
    }
    
    /**
     * 获得上课教师代码
     * 
     * @return
     */
    public String getTeacherCodes() {
        if (null != getTeachers() && !getTeachers().isEmpty()) {
            StringBuffer buf = new StringBuffer(10);
            for (Iterator iter = getTeachers().iterator(); iter.hasNext();) {
                Teacher teacher = (Teacher) iter.next();
                buf.append(teacher.getCode()).append(",");
            }
            buf.deleteCharAt(buf.length() - 1);
            return buf.toString();
        } else {
            return "";
        }
    }
    
    /**
     * 获得上课教师所在部门名称
     * 
     * @return
     */
    public String getTeacherDepartNames() {
        if (null != getTeachers() && !getTeachers().isEmpty()) {
            StringBuffer buf = new StringBuffer(10);
            for (Iterator iter = getTeachers().iterator(); iter.hasNext();) {
                Teacher teacher = (Teacher) iter.next();
                buf.append(teacher.getDepartment().getName()).append(",");
            }
            buf.deleteCharAt(buf.length() - 1);
            return buf.toString();
        } else {
            return "";
        }
    }
    
    /**
     * 获得上课教师的职称名称
     * 
     * @return
     */
    public String getTeacherTitleNames() {
        if (null != getTeachers() && !getTeachers().isEmpty()) {
            StringBuffer buf = new StringBuffer(10);
            for (Iterator iter = getTeachers().iterator(); iter.hasNext();) {
                Teacher teacher = (Teacher) iter.next();
                if (null != teacher.getTitle()) {
                    buf.append(teacher.getTitle().getName()).append(",");
                }
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            return buf.toString();
        } else {
            return "";
        }
    }
    
    
    public List getTeachers() {
        return teachers;
    }

    
    public void setTeachers(List teachers) {
        this.teachers = teachers;
    }

    public SchoolDistrict getSchoolDistrict() {
        return schoolDistrict;
    }
    
    public void setSchoolDistrict(SchoolDistrict schoolDistrict) {
        this.schoolDistrict = schoolDistrict;
    }
    
    
    public Long getActivityUnitsCount() {
        if (CollectionUtils.isEmpty(getActivities())) {
            return new Long(0);
        }
        long count = 0;
        for (Iterator it = getActivities().iterator(); it.hasNext();) {
            CourseActivity activity = (CourseActivity) it.next();
            count += activity.getTime().getUnitCount()
                    * StringUtil.countChar(activity.getTime().getValidWeeks(), '1');
        }
        return new Long(count);
    }
    
    public Set getActivities() {
        return activities;
    }
    
    public void setActivities(Set activities) {
        this.activities = activities;
    }
    
    public Boolean getIsArrangeComplete() {
        return isArrangeComplete;
    }
    
    public void setIsArrangeComplete(Boolean isArrangeComplete) {
        this.isArrangeComplete = isArrangeComplete;
    }
    
    public Integer getOverallUnits() {
        return overallUnits;
    }
    
    public void setOverallUnits(Integer overallUnits) {
        this.overallUnits = overallUnits;
    }
    
    public Integer getWeeks() {
        return weeks;
    }
    
    public void setWeeks(Integer weeks) {
        this.weeks = weeks;
    }
    
    public Float getWeekUnits() {
        return weekUnits;
    }
    
    public void setWeekUnits(Float weekUnits) {
        this.weekUnits = weekUnits;
    }
    
    public Integer getCourseUnits() {
        return courseUnits;
    }
    
    public void setCourseUnits(Integer courseUnits) {
        this.courseUnits = courseUnits;
    }
    
    public Integer getWeekCycle() {
        return weekCycle;
    }
    
    public void setWeekCycle(Integer weekCycle) {
        this.weekCycle = weekCycle;
    }
    
    public Department getTeachDepart() {
        return teachDepart;
    }
    
    public void setTeachDepart(Department teachDepart) {
        this.teachDepart = teachDepart;
    }
    
    public Integer getWeekStart() {
        return weekStart;
    }
    
    public void setWeekStart(Integer weekStart) {
        this.weekStart = weekStart;
    }
    
    public Set getExamActivities() {
        return examActivities;
    }
    
    public void setExamActivities(Set examActivities) {
        this.examActivities = examActivities;
    }
    
    public ArrangeSuggest getSuggest() {
        return suggest;
    }
    
    public void setSuggest(ArrangeSuggest suggest) {
        this.suggest = suggest;
    }
    
    public Set getExamGroups() {
        return examGroups;
    }
    
    public void setExamGroups(Set examGroups) {
        this.examGroups = examGroups;
    }
    
    public Set getArrangeAlterations() {
        return arrangeAlterations;
    }
    
    public void setArrangeAlterations(Set arrangeAlterations) {
        this.arrangeAlterations = arrangeAlterations;
    }
}
