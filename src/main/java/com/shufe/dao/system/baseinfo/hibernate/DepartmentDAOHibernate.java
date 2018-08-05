package com.shufe.dao.system.baseinfo.hibernate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.system.baseinfo.DepartmentDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.system.baseinfo.Department;

public class DepartmentDAOHibernate extends BasicHibernateDAO implements
		DepartmentDAO {

	/**
	 * @see com.shufe.dao.system.baseinfo.DepartmentDAO#getDepartment(java.lang.String)
	 */
	public Department getDepartment(Long id) {
		return (Department) load(Department.class, id);
	}

	/**
	 * @see com.shufe.dao.system.baseinfo.DepartmentDAODWRFacade#getDepartmentsWithName(java.lang.String)
	 */
	public List getDepartmentNames(String departIdSeq) {
		if (StringUtils.isEmpty(departIdSeq))
			return Collections.EMPTY_LIST;
		else {
			String hql = "select d.id, d.name,d.engName from Department as d"
					+ " where d.state=true and d.id in (:departIds)";
			Query query = getSession().createQuery(hql);
			query.setParameterList("departIds", SeqStringUtil
					.transformToLong(departIdSeq));
			return query.list();
		}
	}

	/**
	 * @see com.shufe.dao.system.baseinfo.DepartmentDAO#getDepartments()
	 */
	public List getDepartments() {
		return genDepartCriteria(getSession(), null).list();
	}

	/**
	 * @see com.shufe.dao.system.baseinfo.DepartmentDAO#getAllDepartments()
	 */
	public List getAllDepartments() {
		Criteria criteria = genDepartCriteria(getSession(), null);
//		getSession().disableFilter("validDepart");
		criteria.add(Restrictions.eq("state", true));
		return criteria.list();
	}

	public List getAllDepartments(Department department) {
		Criteria criteria = genDepartCriteria(getSession(), null);
//		getSession().disableFilter("validDepart");
		criteria.add(Restrictions.eq("state", true));
		return criteria.list();
	}

	/**
	 * @see com.shufe.dao.system.baseinfo.DepartmentDAO#getDepartments(java.lang.String[])
	 *      使用缓存实现对已有的部门的读取
	 */
	public List getDepartments(Long[] ids) {
		List departs = new ArrayList();
		if (null != ids) {
			for (int i = 0; i < ids.length; i++) {
				Object depart = get(Department.class, ids[i]);
				if (null != depart) {
					if (Boolean.TRUE.equals(((Department) depart).getState())) {
						departs.add(depart);
					}
				} else {
					logger.warn("departIds contains error departId:" + ids[i]);
				}
			}
		}
		return departs;
	}

	/**
	 * @see com.shufe.dao.system.baseinfo.DepartmentDAO#getDepartments(com.shufe.model.system.baseinfo.Department,
	 *      int, int)
	 */
	public Pagination getDepartments(Department department, int pageNo,
			int pageSize) {
		Criteria criteria = genDepartCriteria(getSession(), department);
		return dynaSearch(criteria, pageNo, pageSize);
	}

	/**
	 * @see com.shufe.dao.system.baseinfo.DepartmentDAO#getAllDepartments(com.shufe.model.system.baseinfo.Department,
	 *      int, int)
	 */
	public Pagination getAllDepartments(Department department, int pageNo,
			int pageSize) {
		Criteria criteria = genDepartCriteria(getSession(), department);
//		getSession().disableFilter("validDepart");
		criteria.add(Restrictions.eq("state", true));
		return dynaSearch(criteria, pageNo, pageSize);
	}

	/**
	 * @see com.shufe.dao.system.baseinfo.DepartmentDAO#getDepartments(com.shufe.model.system.baseinfo.Department)
	 */
	public List getDepartments(Department department) {
		return genDepartCriteria(getSession(), department).list();
	}

	/**
	 * @see com.shufe.dao.system.baseinfo.DepartmentDAO#getAdministatives(java.lang.String[])
	 */
	public List getAdministatives(Long[] ids) {
		Criteria criteria = genDepartCriteria(getSession(), null);
		criteria.add(Restrictions.in("id", ids));
		criteria.add(Restrictions.eq("isCollege", new Boolean(false)));
		return criteria.list();
	}

	/**
	 * @see com.shufe.dao.system.baseinfo.DepartmentDAO#getColleges(java.lang.String[])
	 */
	public List getColleges(Long[] ids) {
		Criteria criteria = genDepartCriteria(getSession(), null);
		criteria.add(Restrictions.in("id", ids));
		criteria.add(Restrictions.eq("isCollege", new Boolean(true)));
		return criteria.list();
	}

	/**
	 * @see com.shufe.dao.system.baseinfo.DepartmentDAO#getColleges(java.lang.String[])
	 */
	public List getTeachDeparts(Long[] ids) {
		Criteria criteria = genDepartCriteria(getSession(), null);
		criteria.add(Restrictions.in("id", ids));
		criteria.add(Restrictions.eq("isTeaching", new Boolean(true)));
		return criteria.list();
	}

	/**
	 * @see com.shufe.dao.system.baseinfo.DepartmentDAO#removeDepartment(java.lang.String)
	 */
	public void removeDepartment(Long id) {
		remove(Department.class, id);

	}

	/**
	 * 根据对象构造一个动态查询
	 * 
	 * @param department
	 * @return
	 */
	public static Criteria genDepartCriteria(Session session,
			Department department) {
		Criteria criteria = session.createCriteria(Department.class);
		List criterions = CriterionUtils.getEntityCriterions(department);
		for (Iterator iter = criterions.iterator(); iter.hasNext();)
			criteria.add((Criterion) iter.next());
//		session.enableFilter("validDepart");
		criteria.add(Restrictions.eq("state", true));
		criteria.setCacheable(true);
		return criteria;
	}

}
