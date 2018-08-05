//$Id: AbroadInfoAction.java,v 1.1 2007-7-17 上午08:49:09 chaostone Exp $
package com.shufe.web.action.webservice;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.std.Student;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 金融学院在读证明接口
 * @author qianjia
 *
 */
public class CertificationForStdAction extends RestrictionSupportAction {
    
    /**
     * http://portal.shfc.edu.cn/eams/certificationForStd.do?StudentNo=2005115116
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * 	([{stumum:"2009115116", name:"小明", sex:"男", stdType:"本科", department:"经济学院", major:"经济学", birthdate:"1991-08-20", los:"四", entrance:"2002-9", graduate:"2006-6"}])
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String studentNo = get(request, "StudentNo");
        response.setContentType("application/json;charset=UTF-8");
        if(StringUtils.isBlank(studentNo)) {
        	return forward(request);
        }
        
        EntityQuery query = new EntityQuery(Student.class, "std");
        query.add(new Condition("std.code=:code", StringUtils.trim(studentNo)));
        Collection<Student> stds = utilService.search(query);
        if(CollectionUtils.isEmpty(stds)) {
        	return forward(request);
        }
        request.setAttribute("std", stds.iterator().next());
        return forward(request);
    }
    
}
