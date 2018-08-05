package com.shufe.service.graduate;

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
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.ekingstar.eams.std.graduation.audit.model.AuditStandard;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.baseinfo.Course;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.std.Student;
import com.shufe.service.graduate.result.CourseGroupAuditResult;
import com.shufe.service.graduate.result.PlanCourseAuditResult;
import com.shufe.service.graduate.result.TeachPlanAuditResult;

public class GraduateAuditServiceImpl extends com.shufe.service.BasicService
    implements com.shufe.service.graduate.GraduateAuditService {

  public void setSubstituteCourseService(
      com.ekingstar.eams.teach.program.service.SubstituteCourseService substituteCourseService) {
    this.substituteCourseService = substituteCourseService;
  }

  public List batchAuditStudent(Long studentIdArray[], MajorType majorType, Long auditStandardId)
      throws java.io.IOException {
    List passStudnet = new ArrayList(studentIdArray.length);
    for (int i = 0; i < studentIdArray.length; i++)
      if (auditStudentTeachPlan(
          (com.shufe.model.std.Student) utilService.get(Student.class, studentIdArray[i]), majorType,
          auditStandardId, new ArrayList(), null))
        passStudnet.add(studentIdArray[i]);

    return passStudnet;
  }

  private Map populateCourseGradeMap(Long studentId, MajorType majorType, Boolean published, Boolean isPass) {
    Map<Long, List<CourseGrade>> courseGradeMap = new HashMap();
    List scores = gradeService.getCourseGrades(studentId, majorType, published, isPass);
    for (Iterator iter = scores.iterator(); iter.hasNext();) {
      CourseGrade courseGrade = (CourseGrade) iter.next();
      List<CourseGrade> gradeList = courseGradeMap.get(courseGrade.getCourse().getId());
      if (null == gradeList) {
        gradeList = new ArrayList();
        courseGradeMap.put(courseGrade.getCourse().getId(), gradeList);
      }
      gradeList.add(courseGrade);
    }
    // 最好成绩放在最前
    for (Iterator<Long> iter = courseGradeMap.keySet().iterator(); iter.hasNext();) {
      Long courseId = iter.next();
      List grades = courseGradeMap.get(courseId);
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

  private AuditStandard getAuditStandard(Long auditStandardId) {
    if (auditStandardId == null) return getNullAuditStandard();
    else return (AuditStandard) utilService.get(AuditStandard.class, auditStandardId);
  }

  private AuditStandard getNullAuditStandard() {
    AuditStandard auditStandard = new AuditStandard();
    auditStandard.setDisauditCourseTypes(Collections.EMPTY_SET);
    auditStandard.setConvertableCourseTypes(Collections.EMPTY_SET);
    auditStandard.setPublicCourseType(new CourseType(CourseType.PUBLIC_COURSID));
    return auditStandard;
  }

  public void batchGraduateAudit(List stdList, MajorType majorType, Long auditStandardId, String auditTerm,
      com.shufe.service.OutputObserver observer) throws java.io.IOException {
    com.shufe.web.action.graduate.StudentAuditProcessObserver auditObserver = getAuditObserver(observer);
    List passStdList = new ArrayList();
    List disPassStdList = new ArrayList();
    List auditTermList = getAuditTermList(auditTerm);
    if (majorType.getId().equals(MajorType.SECOND)) {
      for (Iterator iter = stdList.iterator(); iter.hasNext();) {
        com.shufe.model.std.Student stdElement = (com.shufe.model.std.Student) iter.next();
        if (auditStudentTeachPlan(stdElement, majorType, auditStandardId, auditTermList, auditObserver))
          passStdList.add(stdElement.getId());
        else disPassStdList.add(stdElement.getId());
      }

    } else if (majorType.getId().equals(MajorType.FIRST)) {
      for (Iterator iter = stdList.iterator(); iter.hasNext();) {
        com.shufe.model.std.Student stdElement = (com.shufe.model.std.Student) iter.next();
        if (auditStudentTeachPlan(stdElement, majorType, auditStandardId, auditTermList, auditObserver))
          passStdList.add(stdElement.getId());
        else disPassStdList.add(stdElement.getId());
      }

    }
    if (CollectionUtils.isEmpty(auditTermList)) {
      studentService.batchUpdateGraduateAuditStatus(passStdList, majorType, new Boolean(true));
      studentService.batchUpdateGraduateAuditStatus(disPassStdList, majorType, new Boolean(false));
    }
  }

  public List getStudentTeachPlanAuditDetail(com.shufe.model.std.Student student, MajorType majorType,
      Long auditStandardId, List auditTermList, Boolean isGradeDeploy, Boolean returnNullResult) {
    List list = new ArrayList();
    addStudentTeachPlanAuditDetail(list, student, auditStandardId, getAuditTermMap(list, student, majorType,
        auditStandardId, auditTermList, isGradeDeploy, returnNullResult), isGradeDeploy, returnNullResult);
    return list;
  }

  public List getStudentTeachPlanAuditDetail(com.shufe.model.std.Student student, MajorType majorType,
      Long auditStandardId, com.shufe.dao.system.calendar.TermCalculator termCalculator,
      Boolean omitSmallTerm, Boolean isGradeDeploy, Boolean returnNullResult) {
    List list = new ArrayList();
    addStudentTeachPlanAuditDetail(list, student, auditStandardId, getAuditTermMap(list, student, majorType,
        auditStandardId, termCalculator, omitSmallTerm, isGradeDeploy, returnNullResult), isGradeDeploy,
        returnNullResult);
    return list;
  }

  private void addStudentTeachPlanAuditDetail(List auditDetailList, com.shufe.model.std.Student student,
      Long auditStandardId, Map auditTermMap, Boolean isGradeDeploy, Boolean returnNullResult) {
    List keyList = new ArrayList(auditTermMap.keySet());
    Collections.sort(keyList);
    MajorType majorType;
    List auditTermList;
    for (Iterator iter = keyList.iterator(); iter.hasNext(); auditDetailList.add(auditTeachPlan(student,
        majorType, auditTermList, auditStandardId, isGradeDeploy, returnNullResult))) {
      majorType = (MajorType) iter.next();
      auditTermList = (List) auditTermMap.get(majorType);
    }

  }

  private Map getAuditTermMap(List list, com.shufe.model.std.Student student, MajorType majorType,
      Long auditStandardId, com.shufe.dao.system.calendar.TermCalculator termCalculator,
      Boolean omitSmallTerm, Boolean isGradeDeploy, Boolean returnNullResult) {
    Map auditTermMap = new HashMap(2);
    if (majorType == null) {
      addTermList2Map(list, auditTermMap, student, new MajorType(MajorType.FIRST), termCalculator,
          omitSmallTerm, returnNullResult);
      if (check4Second(student)) addTermList2Map(list, auditTermMap, student, new MajorType(MajorType.SECOND),
          termCalculator, omitSmallTerm, returnNullResult);
    } else {
      addTermList2Map(list, auditTermMap, student, majorType, termCalculator, omitSmallTerm,
          returnNullResult);
    }
    return auditTermMap;
  }

  private Map getAuditTermMap(List list, com.shufe.model.std.Student student, MajorType majorType,
      Long auditStandardId, List auditTermList, Boolean isGradeDeploy, Boolean returnNullResult) {
    Map auditTermMap = new HashMap(2);
    if (majorType == null) {
      auditTermMap.put(utilDao.get(MajorType.class, MajorType.FIRST), auditTermList);
      if (check4Second(student))
        auditTermMap.put(utilDao.get(MajorType.class, MajorType.SECOND), auditTermList);
    } else {
      auditTermMap.put(majorType, auditTermList);
    }
    return auditTermMap;
  }

  private Map addTermList2Map(List list, Map auditTermMap, com.shufe.model.std.Student student,
      MajorType majorType, com.shufe.dao.system.calendar.TermCalculator termCalculator, Boolean omitSmallTerm,
      Boolean returnNullResult) {
    List termList = getTermList(student, list, majorType, termCalculator, omitSmallTerm, returnNullResult);
    if (termList != null) auditTermMap.put(majorType, termList);
    return auditTermMap;
  }

  private boolean check4Second(com.shufe.model.std.Student student) {
    return com.ekingstar.commons.model.predicate.ValidEntityPredicate.getInstance()
        .evaluate(student.getSecondAspect())
        || com.ekingstar.commons.model.predicate.ValidEntityPredicate.getInstance()
            .evaluate(student.getSecondMajor());
  }

  private List getTermList(com.shufe.model.std.Student student, List list, MajorType majorType,
      com.shufe.dao.system.calendar.TermCalculator termCalculator, Boolean omitSmallTerm,
      Boolean returnNullResult) {
    List termList = null;
    try {
      termList = getAuditTermList(student, majorType, termCalculator, omitSmallTerm);
    } catch (com.ekingstar.commons.model.pojo.PojoNotExistException e) {
      if (Boolean.TRUE.equals(returnNullResult)) {
        addTeachPlanAuditResult(list, student, null, majorType, null);
        return null;
      } else {
        throw e;
      }
    } catch (com.shufe.model.system.calendar.OnCampusTimeNotFoundException e) {
      addTeachPlanAuditResult(list, student, getTeachPlan(student, majorType), majorType,
          "error.calendar.infoNotIntegrity");
      return null;
    }
    return termList;
  }

  private void addTeachPlanAuditResult(List list, com.shufe.model.std.Student student, TeachPlan teachPlan,
      MajorType majorType, String remarkKey) {
    TeachPlanAuditResult result = null;
    if (teachPlan == null) result = new TeachPlanAuditResult(student, majorType);
    else result = new TeachPlanAuditResult(student, teachPlan, majorType);
    result.setIsPass(Boolean.FALSE);
    result.setRemark(remarkKey);
    list.add(result);
  }

  private List getAuditTermList(com.shufe.model.std.Student student, MajorType majorType,
      com.shufe.dao.system.calendar.TermCalculator termCalculator, Boolean omitSmallTerm) {
    TeachPlan plan = getTeachPlan(student, majorType);
    int term = termCalculator.getTerm(plan.getStdType(), plan.getEnrollTurn(), omitSmallTerm);
    List auditTermList = new ArrayList();
    auditTermList.add(new Integer(term));
    return auditTermList;
  }

  private boolean checkGCEMap(Map GCEMap, Long courseId) {
    return checkGCEList((List) GCEMap.get(courseId));
  }

  private boolean checkGCEList(List GCEList) {
    if (CollectionUtils.isEmpty(GCEList)) return false;
    for (Iterator iter = GCEList.iterator(); iter.hasNext();) {
      CourseGrade cg = (CourseGrade) iter.next();
      if (Boolean.TRUE.equals(cg.getIsPass())) return true;
    }

    return false;
  }

  public TeachPlanAuditResult auditTeachPlan(com.shufe.model.std.Student student, MajorType majorType,
      List auditTermList, Long auditStandardId, Boolean isGradeDeploy, Boolean returnNullResult) {
    TeachPlan teachPlan = null;
    try {
      teachPlan = getTeachPlan(student, majorType);
    } catch (com.ekingstar.commons.model.pojo.PojoNotExistException e) {
      if (Boolean.TRUE.equals(returnNullResult)) return getNullResult(student, majorType);
      else throw e;
    }
    if (null == teachPlan) return getNullResult(student, majorType);
    
    Map CGMap = populateCourseGradeMap(student.getId(), majorType, isGradeDeploy, null);
    AuditStandard auditStandard = getAuditStandard(auditStandardId);
    TeachPlanAuditResult result = new TeachPlanAuditResult(student, teachPlan, majorType);
    Set auditedTypes = new HashSet();
    populateCourseGroupAuditResult(CGMap, student, teachPlan, result, auditStandard, auditTermList,
        auditedTypes);
    if (!CGMap.isEmpty()) addCG2Group(CGMap, teachPlan, auditStandard, result, auditedTypes);
    CourseGroupAuditResult publicGroupResult = populatePublicGroupResult(CGMap, auditStandard, result);
    addConvertedCredit(result, publicGroupResult, auditStandard);
    result.check(CollectionUtils.isEmpty(auditTermList) ? true
        : auditTermList.size() == teachPlan.getTermsCount().intValue());
    return result;
  }

  private boolean auditStudentTeachPlan(com.shufe.model.std.Student student, MajorType majorType,
      Long auditStandardId, List auditTermList, com.shufe.service.OutputObserver observer)
          throws java.io.IOException {
    TeachPlan teachPlan = getTeachPlan(student, majorType);
    boolean flag = true;
    com.shufe.web.action.graduate.StudentAuditProcessObserver auditObserver = getAuditObserver(observer);
    if (CollectionUtils.isEmpty(auditTermList))
      auditObserver.addStdOutputNotify(student.getCode(), student.getName());
    Map teachPlanCreditMap = new HashMap();
    Map courseGradeMap = populateCourseGradeMap(student.getId(), majorType, null, Boolean.TRUE);
    if (teachPlan == null)
      return addNullTeachPlanResult(student, majorType, auditStandardId, auditTermList, auditObserver);
    AuditStandard auditStandard = getAuditStandard(auditStandardId);
    Set courseTypeSet = new HashSet();
    Set auditedTypes = new HashSet();
    if (!auditFromTeachPlan(student, teachPlan, auditTermList, teachPlanCreditMap, courseGradeMap,
        courseTypeSet, auditedTypes))
      return false;
    if (!courseGradeMap.isEmpty())
      addCGCredit2Map(teachPlanCreditMap, courseGradeMap, courseTypeSet, auditedTypes);
    if (Boolean.FALSE.equals(auditStandard.getIsDualConvert()) && !courseGradeMap.isEmpty())
      addPublicCredit2Map(teachPlanCreditMap, courseGradeMap);
    if (!checkCredit(student, teachPlan, auditStandard, majorType, teachPlanCreditMap, auditTermList,
        auditObserver)) {
      return false;
    } else {
      auditObserver.increaceProcess(1);
      return flag;
    }
  }

  private boolean auditFromTeachPlan(com.shufe.model.std.Student student, TeachPlan teachPlan,
      List auditTermList, Map teachPlanCreditMap, Map gradeMap, Set courseTypeSet, Set auditedTypes) {
    List courseGroups = new ArrayList(teachPlan.getCourseGroups());
    Collections.sort(courseGroups);
    CourseGroup courseGroup;
    float courseGroupCredit;
    for (Iterator iterCourseGroup = courseGroups.iterator(); iterCourseGroup.hasNext(); teachPlanCreditMap
        .put(courseGroup.getCourseType().getId(), new Float(courseGroupCredit))) {
      courseGroup = (com.shufe.model.course.plan.CourseGroup) iterCourseGroup.next();
      courseTypeSet.add(courseGroup.getCourseType().getId());
      courseGroupCredit = MapUtils.getFloatValue(teachPlanCreditMap, courseGroup.getCourseType().getId());
      if (CollectionUtils.isNotEmpty(courseGroup.getPlanCourses())
          && Boolean.TRUE.equals(courseGroup.getCourseType().getIsCompulsory()))
        auditedTypes.add(courseGroup.getCourseType().getId());
      List auditPlanCourseList = courseGroup.getPlanCourses(auditTermList);
      Map courseMap = new HashMap();
      float passedCredits = auditSubstitube2(gradeMap, courseGroup, courseMap,
          substituteCourseService.getStdSubstituteCourses(student));
      courseGroupCredit += passedCredits;
      for (Iterator iterPlanCourse = courseGroup.getPlanCourses().iterator(); iterPlanCourse.hasNext();) {
        PlanCourse planCourse = (com.shufe.model.course.plan.PlanCourse) iterPlanCourse.next();
        if (courseMap.keySet().contains(planCourse.getCourse())) {
          if (auditPlanCourseList.contains(planCourse)
              && gradeMap.containsKey(planCourse.getCourse().getId()))
            courseGroupCredit += planCourse.getCourse().getCredits().floatValue();
          gradeMap.remove(planCourse.getCourse().getId());
        }
      }
    }
    return true;
  }

  private CourseGroupAuditResult populatePublicGroupResult(Map CGMap, AuditStandard auditStandard,
      TeachPlanAuditResult result) {
    CourseType publicCourseType = (CourseType) utilService.get(CourseType.class,
        auditStandard.getPublicCourseType().getId());
    CourseGroupAuditResult publicGroupResult = result.getCourseGroupAuditResult(publicCourseType);
    if (publicGroupResult == null) {
      publicGroupResult = new CourseGroupAuditResult(publicCourseType, null);
      result.addCourseGroupAuditResult(publicGroupResult);
    }
    if (!CGMap.isEmpty()
        && !auditStandard.isDisaudit(new CourseType(auditStandard.getPublicCourseType().getId()))) {
      for (Iterator iter = CGMap.keySet().iterator(); iter.hasNext(); iter.remove()) {
        List gceList = (List) CGMap.get(iter.next());
        CourseGrade firstCG = getFirstCG(gceList);
        addPlanCourseAuditResult(publicGroupResult, firstCG, gceList);
      }

    }
    return publicGroupResult;
  }

  private CourseGrade getFirstCG(List gceList) {
    List passGCEList = (List) CollectionUtils.select(gceList,
        com.shufe.service.graduate.CGPassPredicate.getInstance());
    CourseGrade firstCG = (CourseGrade) (CollectionUtils.isEmpty(passGCEList) ? gceList.get(0)
        : passGCEList.get(0));
    return firstCG;
  }

  private void addConvertedCredit(TeachPlanAuditResult result, CourseGroupAuditResult publicGroupResult,
      AuditStandard auditStandard) {
    if (Boolean.FALSE.equals(auditStandard.getIsDualConvert())) {
      for (Iterator iter = result.getCourseGroupAuditResults().iterator(); iter.hasNext();) {
        CourseGroupAuditResult element = (CourseGroupAuditResult) iter.next();
        if (!auditStandard.getPublicCourseType().equals(element.getCourseType())
            && auditStandard.isConvertable(element.getCourseType()))
          publicGroupResult.getCreditAuditInfo()
              .addConvertedCredit(element.getCreditAuditInfo().getMoreCreditToConvert());
      }

    } else {
      Set courseTypes = auditStandard.getConvertableCourseTypes();
      List moreCreditList = new ArrayList();
      List needCreditList = new ArrayList();
      for (Iterator iter = result.getCourseGroupAuditResults().iterator(); iter.hasNext();) {
        CourseGroupAuditResult r = (CourseGroupAuditResult) iter.next();
        if (!r.getCourseType().equals(auditStandard.getPublicCourseType())
            && courseTypes.contains(r.getCourseType())) {
          Float credits = r.getCreditAuditInfo().getCreditToComplete(Boolean.TRUE);
          if (credits.floatValue() > 0.0F) needCreditList.add(r);
          else if (credits.floatValue() < 0.0F) moreCreditList.add(r);
        }
      }

      if (publicGroupResult != null) {
        Float publicCredit = publicGroupResult.getCreditAuditInfo().getCreditToComplete(Boolean.TRUE);
        if (publicCredit.floatValue() < 0.0F) moreCreditList.add(publicGroupResult);
        else needCreditList.add(publicGroupResult);
      }
      if (needCreditList.isEmpty()) return;
      Iterator needIter = needCreditList.iterator();
      CourseGroupAuditResult needGroup = (CourseGroupAuditResult) needIter.next();
      for (Iterator iter = moreCreditList.iterator(); iter.hasNext();) {
        CourseGroupAuditResult r = (CourseGroupAuditResult) iter.next();
        float moreCredits = r.getCreditAuditInfo().getMoreCreditToConvert().floatValue();
        float needCredits = needGroup.getCreditAuditInfo().getCreditToComplete(Boolean.TRUE).floatValue();
        if (needCredits <= 0.0F) needCredits = 3.402823E+38F;
        while (moreCredits > 0.0F) {
          if (r.getCourseType().equals(needGroup.getCourseType())) break;
          if (moreCredits > needCredits)
            needGroup.getCreditAuditInfo().addConvertedCredit(new Float(needCredits));
          else needGroup.getCreditAuditInfo().addConvertedCredit(new Float(moreCredits));
          moreCredits -= needCredits;
          if (needGroup.getCreditAuditInfo().getCreditToComplete(Boolean.TRUE).floatValue() <= 0.0F) {
            if (!needIter.hasNext()) return;
            needGroup = (CourseGroupAuditResult) needIter.next();
          }
          needCredits = needGroup.getCreditAuditInfo().getCreditToComplete(Boolean.TRUE).floatValue();
          if (needCredits <= 0.0F) needCredits = 3.402823E+38F;
        }
      }

    }
  }

  private void addCG2Group(Map CGMap, TeachPlan teachPlan, AuditStandard auditStandard,
      TeachPlanAuditResult result, Set auditedTypes) {
    Map courseTypeMap = new HashMap();
    Map courseGroupAuditResultMap = new HashMap();
    for (Iterator iter = CGMap.keySet().iterator(); iter.hasNext();) {
      List gceList = (List) CGMap.get(iter.next());
      CourseGrade firstCG = getFirstCG(gceList);
      if (auditStandard.isDisaudit(firstCG.getCourseType())) {
        iter.remove();
      } else {
        CourseGroup group = getCourseGroup(courseTypeMap, teachPlan, firstCG);
        if (group != null && (group.getPlanCourses().isEmpty()
            || !Boolean.TRUE.equals(group.getCourseType().getIsCompulsory()))) {
          CourseGroupAuditResult groupResult = getCourseGroupResult(courseGroupAuditResultMap, result,
              firstCG);
          if (groupResult != null) {
            addPlanCourseAuditResult(groupResult, firstCG, gceList);
            iter.remove();
          }
        }
      }
    }

  }

  private void addPlanCourseAuditResult(CourseGroupAuditResult groupResult, CourseGrade firstCG,
      List gceList) {
    PlanCourseAuditResult planCourseResult = new PlanCourseAuditResult();
    planCourseResult.getCreditAuditInfo().setRequired(firstCG.getCredit());
    planCourseResult.setTerms("");
    planCourseResult.setCourse((com.shufe.model.system.baseinfo.Course) firstCG.getCourse());
    planCourseResult.setGradeList(gceList);
    addPlanCourseAuditResult(groupResult, planCourseResult);
  }

  private CourseGroupAuditResult getCourseGroupResult(Map courseGroupAuditResultMap,
      TeachPlanAuditResult result, CourseGrade firstCG) {
    CourseGroupAuditResult groupResult = (CourseGroupAuditResult) courseGroupAuditResultMap
        .get(firstCG.getCourseType().getId());
    if (groupResult == null) {
      groupResult = result.getCourseGroupAuditResult(firstCG.getCourseType());
      if (groupResult != null) courseGroupAuditResultMap.put(firstCG.getCourseType().getId(), groupResult);
    }
    return groupResult;
  }

  private CourseGroup getCourseGroup(Map courseTypeMap, TeachPlan teachPlan, CourseGrade firstCG) {
    CourseGroup group = (com.shufe.model.course.plan.CourseGroup) courseTypeMap
        .get(firstCG.getCourseType().getId());
    if (group == null) {
      group = teachPlan.getCourseGroup(firstCG.getCourseType());
      if (group != null) courseTypeMap.put(firstCG.getCourseType().getId(), group);
    }
    return group;
  }

  private void populateCourseGroupAuditResult(Map CGMap, com.shufe.model.std.Student student,
      TeachPlan teachPlan, TeachPlanAuditResult result, AuditStandard auditStandard, List auditTermList,
      Set auditedTypes) {
    List courseGroups = new ArrayList(teachPlan.getCourseGroups());
    Collections.sort(courseGroups);
    for (Iterator iterCourseGroup = courseGroups.iterator(); iterCourseGroup.hasNext();) {
      CourseGroup courseGroup = (com.shufe.model.course.plan.CourseGroup) iterCourseGroup.next();
      if (logger.isDebugEnabled()) logger
          .info((new StringBuilder("audit Group:")).append(courseGroup.getCourseType().getName()).toString());
      boolean disaudit = auditStandard.isDisaudit(courseGroup.getCourseType());
      CourseGroupAuditResult groupAuditResult = null;
      if (!disaudit) groupAuditResult = populateGroupAuditResult(result, courseGroup, auditTermList);
      else result.disauditCourseType(courseGroup);
      addAuditedCourseType(auditedTypes, courseGroup);
      List planCourseList = courseGroup.getPlanCourses(auditTermList);
      List substituteCourseList = substituteCourseService.getStdSubstituteCourses(student);
      Map courseMap = auditSubstitube1(CGMap, courseGroup, groupAuditResult, substituteCourseList);
      for (Iterator iterPlanCourse = courseGroup.getPlanCourses().iterator(); iterPlanCourse.hasNext();) {
        PlanCourse planCourse = (com.shufe.model.course.plan.PlanCourse) iterPlanCourse.next();
        if (courseMap.keySet().contains(planCourse.getCourse())) {
          PlanCourseAuditResult planCourseResult = new PlanCourseAuditResult(planCourse);
          Long courseId = planCourse.getCourse().getId();
          boolean pass = false;
          pass = auditCourse(CGMap, planCourseResult, courseId);
          if (!disaudit && planCourseList.contains(planCourse))
            addPlanCourseAuditResult(groupAuditResult, planCourseResult, planCourse, pass);
        }
      }
    }
  }

  protected Map auditSubstitube1(Map CGMap, CourseGroup courseGroup, CourseGroupAuditResult groupAuditResult,
      List substituteCourseList) {
    Map courseMap = new HashMap();
    for (Iterator iterator = courseGroup.getPlanCourses().iterator(); iterator.hasNext();) {
      PlanCourse planCourse = (com.shufe.model.course.plan.PlanCourse) iterator.next();
      courseMap.put(planCourse.getCourse(), planCourse);
    }
    for (Iterator iterator = substituteCourseList.iterator(); iterator.hasNext();) {
      com.ekingstar.eams.teach.program.SubstituteCourse sc = (com.ekingstar.eams.teach.program.SubstituteCourse) iterator
          .next();
      if (courseMap.keySet().containsAll(sc.getOrigins()) && isSubstitutes(CGMap, sc)) {
        boolean passed = true;
        List substituteGrades = new ArrayList();
        for (Iterator iterator2 = sc.getSubstitutes().iterator(); iterator2.hasNext();) {
          Course c = (Course) iterator2.next();
          List substituteCourseGrades = (List) CGMap.get(c.getId());
          if (!checkGCEList(substituteCourseGrades)) passed = false;
          substituteGrades.addAll(substituteCourseGrades);
          CGMap.remove(c.getId());
        }
        for (Iterator iterator2 = sc.getOrigins().iterator(); iterator2.hasNext();) {
          Course c = (Course) iterator2.next();
          PlanCourse planCourse = (com.shufe.model.course.plan.PlanCourse) courseMap.get(c);
          PlanCourseAuditResult planCourseResult = new PlanCourseAuditResult(planCourse);
          if (passed) {
            planCourseResult.getCreditAuditInfo()
                .setCompleted(planCourseResult.getCreditAuditInfo().getRequired());
            planCourseResult.setSubstitionScores(substituteGrades);
          }
          auditCourse(CGMap, planCourseResult, planCourse.getId());
          if (groupAuditResult != null)
            addPlanCourseAuditResult(groupAuditResult, planCourseResult, planCourse, passed);
          courseMap.remove(c);
        }

      }
    }

    return courseMap;
  }

  protected float auditSubstitube2(Map CGMap, CourseGroup courseGroup, Map courseMap,
      List substituteCourseList) {
    PlanCourse planCourse;
    for (Iterator iterator = courseGroup.getPlanCourses().iterator(); iterator.hasNext(); courseMap
        .put(planCourse.getCourse(), planCourse))
      planCourse = (com.shufe.model.course.plan.PlanCourse) iterator.next();

    float credits = 0.0F;
    for (Iterator iterator = substituteCourseList.iterator(); iterator.hasNext();) {
      com.ekingstar.eams.teach.program.SubstituteCourse sc = (com.ekingstar.eams.teach.program.SubstituteCourse) iterator
          .next();
      if (courseMap.keySet().containsAll(sc.getOrigins()) && isSubstitutes(CGMap, sc)) {
        for (Iterator iterator2 = sc.getOrigins().iterator(); iterator2.hasNext();) {
          Course c = (Course) iterator2.next();
          credits += c.getCredits().floatValue();
          courseMap.remove(c);
          CGMap.remove(c.getId());
        }
        for (Iterator iterator2 = sc.getSubstitutes().iterator(); iterator2.hasNext();) {
          Course c = (Course) iterator2.next();
          List gceList = (List) CGMap.get(c.getId());
          CourseGrade firstCG = getFirstCG(gceList);
          CGMap.remove(c.getId());
        }
      }
    }
    return credits;
  }

  private void addPlanCourseAuditResult(CourseGroupAuditResult groupAuditResult,
      PlanCourseAuditResult planCourseResult, PlanCourse planCourse, boolean pass) {
    groupAuditResult.addPlanCourseAuditResult(planCourseResult);
    if (pass) groupAuditResult.getCreditAuditInfo().addCompletedCredit(planCourse.getCourse().getCredits());
  }

  private void addPlanCourseAuditResult(CourseGroupAuditResult groupAuditResult,
      PlanCourseAuditResult planCourseResult) {
    groupAuditResult.addPlanCourseAuditResult(planCourseResult);
    boolean pass = planCourseResult.getCreditAuditInfo().getIsPass();
    if (pass) groupAuditResult.getCreditAuditInfo()
        .addCompletedCredit(planCourseResult.getCreditAuditInfo().getCompleted());
  }

  private boolean auditCourse(Map CGMap, PlanCourseAuditResult planCourseResult, Long courseId) {
    boolean pass = false;
    List gceList = (List) CGMap.get(courseId);
    pass = planCourseResult.setGradeList(gceList);
    CGMap.remove(courseId);
    return pass;
  }

  private void addAuditedCourseType(Set auditedTypes, CourseGroup courseGroup) {
    if (CollectionUtils.isNotEmpty(courseGroup.getPlanCourses())
        && Boolean.TRUE.equals(courseGroup.getCourseType().getIsCompulsory()))
      auditedTypes.add(courseGroup.getCourseType());
  }

  private CourseGroupAuditResult populateGroupAuditResult(TeachPlanAuditResult result,
      CourseGroup courseGroup, List auditTermList) {
    CourseGroupAuditResult groupAuditResult = new CourseGroupAuditResult(courseGroup, auditTermList);
    result.addCourseGroupAuditResult(groupAuditResult);
    return groupAuditResult;
  }

  private TeachPlanAuditResult getNullResult(com.shufe.model.std.Student student, MajorType majorType) {
    TeachPlanAuditResult result = new TeachPlanAuditResult();
    result.setStudent(student);
    result.setMajorType(majorType);
    result.setIsPass(Boolean.FALSE);
    return result;
  }

  private TeachPlan getTeachPlan(com.shufe.model.std.Student student, MajorType majorType) {
    return teachPlanService.getTeachPlan(student,
        MajorType.SECOND.equals(majorType.getId()) ? Boolean.FALSE : Boolean.TRUE, null);
  }

  public List getAuditTermList(String auditTermString) {
    List auditTermList = new ArrayList();
    if (org.apache.commons.lang.StringUtils.isNotEmpty(auditTermString)) {
      Set auditTermSet = new HashSet();
      String auditTermArrayTemp[] = org.apache.commons.lang.StringUtils.split(auditTermString, ",");
      for (int i = 0; i < auditTermArrayTemp.length; i++) {
        String termRangeArray[] = auditTermArrayTemp[i].split("-");
        if (!org.apache.commons.lang.ArrayUtils.isEmpty(termRangeArray)) {
          int from = NumberUtils.toInt(termRangeArray[0]);
          int to = NumberUtils.toInt(termRangeArray[termRangeArray.length - 1]);
          Set tempSet = new HashSet();
          for (int k = from; k <= to; k++)
            tempSet.add(new Integer(k));

          auditTermSet.addAll(tempSet);
        }
      }

      auditTermSet.remove(new Integer(0));
      auditTermList.addAll(auditTermSet);
      Collections.sort(auditTermList);
    }
    return auditTermList;
  }

  private boolean checkCredit(com.shufe.model.std.Student student, TeachPlan teachPlan,
      AuditStandard auditStandard, MajorType majorType, Map teachPlanCreditMap, List auditTermList,
      com.shufe.web.action.graduate.StudentAuditProcessObserver auditObserver) throws java.io.IOException {
    float teachPlanRequireCredit;
    float teachPlanCredit;
    if (Boolean.FALSE.equals(auditStandard.getIsDualConvert())) {
      teachPlanRequireCredit = getTeachPlanRequireCredit(teachPlan, auditTermList);
      teachPlanCredit = 0.0F;
      CourseGroup publicCourseGroup = null;
      float moreCreditToConvert = 0.0F;
      for (Iterator iter = teachPlan.getCourseGroups().iterator(); iter.hasNext();) {
        CourseGroup courseGroupElement = (com.shufe.model.course.plan.CourseGroup) iter.next();
        float courseGroupCredit = MapUtils.getFloatValue(teachPlanCreditMap,
            courseGroupElement.getCourseType().getId());
        float courseGroupRequireCredit = courseGroupElement.getCredit(auditTermList).floatValue();
        if (auditStandard.isDisaudit(courseGroupElement.getCourseType())) {
          teachPlanRequireCredit -= courseGroupRequireCredit;
          teachPlanCreditMap.remove(courseGroupElement.getCourseType().getId());
        } else {
          teachPlanCredit += courseGroupCredit;
          if (CourseType.PUBLIC_COURSID.equals(courseGroupElement.getCourseType().getId())) {
            publicCourseGroup = courseGroupElement;
          } else {
            teachPlanCreditMap.remove(courseGroupElement.getCourseType().getId());
            if (courseGroupCredit < courseGroupRequireCredit) {
              addObserverResult(auditObserver, student, majorType, auditStandard.getId(), auditTermList,
                  false);
              return false;
            }
            if (auditStandard.isConvertable(courseGroupElement.getCourseType()))
              moreCreditToConvert += courseGroupCredit - courseGroupRequireCredit;
          }
        }
      }

      if (publicCourseGroup != null) {
        float courseGroupCredit = MapUtils.getFloatValue(teachPlanCreditMap,
            publicCourseGroup.getCourseType().getId());
        teachPlanCreditMap.remove(publicCourseGroup.getCourseType().getId());
        float courseGroupRequireCredit = publicCourseGroup.getCredit(auditTermList).floatValue();
        courseGroupCredit += moreCreditToConvert;
        if (courseGroupCredit < courseGroupRequireCredit) {
          addObserverResult(auditObserver, student, majorType, auditStandard.getId(), auditTermList, false);
          return false;
        }
      }
      if (!teachPlanCreditMap.isEmpty()) {
        for (Iterator iter = teachPlanCreditMap.keySet().iterator(); iter.hasNext();)
          teachPlanCredit += MapUtils.getFloatValue(teachPlanCreditMap, iter.next());

      }
      if (teachPlanCredit < teachPlanRequireCredit) {
        addObserverResult(auditObserver, student, majorType, auditStandard.getId(), auditTermList, false);
        return false;
      } else {
        return true;
      }
    }
    teachPlanRequireCredit = getTeachPlanRequireCredit(teachPlan, auditTermList);
    teachPlanCredit = 0.0F;
    for (Iterator iter = teachPlan.getCourseGroups().iterator(); iter.hasNext();) {
      CourseGroup courseGroupElement = (com.shufe.model.course.plan.CourseGroup) iter.next();
      float courseGroupCredit = MapUtils.getFloatValue(teachPlanCreditMap,
          courseGroupElement.getCourseType().getId());
      float courseGroupRequireCredit = courseGroupElement.getCredit(auditTermList).floatValue();
      if (auditStandard.isDisaudit(courseGroupElement.getCourseType())) {
        teachPlanRequireCredit -= courseGroupRequireCredit;
        teachPlanCreditMap.remove(courseGroupElement.getCourseType().getId());
      } else {
        teachPlanCredit += courseGroupCredit;
        teachPlanCreditMap.remove(courseGroupElement.getCourseType().getId());
        if (!auditStandard.isConvertable(courseGroupElement.getCourseType())
            && courseGroupCredit < courseGroupRequireCredit) {
          addObserverResult(auditObserver, student, majorType, auditStandard.getId(), auditTermList, false);
          return false;
        }
      }
    }

    if (!teachPlanCreditMap.isEmpty()) {
      for (Iterator iter = teachPlanCreditMap.keySet().iterator(); iter.hasNext();)
        teachPlanCredit += MapUtils.getFloatValue(teachPlanCreditMap, iter.next());

    }
    if (teachPlanCredit < teachPlanRequireCredit) {
      addObserverResult(auditObserver, student, majorType, auditStandard.getId(), auditTermList, false);
      return false;
    } else {
      return true;
    }
  }

  private float getTeachPlanRequireCredit(com.shufe.model.course.plan.TeachPlan teachPlan,
      List auditTermList) {
    float teachPlanRequireCredit = 0.0F;
    if (CollectionUtils.isEmpty(auditTermList)) {
      teachPlanRequireCredit = teachPlan.getCredit() != null ? teachPlan.getCredit().floatValue() : 0.0F;
    } else {
      for (Iterator iter = teachPlan.getCourseGroups().iterator(); iter.hasNext();) {
        CourseGroup element = (com.shufe.model.course.plan.CourseGroup) iter.next();
        teachPlanRequireCredit += element.getCredit(auditTermList).floatValue();
      }
    }
    return teachPlanRequireCredit;
  }

  private void addPublicCredit2Map(Map teachPlanCreditMap, Map courseGradeMap) {
    float courseGroupCredit = MapUtils.getFloatValue(teachPlanCreditMap, CourseType.PUBLIC_COURSID);
    for (Iterator iter = courseGradeMap.keySet().iterator(); iter.hasNext(); iter.remove()) {
      CourseGrade courseGrade = getFirstCG((List) courseGradeMap.get(iter.next()));
      courseGroupCredit += courseGrade.getCredit() != null ? courseGrade.getCredit().floatValue() : 0.0F;
    }
    teachPlanCreditMap.put(CourseType.PUBLIC_COURSID, new Float(courseGroupCredit));
  }

  private void addCGCredit2Map(Map teachPlanCreditMap, Map courseGradeMap, Set courseTypeSet,
      Set auditedTypes) {
    for (Iterator keySetIter = courseGradeMap.keySet().iterator(); keySetIter.hasNext();) {
      CourseGrade courseGrade = getFirstCG((List) courseGradeMap.get(keySetIter.next()));
      if (courseTypeSet.contains(courseGrade.getCourseType().getId())
          && !auditedTypes.contains(courseGrade.getCourseType().getId())
          && !Boolean.TRUE.equals(courseGrade.getCourseType().getIsCompulsory())) {
        float courseGroupCredit = MapUtils.getFloatValue(teachPlanCreditMap,
            courseGrade.getCourseType().getId());
        courseGroupCredit += courseGrade.getCredit() != null ? courseGrade.getCredit().floatValue() : 0.0F;
        teachPlanCreditMap.put(courseGrade.getCourseType().getId(), new Float(courseGroupCredit));
        keySetIter.remove();
      }
    }
  }

  private void addObserverResult(com.shufe.web.action.graduate.StudentAuditProcessObserver auditObserver,
      com.shufe.model.std.Student student, MajorType majorType, Long auditStandardId, List auditTermList,
      boolean flag) throws java.io.IOException {
    if (CollectionUtils.isNotEmpty(auditTermList))
      auditObserver.addStdOutputNotify(student.getCode(), student.getName());
    auditObserver.addResult(student.getCode(), flag, student.getId(), majorType, auditStandardId,
        getSeqTerms(auditTermList));
  }

  private com.shufe.web.action.graduate.StudentAuditProcessObserver getAuditObserver(
      com.shufe.service.OutputObserver observer) {
    com.shufe.web.action.graduate.StudentAuditProcessObserver auditObserver = null;
    if (observer instanceof com.shufe.web.action.graduate.StudentAuditProcessObserver)
      auditObserver = (com.shufe.web.action.graduate.StudentAuditProcessObserver) observer;
    else auditObserver = com.shufe.web.action.graduate.StudentAuditProcessObserver.NULL_OBSERVER;
    return auditObserver;
  }

  private boolean addNullTeachPlanResult(com.shufe.model.std.Student student, MajorType majorType,
      Long auditStandardId, List auditTermList,
      com.shufe.web.action.graduate.StudentAuditProcessObserver auditObserver) throws java.io.IOException {
    if (CollectionUtils.isNotEmpty(auditTermList))
      auditObserver.addStdOutputNotify(student.getCode(), student.getName());
    auditObserver.addNoTeachPlanError(student.getCode(), "\u8BE5\u751F\u57F9\u517B\u8BA1\u5212\u7F3A\u5931",
        student.getId());
    auditObserver.addResult(student.getCode(), false, student.getId(), majorType, auditStandardId,
        getSeqTerms(auditTermList));
    return false;
  }

  private String getSeqTerms(List auditTermList) {
    String terms = "";
    for (Iterator iter = auditTermList.iterator(); iter.hasNext();) {
      Integer element = (Integer) iter.next();
      if (iter.hasNext()) terms = terms + element.intValue() + ",";
      else terms = terms + element.intValue();
    }

    return terms;
  }

  protected boolean isSubstitutes(Map gradeMap,
      com.ekingstar.eams.teach.program.SubstituteCourse substitute) {
    float gpa1 = 0.0F;
    float credit1 = 0.0F;
    boolean fullGrade1 = true;
    for (Iterator it1 = substitute.getOrigins().iterator(); it1.hasNext();) {
      Course course = (Course) it1.next();
      List grades = (List) gradeMap.get(course.getId());
      if (grades != null && !grades.isEmpty()) {
        CourseGrade grade = (CourseGrade) grades.get(0);
        gpa1 += grade.getCredit().floatValue() * grade.getGP().floatValue();
        credit1 += grade.getCredit().floatValue();
      } else {
        fullGrade1 = false;
      }
    }

    float gpa2 = 0.0F;
    float credit2 = 0.0F;
    boolean fullGrade2 = true;
    for (Iterator it1 = substitute.getSubstitutes().iterator(); it1.hasNext();) {
      Course course = (Course) it1.next();
      List grades = (List) gradeMap.get(course.getId());
      if (grades != null && !grades.isEmpty()) {
        CourseGrade grade = (CourseGrade) grades.get(0);
        gpa2 += grade.getCredit().floatValue() * grade.getGP().floatValue();
        credit2 += grade.getCredit().floatValue();
      } else {
        fullGrade2 = false;
      }
    }

    if (credit1 == 0.0F && fullGrade2) return true;
    return (fullGrade1 && fullGrade2) && (credit1 > 0 && credit2 > 0) && (gpa1 / credit1 < gpa2 / credit2);
  }

  public void setStudentService(com.shufe.service.std.StudentService studentService) {
    this.studentService = studentService;
  }

  public void setTeachPlanService(com.shufe.service.course.plan.TeachPlanService teachPlanService) {
    this.teachPlanService = teachPlanService;
  }

  public com.shufe.service.graduate.AuditStandardService getAuditStandardService() {
    return auditStandardService;
  }

  public void setAuditStandardService(com.shufe.service.graduate.AuditStandardService auditStandardService) {
    this.auditStandardService = auditStandardService;
  }

  public void setGradeService(com.shufe.service.course.grade.GradeService gradeService) {
    this.gradeService = gradeService;
  }

  private com.shufe.service.course.grade.GradeService gradeService;
  private com.shufe.service.std.StudentService studentService;
  private com.shufe.service.course.plan.TeachPlanService teachPlanService;
  private com.shufe.service.graduate.AuditStandardService auditStandardService;
  protected com.ekingstar.eams.teach.program.service.SubstituteCourseService substituteCourseService;
}
