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

import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.std.Student;

/**
 * 培养计划审核结果
 */
public class TeachPlanAuditResult {

	public TeachPlanAuditResult() {
		super();
		student = new Student();
	}

	public TeachPlanAuditResult(TeachPlan plan) {
		this.teachPlan = plan;
		creditAuditInfo.setRequired(plan.getCredit());
	}

	public TeachPlanAuditResult(Student student, MajorType majorType) {
		setStudent(student);
		setMajorType(majorType);
	}

	public TeachPlanAuditResult(Student student, TeachPlan plan,
			MajorType majorType) {
		this(plan);
		setStudent(student);
		setMajorType(majorType);
	}

	/** 对应学生 */
	private Student student;

	/** 是否通过 */
	private Boolean isPass;

	/** 专业类别（一/二专业） */
	private MajorType majorType;

	/** 备注 */
	private String remark;

	/** 对应审核的培养计划 */
	private TeachPlan teachPlan;

	/** 学分审核结果 */
	private CreditAuditInfo creditAuditInfo = new CreditAuditInfo();

	/** 各课程组审核结果 */
	private List courseGroupAuditResults = new ArrayList();

	/**
	 * @return 返回 courseGroupAuditResults.
	 */
	public List getCourseGroupAuditResults() {
		return courseGroupAuditResults;
	}

	/**
	 * @param courseGroupAuditResults
	 *            要设置的 courseGroupAuditResults.
	 */
	public void setCourseGroupAuditResults(List courseGroupAuditResults) {
		this.courseGroupAuditResults = courseGroupAuditResults;
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

	public void addCourseGroupAuditResult(CourseGroupAuditResult rs) {
		this.courseGroupAuditResults.add(rs);
	}

	/**
	 * 获取指定课程类别的课程组审核结果
	 * 
	 * @param type
	 * @return
	 */
	public CourseGroupAuditResult getCourseGroupAuditResult(CourseType type) {
		if (null == courseGroupAuditResults)
			return null;
		for (Iterator iter = courseGroupAuditResults.iterator(); iter.hasNext();) {
			CourseGroupAuditResult groupAuditResult = (CourseGroupAuditResult) iter
					.next();
			if (groupAuditResult.getCourseType().equals(type))
				return groupAuditResult;
		}
		return null;
	}

	/**
	 * 检测该培养计划是否完成
	 * 
	 * @param fullTerm
	 *            是否全部学期
	 */
	public void check(boolean fullTerm) {
		boolean flag = true;
		// 若不审核全部学期，此处将该培养计划要求总学分在审核时计为0，下面重新计算要求总学分
		if (!fullTerm) {
			creditAuditInfo.setRequired(new Float(0));
		}
		for (Iterator iter = getCourseGroupAuditResults().iterator(); iter
				.hasNext();) {
			CourseGroupAuditResult rs = (CourseGroupAuditResult) iter.next();
			if (Boolean.FALSE.equals(rs.getCreditAuditInfo().getIsPass())) {
				flag = false;
			}
			creditAuditInfo.addCompletedCredit(rs.getCreditAuditInfo()
					.getCompleted());
			// 若不审核全部学期，此处将重新计算要求总学分
			if (!fullTerm) {
				creditAuditInfo.addRequiredCredit(rs.getCreditAuditInfo()
						.getRequired());
			}
		}
		if (!flag
				|| Boolean.FALSE.equals(this.getCreditAuditInfo().getIsPass())) {
			this.isPass = Boolean.FALSE;
		} else {
			this.isPass = Boolean.TRUE;
		}
	}

	/**
	 * 各课程组审核排序后的结果集
	 * 
	 * @return
	 */
	public List getOrderCourseGroupAuditResults() {
		Collections.sort(courseGroupAuditResults, new Comparator() {
			public int compare(Object arg0, Object arg1) {

				CourseGroupAuditResult o0 = (CourseGroupAuditResult) arg0;
				CourseGroupAuditResult o1 = (CourseGroupAuditResult) arg1;
				int compusoryRs = o1.getCourseType().getIsCompulsory().equals(
						o0.getCourseType().getIsCompulsory()) ? 0 : (o1
						.getCourseType().getIsCompulsory().booleanValue() ? 1
						: -1);
				if (compusoryRs != 0)
					return compusoryRs;
				else {
					return o0.getCourseType().getPriority().compareTo(
							o1.getCourseType().getPriority());
				}
			}
		});
		return courseGroupAuditResults;
	}

	/**
	 * 针对无需审核课程类别的<code>CourseGroup</code>，对培养计划审核要求学分作相应的调整
	 * 
	 * @param courseGroup
	 */
	public void disauditCourseType(CourseGroup courseGroup) {
		if (this.creditAuditInfo.getRequired() != null) {
			float required = this.creditAuditInfo.getRequired().floatValue()
					- (courseGroup.getCredit() == null ? 0 : courseGroup
							.getCredit().floatValue());
			this.creditAuditInfo.setRequired(new Float(required < 0f ? 0f
					: required));
		}
	}

	/**
	 * @return 返回 student.
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            要设置的 student.
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return 返回 termsCount.
	 */
	public Integer getTermsCount() {
		return teachPlan == null ? null : teachPlan.getTermsCount();
	}

	/**
	 * @return 返回 isPass.
	 */
	public Boolean getIsPass() {
		return isPass;
	}

	/**
	 * @param isPass
	 *            要设置的 isPass.
	 */
	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}

	public MajorType getMajorType() {
		return majorType;
	}

	public void setMajorType(MajorType majorType) {
		this.majorType = majorType;
	}

	/**
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            要设置的 remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return teachPlan
	 */
	public TeachPlan getTeachPlan() {
		return teachPlan;
	}

	/**
	 * @param teachPlan
	 *            要设置的 teachPlan
	 */
	public void setTeachPlan(TeachPlan teachPlan) {
		this.teachPlan = teachPlan;
	}

}
