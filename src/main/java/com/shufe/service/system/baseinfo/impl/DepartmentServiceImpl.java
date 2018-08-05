package com.shufe.service.system.baseinfo.impl;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.dao.system.baseinfo.DepartmentDAO;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.service.BasicService;
import com.shufe.service.system.baseinfo.DepartmentService;

public class DepartmentServiceImpl extends BasicService implements
		DepartmentService {
	private DepartmentDAO departmentDAO = null;

	/**
	 * @see com.shufe.service.system.baseinfo.DepartmentService#setDepartmentDAO(com.shufe.dao.system.baseinfo.DepartmentDAO)
	 */
	public void setDepartmentDAO(DepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	public Department getDepartment(Long id) {
		if (null == id)
			return null;
		try {
			return departmentDAO.getDepartment(id);
		} catch (ObjectRetrievalFailureException e) {
			return null;
		}
	}

	public List getColleges() {
		Department college = new Department();
		college.setIsCollege(Boolean.TRUE);
		List departs = departmentDAO.getDepartments(college);
		Collections.sort(departs, new PropertyComparator("code"));
		return departs;
	}

	public List getDepartments() {
		return departmentDAO.getDepartments();
	}

	public List getAdministatives() {
		Department college = new Department();
		college.setIsCollege(Boolean.FALSE);
		return departmentDAO.getDepartments(college);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.DepartmentService#getAllDepartments()
	 */
	public List getAllDepartments() {
		return departmentDAO.getAllDepartments();
	}

	/**
	 * @see com.shufe.service.system.baseinfo.DepartmentService#getDepartments(java.lang.String[])
	 */
	public List getDepartments(Long[] ids) {
		if (null == ids || ids.length < 1)
			return Collections.EMPTY_LIST;
		else
			return departmentDAO.getDepartments(ids);
	}

	public List getDepartments(Department department) {
		return departmentDAO.getDepartments(department);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.DepartmentService#getAllAdministatives()
	 */
	public List getAllAdministatives() {
		Department depart = new Department();
		depart.setIsTeaching(new Boolean(true));
		return getAllDepartments(depart);
	}

	public List getAllDepartments(Department department) {
		return departmentDAO.getAllDepartments(department);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.DepartmentService#getAdministatives(java.lang.String)
	 */
	public List getAdministatives(String idSeq) {
		return getAdministatives(SeqStringUtil.transformToLong(idSeq));
	}

	/**
	 * @see com.shufe.service.system.baseinfo.DepartmentService#getAdministatives(java.lang.String[])
	 */
	public List getAdministatives(Long[] ids) {
		if (null == ids || ids.length < 1)
			return null;
		return departmentDAO.getAdministatives(ids);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.DepartmentService#getAllColleges()
	 */
	public List getAllColleges() {
		Department depart = new Department();
		depart.setIsCollege(new Boolean(true));
		return getDepartments(depart);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.DepartmentService#getColleges(java.lang.String)
	 */
	public List getColleges(String idSeq) {
		return getColleges(SeqStringUtil.transformToLong(idSeq));
	}

	/**
	 * @see com.shufe.service.system.baseinfo.DepartmentService#getColleges(java.lang.String[])
	 */
	public List getColleges(Long[] ids) {
		if (null == ids || ids.length < 1)
			return null;
		return departmentDAO.getColleges(ids);
	}

	public List getTeachDeparts(String idSeq) {
		if (StringUtils.isEmpty(idSeq))
			return Collections.EMPTY_LIST;
		else {
			return departmentDAO.getTeachDeparts(SeqStringUtil
					.transformToLong(idSeq));
		}
	}

	public Pagination getDepartments(Department department, int pageNo,
			int pageSize) {
		return departmentDAO.getDepartments(department, pageNo, pageSize);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.DepartmentService#getAllDepartments(com.shufe.model.system.baseinfo.Department,
	 *      int, int)
	 */
	public Pagination getAllDepartments(Department department, int pageNo,
			int pageSize) {
		return departmentDAO
				.getAllDepartments(department, pageNo, pageSize);
	}

	public Collection getRelatedDeparts(String stdTypeIds) {
		EntityQuery entityQuery = new EntityQuery(Department.class, "depart");
		entityQuery.join("depart.stdTypes", "stdType").add(
				new Condition("stdType.id in (:stdTypeIds)", SeqStringUtil
						.transformToLong(stdTypeIds)));
		entityQuery.setSelect("select distinct depart ");
		return utilDao.search(entityQuery);
	}

	public void saveOrUpdate(Department department) throws PojoExistException {
		try {
			if (!department.isPO()) {
				department.setCreateAt(new Date(System.currentTimeMillis()));
			}
			department.setModifyAt(new Date(System.currentTimeMillis()));
			departmentDAO.saveOrUpdate(department);
		} catch (DataIntegrityViolationException e) {
			throw new PojoExistException("department already exits:"
					+ department);
		} catch (ConstraintViolationException e) {
			throw new PojoExistException("department already exits:"
					+ department);
		}
	}

	public void removeDepartment(Long id) {
		if (null == id)
			return;
		try {
			departmentDAO.removeDepartment(id);
		} catch (PojoNotExistException e) {
			return;
		}

	}

	/**
	 * )
	 * 
	 * @see com.shufe.service.system.baseinfo.StudentTypeService#getStudentTypes(java.lang.String)
	 */
	public List getDepartments(String idSeq) {
		return this.getDepartments(SeqStringUtil.transformToLong(idSeq));
	}

	public List getTeachDeparts() {
		EntityQuery query = new EntityQuery(Department.class, "department");
		query
				.add(new Condition("department.isTeaching=:teaching",
						Boolean.TRUE));
		query.add(new Condition("department.state=true"));
		return (List) utilDao.search(query);
	}

}
