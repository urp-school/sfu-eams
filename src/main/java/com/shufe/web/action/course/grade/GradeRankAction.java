package com.shufe.web.action.course.grade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.MultiValueMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.eams.system.basecode.state.EduDegree;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public class GradeRankAction extends CalendarRestrictionSupportAction {
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeDepart);
        request.setAttribute("genderList", baseCodeService.getCodes(Gender.class));
        request.setAttribute("eduDegreeList", baseCodeService.getCodes(EduDegree.class));
        request.setAttribute("openCollegeList", departmentService
                .getTeachDeparts(getDepartmentIdSeq(request)));
        setCalendarDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        Department department = null;
        List list = new ArrayList();
        String courseSeq = get(request, "courseSeq");
        String courseName = get(request, "courseName");
        String yearfrom = get(request, "yearfrom");
        String termfrom = get(request, "termfrom");
        String yearto = get(request, "yearto");
        String termto = get(request, "termto");
        String searchType = get(request, "searchType");
        Long departmentId = getLong(request, "college.id");
        // 查询统计
        list = gradeService.getGradeRank(courseSeq, courseName, yearfrom, termfrom, yearto, termto,
                searchType, departmentId);
        Map gradeForStudentCountMap = new MultiValueMap();
        if (null == departmentId) {
            gradeForStudentCountMap.put("全校", list);
        } else {
            department = (Department) utilService.searchHQLQuery(
                    "from Department dd where dd.id=" + departmentId).get(0);
            gradeForStudentCountMap.put(department.getName(), list);
        }
        // 查询非正常考试学生
        Collection stdWithExamStatusList = gradeService.getWithExamStatus(courseSeq, courseName,
                yearfrom, termfrom, yearto, termto, searchType, departmentId, request);
        request.setAttribute("gradeForStudentCountMap", gradeForStudentCountMap);
        addCollection(request, "stdWithExamStatusList", stdWithExamStatusList);
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String courseSeq = get(request, "courseSeq");
        String courseName = get(request, "courseName");
        String yearfrom = get(request, "yearfrom");
        String termfrom = get(request, "termfrom");
        String yearto = get(request, "yearto");
        String termto = get(request, "termto");
        String searchType = get(request, "searchType");
        Long departmentId = getLong(request, "college.id");
        Collection stdWithExamIsNotPassList = gradeService.getWithExamIsNotPass(courseSeq,
                courseName, yearfrom, termfrom, yearto, termto, searchType, departmentId, request);
        addCollection(request, "stdWithExamIsNotPassList", stdWithExamIsNotPassList);
        return forward(request, "search");
    }
}
