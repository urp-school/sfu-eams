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
 * chaostone             2006-9-1            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.plan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.data.general.DefaultPieDataset;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.course.plan.TeachPlanStatService;
import com.shufe.service.util.stat.CountItem;
import com.shufe.service.util.stat.GeneralDatasetProducer;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 培养计划统计action
 * 
 * @author chaostone
 * 
 */
public class TeachPlanStatAction extends RestrictionSupportAction {
    
    private TeachPlanStatService teachPlanStatService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    public ActionForward statByDepart(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataRealm realm = new DataRealm();
        realm.setDepartmentIdSeq(getDepartmentIdSeq(request));
        realm.setStudentTypeIdSeq(getStdTypeIdSeq(request));
        String enrollTurn = request.getParameter("enrollTurn");
        List notZeroItems = new ArrayList();
        List enrollTurns = teachPlanStatService.getEnrollTurns(realm);
        if (!enrollTurns.isEmpty()) {
            if (StringUtils.isEmpty(enrollTurn)) {
                enrollTurn = (String) enrollTurns.get(0);
            }
            List countItems = teachPlanStatService.statByDepart(realm, enrollTurn);
            Collections.sort(countItems);
            DefaultPieDataset dataset = new DefaultPieDataset();
            
            for (Iterator iter = countItems.iterator(); iter.hasNext();) {
                CountItem item = (CountItem) iter.next();
                if (item.getCount().intValue() != 0) {
                    dataset.setValue(((Department) item.getWhat()).getName(), item.getCount()
                            .intValue());
                    notZeroItems.add(item);
                }
            }
            request.setAttribute("defaultTurn", enrollTurn);
            request.setAttribute("planStatByDepart", new GeneralDatasetProducer("test", dataset));
        }
        addCollection(request, "countResult", notZeroItems);
        addCollection(request, "enrollTurns", enrollTurns);
        return forward(request);
    }
    
    public ActionForward statByStdType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataRealm realm = new DataRealm();
        realm.setDepartmentIdSeq(getDepartmentIdSeq(request));
        realm.setStudentTypeIdSeq(getStdTypeIdSeq(request));
        String enrollTurn = request.getParameter("enrollTurn");
        List enrollTurns = teachPlanStatService.getEnrollTurns(realm);
        List notZeroItems = new ArrayList();
        if (!enrollTurns.isEmpty()) {
            if (StringUtils.isEmpty(enrollTurn)) {
                enrollTurn = (String) enrollTurns.get(0);
            }
            List countItems = teachPlanStatService.statByStdType(realm, enrollTurn);
            Collections.sort(countItems);
            DefaultPieDataset dataset = new DefaultPieDataset();
            for (Iterator iter = countItems.iterator(); iter.hasNext();) {
                CountItem item = (CountItem) iter.next();
                if (item.getCount().intValue() != 0) {
                    dataset.setValue(((StudentType) item.getWhat()).getName(), item.getCount()
                            .intValue());
                    notZeroItems.add(item);
                }
            }
            request.setAttribute("defaultTurn", enrollTurn);
            request.setAttribute("planStatByDepart", new GeneralDatasetProducer("test", dataset));
        }
        addCollection(request, "countResult", notZeroItems);
        addCollection(request, "enrollTurns", enrollTurns);
        return forward(request);
    }
    
    public void setTeachPlanStatService(TeachPlanStatService teachPlanStatService) {
        this.teachPlanStatService = teachPlanStatService;
    }
    
}
