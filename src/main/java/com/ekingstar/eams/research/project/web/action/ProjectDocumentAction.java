package com.ekingstar.eams.research.project.web.action;

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
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.research.project.model.ProjectDocument;
import com.ekingstar.eams.research.project.model.TeachProject;
import com.ekingstar.eams.system.config.SystemConfig;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.shufe.model.system.file.FilePath;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

public class ProjectDocumentAction  extends RestrictionExampleTemplateAction {

	// 查看所选项目的 文档列表
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String teachProjectIdSeq = request.getParameter("teachProjectId");
    	if ((!"".equals(teachProjectIdSeq)) && (null!=teachProjectIdSeq)) {
    		TeachProject teachProject = (TeachProject) utilService
					.get(TeachProject.class, Long.valueOf(teachProjectIdSeq));
			addCollection(request, "projectDocuments", teachProject.getProjectDocuments());
		}
    	request.setAttribute("teachProjectId", teachProjectIdSeq);
		return forward(request);
    }
    
    // 增加 或者 更新 
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String teachProjectId = request.getParameter("teachProjectId");
		request.setAttribute("teachProjectId", teachProjectId);
		return super.edit(mapping, form, request, response);
    }
    
    // 保存
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	Long teachProjectId = getLong(request, "teachProjectId");
    	TeachProject teachProject = (TeachProject) utilService.get(TeachProject.class, teachProjectId);
    	ProjectDocument projectDocument = (ProjectDocument) populateEntity(request, ProjectDocument.class, "projectDocument");
    	
    	DynaActionForm dynaForm = (DynaActionForm) form;
        FormFile formFile = (FormFile) dynaForm.get("formFile");
        
        // 判断是否已有上传文档, 有则删除
        if (null != projectDocument.getId()) {
        	ProjectDocument document = (ProjectDocument)utilService.load(ProjectDocument.class, projectDocument.getId());
        	if (null != document.getFilePath()) {
        		SystemConfig config = SystemConfigLoader.getConfig();
        		String defaultPath = request.getSession().getServletContext().getRealPath(
        				FilePath.fileDirectory);
    			File file = new File(FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" + document.getFilePath());
    			log.info("user [" + getUser(request.getSession()).getName() + "] delete file[" + file.getAbsolutePath()
    					+ "]");

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
	        
	        projectDocument.setFileName(formFile.getFileName());
	        projectDocument.setFilePath(filePath);
        }
    	projectDocument.setTeachProject(teachProject);    	
    	utilService.saveOrUpdate(projectDocument);
		return redirect(request, new Action("", "search"), "info.save.success",
				new String[] { "teachProjectId" });
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String projectDocumentIdSeq = request.getParameter("projectDocumentIds");
		Long[] projectDocumentIds = SeqStringUtil.transformToLong(projectDocumentIdSeq);
		
		// 删除文档
		for (int i = 0; i < projectDocumentIds.length; i++) {
			Long projectDocumentId = projectDocumentIds[i];
			ProjectDocument projectDocument = (ProjectDocument)utilService.load(ProjectDocument.class, projectDocumentId);
			SystemConfig config = SystemConfigLoader.getConfig();
    		String defaultPath = request.getSession().getServletContext().getRealPath(
    				FilePath.fileDirectory);
			File file = new File(FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" + projectDocument.getFilePath());
			log.info("user [" + getUser(request.getSession()).getName() + "] delete file[" + file.getAbsolutePath()
					+ "]");

			file.delete();
		}
		
		utilService.remove(ProjectDocument.class, "id", projectDocumentIds);
		return redirect(request, new Action("", "search"), "info.save.success",
				new String[] { "teachProjectId" });
    }
    
    public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String projectDocumentId = request.getParameter("projectDocumentId");
    	ProjectDocument projectDocument = (ProjectDocument)utilService.load(ProjectDocument.class, Long.valueOf(projectDocumentId));
    	
    	SystemConfig config = SystemConfigLoader.getConfig();
		String defaultPath = request.getSession().getServletContext().getRealPath(
				FilePath.fileDirectory);
    	File file = new File(FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" + projectDocument.getFilePath());
		if (!file.exists()) {
			response.getWriter().write("without file path:[" + FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" + new String(projectDocument.getFilePath().getBytes(), "ISO-8859-1") + "]");
			return null;
		}
		DownloadHelper.download(request, response, file, "");
    	return null;
    }
	
}
