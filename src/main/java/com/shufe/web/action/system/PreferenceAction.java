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
 * chaostone             2006-9-23            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.utils.web.CookieUtils;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.config.SystemConfig;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.ekingstar.security.Authentication;
import com.ekingstar.security.User;
import com.ekingstar.security.portal.model.MenuProfile;
import com.ekingstar.security.portal.service.MenuAuthorityService;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;
import com.shufe.web.action.system.home.HomeAction;

/**
 * 首选项设置
 * 
 * @author chaostone
 * 
 */
public class PreferenceAction extends CalendarRestrictionSupportAction {
    
    MenuAuthorityService menuAuthorityService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // 教学日历偏好
        StudentType stdType = null;
        String stdTypeId = CookieUtils.getCookieValue(request, "calendar.studentType.id");
        String year = CookieUtils.getCookieValue(request, "calendar.year");
        String term = CookieUtils.getCookieValue(request, "calendar.term");
        
        if (StringUtils.isNotEmpty(stdTypeId) && StringUtils.isNotEmpty(year)
                && StringUtils.isNotEmpty(term)) {
            TeachCalendar calendar = teachCalendarService.getTeachCalendar(Long.valueOf(stdTypeId),
                    year, term);
            if (null != calendar) {
                request.setAttribute("calendar", calendar);
                stdType = (StudentType) utilService.get(StudentType.class, Long.valueOf(stdTypeId));
            }
        }
        List stdTypes = baseInfoService.getBaseInfos(StudentType.class);
        addCollection(request, "stdTypes", stdTypes);
        if (null == request.getAttribute("calendar")) {
            TeachCalendar calendar = teachCalendarService.getTeachCalendar((StudentType) stdTypes
                    .get(0));
            request.setAttribute("calendar", calendar);
            stdType = (StudentType) stdTypes.get(0);
        }
        addEntity(request, stdType);
        
        // 页码、页长偏好
        String pageSize = CookieUtils.getCookieValue(request, "pageSize");
        if (null == pageSize)
            pageSize = "20";
        request.setAttribute("pageSize", pageSize);
        SystemConfig config = SystemConfigLoader.getConfig();
        // 主界面布局
        String facade = CookieUtils.getCookieValue(request, HomeAction.FACADE);
        if (null == facade)
            facade = (String) config.getConfigItemValue(HomeAction.FACADE);
        request.setAttribute("facade", facade);
        // 语言偏好
        String language = CookieUtils.getCookieValue(request, "language");
        if (null == language)
            language = "zh_CN";
        request.setAttribute("language", language);
        String username = CookieUtils.getCookieValue(request, Authentication.NAME);
        if (null == username) {
            request.setAttribute("rememberMe", Boolean.FALSE);
        } else {
            request.setAttribute("rememberMe", Boolean.TRUE);
        }
        request.setAttribute("maxInactiveInterval",request.getSession().getMaxInactiveInterval());
        return forward(request);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        int cookieAge = 60 * 60 * 24 * 30 * 6;// half year
        CookieUtils.setCookie(request, response, "calendar.studentType.id", request
                .getParameter("calendar.studentType.id"), cookieAge);
        CookieUtils.setCookie(request, response, "calendar.year", request
                .getParameter("calendar.year"), cookieAge);
        CookieUtils.setCookie(request, response, "calendar.term", request
                .getParameter("calendar.term"), cookieAge);
        
        CookieUtils.setCookie(request, response, "pageSize", request.getParameter("pageSize"),
                cookieAge);
        CookieUtils.setCookie(request, response, HomeAction.FACADE, request
                .getParameter(HomeAction.FACADE), cookieAge);
        
        Boolean rememberMe = getBoolean(request, "rememberMe");
        User user = getUser(request.getSession());
        if (Boolean.TRUE.equals(rememberMe)) {
            CookieUtils
                    .setCookie(request, response, Authentication.NAME, user.getName(), cookieAge);
            CookieUtils.setCookie(request, response, Authentication.PASSWORD, user.getPassword(),
                    cookieAge);
        } else {
            CookieUtils.deleteCookieByName(request, response, Authentication.NAME);
            CookieUtils.deleteCookieByName(request, response, Authentication.PASSWORD);
        }
        CookieUtils.setCookie(request, response, "language", request.getParameter("language"),
                cookieAge);
        return redirect(request, "index", "info.set.success");
    }
    
    public ActionForward menuList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = getUser(request);
        EntityQuery query = new EntityQuery(MenuProfile.class, "profile");
        query.add(new Condition("profile.category in(:categories)", user.getCategories()));
        List profiles = (List) utilService.search(query);
        List menus = new ArrayList();
        if (!profiles.isEmpty()) {
            MenuProfile profile = (MenuProfile) profiles.get(0);
            Long profileId = getLong(request, "menuProfile.id");
            if (null != profileId) {
                for (Iterator iterator = profiles.iterator(); iterator.hasNext();) {
                    MenuProfile one = (MenuProfile) iterator.next();
                    if (one.getId().equals(profileId)) {
                        profile = one;
                        break;
                    }
                }
            }
            menus = menuAuthorityService.getMenus(profile, user);
        }
        addCollection(request, "menus", menus);
        return forward(request);
    }
    
    public void setMenuAuthorityService(MenuAuthorityService menuAuthorityService) {
        this.menuAuthorityService = menuAuthorityService;
    }
}
