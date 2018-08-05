// $createAt:2005-12-22-14:01:49-14:01:49 userCreated:dell

package com.shufe.service.course.arrange.apply.impl;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.time.CourseUnit;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.security.User;
import com.shufe.model.course.arrange.Activity;
import com.shufe.model.course.arrange.apply.ApplyTime;
import com.shufe.model.course.arrange.apply.RoomApply;
import com.shufe.model.course.arrange.resource.RoomPriceConfig;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.calendar.TimeSetting;
import com.shufe.service.BasicService;
import com.shufe.service.course.arrange.apply.RoomApplyService;
import com.shufe.service.course.arrange.resource.TeachResourceService;
import com.shufe.service.system.message.SystemMessageService;

/**
 * @author dell
 */
public class RoomApplyServiceImpl extends BasicService implements RoomApplyService {
    
    protected TeachResourceService teachResourceService;
    
    protected SystemMessageService messageService;
    
    public List getRoomApply(Classroom room, ApplyTime applyTime) {
        EntityQuery query = new EntityQuery(RoomApply.class, "roomApply");
        query
                .add(new Condition(
                        "roomApply.applyTime.dateBegin <= :dateEnd  and :dateBeign <= roomApply.applyTime.dateEnd",
                        applyTime.getDateEnd(), applyTime.getDateBegin()));
        query
                .add(new Condition(
                        "roomApply.applyTime.timeBegin <= :timeEnd  and :timeBeign <= roomApply.applyTime.timeEnd",
                        applyTime.getTimeEnd(), applyTime.getTimeBegin()));
        query.add(new Condition("roomApply.applyTime.cycleCount = :cycleCount", applyTime
                .getCycleCount()));
        query.add(new Condition("roomApply.applyTime.cycleType = :cycleType", applyTime
                .getCycleType()));
        query.add(new Condition("roomApply.classroom=:classroom", room));
        return (List) utilDao.search(query);
    }
    
    /**
     * 检测教室申请是否可以批准，即教室是否空闲
     * 
     * @param roomApply
     */
    public boolean canApprove(RoomApply roomApply) {
        return true;
    }
    
    /**
     * 批准教室申请(允许批量分配教室)
     * 
     * @param roomApply
     */
    public boolean approve(RoomApply roomApply, User approveBy, Set classrooms) {
        roomApply.setIsApproved(Boolean.TRUE);
        roomApply.setApproveAt(new Timestamp(System.currentTimeMillis()));
        roomApply.setApproveBy(approveBy);
        float all = 0;
        
        if (roomApply.getIsFree().booleanValue() == false) {
            for (Iterator iter = classrooms.iterator(); iter.hasNext();) {
                Classroom classroom = (Classroom) iter.next();
                EntityQuery query = new EntityQuery(RoomPriceConfig.class, "roomPriceConfig");
                String hql = "roomPriceConfig.roomConfigType.id = :id";
                query.add(new Condition(hql, classroom.getConfigType().getId()));
                hql = "(:people) >= roomPriceConfig.minSeats and :people <= roomPriceConfig.maxSeats";
                query.add(new Condition(hql, classroom.getCapacityOfCourse()));
                hql = "roomPriceConfig.catalogue.schoolDistrict is null or roomPriceConfig.catalogue.schoolDistrict = :schoolDistrict";
                query.add(new Condition(hql, classroom.getSchoolDistrict()));
                List list = (List) utilService.search(query);
                Float money = new Float(0);
                if (list.size() == 1) {
                    money = ((RoomPriceConfig) list.get(0)).getPrice();
                } else if (list.size() > 1) {
                    for (Iterator it = list.iterator(); it.hasNext();) {
                        RoomPriceConfig roomPrice = (RoomPriceConfig) it.next();
                        if (null != roomPrice.getCatalogue().getSchoolDistrict()) {
                            money = roomPrice.getPrice();
                            break;
                        }
                    }
                }
                all += roomApply.getHours().floatValue() * money.floatValue();
            }
        }
        roomApply.setMoney(new Float(all));
        // 教研活动
        roomApply.getActivities().clear();
        TimeUnit[] units = roomApply.getApplyTime().convert();
        
        TimeSetting setting = (TimeSetting) utilService.load(TimeSetting.class, new Long(1));
        Integer startTime = CourseUnit.getTimeNumber(roomApply.getApplyTime().getTimeBegin());
        Integer endTime = CourseUnit.getTimeNumber(roomApply.getApplyTime().getTimeEnd());
        Integer[] unit = setting.getUnitIndexes(startTime, endTime);
        if (unit[0] == null) {
            unit[0] = new Integer(0);
        }
        if (unit[unit.length - 1] == null) {
            unit[unit.length - 1] = new Integer(0);
        }
        for (int i = 0; i < units.length; i++) {
            EntityQuery query = new EntityQuery(Activity.class, "activity");
            query.add(new Condition("activity.room in (:room)", classrooms));
            query.add(new Condition("activity.time.year = :year", units[i].getYear()));
            query.add(new Condition("activity.time.weekId = :weekId", units[i].getWeekId()));
            query.add(new Condition("bitand (activity.time.validWeeksNum, :validWeeksNum) > 0",
                    units[i].getValidWeeksNum()));
            query.add(new Condition("activity.time.startUnit <= :endUnit", endTime));
            query.add(new Condition("activity.time.endUnit >= :startUnit", startTime));
            if (CollectionUtils.isNotEmpty(utilService.search(query))) {
                return false;
            }
        }
        for (int i = 0; i < units.length; i++) {
            for (Iterator iter = classrooms.iterator(); iter.hasNext();) {
                Activity activity = new Activity();
                Classroom classroom = (Classroom) iter.next();
                activity.setRoom(classroom);
                units[i].setStartUnit(unit[0]);
                units[i].setEndUnit(unit[unit.length - 1]);
                activity.setTime(units[i]);
                roomApply.getActivities().add(activity);
                
            }
        }
        utilService.saveOrUpdate(roomApply);
        return true;
    }
    
    public void departApprove(RoomApply roomApply, User approveBy) {
        roomApply.setIsDepartApproved(Boolean.TRUE);
        roomApply.setIsApproved(Boolean.FALSE);
        roomApply.setDepartApproveBy(approveBy);
        roomApply.setDepartApproveAt(new Timestamp(System.currentTimeMillis()));
        roomApply.setApproveBy(null);
        roomApply.setApproveAt(null);
        utilService.saveOrUpdate(roomApply);
    }
    
    public void departCancel(RoomApply roomApply, User approveBy) {
        roomApply.setIsDepartApproved(Boolean.FALSE);
        roomApply.setIsApproved(Boolean.FALSE);
        roomApply.setDepartApproveBy(approveBy);
        roomApply.setDepartApproveAt(new Timestamp(System.currentTimeMillis()));
        roomApply.setApproveBy(null);
        roomApply.setApproveAt(null);
        utilService.saveOrUpdate(roomApply);
    }
    
    /**
     * @param messageService
     *            要设置的 messageService.
     */
    public void setMessageService(SystemMessageService messageService) {
        
        this.messageService = messageService;
    }
    
    /**
     * @param teachResourceService
     *            要设置的 teachResourceService.
     */
    public void setTeachResourceService(TeachResourceService teachResourceService) {
        
        this.teachResourceService = teachResourceService;
    }
}
