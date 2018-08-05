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

package com.shufe.service.course.attend;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.ekingstar.eams.course.grade.converter.ConverterFactory;
import com.ekingstar.eams.system.basecode.industry.ExamStatus;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.attend.AttendStatic;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.system.calendar.TeachCalendarService;

/**
 * 考勤信息的导入监听器
 * 
 * @author chaostone
 * 
 */
public class AttendStaticImportListener extends ItemImporterListener {
    
    private UtilDao utilDao;
    
    private AttendStaticService attendStaticService;
    
    private TeachCalendarService teachCalendarService;
    
    protected AttendStatic attendStatic=new AttendStatic();
    
    private Student student=null;
    private TeachTask teachTask=null;
    private String attenddate=null;
    private String attendtime=null;
    private String attendtype=null;
    private  Date attenddateNew=null;;
    
    public AttendStaticImportListener() {
        super();
    }
    
    public AttendStaticImportListener(UtilDao utilDao, AttendStaticService attendStaticService,TeachCalendarService teachCalendarService) {
        super();
        this.utilDao = utilDao;
        this.attendStaticService = attendStaticService;
        this.teachCalendarService=teachCalendarService;
    }
    
    public void startTransferItem(TransferResult tr) {
    	String code=(String) importer.curDataMap().get("student.code");
    	String taskSeqNo=(String) importer.curDataMap().get("task.seqNo");
        attenddate=(String) importer.curDataMap().get("attenddate");
        attendtime=(String) importer.curDataMap().get("attendtime");
        attendtype=(String) importer.curDataMap().get("attendtype");
         if(code==null){
      	   tr.addFailure("error.parameters.illegal", "没有填写学生学号");
         }
         if(taskSeqNo==null){
      	   tr.addFailure("error.parameters.illegal", "没有填写课程序号");
         }
         if(attenddate==null){
      	   tr.addFailure("error.parameters.illegal", "没有填写考勤日期");
         }
         if(attendtime==null){
      	   tr.addFailure("error.parameters.illegal", "没有填写考勤时间");
         }
         if(attendtype==null){
      	   tr.addFailure("error.parameters.illegal", "没有填写考勤类型");
         } 
         
         student=attendStaticService.getStudent(code);
         if(student==null){
      	   tr.addFailure("error.parameters.illegal", "该学生编号不存在对应的学生");
         }
         
         teachTask=attendStaticService.getTeachTask(taskSeqNo);
         if(teachTask==null){
      	   tr.addFailure("error.parameters.illegal", "该课程编号不存在对应教学任务");
         }
         SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd"); 
    	
         try {
			attenddateNew = dateFormat.parse(attenddate);
         } catch (ParseException e) {
        	 tr.addFailure("error.parameters.illegal", "日期格式不正确");
         }
         
              
    }
    
    public void endTransferItem(TransferResult tr) {
         if (tr.errors() == 0) {
        	
        	 TeachCalendar calendar=teachCalendarService.getTeachCalendar(attenddateNew);
        	 attendStatic.setCalendar(calendar);
             attendStatic.setStudent(student);
             attendStatic.setAttenddate(attenddateNew);
             attendStatic.setAttendtime(attendtime.substring(0,5));
             attendStatic.setAttendtype(attendtype);
             attendStatic.setTask(teachTask);
             
        	attendStatic.setCourse(attendStatic.getTask().getCourse());
        	attendStatic.setDepartment(attendStatic.getStudent().getDepartment());
        	utilDao.saveOrUpdate(attendStatic);
        }
    }
    public void setUtilDao(UtilDao utilDao) {
        this.utilDao = utilDao;
    }

	public void setAttendStaticService(AttendStaticService attendStaticService) {
		this.attendStaticService = attendStaticService;
	}

	public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
		this.teachCalendarService = teachCalendarService;
	}

    
    
}
