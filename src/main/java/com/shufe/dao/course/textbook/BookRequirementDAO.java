package com.shufe.dao.course.textbook;

import java.util.Collection;

import com.ekingstar.commons.query.limit.PageLimit;
import com.shufe.model.course.textbook.BookRequirement;

/**
 * 教材需求存取类
 * 
 * @author chaostone
 * 
 */
public interface BookRequirementDAO {

	public Collection getBookRequirements(BookRequirement requirement, PageLimit limit);

	public void saveBookRequirement(BookRequirement requirement);

	public BookRequirement getBookRequirement(Long id);
}
