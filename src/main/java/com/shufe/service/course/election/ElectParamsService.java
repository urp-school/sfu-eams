//$Id: ElectParamsService.java,v 1.1 2006/08/02 00:52:48 duanth Exp $
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
 * chaostone             2005-12-5         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.election;

import java.io.Serializable;
import java.util.List;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.dao.course.election.ElectParamsDAO;
import com.shufe.model.course.election.ElectParams;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 选课参数服务接口
 * 
 * @author chaostone 2005-12-12
 */
public interface ElectParamsService {

	/**
	 * 获得单个选课参数
	 * 
	 * @param id
	 * @return
	 */
	public ElectParams getElectParams(Serializable id);

	/**
	 * 获得选课参数接口.
	 * 
	 * @param stdTypeIds
	 * @param calendar
	 * @return
	 */
	public List getElectParams(String stdTypeIds, TeachCalendar calendar);

	/**
	 * 根据学生信息查找对应的选课参数 查询有效可用的选课参数(结束日期在当前日期之后的第一个参数)
	 * 
	 * @param std
	 * @return
	 */
	public ElectParams getAvailElectParams(Student std);

	/**
	 * 查询选课参数
	 * 
	 * @param params
	 * @return
	 */
	public List getElectParams(ElectParams params);

	/**
	 * 保存选课参数
	 * 
	 * @param params
	 * @throws PojoExistException
	 *             若教学日历和学生类别和院系相同，则认为是相同的选课参数，
	 */
	public void saveElectParams(ElectParams params) throws PojoExistException;

	/**
	 * 设置选课参数存取对象
	 * 
	 * @param paramsDAO
	 */
	public void setElectParamsDAO(ElectParamsDAO paramsDAO);
}
