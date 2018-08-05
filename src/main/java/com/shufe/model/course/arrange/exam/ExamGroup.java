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
 * chaostone             2006-11-9            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange.exam;

import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.ExamType;

/**
 * 排考组
 * 
 * @author chaostone
 * 
 */
public class ExamGroup extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6358621864872851858L;

	/** 课程组名称 */
	private String name;

	/** 考试类别 */
	private ExamType examType = new ExamType();

	/** 组内任务 */
	private Set tasks = new HashSet();

	/** 考试场次 */
	private Set examTurns = new HashSet();

	/** 组内预定的教室 */
	private Set rooms = new HashSet();

	/** 排考结果是否发布 */
	public Boolean isPublish;

	public Set getExamTurns() {
		return examTurns;
	}

	public void setExamTurns(Set examTurns) {
		this.examTurns = examTurns;
	}

	public ExamType getExamType() {
		return examType;
	}

	public void setExamType(ExamType examType) {
		this.examType = examType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getRooms() {
		return rooms;
	}

	public void setRooms(Set rooms) {
		this.rooms = rooms;
	}

	public Set getTasks() {
		return tasks;
	}

	public void setTasks(Set tasks) {
		this.tasks = tasks;
	}

	public Boolean getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}

}
