//$Id: TopicOpenHibernateDAO.java,v 1.4 2007/01/26 10:02:13 cwx Exp $
/*
 * 创建日期 2005-8-31
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 ＄1�7 首�1�7�项 ＄1�7 Java ＄1�7 代码样式 ＄1�7 代码模板
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
 * zq                   2007-09-18          提取了子类的学生类别子类的收集查询的过程为一
 *                                          个方法：stdTypeSearch()
 ********************************************************************************/

package com.shufe.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.limit.Page;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.commons.query.limit.SinglePage;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.util.DataRealmLimit;

/**
 * @author dell
 */
public class BasicHibernateDAO extends HibernateDaoSupport implements BasicDAO {
    
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    protected UtilDao utilDao;
    
    public void save(Object entity) {
        utilDao.saveOrUpdate(entity);
    }
    
    public void update(Object entity) {
        utilDao.saveOrUpdate(entity);
    }
    
    public void saveOrUpdate(Object entity) {
        utilDao.saveOrUpdate(entity);
    }
    
    public void remove(Object entity) {
        utilDao.remove(entity);
    }
    
    public void remove(Class entity, Serializable id) {
        utilDao.remove(utilDao.load(entity, id));
    }
    
    public Object get(Class entityClass, Serializable id) {
        return getHibernateTemplate().get(entityClass, id);
    }
    
    public Object load(Class entity, Serializable id) {
        return utilDao.load(entity, id);
        
    }
    
    public List find(final String queryName) {
        return utilDao.searchNamedQuery(queryName, (Map) null);
    }
    
    public List find(final String queryName, final Object[] argument) {
        return utilDao.searchNamedQuery(queryName, argument);
    }
    
    /**
     * @see interface BasicDAO
     */
    public List find(final String queryName, final Map parameterMap) {
        return utilDao.searchNamedQuery(queryName, parameterMap);
    }
    
    public List dynaFind(final Object entity) {
        Criteria criteria = getSession().createCriteria(entity.getClass()).add(
                Example.create(entity).excludeNone().excludeZeroes().enableLike());
        return criteria.list();
    }
    
    public Pagination dynaSearch(final Object entity, int pageNo, int pageSize) {
        Criteria criteria = getSession().createCriteria(entity.getClass()).add(
                Example.create(entity).excludeNone().excludeZeroes().enableLike());
        return OldPagination.buildOldPagination((SinglePage) utilDao.paginateCriteria(criteria,
                new PageLimit(pageNo, pageSize)));
    }
    
    public Pagination dynaSearch(Criteria criteria, int pageNo, int pageSize) {
        return OldPagination.buildOldPagination((SinglePage) utilDao.paginateCriteria(criteria,
                new PageLimit(pageNo, pageSize)));
    }
    
    public Page dynaSearch(Criteria criteria, PageLimit limit) {
        return utilDao.paginateCriteria(criteria, limit);
    }
    
    public Page search(Query query, Map params, PageLimit limit) {
        return utilDao.paginateQuery(query, params, limit);
    }
    
    public Collection search(EntityQuery query) {
        return utilDao.search(query);
    }
    
    public Pagination search(final String queryName, final Map parameterMap, int pageNo,
            int pageSize) {
        if (queryName == null)
            return null;
        Query queryByType = getSession().createQuery(queryName);
        ;
        return OldPagination.buildOldPagination((SinglePage) utilDao.paginateQuery(queryByType,
                parameterMap, new PageLimit(pageNo, pageSize)));
    }
    
    public void enbleFilter(final String filterName, final String[] name, final Object[] value) {
        Filter filter = getSession().enableFilter(filterName);
        if (name != null && value != null && name.length == value.length) {
            for (int i = 0; i < name.length; i++) {
                String parameterName = name[i];
                if (parameterName == null)
                    continue;
                Object parameterValue = value[i];
                if (parameterValue == null) {
                    filter.setParameter(parameterName, (Serializable) null);
                } else if (parameterValue.getClass().isArray()) {
                    filter.setParameterList(parameterName, (Object[]) parameterValue);
                } else if (parameterValue instanceof Collection) {
                    filter.setParameterList(parameterName, (Collection) parameterValue);
                } else {
                    filter.setParameter(parameterName, parameterValue);
                }
            }
        }
    }
    
    public void enbleFilter(final String filterName, final Map parameterMap) {
        Filter filter = getSession().enableFilter(filterName);
        if (parameterMap != null && !parameterMap.isEmpty()) {
            Set parameterNameSet = parameterMap.keySet();
            for (Iterator ite = parameterNameSet.iterator(); ite.hasNext();) {
                String parameterName = (String) ite.next();
                if (parameterName == null)
                    continue;
                Object parameterValue = parameterMap.get(parameterName);
                if (parameterValue == null) {
                    filter.setParameter(parameterName, (Serializable) null);
                } else if (parameterValue.getClass().isArray()) {
                    filter.setParameterList(parameterName, (Object[]) parameterValue);
                } else if (parameterValue instanceof Collection) {
                    filter.setParameterList(parameterName, (Collection) parameterValue);
                } else {
                    filter.setParameter(parameterName, parameterValue);
                }
            }
        }
    }
    
    public void initialize(Object entity) {
        getHibernateTemplate().initialize(entity);
    }
    
    public void addDataRealmLimt(Criteria criteria, String[] attr, DataRealmLimit limit) {
        if (null == limit || null == attr || null == limit.getDataRealm())
            return;
        if (attr.length > 0) {
            if (StringUtils.isNotEmpty(limit.getDataRealm().getStudentTypeIdSeq())
                    && StringUtils.isNotEmpty(attr[0])) {
                criteria.add(Restrictions.in(attr[0], SeqStringUtil.transformToLong(limit
                        .getDataRealm().getStudentTypeIdSeq())));
            }
        }
        if (attr.length > 1) {
            if (StringUtils.isNotEmpty(limit.getDataRealm().getDepartmentIdSeq())
                    && StringUtils.isNotEmpty(attr[1])) {
                criteria.add(Restrictions.in(attr[1], SeqStringUtil.transformToLong(limit
                        .getDataRealm().getDepartmentIdSeq())));
            }
        }
    }
    
    /**
     * 收集所有的查询学生类别的子类别
     * 
     * @param student
     * @param stdTypeIdSeq
     * @return
     */
    protected String intersectStdTypeIdSeq(StudentType stdType, String stdTypeIdSeq) {
        if (ValidEntityPredicate.INSTANCE.evaluate(stdType)) {
            stdType = (StudentType) utilDao.get(StudentType.class, stdType.getId());
            List stdTypes = stdType.getDescendants();
            stdTypes.add(stdType);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < stdTypes.size(); i++) {
                StudentType one = (StudentType) stdTypes.get(i);
                sb.append(one.getId() + ",");
            }
            stdTypeIdSeq = SeqStringUtil.intersectSeq(stdTypeIdSeq, sb.toString());
        }
        return stdTypeIdSeq;
    }
    
    public void addSortListFor(Criteria criteria, List sortList) {
        if (null != sortList) {
            for (Iterator iter = sortList.iterator(); iter.hasNext();) {
                com.ekingstar.commons.query.Order order = (com.ekingstar.commons.query.Order) iter
                        .next();
                switch (order.getDirection()) {
                    case com.ekingstar.commons.query.Order.DESC:
                        criteria.addOrder(Order.desc(order.getProperty()));
                        break;
                    default:
                        criteria.addOrder(Order.asc(order.getProperty()));
                }
            }
        }
    }
    
    /**
     * log4j debug
     * 
     * @param debugObj
     */
    public void debug(Object debugObj) {
        if (logger.isDebugEnabled()) {
            if (debugObj != null) {
                logger.debug("the debugObj is {}", debugObj);
            } else {
                logger.debug("the object is null");
            }
        }
    }
    
    /**
     * log4j info
     * 
     * @param infoObj
     */
    public void info(Object infoObj) {
        if (logger.isInfoEnabled()) {
            if (infoObj != null) {
                logger.info("the infoObj is {}", infoObj);
            } else {
                logger.info("the object is null");
            }
        }
    }
    
    /**
     * log4j error
     * 
     * @param errorObj
     */
    public void error(Object errorObj) {
        if (logger.isErrorEnabled()) {
            if (errorObj != null) {
                logger.error("the errorObj is {}", errorObj);
            } else {
                logger.error("the object is null");
            }
        }
    }
    
    public UtilDao getUtilDao() {
        return utilDao;
    }
    
    public void setUtilDao(UtilDao utilDao) {
        this.utilDao = utilDao;
    }
}
