package com.shufe.dao.course.textbook.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.textbook.TextbookDAO;
import com.shufe.model.course.textbook.Textbook;

public class TextbookDAOHibernate extends BasicHibernateDAO implements TextbookDAO {

	public List getTextbooks() {
		String sql = "from Textbook";
		Query query = this.getSession().createQuery(sql);
		return query.list();
	}

	public Textbook getTextbook(Long id) {
		Textbook dd = (Textbook) load(Textbook.class, id);
		initialize(dd.getPress());
		initialize(dd.getBookType());
		initialize(dd.getAwardLevel());
		return dd;
	}

	public void saveTextbook(Textbook textbook) throws PojoExistException {
		saveOrUpdate(textbook);
	}

	/**
	 * 通过名字查找教材<br>
	 * 如果名字为空(null或者"")则忽略名字，进行查找<br>
	 */
	public List getTextbooksByName(String name) {
		if (StringUtils.isEmpty(name)) {
			String hql = "select book.id,book.name from Textbook as book";
			List list = utilDao.searchHQLQuery(hql, (Map) null);
			return list;
		} else {
			String hql = "select book.id,book.name from Textbook as book where book.name like :name";
			Map params = new HashMap();
			params.put("name", "%" + name + "%");
			List list = utilDao.searchHQLQuery(hql, params);
			return list;
		}
	}
}
