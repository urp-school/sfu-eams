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
 * chaostone             2006-1-11            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.task;

import java.io.IOException;

import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachCommon;
import com.shufe.web.OutputMessage;
import com.shufe.web.OutputProcessObserver;
import com.shufe.web.OutputWebObserver;

/**
 * 教学任务生成观察者
 * 
 * @author Administrator
 * 
 */
public class TaskGenObserver extends OutputProcessObserver {
	public TaskGenObserver() {
		super();
	}

	public void notifyFinish() throws IOException {
		outputNotify(OutputWebObserver.good, new OutputMessage(
				"info.taskGenInit.finish", ""), false);
	}

	public void notifyGenResult(int schemeCount, int taskCount)
			throws IOException {
		OutputMessage message = new OutputMessage("", taskCount + " Tasks in "
				+ schemeCount + " Schemes generated");
		outputNotify(OutputWebObserver.good, message, false);
	}

	public String message(Object msgObj) {
		OutputMessage message = (OutputMessage) msgObj;
		return messageOf(message.getKey()) + message.getMessage();
	}

	public void outputNotify(int term, int courseCount, TeachPlan plan)
			throws IOException {
		OutputMessage message = new OutputMessage("", term + "  " + courseCount
				+ "  " + new TeachCommon(plan).toString());
		outputNotify(OutputWebObserver.good, message, true);
	}

	public void outputNotify(int term, TeachPlan plan, String messageKey,
			boolean increaceProcess) throws IOException {
		OutputMessage message = new OutputMessage("", messageOf(messageKey)
				+ " " + new TeachCommon(plan).toString());
		outputNotify(OutputWebObserver.good, message, increaceProcess);
	}
}
