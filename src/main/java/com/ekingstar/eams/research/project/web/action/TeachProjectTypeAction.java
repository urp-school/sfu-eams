package com.ekingstar.eams.research.project.web.action;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.eams.system.basecode.industry.TeachProjectType;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

public class TeachProjectTypeAction extends RestrictionExampleTemplateAction {
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request,"teachProjectTypes",baseCodeService.getCodes(TeachProjectType.class));
        return super.edit(mapping, form, request, response);
    }
	
	protected ActionForward saveAndForwad(HttpServletRequest request, Entity entity) throws Exception {
		
		TeachProjectType teachProjectType = (TeachProjectType)entity;
		
		if((teachProjectType.getId())==null){
			teachProjectType.setCreateAt(new Date(System.currentTimeMillis()));
		}
		teachProjectType.setModifyAt(new Date(System.currentTimeMillis()));

		utilService.saveOrUpdate(teachProjectType);
		return redirect(request, "search", "info.save.success");
	}
	
}
