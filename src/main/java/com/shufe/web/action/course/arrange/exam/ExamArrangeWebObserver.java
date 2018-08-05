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
 * chaostone             2006-11-15            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.arrange.exam;

import com.shufe.model.course.arrange.exam.ExamArrangeGroup;
import com.shufe.web.OutputMessage;
import com.shufe.web.OutputWebObserver;

public class ExamArrangeWebObserver extends OutputWebObserver {

	public void outputNotify(OutputMessage msgObj) {
		try {
			msgObj.setMessage(msgObj.getMessage()+"<br>");
			super.outputNotify(msgObj);
		} catch (Exception e) {

		}

	}

	public void notifyStart() {
		writer
				.write("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"></head>");
		writer
				.write("<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"scripts/common/Common.js\" ></script>");
		writer
				.write("<BODY LEFTMARGIN='0' TOPMARGIN='0' style='font-size:12px' onload='f_frameStyleResize(self)'>");
		writer.flush();

	}

	/**
	 * @see
	 */
	public void notifyFinish() {
		writer.println("<br>排考结束!");
		writer.write("</body></html>");
		writer.flush();
		writer.close();
	}

	public void outputNotify(ExamArrangeGroup group, OutputMessage message,
			int level) {
		try {
			message.setMessage(message.getMessage() + "["
					+ group.getCourse().getName() + "]<br>");
			outputNotify(level, message);
		} catch (Exception e) {

		}

	}
}
