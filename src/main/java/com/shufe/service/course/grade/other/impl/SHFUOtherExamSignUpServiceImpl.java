//$Id: OtherExamSignUpServiceImpl.java,v 1.1 2007-2-27 下午07:46:56 chaostone Exp $
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
 *chaostone      2007-2-27         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.grade.other.impl;

import java.util.Date;
import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.course.grade.other.OtherExamSignUp;
import com.shufe.model.course.grade.other.OtherExamSignUpSetting;
import com.shufe.model.course.grade.other.OtherGrade;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.grade.other.OtherExamSignUpService;
import com.shufe.service.course.grade.other.OtherGradeService;

/**
 * 其他考试报名服务实现
 * 
 * @author chaostone
 * 
 */
public class SHFUOtherExamSignUpServiceImpl extends BasicService implements OtherExamSignUpService {

	private OtherGradeService otherGradeService;

	/**
	 * 校外考试报名
	 */
	public String signUp(final Student std, final TeachCalendar calendar,
			final OtherExamSignUpSetting setting) {
		// 判断时间
		if (Boolean.FALSE.equals(setting.getIsOpen()) || !setting.inSignUpTime())
			return notInTime;
		// 前提考试类别
		boolean passSuperCategory = true;
		if (null != setting.getSuperCategory()) {
			if (!setting.getFreeStdTypes().contains(std.getType())) {
				OtherGrade grade = otherGradeService.getBestGrade(std, setting.getSuperCategory());
				if (null == grade || !Boolean.TRUE.equals(grade.getIsPass())) {
					passSuperCategory = false;
				}
			}
		}
		if (!passSuperCategory)
			return notPassSuperCategory;

		// 除四六级英语考试以外，已经通过的不能再报名了
		if ((setting.getExamCategory().getId().intValue() != 3) && (setting.getExamCategory().getId().intValue() != 2)) {
			OtherGrade grade = otherGradeService.getBestGrade(std, setting.getExamCategory());
			if (null != grade && Boolean.TRUE.equals(grade.getIsPass())) {
				return hasPassed;
			}
		}
		// 重复报名
		List existed = getOtherExamSignUps(std, setting);
		if (!existed.isEmpty()) {
			return existExamSignUp;
		}
		OtherExamSignUp signUp = new OtherExamSignUp();
		signUp.setStd(std);
		signUp.setCategory(setting.getExamCategory());
		signUp.setSignUpAt(new Date());
		signUp.setCalendar(calendar);
		utilDao.saveOrUpdate(signUp);
		return "";
	}

	public String cancelSignUp(Student std, OtherExamSignUpSetting setting) {
		if (Boolean.FALSE.equals(setting.getIsOpen()) || !setting.inSignUpTime())
			return notInTime;
		OtherExamSignUp signUp = getOtherExamSignUp(std, setting);

		if (null != signUp) {
			utilDao.remove(signUp);
		}
		return "";
	}

	public OtherExamSignUp getOtherExamSignUp(Student std, OtherExamSignUpSetting setting) {
		EntityQuery query = new EntityQuery(OtherExamSignUp.class, "signUp");
		query.add(new Condition("signUp.std.id=" + std.getId()));
		query.add(new Condition("signUp.signUpAt between :start and :end", setting.getStartAt(),
				setting.getEndAt()));
		query.add(new Condition("signUp.category.id=" + setting.getExamCategory().getId()));
		List existSignUps = (List) utilService.search(query);
		if (!existSignUps.isEmpty()) {
			return (OtherExamSignUp) existSignUps.get(0);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.service.course.grade.other.OtherExamSignUpService#getOtherExamSignUps(com.shufe.model.std.Student,
	 *      java.util.Date, java.util.Date)
	 */
	public List getOtherExamSignUps(Student std, Date start, Date end) {
		EntityQuery query = new EntityQuery(OtherExamSignUp.class, "signUp");
		query.add(new Condition("signUp.std.id=" + std.getId()));
		query.add(new Condition("signUp.signUpAt between :start and :end", start, end));
		return (List) utilService.search(query);
	}
    
    public List getOtherExamSignUps(Student std, OtherExamSignUpSetting setting) {
        EntityQuery query = new EntityQuery(OtherExamSignUp.class, "signUp");
        query.add(new Condition("signUp.std.id=" + std.getId()));
        query.add(new Condition("signUp.signUpAt between :start and :end", setting.getStartAt(),
                setting.getEndAt()));
        query.add(new Condition("signUp.category.id=" + setting.getExamCategory().getId()));
        return (List) utilService.search(query);
    }

	public List getOpenedSettings() {
		EntityQuery query = new EntityQuery(OtherExamSignUpSetting.class, "setting");
		query.add(new Condition("setting.isOpen=true"));
		query.add(new Condition(":now  between setting.startAt and setting.endAt", new Date()));
		return (List) utilService.search(query);
	}

	public void setOtherGradeService(OtherGradeService otherGradeService) {
		this.otherGradeService = otherGradeService;
	}

}
