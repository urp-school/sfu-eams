/*
 * 创建日期 2005-9-6
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 ￄ1�7首�?ￄ1�7ￄ1�7Java ￄ1�7代码样式 ￄ1�7代码模板
 */
package com.shufe.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.Criteria;
import org.hibernate.Query;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.limit.Page;
import com.ekingstar.commons.query.limit.PageLimit;

/**
 * @author dell
 */
public interface BasicDAO {
	/**
	 * 保存对象
	 * 
	 * @param entity
	 */
	public void save(Object entity);

	/**
	 * 更新对象
	 * 
	 * @param entity
	 */
	public void update(Object entity);

	/**
	 * 保存或更新一个对
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(Object entity);

	/**
	 * 删除对象
	 * 
	 * @param entity
	 */
	public void remove(Object entity);

	/**
	 * 根据id删除对象
	 * 
	 * @param entity
	 * @param id
	 */
	public void remove(Class entity, Serializable id);

	/**
	 * 根据id得到对象
	 * 
	 * @param entity
	 * @param id
	 * @return
	 */
	public Object load(Class entity, Serializable id);

	/**
	 * 根据id得到对豄17.<br>
	 * 对象不存在时，返回null
	 * 
	 * @param entity
	 * @param id
	 * @return
	 */
	public Object get(Class entity, Serializable id);

	/**
	 * 执行无参数命名HQL(不分
	 * 
	 * @param query
	 * @return List
	 */
	public List find(String queryName);

	/**
	 * 执行命名HQL(不分,参数类型与?具体hql顺序匹配
	 * 
	 * @param queryName
	 * @param argument
	 * @return List
	 */
	public List find(final String queryName, final Object[] argument);

	/**
	 * 执行命名HQL(不分,参数类型与?和具体hql按照Map中的key名字匹配
	 * 
	 * @param queryName
	 * @param parameterMap
	 * @return List
	 */
	public List find(final String queryName, final Map parameterMap);

	/**
	 * 根据实例的?动?查询对象(不管理实例中关联的其他复杂对
	 * 
	 * @param entity
	 * @return List
	 */
	public List dynaFind(final Object entity);

	/**
	 * 执行动?查询(分页)
	 * 
	 * @param entity
	 * @param pageNo
	 * @param pageSize
	 * @return Pagination
	 */
	public Pagination dynaSearch(final Object entity, int pageNo, int pageSize);

	/**
	 * @see interface BasicDAO
	 */
	public Pagination dynaSearch(Criteria criteria, int pageNo, int pageSize);

	public Page dynaSearch(Criteria criteria, PageLimit limit);

	public Page search(Query query, Map params, PageLimit limit);

	/**
	 * 查询实体
	 * 
	 * @param query
	 * @return
	 */
	public Collection search(EntityQuery query);

	/**
	 * 执行分页查询,参数类型与?和具体hql按照Map中的key名字匹配,根据queryType来确定是命名HQL,hql,nativte sql
	 * 
	 * @param queryName
	 * @param parameterMap
	 * @param pageNo
	 * @param pageSize
	 * @return Pagination
	 */
	public Pagination search(final String queryName, final Map parameterMap, int pageNo,
			int pageSize);

	/**
	 * 启用过滤
	 * 
	 * @param filterName
	 * @param name
	 * @param value
	 */
	public void enbleFilter(final String filterName, final String[] name, final Object[] value);

	/**
	 * 启用过滤
	 * 
	 * @param filterName
	 * @param parameterMap
	 */
	public void enbleFilter(final String filterName, final Map parameterMap);

	/**
	 * @param entity
	 */
	public void initialize(Object entity);
}