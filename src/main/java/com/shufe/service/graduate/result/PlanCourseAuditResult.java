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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.service.graduate.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.system.baseinfo.Course;

/**
 * 培养计划课程审核结果
 */
public class PlanCourseAuditResult {

	public PlanCourseAuditResult() {
		super();
	}

	public PlanCourseAuditResult(PlanCourse planCourse) {
		this.terms = planCourse.getTerms().toString();
		this.course = planCourse.getCourse();
		this.creditAuditInfo.setRequired(planCourse.getCourse().getCredits());
	}

	/** 学分审核结果 */
	private CreditAuditInfo creditAuditInfo = new CreditAuditInfo();

	/** 开课的学期 */
	private String terms;

	/** 课程，对应<code>PlanCourse</code>中的course */
	private Course course;

	/** 成绩 */
	private List scores = new ArrayList();

	/** 重修次数 */
	private Integer reStudyCount;

	/** 替代课程的成绩 */
	private List substitionScores = new ArrayList(0);

	/** 备注 */
	private String remark;

	/**
	 * @return 返回 course.
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @param course
	 *            要设置的 course.
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * @return 返回 creditAuditInfo.
	 */
	public CreditAuditInfo getCreditAuditInfo() {
		return creditAuditInfo;
	}

	/**
	 * @param creditAuditInfo
	 *            要设置的 creditAuditInfo.
	 */
	public void setCreditAuditInfo(CreditAuditInfo creditAuditInfo) {
		this.creditAuditInfo = creditAuditInfo;
	}

	/**
	 * @return 返回 scores.
	 */
	public List getScores() {
		return scores;
	}

	/**
	 * @param scores
	 *            要设置的 scores.
	 */
	public void setScores(List scores) {
		this.scores = scores;
	}

	/**
	 * @return 返回 substitionScores.
	 */
	public List getSubstitionScores() {
		return substitionScores;
	}

	/**
	 * @param substitionScores 要设置的 substitionScores.
	 */
	public void setSubstitionScores(List substitionScores) {
		this.substitionScores = substitionScores;
	}

	/**
	 * @return 返回 reStudyCount.
	 */
	public Integer getReStudyCount() {
		if(CollectionUtils.isEmpty(scores)){
			reStudyCount = null;
		}else{
			/*if(creditAuditInfo.getIsPass().equals(Boolean.TRUE)){
				reStudyCount = new Integer(scores.size()-1);
			}else{
				reStudyCount = new Integer(scores.size()-1);
			}*/
			/*TODO 确定在成绩模块调整后，计算重修次数是否还是这样*/
			reStudyCount = new Integer(scores.size()-1);
		}
		return reStudyCount;
	}
	

	/**
	 * @return 返回 terms.
	 */
	public String getTerms() {
		return terms;
	}

	/**
	 * @param terms
	 *            要设置的 terms.
	 */
	public void setTerms(String terms) {
		this.terms = terms;
	}

	/**
	 * @return 返回 remark.
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            要设置的 remark.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 加入该课程的成绩列表
	 * @param gceList
	 * @return 是否通过
	 */
	public boolean setGradeList(List gceList) {
		scores = new ArrayList();
		if(CollectionUtils.isNotEmpty(gceList)){
			this.scores.addAll(gceList);
			boolean flag = false;
			for (Iterator iter = gceList.iterator(); iter.hasNext();) {
				CourseGrade gce = (CourseGrade) iter.next();
				if(Boolean.TRUE.equals(gce.getIsPass())){
					flag = true;
					break;
				}
			}
			if(flag){
				creditAuditInfo.setCompleted(creditAuditInfo.getRequired());
			}else{
				creditAuditInfo.setCompleted(new Float(0));
			}
			return flag;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取按成绩排序的<code>CourseGrade</code>的<code>List</code>
	 * @return
	 */
	public List getScoresOrderByScore(final Boolean isASC){
		Collections.sort(scores, new Comparator(){
			public int compare(Object o1, Object o2) {
				Float g1 = ((CourseGrade)o1).getScore();
				Float g2 = ((CourseGrade)o2).getScore();
				if(Boolean.FALSE.equals(isASC)){
					return g1==null?1:(g2==null?-1:(g2.compareTo(g1)));
				}else{
					return g1==null?-1:(g2==null?1:(g1.compareTo(g2)));
				}				
			}});
		return scores;
	}
	
}
