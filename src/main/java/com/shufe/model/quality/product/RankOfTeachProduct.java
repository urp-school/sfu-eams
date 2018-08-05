//$Id: RankOfTeachProduct.java,v 1.2 2006/12/19 10:08:45 duanth Exp $
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
 * chenweixiong              2006-5-29         Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.product;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.baseinfo.Teacher;

/**
 * 教学成果排名
 * 
 * @author chaostone
 * 
 */
public class RankOfTeachProduct extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7491600119453088612L;

	/** 教师 */
	private Teacher teacher = new Teacher();

	/** 教学成果 */
	private TeachProduct teachProduct = new TeachProduct();

	/** 在教学成果里面的排名 */
	private Integer rank;

	/**
	 * @return Returns the rank.
	 */
	public Integer getRank() {
		return rank;
	}

	/**
	 * @param rank
	 *            The rank to set.
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	/**
	 * @return Returns the teacher.
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher
	 *            The teacher to set.
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * @return Returns the teachProduct.
	 */
	public TeachProduct getTeachProduct() {
		return teachProduct;
	}

	/**
	 * @param teachProduct
	 *            The teachProduct to set.
	 */
	public void setTeachProduct(TeachProduct teachProduct) {
		this.teachProduct = teachProduct;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof RankOfTeachProduct)) {
			return false;
		}
		RankOfTeachProduct rhs = (RankOfTeachProduct) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(
				this.rank, rhs.rank).append(this.teacher.getId(),
				rhs.teacher.getId()).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1426781437, -1462025883).appendSuper(
				super.hashCode()).append(this.rank)
				.append(this.teacher.getId()).toHashCode();
	}
}
