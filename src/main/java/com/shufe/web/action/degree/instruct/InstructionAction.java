//$Id: InstructWorkloadAction.java,v 1.6 2006/12/19 13:08:41 duanth Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zq                   2007-09-14          在seacrh()中，添加populateConditions(...)
 *                                          方法和addStdTypeTreeDataRealm(...)方法；
 ********************************************************************************/
package com.shufe.web.action.degree.instruct;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.degree.instruct.Instruction;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.web.action.common.CalendarRestrictionExampleTemplateAction;
import com.shufe.web.helper.StdSearchHelper;

/**
 * 导师界面我的学生界面响应类
 * 
 * @author chaostone
 * 
 */
public class InstructionAction extends CalendarRestrictionExampleTemplateAction {
    
    private StdSearchHelper stdSearchHelper;
    
    /**
     * 查询导师指导学生关系信息
     * 
     * @param form
     * @param request
     * @param moduleName
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        Boolean instructed = getBoolean(request, "instructed");
        EntityQuery query = stdSearchHelper.buildStdQuery(request, "calendar.studentType.id");
        Long calendarId = getLong(request, "instruction.calendar.id");
        Integer majorTypeId = getInteger(request, "majorType.id");
        if (Boolean.TRUE.equals(instructed)) {
            query.setAlias("instruction");
            query.setSelect("instruction");
            query.setEntityClass(Instruction.class);
            populateConditions(request, query);
            query.add(Condition.eq("instruction.majorType.id", majorTypeId));
            String from = query.getFrom();
            from = "from Instruction  instruction join instruction.std std "
                    + StringUtils.substringAfter(from, " std");
            query.setFrom(from);
        }
        if (MajorType.FIRST.equals(majorTypeId)) {
            query.join("left", "std.teacher", "tutor");
        } else {
            query.join("left", "std.tutor", "tutor");
            query.add(new Condition("std.secondMajor is not null"));
        }
        if (Boolean.TRUE.equals(instructed)) {
            addCollection(request, "instructions", utilService.search(query));
            return forward(request);
        } else {
            query
                    .add(new Condition(
                            "not exists( from Instruction instruction where instruction.std.id=std.id and instruction.calendar.id=:calendarId)",
                            calendarId));
            addCollection(request, "students", utilService.search(query));
            return forward(request, "stdList");
        }
    }
    
    /**
     * 根据学生的导师信息生成指定学期指导信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward genInstructionByTutor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stdIds = request.getParameter("stdIds");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id"));
        List stds = utilService.load(Student.class, "id", SeqStringUtil.transformToLong(stdIds));
        Boolean isGraduation = getBoolean(request, "isGraduation");
        MajorType majorType = new MajorType(getLong(request, "majorTypeId"));
        List instructions = new ArrayList();
        for (Iterator iterator = stds.iterator(); iterator.hasNext();) {
            Student std = (Student) iterator.next();
            if (null != std.getTeacher()) {
                Instruction instruction = new Instruction(calendar, std, std.getTeacher(),
                        isGraduation);
                instruction.setMajorType(majorType);
                instructions.add(instruction);
            }
        }
        utilService.saveOrUpdate(instructions);
        return redirect(request, "search", "info.action.success");
    }
    
    public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
        this.stdSearchHelper = stdSearchHelper;
    }
    
}
