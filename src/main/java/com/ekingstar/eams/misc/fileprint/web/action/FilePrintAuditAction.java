//$Id: FilePrintAuditAction.java,v 1.21 2006/11/29 01:31:31 duanth Exp $
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
 * liuzhuoshan              2008-5-21         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.misc.fileprint.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.misc.fileprint.model.FilePrintApplication;
import com.ekingstar.eams.system.config.SystemConfig;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.file.FilePath;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public class FilePrintAuditAction extends CalendarRestrictionSupportAction {

	protected TeachTaskService teachTaskService;
	
	/**
     * 管理信息主页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	setCalendarDataRealm(request, hasStdTypeCollege);
    	/*String stdTypeDataRealm = getStdTypeIdSeq(request);
		String departDataRealm = getDepartmentIdSeq(request);
    	addCollection(request, "teachDepartList", teachTaskService
				.getTeachDepartsOfTask(stdTypeDataRealm, departDataRealm,
						(TeachCalendar) request
								.getAttribute(Constants.CALENDAR)));*/
		addCollection(request, "teachDepartList", departmentService.getAllDepartments());
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	Long calendarId = getLong(request, "calendar.id");
    	EntityQuery query = new EntityQuery(FilePrintApplication.class, "filePrintApplication");
		populateConditions(request, query);
		query.add(new Condition(" filePrintApplication.calendar.id=:calendarId",
				calendarId));
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		request.setAttribute("calendar", utilService.load(TeachCalendar.class, calendarId));
    	addCollection(request, "filePrintApplicationList", utilService.search(query));
        return forward(request);
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String filePrintApplicationId = request
					.getParameter("filePrintApplicationId");
		Long calendarId = getLong(request, "calendar.id");
		request.setAttribute("calendar", utilService.load(TeachCalendar.class, calendarId));
		addCollection(request, "departs", departmentService.getAllDepartments());
		if (!"".equals(filePrintApplicationId)) {
			FilePrintApplication filePrintApplication = (FilePrintApplication) utilService
					.get(FilePrintApplication.class, Long.valueOf(filePrintApplicationId));
			request.setAttribute("filePrintApplication", filePrintApplication);
		}
		return forward(request);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	FilePrintApplication filePrintApplication = (FilePrintApplication) populateEntity(request, FilePrintApplication.class, "filePrintApplication");
    	DynaActionForm dynaForm = (DynaActionForm) form;
        FormFile formFile = (FormFile) dynaForm.get("formFile");
        
        // 判断是否已有上传文档, 有则删除
        if (null != filePrintApplication.getId()) {
        	FilePrintApplication application = (FilePrintApplication)utilService.load(FilePrintApplication.class, filePrintApplication.getId());
        	if (null != application.getFilePath()) {
        		SystemConfig config = SystemConfigLoader.getConfig();
        		String defaultPath = request.getSession().getServletContext().getRealPath(
        				FilePath.fileDirectory);
    			File file = new File(FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" + application.getFilePath());
    			log.info("user [" + getUser(request.getSession()).getName() + "] delete file[" + file.getAbsolutePath()
    					+ "]");

    			file.delete();
        	}
        } else {
        	filePrintApplication.setAddAt(new Date(System.currentTimeMillis()));
    		filePrintApplication.setApplyBy(getUser(request.getSession()));
        }
        if (!"".equals(formFile.getFileName())) {
	        String filePath = System.currentTimeMillis() + formFile.getFileName().substring(formFile.getFileName().lastIndexOf("."));
	        try {
	            InputStream stream = formFile.getInputStream();//把文件读入
	            SystemConfig config = SystemConfigLoader.getConfig();
	    		String defaultPath = request.getSession().getServletContext().getRealPath(
	    				FilePath.fileDirectory);
	    		
	            OutputStream bos = new FileOutputStream(FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" +
	            		filePath);//建立一个上传文件的输出流
	            int bytesRead = 0;
	            byte[] buffer = new byte[1024];
	            while ( (bytesRead = stream.read(buffer, 0, 1024)) != -1) {
	              bos.write(buffer, 0, bytesRead);//将文件写入服务器
	            }
	            bos.close();
	            stream.close();
	          }catch(Exception e){
	            System.err.print(e);
	        }
	        filePrintApplication.setFileName(formFile.getFileName());
	        filePrintApplication.setFilePath(filePath);
        }
    	utilService.saveOrUpdate(filePrintApplication);
		return redirect(request, new Action("", "search"), "info.save.success",
				new String[] { "calendar.id" });
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String filePrintApplicationIdSeq = request.getParameter("filePrintApplicationIds");
		Long[] filePrintApplicationIds = SeqStringUtil.transformToLong(filePrintApplicationIdSeq);
		// 删除文档
		for (int i = 0; i < filePrintApplicationIds.length; i++) {
			Long filePrintApplicationId = filePrintApplicationIds[i];
			FilePrintApplication filePrintApplication = (FilePrintApplication)utilService.load(FilePrintApplication.class, filePrintApplicationId);
			SystemConfig config = SystemConfigLoader.getConfig();
    		String defaultPath = request.getSession().getServletContext().getRealPath(
    				FilePath.fileDirectory);
			File file = new File(FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" + filePrintApplication.getFilePath());
			log.info("user [" + getUser(request.getSession()).getName() + "] delete file[" + file.getAbsolutePath()
					+ "]");

			file.delete();
		}		
		utilService.remove(FilePrintApplication.class, "id", filePrintApplicationIds);
		return redirect(request, new Action("", "search"), "info.delete.success",
				new String[] { "calendar.id" });
    }
    
    public ActionForward pass(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String filePrintApplicationIdSeq = request.getParameter("filePrintApplicationIds");
		Long[] filePrintApplicationIds = SeqStringUtil.transformToLong(filePrintApplicationIdSeq);
		for (int i = 0; i < filePrintApplicationIds.length; i++) {
			Long filePrintApplicationId = filePrintApplicationIds[i];
			FilePrintApplication filePrintApplication = (FilePrintApplication)utilService.get(FilePrintApplication.class, filePrintApplicationId);
			filePrintApplication.setAuditBy(getUser(request.getSession()));
			filePrintApplication.setAuditState(Boolean.TRUE);
			filePrintApplication.setAuditAt(new Date(System.currentTimeMillis()));
			utilService.saveOrUpdate(filePrintApplication);
		}
		return redirect(request, new Action("", "search"), "info.save.success",
				new String[] { "calendar.id" });
    }
    
    public ActionForward unPass(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String filePrintApplicationIdSeq = request.getParameter("filePrintApplicationIds");
		Long[] filePrintApplicationIds = SeqStringUtil.transformToLong(filePrintApplicationIdSeq);
		for (int i = 0; i < filePrintApplicationIds.length; i++) {
			Long filePrintApplicationId = filePrintApplicationIds[i];
			FilePrintApplication filePrintApplication = (FilePrintApplication)utilService.get(FilePrintApplication.class, filePrintApplicationId);
			filePrintApplication.setAuditBy(getUser(request.getSession()));
			filePrintApplication.setAuditState(Boolean.FALSE);
			filePrintApplication.setAuditAt(new Date(System.currentTimeMillis()));
			utilService.saveOrUpdate(filePrintApplication);
		}
		return redirect(request, new Action("", "search"), "info.save.success",
				new String[] { "calendar.id" });
    }
    
    public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	return forward(request, new Action(FilePrintApplyAction.class, "download"));

    }
	
	public void setTeachTaskService(TeachTaskService teachTaskService) {
		this.teachTaskService = teachTaskService;
	}

}
