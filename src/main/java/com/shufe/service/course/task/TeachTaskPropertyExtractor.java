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
 * chaostone             2006-4-18            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.task;

import java.text.DateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

import com.ekingstar.commons.mvc.struts.misc.StrutsMessageResource;
import com.ekingstar.commons.transfer.exporter.DefaultPropertyExtractor;
import com.ekingstar.commons.utils.persistence.UtilService;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.model.course.arrange.Activity;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.course.election.ElectStdScope;
import com.shufe.model.course.task.LaboratoryRequirement;
import com.shufe.model.course.task.MultimediaRequirement;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.course.textbook.Textbook;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.course.arrange.task.CourseActivityDigestor;

public class TeachTaskPropertyExtractor extends DefaultPropertyExtractor {
    
    protected String courseActivityFormat;
    
    protected String examActivityFormat;
    
    protected ExamType examType;
    
    protected MessageResources resources;
    
    protected UtilService utilService;
    
    protected Map weekValues;
    
    private Map<TeachTask, MultimediaRequirement> multiRequirementMap;
    
    private Map<TeachTask, LaboratoryRequirement> laborRequirementMap;
    
    {
        weekValues = new HashMap();
        weekValues.put("星期日", "0");
        weekValues.put("星期一", "1");
        weekValues.put("星期二", "2");
        weekValues.put("星期三", "3");
        weekValues.put("星期四", "4");
        weekValues.put("星期五", "5");
        weekValues.put("星期六", "6");
        multiRequirementMap = new HashMap<TeachTask, MultimediaRequirement>();
        laborRequirementMap = new HashMap<TeachTask, LaboratoryRequirement>();
    }
    
    public TeachTaskPropertyExtractor(Locale locale, MessageResources resources, UtilService utilService) {
        super();
        this.locale = locale;
        this.resources = resources;
        this.setBuddle(new StrutsMessageResource(resources));
        this.utilService = utilService;
    }
    
    public Object getPropertyValue(Object target, String property) throws Exception {
        TeachTask task = (TeachTask) target;
        // 授课教师
        if (property.equals("arrangeInfo.teachers")) {
            String teacherNames = "";
            for (Iterator iter = task.getArrangeInfo().getTeachers().iterator(); iter.hasNext();) {
                Teacher teacher = (Teacher) iter.next();
                if (teacherNames != "")
                    teacherNames += " ";
                teacherNames += teacher.getName();
            }
            return teacherNames;
        } else if (property.equals("arrangeInfo.teachers.eduDegreeInside")) {
            String eduDegreeNames = "";
            boolean singleTeacher = task.getArrangeInfo().isSingleTeacher();
            for (Iterator iter = task.getArrangeInfo().getTeachers().iterator(); iter.hasNext();) {
                Teacher teacher = (Teacher) iter.next();
                if (singleTeacher) {
                    return super.getPropertyValue(teacher, "degreeInfo.eduDegreeInside.name");
                } else {
                    if (eduDegreeNames != "")
                        eduDegreeNames += " ";
                    eduDegreeNames += super.getPropertyValue(teacher,
                            "degreeInfo.eduDegreeInside.name")
                            + " ";
                }
            }
            return eduDegreeNames;
        } else if (property.equals("arrangeInfo.teachers.degree")) {
            String degreeNames = "";
            boolean singleTeacher = task.getArrangeInfo().isSingleTeacher();
            for (Iterator iter = task.getArrangeInfo().getTeachers().iterator(); iter.hasNext();) {
                Teacher teacher = (Teacher) iter.next();
                if (singleTeacher) {
                    return super.getPropertyValue(teacher, "degreeInfo.degree.name");
                } else {
                    if (degreeNames != "")
                        degreeNames += " ";
                    degreeNames += super.getPropertyValue(teacher, "degreeInfo.degree.name") + " ";
                }
            }
            return degreeNames;
        } else if (property.equals("arrangeInfo.teachers.age")) {
            String ages = "";
            boolean singleTeacher = task.getArrangeInfo().isSingleTeacher();
            for (Iterator iter = task.getArrangeInfo().getTeachers().iterator(); iter.hasNext();) {
                Teacher teacher = (Teacher) iter.next();
                if (singleTeacher) {
                    return teacher.getAge();
                } else {
                    if (ages != "")
                        ages += " ";
                    ages += (null == teacher.getAge()) ? " " : teacher.getAge().toString();
                }
            }
            return ages;
        } else if (property.equals("arrangeInfo.teachers.teacherType")) {
            return getPropertyIn(task.getArrangeInfo().getTeachers(), "teacherType.name");
        } else if (property.equals("arrangeInfo.teachers.department.name")) {
            return getPropertyIn(task.getArrangeInfo().getTeachers(), "department.name");
        }
        // 建议教室
        else if (property.equals("arrangeInfo.suggest.rooms")) {
            String roomNames = "";
            for (Iterator iter = task.getArrangeInfo().getSuggest().getRooms().iterator(); iter.hasNext();) {
                Classroom room = (Classroom) iter.next();
                if (roomNames != "") {
                    roomNames += " ";
                }
                roomNames += room.getName();
            }
            return roomNames;
        }// 建议时间
        else if (property.equals("arrangeInfo.suggest.time")) {
            if (null == task.getArrangeInfo().getSuggest().getTime()) {
                return "";
            } else {
                return task.getArrangeInfo().getSuggest().getTime().abbreviate();
            }
        } else if (property.equals("calendar")) {
            return task.getCalendar().getYear() + " " + task.getCalendar().getTerm();
        } else if (property.equals("arrangeInfo.activities.weeks")) {
            return CourseActivityDigestor.digest(task.getCalendar(), task.getArrangeInfo(),
                    resources, locale, CourseActivityDigestor.weeks);
        } else if (property.equals("arrangeInfo.activities")) {
            return CourseActivityDigestor.digest(task, resources, locale);
        } else if (property.equals("arrangeInfo.activities.numeric")) {
            StringBuffer arrangeTime = new StringBuffer(CourseActivityDigestor.digest(
                    task.getCalendar(), task.getArrangeInfo(),
                    task.getArrangeInfo().getActivities(), resources, locale, ":day{:units}"));
            if (arrangeTime.length() == 0) {
                return "";
            }
            int countChar = StringUtils.countMatches(arrangeTime.toString(), "{");
            for (Iterator it = weekValues.keySet().iterator(); it.hasNext() && countChar > 0;) {
                String key = (String) it.next();
                if (StringUtils.indexOf(arrangeTime.toString(), key) >= 0) {
                    arrangeTime = new StringBuffer(StringUtils.replace(arrangeTime.toString(), key,
                            weekValues.get(key).toString()));
                    countChar--;
                }
            }
            for (int i = 0, startChar = 0, week = 0; i < arrangeTime.toString().length(); i++) {
                if (arrangeTime.toString().charAt(i) == '{') {
                    week = Integer.parseInt(String.valueOf(arrangeTime.toString().charAt(i - 1)));
                    startChar = i + 1;
                    continue;
                }
                if (arrangeTime.toString().charAt(i) == '}') {
                    String str = StringUtils.substring(arrangeTime.toString(), startChar, i);
                    String[] units = StringUtils.split(str, "-");
                    if (units[0].length() == 1) {
                        units[0] = "0" + units[0];
                        i++;
                    }
                    if (units[1].length() == 1) {
                        units[1] = "0" + units[1];
                        i++;
                    }
                    arrangeTime = new StringBuffer(StringUtils.replace(arrangeTime.toString(), week
                            + "{" + str + "} ", week + units[0] + "-" + week + units[1]));
                }
            }
            return arrangeTime;
        }
        // 教室可容纳人数
        else if (property.equals("arrangeInfo.activities.room.capacityOfCourse")) {
            Set roomSeats = new HashSet();
            for (Iterator it = task.getArrangeInfo().getActivities().iterator(); it.hasNext();) {
                Activity activity = (Activity) it.next();
                roomSeats.add(activity.getRoom().getCapacityOfCourse());
            }
            StringBuffer roomSeatCount = new StringBuffer();
            for (Iterator it = roomSeats.iterator(); it.hasNext();) {
                Integer seatsCount = (Integer) it.next();
                roomSeatCount.append(seatsCount.intValue());
                if (it.hasNext()) {
                    roomSeatCount.append(",");
                }
            }
            return roomSeatCount.toString();
        } else if (property.equals("arrangeInfo.activities.time")) {
            return CourseActivityDigestor.digest(task.getCalendar(), task.getArrangeInfo(),
                    resources, locale, ":day :units");
        } else if (property.equals("arrangeInfo.activities.room")) {
            Set roomSet = new HashSet();
            for (Iterator it = task.getArrangeInfo().getActivities().iterator(); it.hasNext();) {
                Activity activity = (Activity) it.next();
                roomSet.add(activity.getRoom().getName());
            }
            StringBuffer roomNames = new StringBuffer();
            for (Iterator it = roomSet.iterator(); it.hasNext();) {
                String roomName = (String) it.next();
                roomNames.append(roomName);
                if (it.hasNext()) {
                    roomNames.append(",");
                }
            }
            return roomNames.toString();
        } else if (property.equals("teachClass.adminClasses")) {
            String classNames = "";
            for (Iterator iter = task.getTeachClass().getAdminClasses().iterator(); iter.hasNext();) {
                AdminClass adminClass = (AdminClass) iter.next();
                if (classNames != "")
                    classNames += " ";
                classNames += adminClass.getName();
            }
            return classNames;
        } else if (property.equals("electInfo.electScopes")) {
            StringBuffer scopes = new StringBuffer();
            int i = 1;
            for (Iterator iter = task.getElectInfo().getElectScopes().iterator(); iter.hasNext();) {
                ElectStdScope scope = (ElectStdScope) iter.next();
                scopes.append(i).append(scope.toString());
            }
            return scopes;
        } else if (property.equals("requirement.textbooks")) {
            StringBuffer textbooks = new StringBuffer();
            for (Iterator iter = task.getRequirement().getTextbooks().iterator(); iter.hasNext();) {
                Textbook book = (Textbook) iter.next();
                textbooks.append(book.getName() + " ");
            }
            return textbooks;
        } else if (property.equals("exam.time")) {
            return task.getArrangeInfo().digestExam(task.getCalendar(), resources, locale,
                    examType.getId().toString(), examActivityFormat);
        } else if (property.equals("exam.date")) {
            Collection examActivities = task.getArrangeInfo().getExamActivities(examType);
            if (examActivities.isEmpty())
                return "";
            else {
                return dateFormat.format(((ExamActivity) (examActivities.iterator().next())).getDate());
            }
        } else if (property.equals("exam.rooms")) {
            StringBuffer roomNames = new StringBuffer(30);
            for (Iterator iter = task.getArrangeInfo().getExamRooms(examType).iterator(); iter.hasNext();) {
                Classroom classroom = (Classroom) iter.next();
                if (roomNames.length() != 0) {
                    roomNames.append(" ");
                }
                roomNames.append(classroom.getName());
            }
            return roomNames.toString();
        } else if (property.equals("exam.teachers")) {
            return getPropertyIn(task.getArrangeInfo().getExamTeachers(examType), "name");
        } else if (property.equals("arrangeInfo.weekCycle")) {
            String[] weekCycles = { "", "连续周", "单周", "双周", "任意周" };
            return weekCycles[task.getArrangeInfo().getWeekCycle().intValue()];
        } else if (property.equals("multiRequire.environmentRequirement")) {
            MultimediaRequirement multiRequirement = getMultiRequirement(task);
            return null == multiRequirement ? null : multiRequirement.getEnvironmentRequirement();
        } else if (property.equals("labRequire.timeDescrition")) {
            LaboratoryRequirement laborRequirement = getLaboratoryRequirement(task);
            return null == laborRequirement ? null : laborRequirement.getTimeDescrition();
        } else if (property.equals("labRequire.experimentRequirement")) {
            LaboratoryRequirement laborRequirement = getLaboratoryRequirement(task);
            return null == laborRequirement ? null : laborRequirement.getExperimentRequirement();
        } else if (property.equals("labRequire.overallUnit")) {
            LaboratoryRequirement laborRequirement = getLaboratoryRequirement(task);
            return null == laborRequirement ? null : laborRequirement.getOverallUnit();
        } else if (property.equals("labRequire.propExperimental")) {
            LaboratoryRequirement laborRequirement = getLaboratoryRequirement(task);
            return null == laborRequirement ? null : laborRequirement.getPropExperimental();
        } else if (property.equals("labRequire.projectDescrition")) {
            LaboratoryRequirement laborRequirement = getLaboratoryRequirement(task);
            return null == laborRequirement ? null : laborRequirement.getProjectDescrition();
        } else if (property.equals("labRequire.roomType.name")) {
            LaboratoryRequirement laborRequirement = getLaboratoryRequirement(task);
            return null == laborRequirement ? null : laborRequirement.getClassroomType().getName();
        } else {
            return super.getPropertyValue(target, property);
        }
    }
    
    private LaboratoryRequirement getLaboratoryRequirement(TeachTask task) {
        LaboratoryRequirement laborRequirement = laborRequirementMap.get(task);
        if (null == laborRequirement) {
            List<LaboratoryRequirement> laborRequirements = utilService.load(
                    LaboratoryRequirement.class, "task.id", new Long[] { task.getId() });
            if (CollectionUtils.isNotEmpty(laborRequirements)) {
                laborRequirement = laborRequirements.get(0);
                laborRequirementMap.put(task, laborRequirement);
            }
        }
        return laborRequirement;
    }
    
    private MultimediaRequirement getMultiRequirement(TeachTask task) {
        MultimediaRequirement multiRequirement = multiRequirementMap.get(task);
        if (null == multiRequirement) {
            List<MultimediaRequirement> multiRequirements = utilService.load(
                    MultimediaRequirement.class, "task.id", new Long[] { task.getId() });
            if (CollectionUtils.isNotEmpty(multiRequirements)) {
                multiRequirement = multiRequirements.get(0);
                multiRequirementMap.put(task, multiRequirement);
            }
        }
        return multiRequirement;
    }
    
    public void setCourseActivityFormat(String courseActivityFormat) {
        this.courseActivityFormat = courseActivityFormat;
    }
    
    public void setExamActivityFormat(String examActivityFormat) {
        this.examActivityFormat = examActivityFormat;
    }
    
    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    public void setExamType(ExamType examType) {
        this.examType = examType;
    }
    
    public MessageResources getResources() {
        return resources;
    }
    
    public void setResources(MessageResources resources) {
        this.resources = resources;
    }
}
