package com.shufe.web.action.std.graduation.practice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.PracticeSource;
import com.shufe.model.std.Student;
import com.shufe.model.std.graduation.practice.GraduatePractice;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.system.baseinfo.DepartmentService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 学生填写自己的毕业实习响应类
 * 
 * @author chaostone
 * 
 */
public class StdPracticeAction extends DispatchBasicAction {
    
    private TeachCalendarService teachCalendarService;
    
    private DepartmentService departmentService;
    
    /**
     * 显示主毕业实习管理页面<br>
     * 如果学生已经有毕业实习信息则首先进行显示； 如果没有，则提示他进行毕业实习信息填写。
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(GraduatePractice.class, "graduatePractice");
        Student student = getStudentFromSession(request.getSession());
        query.add(new Condition("graduatePractice.student.id =:id", (student.getId())));
        List practices = (List) utilService.search(query);
        addCollection(request, "practices", practices);
        addCollection(request, "practiceSources", utilService.load(PracticeSource.class, "state",
                Boolean.TRUE));
        request.setAttribute("student", student);
        TeachCalendar calendar = teachCalendarService.getCurTeachCalendar(student.getType());
        request.setAttribute("calendar", calendar);
        return forward(request);
    }
    
    /**
     * 修改毕业实习信息。 <br>
     * 通过session中存储的学生信息，加在学生的毕业实习信息。
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
        EntityQuery query = new EntityQuery(GraduatePractice.class, "graduatePractice");
        Student student = getStudentFromSession(request.getSession());
        query.add(new Condition("graduatePractice.student.id =:id", (student.getId())));
        List practices = (List) utilService.search(query);
        if (!practices.isEmpty()) {
            request.setAttribute("graduatePractice", practices.get(0));
        } else {
            request.setAttribute("graduatePractice", populateEntity(request,
                    GraduatePractice.class, "graduatePractice"));
        }
        request.setAttribute("teachDepartList", departmentService.getDepartments());
        request.setAttribute("student", student);
        addCollection(request, "practiceSources", utilService.load(PracticeSource.class, "state",
                Boolean.TRUE));
        return forward(request);
    }
    
    /**
     * 保存毕业实习信息。 <br>
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
        GraduatePractice practice = (GraduatePractice) populateEntity(request,
                GraduatePractice.class, "graduatePractice");
        Student std = getStudentFromSession(request.getSession());
        practice.setStudent(std);
        practice.setPracticeTeacher((Teacher) std.getTeacher());
        utilService.saveOrUpdate(practice);
        return redirect(request, "index", "info.save.success");
    }
    
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    
}
