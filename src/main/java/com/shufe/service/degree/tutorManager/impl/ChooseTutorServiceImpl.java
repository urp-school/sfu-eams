package com.shufe.service.degree.tutorManager.impl;

import java.util.Set;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.degree.tutorManager.ChooseTutorDAO;
import com.shufe.model.degree.tutorManager.ChooseTutor;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Tutor;
import com.shufe.service.BasicService;
import com.shufe.service.degree.tutorManager.ChooseTutorService;

public class ChooseTutorServiceImpl extends BasicService implements ChooseTutorService {
	private ChooseTutorDAO chooseTutorDAO;

	public Pagination getTutorByStd(Long stdId, int pageNo, int pageSize) {
		return chooseTutorDAO.getTutorByStd(stdId, pageNo, pageSize);
	}

	public void setTutorByStd(Long stdId, Tutor tutor) {
		ChooseTutor chooseTutor = chooseTutorDAO.getChooseTutor(stdId);
		if (chooseTutor != null) {
			Set tutorSet = chooseTutor.getTutorSet();
			tutorSet.add(tutor);
			utilService.saveOrUpdate(chooseTutor);
		} else {
			ChooseTutor ct = new ChooseTutor((Student) utilService.load(Student.class, stdId),
					tutor);
			utilService.saveOrUpdate(ct);
		}
	}

	public void setChooseTutorDAO(ChooseTutorDAO chooseTutorDAO) {
		this.chooseTutorDAO = chooseTutorDAO;
	}

	public void delTutorByStd(Long stdId, Tutor tutor) {
		ChooseTutor chooseTutor = chooseTutorDAO.getChooseTutor(stdId);
		if (chooseTutor != null) {
			Set tutorSet = chooseTutor.getTutorSet();
			tutorSet.remove(tutor);
			utilService.saveOrUpdate(chooseTutor);
		}
	}

	public Pagination relationStdList(ChooseTutor ct, String studentTypeIds, String departmentIds,
			int pageNo, int pageSize) {
		return chooseTutorDAO.dynaSearch(chooseTutorDAO.constructStudentCriteria(ct,
				studentTypeIds, departmentIds, pageNo, pageSize), pageNo, pageSize);
	}

}