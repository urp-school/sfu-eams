//$Id: TeachResourceDAO.java,v 1.4 2006/12/26 00:57:51 duanth Exp $
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

package com.shufe.dao.course.arrange.resource;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.dao.BasicDAO;
import com.shufe.model.course.arrange.resource.OccupyUnit;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.util.DataRealmLimit;

/**
 * 教学资源数据服务接口<br>
 * 接口分为三个部分：<br>
 * 1、占用情况查询 形式为isXXXXOccupied(time,id[s]),可以查询学生、班级、教师和教室的是否占用.<br>
 * 2、查询空闲资源 形式为getFreeXXXXOf和getFreeXXXXIn.前者查询特定部分的资源，后者查询制定标示符的资源<br>
 * 3、查询教学活动 形式为getXXXXActivities(id,time,activityClass).<br>
 * 4、查询占用情况 形式为getXXXXOccupyInfo(id,time)返回一个OccupyUnit的序列<br>
 * 5、一些查询教学资源的辅助类 getClassrooms,getTeachers<br>
 * 
 * @author chaostone 2005-11-13
 */
public interface TeachResourceDAO extends BasicDAO {
    
    /**
     * 查询指定id的教室在该时间点上是否被占用
     * 
     * @param roomId
     * @param time
     * @return
     */
    public boolean isRoomOccupied(TimeUnit time, Serializable roomId);
    
    /**
     * 查询指定id的教师在给定的时间上是否被占用
     * 
     * @param time
     * @param teacherId
     * @return
     */
    public boolean isTeacherOccupied(TimeUnit time, Long teacherId);
    
    /**
     * 查询教师在给定的时间上是否被占用
     * 
     * @param time
     * @param teachers
     * @return
     */
    public boolean isTeachersOccupied(TimeUnit time, Collection teachers);
    
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
    public boolean isStdsOccupied(TimeUnit time, Collection stdIds, TeachTask except);
    
    /**
     * 学生冲突人数
     * 
     * @param time
     * @param stdIds
     * @return
     */
    public int occupiedStdCount(TimeUnit time, Collection stdIds, Class activityClass);
    
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
    
    public List getFreeRoomsIn(Collection roomIds, TimeUnit time);
    
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
     * 返回分页的空闲教室
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
     * 
     * @param teachers
     * @param times
     * @param teacher
     * @param activityClass
     * @return
     */
    public Collection getFreeTeacherIdsIn(Collection teacherIds, TimeUnit[] times, Teacher teacher,
            Class activityClass);
    
    /**
     * 
     * @param adminClassIds
     * @param time
     * @return
     */
    public Collection getFreeClassIdsIn(Collection adminClassIds, TimeUnit[] times);
    
    /**
     * 查询班级参与的特定种类的教学活动的安排信息
     * 
     * @param activityClass
     * @param adminClassId
     * @param time
     * @return
     */
    public List getAdminClassActivities(Serializable adminClassId, TimeUnit time,
            Class activityClass);
    
    /**
     * 查询教师参与的特定种类的教学活动的安排信息
     * 
     * @param activityClass
     * @param teacherId
     * @param time
     * @return
     */
    public List getTeacherActivities(Serializable teacherId, TimeUnit time, Class activityClass,
            TeachCalendar calendar);
    
    /**
     * <p>
     * 查询所有给定时间段内的教学活动.<br>
     * 时间段中的weekId/startUnit/units/calendar可以为null<br>
     * 前置条件：roomId不为空.<br>
     * time中的validWeeksNum必须设置.<br>
     * </p>
     * 
     * @param roomId
     * @param time
     * @return
     */
    public List getRoomActivities(Serializable roomId, TimeUnit time, Class activityClass,
            TeachCalendar calendar);
    
    /**
     * <p>
     * 查询所有给定时间段内的排课的教学活动.<br>
     * 时间段中的weekId/startUnit/units/calendar可以为null<br>
     * 前置条件：stdNo不为空.<br>
     * time中的validWeeksNum必须设置.<br>
     * 
     * @param roomId
     * @param time
     * @return
     */
    public List getStdActivities(Long stdId, TimeUnit time, Class activityClass,
            TeachCalendar calendar);
    
    /*-----------------占用信息查询-------------------------*/
    /**
     * 教室占用查询查询
     * 
     * @param roomId
     * @param time
     * @return
     * @see OccupyUnit
     */
    
    public List getRoomOccupyInfos(Serializable roomId, Long validWeeksNum, Integer year);
    
    public List getTeacherOccupyInfos(Serializable teacherId, Long validWeeksNum, Integer year);
    
    public List getAdminClassOccupyInfos(Serializable adminClassId, Long validWeeksNum, Integer year);
    
    /**
     * 返回指定id的教室
     * 
     * @param roomIds
     * @return
     */
    public List getClassrooms(Long[] roomIds);
    
    /**
     * 返回指定id数组的教师列表
     * 
     * @param teacherIds
     * @return
     */
    public List getTeachers(Collection teacherIds);
    
    /**
     * 排考教室占用查询
     * 
     * @param calendar
     * @param examType
     * @param limit
     * @param freeRatio
     * @return
     */
    public Collection getExamRoomUtilizations(TeachCalendar calendar, ExamType examType,
            DataRealmLimit limit, Float freeRatio);
    
    /**
     * 检查教学活动是否冲突
     * 
     * @param examActivityId
     * @param teacherId
     *            TODO
     * @return
     */
    public boolean isTeacherCollision(Long examActivityId, Long teacherId);
    
    /**
     * 按星期统计教室借用率
     * 
     * @param year
     *            TODO
     * @return
     */
    public List weekAccount(Integer year);
    
    /**
     * 按地点统计教室借用率
     * 
     * @param year
     * @return
     */
    public List placeAccount(Integer year);
    
    /**
     * 按活动类型统计教室借用率
     * 
     * @param year
     * @return
     */
    public List activityAccount(Integer year);
    
}
