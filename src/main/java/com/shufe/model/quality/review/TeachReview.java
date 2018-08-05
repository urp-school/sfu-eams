package com.shufe.model.quality.review;

import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.baseinfo.Department;

public class TeachReview extends LongIdObject{
	
	private static final long serialVersionUID = 5499484907391716343L;
	private Department department;
	private Date date;
	private Boolean instituteConfirm;
	private Boolean teachadminConfirm;
	private ReviewDetail selfCheckDetail;
	private ReviewDetail checkDetail;
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public ReviewDetail getCheckDetail() {
		return checkDetail;
	}
	public void setCheckDetail(ReviewDetail checkDetail) {
		this.checkDetail = checkDetail;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Boolean getInstituteConfirm() {
		return instituteConfirm;
	}
	public void setInstituteConfirm(Boolean instituteConfirm) {
		this.instituteConfirm = instituteConfirm;
	}
	public ReviewDetail getSelfCheckDetail() {
		return selfCheckDetail;
	}
	public void setSelfCheckDetail(ReviewDetail selfCheckDetail) {
		this.selfCheckDetail = selfCheckDetail;
	}
	public Boolean getTeachadminConfirm() {
		return teachadminConfirm;
	}
	public void setTeachadminConfirm(Boolean teachadminConfirm) {
		this.teachadminConfirm = teachadminConfirm;
	}
}
