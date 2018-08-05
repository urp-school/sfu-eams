//$Id: TeachResourceService.java,v 1.6 2006/12/26 00:57:53 duanth Exp $
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
 * chaostone             2005-11-13         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.resource;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.dao.course.arrange.resource.TeachResourceDAO;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.util.DataRealmLimit;

/**
 * 教学资源管理服务类<br>
 * 教学资源涉及到的范畴为: 1)班级,2)教师,3)教室,4)学生<br>
 * 教学资源服务主要用来:<br>
 * 1)查询各种教学资源在指定时间<code>TimeUnit<code>内是否被使用.<br>
 *    此时的TimeUnit的各种参数都要设置齐全.提供这种服务的方法有<br>
 *    1)以getFreeRoom开头的方法，可以获得制定条件的空闲教室<br>
 *    2)以getFreeTeacher开头的方法，可以获得制定条件的空闲教师<br>
 *    3)以isXXXXOcuupy为命名方式的方法，通过返回的布尔值，提供在指定时间内的是否被占用<br>
 *      true为占用，false为空闲<br>
 *  2)查询各种教学资源在指定时间上的教学活动（排课和排考以及任何其他的借用、占用）<br>
 *    此时的code>TimeUnit<code>的参数除了教学占用周(validWeeks)必备外，其他参数可选.<br>
 *    提供的方法以getXXXXActivities(...)方式命名<br>

 * @see com.shufe.model.std.Student
 * @see com.shufe.model.system.baseinfo.Classrom
 * @see com.shufe.model.system.baseinfo.Teacher
 * @see com.shufe.model.system.baseinfo.AdminClass
 * @see com.ekingstar.eams.system.time.TimeUnit
 * @author chaostone 2005-11-13
 */
public interface TeachResourceService {
    
    /**
     * 学生在该时间是否被占用
     * 
     * @param time
     * @param std
     * @return
     */
    public boolean isStdOccupied(TimeUnit time, Long stdId);
    
    /**
     * 学生在该时间是否被占用
     * 
     * @param time
     * @param std
     * @return
     */
    public boolean isStdsOccupied(TimeUnit time, Collection stdIds);
    
    /**
     * 学生在该时间是否被占用,除指定的任务外
     * 
     * @param time
     * @param std
     * @return
     */
    public boolean isStdsOccupied(TimeUnit time, Collection stdIds, TeachTask expect);
    
    /**
     * 查询指定id的教室在该时间点上是否被占用
     * 
     * @param roomId
     * @param time
     * @return
     */
    public boolean isRoomOccupied(TimeUnit time, Serializable roomId);
    
    public boolean isRoomOccupied(TimeUnit time, Serializable roomId, TeachTask exceptTask);
    
    /**
     * 查询指定id的教师在给定的时间上是否被占用
     * 
     * @param time
     * @param teacherId
     * @return
     */
    public boolean isTeacherOccupied(TimeUnit time, Long teacherId);
    
    /**
     * 查询指定id的行政班级在给定的时间上是否被占用
     * 
     * @param time
     * @param adminClassId
     * @return
     */
    public boolean isAdminClassOccupied(TimeUnit time, Long adminClassId);
    
    /**
     * 查询一组行政班级在给定的时间上是否被占用
     * 
     * @param time
     * @param adminClasses
     *            班级对象集合
     * @return
     */
    public boolean isAdminClassesOccupied(TimeUnit time, Collection adminClasses);
    
    /**
     * 在已有的教室中查找空闲的教室
     * 
     * @param roomIds
     *            教室的ids
     * @param time
     *            时间
     * @param room
     *            教室要求
     * @param activityClass
     *            指定的教学活动类型
     * @return
     */
    public Classroom getFreeRoomIn(Collection roomIds, TimeUnit[] times, Classroom room,
            Class activityClass);
    
    /**
     * 在已有的教室中查找空闲的教室
     * 
     * @param roomIds
     *            教室的ids
     * @param time
     *            时间
     * @param room
     *            教室要求
     * @param activityClass
     *            指定的教学活动类型
     * @return
     */
    public Collection getFreeRoomsIn(Collection roomIds, TimeUnit[] times, Classroom room,
            Class activityClass);
    
    /**
     * 查询指定的教学活动类型中特定部门的符合条件的空闲教室
     * 
     * @param departIds
     *            部门的id数组
     * @param times
     *            时间序列
     * @param room
     *            教室要求
     * @param activityClass
     *            指定的教学活动类型
     * @return
     */
    public Collection getFreeRoomsOf(Long[] departIds, TimeUnit[] times, Classroom room,
            Class activityClass);
    
    /**
     * 
     * @param departIds
     * @param times
     * @param room
     * @param activityClass
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getFreeRoomsOf(Long[] departIds, TimeUnit[] times, Classroom room,
            Class activityClass, int pageNo, int pageSize);
    
    /**
     * 返回指定部门管理的在times上空闲的教师
     * 
     * @param departIds
     * @param times
     * @return
     */
    public Collection getFreeTeachersOf(Long[] departIds, TimeUnit[] times, Teacher teacher,
            Class activityClass, PageLimit limit);
    
    /**
     * 返回指定部门管理的在times上空闲的教师
     * 
     * @param departIds
     * @param times
     * @return
     */
    public Collection getFreeTeachersIn(Collection teacherIds, TimeUnit[] times, Teacher teacher,
            Class activityClass);
    
    /**
     * 查询教师的教学活动中的排课活动信息
     * 
     * @param teacherId
     * @param time
     * @return
     */
    public List getTeacherActivities(Serializable teacherId, TimeUnit time, Class activityClass,
            TeachCalendar calendar);
    
    /**
     * 查询班级的教学活动中的排课活动信息
     * 
     * @param adminClassId
     * @param time
     * @return
     */
    public List getAdminClassActivities(Serializable adminClassId, TimeUnit time,
            Class activityClass);
    
    /**
     * <p>
     * 查询所有给定时间段内的教学活动(排课/排考).<br>
     * 时间段中的weekId/startUnit/units/calendar可以为null<br>
     * 前置条件：roomId不为空.<br>
     * time中的validWeeksNum必须设置.<br>
     * 
     * @param roomId
     * @param time
     * @return
     */
    public List getRoomActivities(Serializable roomId, TimeUnit time, Class activityClass,
            TeachCalendar calendar);
    
    /**
     * <p>
     * 查询所有给定时间段内的排课教学活动.<br>
     * 时间段中的weekId/startUnit/units可以为null<br>
     * 前置条件：std.id不为空.<br>
     * time中的validWeeksNum必须设置.<br>
     * 
     * @param room
     * @param time
     * @return
     */
    public List getStdActivities(Long stdId, TimeUnit time, Class activityClass,
            TeachCalendar calendar);
    
    public List getRoomOccupyInfos(Long roomId, Long validWeeksNum, Integer year);
    
    public List getTeacherOccupyInfos(Long teacherId, Long validWeeksNum, Integer year);
    
    public List getAdminClassOccupyInfos(Long adminClassId, Long validWeeksNum, Integer year);
    
    /**
     * 返回指定id的教室
     * 
     * @param roomIds
     *            allocate scope
     * @return
     */
    public List getClassrooms(Collection roomIds);
    
    /**
     * 返回指定id数组的教师列表
     * 
     * @param teacherIds
     * @return
     */
    public List getTeachers(Collection teacherIds);
    
    /**
     * 返回指定id串的教室，按照教学楼和教室代码排序.
     * 
     * @param roomIdSeq
     * @return
     */
    public List getClassrooms(String roomIdSeq);
    
    public List getClassrooms(Long[] roomIds);
    
    /**
     * 统计排考教室利用率
     * 
     * @param calendar
     * @param examType
     * @param limit
     * @param ratio
     * @return
     */
    public Collection getRoomUtilizationsOfExam(TeachCalendar calendar, ExamType examType,
            DataRealmLimit limit, Float ratio);
    
    /**
     * 统计课程教室利用率
     * 
     * @param calendar
     * @param examType
     * @param limit
     * @param ratio
     * @return
     */
    public Collection getRoomUtilizationOfCourse(TeachCalendar calendar, DataRealmLimit limit,
            List orders, Float ratio);
    
    /**
     * 设置教室管理的存取对象
     * 
     * @param dao
     */
    public void setTeachResourceDAO(TeachResourceDAO dao);
}
