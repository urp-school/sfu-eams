package com.shufe.web.action.course.attend;

import java.util.ArrayList;
import java.util.List;

import com.ekingstar.eams.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.AdminClass;

public class AdminClassBean {
	
	private AdminClass adminClass;
	
	private List<DateBean> dateBeans=new ArrayList<DateBean>();
	

	 /**
		 * 缺勤
		 */
		private Integer absenceTotal;
		
		/**
		 * 迟到
		 */
		private Integer lateTotal;

		/**
		 * 总课时
		 */
		private Integer ksTotal;

		
	public Integer getKsTotal() {
			return ksTotal;
		}


		public void setKsTotal(Integer ksTotal) {
			this.ksTotal = ksTotal;
		}


	public AdminClassBean() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AdminClassBean(AdminClass adminClass, List<DateBean> dateBeans,
			 Integer absenceTotal, Integer lateTotal) {
		super();
		this.adminClass = adminClass;
		this.dateBeans = dateBeans;
		this.absenceTotal = absenceTotal;
		this.lateTotal = lateTotal;
	}


	public AdminClassBean(AdminClass adminClass, List<DateBean> dateBeans,
			Integer absenceTotal, Integer lateTotal, Integer ksTotal) {
		super();
		this.adminClass = adminClass;
		this.dateBeans = dateBeans;
		this.absenceTotal = absenceTotal;
		this.lateTotal = lateTotal;
		this.ksTotal = ksTotal;
	}


	public AdminClass getAdminClass() {
		return adminClass;
	}


	public void setAdminClass(AdminClass adminClass) {
		this.adminClass = adminClass;
	}


	public List<DateBean> getDateBeans() {
		return dateBeans;
	}


	public void setDateBeans(List<DateBean> dateBeans) {
		this.dateBeans = dateBeans;
	}



	public Integer getAbsenceTotal() {
		return absenceTotal;
	}


	public void setAbsenceTotal(Integer absenceTotal) {
		this.absenceTotal = absenceTotal;
	}


	public Integer getLateTotal() {
		return lateTotal;
	}


	public void setLateTotal(Integer lateTotal) {
		this.lateTotal = lateTotal;
	}

	
	
	
}
