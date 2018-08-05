package com.shufe.service.workload.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilService;
import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.shufe.dao.system.calendar.TeachCalendarDAO;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.workload.course.TeachWorkload;
import com.shufe.model.workload.course.TeacherInfo;

/**
 * 因为没有主键，该类没有更新已有数据的功能，提交也是采用全部提交的手段。
 * @author chaostone
 * 
 */
public class TeachWorkloadImporterListener extends ItemImporterListener {

	private List list = new ArrayList();

	private UtilService utilService;

	private TeachCalendarDAO teachCalendarDAO;

	/**
	 * 缓存日历
	 */
	private Map calendarMap = new HashMap();

	public void endTransfer(TransferResult tr) {
		if (tr.hasErrors() == false) {
			utilService.saveOrUpdate(list);
		}
	}

	public void endTransferItem(TransferResult tr) {
		TeachWorkload workload = (TeachWorkload) importer.getCurrent();
		CourseCategory courseCategory = (CourseCategory) utilService.load(CourseCategory.class,
				CourseCategory.COMMON);

		Map dataMap = this.importer.curDataMap();
		// 学生类别代码
		if (checkEmpty(tr, workload.getStudentType(), "student type is not pointed.")) {
			tr.addFailure("student type is not found in DB.", dataMap.get("studentType.code"));
		}
		// 学年度与学期
		boolean existsCalendar = checkTeachCalendar(tr, workload, dataMap.get("teachCalendar.year")
				+ ", " + dataMap.get("teachCalendar.term"));
		// 教师职工号
		String teacherNo = workload.getTeacherInfo().getTeacher().getCode();
		if (checkEmpty(tr, teacherNo, "teacher-NO is not pointed.") == false) {
			Teacher teacher = (Teacher) getDBExistObject(tr, Teacher.class, "teacher",
					"teacher.code = (:NO)", teacherNo);
			if (teacher == null) {
				tr.addFailure("teacher is not found or not exist in DB.", teacherNo);
			} else {
				TeacherInfo info = new TeacherInfo();
				info.setTeacher(teacher);
				info.setTeacherName(teacher.getName());
				info.setTeacherTitle(teacher.getTitle());
				if (null != teacher.getDegreeInfo()) {
					info.setEduDegree(teacher.getDegreeInfo().getEduDegreeInside());
					info.setDegree(teacher.getDegreeInfo().getDegree());
				} else {
					info.setEduDegree(null);
					info.setDegree(null);
				}
				info.setTeacherType(teacher.getTeacherType());
				info.setGender(teacher.getGender());
				info.setTeacherAge(teacher.getAge());
				info.setTeachDepart((Department) teacher.getDepartment());
				info.setTitleLevel(teacher.getTitleLevel());
				workload.setTeacherInfo(info);
			}
		}
		// 课程类别代码
		try {
			if (checkEmpty(workload.getCourseType().getCode())) {
				workload.setCourseType(null);
			}
		} catch (Exception e) {
			tr.addFailure("No found course type in DB.", dataMap.get("courseType.code"));
		}
		// 课程种类
		try {
			if (checkEmpty(workload.getCourseCategory())
					|| checkEmpty(workload.getCourseCategory().getCode())) {
				workload.setCourseCategory(courseCategory);
			} else {
				checkEmpty(tr, workload.getCourseCategory().getCode(),
						"course category is not pointed.");
			}
		} catch (Exception e) {
			tr.addFailure("No found course category in DB.", dataMap.get("courseCategory.code"));
		}
		// 教学班名称
		checkEmpty(tr, workload.getClassNames(), "class name is not poined.");
		// 课程代码 && 课程名称
		if (!ValidEntityPredicate.INSTANCE.evaluate(workload.getCourse())) {
			workload.setCourse(null);
			if (StringUtils.isEmpty(workload.getCourseName())) {
				tr.addFailure("course is invalid", null);
			}
		}
		// 课程序号
		if (!existsCalendar) {
			tr.addFailure(
					"can't get the value of the teach-task " + "because calendar is invalid.", "");
		} else {
			String seqNo = workload.getCourseSeq();
			if (checkEmpty(seqNo) == false
					&& checkEmpty(tr, seqNo, "task NO can't empty.") == false) {
				TeachTask task = (TeachTask) getDBExistObject(tr, TeachTask.class, "task",
						"task.seqNo = (:no) and task.calendar.id = (:id)", seqNo, workload
								.getTeachCalendar().getId(), false);
				if (task != null) {
					workload.setTeachTask(task);
				} else {
					workload.setTeachTask(null);
				}
			} else {
				workload.setTeachTask(null);
			}
		}
		// 开课院系代码
		try {
			checkEmpty(tr, workload.getCollege().getCode(),
					"the code of department of college is not pointed.");
		} catch (Exception e) {
			tr.addFailure("No found the department of college", dataMap.get("college.code"));
		}

		// 上课人数
		checkEmpty(tr, workload.getStudentNumber(), "the number of students are not poined.",
				workload.getStudentNumber());
		// 工作量系数代码
		// checkEmpty(tr, workload.getTeachModulus().getModulusValue(),
		// "the code of modulus of workload is not pointed.");
		// 支付报酬
		checkEmpty(tr, workload.getPayReward(),
				"whether or not pay reward is empty value or invalid value.", dataMap
						.get("payReward"));
		// 计工作量
		checkEmpty(tr, workload.getCalcWorkload(),
				"whether or not calc-workload is empty value or invalid value.", dataMap
						.get("calcWorkload"));
		// 教师确认
		checkEmpty(tr, workload.getTeacherAffirm(),
				"whether or not need teacher-affirm is empty value or invalid value.", dataMap
						.get("teacherAffirm"));
		// 院系确认
		checkEmpty(tr, workload.getCollegeAffirm(),
				"whether or not need college-affirm is empty value or invalid value.", dataMap
						.get("collegeAffirm"));
		// 是否授课工作量
		checkEmpty(tr, workload.getIsTeachWorkload(),
				"whether or not need teach-workload is empty value or invalid value.", dataMap
						.get("isTeachWorkload"));
		// 上课周数
		checkEmpty(tr, workload.getWeeks(), "the number of weeks in class are not pointed.",
				dataMap.get("weeks"));
		// 总课时
		checkEmpty(tr, workload.getTotleCourses(), "total courses are not pointed.", dataMap
				.get("totleCourses"));
		// 周课时
		checkEmpty(tr, workload.getClassNumberOfWeek(),
				"the total number of weeks in class are not pointed.", dataMap
						.get("classNumberOfWeek"));
		// 授课工作量
		checkEmpty(tr, workload.getTotleWorkload(),
				"the total number of workloads are not pointed.", dataMap.get("totleWorkload"));
		// 备注
		/* 允许为空值 */

		// 如果没有错误，就把记录添加到列表中，准备保存
		if (tr.hasErrors() == false) {
			// 教学性质代码置空
			workload.setTeachCategory(null);
			// 授课工作量系数置空
			workload.setTeachModulus(null);
			list.add(workload);
		}
	}

	/**
	 * 两个条件查询获得对象
	 * 
	 * @param tr
	 * @param clazz
	 * @param field
	 * @param whereStr
	 * @param obj1
	 * @param obj2
	 * @param isOutputErrorMessages
	 * @return
	 */
	private Object getDBExistObject(TransferResult tr, Class clazz, String field, String whereStr,
			Object obj1, Object obj2, boolean isOutputErrorMessages) {
		EntityQuery query = new EntityQuery(clazz, field);
		query.add(new Condition(whereStr, obj1, obj2));
		return getObject(tr, obj1 + ", " + obj2, isOutputErrorMessages, query);
	}

	/**
	 * 查找输符合条件的记录
	 * 
	 * @param tr
	 * @param workload
	 * @param whereStr
	 * @param obj
	 * @return
	 */
	private Object getDBExistObject(TransferResult tr, Class clazz, String field, String whereStr,
			Object obj) {
		return getDBExistObject(tr, clazz, field, whereStr, obj, true);
	}

	/**
	 * 查找输符合条件的记录
	 * 
	 * @param isOutputErrorMessages
	 * @return
	 */
	private Object getDBExistObject(TransferResult tr, Class clazz, String field, String whereStr,
			Object obj, boolean isOutputErrorMessages) {
		EntityQuery query = new EntityQuery(clazz, field);
		query.add(new Condition(whereStr, obj));
		return getObject(tr, obj, isOutputErrorMessages, query);
	}

	/**
	 * 得到查询结果
	 * 
	 * @param tr
	 * @param obj
	 * @param isOutputErrorMessages
	 * @param query
	 * @return
	 */
	private Object getObject(TransferResult tr, Object obj, boolean isOutputErrorMessages,
			EntityQuery query) {
		List list = (List) utilService.search(query);
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() > 1 && isOutputErrorMessages) {
			tr.addFailure("不能得到唯一记录！", obj);
		}
		return null;
	}

	/**
	 * 验证对象或数值是否为空
	 * 
	 * @param tr
	 * @param data
	 * @param obj
	 * @param errorMessage
	 */
	private boolean checkEmpty(Object obj) {
		if (obj == null || (obj instanceof String) && StringUtils.isEmpty(((String) obj))) {
			return true;
		}
		return false;
	}

	/**
	 * 验证对象或数值是否为空，并把错误信息加入 tr 中
	 * 
	 * @param tr
	 * @param data
	 * @param obj
	 * @param errorMessageteachCalendar
	 */
	private boolean checkEmpty(TransferResult tr, Object obj, String errorMessage) {
		return checkEmpty(tr, obj, errorMessage, obj);
	}

	/**
	 * 验证对象或数值是否为空，并把错误信息加入 tr 中
	 * 
	 * @param tr
	 * @param data
	 * @param obj
	 * @param errorMessage
	 */
	private boolean checkEmpty(TransferResult tr, Object obj, String errorMessage, Object value) {
		if (checkEmpty(obj)) {
			tr.addFailure(errorMessage, value);
			return true;
		}
		return false;
	}

	/**
	 * 学年度、学期的处理
	 * 
	 * @param tr
	 * @param feeDetail
	 */
	private boolean checkTeachCalendar(TransferResult tr, TeachWorkload workload, Object value) {
		Long typeId = workload.getStudentType().getId();
		String year = workload.getTeachCalendar().getYear();
		if (checkEmpty(tr, year, "the academic year is empty.")) {
			return false;
		}
		String term = workload.getTeachCalendar().getTerm();
		if (checkEmpty(tr, term, "the academic term is empty.")) {
			return false;
		}

		String calendarMapId = typeId + "_" + year + "_" + term;

		TeachCalendar calendar = (TeachCalendar) calendarMap.get(calendarMapId);
		if (null == calendar) {
			calendar = teachCalendarDAO.getTeachCalendar(typeId, year, term);
			if (null == calendar) {
				tr.addFailure("invalid calender", value);
				return false;
			} else {
				calendarMap.put(calendarMapId, calendar);
			}
		}
		if (null == calendar) {
			tr.addFailure("invalid calender", value);
			return false;
		} else {
			workload.setTeachCalendar(calendar);
			return true;
		}
	}

	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

	public void setTeachCalendarDAO(TeachCalendarDAO teachCalendarDAO) {
		this.teachCalendarDAO = teachCalendarDAO;
	}
}
