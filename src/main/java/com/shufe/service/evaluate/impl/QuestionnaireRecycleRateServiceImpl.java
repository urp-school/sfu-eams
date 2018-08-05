//$Id: QuestionnaireRecycleRateServiceImpl.java,v 1.7 2007/01/11 08:55:33 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-10-25         Created
 *  
 ********************************************************************************/

package com.shufe.service.evaluate.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.shufe.dao.evaluate.QuestionnaireRecycleRateDAO;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.evaluate.QuestionnaireRecycleRateService;
import com.shufe.service.system.calendar.TeachCalendarService;

/**
 * @author hj
 *2005-10-25
 *QuestionnaireRecycleRateServiceImpl.java has been created
 */
public class QuestionnaireRecycleRateServiceImpl extends BasicService implements
        QuestionnaireRecycleRateService {
    
    private QuestionnaireRecycleRateDAO questionnaireRecycleRateDAO;
    private TeachCalendarService teachCalendarService;
    

    
    
	/**
     * @return 返回 questionnaireRecycleRateDAO.
     */
    public QuestionnaireRecycleRateDAO getQuestionnaireRecycleRateDAO() {
        return questionnaireRecycleRateDAO;
    }
    /**
     * @param questionnaireRecycleRateDAO 要设置的 questionnaireRecycleRateDAO.
     */
    public void setQuestionnaireRecycleRateDAO(
            QuestionnaireRecycleRateDAO questionnaireRecycleRateDAO) {
        this.questionnaireRecycleRateDAO = questionnaireRecycleRateDAO;
    }
    
	/**
	 * @param teachCalendarService The teachCalendarService to set.
	 */
	public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
		this.teachCalendarService = teachCalendarService;
	}
    /* （非 Javadoc）
     * @see com.shufe.service.evaluate.QuestionnaireRecycleRateService#getTotlePersonAmount(java.lang.String, com.shufe.model.system.calendar.TeachCalendar, java.lang.String)
     */
    public List getTotleEvaluateAmount(String collegeId,Collection teachCalendarId, String studentTypId) {
        
        return questionnaireRecycleRateDAO.getTotlePersonAmount(collegeId,teachCalendarId,studentTypId);
    }
    /* （非 Javadoc）
     * @see com.shufe.service.evaluate.QuestionnaireRecycleRateService#getHasEvlautaeAmount(java.lang.String, java.lang.String)
     */
    public List getHasEvlautaeDatas(String collegeIdSeq,
			String studentTypeIdSeq, Collection teachCalendars) {
		return questionnaireRecycleRateDAO.getHasEvlautaeData(collegeIdSeq,
				studentTypeIdSeq, teachCalendars);
	}
	/**
	 * @see com.shufe.service.evaluate.QuestionnaireRecycleRateService#getResultNumbers(java.lang.String, java.lang.String, Collection)
	 */
	public List getResultNumbers(String collegeIdSeq, String stdTypeIdSeq, Collection teachCalendars) {
		return questionnaireRecycleRateDAO.getStatisticResults(collegeIdSeq, stdTypeIdSeq, teachCalendars);
	}
	/**
	 * @see com.shufe.service.evaluate.QuestionnaireRecycleRateService#getTotleNumber(java.lang.String, java.lang.String)
	 */
	public List getTotleNumber(String collegeId, String studentTypeId,Collection calendars) {
		return questionnaireRecycleRateDAO.getTotleNumber(collegeId, studentTypeId,calendars);
	}
	/**
	 * @see com.shufe.service.evaluate.QuestionnaireRecycleRateService#getRecycleRateObject(java.lang.String, java.util.Calendar, java.lang.String)
	 */
	public List getRecycleRateObject(String departmentIds, String studentTypeIds, Collection teachCalendars) {
		return questionnaireRecycleRateDAO.getRecycleRateObjects(departmentIds, studentTypeIds, teachCalendars);
	}
	/**
	 * @see com.shufe.service.evaluate.QuestionnaireRecycleRateService#getStudentTypeLists(java.lang.String)
	 */
	public List getStudentTypeLists(String departmentIds) {
		return questionnaireRecycleRateDAO.getStudentTypeLists(departmentIds);
	}
	/**
	 * @see com.shufe.service.evaluate.QuestionnaireRecycleRateService#getTeachCalendarList(java.lang.String)
	 */
	public List getCurrentTeachCalendarList(String studentTypeIds) {
		studentTypeIds = "," + studentTypeIds+",";
		List teachCalendars = teachCalendarService.getCurTeachCalendars();
		List newTeachCalendars = new ArrayList();
		for (Iterator iter = teachCalendars.iterator(); iter.hasNext();) {
			TeachCalendar teachCalendar = (TeachCalendar) iter.next();
			if (studentTypeIds.indexOf(","
					+ teachCalendar.getStudentType().getId() + ",") != -1) {
				newTeachCalendars.add(teachCalendar);
			}
		}
		return newTeachCalendars;
	}
	/**
	 * @see com.shufe.service.evaluate.QuestionnaireRecycleRateService#getAllRateByCourseTask(java.lang.String, java.lang.String, java.util.Collection)
	 */
	public List getAllRateByCourseTask(String departmentIds, String studentTypeIds, Collection teachCalendarSet) {
		return questionnaireRecycleRateDAO.getAllRateByCourseTask(departmentIds, studentTypeIds, teachCalendarSet);
	}
	

	/**
	 * @see com.shufe.service.evaluate.QuestionnaireRecycleRateService#getDetailInfoOfTask(java.lang.String, java.lang.String, java.lang.String, java.util.Collection, java.lang.String)
	 */
	public List getDetailInfoOfTask(String departmentIds, String departmentId, String studentTypeIds, Collection teachCalendarSet, String studentOrTeacher) {
		return questionnaireRecycleRateDAO.getDetailInfoOfTask(departmentIds, departmentId, studentTypeIds, teachCalendarSet, studentOrTeacher);
	}
}
