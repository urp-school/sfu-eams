//$Id: BaseCodeDaoHibernate.java,v 1.4 2007/01/13 10:44:44 duanth Exp $
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
 * chaostone             2005-9-7         Created
 *  
 ********************************************************************************/

package com.shufe.dao.system.code.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.ConditionUtils;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.BaseCode;
import com.ekingstar.eams.system.basecode.Coder;
import com.ekingstar.eams.system.basecode.dao.BaseCodeDao;
import com.shufe.dao.BasicHibernateDAO;

/**
 * 基础代码公共数据的存取类
 * 
 * @author chaostone 2005-9-7
 */
public class BaseCodeDaoHibernate extends BasicHibernateDAO implements BaseCodeDao {

	/**
	 * @see BaseCodeDao#getCode(Class, String)
	 */
	public BaseCode getCode(Class codeClass, Long codeId) {
		return (BaseCode) load(codeClass, codeId);
	}

	/**
	 * @see BaseCodeDao#removeCode(BaseCode)
	 */
	public void removeCode(BaseCode code) {
		remove(code);
	}

	/**
	 * @see BaseCodeDao#removeCode(Class, String)
	 */
	public void removeCode(Class codeClass, Long codeId) {
		remove(codeClass, codeId);
	}

	public void saveOrUpdate(BaseCode arg0) {
		super.saveOrUpdate(arg0);
	}

	/**
	 * @see com.ekingstar.eams.system.basecode.dao.BaseCodeDao#getCodes(java.lang.String)
	 */
	public List getCodes(Class codeClass) {
		Criteria criteria = getSession().createCriteria(codeClass);
		criteria.add(Restrictions.eq("state", Boolean.TRUE));
		criteria.setCacheable(true);
		return criteria.list();
	}

	public List getCoders() {
		return getHibernateTemplate().loadAll(Coder.class);
	}

	/**
	 * @see com.ekingstar.eams.system.basecode.dao.BaseCodeDao#getCodes(BaseCode,
	 *      int, int)
	 */
	public Collection getCodes(BaseCode code) {
		EntityQuery query = new EntityQuery(code.getClass(), "code");
		query.add(ConditionUtils.extractConditions("code", code));
		query.add(new Condition("code.state=true"));
		return utilDao.search(query);
	}
}
