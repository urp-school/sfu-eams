package com.ekingstar.eams.research.project.model;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.TeachProjectType;

/**
 * 项目模板 对应数据库表：JXRW_XMMB_T 2008-05-26
 * 
 * @author maple
 * 
 */
public class TeachProjectTemplate extends LongIdObject {
    
    private static final long serialVersionUID = -6064315684972615092L;
    
    /** 项目类别* */
    private TeachProjectType teachProjectType;
    
    /**
	 * 文档名称
	 */
    private String fileName;
    
    /** 
     * 文档路径
     */
	private String filePath;
    
    /**
	 * 文档描述
	 */
	private String describe;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public TeachProjectType getTeachProjectType() {
		return teachProjectType;
	}
	public void setTeachProjectType(TeachProjectType teachProjectType) {
		this.teachProjectType = teachProjectType;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
    
}
