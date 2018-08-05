//$Id: TuitionFee.java,v 1.1 2007-9-29 上午09:53:29 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi               2007-09-29         	Created
 * zhouqi               2007-10-09          修改了下面查询和统计的错误
 ********************************************************************************/

package com.ekingstar.eams.fee.web.action;

import java.util.ArrayList;
import java.util.Collection;
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
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.fee.model.TuitionFee;
import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.fee.FeeDefaultService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;
import com.shufe.web.helper.StdSearchHelper;

/**
 * @author zhouqi
 */
public class TuitionFeeAction extends CalendarRestrictionSupportAction {
    
    protected StdSearchHelper stdSearchHelper;
    
    protected FeeDefaultService feeDefaultService;
    
    /**
     * 查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        boolean isTuition = StringUtils.isEmpty(request.getParameter("tuition_exists"));
        EntityQuery query = buildQuery(request, isTuition);
        query.setLimit(getPageLimit(request));
        if (isTuition) {
            addCollection(request, "tuitions", utilService.search(query));
            return forward(request);
        } else {
            query.add(new Condition("std.active = 1"));
            query.add(new Condition("std.inSchool = 1"));
            addCollection(request, "students", utilService.search(query));
            return forward(request, "studentList");
        }
    }
    
    /**
     * 选择要查询的方法或方式
     * 
     * @param request
     * @param isTuition
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request, boolean isTuition) {
        if (isTuition) {
            return buildTuitionQuery(request);
        } else {
            return buildNotExistsStudentQuery(request);
        }
    }
    
    /**
     * 查询学费初始化表中学生存在的记录
     * 
     * @param request
     * @param exists
     * @return
     */
    protected EntityQuery buildTuitionQuery(HttpServletRequest request) {
        Boolean isCompleted = getBoolean(request, "isCompleted");
        EntityQuery query = studentQuery(request);
        query.add(new Condition("tuition.std.id = std.id"));
        query.setFrom("TuitionFee tuition, Student std");
        query.add(new Condition("tuition.calendar.id = :calendarId",
                getLong(request, "calendar.id")));
        if (isCompleted != null) {
            query.add(new Condition("tuition.isCompleted = :tuitionIsCompleted", isCompleted));
        }
        query.setSelect("tuition");
        return query;
    }
    
    /**
     * 查询学费初始化表中不存在的学生记录
     * 
     * @param request
     * @return
     */
    protected EntityQuery buildNotExistsStudentQuery(HttpServletRequest request) {
        EntityQuery query = studentQuery(request);
        query
                .add(new Condition(
                        "not exists (from TuitionFee tuition where tuition.std.id = std.id and tuition.calendar.id = :calendarId)",
                        getLong(request, "calendar.id")));
        return query;
    }
    
    /**
     * 查询符合条件的所有学生列表(与学费初始化表没关系)
     * 
     * @param request
     * @return
     */
    protected EntityQuery studentQuery(HttpServletRequest request) {
        EntityQuery query = stdSearchHelper.buildStdQuery(request, "calendar.studentType.id");
        query.setLimit(null);
        return query;
    }
    
    /**
     * 完成设置
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward settingCompleted(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] tuitionIds = SeqStringUtil.transformToLong(get(request, "tuitionIds"));
        Boolean isCompleted = getBoolean(request, "isCompleted");
        
        List tuitions = (List) utilService.load(TuitionFee.class, "id", tuitionIds);
        for (Iterator it = tuitions.iterator(); it.hasNext();) {
            TuitionFee tuition = (TuitionFee) it.next();
            tuition.setIsCompleted(isCompleted);
        }
        utilService.saveOrUpdate(tuitions);
        
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 计算学费
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statFee(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return feeSetting(request, null);
    }
    
    /**
     * 设置学费
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward settingFee(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return feeSetting(request, getFloat(request, "fee"));
    }
    
    /**
     * 学费计算或设置
     * <ul>
     * <li>如果TuitionFee的ID存在，则认为重新计算或设置，即对已计算学费的学生，重算或重设－－update；
     * <li>如果其ID不存在，则认为是第一次计算或设置，即对还没有计算或设置学费的学生ID来进行计算或设置－－insert；
     * </ul>
     * 关于全部还是选择，见程序体中的注释
     * 
     * @param request
     * @param fee
     * @return
     * @throws Exception
     */
    protected ActionForward feeSetting(HttpServletRequest request, Float fee) throws Exception {
        Long[] tuitionIds = SeqStringUtil.transformToLong(request.getParameter("tuitionIds"));
        Long[] stdIds = SeqStringUtil.transformToLong(request.getParameter("stdIds"));
        String remark = request.getParameter("remark");
        // 获得当教学日历ID
        TeachCalendar calendar = (TeachCalendar) utilService.load(TeachCalendar.class, getLong(
                request, "calendar.id"));
        FeeType feeType = (FeeType) utilService.load(FeeType.class, FeeType.TUITION);
        // 当tuitionIds存在，则认为要update；若stdIds存在，则认为insert；
        // 若tuitionIds的个数为0，则认为全部，然则部分；stdIds，同理；
        // 若两者同时存在，则tuitionIds优先于stdIds；
        if (tuitionIds != null && (stdIds == null || stdIds.length == 0)) {
            List tuitions = null;
            if (tuitionIds.length == 0) {
                tuitions = (List) utilService.search(buildTuitionQuery(request));
            } else {
                tuitions = (List) utilService.load(TuitionFee.class, "id", tuitionIds);
            }
            for (Iterator it = tuitions.iterator(); it.hasNext();) {
                TuitionFee tuition = (TuitionFee) it.next();
                try {
                    if (fee != null) {
                        tuition.setFee(fee);
                    } else {
                        tuition.setFee(new Float(feeDefaultService.getFeeDefault(tuition.getStd(),
                                feeType).getValue().toString()));
                    }
                } catch (Exception e) {
                    // 如果feeDefaultService.getFeeDefault()为null，则不赋
                    ;
                }
                tuition.setRemark(remark);
            }
            utilService.saveOrUpdate(tuitions);
        } else if ((tuitionIds == null || tuitionIds.length == 0) && stdIds != null) {
            List tuitions = new ArrayList();
            List students = null;
            if (stdIds.length == 0) {
                students = (List) utilService.search(buildQuery(request, false));
            } else {
                students = (List) utilService.load(Student.class, "id", stdIds);
            }
            for (Iterator it = students.iterator(); it.hasNext();) {
                Student std = (Student) it.next();
                
                TuitionFee tuition = new TuitionFee();
                tuition.setStd(std);
                tuition.setCalendar(calendar);
                try {
                    if (fee != null) {
                        tuition.setFee(fee);
                    } else {
                        tuition.setFee(new Float(feeDefaultService.getFeeDefault(std, feeType)
                                .getValue().toString()));
                    }
                } catch (Exception e) {
                    // 如果feeDefaultService.getFeeDefault()为null，则不赋
                    ;
                }
                tuition.setRemark(remark);
                tuitions.add(tuition);
            }
            utilService.saveOrUpdate(tuitions);
        } else {
            return forward(request, new Action("", "search"), "info.action.failure");
        }
        return forward(request, new Action("", "search"), "info.action.success");
    }
    
    /**
     * 收集导出内容
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        return utilService.search(buildTuitionQuery(request));
    }
    
    public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
        this.stdSearchHelper = stdSearchHelper;
    }
    
    public void setFeeDefaultService(FeeDefaultService feeDefaultService) {
        this.feeDefaultService = feeDefaultService;
    }
    
}
