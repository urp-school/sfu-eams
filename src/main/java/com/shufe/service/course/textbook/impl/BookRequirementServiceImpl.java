package com.shufe.service.course.textbook.impl;

import java.util.Collection;
import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.limit.PageLimit;
import com.shufe.dao.course.textbook.BookRequirementDAO;
import com.shufe.model.course.textbook.BookRequirement;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.textbook.BookRequirementService;

public class BookRequirementServiceImpl extends BasicService implements
		BookRequirementService {
	private BookRequirementDAO bookRequirementDAO;

	public void setBookRequirementDAO(BookRequirementDAO bookRequirementDAO) {
		this.bookRequirementDAO = bookRequirementDAO;
	}

	public Collection getBookRequirements(BookRequirement bookRequirement,
			PageLimit limit) {
		return bookRequirementDAO.getBookRequirements(bookRequirement, limit);

	}

	public List getBookRequirements(TeachCalendar calendar,
			AdminClass adminClass) {
		EntityQuery query = new EntityQuery(BookRequirement.class, "require");
		query.add(new Condition("require.task.calendar=:calendar", calendar));
		query.add(new Condition("require.task.requirement.isGuaPai =false"));
		query.add(new Condition("require.task.teachClass.adminClasses.id="
				+ adminClass.getId()));
		return (List) utilDao.search(query);
	}

	public void saveBookRequirement(BookRequirement bookRequirement) {
		try {
			bookRequirementDAO.saveBookRequirement(bookRequirement);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public BookRequirement getBookRequirement(Long id) {
		return bookRequirementDAO.getBookRequirement(id);
	}
}
