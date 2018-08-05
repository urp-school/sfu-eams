package com.shufe.service.course.achivement;


public class AdminClassIdStat {

  private final Long adminClassId;
  private final Boolean confirmed;
  private final int count;

  public AdminClassIdStat(Number adminClassId, Boolean confirmed, Number count) {
    super();
    this.adminClassId = adminClassId.longValue();
    this.confirmed = confirmed;
    this.count = count.intValue();
  }

  public Long getAdminClassId() {
    return adminClassId;
  }

  public Boolean getConfirmed() {
    return confirmed;
  }

  public int getCount() {
    return count;
  }

}
