//$Id: StudentDAO.java,v 1.20 2007/01/12 01:28:00 yd Exp $
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
 * chaostone             2005-8-31         Created
 *  
 ********************************************************************************/
package com.shufe.dao.std;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;

import com.shufe.dao.BasicDAO;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.util.DataRealmLimit;

/**
 * 学生学籍信息的数据访问类
 * 
 * @author chaostone 2005-9-4
 */
public interface StudentDAO extends BasicDAO {

	public static final String[] DEFAULTEXCLUDEDATTRS = new String[] { "department", "type",
			"firstMajor", "firstAspect", "studentStatusInfo", "basicInfo" };

	/**
	 * 根据对象构造一个对学生基本信息的动态查询
	 * 
	 * @param std
	 * @param sortList
	 * @return
	 */
	public Criteria constructStudentCriteria(Student std, List sortList);

	/**
	 * 学生数量统计
	 * 
	 * @param student
	 * @return
	 */
	public Criteria constructStdCountCriteria(Student student);

	public Collection studentInfoStat(Student student, DataRealmLimit limit, String[] groupArray,
			Boolean isExactly);

	/**
	 * 生成对学生班级的查询对象
	 * 
	 * @param adminClass
	 * @return Criteria
	 */
	public Criteria constructStudentClassCriteria(AdminClass adminClass);

	/**
	 * 批量从班级中移出学生
	 * 
	 * @param studentIdArray
	 * @param adminClassIdArray
	 */
	public void batchCancelStudentClass(Object[] studentIdArray, Object[] adminClassIdArray);

	/**
	 * 批量置空所选学生的班级项
	 * 
	 * @param studentIdArray
	 */
	public void batchResetStudentClass(Long[] studentIdArray);

	/**
	 * 通过ID查找班级信息
	 * 
	 * @param id
	 * @return
	 */
	public AdminClass loadAdminClassById(Long id);

	/**
	 * 批量增加学生的班级项
	 * 
	 * @param studentIdArray
	 * @param parameterMap
	 */
	public void batchAddStudentClass(Object[] studentIdArray, Object[] adminClassIdArray);

	/**
	 * 批量置空所选学生的班级项
	 * 
	 * @param adminClassIds
	 */
	public void batchResetAdminClass(Long[] adminClassIds);

	public boolean checkExist(Long studentId);

	public Map getBasicInfoName(String stdCode);

	public void batchSetStdToTutor(String stdIds, Long tutorId);

	/**
	 * cwx added on september 7
	 * 
	 * @param student
	 * @param studentTypeIds
	 * @param departmentIds
	 * @return
	 */
	public List getStdIdByStd(Student student, String studentTypeIds, String departmentIds);

	/**
	 * add by yang 1月30日 批量更新学生信息
	 * 
	 * @param QueryList
	 *            学生Query的集合
	 */
	public void updateStudent(List QueryList);

}
