package com.shufe.service.course.textbook;

import java.util.Collection;
import java.util.List;

import com.ekingstar.commons.query.limit.PageLimit;
import com.shufe.model.course.textbook.BookRequirement;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.calendar.TeachCalendar;

public interface BookRequirementService {

	public Collection getBookRequirements(BookRequirement requirement,
			PageLimit limit);

	/**
	 * 查找行政班中除去挂排课的所有教材需求
	 * @param calendar
	 * @param adminClass
	 * @return
	 */
	public List getBookRequirements(TeachCalendar calendar,
			AdminClass adminClass);

	public void saveBookRequirement(BookRequirement bookRequirement);

	public BookRequirement getBookRequirement(Long id);

}
