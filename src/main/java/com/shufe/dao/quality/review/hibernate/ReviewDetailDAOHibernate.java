package com.shufe.dao.quality.review.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;

import com.ekingstar.commons.query.limit.PageLimit;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.quality.review.ReviewDetailDAO;
import com.shufe.model.quality.review.ReviewDetail;

public class ReviewDetailDAOHibernate extends BasicHibernateDAO implements ReviewDetailDAO{

	public void saveTeachCheckDetail(ReviewDetail teachCheckDetail) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(teachCheckDetail);
	}

	public Collection getTeachCheckDetail(ReviewDetail teachCheckDetail, PageLimit limit) {
		// TODO Auto-generated method stub
		Criteria criteria=getSession().createCriteria(ReviewDetail.class);
		if(null!=limit)
			return this.dynaSearch(criteria, limit);
		else
			return criteria.list();
		
	}

	public void update(ReviewDetail teachCheckDetail) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(teachCheckDetail);
	}



}
