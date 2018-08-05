package com.shufe.web.action.course.achivement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.shufe.model.course.achivement.GradeAchivement;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 学生查看个人的综合测评结果
 * 
 * @author chaostone
 */
public class GradeAchivementStdAction extends CalendarRestrictionSupportAction {

  public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    EntityQuery query = new EntityQuery(GradeAchivement.class, "ga");
    query.add(new Condition("ga.std=:std", getStudentFromSession(request.getSession())));
    query.add(new Condition("ga.confirmed=true"));
    query.addOrder(new Order("ga.toSemester.year desc"));
    addCollection(request, "gradeAchivements", utilService.search(query));
    return forward(request);
  }

  public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    EntityQuery query = new EntityQuery(GradeAchivement.class, "ga");
    query.add(new Condition("ga.std=:std", getStudentFromSession(request.getSession())));
    query.add(new Condition("ga.id = :id", getLong(request, "gradeAchivement.id")));
    query.add(new Condition("ga.confirmed=true"));
    @SuppressWarnings("unchecked")
    List<GradeAchivement> achivements = (List<GradeAchivement>) utilService.search(query);
    if (!achivements.isEmpty()) this.addSingleParameter(request, "gradeAchivement", achivements.get(0));
    return forward(request);
  }

}
