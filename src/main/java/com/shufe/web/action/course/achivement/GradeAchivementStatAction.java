package com.shufe.web.action.course.achivement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.commons.utils.query.QueryPage;
import com.shufe.model.course.achivement.GradeAchivement;
import com.shufe.model.course.achivement.GradeAchivementSetting;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.course.achivement.AdminClassIdStat;
import com.shufe.service.course.achivement.AdminClassStat;

/**
 * 综合测评统计
 * 
 * @author chaostone
 */
public class GradeAchivementStatAction extends GradeAchivementSearchAction {

  public ActionForward stat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String gradeAchivementIds = get(request, "gradeAchivementIds");
    Long settingId = getLong(request, "setting.id");
    GradeAchivementSetting setting = (GradeAchivementSetting) utilService.get(GradeAchivementSetting.class,
        settingId);
    if (null == gradeAchivementIds) {
      EntityQuery query = new EntityQuery(GradeAchivement.class, "gradeAchivement");
      query.add(new Condition("gradeAchivement.fromSemester=:fromSemester", setting.getFromSemester()));
      query.add(new Condition("gradeAchivement.toSemester=:toSemester", setting.getToSemester()));
      query.add(
          new Condition("gradeAchivement.grade in (:grades)", StringUtils.split(setting.getGrades(), ",")));
      // if (CollectionUtils.isNotEmpty(this.getColleges(request))) {
      // query.add(new Condition("gradeAchivement.department in (:departments)",
      // this.getColleges(request)));
      // }
      query.addOrder(new Order("gradeAchivement.std.code"));
      populateConditions(request, query);
      query.setLimit(new PageLimit(1, 10));
      int i = 0;
      QueryPage page = new QueryPage(query, utilService);
      int total = page.getTotal();
      System.out.println("stat  grade achivement:" + total);
      List<GradeAchivement> buf = new ArrayList<GradeAchivement>();
      for (Object obj : page) {
        GradeAchivement one = (GradeAchivement) obj;
        gradeAchivementService.stat(Collections.singletonList((GradeAchivement) obj), setting);
        i += 1;
        buf.add(one);
        if (i % 10 == 0) {
          utilService.saveOrUpdate(buf);
          buf.clear();
          System.out.println("stat grade achivement num:" + i);
        }
      }
      return redirect(request, "search", "info.stat.success");
    } else {
      List<GradeAchivement> achivements = (List<GradeAchivement>) utilService.load(GradeAchivement.class,
          "id", SeqStringUtil.transformToLong(gradeAchivementIds));

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

  protected EntityQuery buildQuery(HttpServletRequest request) {
    return buildAchivementQuery(request);
  }

  public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String gradeAchivementIds = get(request, "gradeAchivementIds");
    List<GradeAchivement> achivements = (List<GradeAchivement>) utilService.load(GradeAchivement.class, "id",
        SeqStringUtil.transformToLong(gradeAchivementIds));
    utilService.remove(achivements);
    return redirect(request, "search", "info.remove.success");
  }

  /**
   * 生成名单
   */
  public ActionForward generate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long settingId = getLong(request, "setting.id");
    GradeAchivementSetting setting = (GradeAchivementSetting) utilService.get(GradeAchivementSetting.class,
        settingId);
    gradeAchivementService.generate(setting);
    return redirect(request, "search", "info.save.success", "&setting.id=" + getLong(request, "setting.id"));
  }

  /**
   * 班级列表
   */
  @SuppressWarnings("unchecked")
  public ActionForward classList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long settingId = getLong(request, "setting.id");
    GradeAchivementSetting setting = (GradeAchivementSetting) utilService.get(GradeAchivementSetting.class,
        settingId);
    EntityQuery query = new EntityQuery(GradeAchivement.class, "ga");
    query.add(new Condition(
        "ga.adminClass is not null and ga.fromSemester = :fromSemester and ga.toSemester = :toSemester",
        setting.getFromSemester(), setting.getToSemester()));
    query.add(new Condition("ga.grade in(:grades)", StringUtils.split(setting.getGrades(), ",")));
    query.groupBy("ga.adminClass.id,ga.confirmed");
    query.setSelect(
        "new com.shufe.service.course.achivement.AdminClassIdStat(ga.adminClass.id,ga.confirmed,count(*))");
    List<AdminClassIdStat> rs = (List<AdminClassIdStat>) utilService.search(query);
    List<AdminClassStat> classStats = new ArrayList<AdminClassStat>();
    for (AdminClassIdStat idstat : rs) {
      AdminClassStat classStat = new AdminClassStat(
          (AdminClass) utilService.get(AdminClass.class, idstat.getAdminClassId()), idstat.getConfirmed(),
          idstat.getCount());
      classStats.add(classStat);
    }
    Collections.sort(classStats, new PropertyComparator("adminClass.code"));
    this.addSingleParameter(request, "classStats", classStats);
    return forward(request);
  }

  /**
   * 确认或取消确认
   */
  public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long settingId = getLong(request, "setting.id");
    boolean confirmed = getBoolean(request, "confirmed");
    String adminClassIds = get(request, "adminClassIds");
    GradeAchivementSetting setting = (GradeAchivementSetting) utilService.get(GradeAchivementSetting.class,
        settingId);
    EntityQuery query = new EntityQuery(GradeAchivement.class, "ga");
    query.add(new Condition("ga.fromSemester = :fromSemester and ga.toSemester = :toSemester",
        setting.getFromSemester(), setting.getToSemester()));
    query.add(new Condition("ga.adminClass.id in(:adminIds)", SeqStringUtil.transformToLong(adminClassIds)));
    @SuppressWarnings("unchecked")
    List<GradeAchivement> achivements = (List<GradeAchivement>) utilService.search(query);
    for (GradeAchivement ga : achivements) {
      ga.setConfirmed(confirmed);
    }
    utilService.saveOrUpdate(achivements);
    return redirect(request, "classList", "info.save.success",
        "&setting.id=" + getLong(request, "setting.id"));
  }
}
