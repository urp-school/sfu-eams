package com.ekingstar.eams.research.project.web.action;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.eams.research.project.model.TeachProject;
import com.ekingstar.eams.system.basecode.industry.TeachProjectState;
import com.ekingstar.eams.system.basecode.industry.TeachProjectType;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

public class TeachProjectAction extends RestrictionExampleTemplateAction{
    
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request,"teachProjectTypes",baseCodeService.getCodes(TeachProjectType.class));
        addCollection(request,"teachProjectStates",baseCodeService.getCodes(TeachProjectState.class));
        return forward(request);
    }
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request,"teachProjectTypes",baseCodeService.getCodes(TeachProjectType.class));
        addCollection(request,"teachProjectStates",baseCodeService.getCodes(TeachProjectState.class));
        return super.edit(mapping, form, request, response);
    }
	
	protected ActionForward saveAndForwad(HttpServletRequest request, Entity entity) throws Exception {
		
		TeachProject teachProject = (TeachProject)entity;
		
		if((teachProject.getId())==null){
			teachProject.setCreateAt(new Date(System.currentTimeMillis()));
			teachProject.setPetitionBy(getUser(request.getSession()));
		}
		utilService.saveOrUpdate(teachProject);
		return redirect(request, "search", "info.save.success");
	}

}
