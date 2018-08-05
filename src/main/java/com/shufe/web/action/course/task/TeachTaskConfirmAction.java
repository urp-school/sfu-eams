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
 * chaostone            2006-04-06          Created
 * zq                   2007-09-18          修改或替换下面的所有的info()方法
 ********************************************************************************/
package com.shufe.web.action.course.task;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.Constants;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 教学任务确认界面相应类
 * 
 * @author chaostone
 * 
 */
public class TeachTaskConfirmAction extends TeachTaskSearchAction {
    
    /**
     * 初始化教学任务生成和确认的基础数据
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        // 获得开课院系和上课教师列表
        String stdTypeDataRealm = getStdTypeIdSeq(request);
        String departDataRealm = getDepartmentIdSeq(request);
        if (request.getAttribute(Constants.CALENDAR) != null) {
            List departList = teachTaskService.getDepartsOfTask(stdTypeDataRealm, departDataRealm,
                    (TeachCalendar) request.getAttribute(Constants.CALENDAR));
            addCollection(request, "courseTypes", teachTaskService.getCourseTypesOfTask(
                    stdTypeDataRealm, departDataRealm, (TeachCalendar) request
                            .getAttribute(Constants.CALENDAR)));
            addCollection(request, "teachDepartList", teachTaskService.getTeachDepartsOfTask(
                    stdTypeDataRealm, departDataRealm, (TeachCalendar) request
                            .getAttribute(Constants.CALENDAR)));
            
            request.setAttribute(Constants.DEPARTMENT_LIST, departList);
            request.setAttribute("weeks", WeekInfo.WEEKS);
        }
        
        initBaseCodes(request, "schoolDistricts", SchoolDistrict.class);
        /*----------------加载上课学生性别列表--------------------*/
        initBaseCodes(request, "genderList", Gender.class);
        initBaseCodes(request, "courseCategoryList", CourseCategory.class);
        return forward(request);
    }
}
