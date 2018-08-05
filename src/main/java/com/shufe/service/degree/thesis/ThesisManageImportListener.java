//$Id: ThesisManageImportListener.java,v 1.1 2007-6-10 17:35:45 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-6-10         Created
 *  
 ********************************************************************************/

package com.shufe.service.degree.thesis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.degree.thesis.Thesis;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.answer.FormalAnswer;
import com.shufe.model.system.baseinfo.Teacher;

public class ThesisManageImportListener extends ItemImporterListener {

  private UtilDao utilDao;

  private List thesisManages = new ArrayList();

  /** 查询指导教师 */
  private EntityQuery teacherQuery;

  private Condition nameCondition = new Condition("teacher.name =:name and teacher.isTeaching=true");

  // 默认
  public ThesisManageImportListener() {
    this(null);
  }

  public ThesisManageImportListener(UtilDao utilDao) {
    super();
    this.utilDao = utilDao;
    this.teacherQuery = new EntityQuery(Teacher.class, "teacher");
    teacherQuery.add(nameCondition);
  }

  public void startTransferItem(TransferResult tr) {
    Object[] thesisManage = (Object[]) importer.getCurData();
    if (StringUtils.isNotBlank(String.valueOf(thesisManage[0]))) {
      EntityQuery entityQuery = new EntityQuery(ThesisManage.class, "thesisManage");
      entityQuery.add(Condition.eq("thesisManage.student.code", String.valueOf(thesisManage[0])));
      entityQuery.add(Condition.eq("thesisManage.majorType.id", Long.valueOf(thesisManage[4].toString())));
      Collection manages = utilDao.search(entityQuery);
      if (manages.size() > 0) {
        ThesisManage manage = (ThesisManage) manages.iterator().next();
        importer.setCurrent(manage);
      }
    }

  }

  /**
   * @see com.ekingstar.commons.transfer.importer.ItemImporterListener#endTransferItem(com.ekingstar.commons.transfer.TransferResult)
   */
  public void endTransferItem(TransferResult tr) {
    ThesisManage thesisManage = (ThesisManage) importer.getCurrent();
    int errors = tr.errors();
    if (!ValidEntityPredicate.INSTANCE.evaluate(thesisManage.getStudent())) {
      tr.addFailure("error.parameters.illegal", importer.curDataMap().get("student.code"));
      return;
    }
    Object specialityKind = importer.curDataMap().get("specialityKind");
    thesisManage.setMajorType(new MajorType(Long.valueOf(specialityKind.toString())));

    String teacherName = (String) importer.curDataMap().get("student.teacher.name");
    if (StringUtils.isNotEmpty(teacherName)) {
      nameCondition.setValues(Collections.singletonList(teacherName.trim()));
      List teachers = (List) utilDao.search(teacherQuery);
      if (teachers.size() == 1) {
        if (MajorType.FIRST.equals(thesisManage.getMajorType().getId())) {
          thesisManage.getStudent().setTeacher((Teacher) teachers.get(0));
        } else {
          thesisManage.getStudent().setTutor((Teacher) teachers.get(0));
        }
        utilDao.saveOrUpdate(thesisManage.getStudent());
      } else {
        tr.addFailure("导师不存在或不唯一", teacherName);
      }
    }
    // if (null == teacher) {
    // }

    if (tr.errors() == errors) {
      // 新对象
      if (!thesisManage.isPO()) {
        thesisManage.setAnnotate(null);
        thesisManage.setPreAnswerSet(null);
        thesisManage.setSchedule(null);
        thesisManage.setTeachCalendar(null);
        thesisManage.setTopicOpen(null);
        Thesis thesis = thesisManage.getThesis();
        FormalAnswer answer = thesisManage.getFormalAnswer();
        EntityUtils.evictEmptyProperty(thesis);
        EntityUtils.evictEmptyProperty(answer);
        thesis.setThesisManage(thesisManage);
        thesis.setStudent(thesisManage.getStudent());
        answer.setThesisManage(thesisManage);
        answer.setStudent(thesisManage.getStudent());
        thesisManages.add(thesisManage);
        thesisManages.add(thesis);
        thesisManages.add(answer);
      } else {
        Thesis thesis = thesisManage.getThesis();
        thesisManages.add(thesis);
        thesis.setThesisManage(thesisManage);
        thesis.setStudent(thesisManage.getStudent());
        EntityUtils.evictEmptyProperty(thesis);
      }
      if (thesisManages.size() == 500) {
        utilDao.saveOrUpdate(thesisManages);
        thesisManages.clear();
      }
    }
  }

  /**
   * @see com.ekingstar.commons.transfer.importer.ItemImporterListener#endTransfer(com.ekingstar.commons.transfer.TransferResult)
   */
  public void endTransfer(TransferResult arg0) {
    if (!thesisManages.isEmpty()) {
      utilDao.saveOrUpdate(thesisManages);
    }
    super.endTransfer(arg0);
  }

  /**
   * @param utilDao
   *          The utilDao to set.
   */
  public void setUtilDao(UtilDao utilDao) {
    this.utilDao = utilDao;
  }
}
