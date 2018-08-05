package com.shufe.web.action.course.attend;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;

public class ClassBean {
	private AdminClass adminClass;
	
	private List<MonthBean> monthBeans=new ArrayList<MonthBean>();
	
	private PercentBean percentBean;
	
	public PercentBean getPercentBean() {
		return percentBean;
	}

	public void setPercentBean(PercentBean percentBean) {
		this.percentBean = percentBean;
	}


	public AdminClass getAdminClass() {
		return adminClass;
	}

	public void setAdminClass(AdminClass adminClass) {
		this.adminClass = adminClass;
	}

	public List<MonthBean> getMonthBeans() {
		return monthBeans;
	}

	public void setMonthBeans(List<MonthBean> monthBeans) {
		this.monthBeans = monthBeans;
	}
	
	
}
