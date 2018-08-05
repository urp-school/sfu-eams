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
 * chaostone             2006-11-6            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;

import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.course.arrange.exam.ExamArrangeGroup;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.service.course.arrange.exam.comparator.ExamClassroomComparator;
import com.shufe.service.course.arrange.exam.impl.DefaultExamActivityGenerator;
import com.shufe.service.course.arrange.exam.impl.DefaultExamTakeGenerator;

/**
 * 排考教室分配者
 * 
 * @author chaostone
 * 
 */
public class ExamRoomAllocator {
    
    private UtilDao utilDao;
    
    /** 教学任务之间是否可以合并利用教室 */
    private boolean canMerged;
    
    /** 分配的教室是否和教学任务的教室处于同一校区 */
    private boolean isAllocByDistrict;
    
    /** 排考类型 */
    private ExamType examType;
    
    /** 各个校区的教室占用情况(null也是其中一个key) */
    private Map districtRoomCapacityMap = new HashMap();
    
    /**
     * 对教室进行排序初始化
     * 
     * @param rooms
     * @param utilDao
     */
    public ExamRoomAllocator(List rooms, UtilDao utilDao, ExamType examType, boolean canMerged,
            boolean isAllocByDistrict) {
        Collections.sort(rooms, new ExamClassroomComparator());
        // 将教室按照校区分化到map中
        if (!rooms.isEmpty()) {
            SchoolDistrict district = ((Classroom) (rooms.get(0))).getSchoolDistrict();
            List roomCapacities = new ArrayList();
            for (Iterator iterator = rooms.iterator(); iterator.hasNext();) {
                Classroom room = (Classroom) iterator.next();
                if (!ObjectUtils.equals(district, room.getSchoolDistrict())) {
                    districtRoomCapacityMap.put(district, new DistrictRoomCapacity(district,
                            roomCapacities));
                    district = room.getSchoolDistrict();
                    roomCapacities = new ArrayList();
                }
                roomCapacities.add(new RoomCapacity(room, room.getCapacityOfExam().intValue()));
            }
            if (!roomCapacities.isEmpty()) {
                districtRoomCapacityMap.put(district, new DistrictRoomCapacity(district,
                        roomCapacities));
            }
        }
        this.examType = examType;
        this.canMerged = canMerged;
        this.isAllocByDistrict = isAllocByDistrict;
        this.utilDao = utilDao;
    }
    
    /**
     * 教室分配过程
     * 
     * @param group
     * @return
     */
    public boolean alloc(ExamArrangeGroup group, TimeUnit unit) {
        DefaultExamActivityGenerator activityGenerator = new DefaultExamActivityGenerator();
        activityGenerator.setUnit(unit);
        DefaultExamTakeGenerator takeGenerator = new DefaultExamTakeGenerator(utilDao);
        return alloc(group.getTasks(), activityGenerator, takeGenerator);
    }
    
    /**
     * 分配教室
     * 
     * @param tasks
     * @param examType
     * @param activityGenerator
     * @param examTakeGenerator
     * @param canMerged
     * @return
     */
    public boolean alloc(List tasks, ExamActivityGenerator activityGenerator,
            ExamTakeGenerator examTakeGenerator) {
        AllocateArea area = new AllocateArea(districtRoomCapacityMap, true);
        if (!canAlloc(area, tasks, examTakeGenerator)) {
            return false;
        }
        area = new AllocateArea(districtRoomCapacityMap, false);
        // 针对每一个任务
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            // 找到应考学生
            List examTakes = examTakeGenerator.generate(task, examType);
            Iterator takeIter = examTakes.iterator();
            // 分配教室
            List roomAllocs = alloc(area, examTakes.size(), (isAllocByDistrict ? task
                    .getArrangeInfo().getSchoolDistrict() : null));
            if (CollectionUtils.isEmpty(roomAllocs))
                return false;
            // 生成考试安排
            List examActivities = activityGenerator.generate(task, examType, roomAllocs.size());
            
            for (int i = 0; i < roomAllocs.size(); i++) {
                ExamActivity examActivity = (ExamActivity) examActivities.get(i);
                if (null == examActivity.getRoom()) {
                    examActivity.setRoom(((RoomAlloc) roomAllocs.get(i)).room);
                    for (int j = 0; j < ((RoomAlloc) roomAllocs.get(i)).alloc; j++) {
                        ExamTake examTake = (ExamTake) takeIter.next();
                        examTake.setActivity(examActivity);
                        examTake.setCalendar(examActivity.getCalendar());
                        // 应考记录中可能是补考或者缓考,但是这次安排的性质可能是补缓考
                        if (!ValidEntityPredicate.INSTANCE.evaluate(examTake)) {
                            examTake.setExamType(examActivity.getExamType());
                        }
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * 检查是否可以分配
     * 
     * @param tasks
     * @param examType
     * @param takeGenerator
     * @param canMerged
     * @return
     */
    public boolean canAlloc(AllocateArea area, final List tasks, ExamTakeGenerator takeGenerator) {
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            int stdCount = takeGenerator.getTakeCount(task, examType);
            List allocated = alloc(area, stdCount, isAllocByDistrict ? task.getArrangeInfo()
                    .getSchoolDistrict() : null);
            if (allocated.isEmpty())
                return false;
        }
        return true;
    }
    
    /**
     * 进行分配
     * 
     * @param wanted
     * @param canMerged
     * @param schoolDistrict
     * @return
     */
    private List alloc(AllocateArea area, int wanted, SchoolDistrict district) {
        int stdCount = wanted;
        List allocated = new ArrayList();
        int total = 0;
        // 所需的教室
        for (; stdCount > 0 && area.hasFree(district);) {
            RoomCapacity capacity = (RoomCapacity) area.current();
            int alloc = (capacity.free < stdCount) ? capacity.free : stdCount;
            capacity.free -= alloc;
            stdCount -= alloc;
            total += capacity.room.getCapacityOfExam().intValue();
            allocated.add(new RoomAlloc(capacity.room, alloc));
            if (capacity.free <= 0) {
                area.move();
            } else if (!canMerged) {
                area.move();
            }
        }
        // 如果不能合并教室考试，则进行人数分配优化
        if (!canMerged) {
            int allocCount = 0;
            for (Iterator it = allocated.iterator(); it.hasNext();) {
                RoomAlloc roomAlloc = (RoomAlloc) it.next();
                if (!it.hasNext()) {
                    roomAlloc.alloc = wanted - allocCount;
                    break;
                }
                allocCount += roomAlloc.alloc = (int) Math.round(((double) wanted * (roomAlloc.room
                        .getCapacityOfExam().intValue() / (double) total)));
            }
        }
        if (stdCount <= 0) {
            return allocated;
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}

/**
 * 教室分配区域
 * 
 * @author chaostone
 * 
 */
class AllocateArea {
    
    DistrictRoomCapacity[] districtRoomCapacities = null;
    
    private int index = 0;
    
    boolean testMode = false;
    
    public AllocateArea(Map districtRoomCapacityMap, boolean testMode) {
        districtRoomCapacities = new DistrictRoomCapacity[districtRoomCapacityMap.size()];
        int i = 0;
        for (Iterator iter = districtRoomCapacityMap.values().iterator(); iter.hasNext();) {
            DistrictRoomCapacity capacity = (DistrictRoomCapacity) iter.next();
            districtRoomCapacities[i] = (testMode ? (DistrictRoomCapacity) capacity.clone()
                    : capacity);
            i++;
        }
        this.testMode = testMode;
    }
    
    public boolean hasFree(SchoolDistrict district) {
        index = 0;
        while (index < districtRoomCapacities.length) {
            if (null != districtRoomCapacities[index]) {
                if (null == district) {
                    return districtRoomCapacities[index].hasFree();
                } else if (districtRoomCapacities[index].district.equals(district)) {
                    return districtRoomCapacities[index].hasFree();
                }
            }
            index++;
        }
        return false;
    }
    
    public RoomCapacity current() {
        return (RoomCapacity) districtRoomCapacities[index].current();
    }
    
    public void move() {
        districtRoomCapacities[index].move();
    }
    
}

/**
 * 校区教室使用情况
 * 
 * @author chaostone
 * 
 */
class DistrictRoomCapacity {
    
    List roomCapacities;
    
    int index;
    
    SchoolDistrict district;
    
    public DistrictRoomCapacity(SchoolDistrict district, List roomCapacities) {
        this.district = district;
        this.roomCapacities = roomCapacities;
        this.index = 0;
    }
    
    public RoomCapacity current() {
        return (RoomCapacity) roomCapacities.get(index);
    }
    
    public void move() {
        index++;
    }
    
    public boolean hasFree() {
        return index < roomCapacities.size();
    }
    
    public Object clone() {
        List newRoomCapacities = new ArrayList(roomCapacities.size() - index);
        for (int i = index; i < roomCapacities.size(); i++) {
            RoomCapacity capacity = (RoomCapacity) roomCapacities.get(i);
            newRoomCapacities.add(capacity.clone());
        }
        DistrictRoomCapacity newOne = new DistrictRoomCapacity(district, newRoomCapacities);
        newOne.index = 0;
        return newOne;
    }
}

/**
 * 教室空余情况
 * 
 * @author chaostone
 * 
 */
class RoomCapacity {
    
    Classroom room;
    
    int free;
    
    public RoomCapacity() {
        super();
    }
    
    public RoomCapacity(Classroom room, int free) {
        super();
        this.room = room;
        this.free = free;
    }
    
    public Object clone() {
        return new RoomCapacity(room, free);
    }
}

/**
 * 教室分配情况
 */
class RoomAlloc {
    
    Classroom room;
    
    int alloc;
    
    public RoomAlloc(Classroom room, int alloc) {
        super();
        this.room = room;
        this.alloc = alloc;
    }
}
