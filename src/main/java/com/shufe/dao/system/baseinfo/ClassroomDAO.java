//$Id: ClassroomDAO.java,v 1.2 2006/09/06 20:39:46 cwx Exp $
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
 * chaostone             2005-9-14         Created
 *  
 ********************************************************************************/

package com.shufe.dao.system.baseinfo;

import java.util.Collection;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.BasicDAO;
import com.shufe.model.system.baseinfo.Classroom;

/**
 * 学校教室基础信息存取类 <code>com.shufe.model.baseinfo.ClassRoom</code>
 * 
 * @author chaostone 2005-9-14
 */
public interface ClassroomDAO extends BasicDAO {
	/**
	 * 根据指定的教室编码，查询教室的详细信息 如果没有该教室，抛出异常.
	 * 
	 * @param id
	 * @return
	 */
	public Classroom getClassroom(Long id);

	/**
	 * 返回所有教室的第一页数据.
	 * 
	 * @return
	 */
	public List getClassrooms();

	/**
	 * 
	 * @return
	 */
	public List getAllClassrooms();

	/**
	 * 返回基于条件的查询结果.
	 * 
	 * @param classroom
	 * @return
	 */
	public List getClassrooms(Classroom classroom);

	/**
	 * 返回指定条件、页码和页长的教室数据
	 * 
	 * @param classroom
	 * @param PageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getClassrooms(Classroom classroom, int pageNo,
			int pageSize);

	/**
	 * 返回指定部门管理的教室列表
	 * 
	 * @param classroom
	 * @param departIds
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getClassrooms(Classroom classroom, Long[] departIds,
			int pageNo, int pageSize);

	/**
	 * 
	 * @param classroom
	 * @param departIds
	 *            null will be ommited
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getAllClassrooms(Classroom classroom, Long[] departIds,
			int pageNo, int pageSize);

	/**
	 * 返回指定部门管理的符合相应设备要求的教室列表
	 * 
	 * @param classroom
	 * @param departIdSeq
	 * @return
	 */
	public List getClassrooms(Collection configTypeList, Long[] departIds);

	/**
	 * 返回指定部门管理的教室列表
	 * 
	 * @param classroom
	 * @param departIds
	 * @return
	 */
	public List getClassrooms(Classroom classroom, Long[] departIds);

	/**
	 * 返回指定部门使用的教室
	 * 
	 * @param roomId
	 * @return
	 */
	public List getClassrooms(Long[] departIds);

	/**
	 * 返回指定id的教室
	 * 
	 * @param roomIds
	 * @return
	 */
	public List getClassrooms(Collection roomIds);

	/**
	 * 删除教室信息，慎用. 如果教室id不存在，在抛出异常.
	 * 
	 * @param id
	 */
	public void removeClassroom(Long id);

	/**
	 * cwx added on september 7 根据条件得到教室的id串
	 * 
	 * @param classroom
	 * @param departIds
	 * @param stdTypeIds
	 * @return
	 */
	public List getClassroomIdsByClassroom(Classroom classroom, String departIds);
}
