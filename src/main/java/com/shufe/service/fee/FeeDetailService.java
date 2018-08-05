//$Id: FeeDetailService.java,v 1.7 2006/12/19 10:07:07 duanth Exp $
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
 * chenweixiong              2005-11-6         Created
 *  
 ********************************************************************************/

package com.shufe.service.fee;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.Criteria;

import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.shufe.dao.fee.FeeDetailDAO;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.fee.FeeDetail;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

public interface FeeDetailService {
	public List getFeeDetails(FeeDetail feeDetail);

	/**
	 * 根据查询条件 以及部门ids得到一个分页的对象
	 * 
	 * @param studentId
	 * @param studentName
	 * @param teachCalendar
	 * @return
	 */
	public Pagination getFeeDetails(FeeDetail feeDetail, String departIdSeq,
			String orderByName, int pageNo, int pageSize);

	/**
	 * 根据查询条件得到的收费信息的信息.
	 * 
	 * @param feeDetail
	 * @param departIdSeq
	 * @param orderByName
	 * 
	 * @return
	 */
	public List getFeeDetails(FeeDetail feeDetail, String departIdSeq,
			String orderByName);

	/**
	 * 得到学生的选课情况,条件是:部门Ids和教学日历对象
	 * 
	 * @param departmentIds
	 * @param teachCalenar
	 * @return
	 */
	public Criteria getStdSelectCourses(String departmentIds,
			CourseTake courseTake, TeachCalendar teachCalendar);

	/**
	 * 为学生定制消息
	 * 
	 * @param messagekeyId
	 * @param title
	 * @param body
	 * @param studentIds
	 */
	public void saveMessageBystudentNos(String title, String body,
			String studentIds);

	/**
	 * 根据问题类型列表，收费的查询条件，以及排序条件得到相应的排序名称得到分页结果
	 * 
	 * @param feeTypeList
	 * @param feeDetailInfo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getFeeDetailsOfTypes(FeeDetail feeDetail,
			List feeTypeList, String orderbyName, int pageNo, int pageSize);

	/**
	 * @param feeDetailDAO
	 *            The feeDetailDAO to set.
	 */
	public void setFeeDetailDAO(FeeDetailDAO feeDetailDAO);

	/**
	 * 查找某个学生在指定学期，指定收费项目的全部收费纪录.<br>
	 * 如果不指定教学日历，则查找全部学期的收费项目.<br>
	 * 如果不指定收费项目，则查找全部的收费项目.<br>
	 * 
	 * @param std
	 * @param calendar
	 * @param type
	 * @return
	 */
	public List getFeeDetails(Student std, TeachCalendar calendar, FeeType type);
	/**
	 * 查询学生在指定学期应缴的费用<br>
	 *  Float[0]按照默认值应缴交付金额<br>
	 *  Float[1]按照既往缴费的应缴之和<br>
	 *  Float[1]按照既往缴费的实缴之和<br>
	 * @param std
	 * @param calendar
	 * @param type
	 * @return
	 */
	public Float[] statFeeFor(Student std, TeachCalendar calendar, FeeType type);
}