//$Id: TopicOpenServiceImpl.java,v 1.3 2007/01/26 10:02:13 cwx Exp $
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

package com.shufe.service.degree.thesis.topicOpen.impl;

import java.sql.Date;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;

import com.shufe.dao.degree.thesis.topicOpen.TopicOpenDAO;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.topicOpen.TopicOpen;
import com.shufe.model.std.Student;
import com.shufe.service.BasicService;
import com.shufe.service.degree.thesis.ThesisManageService;
import com.shufe.service.degree.thesis.topicOpen.TopicOpenService;

public class TopicOpenServiceImpl extends BasicService implements TopicOpenService {

	private TopicOpenDAO topicOpenDAO;
	private ThesisManageService thesisManageService;

	/**
	 * @param thesisManageService
	 *            The thesisManageService to set.
	 */
	public void setThesisManageService(ThesisManageService thesisManageService) {
		this.thesisManageService = thesisManageService;
	}

	/**
	 * 检测部门或导师是否已确认学生的论文开题情况表
	 * 
	 * @param studentId
	 * @return
	 */
	public boolean checkDepartAndTutorValidate(Long studentId) {
		TopicOpen thesisTopicOpen = getTopicOpen(studentId);
		if (null != thesisTopicOpen
				&& (thesisTopicOpen.getDepartmentAffirm().booleanValue() || thesisTopicOpen
						.getTutorAffirm().booleanValue())) {
			return true;
		}
		return false;
	}

	public boolean checkDepartmentValidate(Student student) {
		TopicOpen thesisTopicOpen = getTopicOpen(student);
		if (null != thesisTopicOpen && thesisTopicOpen.getDepartmentAffirm().booleanValue())
			return true;
		return false;
	}

	public boolean checkDepartAndTutorValidate(Student student) {
		TopicOpen thesisTopicOpen = getTopicOpen(student);
		if (null != thesisTopicOpen
				&& (thesisTopicOpen.getDepartmentAffirm().booleanValue() || thesisTopicOpen
						.getTutorAffirm().booleanValue())) {
			return true;
		}
		return false;
	}

	/**
	 * @param thesisTopicOpenDAO
	 *            The thesisTopicOpenDAO to set.
	 */
	public void setTopicOpenDAO(TopicOpenDAO thesisTopicOpenDAO) {
		this.topicOpenDAO = thesisTopicOpenDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.service.thesisTopicOpen.ThesisTopicOpenService#checkThesisOpenAffirmByCode(java.lang.String)
	 */
	public boolean checkThesisOpenAffirmByCode(String stdId) {
		return checkDepartAndTutorValidate(Long.valueOf(stdId));
	}

	/**
	 * @see com.shufe.service.degree.thesis.topicOpen.TopicOpenService#updateBatchThesisOpen(java.lang.String,
	 *      java.lang.Boolean, java.lang.Boolean)
	 */
	public void updateBatchThesisOpen(String thesisOpenSeq, Boolean validate, Boolean tutor) {
		topicOpenDAO.updateBatchThesisOpen(thesisOpenSeq, validate, tutor);

	}

	/**
	 * @see com.shufe.service.degree.thesis.topicOpen.TopicOpenService#getTopicOpen(java.lang.Long)
	 */
	public TopicOpen getTopicOpen(Long studentId) {
		ThesisManage thesisManage = thesisManageService.getThesisManage(studentId);
		if (null != thesisManage) {
			return thesisManage.getTopicOpen();
		}
		return null;
	}

	/**
	 * @see com.shufe.service.degree.thesis.topicOpen.TopicOpenService#getTopicOpen(com.shufe.model.std.Student)
	 */
	public TopicOpen getTopicOpen(Student student) {
		ThesisManage thesisManage = thesisManageService.getThesisManage(student);
		if (null != thesisManage) {
			return thesisManage.getTopicOpen();
		}
		return null;
	}

	/**
	 * @see com.shufe.service.degree.thesis.topicOpen.TopicOpenService#batchAffirmTimeAndAddress(Object[],
	 *      java.lang.String, java.lang.String)
	 */
	public void batchAffirmTimeAndAddress(Long[] selectIds, String openTime, String openAddress) {
		if (selectIds == null || selectIds.length == 0 || StringUtils.isEmpty(openTime)
				|| StringUtils.isEmpty(openAddress)) {
			return;
		}
		topicOpenDAO.bactchUpdateAddressAndOpenOn(selectIds, openAddress, Date.valueOf(openTime));
	}

	/**
	 * @see com.shufe.service.degree.thesis.topicOpen.TopicOpenService#getstdsNoOpened(com.shufe.model.std.Student,
	 *      java.lang.String, java.lang.String, int, int)
	 */
	public Pagination getstdsNoOpened(Student student, String departmentIdSeq, String stdTypeIdSeq,
			int pageNo, int pageSize) {
		return topicOpenDAO.getStdsNoOpened(student, departmentIdSeq, stdTypeIdSeq, pageNo,
				pageSize);
	}

	/**
	 * @see com.shufe.service.degree.thesis.topicOpen.TopicOpenService#batchUpdate(java.lang.String[],
	 *      java.lang.Object[], java.lang.String)
	 */
	public void batchUpdate(String[] propertys, Object[] values, String selectSeq) {
		topicOpenDAO.batchUpdate(propertys, values, selectSeq);
	}

	/**
	 * @see com.shufe.service.degree.thesis.topicOpen.TopicOpenService#doGetNoOpenedList(com.shufe.model.std.Student,
	 *      java.lang.String, java.lang.String)
	 */
	public List doGetNoOpenedList(Student student, String departmentIdSeq, String stdTypeIdSeq) {
		return topicOpenDAO.doGetNoOpenedList(student, departmentIdSeq, stdTypeIdSeq);
	}

}
