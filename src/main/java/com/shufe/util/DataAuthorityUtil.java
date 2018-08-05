//$Id: DataAuthorityUtil.java,v 1.3 2006/12/05 01:32:34 duanth Exp $
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
 * chaostone             2005-12-3         Created
 *  
 ********************************************************************************/

package com.shufe.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.shufe.service.course.arrange.task.predicate.TaskGroupInDataRealmPredicate;
import com.shufe.service.system.baseinfo.impl.ClassroomInDataRealmPredicate;

/**
 * 数据权限工具类
 * 
 * @author chaostone
 * 
 */
public class DataAuthorityUtil {

	private static Map predicates = new HashMap();

	public static DataAuthorityPredicate predicateWithSimpleName;

	public static DataAuthorityPredicate departPpredicate;

	static {
		predicateWithSimpleName = new DataAuthorityPredicate("", "", "stdType",
				"department");
		departPpredicate = new DataAuthorityPredicate("", "", "", "department");
		DataAuthorityPredicate taskForTeachDepartPredicate = new DataAuthorityPredicate(
				"", "", "teachClass.stdType", "arrangeInfo.teachDepart");
		predicates.put("Classroom", new ClassroomInDataRealmPredicate());
		predicates.put("TaskGroup", new TaskGroupInDataRealmPredicate());
		predicates.put("AdminClass", predicateWithSimpleName);
		predicates.put("Teacher", departPpredicate);
		predicates.put("Speciality", predicateWithSimpleName);
		predicates.put("Course", predicateWithSimpleName);
		predicates.put("TeachPlan", predicateWithSimpleName);
		predicates.put("TeachTaskForTeachDepart", taskForTeachDepartPredicate);
		predicates.put("Student", new DataAuthorityPredicate("", "", "type",
				"department"));
	}

	/**
	 * 判断实体对象是否在权限范围内.<br>
	 * 
	 * @param category
	 *            要判别的权限的数据种类.<br>
	 *            （没有使用数据实体的类名，应为由像CGLIB之类的库会伪装成代码中引用的类型）
	 * @param entity
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @return
	 */
	public static boolean isInDataRealm(String category, Object entity,
			String stdTypeIdSeq, String departIdSeq) {
		return getPredicate(category, entity, stdTypeIdSeq, departIdSeq)
				.evaluate(entity);
	}

	public static boolean isInDataRealm(DataAuthorityPredicate predicate,
			Object entity, String stdTypeIdSeq, String departIdSeq) {
		predicate.setStdTypeDataRealm(stdTypeIdSeq);
		predicate.setDepartDataRealm(departIdSeq);
		return predicate.evaluate(entity);
	}

	/**
	 * @param entity
	 * @return
	 */
	private static DataAuthorityPredicate getPredicate(String category,
			Object entity, String stdTypeIdSeq, String departIdSeq) {
		DataAuthorityPredicate predicate = (DataAuthorityPredicate) predicates
				.get(category);
		if (null == predicate)
			throw new RuntimeException("un registed predicate for "
					+ entity.getClass().getName());
		predicate.setStdTypeDataRealm(stdTypeIdSeq);
		predicate.setDepartDataRealm(departIdSeq);
		return predicate;
	}

	/**
	 * @param statWhat
	 * @return
	 */
	private static DataAuthorityPredicate getPredicate(String predicateName,
			String stdTypeIdSeq, String departIdSeq) {
		DataAuthorityPredicate predicate = (DataAuthorityPredicate) predicates
				.get(predicateName);
		if (null == predicate)
			throw new RuntimeException("un registed predicate for "
					+ predicateName);
		predicate.setStdTypeDataRealm(stdTypeIdSeq);
		predicate.setDepartDataRealm(departIdSeq);
		return predicate;
	}

	/**
	 * 将目标实体列表，按照数据权限进行过滤
	 * 
	 * @param entities
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @return
	 */
	public static void filter(Collection entities, String category,
			String stdTypeIdSeq, String departIdSeq) {
		if (null == entities || entities.isEmpty())
			return;
		CollectionUtils.filter(entities, getPredicate(category, entities
				.iterator().next(), stdTypeIdSeq, departIdSeq));
	}

	/**
	 * 将目标实体列表，按照数据权限进行过滤
	 * 
	 * @param entities
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @return
	 */
	public static void filter(String predicateName, Collection entities,
			String stdTypeIdSeq, String departIdSeq) {
		if (null == entities || entities.isEmpty())
			return;
		CollectionUtils.filter(entities, getPredicate(predicateName,
				stdTypeIdSeq, departIdSeq));
	}

	/**
	 * 不改变目标实体列表，返回按照数据权限进行过滤的新结果
	 * 
	 * @param entities
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @return
	 */
	public static Collection select(String predicateName, Collection entities,
			String stdTypeIdSeq, String departIdSeq) {
		return CollectionUtils.select(entities, getPredicate(predicateName,
				stdTypeIdSeq, departIdSeq));
	}

	/**
	 * 不改变目标实体列表，返回按照数据权限进行过滤的新结果
	 * 
	 * @param entities
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @return
	 */
	public static Collection select(Collection entities, String category,
			String stdTypeIdSeq, String departIdSeq) {
		return CollectionUtils.select(entities, getPredicate(category, entities
				.iterator().next(), stdTypeIdSeq, departIdSeq));
	}

	public static void register(Class entityClass,
			DataAuthorityPredicate predicate) {
		predicates.put(entityClass.getName(), predicate);
	}

	public static void register(String predicateName,
			DataAuthorityPredicate predicate) {
		predicates.put(predicateName, predicate);
	}
}
