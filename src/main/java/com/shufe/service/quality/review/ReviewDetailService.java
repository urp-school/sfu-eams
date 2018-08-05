package com.shufe.service.quality.review;

import java.util.Collection;

import com.ekingstar.commons.query.limit.PageLimit;
import com.shufe.model.quality.review.ReviewDetail;

public interface ReviewDetailService {
	public void saveTeachCheckDetail(ReviewDetail teachCheckDetail);
	public Collection getTeachCheckDetail(ReviewDetail teachCheckDetail,PageLimit limit);
	public ReviewDetail getTeachCheckDetail(Long id);
	public void update(ReviewDetail teachCheckDetail);
}
