//$Id: StudentStatusAction.java,v 1.1 2007 六月 22 11:11:00 Administrator Exp $
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
 * chenweixiong              2007 六月 22         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.alteration;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.shufe.model.std.Student;
import com.shufe.service.std.StudentService;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;
import com.shufe.web.helper.StdSearchHelper;

/**
 * 学籍状态
 * 
 * @author Administrator
 */
public class StudentStatusAction extends RestrictionExampleTemplateAction {
    
    protected StdSearchHelper stdSearchHelper;
    
    protected StudentService studentService;
    
    /**
     * 列出给定模块的可管理的学生列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        addCollection(request, "students", utilService.search(stdSearchHelper
                .buildStdQuery(request)));
        return forward(request);
    }
    
    /**
     * 设置学生学籍状态
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward statusSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        EntityQuery entityQuery = buildChoiceStudent(request);
        entityQuery
                .setSelect("select new map( student.id as id, student.code as code,student.name as name, student.name as engName,student.state as studentState,student.inSchool as inSchool,student.active as active,student.graduateOn as studentGradudateOns ) ");
        addCollection(request, "students", utilService.search(entityQuery));
        /* 学籍状态 */
        addCollection(request, "statusList", baseCodeService.getCodes(StudentState.class));
        return forward(request);
    }
    
    /**
     * 组建所选择学生的查询条件
     * 
     * @param request
     * @return
     */
    protected EntityQuery buildChoiceStudent(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(Student.class, "student");
        entityQuery.add(new Condition("student.id in (:stdIds)", SeqStringUtil
                .transformToLong(request.getParameter("stdIds"))));
        entityQuery.addOrder(OrderUtils.parser("student.code"));
        return entityQuery;
    }
    
    /**
     * 毕业时间设置
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward graduateOnSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        EntityQuery query = buildChoiceStudent(request);
        List stds = (List) utilService.search(query);
        addCollection(request, "students", stds);
        return forward(request);
    }
    
    /**
     * 批量更改学生的学籍状态和学籍有效性
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateStatus(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 更新 在校在籍 状态
        Long stdStateId = getLong(request, "studentStateId");
        Boolean stdActive = getBoolean(request, "isActive");
        Boolean stdInSchool = getBoolean(request, "isInSchool");
        List students = (List) utilService.load(Student.class, "id", SeqStringUtil
                .transformToLong(get(request, "stdIds")));
        List updateList = new ArrayList();
        for (Iterator it = students.iterator(); it.hasNext();) {
            Student std = (Student) it.next();
            if (null != stdStateId) {
                std.setState(new StudentState(stdStateId));
                updateList.add(std);
            }
            if (null != stdActive) {
                std.setActive(stdActive.booleanValue());
                updateList.add(std);
            }
            if (null != stdInSchool) {
                std.setInSchool(stdInSchool.booleanValue());
                updateList.add(std);
            }
            utilService.saveOrUpdate(updateList);
        }
        return redirect(request, "search", "info.action.success");
    }
    
    public ActionForward updateGraduateOn(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Date graduateOn = RequestUtils.getDate(request, "studentGraduateOn");
        
        List students = (List) utilService.load(Student.class, "id", SeqStringUtil
                .transformToLong(get(request, "stdIds")));
        for (Iterator it = students.iterator(); it.hasNext();) {
            Student std = (Student) it.next();
            std.setGraduateOn(graduateOn);
        }
        utilService.saveOrUpdate(students);
        
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 按学籍状态查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward stat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        EntityQuery query = stdSearchHelper.buildStdQuery(request);
        query.setLimit(null);
        query.getOrders().clear();
        List groupBy = new ArrayList();
        groupBy.add("std.state.id");
        query.setSelect("std.state.id, count(*)");
        query.setGroups(groupBy);
        
        List statResult = (List) utilService.search(query);
        List results = new ArrayList();
        for (Iterator it = statResult.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            obj[0] = utilService.load(StudentState.class, new Long(String.valueOf(obj[0])));
            results.add(obj);
        }
        addCollection(request, "results", results);
        return forward(request, "statResult");
    }
    
    /**
     * @param stdSearchHelper
     *            the stdSearchHelper to set
     */
    public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
        this.stdSearchHelper = stdSearchHelper;
    }
    
    /**
     * @param studentService
     *            the studentService to set
     */
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    
    /**
     * 学籍状态
     */
    protected void indexSetting(HttpServletRequest request) throws Exception {
        addCollection(request, "statusList", baseCodeService.getCodes(StudentState.class));
    }
    
}
