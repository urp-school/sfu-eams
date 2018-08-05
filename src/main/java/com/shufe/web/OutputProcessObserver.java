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
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-6-4            Created
 *  
 ********************************************************************************/
package com.shufe.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Locale;

import org.apache.struts.util.MessageResources;

/**
 * 可以体现进度条的内容输出者
 * 
 * @author chaostone
 * 
 */
public class OutputProcessObserver extends OutputWebObserver {

	protected String path;

	public OutputProcessObserver() {
		super();
	}

	public OutputProcessObserver(PrintWriter writer,
			MessageResources resourses, Locale locale, String path) {
		super(writer, resourses, locale);
		this.path = path;
		outputTemplate();
	}

	public void outputTemplate() {
		try {
			LineNumberReader reader = new LineNumberReader(
					new InputStreamReader(new FileInputStream(new File(path)),
							"UTF-8"));
			String lineContent = null;
			do {
				lineContent = reader.readLine();
				if (null != lineContent) {
					writer.write(lineContent+"\r\n");
				}
			} while (null != lineContent);
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException("IO Exception in read " + path);
		}
	}

	public void setSummary(String msg) {
		writer.write("<script>setSummary('" + msg + "')</script>\n");
		writer.flush();
	}

	public void setOverallCount(int count) {
		writer.write("<script>count=" + count + "</script>" + "\n");
		writer.flush();
	}

	/**
	 * @see com.shufe.web.OutputWebObserver#notifyStart()
	 */
	public void notifyStart(String summary, int count, String[] msgs) {
		try {
			setSummary(summary);
			setOverallCount(count);
			if (null != msgs) {
				for (int i = 0; i < msgs.length; i++) {
					writer.write("<script>addProcessMsg('" + msgs[i]
							+ "');</script>\n");
				}
				writer.flush();
			}
		} catch (Exception e) {
			throw new RuntimeException("IO Exeption:" + e.getMessage());
		}
	}

	/**
	 * @see com.shufe.web.OutputWebObserver#outputNotify(int, java.lang.Object)
	 */
	public void outputNotify(int level, OutputMessage msgObj,
			boolean increaceProcess) {
		try {
			if (increaceProcess)
				writer.print("<script>addProcessMsg(" + level + ",\""
						+ message(msgObj) + "\",1);</script>\n");
			else
				writer.print("<script>addProcessMsg(" + level + ",\""
						+ message(msgObj) + "\",0);</script>\n");
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException("IO Exeption:" + e.getMessage());
		}
	}

	public void outputNotify(int level, OutputMessage msgObj)
			throws IOException {
		outputNotify(level, msgObj, true);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
