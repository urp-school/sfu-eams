//$Id: DegreeServiceImpl.java,v 1.3 2006/12/19 05:03:18 cwx Exp $
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

package com.shufe.service.degree.apply.impl;

import java.util.List;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.eams.system.basecode.state.Degree;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.degree.apply.DegreeDAO;
import com.shufe.model.degree.apply.DegreeApply;
import com.shufe.model.std.Student;
import com.shufe.service.BasicService;
import com.shufe.service.degree.apply.DegreeService;
import com.shufe.service.system.baseinfo.StudentTypeService;

public class DegreeServiceImpl extends BasicService implements DegreeService {
    
    private DegreeDAO degreeDAO;
    
    private StudentTypeService studentTypeService;
    
    public void setStudentTypeService(StudentTypeService studentTypeService) {
        this.studentTypeService = studentTypeService;
    }
    
    /**
     * @param degreeDAO The degreeDAO to set.
     */
    public void setDegreeDAO(DegreeDAO degreeDAO) {
        this.degreeDAO = degreeDAO;
    }
    
    public DegreeApply getDegreeByStd(Student student) {
        DegreeApply degreeApply = new DegreeApply();
        degreeApply.setStudent(student);
        List tempList = degreeDAO.getDegreeApplyList(degreeApply, null, null);
        return tempList.size() > 0 ? (DegreeApply) tempList.get(0) : new DegreeApply();
    }
    
    /**
     * @see com.shufe.service.degree.apply.DegreeService#getPaginas(com.shufe.model.degree.apply.DegreeApply,
     *      Long[], Long[], int, int)
     */
    public Pagination getPaginas(DegreeApply degreeApply, Long[] departmentSeq, Long[] stdTypeSeq,
            int pageNo, int pageSize) {
        return degreeDAO.getPaginas(degreeApply, stdTypeSeq, departmentSeq, pageNo, pageSize);
    }
    
    /**
     * @see com.shufe.service.degree.apply.DegreeService#affirmById(java.lang.String,
     *      java.lang.Boolean)
     */
    public void affirmById(String idSeq, Boolean affirm) {
        degreeDAO.affirmById(idSeq, affirm);
    }
    
    public List getDegreeApplyBySelf(DegreeApply degreeApply, Long[] stdTypIds, Long[] departmentIds) {
        return degreeDAO.getDegreeApplyList(degreeApply, departmentIds, stdTypIds);
    }
    
    public String returnDegreeApplyDataPage(StudentType stdType) {
        StudentType studentType = studentTypeService
                .getStudentType(StudentType.PROFESSIONAL_MASTER);
        Set subStdTypes = studentType.getSubTypes();
        if (StudentType.HIGH_EDUCATION_TEACHER.equals(stdType.getId())) {
            return "degreeApplyTeacher";
        } else if (subStdTypes.contains(stdType)) {
            return "degreeApplyProfessional";
        } else if (Degree.MASTER.equals(stdType.getDegree().getId())) {
            return "degreeApplyMaster";
        } else if (Degree.DOCTOR.equals(stdType.getDegree().getId())) {
            return "degreeApplyDoctor";
        } else if (Degree.EQUIVALENTMASTER.equals(stdType.getDegree().getId())) {
            return "degreeApplyEquivalentMaster";
        } else if (Degree.EQUIVALENTDOCTOR.equals(stdType.getDegree().getId())) {
            return "degreeApplyEquivalentMaster";
        }
        return null;
    }
}
