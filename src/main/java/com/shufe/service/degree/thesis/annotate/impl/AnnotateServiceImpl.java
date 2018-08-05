//$Id: AnnotateServiceImpl.java,v 1.2 2006/12/06 03:29:09 cwx Exp $
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
 * chenweixiong              2006-11-7         Created
 *  
 ********************************************************************************/

package com.shufe.service.degree.thesis.annotate.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.degree.thesis.annotate.AnnotateDAO;
import com.shufe.model.degree.thesis.annotate.Annotate;
import com.shufe.model.degree.thesis.annotate.ThesisAnnotateBook;
import com.shufe.service.BasicService;
import com.shufe.service.degree.thesis.annotate.AnnotateService;
import com.shufe.service.util.stat.StatUtils;

public class AnnotateServiceImpl extends BasicService implements
		AnnotateService {

	private AnnotateDAO annotateDAO;
	/**
	 * @see com.shufe.service.degree.thesis.annotate.AnnotateService#batchUpdateAnnotateDoubleBlind(java.lang.Boolean, java.lang.String)
	 */
	public void batchUpdateAnnotateDoubleBlind(Boolean isAffirm,
			String thesisManageSeq) {
		annotateDAO.batchUpdateAnnotateDoubleBlind(isAffirm, thesisManageSeq);
	}
	/**
	 * @param annotateDAO The annotateDAO to set.
	 */
	public void setAnnotateDAO(AnnotateDAO annotateDAO) {
		this.annotateDAO = annotateDAO;
	}
	/*得到论文评阅书的分页对象
	 * @see com.shufe.service.thesis.annotate.AnnotateService#getAnnotateBookPagination(com.shufe.model.thesis.ThesisManage, java.lang.String, java.lang.String, int, int)
	 */
	public Pagination getAnnotateBookPagination(ThesisAnnotateBook thesisAnnotateBook,
			String departmentIdSeq, String stdTypeIdSeq, int pageNo,
			int pageSize, String flag) {
		return annotateDAO.getAnnotateBookPagination(thesisAnnotateBook,
				departmentIdSeq, stdTypeIdSeq, pageNo, pageSize, flag);
	}
	/**批量计算平均成绩
	 * @see com.shufe.service.degree.thesis.annotate.AnnotateService#batchUpdateAvgMark(java.lang.String, java.lang.String)
	 */
	public void batchUpdateAvgMark(String startNo,
			String endNo, String departmentIdSeq, String stdTypeIdSeq) {
		List tempList = annotateDAO.getAllMarkOfAnnotates(startNo,
				endNo, departmentIdSeq, stdTypeIdSeq);
		List updateList = new ArrayList();
		for (Iterator iter = tempList.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			Float sumMark = (Float) element[0];
			Annotate annotate = (Annotate) element[1];
			Set set = annotate.getAnnotateBooks();
			if (null!=sumMark&&null != set && set.size() > 0) {
				annotate
						.setAvgMark(new Float(sumMark.floatValue() / set.size()));
				updateList.add(annotate);
			}
		}
		utilService.saveOrUpdate(updateList);
	}
	/**根据条件得到所有的list
	 * @see com.shufe.service.degree.thesis.annotate.AnnotateService#getAnnotateBooks(com.shufe.model.degree.thesis.annotate.ThesisAnnotateBook, java.lang.String, java.lang.String, String)
	 */
	public List getAnnotateBooks(ThesisAnnotateBook thesisAnnotateBook, String departmentIdSeq, String stdTypeIdSeq, String flag) {
		return annotateDAO.getAnnotateBookList(thesisAnnotateBook, departmentIdSeq, stdTypeIdSeq, flag);
	}
	/**
	 * @see com.shufe.service.degree.thesis.annotate.AnnotateService#getStdNumOfAvgMark(com.shufe.model.degree.thesis.annotate.ThesisAnnotateBook, java.lang.String, java.lang.String)
	 */
	public List getStdNumOfAvgMark(ThesisAnnotateBook thesisAnnotateBook, String departmentIdSeq, String stdTypeIdSeq) {
		List tempList =annotateDAO.getStdNumOfAvgMark(thesisAnnotateBook, departmentIdSeq, stdTypeIdSeq);
		List returnList=new ArrayList();
		for (Iterator iter = tempList.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			Number totalMark = (Number)element[0];
			Number count = (Number)element[1];
			Float avgMark=null;
			if(null!=totalMark){
				avgMark = new Float(totalMark.floatValue()/count.intValue());
			}
			if(null!=avgMark){
				returnList.add(avgMark);
			}
		}
		return returnList;
	}
	/**
	 * @see com.shufe.service.degree.thesis.annotate.AnnotateService#getStdNumOfLevel(com.shufe.model.degree.thesis.annotate.ThesisAnnotateBook, java.lang.String, java.lang.String)
	 */
	public Map getStdNumOfLevel(ThesisAnnotateBook thesisAnnotateBook,
			String departmentIdSeq, String stdTypeIdSeq) {
		List tempList = annotateDAO.getStdNumOfLevel(thesisAnnotateBook,
				departmentIdSeq, stdTypeIdSeq);
		Map returnMap = new HashMap();
		for (Iterator iter = tempList.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			Integer stdNumber = (Integer) element[0];
			Integer titleNumber = (Integer) element[1];
			String level = (String) element[2];
			if (null == level) {
				continue;
			}
			returnMap.put(level, titleNumber);
			StatUtils.setValueToMap("stdNumber",
					stdNumber, "integer", returnMap);
			StatUtils.setValueToMap("titleNumber",
					titleNumber, "integer", returnMap);
		}
		return returnMap;
	}
	/**
	 * 
	 * @see com.shufe.service.degree.thesis.annotate.AnnotateService#getStdNumOfMark(com.shufe.model.degree.thesis.annotate.ThesisAnnotateBook, java.lang.String, java.lang.String)
	 */
	public List getStdNumOfMark(ThesisAnnotateBook thesisAnnotateBook, String departmentIdSeq, String stdTypeIdSeq) {
		return annotateDAO.getStdNumOfMark(thesisAnnotateBook, departmentIdSeq, stdTypeIdSeq);
	}
	/* (non-Javadoc)
	 * @see com.shufe.service.degree.thesis.annotate.AnnotateService#batchUpdateDeleteBookNo(java.lang.String)
	 */
	public void batchUpdateDeleteBookNo(String thesisManageIdSeq) {
		annotateDAO.batchUpdateDeleteBookNo(thesisManageIdSeq);
	}
}
