package com.shufe.web.action.course.attend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.course.attend.AttendWarn;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public class AttendWarnAction extends CalendarRestrictionSupportAction{

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeDepart);
        return forward(request);
	}
	
	protected EntityQuery buildQuery(HttpServletRequest request){
		EntityQuery query = new EntityQuery(AttendWarn.class,"attendWarn");
		populateConditions(request, query, "attendWarn.std.type.id");		
		MajorType majorType = new MajorType(getLong(request, "majorType.id"));
        Long departId = getLong(request, "department.id");
        Long specialityId = getLong(request, "speciality.id");
        Long aspectId = getLong(request, "specialityAspect.id");
        if (!MajorType.SECOND.equals(majorType.getId())) {
            if (null != aspectId) {
            	query.add(new Condition("attendWarn.std.firstAspect.id=" + aspectId));
            }
            if (null != specialityId) {
                query.add(new Condition("attendWarn.std.firstMajor.id=" + specialityId));
            } else {
                if (null != departId) {
                    query.add(new Condition("attendWarn.std.department.id=" + departId));
                }
            }
        } else {
            if (null != aspectId) {
                query.add(new Condition("attendWarn.std.secondAspect.id=" + aspectId));
            } else {
                if (null != specialityId) {
                    query.add(new Condition("attendWarn.std.secondMajor.id=" + specialityId));
                } else {
                    if (null != departId) {
                        query.add(new Condition("attendWarn.std.secondMajor.department.id=" + departId));
                    }
                }
            }
        }
        // 添加权限
        String departName = "attendWarn.std.department.id";
        if (MajorType.SECOND.equals(majorType.getId())) {
            departName = "attendWarn.std.secondMajor.department.id";
        }		
		Long stdTypeId = getLong(request, "attendWarn.std.type.id");
		DataRealmUtils.addDataRealms(query, new String[] { "attendWarn.std.type.id",
				departName }, getDataRealmsWith(stdTypeId, request));
		Integer yjz=getInteger(request, "yjz");
		if (yjz!=null) {
			query.add(new Condition("attendWarn.ljks >= "+yjz));
		}
		Long adminClassId = getLong(request, "adminClass.id");
        if (null!=adminClassId) {
        	query.join("attendWarn.std.adminClasses", "adminClass");
        	query.add(new Condition("adminClass.id="+adminClassId));
        }
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));		
		return query;
	}
	
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		addCollection(request, "attendWarns", utilService.search(buildQuery(request)));
		return forward(request);
	}
	
}
