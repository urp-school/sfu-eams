//$Id: GradeSwitchSettingAction.java,v 1.1 2008-1-4 下午04:43:56 zhouqi Exp $
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
 * zhouqi              2008-1-4         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.grade;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.mvc.struts.misc.ForwardSupport;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.shufe.model.course.grade.GradeInputSwitch;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 成绩录入开关设置
 * 
 * @author zhouqi
 */
public class GradeInputSwitchAction extends CalendarRestrictionSupportAction {
    
    /**
     * 查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(GradeInputSwitch.class, "gradeInputSwitch");
        populateConditions(request, query);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "gradeInputSwitchs", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 转入新建或修改页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long id = getLong(request, "gradeInputSwitchId");
        GradeType gradeType = new GradeType();
        gradeType.setTeacherCanInputGrade(Boolean.TRUE);
        List canInputTypes = (List) utilService.loadAll(GradeType.class);
        request.setAttribute("canInputTypes", canInputTypes);
        if (id != null) {
            GradeInputSwitch gradeInputSwitch = (GradeInputSwitch)utilService.load(GradeInputSwitch.class,
                    id);
            request.setAttribute("mngGradeTypes", gradeInputSwitch.getGradeTypes());
            request.setAttribute("gradeInputSwitch", utilService.load(GradeInputSwitch.class,
                    id));
        } else {
            setCalendarDataRealm(request, hasStdTypeCollege);
        }
        return forward(request);
    }
    
    /**
     * 保存
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        GradeInputSwitch gradeInputSwitch = (GradeInputSwitch) populateEntity(request,
                GradeInputSwitch.class, "gradeInputSwitch");
        if (gradeInputSwitch.getCalendar() == null) {
            EntityQuery query = new EntityQuery(TeachCalendar.class, "calendar");
            populateConditions(request, query);
            TeachCalendar calendar = new TeachCalendar();
            calendar.setId(((TeachCalendar) ((List) utilService.search(query)).get(0)).getId());
            gradeInputSwitch.setCalendar(calendar);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        gradeInputSwitch.setStartAt(sdf.parse(get(request, "startDate") + " "
                + get(request, "startTime")));
        gradeInputSwitch.setEndAt(sdf
                .parse(get(request, "endDate") + " " + get(request, "endTime")));
        
        // 验证新建或修改的记录是否有重复
        EntityQuery query = new EntityQuery(GradeInputSwitch.class, "gradeInputSwitch");
        query.add(new Condition("gradeInputSwitch.startAt = (:startAt)", gradeInputSwitch
                .getStartAt()));
        query.add(new Condition("gradeInputSwitch.endAt = (:endAt)", gradeInputSwitch.getEndAt()));
        if (gradeInputSwitch.getId() != null) {
            query.add(new Condition("gradeInputSwitch.calendar.id = (:calendarId)",
                    gradeInputSwitch.getCalendar().getId()));
            query.add(new Condition("gradeInputSwitch.id != (:id)", gradeInputSwitch.getId()));
        } else {
            TeachCalendar calendar = (TeachCalendar) populateEntity(request, TeachCalendar.class,
                    "calendar");
            query.add(new Condition("gradeInputSwitch.calendar.studentType.id = (:stdTypeId)",
                    calendar.getStudentType().getId()));
            query
                    .add(new Condition("gradeInputSwitch.calendar.year = (:year)", calendar
                            .getYear()));
            query
                    .add(new Condition("gradeInputSwitch.calendar.term = (:term)", calendar
                            .getTerm()));
        }
        List checkResults = (List) utilService.search(query);
        if (checkResults.size() > 0) {
            request.setAttribute("gradeInputSwitch", gradeInputSwitch);
            saveErrors(request.getSession(), ForwardSupport
                    .buildMessages(new String[] { "info.save.failure.overlap" }));
            return forward(request, "form");
        } else {
            // 保存新的 可录入成绩类型
            String gradeTypeIds = request.getParameter("gradeTypeIds");
            EntityQuery gradeTypeQuery = new EntityQuery(GradeType.class, "gradeType");
            gradeTypeQuery.add(new Condition("gradeType.id in (:gradeTypeId)",SeqStringUtil.transformToLong(gradeTypeIds)));
            List gradeTypes = (List) utilService.search(gradeTypeQuery);
            gradeInputSwitch.getGradeTypes().clear();
            gradeInputSwitch.getGradeTypes().addAll(gradeTypes);
            utilService.saveOrUpdate(gradeInputSwitch);
            return redirect(request, "search", "info.save.success");
        }
    }
    
    /**
     * 删除
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long[] ids = SeqStringUtil.transformToLong(get(request, "gradeInputSwitchId"));
        List removeList = (List) utilService.load(GradeInputSwitch.class, "id", ids);
        utilService.remove(removeList);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 查看
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long id = getLong(request, "gradeInputSwitchId");
        request.setAttribute("gradeInputSwitch", utilService.load(GradeInputSwitch.class, id));
        return forward(request);
    }
}
