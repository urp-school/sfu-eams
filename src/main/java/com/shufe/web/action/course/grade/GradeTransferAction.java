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
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2007-1-28            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.grade;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.course.grade.CourseGrade;

/*
 * 成绩转移 将二专成绩转入一专业
 */
public class GradeTransferAction extends StdGradeSearchAction {
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward transfer(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String courseGradeIdSeq = request.getParameter("courseGradeIds");
        if (StringUtils.isEmpty(courseGradeIdSeq)) {
            return forwardError(mapping, request, "error.parameters.needed");
        }
        EntityQuery gradeQuery = new EntityQuery(CourseGrade.class, "grade");
        gradeQuery.add(new Condition("instr('," + courseGradeIdSeq + ",',','||grade.id||',')>0"));
        List courseGrades = (List) utilService.search(gradeQuery);
        for (Iterator iter = courseGrades.iterator(); iter.hasNext();) {
            CourseGrade grade = (CourseGrade) iter.next();
            grade.setMajorType(new MajorType(MajorType.FIRST));
        }
        utilService.saveOrUpdate(courseGrades);
        return redirect(request, "search", "info.action.success");
    }
}
