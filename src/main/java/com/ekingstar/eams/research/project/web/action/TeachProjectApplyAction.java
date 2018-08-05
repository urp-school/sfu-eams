package com.ekingstar.eams.research.project.web.action;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.research.project.model.TeachProject;
import com.ekingstar.eams.research.project.model.TeachProjectTemplate;
import com.ekingstar.eams.system.basecode.industry.TeachProjectState;
import com.ekingstar.eams.system.basecode.industry.TeachProjectType;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

public class TeachProjectApplyAction extends RestrictionExampleTemplateAction {
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request,"teachProjectTypes",baseCodeService.getCodes(TeachProjectType.class));
        addCollection(request,"teachProjectStates",baseCodeService.getCodes(TeachProjectState.class));
        return forward(request);
    }
	
	protected EntityQuery buildQuery(HttpServletRequest request) {
		EntityQuery query = new EntityQuery(entityClass, getEntityName());
		populateConditions(request, query);
		query.add(new Condition(" teachProject.petitionBy.id=:petitionById", getUser(request.getSession()).getId()));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		query.setLimit(getPageLimit(request));
		return query;
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
			teachProject.setTeachProjectState(null);
		}
		utilService.saveOrUpdate(teachProject);
		return redirect(request, "search", "info.save.success");
	}
	
	public ActionForward templateList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		EntityQuery query = new EntityQuery(TeachProjectTemplate.class, "teachProjectTemplate");
		populateConditions(request, query);
		query.setLimit(getPageLimit(request));
		addCollection(request, "teachProjectTemplates", utilService.search(query));
		return forward(request);
    }
	
	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		return forward(request, new Action(TeachProjectTemplateAction.class, "download"));
    }
}
