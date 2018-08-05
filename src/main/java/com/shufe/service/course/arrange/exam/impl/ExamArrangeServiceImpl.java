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

package com.shufe.service.course.arrange.exam.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.dao.course.arrange.exam.ExamArrangeDAO;
import com.shufe.dao.course.arrange.resource.TeachResourceDAO;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.course.arrange.exam.ExamArrangeGroup;
import com.shufe.model.course.arrange.exam.ExamParams;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.service.BasicService;
import com.shufe.service.OutputObserver;
import com.shufe.service.course.arrange.exam.ExamArrangeService;
import com.shufe.service.course.arrange.exam.ExamRoomAllocator;
import com.shufe.service.course.arrange.exam.ExamTakeGenerator;
import com.shufe.service.course.arrange.exam.ExamTime;
import com.shufe.service.course.arrange.exam.comparator.ExamTeachTaskComparator;
import com.shufe.service.course.arrange.exam.impl.time.ExamCollisionInfo;
import com.shufe.service.course.arrange.exam.impl.time.TimeIterator;
import com.shufe.service.course.arrange.exam.impl.time.TimeOccupy;
import com.shufe.web.OutputMessage;
import com.shufe.web.action.course.arrange.exam.ExamArrangeWebObserver;

public class ExamArrangeServiceImpl extends BasicService implements ExamArrangeService {
    
    private TeachResourceDAO teachResourceDAO;
    
    private ExamArrangeDAO examArrangeDAO;
    
    public void arrange(List tasks, ExamParams params) {
        arrange(tasks, params, null);
    }
    
    /**
     * 排考
     */
    public void arrange(List tasks, ExamParams params, ExamArrangeWebObserver observer) {
        // 如果是补缓考,则按照补缓考的方法进行安排
        if ((params.getExamType().getId().equals(ExamType.DELAY_AGAIN))
                || (params.getExamType().getId().equals(ExamType.AGAIN))
                || (params.getExamType().getId().equals(ExamType.DELAY))) {
            arrangeExamTake(tasks, params, observer);
            return;
        }
        if (null != observer) {
            observer.notifyStart();
        }
        // 清除已有的排考结果
        examArrangeDAO.removeExamArranges(tasks, params.getExamType());
        if (null != observer) {
            observer.outputNotify(new OutputMessage("", "正在编组..."));
        }
        DefaultExamTakeGenerator examTakeGenerator = new DefaultExamTakeGenerator(utilDao);
        // 构建排考组ExamArrangeGroup
        List groups = buildExamArrangeGroups(tasks, examTakeGenerator, params);
        for (Iterator iter = groups.iterator(); iter.hasNext();) {
            ExamArrangeGroup group = (ExamArrangeGroup) iter.next();
            if (null != observer) {
                observer.outputNotify(group, new OutputMessage("", "安排开始"), OutputObserver.good);
            }
            // 找出共同起始排考周
            int commonStartWeek = 0;
            if (ExamParams.COMMON_WEEK_MODE == params.getStartWeekMode()) {
                commonStartWeek = params.getFromWeek();
            } else {
                commonStartWeek = group.getCommonStartWeek() + params.getFromWeek();
            }
            TimeIterator timeIter = new TimeIterator(group.getCalendar(), commonStartWeek, params
                    .getLastWeek(), params.getWeekMode(), params.getExamTimes());
            
            if (null != observer) {
                observer.outputNotify(new OutputMessage("", "从" + commonStartWeek + "周开始查找可用时间"));
            }
            boolean arrangeSuccess = false;
            List collisionTimes = new ArrayList();
            // 遍历场次,开始排考
            while (timeIter.hasNext()) {
                ExamTime time = (ExamTime) timeIter.next();
                TimeUnit unit = time.getUnit();
                if (null != observer) {
                    observer.outputNotify(new OutputMessage("", "查找时间段:第" + time.getStartWeek()
                            + "周 " + time.getWeek().getName() + " " + time.getTurn()));
                }
                if ((unit.getStartUnit() == null || unit.getEndUnit() == null) && null != observer) {
                    observer.outputNotify(new OutputMessage("", "该时间段没有对应小节,暂时无法安排"));
                    continue;
                }
                // 检测冲突，并且记录
                int collisionCount = detectCollisionCount(unit, group, params, examTakeGenerator,
                        observer);
                if (0 != collisionCount) {
                    collisionTimes.add(new ExamCollisionInfo(unit, collisionCount));
                    continue;
                }
                // 不用安排教室
                if (null == params.getRooms() || params.getRooms().isEmpty()) {
                    for (Iterator taskIter = group.getTasks().iterator(); taskIter.hasNext();) {
                        TeachTask task = (TeachTask) taskIter.next();
                        Set courseTakes = task.getTeachClass().getCourseTakes();
                        Iterator takeIter = courseTakes.iterator();
                        ExamActivity examActivity = new ExamActivity();
                        examActivity.setRoom(null);
                        examActivity.setTask(task);
                        examActivity.setTime(unit);
                        examActivity.setExamType(params.getExamType());
                        examActivity.setCalendar(task.getCalendar());
                        examActivity.getExamMonitor().setExaminer(null);
                        examActivity.getExamMonitor().setInvigilator(null);
                        examActivity.getExamMonitor().setDepart(task.getTeachClass().getDepart());
                        task.getArrangeInfo().getExamActivities().add(examActivity);
                        while (takeIter.hasNext()) {
                            CourseTake courseTake = (CourseTake) takeIter.next();
                            task.getTeachClass()
                                    .addExamTake(new ExamTake(courseTake, examActivity));
                        }
                    }
                    saveExamResult(group.getTasks(), params.getExamType());
                    arrangeSuccess = true;
                    break;
                } else {
                    // 分配教室
                    arrangeSuccess = allocateRoom(unit, params, group, observer);
                    if (arrangeSuccess) {
                        break;
                    }
                }
            }
            if (!arrangeSuccess && !collisionTimes.isEmpty()) {
                Collections.sort(collisionTimes);
                ExamCollisionInfo collision = (ExamCollisionInfo) collisionTimes.get(0);
                if ((collision.getCollisionCount() * 1.0) / group.getStdCount() < params
                        .getCollisionRatio()) {
                    arrangeSuccess = allocateRoom(collision.getTime(), params, group, observer);
                }
            } else {
                collisionTimes = null;
            }
        }
        if (null != observer) {
            observer.notifyFinish();
        }
        return;
    }
    
    /**
     * 对教学任务进行编组(同一课程代码安排在同一场次).<br>
     * 课程组之间排序标准,课程代码,人数<br>
     * 组内教学任务排序,按照校区、教师、和人数<br>
     * ＠see ExamTeachTaskComparator
     */
    public List buildExamArrangeGroups(List tasks, ExamTakeGenerator takeGenerator,
            ExamParams params) {
        // 合并同一课程代码的课程
        Map courseMap = new HashMap();
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            ExamArrangeGroup group = null;
            if (courseMap.containsKey(task.getCourse().getId())) {
                group = (ExamArrangeGroup) courseMap.get(task.getCourse().getId());
            } else {
                group = new ExamArrangeGroup(task.getCalendar(), task.getCourse());
                group.setTasks(new ArrayList());
                courseMap.put(task.getCourse().getId(), group);
            }
            group.getTasks().add(task);
        }
        // 合并业务人员规定可以合并的组
        List groupCourseLists = buildGroupCourseList(params);
        List removedGroups = new ArrayList();
        if (!CollectionUtils.isEmpty(groupCourseLists)) {
            for (Iterator iterator = groupCourseLists.iterator(); iterator.hasNext();) {
                List groupCourseList = (List) iterator.next();
                ExamArrangeGroup group = null;
                for (Iterator iterator2 = groupCourseList.iterator(); iterator2.hasNext();) {
                    Course course = (Course) iterator2.next();
                    if (null == group) {
                        group = (ExamArrangeGroup) courseMap.get(course.getId());
                    } else {
                        ExamArrangeGroup myGroup = (ExamArrangeGroup) courseMap.get(course.getId());
                        if (null != myGroup) {
                            group.getTasks().addAll(myGroup.getTasks());
                            removedGroups.add(myGroup);
                        }
                    }
                }
            }
        }
        
        List examArrangeGroups = new ArrayList(courseMap.values());
        if (!CollectionUtils.isEmpty(removedGroups)) {
            examArrangeGroups.removeAll(removedGroups);
        }
        
        // 统计人数,并且进行排序
        Comparator comparator = new ExamTeachTaskComparator(params.isSameDistrictWithCourse());
        for (Iterator iter = examArrangeGroups.iterator(); iter.hasNext();) {
            ExamArrangeGroup group = (ExamArrangeGroup) iter.next();
            group.sortTasks(comparator);
            group.statStdCount(takeGenerator, params.getExamType());
        }
        // 按照人数对组进行降序排列
        Collections.sort(examArrangeGroups, new PropertyComparator("stdCount desc"));
        return examArrangeGroups;
    }
    
    /**
     * 可用外界使用的教室分配服务
     */
    public boolean allocateRooms(List tasks, ExamType examType, List classrooms,
            Boolean canCombineTask, Boolean sameDistrictWithCourse) {
        ExamRoomAllocator roomAllocator = new ExamRoomAllocator(classrooms, utilDao, examType,
                canCombineTask.booleanValue(), sameDistrictWithCourse.booleanValue());
        DefaultExamTakeGenerator takeGenerator = new DefaultExamTakeGenerator(utilDao);
        return roomAllocator.alloc(tasks, new DefaultExamActivityGenerator(), takeGenerator);
    }
    
    /**
     * 给已经生成的应考学生记录，分配时间
     * 
     * @param taskss
     * @param params
     * @param observer
     * @return
     */
    private boolean arrangeExamTake(List tasks, ExamParams params, ExamArrangeWebObserver observer) {
        DefaultExamTakeGenerator takeGenerator = new DefaultExamTakeGenerator(utilDao);
        DefaultExamActivityGenerator activityGenerator = new DefaultExamActivityGenerator();
        List stdIds = statExamStd(tasks, params);
        if (null != observer) {
            observer.outputNotify(new OutputMessage("", "参加考试的学生人数：" + stdIds.size()));
            observer.outputNotify(new OutputMessage("", "开始编组..."));
        }
        
        List groups = buildExamArrangeGroups(tasks, takeGenerator, params);
        List timeOccupies = new ArrayList();
        for (Iterator iterator = groups.iterator(); iterator.hasNext();) {
            ExamArrangeGroup group = (ExamArrangeGroup) iterator.next();
            group.buildOccupy(stdIds, params.getExamType(), takeGenerator);
        }
        if (null != observer) {
            observer.outputNotify(new OutputMessage("", "开始安排..."));
        }
        TimeIterator timeIter = new TimeIterator(params.getToCalendar(), params.getFromWeek(),
                params.getLastWeek(), params.getWeekMode(), params.getExamTimes());
        int turns = 0;
        for (Iterator iterator = groups.iterator(); iterator.hasNext();) {
            ExamArrangeGroup group = (ExamArrangeGroup) iterator.next();
            boolean added = false;
            for (Iterator iterator2 = timeOccupies.iterator(); iterator2.hasNext();) {
                TimeOccupy timeOccupy = (TimeOccupy) iterator2.next();
                timeOccupy.setMaxStdNum(params.getStdPerTurn());
                if (timeOccupy.isCompatible(group) && timeOccupy.isHaveCapacityForGroup(group)) {
                    timeOccupy.addGroup(group);
                    added = true;
                    break;
                }
            }
            if (!added) {
                if (timeIter.hasNext()) {
                    ExamTime time = (ExamTime) timeIter.next();
                    turns++;
                    timeOccupies.add(new TimeOccupy(group, (TimeUnit) time.getUnit().clone()));
                } else {
                    observer
                            .outputNotify(new OutputMessage("", "排考失败,场次不够!!(已使用" + turns + "个场次)"));
                    return false;
                }
            }
        }
        if (null != observer) {
            observer.outputNotify(new OutputMessage("", "正在保存数据..."));
        }
        // 存储
        for (Iterator iterator = timeOccupies.iterator(); iterator.hasNext();) {
            TimeOccupy timeOccupy = (TimeOccupy) iterator.next();
            activityGenerator.setUnit(timeOccupy.getUnit());
            for (Iterator iterator2 = timeOccupy.getGroups().iterator(); iterator2.hasNext();) {
                ExamArrangeGroup group = (ExamArrangeGroup) iterator2.next();
                for (Iterator iterator3 = group.getTasks().iterator(); iterator3.hasNext();) {
                    TeachTask task = (TeachTask) iterator3.next();
                    List examActivities = activityGenerator.generate(task, params.getExamType(), 1);
                    List takes = takeGenerator.generate(task, params.getExamType());
                    for (Iterator iterator4 = takes.iterator(); iterator4.hasNext();) {
                        ExamTake take = (ExamTake) iterator4.next();
                        take.setActivity((ExamActivity) examActivities.get(0));
                    }
                }
                utilDao.saveOrUpdate(group.getTasks());
            }
        }
        if (null != observer) {
            observer.outputNotify(new OutputMessage("", "排考成功"));
        }
        return true;
    }
    
    /**
     * 查找应考学生stdId,count(*) 学生课多的列在前方
     * 
     * @return
     */
    private List statExamStd(List tasks, ExamParams params) {
        EntityQuery query = new EntityQuery(ExamTake.class, "take");
        query.add(new Condition("take.calendar.id=:calendarId", params.getFromCalendar().getId()));
        List examTypeIds = new ArrayList();
        examTypeIds.add(params.getExamType().getId());
        if (params.getExamType().getId().equals(ExamType.DELAY_AGAIN)) {
            examTypeIds.add(ExamType.AGAIN);
            examTypeIds.add(ExamType.DELAY);
        }
        query.add(new Condition("take.examType.id in(:examTypeId)", examTypeIds));
        query.add(new Condition(" instr(:taskIds,','||take.courseTake.task.id||',')>0", ""));
        query.setSelect("take.std.id,count(*)");
        query.groupBy("take.std.id");
        
        List taskIdsList = new ArrayList();
        StringBuffer taskIds = new StringBuffer(",");
        int i = 0;
        // 为了sql的长度，每100个任务查找一次
        for (Iterator iterator = tasks.iterator(); iterator.hasNext();) {
            TeachTask task = (TeachTask) iterator.next();
            taskIds.append(task.getId()).append(",");
            i++;
            i %= 100;
            if (i == 0) {
                taskIdsList.add(taskIds);
                taskIds = new StringBuffer(",");
            }
        }
        taskIdsList.add(taskIds);
        
        List stdIdCount = new ArrayList();
        Map queryParams = query.getParams();
        for (Iterator iter = taskIdsList.iterator(); iter.hasNext();) {
            StringBuffer taskIdBuf = (StringBuffer) iter.next();
            queryParams.put("taskIds", taskIdBuf.toString());
            query.setParams(queryParams);
            stdIdCount.addAll(utilDao.search(query));
        }
        // 按照考试课程数量进行组合
        Map stdMap = new HashMap();
        for (Iterator iter = stdIdCount.iterator(); iter.hasNext();) {
            Object[] datas = (Object[]) iter.next();
            Object[] exist = (Object[]) stdMap.get(datas[0]);
            if (null == exist) {
                stdMap.put(datas[0], datas);
            } else {
                int newCount = NumberUtils.toInt(exist[1].toString());
                if (null != datas[1])
                    newCount += ((Number) datas[1]).intValue();
                datas[1] = new Long(newCount);
                stdMap.put(datas[0], datas);
            }
        }
        // 排序
        List finalStdIds = new ArrayList(stdMap.values());
        Collections.sort(finalStdIds, new PropertyComparator("[1] desc"));
        return finalStdIds;
    }
    
    /**
     * 分配教室
     * 
     * @param unit
     * @param params
     * @param group
     * @param observer
     * @return
     */
    private boolean allocateRoom(TimeUnit unit, ExamParams params, ExamArrangeGroup group,
            ExamArrangeWebObserver observer) {
        List freeRooms = teachResourceDAO.getFreeRoomsIn(EntityUtils.extractIds(params.getRooms()),
                unit);
        if (logger.isDebugEnabled()) {
            logger.debug("Find Free rooms for exam at " + unit + freeRooms);
        }
        ExamRoomAllocator roomAllocator = new ExamRoomAllocator(freeRooms, utilDao, params
                .getExamType(), params.isCanCombineTask(), params.isSameDistrictWithCourse());
        if (null != observer) {
            observer.outputNotify(new OutputMessage("", "开始分配教室.."));
        }
        if (roomAllocator.alloc(group, unit)) {
            saveExamResult(group.getTasks(), params.getExamType());
            if (null != observer) {
                observer.outputNotify(new OutputMessage("", "分配成功!"));
            }
            return true;
        } else {
            if (null != observer) {
                observer.outputNotify(new OutputMessage("", "教室不够!"));
            }
            return false;
        }
        
    }
    
    /**
     * 依照排考参数中的设置，检查资源时间冲突
     * 
     * @param unit
     * @param group
     * @param params
     * @param takeGenerator
     * @param observer
     * @return
     */
    private int detectCollisionCount(TimeUnit unit, ExamArrangeGroup group, ExamParams params,
            ExamTakeGenerator takeGenerator, ExamArrangeWebObserver observer) {
        switch (params.getDetectCollision()) {
            case ExamParams.CLASS_COLLISION:
                if (teachResourceDAO.isAdminClassesOccupied(unit, group.getAdminClasses())) {
                    if (null != observer) {
                        observer.outputNotify(new OutputMessage("", "班级时间冲突!"));
                    }
                    return 1;
                } else {
                    return 0;
                }
            case ExamParams.STD_COLLISION:
                Collection stdIds = group.getStdIds(takeGenerator, params.getExamType());
                if (CollectionUtils.isEmpty(stdIds)) {
                    return 0;
                } else {
                    int collistionCount = teachResourceDAO.occupiedStdCount(unit, stdIds,
                            ExamActivity.class);
                    if (0 != collistionCount) {
                        if (null != observer) {
                            observer.outputNotify(new OutputMessage("", "学生时间冲突!("
                                    + collistionCount + ")人"));
                        }
                        return collistionCount;
                    } else {
                        return 0;
                    }
                }
            default:
                return 0;
        }
    }
    
    private void saveExamResult(Collection tasks, ExamType examType) {
        utilDao.saveOrUpdate(tasks);
    }
    
    /**
     * 将courseNo1,courseNo2;course3,courseNo4的分组输入变成对象列表
     * 
     * @param params
     * @return
     */
    private List buildGroupCourseList(ExamParams params) {
        List groupCourseLists = new ArrayList();
        String groupCourseNos = params.getGroupCourseNos();
        if (StringUtils.isNotEmpty(groupCourseNos)) {
            EntityQuery courseQuery = new EntityQuery(Course.class, "course");
            Condition courseNoCon = new Condition("course.code in(:codes)");
            courseQuery.add(courseNoCon);
            String[] groupCourseNo = StringUtils.split(groupCourseNos, ";");
            for (int i = 0; i < groupCourseNo.length; i++) {
                String[] courseNos = StringUtils.split(groupCourseNo[i].trim(), ",");
                if (null != courseNos && courseNos.length != 0) {
                    courseNoCon.setValues(Collections.singletonList(courseNos));
                    groupCourseLists.add(utilService.search(courseQuery));
                }
            }
        }
        return groupCourseLists;
    }
    
    public void removeExamArranges(List tasks, ExamType examType) {
        examArrangeDAO.removeExamArranges(tasks, examType);
    }
    
    public void setTeachResourceDAO(TeachResourceDAO teachResourceDAO) {
        this.teachResourceDAO = teachResourceDAO;
    }
    
    public void setExamArrangeDAO(ExamArrangeDAO examArrangeDAO) {
        this.examArrangeDAO = examArrangeDAO;
    }
    
}
