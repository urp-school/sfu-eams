package com.shufe.web.action.course.attend;

public class DateBean {

	/**
	 * 日期
	 */
  private	String date;
  
  /**
   * 出勤
   */
  private Float normal;
  
  /**
	 * 缺勤
	 */
	private Integer absenceCount;
	
	/**
	 * 迟到
	 */
	private Integer latCounet;
	
	/**
	 * 课时
	 */
	private Integer ksCount;
	
	
	
	
	

public Integer getKsCount() {
		return ksCount;
	}

	public void setKsCount(Integer ksCount) {
		this.ksCount = ksCount;
	}

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}

public Float getNormal() {
	return normal;
}

public void setNormal(Float normal) {
	this.normal = normal;
}

public DateBean(String date, Float normal) {
	super();
	this.date = date;
	this.normal = normal;
}



public DateBean(String date, Integer absenceCount, Integer latCounet) {
	super();
	this.date = date;
	this.absenceCount = absenceCount;
	this.latCounet = latCounet;
}



public DateBean(String date, Float normal, Integer absenceCount,
		Integer latCounet, Integer ksCount) {
	super();
	this.date = date;
	this.normal = normal;
	this.absenceCount = absenceCount;
	this.latCounet = latCounet;
	this.ksCount = ksCount;
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

public DateBean() {
	super();
	// TODO Auto-generated constructor stub
}
  
	
}
