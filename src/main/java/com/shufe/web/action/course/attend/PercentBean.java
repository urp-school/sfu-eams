package com.shufe.web.action.course.attend;

public class PercentBean {
	
	
	/**
	 * 缺勤率
	 */
	private Float absencePer;
	
	/**
	 * 迟到率
	 */
	private Float latPer;
	
	/**
	 * 总出勤率
	 */
	private Float totalPer;

	public PercentBean(Float absencePer, Float latPer, Float totalPer) {
		super();
		this.absencePer = absencePer;
		this.latPer = latPer;
		this.totalPer = totalPer;
	}

	public Float getAbsencePer() {
		return absencePer;
	}

	public void setAbsencePer(Float absencePer) {
		this.absencePer = absencePer;
	}

	public Float getLatPer() {
		return latPer;
	}

	public void setLatPer(Float latPer) {
		this.latPer = latPer;
	}

	public Float getTotalPer() {
		return totalPer;
	}

	public void setTotalPer(Float totalPer) {
		this.totalPer = totalPer;
	}

	
	
	
	
	
}
