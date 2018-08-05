package com.shufe.web.action.course.achivement;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.course.achivement.GradeAchivement;
import com.shufe.model.course.achivement.GradeAchivementSetting;

public class GradeAchivementDepartAction extends GradeAchivementSearchAction {

  protected EntityQuery buildQuery(HttpServletRequest request) {
    return buildAchivementQuery(request);
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
    } else {
      return redirect(request, "search", "info.stat.failure.freezen");
    }
  }
}
