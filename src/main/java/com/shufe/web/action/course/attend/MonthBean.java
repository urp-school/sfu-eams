package com.shufe.web.action.course.attend;

public class MonthBean {
	
	/**
	 * 月份
	 */
	private Integer month;
	
	/**
	 * 缺勤
	 */
	private Integer absenceCount;
	
	/**
	 * 迟到
	 */
	private Integer latCounet;
	
	/**
	 * 总出勤数
	 */
	private Integer totalDuty;
	
	
	
	public MonthBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MonthBean(Integer month, Integer absenceCount, Integer latCounet,Integer totalDuty) {
		this.month = month;
		this.absenceCount = absenceCount;
		this.latCounet = latCounet;
		this.totalDuty=totalDuty;
		
	}
	
	
	public Integer getTotalDuty() {
		return totalDuty;
	}
	public void setTotalDuty(Integer totalDuty) {
		this.totalDuty = totalDuty;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getAbsenceCount() {
		return absenceCount;
	}
	public void setAbsenceCount(Integer absenceCount) {
		this.absenceCount = absenceCount;
	}
	public Integer getLatCounet() {
		return latCounet;
	}
	public void setLatCounet(Integer latCounet) {
		this.latCounet = latCounet;
	}
	
	
	
}
