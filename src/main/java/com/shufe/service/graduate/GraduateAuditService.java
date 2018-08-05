//$Id: GraduateAuditService.java,v 1.25 2007/01/24 13:43:18 yd Exp $
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
 * pippo             2005-11-15         Created
 *  
 ********************************************************************************/

package com.shufe.service.graduate;

import java.io.IOException;
import java.util.List;

import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.dao.system.calendar.TermCalculator;
import com.shufe.model.std.Student;
import com.shufe.service.OutputObserver;
import com.shufe.service.graduate.result.TeachPlanAuditResult;

/**
 * 毕业审核服务
 */
public interface GraduateAuditService {
    
    /**
     * 批量对学生进行毕业审核
     * 
     * @param studentIdArray
     * @param majorType
     * @param auditStandardId
     * @return
     * @throws IOException
     */
    public List batchAuditStudent(Long[] studentIdArray, MajorType majorType,
            Long auditStandardId) throws IOException;
    
    /**
     * 批量审核符合条件的学生<br>
     * <font color="red">双专业毕业审核时，不关联单专业</font>
     * 
     * @param stdList
     * @param majorType
     * @param auditStandardId
     * @param auditTerm
     * @param observer
     * @throws IOException
     */
    public void batchGraduateAudit(List stdList, MajorType majorType, Long auditStandardId,
            String auditTerm, OutputObserver observer) throws IOException;
    
    /**
     * 指定学生毕业审核
     * 
     * @param student
     *            指定学生，为一个确切的学生
     * @param majorType
     *            专业类别：1、一专业；2、二专业
     * @param auditTermList
     *            审核学期<code>List</code>，为空或空集合审核全部学期
     * @param auditStandardId
     *            审核标准<code>AuditStandard</code>的Id，为空直接按培养计划审核
     * @param isGradeDeploy
     *            审核的成绩的发布状况，为空表示包含发布和未发布的成绩
     * @param returnNullResult
     *            是否返回空对象结果
     * @return <code>TeachPlanAuditResult</code> 培养计划审核结果
     */
    public TeachPlanAuditResult auditTeachPlan(Student student, MajorType majorType,
            List auditTermList, Long auditStandardId, Boolean isGradeDeploy,
            Boolean returnNullResult);
    
    /**
     * 获取学生毕业审核结果列表
     * 
     * @param student
     * @param majorType
     * @param auditStandardId
     * @param auditTermList
     * @param isGradeDeploy
     * @param returnNullResult
     * @return
     */
    public List getStudentTeachPlanAuditDetail(Student student, MajorType majorType,
            Long auditStandardId, List auditTermList, Boolean isGradeDeploy,
            Boolean returnNullResult);
    
    /**
     * 获取学生毕业审核结果列表
     * 
     * @param student
     * @param majorType
     * @param auditStandardId
     * @param termCalculator
     * @param omitSmallTerm
     * @param isGradeDeploy
     * @param returnNullResult
     * @return
     */
    public List getStudentTeachPlanAuditDetail(Student student, MajorType majorType,
            Long auditStandardId, TermCalculator termCalculator, Boolean omitSmallTerm,
            Boolean isGradeDeploy, Boolean returnNullResult);
    
    /**
     * 通过审核学期串生成审核学期<code>List</code>
     * 
     * @param auditTermString
     * @return
     */
    public List getAuditTermList(String auditTermString);
}
