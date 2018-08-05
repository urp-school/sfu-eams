//$Id: TeachCategoryDAO.java,v 1.4 2006/12/19 13:08:42 duanth Exp $
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
 * chenweixiong              2006-8-18         Created
 *  
 ********************************************************************************/

package com.shufe.dao.workload.course;

import java.util.List;

import org.hibernate.Criteria;

import com.shufe.dao.BasicDAO;
import com.shufe.model.workload.course.TeachCategory;

public interface TeachCategoryDAO extends BasicDAO {

	/**
	 * 得到所有的合法的数据
	 * 
	 * @return
	 */
	public abstract List getAlllegality();

	/**
	 * 得到对应的对象的数据，真表示合法，假表示非法.
	 * 
	 * @param b
	 * @return
	 */
	public abstract Criteria getExampleOfCategory(boolean b);

	/**
	 * 根据学生类别和课程类别得到一个当前的对象.
	 * 
	 * @param studnetType
	 * @param courseType
	 * @return
	 */
	public abstract TeachCategory getTeachCategoryBystdTypeAndcourseType(Long stdTypeId,
			Long courseTypeId);

}