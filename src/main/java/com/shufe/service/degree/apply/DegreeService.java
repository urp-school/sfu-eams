//$Id: DegreeService.java,v 1.2 2006/11/04 10:02:34 cwx Exp $
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
 * chenweixiong              2006-10-26         Created
 *  
 ********************************************************************************/

package com.shufe.service.degree.apply;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.degree.apply.DegreeDAO;
import com.shufe.model.degree.apply.DegreeApply;
import com.shufe.model.std.Student;

public interface DegreeService {
    
    /**
     * @param degreeDAO
     */
    public void setDegreeDAO(DegreeDAO degreeDAO);
    
    /**
     * @param student
     * @return
     */
    public DegreeApply getDegreeByStd(Student student);
    
    /**
     * @param degreeApply
     * @param departmentSeq
     * @param stdTypeSeq
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getPaginas(DegreeApply degreeApply, Long[] departmentSeq, Long[] stdTypeSeq,
            int pageNo, int pageSize);
    
    public void affirmById(String idSeq, Boolean affirm);
    
    /**
     * 根据degreeApply数据倒到学位申请的信息
     * 
     * @param degreeApply
     * @param stdTypIds
     * @param departmentIds
     * @return
     */
    public List getDegreeApplyBySelf(DegreeApply degreeApply, Long[] stdTypIds, Long[] departmentIds);
    
    /**
     * 学位申请页面跳转规则
     * 
     * @param stdType
     * @return
     */
    public String returnDegreeApplyDataPage(StudentType stdType);
}
