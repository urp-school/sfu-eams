package com.shufe.web.action.course.achivement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.course.achivement.GradeAchivement;
import com.shufe.model.course.achivement.GradeAchivementSetting;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.web.helper.RestrictionHelper;

/**
 * 辅导员管理综合测评
 * 
 * @author chaostone
 */
public class GradeAchivementInstructorAction extends GradeAchivementSearchAction {

  @SuppressWarnings("unchecked")
  public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // 取得辅导员的可见学生类别和部门权限
    EntityQuery stdQuery = new EntityQuery(AdminClass.class, "ad");
    stdQuery.add(new Condition("ad.instructor.code = :me", getLoginName(request)));
    stdQuery.setSelect("distinct ad.stdType");
    request.setAttribute(RestrictionHelper.STDTYPES_KEY, utilService.search(stdQuery));

    EntityQuery departQuery = new EntityQuery(AdminClass.class, "ad");
    departQuery.add(new Condition("ad.instructor.code = :me", getLoginName(request)));
    departQuery.setSelect("distinct ad.department");
    request.setAttribute(RestrictionHelper.DEPARTS_KEY, utilService.search(departQuery));

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

  protected EntityQuery buildQuery(HttpServletRequest request) {
    EntityQuery query = buildAchivementQuery(request);
    query.add(new Condition("gradeAchivement.adminClass.instructor.code = :me", getLoginName(request)));
    return query;
  }

  public ActionForward stat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String gradeAchivementIds = get(request, "gradeAchivementIds");
    Long settingId = getLong(request, "setting.id");
    GradeAchivementSetting setting = (GradeAchivementSetting) utilService.get(GradeAchivementSetting.class,
        settingId);
    List<GradeAchivement> achivements = (List<GradeAchivement>) utilService.load(GradeAchivement.class, "id",
        SeqStringUtil.transformToLong(gradeAchivementIds));

    List<GradeAchivement> unConfirmed = new ArrayList<GradeAchivement>();
    for (GradeAchivement a : achivements) {
      if (!a.isConfirmed()) unConfirmed.add(a);
    }
    if (unConfirmed.size() > 0) {
      gradeAchivementService.stat(unConfirmed, setting);
      return redirect(request, "search", "info.stat.success");
    }else{
      return redirect(request, "search", "info.stat.failure.freezen");
    }
  }

}
