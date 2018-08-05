package com.shufe.web.action.course.attend;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.shufe.model.course.attend.AttendDevice;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.service.course.attend.AttendDeviceImportListener;
import com.shufe.service.course.attend.AttendDeviceService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 考勤机状态监控 
 */
public class AttendDeviceAction extends CalendarRestrictionSupportAction{
	private AttendDeviceService attendDeviceService;	
	public void setAttendDeviceService(AttendDeviceService attendDeviceService) {
		this.attendDeviceService = attendDeviceService;
	}

	/**
     * 准备数据
     */
    protected void prepare(HttpServletRequest request) {
        addCollection(request, "roomDepartList", getDeparts(request));
        addCollection(request, "classroomConfigTypeList", baseCodeService.getCodes(ClassroomType.class));
        addCollection(request, "districtList", baseCodeService.getCodes(SchoolDistrict.class));
    }	 
	 
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		prepare(request);
	    return forward(request);
	}	
	
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {		
		addCollection(request, "attendDevices", utilService.search(getQuery(request)));
		return forward(request);
	}
	
	public EntityQuery getQuery(HttpServletRequest request){
		EntityQuery query = new EntityQuery(AttendDevice.class, "attendDevice");
		populateConditions(request, query);
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		return query;
	}
	 
	 /**
	  * 保存
	  */
	 public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 //AttendDevice attendDevice= (AttendDevice) populateEntity(request, AttendDevice.class, "attendDevice");
		 Long devid_old=getLong(request, "attendDevice.olddevid");
		 Long devid=Long.parseLong(request.getParameter("attendDevice.devid").trim());
		 Long jsid=getLong(request, "attendDevice.jsid.id");
		 String qdsjStr=request.getParameter("attendDevice.qdsj").trim();
		 Date qdsj = null;
		 Boolean kqjzt=getBoolean(request, "attendDevice.kqjzt");
		 String ip=request.getParameter("attendDevice.ip").trim();
		 try {
			 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 qdsj = dateFormat.parse(qdsjStr);			 
		 } catch (Exception e) {
			return redirect(request, "search", "info.save.dateError");
		 }
		 if (devid_old!=null) {
			 if (!devid.equals(devid_old)) {
				 EntityQuery query = new EntityQuery(AttendDevice.class, "ad");
				 query.add(new Condition("ad.devid=(:id)", devid));
				 if (CollectionUtils.isNotEmpty(utilService.search(query))) {
					 return redirect(request, "search", "info.save.devidRepeat");
				}
			}
			String sql="update "+AttendDevice.class.getName()+" ad set ad.devid=?, ad.jsid.id=?, ad.kqjzt=?, ad.qdsj=?, ad.ip=? where ad.devid=?";
			Object[] argument=new Object[]{devid, jsid, kqjzt, qdsj, ip, devid_old};
			utilService.getUtilDao().executeUpdateHql(sql, argument);
		 }else{
			 EntityQuery query = new EntityQuery(AttendDevice.class, "ad");
			 query.add(new Condition("ad.devid=(:id)", devid));
			 if (CollectionUtils.isNotEmpty(utilService.search(query))) {
				 return redirect(request, "search", "info.save.devidRepeat");
			 }
			 AttendDevice attendDevice=new AttendDevice();
			 attendDevice.setDevid(devid);
			 attendDevice.setJsid((Classroom)utilService.load(Classroom.class, jsid));
			 attendDevice.setKqjzt(kqjzt);
			 attendDevice.setIp(ip);
			 attendDevice.setQdsj(qdsj);
			 attendDeviceService.saveAttendDevice(attendDevice);
		 }		 
	     return redirect(request, "search", "info.save.success");
	 }

	 /**
	  * 删除
	  */
	 public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {		 
		 Long[] attendDeviceIds = SeqStringUtil.transformToLong(get(request, "id"));
		 for (Long id : attendDeviceIds) {
			 utilService.remove(utilService.load(AttendDevice.class, id));
		 }
		 return redirect(request, "search", "info.action.success");
	 }
	 
	 /**
	  * 添加
	  */
	 public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 AttendDevice attendDevice = (AttendDevice) getEntity(request, AttendDevice.class, "attendDevice");
		 addCollection(request, "jsidList", baseCodeService.getCodes(Classroom.class));
		 addEntity(request, attendDevice);
		 return forward(request);
	 }
	 
	 @Override
	protected Collection getExportDatas(HttpServletRequest request) {
		 EntityQuery query = getQuery(request);
		 query.setLimit(null);
		 return utilService.search(query);
	}	 
	 
	/**
	 * 导入
	 */
	public ActionForward importData(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		TransferResult tr = new TransferResult();
		Transfer transfer = ImporterServletSupport.buildEntityImporter(request,	AttendDevice.class, tr);
		if (null == transfer) {
			return forward(request, "/pages/components/importData/error");
		}
		transfer.addListener(new ImporterForeignerListener(utilService))
				.addListener(new AttendDeviceImportListener(utilService.getUtilDao(), attendDeviceService));
		transfer.transfer(tr);
		if (tr.hasErrors()) {
			return forward(request, "/pages/components/importData/error");
		} else {
			return redirect(request, "search", "info.import.success");
		}
	}	    
}