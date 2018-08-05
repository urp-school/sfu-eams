package com.shufe.web.action.course.attend;

import java.util.ArrayList;
import java.util.List;

import com.ekingstar.eams.system.baseinfo.Department;

public class DepartmentBean {
	
	private Department department;
	
	private List<DateBean> dateBeans=new ArrayList<DateBean>();
	
	private Float totalNormal;




	public DepartmentBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DepartmentBean(Department department, List<DateBean> dateBeans,
			Float totalNormal) {
		super();
		this.department = department;
		this.dateBeans = dateBeans;
		this.totalNormal = totalNormal;
	}

	public Float getTotalNormal() {
		return totalNormal;
	}

	public void setTotalNormal(Float totalNormal) {
		this.totalNormal = totalNormal;
	}
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<DateBean> getDateBeans() {
		return dateBeans;
	}

	public void setDateBeans(List<DateBean> dateBeans) {
		this.dateBeans = dateBeans;
	}
	
	
	
	
}
