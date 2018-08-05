package com.ekingstar.eams.research.project.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

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
import com.ekingstar.eams.research.project.model.TeachProjectTemplate;
import com.ekingstar.eams.system.basecode.industry.TeachProjectType;
import com.ekingstar.eams.system.config.SystemConfig;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.shufe.model.system.file.FilePath;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

public class TeachProjectTemplateAction extends RestrictionExampleTemplateAction{
    
	// 查看所选项目的 文档列表
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String teachProjectTypeIdSeq = request.getParameter("teachProjectTypeId");
    	if (!"".equals(teachProjectTypeIdSeq)) {
    		List teachProjectTemplateList = (List) utilService
					.load(TeachProjectTemplate.class, "teachProjectType.id", Long.valueOf(teachProjectTypeIdSeq));
			addCollection(request, "teachProjectTemplates", teachProjectTemplateList);
		}
    	request.setAttribute("teachProjectTypeId", teachProjectTypeIdSeq);
		return forward(request);
    }
    
    // 增加 或者 更新 
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String teachProjectTypeId = request.getParameter("teachProjectTypeId");
		request.setAttribute("teachProjectTypeId", teachProjectTypeId);
		return super.edit(mapping, form, request, response);
    }
    
    // 保存
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	Long teachProjectTypeId = getLong(request, "teachProjectTypeId");
    	TeachProjectType teachProjectType = (TeachProjectType) utilService.get(TeachProjectType.class, teachProjectTypeId);
    	TeachProjectTemplate teachProjectTemplate = (TeachProjectTemplate) populateEntity(request, TeachProjectTemplate.class, "teachProjectTemplate");
    	
    	DynaActionForm dynaForm = (DynaActionForm) form;
        FormFile formFile = (FormFile) dynaForm.get("formFile");
        
        // 判断是否已有上传文档, 有则删除
        if (null != teachProjectTemplate.getId()) {
        	TeachProjectTemplate template = (TeachProjectTemplate)utilService.load(TeachProjectTemplate.class, teachProjectTemplate.getId());
        	if (null != template.getFilePath()) {
        		SystemConfig config = SystemConfigLoader.getConfig();
        		String defaultPath = request.getSession().getServletContext().getRealPath(
        				FilePath.fileDirectory);
    			File file = new File(FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" + template.getFilePath());
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
	          
	        teachProjectTemplate.setFileName(formFile.getFileName());
	        teachProjectTemplate.setFilePath(filePath);
        }
        teachProjectTemplate.setTeachProjectType(teachProjectType);    	
    	utilService.saveOrUpdate(teachProjectTemplate);
		return redirect(request, new Action("", "search"), "info.save.success",
				new String[] { "teachProjectTypeId" });
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String teachProjectTemplateIdSeq = request.getParameter("teachProjectTemplateIds");
		Long[] teachProjectTemplateIds = SeqStringUtil.transformToLong(teachProjectTemplateIdSeq);
		
		// 删除文档
		for (int i = 0; i < teachProjectTemplateIds.length; i++) {
			Long teachProjectTemplateId = teachProjectTemplateIds[i];
			TeachProjectTemplate teachProjectTemplate = (TeachProjectTemplate)utilService.load(TeachProjectTemplate.class, teachProjectTemplateId);
			SystemConfig config = SystemConfigLoader.getConfig();
    		String defaultPath = request.getSession().getServletContext().getRealPath(
    				FilePath.fileDirectory);
			File file = new File(FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" + teachProjectTemplate.getFilePath());
			log.info("user [" + getUser(request.getSession()).getName() + "] delete file[" + file.getAbsolutePath()
					+ "]");

			file.delete();
		}
		
		utilService.remove(TeachProjectTemplate.class, "id", teachProjectTemplateIds);
		return redirect(request, new Action("", "search"), "info.save.success",
				new String[] { "teachProjectTypeId" });
    }
    
    public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String teachProjectTemplateId = request.getParameter("teachProjectTemplateId");
    	TeachProjectTemplate teachProjectTemplate = (TeachProjectTemplate)utilService.load(TeachProjectTemplate.class, Long.valueOf(teachProjectTemplateId));
    	
    	SystemConfig config = SystemConfigLoader.getConfig();
		String defaultPath = request.getSession().getServletContext().getRealPath(
				FilePath.fileDirectory);
    	File file = new File(FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" + teachProjectTemplate.getFilePath());
		if (!file.exists()) {
			response.getWriter().write("without file path:[" + FilePath.getRealPath(config, FilePath.DOC, defaultPath) + "/" + new String(teachProjectTemplate.getFilePath().getBytes(), "ISO-8859-1") + "]");
			return null;
		}
		DownloadHelper.download(request, response, file, "");
    	return null;

    }

}
