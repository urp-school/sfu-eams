package com.shufe.service.std;

import java.util.Collection;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.std.AbroadStudentInfo;
import com.shufe.model.std.BasicInfo;
import com.shufe.model.std.Student;
import com.shufe.model.std.StudentStatusInfo;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.util.DataRealmLimit;

/**
 * 学生学籍管理的服务类
 * 
 * @author chaostone 2005-9-4
 */
public interface StudentService {

	/**
	 * 获取一个学生的学籍信息
	 * 
	 * @param studentId
	 * @return
	 */
	public Student getStudent(Long studentId);

	/**
	 * 获取一个学生的学籍信息
	 * 
	 * @param code
	 * @return
	 */
	public Student getStudent(String code);

	/**
	 * 保存新建的学生学籍信息
	 * 
	 * @param student
	 * @param statusInfo
	 * @param basicInfo
	 * @param abroadStudentInfo
	 * @return
	 */
	public Student saveStudent(Student student, StudentStatusInfo statusInfo,
	        BasicInfo basicInfo, AbroadStudentInfo abroadStudentInfo);

	/**
	 * 修改学生信息
	 * 
	 * @param student
	 * @param statusInfo
	 * @param basicInfo
	 * @param abroadStudentInfo
	 * @return
	 */
	public Student updateStudent(Student student, StudentStatusInfo statusInfo,
	        BasicInfo basicInfo, AbroadStudentInfo abroadStudentInfo);

	/**
	 * 依照std设置的条件,查询学生信息
	 * 
	 * @param std
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination searchStudent(Student std, int pageNo, int pageSize);

	/**
	 * 返回指定学生类别和部门以及学生信息为条件的无分页学生信息列表
	 * 
	 * @param student
	 * @param studentTypeIds
	 * @param departmentIds
	 * @return
	 */
	public List searchStudent(Student student, String studentTypeIds,
	        String departmentIds);

	/**
	 * 搜索双专业学生
	 * 
	 * @param student
	 * @param pageLimit
	 * @param studentTypeIds
	 * @param departmentIds
	 *            一专业权限内部门id串，空为不作限制
	 * @param secondDepartmentIds
	 *            二专业权限内部门id串，空为不作限制
	 * @param andOr
	 *            一二专业部门权限的关系
	 * @return
	 */
	public Collection search2ndSpecialityStudent(Student student,
	        PageLimit pageLimit, String studentTypeIds, String departmentIds,
	        String secondDepartmentIds, String andOr);

	/**
	 * 依据student的条件搜索学生，返回id的<code>List</code>
	 * 
	 * @param student
	 * @return
	 */
	public List searchStudentId(Student student);

	/**
	 * 依据student、学生类别和部门的条件搜索学生，返回id的<code>List</code>
	 * 
	 * @param student
	 * @param studentTypeIds
	 * @param departmentIds
	 * @return
	 */
	public List searchStudentId(Student student, String studentTypeIds,
	        String departmentIds);

	/**
	 * 返回指定学生类别、部门、毕业审核状况以及学生信息为条件的学生信息列表
	 * 
	 * @param student
	 * @param studentTypeIds
	 * @param departmentIds
	 * @param studentGraduateAuditStatus
	 * @return
	 */
	public List searchStudent(Student student, String studentTypeIds,
	        String departmentIds, String studentGraduateAuditStatus);

	/**
	 * 返回指定学生类别、部门、毕业审核状况以及学生信息为条件的双专业学生信息列表
	 * 
	 * @param student
	 * @param studentTypeIds
	 * @param departmentIds
	 * @param studentGraduateAuditStatus
	 * @return
	 */
	public List searchSecondSpecialityStudent(Student student,
	        String studentTypeIds, String departmentIds,
	        String[] studentGraduateAuditStatus);

	/**
	 * 返回指定学生类别和部门以及学生信息为条件的学生信息列表
	 * 
	 * @param student
	 * @param pageNo
	 * @param pageSize
	 * @param studentTypeIds
	 * @param departmentIds
	 * @return
	 */
	public Pagination searchStudent(Student student, int pageNo,
	        int pageSize, String studentTypeIds, String departmentIds);

	/**
	 * 返回指定老师以及学生信息为条件的学生信息列表
	 * 
	 * @param student
	 * @param pageNo
	 * @param pageSize
	 * @param teacherId
	 * @return
	 */
	public Pagination searchStudent(Student student, int pageNo,
	        int pageSize, Long teacherId);

	/**
	 * 返回指定学生类别、部门、毕业审核状况以及学生信息为条件的学生信息列表
	 * 
	 * @param student
	 * @param pageNo
	 * @param pageSize
	 * @param studentTypeIds
	 * @param departmentIds
	 * @param studentGraduateAuditStatus
	 * @return
	 */
	public Pagination searchStudent(Student student, int pageNo,
	        int pageSize, String studentTypeIds, String departmentIds,
	        String studentGraduateAuditStatus);

	/**
	 * 搜索双专业管理模块的分页学生集合
	 * 
	 * @param student
	 * @param pageNo
	 * @param pageSize
	 * @param studentTypeIds
	 * @param departmentIds
	 * @return
	 */
	/*
	 * public Collection searchManage2ndSpecialityStudent(Student student,
	 * PageLimit pageLimit, String studentTypeIds, String departmentIds);
	 */

	public Pagination searchSecondSpecialityStudent(Student student,
	        int pageNo, int pageSize, String studentTypeIds,
	        String departmentIds,
	        String[] secondSpecialityStudentGraduateAuditStatus);

	/**
	 * 返回指定学生类别和部门以及学生信息为条件的第二专业学生信息分页列表
	 * 
	 * @param student
	 * @param pageNo
	 * @param pageSize
	 * @param studentTypeIds
	 * @param departmentIds
	 * @return
	 */
	public Pagination searchSecondSpecialityStudent(Student student,
	        int pageNo, int pageSize, String studentTypeIds,
	        String departmentIds);

	/**
	 * 返回指定条件的学生列表
	 * 
	 * @param std
	 * @return
	 */
	public List findStudent(Student std);

	/**
	 * 根据学号数组中指定的学号，返回指定学生列表
	 * 
	 * @param studentIdArray
	 * @return
	 */
	public List searchStudent(Long[] studentIdArray);

	/**
	 * 依据部门串得到学生列表
	 * 
	 * @param departIds
	 * @return
	 */
	public List getStudentsByDepartment(String departIds);

	/**
	 * 批量修改毕业审核状态
	 * 
	 * @param studentIdArray
	 * @param status
	 */
	public void batchUpdateGraduateAuditStatus(Long[] studentIdArray,
	        Boolean status);

	public void batchUpdateSecondGraduateAuditStatus(Long[] studentIdArray,
	        Boolean status);

	/**
	 * 批量修改一专业毕业审核状态
	 * 
	 * @param stdIds
	 * @param status
	 */
	public void batchUpdateGraduateAuditStatus(Collection stdIds, Boolean status);

	/**
	 * 批量修改二专业毕业审核状态
	 * 
	 * @param stdId
	 * @param status
	 */
	public void batchUpdateSecondGraduateAuditStatus(Collection stdId,
	        Boolean status);

	/**
	 * 批量修改指定（一/二专业）毕业审核状态
	 * 
	 * @param stdId
	 * @param majorType
	 * @param status
	 */
	public void batchUpdateGraduateAuditStatus(Collection stdId,
			MajorType majorType, Boolean status);

	/**
	 * 检索学生班级
	 * 
	 * @param adminClass
	 * @param pageNo
	 * @param pageSize
	 * @param departmentIds(数据级权限,如果为null那么不判断)
	 * @param studentTypeIds(数据级权限,如果为null那么不判断)
	 * @return Pagination
	 */
	public Pagination searchStudentClass(AdminClass adminClass, int pageNo,
	        int pageSize, String studentTypeIds, String departmentIds);

	/**
	 * 检索学生班级(不分页)
	 * 
	 * @param adminClass
	 * @param studentTypeIds
	 * @param departmentIds
	 * @return List
	 */
	public List findStudentClass(AdminClass adminClass, String studentTypeIds,
	        String departmentIds);

	/**
	 * 批量修改学生所在班级
	 * 
	 * @param studentIdArray
	 * @param adminClassIdArray
	 */
	public void batchUpdateStudentClass(Object[] studentIdArray,
	        Object[] adminClassIdArray);

	/**
	 * 批量从班级中移出学生
	 * 
	 * @param studentIdArray
	 * @param classId
	 */
	public void batchCancelStudentClass(Object[] studentIdArray,
	        Object[] adminClassIdArray);

	/**
	 * 批量置空所选学生的班级项
	 * 
	 * @param studentIdArray
	 */
	public void batchResetStudentClass(Long[] studentIdArray);

	/**
	 * 批量修改学生学籍状态并记录变更记录<br>
	 * 如果status和infoStatus的取值是null则不更新
	 * 
	 * @param studentIdArray
	 * @param infoStatus
	 */
	public void batchUpdateStudentInfoStatus(Long[] studentIdArray,
	        Long infoStatus);

	/**
	 * 自动分班
	 * 
	 * @param studentIdArray
	 * @param classIdArray
	 */
	public void autoSplitClass(Long[] studentIdArray, Long[] classIdArray);

	/**
	 * 通过班级ID查找班级信息
	 * 
	 * @param id
	 * @return
	 */
	public AdminClass loadAdminClassById(Long id);

	/**
	 * 检查学生stdNo的可用性
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkStdNoIfExists(String code);

	/**
	 * 批量置空所选学生的班级项
	 * 
	 * @param adminClassIds
	 */
	public void batchResetAdminClass(Long[] adminClassIds);

	/**
	 * 批量计算班级人数
	 * 
	 * @param adminClassIds
	 */
	public void batchCountActualStdCount(Long[] adminClassIds);

	/**
	 * 批量增减班级人数
	 * 
	 * @param adminClassIds
	 */
	public void batchIncreaseDecreaseActualStdCount(Long[] adminClassIds,
	        Integer addend);

	/**
	 * 学生数量统计
	 * 
	 * @param student
	 * @param studentTypeIds
	 * @param departmentIds
	 * @return
	 */
	public int countStudent(Student student, String studentTypeIds,
	        String departmentIds);

	/**
	 * 学生信息统计
	 * 
	 * @param student
	 * @param limit
	 * @param groupArray
	 *            统计项
	 * @param isExactly
	 *            为null时是否确切按null查找
	 * @return
	 */
	public Collection studentInfoStat(Student student, DataRealmLimit limit,
	        String[] groupArray, Boolean isExactly);

	/**
	 * 按student中的条件搜索返回学生的学号列表
	 * 
	 * @param student
	 * @return
	 */
	public List searchStdNos(Student student);

	/**
	 * 按学籍变动类型查找相对应的变动原因
	 * 
	 * @param alterationTypeId
	 * @return
	 */
	public List getAlterationReasonList(Long alterationTypeId);
	/**
	 * 设置学生和导师的关联
	 * 
	 * @param stdNos
	 * @param tutorId
	 */
	public void batchSetStdToTutor(String stdIds, Long tutorId);

	/**
	 * 批量置空学生的导师
	 * 
	 * @param stdIds
	 */
	public void batchUnSetStdToTutor(String stdIds);

	/**
	 * 更新学生和班级间的关联
	 * 
	 * @param stdId
	 *            学生对象ID
	 * @param adminClassIdArray
	 *            该生关联的班级ID数组
	 * @param is2ndMajor
	 *            是否第二专业班级，该参数为null对应该生全部班级
	 */
	public void updateStudentAdminClass(Long stdId, Long[] adminClassIdArray,
	        Boolean is2ndMajor);

	/**
	 * 更新学生和班级间的关联
	 * 
	 * @param stdId
	 *            学生对象ID
	 * @param adminClassSet
	 *            该生关联的班级
	 * @param is2ndMajor
	 *            是否第二专业班级，该参数为null对应该生全部班级
	 */
	public void updateStudentAdminClass(Long stdId, Collection adminClasses,
	        Boolean is2ndMajor);

	/**
	 * 依据student的条件和学生类别、部门权限搜索学生，返回id的<code>List</code> added by cwx 9月6日
	 * 
	 * @param student
	 * @param studentTypeIds
	 * @param departmentIds
	 * @return
	 */
	public List getStdIdsByStd(Student student, String studentTypeIds,
	        String departmentIds);

	/**
	 * 通过学号获取一个学生的学籍信息 added by hc 9月25日
	 * 
	 * @param code
	 * @return
	 */
	public Student getStd(String code);

	/**
	 * add by yang 1月30日 批量更新学生信息
	 * 
	 * @param QueryList
	 *            学生Query的集合
	 */
	public void updateStudent(List QueryList);
}
