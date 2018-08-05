// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UtilDaoHibernate.java

package com.ekingstar.commons.utils.persistence.hibernate;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.AbstractQuery;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.hibernate.HibernateQuerySupport;
import com.ekingstar.commons.query.limit.Page;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.commons.query.limit.SinglePage;
import com.ekingstar.commons.utils.persistence.UtilDao;

public class UtilDaoHibernate extends HibernateDaoSupport implements UtilDao {

	public UtilDaoHibernate() {
	}

	public List loadAll(Class clazz) {
		String hql = "from " + EntityUtils.getEntityType(clazz).getEntityName();
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	public List load(Class entityClass, String keyName, Object values[]) {
		if (entityClass == null || StringUtils.isEmpty(keyName)
				|| values == null || values.length == 0) {
			return Collections.EMPTY_LIST;
		} else {
			String entityName = EntityUtils.getEntityType(entityClass)
					.getEntityName();
			return load(entityName, keyName, values);
		}
	}

	public List load(String entityName, String keyName, Object values[]) {
		StringBuffer hql = new StringBuffer();
		hql.append("select entity from ").append(entityName)
				.append(" as entity where entity.").append(keyName)
				.append(" in (:keyName)");
		Map parameterMap = new HashMap(1);
		EntityQuery query;
		if (values.length < 500) {
			parameterMap.put("keyName", ((Object) (values)));
			query = new EntityQuery(hql.toString());
			query.setParams(parameterMap);
			return (List) search(query);
		}
		query = new EntityQuery(hql.toString());
		query.setParams(parameterMap);
		List rs = new ArrayList();
		for (int i = 0; i < values.length; i += 500) {
			int end = i + 500;
			if (end > values.length)
				end = values.length;
			parameterMap.put("keyName",
					((Object) (ArrayUtils.subarray(values, i, end))));
			rs.addAll(search(query));
		}

		return rs;
	}

	public Entity get(Class clazz, Serializable id) {
		return get(EntityUtils.getEntityType(clazz).getEntityName(), id);
	}

	public Entity get(String entityName, Serializable id) {
		if (-1 != entityName.indexOf('.'))
			return (Entity) getHibernateTemplate().get(entityName, id);
		String hql = "from " + entityName + " where id =:id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", id);
		List rs = query.list();
		if (!rs.isEmpty())
			return (Entity) rs.get(0);
		else
			return null;
	}

	public Entity load(Class clazz, Serializable id) {
		return (Entity) getHibernateTemplate().load(
				EntityUtils.getEntityType(clazz).getEntityName(), id);
	}

	public Collection search(AbstractQuery query) {
		return HibernateQuerySupport.search(query, getSession());
	}

	public List searchNamedQuery(String queryName, Map params) {
		Query query = getSession().getNamedQuery(queryName);
		return HibernateQuerySupport.setParameter(query, params).list();
	}

	public List searchNamedQuery(String queryName, Map params, boolean cacheable) {
		Query query = getSession().getNamedQuery(queryName);
		query.setCacheable(cacheable);
		return HibernateQuerySupport.setParameter(query, params).list();
	}

	public List searchNamedQuery(String queryName, Object params[]) {
		Query query = getSession().getNamedQuery(queryName);
		return HibernateQuerySupport.setParameter(query, params).list();
	}

	public List searchHQLQuery(String hql) {
		return getSession().createQuery(hql).list();
	}

	public List searchHQLQuery(String hql, Map params) {
		Query query = getSession().createQuery(hql);
		return HibernateQuerySupport.setParameter(query, params).list();
	}

	public List searchHQLQuery(String hql, Object params[]) {
		Query query = getSession().createQuery(hql);
		return HibernateQuerySupport.setParameter(query, params).list();
	}

	public List searchHQLQuery(String hql, Map params, boolean cacheable) {
		Query query = getSession().createQuery(hql);
		query.setCacheable(cacheable);
		return HibernateQuerySupport.setParameter(query, params).list();
	}

	public Page paginateNamedQuery(String queryName, Map params, PageLimit limit) {
		Query query = getSession().getNamedQuery(queryName);
		return paginateQuery(query, params, limit);
	}

	public Page paginateHQLQuery(String hql, Map params, PageLimit limit) {
		Query query = getSession().createQuery(hql);
		return paginateQuery(query, params, limit);
	}

	public Page paginateCriteria(Criteria criteria, PageLimit limit) {
		CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
		int totalCount = 0;
		List targetList = null;
		if (null == criteriaImpl.getProjection()) {
			criteria.setFirstResult(
					(limit.getPageNo() - 1) * limit.getPageSize())
					.setMaxResults(limit.getPageSize());
			targetList = criteria.list();
			org.hibernate.criterion.Projection projection = null;
			criteria.setFirstResult(0).setMaxResults(1);
			projection = Projections.rowCount();
			// delete all order by duantihua 2015-04-01
			Iterator iter = criteriaImpl.iterateOrderings();
			while (iter.hasNext()) {
				iter.next();
				iter.remove();
			}
			totalCount = ((Integer) criteria.setProjection(projection)
					.uniqueResult()).intValue();
		} else {
			List list = criteria.list();
			totalCount = list.size();
			criteria.setFirstResult(
					(limit.getPageNo() - 1) * limit.getPageSize())
					.setMaxResults(limit.getPageSize());
			targetList = criteria.list();
		}
		return new SinglePage(limit.getPageNo(), limit.getPageSize(),
				totalCount, targetList);
	}

	public void evict(Object entity) {
		getSession().evict(entity);
	}

	public int executeUpdateHql(String queryStr, Object argument[]) {
		Query query = getSession().createQuery(queryStr);
		return HibernateQuerySupport.setParameter(query, argument)
				.executeUpdate();
	}

	public int executeUpdateHql(String queryStr, Map parameterMap) {
		Query query = getSession().createQuery(queryStr);
		return HibernateQuerySupport.setParameter(query, parameterMap)
				.executeUpdate();
	}

	public int executeUpdateNamedQuery(String queryName, Map parameterMap) {
		Query query = getSession().getNamedQuery(queryName);
		return HibernateQuerySupport.setParameter(query, parameterMap)
				.executeUpdate();
	}

	public int executeUpdateNamedQuery(String queryName, Object argument[]) {
		Query query = getSession().getNamedQuery(queryName);
		return HibernateQuerySupport.setParameter(query, argument)
				.executeUpdate();
	}

	public Blob createBlob(InputStream inputStream, int length) {
		BufferedInputStream imageInput = new BufferedInputStream(inputStream);
		return Hibernate.createBlob(imageInput, length);
	}

	public Blob createBlob(InputStream inputStream) {
		BufferedInputStream imageInput = new BufferedInputStream(inputStream);
		try {
			return Hibernate.createBlob(imageInput);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Clob createClob(String str) {
		return Hibernate.createClob(str);
	}

	public void refresh(Object entity) {
		getSession().refresh(entity);
	}

	public void initialize(Object entity) {
		getHibernateTemplate().initialize(entity);
	}

	public Page paginateQuery(Query query, Map params, PageLimit limit) {
		HibernateQuerySupport.setParameter(query, params);
		query.setFirstResult((limit.getPageNo() - 1) * limit.getPageSize())
				.setMaxResults(limit.getPageSize());
		List targetList = query.list();
		String queryStr = buildCountQueryStr(query);
		Query countQuery = null;
		if (query instanceof SQLQuery)
			countQuery = getSession().createSQLQuery(queryStr);
		else
			countQuery = getSession().createQuery(queryStr);
		HibernateQuerySupport.setParameter(countQuery, params);
		return new SinglePage(limit.getPageNo(), limit.getPageSize(),
				((Number) countQuery.uniqueResult()).intValue(), targetList);
	}

	public void saveOrUpdate(Object entity) {
		if (entity instanceof Collection) {
			Collection entities = (Collection) entity;
			for (Iterator iter = entities.iterator(); iter.hasNext();) {
				Object obj = iter.next();
				if (obj instanceof HibernateProxy)
					getHibernateTemplate().saveOrUpdate(obj);
				else
					getHibernateTemplate().saveOrUpdate(
							EntityUtils.getEntityType(obj.getClass())
									.getEntityName(), obj);
			}

		} else if (entity instanceof HibernateProxy)
			getHibernateTemplate().saveOrUpdate(entity);
		else
			getHibernateTemplate().saveOrUpdate(
					EntityUtils.getEntityType(entity.getClass())
							.getEntityName(), entity);
	}

	public void saveOrUpdate(String entityName, Object entity) {
		if (entity instanceof Collection) {
			Collection entities = (Collection) entity;
			for (Iterator iter = entities.iterator(); iter.hasNext(); getHibernateTemplate()
					.saveOrUpdate(entityName, iter.next()))
				;
		} else {
			getHibernateTemplate().saveOrUpdate(entityName, entity);
		}
	}

	public void remove(Collection entities) {
		for (Iterator iter = entities.iterator(); iter.hasNext(); getHibernateTemplate()
				.delete(iter.next()))
			;
	}

	public void remove(Object entity) {
		getHibernateTemplate().delete(entity);
	}

	public boolean remove(Class clazz, String attr, Object values[]) {
		if (clazz == null || StringUtils.isEmpty(attr) || values == null
				|| values.length == 0) {
			return false;
		} else {
			String entityName = EntityUtils.getEntityType(clazz)
					.getEntityName();
			StringBuffer hql = new StringBuffer();
			hql.append("delete from ").append(entityName).append(" where ")
					.append(attr).append(" in (:ids)");
			Map parameterMap = new HashMap(1);
			parameterMap.put("ids", ((Object) (values)));
			return executeUpdateHql(hql.toString(), parameterMap) > 0;
		}
	}

	public boolean remove(Class entityClass, String attr, Collection values) {
		return remove(entityClass, attr, values.toArray());
	}

	public boolean remove(Class clazz, Map keyMap) {
		if (clazz == null || keyMap == null || keyMap.isEmpty())
			return false;
		String entityName = EntityUtils.getEntityType(clazz).getEntityName();
		StringBuffer hql = new StringBuffer();
		hql.append("delete from ").append(entityName).append(" where ");
		Set keySet = keyMap.keySet();
		Map params = new HashMap();
		for (Iterator ite = keySet.iterator(); ite.hasNext();) {
			String keyName = ite.next().toString();
			Object keyValue = keyMap.get(keyName);
			String paramName = keyName.replace('.', '_');
			params.put(paramName, keyValue);
			if (keyValue.getClass().isArray()
					|| (keyValue instanceof Collection))
				hql.append(keyName).append(" in (:").append(paramName)
						.append(") and ");
			else
				hql.append(keyName).append(" = :").append(paramName)
						.append(" and ");
		}

		hql.append(" (1=1) ");
		return executeUpdateHql(hql.toString(), params) > 0;
	}

	private String buildCountQueryStr(Query query) {
		String queryStr = "select count(*) ";
		if (query instanceof SQLQuery) {
			queryStr = queryStr + "from (" + query.getQueryString() + ")";
		} else {
			String lowerCaseQueryStr = query.getQueryString().toLowerCase();
			String selectWhich = lowerCaseQueryStr.substring(0, query
					.getQueryString().indexOf("from"));
			int indexOfDistinct = selectWhich.indexOf("distinct");
			int indexOfFrom = lowerCaseQueryStr.indexOf("from");
			if (-1 != indexOfDistinct)
				if (StringUtils.contains(selectWhich, ","))
					queryStr = "select count("
							+ query.getQueryString().substring(indexOfDistinct,
									query.getQueryString().indexOf(",")) + ")";
				else
					queryStr = "select count("
							+ query.getQueryString().substring(indexOfDistinct,
									indexOfFrom) + ")";
			queryStr = queryStr + query.getQueryString().substring(indexOfFrom);
		}
		return queryStr;
	}
}
