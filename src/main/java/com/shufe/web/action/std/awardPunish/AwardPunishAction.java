//$Id: AwardPunishAction.java,v 1.1 2007-5-30 上午11:47:38 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-5-30         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.awardPunish;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.eams.system.basecode.industry.AwardType;
import com.ekingstar.eams.system.basecode.industry.PunishmentType;
import com.shufe.model.std.awardPunish.Award;
import com.shufe.model.std.awardPunish.Punishment;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.std.AwardPunishImportListener;

/**
 * 奖励处分管理类
 * 
 * @author chaostone
 */
public class AwardPunishAction extends AwardPunishSearchAction {
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        detectAwardPunish(request);
        setDataRealm(request, hasStdTypeCollege);
        return super.edit(mapping, form, request, response);
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        detectAwardPunish(request);
        return super.remove(mapping, form, request, response);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        detectAwardPunish(request);
        return super.save(mapping, form, request, response);
    }
    
    protected ActionForward saveAndForwad(HttpServletRequest request, Entity entity)
            throws Exception {
        TeachCalendar calendar = (TeachCalendar) teachCalendarService.getTeachCalendar(getLong(
                request, "calendar.studentType.id"), get(request, "calendar.year"), get(request,
                "calendar.term"));
        if (entity instanceof Award) {
            Award award = (Award) entity;
            award.setCalendar(calendar);
        } else {
            Punishment punishment = (Punishment) entity;
            punishment.setCalendar(calendar);
        }
        
        return super.saveAndForwad(request, entity);
    }
    
    protected void editSetting(HttpServletRequest request, Entity entity) throws Exception {
        detectAwardPunish(request);
        if (getEntityClass().equals(Award.class)) {
            addCollection(request, "types", baseCodeService.getCodes(AwardType.class));
            request.setAttribute("isAward", Boolean.TRUE);
        } else {
            addCollection(request, "types", baseCodeService.getCodes(PunishmentType.class));
            request.setAttribute("isAward", Boolean.FALSE);
        }
        
        super.editSetting(request, entity);
    }
    
    /**
     * 导入奖惩信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TransferResult tr = new TransferResult();
        String kind = request.getParameter("kind");
        Transfer transfer = null;
        if ("award".equals(kind)) {
            transfer = ImporterServletSupport.buildEntityImporter(request, Award.class, tr);            
        } else {
            transfer = ImporterServletSupport.buildEntityImporter(request, Punishment.class, tr);
        }

        if (null == transfer) {
            return forward(request, "/pages/components/importData/error");
        }
        transfer.addListener(new ImporterForeignerListener(utilService)).addListener(
                new AwardPunishImportListener(utilService.getUtilDao(),kind));
        transfer.transfer(tr);
        if (tr.hasErrors()) {
            return forward(request, "/pages/components/importData/error");
        } else {
            return forward(request, "/pages/components/importData/result");
        }
    }
    
}
