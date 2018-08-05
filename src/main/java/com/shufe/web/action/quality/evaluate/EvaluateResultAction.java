//$Id: EvaluateResultAction.java,v 1.1 2008-1-16 下午01:08:22 zhouqi Exp $
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
 * zhouqi              2008-1-16         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.evaluate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.model.quality.evaluate.EvaluateResult;
import com.shufe.web.action.common.CalendarRestrictionExampleTemplateAction;

/**
 * @author zhouqi
 */
public class EvaluateResultAction extends CalendarRestrictionExampleTemplateAction {
    
    /**
     * 查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(EvaluateResult.class, "evaluateResult");
        populateConditions(request, query);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, getEntityName() + "s", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 批量修改评教状态
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateState(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Boolean statState = getBoolean(request, "isEvaluate");
        if (null == statState) {
            statState = Boolean.FALSE;
        }
        Long[] ids = SeqStringUtil.transformToLong(get(request, "evaluateResultIds"));
        List results = (List) utilService.load(EvaluateResult.class, "id", ids);
        for (Iterator it = results.iterator(); it.hasNext();) {
            EvaluateResult result = (EvaluateResult) it.next();
            result.setStatState(statState);
        }
        utilService.saveOrUpdate(results);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 查看标准信息
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
        EvaluateResult result = (EvaluateResult) getEntity(request, entityClass, entityName);
        List questionResults = new ArrayList(result.getQuestionResultSet());
        Collections.sort(questionResults, new PropertyComparator("question"));
        request.setAttribute("questions", questionResults);
        return forward(request);
    }
}
