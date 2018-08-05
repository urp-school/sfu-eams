package com.shufe.service.std.graduation.practice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.PracticeSource;
import com.shufe.dao.system.calendar.TeachCalendarDAO;
import com.shufe.model.std.graduation.practice.GraduatePractice;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 毕业实习
 * 
 * @author chaostone
 * 
 */
public class GraduatePracticeImportListener extends ItemImporterListener {

	private UtilDao utilDao;

	private TeachCalendarDAO teachCalendarDAO;

	/**
	 * 缓存日历
	 */
	private Map calendarMap = new HashMap();

	private List pratices = new ArrayList();

	/** 查询指导教师 */
	private EntityQuery teacherQuery;
	private Condition nameCondition = new Condition("teacher.name =:name");

	// 默认
	public GraduatePracticeImportListener() {
		super();
	}

	public GraduatePracticeImportListener(UtilDao utilDao,
			TeachCalendarDAO teachCalendarDAO) {
		super();
		this.utilDao = utilDao;
		this.teachCalendarDAO = teachCalendarDAO;
		this.teacherQuery = new EntityQuery(Teacher.class, "teacher");
		teacherQuery.add(nameCondition);
	}

	public void startTransferItem(TransferResult tr) {
		Object[] data = (Object[]) importer.getCurData();
		if (StringUtils.isNotBlank(String.valueOf(data[0]))) {
			EntityQuery entityQuery = new EntityQuery(GraduatePractice.class,
					"graduatePractice");
			entityQuery.add(Condition.eq("graduatePractice.student.code",
					String.valueOf(data[0])));
			Collection practices = utilDao.search(entityQuery);
			if (practices.size() > 0) {
				importer.setCurrent(practices.iterator().next());
			}
		}
	}

	/**
	 * @see com.ekingstar.commons.transfer.importer.ItemImporterListener#endTransferItem(com.ekingstar.commons.transfer.TransferResult)
	 */
	public void endTransferItem(TransferResult tr) {
		GraduatePractice graduatePractice = (GraduatePractice) importer
				.getCurrent();
		int errors = tr.errors();
		if (!ValidEntityPredicate.INSTANCE.evaluate(graduatePractice
				.getStudent())) {
			tr.addFailure("error.parameters.illegal", importer.curDataMap()
					.get("std.code"));
		} else {
			// 查找教学日历
			String calendarMapId = graduatePractice.getStudent().getType()
					.getId()
					+ "_"
					+ graduatePractice.getTeachCalendar().getYear()
					+ "_"
					+ graduatePractice.getTeachCalendar().getTerm();

			TeachCalendar calendar = (TeachCalendar) calendarMap
					.get(calendarMapId);
			if (null == calendar) {
				calendar = teachCalendarDAO.getTeachCalendar(graduatePractice
						.getStudent().getType().getId(), graduatePractice
						.getTeachCalendar().getYear(), graduatePractice
						.getTeachCalendar().getTerm());
				if (null == calendar) {
					tr.addFailure("error.parameters.illegal", importer
							.curDataMap().get("calendar.year")
							+ " " + importer.curDataMap().get("calendar.term"));
				} else {
					calendarMap.put(calendarMapId, calendar);
				}
			}
			if (null != calendar) {
				graduatePractice.setTeachCalendar(calendar);
			}

			if (null == graduatePractice.getMajorType()) {
				graduatePractice.setMajorType(new MajorType(MajorType.FIRST));
			}
			// 尝试这加载教师
			if (null != graduatePractice.getPracticeTeacher()) {
				if (!ValidEntityPredicate.INSTANCE.evaluate(graduatePractice
						.getPracticeTeacher())) {
					if (StringUtils.isNotEmpty(graduatePractice
							.getPracticeTeacher().getName())) {
						nameCondition
								.setValues(Collections
										.singletonList(graduatePractice
												.getPracticeTeacher().getName()
												.trim()));
						List teachers = (List) utilDao.search(teacherQuery);
						if (teachers.size() == 1) {
							graduatePractice
									.setPracticeTeacher((Teacher) teachers
											.get(0));

							// 如果学生的导师也没有，则更新学生的导师信息
							Teacher stdTeacher = null;
							if (MajorType.FIRST.equals(graduatePractice
									.getMajorType().getId())) {
								stdTeacher = graduatePractice.getStudent()
										.getTeacher();
							} else {
								stdTeacher = graduatePractice.getStudent()
										.getTutor();
							}
							if (null == stdTeacher) {
								if (MajorType.FIRST.equals(graduatePractice
										.getMajorType().getId())) {
									graduatePractice.getStudent().setTeacher(
											graduatePractice
													.getPracticeTeacher());
								} else {
									graduatePractice.getStudent().setTutor(
											graduatePractice
													.getPracticeTeacher());
								}
								utilDao.saveOrUpdate(graduatePractice
										.getStudent());
							}
						}
					}
					if (!ValidEntityPredicate.INSTANCE
							.evaluate(graduatePractice.getPracticeTeacher())) {
						graduatePractice.setPracticeTeacher(null);
					}
				}
			} else {
				// 否则按照学生的指导教师作为这次实习的教师
				Teacher teacher = graduatePractice.getStudent().getTeacher();
				if (!MajorType.FIRST.equals(graduatePractice.getMajorType()
						.getId())) {
					teacher = graduatePractice.getStudent().getTutor();
				}
				if (null != teacher) {
					graduatePractice.setPracticeTeacher(teacher);
				}
			}
		}

		if (tr.errors() == errors) {
			graduatePractice.setPracticeTeacher(graduatePractice.getStudent()
					.getTeacher());
			if (null == graduatePractice.getPracticeSource()) {
				graduatePractice.setPracticeSource(new PracticeSource(
						PracticeSource.SELF_DECIDE));
			}
			if (null == graduatePractice.getIsPractictBase()) {
				graduatePractice.setIsPractictBase(Boolean.FALSE);
			}
			if (pratices.size() >= 500) {
				utilDao.saveOrUpdate(pratices);
				pratices.clear();
			}
			pratices.add(graduatePractice);
		}
	}

	/**
	 * @see com.ekingstar.commons.transfer.importer.ItemImporterListener#endTransfer(com.ekingstar.commons.transfer.TransferResult)
	 */
	public void endTransfer(TransferResult tr) {
		if (!pratices.isEmpty()) {
			utilDao.saveOrUpdate(pratices);
		}
		super.endTransfer(tr);
	}

	/**
	 * @param utilDao
	 *            The utilDao to set.
	 */
	public void setUtilDao(UtilDao utilDao) {
		this.utilDao = utilDao;
	}

}
