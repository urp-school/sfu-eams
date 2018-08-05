//$Id: MarkStyleConfigAction.java,v 1.1 2008-7-3 下午01:41:39 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2008-7-3             Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.course.grade.converter.DefaultConverterConfig;
import com.ekingstar.eams.course.grade.converter.DefaultConverterItem;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * @author zhouqi
 * 
 */
public class MarkStyleConfigAction extends CalendarRestrictionSupportAction {
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(DefaultConverterConfig.class, "defaultConfig");
        populateConditions(request, query);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "defaultConfigs", utilService.search(query));
        return forward(request);
    }
    
    public ActionForward addConfig(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(MarkStyle.class, "markStyle");
        populateConditions(request, query);
        query
                .add(new Condition(
                        "markStyle.id not in (select defaultConfig.markStyle.id from DefaultConverterConfig defaultConfig)"));
        addCollection(request, "markStyles", utilService.search(query));
        return forward(request);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long[] markStyleIds = SeqStringUtil.transformToLong(get(request, "markStyleIds"));
        List markStyles = utilService.load(MarkStyle.class, "id", markStyleIds);
        List defaultConfigs = new ArrayList();
        for (Iterator it = markStyles.iterator(); it.hasNext();) {
            MarkStyle markStyle = (MarkStyle) it.next();
            DefaultConverterConfig defaultConfig = new DefaultConverterConfig();
            defaultConfig.setMarkStyle(markStyle);
            defaultConfig.setPassScore(markStyle.getPassScore());
            defaultConfigs.add(defaultConfig);
        }
        utilService.saveOrUpdate(defaultConfigs);
        return redirect(request, "search", "info.action.success");
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long[] defaultConfigIds = SeqStringUtil.transformToLong(get(request, "defaultConfigIds"));
        List defaultConfigs = utilService
                .load(DefaultConverterConfig.class, "id", defaultConfigIds);
        utilService.remove(defaultConfigs);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 对某项成绩记录方式其分数显示详细设置
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward setting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("defaultConfig", utilService.load(DefaultConverterConfig.class,
                getLong(request, "defaultConfigId")));
        return forward(request);
    }
    
    /**
     * 保存详细配置
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveConfigSettng(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DefaultConverterConfig defaultConfig = (DefaultConverterConfig) utilService.load(
                DefaultConverterConfig.class, getLong(request, "defaultConfigId"));
        Long[] configItemIds = SeqStringUtil.transformToLong(get(request, "configItemIds"));
        
        // 添加配置项
        if (null == configItemIds || configItemIds.length == 0) {
            List converters = defaultConfig.getConverters();
            if (CollectionUtils.isEmpty(converters)) {
                converters = new ArrayList();
            }
            // 此类中没有id
            DefaultConverterItem configItem = (DefaultConverterItem) populateEntity(request,
                    DefaultConverterItem.class, "configItem");
            defaultConfig.getConverters().add(configItem);
            configItem.setConfig(defaultConfig);
            utilService.saveOrUpdate(defaultConfig);
            return redirect(request, "setting", "info.action.success");
        }
        // 修改配置项
        else {
            Map itemMap = new HashMap();
            for (Iterator it = defaultConfig.getConverters().iterator(); it.hasNext();) {
                DefaultConverterItem configItem = (DefaultConverterItem) it.next();
                itemMap.put(configItem.getId(), configItem);
            }
            for (int i = 0; i < configItemIds.length; i++) {
                DefaultConverterItem configItem = (DefaultConverterItem) itemMap
                        .get(configItemIds[i]);
                configItem.setGrade(get(request, "scoreName" + configItemIds[i]));
                configItem.setMaxScore(getFloat(request, "maxScore" + configItemIds[i]));
                configItem.setMinScore(getFloat(request, "minScore" + configItemIds[i]));
                configItem.setDefaultScore(getFloat(request, "defaultScore" + configItemIds[i]));
            }
            utilService.saveOrUpdate(defaultConfig);
            return redirect(request, "search", "info.action.success");
        }
        
    }
    
    /**
     * 删除配置项
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeConfigSettng(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        utilService.remove(utilService.load(DefaultConverterItem.class, "id", SeqStringUtil
                .transformToLong(get(request, "configItemIds"))));
        return redirect(request, "setting", "info.action.success");
    }
    
    /**
     * 查看详细配置
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setAttribute("defaultConfig", utilService.load(DefaultConverterConfig.class,
                getLong(request, "defaultConfigId")));
        return forward(request);
    }
}
