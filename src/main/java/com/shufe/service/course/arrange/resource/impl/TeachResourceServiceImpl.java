//$Id: TeachResourceServiceImpl.java,v 1.6 2006/12/26 00:57:51 duanth Exp $
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

package com.shufe.service.course.arrange.resource.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.commons.query.limit.SinglePage;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.dao.course.arrange.resource.TeachResourceDAO;
import com.shufe.model.course.arrange.Activity;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.arrange.resource.TeachResourceService;
import com.shufe.util.DataRealmLimit;

public class TeachResourceServiceImpl extends BasicService implements TeachResourceService {
    
    private TeachResourceDAO teachResourceDAO;
    
    public boolean isStdOccupied(TimeUnit time, Long stdId) {
        return teachResourceDAO.isStdOccupied(time, stdId);
    }
    
    public boolean isStdsOccupied(TimeUnit time, Collection stdIds) {
        if (stdIds.isEmpty())
            return false;
        else
            return teachResourceDAO.isStdsOccupied(time, stdIds);
    }
    
    public boolean isStdsOccupied(TimeUnit time, Collection stdIds, TeachTask expect) {
        if (stdIds.isEmpty())
            return false;
        else
            return teachResourceDAO.isStdsOccupied(time, stdIds, expect);
    }
    
    /**
     * @see com.shufe.service.course.arrange.resource.TeachResourceService#isAdminClassOccupied(com.ekingstar.eams.system.time.TimeUnit,
     *      java.util.Collection)
     */
    public boolean isAdminClassesOccupied(TimeUnit time, Collection adminClasses) {
        return (!adminClasses.isEmpty()) ? teachResourceDAO.isAdminClassesOccupied(time,
                adminClasses) : true;
    }
    
    /**
     * @see com.shufe.service.course.arrange.resource.TeachResourceService#isAdminClassOccupied(com.ekingstar.eams.system.time.TimeUnit,
     *      java.lang.String)
     */
    public boolean isAdminClassOccupied(TimeUnit time, Long adminClassId) {
        return (null != adminClassId) ? teachResourceDAO.isAdminClassOccupied(time, adminClassId)
                : true;
        
    }
    
    /**
     * @see com.shufe.service.course.arrange.resource.TeachResourceService#isRoomOccupied(com.ekingstar.eams.system.time.TimeUnit,
     *      java.io.Serializable)
     */
    public boolean isRoomOccupied(TimeUnit time, Serializable roomId) {
        return isRoomOccupied(time, roomId, null);
    }
    
    public boolean isRoomOccupied(TimeUnit time, Serializable roomId, TeachTask exceptTask) {
        EntityQuery query = new EntityQuery(Activity.class, "activity");
        query.add(new Condition("activity.room.id = :roomId", roomId));
        query.add(new Condition("BITAND (activity.time.validWeeksNum, :validWeeksNum) > 0", time
                .getValidWeeksNum()));
        query.add(new Condition("activity.time.year = :year", time.getYear()));
        query.add(new Condition("activity.time.weekId = :weekId", time.getWeekId()));
        query.add(new Condition("activity.time.startUnit <= :endUnit", time.getEndUnit()));
        query.add(new Condition(":startUnit <= activity.time.endUnit", time.getStartUnit()));
        query.add(new Condition("activity.room.isCheckActivity = true"));
        if (null != exceptTask && null != exceptTask.getArrangeInfo()
                && CollectionUtils.isNotEmpty(exceptTask.getArrangeInfo().getActivities())) {
            Set activities = exceptTask.getArrangeInfo().getActivities();
            Long[] activityIds = new Long[activities.size()];
            int k = 0;
            for (Iterator it = activities.iterator(); it.hasNext();) {
                Activity activity = (Activity) it.next();
                activityIds[k++] = activity.getId();
            }
            query.add(new Condition("activity.id not in (:activityIds)", activityIds));
        }
        return CollectionUtils.isNotEmpty(utilDao.search(query));
    }
    
    /**
     * @see com.shufe.service.course.arrange.resource.TeachResourceService#isTeacherOccupied(com.ekingstar.eams.system.time.TimeUnit,
     *      java.lang.String)
     */
    public boolean isTeacherOccupied(TimeUnit time, Long teacherId) {
        return (null != teacherId) ? teachResourceDAO.isTeacherOccupied(time, teacherId) : true;
    }
    
    public Classroom getFreeRoomIn(Collection roomIds, TimeUnit[] times, Classroom room,
            Class activityClass) {
        return teachResourceDAO.getFreeRoomIn(roomIds, times, room, activityClass);
    }
    
    public Collection getFreeRoomsIn(Collection roomIds, TimeUnit[] times, Classroom room,
            Class activityClass) {
        return teachResourceDAO.getFreeRoomsIn(roomIds, times, room, activityClass);
    }
    
    public Collection getFreeRoomsOf(Long[] departIds, TimeUnit[] times, Classroom room,
            Class activityClass) {
        return teachResourceDAO.getFreeRoomsOf(departIds, times, room, activityClass);
    }
    
    public Pagination getFreeRoomsOf(Long[] departIds, TimeUnit[] times, Classroom room,
            Class activityClass, int pageNo, int pageSize) {
        return teachResourceDAO.getFreeRoomsOf(departIds, times, room, activityClass, pageNo,
                pageSize);
    }
    
    public Collection getFreeTeachersOf(Long[] departIds, TimeUnit[] times, Teacher teacher,
            Class activityClass, PageLimit limit) {
        return teachResourceDAO.getFreeTeachersOf(departIds, times, teacher, activityClass, limit);
    }
    
    public Collection getFreeTeachersIn(Collection teacherIds, TimeUnit[] times, Teacher teacher,
            Class activityClass) {
        return teachResourceDAO.getFreeTeacherIdsIn(teacherIds, times, teacher, activityClass);
    }
    
    /**
     * @see com.shufe.service.course.arrange.resource.TeachResourceService#getAdminClassActivities(java.io.Serializable,
     *      com.ekingstar.eams.system.time.TimeUnit)
     */
    public List getAdminClassActivities(Serializable adminClassId, TimeUnit time,
            Class activityClass) {
        return (null != adminClassId && StringUtils.isNotEmpty(time.getValidWeeks())) ? teachResourceDAO
                .getAdminClassActivities(adminClassId, time, activityClass)
                : Collections.EMPTY_LIST;
    }
    
    /**
     * @see com.shufe.service.course.arrange.resource.TeachResourceService#getRoomActivities(java.io.Serializable,
     *      com.ekingstar.eams.system.time.TimeUnit)
     */
    public List getRoomActivities(Serializable roomId, TimeUnit time, Class activityClass,
            TeachCalendar calendar) {
        return (null != roomId && StringUtils.isNotEmpty(time.getValidWeeks())) ? teachResourceDAO
                .getRoomActivities(roomId, time, activityClass, calendar) : Collections.EMPTY_LIST;
    }
    
    /**
     * @see com.shufe.service.course.arrange.resource.TeachResourceService#getTeacherActivities(java.io.Serializable,
     *      com.ekingstar.eams.system.time.TimeUnit)
     */
    public List getTeacherActivities(Serializable teacherId, TimeUnit time, Class activityClass,
            TeachCalendar calendar) {
        return (null != teacherId && StringUtils.isNotEmpty(time.getValidWeeks())) ? teachResourceDAO
                .getTeacherActivities(teacherId, time, activityClass, calendar)
                : Collections.EMPTY_LIST;
    }
    
    public List getStdActivities(Long stdId, TimeUnit time, Class activityClass,
            TeachCalendar calendar) {
        return (null != stdId && StringUtils.isNotEmpty(time.getValidWeeks())) ? teachResourceDAO
                .getStdActivities(stdId, time, activityClass, calendar) : Collections.EMPTY_LIST;
    }
    
    public List getAdminClassOccupyInfos(Long adminClassId, Long validWeeksNum, Integer year) {
        return teachResourceDAO.getAdminClassOccupyInfos(adminClassId, validWeeksNum, year);
    }
    
    public List getRoomOccupyInfos(Long roomId, Long validWeeksNum, Integer year) {
        return teachResourceDAO.getRoomOccupyInfos(roomId, validWeeksNum, year);
    }
    
    public List getTeacherOccupyInfos(Long teacherId, Long validWeeksNum, Integer year) {
        return teachResourceDAO.getTeacherOccupyInfos(teacherId, validWeeksNum, year);
    }
    
    public Collection getRoomUtilizationsOfExam(TeachCalendar calendar, ExamType examType,
            DataRealmLimit limit, Float freeRatio) {
        Collection rs = teachResourceDAO.getExamRoomUtilizations(calendar, examType, limit,
                freeRatio);
        
        List activityIds = new ArrayList();
        for (Iterator iter = rs.iterator(); iter.hasNext();) {
            Object[] util = (Object[]) iter.next();
            activityIds.add(util[0]);
        }
        List activities = utilService.load(ExamActivity.class, "id", activityIds);
        
        Map activityMap = new HashMap();
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            ExamActivity element = (ExamActivity) iter.next();
            activityMap.put(element.getId(), element);
        }
        List utilizations = new ArrayList();
        for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
            Object[] util = (Object[]) iterator.next();
            utilizations.add(new Object[] { activityMap.get(util[0]), util[1], util[2], util[3] });
        }
        if (rs instanceof SinglePage) {
            ((SinglePage) rs).setPageDatas(utilizations);
            return rs;
        } else {
            return utilizations;
        }
    }
    
    /**
     * 查询上课教室利用率
     */
    public Collection getRoomUtilizationOfCourse(TeachCalendar calendar, DataRealmLimit limit,
            List orders, Float ratio) {
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        
        entityQuery.add(new Condition("task.calendar=:calendar", calendar));
        entityQuery.add(new Condition("task.arrangeInfo.teachDepart.id in(:departIds)",
                SeqStringUtil.transformToLong(limit.getDataRealm().getDepartmentIdSeq())));
        entityQuery.add(new Condition("task.teachClass.stdType.id in(:stdTypeId)", SeqStringUtil
                .transformToLong(limit.getDataRealm().getStudentTypeIdSeq())));
        entityQuery.join("task.arrangeInfo.activities", "activity");
        entityQuery.join("activity.room", "room");
        String ratioStr = "(case when room.capacityOfCourse >0 "
                + " then case when task.teachClass.stdCount!=0 "
                + "   then (task.teachClass.stdCount/room.capacityOfCourse*1.0)"
                + "   else  (task.teachClass.planStdCount/room.capacityOfCourse*1.0)"
                + "end else 0.0 end)";
        entityQuery.setSelect("select task,room," + ratioStr);
        entityQuery.add(new Condition(ratioStr + "  <=" + ratio));
        entityQuery.setLimit(limit.getPageLimit());
        for (Iterator iter = orders.iterator(); iter.hasNext();) {
            Order order = (Order) iter.next();
            if (order.getProperty().equals("ratio")) {
                order.setProperty(ratioStr);
            }
        }
        entityQuery.addOrder(orders);
        return utilDao.search(entityQuery);
        
    }
    
    /**
     * @see com.shufe.service.course.arrange.resource.TeachResourceService#getRoomsByIds(java.lang.String)
     */
    public List getClassrooms(String roomIdSeq) {
        return (!StringUtils.isEmpty(roomIdSeq)) ? teachResourceDAO.getClassrooms(SeqStringUtil
                .transformToLong(roomIdSeq)) : Collections.EMPTY_LIST;
        
    }
    
    /**
     * @see com.shufe.service.course.arrange.resource.TeachResourceService#getRoomsByIds(java.lang.String[])
     */
    public List getClassrooms(Long[] roomIds) {
        if (null != roomIds || roomIds.length > 0)
            return teachResourceDAO.getClassrooms(roomIds);
        else
            return Collections.EMPTY_LIST;
    }
    
    /**
     * @see com.shufe.service.course.arrange.resource.TeachResourceService#getRoomsByIds(java.lang.String[])
     */
    public List getClassrooms(Collection roomIds) {
        return (!roomIds.isEmpty()) ? teachResourceDAO.getClassrooms((Long[]) roomIds
                .toArray(new Long[roomIds.size()])) : Collections.EMPTY_LIST;
    }
    
    /**
     * @see com.shufe.service.course.arrange.resource.TeachResourceService#getTeachers(java.util.Collection)
     */
    public List getTeachers(Collection teacherIds) {
        return (!teacherIds.isEmpty()) ? teachResourceDAO.getTeachers(teacherIds)
                : Collections.EMPTY_LIST;
    }
    
    /**
     * @see com.shufe.service.course.arrange.resource.TeachResourceService#setResourceDAO(com.shufe.dao.course.arrange.resource.TeachResourceDAO)
     */
    public void setTeachResourceDAO(TeachResourceDAO dao) {
        this.teachResourceDAO = dao;
    }
    
}
