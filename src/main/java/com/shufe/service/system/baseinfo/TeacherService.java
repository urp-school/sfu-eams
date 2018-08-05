//$Id: TeacherService.java,v 1.4 2006/10/12 12:05:06 duanth Exp $
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
 * chaostone             2005-10-28         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.baseinfo;

import java.util.Collection;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.dao.system.baseinfo.TeacherDAO;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.util.DataRealmLimit;

/**
 * 教师信息服务接口
 * 
 * @author chaostone 2005-10-28
 */
public interface TeacherService {

	/**
	 * 返回指定id的教师信息，如果不存在这样的教师信息，则返回空.
	 * 
	 * @param id
	 * @return
	 */
	public Teacher getTeacherById(Long id);

	/**
	 * 返回指定职工号的教师信息，如果不存在这样的教师信息，则返回空.
	 * 
	 * @param teacherNO
	 * @return
	 */
	public Teacher getTeacherByNO(String teacherNO);

	/**
	 * 返回所有教师信息
	 * 
	 * @return
	 */
	public List getTeachers();

	/**
	 * 返回指定条件的教师信息
	 * 
	 * @param teacher
	 * @return
	 */
	public List getTeachers(Teacher teacher);

	/**
	 * 根据教师所在部门查询
	 * 
	 * @param departIds
	 * @return
	 */
	public List getTeachersByDepartment(String departIds);

	/**
	 * 根据教师所在部门查询
	 * 
	 * @param departIds
	 * @return
	 */
	public List getTeachersByDepartment(Long[] departIds);

	/**
	 * 返回指定id数组的教师列表
	 * 
	 * @param teacherIds
	 * @return
	 */
	public List getTeachersById(Long teacherIds[]);

	/**
	 * 返回指定id数组的教师列表
	 * 
	 * @param teacherIds
	 * @return
	 */
	public List getTeachersById(Collection teacherIds);

	/**
	 * 返回指定职工号数组的教师列表
	 * 
	 * @param teacherIds
	 * @return
	 */
	public List getTeachersByNO(String teacherNOs[]);

	/**
	 * 获得教师id串对应的教师姓名
	 * 
	 * @param teacherIdSeq
	 * @return
	 */
	public List getTeacherNames(String teacherIdSeq);

	public Collection getTeachers(Teacher teacher, DataRealmLimit limit,
			List sortList);

	/**
	 * 返回指定条件、页码和页长的教师数据.
	 * 
	 * @param teacher
	 * @param PageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getTeachers(Teacher teacher, int pageNo, int pageSize);

	/**
	 * 返回指定部门列表和教师条件的教师数据
	 * 
	 * @param teacher
	 * @param departIds
	 * @param PageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getTeachers(Teacher teacher, Long departIds[],
			int pageNo, int pageSize);

	/**
	 * 返回指定部门列表和教师条件的教师数据
	 * 
	 * @param teacher
	 * @param departIdSeq
	 * @param PageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getTeachers(Teacher teacher, String departIdSeq,
			int pageNo, int pageSize);

	/**
	 * 返回指定部门列表和教师条件的教师数据(不分页)
	 * 
	 * @param teacher
	 * @param departIdSeq
	 * @return
	 */
	public List getTeachers(Teacher teacher, String departIdSeq);

	/**
	 * 保存新建的教师信息，如果已经存在了同样的信息，则抛出异常.
	 * 
	 * @param teacher
	 */
	public void saveOrUpdate(Teacher teacher) throws PojoExistException;

	/**
	 * 删除教师信息，慎用. 如果教师id不存在，直接返回.
	 * 
	 * @param id
	 */
	public void removeTeacher(String id);

	/**
	 * 设置教师信息存取对象
	 * 
	 * @param teacherDAO
	 */
	public void setTeacherDAO(TeacherDAO teacherDAO);

	/**
	 * cwx added on september 7
	 * 
	 * @param teacher
	 * @param deparmentsq
	 * @return
	 */
	public List getTeacherIdsByTeacher(Teacher teacher, String deparmentsq);
}
