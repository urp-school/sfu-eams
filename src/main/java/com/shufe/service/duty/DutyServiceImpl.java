//$Id: DutyServiceImpl.java,v 1.26 2007/01/16 05:15:02 yd Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-12-1         Created
 *  
 ********************************************************************************/

package com.shufe.service.duty;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanPredicate;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.EqualPredicate;
import org.apache.commons.collections.functors.NullPredicate;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.collection.predicates.NotZeroNumberPredicate;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.AttendanceType;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.time.CourseUnit;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.duty.DutyRecord;
import com.shufe.model.duty.DutyRecordStatisticsCourse;
import com.shufe.model.duty.DutyRecordStatisticsStatus;
import com.shufe.model.duty.DutyRecordStatisticsStd;
import com.shufe.model.duty.RecordDetail;
import com.shufe.model.std.Student;
import com.shufe.service.BasicService;

/**
 * @author dell
 */
public class DutyServiceImpl extends BasicService implements DutyService {
    
    public void batchSaveDutyRecord(Long teachTaskId, Long[] studentIdArray) {
        DutyRecord dutyRecord = new DutyRecord();
        dutyRecord.setDutyCount(new Integer(0));
        dutyRecord.setTotalCount(new Integer(0));
        TeachTask teachTask = new TeachTask();
        teachTask.setId(teachTaskId);
        dutyRecord.setTeachTask(teachTask);
        for (int i = 0; i < studentIdArray.length; i++) {
            try {
                Student student = new Student();
                Long id = studentIdArray[i];
                if (new NotZeroNumberPredicate().evaluate(id)) {
                    DutyRecord cloneObj = (DutyRecord) dutyRecord.clone();
                    student.setId(id);
                    cloneObj.setStudent(student);
                    utilService.saveOrUpdate(cloneObj);
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void batchUpdateDutyRecord(Long teachTaskId, Long[] studentIdArray, Map dutyStatusMap,
            RecordDetail recordDetail) {
        List dutyRecordList = utilService.load(DutyRecord.class, new String[] { "student.id",
                "teachTask.id" }, new Object[] { studentIdArray, teachTaskId });
        for (Iterator ite = dutyRecordList.iterator(); ite.hasNext();) {
            DutyRecord dutyRecord = (DutyRecord) ite.next();
            Long stdId = dutyRecord.getStudent().getId();
            dutyRecord.setTotalCount(dutyRecord.getTotalCount() != null ? new Integer(dutyRecord
                    .getTotalCount().intValue() + 1) : new Integer(1));
            try {
                RecordDetail cloneObj = (RecordDetail) recordDetail.clone();
                cloneObj.setDutyRecord(dutyRecord);
                Object dutyStatusObj = dutyStatusMap.get(stdId);
                if (dutyStatusObj instanceof AttendanceType) {
                    AttendanceType dutyStatus = (AttendanceType) dutyStatusObj;
                    cloneObj.setDutyStatus(dutyStatus);
                    if (dutyStatus.getId().equals(AttendanceType.presence)) {
                        this.setDutyRecordProperty(dutyRecord, "dutyCount", 1);
                    } else if (dutyStatus.getId().equals(AttendanceType.absenteeism)) {
                        this.setDutyRecordProperty(dutyRecord, "absenteeismCount", 1);
                    } else if (dutyStatus.getId().equals(AttendanceType.late)) {
                        this.setDutyRecordProperty(dutyRecord, "lateCount", 1);
                    } else if (dutyStatus.getId().equals(AttendanceType.leaveEarly)) {
                        this.setDutyRecordProperty(dutyRecord, "leaveEarlyCount", 1);
                    } else if (dutyStatus.getId().equals(AttendanceType.askedForLeave)) {
                        this.setDutyRecordProperty(dutyRecord, "askedForLeaveCount", 1);
                    }
                } else {
                    cloneObj.setDutyStatus(new AttendanceType(AttendanceType.absenteeism));
                    this.setDutyRecordProperty(dutyRecord, "absenteeismCount", 1);
                }
                List updateList = new ArrayList();
                updateList.add(cloneObj);
                updateList.add(dutyRecord);
                utilDao.saveOrUpdate(updateList);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        String hql = "UPDATE DutyRecord SET dutyRatio=DECODE(dutyCount,NULL,0,DECODE(totalCount,NULL,0,0,0,dutyCount))/DECODE(totalCount,NULL,-1,0,-1,totalCount),absenteeismRatio=DECODE(totalCount,NULL,0,0,0,DECODE(absenteeismCount,null,TRUNC((DECODE(lateCount,null,0,lateCount)+DECODE(leaveEarlyCount,null,0,leaveEarlyCount))/(:convertAbsenteeism)),(absenteeismCount+TRUNC((DECODE(lateCount,null,0,lateCount)+DECODE(leaveEarlyCount,null,0,leaveEarlyCount))/(:convertAbsenteeism)))))/DECODE(totalCount,NULL,-1,0,-1,totalCount)where student.id in (:stdId) and teachTask.id = :teachTaskId";
        Map map = new HashMap(3);
        map.put("stdId", studentIdArray);
        map.put("teachTaskId", teachTaskId);
        map.put("convertAbsenteeism", Double.valueOf(String
                .valueOf(AttendanceType.convertAbsenteeism)));
        utilDao.executeUpdateHql(hql, map);
    }
    
    public void batchInputDutyRecord(Long teachTaskId, Long[] studentIdArray, Map dutyStatusMap,
            RecordDetail recordDetail) {
        List dutyRecordList = utilService.load(DutyRecord.class, new String[] { "student.id",
                "teachTask.id" }, new Object[] { studentIdArray, teachTaskId });
        for (Iterator ite = dutyRecordList.iterator(); ite.hasNext();) {
            DutyRecord dutyRecord = (DutyRecord) ite.next();
            Long stdId = dutyRecord.getStudent().getId();
            dutyRecord.setTotalCount(dutyRecord.getTotalCount() != null ? new Integer(dutyRecord
                    .getTotalCount().intValue() + 1) : new Integer(1));
            try {
                RecordDetail cloneObj = (RecordDetail) recordDetail.clone();
                cloneObj.setDutyRecord(dutyRecord);
                Object dutyStatusObj = dutyStatusMap.get(stdId);
                if (dutyStatusObj instanceof AttendanceType) {
                    AttendanceType dutyStatus = (AttendanceType) dutyStatusObj;
                    cloneObj.setDutyStatus(dutyStatus);
                    if (dutyStatus.getId().equals(AttendanceType.presence)) {
                        dutyRecord.setDutyCount(dutyRecord.getDutyCount() != null ? new Integer(
                                dutyRecord.getDutyCount().intValue() + 1) : new Integer(1));
                    } else if (dutyStatus.getId().equals(AttendanceType.absenteeism)) {
                        dutyRecord
                                .setAbsenteeismCount(dutyRecord.getAbsenteeismCount() != null ? new Integer(
                                        dutyRecord.getAbsenteeismCount().intValue() + 1)
                                        : new Integer(1));
                    } else if (dutyStatus.getId().equals(AttendanceType.late)) {
                        dutyRecord.setLateCount(dutyRecord.getLateCount() != null ? new Integer(
                                dutyRecord.getLateCount().intValue() + 1) : new Integer(1));
                    } else if (dutyStatus.getId().equals(AttendanceType.leaveEarly)) {
                        dutyRecord
                                .setLeaveEarlyCount(dutyRecord.getLeaveEarlyCount() != null ? new Integer(
                                        dutyRecord.getLeaveEarlyCount().intValue() + 1)
                                        : new Integer(1));
                    } else if (dutyStatus.getId().equals(AttendanceType.askedForLeave)) {
                        dutyRecord
                                .setAskedForLeaveCount(dutyRecord.getAskedForLeaveCount() != null ? new Integer(
                                        dutyRecord.getAskedForLeaveCount().intValue() + 1)
                                        : new Integer(1));
                    }
                } else {
                    cloneObj.setDutyStatus(new AttendanceType(AttendanceType.absenteeism));
                    dutyRecord
                            .setAbsenteeismCount(dutyRecord.getAbsenteeismCount() != null ? new Integer(
                                    dutyRecord.getAbsenteeismCount().intValue() + 1)
                                    : new Integer(1));
                }
                utilDao.saveOrUpdate(dutyRecord);
                utilDao.saveOrUpdate(cloneObj);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        
        String hql = "UPDATE DutyRecord SET dutyRatio=DECODE(dutyCount,NULL,0,DECODE(totalCount,NULL,0,0,0,dutyCount))/DECODE(totalCount,NULL,-1,0,-1,totalCount),"
                + "absenteeismRatio=DECODE(absenteeismCount,null,0,TRUNC(DECODE(totalCount,NULL,0,0,0,absenteeismCount+(DECODE(lateCount,null,0,lateCount)+DECODE(leaveEarlyCount,null,0,leaveEarlyCount))/(:convertAbsenteeism))))/DECODE(totalCount,NULL,-1,0,-1,totalCount)"
                + "where student.id in (:stdId) and teachTask.id = :teachTaskId";
        Map map = new HashMap(3);
        
        map.put("stdId", studentIdArray);
        map.put("teachTaskId", teachTaskId);
        map.put("convertAbsenteeism", Double.valueOf(String
                .valueOf(AttendanceType.convertAbsenteeism)));
        utilDao.executeUpdateHql(hql, map);
        
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.duty.DutyService#batchUpdateDutyRecordRatio(java.lang.Long,
     *      java.lang.String[])
     */
    public void batchUpdateDutyRecordRatio(Long teachTaskId, Long[] studentIds) {
        Map params = new HashMap();
        params.put("studentIds", studentIds);
        params.put("teachTaskId", teachTaskId);
        utilDao.executeUpdateNamedQuery("batchUpdateDutyRecordRatio", params);
    }
    
    public void batchUpdateDutyRecordDetail(Long recordId, String[] recordDetailIds) {
        utilService.update(RecordDetail.class, "dutyRecord.id", new Object[] { recordId },
                new String[] { "dutyStatus" }, new Object[] { new Boolean(false) });
        utilService.update(RecordDetail.class, "id", recordDetailIds,
                new String[] { "dutyStatus" }, new Object[] { new Boolean(true) });
        Integer dutyCount = new Integer(recordDetailIds.length);
        utilService.update(DutyRecord.class, "id", new Object[] { recordId }, new String[] {
                "dutyCount", "dutyRatio" }, new Object[] { dutyCount, dutyCount + "/totalCount" });
    }
    
    public List loadTeachCalendarTerm() {
        return utilDao.searchNamedQuery("listTeachCalendarTerm", (Map) null);
    }
    
    public List loadDutyRecordDetailsByTeachTaskId(Long teachTaskId) {
        String hql = "SELECT new map(recordDetail.dutyDate as dutyDate,recordDetail.beginUnit as beginUnit,recordDetail.endUnit as endUnit)"
                + " FROM RecordDetail as recordDetail "
                /*
                 * + "join recordDetail.dutyRecord as dutyRecord " + "join dutyRecord.teachTask as
                 * teachTask " + "WHERE teachTask.id=(:teachTaskId)"
                 */
                + " WHERE recordDetail.dutyRecord.teachTask.id=(:teachTaskId)"
                + " GROUP BY (recordDetail.dutyDate,recordDetail.beginUnit,recordDetail.endUnit)"
                + " order by recordDetail.dutyDate asc, recordDetail.beginUnit asc, recordDetail.endUnit asc";
        Map params = new HashMap();
        params.put("teachTaskId", teachTaskId);
        return utilDao.searchHQLQuery(hql, params);
    }
    
    public List loadStudentDutyRecordDetails(Long teachTaskId, String dutyDate, Long beginUnitId,
            Long endUnitId) {
        String hql = "SELECT recordDetail" + " FROM RecordDetail as recordDetail "
                + "join recordDetail.dutyRecord as dutyRecord "
                + "join dutyRecord.teachTask as teachTask " + "WHERE teachTask.id=(:teachTaskId)"
                + "and recordDetail.dutyDate=TO_DATE((:dutyDate),'YYYY-MM-DD')"
                + "and recordDetail.beginUnit.id=(:beginUnitId)"
                + "and recordDetail.endUnit.id=(:endUnitId)";
        Map params = new HashMap();
        params.put("teachTaskId", teachTaskId);
        params.put("dutyDate", dutyDate);
        params.put("beginUnitId", beginUnitId);
        params.put("endUnitId", endUnitId);
        List studentDutyRecordDetailsList = utilDao.searchHQLQuery(hql, params);
        return studentDutyRecordDetailsList;
    }
    
    public boolean checkDutyRecordsIfExists(String stdIds, Long teachTaskId, String dutyDate,
            String beginUnitIdString, String endUnitIdString) {
        Long[] stdIdArray = SeqStringUtil.transformToLong(stdIds);
        if (ArrayUtils.isEmpty(stdIdArray)
                || !NotZeroNumberPredicate.getInstance().evaluate(teachTaskId)
                || StringUtils.isEmpty(dutyDate) || StringUtils.isEmpty(beginUnitIdString)
                || StringUtils.isEmpty(endUnitIdString)) {
            return false;
        }
        EntityQuery entityQuery = new EntityQuery(RecordDetail.class, "recordDetail");
        entityQuery
                .add(new Condition("recordDetail.dutyRecord.student.id in (:stdIds)", stdIdArray));
        entityQuery.add(new Condition("recordDetail.dutyRecord.teachTask.id = :teachTaskId",
                teachTaskId));
        entityQuery.add(new Condition("recordDetail.dutyDate = :dutyDate", Date.valueOf(dutyDate)));
        entityQuery.add(new Condition("recordDetail.beginUnit.id = :beginUnitId", new Long(
                beginUnitIdString)));
        entityQuery.add(new Condition("recordDetail.endUnit.id = :endUnitId", new Long(
                endUnitIdString)));
        entityQuery.setSelect("select count(*) ");
        if (((Number) (utilService.search(entityQuery).iterator().next())).intValue() > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public Long getDutyRecordIdIfExists(String stdIds, Long teachTaskId, String dutyDate,
            String timeBegin, String timeEnd) {
        Long[] stdIdArray = SeqStringUtil.transformToLong(stdIds);
        for (int i = 0; i < stdIdArray.length; i++) {
            if (utilService.exist(RecordDetail.class, new String[] { "dutyRecord.student.id",
                    "dutyRecord.teachTask.id", "dutyDate", "timeBegin", "timeEnd" }, new Object[] {
                    stdIdArray[i], teachTaskId, Date.valueOf(dutyDate), timeBegin, timeEnd })) {
                Long recordId = ((RecordDetail) utilService.load(
                        RecordDetail.class,
                        new String[] { "dutyRecord.student.id", "dutyRecord.teachTask.id",
                                "dutyDate", "timeBegin", "timeEnd" },
                        new Object[] { stdIdArray[i], teachTaskId, Date.valueOf(dutyDate),
                                timeBegin, timeEnd }).get(0)).getDutyRecord().getId();
                return recordId;
            }
        }
        
        return null;
    }
    
    public void batchUpdateDutyRecordDetail(Map dutyStatusMap) {
        Set DetailKeySet = dutyStatusMap.keySet();
        List studentDutyRecordDetailsList = utilService
                .load(RecordDetail.class, "id", DetailKeySet);
        
        for (Iterator iter = studentDutyRecordDetailsList.iterator(); iter.hasNext();) {
            RecordDetail recordDetail = (RecordDetail) iter.next();
            DutyRecord dutyRecord = recordDetail.getDutyRecord();
            Long recordDetailId = recordDetail.getId();
            AttendanceType dutyStatus = recordDetail.getDutyStatus();
            if (NullPredicate.getInstance().evaluate(dutyStatusMap.get(recordDetailId))
                    || dutyStatus.getId().equals(dutyStatusMap.get(recordDetailId))) {
                
            } else {
                if (dutyStatus.getId().equals(AttendanceType.presence)) {
                    dutyRecord.setDutyCount(new Integer(dutyRecord.getDutyCount().intValue() - 1));
                } else if (dutyStatus.getId().equals(AttendanceType.absenteeism)) {
                    dutyRecord.setAbsenteeismCount(new Integer(dutyRecord.getAbsenteeismCount()
                            .intValue() - 1));
                } else if (dutyStatus.getId().equals(AttendanceType.late)) {
                    dutyRecord.setLateCount(new Integer(dutyRecord.getLateCount().intValue() - 1));
                } else if (dutyStatus.getId().equals(AttendanceType.leaveEarly)) {
                    dutyRecord.setLeaveEarlyCount(new Integer(dutyRecord.getLeaveEarlyCount()
                            .intValue() - 1));
                } else if (dutyStatus.getId().equals(AttendanceType.askedForLeave)) {
                    dutyRecord.setAskedForLeaveCount(new Integer(dutyRecord.getAskedForLeaveCount()
                            .intValue() - 1));
                }
                if (dutyStatusMap.get(recordDetailId) instanceof Long) {
                    Long dutyStatusId = (Long) dutyStatusMap.get(recordDetailId);
                    if (dutyStatusId.equals(AttendanceType.presence)) {
                        dutyRecord.setDutyCount(dutyRecord.getDutyCount() != null ? new Integer(
                                dutyRecord.getDutyCount().intValue() + 1) : new Integer(1));
                        recordDetail.setDutyStatus(new AttendanceType(AttendanceType.presence));
                    } else if (dutyStatusId.equals(AttendanceType.absenteeism)) {
                        dutyRecord
                                .setAbsenteeismCount(dutyRecord.getAbsenteeismCount() != null ? new Integer(
                                        dutyRecord.getAbsenteeismCount().intValue() + 1)
                                        : new Integer(1));
                        recordDetail.setDutyStatus(new AttendanceType(AttendanceType.absenteeism));
                    } else if (dutyStatusId.equals(AttendanceType.late)) {
                        dutyRecord.setLateCount(dutyRecord.getLateCount() != null ? new Integer(
                                dutyRecord.getLateCount().intValue() + 1) : new Integer(1));
                        recordDetail.setDutyStatus(new AttendanceType(AttendanceType.late));
                    } else if (dutyStatusId.equals(AttendanceType.leaveEarly)) {
                        dutyRecord
                                .setLeaveEarlyCount(dutyRecord.getLeaveEarlyCount() != null ? new Integer(
                                        dutyRecord.getLeaveEarlyCount().intValue() + 1)
                                        : new Integer(1));
                        recordDetail.setDutyStatus(new AttendanceType(AttendanceType.leaveEarly));
                    } else if (dutyStatusId.equals(AttendanceType.askedForLeave)) {
                        dutyRecord
                                .setAskedForLeaveCount(dutyRecord.getAskedForLeaveCount() != null ? new Integer(
                                        dutyRecord.getAskedForLeaveCount().intValue() + 1)
                                        : new Integer(1));
                        recordDetail
                                .setDutyStatus(new AttendanceType(AttendanceType.askedForLeave));
                    }
                }
                
                dutyRecord.setDutyRatio(new Float((dutyRecord.getDutyCount() != null ? dutyRecord
                        .getDutyCount().floatValue() : 0.0f)
                        / ((dutyRecord.getTotalCount() != null ? (dutyRecord.getTotalCount()
                                .floatValue() == 0.0f ? (-1f) : dutyRecord.getTotalCount()
                                .floatValue()) : (-1f)))));
                int convertAbsenteeismCount = ((dutyRecord.getLateCount() != null ? dutyRecord
                        .getLateCount().intValue() : 0) + (dutyRecord.getLeaveEarlyCount() != null ? dutyRecord
                        .getLeaveEarlyCount().intValue()
                        : 0))
                        / AttendanceType.convertAbsenteeism;
                dutyRecord
                        .setAbsenteeismRatio(new Float(
                                (dutyRecord.getTotalCount() == null || dutyRecord.getTotalCount()
                                        .floatValue() == 0.0f) ? 0.0f
                                        : ((dutyRecord.getAbsenteeismCount() != null ? dutyRecord
                                                .getAbsenteeismCount().floatValue() : 0.0f) + convertAbsenteeismCount)
                                                / ((dutyRecord.getTotalCount() != null ? (dutyRecord
                                                        .getTotalCount().floatValue() == 0.0f ? (-1f)
                                                        : dutyRecord.getTotalCount().floatValue())
                                                        : (-1f)))));
            }
            
            utilService.saveOrUpdate(dutyRecord);
            utilService.saveOrUpdate(recordDetail);
        }
        dutyStatusMap = null;
        
    }
    
    public Map dutyRecordStatistics(Long[] studentIdArray, List calendarList, String dateBegin,
            String dateEnd, boolean isCancelStat, Predicate predicate) {
        
        Map dutyRecordStatisticsMap = new HashMap();
        Map totalStatisticsMap = new HashMap();
        Map stdMap = new HashMap();
        Map courseMap = new HashMap();
        
        if (ArrayUtils.isEmpty(studentIdArray) || CollectionUtils.isEmpty(calendarList)) {
            
        } else {
            // 获得指定学生在指定教学日历中的考勤记录
            List dutyRecordList = this.getDutyRecordOfStd(studentIdArray, calendarList);
            
            for (int i = 0; i < studentIdArray.length; i++) {
                Student student = (Student) utilService.get(Student.class, studentIdArray[i]);
                if (predicate != null && !predicate.evaluate(student)) {
                    continue;
                }
                // 生成学生考勤统计对象
                DutyRecordStatisticsStd dutyRecordStatisticsStd = new DutyRecordStatisticsStd(
                        String.valueOf(student.getId()), student.getCode(), student.getName(),
                        student.getEngName());
                stdMap
                        .put(String.valueOf(dutyRecordStatisticsStd.getId()),
                                dutyRecordStatisticsStd);
                Map studentStatisticsMap = new HashMap();
                Map studentCourseTakeTypeMap = new HashMap();
                // 获取学生考勤记录集合
                Collection studentDutyRecordList = CollectionUtils.select(dutyRecordList,
                        new BeanPredicate("student.id", new EqualPredicate(student.getId())));
                for (Iterator iter = studentDutyRecordList.iterator(); iter.hasNext();) {
                    DutyRecord dutyRecordElement = (DutyRecord) iter.next();
                    DutyRecordStatisticsCourse dutyRecordStatisticsCourse = new DutyRecordStatisticsCourse(
                            dutyRecordElement);
                    courseMap.put(dutyRecordStatisticsCourse.getTeachTaskId(),
                            dutyRecordStatisticsCourse);
                    CourseTakeType courseTakeType = dutyRecordElement
                            .getCourseTakeType(Boolean.TRUE);
                    studentCourseTakeTypeMap.put(dutyRecordStatisticsCourse.getTeachTaskId(),
                            courseTakeType);
                    // 获取指定日期范围内的考勤详情
                    List recordCountList = dutyRecordElement.getRecordDetailList(StringUtils
                            .isEmpty(dateBegin) ? null : Date.valueOf(dateBegin), StringUtils
                            .isEmpty(dateEnd) ? null : Date.valueOf(dateEnd));
                    String dutyRecordStatisticsCourseId = dutyRecordStatisticsCourse
                            .getTeachTaskId();
                    countStatus(recordCountList, studentStatisticsMap,
                            dutyRecordStatisticsCourseId, "presence");
                    countStatus(recordCountList, studentStatisticsMap,
                            dutyRecordStatisticsCourseId, "absenteeism");
                    countStatus(recordCountList, studentStatisticsMap,
                            dutyRecordStatisticsCourseId, "late");
                    countStatus(recordCountList, studentStatisticsMap,
                            dutyRecordStatisticsCourseId, "leaveEarly");
                    countStatus(recordCountList, studentStatisticsMap,
                            dutyRecordStatisticsCourseId, "askedForLeave");
                    countStatus(recordCountList, studentStatisticsMap,
                            dutyRecordStatisticsCourseId, null);
                    
                    if (studentStatisticsMap.get(dutyRecordStatisticsCourse.getTeachTaskId()) instanceof DutyRecordStatisticsStatus) {
                        DutyRecordStatisticsStatus mapStatus = (DutyRecordStatisticsStatus) studentStatisticsMap
                                .get(dutyRecordStatisticsCourse.getTeachTaskId());
                        Object courseTotalObject = studentStatisticsMap
                                .get(DutyRecordStatisticsCourse.totalId);
                        if ((courseTakeType != null)
                                && (courseTakeType.getId().equals(CourseTakeType.RESTUDY) || courseTakeType
                                        .getId().equals(CourseTakeType.REEXAM))) {
                            // 重修学生或者免修不免试学生不计入最后合计
                        } else if ((courseTakeType != null) || (isCancelStat)) {
                            if (courseTotalObject == null) {
                                DutyRecordStatisticsStatus courseTotal = new DutyRecordStatisticsStatus();
                                try {
                                    PropertyUtils.copyProperties(courseTotal, mapStatus);
                                    courseTotal.setPresenceCount(mapStatus
                                            .getPresenceCount(Boolean.TRUE));
                                    courseTotal.setAbsenteeismCount(mapStatus
                                            .getAbsenteeismCount(Boolean.TRUE));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    throw new RuntimeException(
                                            "PropertyUtils.copyProperties( courseTotal, mapStatus );");
                                }
                                courseMap.put(DutyRecordStatisticsCourse.totalId,
                                        new DutyRecordStatisticsCourse(
                                                DutyRecordStatisticsCourse.totalId,
                                                DutyRecordStatisticsCourse.totalName,
                                                DutyRecordStatisticsCourse.totalEngName));
                                studentStatisticsMap.put(DutyRecordStatisticsCourse.totalId,
                                        courseTotal);
                            } else {
                                DutyRecordStatisticsStatus courseTotal = (DutyRecordStatisticsStatus) courseTotalObject;
                                courseTotal.setPresenceCount(new Integer((courseTotal
                                        .getPresenceCount() == null ? 0 : courseTotal
                                        .getPresenceCount().intValue())
                                        + (mapStatus.getPresenceCount(Boolean.TRUE) == null ? 0
                                                : mapStatus.getPresenceCount(Boolean.TRUE)
                                                        .intValue())));
                                courseTotal.setAbsenteeismCount(new Integer((courseTotal
                                        .getAbsenteeismCount() == null ? 0 : courseTotal
                                        .getAbsenteeismCount().intValue())
                                        + (mapStatus.getAbsenteeismCount(Boolean.TRUE) == null ? 0
                                                : mapStatus.getAbsenteeismCount(Boolean.TRUE)
                                                        .intValue())));
                                courseTotal.setLateCount(new Integer(
                                        (courseTotal.getLateCount() == null ? 0 : courseTotal
                                                .getLateCount().intValue())
                                                + (mapStatus.getLateCount() == null ? 0 : mapStatus
                                                        .getLateCount().intValue())));
                                courseTotal.setLeaveEarlyCount(new Integer((courseTotal
                                        .getLeaveEarlyCount() == null ? 0 : courseTotal
                                        .getLeaveEarlyCount().intValue())
                                        + (mapStatus.getLeaveEarlyCount() == null ? 0 : mapStatus
                                                .getLeaveEarlyCount().intValue())));
                                courseTotal.setAskedForLeaveCount(new Integer((courseTotal
                                        .getAskedForLeaveCount() == null ? 0 : courseTotal
                                        .getAskedForLeaveCount().intValue())
                                        + (mapStatus.getAskedForLeaveCount() == null ? 0
                                                : mapStatus.getAskedForLeaveCount().intValue())));
                                courseTotal.setTotalCount(new Integer(
                                        (courseTotal.getTotalCount() == null ? 0 : courseTotal
                                                .getTotalCount().intValue())
                                                + (mapStatus.getTotalCount() == null ? 0
                                                        : mapStatus.getTotalCount().intValue())));
                            }
                            
                            Object totalMapStatusObject = (Object) totalStatisticsMap
                                    .get(dutyRecordStatisticsCourse.getTeachTaskId());
                            if (totalMapStatusObject == null) {
                                DutyRecordStatisticsStatus courseTotal = new DutyRecordStatisticsStatus();
                                try {
                                    PropertyUtils.copyProperties(courseTotal, mapStatus);
                                    courseTotal.setPresenceCount(mapStatus
                                            .getPresenceCount(Boolean.TRUE));
                                    courseTotal.setAbsenteeismCount(mapStatus
                                            .getAbsenteeismCount(Boolean.TRUE));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    throw new RuntimeException(
                                            "PropertyUtils.copyProperties( courseTotal, mapStatus );");
                                }
                                totalStatisticsMap.put(dutyRecordStatisticsCourse.getTeachTaskId(),
                                        courseTotal);
                            } else {
                                DutyRecordStatisticsStatus totalMapStatus = (DutyRecordStatisticsStatus) totalMapStatusObject;
                                totalMapStatus.setPresenceCount(new Integer((totalMapStatus
                                        .getPresenceCount() == null ? 0 : totalMapStatus
                                        .getPresenceCount().intValue())
                                        + (mapStatus.getPresenceCount(Boolean.TRUE) == null ? 0
                                                : mapStatus.getPresenceCount(Boolean.TRUE)
                                                        .intValue())));
                                totalMapStatus.setAbsenteeismCount(new Integer((totalMapStatus
                                        .getAbsenteeismCount() == null ? 0 : totalMapStatus
                                        .getAbsenteeismCount().intValue())
                                        + (mapStatus.getAbsenteeismCount(Boolean.TRUE) == null ? 0
                                                : mapStatus.getAbsenteeismCount(Boolean.TRUE)
                                                        .intValue())));
                                totalMapStatus.setLateCount(new Integer((totalMapStatus
                                        .getLateCount() == null ? 0 : totalMapStatus.getLateCount()
                                        .intValue())
                                        + (mapStatus.getLateCount() == null ? 0 : mapStatus
                                                .getLateCount().intValue())));
                                totalMapStatus.setLeaveEarlyCount(new Integer((totalMapStatus
                                        .getLeaveEarlyCount() == null ? 0 : totalMapStatus
                                        .getLeaveEarlyCount().intValue())
                                        + (mapStatus.getLeaveEarlyCount() == null ? 0 : mapStatus
                                                .getLeaveEarlyCount().intValue())));
                                totalMapStatus.setAskedForLeaveCount(new Integer((totalMapStatus
                                        .getAskedForLeaveCount() == null ? 0 : totalMapStatus
                                        .getAskedForLeaveCount().intValue())
                                        + (mapStatus.getAskedForLeaveCount() == null ? 0
                                                : mapStatus.getAskedForLeaveCount().intValue())));
                                totalMapStatus.setTotalCount(new Integer((totalMapStatus
                                        .getTotalCount() == null ? 0 : totalMapStatus
                                        .getTotalCount().intValue())
                                        + (mapStatus.getTotalCount() == null ? 0 : mapStatus
                                                .getTotalCount().intValue())));
                            }
                        }
                        
                    }
                }
                
                studentStatisticsMap.put(DutyRecordStatisticsCourse.courseTakeType,
                        studentCourseTakeTypeMap);
                
                dutyRecordStatisticsMap.put(dutyRecordStatisticsStd.getId(), studentStatisticsMap);
                
                if (studentStatisticsMap.get(DutyRecordStatisticsCourse.totalId) instanceof DutyRecordStatisticsStatus) {
                    DutyRecordStatisticsStatus mapStatus = (DutyRecordStatisticsStatus) studentStatisticsMap
                            .get(DutyRecordStatisticsCourse.totalId);
                    Object totalObject = totalStatisticsMap.get(DutyRecordStatisticsCourse.totalId);
                    if (totalObject == null) {
                        DutyRecordStatisticsStatus stdTotal = new DutyRecordStatisticsStatus();
                        try {
                            PropertyUtils.copyProperties(stdTotal, mapStatus);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException(
                                    "PropertyUtils.copyProperties( stdTotal, mapStatus );");
                        }
                        stdMap.put(DutyRecordStatisticsCourse.totalId,
                                new DutyRecordStatisticsCourse(DutyRecordStatisticsCourse.totalId,
                                        DutyRecordStatisticsCourse.totalName,
                                        DutyRecordStatisticsCourse.totalEngName));
                        totalStatisticsMap.put(DutyRecordStatisticsCourse.totalId, stdTotal);
                    } else {
                        DutyRecordStatisticsStatus stdTotal = (DutyRecordStatisticsStatus) totalObject;
                        stdTotal.setPresenceCount(new Integer(
                                (stdTotal.getPresenceCount() == null ? 0 : stdTotal
                                        .getPresenceCount().intValue())
                                        + (mapStatus.getPresenceCount() == null ? 0 : mapStatus
                                                .getPresenceCount().intValue())));
                        stdTotal.setAbsenteeismCount(new Integer(
                                (stdTotal.getAbsenteeismCount() == null ? 0 : stdTotal
                                        .getAbsenteeismCount().intValue())
                                        + (mapStatus.getAbsenteeismCount() == null ? 0 : mapStatus
                                                .getAbsenteeismCount().intValue())));
                        stdTotal.setLateCount(new Integer((stdTotal.getLateCount() == null ? 0
                                : stdTotal.getLateCount().intValue())
                                + (mapStatus.getLateCount() == null ? 0 : mapStatus.getLateCount()
                                        .intValue())));
                        stdTotal.setLeaveEarlyCount(new Integer(
                                (stdTotal.getLeaveEarlyCount() == null ? 0 : stdTotal
                                        .getLeaveEarlyCount().intValue())
                                        + (mapStatus.getLeaveEarlyCount() == null ? 0 : mapStatus
                                                .getLeaveEarlyCount().intValue())));
                        stdTotal.setAskedForLeaveCount(new Integer((stdTotal
                                .getAskedForLeaveCount() == null ? 0 : stdTotal
                                .getAskedForLeaveCount().intValue())
                                + (mapStatus.getAskedForLeaveCount() == null ? 0 : mapStatus
                                        .getAskedForLeaveCount().intValue())));
                        stdTotal.setTotalCount(new Integer((stdTotal.getTotalCount() == null ? 0
                                : stdTotal.getTotalCount().intValue())
                                + (mapStatus.getTotalCount() == null ? 0 : mapStatus
                                        .getTotalCount().intValue())));
                    }
                }
                
            }
        }
        
        stdMap.put(DutyRecordStatisticsStd.totalNo, new DutyRecordStatisticsStd(
                DutyRecordStatisticsStd.totalNo, DutyRecordStatisticsStd.totalNo,
                DutyRecordStatisticsStd.totalName, DutyRecordStatisticsStd.totalEngName));
        dutyRecordStatisticsMap.put(DutyRecordStatisticsStd.totalNo, totalStatisticsMap);
        
        Map resultMap = new HashMap();
        resultMap.put("dutyRecordStatisticsMap", dutyRecordStatisticsMap);
        resultMap.put("stdMap", stdMap);
        resultMap.put("courseMap", courseMap);
        
        return resultMap;
    }
    
    /**
     * 考勤状态计数
     * 
     * @param recordCountList 考勤详情列表
     * @param studentStatisticsMap 考勤统计<code>Map</code>,key:dutyRecordStatisticsCourseId;value:<code>DutyRecordStatisticsStatus</code>
     * @param dutyRecordStatisticsCourseId
     * @param status
     */
    private void countStatus(List recordCountList, Map studentStatisticsMap,
            String dutyRecordStatisticsCourseId, String status) {
        
        if (StringUtils.isEmpty(status)) {
            Object statusCount = studentStatisticsMap.get(dutyRecordStatisticsCourseId);
            if (statusCount == null) {
                DutyRecordStatisticsStatus dutyRecordStatisticsStatus = new DutyRecordStatisticsStatus();
                dutyRecordStatisticsStatus.setTotalCount(new Integer(recordCountList.size()));
                studentStatisticsMap.put(dutyRecordStatisticsCourseId, dutyRecordStatisticsStatus);
            } else {
                DutyRecordStatisticsStatus dutyRecordStatisticsStatus = (DutyRecordStatisticsStatus) statusCount;
                dutyRecordStatisticsStatus.setTotalCount(new Integer(((dutyRecordStatisticsStatus
                        .getTotalCount() == null) ? 0 : dutyRecordStatisticsStatus.getTotalCount()
                        .intValue())
                        + recordCountList.size()));
            }
            return;
        }
        int count = CollectionUtils.countMatches(recordCountList, new StatusPredicate(status));
        
        Object statusCount = studentStatisticsMap.get(dutyRecordStatisticsCourseId);
        if (statusCount == null) {
            DutyRecordStatisticsStatus dutyRecordStatisticsStatus = new DutyRecordStatisticsStatus();
            try {
                PropertyUtils.setProperty(dutyRecordStatisticsStatus, status + "Count",
                        new Integer(count));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("dutyRecordStatisticsStatus NoSuchMethodException");
            }
            /*
             * if(status.equals(AttendanceType.late)||status.equals(AttendanceType.leaveEarly)){
             * dutyRecordStatisticsStatus.doTotalConvert((Integer)s[1]); }
             * dutyRecordStatisticsStatus.setTotalCount(dutyRecordStatisticsStatus.getTotalCount()==null?(Integer)s[1]:new
             * Integer(dutyRecordStatisticsStatus.getTotalCount().intValue()+((Integer)s[1]).intValue()));
             */
            studentStatisticsMap.put(dutyRecordStatisticsCourseId, dutyRecordStatisticsStatus);
        } else {
            DutyRecordStatisticsStatus dutyRecordStatisticsStatus = (DutyRecordStatisticsStatus) statusCount;
            Object oldCount;
            try {
                oldCount = PropertyUtils.getProperty(dutyRecordStatisticsStatus, status + "Count");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("dutyRecordStatisticsStatus InvocationTargetException");
            }
            try {
                PropertyUtils.setProperty(dutyRecordStatisticsStatus, status + "Count",
                        new Integer(((oldCount == null) ? 0 : ((Integer) oldCount).intValue())
                                + count));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("dutyRecordStatisticsStatus NoSuchMethodException");
            }
            studentStatisticsMap.put(dutyRecordStatisticsCourseId, dutyRecordStatisticsStatus);
        }
    }
    
    public List getDutyRecordOfStd(Long[] studentIdArray, List calendarList) {
        if (calendarList == null) {
            return null;
        } else if (calendarList.isEmpty())
            return utilService.load(DutyRecord.class, "student.id", studentIdArray);
        else {
            String hql = "select record from DutyRecord as record "
                    + " where (record.student.id in (:stdId))  and record.teachTask.calendar in (:calendarList) ";
            Map params = new HashMap();
            params.put("stdId", studentIdArray);
            params.put("calendarList", calendarList);
            return utilDao.searchHQLQuery(hql, params);
        }
        
    }
    
    public Map getCourseUnitStatusMap(Long teachTaskId, java.util.Date beginDate,
            java.util.Date endDate) {
        Map stdCourseUnitStatusMap = new HashMap();
        String hqlString = "select recordDetail from RecordDetail "
                + "as recordDetail where recordDetail.dutyRecord.teachTask.id=:teachTaskId";
        Map parameterMap = new HashMap();
        parameterMap.put("teachTaskId", teachTaskId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (beginDate instanceof java.util.Date) {
            hqlString = hqlString + " and recordDetail.dutyDate>=:beginDate ";
            parameterMap.put("beginDate", beginDate);
        }
        if (endDate instanceof java.util.Date) {
            hqlString = hqlString + " and recordDetail.dutyDate<=:endDate ";
            parameterMap.put("endDate", endDate);
        }
        List recordDetailList = utilDao.searchHQLQuery(hqlString, parameterMap);
        parameterMap = null;
        for (Iterator iter = recordDetailList.iterator(); iter.hasNext();) {
            RecordDetail recordDetailElement = (RecordDetail) iter.next();
            String dutyDate = simpleDateFormat.format(recordDetailElement.getDutyDate());
            Object stdCourseUnitStatusMapObject = stdCourseUnitStatusMap.get(String
                    .valueOf(recordDetailElement.getDutyRecord().getStudent().getId()));
            if (stdCourseUnitStatusMapObject instanceof Map) {
                Map courseUnitStatusMap = (Map) stdCourseUnitStatusMapObject;
                Object courseUnitStatusMapObject = courseUnitStatusMap.get(dutyDate);
                if (courseUnitStatusMapObject instanceof Map) {
                    ((Map) courseUnitStatusMapObject).put(String.valueOf(recordDetailElement
                            .getBeginUnit().getId()
                            + "-" + recordDetailElement.getEndUnit().getId()), recordDetailElement
                            .getDutyStatus());
                } else {
                    Map unitTempMap = new HashMap();
                    unitTempMap.put(String.valueOf(recordDetailElement.getBeginUnit().getId() + "-"
                            + recordDetailElement.getEndUnit().getId()), recordDetailElement
                            .getDutyStatus());
                    courseUnitStatusMap.put(dutyDate, unitTempMap);
                }
            } else {
                Map unitTempMap = new HashMap();
                unitTempMap.put(String.valueOf(recordDetailElement.getBeginUnit().getId() + "-"
                        + recordDetailElement.getEndUnit().getId()), recordDetailElement
                        .getDutyStatus());
                Map courseUnitStatusMap = new HashMap();
                courseUnitStatusMap.put(dutyDate, unitTempMap);
                stdCourseUnitStatusMap.put(String.valueOf(recordDetailElement.getDutyRecord()
                        .getStudent().getId()), courseUnitStatusMap);
            }
        }
        return stdCourseUnitStatusMap;
    }
    
    public void saveRecordDetail(Long studentId, Long teachTaskId, Date day, Long beginUnitId,
            Long endUnitId, Long dutyStatusId) {
        
    }
    
    public void updateOrSaveRecordDetail(Long studentId, Long teachTaskId, Date day,
            Long beginUnitId, Long endUnitId, Long dutyStatusId) {
        List recordDetailList = utilService.load(RecordDetail.class, new String[] {
                "dutyRecord.student.id", "dutyRecord.teachTask.id", "dutyDate", "beginUnit.id",
                "endUnit.id" },
                new Object[] { studentId, teachTaskId, day, beginUnitId, endUnitId });
        if (!recordDetailList.isEmpty()) {
            for (Iterator iter = recordDetailList.iterator(); iter.hasNext();) {
                RecordDetail recordDetail = (RecordDetail) iter.next();
                updateRecordDetail(recordDetail, dutyStatusId);
            }
        } else {
            List dutyRecordList = utilService.load(DutyRecord.class, new String[] { "student.id",
                    "teachTask.id" }, new Object[] { studentId, teachTaskId });
            if (!dutyRecordList.isEmpty()) {
                if (dutyRecordList.size() > 1) {
                    throw new RuntimeException("DutyRecord more than one Exception \n");
                } else {
                    CourseUnit beginUnit = (CourseUnit) utilService.load(CourseUnit.class,
                            beginUnitId);
                    CourseUnit endUnit = (CourseUnit) utilService.load(CourseUnit.class, endUnitId);
                    DutyRecord dutyRecord = (DutyRecord) dutyRecordList.get(0);
                    RecordDetail recordDetail = new RecordDetail();
                    recordDetail.setDutyRecord(dutyRecord);
                    recordDetail.setBeginUnit(beginUnit);
                    recordDetail.setBeginUnitIndex(beginUnit.getIndex());
                    recordDetail.setEndUnit(endUnit);
                    recordDetail.setEndUnitIndex(endUnit.getIndex());
                    recordDetail.setDutyDate(day);
                    recordDetail.setBeginTime(beginUnit.getStartTime());
                    recordDetail.setEndTime(endUnit.getFinishTime());
                    recordDetail.setDutyStatus(new AttendanceType(dutyStatusId));
                    if (dutyStatusId != null) {
                        if (dutyStatusId.equals(AttendanceType.presence))
                            this.setDutyRecordProperty(dutyRecord, "dutyCount", 1);
                        else if (dutyStatusId.equals(AttendanceType.absenteeism))
                            this.setDutyRecordProperty(dutyRecord, "absenteeismCount", 1);
                        else if (dutyStatusId.equals(AttendanceType.askedForLeave))
                            this.setDutyRecordProperty(dutyRecord, "askedForLeaveCount", 1);
                        else if (dutyStatusId.equals(AttendanceType.late))
                            this.setDutyRecordProperty(dutyRecord, "lateCount", 1);
                        else if (dutyStatusId.equals(AttendanceType.leaveEarly))
                            this.setDutyRecordProperty(dutyRecord, "leaveEarlyCount", 1);
                    }
                    utilService.saveOrUpdate(dutyRecord);
                    utilService.saveOrUpdate(recordDetail);
                }
            } else {
                DutyRecord dutyRecord = new DutyRecord();
                dutyRecord.setStudent(new Student(studentId));
                dutyRecord.setTeachTask(new TeachTask(teachTaskId));
                CourseUnit beginUnit = (CourseUnit) utilService.load(CourseUnit.class, beginUnitId);
                CourseUnit endUnit = (CourseUnit) utilService.load(CourseUnit.class, endUnitId);
                RecordDetail recordDetail = new RecordDetail();
                recordDetail.setDutyRecord(dutyRecord);
                recordDetail.setBeginUnit(beginUnit);
                recordDetail.setBeginUnitIndex(beginUnit.getIndex());
                recordDetail.setEndUnit(endUnit);
                recordDetail.setEndUnitIndex(endUnit.getIndex());
                recordDetail.setDutyDate(day);
                recordDetail.setBeginTime(beginUnit.getStartTime());
                recordDetail.setEndTime(endUnit.getFinishTime());
                recordDetail.setDutyStatus(new AttendanceType(dutyStatusId));
                if (dutyStatusId != null) {
                    if (dutyStatusId.equals(AttendanceType.presence))
                        this.setDutyRecordProperty(dutyRecord, "dutyCount", 1);
                    else if (dutyStatusId.equals(AttendanceType.absenteeism))
                        this.setDutyRecordProperty(dutyRecord, "absenteeismCount", 1);
                    else if (dutyStatusId.equals(AttendanceType.askedForLeave))
                        this.setDutyRecordProperty(dutyRecord, "askedForLeaveCount", 1);
                    else if (dutyStatusId.equals(AttendanceType.late))
                        this.setDutyRecordProperty(dutyRecord, "lateCount", 1);
                    else if (dutyStatusId.equals(AttendanceType.leaveEarly))
                        this.setDutyRecordProperty(dutyRecord, "leaveEarlyCount", 1);
                }
                utilService.saveOrUpdate(dutyRecord);
                utilService.saveOrUpdate(recordDetail);
            }
        }
        
    }
    
    public void updateRecordDetail(Long recordDetailId, Long dutyStatusId) {
        RecordDetail recordDetail = (RecordDetail) utilService.get(RecordDetail.class,
                recordDetailId);
        updateRecordDetail(recordDetail, dutyStatusId);
    }
    
    /**
     * 更新指定考勤记录详情到指定考勤状态
     * 
     * @param recordDetail
     * @param dutyStatusId
     */
    private void updateRecordDetail(RecordDetail recordDetail, Long dutyStatusId) {
        if (recordDetail == null) {
            throw new RuntimeException("recordDetail==null\n");
        }
        DutyRecord dutyRecord = recordDetail.getDutyRecord();
        AttendanceType oldDutyStatus = recordDetail.getDutyStatus();
        if (oldDutyStatus != null) {
            if (oldDutyStatus.getId().equals(AttendanceType.presence))
                this.setDutyRecordProperty(dutyRecord, "dutyCount", -1);
            else if (oldDutyStatus.getId().equals(AttendanceType.absenteeism))
                this.setDutyRecordProperty(dutyRecord, "absenteeismCount", -1);
            else if (oldDutyStatus.getId().equals(AttendanceType.askedForLeave))
                this.setDutyRecordProperty(dutyRecord, "askedForLeaveCount", -1);
            else if (oldDutyStatus.getId().equals(AttendanceType.late))
                this.setDutyRecordProperty(dutyRecord, "lateCount", -1);
            else if (oldDutyStatus.getId().equals(AttendanceType.leaveEarly))
                this.setDutyRecordProperty(dutyRecord, "leaveEarlyCount", -1);
        }
        if (dutyStatusId != null) {
            if (dutyStatusId.equals(AttendanceType.presence))
                this.setDutyRecordProperty(dutyRecord, "dutyCount", 1);
            else if (dutyStatusId.equals(AttendanceType.absenteeism))
                this.setDutyRecordProperty(dutyRecord, "absenteeismCount", 1);
            else if (dutyStatusId.equals(AttendanceType.askedForLeave))
                this.setDutyRecordProperty(dutyRecord, "askedForLeaveCount", 1);
            else if (dutyStatusId.equals(AttendanceType.late))
                this.setDutyRecordProperty(dutyRecord, "lateCount", 1);
            else if (dutyStatusId.equals(AttendanceType.leaveEarly))
                this.setDutyRecordProperty(dutyRecord, "leaveEarlyCount", 1);
        }
        recordDetail.setDutyStatus(new AttendanceType(dutyStatusId));
        List updateList = new ArrayList();
        updateList.add(dutyRecord);
        updateList.add(recordDetail);
        utilDao.saveOrUpdate(updateList);
    }
    
    public void deleteRecordDetail(Long studentId, Long teachTaskId, Date day, Long beginUnitId,
            Long endUnitId) {
        
        List recordDetailList = utilService.load(RecordDetail.class, new String[] {
                "dutyRecord.student.id", "dutyRecord.teachTask.id", "dutyDate", "beginUnit.id",
                "endUnit.id" },
                new Object[] { studentId, teachTaskId, day, beginUnitId, endUnitId });
        if (!recordDetailList.isEmpty()) {
            for (Iterator iter = recordDetailList.iterator(); iter.hasNext();) {
                RecordDetail recordDetail = (RecordDetail) iter.next();
                deleteRecordDetail(recordDetail);
            }
        }
        
    }
    
    public void deleteRecordDetail(Long recordDetailId) {
        Entity entity = utilService.get(RecordDetail.class, recordDetailId);
        RecordDetail recordDetail = (RecordDetail) entity;
        this.deleteRecordDetail(recordDetail);
    }
    
    /**
     * 删除考勤详情
     * 
     * @param recordDetail
     */
    private void deleteRecordDetail(RecordDetail recordDetail) {
        if (recordDetail == null) {
            throw new RuntimeException("recordDetail==null\n");
        }
        DutyRecord dutyRecord = recordDetail.getDutyRecord();
        AttendanceType dutyStatus = recordDetail.getDutyStatus();
        if (dutyStatus != null) {
            if (dutyStatus.getId().equals(AttendanceType.presence))
                this.setDutyRecordProperty(dutyRecord, "dutyCount", -1);
            else if (dutyStatus.getId().equals(AttendanceType.absenteeism))
                this.setDutyRecordProperty(dutyRecord, "absenteeismCount", -1);
            else if (dutyStatus.getId().equals(AttendanceType.askedForLeave))
                this.setDutyRecordProperty(dutyRecord, "askedForLeaveCount", -1);
            else if (dutyStatus.getId().equals(AttendanceType.late))
                this.setDutyRecordProperty(dutyRecord, "lateCount", -1);
            else if (dutyStatus.getId().equals(AttendanceType.leaveEarly))
                this.setDutyRecordProperty(dutyRecord, "leaveEarlyCount", -1);
        }
        utilService.saveOrUpdate(dutyRecord);
        utilService.remove(recordDetail);
    }
    
    /**
     * 设置调整考勤记录各个计数项的值
     * 
     * @param dutyRecord
     * @param propertyName 计数项名称
     * @param number 调整值，如1，-1，表示该计数项+1或-1
     */
    private void setDutyRecordProperty(DutyRecord dutyRecord, String propertyName, int number) {
        Object propertyObject = null;
        try {
            propertyObject = PropertyUtils.getProperty(dutyRecord, propertyName);
        } catch (Exception e) {
            throw new RuntimeException("dutyRecord get property exception");
        }
        if (propertyObject == null) {
            try {
                PropertyUtils.setProperty(dutyRecord, propertyName, new Integer(number));
            } catch (Exception e) {
                throw new RuntimeException("dutyRecord set property exception");
            }
        } else {
            Integer propertyValue = (Integer) propertyObject;
            try {
                PropertyUtils.setProperty(dutyRecord, propertyName, new Integer(propertyValue
                        .intValue()
                        + number));
            } catch (Exception e) {
                throw new RuntimeException("dutyRecord set property exception");
            }
        }
        dutyRecord.setTotalCount(dutyRecord.getTotalCount() == null ? new Integer(number)
                : new Integer(dutyRecord.getTotalCount().intValue() + number));
    }
    
    public List searchRecordDetail(Long[] stdIds, java.util.Date beginDate, java.util.Date endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map parameterMap = new HashMap();
        String hqlString = "select recordDetail from RecordDetail as recordDetail where recordDetail.dutyRecord.student.id in (:stdIds) "
                + " and recordDetail.dutyDate>=to_date(:beginDate,'YYYY-MM-DD') "
                + " and recordDetail.dutyDate<=to_date(:endDate,'YYYY-MM-DD') order by recordDetail.dutyRecord.student.code asc, recordDetail.dutyDate asc, recordDetail.beginUnitIndex asc, recordDetail.endUnitIndex asc, recordDetail.dutyRecord.teachTask.course.code asc";
        parameterMap.put("beginDate", simpleDateFormat.format(beginDate));
        parameterMap.put("endDate", simpleDateFormat.format(endDate));
        parameterMap.put("stdIds", stdIds);
        List recordDetailList = utilDao.searchHQLQuery(hqlString, parameterMap);
        return recordDetailList;
    }
    
}
