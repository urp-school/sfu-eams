//$Id: FormalAnswer.java,v 1.1 2006/12/18 11:00:56 cwx Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-12-18         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.thesis.answer;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.ThesisStore;
import com.shufe.model.std.Student;

public class FormalAnswer extends ThesisStore {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7248832056484518949L;
	private Set answerTeamSet = new HashSet(); //答辩小组成员
	private Date time;       //正式答辩时间
	private String address; //正式答辩地点
	private Float formelMark; //答辩成绩
	private Boolean isPassed; //是否通过
	private ThesisManage thesisManage; //论文对象
	private Student student = new Student(); // 学生
	
	/**
	 * @return Returns the student.
	 */
	public Student getStudent() {
		return student;
	}
	/**
	 * @param student The student to set.
	 */
	public void setStudent(Student student) {
		this.student = student;
	}
	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return Returns the answerTeamSet.
	 */
	public Set getAnswerTeamSet() {
		return answerTeamSet;
	}
	/**
	 * @param answerTeamSet The answerTeamSet to set.
	 */
	public void setAnswerTeamSet(Set answerTeamSet) {
		this.answerTeamSet = answerTeamSet;
	}
	/**
	 * @return Returns the formelMark.
	 */
	public Float getFormelMark() {
		return formelMark;
	}
	/**
	 * @param formelMark The formelMark to set.
	 */
	public void setFormelMark(Float formelMark) {
		this.formelMark = formelMark;
	}
	/**
	 * @return Returns the isPassed.
	 */
	public Boolean getIsPassed() {
		return isPassed;
	}
	/**
	 * @param isPassed The isPassed to set.
	 */
	public void setIsPassed(Boolean isPassed) {
		this.isPassed = isPassed;
	}
	/**
	 * @return Returns the thesisManage.
	 */
	public ThesisManage getThesisManage() {
		return thesisManage;
	}
	/**
	 * @param thesisManage The thesisManage to set.
	 */
	public void setThesisManage(ThesisManage thesisManage) {
		this.thesisManage = thesisManage;
	}
	/**
	 * @return Returns the time.
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * @param time The time to set.
	 */
	public void setTime(Date time) {
		this.time = time;
	}
}
