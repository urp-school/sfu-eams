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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.collection.predicates.NotZeroNumberPredicate;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.dao.system.calendar.TermCalculator;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

public class AuditResultSupportAction extends StudentAuditSupportAction {
    
    public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        Long stdId = getLong(request, "student.id");
        Long auditStandardId =getLong(request,"auditStandardId");
        if (!NotZeroNumberPredicate.getInstance().evaluate(stdId)) {
            throw new RuntimeException("No such student id!");
        }
        Student student = studentService.getStudent(stdId);
        if (student == null) {
            throw new RuntimeException("No such student!");
        }
        try {
            getStudentTeachPlanAuditDetail(student, getMajorType(), auditStandardId, request
                    .getParameter("auditTerm"), Boolean.TRUE, Boolean.TRUE);
        } catch (PojoNotExistException e) {
            if (e.getMessage() != null
                    && e.getMessage().equals(com.shufe.model.Constants.TEACHPLAN)) {
                return this.forwardError(mapping, request, "error.teachPlan.notExists");
            }
        }
        return this.forward(request, "../auditResultDetail");
    }
    
    /**
     * @param request
     * @param isReturnNull
     */
    protected TeachCalendar getTeachCalendar(HttpServletRequest request, boolean isReturnNull) {
        EntityQuery entityQuery = new EntityQuery(TeachCalendar.class, "calendar");
        QueryRequestSupport.populateConditions(request, entityQuery);
        if (CollectionUtils.isEmpty(entityQuery.getConditions())) {
            if (isReturnNull) {
                return null;
            } else {
                throw new RuntimeException("One TeachCalendar Need!\n");
            }
        }
        Collection coll = utilService.search(entityQuery);
        if (CollectionUtils.isEmpty(coll)) {
            throw new RuntimeException("No TeachCalendar Find!\n");
        } else {
            return (TeachCalendar) coll.iterator().next();
        }
    }
    
    protected List addDetailResultList(List stdList, MajorType majorType, Boolean omitSmallTerm,
            Long auditStandardId, TeachCalendar calendar, Boolean noTeachPlanEnd) {
        List resultList = new ArrayList();
        List errorList = new ArrayList();
        if (calendar == null) {
            for (Iterator iter = stdList.iterator(); iter.hasNext();) {
                Student student = (Student) iter.next();
                try {
                    resultList.addAll(graduateAuditService.getStudentTeachPlanAuditDetail(student,
                            majorType, auditStandardId, null, null, Boolean.TRUE));
                } catch (PojoNotExistException e) {
                    if (e.getMessage() != null
                            && e.getMessage().equals(com.shufe.model.Constants.TEACHPLAN)) {
                        errorList.add(student);
                        if (Boolean.TRUE.equals(noTeachPlanEnd)) {
                            return errorList;
                        }
                    }
                }
            }
        } else {
            TermCalculator termCalculator = new TermCalculator(teachCalendarService, calendar);
            for (Iterator iter = stdList.iterator(); iter.hasNext();) {
                Student student = (Student) iter.next();
                try {
                    resultList.addAll(graduateAuditService.getStudentTeachPlanAuditDetail(student,
                            majorType, auditStandardId, termCalculator, omitSmallTerm, null,
                            Boolean.TRUE));
                } catch (PojoNotExistException e) {
                    if (e.getMessage() != null
                            && e.getMessage().equals(com.shufe.model.Constants.TEACHPLAN)) {
                        errorList.add(student);
                        if (Boolean.TRUE.equals(noTeachPlanEnd)) {
                            return errorList;
                        }
                    }
                }
            }
        }
        Results.addObject("resultList", resultList);
        return errorList;
    }
    
    public ActionForward batchDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stdIdString = request.getParameter("stdIds");
        Long[] stdIdArray = SeqStringUtil.transformToLong(stdIdString);
        List stdList = utilService.load(Student.class, "id", stdIdArray);
        if (CollectionUtils.isEmpty(stdList)) {
            throw new RuntimeException("No such student!");
        }
        Boolean omitSmallTerm = getBoolean(request, "omitSmallTerm");
        Long auditStandardId = null;
        if (NumberUtils.isNumber(request.getParameter("auditStandardId"))) {
            auditStandardId = Long.valueOf(request.getParameter("auditStandardId"));
        }
        ComparatorChain comparatorChain = new ComparatorChain();
        comparatorChain.addComparator(new BeanComparator("enrollYear"), true);
        comparatorChain.addComparator(new BeanComparator("code"));
        Collections.sort(stdList, comparatorChain);
        TeachCalendar calendar = getTeachCalendar(request, true);
        addDetailResultList(stdList, getMajorType(), omitSmallTerm, auditStandardId, calendar,
                Boolean.FALSE);
        return this.forward(request, "../batchAuditResultDetail");
    }
    
    protected MajorType getMajorType() {
        return null;
    }
    
}
