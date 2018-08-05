//$Id: ArrangeManager.java,v 1.4 2006/12/06 03:37:25 duanth Exp $
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
 * chaostone             2005-11-8         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ekingstar.commons.lang.BitStringUtil;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.course.arrange.task.ArrangeParams;
import com.shufe.model.course.arrange.task.TaskGroup;
import com.shufe.model.course.task.TeachTask;
import com.shufe.service.course.arrange.task.auto.impl.ArrangeDAOListener;

/**
 * 排课管理类
 * 
 * @author chaostone 2005-11-22
 */
public class ArrangeManager {
	protected final static Logger logger = LoggerFactory.getLogger(ArrangeManager.class);
	
	private ArrangeDAOListener persistence;

	private ResultPrinter printer;

	private ArrangeFixture fixture;

	public ArrangeManager(ResultPrinter printer) {
		this.printer = printer;
	}

	public ArrangeManager() {
		printer = new ResultPrinter(System.out);
	}

	/**
	 * Creates the TestResult to be used for the test run.
	 */
	protected ArrangeResult createArrangeResult() {
		return new ArrangeResult();
	}

	/**
	 * Returns the formatted string of the elapsed time.
	 */
	public static String elapsedTimeAsString(long runTime) {
		return NumberFormat.getInstance().format((double) runTime / 1000);
	}

	/**
	 * 运行安排
	 * 
	 * @param suite
	 * @return
	 */
	public ArrangeResult doArrange(Arrange arrange) {
		return doArrange(arrange, Collections.singletonList(persistence));
	}

	/**
	 * 添加一个监听器
	 * 
	 * @param arrange
	 * @param optionListener
	 * @return
	 */
	public ArrangeResult doArrange(Arrange arrange,
			ArrangeListener optionListener) {
		ArrangeListener[] listeners = new ArrangeListener[] { persistence,
				optionListener };
		return doArrange(arrange, Arrays.asList(listeners));
	}

	/**
	 * 指定监听器安排
	 * 
	 * @param arrange
	 * @param listeners
	 * @return
	 */
	public ArrangeResult doArrange(Arrange arrange, Collection listeners) {
		ArrangeResult result = createArrangeResult();
		for (Iterator iter = listeners.iterator(); iter.hasNext();)
			addListener(result, (ArrangeListener) iter.next());
		long startTime = System.currentTimeMillis();
		result.notifyStart();
		arrange.arrange(result);
		result.notifyEnd();
		if (logger.isDebugEnabled()) {
			long endTime = System.currentTimeMillis();
			long runTime = endTime - startTime;
			printer.print(result, runTime);
		}
		return result;
	}

	/**
	 * 创建一个排课组
	 * 
	 * @return
	 */
	public Arrange makeArrange(TaskGroup group, ArrangeParams params) {
		Arrange arrange = null;
		//如果排在同一时间
		if (Boolean.TRUE.equals(group.getIsSameTime())) {
            arrange = new ArrangeGroup(group);
		} else {
            if(Boolean.TRUE.equals(group.getIsClassChange())){
                arrange=new SpecialArrangeGroup(group);
            }else{
    			//建立课程组内部的排课组
    			ArrangeSuite suite = new ArrangeSuite();
    			//合并上级组的可用时间.如果上级可用时间为空，则合并下级组自定义对可用时间
    			AvailableTime parentTime = (null == group.getParent()) ? null
    					: group.getParent().getSuggest().getTime();
    			suite.setAvailableTime(mergeAvailableTime(group.getSuggest()
    					.getTime(), parentTime));
    			suite.setClassrooms(group.getSuggest().getRooms());
    			suite.setFixture(fixture);
    			suite.setPriority(group.getPriority().intValue());
    			//添加单独课程
    			if (null != group.getDirectTasks()
    					&& !group.getDirectTasks().isEmpty())
    				for (Iterator iter = group.getDirectTasks().iterator(); iter
    						.hasNext();) {
    					TeachTask task = (TeachTask) iter.next();
    					//如果已经排了，就不再排课了
    					if (Boolean.TRUE.equals(task.getArrangeInfo()
    							.getIsArrangeComplete()))
    						continue;
    					ArrangeCase arrangeCase = new ArrangeCase(task);
    					arrangeCase.setSuite(suite);
    					// 设置任务排课的建议可用时间
    					if (params.getBySuggest()[ArrangeParams.TIME]) {
    						if (null == task.getArrangeInfo().getSuggest()
    								.getTime())
    							arrangeCase.setAvailableTime(suite
    									.getAvailableTime());
    						else {
    							arrangeCase.setAvailableTime(task.getArrangeInfo()
    									.getSuggest().getTime());
    						}
    					} else {
    						arrangeCase.setAvailableTime(suite.getAvailableTime());
    					}
    					arrangeCase.setPriority(group.getPriority().intValue());
    					suite.addArrange(arrangeCase);
    				}
    			//添加嵌套课程组
    			if (null != group.getGroups() && !group.getGroups().isEmpty())
    				for (Iterator iter = group.getGroups().iterator(); iter
    						.hasNext();) {
    					TaskGroup nestedGroup = (TaskGroup) iter.next();
    					Arrange groupArrange = makeArrange(nestedGroup, params);
    					groupArrange.setSuite(suite);
    					groupArrange.setPriority(group.getPriority().intValue());
    					suite.addArrange(groupArrange);
    				}
    			arrange = suite;
            }
		}
		return arrange;
	}

	/**
	 * 合并父子排课组的可用时间
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	private AvailableTime mergeAvailableTime(AvailableTime child,
			AvailableTime parent) {
		if (null == parent)
			return child;
		if (null == child)
			return parent;
		else {
			child.setAvailable(BitStringUtil.andWith(child.getAvailable(),
					parent.getAvailable()));
			return child;
		}
	}

	// TODO for single task
	public Arrange makeArrange(TeachTask task) {
		return null;
	}

	/**
	 * @return Returns the fixture.
	 */
	public ArrangeFixture getFixture() {
		return fixture;
	}

	/**
	 * @param fixture
	 *            The fixture to set.
	 */
	public void setFixture(ArrangeFixture fixture) {
		this.fixture = fixture;
	}

	/**
	 * @return Returns the printer.
	 */
	public ResultPrinter getPrinter() {
		return printer;
	}

	/**
	 * @param printer
	 *            The printer to set.
	 */
	public void setPrinter(ResultPrinter printer) {
		this.printer = printer;
	}

	/**
	 * @param persistence
	 *            The persistence to set.
	 */
	public void setPersistence(ArrangeDAOListener persistence) {
		this.persistence = persistence;
	}

	public void addListener(ArrangeResult rs, ArrangeListener listener) {
		if (null == listener)
			return;
		else
			rs.addListener(listener);
	}

}
