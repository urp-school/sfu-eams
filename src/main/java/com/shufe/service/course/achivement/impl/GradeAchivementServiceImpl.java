package com.shufe.service.course.achivement.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.course.achivement.GradeAchivement;
import com.shufe.model.course.achivement.GradeAchivementSetting;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.achivement.Cet4GradeProvider;
import com.shufe.service.course.achivement.GaCalculator;
import com.shufe.service.course.achivement.GpaProvider;
import com.shufe.service.course.achivement.GradeAchivementService;
import com.shufe.service.course.achivement.GradeResult;
import com.shufe.service.course.achivement.IeGradeProvider;
import com.shufe.service.course.achivement.MoralGradeProvider;
import com.shufe.service.course.achivement.PeGradeProvider;

/**
 * 综合测评统计服务实现者
 * 
 * @author chaostone
 */
public class GradeAchivementServiceImpl extends BasicService implements GradeAchivementService {

  private IeGradeProvider ieGradeProvider;
  private MoralGradeProvider moralGradeProvider;
  private PeGradeProvider peGradeProvider;
  private GaCalculator gaCalculator;
  private Cet4GradeProvider cet4GradeProvider;
  private GpaProvider gpaProvider;

  @Override
  public void stat(List<GradeAchivement> achivements, GradeAchivementSetting setting) {
    List<TeachCalendar> calendars = getCalendars(setting);

    for (GradeAchivement a : achivements) {
      if (a.isConfirmed()) continue;
      StringBuilder sb = new StringBuilder();
      GradeResult result = ieGradeProvider.getGrade(a.getStd(), calendars);
      int unpassed=0;
      a.setIeScore(result.score);
      a.setIePassed(result.passed);
      unpassed += result.unpassedCourse;
      if (null != result.content) sb.append(result.content);

      result = peGradeProvider.getGrade(a.getStd(), calendars);
      a.setPeScore(result.score);
      a.setPePassed(result.passed);
      unpassed += result.unpassedCourse;
      join(sb, result.content);

      result = moralGradeProvider.getGrade(a.getStd(), calendars);
      a.setMoralScore(result.score);
      unpassed += result.unpassedCourse;
      join(sb, result.content);

      result = cet4GradeProvider.getGrade(a.getStd(), calendars);
      a.setCet4Score(result.score);
      a.setCet4Passed(result.passed);
      join(sb, result.content);

      Float score = gaCalculator.calc(a);
      a.setScore(score);
      a.setUnpassed(unpassed);
      a.setGpa(gpaProvider.getGPA(a.getStd(), calendars));
      a.setRemark(sb.toString());
      a.setUpdatedAt(new Date());
    }
    this.utilDao.saveOrUpdate(achivements);
  }

  private void join(StringBuilder sb, String content) {
    if (null == content) return;
    if (sb.length() > 0) {
      if (sb.charAt(sb.length() - 1) == '\n') sb.append(content);
      else sb.append('\n').append(content);
    } else {
      sb.append(content);
    }
  }

  @Override
  public List<GradeAchivementSetting> getSettings(TeachCalendar calendar) {
    return (List<GradeAchivementSetting>) utilService.load(GradeAchivementSetting.class, "calendar",
        new Object[] { calendar });
  }

  @Override
  public void generate(GradeAchivementSetting setting) {
    Map<String, Object> params = new HashMap<String, Object>();
    String[] grades = StringUtils.split(setting.getGrades(), ",");
    for (String grade : grades) {
      params.put("grade", grade);
      params.put("toSemesterId", setting.getToSemester().getId());
      utilDao.executeUpdateNamedQuery("insertGradeAchivement", params);
      utilDao.executeUpdateNamedQuery("insertNotInSchoolGradeAchivement", params);
    }
    params.clear();
    params.put("fromSemesterId", setting.getFromSemester().getId());
    params.put("toSemesterId", setting.getToSemester().getId());
    params.put("lastDay", setting.getToSemester().getEndOn());

    @SuppressWarnings("unchecked")
    List<Object[]> rs = (List<Object[]>) utilDao.searchNamedQuery("queryStdAlteration", params);
    List<GradeAchivement> achivements = new ArrayList<GradeAchivement>();
    for (Object[] data : rs) {
      Long id = ((Number) data[0]).longValue();
      GradeAchivement achivement = (GradeAchivement) utilDao.get(GradeAchivement.class, id);
      achivements.add(achivement);
      achivement.setGrade(data[1].toString());
      Long departId = ((Number) data[2]).longValue();
      achivement.setDepartment(new Department(departId));
      Speciality oldMajor = null;
      if (null != data[3]) {
        Long majorId = ((Number) data[3]).longValue();
        oldMajor = new Speciality(majorId);
      }
      achivement.setMajor(oldMajor);

      AdminClass adminClass = null;
      if (null != data[4]) {
        Long adminclassId = ((Number) data[4]).longValue();
        adminClass = new AdminClass(adminclassId);
      }
      achivement.setAdminClass(adminClass);
    }
    utilDao.saveOrUpdate(achivements);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TeachCalendar> getCalendars(GradeAchivementSetting setting) {
    EntityQuery query = new EntityQuery(TeachCalendar.class, "tc");
    query.add(new Condition("tc.start >= :start", setting.getFromSemester().getBeginOn()));
    query.add(new Condition("tc.finish <= :end", setting.getToSemester().getEndOn()));
    return (List<TeachCalendar>) utilService.search(query);
  }

  public void setIeGradeProvider(IeGradeProvider ieGradeProvider) {
    this.ieGradeProvider = ieGradeProvider;
  }

  public void setMoralGradeProvider(MoralGradeProvider moralGradeProvider) {
    this.moralGradeProvider = moralGradeProvider;
  }

  public void setPeGradeProvider(PeGradeProvider peGradeProvider) {
    this.peGradeProvider = peGradeProvider;
  }

  public void setGaCalculator(GaCalculator gaCalculator) {
    this.gaCalculator = gaCalculator;
  }

  public void setCet4GradeProvider(Cet4GradeProvider cet4GradeProvider) {
    this.cet4GradeProvider = cet4GradeProvider;
  }

  public void setGpaProvider(GpaProvider gpaProvider) {
    this.gpaProvider = gpaProvider;
  }

}
