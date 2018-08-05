package com.shufe.web.action.course.achivement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.shufe.model.course.achivement.GradeAchivementSetting;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 列举、编辑和保存综合测评设定
 * 
 * @author chaostone
 * @since 2015-6-21
 */
public class GradeAchivementSettingAction extends CalendarRestrictionSupportAction {

  public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    GradeAchivementSetting setting = (GradeAchivementSetting) getEntity(request,
        GradeAchivementSetting.class, "setting");
    Long calendarId = getLong(request, "setting.toSemester.id");
    if (null != calendarId && setting.isVO()) {
      TeachCalendar toSemester = (TeachCalendar) utilService.get(TeachCalendar.class, calendarId);
      setting.setToSemester(toSemester);
      setting.setFromSemester(toSemester.getPrevious());
    }
    this.addSingleParameter(request, "setting", setting);

    EntityQuery calendarQuery = new EntityQuery(TeachCalendar.class, "calendar");
    calendarQuery.addOrder(new Order("calendar.year desc"));
    calendarQuery.add(new Condition("calendar.term='2'"));

    addCollection(request, "calendars", utilService.search(calendarQuery));
    return forward(request);
  }

  public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    GradeAchivementSetting setting = (GradeAchivementSetting) populate(request, GradeAchivementSetting.class,
        "setting");
    // 更换汉语全角逗号
    setting.setGrades(StringUtils.replace(setting.getGrades(), "，", ",").trim());
    TeachCalendar calendar =(TeachCalendar) utilService.get(TeachCalendar.class, setting.getToSemester().getId());
    setting.setFromSemester(calendar.getPrevious());
    utilService.saveOrUpdate(setting);
    return redirect(request, "search", "info.save.success");
  }

  public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    EntityQuery query = new EntityQuery(GradeAchivementSetting.class, "setting");
    populateConditions(request, query);
    query.addOrder(new Order(get(request, Order.ORDER_STR)));
    addCollection(request, "settings", utilService.search(query));
    return forward(request);
  }

  public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String settingIds = get(request, "settingIds");
    List<GradeAchivementSetting> settings = (List<GradeAchivementSetting>) utilService.load(
        GradeAchivementSetting.class, "id", SeqStringUtil.transformToLong(settingIds));
    utilService.remove(settings);
    return redirect(request, "search", "info.remove.success");
  }

}
