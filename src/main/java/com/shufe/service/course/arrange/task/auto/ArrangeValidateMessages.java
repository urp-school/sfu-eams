//$Id: ArrangeValidateMessages.java,v 1.1 2006/11/09 11:22:28 duanth Exp $
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
 * chaostone             2005-12-20         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 教学任务可安排的验证消息
 * 
 * @author chaostone
 * 
 */
public class ArrangeValidateMessages implements Serializable {
	private static final long serialVersionUID = -7279580077305798588L;

	/**
	 * <p>
	 * Compares ArrangeValidateMessageItem objects.
	 * </p>
	 */
	private static final Comparator validateItemComparator = new Comparator() {
		public int compare(Object o1, Object o2) {
			return ((ArrangeValidateMessageItem) o1).getOrder()
					- ((ArrangeValidateMessageItem) o2).getOrder();
		}
	};

	// Manifest Constants
	public static String TASK = "entity.teachTask";

	public static String TASKGROUP = "entity.taskGroup";

	/**
	 * <p>
	 * The accumulated set of <code>ArrangeValidateMessage</code> objects
	 * (represented as an ArrayList) for each property, keyed by property name.
	 * </p>
	 */
	protected HashMap messages = new HashMap();

	/**
	 * <p>
	 * The current number of the property/key being added. This is used to
	 * maintain the order messages are added.
	 * </p>
	 */
	protected int iCount = 0;

	/**
	 * <p>
	 * Create an empty <code>ArrangeValidateMessages</code> object.
	 * </p>
	 */
	public ArrangeValidateMessages() {
		super();
	}

	/**
	 * <p>
	 * Add a message to the set of messages for the specified property. An order
	 * of the property/key is maintained based on the initial addition of the
	 * property/key.
	 * </p>
	 * 
	 * @param property
	 *            Property name (or ActionMessages.GLOBAL_MESSAGE)
	 * @param message
	 *            The message to be added
	 */
	public void add(String property, ArrangeValidateMessage message) {

		ArrangeValidateMessageItem item = (ArrangeValidateMessageItem) messages
				.get(property);
		List list = null;

		if (item == null) {
			list = new ArrayList();
			item = new ArrangeValidateMessageItem(list, iCount++, property);

			messages.put(property, item);
		} else {
			list = item.getList();
		}
		list.add(message);
	}

	/**
	 * <p>
	 * Adds the messages from the given <code>ArrangeValidateMessages</code>
	 * object to this set of messages. The messages are added in the order they
	 * are returned from the <code>properties</code> method. If a message's
	 * property is already in the current <code>ArrangeValidateMessages</code>
	 * object, it is added to the end of the list for that property. If a
	 * message's property is not in the current list it is added to the end of
	 * the properties.
	 * </p>
	 * 
	 * @param messages
	 *            The <code>ArrangeValidateMessages</code> object to be added.
	 *            This parameter can be <code>null</code>.
	 * @since Struts 1.1
	 */
	public void add(ArrangeValidateMessages messages) {

		if (messages == null) {
			return;
		}

		// loop over properties
		Iterator props = messages.properties();
		while (props.hasNext()) {
			String property = (String) props.next();
			// loop over messages for each property
			Iterator msgs = messages.get(property);
			while (msgs.hasNext()) {
				ArrangeValidateMessage msg = (ArrangeValidateMessage) msgs
						.next();
				this.add(property, msg);
			}
		}
	}

	/**
	 * <p>
	 * Return the set of messages related to a specific property. If there are
	 * no such messages, an empty enumeration is returned.
	 * </p>
	 * 
	 * @param property
	 *            Property name (or ArrangeValidateMessages.TASK)
	 */
	public Iterator get(String property) {
		ArrangeValidateMessageItem item = (ArrangeValidateMessageItem) messages
				.get(property);
		if (item == null) {
			return (Collections.EMPTY_LIST.iterator());
		} else {
			return (item.getList().iterator());
		}
	}

	public List getMassageList(String property) {
		ArrangeValidateMessageItem item = (ArrangeValidateMessageItem) messages
				.get(property);
		if (item == null) {
			return (Collections.EMPTY_LIST);
		} else {
			return (item.getList());
		}
	}

	/**
	 * <p>
	 * Return the set of all recorded messages, without distinction by which
	 * property the messages are associated with. If there are no messages
	 * recorded, an empty enumeration is returned.
	 * </p>
	 */
	public Iterator get() {
		if (messages.isEmpty()) {
			return Collections.EMPTY_LIST.iterator();
		}

		ArrayList results = new ArrayList();
		ArrayList actionItems = new ArrayList();

		for (Iterator i = messages.values().iterator(); i.hasNext();) {
			actionItems.add(i.next());
		}

		// Sort ActionMessageItems based on the initial order the
		// property/key was added to ActionMessages.
		Collections.sort(actionItems, validateItemComparator);

		for (Iterator i = actionItems.iterator(); i.hasNext();) {
			ArrangeValidateMessageItem ami = (ArrangeValidateMessageItem) i
					.next();

			for (Iterator messages = ami.getList().iterator(); messages
					.hasNext();) {
				results.add(messages.next());
			}
		}
		return results.iterator();
	}

	/**
	 * <p>
	 * Return the set of property names for which at least one message has been
	 * recorded. If there are no messages, an empty <code>Iterator</code> is
	 * returned. If you have recorded global messages, the <code>String</code>
	 * value of <code>ArrangeMessages.TASK</code> will be one of the returned
	 * property names.
	 * </p>
	 */
	public Iterator properties() {

		if (messages.isEmpty()) {
			return Collections.EMPTY_LIST.iterator();
		}

		ArrayList results = new ArrayList();
		ArrayList actionItems = new ArrayList();

		for (Iterator i = messages.values().iterator(); i.hasNext();) {
			actionItems.add(i.next());
		}

		// Sort ActionMessageItems based on the initial order the
		// property/key was added to ActionMessages.
		Collections.sort(actionItems, validateItemComparator);

		for (Iterator i = actionItems.iterator(); i.hasNext();) {
			ArrangeValidateMessageItem ami = (ArrangeValidateMessageItem) i
					.next();
			results.add(ami.getProperty());
		}

		return results.iterator();

	}

	/**
	 * <p>
	 * Return the number of messages recorded for all properties (including
	 * global messages). <strong>NOTE</strong> - it is more efficient to call
	 * <code>isEmpty</code> if all you care about is whether or not there are
	 * any messages at all.
	 * </p>
	 */
	public int size() {
		int total = 0;
		for (Iterator i = messages.values().iterator(); i.hasNext();) {
			ArrangeValidateMessageItem ami = (ArrangeValidateMessageItem) i
					.next();
			total += ami.getList().size();
		}
		return (total);
	}

	/**
	 * <p>
	 * Return the number of messages associated with the specified property.
	 * </p>
	 * 
	 * @param property
	 *            Property name (or ArrangeValidateMessages.TASK)
	 */
	public int size(String property) {
		ArrangeValidateMessageItem item = (ArrangeValidateMessageItem) messages
				.get(property);
		return (item == null) ? 0 : item.getList().size();
	}

	/**
	 * <p>
	 * Clear all messages recorded by this object.
	 * </p>
	 */
	public void clear() {
		messages.clear();
	}

	/**
	 * <p>
	 * Return <code>true</code> if there are no messages recorded in this
	 * collection, or <code>false</code> otherwise.
	 * </p>
	 * 
	 * @since Struts 1.1
	 */
	public boolean isEmpty() {
		return (messages.isEmpty());
	}

	/**
	 * <p>
	 * Returns a String representation of this ActionMessages' property
	 * name=message list mapping.
	 * </p>
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.messages.toString();
	}

	/**
	 * <p>
	 * This class is used to store a set of messages associated with a
	 * property/key and the position it was initially added to list.
	 * </p>
	 */
	protected class ArrangeValidateMessageItem implements Serializable {
		private static final long serialVersionUID = -7357197478545226632L;

		/**
		 * <p>
		 * The list of <code>ActionMessage</code>s.
		 * </p>
		 */
		protected List list = null;

		/**
		 * <p>
		 * The position in the list of messages.
		 * </p>
		 */
		protected int iOrder = 0;

		/**
		 * <p>
		 * The property associated with <code>ActionMessage</code>.
		 * </p>
		 */
		protected String property = null;

		public ArrangeValidateMessageItem(List list, int iOrder, String property) {
			this.list = list;
			this.iOrder = iOrder;
			this.property = property;
		}

		public List getList() {
			return list;
		}

		public void setList(List list) {
			this.list = list;
		}

		public int getOrder() {
			return iOrder;
		}

		public void setOrder(int iOrder) {
			this.iOrder = iOrder;
		}

		public String getProperty() {
			return property;
		}

		public void setProperty(String property) {
			this.property = property;
		}

		public String toString() {
			return this.list.toString();
		}

	}
}
