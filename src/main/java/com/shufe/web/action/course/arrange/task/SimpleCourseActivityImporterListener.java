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
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilService;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

public class SimpleCourseActivityImporterListener extends ItemImporterListener {
  private UtilService utilService;

  private TeachCalendar calendar;

  // private Set<String> inlcudes = new HashSet<String>();

  private Set<TeachTask> tasks = new HashSet<TeachTask>();

  public SimpleCourseActivityImporterListener(UtilService utilService, TeachCalendar calendar) {
    super();
    this.utilService = utilService;
    this.calendar = calendar;
    if (calendar.getBeginOn().before(new Date())) { throw new RuntimeException(
        "Cannot import current semester"); }
    // String[] kcxhs= new
    // String[]{"2160","2162","2206","2207","2210","2212","2214","2215","2216","2219","2220","2221","2222","2223","2224","2358","2359","2360","2363","2364","2365","2366","2368","2370","3288","3290","3349","3352","3353","3356","3358","3360","3361","3362","3365","3366","3367","3368","3369","3370","3372","3373","3374","3378","3379","3380","3382","3384","3386","3389","3390","3393","3395","3397","3398","3399","3402","3403","3404","3405","3406","3407","3409","3410","3411","3415","3416","3417","3421","3423","3425","3426","3427","3430","3432","3434","3435","3436","3439","3440","3441","3442","3444","3446","3447","3448","3452","3453","3454","3458","3495","3532"};
    // String[] kcxhs = new String[] { "3443" };
    // inlcudes.addAll(Arrays.asList(kcxhs));
  }

  @Override
  public void endTransferItem(TransferResult tr) {
    SimpleCourseActivity ca = (SimpleCourseActivity) importer.getCurrent();
    EntityQuery query = new EntityQuery(TeachTask.class, "task");
    if (null == ca.getNo()) {
      tr.addFailure("缺少课程序号", ca.getTimes());
      return;
    }
    // if (!inlcudes.contains(ca.getNo())) return;
    query.add(new Condition("task.calendar=:calendar and task.seqNo=:seqNo", calendar, ca.getNo()));
    List<TeachTask> tasks = (List<TeachTask>) utilService.search(query);
    if (tasks.isEmpty()) {
      tr.addFailure("错误的课程序号", ca.getNo());
      return;
    }
    TeachTask task = tasks.get(0);
    // clear activity
    if (!this.tasks.contains(task)) {
      this.tasks.add(task);
      if (!task.getArrangeInfo().getActivities().isEmpty()) {
        List<CourseActivity> old = new ArrayList<CourseActivity>();
        old.addAll(task.getArrangeInfo().getActivities());
        task.getArrangeInfo().getActivities().clear();
        utilService.remove(old);
      }
    }

    Teacher teacher = null;
    if (!task.getArrangeInfo().getTeachers().isEmpty()) {
      teacher = (Teacher) task.getArrangeInfo().getTeachers().get(0);
    }
    List<CourseActivity> activityList = ca.getActivities(task);
    for (CourseActivity one : activityList) {
      if (null != one.getRoom() && null != one.getRoom().getName()) {
        EntityQuery roomQuery = new EntityQuery(Classroom.class, "room");
        roomQuery.add(new Condition("room.name=:name or room.code=:code", one.getRoom().getName(), one
            .getRoom().getName()));
        List<Classroom> rooms = (List<Classroom>) utilService.search(roomQuery);
        if (rooms.size() != 1) {
          tr.addFailure("错误的教室名称", one.getRoom().getName());
          one.setRoom(null);
          return;
        } else {
          one.setRoom(rooms.get(0));
        }
      }
      one.setTeacher(teacher);
    }
    if (!activityList.isEmpty()) {
      task.addActivities(activityList);
      task.getArrangeInfo().setIsArrangeComplete(Boolean.TRUE);
      utilService.saveOrUpdate(task);
    } else {
      tr.addFailure("时间格式有错误", ca.getTimes());
    }
  }

  @Override
  public void endTransfer(TransferResult tr) {
    tasks.clear();
    super.endTransfer(tr);
  }

}
