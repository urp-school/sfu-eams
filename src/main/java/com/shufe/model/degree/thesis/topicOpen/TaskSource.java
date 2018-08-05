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
 * 塞外狂人             2006-8-8            Created
 *  
 ********************************************************************************/
package com.shufe.model.degree.thesis.topicOpen;

import com.ekingstar.commons.model.Component;
import com.ekingstar.eams.system.basecode.industry.TaskLevel;
import com.ekingstar.eams.system.basecode.industry.ThesisTopicSource;
/**
 * 课题来源
 * @author 塞外狂人
 *
 */
public class TaskSource implements Component{

	private TaskLevel taskLevel = new TaskLevel();//课题级别
	private ThesisTopicSource thesisSourse = new ThesisTopicSource(); //选题来源
	private String projectTaskName;//项目、课题名称
	private String outlaySource;//经费来源
	private String passCompany;//批准单位
	private Float money;//资助金额
	
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public String getOutlaySource() {
		return outlaySource;
	}
	public void setOutlaySource(String outlaySource) {
		this.outlaySource = outlaySource;
	}
	public String getPassCompany() {
		return passCompany;
	}
	public void setPassCompany(String passCompany) {
		this.passCompany = passCompany;
	}
	public String getProjectTaskName() {
		return projectTaskName;
	}
	public void setProjectTaskName(String projectTaskName) {
		this.projectTaskName = projectTaskName;
	}
	public TaskLevel getTaskLevel() {
		return taskLevel;
	}
	public void setTaskLevel(TaskLevel taskLevel) {
		this.taskLevel = taskLevel;
	}
	/**
	 * @return Returns the thesisSourse.
	 */
	public ThesisTopicSource getThesisSourse() {
		return thesisSourse;
	}
	/**
	 * @param thesisSourse The thesisSourse to set.
	 */
	public void setThesisSourse(ThesisTopicSource thesisSourse) {
		this.thesisSourse = thesisSourse;
	}

}

