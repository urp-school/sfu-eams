//$Id: ClassroomService.java,v 1.2 2006/09/06 20:39:47 cwx Exp $
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
 * chaostone             2005-9-15         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.baseinfo;

import java.util.Collection;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.dao.system.baseinfo.ClassroomDAO;
import com.shufe.model.system.baseinfo.Classroom;

public interface ClassroomService {
	/**
	 * 设置教室信息管理的数据存取对象.
	 * 
	 * @param classroomDAO
	 */
	public void setClassroomDAO(ClassroomDAO classroomDAO);

	/**
	 * 根据指定的教室编码，查询教室的详细信息 如果没有该教室，返回null;
	 * 
	 * @param id
	 * @return
	 */
	public Classroom getClassroom(Long id);

	/**
	 * 返回所有教室的数据.
	 * 
	 * @return
	 */
	public List getClassrooms();

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
	public Pagination getClassrooms(Classroom classroom, String departIdSeq,
			int pageNo, int pageSize);

	/**
	 * 
	 * @param classroom
	 * @param departIdSeq
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getAllClassrooms(Classroom classroom, String departIdSeq,
			int pageNo, int pageSize);

	/**
	 * 返回指定部门管理的教室列表
	 * 
	 * @param classroom
	 * @param departIdSeq
	 * @return
	 */
	public List getClassrooms(Classroom classroom, String departIdSeq);

	/**
	 * 返回指定部门管理的符合相应设备要求的教室列表
	 * 
	 * @param classroom
	 * @param departIdSeq
	 * @return
	 */
	public List getClassrooms(Collection configTypeList, String departIdSeq);

	/**
	 * 获得指定学院使用的教室
	 * 
	 * @param departIdSeq
	 * @return
	 */
	public List getClassrooms(String departIdSeq);

	/**
	 * 返回指定id的教室
	 * 
	 * @param roomIds
	 * @return
	 */
	public List getClassrooms(Collection roomIds);

	/**
	 * 保存新的教室信息，如果教室已经存在则抛出异常.
	 * 
	 * @param classroom
	 */
	public void saveOrUpdate(Classroom classroom) throws PojoExistException;

	/**
	 * 删除教室信息，慎用. 如果教室id不存在，返回.
	 * 
	 * @param id
	 */
	public void removeClassroom(Long id);

	/**
	 * cwx added on september 7
	 * 
	 * @param classroom
	 * @param departIds
	 * @return
	 */
	public List getClassroomIdsByClassroom(Classroom classroom, String departIds);
}
