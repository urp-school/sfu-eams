package com.shufe.model.course.grade;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.course.task.TeachTask;

/**
 * 期末总评加分设置
 * @author chaostone
 *
 */
public class BonusLesson extends LongIdObject {

  private TeachTask task;

  private Float ratio;

  public TeachTask getTask() {
    return task;
  }

  public void setTask(TeachTask task) {
    this.task = task;
  }

  public Float getRatio() {
    return ratio;
  }

  public void setRatio(Float ratio) {
    this.ratio = ratio;
  }

}
