//$Id: TopicOpenDAO.java,v 1.3 2007/01/22 10:42:44 cwx Exp $
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

package com.shufe.dao.degree.thesis.topicOpen;

import java.sql.Date;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.model.std.Student;


public interface TopicOpenDAO {
	
	/**
	 * 批量更新
	 * @param topicOpenId
	 * @param validate
	 * @param tutor
	 */
	public void updateThesisOpen(Long topicOpenId,Boolean validate,Boolean tutor);
	
	/**
	 * 批量更新
	 * @param topicOpenId
	 * @param validate
	 * @param tutor
	 */
	public void updateBatchThesisOpen(String topicOpenSeq,Boolean validate,Boolean tutor);
	
	/**
	 * 得到所有未开题的学生的分页对象.
	 * @param student
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getStdsNoOpened(Student student,String departmentIdSeq,String stdTypeIdSeq,int pageNo,int pageSize);
	
	
	/**
	 * 得到未开题的学生列表
	 * @param student
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List doGetNoOpenedList(Student student, String departmentIdSeq,
			String stdTypeIdSeq);
	/**
	 * 批量执行论文开题里面的是否通过属性
	 * @param propertys
	 * @param values
	 * @param selectSeq
	 */
	public void batchUpdate(String[] propertys,Object[] values, String selectSeq);

	/**
	 * 批量更新学生开题时间以及地点
	 * @param topicOpenIds
	 * @param address
	 * @param openOn
	 */
	public void bactchUpdateAddressAndOpenOn(Long[] topicOpenIds, String address, Date openOn);
}
