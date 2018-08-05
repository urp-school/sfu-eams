//$Id: TeachTask.java,v 1.18 2007/01/15 01:03:43 duanth Exp $
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
 * @author hc
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * hc                   2005-9-23           Created
 * chaostone            2005-10-16          added and moved
 *  
 ********************************************************************************/
package com.shufe.model.course.task;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.ekingstar.commons.lang.BitStringUtil;
import com.ekingstar.commons.lang.StringUtil;
import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.ExamMode;
import com.ekingstar.eams.system.time.CourseUnit;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.course.arrange.Activity;
import com.shufe.model.course.arrange.ArrangeInfo;
import com.shufe.model.course.arrange.Arrangement;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.arrange.task.TaskGroup;
import com.shufe.model.course.grade.GradeState;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.quality.evaluate.Questionnaire;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 教学任务实体类<br>
 * 代表着从对上课对象和开课院系的完整的教学实际任务信息.<br>
 * 1、教什么（课程名称、类别）、什么时候教（教学日历）<br>
 * 2、教学班信息——教谁（学生人数、上课对象（院系、学生类别）、选课对象）<br>
 * 3、安排情况（是否排完、具体安排,周数、周课时、总课时、授课学院、零个或多个教师）<br>
 * 4、选课情况（是否预选、选课上下限、实选人数、选课规则）<br>
 * 5、任务要求（教室要求、课程要求（教材、参考书、案例）、是否挂牌、是否双语）<br>
 * 6、依据的培养计划（零个或一个）<br>
 * 7、怎么考核（考试方式）<br>
 * 8、创建时间、修改时间、备注<br>
 * 
 * @author hc,chaostone 2005-10-16
 */
public class TeachTask extends LongIdObject implements Arrangement,
        com.ekingstar.eams.course.grade.course.GradeTask {
    
    private static final long serialVersionUID = 1071972497531228225L;
    
    /**
     * 课程序号
     */
    private String seqNo;
    
    /**
     * 课程
     */
    private Course course = new Course();
    
    /**
     * 课程类别
     */
    private CourseType courseType = new CourseType();
    
    /**
     * 教学班
     */
    private TeachClass teachClass = new TeachClass();
    
    /**
     * 教学日历
     */
    private TeachCalendar calendar = new TeachCalendar();
    
    /**
     * 安排情况
     */
    private ArrangeInfo arrangeInfo = new ArrangeInfo();
    
    /**
     * 选课情况
     */
    private TaskElectInfo electInfo = new TaskElectInfo();
    
    /**
     * 任务要求
     */
    private TaskRequirement requirement = new TaskRequirement();
    
    /**
     * 考试方式
     */
    private ExamMode examMode = new ExamMode();
    
    /**
     * 评教问卷
     */
    private Questionnaire questionnaire = new Questionnaire();
    
    /**
     * 是否确认
     */
    private Boolean isConfirm;
    
    /**
     * 教学任务对应的成绩状态
     */
    private GradeState gradeState;
    
    /**
     * 创建时间
     */
    private Date createAt;
    
    /**
     * 最后修改时间
     */
    private Date modifyAt;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 产生于哪个培养计划
     */
    private TeachPlan fromPlan = new TeachPlan();
    
    /**
     * 所在排课组
     */
    private TaskGroup taskGroup = new TaskGroup();
    
    /**
     * 默认构造函数
     * 
     */
    public TeachTask() {
        super();
    }
    
    public TeachTask(Long teachTaskId) {
        this.id = teachTaskId;
    }
    
    public static TeachTask getDefault() {
        TeachTask task = new TeachTask();
        task.isConfirm = new Boolean(false);
        task.teachClass = TeachClass.getDefault();
        task.arrangeInfo = ArrangeInfo.getDefault();
        task.electInfo = TaskElectInfo.getDefault();
        task.requirement = TaskRequirement.getDefault();
        task.questionnaire = null;
        task.taskGroup = null;
        task.getExamMode().setId(ExamMode.NORMAL);
        task.gradeState = new GradeState(task);
        return task;
    }
    
    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        TeachTask task = new TeachTask();
        task.setArrangeInfo((ArrangeInfo) arrangeInfo.clone());
        task.setTeachClass((TeachClass) teachClass.clone());
        task.setRequirement((TaskRequirement) requirement.clone());
        task.setElectInfo((TaskElectInfo) electInfo.clone());
        task.setCourse(getCourse());
        task.setCourseType(getCourseType());
        task.setFromPlan(getFromPlan());
        task.setExamMode(getExamMode());
        task.setGradeState(new GradeState(task));
        task.setCalendar(getCalendar());
        task.setIsConfirm(getIsConfirm());
        task.setQuestionnaire(getQuestionnaire());
        task.setTaskGroup(getTaskGroup());
        task.setRemark(getRemark());
        return task;
    }
    
    /**
     * @return Returns the calendar.
     */
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    /**
     * @param calendar
     *            The calendar to set.
     */
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    /**
     * @return Returns the course.
     */
    public Course getCourse() {
        return course;
    }
    
    /**
     * @param course
     *            The course to set.
     */
    public void setCourse(Course course) {
        this.course = course;
    }
    
    /**
     * @return Returns the seqNo.
     */
    public String getSeqNo() {
        return seqNo;
    }
    
    /**
     * @param seqNo
     *            The seqNo to set.
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
    
    /**
     * @return Returns the courseType.
     */
    public CourseType getCourseType() {
        return courseType;
    }
    
    /**
     * @param courseType
     *            The courseType to set.
     */
    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
    
    /**
     * @return Returns the examMode.
     */
    public ExamMode getExamMode() {
        return examMode;
    }
    
    /**
     * @param examMode
     *            The examMode to set.
     */
    public void setExamMode(ExamMode examMode) {
        this.examMode = examMode;
    }
    
    /**
     * @return Returns the isConfirm.
     */
    public Boolean getIsConfirm() {
        return isConfirm;
    }
    
    /**
     * @param isConfirm
     *            The isConfirm to set.
     */
    public void setIsConfirm(Boolean isConfirm) {
        this.isConfirm = isConfirm;
    }
    
    /**
     * @return Returns the questionnaire.
     */
    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }
    
    /**
     * @param questionnaire
     *            The questionnaire to set.
     */
    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }
    
    /**
     * @return Returns the teachClass.
     */
    public TeachClass getTeachClass() {
        return teachClass;
    }
    
    /**
     * @param teachClass
     *            The teachClass to set.
     */
    public void setTeachClass(TeachClass teachClass) {
        if (null != teachClass)
            teachClass.setTask(this);
        this.teachClass = teachClass;
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
     * @return Returns the createAt.
     */
    public Date getCreateAt() {
        return createAt;
    }
    
    /**
     * @param createAt
     *            The createAt to set.
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    
    /**
     * @return Returns the requirement.
     */
    public TaskRequirement getRequirement() {
        return requirement;
    }
    
    /**
     * @param requirement
     *            The requirement to set.
     */
    public void setRequirement(TaskRequirement requirement) {
        this.requirement = requirement;
    }
    
    /**
     * @return Returns the electInfo.
     */
    public TaskElectInfo getElectInfo() {
        return electInfo;
    }
    
    /**
     * @param electInfo
     *            The electInfo to set.
     */
    public void setElectInfo(TaskElectInfo electInfo) {
        this.electInfo = electInfo;
    }
    
    /**
     * @return Returns the modifyAt.
     */
    public Date getModifyAt() {
        return modifyAt;
    }
    
    /**
     * @param modifyAt
     *            The modifyAt to set.
     */
    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }
    
    /**
     * @return Returns the gradeState.
     */
    public GradeState getGradeState() {
        return gradeState;
    }
    
    public com.ekingstar.eams.course.grade.course.GradeState getCourseGradeState() {
        return gradeState;
    }
    
    /**
     * @param gradeState
     *            The gradeState to set.
     */
    public void setGradeState(GradeState examState) {
        this.gradeState = examState;
    }
    
    /**
     * @return Returns the arrangeInfo.
     */
    public ArrangeInfo getArrangeInfo() {
        return arrangeInfo;
    }
    
    /**
     * @param arrangeInfo
     *            The arrangeInfo to set.
     */
    public void setArrangeInfo(ArrangeInfo arrangeInfo) {
        this.arrangeInfo = arrangeInfo;
    }
    
    /**
     * @return Returns the fromScheme.
     */
    public TeachPlan getFromPlan() {
        return fromPlan;
    }
    
    /**
     * @param fromScheme
     *            The fromScheme to set.
     */
    public void setFromPlan(TeachPlan fromPlan) {
        this.fromPlan = fromPlan;
    }
    
    /**
     * @return Returns the taskGroup.
     */
    public TaskGroup getTaskGroup() {
        return taskGroup;
    }
    
    /**
     * @param taskGroup
     *            The taskGroup to set.
     */
    public void setTaskGroup(TaskGroup taskGroup) {
        this.taskGroup = taskGroup;
    }
    
    public boolean selfCheck() {
        return !((null == course) || (null == courseType) || (null == teachClass.getStdType())
                || (null == arrangeInfo.getTeachDepart()) || (null == calendar));
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return " [id:" + this.getId() + "]  " + this.getTeachClass().getName();
    }
    
    /**
     * 获得教学任务某一个教师的上课总课时和总周数<br>
     * 如果是一个教师则直接返回安排信息，<br>
     * 否则则实际计算教师的总课时，总课时按照教师上课的总的小节数计算得来的<br>
     * 当出现多个教师,而又没有排课时,返回0个总课时.
     * 
     * @param teacherId
     * @return
     */
    public ArrangeInfo getArrangeInfoOfTeacher(Teacher teacher) {
        if (getArrangeInfo().isSingleTeacher() && getArrangeInfo().getTeachers().contains(teacher))
            return getArrangeInfo();
        else {
            Set activities = getArrangeInfo().getActivities();
            if (activities.isEmpty()) {
                ArrangeInfo info = new ArrangeInfo();
                info.setWeeks(getArrangeInfo().getWeeks());
                info.setWeekUnits(getArrangeInfo().getWeekUnits());
                info.setOverallUnits(new Integer(0));
                return info;
            }
            int overallUnits = 0;
            String validWeeks = StringUtils.repeat("0", TeachCalendar.OVERALLWEEKS);
            int weekUnit = 2;
            for (Iterator iter = activities.iterator(); iter.hasNext();) {
                CourseActivity activity = (CourseActivity) iter.next();
                if (teacher.equals(activity.getTeacher())) {
                    validWeeks = BitStringUtil.or(validWeeks, activity.getTime().getValidWeeks());
                    weekUnit = activity.getTime().getUnitCount();
                    /*----------每次活动安排的共课时---------------------*/
                    overallUnits += activity.getTime().getUnitCount()
                            * StringUtil.countChar(activity.getTime().getValidWeeks(), '1');
                }
            }
            ArrangeInfo rsInfo = new ArrangeInfo();
            rsInfo.setWeeks(new Integer(StringUtil.countChar(validWeeks, '1')));
            rsInfo.setWeekUnits(Float.valueOf(weekUnit));
            rsInfo.setOverallUnits(new Integer(overallUnits));
            return rsInfo;
        }
    }
    
    /**
     * Add activities to task,also add task to activity inversely.
     * 
     * @param actives
     */
    public void addActivities(Collection actives) {
        if (null == getArrangeInfo().getActivities())
            getArrangeInfo().setActivities(new HashSet());
        Set activities = getArrangeInfo().getActivities();
        for (Iterator iter = actives.iterator(); iter.hasNext();) {
            CourseActivity activity = (CourseActivity) iter.next();
            activity.setTask(this);
            activities.add(activity);
        }
    }
    
    /**
     * 得到第一次上课时间
     * 
     * @return
     */
    public Date getFirstCourseTime() {
        if (null != calendar && calendar.isPO()) {
            Date date = null;
            if (null != getArrangeInfo().getActivities()) {
                for (Iterator iter = getArrangeInfo().getActivities().iterator(); iter.hasNext();) {
                    CourseActivity courseActivity = (CourseActivity) iter.next();
                    Date myDate = courseActivity.getFirstActivityTime(calendar);
                    if (date == null || myDate.before(date)) {
                        date = myDate;
                    }
                }
            }
            return date;
        }
        return null;
    }
    
    /**
     * 得到最后一次的上课时间TimeUnit和地点
     * 
     * @return
     */
    public CourseActivity getLastCourseActivity() {
        if (null != calendar && calendar.isPO()) {
            CourseActivity activity = null;
            if (null != getArrangeInfo().getActivities()) {
                activity = new CourseActivity();
                for (Iterator iter = getArrangeInfo().getActivities().iterator(); iter.hasNext();) {
                    CourseActivity courseActivity = (CourseActivity) iter.next();
                    java.util.Date lastDay = courseActivity.getTime().getLastDay();
                    if (activity.getTime().getLastDay() == null) {
                        activity.getTime().setLastDay(lastDay);
                        copyUnitAndRoom(activity, courseActivity);
                    } else {
                        if (lastDay.after(activity.getTime().getLastDay())) {
                            activity.getTime().setLastDay(lastDay);
                            copyUnitAndRoom(activity, courseActivity);
                        } else if (DateUtils.isSameDay(lastDay, activity.getTime().getLastDay())) {
                            if (courseActivity.getTime().getStartUnit().intValue() > activity
                                    .getTime().getStartUnit().intValue()) {
                                copyUnitAndRoom(activity, courseActivity);
                            }
                        }
                    }
                }
                TimeUnit lastTime = activity.getTime();
                if (null != lastTime.getEndUnit()) {
                    // 返回固定上课的时间段，而不是合并后的（可能存在合并）
                    if (lastTime.getEndUnit().intValue()
                            - getArrangeInfo().getCourseUnits().intValue() + 1 > 0) {
                        lastTime.setStartUnit(new Integer(lastTime.getEndUnit().intValue()
                                - getArrangeInfo().getCourseUnits().intValue() + 1));
                    }
                    CourseUnit startUnit = calendar.getTimeSetting().getCourseUnit(
                            lastTime.getStartUnit().intValue());
                    CourseUnit endUnit = calendar.getTimeSetting().getCourseUnit(
                            lastTime.getEndUnit().intValue());
                    if (null != startUnit) {
                        lastTime.setStartTime(startUnit.getStartTime());
                    }
                    if (null != endUnit) {
                        lastTime.setEndTime(endUnit.getFinishTime());
                    }
                } else {
                    activity = null;
                }
            }
            return activity;
        }
        return null;
    }
    
    private void copyUnitAndRoom(CourseActivity activity, CourseActivity courseActivity) {
        activity.getTime().setStartUnit(courseActivity.getTime().getStartUnit());
        activity.getTime().setEndUnit(courseActivity.getTime().getEndUnit());
        activity.setRoom(courseActivity.getRoom());
    }
    
    public Long getRoomsOfCapacity() {
        long capacity = 0;
        for (Iterator it = this.arrangeInfo.getActivities().iterator(); it.hasNext();) {
            Activity activity = (Activity) it.next();
            capacity += activity.getRoom().getCapacity().longValue();
        }
        return new Long(capacity);
    }
    
    /**
     * 提供占用周串，得到该起始周<br>
     * 
     * 返回值-1表示没有占用周，否则为该串起始周
     * 
     * @param weekStringValues
     * @return
     * @throws Exception
     */
    public int getActivityFirstWeek(String weekStringValues, TeachCalendar calendar)
            throws Exception {
        if (StringUtils.isEmpty(weekStringValues)) {
            return -1;
        }
        for (int i = calendar.getWeekStart().intValue() - 1; i < weekStringValues.length(); i++) {
            if (weekStringValues.charAt(i) == '1') {
                return i + 1;
            }
        }
        return -1;
    }
    
    /**
     * 提供教学任务，得到该任务的排课后的起始周
     * 
     * @param task
     * @return
     * @throws Exception
     */
    public int getActivityStartWeek() throws Exception {
        int xStart = -1;
        for (Iterator it = this.getArrangeInfo().getActivities().iterator(); it.hasNext();) {
            CourseActivity activity = (CourseActivity) it.next();
            int temp = getActivityFirstWeek(activity.getTime().getValidWeeks(), this.getCalendar());
            if (xStart < 0 || xStart > temp) {
                xStart = temp;
            }
        }
        return xStart;
    }
    
    public int getActivityWeeks() throws Exception {
        if (CollectionUtils.isEmpty(this.getArrangeInfo().getActivities())) {
            return -1;
        }
        String result = "";
        for (Iterator it = this.getArrangeInfo().getActivities().iterator(); it.hasNext();) {
            CourseActivity activity = (CourseActivity) it.next();
            if (StringUtils.isEmpty(result)) {
                result = activity.getTime().getValidWeeks();
            } else {
                result = BitStringUtil.or(result, activity.getTime().getValidWeeks());
            }
        }
        return StringUtil.countChar(result, '1');
    }
    
    public float getActivityWeekUnits() throws Exception {
        float units = -1;
        if (CollectionUtils.isEmpty(this.getArrangeInfo().getActivities())) {
            return units;
        }
        units = 0;
        float tempEU = -1;
        float tempSU = -1;
        int week = -1;
        for (Iterator it = this.getArrangeInfo().getActivities().iterator(); it.hasNext();) {
            CourseActivity activity = (CourseActivity) it.next();
            if (tempEU < -1) {
                tempEU = activity.getTime().getEndUnit().floatValue();
                tempSU = activity.getTime().getStartUnit().floatValue();
                week = activity.getTime().getWeekId().intValue();
            } else if (tempEU == activity.getTime().getEndUnit().floatValue()
                    && tempSU == activity.getTime().getStartUnit().floatValue()
                    && week == activity.getTime().getWeekId().intValue()) {
                break;
            }
            units += activity.getTime().getEndUnit().floatValue()
                    - activity.getTime().getStartUnit().floatValue() + 1;
            tempEU = activity.getTime().getEndUnit().floatValue();
            tempSU = activity.getTime().getStartUnit().floatValue();
            week = activity.getTime().getWeekId().intValue();
        }
        return units;
    }
}