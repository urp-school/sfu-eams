package com.shufe.model.course.attend;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import com.ekingstar.commons.model.AbstractEntity;
import com.shufe.model.system.baseinfo.Classroom;

/**
 * 考勤机状态监控 
 */
public class AttendDevice extends AbstractEntity{
	private static final long serialVersionUID = 3742746760025720569L;
	/** 考勤机ID */
    private Long devid;    
    /** 教室   */
    private Classroom jsid;    
    /** 签到时间   */
    private Date qdsj;    
    /** 考勤机状态   */
    private Boolean kqjzt;
    /** IP地址  */
    private String ip;
    
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Long getDevid() {
		return devid;
	}
	public void setDevid(Long devid) {
		this.devid = devid;
	}
	public Classroom getJsid() {
		return jsid;
	}
	public void setJsid(Classroom jsid) {
		this.jsid = jsid;
	}
	public Date getQdsj() {
		return qdsj;
	}
	public void setQdsj(Date qdsj) {
		this.qdsj = qdsj;
	}
	public Boolean getKqjzt() {
		return kqjzt;
	}
	public void setKqjzt(Boolean kqjzt) {
		this.kqjzt = kqjzt;
	}
	
	@Override
	public Serializable getEntityId() {
		return this.devid;
	}
	@Override
	public String key() {
		return "devid";
	}
	public int hashCode(){
		return new HashCodeBuilder(-64900959, -454788261).append(this.devid).toHashCode();
	}
	public boolean equals(Object object){
		if (!(object instanceof AttendDevice)) {
			return false;
		}
	    AttendDevice rhs = (AttendDevice)object;
	    if ((null == getDevid()) || (null == rhs.getDevid()))
	      return false;
	    return new EqualsBuilder().append(getDevid(), rhs.getDevid()).isEquals();
	}
}
