package com.shufe.model.course.attend;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

public class AttendWarn extends LongIdObject{
	/** 学生  */
    protected Student std;    
    /** 教学日历   */
    protected TeachCalendar calendar;
    /** 总课时 */
    protected Float zks;
    /** 应出勤 */
    protected Integer ycq;
    /** 累计缺勤 */
    protected Integer ljqq;
    /** 累计迟到 */
    protected Integer ljcd;
    /** 累计课时 */
    protected Float ljks;
	
    public Student getStd() {
		return std;
	}
	public void setStd(Student std) {
		this.std = std;
	}
	public TeachCalendar getCalendar() {
		return calendar;
	}
	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}
	public Float getZks() {
		return zks;
	}
	public void setZks(Float zks) {
		this.zks = zks;
	}
	public Integer getYcq() {
		return ycq;
	}
	public void setYcq(Integer ycq) {
		this.ycq = ycq;
	}
	public Integer getLjqq() {
		return ljqq;
	}
	public void setLjqq(Integer ljqq) {
		this.ljqq = ljqq;
	}
	public Integer getLjcd() {
		return ljcd;
	}
	public void setLjcd(Integer ljcd) {
		this.ljcd = ljcd;
	}
	public Float getLjks() {
		return ljks;
	}
	public void setLjks(Float ljks) {
		this.ljks = ljks;
	}
    
}
