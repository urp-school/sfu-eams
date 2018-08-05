package com.shufe.web.action.course.achivement;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.course.achivement.GradeAchivement;
import com.shufe.model.course.achivement.GradeAchivementSetting;
import com.shufe.service.course.achivement.GradeAchivementService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public class GradeAchivementSearchAction extends CalendarRestrictionSupportAction {
  protected GradeAchivementService gradeAchivementService;

  @SuppressWarnings("unchecked")
  public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    setDataRealm(request, hasStdTypeCollege);

    EntityQuery query = new EntityQuery(GradeAchivementSetting.class, "gas");
    query.addOrder(new Order("gas.toSemester.start desc"));
    List<GradeAchivementSetting> settings = (List<GradeAchivementSetting>) utilService.search(query);
    if (settings.isEmpty()) { return redirect(request, new Action(GradeAchivementSettingAction.class, "edit",
        "&setting.toSemester.id=" + teachCalendarService.getTeachCalendar(new Date()).getId()), ""); }
    addSingleParameter(request, "settings", settings);
    Long settingId = getLong(request, "setting.id");
    GradeAchivementSetting setting = settings.get(0);
    if (null != settingId) {
      for (GradeAchivementSetting s : settings) {
        if (s.getId().equals(settingId)) {
          setting = s;
          break;
        }
      }
    }
    this.addSingleParameter(request, "setting", setting);
    return forward(request);
  }

  public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    EntityQuery gradeAchivementQuery = this.buildQuery(request);

    gradeAchivementQuery.setLimit(this.getPageLimit(request));
    request.setAttribute("action", RequestUtils.getRequestAction(request));
    addCollection(request, "gradeAchivements", utilService.search(gradeAchivementQuery));
    return forward(request);
  }

  protected EntityQuery buildQuery(HttpServletRequest request) {
    EntityQuery query = buildAchivementQuery(request);
    query.add(new Condition("gradeAchivement.confirmed=true"));
    return query;
  }

  protected EntityQuery buildAchivementQuery(HttpServletRequest request) {
    EntityQuery query = new EntityQuery(GradeAchivement.class, "gradeAchivement");
    
    Long settingId = getLong(request,"setting.id");
    GradeAchivementSetting setting = (GradeAchivementSetting)utilService.get(GradeAchivementSetting.class, settingId);
    query.add(new Condition("gradeAchivement.fromSemester=:fromSemester", setting.getFromSemester()));
    query.add(new Condition("gradeAchivement.toSemester=:toSemester", setting.getToSemester()));
    
    query.add(new Condition("gradeAchivement.grade in (:grades)", StringUtils.split(setting.getGrades(), ",")));
    
    QueryRequestSupport.populateConditions(request, query);
    String gradePassed = get(request, "gradePassed");
    if (StringUtils.isNotBlank(gradePassed)) {
      boolean grade_passed = gradePassed.equals("1");
      if (grade_passed) query.add(new Condition(
          "gradeAchivement.iePassed = true and gradeAchivement.pePassed = true"));
      else query.add(new Condition("gradeAchivement.iePassed = false or gradeAchivement.pePassed = false"));
    }

    String cetPassed = get(request, "cet4Passed");
    if (StringUtils.isNotBlank(cetPassed)) {
      query.add(new Condition("gradeAchivement.cet4Passed=:cetPassed", cetPassed));
    }

    Long majorId = getLong(request, "specialityId");
    if (null != majorId) {
      query.add(new Condition("gradeAchivement.major.id=:majorId", majorId));
    }

//    if (CollectionUtils.isNotEmpty(this.getColleges(request)) ) {
//      query.add(new Condition("gradeAchivement.department in (:departments)", this.getColleges(request)));
//    }

    Float moralScore1 = getFloat(request, "moralScore1");
    if (null != moralScore1) {
      query.add(new Condition("gradeAchivement.moralScore>=:moralScore1", moralScore1));
    }
    Float moralScore2 = getFloat(request, "moralScore2");
    if (null != moralScore2) {
      query.add(new Condition("gradeAchivement.moralScore<=:moralScore2", moralScore2));
    }

    Float ieScore1 = getFloat(request, "ieScore1");
    if (null != ieScore1) {
      query.add(new Condition("gradeAchivement.ieScore>=:ieScore1", ieScore1));
    }
    Float ieScore2 = getFloat(request, "ieScore2");
    if (null != ieScore2) {
      query.add(new Condition("gradeAchivement.ieScore<=:ieScore2", ieScore2));
    }

    Float peScore1 = getFloat(request, "peScore1");
    if (null != peScore1) {
      query.add(new Condition("gradeAchivement.peScore>=:peScore1", peScore1));
    }
    Float peScore2 = getFloat(request, "peScore2");
    if (null != peScore2) {
      query.add(new Condition("gradeAchivement.peScore<=:peScore2", peScore2));
    }

    Float score1 = getFloat(request, "score1");
    if (null != score1) {
      query.add(new Condition("gradeAchivement.score>=:score1", score1));
    }
    Float score2 = getFloat(request, "score2");
    if (null != score2) {
      query.add(new Condition("gradeAchivement.score<=:score2", score2));
    }
    query.setOrders(OrderUtils.parser(get(request, "orderBy")));
    return query;
  }

  @SuppressWarnings("rawtypes")
  protected Collection getExportDatas(HttpServletRequest request) {
    String gradeAchivementIds = get(request, "gradeAchivementIds");
    if (StringUtils.isNotBlank(gradeAchivementIds)) { return utilService.load(GradeAchivement.class, "id",
        SeqStringUtil.transformToLong(gradeAchivementIds)); }

    EntityQuery moralAwardQuery = this.buildQuery(request);
    moralAwardQuery.setLimit(null);
    return utilService.search(moralAwardQuery);
  }

  public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long achivementId = getLong(request, "gradeAchivement.id");
    if (null == achivementId) achivementId = getLong(request, "gradeAchivementId");
    GradeAchivement achivement = (GradeAchivement) utilService.get(GradeAchivement.class, achivementId);
    this.addSingleParameter(request, "gradeAchivement", achivement);
    return forward(request);
  }

  public void setGradeAchivementService(GradeAchivementService gradeAchivementService) {
    this.gradeAchivementService = gradeAchivementService;
  }
}
