package com.shufe.web.action.course.attend;

import java.util.ArrayList;
import java.util.List;

public class StuTaskBean {
	
	private Long stuId;
	
	private List<TaskBean> taskBeans=new ArrayList<TaskBean>();

	public Long getStuId() {
		return stuId;
	}

	public void setStuId(Long stuId) {
		this.stuId = stuId;
	}

	public List<TaskBean> getTaskBeans() {
		return taskBeans;
	}

	public void setTaskBeans(List<TaskBean> taskBeans) {
		this.taskBeans = taskBeans;
	}

	public StuTaskBean(Long stuId, List<TaskBean> taskBeans) {
		this.stuId = stuId;
		this.taskBeans = taskBeans;
	}

	public StuTaskBean() {
	}

	
	
}
