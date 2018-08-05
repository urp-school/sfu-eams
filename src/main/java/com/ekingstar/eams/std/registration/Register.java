package com.ekingstar.eams.std.registration;

import java.sql.Timestamp;

import com.ekingstar.eams.std.info.Student;
import com.ekingstar.eams.system.basecode.industry.RegisterState;
import com.ekingstar.eams.system.time.TeachCalendar;
/**
 * 注册信息
 * @author dth,huanghaijun,husheng
 *
 */
public interface Register {

	public TeachCalendar getCalendar();

	public void setCalendar(TeachCalendar calendar);

	public Boolean getIsPassed();

	public void setIsPassed(Boolean status);

	public Student getStd();

	public void setStd(Student std);

	public Timestamp getRegisterAt();

	public void setRegisterAt(Timestamp registerAt);

	public String getRemark();

	public void setRemark(String remark);
	
	public void setState(RegisterState state);

}