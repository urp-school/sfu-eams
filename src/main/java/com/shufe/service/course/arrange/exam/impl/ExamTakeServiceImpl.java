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
 * chaostone             2006-11-29            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.exam.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.eams.system.basecode.industry.ExamStatus;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.dao.course.arrange.exam.ExamTakeDAO;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.arrange.resource.TimeTable;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.arrange.exam.ExamTakeService;

/**
 * 应考记录服务实现
 * 
 * @author chaostone
 * 
 */
public class ExamTakeServiceImpl extends BasicService implements ExamTakeService {
    
    private ExamTakeDAO examTakeDAO;
    
    /**
     * 冲突学生名单
     */
    public Collection collisionStds(TeachCalendar calendar, ExamType examType) {
        // 找出指定学期中所有安排的时间段
        EntityQuery entityQuery = new EntityQuery(ExamActivity.class, "activity");
        entityQuery.add(new Condition("activity.calendar=:calendar", calendar));
        entityQuery.add(new Condition("activity.examType=:examType", examType));
        entityQuery.setSelect("select  distinct activity.time.year,"
                + " activity.time.validWeeksNum," + "activity.time.weekId,"
                + "activity.time.startUnit," + "activity.time.endUnit");
        Collection examTimes = utilDao.search(entityQuery);
        
        // 针对每一个时间段查找参加两门以上考试的学生
        entityQuery = new EntityQuery(ExamTake.class, "take");
        entityQuery.setSelect("select take.std.id");
        entityQuery.add(new Condition("take.calendar=:calendar", calendar));
        Condition timeCondition = new Condition("take.activity.time.year=:year and "
                + " take.activity.time.validWeeksNum=:validWeeksNum"
                + " and take.activity.time.weekId=:weekId "
                + " and take.activity.time.startUnit=:startUnit "
                + " and take.activity.time.endUnit=:endUnit");
        entityQuery.add(timeCondition);
        entityQuery.setGroups(Collections.singletonList("take.std having count(*)>1"));
        List stdIds = new ArrayList();
        for (Iterator iter = examTimes.iterator(); iter.hasNext();) {
            Object[] time = (Object[]) iter.next();
            timeCondition.setValues(Arrays.asList(time));
            stdIds.addAll(utilDao.search(entityQuery));
        }
        return utilService.load(Student.class, "id", stdIds);
    }
    
    /**
     * 判断学生是否可以进行申请缓考
     */
    public boolean canApplyDelayExam(Student std, ExamTake examTake) {
        boolean canApplyDelay = false;
        // 只有正常或者现在是缓考申请状态,方可修改
        if (examTake.getStd().equals(std)) {
            if (examTake.getExamStatus().getId().equals(ExamStatus.NORMAL)
                    || examTake.getExamStatus().getId().equals(ExamStatus.APPLY_DELAY)) {
                canApplyDelay = true;
            }
        } else {
            return false;
        }
        if (canApplyDelay) {
            // 查找正常考试的参考记录
            EntityQuery entityQuery = new EntityQuery(ExamTake.class, "take");
            entityQuery.add(new Condition("take.calendar.id=" + examTake.getCalendar().getId()
                    + " and take.std.id=" + std.getId() + " and take.examType.id="
                    + examTake.getExamType().getId() + " and take.examStatus.id=1"));
            entityQuery.setSelect("select take.activity");
            Collection examActivities = utilService.search(entityQuery);
            examActivities.remove(examTake.getActivity());
            
            TimeTable table = new TimeTable();
            table.addActivities(examActivities);
            
            // 如果冲突则可以进行缓考申请
            if (table.isTimeConflict(Collections.singletonList(examTake.getActivity()))) {
                return true;
            } else {
                return false;
            }
        } else
            return false;
    }
    
    public List statTakeCountWithTurn(TeachCalendar calendar, ExamType examType) {
        if (examType.getId().equals(ExamType.DELAY_AGAIN)) {
            return examTakeDAO.statTakeCountWithTurn(calendar, Arrays.asList(new ExamType[] {
                    examType, new ExamType(ExamType.DELAY), new ExamType(ExamType.AGAIN) }));
        } else {
            return examTakeDAO.statTakeCountWithTurn(calendar, Collections.singletonList(examType));
        }
    }
    
    public List statTakeCountInCourse(TeachCalendar calendar, ExamType examType) {
        if (examType.getId().equals(ExamType.DELAY_AGAIN)) {
            return examTakeDAO.statTakeCountInCourse(calendar, Arrays.asList(new ExamType[] {
                    examType, new ExamType(ExamType.DELAY), new ExamType(ExamType.AGAIN) }));
        } else {
            return examTakeDAO.statTakeCountInCourse(calendar, Collections.singletonList(examType));
        }
    }
    
    public boolean isTakeExam(Student std, TeachCalendar calendar, TeachTask task, ExamType examType) {
        if (null == task || null == examType)
            return false;
        EntityQuery query = new EntityQuery(ExamTake.class, "take");
        query.add(new Condition("take.std=:std and take.calendar=:calendar", std, calendar));
        query.add(new Condition("take.task=:task and take.examType=:examType", task, examType));
        query.add(new Condition("take.examType.id=" + examType.getId()));
        query.setSelect("count(*)");
        List rs = (List) utilDao.search(query);
        Number delayCount = (Number) (rs.get(0));
        if (null == delayCount || delayCount.intValue() == 0)
            return false;
        else
            return true;
    }
    
    public void setExamTakeDAO(ExamTakeDAO examTakeDAO) {
        this.examTakeDAO = examTakeDAO;
    }
    
    /**
     * 根据应考对象得到学生考试座位号
     */
    public Integer getSeatNum(ExamTake exam) {
        if (null != exam.getActivity()&&exam.getActivity().getRoom()!=null) {
            EntityQuery query = new EntityQuery(ExamTake.class, "examTake");
            query.add(new Condition("examTake.activity.room=:room", exam.getActivity().getRoom()));
            query.add(new Condition("examTake.activity.time.year=:year", exam.getActivity()
                    .getTime().getYear()));
            query.add(new Condition("examTake.activity.time.validWeeksNum=:weeksNum", exam
                    .getActivity().getTime().getValidWeeksNum()));
            query.add(new Condition("examTake.activity.time.weekId=:weekId", exam.getActivity()
                    .getTime().getWeekId()));
            query.add(new Condition("examTake.activity.time.startUnit=:startUnit", exam
                    .getActivity().getTime().getStartUnit()));
            query.add(new Condition("examTake.activity.time.endUnit=:endUnit", exam.getActivity()
                    .getTime().getEndUnit()));
            query.addOrder(new Order("examTake.std.code", Order.ASC));
            query.setSelect("examTake.std.code");
            Collection stdCodes = utilDao.search(query);
            int count = 0;
            for (Iterator iter = stdCodes.iterator(); iter.hasNext();) {
                String stdCode = (String) iter.next();
                if (stdCode.equals(exam.getStd().getCode())) {
                    return new Integer(count + 1);
                }
                count++;
            }
        }
        return null;
    }
    
}
