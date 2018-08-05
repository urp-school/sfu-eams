//$Id: TutorStatisticAction.java,v 1.1 2007-2-26 11:10:55 Administrator Exp $
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
 * chenweixiong              2007-2-26         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.tutorManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.shufe.model.degree.subject.Level2Subject;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.Tutor;
import com.shufe.service.degree.subject.Level2SubjectService;
import com.shufe.service.degree.tutorManager.TutorService;
import com.shufe.service.util.stat.FloatSegment;
import com.shufe.web.action.common.RestrictionSupportAction;

public class TutorStatisticAction extends RestrictionSupportAction {
    
    private TutorService tutorService;
    
    private Level2SubjectService level2SubjectService;
    
    /**
     * 统计教师的基本属性
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doStatisticTutorProperty(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String propertyName = request.getParameter("propertyName");
        if (StringUtils.isBlank(propertyName)) {
            return forward(request, "loadDefault");
        }
        request.setAttribute("propertyName", propertyName);
        String departIdSeq = getDepartmentIdSeq(request);
        if ("gender".equals(propertyName)) {
            List genderList = tutorService.getPropertyOfTutor(new Tutor(), departIdSeq, "gender",
                    Boolean.FALSE);
            List departmentList = tutorService.getPropertyOfTutor(new Tutor(), departIdSeq,
                    "department", Boolean.FALSE);
            Map returnMap = tutorService.getStatisticMap(new Tutor(), departIdSeq,
                    "department,gender");
            request.setAttribute("propertyList", genderList);
            request.setAttribute("departmentList", departmentList);
            request.setAttribute("returnMap", returnMap);
            return forward(request, "propertyStatistic");
        }
        return null;
    }
    
    /**
     * 教师专业分布
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doStatisticSpecialityOfTutor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List specialitys = level2SubjectService.getPropertyOfSubject(new Level2Subject(),
                "speciality", Boolean.FALSE);
        Collections.sort(specialitys, new Comparator() {
            
            public int compare(Object arg0, Object arg1) {
                Speciality specia0 = (Speciality) arg0;
                Speciality specia1 = (Speciality) arg1;
                return new CompareToBuilder().append(specia0.getDepartment().getName(),
                        specia1.getDepartment().getName()).append(specia0.getName(),
                        specia1.getName()).toComparison();
            }
        });
        String departIdSeq = getDepartmentIdSeq(request);
        Map specialityTutorMap = tutorService.getSpecialityTutorMap(new Tutor(), departIdSeq);
        request.setAttribute("specialitys", specialitys);
        request.setAttribute("specialityTutorMap", specialityTutorMap);
        return forward(request, "specialityTutor");
    }
    
    /**
     * 按照年龄来统计的
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doStatisticByAge(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String doStatistic = request.getParameter("doStatistic");
        String departIdSeq = getDepartmentIdSeq(request);
        // List tutorList = tutorService.getTutorList(new Tutor(), departIdSeq);
        if (StringUtils.isBlank(doStatistic) && !"statistic".equals(doStatistic)) {
            return forward(request, "statisticConfig");
        } else {
            /**
             * 得到分段的对象list
             */
            List scoreList = new ArrayList();
            int startAge = (Integer.valueOf(request.getParameter("startAge"))).intValue();
            int middleAge = (Integer.valueOf(request.getParameter("jumpAge"))).intValue();
            int cycleNum = (Integer.valueOf(request.getParameter("cycleAge"))).intValue();
            for (int i = 0; i < cycleNum; i++) {
                FloatSegment scoreSegment = new FloatSegment();
                if (i == 0) {
                    scoreSegment.setMin(0);
                    scoreSegment.setMax(startAge + i * middleAge);
                } else if (i == cycleNum - 1) {
                    scoreSegment.setMin(startAge + (i - 1) * middleAge);
                    scoreSegment.setMax(100);
                } else {
                    scoreSegment.setMin(startAge + (i - 1) * middleAge);
                    scoreSegment.setMax(startAge + i * middleAge);
                }
                scoreList.add(scoreSegment);
            }
            Map tutorTypeGendarMap = tutorService.getTutorTypeMap(new Tutor(), departIdSeq,
                    scoreList);
            request.setAttribute("scoreList", scoreList);
            request.setAttribute("tutorTypeGendarMap", tutorTypeGendarMap);
            return forward(request, "tutorTypeAge");
        }
    }
    
    /**
     * @param tutorService The tutorService to set.
     */
    public void setTutorService(TutorService tutorService) {
        this.tutorService = tutorService;
    }
    
    /**
     * @param level2SubjectService The level2SubjectService to set.
     */
    public void setLevel2SubjectService(Level2SubjectService level2SubjectService) {
        this.level2SubjectService = level2SubjectService;
    }
}
