package com.shufe.web.action.std;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.collection.predicates.NotZeroNumberPredicate;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.EducationMode;
import com.ekingstar.eams.system.basecode.industry.EnrollMode;
import com.ekingstar.eams.system.basecode.industry.FeeOrigin;
import com.ekingstar.eams.system.basecode.industry.HSKDegree;
import com.ekingstar.eams.system.basecode.industry.LeaveSchoolCause;
import com.ekingstar.eams.system.basecode.industry.MaritalStatus;
import com.ekingstar.eams.system.basecode.industry.PassportType;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.ekingstar.eams.system.basecode.industry.VisaType;
import com.ekingstar.eams.system.basecode.state.Country;
import com.ekingstar.eams.system.basecode.state.EduDegree;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.basecode.state.LanguageAbility;
import com.ekingstar.eams.system.basecode.state.Nation;
import com.ekingstar.eams.system.basecode.state.PoliticVisage;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.security.User;
import com.shufe.model.std.AbroadStudentInfo;
import com.shufe.model.std.BasicInfo;
import com.shufe.model.std.Student;
import com.shufe.model.std.StudentStatusInfo;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.service.std.StudentService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.DispatchBasicAction;

public class StudentOperation extends DispatchBasicAction {
    
    private StudentService studentService;
    
    /**
     * 新增学籍信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return studentAddForm.ftl
     */
    public ActionForward loadAddForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        this.initForm();
        request.setAttribute("user", getUser(request.getSession()));
        Results.addObject("student", null);
        return this.forward(mapping, request, "addForm");
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Student student = (Student) RequestUtil.populate(request, Student.class, "student");
        StudentStatusInfo statusInfo = (StudentStatusInfo) RequestUtil.populate(request,
                StudentStatusInfo.class, "statusInfo");
        AbroadStudentInfo abroadStudentInfo = (AbroadStudentInfo) RequestUtil.populate(request,
                AbroadStudentInfo.class, "abroadStudentInfo");
        BasicInfo basicInfo = (BasicInfo) RequestUtil.populate(request, BasicInfo.class,
                "basicInfo");
        
        if (utilService.exist(Student.class, "code", student.getCode())) {
            return forward(mapping, request, "student.exist.error", FAILURE);
        }
        
        Student saved = studentService.saveStudent(student, statusInfo, basicInfo,
                abroadStudentInfo);
        studentService.updateStudentAdminClass(saved.getId(), SeqStringUtil.transformToLong(request
                .getParameter("adminClassIds")), new Boolean(false));
        Action action = new Action("studentManager", "manager");
        return this.redirect(request, action, "info.save.success", request.getParameter("params"));
    }
    
    /**
     * 修改学籍信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return loadStudentUpdateForm.ftl
     */
    public ActionForward loadUpdateForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        this.initForm();
        Results.addObject("student", utilService.load(Student.class, getLong(request, "stdId")));
        request.setAttribute("user", getUser(request.getSession()));
        return this.forward(mapping, request, "updateForm");
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Student student = (Student) populateEntity(request, Student.class, "student");
        StudentStatusInfo statusInfo = (StudentStatusInfo) populateEntity(request,
                StudentStatusInfo.class, "statusInfo");
        AbroadStudentInfo abroadStudentInfo = (AbroadStudentInfo) populateEntity(request,
                AbroadStudentInfo.class, "abroadStudentInfo");
        BasicInfo basicInfo = (BasicInfo) populateEntity(request, BasicInfo.class, "basicInfo");
        
        studentService.updateStudent(student, statusInfo, basicInfo, abroadStudentInfo);
        studentService.updateStudentAdminClass(student.getId(), SeqStringUtil.transformToLong(get(
                request, "adminClassIds")), new Boolean(false));
        return this.redirect(request, new Action("studentManager", "manager"), "info.save.success",
                request.getParameter("params"));
    }
    
    /**
     * 管理人员查看学籍信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        request.setAttribute("std", utilService.load(Student.class, getLong(request, "stdId")));
        return this.forward(mapping, request, "detail");
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long[] stdIds = SeqStringUtil.transformToLong(get(request, "stdIds"));
        try {
            utilService.remove(utilService.load(Student.class, "id", stdIds));
            return redirect(request, new Action("studentManager", "manager"), "info.action.success");
        } catch (Exception e) {
            return redirect(request, new Action("studentManager", "manager"),
                    "error.remove.beenUsed");
        }
    }
    
    public ActionForward editCode(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String from = get(request, "from");
        String to = get(request, "to");
        if (StringUtils.isEmpty(from) || StringUtils.isEmpty(to) || ObjectUtils.equals(from, to)) {
            return forward(request, new Action("studentManager", "manager"));
        }
        Student std = studentService.getStd(from);
        std.setCode(to);
        utilService.saveOrUpdate(std);
        User user = (User) utilService.load(User.class, "name", from);
        user.setName(to);
        utilService.saveOrUpdate(user);
        return redirect(request, new Action("studentManager", "manager"), "info.action.success");
    }
    
    /**
     * 学生查看自己学籍信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward studentDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String code = getLoginName(request);
        
        if (StringUtils.isEmpty(code)) {
            this.forward(mapping, request, "student.notExist.error", FAILURE);
        }
        if (!utilService.exist(Student.class, "code", code)) {
            this.forward(mapping, request, "student.notExist.error", FAILURE);
        }
        
        Results.addObject("student", studentService.getStudent(code));
        return this.forward(mapping, request, "studentDetail");
    }
    
    /**
     * 学生修改个人基本信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward basicInfoUpdateForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        /* 性别 */
        Results.addObject("genderList", utilService.loadAll(Gender.class));
        /* 政治面貌 */
        Results.addObject("politicVisageList", utilService.loadAll(PoliticVisage.class));
        /* 民族 */
        Results.addObject("nationList", utilService.loadAll(Nation.class));
        /* 国籍 */
        Results.addObject("countryList", utilService.loadAll(Country.class));
        /* 婚姻状况 */
        Results.addObject("maritalStatusList", utilService.loadAll(MaritalStatus.class));
        Results.addObject("student", utilService.load(Student.class, getLong(request, "stdId")));
        return this.forward(mapping, request, "basicInfoUpdateForm");
    }
    
    /**
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward updateStudentBasicInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        BasicInfo basicInfo = (BasicInfo) RequestUtil.populate(request, BasicInfo.class,
                "basicInfo");
        BasicInfo basicInfoDest = (BasicInfo) utilService.load(BasicInfo.class, basicInfo.getId());
        EntityUtils.merge(basicInfoDest, basicInfo);
        utilService.saveOrUpdate(basicInfoDest);
        return mapping.findForward(SUCCESS);
    }
    
    /**
     * 变更学生专业
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward studentSpecialityUpdateForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long[] stdIds = SeqStringUtil.transformToLong(request.getParameter("stdIds"));
        List studentList = utilService.load(Student.class, "id", stdIds);
        Results.addObject("studentList", studentList);
        return this.forward(mapping, request, "updateForm");
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward updateStudentSpeciality(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long[] stdIds = SeqStringUtil.transformToLong(request.getParameter("stdIds"));
        List studentList = utilService.load(Student.class, "id", stdIds);
        Long adminClassId = getLong(request, "adminClasssId1");
        if (new NotZeroNumberPredicate().evaluate(adminClassId)) {
            for (Iterator iter = studentList.iterator(); iter.hasNext();) {
                Student student = (Student) iter.next();
                Set adminClassSet = student.getAdminClasses();
                for (Iterator adminClassSetIter = adminClassSet.iterator(); adminClassSetIter
                        .hasNext();) {
                    AdminClass adminClassElement = (AdminClass) adminClassSetIter.next();
                    if (adminClassElement.getDepartment().equals(student.getDepartment())
                            && adminClassElement.getSpeciality().equals(student.getFirstMajor())) {
                        adminClassSet.remove(adminClassElement);
                        if (student.isInSchool()) {
                            adminClassElement.setActualStdCount(adminClassElement
                                    .getActualStdCount() == null ? new Integer(0) : new Integer(
                                    adminClassElement.getActualStdCount().intValue() - 1));
                        }
                        if (student.isActive()) {
                            adminClassElement
                                    .setStdCount(adminClassElement.getStdCount() == null ? new Integer(
                                            0)
                                            : new Integer(adminClassElement.getStdCount()
                                                    .intValue() - 1));
                        }
                        // adminClassElement.setStdCount(adminClassElement.getStdCount()==null?new
                        // Integer(0):new
                        // Integer(adminClassElement.getStdCount().intValue()-1));
                        utilService.saveOrUpdate(adminClassElement);
                        break;
                    }
                }
                student.setDepartment(new Department(getLong(request, "departmentId")));
                student.setFirstMajor(new Speciality(getLong(request, "specialityId")));
                if (getLong(request, "specialityAspectId") == null) {
                    student.setFirstAspect(null);
                } else if (new NotZeroNumberPredicate().evaluate(getLong(request,
                        "specialityAspectId"))) {
                    student.setFirstAspect(new SpecialityAspect(getLong(request,
                            "specialityAspectId")));
                }
                AdminClass adminClass = (AdminClass) utilService.load(AdminClass.class,
                        adminClassId);
                if (student.isInSchool()) {
                    adminClass
                            .setActualStdCount(adminClass.getActualStdCount() == null ? new Integer(
                                    0)
                                    : new Integer(adminClass.getActualStdCount().intValue() + 1));
                }
                if (student.isActive()) {
                    adminClass.setStdCount(adminClass.getStdCount() == null ? new Integer(0)
                            : new Integer(adminClass.getStdCount().intValue() + 1));
                }
                adminClass.setStdCount(adminClass.getStdCount() == null ? new Integer(1)
                        : new Integer(adminClass.getStdCount().intValue() + 1));
                adminClassSet.add(adminClass);
                student.setAdminClasses(adminClassSet);
                utilService.saveOrUpdate(student);
                // int p=9/0;
                utilService.saveOrUpdate(adminClass);
            }
        } else {
            for (Iterator iter = studentList.iterator(); iter.hasNext();) {
                Student student = (Student) iter.next();
                student.setDepartment(new Department(getLong(request, "departmentId")));
                student.setFirstMajor(new Speciality(getLong(request, "specialityId")));
                if (getLong(request, "specialityAspectId") == null) {
                    student.setFirstAspect(null);
                } else if (new NotZeroNumberPredicate().evaluate(getLong(request,
                        "specialityAspectId"))) {
                    student.setFirstAspect(new SpecialityAspect(getLong(request,
                            "specialityAspectId")));
                }
                utilService.saveOrUpdate(student);
            }
        }
        
        // TODO
        return mapping.findForward(SUCCESS);
    }
    
    /**
     * 初始化表单需要的一些信息
     */
    private void initForm() {
        /* 行政管理院系 */
        initBaseCodes("managementDepartList", Department.class);
        /* 入学前最高学历 */
        initBaseCodes("eduDegreeList", EduDegree.class);
        /* 入学方式 */
        initBaseCodes("enrollModeList", EnrollMode.class);
        /* 离校原因 */
        initBaseCodes("leaveSchoolCauseList", LeaveSchoolCause.class);
        /* 培养方式 */
        initBaseCodes("educationModeList", EducationMode.class);
        /* 费用来源 */
        initBaseCodes("feeOriginList", FeeOrigin.class);
        /* 学籍状态 */
        initBaseCodes("studentStateList", StudentState.class);
        /* 性别 */
        initBaseCodes("genderList", Gender.class);
        /* 政治面貌 */
        initBaseCodes("politicVisageList", PoliticVisage.class);
        /* 民族 */
        initBaseCodes("nationList", Nation.class);
        /* 国籍 */
        initBaseCodes("countryList", Country.class);
        /* 留学生HSK等级 */
        initBaseCodes("HSKDegreeList", HSKDegree.class);
        /* 护照类别 */
        initBaseCodes("passportTypeList", PassportType.class);
        /* 签证类别 */
        initBaseCodes("visaTypeList", VisaType.class);
        /* 婚姻状况 */
        initBaseCodes("maritalStatusList", MaritalStatus.class);
        /* 语种熟练程度（语言能力） */
        initBaseCodes("languageAbilityList", LanguageAbility.class);
        initBaseCodes("schoolDistrictList", SchoolDistrict.class);
    }
    
    /**
     * @param studentService
     *            The studentService to set.
     */
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    
}
