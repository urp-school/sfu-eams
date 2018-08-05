package com.shufe.service.system.baseinfo.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.BasicService;

public class AdminClassDwrServiceImpl extends BasicService {
	/**
	 * 根据参数得到可选班级<br>
	 * 使用位置：页面dwr
	 * 
	 * @param enrollYear
	 * @param stdTypeId
	 * @param departmentId
	 * @param specialityId
	 * @param aspectId
	 * @return
	 */
	public List getAdminClasses(String enrollYear, Long stdTypeId,
			Long departmentId, Long specialityId, Long aspectId) {
		EntityQuery query = new EntityQuery(AdminClass.class, "adminClass");
		if (StringUtils.isEmpty(enrollYear)) {
			return null;
		}
		query.add(new Condition("adminClass.enrollYear = (:enrollYear)",
				enrollYear));
		if (null == stdTypeId) {
			return null;
		}
		query.add(new Condition("adminClass.stdType.id = (:stdTypeId)",
				stdTypeId));
		if (null == departmentId) {
			return null;
		}
		query.add(new Condition("adminClass.department.id = (:departmentId)",
				departmentId));
		if (null == specialityId || specialityId.intValue() == 0) {
			query.add(new Condition("adminClass.speciality.id is null"));
		} else {
			query
					.add(new Condition(
							"adminClass.speciality.id = (:specialityId)",
							specialityId));
		}
		if (null == aspectId || aspectId.intValue() == 0) {
			query.add(new Condition("adminClass.aspect.id is null"));
		} else {
			query.add(new Condition("adminClass.aspect.id = (:aspectId)",
					aspectId));
		}
		return (List) utilDao.search(query);
	}

	/**
	 * 模糊查找行政班级名称
	 * 
	 * @param enrollTurn
	 * @param stdTypeId
	 * @param departId
	 * @param specialityId
	 * @param aspectId
	 * @return
	 */
	public List getAdminClassNames(String enrollTurn, Long stdTypeId,
			Long departId, Long specialityId, Long aspectId) {
		HashMap params = new HashMap();
		String hql = "select d.id, d.name from AdminClass as d "
				+ "where d.state=true";
		if (!StringUtils.isEmpty(enrollTurn)) {
			params.put("enrollTurn", enrollTurn);
			hql += " and d.enrollYear=:enrollTurn";
		}
		if (null != stdTypeId) {
			params.put("stdTypeId", stdTypeId);
			hql += " and d.stdType.id=:stdTypeId";
		}
		if (null != departId) {
			params.put("departId", departId);
			hql += " and d.department.id=:departId";
		}
		if (null != specialityId && specialityId.intValue() != 0) {
			params.put("specialityId", specialityId);
			hql += " and d.speciality.id=:specialityId";
		}
		if (null != aspectId && aspectId.intValue() != 0) {
			params.put("aspectId", aspectId);
			hql += " and d.aspect.id=:aspectId";
		}
		hql += " order by d.name";
		EntityQuery query = new EntityQuery(hql);
		query.setParams(params);

		return (List) utilDao.search(query);
	}
}
