//$Id: ExamTakeGenerator.java,v 1.1 2007-7-28 下午04:42:00 chaostone Exp $
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

package com.shufe.service.course.arrange.exam;

import java.util.Collection;
import java.util.List;

import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.model.course.task.TeachTask;

/**
 * 应考学生生成器
 * 
 * @author chaostone
 * 
 */
public interface ExamTakeGenerator {

	/**
	 * 产生应考学生记录
	 * 
	 * @param task
	 * @param examType
	 * @return
	 */
	public List generate(TeachTask task, ExamType examType);

	/**
	 * 查找教学任务中对应考试类型的应考人数
	 * 
	 * @param task
	 * @param examType
	 * @return
	 */
	public int getTakeCount(TeachTask task, ExamType examType);
	
	/**
	 * 查找可以考试的学生
	 * @param task
	 * @param examType
	 * @return
	 */
	public Collection getTakeStdIds(TeachTask task, ExamType examType);
}
