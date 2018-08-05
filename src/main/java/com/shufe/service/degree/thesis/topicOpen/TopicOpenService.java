//$Id: TopicOpenService.java,v 1.4 2007/01/26 10:02:13 cwx Exp $
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
 * hc             2005-12-2         Created
 *  
 ********************************************************************************/

package com.shufe.service.degree.thesis.topicOpen;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.model.degree.thesis.topicOpen.TopicOpen;
import com.shufe.model.std.Student;

public interface TopicOpenService {
	boolean checkDepartmentValidate(Student student);

	/**
	 * 检测部门或导师是否已确认学生的论文开题情况表
	 * 
	 * @param studentId
	 * @return
	 */
	public boolean checkDepartAndTutorValidate(Long studentId);

	public boolean checkDepartAndTutorValidate(Student student);

	/**
	 * 根椐学生序号判断学生是否通过毕业审核
	 * 
	 * @param stdcode
	 * @return
	 */
	public boolean checkThesisOpenAffirmByCode(String stdId);

	/**
	 * @param thesisOpenSeq
	 * @param validate
	 * @param tutor
	 */
	public void updateBatchThesisOpen(String thesisOpenSeq, Boolean validate, Boolean tutor);

	/**
	 * @param studentId
	 * @return
	 */
	public TopicOpen getTopicOpen(Long studentId);

	/**
	 * @param student
	 * @return
	 */
	public TopicOpen getTopicOpen(Student student);

	/**
	 * 对相应的seq序列,更新开放时间和开放地点.
	 * 
	 * @param selectIds
	 * @param openTime
	 * @param openAddress
	 */
	public void batchAffirmTimeAndAddress(Long[] selectIds, String openTime, String openAddress);

	/**
	 * 根据条件得到未开放学生的分页对象.
	 * 
	 * @param student
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getstdsNoOpened(Student student, String departmentIdSeq, String stdTypeIdSeq,
			int pageNo, int pageSize);

	/**
	 * 得到所有未开题的学生
	 * 
	 * @param student
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List doGetNoOpenedList(Student student, String departmentIdSeq, String stdTypeIdSeq);

	/**
	 * 批量执行是否通过字段
	 * 
	 * @param propertys
	 * @param values
	 * @param selectSeq
	 */
	public void batchUpdate(String[] propertys, Object[] values, String selectSeq);
}
