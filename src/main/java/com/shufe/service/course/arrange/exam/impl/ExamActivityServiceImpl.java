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
 * chaostone             2006-12-18            Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.exam.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.dao.course.arrange.resource.TeachResourceDAO;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.arrange.exam.ExamActivityService;

public class ExamActivityServiceImpl extends BasicService implements ExamActivityService {
    
    protected TeachResourceDAO teachResourceDAO;
    
    public Collection getExamActivities(TeachCalendar calendar, Teacher teacher, int kind) {
        EntityQuery entityQuery = new EntityQuery(ExamActivity.class, "activity");
        entityQuery.add(new Condition("activity.calendar =:calendar", calendar));
        switch (kind) {
            case COURSE:
                entityQuery.add(new Condition("exists (from activity.task task where exists (from task.arrangeInfo.teachers teacher where teacher.id=:teacherId))", teacher.getId()));
                return utilDao.search(entityQuery);
            case EXAMINER:
                entityQuery.add(new Condition("activity.teacher.id=:teacherId", teacher.getId()));
                return utilDao.search(entityQuery);
            case INVIGILATOR:
                entityQuery.add(new Condition("activity.examMonitor.invigilator.id=:teacherId", teacher.getId()));
                return utilDao.search(entityQuery);
            case ALL:
                Condition teacherCondition = new Condition();
                teacherCondition.getValues().add(teacher.getId());
                entityQuery.add(teacherCondition);
                HashSet rs = new HashSet();
                teacherCondition.setContent("exists (from activity.task task where exists (from task.arrangeInfo.teachers teacher where teacher.id=:teacherId))");
                rs.addAll(utilDao.search(entityQuery));
                entityQuery.setQueryStr(null);
                teacherCondition.setContent("activity.teacher.id=:teacherId");
                rs.addAll(utilDao.search(entityQuery));
                entityQuery.setQueryStr(null);
                teacherCondition.setContent("activity.examMonitor.invigilator.id=:teacherId");
                rs.addAll(utilDao.search(entityQuery));
                return rs;
            default:
                return Collections.EMPTY_LIST;
        }
    }
    
    /**
     * 检查教学活动是否冲突
     * 
     * @see com.shufe.service.course.arrange.exam.ExamActivityService#isTeacherCollision(java.lang.Long,
     *      java.lang.Long)
     */
    public boolean isTeacherCollision(Long examActivityId, Long teacherId) {
        return teachResourceDAO.isTeacherCollision(examActivityId, teacherId);
    }
    
    public void setTeachResourceDAO(TeachResourceDAO teachResourceDAO) {
        this.teachResourceDAO = teachResourceDAO;
    }
    
    /**
     * 是监考教师的监考次数
     * 
     * @see com.shufe.service.course.arrange.exam.ExamActivityService#invigilationTimes(java.lang.Long,
     *      java.lang.Long)
     */
    public int invigilationTimes(Long invigilatorId, Long calendarId) {
        EntityQuery query = new EntityQuery(ExamActivity.class, "examActivity");
        query.add(new Condition("examActivity.examMonitor.invigilator.id = (:invigilatorId)", invigilatorId));
        query.add(new Condition("examActivity.calendar.id = (:calendarId)", calendarId));
        query.setSelect("count(*)");
        List list = (List) utilService.search(query);
        return Integer.parseInt(list.get(0).toString());
    }
}
