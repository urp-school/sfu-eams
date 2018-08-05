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
 * chaostone             2006-12-13            Created
 *  
 ********************************************************************************/
package com.shufe.dao.course.grade;

import java.util.List;
import java.util.Map;

import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.security.User;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;

/**
 * 成绩存取类
 * 
 * @author chaostone
 * 
 */
public interface GradeDAO {
	/**
	 * 判断是否需要进行重修
	 * 
	 * @param std
	 * @param course
	 * @return
	 */
	public boolean needReStudy(Student std, Course course);
    
    /**
     * 判断是否已经修读通过
     * 
     * @param std
     * @param course
     * @return
     */
    public boolean isPass(Student std, Course course);

	/**
	 * 查询学生所有的最终成绩 的课程id和是否通过
	 * 
	 * @param std
	 * @return
	 */
	public Map getGradeCourseMap(Long stdId);

	/**
	 * 删除考试成绩
	 * 
	 * @param task
	 * @param gradeType
	 * @param user TODO
	 */
	public void removeExamGrades(TeachTask task, GradeType gradeType, User user);

	/**
	 * 发布课程成绩
	 * 
	 * @param task
	 * @param isPublished
	 */
	public void publishCourseGrade(TeachTask task, Boolean isPublished);

	/**
	 * 发布考试成绩
	 * 
	 * @param task
	 * @param gradeType
	 * @param isPublished
	 */
	public void publishExamGrade(TeachTask task, GradeType gradeType, Boolean isPublished);

	/**
	 * 发布考试成绩
	 * 
	 * @param task
	 * @param gradeTypes
	 * @param isPublished
	 */
	public void publishExamGrade(TeachTask task, List gradeTypes, Boolean isPublished);

	/**
	 * 删除所有任务相关的成绩
	 * 
	 * @param task
	 * @param user TODO
	 */
	public void removeGrades(TeachTask task, User user);
	
	public Long getGradeMaxCalendarId(Student std);
}
