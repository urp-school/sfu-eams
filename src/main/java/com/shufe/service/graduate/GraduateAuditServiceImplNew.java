//$Id: GraduateAuditServiceImpl.java,v 1.54 2007/01/26 01:03:57 yd Exp $
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
 * pippo                2005-11-15          Created
 *  
 ********************************************************************************/

package com.shufe.service.graduate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.AuditStandard;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.baseinfo.Course;
import com.ekingstar.eams.teach.program.SubstituteCourse;
import com.ekingstar.eams.teach.program.service.SubstituteCourseService;
import com.shufe.dao.system.calendar.TermCalculator;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.std.Student;
import com.shufe.service.BasicService;
import com.shufe.service.OutputObserver;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.service.graduate.result.CourseGroupAuditResult;
import com.shufe.service.graduate.result.PlanCourseAuditResult;
import com.shufe.service.graduate.result.TeachPlanAuditResult;
import com.shufe.service.std.StudentService;
import com.shufe.web.action.graduate.StudentAuditProcessObserver;

public class GraduateAuditServiceImplNew extends BasicService implements GraduateAuditService {

  private GradeService gradeService;

  private StudentService studentService;

  private TeachPlanService teachPlanService;

  private AuditStandardService auditStandardService;

  protected SubstituteCourseService substituteCourseService;

  public void setSubstituteCourseService(SubstituteCourseService substituteCourseService) {
    this.substituteCourseService = substituteCourseService;
  }

  public List batchAuditStudent(Long[] studentIdArray, MajorType majorType, Long auditStandardId)
      throws IOException {
    List passStudnet = new ArrayList(studentIdArray.length);
    for (int i = 0; i < studentIdArray.length; i++) {
      if (auditStudentTeachPlan((Student) utilService.get(Student.class, studentIdArray[i]), majorType,
          auditStandardId, new ArrayList(), null)) {
        passStudnet.add(studentIdArray[i]);
      }
    }
    return passStudnet;
  }

  /**
   * @deprecated
   */
  public TeachPlanAuditResult auditTeachPlan(Student student, MajorType majorType, List auditTermList,
      Long auditStandardId, Boolean isGradeDeploy, Boolean returnNullResult) {
    return audit(student, majorType, auditStandardId, auditTermList, null);
  }

  /**
   * 生成CourseGradeMap（Course的Id为key；CourseGrade为value）
   * 
   * @return CourseGradeMap key:Course的Id；value:CourseGrade
   */
  private Map<Course, List<CourseGrade>> populateCourseGradeMap(Long studentId, MajorType majorType,
      Boolean published) {
    Map<Course, List<CourseGrade>> courseGradeMap = new HashMap();
    List scores = gradeService.getCourseGrades(studentId, majorType, published, null);
    for (Iterator iter = scores.iterator(); iter.hasNext();) {
      CourseGrade courseGrade = (CourseGrade) iter.next();
      List<CourseGrade> gradeList = courseGradeMap.get(courseGrade.getCourse());
      if (null == gradeList) {
        gradeList = new ArrayList();
        courseGradeMap.put(courseGrade.getCourse(), gradeList);
      }
      gradeList.add(courseGrade);
    }
    // 最好成绩放在最前
    for (Iterator<Course> iter = courseGradeMap.keySet().iterator(); iter.hasNext();) {
      Course course = iter.next();
      List grades = courseGradeMap.get(course);
      Collections.sort(grades, new Comparator() {
        public int compare(Object arg0, Object arg1) {
          if (Boolean.TRUE.equals(((CourseGrade) arg0).getIsPass())) return -1;
          else {
            return 1;
          }
        }
      });
    }
    return courseGradeMap;
  }

  /**
   * 获取审核标准<br>
   * 若auditStandardId为空返回一个空AuditStandard对象
   * 
   * @param auditStandardId
   * @return
   */
  private AuditStandard getAuditStandard(Long auditStandardId) {
    // 如果没有标准，则默认找其中一个，
    if (auditStandardId == null) {
      AuditStandard auditStandard = new AuditStandard();
      auditStandard.setDisauditCourseTypes(Collections.EMPTY_SET);
      auditStandard.setConvertableCourseTypes(Collections.EMPTY_SET);
      EntityQuery query = new EntityQuery(AuditStandard.class, "st");
      query.setSelect("distinct st.publicCourseType");
      List rs = (List) utilDao.search(query);
      if (!rs.isEmpty()) {
        auditStandard.setPublicCourseType((CourseType) rs.get(0));
      } else {
        auditStandard.setPublicCourseType(new CourseType(CourseType.PUBLIC_COURSID));
      }
      return auditStandard;
    } else {
      return (AuditStandard) utilService.get(AuditStandard.class, auditStandardId);
    }
  }

  /**
   * 双专业毕业审核时，不关联单专业
   * 
   * @see com.shufe.service.graduate.GraduateAuditService#batchGraduateAudit(java.util.List,
   *      java.lang.Integer, java.lang.Long, java.lang.String, com.shufe.service.OutputObserver)
   */
  public void batchGraduateAudit(List stdList, MajorType majorType, Long auditStandardId, String auditTerm,
      OutputObserver observer) throws IOException {
    StudentAuditProcessObserver auditObserver = getAuditObserver(observer);
    List passStdList = new ArrayList();
    List disPassStdList = new ArrayList();
    List auditTermList = getAuditTermList(auditTerm);
    if (majorType.getId().equals(MajorType.SECOND)) {
      for (Iterator iter = stdList.iterator(); iter.hasNext();) {
        Student stdElement = (Student) iter.next();
        if (auditStudentTeachPlan(stdElement, majorType, auditStandardId, auditTermList, auditObserver)) {
          passStdList.add(stdElement.getId());
        } else {
          disPassStdList.add(stdElement.getId());
        }
      }
    } else if (majorType.getId().equals(MajorType.FIRST)) {
      for (Iterator iter = stdList.iterator(); iter.hasNext();) {
        Student stdElement = (Student) iter.next();
        if (auditStudentTeachPlan(stdElement, majorType, auditStandardId, auditTermList, auditObserver)) {
          passStdList.add(stdElement.getId());
        } else {
          disPassStdList.add(stdElement.getId());
        }
      }
    }
    if (CollectionUtils.isEmpty(auditTermList)) {
      studentService.batchUpdateGraduateAuditStatus(passStdList, majorType, new Boolean(true));
      studentService.batchUpdateGraduateAuditStatus(disPassStdList, majorType, new Boolean(false));
    }

  }

  /**
   * 查询单个学生完成情况
   */
  public List getStudentTeachPlanAuditDetail(Student student, MajorType majorType, Long auditStandardId,
      List auditTermList, Boolean isGradeDeploy, Boolean returnNullResult) {
    List list = new ArrayList();
    Map auditTermMap = getAuditTermMap(list, student, majorType, auditStandardId, auditTermList,
        isGradeDeploy, returnNullResult);
    List keyList = new ArrayList(auditTermMap.keySet());
    Collections.sort(keyList);
    for (Iterator iter = keyList.iterator(); iter.hasNext();) {
      MajorType mType = (MajorType) iter.next();
      list.add(audit(student, mType, auditStandardId, auditTermList, null));
    }
    return list;
  }

  /**
   * 在批量查看学生完成情况中使用
   */
  public List getStudentTeachPlanAuditDetail(Student student, MajorType majorType, Long auditStandardId,
      TermCalculator termCalculator, Boolean omitSmallTerm, Boolean isGradeDeploy, Boolean returnNullResult) {
    List list = new ArrayList();
    Map auditTermMap = getAuditTermMap(list, student, majorType, auditStandardId, termCalculator,
        omitSmallTerm, isGradeDeploy, returnNullResult);
    List keyList = new ArrayList(auditTermMap.keySet());
    Collections.sort(keyList);
    for (Iterator iter = keyList.iterator(); iter.hasNext();) {
      MajorType mType = (MajorType) iter.next();
      list.add(audit(student, mType, auditStandardId, (List) auditTermMap.get(mType), null));
    }
    return list;
  }

  /**
   * 获取审核专业类别学期<code>Map</code>
   * 
   * @param list
   * @param student
   * @param majorType
   * @param auditStandardId
   * @param termCalculator
   * @param omitSmallTerm
   * @param isGradeDeploy
   * @param returnNullResult
   * @return
   */
  private Map getAuditTermMap(List list, Student student, MajorType majorType, Long auditStandardId,
      TermCalculator termCalculator, Boolean omitSmallTerm, Boolean isGradeDeploy, Boolean returnNullResult) {
    Map auditTermMap = new HashMap(2);
    List termList = new ArrayList();
    if (majorType == null) {
      Integer term = getTerm(student, termList, new MajorType(MajorType.FIRST), termCalculator,
          omitSmallTerm, returnNullResult);
      auditTermMap.put(new MajorType(MajorType.FIRST), Collections.singletonList(term));
      if (check4Second(student)) {
        term = getTerm(student, termList, new MajorType(MajorType.SECOND), termCalculator, omitSmallTerm,
            returnNullResult);
        auditTermMap.put(new MajorType(MajorType.SECOND), Collections.singletonList(term));
      }
    } else {
      Integer term = getTerm(student, termList, majorType, termCalculator, omitSmallTerm, returnNullResult);
      if (null != term) {
        auditTermMap.put(majorType, Collections.singletonList(term));
      }
    }
    return auditTermMap;
  }

  /**
   * 获取审核学期<code>Map</code>
   * 
   * @param list
   * @param student
   * @param majorType
   * @param auditStandardId
   * @param auditTermList
   * @param isGradeDeploy
   * @param returnNullResult
   * @return
   */
  private Map getAuditTermMap(List list, Student student, MajorType majorType, Long auditStandardId,
      List auditTermList, Boolean isGradeDeploy, Boolean returnNullResult) {
    Map auditTermMap = new HashMap(2);
    if (majorType == null) {
      auditTermMap.put(utilDao.get(MajorType.class, MajorType.FIRST), auditTermList);
      if (check4Second(student)) {
        auditTermMap.put(utilDao.get(MajorType.class, MajorType.SECOND), auditTermList);
      }
    } else {
      auditTermMap.put(majorType, auditTermList);
    }
    return auditTermMap;
  }

  /**
   * 检测双专业信息
   * 
   * @param student
   * @return
   */
  private boolean check4Second(Student student) {
    return ValidEntityPredicate.getInstance().evaluate(student.getSecondAspect())
        || ValidEntityPredicate.getInstance().evaluate(student.getSecondMajor());
  }

  /**
   * 获取审核学期
   * 
   * @param student
   * @param list
   * @param majorType
   * @param termCalculator
   * @param omitSmallTerm
   *          计算学期间隔中,是否忽略小学期
   * @param returnNullResult
   * @return
   */
  private Integer getTerm(Student student, List list, MajorType majorType, TermCalculator termCalculator,
      Boolean omitSmallTerm, Boolean returnNullResult) {
    TeachPlan plan = getTeachPlan(student, majorType);
    try {
      int term = termCalculator.getTerm(plan.getStdType(), plan.getEnrollTurn(), omitSmallTerm);
      return new Integer(term);
    } catch (Exception e) {
      TeachPlanAuditResult result = null;
      if (plan == null) {
        result = new TeachPlanAuditResult(student, majorType);
      } else {
        result = new TeachPlanAuditResult(student, plan, majorType);
      }
      result.setIsPass(Boolean.FALSE);
      result.setRemark(e.getMessage());
      list.add(result);
      return null;
    }
  }

  /**
   * 检测是否有通过的成绩
   * 
   * @param GCEList
   * @return
   */
  private boolean checkGCEList(List GCEList) {
    if (CollectionUtils.isEmpty(GCEList)) {
      return false;
    } else {
      for (Iterator iter = GCEList.iterator(); iter.hasNext();) {
        CourseGrade cg = (CourseGrade) iter.next();
        if (Boolean.TRUE.equals(cg.getIsPass())) { return true; }
      }
      return false;
    }
  }

  /**
   * 获取指定学生、指定专业类别（一/二）、指定审核标准、指定学期的审核结果
   * 
   * @param student
   * @param majorType
   *          为空默认为一专业
   * @param auditStandardId
   *          为空为空审核标准对象
   * @param auditTermList
   *          审核学期，为空默认为全部学期
   * @param observer
   *          观察者对象，为空默认为空的<code>StudentAuditProcessObserver</code>对象
   * @return
   * @throws IOException
   */
  private TeachPlanAuditResult audit(Student student, MajorType majorType, Long auditStandardId,
      List auditTermList, OutputObserver observer) {
    StudentAuditProcessObserver auditObserver = getAuditObserver(observer);
    TeachPlan teachPlan = getTeachPlan(student, majorType);
    try {
      // 查询培养计划
      if (null == teachPlan) {
        if (CollectionUtils.isNotEmpty(auditTermList)) {
          auditObserver.addStdOutputNotify(student.getCode(), student.getName());
        }
        auditObserver.addNoTeachPlanError(student.getCode(), "该生培养计划缺失", student.getId());
        auditObserver.addResult(student.getCode(), false, student.getId(), majorType, auditStandardId,
            getSeqTerms(auditTermList));
        TeachPlanAuditResult result = new TeachPlanAuditResult();
        result.setStudent(student);
        result.setMajorType(majorType);
        result.setIsPass(Boolean.FALSE);
        return result;
      }

      if (CollectionUtils.isEmpty(auditTermList)) {
        auditObserver.addStdOutputNotify(student.getCode(), student.getName());
      }
      // 组装成绩
      Map<Course, List<CourseGrade>> courseGradeMap = populateCourseGradeMap(student.getId(), majorType,
          Boolean.TRUE);
      // 查询审核标准
      AuditStandard auditStandard = getAuditStandard(auditStandardId);
      // 培养计划审核结果对象
      TeachPlanAuditResult result = new TeachPlanAuditResult(student, teachPlan, majorType);
      // 被审核过的课程类别
      Set auditedTypes = new HashSet();
      // 针对每一个组进行审核(包括任选课)
      populateCourseGroupAuditResult(courseGradeMap, student, teachPlan, result, auditStandard,
          auditTermList, auditedTypes);

      if (!courseGradeMap.isEmpty()) {
        // 其他没有具体课程要求的课程组(例如模块课)
        addCG2Group(courseGradeMap, teachPlan, auditStandard, result, auditedTypes);
      }
      // 任意选修课
      CourseGroupAuditResult publicGroupResult = populatePublicGroupResult(courseGradeMap, auditStandard,
          result);
      addConvertedCredit(result, publicGroupResult, auditStandard);

      result.check(CollectionUtils.isEmpty(auditTermList) ? true : auditTermList.size() == teachPlan
          .getTermsCount().intValue());
      addObserverResult(auditObserver, student, majorType, auditStandard.getId(), auditTermList, result
          .getIsPass().booleanValue());
      auditObserver.increaceProcess(1);
      return result;
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * 添加学生审核结果
   * 
   * @param auditObserver
   * @param student
   * @param majorType
   * @param auditStandardId
   * @param auditTermList
   * @param flag
   * @throws IOException
   */
  private void addObserverResult(StudentAuditProcessObserver auditObserver, Student student,
      MajorType majorType, Long auditStandardId, List auditTermList, boolean flag) throws IOException {
    if (CollectionUtils.isNotEmpty(auditTermList)) {
      auditObserver.addStdOutputNotify(student.getCode(), student.getName());
    }
    auditObserver.addResult(student.getCode(), flag, student.getId(), majorType, auditStandardId,
        getSeqTerms(auditTermList));
  }

  private boolean auditStudentTeachPlan(Student student, MajorType majorType, Long auditStandardId,
      List auditTermList, OutputObserver observer) throws IOException {
    TeachPlanAuditResult rs = audit(student, majorType, auditStandardId, auditTermList, observer);
    // 更新学生毕业审核结果 如果没有审核结果 则生成
    AuditResult result = getAuditResult(student, majorType);
    if (null == result) {
      result = new AuditResult(student, majorType);
      result.setIsPass(rs.getIsPass());
    }
    result.setIsCompletePlan(rs.getIsPass());
    utilDao.saveOrUpdate(result);
    return rs.getIsPass().booleanValue();
  }

  // 取得指定学生 专业类别的毕业审核结果
  private AuditResult getAuditResult(Student std, MajorType majorType) {
    EntityQuery query = new EntityQuery(AuditResult.class, "result");
    query.add(new Condition("result.std=:std", std));
    query.add(new Condition("result.majorType=:majorType", majorType));
    List results = (List) utilDao.search(query);
    if (results.isEmpty()) {
      return null;
    } else {
      return (AuditResult) results.get(0);
    }
  }

  /**
   * 生成任意选修课审核结果对象
   * 
   * @param CGMap
   * @param auditStandard
   * @param result
   * @return
   */
  private CourseGroupAuditResult populatePublicGroupResult(Map<Course, List<CourseGrade>> CGMap,
      AuditStandard auditStandard, TeachPlanAuditResult result) {
    CourseType publicCourseType = (CourseType) utilService.get(CourseType.class, auditStandard
        .getPublicCourseType().getId());
    CourseGroupAuditResult publicGroupResult = result.getCourseGroupAuditResult(publicCourseType);
    if (null == publicGroupResult) {
      publicGroupResult = new CourseGroupAuditResult(publicCourseType, null);
      result.addCourseGroupAuditResult(publicGroupResult);
    }
    if (!CGMap.isEmpty()
        && !auditStandard.isDisaudit(new CourseType(auditStandard.getPublicCourseType().getId()))) {
      for (Iterator iter = CGMap.keySet().iterator(); iter.hasNext();) {
        List gceList = CGMap.get(iter.next());
        addPlanCourseAuditResult(publicGroupResult, gceList);
        iter.remove();
      }
    }
    return publicGroupResult;
  }

  /**
   * 在各组成绩添加完成后，将可转换为任意选修课学分的课程组多出来的学分添加到任意选修课转换学分
   * 
   * @param result
   * @param publicGroupResult
   * @param auditStandard
   */
  private void addConvertedCredit(TeachPlanAuditResult result, CourseGroupAuditResult publicGroupResult,
      AuditStandard auditStandard) {
    if (Boolean.FALSE.equals(auditStandard.getIsDualConvert())) {
      for (Iterator iter = result.getCourseGroupAuditResults().iterator(); iter.hasNext();) {
        CourseGroupAuditResult element = (CourseGroupAuditResult) iter.next();
        if (!auditStandard.getPublicCourseType().equals(element.getCourseType())
            && auditStandard.isConvertable(element.getCourseType())) {
          publicGroupResult.getCreditAuditInfo().addConvertedCredit(
              element.getCreditAuditInfo().getMoreCreditToConvert());
        }
      }
    } else {
      Set courseTypes = auditStandard.getConvertableCourseTypes();
      // 首先将多余和需要的审核组进行分开(但是排除公选课)
      List moreCreditList = new ArrayList();
      List needCreditList = new ArrayList();
      for (Iterator iter = result.getCourseGroupAuditResults().iterator(); iter.hasNext();) {
        CourseGroupAuditResult r = (CourseGroupAuditResult) iter.next();
        if (r.getCourseType().equals(auditStandard.getPublicCourseType())) {
          continue;
        }
        if (!courseTypes.contains(r.getCourseType())) continue;
        Float credits = r.getCreditAuditInfo().getCreditToComplete(Boolean.TRUE);
        if (credits.floatValue() > 0) {
          needCreditList.add(r);
        } else if (credits.floatValue() < 0) {
          moreCreditList.add(r);
        }
      }
      // 特殊处理公选课
      if (null != publicGroupResult) {
        Float publicCredit = publicGroupResult.getCreditAuditInfo().getCreditToComplete(Boolean.TRUE);
        if (publicCredit.floatValue() < 0) {
          moreCreditList.add(publicGroupResult);
        } else {
          needCreditList.add(publicGroupResult);
        }
      }

      if (needCreditList.isEmpty()) return;
      // 开始补偿
      Iterator needIter = needCreditList.iterator();
      CourseGroupAuditResult needGroup = (CourseGroupAuditResult) needIter.next();
      for (Iterator iter = moreCreditList.iterator(); iter.hasNext();) {
        CourseGroupAuditResult r = (CourseGroupAuditResult) iter.next();
        float moreCredits = r.getCreditAuditInfo().getMoreCreditToConvert().floatValue();
        float needCredits = needGroup.getCreditAuditInfo().getCreditToComplete(Boolean.TRUE).floatValue();
        // 任选课是魔鬼
        if (needCredits <= 0) {
          needCredits = Float.MAX_VALUE;
        }
        // 只要还有多余的学分
        while (moreCredits > 0) {
          if (r.getCourseType().equals(needGroup.getCourseType())) break;
          if (moreCredits > needCredits) {
            needGroup.getCreditAuditInfo().addConvertedCredit(new Float(needCredits));
          } else {
            needGroup.getCreditAuditInfo().addConvertedCredit(new Float(moreCredits));
          }
          moreCredits -= needCredits;
          // 下一个需要的组和学分
          if (needGroup.getCreditAuditInfo().getCreditToComplete(Boolean.TRUE).floatValue() <= 0) {
            if (!needIter.hasNext()) { return; }
            needGroup = (CourseGroupAuditResult) needIter.next();
          }
          needCredits = needGroup.getCreditAuditInfo().getCreditToComplete(Boolean.TRUE).floatValue();
          if (needCredits <= 0) {
            needCredits = Float.MAX_VALUE;
          }
        }
      }
    }
  }

  /**
   * 将课程成绩计入各课程组审核结果对象
   * 
   * @param CGMap
   * @param teachPlan
   * @param auditStandard
   * @param result
   * @param auditedTypes
   */
  private void addCG2Group(Map<Course, List<CourseGrade>> CGMap, TeachPlan teachPlan,
      AuditStandard auditStandard, TeachPlanAuditResult result, Set auditedTypes) {
    Map courseTypeMap = new HashMap();
    Map courseGroupAuditResultMap = new HashMap();
    for (Iterator iter = CGMap.keySet().iterator(); iter.hasNext();) {
      List gceList = CGMap.get(iter.next());
      CourseGrade firstCG = (CourseGrade) gceList.get(0);
      if (auditStandard.isDisaudit(firstCG.getCourseType())) {
        iter.remove();
        continue;
      }
      // 查找对应group
      CourseGroup group = getCourseGroup(courseTypeMap, teachPlan, firstCG);
      if (null == group) {
        continue;
      }
      // 过滤含有课程的必修组.选修课（不管内部有没有课程）,则允许计入该组
      if (!group.getPlanCourses().isEmpty() && Boolean.TRUE.equals(group.getCourseType().getIsCompulsory())) continue;
      // 查找group审核结果
      CourseGroupAuditResult groupResult = getCourseGroupResult(courseGroupAuditResultMap, result, firstCG);
      if (groupResult != null) {
        addPlanCourseAuditResult(groupResult, gceList);
        iter.remove();
      }
    }
  }

  /**
   * 向课程组审核结果中添加该课程审核结果
   * 
   * @param groupResult
   * @param firstCG
   * @param gceList
   * @return
   */
  private void addPlanCourseAuditResult(CourseGroupAuditResult groupResult, List gceList) {
    CourseGrade firstCG = (CourseGrade) gceList.get(0);
    PlanCourseAuditResult planCourseResult = new PlanCourseAuditResult();
    planCourseResult.getCreditAuditInfo().setRequired(firstCG.getCredit());
    planCourseResult.setTerms("");
    planCourseResult.setCourse((com.shufe.model.system.baseinfo.Course) firstCG.getCourse());
    planCourseResult.setGradeList(gceList);
    addPlanCourseAuditResult(groupResult, planCourseResult);
  }

  /**
   * 查找对应课程组审核结果<code>CourseGroupAuditResult</code>
   * 
   * @param courseGroupAuditResultMap
   * @param result
   * @param firstCG
   * @return
   */
  private CourseGroupAuditResult getCourseGroupResult(Map courseGroupAuditResultMap,
      TeachPlanAuditResult result, CourseGrade firstCG) {
    CourseGroupAuditResult groupResult = (CourseGroupAuditResult) courseGroupAuditResultMap.get(firstCG
        .getCourseType().getId());
    if (null == groupResult) {
      groupResult = result.getCourseGroupAuditResult(firstCG.getCourseType());
      if (null != groupResult) {
        courseGroupAuditResultMap.put(firstCG.getCourseType().getId(), groupResult);
      }
    }
    return groupResult;
  }

  /**
   * 查找对应课程组<code>CourseGroup</code>
   * 
   * @param courseTypeMap
   * @param teachPlan
   * @param firstCG
   * @return
   */
  private CourseGroup getCourseGroup(Map courseTypeMap, TeachPlan teachPlan, CourseGrade firstCG) {
    CourseGroup group = (CourseGroup) courseTypeMap.get(firstCG.getCourseType().getId());
    if (null == group) {
      group = teachPlan.getCourseGroup(firstCG.getCourseType());
      if (null != group) {
        courseTypeMap.put(firstCG.getCourseType().getId(), group);
      }
    }
    return group;
  }

  /**
   * 针对每一个组进行审核(包括任选课)
   * 
   * @param CGMap
   * @param teachPlan
   * @param result
   * @param auditStandard
   * @param auditTermList
   * @param auditedTypes
   */
  private void populateCourseGroupAuditResult(Map<Course, List<CourseGrade>> CGMap, Student student,
      TeachPlan teachPlan, TeachPlanAuditResult result, AuditStandard auditStandard, List auditTermList,
      Set auditedTypes) {
    // 对审核的课程组进行排序,以防选修课程组中含有必修课组里面的课程。从而造成选修课学分满，而必修课未通过
    List courseGroups = new ArrayList(teachPlan.getCourseGroups());
    Collections.sort(courseGroups);
    for (Iterator iterCourseGroup = courseGroups.iterator(); iterCourseGroup.hasNext();) {
      CourseGroup courseGroup = (CourseGroup) iterCourseGroup.next();
      if (logger.isDebugEnabled()) {
        logger.info("audit Group:" + courseGroup.getCourseType().getName());
      }
      boolean disaudit = auditStandard.isDisaudit(courseGroup.getCourseType());
      CourseGroupAuditResult groupAuditResult = null;
      if (!disaudit) {
        groupAuditResult = populateGroupAuditResult(result, courseGroup, auditTermList);
      } else {
        result.disauditCourseType(courseGroup);
      }
      addAuditedCourseType(auditedTypes, courseGroup);
      // 取出指定学期的课程
      List planCourseList = courseGroup.getPlanCourses(auditTermList);
      List substituteCourseList = substituteCourseService.getStdSubstituteCourses(student);
      // 预先审核替代课程，返回剩余的培养计划课程course->planCourse
      Map courseMap = auditSubstitube(CGMap, courseGroup, groupAuditResult, substituteCourseList);
      // 审核组内的所有PlanCourse成绩，对于不审核的课程也进行如下审核步骤，以防这些课程的成绩计入任意选修课
      for (Iterator iterPlanCourse = courseGroup.getPlanCourses().iterator(); iterPlanCourse.hasNext();) {
        PlanCourse planCourse = (PlanCourse) iterPlanCourse.next();
        // 如果不包含，则不进行审核(考虑替代课程)
        if (!courseMap.keySet().contains(planCourse.getCourse())) continue;
        // 审核某一门课程成绩
        PlanCourseAuditResult planCourseResult = auditCourse(CGMap, planCourse);
        // 添加到Group审核结果中
        if (!disaudit && planCourseList.contains(planCourse)) {
          addPlanCourseAuditResult(groupAuditResult, planCourseResult);
        }
      }
    }
  }

  /**
   * 审核替代课程
   * 
   * @param CGMap
   * @param courseGroup
   * @param groupAuditResult
   * @param substituteCourseList
   * @return
   */
  protected Map auditSubstitube(Map<Course, List<CourseGrade>> CGMap, CourseGroup courseGroup,
      CourseGroupAuditResult groupAuditResult, List substituteCourseList) {
    Map courseMap = new HashMap();
    for (Iterator iterator = courseGroup.getPlanCourses().iterator(); iterator.hasNext();) {
      PlanCourse planCourse = (PlanCourse) iterator.next();
      courseMap.put(planCourse.getCourse(), planCourse);
    }
    for (Iterator iterator = substituteCourseList.iterator(); iterator.hasNext();) {
      SubstituteCourse sc = (SubstituteCourse) iterator.next();
      if (courseMap.keySet().containsAll(sc.getOrigins()) && isSubstitutes(CGMap, sc)) {
        // 替代课程是否通过
        boolean passed = true;
        // 保留替代成绩
        List substituteGrades = new ArrayList();
        float substituteCredits = 0f;
        for (Iterator iterator2 = sc.getSubstitutes().iterator(); iterator2.hasNext();) {
          Course c = (Course) iterator2.next();
          List substituteCourseGrades = CGMap.get(c);
          if (!checkGCEList(substituteCourseGrades)) {
            passed = false;
          }
          substituteCredits += c.getCredits().floatValue();
          substituteGrades.addAll(substituteCourseGrades);
          CGMap.remove(c);
        }
        float originCredits = 0f;
        List<PlanCourseAuditResult> courseResults = new ArrayList();
        // 增加原课程审核结果
        // FIXME 不支持笛卡尔积替代和学分不能替代
        for (Iterator iterator2 = sc.getOrigins().iterator(); iterator2.hasNext();) {
          Course c = (Course) iterator2.next();
          PlanCourse planCourse = (PlanCourse) courseMap.get(c);
          // remove grade and populate result
          PlanCourseAuditResult planCourseResult = auditCourse(CGMap, planCourse);
          originCredits += planCourse.getCourse().getCredits().floatValue();
          if (passed) {
            planCourseResult.getCreditAuditInfo().setCompleted(planCourse.getCourse().getCredits());
            planCourseResult.setSubstitionScores(substituteGrades);
          }
          courseResults.add(planCourseResult);
          courseMap.remove(c);
        }
        if (passed) {
          float dalta = substituteCredits - originCredits;
          if (Float.compare(dalta, 0) != 0) {
            courseResults.get(courseResults.size() - 1).getCreditAuditInfo()
                .addCompletedCredit(new Float(dalta));
          }
          for (PlanCourseAuditResult courseResult : courseResults) {
            if (null != groupAuditResult) {
              addPlanCourseAuditResult(groupAuditResult, courseResult);
            }
          }
        }
      }
    }
    return courseMap;
  }

  /**
   * 向课程组审核结果<code>CourseGroupAuditResult</code>中添加课程审核结果 <code>PlanCourseAuditResult</code>
   */
  private void addPlanCourseAuditResult(CourseGroupAuditResult groupAuditResult,
      PlanCourseAuditResult planCourseResult) {
    groupAuditResult.addPlanCourseAuditResult(planCourseResult);
    if (Boolean.TRUE.equals(planCourseResult.getCreditAuditInfo().getIsPass())) {
      groupAuditResult.getCreditAuditInfo().addCompletedCredit(
          planCourseResult.getCreditAuditInfo().getCompleted());
    }
  }

  /**
   * 审核课程
   * 
   * @param CGMap
   * @param planCourseResult
   * @param courseId
   * @param pass
   * @return
   */
  private PlanCourseAuditResult auditCourse(Map<Course, List<CourseGrade>> CGMap, PlanCourse planCourse) {
    PlanCourseAuditResult result = new PlanCourseAuditResult(planCourse);
    List gceList = CGMap.get(planCourse.getCourse());
    result.setGradeList(gceList);
    CGMap.remove(planCourse.getCourse());
    return result;
  }

  /**
   * 添加培养计划中有计划课程的必修组计入已审核。<br>
   * 针对含课程<code>PlanCourse</code>的课程组<code>CourseGroup</code><br>
   * 如普通共同课、专业必修课等，该步审核后加入auditedTypes<br>
   * 这些组不参与到“游离”成绩的审核中。 addCG2Group
   * 
   * @param auditedTypes
   * @param courseGroup
   * @see #addCG2Group
   */
  private void addAuditedCourseType(Set auditedTypes, CourseGroup courseGroup) {
    if (CollectionUtils.isNotEmpty(courseGroup.getPlanCourses())
        && Boolean.TRUE.equals(courseGroup.getCourseType().getIsCompulsory())) {
      auditedTypes.add(courseGroup.getCourseType());
    }
  }

  /**
   * 生成课程组审核结果<code>CourseGroupAuditResult</code>对象
   * 
   * @param result
   * @param courseGroup
   * @param auditTermList
   * @return
   */
  private CourseGroupAuditResult populateGroupAuditResult(TeachPlanAuditResult result,
      CourseGroup courseGroup, List auditTermList) {
    CourseGroupAuditResult groupAuditResult;
    groupAuditResult = new CourseGroupAuditResult(courseGroup, auditTermList);
    result.addCourseGroupAuditResult(groupAuditResult);
    return groupAuditResult;
  }

  /**
   * 获取指定学生指定专业类别（一/二专业）的培养计划
   * 
   * @param student
   * @param majorType
   * @return
   */
  private TeachPlan getTeachPlan(Student student, MajorType majorType) {
    return teachPlanService.getTeachPlan(student, MajorType.SECOND.equals(majorType.getId()) ? Boolean.FALSE
        : Boolean.TRUE, null);
  }

  public List getAuditTermList(String auditTermString) {
    List auditTermList = new ArrayList();
    if (StringUtils.isNotEmpty(auditTermString)) {
      Set auditTermSet = new HashSet();
      String[] auditTermArrayTemp = StringUtils.split(auditTermString, ",");
      for (int i = 0; i < auditTermArrayTemp.length; i++) {
        String[] termRangeArray = auditTermArrayTemp[i].split("-");
        if (ArrayUtils.isEmpty(termRangeArray)) {
        } else {
          int from = NumberUtils.toInt(termRangeArray[0]);
          int to = NumberUtils.toInt(termRangeArray[termRangeArray.length - 1]);
          Set tempSet = new HashSet();
          for (int k = from; k <= to; k++) {
            tempSet.add(new Integer(k));
          }
          auditTermSet.addAll(tempSet);
        }
      }
      auditTermSet.remove(new Integer(0));
      auditTermList.addAll(auditTermSet);
      Collections.sort(auditTermList);
    }
    return auditTermList;
  }

  /**
   * 获得<code>StudentAuditProcessObserver</code>对象，或一个空对象
   * 
   * @param observer
   * @return
   */
  private StudentAuditProcessObserver getAuditObserver(OutputObserver observer) {
    StudentAuditProcessObserver auditObserver = null;
    if (observer instanceof StudentAuditProcessObserver) {
      auditObserver = (StudentAuditProcessObserver) observer;
    } else {
      auditObserver = StudentAuditProcessObserver.NULL_OBSERVER;
    }
    return auditObserver;
  }

  /**
   * 获取审核学期串
   * 
   * @param auditTermList
   * @param isTermEmpty
   * @return
   */
  private String getSeqTerms(List auditTermList) {
    String terms = "";
    if (null == auditTermList) return terms;
    for (Iterator iter = auditTermList.iterator(); iter.hasNext();) {
      Integer element = (Integer) iter.next();
      if (iter.hasNext()) {
        terms += element.intValue() + ",";
      } else {
        terms += element.intValue();
      }
    }
    return terms;
  }

  /**
   * 把替换课程List转换成Map Map（原始课程ID，替换课程）<br>
   * 课程必须在满足两边组都有成绩的情况下，才能按照平均绩点进行比较
   * 
   * @param substituteCourseList
   *          ：替换课程LIst
   * @return
   */
  protected boolean isSubstitutes(Map<Course, List<CourseGrade>> gradeMap, SubstituteCourse substitute) {
    float gpa1 = 0;
    float credit1 = 0;
    boolean fullGrade1 = true;
    for (Iterator it1 = substitute.getOrigins().iterator(); it1.hasNext();) {
      Course course = (Course) it1.next();
      List grades = gradeMap.get(course);
      if (null != grades && !grades.isEmpty()) {
        CourseGrade grade = (CourseGrade) grades.get(0);
        gpa1 += grade.getCredit().floatValue() * grade.getGP().floatValue();
        credit1 += grade.getCredit().floatValue();
      } else {
        fullGrade1 = false;
      }
    }

    float gpa2 = 0;
    float credit2 = 0;
    boolean fullGrade2 = true;
    for (Iterator it1 = substitute.getSubstitutes().iterator(); it1.hasNext();) {
      Course course = (Course) it1.next();
      List grades = gradeMap.get(course);
      if (null != grades && !grades.isEmpty()) {
        CourseGrade grade = (CourseGrade) grades.get(0);
        gpa2 += grade.getCredit().floatValue() * grade.getGP().floatValue();
        credit2 += grade.getCredit().floatValue();
      } else {
        fullGrade2 = false;
      }
    }
    if (credit1 == 0 && fullGrade2) {
      return true;
    } else {
      return (fullGrade1 && fullGrade2) && (credit1 > 0 && credit2 > 0) && (gpa1 / credit1 < gpa2 / credit2);
    }
  }

  public void setStudentService(StudentService studentService) {
    this.studentService = studentService;
  }

  public void setTeachPlanService(TeachPlanService teachPlanService) {
    this.teachPlanService = teachPlanService;
  }

  public AuditStandardService getAuditStandardService() {
    return auditStandardService;
  }

  public void setAuditStandardService(AuditStandardService auditStandardService) {
    this.auditStandardService = auditStandardService;
  }

  public void setGradeService(GradeService gradeService) {
    this.gradeService = gradeService;
  }
}
