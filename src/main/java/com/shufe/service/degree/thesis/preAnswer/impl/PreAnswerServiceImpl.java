//$Id: PreAnswerServiceImpl.java,v 1.3 2007/01/26 10:02:13 cwx Exp $
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

package com.shufe.service.degree.thesis.preAnswer.impl;

import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.degree.thesis.preAnswer.PreAnswerDAO;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.answer.PreAnswer;
import com.shufe.model.std.Student;
import com.shufe.service.BasicService;
import com.shufe.service.degree.thesis.preAnswer.PreAnswerService;

public class PreAnswerServiceImpl extends BasicService implements PreAnswerService {
    
    private PreAnswerDAO preAnswerDAO;
    
    /**
     * @param preAnswerDAO
     *            The preAnswerDAO to set.
     */
    public void setPreAnswerDAO(PreAnswerDAO preAnswerDAO) {
        this.preAnswerDAO = preAnswerDAO;
    }
    
    public void batchUpdateTimeAndAddress(String answerIdSeq, String answerTime, String anwerAddress) {
        preAnswerDAO.batchUpdateTimeAndAddress(answerIdSeq, answerTime, anwerAddress);
    }
    
    public void batchUpdateTutorAffirm(String thesisManageIdSeq, Boolean flag) {
        preAnswerDAO.batchUpdateTutorAffirm(thesisManageIdSeq, flag);
    }
    
    /**
     * @see com.shufe.service.degree.thesis.preAnswer.PreAnswerService#getPreAnswers(com.shufe.model.degree.thesis.answer.PreAnswer,
     *      java.lang.String, java.lang.String)
     */
    public List getPreAnswers(PreAnswer preAnswer, String departIdSeq, String stdTypeIdSeq) {
        return preAnswerDAO.getPreAnswers(preAnswer, departIdSeq, stdTypeIdSeq);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.thesis.answer.AnswerService#getPropertyList(com.shufe.model.thesis.answer.PreAnswer,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    public List getPropertyList(PreAnswer preAnswer, String departIdSeq, String stdTypeIdSeq,
            String property) {
        return preAnswerDAO.getPropertyList(preAnswer, departIdSeq, stdTypeIdSeq, property);
    }
    
    /**
     * @see com.shufe.service.degree.thesis.preAnswer.PreAnswerService#getPaginationOfNoApply(com.shufe.model.degree.thesis.ThesisManage,
     *      java.lang.String, java.lang.String, int, int)
     */
    public Pagination getPaginationOfNoApply(ThesisManage thesisManage, String departIdSeq,
            String stdTypeIdSeq, int pageNo, int pageSize) {
        return preAnswerDAO.getPaginationOfNoApply(thesisManage, departIdSeq, stdTypeIdSeq, pageNo,
                pageSize);
    }
    
    /**
     * @see com.shufe.service.degree.thesis.preAnswer.PreAnswerService#getStdsOfNoApply(com.shufe.model.degree.thesis.ThesisManage,
     *      java.lang.String, java.lang.String)
     */
    public List getStdsOfNoApply(ThesisManage thesisManage, String departIdSeq, String stdTypeIdSeq) {
        return preAnswerDAO.getStdsOfNoApply(thesisManage, departIdSeq, stdTypeIdSeq);
    }
    
    /**
     * 得到学生的最后一次预答辩报告
     * 
     * @see com.shufe.service.degree.thesis.preAnswer.PreAnswerService#getLastPreAnswer(com.shufe.model.std.Student)
     */
    public PreAnswer getLastPreAnswer(Student student) {
        PreAnswer preAnswer = new PreAnswer();
        preAnswer.getThesisManage().setStudent(student);
        List stdPreAnswers = preAnswerDAO.getPreAnswers(preAnswer, student.getDepartment().getId()
                .toString(), student.getType().getId().toString());
        Integer temp = new Integer(0);
        for (Iterator iter = stdPreAnswers.iterator(); iter.hasNext();) {
            PreAnswer element = (PreAnswer) iter.next();
            if (temp.compareTo(element.getAnswerNum()) <= 0) {
                temp = element.getAnswerNum();
                preAnswer = element;
            }
        }
        return preAnswer;
    }
    
    /**
     * @see com.shufe.service.degree.thesis.preAnswer.PreAnswerService#getPreAnswerByNum(com.shufe.model.std.Student,
     *      java.lang.Integer)
     */
    public PreAnswer getPreAnswerByNum(Student student, Integer num) {
        PreAnswer preAnswer = new PreAnswer();
        preAnswer.setThesisManage(new ThesisManage());
        preAnswer.getThesisManage().setStudent(student);
        preAnswer.setAnswerNum(num);
        List stdPreAnswers = preAnswerDAO.getPreAnswers(preAnswer, student.getDepartment().getId()
                .toString(), student.getType().getId().toString());
        return (stdPreAnswers.size() > 0) ? (PreAnswer) stdPreAnswers.get(0) : preAnswer;
    }
    
}
