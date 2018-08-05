//$Id: PreAnswerService.java,v 1.2 2007/01/26 10:02:13 cwx Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-12-11         Created
 *  
 ********************************************************************************/

package com.shufe.service.degree.thesis.preAnswer;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.answer.PreAnswer;
import com.shufe.model.std.Student;

public interface PreAnswerService {

	public void batchUpdateTimeAndAddress(String answerIdSeq, String answerTime,
			String anwerAddress);
	
	/**
	 * 教师对于学生答辩的确认.
	 * @param thesisManageIdSeq
	 * @param flag
	 */
	public void batchUpdateTutorAffirm(String thesisManageIdSeq,Boolean flag);
	
	/**
	 * @param preAnswer
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List getPreAnswers(PreAnswer preAnswer,
			String departIdSeq, String stdTypeIdSeq);
	/**得到某个属性的list
	 * @param preAnswer
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @param property
	 * @return
	 */
	public List getPropertyList(PreAnswer preAnswer, String departIdSeq,
			String stdTypeIdSeq, String property);
	
	/**
	 * 得到未申请预答辩的学生名单
	 * @param thesisManage
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getPaginationOfNoApply(ThesisManage thesisManage,
			String departIdSeq, String stdTypeIdSeq, int pageNo,
			int pageSize);
	
	/**
	 * 得到未申请预答辩的学生名单
	 * @param thesisManage
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List getStdsOfNoApply(ThesisManage thesisManage,
			String departIdSeq, String stdTypeIdSeq);
	
	/**
	 * 得到学生最后的论文答辩情况
	 * @param student
	 * @return
	 */
	public PreAnswer getLastPreAnswer(Student student);
	
	/**
	 * 根据学生和答辩的次数得到具体的一个预答辩对象
	 * @param student
	 * @return
	 */
	public PreAnswer getPreAnswerByNum(Student student,Integer num);
}
