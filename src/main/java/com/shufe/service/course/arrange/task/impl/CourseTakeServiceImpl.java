//$Id: CourseTakeServiceImpl.java,v 1.21 2007/01/17 06:18:11 duanth Exp $
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
 * hc             2005-12-17         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.ekingstar.commons.collection.transformers.PropertyTransformer;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.security.User;
import com.shufe.dao.course.arrange.resource.TeachResourceDAO;
import com.shufe.dao.course.arrange.task.CourseTakeDAO;
import com.shufe.dao.course.election.CreditConstraintDAO;
import com.shufe.dao.course.election.ElectRecordDAO;
import com.shufe.dao.course.grade.GradeDAO;
import com.shufe.dao.system.calendar.TeachCalendarDAO;
import com.shufe.model.course.arrange.resource.TimeTable;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.election.ElectRecord;
import com.shufe.model.course.election.ReservedStudent;
import com.shufe.model.course.election.StdCreditConstraint;
import com.shufe.model.course.election.WithdrawRecord;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.message.Message;
import com.shufe.model.system.message.StudentMessage;
import com.shufe.service.BasicService;
import com.shufe.service.OutputObserver;
import com.shufe.service.course.arrange.task.CourseTakeService;
import com.shufe.service.course.election.CourseTakeInitMessage;
import com.shufe.service.course.election.ElectCourseService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.OutputProcessObserver;

public class CourseTakeServiceImpl extends BasicService implements CourseTakeService {
    
	protected CourseTakeDAO courseTakeDAO;
    
    private GradeDAO gradeDAO;
    
    private CreditConstraintDAO creditConstraintDAO;
    
    private TeachCalendarDAO teachCalendarDAO;
    
    protected ElectRecordDAO electRecordDAO;
    
    private TeachTaskService teachTaskService;
    
    private TeachResourceDAO teachResourceDAO;
    
    /**
     * @param courseTakeDAO
     *            The courseTakeDAO to set.
     */
    public void setCourseTakeDAO(CourseTakeDAO courseTakeDAO) {
        this.courseTakeDAO = courseTakeDAO;
    }
    
    public CourseTake getCourseTask(Long teachTaskId, Long stdId) {
        return courseTakeDAO.getCourseTask(teachTaskId, stdId);
    }
    
    public Float statCreditFor(Student std, List calendars) {
        return courseTakeDAO.statCreditFor(std, calendars);
    }
    
    public List getCourseTakes(Student std) {
        return courseTakeDAO.getCourseTakes(std);
    }
    
    /**
     * 查询学生在指定时间内的冲突名单
     * 
     * @param stdId
     * @param timeUnit
     * @param calendar
     *            可以为null
     * @return
     */
    public List getCourseTakes(Long stdId, TimeUnit timeUnit, TeachCalendar calendar) {
        if (null != stdId)
            return courseTakeDAO.getCourseTakes(stdId, timeUnit, calendar);
        else
            return Collections.EMPTY_LIST;
    }
    
    /**
     * * 加课<br>
     * 1)新增courseTake<br>
     * 2)更新教学任务所在学期的courseTake中学生的已选学分<br>
     * 3)更新教学任务的实际人数<br>
     * 4)自动判断修读类别<br>
     * 5)将最后一次选课状态更新为"选中" 6)不允许超过学分上限 7)语言等级,HSK等级判断
     */
    public String add(TeachTask task, Student std, boolean checkConstraint) {
        List calendars = teachCalendarDAO.getTeachCalendarsOfOverlapped(task.getCalendar());
        calendars.add(task.getCalendar());
        if (checkConstraint) {
            // 检查语言等级
            if (null != task.getCourse().getLanguageAbility()) {
                if (!task.getCourse().getLanguageAbility().sameLevel(std.getLanguageAbility())) {
                    return ElectCourseService.languageAbilityNotSatisfied;
                }
            }
            // 检查HSK等级
            if (null != task.getElectInfo().getHSKDegree() && null != std.getAbroadStudentInfo()
                    && null != std.getAbroadStudentInfo().getHSKDegree()) {
                if (std.getAbroadStudentInfo().getHSKDegree().getDegree().intValue() < task
                        .getElectInfo().getHSKDegree().getDegree().intValue())
                    return ElectCourseService.HSKNotSatisfied;
            }
            // 判断选修课程的人数上限
            if (Boolean.TRUE.equals(task.getElectInfo().getIsElectable())) {
                if (task.getElectInfo().getMaxStdCount().intValue() < task.getTeachClass()
                        .getStdCount().intValue() + 1) {
                    return ElectCourseService.overMaxStdCount;
                }
            }
        }
        // 检查先修课程
        Map courseMap = gradeDAO.getGradeCourseMap(std.getId());
        if (checkConstraint) {
            if (null != task.getElectInfo().getPrerequisteCourses()) {
                for (Iterator iter = task.getElectInfo().getPrerequisteCourses().iterator(); iter
                        .hasNext();) {
                    Course course = (Course) iter.next();
                    Boolean prePass = (Boolean) courseMap.get(course.getId());
                    if (!Boolean.TRUE.equals(prePass)) {
                        return ElectCourseService.prerequisteCoursesNotMeeted;
                    }
                }
            }
        }
        // 更新相关学期的学分(实际上应该只有一个)
        List constraints = creditConstraintDAO.getStdCreditConstraints(std, calendars);
        // 检查个人学分上限
        for (Iterator iterator = constraints.iterator(); iterator.hasNext();) {
            StdCreditConstraint constraint = (StdCreditConstraint) iterator.next();
            if (!constraint.checkElectable(task.getCourse().getCredits())) {
                return ElectCourseService.overCeilCreditConstraint;
            }
        }
        // 判断重复选课
        List tasks = teachTaskService.getTeachTasksOfStd(std.getId(), calendars);
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask one = (TeachTask) iter.next();
            if (one.getCourse().getId().equals(task.getCourse().getId())) {
                return ElectCourseService.elected;
            }
        }
        
        // 判断时间冲突
        TimeTable timeTable = new TimeTable(tasks);
        boolean collision = timeTable.isTimeConflict(task);
        if (checkConstraint && collision) {
            return ElectCourseService.timeCollision;
        }
        
        // 生成选课记录
        CourseTake take = new CourseTake();
        take.setStudent(std);
        // take.setCourseType(task.getCourseType());
        take.setIsCourseEvaluated(Boolean.FALSE);
        // take.setCredit(task.getCourse().getCredits());
        take.setCourseTakeType(new CourseTakeType());
        // 检查是否修读过这门课程
        Boolean pass = (Boolean) courseMap.get(task.getCourse().getId());
        if (null != pass) {
            if (checkConstraint && Boolean.TRUE.equals(pass)) {
                return ElectCourseService.reStudyPassedCourseNotAllowed;
            } else {
                take.getCourseTakeType().setId(
                        collision ? CourseTakeType.REEXAM : CourseTakeType.RESTUDY);
            }
        } else {
            take.getCourseTakeType().setId(CourseTakeType.COMPULSORY);
        }
        for (Iterator iterator = constraints.iterator(); iterator.hasNext();) {
            StdCreditConstraint constraint = (StdCreditConstraint) iterator.next();
            constraint.addElectedCredit(task.getCourse().getCredits());
        }
        task.getTeachClass().addCourseTake(take);
        task.getTeachClass().setStdCount(new Integer(task.getTeachClass().getCourseTakes().size()));
        
        List rs = new ArrayList();
        // 将选课记录中的选中置为"是"
        ElectRecord record = electRecordDAO.getLatestElectRecord(take.getTask(), take.getStudent()
                .getId());
        if (null != record) {
            record.setIsPitchOn(Boolean.TRUE);
            rs.add(record);
        }
        // 将学分添加到保存列表中
        rs.addAll(constraints);
        rs.add(take);
        rs.add(task);
        utilService.saveOrUpdate(rs);
        return ElectCourseService.selectSuccess;
    }
    
    /**
     * 退课<br>
     * 1)删除courseTake<br>
     * 2)更新教学任务所在学期的courseTake中学生的已选学分<br>
     * 3)更新教学任务的实际人数<br>
     * 4)*更新选中状态<br>
     * 5)*新增退课记录<br>
     */
    public void withdraw(List courseTakes, User withdrawOperator) {
        for (Iterator iter = courseTakes.iterator(); iter.hasNext();) {
            CourseTake take = (CourseTake) iter.next();
            TeachTask task = take.getTask();
            List rs = new ArrayList();
            
            List calendars = teachCalendarDAO.getTeachCalendarsOfOverlapped(take.getTask()
                    .getCalendar());
            calendars.add(take.getTask().getCalendar());
            // 实际上只有一个学分上限
            List constraints = creditConstraintDAO.getStdCreditConstraints(take.getStudent(),
                    calendars);
            for (Iterator iterator = constraints.iterator(); iterator.hasNext();) {
                StdCreditConstraint constraint = (StdCreditConstraint) iterator.next();
                constraint.removeElectedCredit(take.getTask().getCourse().getCredits());
            }
            // 将学分添加到保存列表中
            rs.addAll(constraints);
            // 如果有退课人,则记录该退课记录
            if (null != withdrawOperator) {
                rs.add(new WithdrawRecord(take, withdrawOperator.getName()));
            }
            // 将选课记录中的选中置为"否"
            ElectRecord record = electRecordDAO.getLatestElectRecord(take.getTask(), take
                    .getStudent().getId());
            if (null != record) {
                record.setIsPitchOn(Boolean.FALSE);
                rs.add(record);
            }
            // 退课(删除考试安排)
            // utilService.remove(take.getExamTakes().values());
            task.getTeachClass().removeCourseTake(take);
            utilService.remove(take);
            task.getTeachClass().setStdCount(
                    new Integer(task.getTeachClass().getCourseTakes().size()));
            rs.add(task);
            utilService.saveOrUpdate(rs);
        }
    }
    
    /**
     * 指定学生
     */
    public void assignStds(Collection tasks, Boolean deleteExisted, OutputProcessObserver observer) {
        try {
            observer.notifyStart(observer.messageOf("info.setCourseTake") + "(" + tasks.size()
                    + ")", tasks.size(), null);
            for (Iterator iter = tasks.iterator(); iter.hasNext();) {
                TeachTask task = (TeachTask) iter.next();
                try {
                    if (Boolean.TRUE.equals(task.getTeachClass().isStdAssigned())
                            && Boolean.FALSE.equals(deleteExisted)) {
                        if (null != observer)
                            observer.outputNotify(OutputObserver.warnning,
                                    new CourseTakeInitMessage("error.courseTakeForTask.existed",
                                            task), true);
                    } else {
                        int count = courseTakeDAO.assignStds(task);
                        if (null != observer)
                            observer.outputNotify(OutputObserver.good, new CourseTakeInitMessage(
                                    "info.init.courseTake.for", task, String.valueOf(count)), true);
                    }
                } catch (Exception e) {
                    try {
                        if (null != observer)
                            observer.outputNotify(OutputObserver.error, new CourseTakeInitMessage(
                                    "error.init.courseTake.for", task), true);
                    } catch (Exception e1) {
                        // ignore io exception
                        info("IOException occurred when credit init scheme id:" + task.toString()
                                + "\n and exception stack is " + ExceptionUtils.getStackTrace(e));
                    }
                }
            }
            observer.notifyFinish();
        } catch (Exception e) {
            info("Failure to CourseTake Init " + ExceptionUtils.getStackTrace(e));
        }
    }
    
    /**
     * @see CourseTakeService#filter(List, Integer)
     */
    public void old_filter(List tasks, Integer turn) {
        // 过滤人数上限大于实际人数的任务
        CollectionUtils.filter(tasks, new Predicate() {
            
            public boolean evaluate(Object object) {
                TeachTask task = (TeachTask) object;
                return task.getTeachClass().getStdCount().intValue() > task.getElectInfo()
                        .getMaxStdCount().intValue();
            }
        });
        if (tasks.isEmpty())
            return;
        Map turnMap = electRecordDAO.getLastElectTurn(tasks);
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            // 查找选课记录
            Set electStdIds = null;
            if (null != turn) {
                electStdIds = electRecordDAO.getElectStdIdSet(task, turn, Boolean.TRUE);
            } else {
                Integer myTurn = (Integer) turnMap.get(task.getId());
                if (null == myTurn)
                    continue;
                else {
                    electStdIds = electRecordDAO.getElectStdIdSet(task, myTurn, Boolean.TRUE);
                }
            }
            // 虽然超过上限,但是该轮次中没有选中的学生.
            if (electStdIds.isEmpty())
                continue;
            // 上课记录
            List courseTakes = new ArrayList(task.getTeachClass().getCourseTakes());
            // 随机化
            Collections.shuffle(courseTakes);
            List removedTakes = new ArrayList();
            List unPitchOnStdIds = new ArrayList();
            // 确定要筛选掉的总人数
            int filterCount = courseTakes.size() - task.getElectInfo().getMaxStdCount().intValue();
            
            for (Iterator iterator = courseTakes.iterator(); iterator.hasNext();) {
                CourseTake take = (CourseTake) iterator.next();
                if (filterCount <= 0)
                    break;
                // 指定的学生是不被筛选掉的
                if (!take.getCourseTakeType().getId().equals(CourseTakeType.COMPULSORY)) {
                    if (electStdIds.contains(take.getStudent().getId())) {
                        removedTakes.add(take);
                        unPitchOnStdIds.add(take.getStudent().getId());
                        filterCount--;
                    }
                }
            }
            // 从教学班中删除学生,更新教学班的上课人数
            task.getTeachClass().getCourseTakes().removeAll(removedTakes);
            task.getTeachClass().calcStdCount();
            utilDao.saveOrUpdate(task);
            // 实际删除上课记录
            courseTakeDAO.batchRemove(task, unPitchOnStdIds);
            // 更新选中状态
            electRecordDAO.updatePitchOn(task, unPitchOnStdIds, Boolean.FALSE);
        }
    }
    
    public void filter(List tasks, Integer turn) {
		// 过滤人数上限大于实际人数的任务
		CollectionUtils.filter(tasks, new Predicate() {
			public boolean evaluate(Object object) {
				TeachTask task = (TeachTask) object;
				return task.getTeachClass().getStdCount().intValue() > task.getElectInfo()
						.getMaxStdCount().intValue();
			}
		});
		if (tasks.isEmpty())
			return;
		Map turnMap = electRecordDAO.getLastElectTurn(tasks);

		EntityQuery reservedQuery = new EntityQuery(ReservedStudent.class, "rs");
		reservedQuery.add(new Condition("rs.calendar=:calendar", ((TeachTask) tasks.get(0))
				.getCalendar()));
		List reservedStudents = (List) utilService.search(reservedQuery);

		// 针对每一个任务进行筛选
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			TeachTask task = (TeachTask) iter.next();
			// 查找选课记录
			Set electStdIds = null;
			if (null != turn) {
				electStdIds = electRecordDAO.getElectStdIdSet(task, turn, Boolean.TRUE);
			} else {
				Integer myTurn = (Integer) turnMap.get(task.getId());
				if (null == myTurn)
					continue;
				else {
					electStdIds = electRecordDAO.getElectStdIdSet(task, myTurn, Boolean.TRUE);
				}
			}
			// 虽然超过上限,但是该轮次中没有选中的学生.
			if (electStdIds.isEmpty())
				continue;
			// 总共筛选掉人数
			int filterCount = task.getTeachClass().getCourseTakes().size()
					- task.getElectInfo().getMaxStdCount().intValue();
			if (filterCount <= 0)
				continue;
			// 筛选目标
			List targets = new ArrayList();
			for (Iterator iterator = task.getTeachClass().getCourseTakes().iterator(); iterator
					.hasNext();) {
				CourseTake take = (CourseTake) iterator.next();
				// 指定的学生是不被筛选掉的
				if (!take.getCourseTakeType().getId().equals(CourseTakeType.COMPULSORY)) {
					if (electStdIds.contains(take.getStudent().getId())) {
						targets.add(take);
					}
				}
			}
			// 没有可以删除的上课记录
			if (targets.isEmpty()) {
				continue;
			}
			// 保留部分
			List reservedTakes = filterReserved(targets, reservedStudents);
			targets.removeAll(reservedTakes);
			// 随机化
			Collections.shuffle(targets);
			Collections.shuffle(reservedTakes);
			targets.addAll(reservedTakes);

			List removedTakes = new ArrayList();
			List unPitchOnStdIds = new ArrayList();
			// 开始删除
			for (Iterator iterator = targets.iterator(); iterator.hasNext();) {
				CourseTake take = (CourseTake) iterator.next();
				if (filterCount <= 0)
					break;
				removedTakes.add(take);
				unPitchOnStdIds.add(take.getStudent().getId());
				filterCount--;
			}
			// 从教学班中删除学生,更新教学班的上课人数
			task.getTeachClass().getCourseTakes().removeAll(removedTakes);
			task.getTeachClass().calcStdCount();
			utilDao.saveOrUpdate(task);
			// 实际删除上课记录
			courseTakeDAO.batchRemove(task, unPitchOnStdIds);
			// 更新选中状态
			electRecordDAO.updatePitchOn(task, unPitchOnStdIds, Boolean.FALSE);
		}
	}

	private List filterReserved(List targets, List reservedStudents) {
		List reservedTakes = new ArrayList();
		if (reservedStudents.isEmpty())
			return reservedTakes;
		for (Iterator iter = targets.iterator(); iter.hasNext();) {
			CourseTake take = (CourseTake) iter.next();
			for (Iterator iter2 = reservedStudents.iterator(); iter2.hasNext();) {
				ReservedStudent rs = (ReservedStudent) iter2.next();
				if (rs.contains(take.getStudent())) {
					reservedTakes.add(take);
					break;
				}
			}
		}
		return reservedTakes;
	}

    public Collection collisionTakes(TeachTask task, Collection activities) {
        // 如果有学生,则检测班级正常学生的时间冲突
        if (!task.getTeachClass().getStds().isEmpty()) {
            Collection stdIds = CollectionUtils.collect(
                    task.getTeachClass().getNormalCourseTakes(), new PropertyTransformer(
                            "student.id"));
            if (!stdIds.isEmpty()) {
                List collisionTakes = new ArrayList();
                for (Iterator iter = activities.iterator(); iter.hasNext();) {
                    CourseActivity courseActivity = (CourseActivity) iter.next();
                    if (teachResourceDAO.isStdsOccupied(courseActivity.getTime(), stdIds, task)) {
                        // 检测每一个学生的上课记录,并在结果中过滤掉当前任务
                        for (Iterator iterator = stdIds.iterator(); iterator.hasNext();) {
                            Long stdId = (Long) iterator.next();
                            List rs = getCourseTakes(stdId, courseActivity.getTime(), task
                                    .getCalendar());
                            for (Iterator takeIter = rs.iterator(); takeIter.hasNext();) {
                                CourseTake take = (CourseTake) takeIter.next();
                                if (!take.getTask().getId().equals(task.getId())
                                        && CourseTakeType.NORMAL_TYPES.contains(take
                                                .getCourseTakeType().getId())) {
                                    collisionTakes.add(take);
                                }
                            }
                        }
                    }
                }
                return collisionTakes;
            }
        }
        return Collections.EMPTY_LIST;
        
    }
    
    /**
     * 对被退课的学生发送通知
     * 
     * @param courseTakes
     * @param sender
     */
    public void sendWithdrawMessage(List courseTakes, User sender) {
        Message message = Message.getDefaultMessage(sender);
        String messageTitle = "";
        String messageBody = "";
        for (Iterator it = courseTakes.iterator(); it.hasNext();) {
            CourseTake take = (CourseTake) it.next();
            if (StringUtils.isEmpty(messageBody)) {
                messageTitle = "课程" + take.getTask().getCourse().getCode() + "已被退课";
                message.setTitle(messageTitle);
                messageBody = "序号为" + take.getTask().getSeqNo() + "的"
                        + take.getTask().getCourse().getName() + "("
                        + take.getTask().getCourse().getCode() + ")，已被退课。";
                message.setBody(messageBody);
            }
            StudentMessage studentMessage = new StudentMessage();
            Student std = take.getStudent();
            studentMessage.setMessage(message);
            studentMessage.setStatus(StudentMessage.newly);
            studentMessage.setRecipient((User) utilDao.load(User.class, "name",
                    new Object[] { std.getCode() }).get(0));
            message.getReceiptors().add(studentMessage);
        }
        utilService.saveOrUpdate(message);
    }
    
    public void sendFilterMessage(Map taskStudentMap, User sender) {
        List messages = new ArrayList();
        for (Iterator it = taskStudentMap.keySet().iterator(); it.hasNext();) {
            TeachTask task = (TeachTask) it.next();
            Message message = Message.getDefaultMessage(sender);
            String messageTitle = "课程" + task.getCourse().getCode() + "已被筛选掉";
            message.setTitle(messageTitle);
            String messageBody = "序号为" + task.getSeqNo() + "的" + task.getCourse().getName() + "("
                    + task.getCourse().getCode() + ")已被随机筛选掉。";
            message.setBody(messageBody);
            
            for (Iterator it2 = ((Collection) taskStudentMap.get(task)).iterator(); it2.hasNext();) {
                Student std = (Student) it2.next();
                StudentMessage studentMessage = new StudentMessage();
                studentMessage.setMessage(message);
                studentMessage.setStatus(StudentMessage.newly);
                studentMessage.setRecipient((User) utilDao.load(User.class, "name",
                        new Object[] { std.getCode() }).get(0));
                message.getReceiptors().add(studentMessage);
            }
            messages.add(message);
        }
        utilService.saveOrUpdate(messages);
    }
    
    public List getNotStdList(HttpServletRequest request,Long calendarId){
        EntityQuery entityQuery = new EntityQuery(CourseTake.class,"courseTake");
        entityQuery.add(new Condition("courseTake.task.calendar.id="+calendarId));
        entityQuery.add(new Condition("courseTake.isCourseEvaluated=0"));
        entityQuery.setSelect("select distinct(courseTake.student)");
        entityQuery.addOrder(OrderUtils.parser(RequestUtils.get(request, "orderBy")));
        List stdList = (List) utilService.search(entityQuery);
        return stdList;
    }
    
    public List getTakedTasks(Student std, Collection calendars) {
        return courseTakeDAO.getTakedTasks(std, calendars);
    }
    
    public void setCreditConstraintDAO(CreditConstraintDAO creditConstraintDAO) {
        this.creditConstraintDAO = creditConstraintDAO;
    }
    
    public void setTeachCalendarDAO(TeachCalendarDAO teachCalendarDAO) {
        this.teachCalendarDAO = teachCalendarDAO;
    }
    
    public void setGradeDAO(GradeDAO gradeDAO) {
        this.gradeDAO = gradeDAO;
    }
    
    public void setElectRecordDAO(ElectRecordDAO electRecordDAO) {
        this.electRecordDAO = electRecordDAO;
    }
    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    public void setTeachResourceDAO(TeachResourceDAO teachResourceDAO) {
        this.teachResourceDAO = teachResourceDAO;
    }
    
}
