//$Id: FeeDefaultServiceImpl.java,v 1.5 2006/10/12 12:20:27 duanth Exp $
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
 * chenweixiong              2006-7-11         Created
 *  
 ********************************************************************************/

package com.shufe.service.fee.impl;

import java.util.Collections;
import java.util.List;

import com.ekingstar.eams.std.info.Student;
import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.shufe.dao.fee.FeeDefaultDAO;
import com.shufe.model.fee.FeeDefault;
import com.shufe.service.BasicService;
import com.shufe.service.fee.FeeDefaultComparator;
import com.shufe.service.fee.FeeDefaultService;
import com.shufe.service.system.baseinfo.StudentTypeService;

public class FeeDefaultServiceImpl extends BasicService implements FeeDefaultService {
    
    private FeeDefaultDAO feeDefaultDAO;
    
    private StudentTypeService studentTypeService;
    
    public FeeDefault getFeeDefault(Student std1, FeeType type) {
        // TODO for ??
        com.shufe.model.std.Student std = (com.shufe.model.std.Student) std1;
        List feeDefaults = feeDefaultDAO.getFeeDefaults(type, studentTypeService.getStdTypesUp(std
                .getType().getId()), std.getDepartment(), std.getFirstMajor());
        if (feeDefaults.isEmpty())
            return null;
        Collections.sort(feeDefaults, new FeeDefaultComparator());
        return (FeeDefault) feeDefaults.get(0);
    }
    
    /**
     * @param feeDefaultDAO The feeDefaultDAO to set.
     */
    public void setFeeDefaultDAO(FeeDefaultDAO feeDefaultDAO) {
        this.feeDefaultDAO = feeDefaultDAO;
    }
    
    public void setStudentTypeService(StudentTypeService studentTypeService) {
        this.studentTypeService = studentTypeService;
    }
    
}
