/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is
 * intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source,
 * or other redistribution of this source is not permitted without written
 * permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/*******************************************************************************
 * @author chaostone MODIFICATION DESCRIPTION Name Date Description ============
 *         ============ ============ chaostone 2006-11-21 Created
 ******************************************************************************/
package com.shufe.model.system.file;

import java.io.File;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.Component;
import com.ekingstar.eams.system.config.SystemConfig;

/**
 * 文件路径
 * 
 * @author chaostone
 */
public class FilePath implements Component {

	public static final String DOC = "doc";

	public static final String TEACHER_PHOTO = "teacherPhoto";

	public static final String STD_PHOTO = "stdPhoto";

	public static final String DEGREE = "degree";

	public static final String THESIS = "degree/thesis";

	public static final String TEMPLATE_UPLOAD = "template" + File.separator + "upload";

	public static final String TEMPLATE_DOWNLOAD = "template" + File.separator + "download";

	public static final String fileDirectory = "/WEB-INF/download/";

	/**
	 * 系统文档路径
	 */
	String doc;

	/**
	 * 教师照片路径
	 */
	String teacherPhoto;

	/**
	 * 学生照片路径
	 */
	String stdPhoto;

	/**
	 * 学生论文路径
	 */
	String thesis;
	/**
	 * 学位学科相关文档
	 */
	String degree;

	public FilePath() {
		super();
	}

	public FilePath(String path) {
		this.doc = path + "/doc/";
		this.teacherPhoto = path + "/teacherPhoto/";
		this.stdPhoto = path + "/stdPhoto/";
		this.degree = path + "/degree/";
		this.thesis = path + "/degree/thesis/";
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String docPath) {
		this.doc = docPath;
	}

	public String getStdPhoto() {
		return stdPhoto;
	}

	public void setStdPhoto(String stdPhotoPath) {
		this.stdPhoto = stdPhotoPath;
	}

	public String getTeacherPhoto() {
		return teacherPhoto;
	}

	public void setTeacherPhoto(String teacherPhotoPath) {
		this.teacherPhoto = teacherPhotoPath;
	}

	public String getThesis() {
		return thesis;
	}

	public void setThesis(String thesis) {
		this.thesis = thesis;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String[] checkPath() {
		Vector dd = new Vector();
		checkPath(dd, getDoc());
		checkPath(dd, getTeacherPhoto());
		checkPath(dd, getStdPhoto());
		checkPath(dd, getThesis());
		checkPath(dd, getDegree());
		return (String[]) dd.toArray(new String[dd.size()]);
	}

	private void checkPath(Vector errorPaths, String path) {
		if (StringUtils.isNotEmpty(path)) {
			if (!isPathExists(path)) {
				errorPaths.add(path);
			}
		}
	}

	public static boolean isPathExists(String path) {
		File storeDirectory = new File(path);
		return storeDirectory.exists() && storeDirectory.isDirectory();
	}

	/**
	 * 获取特定文档类别存放的路径(以\结束)
	 * 
	 * @param kind
	 * @param defaultDir
	 *            如果系统设置中为null,则采用defaultDir作为前缀
	 * @return
	 */
	public static String getRealPath(SystemConfig config, String kind, String defaultDir) {
		String customPath = null;
		if (FilePath.DOC.equals(kind)) {
			customPath = (String) config.getConfigItemValue("system.document.docPath");
			if ( StringUtils.isNotEmpty(customPath)) {
				return customPath;
			} else {
				return defaultDir + File.separator + FilePath.DOC + File.separator;
			}
		} else if (FilePath.STD_PHOTO.equals(kind)) {
			customPath = (String) config.getConfigItemValue("system.document.stdPhotoPath");
			if (StringUtils.isNotEmpty(customPath)) {
				return customPath;
			} else {
				return defaultDir + File.separator + FilePath.STD_PHOTO + File.separator;
			}
		} else if (FilePath.TEACHER_PHOTO.equals(kind)) {
			customPath = (String) config.getConfigItemValue("system.document.teacherPhotoPath");
			if (StringUtils.isNotEmpty(customPath)) {
				return customPath;
			} else {
				return defaultDir + File.separator + FilePath.TEACHER_PHOTO + File.separator;
			}
		} else if (FilePath.DEGREE.equals(kind)) {
			customPath = (String) config.getConfigItemValue("system.document.degreePath");
			if (StringUtils.isNotEmpty(customPath)) {
				return customPath;
			} else {
				return defaultDir + File.separator + FilePath.DEGREE + File.separator;
			}
		} else if (FilePath.THESIS.equals(kind)) {
			customPath = (String) config.getConfigItemValue("system.document.degreeThesisPath");
			if (StringUtils.isNotEmpty(customPath)) {
				return customPath;
			} else {
				return defaultDir + File.separator + FilePath.THESIS + File.separator;
			}
		}// 系统模板路径不允许自定义
		else if (FilePath.TEMPLATE_UPLOAD.equals(kind)) {
			return defaultDir + File.separator + FilePath.TEMPLATE_UPLOAD + File.separator;
		} else if (FilePath.TEMPLATE_DOWNLOAD.equals(kind)) {
			return defaultDir + File.separator + FilePath.TEMPLATE_DOWNLOAD + File.separator;
		}
		throw new RuntimeException("not supported path type:" + kind);
	}
}
