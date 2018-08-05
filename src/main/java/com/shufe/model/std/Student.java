//$Id: Student.java,v 1.26 2007/01/22 06:26:08 yd Exp $
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
 * chaostone             2005-9-4         Created
 *  
 ********************************************************************************/

package com.shufe.model.std;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.ekingstar.eams.system.basecode.industry.TrainingType;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.basecode.state.LanguageAbility;
import com.ekingstar.eams.system.baseinfo.Major;
import com.ekingstar.eams.system.baseinfo.MajorField;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.std.degree.DegreeInfo;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.model.system.baseinfo.Teacher;

/**
 * 基本学生学籍信息类
 * 
 * @author dell
 */
public class Student extends LongIdObject implements Cloneable, com.ekingstar.eams.std.info.Student {

	private static final long serialVersionUID = -4689485984947287543L;

	/** 非空属性列表 */
	public static List notNullPropertyList = new ArrayList();
	static {
		notNullPropertyList.add("code");
		notNullPropertyList.add("name");
		notNullPropertyList.add("createAt");
		notNullPropertyList.add("modifyAt");
		notNullPropertyList.add("enrollYear");
		notNullPropertyList.add("type");
		notNullPropertyList.add("department");
		notNullPropertyList.add("studentStatusInfo");
		notNullPropertyList.add("basicInfo");
		Collections.unmodifiableList(notNullPropertyList);
	}

	/** 学号 */
	protected String code;

	/** 姓名 */
	protected String name;

	/** 英文名 */
	protected String engName;

	/** 制定时间 */
	protected Date createAt;

	/** 修改时间 */
	protected Date modifyAt;

	/** 备注 */
	protected String remark;

	/** 入校年月 */
	private String enrollYear;

	/** 学生类别 */
	private StudentType type;

	/** 院系 */
	private Department department = new Department();

	/** 行政管理院系 */
	private Department managementDepart;

	/** 专业 */
	private Speciality firstMajor = new Speciality();

	/** 专业方向 */
	private SpecialityAspect firstAspect = new SpecialityAspect();

	/** 校区 */
	private SchoolDistrict schoolDistrict;

	/** 学制 */
	private String schoolingLength;

	/** 语种熟练程度（语言能力） */
	private LanguageAbility languageAbility;

	/** 班级列表 */
	private Set adminClasses;

	/** 对应导师 */
	private Teacher teacher = new Teacher();

	/** 二专业导师,临时用的 */
	private Teacher tutor = new Teacher();

	// －－－－－－－双专业信息－－－－－
	/** 第二专业代码 */
	private Speciality secondMajor = new Speciality();

	/** 第二专业方向代码 */
	private SpecialityAspect secondAspect = new SpecialityAspect();

	/** 第二专业是否就读 */
	private Boolean isSecondMajorStudy;

	/** 第二专业是否写论文 */
	private Boolean isSecondMajorThesisNeed;

	// －－－－－其他信息分类－－－－－－
	/** 学生基本信息 */
	private BasicInfo basicInfo = new BasicInfo();

	/** 学籍状态信息 */
	private StudentStatusInfo studentStatusInfo = new StudentStatusInfo();

	/** 留学生信息 */
	private AbroadStudentInfo abroadStudentInfo = new AbroadStudentInfo();

	/** 学位信息 */
	private DegreeInfo degreeInfo;

	/** 毕业审核结果 */
	private Set auditResults;

	/** 应毕业时间 */
	private Date graduateOn;

	/** 学籍状态 */
	private StudentState state;

	/** 是否在校 */
	private boolean inSchool = true;
	/** 是否在籍 */
	private boolean active = true;

	public Student(Long id) {
		super(id);
	}

	public Student() {
		super();
		adminClasses = new HashSet();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public Date getModifyAt() {
		return modifyAt;
	}

	public void setModifyAt(Date modifyAt) {
		this.modifyAt = modifyAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Gender getGender() {
		return getBasicInfo().getGender();
	}

	public Major getMajor() {
		return firstMajor;
	}

	public Set getAdminClasses() {
		return adminClasses;
	}

	public void setAdminClasses(Set adminClasses) {
		this.adminClasses = adminClasses;
	}

	public int hashCode() {
		return new HashCodeBuilder(928297193, -478905963).append(this.id).toHashCode();
	}

	public boolean equals(Object object) {
		if (!(object instanceof Student)) {
			return false;
		}
		Student rhs = (Student) object;
		return new EqualsBuilder().append(this.getId(), rhs.getId()).isEquals();
	}

	public BasicInfo getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(BasicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getEnrollYear() {
		return enrollYear;
	}

	public String getGrade() {
		return enrollYear;
	}

	public void setEnrollYear(String enrollYear) {
		this.enrollYear = enrollYear;
	}

	public SpecialityAspect getFirstAspect() {
		return firstAspect;
	}

	public void setFirstAspect(SpecialityAspect firstAspect) {
		this.firstAspect = firstAspect;
	}

	public Speciality getFirstMajor() {
		return firstMajor;
	}

	public void setFirstMajor(Speciality firstMajor) {
		this.firstMajor = firstMajor;
	}

	public SpecialityAspect getSecondAspect() {
		return secondAspect;
	}

	public void setSecondAspect(SpecialityAspect secondAspect) {
		this.secondAspect = secondAspect;
	}

	public Speciality getSecondMajor() {
		return secondMajor;
	}

	public void setSecondMajor(Speciality secondMajor) {
		this.secondMajor = secondMajor;
	}

	public StudentStatusInfo getStudentStatusInfo() {
		return studentStatusInfo;
	}

	public void setStudentStatusInfo(StudentStatusInfo studentStatusInfo) {
		this.studentStatusInfo = studentStatusInfo;
	}

	public StudentType getType() {
		return type;
	}

	public void setType(StudentType type) {
		this.type = type;
	}


	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getSchoolingLength() {
		return schoolingLength;
	}

	public void setSchoolingLength(String schoolingLength) {
		this.schoolingLength = schoolingLength;
	}

	public AbroadStudentInfo getAbroadStudentInfo() {
		return abroadStudentInfo;
	}

	public void setAbroadStudentInfo(AbroadStudentInfo abroadStudentInfo) {
		this.abroadStudentInfo = abroadStudentInfo;
	}

	public void setIsSecondMajorStudy(Boolean isSecondMajorStudy) {
		this.isSecondMajorStudy = isSecondMajorStudy;
	}

	public Boolean getIsSecondMajorThesisNeed() {
		return isSecondMajorThesisNeed;
	}

	public void setIsSecondMajorThesisNeed(Boolean isSecondMajorThesisNeed) {
		this.isSecondMajorThesisNeed = isSecondMajorThesisNeed;
	}

	public LanguageAbility getLanguageAbility() {
		return languageAbility;
	}

	public void setLanguageAbility(LanguageAbility languageAbility) {
		this.languageAbility = languageAbility;
	}

	/**
	 * 查看是否是双专业学生
	 * 
	 * @return
	 */
	public boolean isSecondMajorStd() {
		return (null != getSecondMajor());
	}

	/**
	 * 查看学籍是否有效
	 * 
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * 查看学籍状态是否在校
	 * 
	 * @return
	 */
	public boolean isInSchool() {
		return inSchool;
	}

	public Boolean getIsInSchool() {
		return new Boolean(inSchool);
	}

	public Boolean getIsValid() {
		return new Boolean(active);
	}

	public StudentState getState() {
		return state;
	}

	public void setState(StudentState state) {
		this.state = state;
	}

	/**
	 * 第一专业班级
	 * 
	 * @add by duantihua
	 * @return
	 */
	public AdminClass getFirstMajorClass() {
		return getMajorClass(new MajorType(MajorType.FIRST));
	}

	/**
	 * 第二专业班级
	 * 
	 * @add by duantihua
	 * @return
	 */
	public AdminClass getSecondMajorClass() {
		return getMajorClass(new MajorType(MajorType.SECOND));
	}

	/**
	 * @author 鄂州蚊子
	 * @param majorType
	 * @see MajorType.FIRST
	 * @return
	 */
	public AdminClass getMajorClass(MajorType majorType) {
		if (null == majorType) {
			return null;
		}
		Long majorTypeId = majorType.getId();
		if (null == majorTypeId) {
			majorTypeId = MajorType.FIRST;
		}
		if (null != getAdminClasses()) {
			if (getAdminClasses().isEmpty())
				return null;
			else {
				for (Iterator iter = getAdminClasses().iterator(); iter.hasNext();) {
					AdminClass adminClass = (AdminClass) iter.next();
					if (null != adminClass.getSpeciality()
							&& adminClass.getSpeciality().getMajorType().getId()
									.equals(majorTypeId)) {
						return adminClass;
					}
				}
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 获取班级列表
	 * 
	 * @param is2ndMajor
	 * @return
	 */
	public Set getAdminClassSet(final Boolean is2ndMajor) {
		if (is2ndMajor == null) {
			return this.getAdminClasses();
		} else {
			if (getAdminClasses() == null) {
				return getAdminClasses();
			}
			return new HashSet(CollectionUtils.select(getAdminClasses(), new Predicate() {
				public boolean evaluate(Object object) {
					AdminClass adminClass = (AdminClass) object;
					if (adminClass.getSpeciality() == null) {
						return false;
					} else {
						if (Boolean.TRUE.equals(is2ndMajor)) {
							return adminClass.getSpeciality().getMajorType().getId().equals(
									MajorType.SECOND);
						} else {
							return adminClass.getSpeciality().getMajorType().getId().equals(
									MajorType.FIRST);
						}
					}
				}
			}));
		}
	}

	/**
	 * 获取双专业是否就读
	 * 
	 * @return
	 */
	public Boolean getIsSecondMajorStudy() {
		return isSecondMajorStudy;
	}

	public Set getAuditResults() {
		return auditResults;
	}

	public void setAuditResults(Set auditResults) {
		this.auditResults = auditResults;
	}

	public DegreeInfo getDegreeInfo() {
		return degreeInfo;
	}

	public void setDegreeInfo(DegreeInfo degreeInfo) {
		this.degreeInfo = degreeInfo;
	}

	public Date getGraduateOn() {
		return graduateOn;
	}

	public void setGraduateOn(Date graduateOn) {
		this.graduateOn = graduateOn;
	}

	public Teacher getTutor() {
		return tutor;
	}

	public void setTutor(Teacher tutor) {
		this.tutor = tutor;
	}

	public SchoolDistrict getSchoolDistrict() {
		return schoolDistrict;
	}

	public void setSchoolDistrict(SchoolDistrict schoolDistrict) {
		this.schoolDistrict = schoolDistrict;
	}

	public Department getManagementDepart() {
		return managementDepart;
	}

	public void setManagementDepart(Department managementDepart) {
		this.managementDepart = managementDepart;
	}

	/**
	 * 招生季节
	 * 
	 * @return
	 */
	public String getSeasonOfEnrollYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M");
		try {
			java.util.Date date = sdf.parse(this.enrollYear);
			return (date.getMonth() + 1) < 7 ? "春季" : "秋季";
		} catch (ParseException e) {
			return "";
		}
	}

	public MajorField getMajorField() {
		return null;
	}

	public com.ekingstar.eams.system.baseinfo.StudentType getStdType() {
		return type;
	}

	public TrainingType getTrainingType() {
		return null;
	}

	public void setInSchool(boolean inSchool) {
		this.inSchool = inSchool;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
