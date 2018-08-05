//$Id: TutorService.java,v 1.2 2007/01/19 09:34:31 cwx Exp $
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
 * hc             2005-11-24         Created
 *  
 ********************************************************************************/

package com.shufe.service.degree.tutorManager;

import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.degree.tutorManager.TutorDAO;
import com.shufe.model.system.baseinfo.Tutor;

/**
 * 
 * @author Administrator
 * @param cwx
 *            2007-2-26修改
 */
public interface TutorService {
	/**
	 * 得到所有的导师分页对象
	 * 
	 * @param departIdSeq
	 * @param pageNo
	 * @param pageSize
	 * @param Tutor
	 * @return
	 */
	public Pagination getAllTutor(Tutor tutor, String departIdSeq, int pageNo, int pageSize);

	/**
	 * 设置一批学生和导师的关系
	 * 
	 * @param studentIds
	 * @param tutorId
	 */
	public void batchModifyTeacherOfStd(String studentIds, Long tutorId);

	/**
	 * 改变教师状态
	 * 
	 * @param teacherId
	 * @param updateToTutor 为true是把教师改为导师 反之则是把导师改为教师
	 */
	public void changeTutor(Long teacherId,boolean updateToTutor);

	/**
	 * 改变导师对象
	 * 
	 * @param tutorDAO
	 */
	public void setTutorDAO(TutorDAO tutorDAO);

	/**
	 * 得到导师的分类对象
	 * 
	 * @param tutor
	 * @param departIdSeq
	 * @return
	 */
	public List getTutorList(Tutor tutor, String departIdSeq);

	/**
	 * 得到分类对象的group属性列表
	 * 
	 * @param tutor
	 * @param departIdSeq
	 * @param propertys
	 * @param isCount
	 * @return
	 */
	public List getPropertyOfTutor(Tutor tutor, String departIdSeq, String propertys,
			Boolean isCount);

	/**
	 * 对统计结果进行统计
	 * 
	 * @param tutor
	 * @param departIdSeq
	 * @param propertys
	 * @return
	 */
	public Map getStatisticMap(Tutor tutor, String departIdSeq, String propertys);

	/**
	 * 根据条件得到导师专业分布的Map
	 * 
	 * @param tutor
	 * @param departIdSeq
	 * @return
	 */
	public Map getSpecialityTutorMap(Tutor tutor, String departIdSeq);

	/**
	 * 根据条件导师
	 * 
	 * @param tutor
	 * @param departIdSeq
	 * @return
	 */
	public Map getTutorTypeMap(Tutor tutor, String departIdSeq, List operatorList);

}
