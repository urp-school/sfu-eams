package com.shufe.model.std;

import com.ekingstar.commons.model.pojo.LongIdObject;

public class PlanAuditResult extends LongIdObject{

	Student std;
	
	Boolean graduateAuditStatus;
	
	Boolean secondGraduateAuditStatus;
	
	
	public Student getStd() {
		return std;
	}
	public void setStd(Student std) {
		this.std = std;
	}
	public Boolean getGraduateAuditStatus() {
		return graduateAuditStatus;
	}
	public void setGraduateAuditStatus(Boolean graduateAuditStatus) {
		this.graduateAuditStatus = graduateAuditStatus;
	}
	public Boolean getSecondGraduateAuditStatus() {
		return secondGraduateAuditStatus;
	}
	public void setSecondGraduateAuditStatus(Boolean secondGraduateAuditStatus) {
		this.secondGraduateAuditStatus = secondGraduateAuditStatus;
	}
	
	
}
