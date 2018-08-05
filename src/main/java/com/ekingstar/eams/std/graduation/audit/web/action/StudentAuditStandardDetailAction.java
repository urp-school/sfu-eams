// $Id: DegreeAuditStandardAction.java,v 1.1 2007-4-9 上午10:56:38 chaostone Exp $
/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is
 * intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source,
 * or other redistribution of this source is not permitted without written
 * permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/*******************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description
 * ============         ============        ============
 * chaostone            2007-04-09          Created
 * zq                   2007-09-18          重载buildQuery()方法
 ******************************************************************************/

package com.ekingstar.eams.std.graduation.audit.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditStandard;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;
import com.ekingstar.eams.system.basecode.industry.PunishmentType;
import com.shufe.model.std.Student;
import com.shufe.web.action.common.DispatchBasicAction;


/**
 * 学位审核标准-学生查看标准
 * 
 * @author chaostone
 */
public class StudentAuditStandardDetailAction  extends DispatchBasicAction{

    public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Student std = getStudentFromSession(request.getSession());    
        
        EntityQuery resultQuery = new EntityQuery(AuditResult.class, "auditResult");
        resultQuery.add(new Condition("auditResult.std.id=:stdId", std.getId()));
        resultQuery.add(new Condition("auditResult.majorType.id=:majorTypeId", MajorType.FIRST));
        List resultList = (List)utilService.search(resultQuery);
        if (resultList.size() != 0) {
            request.setAttribute("auditResult", resultList.get(0));
        } else {
            request.setAttribute("auditResult", new AuditResult());
        }
        
        addCollection(request, "standards", utilService.load(DegreeAuditStandard.class, "stdType.id", std.getStdType().getId()));
        request.setAttribute("punishTypes", baseCodeService.getCodes(PunishmentType.class));
        request.setAttribute("otherExamCategories", baseCodeService
                .getCodes(OtherExamCategory.class));
        return forward(request);
    }
    
}
