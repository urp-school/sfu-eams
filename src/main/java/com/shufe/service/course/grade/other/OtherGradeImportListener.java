//$Id: OtherGradeImportListener.java,v 1.1 2007-3-19 下午12:36:00 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-19         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.grade.other;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.ekingstar.eams.course.grade.converter.ConverterFactory;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.shufe.dao.system.calendar.TeachCalendarDAO;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.grade.other.OtherGrade;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 成绩导入监听器,实现全部数据导入的完整性。<br>
 * 依照学生、学期和考试类型作为唯一标识
 * 
 * @author chaostone
 * 
 */
public class OtherGradeImportListener extends ItemImporterListener {

	private UtilDao utilDao;

	private TeachCalendarDAO teachCalendarDAO;

	/**
	 * 缓存记录方式
	 */
	private Map markStyles = new HashMap();

	/**
	 * 缓存日历
	 */
	private Map calendarMap = new HashMap();

	public OtherGradeImportListener() {
		super();
	}

	public OtherGradeImportListener(UtilDao utilDao, TeachCalendarDAO teachCalendarDAO) {
		super();
		this.utilDao = utilDao;
		this.teachCalendarDAO = teachCalendarDAO;
	}

	public void startTransferItem(TransferResult tr) {
		int scoreIndex = 0;
		int markStyleIndex = 0;
		String[] titles = importer.getAttrs();
		for (int i = 0; i < titles.length; i++) {
			if (titles[i].equals("score")) {
				scoreIndex = i;
			}
			if (titles[i].equals("markStyle.code")) {
				markStyleIndex = i;
			}
		}
		Object[] values = (Object[]) importer.getCurData();
		String markStyleCode = String.valueOf(values[markStyleIndex]);

		MarkStyle markStyle = (MarkStyle) markStyles.get(markStyleCode);
		if (null == markStyle) {
			List d = utilDao.load(MarkStyle.class, "code", new Object[] { markStyleCode });
			if (d.size() != 1) {
				tr.addFailure("error.parameters.illegal", markStyleCode);
			} else {
				markStyle = (MarkStyle) d.get(0);
				markStyles.put(markStyleCode, markStyle);
			}
		}
		if (null != markStyle) {
			values[scoreIndex] = ConverterFactory.getConverter().convert(
					values[scoreIndex].toString(), markStyle);
		}
	}

	public void endTransferItem(TransferResult tr) {
		OtherGrade otherGrade = (OtherGrade) importer.getCurrent();
		int errors = tr.errors();
		if (null == otherGrade.getStd()) {
			tr.addFailure("error.parameters.illegal", importer.curDataMap().get("std.code"));
		} else {
			String calendarMapId = otherGrade.getStd().getType().getId() + "_"
					+ otherGrade.getCalendar().getYear() + "_" + otherGrade.getCalendar().getTerm();

			TeachCalendar calendar = (TeachCalendar) calendarMap.get(calendarMapId);
			if (null == calendar) {
				calendar = teachCalendarDAO.getTeachCalendar(otherGrade.getStd().getType().getId(),
						otherGrade.getCalendar().getYear(), otherGrade.getCalendar().getTerm());
				if (null == calendar) {
					tr.addFailure("error.parameters.illegal", importer.curDataMap().get(
							"calendar.year")
							+ " " + importer.curDataMap().get("calendar.term"));
				} else {
					calendarMap.put(calendarMapId, calendar);
				}
			}
			if (null != calendar) {
				otherGrade.setCalendar(calendar);
			}
		}
		if (tr.errors() == errors) {
			if (null == otherGrade.getStatus()) {
				otherGrade.setStatus(new Integer(Grade.PUBLISHED));
			}
			if (null == otherGrade.getIsPass()) {
				otherGrade.setIsPass(Boolean.TRUE);
			}
			// 如果出现已有的则进行更新
			try {
				utilDao.saveOrUpdate(otherGrade);
			} catch (DataIntegrityViolationException e) {
				EntityQuery query = new EntityQuery(OtherGrade.class, "grade");
				query.add(new Condition("grade.std.id=:stdId", otherGrade.getStd().getId()));
				query.add(new Condition(
						"grade.calendar.id=:calendarId and grade.category.id=:categoryId",
						otherGrade.getCalendar().getId(), otherGrade.getCategory().getId()));
				List grades = (List) utilDao.search(query);
				for (Iterator iterator = grades.iterator(); iterator.hasNext();) {
					OtherGrade exist = (OtherGrade) iterator.next();
					exist.setScore(otherGrade.getScore());
					exist.setIsPass(otherGrade.getIsPass());
					exist.setMarkStyle(otherGrade.getMarkStyle());
					utilDao.saveOrUpdate(exist);
					break;
				}
			}
		}
	}

	public void setUtilDao(UtilDao utilDao) {
		this.utilDao = utilDao;
	}

	public void setTeachCalendarDAO(TeachCalendarDAO teachCalendarDAO) {
		this.teachCalendarDAO = teachCalendarDAO;
	}
}
