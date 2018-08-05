package com.shufe.service.quality.review.impl;

import java.util.Collection;

import com.ekingstar.commons.query.limit.PageLimit;
import com.shufe.dao.quality.review.ReviewDetailDAO;
import com.shufe.model.quality.review.ReviewDetail;
import com.shufe.service.BasicService;
import com.shufe.service.quality.review.ReviewDetailService;

public class ReviewDetailServiceImpl extends BasicService implements
		ReviewDetailService {
	private ReviewDetailDAO reviewDetailDAO;

	public void setReviewDetailDAO(ReviewDetailDAO teachCheckDetailDAO) {
		this.reviewDetailDAO = teachCheckDetailDAO;
	}

	public void saveTeachCheckDetail(ReviewDetail teachCheckDetail) {
		reviewDetailDAO.saveTeachCheckDetail(teachCheckDetail);
	}

	public Collection getTeachCheckDetail(ReviewDetail teachCheckDetail,
			PageLimit limit) {
		return reviewDetailDAO.getTeachCheckDetail(teachCheckDetail, limit);
	}

	public ReviewDetail getTeachCheckDetail(Long id) {
		return (ReviewDetail) reviewDetailDAO.get(ReviewDetail.class, id);
	}

	public void update(ReviewDetail teachCheckDetail) {
		reviewDetailDAO.update(teachCheckDetail);
	}
}
