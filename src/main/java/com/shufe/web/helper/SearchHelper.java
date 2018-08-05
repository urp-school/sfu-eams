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
 * chaostone             2006-12-4            Created
 *  
 ********************************************************************************/
package com.shufe.web.helper;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.commons.utils.persistence.UtilService;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.security.service.AuthorityService;
import com.shufe.service.system.baseinfo.DepartmentService;
import com.shufe.service.system.baseinfo.StudentTypeService;
import com.shufe.service.system.calendar.TeachCalendarService;

public abstract class SearchHelper {
    
    protected DepartmentService departmentService;
    
    protected StudentTypeService studentTypeService;
    
    protected AuthorityService authorityService;
    
    protected UtilService utilService;
    
    protected RestrictionHelper dataRealmHelper;
    
    protected TeachCalendarService teachCalendarService;
    
    /**
     * 从request的参数或者cookie中(参数优先)取得分页信息
     * 
     * @param request
     * @return
     */
    public PageLimit getPageLimit(HttpServletRequest request) {
        PageLimit limit = new PageLimit();
        limit.setPageNo(QueryRequestSupport.getPageNo(request));
        limit.setPageSize(QueryRequestSupport.getPageSize(request));
        return limit;
    }
    
    /**
     * 返回在权限内的指定学生类别及其子类别的列表串.
     * 
     * @param parentStdTypeId
     * @param moduleName
     * @param request
     * @return
     */
    public String getCalendarStdTypeIdSeqOf(Long parentStdTypeId, HttpServletRequest request,
            String moduleName) {
        List rs = getCalendarStdTypesOf(parentStdTypeId, request, moduleName);
        StringBuffer buf = new StringBuffer("");
        for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
            StudentType name = (StudentType) iterator.next();
            if (buf.length() != 0) {
                buf.append(",");
            }
            buf.append(name.getId());
        }
        return buf.toString();
    }
    
    public List getCalendarStdTypesOf(Long parentStdTypeId, HttpServletRequest request,
            String moduleName) {
        List calendarStdTypes = teachCalendarService.getCalendarStdTypes(parentStdTypeId);
        List stdTypes = dataRealmHelper.getStdTypes(request);
        List rs = (List) CollectionUtils.intersection(calendarStdTypes, stdTypes);
        Collections.sort(rs, new PropertyComparator("code"));
        return rs;
    }
    
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }
    
    public DepartmentService getDepartmentService() {
        return departmentService;
    }
    
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    
    public StudentTypeService getStudentTypeService() {
        return studentTypeService;
    }
    
    public void setStudentTypeService(StudentTypeService studentTypeService) {
        this.studentTypeService = studentTypeService;
    }
    
    public UtilService getUtilService() {
        return utilService;
    }
    
    public void setUtilService(UtilService utilService) {
        this.utilService = utilService;
    }
    
    public void setDataRealmHelper(RestrictionHelper dataRealmHelper) {
        this.dataRealmHelper = dataRealmHelper;
    }
    
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
    
    public static String getResourceName(HttpServletRequest request) {
        return RequestUtils.getRequestAction(request);
    }
}
