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
 * chaostone             2006-1-6            Created
 *  
 ********************************************************************************/
package com.shufe.service;

import java.io.IOException;
import java.io.PrintWriter;

import com.shufe.web.OutputMessage;

/**
 * 
 * @author Administrator
 * 
 */
public interface OutputObserver {
	public static int good = 1;

	public static int warnning = 2;

	public static int error = 3;

	public void outputNotify(int level, OutputMessage arg2) throws IOException;

	public void notifyStart() throws IOException;

	public void notifyFinish() throws IOException;

	public String message(OutputMessage msgObj);

	public PrintWriter getWriter();

	public void setWriter(PrintWriter writer);
}
