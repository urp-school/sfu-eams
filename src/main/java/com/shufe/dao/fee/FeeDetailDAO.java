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
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-8-13            Created
 *  
 ********************************************************************************/
package com.shufe.dao.fee;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.Criteria;

import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.fee.FeeDetail;
import com.shufe.model.system.calendar.TeachCalendar;
/**
 * 收费信息存取接口
 * @author chenweixiong,chaostone
 *
 */
public interface FeeDetailDAO {
	public List getFeeDetails(FeeDetail feeDetail);
	/**
	 * 查询收费信息，限制收费部门权限，返回分页数据
	 * 
	 * @param feeDetailInfo
	 * @param departIdSeq
	 * @param orderbyName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract Pagination getFeeDetails(FeeDetail feeDetailInfo,
			String departIdSeq, String orderbyName, int pageNo, int pageSize);

	/**
	 * 查询收费信息，限制收费部门权限
	 * 
	 * @param feeDetailInfo
	 * @param departIdSeq
	 * @param orderByName
	 * @return
	 */
	public abstract List getFeeDetails(FeeDetail feeDetailInfo,
			String departIdSeq, String orderByName);

	/**
	 * 查询各种收费类型的分页数据
	 * 
	 * @param feeDetail
	 * @param feeTypeList
	 * @param orderByName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract Pagination getFeeDetailOfTypes(FeeDetail feeDetail,
			List feeTypeList, String orderByName, int pageNo, int pageSize);

	/**
	 * 根据条件得到学生类别的选课criteria 对象
	 * 
	 * @param departmentIds
	 * @param teachCalenar
	 * @return
	 */
	public abstract Criteria getStdSelectCourses(String departmentIds,
			CourseTake courseTake, TeachCalendar teachCalenar);

}
