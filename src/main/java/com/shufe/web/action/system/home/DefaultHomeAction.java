package com.shufe.web.action.system.home;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.system.baseinfo.model.Course;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.security.Authentication;
import com.ekingstar.security.AuthenticationException;
import com.ekingstar.security.User;
import com.ekingstar.security.monitor.SecurityMonitor;
import com.ekingstar.security.portal.model.MenuProfile;
import com.ekingstar.security.portal.service.MenuAuthorityService;
import com.ekingstar.security.service.ResourceService;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.NewCourse;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.file.ManagerDocument;
import com.shufe.model.system.file.StudentDocument;
import com.shufe.model.system.file.TeacherDocument;
import com.shufe.model.system.message.SystemMessage;
import com.shufe.service.system.message.SystemMessageService;
import com.shufe.service.system.notice.NoticeService;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 加载用户主界面
 * 
 * @author duyaming
 * @author chaostone
 */
public class DefaultHomeAction extends DispatchBasicAction {
    
    protected SystemMessageService systemMessageService;
    
    protected SecurityMonitor securityMonitor;
    
    protected NoticeService noticeService;
    
    protected ResourceService resourceService;
    
    protected MenuAuthorityService menuAuthorityService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long userId = getUserId(request.getSession());
        if (null == userId)
            throw new AuthenticationException("without login");
        Long category = getLong(request, Authentication.USER_CATEGORYID);
        Long curCategory = getUserCategoryId(request);
        if (null == category || category.intValue() == 0) {
            category = curCategory;
        }
        // 身份发生了变化
        if (!category.equals(curCategory)) {
            securityMonitor.getSessionController().changeCategory(request.getSession().getId(),
                    category);
            request.getSession().setAttribute(Authentication.USER_CATEGORYID, category);
        }
        User user = getUser(request.getSession());
        if (category.equals(EamsRole.MANAGER_USER)) {
            List dd = menuAuthorityService.getMenus(getMenuProfile(category), user, 1, "0");
            request.setAttribute("menus", dd);
        }
        request.setAttribute("user", user);
        return forward(request);
    }
    
    public ActionForward welcome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = getUser(request.getSession());
        List notices = null;
        EntityQuery docQuery = null;
        Long curCategory = getUserCategoryId(request);
        if (curCategory.equals(EamsRole.STD_USER)) {
            List stds = utilService.load(Student.class, "code", user.getName());
            Student std = null;
            if (!stds.isEmpty()) {
                std = (Student) stds.get(0);
                addEntity(request, std);
                notices = noticeService.getStudentNotices(std);
            } else {
                throw new RuntimeException("student code cannot find:" + user.getName());
            }
            docQuery = new EntityQuery(StudentDocument.class, "doc");
            docQuery.join("doc.studentTypes", "stdType");
            docQuery.join("doc.departs", "department");
            docQuery.add(new Condition("stdType.id = (:stdTypeId)", std.getType().getId()));
            docQuery.add(new Condition("department.id = (:departmentId)", std.getDepartment()
                    .getId()));
            docQuery
                    .add(new Condition(
                            "current_date()>doc.startDate and current_date()<doc.finishDate or doc.startDate is null or doc.finishDate is null"));
            docQuery.setCacheable(true);
        } else if (curCategory.equals(EamsRole.TEACHER_USER)) {
            List teachers = utilService.load(Teacher.class, "code", user.getName());
            if (!teachers.isEmpty()) {
                Teacher teacher = (Teacher) teachers.get(0);
                request.setAttribute("teacher", teacher);
                notices = noticeService.getTeacherNotices();
            }
            docQuery = new EntityQuery(TeacherDocument.class, "doc");
            docQuery
                    .add(new Condition(
                            "current_date()>doc.startDate and current_date()<doc.finishDate or doc.startDate is null or doc.finishDate is null"));
        } else {
            notices = noticeService.getManagerNotices();
            docQuery = new EntityQuery(ManagerDocument.class, "doc");
            docQuery
                    .add(new Condition(
                            "current_date()>doc.startDate and current_date()<doc.finishDate or doc.startDate is null or doc.finishDate is null"));
        }
        addCollection(request, "notices", notices);
        docQuery.addOrder(OrderUtils.parser("doc.uploadOn desc"));
        docQuery.setLimit(new PageLimit(1, 7));
        addCollection(request, "downloadFileList", utilService.search(docQuery));
        
        //此处新增新开课程专栏
//        EntityQuery newCourseQuery=new EntityQuery(NewCourse.class,"newCourse");
//        newCourseQuery.addOrder(OrderUtils.parser("newCourse.ordernum asc"));
//        newCourseQuery.add(new Condition("newCourse.priority=1"));
//        addCollection(request, "newCourses", utilService.search(newCourseQuery));
        //*******************************
        
        request.setAttribute("newMessageCount", new Integer(systemMessageService.getMessageCount(
                user.getId(), SystemMessage.newly)));
        request.setAttribute("date", new Date(System.currentTimeMillis()));
        request.setAttribute("user", user);
        request.setAttribute("onlineUserCount", new Integer(securityMonitor.getSessionController()
                .getOnlineCount()));
        
        EntityQuery courseQuery = new EntityQuery(Course.class, "course");
        courseQuery.add(new Condition("course.state = true"));
        StringBuilder orderBy = new StringBuilder();
        orderBy.append("course.createAt desc");
        courseQuery.addOrder(OrderUtils.parser(orderBy.toString()));
        addCollection(request, "courses", utilService.search(courseQuery));
        return forward(request);
    }
    
    protected MenuProfile getMenuProfile(Long categoryId) {
        EntityQuery query = new EntityQuery(MenuProfile.class, "mp");
        query.add(new Condition("category.id=:categoryId", categoryId));
        query.setCacheable(true);
        List mps = (List) utilService.search(query);
        if (mps.isEmpty()) {
            return null;
        } else {
            return (MenuProfile) mps.get(0);
        }
    }
    
    /**
     * 加载特定模块下的所有子模块
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward moduleList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        User user = getUser(request.getSession());
        String menuParentCode = request.getParameter("parentCode");
        if (StringUtils.isEmpty(menuParentCode)) {
            menuParentCode = "0";
        }
        Long curCategory = (Long) request.getSession().getAttribute(Authentication.USER_CATEGORYID);
        List modulesTree = menuAuthorityService.getMenus(getMenuProfile(curCategory), user, 100,
                menuParentCode);
        request.setAttribute("moduleTree", modulesTree);
        request.setAttribute("parentCode", menuParentCode);
        if (log.isDebugEnabled()) {
            log.debug("load module time elapse:" + (System.currentTimeMillis() - start));
        }
        return forward(request);
    }
    
    public void setSystemMessageService(SystemMessageService systemMessageService) {
        this.systemMessageService = systemMessageService;
    }
    
    public void setSecurityMonitor(SecurityMonitor securityMonitor) {
        this.securityMonitor = securityMonitor;
    }
    
    public void setNoticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }
    
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
    
    public void setMenuAuthorityService(MenuAuthorityService menuAuthorityService) {
        this.menuAuthorityService = menuAuthorityService;
    }
}
