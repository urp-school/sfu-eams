package com.ekingstar.eams.research.project.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.research.project.model.ProjectMember;
import com.ekingstar.eams.research.project.model.TeachProject;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.basecode.state.TeacherTitle;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

public class ProjectMemberAction extends RestrictionExampleTemplateAction {
    
    // 查看所选项目的 成员列表
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String teachProjectIdSeq = request.getParameter("teachProjectId");
        if ((!"".equals(teachProjectIdSeq)) && (null != teachProjectIdSeq)) {
            TeachProject teachProject = (TeachProject) utilService.get(TeachProject.class, Long
                    .valueOf(teachProjectIdSeq));
            addCollection(request, "projectMembers", teachProject.getProjectMembers());
            request.setAttribute("teachProjectId", teachProjectIdSeq);
        }
        request.setAttribute("teachProjectId", teachProjectIdSeq);
        return forward(request);
    }
    
    // 增加 或者 更新
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String teachProjectId = request.getParameter("teachProjectId");
        request.setAttribute("teachProjectId", teachProjectId);
        addCollection(request, "genders", baseCodeService.getCodes(Gender.class));
        addCollection(request, "teacherTitles", baseCodeService.getCodes(TeacherTitle.class));
        return super.edit(mapping, form, request, response);
    }
    
    // 保存
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long teachProjectId = getLong(request, "teachProjectId");
        TeachProject teachProject = (TeachProject) utilService.get(TeachProject.class,
                teachProjectId);
        ProjectMember projectMember = (ProjectMember) populateEntity(request, ProjectMember.class,
                "projectMember");
        
        projectMember.setTeachProject(teachProject);
        
        utilService.saveOrUpdate(projectMember);
        return redirect(request, new Action("", "search"), "info.save.success",
                new String[] { "teachProjectId" });
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String projectMemberIdSeq = request.getParameter("projectMemberIds");
        Long[] projectMemberIds = SeqStringUtil.transformToLong(projectMemberIdSeq);
        utilService.remove(ProjectMember.class, "id", projectMemberIds);
        return redirect(request, new Action("", "search"), "info.save.success",
                new String[] { "teachProjectId" });
    }
}
