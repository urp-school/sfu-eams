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
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.model.course.plan.CourseGroup;

/**
 * 课程组审核结果
 */
public class CourseGroupAuditResult {
	/** 学分审核结果 */
	private CreditAuditInfo creditAuditInfo = new CreditAuditInfo();

	/** 各课程审核结果 */
	private List planCourseAuditResults = new ArrayList(0);

	/** 课程类别，对应<code>CourseGroup</code>中的courseType */
	private CourseType courseType;

	/** 课程组每学期对应学分，对应<code>CourseGroup</code>中的creditPerTerms */
	private String creditPerTerms;

	public CourseGroupAuditResult() {
		super();
	}

	public CourseGroupAuditResult(CourseGroup group, List termList) {
		this.courseType = group.getCourseType();
		this.creditPerTerms = group.getCreditPerTerms();
		this.getCreditAuditInfo().setRequired(group.getCredit(termList));
	}

	public CourseGroupAuditResult(CourseType type, Float required) {
		this.courseType = type;
		this.creditPerTerms = "";
		this.getCreditAuditInfo().setRequired(required);
	}

	/**
	 * @return 返回 creditPerTerms.
	 */
	public String getCreditPerTerms() {
		return creditPerTerms;
	}

	/**
	 * @param creditPerTerms
	 *            要设置的 creditPerTerms.
	 */
	public void setCreditPerTerms(String creditPerTerms) {
		this.creditPerTerms = creditPerTerms;
	}

	/**
	 * @return 返回 courseType.
	 */
	public CourseType getCourseType() {
		return courseType;
	}

	/**
	 * @param courseType
	 *            要设置的 courseType.
	 */
	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
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
	 * @return 返回 planCourseAuditResults.
	 */
	public List getPlanCourseAuditResults() {
		return planCourseAuditResults;
	}

	/**
	 * @param planCourseAuditResults
	 *            要设置的 planCourseAuditResults.
	 */
	public void setPlanCourseAuditResults(List planCourseAuditResults) {
		this.planCourseAuditResults = planCourseAuditResults;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		if (null == courseType)
			return -1;
		return this.courseType.hashCode();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CourseGroupAuditResult)) {
			return false;
		}
		CourseGroupAuditResult rhs = (CourseGroupAuditResult) object;
		return new EqualsBuilder().append(this.courseType, rhs.courseType).isEquals();
	}

	/**
	 * 添加培养计划课程审核结果
	 * 
	 * @param rs
	 */
	public void addPlanCourseAuditResult(PlanCourseAuditResult rs) {
		this.planCourseAuditResults.add(rs);
	}

	/**
	 * 各课程审核排序后的结果集
	 * 
	 * @return
	 */
	public List getOrderPlanCourseAuditResults() {
		if (CollectionUtils.isNotEmpty(planCourseAuditResults)) {
			ComparatorChain comparatorChain = new ComparatorChain();
			comparatorChain.addComparator(new BeanComparator("terms"));
			comparatorChain.addComparator(new BeanComparator("course.code"));
			Collections.sort(planCourseAuditResults, comparatorChain);
		}
		return this.planCourseAuditResults;
	}

	/**
	 * 获取通过课程的数量
	 * 
	 * @return
	 */
	public int getPassCourseCount() {
		int i = 0;
		if (CollectionUtils.isNotEmpty(planCourseAuditResults)) {
			for (Iterator iter = planCourseAuditResults.iterator(); iter.hasNext();) {
				PlanCourseAuditResult element = (PlanCourseAuditResult) iter.next();
				if (Boolean.TRUE.equals(element.getCreditAuditInfo().getIsPass())) {
					i++;
				}
			}
		}
		return i;
	}

}
