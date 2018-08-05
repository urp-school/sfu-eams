package com.shufe.dao.course.textbook.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.query.limit.PageLimit;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.textbook.BookRequirementDAO;
import com.shufe.model.course.textbook.BookRequirement;

public class BookRequirementDAOHibernate extends BasicHibernateDAO implements
		BookRequirementDAO {

	public Collection getBookRequirements(BookRequirement requirement,
			PageLimit limit) {
		Criteria criteria = getSession().createCriteria(BookRequirement.class);
		criteria.createCriteria("task", "task");
		criteria.createCriteria("task.course", "course");
		criteria.createCriteria("textbook", "textbook");
		if (requirement != null) {
			criteria.add(Restrictions.like("course.name", requirement.getTask()
					.getCourse().getName(), MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("task.teachClass.name", requirement
					.getTask().getTeachClass().getName(), MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("textbook.name", requirement
					.getTextbook().getName(), MatchMode.ANYWHERE));
		}
		if (null != limit)
			return dynaSearch(criteria, limit);
		else
			return criteria.list();

	}

	public void saveBookRequirement(BookRequirement requirement) {
		save(requirement);
	}

	public BookRequirement getBookRequirement(Long id) {
		String hql = "from BookRequirement where id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", id);
		List rs = query.list();
		return (BookRequirement) (rs.isEmpty() ? null : rs.get(0));
	}

}
