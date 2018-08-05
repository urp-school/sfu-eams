package com.shufe.service.course.attend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.shufe.model.course.attend.AttendDevice;
import com.shufe.model.system.baseinfo.Classroom;

/**
 * 考勤机状态监控 信息的导入监听器
 */
public class AttendDeviceImportListener extends ItemImporterListener {
	public AttendDeviceImportListener() {
		super();
	}

	private UtilDao utilDao;
	private AttendDeviceService attendDeviceService;
	public AttendDeviceImportListener(UtilDao utilDao, AttendDeviceService attendDeviceService) {
		super();
		this.utilDao = utilDao;
		this.attendDeviceService=attendDeviceService;
	}

	protected AttendDevice attendDevice = new AttendDevice();

	/** 考勤机 */
	private String devidStr = null;
	private Long devid = null;
	/** 教室 */
	private Classroom jsid = null;
	private String jsidStr = null;
	/** 签到时间 */
	private Date qdsj = null;
	private String qdsjStr = null;
	/** 考勤机状态 */
	private Boolean kqjzt = null;
	private String kqjztStr = null;
	/** IP地址  */
	private String ip = null;
	List<Classroom> rs = new ArrayList<Classroom>();

	public void startTransferItem(TransferResult tr) {
		devidStr = (String) importer.curDataMap().get("devid");
		jsidStr = (String) importer.curDataMap().get("jsid");
		qdsjStr = (String) importer.curDataMap().get("qdsj");
		kqjztStr = (String) importer.curDataMap().get("kqjzt");
		ip = (String) importer.curDataMap().get("ip");
		if (StringUtils.isBlank(devidStr)) {
			tr.addFailure("error.parameters.illegal", "没有填写考勤机代码");
		} else {
			try {
				devid = Long.parseLong(devidStr);
				EntityQuery query = new EntityQuery(AttendDevice.class, "ad");
				query.add(new Condition("ad.devid=(:id)", devid));
				if (CollectionUtils.isNotEmpty(utilDao.search(query))) {
					tr.addFailure("error.parameters.illegal", "考勤机代码重复");
				}
			} catch (Exception e) {
				tr.addFailure("error.parameters.illegal", "考勤机代码格式不正确");
			}
		}
		if (StringUtils.isBlank(jsidStr)) {
			tr.addFailure("error.parameters.illegal", "没有填写教室代码");
		} else {
			EntityQuery query = new EntityQuery(Classroom.class, "cr");
			query.add(new Condition("cr.code=(:code)", jsidStr));
			rs.addAll(utilDao.search(query));
			if (CollectionUtils.isNotEmpty(rs)) {
				jsid = rs.get(0);
			} else {
				tr.addFailure("error.parameters.illegal", "该教室代码不存在对应教室");
			}
		}
		if (StringUtils.isBlank(qdsjStr)) {
			tr.addFailure("error.parameters.illegal", "没有填写签到时间");
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				qdsj = dateFormat.parse(qdsjStr);
			} catch (ParseException e) {
				tr.addFailure("error.parameters.illegal", "签到时间格式不正确");
			}
		}
		if (StringUtils.isBlank(kqjztStr)) {
			tr.addFailure("error.parameters.illegal", "没有填写考勤机状态");
		} else {
			if (kqjztStr.equals("正常")) {
				kqjzt = true;
			} else if (kqjztStr.equals("出错")) {
				kqjzt = false;
			} else {
				tr.addFailure("error.parameters.illegal", "考勤机状态填写错误");
			}
		}
		if (StringUtils.isBlank(ip)) {
			tr.addFailure("error.parameters.illegal", "没有填写IP地址");
		}
	}

	public void endTransferItem(TransferResult tr) {
		if (tr.errors() == 0) {
			attendDevice.setDevid(devid);
			attendDevice.setJsid(jsid);
			attendDevice.setKqjzt(kqjzt);
			attendDevice.setIp(ip);
			attendDevice.setQdsj(qdsj);
			attendDeviceService.saveAttendDevice(attendDevice);
		}
	}
}