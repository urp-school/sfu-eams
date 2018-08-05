//$Id: StudentSignUp.java,v 1.1 2007-5-4 下午03:21:39 chaostone Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-5-4         Created
 *  
 ********************************************************************************/

package com.shufe.model.std.speciality2nd;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 学生报名数据和录取数据
 * 
 * @author chaostone
 * 
 */
public class SignUpStudent extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7895451460672858229L;

	/** 对应学生 */
	private Student std;

	/** 学生绩点 */
	private Float GPA;

	/** 根据志愿级差实际录取的绩点 */
	private Float matriculateGPA;

	/** 报名时间 */
	private Timestamp signUpAt;

	/** 报名学期 */
	private TeachCalendar calendar;

	/** 是否可以调剂 */
	private Boolean isAdjustable;

	/** 报名记录 */
	private Set records = new HashSet();

	/** 报名设置 */
	private SignUpSetting setting = new SignUpSetting();

	/** 录取志愿级别 */
	private Integer rank;

	/** 录取的专业 */
	private SignUpSpecialitySetting matriculated;

	/** 是否调剂录取 */
	private Boolean isAdjustMatriculated;

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public Float getGPA() {
		return GPA;
	}

	public void setGPA(Float gpa) {
		GPA = gpa;
	}

	public Boolean getIsAdjustable() {
		return isAdjustable;
	}

	public void setIsAdjustable(Boolean isAdjustable) {
		this.isAdjustable = isAdjustable;
	}

	public Timestamp getSignUpAt() {
		return signUpAt;
	}

	public void setSignUpAt(Timestamp signUpAt) {
		this.signUpAt = signUpAt;
	}

	public Set getRecords() {
		return records;
	}

	public void setRecords(Set signUpRecords) {
		this.records = signUpRecords;
	}

	public Student getStd() {
		return std;
	}

	public void setStd(Student std) {
		this.std = std;
	}

	public SignUpSetting getSetting() {
		return setting;
	}

	public void setSetting(SignUpSetting setting) {
		this.setting = setting;
	}

	public void addSignUpRecord(SignUpStudentRecord record) {
		record.setSignUpStd(this);
		getRecords().add(record);
	}

	public Float getMatriculateGPA() {
		return matriculateGPA;
	}

	public void setMatriculateGPA(Float matriculateGPA) {
		this.matriculateGPA = matriculateGPA;
	}

	public Boolean getIsMatriculated() {
		return Boolean.valueOf(null != matriculated);
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public SignUpSpecialitySetting getMatriculated() {
		return matriculated;
	}

	public void setMatriculated(SignUpSpecialitySetting matriculated) {
		this.matriculated = matriculated;
	}

	public SignUpStudentRecord getRecord(int rank) {
		for (Iterator iter = getRecords().iterator(); iter.hasNext();) {
			SignUpStudentRecord record = (SignUpStudentRecord) iter.next();
			if (record.getRank().intValue() == rank)
				return record;
		}
		return null;
	}

	/**
	 * 取消录取
	 * 
	 */
	public void cancelMatriculate() {
		setMatriculated(null);
		setMatriculateGPA(getGPA());
		setRank(null);
		setIsAdjustMatriculated(null);
		for (Iterator iter = getRecords().iterator(); iter.hasNext();) {
			SignUpStudentRecord record = (SignUpStudentRecord) iter.next();
			record.setStatus(Boolean.FALSE);
		}
		getStd().setSecondMajor(null);
		getStd().setSecondAspect(null);
		getStd().setIsSecondMajorStudy(null);
	}

	/**
	 * 录取特定专业
	 * 
	 * @param setting
	 */
	public void matriculate(SignUpSpecialitySetting setting) {
		setMatriculated(setting);
		getStd().setSecondMajor(setting.getSpeciality());
		getStd().setSecondAspect(setting.getAspect());
		getStd().setIsSecondMajorStudy(Boolean.TRUE);

		SignUpStudentRecord record = null;
		for (Iterator iter = getRecords().iterator(); iter.hasNext();) {
			SignUpStudentRecord r = (SignUpStudentRecord) iter.next();
			if (r.getSpecialitySetting().getId().equals(setting.getId())) {
				record = r;
			}
			r.setStatus(Boolean.FALSE);
		}
		if (null != record) {
			record.setStatus(Boolean.TRUE);
			setRank(record.getRank());
			setIsAdjustMatriculated(Boolean.FALSE);
		} else {
			setRank(null);
			setIsAdjustMatriculated(Boolean.TRUE);
		}
	}

	public Boolean getIsAdjustMatriculated() {
		return isAdjustMatriculated;
	}

	public void setIsAdjustMatriculated(Boolean isAdjustMatriculated) {
		this.isAdjustMatriculated = isAdjustMatriculated;
	}

}
