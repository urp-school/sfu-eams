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
 * chaostone             2006-11-12            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.exam;

import java.util.Collection;

import com.ekingstar.eams.system.baseinfo.StudentType;

public interface ExamTurnService {

	/**
	 * 查找学生类别适用的考试场次<br>
	 * 如果没有直接对应的场次,则依次查找上级类别的设置.<br>
	 * 如果最后仍没有找到,则使用系统默认的场次安排.<br>
	 * 
	 * 返回结果以开始时间进行排序<br>
	 * 如果学生类别为null,返回系统默认的场次安排
	 * @param stdType
	 * @return
	 */
	Collection getExamTurns(StudentType stdType);

}
