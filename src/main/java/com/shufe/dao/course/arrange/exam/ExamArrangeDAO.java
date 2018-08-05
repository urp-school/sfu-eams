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
 * chaostone             2006-11-7            Created
 *  
 ********************************************************************************/
package com.shufe.dao.course.arrange.exam;

import java.util.List;

import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.dao.BasicDAO;

public interface ExamArrangeDAO extends BasicDAO {

	/**
	 * 删除指定考试类型的考试安排<br>
	 * 1,删除学生名单中的具体安排(如果不是补考或缓考)<br>
	 * 2,删除教学活动中的教学安排
	 * 
	 * @param tasks
	 * @param examType
	 */
	public void removeExamArranges(List tasks, ExamType examType);
}
