package com.shufe.dao.quality.review;

import java.util.Collection;

import com.ekingstar.commons.query.limit.PageLimit;
import com.shufe.dao.BasicDAO;
import com.shufe.model.quality.review.ReviewDetail;

public interface ReviewDetailDAO extends BasicDAO {
	public void saveTeachCheckDetail(ReviewDetail teachCheckDetail);

	public Collection getTeachCheckDetail(ReviewDetail teachCheckDetail, PageLimit limit);

	public void update(ReviewDetail teachCheckDetail);
}
