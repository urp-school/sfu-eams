package com.shufe.dao;

import java.util.List;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import com.ekingstar.commons.query.limit.SinglePage;

public class OldPagination {

	public static Pagination buildOldPagination(SinglePage page) {
		Result result = new Result(page.getTotal(), (List) page.getPageDatas());
		return new Pagination(page.getPageNo(), page.getPageSize(), result);
	}
}
