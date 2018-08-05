package com.shufe.service.course.achivement;

import java.util.List;

import com.shufe.model.course.achivement.GradeAchivement;
import com.shufe.model.course.achivement.GradeAchivementSetting;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 综合测评服务
 * 
 * @author chaostone
 */
public interface GradeAchivementService {

  /**
   * 统计各类指标
   * 
   * @param achivements
   * @param calendars
   */
  void stat(List<GradeAchivement> achivements, GradeAchivementSetting setting);

  List<TeachCalendar> getCalendars(GradeAchivementSetting setting);

  /**
   * 生成指定批次的学生测评数据
   * 
   * @param calendar
   */
  void generate(GradeAchivementSetting setting);

  List<GradeAchivementSetting> getSettings(TeachCalendar calendar);
}
