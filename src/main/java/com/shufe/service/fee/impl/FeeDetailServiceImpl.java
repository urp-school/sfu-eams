//$Id: FeeDetailServiceImpl.java,v 1.13 2006/11/09 11:29:13 duanth Exp $
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
 * chenweixiong              2005-11-6         Created
 *  
 ********************************************************************************/

package com.shufe.service.fee.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.Criteria;

import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.shufe.dao.fee.FeeDetailDAO;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.fee.FeeDefault;
import com.shufe.model.fee.FeeDetail;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.fee.FeeDefaultService;
import com.shufe.service.fee.FeeDetailService;
import com.shufe.service.system.message.SystemMessageService;

/**
 * @author Administrator
 * 
 */
public class FeeDetailServiceImpl extends BasicService implements FeeDetailService {
    
    /**
     * <code>feeDetailDAO</code> feeDetailDAO FeeDetailDAOHibernate Administrator create in
     * 2005-11-6
     */
    protected FeeDetailDAO feeDetailDAO;
    
    protected SystemMessageService messageService;
    
    protected FeeDefaultService FeeDefaultService;
    
    public List getFeeDetails(FeeDetail feeDetail) {
        if (null == feeDetail)
            return Collections.EMPTY_LIST;
        else
            return feeDetailDAO.getFeeDetails(feeDetail);
    }
    
    public List getFeeDetails(Student std, TeachCalendar calendar, FeeType type) {
        FeeDetail feeDetail = new FeeDetail();
        feeDetail.setStd(std);
        feeDetail.setCalendar(calendar);
        feeDetail.setType(type);
        return getFeeDetails(feeDetail);
    }
    
    public Float[] statFeeFor(Student std, TeachCalendar calendar, FeeType type) {
        Float[] values = new Float[3];
        List fees = getFeeDetails(std, calendar, type);
        FeeDefault defaultValue = FeeDefaultService.getFeeDefault(std, type);
        values[0] = (null == defaultValue) ? new Float(0) : new Float(defaultValue.getValue()
                .intValue());
        
        float shouldPay = 0;
        float payed = 0;
        for (Iterator iter = fees.iterator(); iter.hasNext();) {
            FeeDetail feeDetail = (FeeDetail) iter.next();
            shouldPay += (null == feeDetail.getShouldPay()) ? 0 : feeDetail.getShouldPay()
                    .floatValue();
            payed += (null == feeDetail.getToRMB()) ? 0 : feeDetail.getToRMB().floatValue();
        }
        values[1] = new Float(shouldPay);
        values[2] = new Float(payed);
        return values;
        
    }
    
    /**
     * @param FeeDefaultService
     *            The FeeDefaultService to set.
     */
    public void setFeeDefaultService(FeeDefaultService stdAndFeeTypeDefaultService) {
        this.FeeDefaultService = stdAndFeeTypeDefaultService;
    }
    
    /**
     * @param feeDetailDAO
     *            The feeDetailDAO to set.
     */
    public void setFeeDetailDAO(FeeDetailDAO feeDetailInfoDAO) {
        this.feeDetailDAO = feeDetailInfoDAO;
    }
    
    /**
     * @param messageService
     *            The messageService to set.
     */
    public void setMessageService(SystemMessageService messageService) {
        this.messageService = messageService;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.fee.FeeDetailService#getStdSelectCourses(java.lang.String,
     *      com.shufe.model.system.calendar.TeachCalendar)
     */
    public Criteria getStdSelectCourses(String departmentIds, CourseTake courseTake,
            TeachCalendar teachCalenar) {
        return feeDetailDAO.getStdSelectCourses(departmentIds, courseTake, teachCalenar);
    }
    
    /**
     * 发送消息
     * 
     * @see com.shufe.service.fee.FeeDetailService#saveMessageBystudentNos(java.lang.Long,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    public void saveMessageBystudentNos(String title, String body, String studentIds) {
        // Long[] studentId = SeqStringUtil.transformToLong(studentIds);
        // String[] string = new String[10];
        // messageService.saveStudentMessage(title, body, string);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.fee.FeeDetailService#getDetailOfAllType(java.util.List,
     *      com.shufe.model.fee.FeeDetail, java.lang.String, int, int)
     */
    public Pagination getFeeDetailsOfTypes(FeeDetail feeDetail, List feeTypeList,
            String orderByName, int pageNo, int pageSize) {
        return feeDetailDAO.getFeeDetailOfTypes(feeDetail, feeTypeList, orderByName, pageNo,
                pageSize);
    }
    
    public Pagination getFeeDetails(FeeDetail feeDetail, String departIdSeq, String orderbyName,
            int pageNo, int pageSize) {
        return feeDetailDAO.getFeeDetails(feeDetail, departIdSeq, orderbyName, pageNo, pageSize);
    }
    
    public List getFeeDetails(FeeDetail feeDetail, String departIdSeq, String orderByName) {
        return feeDetailDAO.getFeeDetails(feeDetail, departIdSeq, orderByName);
    }
    
}
