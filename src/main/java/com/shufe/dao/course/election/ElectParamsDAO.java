//$Id: ElectParamsDAO.java,v 1.1 2006/08/02 00:52:48 duanth Exp $
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
 * chaostone             2005-12-12         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.election;

import java.util.List;

import com.shufe.dao.BasicDAO;
import com.shufe.model.course.election.ElectParams;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

public interface ElectParamsDAO extends BasicDAO {
	/**
	 * 获得选课参数接口.
	 * 
	 * @param stdTypeIds
	 * @param calendar
	 * @return
	 */
	public List getElectParams(Long[] stdTypeIds, TeachCalendar calendar);

	/**
	 * 查询选课参数
	 * 
	 * @param params
	 * @return
	 */
	public List getElectParams(ElectParams params);

	/**
	 * 查询有效可用的选课参数(结束日期在当前日期之后的)
	 * 
	 * @param std
	 * @return
	 */
	public List getAvailElectParams(Student std);

	public void saveElectParams(ElectParams params);
}
