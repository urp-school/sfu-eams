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
package com.shufe.service.course.arrange.exam;

import java.util.Collection;
import java.util.List;

import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.dao.course.arrange.exam.ExamTakeDAO;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

public interface ExamTakeService {

	/**
	 * 冲突学生名单
	 * 
	 * @return
	 */
	public Collection collisionStds(TeachCalendar calendar, ExamType examType);

	/**
	 * 查询学生是否可以申请缓考
	 * 
	 * @param std
	 * @param take
	 * @return
	 */
	public boolean canApplyDelayExam(Student std, ExamTake take);

	/**
	 * 统计参加某类考试的需要的最小场次以及人数
	 * 
	 * @param calendar
	 * @param examType
	 * @return［turn,takeCount］返回结果按照turn进行升序排列
	 */
	public List statTakeCountWithTurn(TeachCalendar calendar, ExamType examType);

	/**
	 * 统计在某个学期，参加各门课程的人数(指定考试性质)
	 * 
	 * @param calendar
	 * @param examType
	 * @return
	 */
	public List statTakeCountInCourse(TeachCalendar calendar, ExamType examType);
	/**
	 * 学生是否参加了某类考试类型
	 * @param std
	 * @param calendar
	 * @param task
	 * @param examType
	 * @return
	 */
	public boolean isTakeExam(Student std, TeachCalendar calendar,
			TeachTask task, ExamType examType);

	public void setExamTakeDAO(ExamTakeDAO examTakeDAO);
    /**
     * 根据应考学生得到座位号
     * @param exam
     * @return
     */
    public Integer getSeatNum(ExamTake exam);
}
