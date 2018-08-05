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

import java.util.List;

import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.dao.course.arrange.exam.ExamArrangeDAO;
import com.shufe.dao.course.arrange.resource.TeachResourceDAO;
import com.shufe.model.course.arrange.exam.ExamParams;
import com.shufe.web.action.course.arrange.exam.ExamArrangeWebObserver;

/**
 * 排考活动服务接口
 * 
 * @author chaostone
 * 
 */

public interface ExamArrangeService {

	/**
	 * 考试安排
	 * 
	 * @param tasks
	 * @param params
	 */
	public void arrange(List tasks, ExamParams params);

	public void arrange(List tasks, ExamParams params, ExamArrangeWebObserver observer);

	public void setTeachResourceDAO(TeachResourceDAO teachResourceDAO);

	public void setExamArrangeDAO(ExamArrangeDAO examArrangeDAO);

	/**
	 * 删除指定考试类型的考试安排<br>
	 * 1,删除学生名单中的具体安排(如果不是补考或缓考)<br>
	 * 2,删除教学活动中的教学安排
	 * 
	 * @param tasks
	 * @param examType
	 */
	public void removeExamArranges(List tasks, ExamType examType);

	/**
	 * 为没有分配教室的考务活动分配教室<br>
	 * 1)对教学任务不排序，但是对教室会排序后顺序使用<br>
	 * 2)对已分配的教学任务不作分配处理
	 * 
	 * @param tasks
	 * @param examType
	 * @param classrooms
	 * @param canCombineTask
	 *            是否允许多个教学任务在同一个教室中考试
	 * @param sameDistrictWithCourse
	 *            是否按照教学任务的所在校区进行安排教室
	 */
	public boolean allocateRooms(List tasks, ExamType examType, List classrooms,
			Boolean canCombineTask, Boolean sameDistrictWithCourse);
}
