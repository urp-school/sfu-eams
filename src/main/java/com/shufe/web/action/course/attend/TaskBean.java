package com.shufe.web.action.course.attend;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.shufe.model.course.task.TeachTask;

public class TaskBean {
	private TeachTask task;
	
	private List<MonthBean> monthBeans=new ArrayList<MonthBean>();
	
	private PercentBean percentBean;
	
	public PercentBean getPercentBean() {
		return percentBean;
	}

	public void setPercentBean(PercentBean percentBean) {
		this.percentBean = percentBean;
	}

	public TeachTask getTask() {
		return task;
	}

	public void setTask(TeachTask task) {
		this.task = task;
	}

	public List<MonthBean> getMonthBeans() {
		return monthBeans;
	}

	public void setMonthBeans(List<MonthBean> monthBeans) {
		this.monthBeans = monthBeans;
	}
	
	
}
