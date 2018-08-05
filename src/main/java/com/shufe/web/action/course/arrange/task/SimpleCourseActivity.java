/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone            2006-05-21          Created
 * zq                   2007-09-18          在saveActivities()方法中，替换掉两个info，
 *                                          添加了两个logHelp.info(...)方法；
 ********************************************************************************/

package com.shufe.web.action.course.arrange.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.time.CourseUnit;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.TimeUnitUtil;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.calendar.TimeSetting;

public class SimpleCourseActivity extends LongIdObject {

  private static final Map<String, Integer> weekMap = new HashMap<String, Integer>();
  static {
    weekMap.put("一", 1);
    weekMap.put("二", 2);
    weekMap.put("三", 3);
    weekMap.put("四", 4);
    weekMap.put("五", 5);
    weekMap.put("六", 6);
    weekMap.put("日", 6);
  }
  private String no;

  private String weeks;

  private String times;

  private String rooms;

  public String getNo() {
    return no;
  }

  public void setNo(String no) {
    this.no = no;
  }

  public String getWeeks() {
    return weeks;
  }

  public void setWeeks(String weeks) {
    this.weeks = weeks;
  }

  public List<CourseActivity> getActivities(TeachTask task) {
    if (StringUtils.isEmpty(times)) return Collections.emptyList();
    else {
      String newTime = StringUtils.replace(times, "　", " ");
      String[] timeArray = StringUtils.split(newTime, " ");
      List<CourseActivity> rs = new ArrayList<CourseActivity>();
      int startWeek = task.getArrangeInfo().getWeekStart();
      int endWeek = task.getArrangeInfo().getEndWeek();

      if (null != weeks) {
        if (weeks.contains("-")) {
          startWeek = NumberUtils.toInt(StringUtils.substringBefore(weeks, "-"));
          endWeek = NumberUtils.toInt(StringUtils.substringAfter(weeks, "-"));
        } else {
          endWeek = NumberUtils.toInt(weeks);
          startWeek=endWeek;
        }
      }

      String[] roomNames = new String[0];
      if (StringUtils.isNotBlank(rooms)) {
        String newRoom = StringUtils.replace(rooms, "　", " ");
        roomNames = StringUtils.split(newRoom, " ");
      }
      int i = 0;
      for (String time : timeArray) {
        String day = time.substring(0, 1);
        Integer weekId = weekMap.get(day);
        if (null == weekId) return rs;
        String startUnit = StringUtils.substringBefore(time.substring(1), "-");
        String endUnit = StringUtils.substringAfter(time.substring(1), "-");
        int cycle = 1;
        if (endUnit.endsWith("单")) {
          cycle = 2;
          endUnit = endUnit.substring(0, endUnit.length() - 1).trim();
        } else if (endUnit.endsWith("双")) {
          cycle = 3;
          endUnit = endUnit.substring(0, endUnit.length() - 1).trim();
        }

        TeachCalendar calendar = task.getCalendar();
        TimeUnit[] units = TimeUnitUtil.buildTimeUnits(calendar.getStartYear(), calendar.getWeekStart(),
            startWeek, endWeek, cycle);
        for (TimeUnit unit : units) {
          unit.setStartUnit(NumberUtils.toInt(startUnit));
          unit.setEndUnit(NumberUtils.toInt(endUnit));
          if (unit.getStartUnit() < 0 || unit.getEndUnit() < 0 || unit.getStartUnit() > unit.getEndUnit()) return rs;
          unit.setWeekId(weekId);
          TimeSetting timeSetting = calendar.getTimeSetting();
          unit.setStartTime(timeSetting.getCourseUnit(unit.getStartUnit().intValue()).getStartTime());
          CourseUnit cu = timeSetting.getCourseUnit(unit.getEndUnit().intValue());
          if (null != cu) unit.setEndTime(cu.getFinishTime());
          else return Collections.emptyList();
          // unit.setTimeByTimeSetting(calendar.getTimeSetting());
          CourseActivity one = new CourseActivity();
          one.setCalendar(calendar);
          one.setTask(task);
          one.setTime(unit);
          Classroom room = null;
          if (roomNames.length > 0) {
            room = new Classroom();
            if (roomNames.length > i) {
              room.setName(roomNames[i]);
            } else {
              room.setName(roomNames[0]);
            }
          }
          one.setRoom(room);
          rs.add(one);
        }
        i += 1;
      }
      return rs;
    }
  }

  public String getTimes() {
    return times;
  }

  public void setTimes(String times) {
    this.times = times;
  }

  public String getRooms() {
    return rooms;
  }

  public void setRooms(String rooms) {
    this.rooms = rooms;
  }

}
