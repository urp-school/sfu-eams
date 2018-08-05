package com.shufe.service.degree.tutorManager;

import net.ekingstar.common.detail.Pagination;

import com.shufe.model.degree.tutorManager.ChooseTutor;
import com.shufe.model.system.baseinfo.Tutor;

public interface ChooseTutorService {
	/**
	 * 得到学生选中的导师
	 * @param stdNo
	 * @return
	 */
	Pagination getTutorByStd(Long stdId,int pageNo,int pageSize);
	/**
	 * 添加导师
	 * @param stdNo
	 * @param tutor
	 */
	void setTutorByStd(Long stdId, Tutor tutor);
	/**
	 * 删除选中的导师
	 * @param stdNo
	 * @param tutor
	 */
	void delTutorByStd(Long stdId, Tutor tutor);
	Pagination relationStdList(ChooseTutor ct, String stdTypeSeq, String departDataSeq,int pageNo,int pageSize);

}
