package com.shufe.dao.degree.tutorManager;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.Criteria;

import com.shufe.dao.BasicDAO;
import com.shufe.model.degree.tutorManager.ChooseTutor;

public interface ChooseTutorDAO extends BasicDAO {
	/**
	 * 得到学生选中的导师
	 * 
	 * @param stdNo
	 * @return
	 */
	Pagination getTutorByStd(Long stdId, int pageNo, int pageSize);

	ChooseTutor getChooseTutor(Long stdId);

	Criteria constructStudentCriteria(ChooseTutor ct, String studentTypeIds, String departmentIds,
			int pageNo, int pageSize);

}
