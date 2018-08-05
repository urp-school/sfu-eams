//$Id: TutorServiceImpl.java,v 1.2 2007/01/19 09:34:31 cwx Exp $
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
 * hc             2005-11-24         Created
 *  
 ********************************************************************************/

package com.shufe.service.degree.tutorManager.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.eams.system.basecode.BaseCode;
import com.ekingstar.eams.system.basecode.industry.TeacherType;
import com.ekingstar.eams.system.basecode.industry.TutorType;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.shufe.dao.degree.tutorManager.TutorDAO;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.baseinfo.Tutor;
import com.shufe.service.BasicService;
import com.shufe.service.degree.tutorManager.TutorService;
import com.shufe.service.util.stat.FloatSegment;
import com.shufe.service.util.stat.StatUtils;

public class TutorServiceImpl extends BasicService implements TutorService {
	private TutorDAO tutorDAO;

	/**
	 * 得到所有的导师
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param Tutor
	 * @return
	 */
	public Pagination getAllTutor(Tutor tutor, String departIdSeq,
			int pageNo, int pageSize) {
		return tutorDAO.getTutorPagi(tutor, departIdSeq, pageNo, pageSize);
	}

	/**
	 * @param tutorDAO
	 *            The tutorDAO to set.
	 */
	public void setTutorDAO(TutorDAO tutorDAO) {
		this.tutorDAO = tutorDAO;
	}

	public void batchModifyTeacherOfStd(String studentIds, Long tutorId) {
		List studentList = utilService.load(Student.class, "id",
				SeqStringUtil.transformToLong(studentIds));
		for (Iterator iter = studentList.iterator(); iter.hasNext();) {
			Student element = (Student) iter.next();
			if (null == tutorId) {
				element.setTeacher(null);
			} else {
				Teacher teacher = new Teacher(tutorId);
				element.setTeacher(teacher);
			}
		}
		utilService.saveOrUpdate(studentList);
	}

	public void changeTutor(Long teacherId,boolean updateToTuto) {
        Map params=new HashMap();
        params.put("teacherId", teacherId);
        if(updateToTuto){
            utilDao.executeUpdateNamedQuery("convertToTutor", params);
        }else{
            utilDao.executeUpdateNamedQuery("convertToTeacher", params);
        }
		//tutorDAO.executeProc(teacherId);
	}

	/**
	 * 根据导师对应的条件得到 导师列表
	 * 
	 * @see com.shufe.service.degree.tutorManager.TutorService#getTutorList(com.shufe.model.system.baseinfo.Tutor,
	 *      java.lang.String)
	 */
	public List getTutorList(Tutor tutor, String departIdSeq) {
		return tutorDAO.getTutorList(tutor, departIdSeq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.service.degree.tutorManager.TutorService#getPropertyOfTutor(com.shufe.model.system.baseinfo.Tutor,
	 *      java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	public List getPropertyOfTutor(Tutor tutor, String departIdSeq,
			String propertys, Boolean isCount) {
		return tutorDAO.getPropertyOfTutor(tutor, departIdSeq, propertys,
				isCount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.service.degree.tutorManager.TutorService#getStatisticMap(com.shufe.model.system.baseinfo.Tutor,
	 *      java.lang.String, java.lang.String)
	 */
	public Map getStatisticMap(Tutor tutor, String departIdSeq, String propertys) {
		Map returnMap = new HashMap();
		if (StringUtils.split(propertys, ",").length != 2) {
			return returnMap;
		}
		List results = tutorDAO.getPropertyOfTutor(tutor, departIdSeq,
				propertys, Boolean.TRUE);
		for (Iterator iter = results.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			Department department = (Department) element[0];
			BaseCode baseCode = (BaseCode) element[1];
			String keyValue = "";
			if (null == department) {
				if (null == baseCode) {
					keyValue = "null-null";
				} else {
					keyValue = "null-" + baseCode.getId();
				}
			} else {
				if (null == baseCode) {
					keyValue = department.getId() + "-null";
				} else {
					keyValue = department.getId() + "-" + baseCode.getId();
				}
			}
			StatUtils.setValueToMap(keyValue, element[2], "integer", returnMap);
			StatUtils.setValueToMap(null == baseCode ? "0-null" : "0-"
					+ baseCode.getId(), element[2], "integer", returnMap);
			StatUtils.setValueToMap(null == department ? "null-0" : department
					.getId()
					+ "-0", element[2], "integer", returnMap);
			StatUtils.setValueToMap("0-0", element[2], "integer", returnMap);
		}
		return returnMap;
	}

	/**
	 * @see com.shufe.service.degree.tutorManager.TutorService#getSpecialityTutorMap(com.shufe.model.system.baseinfo.Tutor,
	 *      java.lang.String)
	 */
	public Map getSpecialityTutorMap(Tutor tutor, String departIdSeq) {
		Map returnMap = new HashMap();
		List tutorList = tutorDAO.getTutorList(tutor, departIdSeq);
		for (Iterator iter = tutorList.iterator(); iter.hasNext();) {
			Tutor element = (Tutor) iter.next();
			Speciality speciality = element.getSpeciality();
			String keyValue = null == speciality ? "null" : speciality.getId()
					.toString();
			if (null != element.getTeacherType()) {
				if (TeacherType.REGISTERTYPEID.equals(element.getTeacherType()
						.getId())) {
					StatUtils.setValueToMap(keyValue + "ZS", element, "list",
							returnMap);
				} else if (TeacherType.TEMPTYPEID.equals(element.getTeacherType()
						.getId())) {
					StatUtils.setValueToMap(keyValue + "JZ", element, "list",
							returnMap);
				} else if (TeacherType.BACKTYPEID.equals(element.getTeacherType()
						.getId())) {
					StatUtils.setValueToMap(keyValue + "FP", element, "list",
							returnMap);
				}
			}
		}
		return returnMap;
	}

	/**
	 * 导师类型的统计
	 * 
	 * @see com.shufe.service.degree.tutorManager.TutorService#getTutorTypeMap(com.shufe.model.system.baseinfo.Tutor,
	 *      java.lang.String, java.util.List)
	 */
	public Map getTutorTypeMap(Tutor tutor, String departIdSeq,
			List operatorList) {
		Map returnMap = new HashMap();
		List tutorList = tutorDAO.getTutorList(tutor, departIdSeq);
		for (Iterator iter = tutorList.iterator(); iter.hasNext();) {
			Tutor element = (Tutor) iter.next();
			Gender gender = element.getGender();
			TutorType tutorType = element.getTutorType();
			Calendar teacherBirthday = Calendar.getInstance();
			if (null != element.getBirthday()) {
				teacherBirthday.setTime(element.getBirthday());
			}
			int teacherAge = Calendar.getInstance().get(Calendar.YEAR)
					- teacherBirthday.get(Calendar.YEAR);
			FloatSegment scoreSegment = getSegment(teacherAge, operatorList);
			String keyValue = "";
			String scoreMin = "-" + String.valueOf((int) scoreSegment.getMin());
			if (null == gender) {
				if (null == tutorType) {
					keyValue = "null-null" + scoreMin;
				} else {
					keyValue = "null-" + tutorType.getId() + scoreMin;
				}
			} else {
				if (null == tutorType) {
					keyValue = gender.getId() + "-null" + scoreMin;
				} else {
					keyValue = gender.getId() + "-" + tutorType.getId()
							+ scoreMin;
				}
			}
			StatUtils.setValueToMap(keyValue, new Integer(1), "integer",
					returnMap);
			if (null != tutorType) {
				StatUtils.setValueToMap(null == gender ? "null-0" + scoreMin
						: "null-" + tutorType.getId() + scoreMin,
						new Integer(1), "integer", returnMap);
			}
			if (null != gender) {
				StatUtils.setValueToMap(null == tutorType ? "0-null" + scoreMin
						: gender.getId() + "-0" + scoreMin, new Integer(1),
						"integer", returnMap);
			}
			StatUtils.setValueToMap("0-0" + scoreMin, new Integer(1),
					"integer", returnMap);
		}
		return returnMap;
	}

	/**
	 * 过滤得到对应的数目
	 * 
	 * @param age
	 * @param segmengList
	 * @return
	 */
	public FloatSegment getSegment(int age, List segmengList) {
		FloatSegment segment = new FloatSegment();
		for (Iterator iter = segmengList.iterator(); iter.hasNext();) {
			FloatSegment element = (FloatSegment) iter.next();
			if (age < element.getMax() && age >= element.getMin()) {
				segment = element;
				break;
			}
		}
		return segment;
	}

}
