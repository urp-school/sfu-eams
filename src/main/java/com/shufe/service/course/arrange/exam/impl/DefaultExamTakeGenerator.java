//$Id: DefaultExamTakeGenerator.java,v 1.1 2007-7-28 下午05:37:20 chaostone Exp $
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
 * chenweixiong              2007-7-28         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.exam.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.ekingstar.commons.collection.transformers.PropertyTransformer;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.task.TeachTask;
import com.shufe.service.course.arrange.exam.ExamTakeGenerator;

/**
 * 缺省的应考学生生成器<br>
 * 默认除期末考试外的其他考试是直接从数据库中读取。
 * 
 * @author chaostone
 */
public class DefaultExamTakeGenerator implements ExamTakeGenerator {
    
    private UtilDao utilDao;
    
    public DefaultExamTakeGenerator(UtilDao utilDao) {
        this.utilDao = utilDao;
    }
    
    public List generate(TeachTask task, ExamType examType) {
        if (examType.getId().equals(ExamType.FINAL)) {
            List examTakes = new ArrayList();
            for (Iterator iterator = task.getTeachClass().getCourseTakes().iterator(); iterator
                    .hasNext();) {
                CourseTake courseTake = (CourseTake) iterator.next();
                ExamTake take = new ExamTake(courseTake);
                take.setExamType(examType);
                // FIXME 加上了教学日历
                take.setCalendar(task.getCalendar());
                examTakes.add(take);
                task.getTeachClass().addExamTake(take);
            }
            return examTakes;
        } else {
            EntityQuery query = new EntityQuery(ExamTake.class, "take");
            query.add(new Condition("take.courseTake.task.id=:taskId", task.getId()));
            List examTypes = new ArrayList();
            examTypes.add(examType.getId());
            if (examType.getId().equals(ExamType.DELAY_AGAIN)) {
                examTypes.add(ExamType.AGAIN);
                examTypes.add(ExamType.DELAY);
            }
            query.add(new Condition("take.examType.id in(:examTypeIds)", examTypes));
            return (List) utilDao.search(query);
        }
    }
    
    public int getTakeCount(TeachTask task, ExamType examType) {
        if (examType.getId().equals(ExamType.FINAL)) {
            return task.getTeachClass().getCourseTakes().size();
        } else {
            EntityQuery query = new EntityQuery(ExamTake.class, "take");
            query.add(new Condition("take.courseTake.task.id=:taskId", task.getId()));
            List examTypes = new ArrayList();
            examTypes.add(examType.getId());
            if (examType.getId().equals(ExamType.DELAY_AGAIN)) {
                examTypes.add(ExamType.AGAIN);
                examTypes.add(ExamType.DELAY);
            }
            query.add(new Condition("take.examType.id in(:examTypeIds)", examTypes));
            query.setSelect("count(*)");
            List rs = (List) utilDao.search(query);
            return ((Number) rs.get(0)).intValue();
        }
    }
    
    /**
     * 得到参加考试的学生
     */
    public Collection getTakeStdIds(TeachTask task, ExamType examType) {
        if (examType.getId().equals(ExamType.FINAL)) {
            return CollectionUtils.collect(task.getTeachClass().getCourseTakes(),
                    new PropertyTransformer("student.id"));
        } else {
            EntityQuery query = new EntityQuery(ExamTake.class, "take");
            query.add(new Condition("take.courseTake.task.id=:taskId", task.getId()));
            List examTypes = new ArrayList();
            examTypes.add(examType.getId());
            if (examType.getId().equals(ExamType.DELAY_AGAIN)) {
                examTypes.add(ExamType.AGAIN);
                examTypes.add(ExamType.DELAY);
            }
            query.add(new Condition("take.examType.id in(:examTypeIds)", examTypes));
            query.setSelect("take.std.id");
            return utilDao.search(query);
            
        }
    }
    
    public UtilDao getUtilDao() {
        return utilDao;
    }
    
    public void setUtilDao(UtilDao utilDao) {
        this.utilDao = utilDao;
    }
    
}
