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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.web.action.graduate;

import java.util.Collections;
import java.util.List;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.std.Student;
import com.shufe.service.graduate.GraduateAuditService;

public class StudentAuditSupportAction extends StudentSearchAction {
    
    protected GraduateAuditService graduateAuditService;
    /**
     * 获得学生培养计划完成情况详情
     * 
     * @param student
     * @param majorType
     * @param auditStandardId
     *            审核标准ID，为<code>null</code>则直接按培养计划审核
     * @param auditTerm
     *            审核学期串，为<code>null</code>则直接按培养计划审核
     * @param isGradeDeploy
     *            审核时计算成绩的发布状况
     * @param returnNullResult
     *            是否返回空对象结果
     */
    public void getStudentTeachPlanAuditDetail(Student student, MajorType majorType,
            Long auditStandardId, String auditTerm, Boolean isGradeDeploy, Boolean returnNullResult) {
        List resultList = graduateAuditService.getStudentTeachPlanAuditDetail(student, majorType,
                auditStandardId, graduateAuditService.getAuditTermList(auditTerm), isGradeDeploy,
                returnNullResult);
        Results.addObject("resultList", resultList);
    }
    /**
     * @param graduateAuditService
     *            要设置的 graduateAuditService.
     */
    public void setGraduateAuditService(GraduateAuditService graduateAuditService) {
        this.graduateAuditService = graduateAuditService;
    }

	
	/**
     * 初始化学生信息搜索框
     * 
     * @param departmentIds
     * @param studentTypeIds
     */
    protected void initSearchStudentBar(String departmentIds, String studentTypeIds) {
        List stdTypes = studentTypeService.getStudentTypes(studentTypeIds);
        Collections.sort(stdTypes, new PropertyComparator("code"));
        Results.addObject("stdTypeList", stdTypes);
        List departs = departmentService.getColleges(departmentIds);
        Collections.sort(departs, new PropertyComparator("code"));
        Results.addObject("departmentList", departs);
    }
}
