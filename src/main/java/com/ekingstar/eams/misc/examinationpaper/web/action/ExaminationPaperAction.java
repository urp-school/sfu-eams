//$Id: ExaminationPaperAction.java,v 1.21 2006/11/29 01:31:31 duanth Exp $
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

package com.ekingstar.eams.misc.examinationpaper.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.mvc.web.download.DownloadHelper;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.misc.examinationpaper.model.ExaminationPaper;
import com.ekingstar.eams.system.config.SystemConfig;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.file.FilePath;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public class ExaminationPaperAction extends CalendarRestrictionSupportAction {

	protected TeachTaskService teachTaskService;
	
	public void setTeachTaskService(TeachTaskService teachTaskService) {
		this.teachTaskService = teachTaskService;
	}

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	Long calendarId = getLong(request, "calendar.id");
    	EntityQuery query = new EntityQuery(ExaminationPaper.class, "examinationPaper");
		populateConditions(request, query);
		query.add(new Condition(" examinationPaper.calendar.id=:calendarId",
				calendarId));
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		request.setAttribute("calendar", utilService.load(TeachCalendar.class, calendarId));
    	addCollection(request, "examinationPaperList", utilService.search(query));
        return forward(request);
    }
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String examinationPaperId = request
				.getParameter("examinationPaperId");
    	Long calendarId = getLong(request, "calendar.id");
    	request.setAttribute("calendar", utilService.load(TeachCalendar.class, calendarId));
    	if (!"".equals(examinationPaperId)) {
    		ExaminationPaper examinationPaper = (ExaminationPaper) utilService
					.get(ExaminationPaper.class, Long.valueOf(examinationPaperId));
			request.setAttribute("examinationPaper", examinationPaper);
    	}
		return forward(request);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	ExaminationPaper examinationPaper = (ExaminationPaper) populateEntity(request, ExaminationPaper.class, "examinationPaper");
    	DynaActionForm dynaForm = (DynaActionForm) form;
        FormFile formFile = (FormFile) dynaForm.get("formFile");
        // 判断是否已有上传文档, 有则删除
        if (null != examinationPaper.getId()) {
        	ExaminationPaper paper = (ExaminationPaper)utilService.load(ExaminationPaper.class, examinationPaper.getId());
        	if (null != paper.getFilePath()) {
        		SystemConfig config = SystemConfigLoader.getConfig();
        		String defaultPath = request.getSession().getServletContext().getRealPath(
        				FilePath.fileDirectory);
    			File file = new File(FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" + paper.getFilePath());
//    			log.info("user [" + getUserFromSession(request.getSession()).getName() + "] delete file[" + file.getAbsolutePath()
//    					+ "]");
    			file.delete();
        	}
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
	        examinationPaper.setFileName(formFile.getFileName());
	        examinationPaper.setFilePath(filePath);
        }
        utilService.saveOrUpdate(examinationPaper);
		return redirect(request, new Action("", "search"), "info.save.success",
				new String[] { "calendar.id" });
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String examinationPaperIdSeq = request.getParameter("examinationPaperIds");
		Long[] examinationPaperIds = SeqStringUtil.transformToLong(examinationPaperIdSeq);
		// 删除文档
		for (int i = 0; i < examinationPaperIds.length; i++) {
			Long examinationPaperId = examinationPaperIds[i];
			ExaminationPaper examinationPaper = (ExaminationPaper)utilService.load(ExaminationPaper.class, examinationPaperId);
			SystemConfig config = SystemConfigLoader.getConfig();
    		String defaultPath = request.getSession().getServletContext().getRealPath(
    				FilePath.fileDirectory);
			File file = new File(FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" + examinationPaper.getFilePath());
//			log.info("user [" + getUserFromSession(request.getSession()).getName() + "] delete file[" + file.getAbsolutePath()
//					+ "]");
			file.delete();
		}
		utilService.remove(ExaminationPaper.class, "id", examinationPaperIds);
		
		return redirect(request, new Action("", "search"), "info.delete.success",
				new String[] { "calendar.id" });
    }
    
    public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String examinationPaperId = request.getParameter("examinationPaperId");
    	ExaminationPaper examinationPaper = (ExaminationPaper)utilService.load(ExaminationPaper.class, Long.valueOf(examinationPaperId));
    	
    	SystemConfig config = SystemConfigLoader.getConfig();
		String defaultPath = request.getSession().getServletContext().getRealPath(
				FilePath.fileDirectory);
    	File file = new File(FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" + examinationPaper.getFilePath());
		if (!file.exists()) {
			response.getWriter().write("without file path:[" + FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" + new String(examinationPaper.getFilePath().getBytes(), "ISO-8859-1") + "]");
			return null;
		}
		DownloadHelper.download(request, response, file, "");
    	return null;

    }
}
