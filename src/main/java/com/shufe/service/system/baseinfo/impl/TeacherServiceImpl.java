//$Id: TeacherServiceImpl.java,v 1.4 2006/10/12 12:05:06 duanth Exp $
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

package com.shufe.service.system.baseinfo.impl;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.eams.system.basecode.industry.TeacherType;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.shufe.dao.system.baseinfo.TeacherDAO;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.baseinfo.TeacherAddressInfo;
import com.shufe.model.system.baseinfo.TeacherDegreeInfo;
import com.shufe.service.BasicService;
import com.shufe.service.system.baseinfo.TeacherService;
import com.shufe.util.DataRealmLimit;

/**
 * 教师信息服务实现
 * 
 * @author chaostone 2005-10-28
 */
public class TeacherServiceImpl extends BasicService implements TeacherService {

	private TeacherDAO teacherDAO = null;

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#setTeacherDAO(com.shufe.dao.system.baseinfo.TeacherDAO)
	 */
	public void setTeacherDAO(TeacherDAO teacherDAO) {
		this.teacherDAO = teacherDAO;
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#getTeacher(java.lang.Long)
	 */
	public Teacher getTeacherById(Long id) {
		try {
			return teacherDAO.getTeacherById(id);
		} catch (ObjectRetrievalFailureException e) {
			return null;
		}
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#getTeacherByTeacherNumber(java.lang.String)
	 */
	public Teacher getTeacherByNO(String teacherNO) {
		if (StringUtils.isEmpty(teacherNO))
			return null;
		else
			return teacherDAO.getTeacherByNO(teacherNO);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#getTeachers()
	 */
	public List getTeachers() {
		return teacherDAO.getTeachers();
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#getTeachers(com.shufe.model.system.baseinfo.Teacher)
	 */
	public List getTeachers(Teacher teacher) {
		return teacherDAO.getTeachers(teacher);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#getTeachersByDepartment(java.lang.String)
	 */
	public List getTeachersByDepartment(String departIds) {
		if (StringUtils.isEmpty(departIds))
			return Collections.EMPTY_LIST;
		else
			return getTeachersByDepartment(SeqStringUtil
					.transformToLong(departIds));
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#getTeachersByDepartment(java.lang.String[])
	 */
	public List getTeachersByDepartment(Long[] departIds) {
		if (null == departIds || departIds.length < 1)
			return Collections.EMPTY_LIST;
		else
			return teacherDAO.getTeachersByDepartment(departIds);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#getTeachersById(java.lang.String[])
	 */
	public List getTeachersById(Long[] teacherIds) {
		if (null == teacherIds || teacherIds.length < 1)
			return Collections.EMPTY_LIST;
		else
			return teacherDAO.getTeachersById(teacherIds);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#getTeachersById(java.util.Collection)
	 */
	public List getTeachersById(Collection teacherIds) {
		if (!teacherIds.isEmpty())
			return teacherDAO.getTeachersById(teacherIds);
		else
			return Collections.EMPTY_LIST;
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#getTeachersByNO(java.lang.String[])
	 */
	public List getTeachersByNO(String[] teacherNOs) {
		if (null == teacherNOs || teacherNOs.length < 1)
			return Collections.EMPTY_LIST;
		else
			return teacherDAO.getTeachersByNO(teacherNOs);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#getTeacherNames(java.lang.String)
	 */
	public List getTeacherNames(String teacherIdSeq) {
		if (StringUtils.isEmpty(teacherIdSeq))
			return Collections.EMPTY_LIST;
		else
			return teacherDAO.getTeacherNames(SeqStringUtil
					.transformToLong(teacherIdSeq));
	}

	public Collection getTeachers(Teacher teacher, DataRealmLimit limit,
			List sortList) {
		return teacherDAO.getTeachers(teacher, limit, sortList);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#getTeachers(com.shufe.model.system.baseinfo.Teacher,
	 *      int, int)
	 */
	public Pagination getTeachers(Teacher teacher, int pageNo, int pageSize) {
		return teacherDAO.getTeachers(teacher, pageNo, pageSize);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#getTeachers(com.shufe.model.system.baseinfo.Teacher,
	 *      java.lang.String[], int, int)
	 */
	public Pagination getTeachers(Teacher teacher, Long[] departIds,
			int pageNo, int pageSize) {
		if (pageNo < 1 || pageSize < 1)
			return new Pagination(new Result(0, Collections.EMPTY_LIST));
		else
			return teacherDAO.getTeachers(teacher, departIds, pageNo,
					pageSize);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#getTeachers(com.shufe.model.system.baseinfo.Teacher,
	 *      java.lang.String, int, int)
	 */
	public Pagination getTeachers(Teacher teacher, String departIdSeq,
			int pageNo, int pageSize) {
		if (StringUtils.isEmpty(departIdSeq) || pageNo < 1 || pageSize < 1)
			return new Pagination(new Result(0, Collections.EMPTY_LIST));
		return teacherDAO.getTeachers(teacher, SeqStringUtil
				.transformToLong(departIdSeq), pageNo, pageSize);
	}

	public List getTeachers(Teacher teacher, String departIdSeq) {
		if (StringUtils.isEmpty(departIdSeq))
			return Collections.EMPTY_LIST;
		else
			return teacherDAO.getTeachers(teacher, SeqStringUtil
					.transformToLong(departIdSeq));
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#removeTeacher(java.lang.String)
	 */
	public void removeTeacher(String id) {
		if (null == id || "".equals(id))
			return;
		try {
			teacherDAO.removeTeacher(id);
		} catch (PojoNotExistException e) {
			return;
		}
	}

	/**
	 * @see com.shufe.service.system.baseinfo.TeacherService#saveTeacher(com.shufe.model.system.baseinfo.Teacher)
	 */
	public void saveOrUpdate(Teacher teacher) throws PojoExistException {
		try {
			if (!teacher.isPO()) {
				teacher.setCreateAt(new Date(System.currentTimeMillis()));
			}
			teacher.setModifyAt(new Date(System.currentTimeMillis()));
			if (null == teacher.getIsTeaching()) {
				teacher.setIsTeaching(Boolean.TRUE);
			}
			if (null == teacher.getIsEngageFormRetire()) {
				teacher.setIsEngageFormRetire(Boolean.FALSE);
			}
			if (null == teacher.getIsConcurrent()) {
				teacher.setIsConcurrent(Boolean.FALSE);
			}
			if (null == teacher.getGender()) {
				teacher.setGender(new Gender(Gender.MALE));
			}
			if (null == teacher.getTeacherType()) {
				teacher.setTeacherType(new TeacherType(TeacherType.REGISTERTYPEID));
			}
			TeacherAddressInfo addressInfo = teacher.getAddressInfo();
			TeacherDegreeInfo degreeInfo = teacher.getDegreeInfo();
			EntityUtils.evictEmptyProperty(addressInfo);
			EntityUtils.evictEmptyProperty(degreeInfo);
			EntityUtils.evictEmptyProperty(teacher);

			teacher.setAddressInfo(addressInfo);
			teacher.setDegreeInfo(degreeInfo);
			teacherDAO.saveOrUpdate(teacher);
		} catch (DataIntegrityViolationException e) {
			throw new PojoExistException("teacher already exits:" + teacher);
		} catch (ConstraintViolationException e) {
			throw new PojoExistException("teacher already exits:" + teacher);
		}
	}

	public List getTeacherIdsByTeacher(Teacher teacher, String deparmentsq) {
		return teacherDAO.getTeacherIdsByTeacher(teacher, deparmentsq);
	}

}
