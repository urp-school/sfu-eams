package com.shufe.service.course.achivement;

import com.shufe.model.system.baseinfo.AdminClass;

public class AdminClassStat {

  private final AdminClass adminClass;
  private final Boolean confirmed;
  private final int count;

  public AdminClassStat(AdminClass adminClass, Boolean confirmed, int count) {
    super();
    this.adminClass = adminClass;
    this.confirmed = confirmed;
    this.count = count;
  }

  public AdminClass getAdminClass() {
    return adminClass;
  }

  public Boolean getConfirmed() {
    return confirmed;
  }

  public int getCount() {
    return count;
  }

}
