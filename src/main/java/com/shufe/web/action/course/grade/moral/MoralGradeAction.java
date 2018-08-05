package com.shufe.web.action.course.grade.moral;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.grade.MoralGrade;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionExampleTemplateAction;

public class MoralGradeAction extends CalendarRestrictionExampleTemplateAction {

  public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    EntityQuery query = new EntityQuery(MoralGrade.class, "moralGrade");
    QueryRequestSupport.populateConditions(request, query, "moralGrade.std.type.id");
    DataRealmUtils.addDataRealms(query, new String[] { "moralGrade.std.type.id",
        "moralGrade.std.department.id" },
        getDataRealmsWith(getLong(request, "moralGrade.std.type.id"), request));
    Long departId = getLong(request, "department.id");
    Long specialityId = getLong(request, "speciality.id");
    Long aspectId = getLong(request, "specialityAspect.id");
    if (null != aspectId) query.add(new Condition("moralGrade.std.firstAspect.id=:aspectId", aspectId));
    if (null != specialityId) {
      query.add(new Condition("moralGrade.std.firstMajor.id=:specialityId", specialityId));
    } else {
      if (null != departId) query.add(new Condition("moralGrade.std.department.id=:departId", departId));
    }
    query.setLimit(getPageLimit(request));
    query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
    addCollection(request, "moralGrades", utilService.search(query));
    return forward(request);
  }

  /**
   * 更新成绩状态
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward updateStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Integer status = getInteger(request, "status");
    if (null != status) {
      if (status.intValue() == Grade.CONFIRMED || status.intValue() == Grade.PUBLISHED) {
        Long[] gradeIds = SeqStringUtil.transformToLong(request.getParameter("moralGradeIds"));
        List grades = utilService.load(MoralGrade.class, "id", gradeIds);
        for (Iterator iterator = grades.iterator(); iterator.hasNext();) {
          MoralGrade grade = (MoralGrade) iterator.next();
          grade.setStatus(status);
        }
        utilService.saveOrUpdate(grades);
      }
    }
    return redirect(request, "search", "info.action.success");
  }

}
