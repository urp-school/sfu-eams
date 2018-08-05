//$Id: AuditStandardServiceImpl.java,v 1.4 2007/01/16 03:26:39 yd Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-11-11         Created
 *  
 ********************************************************************************/

package com.shufe.service.graduate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.eams.std.graduation.audit.model.AuditStandard;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.dao.graduate.GraduateDAO;
import com.shufe.model.std.Student;
import com.shufe.service.BasicService;

/**
 * @author dell
 */
public class AuditStandardServiceImpl extends BasicService implements AuditStandardService {
    
    private GraduateDAO graduateDAO;
    
    /**
     * @see Interface AuditStandardService
     */
    public Pagination searchAuditStandard(AuditStandard auditStandard, int pageNo,
            int pageSize, String departmentIds, String studentTypeIds) {
        if (null != auditStandard.getId()) {
            if (auditStandard.getStudentType().isVO()) {
                this.filterAuthorityByStudentType(studentTypeIds);
            }
        }
        return graduateDAO.searchAuditStandard(auditStandard, pageNo, pageSize);
    }
    
    public List searchAuditStandard(AuditStandard auditStandard, String studentTypeIds) {
        if (null != auditStandard.getId()) {
            if (auditStandard.getStudentType().isVO()) {
                this.filterAuthorityByStudentType(studentTypeIds);
            }
        }
        return graduateDAO.searchAuditStandard(auditStandard);
    }
    
    /**
     * @see Interface AuditStandardService
     */
    public boolean isAuditStandardExists(AuditStandard auditStandard) {
        boolean flag = false;
        if (graduateDAO.countAuditStandardWithNull(auditStandard) > 0) {
            flag = true;
        }
        return flag;
    }
    
    /**
     * @see Interface AuditStandardService
     */
    public List listOuterExamAuditStandard(Long auditStandardId) {
        return utilDao.searchNamedQuery("listOuterExamAuditStandard",
                new Object[] { auditStandardId });
    }
    
    /**
     * @see Interface AuditStandardService
     */
    public Map searchStudentAuditStandard(Student student, Long auditType) {
        
        Map studentAuditStandardMap = new HashMap();
        
        boolean flag = false;
        AuditStandard auditStandard = new AuditStandard();
        auditStandard.setStudentType(student.getType());
        List auditStandardList = graduateDAO.searchAuditStandard(auditStandard);
        auditStandard = null;
        
        if (flag == false && student.getType() != null) {
            for (Iterator iter = auditStandardList.iterator(); iter.hasNext();) {
                AuditStandard element = (AuditStandard) iter.next();
                if (element.getStudentType() != null
                        && (element.getStudentType().getId().equals(student.getType().getId()))) {
                    auditStandard = element;
                    flag = true;
                    break;
                }
            }
        }
        
        studentAuditStandardMap.put(MajorType.FIRST, auditStandard);
        
        auditStandard = new AuditStandard();
        auditStandard.setStudentType(student.getType());
        auditStandardList = graduateDAO.searchAuditStandard(auditStandard);
        auditStandard = null;
        flag = false;
        if (flag == false && student.getType() != null) {
            for (Iterator iter = auditStandardList.iterator(); iter.hasNext();) {
                AuditStandard element = (AuditStandard) iter.next();
                if (element.getStudentType() != null
                        && (element.getStudentType().getId().equals(student.getType().getId()))) {
                    auditStandard = element;
                    flag = true;
                    break;
                }
            }
        }
        
        studentAuditStandardMap.put(MajorType.SECOND, auditStandard);
        
        return studentAuditStandardMap;
    }
    
    /**
     * @return Returns the graduateDAO.
     */
    public GraduateDAO getGraduateDAO() {
        return graduateDAO;
    }
    
    /**
     * @param graduateDAO
     *            The graduateDAO to set.
     */
    public void setGraduateDAO(GraduateDAO graduateDAO) {
        this.graduateDAO = graduateDAO;
    }
    
    /**
     * 部门数据级权限
     * 
     * @param departmentIds
     */
    protected void filterAuthorityByDepartment(String departmentIds) {
        if (departmentIds != null) {
            Map parameterMap = new HashMap(1);
            parameterMap.put("departmentIds", SeqStringUtil.transformToLong(departmentIds));
            graduateDAO.enbleFilter("filterAuthorityByDepartment", parameterMap);
        }
    }
    
    /**
     * 学生类别数据级权限
     * 
     * @param departmentIds
     */
    private void filterAuthorityByStudentType(String studentTypeIds) {
        if (studentTypeIds != null) {
            Map parameterMap = new HashMap(1);
            parameterMap.put("studentTypeIds", SeqStringUtil.transformToLong(studentTypeIds));
            graduateDAO.enbleFilter("filterAuthorityByStudentType", parameterMap);
        }
    }
    
}
