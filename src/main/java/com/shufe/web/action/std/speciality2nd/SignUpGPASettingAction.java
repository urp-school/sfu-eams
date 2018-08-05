//$Id: SignUpGPASettingAction.java,v 1.1 2007-5-4 下午04:15:19 chaostone Exp $
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
 *chaostone      2007-5-4         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.speciality2nd;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.std.speciality2nd.SignUpGPASetting;
import com.shufe.web.action.common.ExampleTemplateAction;

public class SignUpGPASettingAction extends ExampleTemplateAction {
    
    protected void indexSetting(HttpServletRequest request) {
        List settings = (List) utilService.search(buildQuery(request));
        addCollection(request, getEntityName() + "s", settings);
    }
    
    /**
     * 保存
     * 
     * @see com.shufe.web.action.common.ExampleTemplateAction#saveAndForwad(javax.servlet.http.HttpServletRequest,
     *      com.ekingstar.commons.model.Entity)
     */
    protected ActionForward saveAndForwad(HttpServletRequest request, Entity entity)
            throws Exception {
        SignUpGPASetting setting = (SignUpGPASetting) entity;
        EntityQuery query = new EntityQuery(SignUpGPASetting.class, "setting");
        query.add(new Condition("setting.fromRank <= :toRank", setting.getToRank()));
        query.add(new Condition("setting.toRank >= :fromRank", setting.getFromRank()));
        if (setting.getId() != null) {
            query.add(new Condition("setting.id != :id", setting.getId()));
        }
        if (((List) utilService.search(query)).size() > 0) {
            return redirect(request, "search", "info.save.failure.overlapAcross");
        }
        return super.saveAndForwad(request, setting);
    }
    
}
