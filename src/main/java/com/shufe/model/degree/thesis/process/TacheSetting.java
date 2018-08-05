//$Id: TraceSetting.java,v 1.1 2007-3-8 13:39:33 Administrator Exp $
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
 * chenweixiong              2007-3-8         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.thesis.process;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.Tache;
import com.shufe.model.system.file.DegreeDocument;

public class TacheSetting extends LongIdObject implements Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 21106754122214164L;
	private Tache tache = new Tache();  //论文环节
	private Date planedTimeOn = new Date(); //计划完成时间
	private Boolean isTutorAffirm; // 是否导师确认
	private Schedule schedule = new Schedule(); // 计划进度
	private String settingRemark; //环节设置备注
	private Set thesisDocuments = new HashSet();
	private Set thesisModels = new HashSet();
	
	
	/**
	 * @return Returns the isTutorAffirm.
	 */
	public Boolean getIsTutorAffirm() {
		return isTutorAffirm;
	}
	/**
	 * @param isTutorAffirm The isTutorAffirm to set.
	 */
	public void setIsTutorAffirm(Boolean isTutorAffirm) {
		this.isTutorAffirm = isTutorAffirm;
	}
	
	/**
	 * @return Returns the planedTimeOn.
	 */
	public Date getPlanedTimeOn() {
		return planedTimeOn;
	}
	/**
	 * @param planedTimeOn The planedTimeOn to set.
	 */
	public void setPlanedTimeOn(Date planedTimeOn) {
		this.planedTimeOn = planedTimeOn;
	}
	/**
	 * @return Returns the settingRemark.
	 */
	public String getSettingRemark() {
		return settingRemark;
	}
	/**
	 * @param settingRemark The settingRemark to set.
	 */
	public void setSettingRemark(String settingRemark) {
		this.settingRemark = settingRemark;
	}
	/**
	 * @return Returns the tache.
	 */
	public Tache getTache() {
		return tache;
	}
	/**
	 * @param tache The tache to set.
	 */
	public void setTache(Tache tache) {
		this.tache = tache;
	}
	/**
	 * @return Returns the schedule.
	 */
	public Schedule getSchedule() {
		return schedule;
	}
	/**
	 * @param schedule The schedule to set.
	 */
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	public Object clone() throws CloneNotSupportedException {
		TacheSetting tacheSetting = new TacheSetting();
		tacheSetting.setIsTutorAffirm(this.getIsTutorAffirm());
		tacheSetting.setPlanedTimeOn(this.getPlanedTimeOn());
		tacheSetting.setSettingRemark(this.getSettingRemark());
		tacheSetting.setTache(this.getTache());
		Set thesisDocuments = new HashSet();
		for (Iterator iter = this.getThesisDocuments().iterator(); iter
				.hasNext();) {
			DegreeDocument document = (DegreeDocument) iter.next();
			thesisDocuments.add(document);
		}
		tacheSetting.setThesisDocuments(thesisDocuments);
		Set theisModels = new HashSet();
		for (Iterator iter = this.getThesisModels().iterator(); iter.hasNext();) {
			DegreeDocument element = (DegreeDocument) iter.next();
			theisModels.add(element);
		}
		tacheSetting.setThesisModels(theisModels);
		return tacheSetting;
	}
	
	/**
	 * @param object
	 * @return
	 */
	public int compareTo(Object object) {
		TacheSetting myClass = (TacheSetting) object;
		return new CompareToBuilder().append(
				myClass.getSchedule().getEnrollYear(),
				this.getSchedule().getEnrollYear()).append(
				myClass.getSchedule().getStudentType().getName(),
				this.getSchedule().getStudentType().getName()).append(
				this.getSchedule().getStudyLength(),
				myClass.getSchedule().getStudyLength()).append(
				this.getPlanedTimeOn(), myClass.getPlanedTimeOn()).toComparison();
	}
	/**
	 * @return Returns the thesisDocuments.
	 */
	public Set getThesisDocuments() {
		return thesisDocuments;
	}
	/**
	 * @param thesisDocuments The thesisDocuments to set.
	 */
	public void setThesisDocuments(Set thesisDocuments) {
		this.thesisDocuments = thesisDocuments;
	}
	/**
	 * @return Returns the thesisModels.
	 */
	public Set getThesisModels() {
		return thesisModels;
	}
	/**
	 * @param thesisModels The thesisModels to set.
	 */
	public void setThesisModels(Set thesisModels) {
		this.thesisModels = thesisModels;
	}
	
}
